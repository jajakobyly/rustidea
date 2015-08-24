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

// Version of Rust lexer tuned for syntax highlighting. It yields a very reduced
// subset of Rust tokens, defined in `RustHighlighterTypes` object. The main
// goal of this lexer is to have as few DFA states as possible (performance),
// and generate tokens useful in highlighting (like valid/invalid escapes).
//
// https://doc.rust-lang.org/nightly/reference.html
// Based on Rust 1.4.0-nightly (7e13faee1)

package org.rustidea.lexer;

import com.intellij.lexer.FlexLexer;
import com.intellij.psi.tree.IElementType;
import com.intellij.util.containers.Stack;
import com.intellij.util.containers.ContainerUtil;
import static com.intellij.psi.TokenType.WHITE_SPACE;
import static com.intellij.psi.TokenType.BAD_CHARACTER;

import static org.rustidea.editor.RustHighlighterTypes.*;


%%


%{
    enum CommentType {
        PARENT_DOC,
        DOC,
        NORMAL;

        private IElementType elementType;

        static {
            PARENT_DOC.elementType = SH_BLOCK_PARENT_DOC;
            DOC.elementType        = SH_BLOCK_DOC;
            NORMAL.elementType     = SH_BLOCK_COMMENT;
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

    public _RustHighlighterLexer() {
        this((java.io.Reader)null);
    }

    private void yypushstate(int state) {
        stateStack.push(yystate());
        yybegin(state);
    }

    private void yypopstate() {
        yybegin(stateStack.pop());
    }

    private void beginCompositeToken(int state) {
        yypushstate(state);
         // Grrrr zzStartRead is JFlex internal.
        tokenStartStack.push(zzStartRead);
    }

    private void endCompositeToken() {
        yypopstate();
        zzStartRead = tokenStartStack.pop();
    }

    private void beginBlockComment(CommentType ctype) {
        beginCompositeToken(IN_BLOCK_COMMENT);
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

%class _RustHighlighterLexer
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
    "abstract"  |
    "alignof"   |
    "as"        |
    "become"    |
    "box"       |
    "break"     |
    "const"     |
    "continue"  |
    "crate"     |
    "do"        |
    "else"      |
    "enum"      |
    "extern"    |
    "false"     |
    "final"     |
    "fn"        |
    "for"       |
    "if"        |
    "impl"      |
    "in"        |
    "let"       |
    "loop"      |
    "macro"     |
    "match"     |
    "mod"       |
    "move"      |
    "mut"       |
    "offsetof"  |
    "override"  |
    "priv"      |
    "proc"      |
    "pub"       |
    "pure"      |
    "ref"       |
    "return"    |
    "Self"      |
    "self"      |
    "sizeof"    |
    "static"    |
    "struct"    |
    "super"     |
    "trait"     |
    "true"      |
    "type"      |
    "typeof"    |
    "unsafe"    |
    "unsized"   |
    "use"       |
    "virtual"   |
    "where"     |
    "while"     |
    "yield"     { return SH_KEYWORD; }


    //=== Operators
    //=== https://doc.rust-lang.org/nightly/grammar.html#symbols
    //=== https://doc.rust-lang.org/nightly/reference.html#expressions
    "&"       |
    "->"      |
    "="       |
    "*"       |
    "@"       |
    "^"       |
    ":"       |
    "$"       |
    "&&"      |
    "::"      |
    ".."      |
    "||"      |
    "=="      |
    "!"       |
    "=>"      |
    ">"       |
    ">="      |
    "#"       |
    "<<"      |
    "<"       |
    "<="      |
    "-"       |
    "!="      |
    "%"       |
    "|"       |
    "+"       |
    ">>"      |
    "/"       |
    "..."     |
    "_"       { return SH_OPERATOR; }
    "."       { return SH_DOT; }
    ","       { return SH_COMMA; }
    ";"       { return SH_SEMICOLON; }
    "(" | ")" { return SH_PARENTHESES; }
    "[" | "]" { return SH_BRACKETS; }
    "{" | "}" { return SH_BRACES; }


    //=== Comments & Docs
    //=== https://doc.rust-lang.org/nightly/reference.html#comments
    "//!" ~{EOL}                     { return SH_LINE_PARENT_DOC; }
    "///" ~{EOL}                     { return SH_LINE_DOC; }
    "///" "/"+ ~{EOL} | "//" ~{EOL}  { return SH_LINE_COMMENT; }

    "/*!"      { beginBlockComment(CommentType.PARENT_DOC); }
    "/**"[^*/] { beginBlockComment(CommentType.DOC); }
    "/*"       { beginBlockComment(CommentType.NORMAL); }


    //=== Lifetimes
    //=== I couldn't find docs for them in both Rust Reference and Grammar
    "'" {IDENTIFIER} { return SH_LIFETIME; }


    //=== Character & string literals
    //=== https://doc.rust-lang.org/nightly/reference.html#characters-and-strings
    //=== with little exception: \0 \' and \" are valid escapes in rustc lexer
    //    (they are covered by BYTE_ESCAPE macro)
    // TODO Highlight escapes in char & byte literals
    {BYTE_LIT} |
    {CHAR_LIT} { return SH_SINGLE_CHAR; }

    "b'" [^] "'" { return SH_INVALID_ESCAPE; }

    "br" "#"* "\"" {
        yybegin(IN_RAW_BYTE_STR_LIT);
        rawStringHashes = yylength() - 2;
        return SH_RAW_STRING;
    }

    "r" "#"* "\"" {
        yybegin(IN_RAW_STR_LIT);
        rawStringHashes = yylength() - 1;
        return SH_RAW_STRING;
    }

    "b\"" {
        yybegin(IN_BYTE_STR_LIT);
        return SH_STRING;
    }

    "\"" {
        yybegin(IN_STR_LIT);
        return SH_STRING;
    }


    {DEC_LIT}        |
    {BIN_LIT}        |
    {OCT_LIT}        |
    {HEX_LIT}        |
    // Prevent matching ranges as [float] [dot] [dec], e.g. 0..9 as 0. . 9
    {DEC_LIT} / ".." { return SH_NUMBER; }
    {FLOAT_LIT}      { return SH_NUMBER; }


    //=== Macros
    //=== https://doc.rust-lang.org/nightly/reference.html#macros
    "$" {IDENTIFIER} { return SH_MACRO_VAR; }
    {IDENTIFIER} "!" { return SH_MACRO_CALL; }


    {IDENTIFIER} { return SH_IDENTIFIER; }


    [^] { return BAD_CHARACTER; }
}


<IN_BLOCK_COMMENT> {
    "*/" {
        commentDepth--;
        if (commentDepth == 0) {
            endCompositeToken();
            return commentType.getElementType();
        }
    }

    "/*" { commentDepth++; }

    <<EOF>> {
        endCompositeToken();
        // TODO Maybe return BAD_CHARACTER?
        return commentType.getElementType();
    }

    [^] { /* do nothing */ }
}


<IN_STR_LIT> {
    "\\" ( {BYTE_ESCAPE} | {UNICODE_ESCAPE} | {EOL} ) { return SH_VALID_ESCAPE; }
    [^\"]                                             { return SH_STRING; }

    "\""    { yybegin(YYINITIAL); return SH_STRING; }
    <<EOF>> { yybegin(YYINITIAL); return BAD_CHARACTER; }
//    [^]     { return BAD_CHARACTER; }
}


<IN_BYTE_STR_LIT> {
    "\\" ( {BYTE_ESCAPE} | {EOL} )        { return SH_VALID_ESCAPE; }
    // ASCII excluding \t \n \r "
    [\x00-\x08\x0B\x0C\x0E-\x21\x23-\x7F] { return SH_STRING; }

    "\""    { yybegin(YYINITIAL); return SH_STRING; }
    <<EOF>> { yybegin(YYINITIAL); return BAD_CHARACTER; }
    [^]     { return SH_INVALID_ESCAPE; }
}


<IN_RAW_STR_LIT> {
    "\"" "#"* { if (endRawString()) return SH_RAW_STRING; }
    <<EOF>>   { yybegin(YYINITIAL); return BAD_CHARACTER; }
    [^]       { return SH_RAW_STRING; }
}


<IN_RAW_BYTE_STR_LIT> {
    "\"" "#"*   { if (endRawString()) return SH_RAW_STRING; }
    [\x00-\x7F] { return SH_RAW_STRING; }
    <<EOF>>     { yybegin(YYINITIAL); return BAD_CHARACTER; }
    [^]         { return SH_INVALID_ESCAPE; }
}
