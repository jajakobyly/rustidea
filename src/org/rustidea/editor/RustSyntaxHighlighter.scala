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

package org.rustidea.editor

import com.intellij.lexer.Lexer
import com.intellij.openapi.editor.colors.TextAttributesKey
import com.intellij.openapi.editor.colors.TextAttributesKey.createTextAttributesKey
import com.intellij.openapi.editor.{DefaultLanguageHighlighterColors, HighlighterColors}
import com.intellij.openapi.fileTypes.SyntaxHighlighterBase
import com.intellij.openapi.fileTypes.SyntaxHighlighterBase.{EMPTY, pack}
import com.intellij.psi.TokenType
import com.intellij.psi.tree.IElementType
import org.rustidea.lexer.RustHighlighterLexer

// FIXME Syntax highlighting for raw (byte) string breaks sometimes, I don't know when exactly and why.
// FIXME An assertion fails in com.intellij.openapi.editor.ex.util.SegmentArray.segmentNotFound(SegmentArray.java:129) when trying to delete last quote character form raw (byte) string at the end of the file (making this operation impossible). I don't know why.
// FIXME To sum up: raw (byte) string handling in lexer is fucked up.

class RustSyntaxHighlighter extends SyntaxHighlighterBase {
  override def getHighlightingLexer: Lexer = new RustHighlighterLexer()

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
  final val VALID_ESCAPE = createTextAttributesKey(
    "RUST.VALID_ESCAPE",
    DefaultLanguageHighlighterColors.VALID_STRING_ESCAPE)
  final val INVALID_ESCAPE = createTextAttributesKey(
    "RUST.INVALID_ESCAPE",
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
    RustHighlighterTypes.SH_IDENTIFIER -> IDENTIFIER,
    RustHighlighterTypes.SH_KEYWORD -> KEYWORD,
    RustHighlighterTypes.SH_OPERATOR -> OPERATION_SIGN,
    RustHighlighterTypes.SH_BRACES -> BRACES,
    RustHighlighterTypes.SH_DOT -> DOT,
    RustHighlighterTypes.SH_SEMICOLON -> SEMICOLON,
    RustHighlighterTypes.SH_COMMA -> COMMA,
    RustHighlighterTypes.SH_PARENTHESES -> PARENTHESES,
    RustHighlighterTypes.SH_BRACKETS -> BRACKETS,
    RustHighlighterTypes.SH_LIFETIME -> LIFETIME,
    RustHighlighterTypes.SH_NUMBER -> NUMBER,
    RustHighlighterTypes.SH_SINGLE_CHAR -> CHAR,
    RustHighlighterTypes.SH_STRING -> STRING,
    RustHighlighterTypes.SH_RAW_STRING -> RAW_STRING,
    RustHighlighterTypes.SH_VALID_ESCAPE -> VALID_ESCAPE,
    RustHighlighterTypes.SH_INVALID_ESCAPE -> INVALID_ESCAPE,
    RustHighlighterTypes.SH_BLOCK_COMMENT -> BLOCK_COMMENT,
    RustHighlighterTypes.SH_LINE_COMMENT -> LINE_COMMENT,
    RustHighlighterTypes.SH_BLOCK_DOC -> BLOCK_DOC,
    RustHighlighterTypes.SH_LINE_DOC -> LINE_DOC,
    RustHighlighterTypes.SH_BLOCK_PARENT_DOC -> BLOCK_PARENT_DOC,
    RustHighlighterTypes.SH_LINE_PARENT_DOC -> LINE_PARENT_DOC,
    RustHighlighterTypes.SH_MACRO_VAR -> MACRO_VARIABLE,
    RustHighlighterTypes.SH_MACRO_CALL -> MACRO_CALL,

    TokenType.BAD_CHARACTER -> BAD_CHARACTER
  )
}
