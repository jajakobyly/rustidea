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
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Map;

public final class RsStringUtil {
    public static final String DEC_DIGIT = "0123456789";
    public static final String BIN_DIGIT = "01";
    public static final String OCT_DIGIT = "01234567";
    public static final String HEX_DIGIT = "0123456789abcdefABCDEF";
    public static final String NUM_OTHER_CHARS = "+-_.";
    private static final Map<Character, Character> SHORT_ESCAPES =
        new ImmutableMap.Builder<Character, Character>()
            .put('n', '\n')
            .put('r', '\r')
            .put('t', '\t')
            .put('\\', '\\')
            .put('0', '\0')
            .put('\'', '\'')
            .put('"', '"')
            .build();

    private RsStringUtil() {
    }

    @Contract(pure = true)
    public static boolean isRustWhitespace(final char ch) {
        return ch == ' ' || ch == '\n' || ch == '\r' || ch == '\t';
    }

    @Contract(pure = true)
    public static boolean isRustIdentifierStart(final char ch) {
        // XID_START ::= [[:L:][:Nl:][:Other_ID_Start:]--[:Pattern_Syntax:]--[:Pattern_White_Space:]]
        // FIXME:RJP-28 Does not comply in 100% with spec
        return Character.isLetter(ch);
    }

    @Contract(pure = true)
    public static boolean isRustIdentifierPart(final char ch) {
        // XID_CONTINUE ::= [[:ID_Start:][:Mn:][:Mc:][:Nd:][:Pc:][:Other_ID_Continue:]--[:Pattern_Syntax:]--[:Pattern_White_Space:]]
        // FIXME:RJP-28 Does not comply in 100% with spec
        return Character.isLetterOrDigit(ch) || ch == '_';
    }

    @NotNull
    @Contract(pure = true)
    public static String unquote(@Nullable final String str, final char quote) {
        if (Strings.isNullOrEmpty(str)) return "";

        final int len = str.length();
        int begin = 0;
        int end = len - 1;

        if (str.charAt(0) == quote) begin++;
        if (len > 1 && str.charAt(end) == quote) end--;

        return str.substring(begin, end + 1);
    }

    // TODO:RJP-42 write #escapeRust method which escapes strings using Rust's escaping rules

    @NotNull
    @Contract(pure = true)
    public static String unescapeRust(@NotNull final String str,
                                      final boolean escapeUnicode,
                                      final boolean escapeEol) {
        final int NORMAL = 0;
        final int ESCAPE = 1;
        final int ESCAPE_UNICODE = 2;
        final int ESCAPE_EOL = 3;

        final int len = str.length();
        final StringBuilder sb = new StringBuilder(len);
        int state = NORMAL;
        int escStart = 0;

        for (int idx = 0; idx < len; idx++) {
            final char ch = str.charAt(idx);
            switch (state) {
                case ESCAPE:
                    if (idx + 2 < len && ch == 'x') {
                        state = NORMAL;
                        try {
                            int code = Integer.parseInt(str.substring(idx + 1, idx + 3), 16);
                            idx += 2;
                            sb.append((char) code);
                        } catch (NumberFormatException e) {
                            sb.append("\\x");
                        }
                    } else if (escapeUnicode && idx + 1 < len && ch == 'u' && str.charAt(idx + 1) == '{') {
                        if (idx + 2 == len) {
                            state = NORMAL;
                            idx++;
                            sb.append("\\u{");
                        } else {
                            state = ESCAPE_UNICODE;
                            idx++;
                            escStart = idx + 1;
                        }
                    } else if (escapeEol && (ch == '\r' || ch == '\n')) {
                        state = ESCAPE_EOL;
                    } else if (SHORT_ESCAPES.containsKey(ch)) {
                        state = NORMAL;
                        sb.append(SHORT_ESCAPES.get(ch));
                    } else {
                        state = NORMAL;
                        sb.append('\\');
                        sb.append(ch);
                    }
                    break;
                case ESCAPE_UNICODE:
                    boolean revert = false;

                    if (ch == '}') {
                        if (idx - escStart > 6) {
                            revert = true;
                        } else {
                            try {
                                int code = Integer.parseInt(str.substring(escStart, idx), 16);
                                sb.append((char) code);
                                state = NORMAL;
                                escStart = 0;
                            } catch (NumberFormatException e) {
                                revert = true;
                            }
                        }
                    }

                    if (revert) {
                        state = NORMAL;
                        sb.append("\\u{");
                        idx = escStart - 1;
                        escStart = 0;
                    }

                    break;
                case ESCAPE_EOL:
                    if (!isRustWhitespace(ch)) {
                        state = NORMAL;
                        sb.append(ch);
                    }
                    break;
                default:
                    if (ch == '\\') {
                        state = ESCAPE;
                    } else {
                        sb.append(ch);
                    }
                    break;
            }
        }

        return sb.toString();
    }
}
