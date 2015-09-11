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

package org.rustidea.psi.types;

import com.intellij.psi.TokenType;
import com.intellij.psi.tree.IElementType;

public interface RsTokenTypes extends TokenType {
    IElementType IDENTIFIER = new IRsIdentifierType("IDENTIFIER");
    IElementType LIFETIME_TOKEN = new IRsIdentifierType("LIFETIME_TOKEN");

    IElementType KW_ABSTRACT = new IRsKeywordType("abstract");
    IElementType KW_ALIGNOF = new IRsKeywordType("alignof");
    IElementType KW_AS = new IRsKeywordType("as");
    IElementType KW_BECOME = new IRsKeywordType("become");
    IElementType KW_BOX = new IRsKeywordType("box");
    IElementType KW_BREAK = new IRsKeywordType("break");
    IElementType KW_CONST = new IRsKeywordType("const");
    IElementType KW_CONTINUE = new IRsKeywordType("continue");
    IElementType KW_CRATE = new IRsKeywordType("crate");
    IElementType KW_DO = new IRsKeywordType("do");
    IElementType KW_ELSE = new IRsKeywordType("else");
    IElementType KW_ENUM = new IRsKeywordType("enum");
    IElementType KW_EXTERN = new IRsKeywordType("extern");
    IElementType KW_FALSE = new IRsKeywordType("false");
    IElementType KW_FINAL = new IRsKeywordType("final");
    IElementType KW_FN = new IRsKeywordType("fn");
    IElementType KW_FOR = new IRsKeywordType("for");
    IElementType KW_IF = new IRsKeywordType("if");
    IElementType KW_IMPL = new IRsKeywordType("impl");
    IElementType KW_IN = new IRsKeywordType("in");
    IElementType KW_LET = new IRsKeywordType("let");
    IElementType KW_LOOP = new IRsKeywordType("loop");
    IElementType KW_MACRO = new IRsKeywordType("macro");
    IElementType KW_MATCH = new IRsKeywordType("match");
    IElementType KW_MOD = new IRsKeywordType("mod");
    IElementType KW_MOVE = new IRsKeywordType("move");
    IElementType KW_MUT = new IRsKeywordType("mut");
    IElementType KW_OFFSETOF = new IRsKeywordType("offsetof");
    IElementType KW_OVERRIDE = new IRsKeywordType("override");
    IElementType KW_PRIV = new IRsKeywordType("priv");
    IElementType KW_PROC = new IRsKeywordType("proc");
    IElementType KW_PUB = new IRsKeywordType("pub");
    IElementType KW_PURE = new IRsKeywordType("pure");
    IElementType KW_REF = new IRsKeywordType("ref");
    IElementType KW_RETURN = new IRsKeywordType("return");
    IElementType KW_SELF = new IRsKeywordType("self");
    IElementType KW_SELF_T = new IRsKeywordType("Self");
    IElementType KW_SIZEOF = new IRsKeywordType("sizeof");
    IElementType KW_STATIC = new IRsKeywordType("static");
    IElementType KW_STRUCT = new IRsKeywordType("struct");
    IElementType KW_SUPER = new IRsKeywordType("super");
    IElementType KW_TRAIT = new IRsKeywordType("trait");
    IElementType KW_TRUE = new IRsKeywordType("true");
    IElementType KW_TYPE = new IRsKeywordType("type");
    IElementType KW_TYPEOF = new IRsKeywordType("typeof");
    IElementType KW_UNSAFE = new IRsKeywordType("unsafe");
    IElementType KW_UNSIZED = new IRsKeywordType("unsized");
    IElementType KW_USE = new IRsKeywordType("use");
    IElementType KW_VIRTUAL = new IRsKeywordType("virtual");
    IElementType KW_WHERE = new IRsKeywordType("where");
    IElementType KW_WHILE = new IRsKeywordType("while");
    IElementType KW_YIELD = new IRsKeywordType("yield");

