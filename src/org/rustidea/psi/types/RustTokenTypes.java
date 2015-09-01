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

public interface RustTokenTypes extends TokenType {
    IElementType IDENTIFIER = new IRustIdentifierType("IDENTIFIER");
    IElementType LIFETIME_TOKEN = new IRustIdentifierType("LIFETIME_TOKEN");

    IElementType KW_ABSTRACT = new IRustKeywordType("abstract");
    IElementType KW_ALIGNOF = new IRustKeywordType("alignof");
    IElementType KW_AS = new IRustKeywordType("as");
    IElementType KW_BECOME = new IRustKeywordType("become");
    IElementType KW_BOX = new IRustKeywordType("box");
    IElementType KW_BREAK = new IRustKeywordType("break");
    IElementType KW_CONST = new IRustKeywordType("const");
    IElementType KW_CONTINUE = new IRustKeywordType("continue");
    IElementType KW_CRATE = new IRustKeywordType("crate");
    IElementType KW_DO = new IRustKeywordType("do");
    IElementType KW_ELSE = new IRustKeywordType("else");
    IElementType KW_ENUM = new IRustKeywordType("enum");
    IElementType KW_EXTERN = new IRustKeywordType("extern");
    IElementType KW_FALSE = new IRustKeywordType("false");
    IElementType KW_FINAL = new IRustKeywordType("final");
    IElementType KW_FN = new IRustKeywordType("fn");
    IElementType KW_FOR = new IRustKeywordType("for");
    IElementType KW_IF = new IRustKeywordType("if");
    IElementType KW_IMPL = new IRustKeywordType("impl");
    IElementType KW_IN = new IRustKeywordType("in");
    IElementType KW_LET = new IRustKeywordType("let");
    IElementType KW_LOOP = new IRustKeywordType("loop");
    IElementType KW_MACRO = new IRustKeywordType("macro");
    IElementType KW_MATCH = new IRustKeywordType("match");
    IElementType KW_MOD = new IRustKeywordType("mod");
    IElementType KW_MOVE = new IRustKeywordType("move");
    IElementType KW_MUT = new IRustKeywordType("mut");
    IElementType KW_OFFSETOF = new IRustKeywordType("offsetof");
    IElementType KW_OVERRIDE = new IRustKeywordType("override");
    IElementType KW_PRIV = new IRustKeywordType("priv");
    IElementType KW_PROC = new IRustKeywordType("proc");
    IElementType KW_PUB = new IRustKeywordType("pub");
    IElementType KW_PURE = new IRustKeywordType("pure");
    IElementType KW_REF = new IRustKeywordType("ref");
    IElementType KW_RETURN = new IRustKeywordType("return");
    IElementType KW_SELF = new IRustKeywordType("self");
    IElementType KW_SELF_T = new IRustKeywordType("Self");
    IElementType KW_SIZEOF = new IRustKeywordType("sizeof");
    IElementType KW_STATIC = new IRustKeywordType("static");
    IElementType KW_STRUCT = new IRustKeywordType("struct");
    IElementType KW_SUPER = new IRustKeywordType("super");
    IElementType KW_TRAIT = new IRustKeywordType("trait");
    IElementType KW_TRUE = new IRustKeywordType("true");
    IElementType KW_TYPE = new IRustKeywordType("type");
    IElementType KW_TYPEOF = new IRustKeywordType("typeof");
    IElementType KW_UNSAFE = new IRustKeywordType("unsafe");
    IElementType KW_UNSIZED = new IRustKeywordType("unsized");
    IElementType KW_USE = new IRustKeywordType("use");
    IElementType KW_VIRTUAL = new IRustKeywordType("virtual");
    IElementType KW_WHERE = new IRustKeywordType("where");
    IElementType KW_WHILE = new IRustKeywordType("while");
    IElementType KW_YIELD = new IRustKeywordType("yield");

