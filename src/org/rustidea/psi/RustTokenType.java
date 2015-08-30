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

import com.intellij.psi.TokenType;
import com.intellij.psi.tree.IElementType;

public interface RustTokenType extends TokenType {
    IElementType BIN_LIT = new IRustTokenType("BIN_LIT");
    IElementType BLOCK_COMMENT = new IRustTokenType("BLOCK_COMMENT");
    IElementType BLOCK_DOC = new IRustTokenType("BLOCK_DOC");
    IElementType BLOCK_PARENT_DOC = new IRustTokenType("BLOCK_PARENT_DOC");
    IElementType BYTE_LIT = new IRustTokenType("BYTE_LIT");
    IElementType BYTE_STRING_LIT = new IRustTokenType("BYTE_STRING_LITERAL");
    IElementType CHAR_LIT = new IRustTokenType("CHAR_LIT");
    IElementType DEC_LIT = new IRustTokenType("DEC_LIT");
    IElementType FLOAT_LIT = new IRustTokenType("FLOAT_LIT");
    IElementType HEX_LIT = new IRustTokenType("HEX_LIT");
    IElementType IDENTIFIER = new IRustTokenType("IDENTIFIER");
    IElementType KW_ABSTRACT = new IRustTokenType("abstract");
    IElementType KW_ALIGNOF = new IRustTokenType("alignof");
    IElementType KW_AS = new IRustTokenType("as");
    IElementType KW_BECOME = new IRustTokenType("become");
    IElementType KW_BOX = new IRustTokenType("box");
    IElementType KW_BREAK = new IRustTokenType("break");
    IElementType KW_CONST = new IRustTokenType("const");
    IElementType KW_CONTINUE = new IRustTokenType("continue");
    IElementType KW_CRATE = new IRustTokenType("crate");
    IElementType KW_DO = new IRustTokenType("do");
    IElementType KW_ELSE = new IRustTokenType("else");
    IElementType KW_ENUM = new IRustTokenType("enum");
    IElementType KW_EXTERN = new IRustTokenType("extern");
    IElementType KW_FALSE = new IRustTokenType("false");
    IElementType KW_FINAL = new IRustTokenType("final");
    IElementType KW_FN = new IRustTokenType("fn");
    IElementType KW_FOR = new IRustTokenType("for");
    IElementType KW_IF = new IRustTokenType("if");
    IElementType KW_IMPL = new IRustTokenType("impl");
    IElementType KW_IN = new IRustTokenType("in");
    IElementType KW_LET = new IRustTokenType("let");
    IElementType KW_LOOP = new IRustTokenType("loop");
    IElementType KW_MACRO = new IRustTokenType("macro");
    IElementType KW_MATCH = new IRustTokenType("match");
    IElementType KW_MOD = new IRustTokenType("mod");
    IElementType KW_MOVE = new IRustTokenType("move");
    IElementType KW_MUT = new IRustTokenType("mut");
    IElementType KW_OFFSETOF = new IRustTokenType("offsetof");
    IElementType KW_OVERRIDE = new IRustTokenType("override");
    IElementType KW_PRIV = new IRustTokenType("priv");
    IElementType KW_PROC = new IRustTokenType("proc");
    IElementType KW_PUB = new IRustTokenType("pub");
    IElementType KW_PURE = new IRustTokenType("pure");
    IElementType KW_REF = new IRustTokenType("ref");
    IElementType KW_RETURN = new IRustTokenType("return");
    IElementType KW_SELF = new IRustTokenType("self");
    IElementType KW_SELF_T = new IRustTokenType("Self");
    IElementType KW_SIZEOF = new IRustTokenType("sizeof");
    IElementType KW_STATIC = new IRustTokenType("static");
    IElementType KW_STRUCT = new IRustTokenType("struct");
    IElementType KW_SUPER = new IRustTokenType("super");
    IElementType KW_TRAIT = new IRustTokenType("trait");
    IElementType KW_TRUE = new IRustTokenType("true");
    IElementType KW_TYPE = new IRustTokenType("type");
    IElementType KW_TYPEOF = new IRustTokenType("typeof");
    IElementType KW_UNSAFE = new IRustTokenType("unsafe");
    IElementType KW_UNSIZED = new IRustTokenType("unsized");
    IElementType KW_USE = new IRustTokenType("use");
    IElementType KW_VIRTUAL = new IRustTokenType("virtual");
    IElementType KW_WHERE = new IRustTokenType("where");
    IElementType KW_WHILE = new IRustTokenType("while");
    IElementType KW_YIELD = new IRustTokenType("yield");
    IElementType LIFETIME = new IRustTokenType("LIFETIME");
    IElementType LINE_COMMENT = new IRustTokenType("LINE_COMMENT");
    IElementType LINE_DOC = new IRustTokenType("LINE_DOC");
    IElementType LINE_PARENT_DOC = new IRustTokenType("LINE_PARENT_DOC");
    IElementType MACRO_CALL = new IRustTokenType("MACRO_CALL");
    IElementType MACRO_VARIABLE = new IRustTokenType("MACRO_VARIABLE");
    IElementType OCT_LIT = new IRustTokenType("OCT_LIT");
    IElementType OP_AMPERSAND = new IRustTokenType("&");
    IElementType OP_ARROW = new IRustTokenType("->");
    IElementType OP_ASSIGN = new IRustTokenType("=");
    IElementType OP_ASTERISK = new IRustTokenType("*");
    IElementType OP_AT = new IRustTokenType("@");
    IElementType OP_CARET = new IRustTokenType("^");
    IElementType OP_CLOSE_BRACE = new IRustTokenType("}");
    IElementType OP_CLOSE_BRACKET = new IRustTokenType("]");
    IElementType OP_CLOSE_PAREN = new IRustTokenType(")");
    IElementType OP_COLON = new IRustTokenType(":");
    IElementType OP_COMMA = new IRustTokenType(",");
    IElementType OP_DOLLAR = new IRustTokenType("$");
    IElementType OP_DOT = new IRustTokenType(".");
    IElementType OP_DOUBLE_AMPERSAND = new IRustTokenType("&&");
    IElementType OP_DOUBLE_COLON = new IRustTokenType("::");
    IElementType OP_DOUBLE_DOT = new IRustTokenType("..");
    IElementType OP_DOUBLE_PIPE = new IRustTokenType("||");
    IElementType OP_EQUALS = new IRustTokenType("==");
    IElementType OP_EXCLAMATION_MARK = new IRustTokenType("!");
    IElementType OP_FAT_ARROW = new IRustTokenType("=>");
    IElementType OP_GREATER_THAN = new IRustTokenType(">");
    IElementType OP_GREATER_THAN_EQ = new IRustTokenType(">=");
    IElementType OP_HASH = new IRustTokenType("#");
    IElementType OP_LEFT_SHIFT = new IRustTokenType("<<");
    IElementType OP_LESS_THAN = new IRustTokenType("<");
    IElementType OP_LESS_THAN_EQ = new IRustTokenType("<=");
    IElementType OP_MINUS = new IRustTokenType("-");
    IElementType OP_NOT_EQUALS = new IRustTokenType("!=");
    IElementType OP_OPEN_BRACE = new IRustTokenType("{");
    IElementType OP_OPEN_BRACKET = new IRustTokenType("[");
    IElementType OP_OPEN_PAREN = new IRustTokenType("(");
    IElementType OP_PERCENT = new IRustTokenType("%");
    IElementType OP_PIPE = new IRustTokenType("|");
    IElementType OP_PLUS = new IRustTokenType("+");
    IElementType OP_RIGHT_SHIFT = new IRustTokenType(">>");
    IElementType OP_SEMICOLON = new IRustTokenType(";");
    IElementType OP_SLASH = new IRustTokenType("/");
    IElementType OP_TRIPLE_DOT = new IRustTokenType("...");
    IElementType OP_UNDERSCORE = new IRustTokenType("_");
    IElementType RAW_BYTE_STRING_LIT = new IRustTokenType("RAW_BYTE_STRING_LITERAL");
    IElementType RAW_STRING_LIT = new IRustTokenType("RAW_STRING_LITERAL");
    IElementType STRING_LIT = new IRustTokenType("STRING_LIT");
}
