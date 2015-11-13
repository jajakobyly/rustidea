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

package org.rustidea.lexer;

import com.intellij.lexer.FlexLexer;
import com.intellij.psi.tree.IElementType;
import com.intellij.util.containers.Stack;
import com.intellij.util.containers.ContainerUtil;
import org.rustidea.psi.types.RsTokenTypes;


%%


%{
    enum CommentType {
        INNER_DOC,
        DOC,
        NORMAL;

        private IElementType elementType;

        static {
            INNER_DOC.elementType = RsTokenTypes.BLOCK_INNER_DOC;
            DOC.elementType        = RsTokenTypes.BLOCK_DOC;
            NORMAL.elementType     = RsTokenTypes.BLOCK_COMMENT;
        }

        public IElementType getElementType() {
          return elementType;
        }
    }

    private Stack<Integer> stateStack = ContainerUtil.newStack();
    private Stack<Integer> tokenStartStack = ContainerUtil.newStack();

    private int commentDepth = 0;
    private CommentType commentType = CommentType.NORMAL;

    private boolean rawStringIsByte = false;
    private int rawStringHashes = 0;

    public _RsLexer() {
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

    private IElementType endBlockComment() {
        endCompositeToken();
        return commentType.getElementType();
    }

    private void beginRawString(boolean isByte, int hashes) {
        beginCompositeToken(IN_RAW_STRING);
        rawStringIsByte = isByte;
        rawStringHashes = hashes;
    }

    private boolean endRawString() {
        CharSequence text = yytext();
        int index = 1;
        while (index < text.length() && text.charAt(index) == '#') index++;
        int hashes = index - 1;

        if (hashes >= rawStringHashes) {
            // Handle this situation:
            //  r##"foo"########
            //           ^ here desired token ends
            //                 ^ but we are here
            if (hashes > rawStringHashes) {
                yypushback(yylength() - rawStringHashes - 1);
            }
            endCompositeToken();
            return true;
        }
        return false;
    }

    private IElementType returnRawString() {
        return rawStringIsByte ? RsTokenTypes.RAW_BYTE_STRING_LIT : RsTokenTypes.RAW_STRING_LIT;
    }
%}

%class _RsLexer
%implements FlexLexer
%unicode
%function advance
%type IElementType


EOL              = \n | \r | \r\n
LINE_WS          = [\ \t]
WHITE_SPACE_CHAR = {EOL} | {LINE_WS}
WHITE_SPACE      = {WHITE_SPACE_CHAR}+

// XID_START ::= [[:L:][:Nl:][:Other_ID_Start:]--[:Pattern_Syntax:]--[:Pattern_White_Space:]]
// XID_CONTINUE ::= [[:ID_Start:][:Mn:][:Mc:][:Nd:][:Pc:][:Other_ID_Continue:]--[:Pattern_Syntax:]--[:Pattern_White_Space:]]
// FIXME These rules do not comply in 100% with spec
XID_START    = [:letter:]
XID_CONTINUE = {XID_START} | [:digit:] | _
IDENTIFIER   = {XID_START} {XID_CONTINUE}*

SUFFIX = {IDENTIFIER}?

// Character literals without ending single quote conflict with lifetimes and labels
CHAR_LIT   = \' ( [^\\\'\r\n] | \\[^\r\n] | "\\x" [a-zA-Z0-9]+ | "\\u{" [a-zA-Z0-9]* "}"? )? ( \' {SUFFIX} | \\ )?
STRING_LIT = \" ( [^\\\"\r\n] | \\[^] )* ( \" {SUFFIX} | \\ )?

RAW_STRING_BEGIN = r #* \"
RAW_STRING_END   = \" #* {SUFFIX}

DEC_LIT = [0-9] [0-9_]* {SUFFIX}
BIN_LIT = "0b" [01_]+ {SUFFIX}
OCT_LIT = "0o" [0-7_]+ {SUFFIX}
HEX_LIT = "0x" [a-fA-F0-9_]+ {SUFFIX}

EXPONENT    = [eE] [-+]? [0-9_]+
_FLOAT_LIT1 = [0-9] [0-9_]* \. [0-9] [0-9_]* {EXPONENT}? {SUFFIX}
_FLOAT_LIT2 = [0-9] [0-9_]* {EXPONENT} {SUFFIX}
_FLOAT_LIT3 = [0-9] [0-9_]* \.
FLOAT_LIT   = {_FLOAT_LIT1} | {_FLOAT_LIT2} | {_FLOAT_LIT3}

LINE_INNER_DOC = "//!" [^\r\n]*
LINE_DOC = "///" [^\r\n]*


%state IN_BLOCK_COMMENT, IN_RAW_STRING


%%


<YYINITIAL> {
    {WHITE_SPACE} { return RsTokenTypes.WHITE_SPACE; }

    "abstract"  { return RsTokenTypes.KW_ABSTRACT; }
    "alignof"   { return RsTokenTypes.KW_ALIGNOF; }
    "as"        { return RsTokenTypes.KW_AS; }
    "become"    { return RsTokenTypes.KW_BECOME; }
    "box"       { return RsTokenTypes.KW_BOX; }
    "break"     { return RsTokenTypes.KW_BREAK; }
    "const"     { return RsTokenTypes.KW_CONST; }
    "continue"  { return RsTokenTypes.KW_CONTINUE; }
    "crate"     { return RsTokenTypes.KW_CRATE; }
    "do"        { return RsTokenTypes.KW_DO; }
    "else"      { return RsTokenTypes.KW_ELSE; }
    "enum"      { return RsTokenTypes.KW_ENUM; }
    "extern"    { return RsTokenTypes.KW_EXTERN; }
    "false"     { return RsTokenTypes.KW_FALSE; }
    "final"     { return RsTokenTypes.KW_FINAL; }
    "fn"        { return RsTokenTypes.KW_FN; }
    "for"       { return RsTokenTypes.KW_FOR; }
    "if"        { return RsTokenTypes.KW_IF; }
    "impl"      { return RsTokenTypes.KW_IMPL; }
    "in"        { return RsTokenTypes.KW_IN; }
    "let"       { return RsTokenTypes.KW_LET; }
    "loop"      { return RsTokenTypes.KW_LOOP; }
    "macro"     { return RsTokenTypes.KW_MACRO; }
    "match"     { return RsTokenTypes.KW_MATCH; }
    "mod"       { return RsTokenTypes.KW_MOD; }
    "move"      { return RsTokenTypes.KW_MOVE; }
    "mut"       { return RsTokenTypes.KW_MUT; }
    "offsetof"  { return RsTokenTypes.KW_OFFSETOF; }
    "override"  { return RsTokenTypes.KW_OVERRIDE; }
    "priv"      { return RsTokenTypes.KW_PRIV; }
    "proc"      { return RsTokenTypes.KW_PROC; }
    "pub"       { return RsTokenTypes.KW_PUB; }
    "pure"      { return RsTokenTypes.KW_PURE; }
    "ref"       { return RsTokenTypes.KW_REF; }
    "return"    { return RsTokenTypes.KW_RETURN; }
    "Self"      { return RsTokenTypes.KW_SELF_T; }
    "self"      { return RsTokenTypes.KW_SELF; }
    "sizeof"    { return RsTokenTypes.KW_SIZEOF; }
    "static"    { return RsTokenTypes.KW_STATIC; }
    "struct"    { return RsTokenTypes.KW_STRUCT; }
    "super"     { return RsTokenTypes.KW_SUPER; }
    "trait"     { return RsTokenTypes.KW_TRAIT; }
    "true"      { return RsTokenTypes.KW_TRUE; }
    "type"      { return RsTokenTypes.KW_TYPE; }
    "typeof"    { return RsTokenTypes.KW_TYPEOF; }
    "unsafe"    { return RsTokenTypes.KW_UNSAFE; }
    "unsized"   { return RsTokenTypes.KW_UNSIZED; }
    "use"       { return RsTokenTypes.KW_USE; }
    "virtual"   { return RsTokenTypes.KW_VIRTUAL; }
    "where"     { return RsTokenTypes.KW_WHERE; }
    "while"     { return RsTokenTypes.KW_WHILE; }
    "yield"     { return RsTokenTypes.KW_YIELD; }

    "&"    { return RsTokenTypes.OP_AND; }
    "&&"   { return RsTokenTypes.OP_ANDAND; }
    "&="   { return RsTokenTypes.OP_ANDEQ; }
    "->"   { return RsTokenTypes.OP_ARROW; }
    "*"    { return RsTokenTypes.OP_ASTERISK; }
    "*="   { return RsTokenTypes.OP_ASTERISKEQ; }
    "@"    { return RsTokenTypes.OP_AT; }
    "!"    { return RsTokenTypes.OP_BANG; }
    ":"    { return RsTokenTypes.OP_COLON; }
    ","    { return RsTokenTypes.OP_COMMA; }
    "/"    { return RsTokenTypes.OP_DIV; }
    "/="   { return RsTokenTypes.OP_DIVEQ; }
    "$"    { return RsTokenTypes.OP_DOLLAR; }
    "."    { return RsTokenTypes.OP_DOT; }
    ".."   { return RsTokenTypes.OP_DOTDOT; }
    "::"   { return RsTokenTypes.OP_DOUBLE_COLON; }
    "..."  { return RsTokenTypes.OP_ELLIPSIS; }
    "="    { return RsTokenTypes.OP_EQ; }
    "=="   { return RsTokenTypes.OP_EQEQ; }
    "=>"   { return RsTokenTypes.OP_FAT_ARROW; }
    ">"    { return RsTokenTypes.OP_GT; }
    ">="   { return RsTokenTypes.OP_GTEQ; }
    "#"    { return RsTokenTypes.OP_HASH; }
    "{"    { return RsTokenTypes.OP_LBRACE; }
    "["    { return RsTokenTypes.OP_LBRACKET; }
    "("    { return RsTokenTypes.OP_LPAREN; }
    "<"    { return RsTokenTypes.OP_LT; }
    "<="   { return RsTokenTypes.OP_LTEQ; }
    "-"    { return RsTokenTypes.OP_MINUS; }
    "-="   { return RsTokenTypes.OP_MINUSEQ; }
    "!="   { return RsTokenTypes.OP_NE; }
    "|"    { return RsTokenTypes.OP_OR; }
    "|="   { return RsTokenTypes.OP_OREQ; }
    "||"   { return RsTokenTypes.OP_OROR; }
    "+"    { return RsTokenTypes.OP_PLUS; }
    "+="   { return RsTokenTypes.OP_PLUSEQ; }
    "}"    { return RsTokenTypes.OP_RBRACE; }
    "]"    { return RsTokenTypes.OP_RBRACKET; }
    "%"    { return RsTokenTypes.OP_REM; }
    "%="   { return RsTokenTypes.OP_REMEQ; }
    ")"    { return RsTokenTypes.OP_RPAREN; }
    ";"    { return RsTokenTypes.OP_SEMICOLON; }
    "<<"   { return RsTokenTypes.OP_SHL; }
    "<<="  { return RsTokenTypes.OP_SHLEQ; }
    ">>"   { return RsTokenTypes.OP_SHR; }
    ">>="  { return RsTokenTypes.OP_SHREQ; }
    "_"    { return RsTokenTypes.OP_UNDERSCORE; }
    "^"    { return RsTokenTypes.OP_XOR; }
    "^="   { return RsTokenTypes.OP_XOREQ; }

    ( {LINE_INNER_DOC} {EOL} )* {LINE_INNER_DOC}  { return RsTokenTypes.LINE_INNER_DOC; }
    "///" "/"+ [^\r\n]*                             { return RsTokenTypes.LINE_COMMENT; }
    ( {LINE_DOC} {EOL} )* {LINE_DOC}                { return RsTokenTypes.LINE_DOC; }
    "//" [^\r\n]*                                   { return RsTokenTypes.LINE_COMMENT; }

    "/*!"      { beginBlockComment(CommentType.INNER_DOC); }
    "/**"[^*/] { beginBlockComment(CommentType.DOC); }
    "/*"       { beginBlockComment(CommentType.NORMAL); }

    \' {IDENTIFIER} { return RsTokenTypes.PRIM_IDENT; }

    b {CHAR_LIT} { return RsTokenTypes.BYTE_LIT; }
    {CHAR_LIT}   { return RsTokenTypes.CHAR_LIT; }

    b {STRING_LIT} { return RsTokenTypes.BYTE_STRING_LIT; }
    {STRING_LIT}   { return RsTokenTypes.STRING_LIT; }

    b {RAW_STRING_BEGIN} { beginRawString(true,  yylength() - 3); }
    {RAW_STRING_BEGIN}   { beginRawString(false, yylength() - 2); }

    {DEC_LIT} | {BIN_LIT} | {OCT_LIT} | {HEX_LIT} { return RsTokenTypes.INT_LIT; }
    // Prevent matching ranges as [float] [dot] [dec], e.g. 0..9 as 0. . 9
    {DEC_LIT} / ".."                              { return RsTokenTypes.INT_LIT; }
    {FLOAT_LIT}                                   { return RsTokenTypes.FLOAT_LIT; }

    {IDENTIFIER} { return RsTokenTypes.IDENTIFIER; }

    [^] { return RsTokenTypes.BAD_CHARACTER; }
}


<IN_BLOCK_COMMENT> {
    "*/"     { if (--commentDepth == 0) return endBlockComment(); }
    "/*"     { commentDepth++; }
    <<EOF>>  { return endBlockComment(); }
    [^]      { /* continue */ }
}


<IN_RAW_STRING> {
    {RAW_STRING_END} { if (endRawString())  return returnRawString(); }
    <<EOF>>          { endCompositeToken(); return returnRawString(); }
    [^]              { /* continue */ }
}
