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

package org.rustidea.editor;

import com.google.common.collect.ImmutableMap;
import com.intellij.lexer.Lexer;
import com.intellij.openapi.editor.DefaultLanguageHighlighterColors;
import com.intellij.openapi.editor.HighlighterColors;
import com.intellij.openapi.editor.colors.TextAttributesKey;
import com.intellij.openapi.fileTypes.SyntaxHighlighterBase;
import com.intellij.psi.TokenType;
import com.intellij.psi.tree.IElementType;
import org.jetbrains.annotations.NotNull;
import org.rustidea.lexer.RustHighlighterLexer;

import java.util.Map;

import static com.intellij.openapi.editor.colors.TextAttributesKey.createTextAttributesKey;
import static org.rustidea.editor.RustHighlighterTypes.*;

// FIXME Syntax highlighting for raw (byte) string breaks sometimes, I don't know when exactly and why.
// FIXME An assertion fails in com.intellij.openapi.editor.ex.util.SegmentArray.segmentNotFound(SegmentArray.java:129) when trying to delete last quote character form raw (byte) string at the end of the file (making this operation impossible). I don't know why.
// FIXME To sum up: raw (byte) string handling in lexer is fucked up.

public class RustSyntaxHighlighter extends SyntaxHighlighterBase {
    public static final TextAttributesKey IDENTIFIER = createTextAttributesKey(
        "RUST.IDENTIFIER",
        DefaultLanguageHighlighterColors.IDENTIFIER);
    public static final TextAttributesKey KEYWORD = createTextAttributesKey(
        "RUST.KEYWORD",
        DefaultLanguageHighlighterColors.KEYWORD);
    public static final TextAttributesKey OPERATION_SIGN = createTextAttributesKey(
        "RUST.OPERATION_SIGN",
        DefaultLanguageHighlighterColors.OPERATION_SIGN);
    public static final TextAttributesKey BRACES = createTextAttributesKey(
        "RUST.BRACES",
        DefaultLanguageHighlighterColors.BRACES);
    public static final TextAttributesKey DOT = createTextAttributesKey(
        "RUST.DOT",
        DefaultLanguageHighlighterColors.DOT);
    public static final TextAttributesKey SEMICOLON = createTextAttributesKey(
        "RUST.SEMICOLON",
        DefaultLanguageHighlighterColors.SEMICOLON);
    public static final TextAttributesKey COMMA = createTextAttributesKey(
        "RUST.COMMA",
        DefaultLanguageHighlighterColors.COMMA);
    public static final TextAttributesKey PARENTHESES = createTextAttributesKey(
        "RUST.PARENTHESES",
        DefaultLanguageHighlighterColors.PARENTHESES);
    public static final TextAttributesKey BRACKETS = createTextAttributesKey(
        "RUST.BRACKETS",
        DefaultLanguageHighlighterColors.BRACKETS);
    public static final TextAttributesKey LIFETIME = createTextAttributesKey(
        "RUST.LIFETIME",
        DefaultLanguageHighlighterColors.METADATA);

    public static final TextAttributesKey NUMBER = createTextAttributesKey(
        "RUST.NUMBER",
        DefaultLanguageHighlighterColors.NUMBER);
    public static final TextAttributesKey CHAR = createTextAttributesKey(
        "RUST.CHAR",
        DefaultLanguageHighlighterColors.STRING);
    public static final TextAttributesKey STRING = createTextAttributesKey(
        "RUST.STRING",
        DefaultLanguageHighlighterColors.STRING);
    public static final TextAttributesKey RAW_STRING = createTextAttributesKey(
        "RUST.RAW_STRING",
        DefaultLanguageHighlighterColors.STRING);
    public static final TextAttributesKey VALID_ESCAPE = createTextAttributesKey(
        "RUST.VALID_ESCAPE",
        DefaultLanguageHighlighterColors.VALID_STRING_ESCAPE);
    public static final TextAttributesKey INVALID_ESCAPE = createTextAttributesKey(
        "RUST.INVALID_ESCAPE",
        DefaultLanguageHighlighterColors.INVALID_STRING_ESCAPE);

