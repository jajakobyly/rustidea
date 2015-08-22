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

package org.rustidea.highlighting

import com.intellij.lexer.Lexer
import com.intellij.openapi.editor.colors.TextAttributesKey
import com.intellij.openapi.editor.colors.TextAttributesKey.createTextAttributesKey
import com.intellij.openapi.editor.{DefaultLanguageHighlighterColors, HighlighterColors}
import com.intellij.openapi.fileTypes.SyntaxHighlighterBase
import com.intellij.openapi.fileTypes.SyntaxHighlighterBase.{EMPTY, pack}
import com.intellij.psi.TokenType
import com.intellij.psi.tree.IElementType
import org.rustidea.parser.RustLexer
import org.rustidea.psi.RustTypes

// FIXME Syntax highlighting for raw (byte) string breaks sometimes, I don't know when exactly and why.
// FIXME An assertion fails in com.intellij.openapi.editor.ex.util.SegmentArray.segmentNotFound(SegmentArray.java:129) when trying to delete last quote character form raw (byte) string at the end of the file (making this operation impossible). I don't know why.
// FIXME To sum up: raw (byte) string handling in lexer is fucked up.

class RustSyntaxHighlighter extends SyntaxHighlighterBase {
  override def getHighlightingLexer: Lexer = new RustLexer()

  override def getTokenHighlights(et: IElementType): Array[TextAttributesKey] =
    pack(RustSyntaxHighlighter.KEYS.get(et).orNull, EMPTY)
}

object RustSyntaxHighlighter {
  final val IDENTIFIER = createTextAttributesKey(
    "RUST.IDENTIFIER",
    DefaultLanguageHighlighterColors.IDENTIFIER)
  final val KEYWORD = createTextAttributesKey(
    "RUST.KEYWORD",
    DefaultLanguageHighlighterColors.KEYWORD)
  final val OPERATION_SIGN = createTextAttributesKey(
    "RUST.OPERATION_SIGN",
    DefaultLanguageHighlighterColors.OPERATION_SIGN)
  final val BRACES = createTextAttributesKey(
    "RUST.BRACES",
    DefaultLanguageHighlighterColors.BRACES)
  final val DOT = createTextAttributesKey(
    "RUST.DOT",
    DefaultLanguageHighlighterColors.DOT)
  final val SEMICOLON = createTextAttributesKey(
    "RUST.SEMICOLON",
    DefaultLanguageHighlighterColors.SEMICOLON)
  final val COMMA = createTextAttributesKey(
    "RUST.COMMA",
    DefaultLanguageHighlighterColors.COMMA)
  final val PARENTHESES = createTextAttributesKey(
    "RUST.PARENTHESES",
    DefaultLanguageHighlighterColors.PARENTHESES)
  final val BRACKETS = createTextAttributesKey(
    "RUST.BRACKETS",
    DefaultLanguageHighlighterColors.BRACKETS)
  final val LIFETIME = createTextAttributesKey(
    "RUST.LIFETIME",
    DefaultLanguageHighlighterColors.METADATA)

  final val NUMBER = createTextAttributesKey(
    "RUST.NUMBER",
    DefaultLanguageHighlighterColors.NUMBER)
  final val CHAR = createTextAttributesKey(
    "RUST.CHAR",
    DefaultLanguageHighlighterColors.STRING)
  final val STRING = createTextAttributesKey(
    "RUST.STRING",
    DefaultLanguageHighlighterColors.STRING)
  final val RAW_STRING = createTextAttributesKey(
    "RUST.RAW_STRING",
    DefaultLanguageHighlighterColors.STRING)
  final val BYTE = createTextAttributesKey(
    "RUST.BYTE",
    DefaultLanguageHighlighterColors.STRING)
  final val BYTE_STRING = createTextAttributesKey(
    "RUST.BYTE_STRING",
    DefaultLanguageHighlighterColors.STRING)
  final val RAW_BYTE_STRING = createTextAttributesKey(
    "RUST.RAW_BYTE_STRING",
    DefaultLanguageHighlighterColors.STRING)
  final val VALID_ESCAPE = createTextAttributesKey(
    "RUST.VALID_ESCAPE",
    DefaultLanguageHighlighterColors.VALID_STRING_ESCAPE)
  final val INVALID_BYTE = createTextAttributesKey(
    "RUST.INVALID_BYTE",
    DefaultLanguageHighlighterColors.INVALID_STRING_ESCAPE)
  final val INVALID_BYTE_STR_CHAR = createTextAttributesKey(
    "RUST.INVALID_BYTE_STR_CHAR",
    DefaultLanguageHighlighterColors.INVALID_STRING_ESCAPE)
  final val INVALID_RAW_BYTE_STR_CHAR = createTextAttributesKey(
    "RUST.INVALID_RAW_BYTE_STR_CHAR",
    DefaultLanguageHighlighterColors.INVALID_STRING_ESCAPE)