    IElementType OP_AMPERSAND = new IRustTokenType("AMPERSAND");
    IElementType OP_ARROW = new IRustTokenType("ARROW");
    IElementType OP_ASSIGN = new IRustTokenType("ASSIGN");
    IElementType OP_ASTERISK = new IRustTokenType("ASTERISK");
    IElementType OP_AT = new IRustTokenType("AT");
    IElementType OP_CARET = new IRustTokenType("CARET");
    IElementType OP_CLOSE_BRACE = new IRustTokenType("CLOSE_BRACE");
    IElementType OP_CLOSE_BRACKET = new IRustTokenType("CLOSE_BRACKET");
    IElementType OP_CLOSE_PAREN = new IRustTokenType("CLOSE_PAREN");
    IElementType OP_COLON = new IRustTokenType("COLON");
    IElementType OP_COMMA = new IRustTokenType("COMMA");
    IElementType OP_DOLLAR = new IRustTokenType("DOLLAR");
    IElementType OP_DOT = new IRustTokenType("DOT");
    IElementType OP_DOUBLE_AMPERSAND = new IRustTokenType("DOUBLE_AMPERSAND");
    IElementType OP_DOUBLE_COLON = new IRustTokenType("DOUBLE_COLON");
    IElementType OP_DOUBLE_DOT = new IRustTokenType("DOUBLE_DOT");
    IElementType OP_DOUBLE_PIPE = new IRustTokenType("DOUBLE_PIPE");
    IElementType OP_EQUALS = new IRustTokenType("EQUALS");
    IElementType OP_EXCLAMATION_MARK = new IRustTokenType("EXCLAMATION_MARK");
    IElementType OP_FAT_ARROW = new IRustTokenType("FAT_ARROW");
    IElementType OP_GREATER_THAN = new IRustTokenType("GREATER_THAN");
    IElementType OP_GREATER_THAN_EQ = new IRustTokenType("GREATER_THAN_EQ");
    IElementType OP_HASH = new IRustTokenType("HASH");
    IElementType OP_LEFT_SHIFT = new IRustTokenType("LEFT_SHIFT");
    IElementType OP_LESS_THAN = new IRustTokenType("LESS_THAN");
    IElementType OP_LESS_THAN_EQ = new IRustTokenType("LESS_THAN_EQ");
    IElementType OP_MINUS = new IRustTokenType("MINUS");
    IElementType OP_NOT_EQUALS = new IRustTokenType("NOT_EQUALS");
    IElementType OP_OPEN_BRACE = new IRustTokenType("OPEN_BRACE");
    IElementType OP_OPEN_BRACKET = new IRustTokenType("OPEN_BRACKET");
    IElementType OP_OPEN_PAREN = new IRustTokenType("OPEN_PAREN");
    IElementType OP_PERCENT = new IRustTokenType("PERCENT");
    IElementType OP_PIPE = new IRustTokenType("PIPE");
    IElementType OP_PLUS = new IRustTokenType("PLUS");
    IElementType OP_RIGHT_SHIFT = new IRustTokenType("RIGHT_SHIFT");
    IElementType OP_SEMICOLON = new IRustTokenType("SEMICOLON");
    IElementType OP_SLASH = new IRustTokenType("SLASH");
    IElementType OP_TRIPLE_DOT = new IRustTokenType("TRIPLE_DOT");
    IElementType OP_UNDERSCORE = new IRustTokenType("UNDERSCORE");

    IElementType BIN_LIT = new IRustTokenType("BIN_LITERAL");
    IElementType DEC_LIT = new IRustTokenType("DEC_LITERAL");
    IElementType FLOAT_LIT = new IRustTokenType("FLOAT_LITERAL");
    IElementType HEX_LIT = new IRustTokenType("HEX_LITERAL");
    IElementType OCT_LIT = new IRustTokenType("OCT_LITERAL");

    IElementType BYTE_LIT = new IRustTokenType("BYTE_LITERAL");
    IElementType CHAR_LIT = new IRustTokenType("CHAR_LITERAL");

    IElementType BYTE_STRING_LIT = new IRustTokenType("BYTE_STRING_LITERAL");
    IElementType STRING_LIT = new IRustTokenType("STRING_LITERAL");

    IElementType RAW_BYTE_STRING_LIT = new IRustTokenType("RAW_BYTE_STRING_LITERAL");
    IElementType RAW_STRING_LIT = new IRustTokenType("RAW_STRING_LITERAL");

    IElementType BLOCK_COMMENT = new IRustTokenType("BLOCK_COMMENT");
    IElementType LINE_COMMENT = new IRustTokenType("LINE_COMMENT");

    IElementType BLOCK_DOC = new IRustTokenType("BLOCK_DOC");
    IElementType BLOCK_PARENT_DOC = new IRustTokenType("BLOCK_PARENT_DOC");
    IElementType LINE_DOC = new IRustTokenType("LINE_DOC");
    IElementType LINE_PARENT_DOC = new IRustTokenType("LINE_PARENT_DOC");
}
