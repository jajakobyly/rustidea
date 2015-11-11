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

package org.rustidea.parser;

import com.intellij.lang.PsiBuilder;
import com.intellij.lang.PsiBuilder.Marker;
import com.intellij.openapi.diagnostic.Logger;
import com.intellij.psi.tree.IElementType;
import org.jetbrains.annotations.NotNull;
import org.rustidea.psi.types.RsPsiTypes;

import java.util.EnumSet;

import static com.intellij.lang.PsiBuilderUtil.expect;
import static org.rustidea.psi.util.RsPsiUtil.getHumanReadableName;

public final class RsParserUtil {
    private static final Logger LOG = Logger.getInstance(RsParserUtil.class);

    private RsParserUtil() {
    }

    public static void error(@NotNull final PsiBuilder builder, final String message) {
        builder.mark().error(message);
    }

    public static void errorExpected(@NotNull final PsiBuilder builder, final IElementType elementType) {
        error(builder, "expected " + getHumanReadableName(elementType));
    }

    public static boolean expectOrWarn(@NotNull final PsiBuilder builder, @NotNull final IElementType token) {
        return expectOrWarn(builder, token, "expected " + getHumanReadableName(token));
    }

    public static boolean expectOrWarnMissing(@NotNull final PsiBuilder builder, @NotNull final IElementType token) {
        return expectOrWarn(builder, token, "missing " + getHumanReadableName(token));
    }

    public static boolean expectOrWarn(@NotNull final PsiBuilder builder,
                                       @NotNull final IElementType token,
                                       @NotNull final String errMsg) {
        if (!expect(builder, token)) {
            error(builder, errMsg);
            return false;
        }
        return true;
    }

    public static void unexpected(@NotNull final PsiBuilder builder) {
        Marker marker = builder.mark();
        final String token = getHumanReadableName(builder.getTokenType());
        builder.advanceLexer();
        marker.error("unexpected " + token);
    }

    public static boolean identifier(@NotNull final PsiBuilder builder) {
        return expectOrWarn(builder, RsPsiTypes.IDENTIFIER);
    }

    public static boolean identifier(@NotNull final PsiBuilder builder, @NotNull final IElementType psiType) {
        final Marker marker = builder.mark();
        if (identifier(builder)) {
            marker.done(psiType);
            return true;
        } else {
            marker.drop();
            return false;
        }
    }

    public static boolean semicolon(@NotNull final PsiBuilder builder) {
        return expectOrWarn(builder, RsPsiTypes.OP_SEMICOLON, "missing semicolon");
    }

    public static boolean sep(@NotNull final PsiBuilder builder,
                              @NotNull final IElementType separator,
                              @NotNull final ParserWrapper parser) {
        return sep(builder, separator, parser, EnumSet.noneOf(SepCfg.class));
    }

    public static boolean sep(@NotNull final PsiBuilder builder,
                              @NotNull final IElementType separator,
                              @NotNull final ParserWrapper parser,
                              @NotNull final EnumSet<SepCfg> config) {
        boolean globalResult = false;
        boolean first = true;

        while (!builder.eof()) {
            final Marker marker = builder.mark();
            boolean result = true;

            if (!first) result = expectOrWarn(builder, separator);
            result = result && parser.parse();

            if (!result && config.contains(SepCfg.TOLERATE_EMPTY) && builder.getTokenType() == separator) {
                result = true;
            }

            if (result) {
                marker.drop();
                globalResult = true;
            } else {
                marker.rollbackTo();
                break;
            }

            first = false;
        }

        if (builder.getTokenType() == separator) {
            if (config.contains(SepCfg.ALLOW_TRAILING)) {
                builder.advanceLexer();
                globalResult = true;
            } else {
                unexpected(builder);
            }
        }

        return globalResult;
    }

    public static boolean parenthesize(@NotNull final PsiBuilder builder,
                                       @NotNull final IElementType lparen,
                                       @NotNull final IElementType rparen,
                                       @NotNull final VoidParserWrapper parser,
                                       @NotNull final IElementType elementType) {
        final Marker marker = builder.mark();

        if (!expect(builder, lparen)) {
            marker.rollbackTo();
            return false;
        }

        parser.parse();

        expectOrWarn(builder, rparen);

        marker.done(elementType);
        return true;
    }

    public enum SepCfg {
        ALLOW_TRAILING, TOLERATE_EMPTY
    }

    public interface ParserWrapper {
        boolean parse();
    }

    public interface VoidParserWrapper {
        void parse();
    }
}