  final val BLOCK_COMMENT = createTextAttributesKey(
    "RUST.BLOCK_COMMENT",
    DefaultLanguageHighlighterColors.BLOCK_COMMENT)
  final val LINE_COMMENT = createTextAttributesKey(
    "RUST.LINE_COMMENT",
    DefaultLanguageHighlighterColors.LINE_COMMENT)
  final val BLOCK_DOC = createTextAttributesKey(
    "RUST.BLOCK_DOC",
    DefaultLanguageHighlighterColors.DOC_COMMENT)
  final val LINE_DOC = createTextAttributesKey(
    "RUST.LINE_DOC",
    DefaultLanguageHighlighterColors.DOC_COMMENT)
  final val BLOCK_PARENT_DOC = createTextAttributesKey(
    "RUST.BLOCK_PARENT_DOC",
    BLOCK_DOC)
  final val LINE_PARENT_DOC = createTextAttributesKey(
    "RUST.LINE_PARENT_DOC",
    LINE_DOC)

  final val MACRO_VARIABLE = createTextAttributesKey(
    "RUST.MACRO_VARIABLE",
    DefaultLanguageHighlighterColors.LOCAL_VARIABLE)
  final val MACRO_CALL = createTextAttributesKey(
    "RUST.MACRO_CALL",
    DefaultLanguageHighlighterColors.FUNCTION_CALL)

  final val BAD_CHARACTER = createTextAttributesKey(
    "RUST.BAD_CHARACTER",
    HighlighterColors.BAD_CHARACTER)

