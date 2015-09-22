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

package org.rustidea.psi.util;

import com.google.common.base.Strings;
import com.google.common.collect.ImmutableMap;
import com.intellij.openapi.util.text.StringUtil;
import com.intellij.psi.tree.IElementType;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.Nullable;
import org.rustidea.psi.types.RsTypes;

import java.util.Map;

import static org.rustidea.psi.types.RsTokenTypes.*;

public final class RsTokenUtil {
    private static final Map<IElementType, String> HUMAN_READABLE_NAMES =
        new ImmutableMap.Builder<IElementType, String>()
            .put(INT_LIT, "numeric literal")
            .put(FLOAT_LIT, "float literal")
            .put(CHAR_LIT, "char literal")
            .put(BYTE_LIT, "byte literal")
            .put(STRING_LIT, "string literal")
            .put(BYTE_STRING_LIT, "byte string literal")
            .put(RAW_STRING_LIT, "raw string literal")
            .put(RAW_BYTE_STRING_LIT, "raw byte string literal")
            .put(IDENTIFIER, "identifier")
            .put(LIFETIME_TOKEN, "lifetime")
            .put(OP_SEMICOLON, "semicolon")
            .put(OP_COMMA, "comma")
            .put(OP_DOT, "dot")
            .build();

    private RsTokenUtil() {
    }

    @Nullable
    @Contract(value = "null -> null", pure = true)
    public static String getHumanReadableName(@Nullable final IElementType tokenType) {
        if (tokenType == null) return null;
        if (HUMAN_READABLE_NAMES.containsKey(tokenType)) {
            return HUMAN_READABLE_NAMES.get(tokenType);
        }
        if (RsTypes.KEYWORD_TOKEN_SET.contains(tokenType) || RsTypes.OPERATOR_TOKEN_SET.contains(tokenType)) {
            return '\'' + tokenType.toString() + '\'';
        }
        return null;
    }

    @Nullable
    @Contract(value = "null, _ -> null", pure = true)
    public static String getHumanReadableName(@Nullable final IElementType tokenType, final int n) {
        if (tokenType == null) return null;
        final String name = getHumanReadableName(tokenType);
        if (Strings.isNullOrEmpty(name)) return null;
        return StringUtil.pluralize(name, n);
    }
}
