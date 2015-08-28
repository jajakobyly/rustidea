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

package org.rustidea.psi;

import com.intellij.psi.tree.IElementType;

public interface RustTokenTypes {
    IElementType BIN_LIT = new RustTokenType("BIN_LIT");
    IElementType BLOCK_COMMENT = new RustTokenType("BLOCK_COMMENT");
    IElementType BLOCK_DOC = new RustTokenType("BLOCK_DOC");
    IElementType BLOCK_PARENT_DOC = new RustTokenType("BLOCK_PARENT_DOC");
    IElementType BYTE_LIT = new RustTokenType("BYTE_LIT");
    IElementType BYTE_STRING_LIT = new RustTokenType("BYTE_STRING_LITERAL");
    IElementType CHAR_LIT = new RustTokenType("CHAR_LIT");
    IElementType DEC_LIT = new RustTokenType("DEC_LIT");
    IElementType FLOAT_LIT = new RustTokenType("FLOAT_LIT");
    IElementType HEX_LIT = new RustTokenType("HEX_LIT");
    IElementType IDENTIFIER = new RustTokenType("IDENTIFIER");
    IElementType KW_ABSTRACT = new RustTokenType("abstract");
    IElementType KW_ALIGNOF = new RustTokenType("alignof");
    IElementType KW_AS = new RustTokenType("as");
    IElementType KW_BECOME = new RustTokenType("become");
    IElementType KW_BOX = new RustTokenType("box");
    IElementType KW_BREAK = new RustTokenType("break");
    IElementType KW_CONST = new RustTokenType("const");
    IElementType KW_CONTINUE = new RustTokenType("continue");
    IElementType KW_CRATE = new RustTokenType("crate");
    IElementType KW_DO = new RustTokenType("do");
    IElementType KW_ELSE = new RustTokenType("else");
    IElementType KW_ENUM = new RustTokenType("enum");
    IElementType KW_EXTERN = new RustTokenType("extern");
    IElementType KW_FALSE = new RustTokenType("false");
    IElementType KW_FINAL = new RustTokenType("final");
    IElementType KW_FN = new RustTokenType("fn");
    IElementType KW_FOR = new RustTokenType("for");
    IElementType KW_IF = new RustTokenType("if");
    IElementType KW_IMPL = new RustTokenType("impl");
    IElementType KW_IN = new RustTokenType("in");
    IElementType KW_LET = new RustTokenType("let");
    IElementType KW_LOOP = new RustTokenType("loop");
    IElementType KW_MACRO = new RustTokenType("macro");
    IElementType KW_MATCH = new RustTokenType("match");
    IElementType KW_MOD = new RustTokenType("mod");
    IElementType KW_MOVE = new RustTokenType("move");
    IElementType KW_MUT = new RustTokenType("mut");
    IElementType KW_OFFSETOF = new RustTokenType("offsetof");
    IElementType KW_OVERRIDE = new RustTokenType("override");
    IElementType KW_PRIV = new RustTokenType("priv");
    IElementType KW_PROC = new RustTokenType("proc");
    IElementType KW_PUB = new RustTokenType("pub");
    IElementType KW_PURE = new RustTokenType("pure");
    IElementType KW_REF = new RustTokenType("ref");
    IElementType KW_RETURN = new RustTokenType("return");
    IElementType KW_SELF = new RustTokenType("self");
    IElementType KW_SELF_T = new RustTokenType("Self");
    IElementType KW_SIZEOF = new RustTokenType("sizeof");
    IElementType KW_STATIC = new RustTokenType("static");
    IElementType KW_STRUCT = new RustTokenType("struct");
    IElementType KW_SUPER = new RustTokenType("super");
    IElementType KW_TRAIT = new RustTokenType("trait");
    IElementType KW_TRUE = new RustTokenType("true");
    IElementType KW_TYPE = new RustTokenType("type");
    IElementType KW_TYPEOF = new RustTokenType("typeof");
    IElementType KW_UNSAFE = new RustTokenType("unsafe");
    IElementType KW_UNSIZED = new RustTokenType("unsized");
    IElementType KW_USE = new RustTokenType("use");
    IElementType KW_VIRTUAL = new RustTokenType("virtual");
    IElementType KW_WHERE = new RustTokenType("where");
    IElementType KW_WHILE = new RustTokenType("while");
    IElementType KW_YIELD = new RustTokenType("yield");
    IElementType LIFETIME = new RustTokenType("LIFETIME");
    IElementType LINE_COMMENT = new RustTokenType("LINE_COMMENT");
    IElementType LINE_DOC = new RustTokenType("LINE_DOC");
    IElementType LINE_PARENT_DOC = new RustTokenType("LINE_PARENT_DOC");
    IElementType MACRO_CALL = new RustTokenType("MACRO_CALL");
    IElementType MACRO_VARIABLE = new RustTokenType("MACRO_VARIABLE");
    IElementType OCT_LIT = new RustTokenType("OCT_LIT");
    IElementType OP_AMPERSAND = new RustTokenType("&");
    IElementType OP_ARROW = new RustTokenType("->");
    IElementType OP_ASSIGN = new RustTokenType("=");
    IElementType OP_ASTERISK = new RustTokenType("*");
    IElementType OP_AT = new RustTokenType("@");
    IElementType OP_CARET = new RustTokenType("^");
    IElementType OP_CLOSE_BRACE = new RustTokenType("}");
    IElementType OP_CLOSE_BRACKET = new RustTokenType("]");
    IElementType OP_CLOSE_PAREN = new RustTokenType(")");
    IElementType OP_COLON = new RustTokenType(":");
    IElementType OP_COMMA = new RustTokenType(",");
    IElementType OP_DOLLAR = new RustTokenType("$");
    IElementType OP_DOT = new RustTokenType(".");
    IElementType OP_DOUBLE_AMPERSAND = new RustTokenType("&&");
    IElementType OP_DOUBLE_COLON = new RustTokenType("::");
    IElementType OP_DOUBLE_DOT = new RustTokenType("..");
    IElementType OP_DOUBLE_PIPE = new RustTokenType("||");
    IElementType OP_EQUALS = new RustTokenType("==");
    IElementType OP_EXCLAMATION_MARK = new RustTokenType("!");
    IElementType OP_FAT_ARROW = new RustTokenType("=>");
    IElementType OP_GREATER_THAN = new RustTokenType(">");
    IElementType OP_GREATER_THAN_EQ = new RustTokenType(">=");
    IElementType OP_HASH = new RustTokenType("#");
    IElementType OP_LEFT_SHIFT = new RustTokenType("<<");
    IElementType OP_LESS_THAN = new RustTokenType("<");
    IElementType OP_LESS_THAN_EQ = new RustTokenType("<=");
    IElementType OP_MINUS = new RustTokenType("-");
    IElementType OP_NOT_EQUALS = new RustTokenType("!=");
    IElementType OP_OPEN_BRACE = new RustTokenType("{");
    IElementType OP_OPEN_BRACKET = new RustTokenType("[");
    IElementType OP_OPEN_PAREN = new RustTokenType("(");
    IElementType OP_PERCENT = new RustTokenType("%");
    IElementType OP_PIPE = new RustTokenType("|");
    IElementType OP_PLUS = new RustTokenType("+");
    IElementType OP_RIGHT_SHIFT = new RustTokenType(">>");
    IElementType OP_SEMICOLON = new RustTokenType(";");
    IElementType OP_SLASH = new RustTokenType("/");
    IElementType OP_TRIPLE_DOT = new RustTokenType("...");
    IElementType OP_UNDERSCORE = new RustTokenType("_");
    IElementType RAW_BYTE_STRING_LIT = new RustTokenType("RAW_BYTE_STRING_LITERAL");
    IElementType RAW_STRING_LIT = new RustTokenType("RAW_STRING_LITERAL");
    IElementType STRING_LIT = new RustTokenType("STRING_LIT");
}
