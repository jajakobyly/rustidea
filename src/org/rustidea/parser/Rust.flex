/*
 * Copyright 2015 Marek Kaput
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

// https://doc.rust-lang.org/nightly/reference.html
// Based on Rust 1.4.0-nightly (7e13faee1)

package org.rustidea.parser;

import com.intellij.lexer.FlexLexer;
import com.intellij.psi.tree.IElementType;
import com.intellij.util.containers.Stack;
import com.intellij.util.containers.ContainerUtil;
import static com.intellij.psi.TokenType.WHITE_SPACE;
import static com.intellij.psi.TokenType.BAD_CHARACTER;

import static org.rustidea.psi.RustTypes.*;


%%


%{
    enum CommentType {
        PARENT_DOC,
        DOC,
        NORMAL;

        private IElementType elementType;

        static {
            PARENT_DOC.elementType = BLOCK_PARENT_DOC;
            DOC.elementType = BLOCK_DOC;
            NORMAL.elementType = BLOCK_COMMENT;
        }

        public IElementType getElementType() {
          return elementType;
        }
    }

    private Stack<Integer> stateStack = ContainerUtil.newStack();
    private Stack<Integer> tokenStartStack = ContainerUtil.newStack();

    private int commentDepth = 0;
    private CommentType commentType = CommentType.NORMAL;

    private int rawStringHashes = 0;

    public _RustLexer() {
        this((java.io.Reader)null);
    }

    private void yypushstate(int state) {
        stateStack.push(yystate());
        yybegin(state);
    }

    private void yypopstate() {
        yybegin(stateStack.pop());
    }

    // TODO Invent better names for `beginComplexToken` & `endComplexToken`.
    private void beginComplexToken(int state) {
        yypushstate(state);
         // FIXME Grrrr zzStartRead is JFlex internal.
        tokenStartStack.push(zzStartRead);
    }

    private void endComplexToken() {
        yypopstate();
        zzStartRead = tokenStartStack.pop();
    }

    private void beginBlockComment(CommentType ctype) {
        beginComplexToken(IN_BLOCK_COMMENT);
        commentType = ctype;
        commentDepth = 1;
    }

    private boolean endRawString() {
        if (yylength() >= rawStringHashes) {
            // Handle this situation:
            //  r##"foo"########
            //           ^ here desired token ends
            //                 ^ but we are here
            if (yylength() > rawStringHashes) {
                yypushback(yylength() - rawStringHashes);
            }
            yybegin(YYINITIAL);
            return true;
        }
        return false;
    }
%}

%class _RustLexer
%implements FlexLexer
%unicode
%function advance
%type IElementType


//=== White spaces
//=== https://doc.rust-lang.org/nightly/reference.html#whitespace
EOL              = \n | \r | \r\n
LINE_WS          = [\ \t]
WHITE_SPACE_CHAR = {EOL} | {LINE_WS}
WHITE_SPACE      = ({WHITE_SPACE_CHAR})+


//=== Identifiers
//=== https://doc.rust-lang.org/nightly/reference.html#identifiers
// FIXME These macros define Java's identifier name, not Rust's one
XID_START    = [:jletter:]
XID_CONTINUE = [:jletterdigit:]
IDENTIFIER   = {XID_START} {XID_CONTINUE}*


//=== Literals
//=== https://doc.rust-lang.org/nightly/reference.html#literals

// Notice that there is no forward slash at the beginning
BYTE_ESCAPE    = [nrt\\0'\"] | "x" [a-fA-F0-9]{2} // \" \' \0 are undocumented
UNICODE_ESCAPE = "u{" [a-fA-F0-9]{1,6} "}"

CHAR_LIT = "'" ( [^\t\r\n'] | ( "\\" ( {BYTE_ESCAPE} | {UNICODE_ESCAPE} ) ) ) "'"
BYTE_LIT = "b'" ( [\x00-\x08\x0B\x0C\x0E-\x21\x23-\x7F] | ( "\\" {BYTE_ESCAPE} ) ) "'"

INT_SUFFIX   = [ui]("8"|"16"|"32"|"64"|"size")
FLOAT_SUFFIX = "f"("32"|"64")

DEC_LIT = [0-9] [0-9_]* {INT_SUFFIX}?
BIN_LIT = "0b" [01_]+ {INT_SUFFIX}?
OCT_LIT = "0o" [0-7_]+ {INT_SUFFIX}?
HEX_LIT = "0x" [a-fA-F0-9_]+ {INT_SUFFIX}?

EXPONENT    = [eE] [-+]? [0-9_]+
_FLOAT_LIT1 = [0-9] [0-9_]* "." [0-9] [0-9_]* {EXPONENT}? {FLOAT_SUFFIX}?
_FLOAT_LIT2 = [0-9] [0-9_]* {EXPONENT} {FLOAT_SUFFIX}?
_FLOAT_LIT3 = [0-9] [0-9_]* "."
FLOAT_LIT   = {_FLOAT_LIT1} | {_FLOAT_LIT2} | {_FLOAT_LIT3}


%state IN_BLOCK_COMMENT
%state IN_STR_LIT, IN_BYTE_STR_LIT, IN_RAW_STR_LIT, IN_RAW_BYTE_STR_LIT


%%


<YYINITIAL> {
    {WHITE_SPACE} { return WHITE_SPACE; }


    //=== Keywords
    //=== https://doc.rust-lang.org/nightly/grammar.html#keywords
    "abstract"  { return KW_ABSTRACT; }
    "alignof"   { return KW_ALIGNOF; }
    "as"        { return KW_AS; }
    "become"    { return KW_BECOME; }
    "box"       { return KW_BOX; }
    "break"     { return KW_BREAK; }
    "const"     { return KW_CONST; }
    "continue"  { return KW_CONTINUE; }
    "crate"     { return KW_CRATE; }
    "do"        { return KW_DO; }
    "else"      { return KW_ELSE; }
    "enum"      { return KW_ENUM; }
    "extern"    { return KW_EXTERN; }
    "false"     { return KW_FALSE; }
    "final"     { return KW_FINAL; }
    "fn"        { return KW_FN; }
    "for"       { return KW_FOR; }
    "if"        { return KW_IF; }
    "impl"      { return KW_IMPL; }
    "in"        { return KW_IN; }
    "let"       { return KW_LET; }
    "loop"      { return KW_LOOP; }
    "macro"     { return KW_MACRO; }
    "match"     { return KW_MATCH; }
    "mod"       { return KW_MOD; }
    "move"      { return KW_MOVE; }
    "mut"       { return KW_MUT; }
    "offsetof"  { return KW_OFFSETOF; }
    "override"  { return KW_OVERRIDE; }
    "priv"      { return KW_PRIV; }
    "proc"      { return KW_PROC; }
    "pub"       { return KW_PUB; }
    "pure"      { return KW_PURE; }
    "ref"       { return KW_REF; }
    "return"    { return KW_RETURN; }
    "Self"      { return KW_SELF_T; }
    "self"      { return KW_SELF; }
    "sizeof"    { return KW_SIZEOF; }
    "static"    { return KW_STATIC; }
    "struct"    { return KW_STRUCT; }
    "super"     { return KW_SUPER; }
    "trait"     { return KW_TRAIT; }
    "true"      { return KW_TRUE; }
    "type"      { return KW_TYPE; }
    "typeof"    { return KW_TYPEOF; }
    "unsafe"    { return KW_UNSAFE; }
    "unsized"   { return KW_UNSIZED; }
    "use"       { return KW_USE; }
    "virtual"   { return KW_VIRTUAL; }
    "where"     { return KW_WHERE; }
    "while"     { return KW_WHILE; }
    "yield"     { return KW_YIELD; }


    //=== Operators
    //=== https://doc.rust-lang.org/nightly/grammar.html#symbols
    //=== https://doc.rust-lang.org/nightly/reference.html#expressions
    "&"    { return OP_AMPERSAND; }
    "->"   { return OP_ARROW; }
    "="    { return OP_ASSIGN; }
    "*"    { return OP_ASTERISK; }
    "@"    { return OP_AT; }
    "^"    { return OP_CARET; }
    "}"    { return OP_CLOSE_BRACE; }
    "]"    { return OP_CLOSE_BRACKET; }
    ")"    { return OP_CLOSE_PAREN; }
    ":"    { return OP_COLON; }
    ","    { return OP_COMMA; }
    "$"    { return OP_DOLLAR; }
    "."    { return OP_DOT; }
    "&&"   { return OP_DOUBLE_AMPERSAND; }
    "::"   { return OP_DOUBLE_COLON; }
    ".."   { return OP_DOUBLE_DOT; }
    "||"   { return OP_DOUBLE_PIPE; }
    "=="   { return OP_EQUALS; }
    "!"    { return OP_EXCLAMATION_MARK; }
    "=>"   { return OP_FAT_ARROW; }
    ">"    { return OP_GREATER_THAN; }
    ">="   { return OP_GREATER_THAN_EQ; }
    "#"    { return OP_HASH; }
    "<<"   { return OP_LEFT_SHIFT; }
    "<"    { return OP_LESS_THAN; }
    "<="   { return OP_LESS_THAN_EQ; }
    "-"    { return OP_MINUS; }
    "!="   { return OP_NOT_EQUALS; }
    "{"    { return OP_OPEN_BRACE; }
    "["    { return OP_OPEN_BRACKET; }
    "("    { return OP_OPEN_PAREN; }
    "%"    { return OP_PERCENT; }
    "|"    { return OP_PIPE; }
    "+"    { return OP_PLUS; }
    ">>"   { return OP_RIGHT_SHIFT; }
    ";"    { return OP_SEMICOLON; }
    "/"    { return OP_SLASH; }
    "..."  { return OP_TRIPLE_DOT; }
    "_"    { return OP_UNDERSCORE; }


    //=== Comments & Docs
    //=== https://doc.rust-lang.org/nightly/reference.html#comments
    // FIXME Following fragment is tokenized as comment:
    //     //! foo
    //     /// foo
    ("//!" ~{EOL})+                     { return LINE_PARENT_DOC; }
    ("///" ~{EOL})+                     { return LINE_DOC; }
    ("///" "/"+ ~{EOL} | "//" ~{EOL})+  { return LINE_COMMENT; }

    "/*!"      { beginBlockComment(CommentType.PARENT_DOC); }
    // FIXME This rule consumes first character of comment contents
    "/**"[^*/] { beginBlockComment(CommentType.DOC); }
    "/*"       { beginBlockComment(CommentType.NORMAL); }


    //=== Lifetimes
    //=== I couldn't find docs for them in both Rust Reference and Grammar
    "'" {IDENTIFIER} { return LIFETIME; }


    //=== Character & string literals
    //=== https://doc.rust-lang.org/nightly/reference.html#characters-and-strings
    //=== with little exception: \0 \' and \" are valid escapes in rustc lexer
    //    (they are covered by BYTE_ESCAPE macro)
    // TODO Highlight escapes in char & byte literals
    {BYTE_LIT} { return BYTE_LIT; }
    {CHAR_LIT} { return CHAR_LIT; }

    "b'" [^] "'" { return INVALID_BYTE_LIT; }

    "br" "#"* "\"" {
        yybegin(IN_RAW_BYTE_STR_LIT);
        rawStringHashes = yylength() - 2;
        return RAW_BYTE_STR_LIT_BEGIN;
    }

    "r" "#"* "\"" {
        yybegin(IN_RAW_STR_LIT);
        rawStringHashes = yylength() - 1;
        return RAW_STR_LIT_BEGIN;
    }

    "b\"" {
        yybegin(IN_BYTE_STR_LIT);
        return BYTE_STR_LIT_BEGIN;
    }

    "\"" {
        yybegin(IN_STR_LIT);
        return STR_LIT_BEGIN;
    }


    {DEC_LIT}        { return DEC_LIT; }
    {BIN_LIT}        { return BIN_LIT; }
    {OCT_LIT}        { return OCT_LIT; }
    {HEX_LIT}        { return HEX_LIT; }
    // Prevent matching ranges as [float] [dot] [dec], e.g. 0..9 as 0. . 9
    {DEC_LIT} / ".." { return DEC_LIT; }
    {FLOAT_LIT}      { return FLOAT_LIT; }


    //=== Macros
    //=== https://doc.rust-lang.org/nightly/reference.html#macros
    "$" {IDENTIFIER} { return MACRO_VARIABLE; }
    {IDENTIFIER} "!" { return MACRO_CALL; }


    {IDENTIFIER} { return IDENTIFIER; }


    [^] { return BAD_CHARACTER; }
}


