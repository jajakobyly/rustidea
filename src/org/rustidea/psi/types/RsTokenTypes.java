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

    IElementType OP_AND = new IRsTokenType("AND");
    IElementType OP_ANDAND = new IRsTokenType("ANDAND");
    IElementType OP_ANDEQ = new IRsTokenType("ANDEQ");
    IElementType OP_ARROW = new IRsTokenType("ARROW");
    IElementType OP_ASTERISK = new IRsTokenType("ASTERISK");
    IElementType OP_ASTERISKEQ = new IRsTokenType("ASTERISKEQ");
    IElementType OP_AT = new IRsTokenType("AT");
    IElementType OP_BANG = new IRsTokenType("BANG");
    IElementType OP_COLON = new IRsTokenType("COLON");
    IElementType OP_COMMA = new IRsTokenType("COMMA");
    IElementType OP_DIV = new IRsTokenType("DIV");
    IElementType OP_DIVEQ = new IRsTokenType("DIVEQ");
    IElementType OP_DOLLAR = new IRsTokenType("DOLLAR");
    IElementType OP_DOT = new IRsTokenType("DOT");
    IElementType OP_DOTDOT = new IRsTokenType("DOTDOT");
    IElementType OP_DOUBLE_COLON = new IRsTokenType("DOUBLE_COLON");
    IElementType OP_ELLIPSIS = new IRsTokenType("ELLIPSIS");
    IElementType OP_EQ = new IRsTokenType("EQ");
    IElementType OP_EQEQ = new IRsTokenType("EQEQ");
    IElementType OP_FAT_ARROW = new IRsTokenType("FAT_ARROW");
    IElementType OP_GT = new IRsTokenType("GT");
    IElementType OP_GTEQ = new IRsTokenType("GTEQ");
    IElementType OP_HASH = new IRsTokenType("HASH");
    IElementType OP_LBRACE = new IRsTokenType("LBRACE");
    IElementType OP_LBRACKET = new IRsTokenType("LBRACKET");
    IElementType OP_LPAREN = new IRsTokenType("LPAREN");
    IElementType OP_LT = new IRsTokenType("LT");
    IElementType OP_LTEQ = new IRsTokenType("LTEQ");
    IElementType OP_MINUS = new IRsTokenType("MINUS");
    IElementType OP_MINUSEQ = new IRsTokenType("MINUSEQ");
    IElementType OP_NE = new IRsTokenType("NE");
    IElementType OP_OR = new IRsTokenType("OR");
    IElementType OP_OREQ = new IRsTokenType("OREQ");
    IElementType OP_OROR = new IRsTokenType("OROR");
    IElementType OP_PLUS = new IRsTokenType("PLUS");
    IElementType OP_PLUSEQ = new IRsTokenType("PLUSEQ");
    IElementType OP_RBRACE = new IRsTokenType("RBRACE");
    IElementType OP_RBRACKET = new IRsTokenType("RBRACKET");
    IElementType OP_REM = new IRsTokenType("REM");
    IElementType OP_REMEQ = new IRsTokenType("REMEQ");
    IElementType OP_RPAREN = new IRsTokenType("RPAREN");
    IElementType OP_SEMICOLON = new IRsTokenType("SEMICOLON");
    IElementType OP_SHL = new IRsTokenType("SHL");
    IElementType OP_SHLEQ = new IRsTokenType("SHLEQ");
    IElementType OP_SHR = new IRsTokenType("SHR");
    IElementType OP_SHREQ = new IRsTokenType("SHREQ");
    IElementType OP_UNDERSCORE = new IRsTokenType("UNDERSCORE");
    IElementType OP_XOR = new IRsTokenType("XOR");
    IElementType OP_XOREQ = new IRsTokenType("XOREQ");

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

    IElementType BLOCK_COMMENT = new IRsTokenType("BLOCK_COMMENT");
    IElementType LINE_COMMENT = new IRsTokenType("LINE_COMMENT");

    IElementType BLOCK_DOC = new IRsTokenType("BLOCK_DOC");
    IElementType BLOCK_PARENT_DOC = new IRsTokenType("BLOCK_PARENT_DOC");
    IElementType LINE_DOC = new IRsTokenType("LINE_DOC");
    IElementType LINE_PARENT_DOC = new IRsTokenType("LINE_PARENT_DOC");
}
