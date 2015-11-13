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
    IElementType IDENTIFIER = new IRsIdentifierType("IDENTIFIER", "identifier");
    IElementType PRIM_IDENT = new IRsIdentifierType("PRIM_IDENT");

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

    IElementType OP_AND = new IRsKwOrOpElementType("&");
    IElementType OP_ANDAND = new IRsKwOrOpElementType("&&");
    IElementType OP_ANDEQ = new IRsKwOrOpElementType("&=");
    IElementType OP_ARROW = new IRsKwOrOpElementType("->");
    IElementType OP_ASTERISK = new IRsKwOrOpElementType("*");
    IElementType OP_ASTERISKEQ = new IRsKwOrOpElementType("*=");
    IElementType OP_AT = new IRsKwOrOpElementType("@");
    IElementType OP_BANG = new IRsKwOrOpElementType("!");
    IElementType OP_COLON = new IRsKwOrOpElementType(":");
    IElementType OP_COMMA = new IRsKwOrOpElementType(",");
    IElementType OP_DIV = new IRsKwOrOpElementType("/");
    IElementType OP_DIVEQ = new IRsKwOrOpElementType("/=");
    IElementType OP_DOLLAR = new IRsKwOrOpElementType("$");
    IElementType OP_DOT = new IRsKwOrOpElementType(".");
    IElementType OP_DOTDOT = new IRsKwOrOpElementType("..");
    IElementType OP_DOUBLE_COLON = new IRsKwOrOpElementType("::");
    IElementType OP_ELLIPSIS = new IRsKwOrOpElementType("...");
    IElementType OP_EQ = new IRsKwOrOpElementType("=");
    IElementType OP_EQEQ = new IRsKwOrOpElementType("==");
    IElementType OP_FAT_ARROW = new IRsKwOrOpElementType("=>");
    IElementType OP_GT = new IRsKwOrOpElementType(">");
    IElementType OP_GTEQ = new IRsKwOrOpElementType(">=");
    IElementType OP_HASH = new IRsKwOrOpElementType("#");
    IElementType OP_LBRACE = new IRsKwOrOpElementType("{");
    IElementType OP_LBRACKET = new IRsKwOrOpElementType("[");
    IElementType OP_LPAREN = new IRsKwOrOpElementType("(");
    IElementType OP_LT = new IRsKwOrOpElementType("<");
    IElementType OP_LTEQ = new IRsKwOrOpElementType("<=");
    IElementType OP_MINUS = new IRsKwOrOpElementType("-");
    IElementType OP_MINUSEQ = new IRsKwOrOpElementType("-=");
    IElementType OP_NE = new IRsKwOrOpElementType("!=");
    IElementType OP_OR = new IRsKwOrOpElementType("|");
    IElementType OP_OREQ = new IRsKwOrOpElementType("|=");
    IElementType OP_OROR = new IRsKwOrOpElementType("||");
    IElementType OP_PLUS = new IRsKwOrOpElementType("+");
    IElementType OP_PLUSEQ = new IRsKwOrOpElementType("+=");
    IElementType OP_RBRACE = new IRsKwOrOpElementType("}");
    IElementType OP_RBRACKET = new IRsKwOrOpElementType("]");
    IElementType OP_REM = new IRsKwOrOpElementType("%");
    IElementType OP_REMEQ = new IRsKwOrOpElementType("%=");
    IElementType OP_RPAREN = new IRsKwOrOpElementType(")");
    IElementType OP_SEMICOLON = new IRsKwOrOpElementType(";");
    IElementType OP_SHL = new IRsKwOrOpElementType("<<");
    IElementType OP_SHLEQ = new IRsKwOrOpElementType("<<=");
    IElementType OP_SHR = new IRsKwOrOpElementType(">>");
    IElementType OP_SHREQ = new IRsKwOrOpElementType(">>=");
    IElementType OP_UNDERSCORE = new IRsKwOrOpElementType("_");
    IElementType OP_XOR = new IRsKwOrOpElementType("^");
    IElementType OP_XOREQ = new IRsKwOrOpElementType("^=");

    IElementType INT_LIT = new IRsTokenType("INTEGER_LITERAL", "numeric literal");
    IElementType FLOAT_LIT = new IRsTokenType("FLOAT_LITERAL", "float literal");

    IElementType BYTE_LIT = new IRsTokenType("BYTE_LITERAL", "byte literal");
    IElementType CHAR_LIT = new IRsTokenType("CHAR_LITERAL", "char literal");

    IElementType BYTE_STRING_LIT = new IRsTokenType("BYTE_STRING_LITERAL", "byte string literal");
    IElementType STRING_LIT = new IRsTokenType("STRING_LITERAL", "string literal");

    IElementType RAW_BYTE_STRING_LIT = new IRsTokenType("RAW_BYTE_STRING_LITERAL", "raw byte string literal");
    IElementType RAW_STRING_LIT = new IRsTokenType("RAW_STRING_LITERAL", "raw string literal");

    // Using IRsTokenType will break the code because of inappropriate leaf node creation
    IElementType BLOCK_COMMENT = new IRsElementTypeImpl("BLOCK_COMMENT");
    IElementType LINE_COMMENT = new IRsElementTypeImpl("LINE_COMMENT");

    IElementType BLOCK_DOC = new IRsTokenType("BLOCK_DOC");
    IElementType BLOCK_PARENT_DOC = new IRsTokenType("BLOCK_PARENT_DOC");
    IElementType LINE_DOC = new IRsTokenType("LINE_DOC");
    IElementType LINE_PARENT_DOC = new IRsTokenType("LINE_PARENT_DOC");
}
