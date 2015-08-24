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

import com.intellij.psi.tree.IElementType;
import org.rustidea.psi.RustTokenType;

public interface RustHighlighterTypes {
    IElementType SH_IDENTIFIER = new RustTokenType("SH_IDENTIFIER");
    IElementType SH_KEYWORD = new RustTokenType("SH_KEYWORD");
    IElementType SH_OPERATOR = new RustTokenType("SH_OPERATOR");
    IElementType SH_BRACES = new RustTokenType("SH_BRACES");
    IElementType SH_DOT = new RustTokenType("SH_DOT");
    IElementType SH_SEMICOLON = new RustTokenType("SH_SEMICOLON");
    IElementType SH_COMMA = new RustTokenType("SH_COMMA");
    IElementType SH_PARENTHESES = new RustTokenType("SH_PARENTHESES");
    IElementType SH_BRACKETS = new RustTokenType("SH_BRACKETS");
    IElementType SH_LIFETIME = new RustTokenType("SH_LIFETIME");
    IElementType SH_NUMBER = new RustTokenType("SH_NUMBER");
    IElementType SH_SINGLE_CHAR = new RustTokenType("SH_SINGLE_CHAR");
    IElementType SH_STRING = new RustTokenType("SH_STRING");
    IElementType SH_RAW_STRING = new RustTokenType("SH_RAW_STRING");
    IElementType SH_VALID_ESCAPE = new RustTokenType("SH_VALID_ESCAPE");
    IElementType SH_INVALID_ESCAPE = new RustTokenType("SH_INVALID_ESCAPE");
    IElementType SH_BLOCK_COMMENT = new RustTokenType("SH_BLOCK_COMMENT");
    IElementType SH_LINE_COMMENT = new RustTokenType("SH_LINE_COMMENT");
    IElementType SH_BLOCK_DOC = new RustTokenType("SH_BLOCK_DOC");
    IElementType SH_LINE_DOC = new RustTokenType("SH_LINE_DOC");
    IElementType SH_BLOCK_PARENT_DOC = new RustTokenType("SH_BLOCK_PARENT_DOC");
    IElementType SH_LINE_PARENT_DOC = new RustTokenType("SH_LINE_PARENT_DOC");
    IElementType SH_MACRO_VAR = new RustTokenType("SH_MACRO_VAR");
    IElementType SH_MACRO_CALL = new RustTokenType("SH_MACRO_CALL");
}