<IN_BLOCK_COMMENT> {
    "*/" {
        commentDepth--;
        if (commentDepth == 0) {
            endComplexToken();
            return commentType.getElementType();
        }
    }

    "/*" { commentDepth++; }

    <<EOF>> {
        endComplexToken();
        // TODO Maybe return BAD_CHARACTER?
        return commentType.getElementType();
    }

    [^] { /* do nothing */ }
}


<IN_STR_LIT> {
    "\\" ( {BYTE_ESCAPE} | {UNICODE_ESCAPE} | {EOL} ) { return STR_LIT_ESCAPE; }
    [^\"]                                             { return STR_LIT_TOKEN; }

    "\""    { yybegin(YYINITIAL); return STR_LIT_END; }
    <<EOF>> { yybegin(YYINITIAL); return BAD_CHARACTER; }
    [^]     { return BAD_CHARACTER; }
}


<IN_BYTE_STR_LIT> {
    "\\" ( {BYTE_ESCAPE} | {EOL} )        { return BYTE_STR_LIT_ESCAPE; }
    // ASCII excluding \t \n \r "
    [\x00-\x08\x0B\x0C\x0E-\x21\x23-\x7F] { return BYTE_STR_LIT_TOKEN; }

    "\""    { yybegin(YYINITIAL); return BYTE_STR_LIT_END; }
    <<EOF>> { yybegin(YYINITIAL); return BAD_CHARACTER; }
    [^]     { return INVALID_BYTE_STR_LIT_TOKEN; }
}


<IN_RAW_STR_LIT> {
    "\"" "#"* { if (endRawString()) return RAW_STR_LIT_END; }
    <<EOF>>   { yybegin(YYINITIAL); return BAD_CHARACTER; }
    [^]       { return RAW_STR_LIT_TOKEN; }
}


<IN_RAW_BYTE_STR_LIT> {
    "\"" "#"*   { if (endRawString()) return RAW_BYTE_STR_LIT_END; }
    [\x00-\x7F] { return RAW_BYTE_STR_LIT_TOKEN; }
    <<EOF>>     { yybegin(YYINITIAL); return BAD_CHARACTER; }
    [^]         { return INVALID_RAW_BYTE_STR_LIT_TOKEN; }
}
