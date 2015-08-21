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
import com.intellij.openapi.editor.{DefaultLanguageHighlighterColors => DLHC}
import com.intellij.openapi.fileTypes.SyntaxHighlighterBase
import com.intellij.openapi.fileTypes.SyntaxHighlighterBase.{EMPTY, pack}
import com.intellij.psi.tree.IElementType
import org.rustidea.parser.RustLexer
import org.rustidea.psi.{RustTypes => RT}

import scala.collection.immutable.HashMap

class RustSyntaxHighlighter extends SyntaxHighlighterBase {
  override def getHighlightingLexer: Lexer = new RustLexer()

  override def getTokenHighlights(et: IElementType): Array[TextAttributesKey] =
    pack(RustSyntaxHighlighter.KEYS.get(et).orNull, EMPTY)
}

object RustSyntaxHighlighter {
  final val IDENTIFIER = createTextAttributesKey(
    "RUST.IDENTIFIER",
    DLHC.IDENTIFIER)
  final val KEYWORD = createTextAttributesKey(
    "RUST.KEYWORD",
    DLHC.KEYWORD)
  final val OPERATION_SIGN = createTextAttributesKey(
    "RUST.OPERATION_SIGN",
    DLHC.OPERATION_SIGN)
  final val BRACES = createTextAttributesKey(
    "RUST.BRACES",
    DLHC.BRACES)
  final val DOT = createTextAttributesKey(
    "RUST.DOT",
    DLHC.DOT)
  final val SEMICOLON = createTextAttributesKey(
    "RUST.SEMICOLON",
    DLHC.SEMICOLON)
  final val COMMA = createTextAttributesKey(
    "RUST.COMMA",
    DLHC.COMMA)
  final val PARENTHESES = createTextAttributesKey(
    "RUST.PARENTHESES",
    DLHC.PARENTHESES)
  final val BRACKETS = createTextAttributesKey(
    "RUST.BRACKETS",
    DLHC.BRACKETS)
  final val LIFETIME = createTextAttributesKey(
    "RUST.LIFETIME",
    DLHC.METADATA)

  final val NUMBER = createTextAttributesKey(
    "RUST.NUMBER",
    DLHC.NUMBER)
  final val CHAR = createTextAttributesKey(
    "RUST.CHAR",
    DLHC.STRING)
  final val STRING = createTextAttributesKey(
    "RUST.STRING",
    DLHC.STRING)
  final val RAW_STRING = createTextAttributesKey(
    "RUST.RAW_STRING",
    DLHC.STRING)
  final val BYTE = createTextAttributesKey(
    "RUST.BYTE",
    DLHC.STRING)
  final val BYTE_STRING = createTextAttributesKey(
    "RUST.BYTE_STRING",
    DLHC.STRING)
  final val RAW_BYTE_STRING = createTextAttributesKey(
    "RUST.RAW_BYTE_STRING",
    DLHC.STRING)
  final val VALID_ESCAPE = createTextAttributesKey(
    "RUST.VALID_ESCAPE",
    DLHC.VALID_STRING_ESCAPE)
  final val INVALID_BYTE = createTextAttributesKey(
    "RUST.INVALID_BYTE",
    DLHC.INVALID_STRING_ESCAPE)
  final val INVALID_BYTE_STR_CHAR = createTextAttributesKey(
    "RUST.INVALID_BYTE_STR_CHAR",
    DLHC.INVALID_STRING_ESCAPE)
  final val INVALID_RAW_BYTE_STR_CHAR = createTextAttributesKey(
    "RUST.INVALID_RAW_BYTE_STR_CHAR",
    DLHC.INVALID_STRING_ESCAPE)

  final val BLOCK_COMMENT = createTextAttributesKey(
    "RUST.BLOCK_COMMENT",
    DLHC.BLOCK_COMMENT)
  final val LINE_COMMENT = createTextAttributesKey(
    "RUST.LINE_COMMENT",
    DLHC.LINE_COMMENT)
  final val DOC_BLOCK_COMMENT = createTextAttributesKey(
    "RUST.DOC_BLOCK_COMMENT",
    DLHC.DOC_COMMENT)
  final val DOC_LINE_COMMENT = createTextAttributesKey(
    "RUST.DOC_LINE_COMMENT",
    DLHC.DOC_COMMENT)
  final val PARENT_DOC_BLOCK_COMMENT = createTextAttributesKey(
    "RUST.PARENT_DOC_BLOCK_COMMENT",
    DOC_BLOCK_COMMENT)
  final val PARENT_DOC_LINE_COMMENT = createTextAttributesKey(
    "RUST.PARENT_DOC_LINE_COMMENT",
    DOC_LINE_COMMENT)