    public static final TextAttributesKey BLOCK_COMMENT = createTextAttributesKey(
        "RUST.BLOCK_COMMENT",
        DefaultLanguageHighlighterColors.BLOCK_COMMENT);
    public static final TextAttributesKey LINE_COMMENT = createTextAttributesKey(
        "RUST.LINE_COMMENT",
        DefaultLanguageHighlighterColors.LINE_COMMENT);
    public static final TextAttributesKey BLOCK_DOC = createTextAttributesKey(
        "RUST.BLOCK_DOC",
        DefaultLanguageHighlighterColors.DOC_COMMENT);
    public static final TextAttributesKey LINE_DOC = createTextAttributesKey(
        "RUST.LINE_DOC",
        DefaultLanguageHighlighterColors.DOC_COMMENT);
    public static final TextAttributesKey BLOCK_PARENT_DOC = createTextAttributesKey(
        "RUST.BLOCK_PARENT_DOC",
        BLOCK_DOC);
    public static final TextAttributesKey LINE_PARENT_DOC = createTextAttributesKey(
        "RUST.LINE_PARENT_DOC",
        LINE_DOC);

    public static final TextAttributesKey MACRO_VARIABLE = createTextAttributesKey(
        "RUST.MACRO_VARIABLE",
        DefaultLanguageHighlighterColors.LOCAL_VARIABLE);
    public static final TextAttributesKey MACRO_CALL = createTextAttributesKey(
        "RUST.MACRO_CALL",
        DefaultLanguageHighlighterColors.FUNCTION_CALL);

    public static final TextAttributesKey BAD_CHARACTER = createTextAttributesKey(
        "RUST.BAD_CHARACTER",
        HighlighterColors.BAD_CHARACTER);

    public static final Map<IElementType, TextAttributesKey> KEYS;

    static {
        KEYS = ImmutableMap.<IElementType, TextAttributesKey>builder()
            .put(SH_IDENTIFIER, IDENTIFIER)
            .put(SH_KEYWORD, KEYWORD)
            .put(SH_OPERATOR, OPERATION_SIGN)
            .put(SH_BRACES, BRACES)
            .put(SH_DOT, DOT)
            .put(SH_SEMICOLON, SEMICOLON)
            .put(SH_COMMA, COMMA)
            .put(SH_PARENTHESES, PARENTHESES)
            .put(SH_BRACKETS, BRACKETS)
            .put(SH_LIFETIME, LIFETIME)
            .put(SH_NUMBER, NUMBER)
            .put(SH_SINGLE_CHAR, CHAR)
            .put(SH_STRING, STRING)
            .put(SH_RAW_STRING, RAW_STRING)
            .put(SH_VALID_ESCAPE, VALID_ESCAPE)
            .put(SH_INVALID_ESCAPE, INVALID_ESCAPE)
            .put(SH_BLOCK_COMMENT, BLOCK_COMMENT)
            .put(SH_LINE_COMMENT, LINE_COMMENT)
            .put(SH_BLOCK_DOC, BLOCK_DOC)
            .put(SH_LINE_DOC, LINE_DOC)
            .put(SH_BLOCK_PARENT_DOC, BLOCK_PARENT_DOC)
            .put(SH_LINE_PARENT_DOC, LINE_PARENT_DOC)
            .put(SH_MACRO_VAR, MACRO_VARIABLE)
            .put(SH_MACRO_CALL, MACRO_CALL)
            .put(TokenType.BAD_CHARACTER, BAD_CHARACTER)
            .build();
    }

    @NotNull
    @Override
    public Lexer getHighlightingLexer() {
        return new RustHighlighterLexer();
    }

    @NotNull
    @Override
    public TextAttributesKey[] getTokenHighlights(IElementType elementType) {
        return pack(KEYS.get(elementType), EMPTY);
    }
}