  private final val KEYS = Map(
    RustTypes.IDENTIFIER -> IDENTIFIER,

    RustTypes.KW_ABSTRACT -> KEYWORD,
    RustTypes.KW_ALIGNOF -> KEYWORD,
    RustTypes.KW_AS -> KEYWORD,
    RustTypes.KW_BECOME -> KEYWORD,
    RustTypes.KW_BOX -> KEYWORD,
    RustTypes.KW_BREAK -> KEYWORD,
    RustTypes.KW_CONST -> KEYWORD,
    RustTypes.KW_CONTINUE -> KEYWORD,
    RustTypes.KW_CRATE -> KEYWORD,
    RustTypes.KW_DO -> KEYWORD,
    RustTypes.KW_ELSE -> KEYWORD,
    RustTypes.KW_ENUM -> KEYWORD,
    RustTypes.KW_EXTERN -> KEYWORD,
    RustTypes.KW_FALSE -> KEYWORD,
    RustTypes.KW_FINAL -> KEYWORD,
    RustTypes.KW_FN -> KEYWORD,
    RustTypes.KW_FOR -> KEYWORD,
    RustTypes.KW_IF -> KEYWORD,
    RustTypes.KW_IMPL -> KEYWORD,
    RustTypes.KW_IN -> KEYWORD,
    RustTypes.KW_LET -> KEYWORD,
    RustTypes.KW_LOOP -> KEYWORD,
    RustTypes.KW_MACRO -> KEYWORD,
    RustTypes.KW_MATCH -> KEYWORD,
    RustTypes.KW_MOD -> KEYWORD,
    RustTypes.KW_MOVE -> KEYWORD,
    RustTypes.KW_MUT -> KEYWORD,
    RustTypes.KW_OFFSETOF -> KEYWORD,
    RustTypes.KW_OVERRIDE -> KEYWORD,
    RustTypes.KW_PRIV -> KEYWORD,
    RustTypes.KW_PROC -> KEYWORD,
    RustTypes.KW_PUB -> KEYWORD,
    RustTypes.KW_PURE -> KEYWORD,
    RustTypes.KW_REF -> KEYWORD,
    RustTypes.KW_RETURN -> KEYWORD,
    RustTypes.KW_SELF_T -> KEYWORD,
    RustTypes.KW_SELF -> KEYWORD,
    RustTypes.KW_SIZEOF -> KEYWORD,
    RustTypes.KW_STATIC -> KEYWORD,
    RustTypes.KW_STRUCT -> KEYWORD,
    RustTypes.KW_SUPER -> KEYWORD,
    RustTypes.KW_TRAIT -> KEYWORD,
    RustTypes.KW_TRUE -> KEYWORD,
    RustTypes.KW_TYPE -> KEYWORD,
    RustTypes.KW_TYPEOF -> KEYWORD,
    RustTypes.KW_UNSAFE -> KEYWORD,
    RustTypes.KW_UNSIZED -> KEYWORD,
    RustTypes.KW_USE -> KEYWORD,
    RustTypes.KW_VIRTUAL -> KEYWORD,
    RustTypes.KW_WHERE -> KEYWORD,
    RustTypes.KW_WHILE -> KEYWORD,
    RustTypes.KW_YIELD -> KEYWORD,

    RustTypes.OP_AMPERSAND -> OPERATION_SIGN,
    RustTypes.OP_ARROW -> OPERATION_SIGN,
    RustTypes.OP_ASSIGN -> OPERATION_SIGN,
    RustTypes.OP_ASTERISK -> OPERATION_SIGN,
    RustTypes.OP_AT -> OPERATION_SIGN,
    RustTypes.OP_CARET -> OPERATION_SIGN,
    RustTypes.OP_CLOSE_BRACE -> BRACES,
    RustTypes.OP_CLOSE_BRACKET -> BRACKETS,
    RustTypes.OP_CLOSE_PAREN -> PARENTHESES,
    RustTypes.OP_COLON -> OPERATION_SIGN,
    RustTypes.OP_COMMA -> COMMA,
    RustTypes.OP_DOLLAR -> OPERATION_SIGN,
    RustTypes.OP_DOT -> DOT,
    RustTypes.OP_DOUBLE_AMPERSAND -> OPERATION_SIGN,
    RustTypes.OP_DOUBLE_COLON -> OPERATION_SIGN,
    RustTypes.OP_DOUBLE_DOT -> OPERATION_SIGN,
    RustTypes.OP_DOUBLE_PIPE -> OPERATION_SIGN,
    RustTypes.OP_EQUALS -> OPERATION_SIGN,
    RustTypes.OP_EXCLAMATION_MARK -> OPERATION_SIGN,
    RustTypes.OP_FAT_ARROW -> OPERATION_SIGN,
    RustTypes.OP_GREATER_THAN -> OPERATION_SIGN,
    RustTypes.OP_GREATER_THAN_EQ -> OPERATION_SIGN,
    RustTypes.OP_HASH -> OPERATION_SIGN,
    RustTypes.OP_LEFT_SHIFT -> OPERATION_SIGN,
    RustTypes.OP_LESS_THAN -> OPERATION_SIGN,
    RustTypes.OP_LESS_THAN_EQ -> OPERATION_SIGN,
    RustTypes.OP_MINUS -> OPERATION_SIGN,
    RustTypes.OP_NOT_EQUALS -> OPERATION_SIGN,
    RustTypes.OP_OPEN_BRACE -> BRACES,
    RustTypes.OP_OPEN_BRACKET -> BRACKETS,
    RustTypes.OP_OPEN_PAREN -> PARENTHESES,
    RustTypes.OP_PERCENT -> OPERATION_SIGN,
    RustTypes.OP_PIPE -> OPERATION_SIGN,
    RustTypes.OP_PLUS -> OPERATION_SIGN,
    RustTypes.OP_RIGHT_SHIFT -> OPERATION_SIGN,
    RustTypes.OP_SEMICOLON -> SEMICOLON,
    RustTypes.OP_SLASH -> OPERATION_SIGN,
    RustTypes.OP_TRIPLE_DOT -> OPERATION_SIGN,
    RustTypes.OP_UNDERSCORE -> OPERATION_SIGN,

    RustTypes.LIFETIME -> LIFETIME,

    RustTypes.DEC_LIT -> NUMBER,
    RustTypes.BIN_LIT -> NUMBER,
    RustTypes.OCT_LIT -> NUMBER,
    RustTypes.HEX_LIT -> NUMBER,
    RustTypes.FLOAT_LIT -> NUMBER,

    RustTypes.CHAR_LIT -> CHAR,
    RustTypes.BYTE_LIT -> BYTE,
    RustTypes.STR_LIT_BEGIN -> STRING,
    RustTypes.STR_LIT_END -> STRING,
    RustTypes.STR_LIT_TOKEN -> STRING,
    RustTypes.STR_LIT_ESCAPE -> VALID_ESCAPE,
    RustTypes.RAW_STR_LIT_BEGIN -> RAW_STRING,
    RustTypes.RAW_STR_LIT_END -> RAW_STRING,
    RustTypes.RAW_STR_LIT_TOKEN -> RAW_STRING,
    RustTypes.BYTE_STR_LIT_BEGIN -> BYTE_STRING,
    RustTypes.BYTE_STR_LIT_END -> BYTE_STRING,
    RustTypes.BYTE_STR_LIT_TOKEN -> BYTE_STRING,
    RustTypes.BYTE_STR_LIT_ESCAPE -> VALID_ESCAPE,
    RustTypes.RAW_BYTE_STR_LIT_BEGIN -> RAW_BYTE_STRING,
    RustTypes.RAW_BYTE_STR_LIT_END -> RAW_BYTE_STRING,
    RustTypes.RAW_BYTE_STR_LIT_TOKEN -> RAW_BYTE_STRING,

    RustTypes.INVALID_BYTE_LIT -> INVALID_BYTE,
    RustTypes.INVALID_BYTE_STR_LIT_TOKEN -> INVALID_BYTE_STR_CHAR,
    RustTypes.INVALID_RAW_BYTE_STR_LIT_TOKEN -> INVALID_RAW_BYTE_STR_CHAR,

    RustTypes.BLOCK_COMMENT -> BLOCK_COMMENT,
    RustTypes.LINE_COMMENT -> LINE_COMMENT,
    RustTypes.BLOCK_DOC -> BLOCK_DOC,
    RustTypes.LINE_DOC -> LINE_DOC,
    RustTypes.BLOCK_PARENT_DOC -> BLOCK_PARENT_DOC,
    RustTypes.LINE_PARENT_DOC -> LINE_PARENT_DOC,

    RustTypes.MACRO_VARIABLE -> MACRO_VARIABLE,
    RustTypes.MACRO_CALL -> MACRO_CALL,

    TokenType.BAD_CHARACTER -> BAD_CHARACTER
  )
}
