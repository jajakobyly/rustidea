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

import com.intellij.openapi.util.text.StringUtil;
import com.intellij.psi.tree.IElementType;
import com.intellij.util.containers.ContainerUtil;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.rustidea.psi.RsLiteral;
import org.rustidea.psi.types.RsTypes;

import java.util.Set;

public final class RsLiteralUtil {
    public static final Set<String> VALID_INT_SUFFIXES = ContainerUtil.immutableSet(
        "u8", "i8", "u16", "i16", "u32", "i32", "u64", "i64", "isize", "usize");
    public static final Set<String> VALID_FLOAT_SUFFIXES = ContainerUtil.immutableSet(
        "f32", "f64");

    private RsLiteralUtil() {
    }

    @Contract(pure = true)
    @SuppressWarnings("SimplifiableIfStatement")
    public static boolean hasValidSuffix(@NotNull final RsLiteral literal) {
        IElementType tokenType = literal.getTokenType();

        assert RsTypes.LITERAL_TOKEN_SET.contains(tokenType);

        if (tokenType == RsTypes.INT_LIT) {
            return isValidIntegerSuffix(literal.getSuffix());
        }

        if (tokenType == RsTypes.FLOAT_LIT) {
            return isValidFloatSuffix(literal.getSuffix());
        }

        return false;
    }

    @Contract(pure = true)
    public static boolean isValidIntegerSuffix(@NotNull final String suffix) {
        return VALID_INT_SUFFIXES.contains(suffix);
    }

    @Contract(pure = true)
    public static boolean isValidFloatSuffix(@NotNull final String suffix) {
        return VALID_FLOAT_SUFFIXES.contains(suffix);
    }

    @NotNull
    @Contract(pure = true)
    // TODO This code is a very basic pseudo-lexer so it can be enhanced and extracted as a new literal lexer
    public static String extractSuffixFromNumLit(@NotNull final String text) {
        final int len = text.length();

        String digits = RsStringUtil.DEC_DIGIT;
        boolean hasExponent = false;

        int idx = 0;
        if (len >= 2 && text.charAt(0) == '0') {
            final char ch1 = text.charAt(1);
            if (ch1 == 'b') {
                digits = RsStringUtil.BIN_DIGIT;
                idx = 2;
            } else if (ch1 == 'o') {
                digits = RsStringUtil.OCT_DIGIT;
                idx = 2;
            } else if (ch1 == 'x') {
                digits = RsStringUtil.HEX_DIGIT;
                idx = 2;
            }
        }

        for (; idx < len; idx++) {
            final char ch = text.charAt(idx);

            if (!hasExponent && ch == 'e' || ch == 'E') {
                hasExponent = true;
                continue;
            }

            if (!StringUtil.containsChar(digits, ch) && !StringUtil.containsChar(RsStringUtil.NUM_OTHER_CHARS, ch)) {
                return text.substring(idx);
            }
        }

        return "";
    }

    @NotNull
    @Contract(pure = true)
    // TODO This code is a very basic pseudo-lexer so it can be enhanced and extracted as a new literal lexer
    public static String extractSuffixFromQuotedLit(@NotNull final String text, final char quote) {
        final int len = text.length();

        int idx = 0;
        if (len >= 2 && text.charAt(0) == 'b' && text.charAt(1) == quote) {
            // literal starts with `b<quote>`
            idx = 2;
        } else if (len >= 1 && text.charAt(0) == quote) {
            // literal starts with `<quote>`
            idx = 1;
        }

        for (; idx < len; idx++) {
            final char ch = text.charAt(idx);

            // handle escapes (`\<quote`)
            if (idx > 0 && ch == quote && text.charAt(idx - 1) == '\\') {
                continue;
            }

            // we have closing quote so the rest must be the suffix
            if (ch == quote) {
                return text.substring(idx + 1);
            }
        }

        return "";
    }

    @NotNull
    @Contract(pure = true)
    // TODO This code is a very basic pseudo-lexer so it can be enhanced and extracted as a new literal lexer
    public static String extractSuffixFromRawStr(@NotNull final String text) {
        final int NORMAL = 0;
        final int COUNTING_START_HASHES = 1;
        final int COUNTING_END_HASHES = 2;

        final int len = text.length();
        int state = COUNTING_START_HASHES;
        int startHashes = 0;
        int endHashes = 0;

        int idx = 0;
        if (len >= 2 && text.charAt(0) == 'b' && text.charAt(1) == 'r') {
            // literal starts with `br`
            idx = 2;
        } else if (len >= 1 && text.charAt(0) == 'r') {
            // literal starts with `r`
            idx = 1;
        }

        for (; idx < len; idx++) {
            final char ch = text.charAt(idx);
            switch (state) {
                case COUNTING_START_HASHES:
                    if (ch == '#') {
                        startHashes++;
                    } else {
                        state = NORMAL;
                    }
                    break;
                case COUNTING_END_HASHES:
                    if (ch == '#') {
                        endHashes++;
                        // we reached the end of the raw string, so the rest must be the suffix
                        if (startHashes == endHashes) {
                            return text.substring(idx + 1);
                        }
                    } else {
                        state = NORMAL;
                        endHashes = 0;
                    }
                    break;
                default:
                    if (ch == '"') {
                        state = COUNTING_END_HASHES;
                        endHashes = 0;

                        // we reached the end of the raw string, so the rest must be the suffix
                        if (startHashes == 0) {
                            return text.substring(idx + 1);
                        }
                    }
                    break;
            }
        }

        return "";
    }

    @NotNull
    @Contract(pure = true)
    public static String removeRawStringHashes(@NotNull final String str) {
        final String noR = StringUtil.trimStart(str, "r");
        final int count = StringUtil.countChars(noR, '#', 0, true);
        final String hashes = StringUtil.repeatSymbol('#', count);
        return StringUtil.trimEnd(StringUtil.trimStart(noR, hashes + '"'), '"' + hashes);
    }
}