    IElementType OP_AND = new IRsTokenType("&");
    IElementType OP_ANDAND = new IRsTokenType("&&");
    IElementType OP_ANDEQ = new IRsTokenType("&=");
    IElementType OP_ARROW = new IRsTokenType("->");
    IElementType OP_ASTERISK = new IRsTokenType("*");
    IElementType OP_ASTERISKEQ = new IRsTokenType("*=");
    IElementType OP_AT = new IRsTokenType("@");
    IElementType OP_BANG = new IRsTokenType("!");
    IElementType OP_COLON = new IRsTokenType(":");
    IElementType OP_COMMA = new IRsTokenType(",");
    IElementType OP_DIV = new IRsTokenType("/");
    IElementType OP_DIVEQ = new IRsTokenType("/=");
    IElementType OP_DOLLAR = new IRsTokenType("$");
    IElementType OP_DOT = new IRsTokenType(".");
    IElementType OP_DOTDOT = new IRsTokenType("..");
    IElementType OP_DOUBLE_COLON = new IRsTokenType("::");
    IElementType OP_ELLIPSIS = new IRsTokenType("...");
    IElementType OP_EQ = new IRsTokenType("=");
    IElementType OP_EQEQ = new IRsTokenType("==");
    IElementType OP_FAT_ARROW = new IRsTokenType("=>");
    IElementType OP_GT = new IRsTokenType(">");
    IElementType OP_GTEQ = new IRsTokenType(">=");
    IElementType OP_HASH = new IRsTokenType("#");
    IElementType OP_LBRACE = new IRsTokenType("{");
    IElementType OP_LBRACKET = new IRsTokenType("[");
    IElementType OP_LPAREN = new IRsTokenType("(");
    IElementType OP_LT = new IRsTokenType("<");
    IElementType OP_LTEQ = new IRsTokenType("<=");
    IElementType OP_MINUS = new IRsTokenType("-");
    IElementType OP_MINUSEQ = new IRsTokenType("-=");
    IElementType OP_NE = new IRsTokenType("!=");
    IElementType OP_OR = new IRsTokenType("|");
    IElementType OP_OREQ = new IRsTokenType("|=");
    IElementType OP_OROR = new IRsTokenType("||");
    IElementType OP_PLUS = new IRsTokenType("+");
    IElementType OP_PLUSEQ = new IRsTokenType("+=");
    IElementType OP_RBRACE = new IRsTokenType("}");
    IElementType OP_RBRACKET = new IRsTokenType("]");
    IElementType OP_REM = new IRsTokenType("%");
    IElementType OP_REMEQ = new IRsTokenType("%=");
    IElementType OP_RPAREN = new IRsTokenType(")");
    IElementType OP_SEMICOLON = new IRsTokenType(";");
    IElementType OP_SHL = new IRsTokenType("<<");
    IElementType OP_SHLEQ = new IRsTokenType("<<=");
    IElementType OP_SHR = new IRsTokenType(">>");
    IElementType OP_SHREQ = new IRsTokenType(">>=");
    IElementType OP_UNDERSCORE = new IRsTokenType("_");
    IElementType OP_XOR = new IRsTokenType("^");
    IElementType OP_XOREQ = new IRsTokenType("^=");

    IElementType BIN_LIT = new IRsTokenType("BIN_LITERAL");
    IElementType DEC_LIT = new IRsTokenType("DEC_LITERAL");
    IElementType FLOAT_LIT = new IRsTokenType("FLOAT_LITERAL");
    IElementType HEX_LIT = new IRsTokenType("HEX_LITERAL");
    IElementType OCT_LIT = new IRsTokenType("OCT_LITERAL");

    IElementType BYTE_LIT = new IRsTokenType("BYTE_LITERAL");
    IElementType CHAR_LIT = new IRsTokenType("CHAR_LITERAL");

    IElementType BYTE_STRING_LIT = new IRsTokenType("BYTE_STRING_LITERAL");
    IElementType STRING_LIT = new IRsTokenType("STRING_LITERAL");

    IElementType RAW_BYTE_STRING_LIT = new IRsTokenType("RAW_BYTE_STRING_LITERAL");
    IElementType RAW_STRING_LIT = new IRsTokenType("RAW_STRING_LITERAL");

    // Using IRsTokenType will break the code because of inappropriate leaf node creation
    IElementType BLOCK_COMMENT = new IRsElementType("BLOCK_COMMENT");
    IElementType LINE_COMMENT = new IRsElementType("LINE_COMMENT");

    IElementType BLOCK_DOC = new IRsTokenType("BLOCK_DOC");
    IElementType BLOCK_PARENT_DOC = new IRsTokenType("BLOCK_PARENT_DOC");
    IElementType LINE_DOC = new IRsTokenType("LINE_DOC");
    IElementType LINE_PARENT_DOC = new IRsTokenType("LINE_PARENT_DOC");
}