  private final val KEYS = HashMap(
    RT.IDENTIFIER -> IDENTIFIER,

    RT.KW_ABSTRACT -> KEYWORD,
    RT.KW_ALIGNOF -> KEYWORD,
    RT.KW_AS -> KEYWORD,
    RT.KW_BECOME -> KEYWORD,
    RT.KW_BOX -> KEYWORD,
    RT.KW_BREAK -> KEYWORD,
    RT.KW_CONST -> KEYWORD,
    RT.KW_CONTINUE -> KEYWORD,
    RT.KW_CRATE -> KEYWORD,
    RT.KW_DO -> KEYWORD,
    RT.KW_ELSE -> KEYWORD,
    RT.KW_ENUM -> KEYWORD,
    RT.KW_EXTERN -> KEYWORD,
    RT.KW_FALSE -> KEYWORD,
    RT.KW_FINAL -> KEYWORD,
    RT.KW_FN -> KEYWORD,
    RT.KW_FOR -> KEYWORD,
    RT.KW_IF -> KEYWORD,
    RT.KW_IMPL -> KEYWORD,
    RT.KW_IN -> KEYWORD,
    RT.KW_LET -> KEYWORD,
    RT.KW_LOOP -> KEYWORD,
    RT.KW_MACRO -> KEYWORD,
    RT.KW_MATCH -> KEYWORD,
    RT.KW_MOD -> KEYWORD,
    RT.KW_MOVE -> KEYWORD,
    RT.KW_MUT -> KEYWORD,
    RT.KW_OFFSETOF -> KEYWORD,
    RT.KW_OVERRIDE -> KEYWORD,
    RT.KW_PRIV -> KEYWORD,
    RT.KW_PROC -> KEYWORD,
    RT.KW_PUB -> KEYWORD,
    RT.KW_PURE -> KEYWORD,
    RT.KW_REF -> KEYWORD,
    RT.KW_RETURN -> KEYWORD,
    RT.KW_SELF_T -> KEYWORD,
    RT.KW_SELF -> KEYWORD,
    RT.KW_SIZEOF -> KEYWORD,
    RT.KW_STATIC -> KEYWORD,
    RT.KW_STRUCT -> KEYWORD,
    RT.KW_SUPER -> KEYWORD,
    RT.KW_TRAIT -> KEYWORD,
    RT.KW_TRUE -> KEYWORD,
    RT.KW_TYPE -> KEYWORD,
    RT.KW_TYPEOF -> KEYWORD,
    RT.KW_UNSAFE -> KEYWORD,
    RT.KW_UNSIZED -> KEYWORD,
    RT.KW_USE -> KEYWORD,
    RT.KW_VIRTUAL -> KEYWORD,
    RT.KW_WHERE -> KEYWORD,
    RT.KW_WHILE -> KEYWORD,
    RT.KW_YIELD -> KEYWORD,

    RT.OP_AMPERSAND -> OPERATION_SIGN,
    RT.OP_ARROW -> OPERATION_SIGN,
    RT.OP_ASSIGN -> OPERATION_SIGN,
    RT.OP_ASTERISK -> OPERATION_SIGN,
    RT.OP_AT -> OPERATION_SIGN,
    RT.OP_CARET -> OPERATION_SIGN,
    RT.OP_CLOSE_BRACE -> BRACES,
    RT.OP_CLOSE_BRACKET -> BRACKETS,
    RT.OP_CLOSE_PAREN -> PARENTHESES,
    RT.OP_COLON -> OPERATION_SIGN,
    RT.OP_COMMA -> COMMA,
    RT.OP_DOLLAR -> OPERATION_SIGN,
    RT.OP_DOT -> DOT,
    RT.OP_DOUBLE_AMPERSAND -> OPERATION_SIGN,
    RT.OP_DOUBLE_COLON -> OPERATION_SIGN,
    RT.OP_DOUBLE_DOT -> OPERATION_SIGN,
    RT.OP_DOUBLE_PIPE -> OPERATION_SIGN,
    RT.OP_EQUALS -> OPERATION_SIGN,
    RT.OP_EXCLAMATION_MARK -> OPERATION_SIGN,
    RT.OP_FAT_ARROW -> OPERATION_SIGN,
    RT.OP_GREATER_THAN -> OPERATION_SIGN,
    RT.OP_GREATER_THAN_EQ -> OPERATION_SIGN,
    RT.OP_HASH -> OPERATION_SIGN,
    RT.OP_LEFT_SHIFT -> OPERATION_SIGN,
    RT.OP_LESS_THAN -> OPERATION_SIGN,
    RT.OP_LESS_THAN_EQ -> OPERATION_SIGN,
    RT.OP_MINUS -> OPERATION_SIGN,
    RT.OP_NOT_EQUALS -> OPERATION_SIGN,
    RT.OP_OPEN_BRACE -> BRACES,
    RT.OP_OPEN_BRACKET -> BRACKETS,
    RT.OP_OPEN_PAREN -> PARENTHESES,
    RT.OP_PERCENT -> OPERATION_SIGN,
    RT.OP_PIPE -> OPERATION_SIGN,
    RT.OP_PLUS -> OPERATION_SIGN,
    RT.OP_RIGHT_SHIFT -> OPERATION_SIGN,
    RT.OP_SEMICOLON -> SEMICOLON,
    RT.OP_SLASH -> OPERATION_SIGN,
    RT.OP_TRIPLE_DOT -> OPERATION_SIGN,
    RT.OP_UNDERSCORE -> OPERATION_SIGN,

    RT.LIFETIME -> LIFETIME,

    RT.DEC_LIT -> NUMBER,
    RT.BIN_LIT -> NUMBER,
    RT.OCT_LIT -> NUMBER,
    RT.HEX_LIT -> NUMBER,
    RT.FLOAT_LIT -> NUMBER,

    RT.CHAR_LIT -> CHAR,
    RT.BYTE_LIT -> BYTE,
    RT.STR_LIT_BEGIN -> STRING,
    RT.STR_LIT_END -> STRING,
    RT.STR_LIT_TOKEN -> STRING,
    RT.STR_LIT_ESCAPE -> VALID_ESCAPE,
    RT.RAW_STR_LIT_BEGIN -> RAW_STRING,
    RT.RAW_STR_LIT_END -> RAW_STRING,
    RT.RAW_STR_LIT_TOKEN -> RAW_STRING,
    RT.BYTE_STR_LIT_BEGIN -> BYTE_STRING,
    RT.BYTE_STR_LIT_END -> BYTE_STRING,
    RT.BYTE_STR_LIT_TOKEN -> BYTE_STRING,
    RT.BYTE_STR_LIT_ESCAPE -> VALID_ESCAPE,
    RT.RAW_BYTE_STR_LIT_BEGIN -> RAW_BYTE_STRING,
    RT.RAW_BYTE_STR_LIT_END -> RAW_BYTE_STRING,
    RT.RAW_BYTE_STR_LIT_TOKEN -> RAW_BYTE_STRING,

    RT.INVALID_BYTE_LIT -> INVALID_BYTE,
    RT.INVALID_BYTE_STR_LIT_TOKEN -> INVALID_BYTE_STR_CHAR,
    RT.INVALID_RAW_BYTE_STR_LIT_TOKEN -> INVALID_RAW_BYTE_STR_CHAR,

    RT.BLOCK_COMMENT -> BLOCK_COMMENT,
    RT.LINE_COMMENT -> LINE_COMMENT,
    RT.DOC_BLOCK_COMMENT -> DOC_BLOCK_COMMENT,
    RT.DOC_LINE_COMMENT -> DOC_LINE_COMMENT,
    RT.PARENT_DOC_BLOCK_COMMENT -> PARENT_DOC_BLOCK_COMMENT,
    RT.PARENT_DOC_LINE_COMMENT -> PARENT_DOC_LINE_COMMENT
  )
}