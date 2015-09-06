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

import com.intellij.lexer.Lexer;
import com.intellij.openapi.editor.DefaultLanguageHighlighterColors;
import com.intellij.openapi.editor.HighlighterColors;
import com.intellij.openapi.editor.colors.TextAttributesKey;
import com.intellij.openapi.fileTypes.SyntaxHighlighterBase;
import com.intellij.psi.StringEscapesTokenTypes;
import com.intellij.psi.tree.IElementType;
import com.intellij.util.containers.ContainerUtil;
import org.jetbrains.annotations.NotNull;
import org.rustidea.psi.types.RsTypes;

import java.util.Map;

import static com.intellij.openapi.editor.colors.TextAttributesKey.createTextAttributesKey;

// FIXME Syntax highlighting for raw (byte) string breaks during writing in the editor.

public class RsSyntaxHighlighter extends SyntaxHighlighterBase {
    public static final TextAttributesKey IDENTIFIER = createTextAttributesKey("RUST.IDENTIFIER", DefaultLanguageHighlighterColors.IDENTIFIER);
    public static final TextAttributesKey KEYWORD = createTextAttributesKey("RUST.KEYWORD", DefaultLanguageHighlighterColors.KEYWORD);
    public static final TextAttributesKey OPERATION_SIGN = createTextAttributesKey("RUST.OPERATION_SIGN", DefaultLanguageHighlighterColors.OPERATION_SIGN);
    public static final TextAttributesKey BRACES = createTextAttributesKey("RUST.BRACE_TOKEN_SET", DefaultLanguageHighlighterColors.BRACES);
    public static final TextAttributesKey DOT = createTextAttributesKey("RUST.DOT", DefaultLanguageHighlighterColors.DOT);
    public static final TextAttributesKey SEMICOLON = createTextAttributesKey("RUST.SEMICOLON", DefaultLanguageHighlighterColors.SEMICOLON);
    public static final TextAttributesKey COMMA = createTextAttributesKey("RUST.COMMA", DefaultLanguageHighlighterColors.COMMA);
    public static final TextAttributesKey PARENTHESES = createTextAttributesKey("RUST.PAREN_TOKEN_SET", DefaultLanguageHighlighterColors.PARENTHESES);
    public static final TextAttributesKey BRACKETS = createTextAttributesKey("RUST.BRACKET_TOKEN_SET", DefaultLanguageHighlighterColors.BRACKETS);
    public static final TextAttributesKey LIFETIME = createTextAttributesKey("RUST.LIFETIME", DefaultLanguageHighlighterColors.METADATA);

    public static final TextAttributesKey NUMBER = createTextAttributesKey("RUST.NUMBER", DefaultLanguageHighlighterColors.NUMBER);
    public static final TextAttributesKey CHAR = createTextAttributesKey("RUST.CHAR", DefaultLanguageHighlighterColors.STRING);
    public static final TextAttributesKey STRING = createTextAttributesKey("RUST.STRING", DefaultLanguageHighlighterColors.STRING);
    public static final TextAttributesKey RAW_STRING = createTextAttributesKey("RUST.RAW_STRING", DefaultLanguageHighlighterColors.STRING);
    public static final TextAttributesKey VALID_ESCAPE = createTextAttributesKey("RUST.VALID_ESCAPE", DefaultLanguageHighlighterColors.VALID_STRING_ESCAPE);
    public static final TextAttributesKey INVALID_ESCAPE = createTextAttributesKey("RUST.INVALID_ESCAPE", DefaultLanguageHighlighterColors.INVALID_STRING_ESCAPE);

    public static final TextAttributesKey BLOCK_COMMENT = createTextAttributesKey("RUST.BLOCK_COMMENT", DefaultLanguageHighlighterColors.BLOCK_COMMENT);
    public static final TextAttributesKey LINE_COMMENT = createTextAttributesKey("RUST.LINE_COMMENT", DefaultLanguageHighlighterColors.LINE_COMMENT);
    public static final TextAttributesKey BLOCK_DOC = createTextAttributesKey("RUST.BLOCK_DOC", DefaultLanguageHighlighterColors.DOC_COMMENT);
    public static final TextAttributesKey LINE_DOC = createTextAttributesKey("RUST.LINE_DOC", DefaultLanguageHighlighterColors.DOC_COMMENT);
    public static final TextAttributesKey BLOCK_PARENT_DOC = createTextAttributesKey("RUST.BLOCK_PARENT_DOC", BLOCK_DOC);
    public static final TextAttributesKey LINE_PARENT_DOC = createTextAttributesKey("RUST.LINE_PARENT_DOC", LINE_DOC);

    public static final TextAttributesKey BAD_CHARACTER = createTextAttributesKey("RUST.BAD_CHARACTER", HighlighterColors.BAD_CHARACTER);

    public static final Map<IElementType, TextAttributesKey> KEYS = ContainerUtil.newHashMap();

    static {
        fillMap(KEYS, RsTypes.KEYWORD_TOKEN_SET, KEYWORD);
        fillMap(KEYS, RsTypes.OPERATION_TOKEN_SET, OPERATION_SIGN);
        fillMap(KEYS, RsTypes.BRACE_TOKEN_SET, BRACES);
        fillMap(KEYS, RsTypes.BRACKET_TOKEN_SET, BRACKETS);
        fillMap(KEYS, RsTypes.PAREN_TOKEN_SET, PARENTHESES);
        fillMap(KEYS, RsTypes.NUMBER_TOKEN_SET, NUMBER);
        fillMap(KEYS, RsTypes.CHAR_TOKEN_SET, CHAR);
        fillMap(KEYS, RsTypes.STRING_TOKEN_SET, STRING);
        fillMap(KEYS, RsTypes.RAW_STRING_TOKEN_SET, RAW_STRING);

        KEYS.put(RsTypes.IDENTIFIER, IDENTIFIER);
        KEYS.put(RsTypes.OP_DOT, DOT);
        KEYS.put(RsTypes.OP_SEMICOLON, SEMICOLON);
        KEYS.put(RsTypes.OP_COMMA, COMMA);
        KEYS.put(RsTypes.LIFETIME_TOKEN, LIFETIME);
        KEYS.put(StringEscapesTokenTypes.VALID_STRING_ESCAPE_TOKEN, VALID_ESCAPE);
        KEYS.put(StringEscapesTokenTypes.INVALID_CHARACTER_ESCAPE_TOKEN, INVALID_ESCAPE);
        KEYS.put(RsTypes.BLOCK_COMMENT, BLOCK_COMMENT);
        KEYS.put(RsTypes.LINE_COMMENT, LINE_COMMENT);
        KEYS.put(RsTypes.BLOCK_DOC, BLOCK_DOC);
        KEYS.put(RsTypes.LINE_DOC, LINE_DOC);
        KEYS.put(RsTypes.BLOCK_PARENT_DOC, BLOCK_PARENT_DOC);
        KEYS.put(RsTypes.LINE_PARENT_DOC, LINE_PARENT_DOC);
        KEYS.put(RsTypes.BAD_CHARACTER, BAD_CHARACTER);
    }

    @NotNull
    @Override
    public Lexer getHighlightingLexer() {
        return new RsHighlightingLexer();
    }

    @NotNull
    @Override
    public TextAttributesKey[] getTokenHighlights(IElementType elementType) {
        return pack(KEYS.get(elementType), EMPTY);
    }
}
