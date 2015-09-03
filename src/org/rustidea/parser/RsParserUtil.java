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
import com.intellij.openapi.util.Condition;
import com.intellij.psi.tree.IElementType;
import com.intellij.psi.tree.TokenSet;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import static com.intellij.lang.PsiBuilderUtil.expect;
import static com.intellij.lang.PsiBuilderUtil.rollbackTo;

public final class RsParserUtil {
    private RsParserUtil() {
    }

    public static void error(@NotNull final PsiBuilder builder, @NotNull final String message) {
        builder.mark().error(message);
    }

    public static void error(@NotNull final PsiBuilder builder, @NotNull final String message, @Nullable final Marker before) {
        if (before == null) {
            error(builder, message);
        } else {
            before.precede().errorBefore(message, before);
        }
    }

    @NotNull
    public static Marker eat(@NotNull final PsiBuilder builder, @NotNull final Condition<PsiBuilder> condition) {
        Marker marker = builder.mark();
        while (condition.value(builder) && !builder.eof()) {
            builder.advanceLexer();
        }
        return marker;
    }

    @NotNull
    public static Marker eatWhile(@NotNull final PsiBuilder builder, @NotNull final TokenSet expectedTypes) {
        return eat(builder, new Condition<PsiBuilder>() {
            @Override
            public boolean value(@NotNull PsiBuilder builder) {
                return expectedTypes.contains(builder.getTokenType());
            }
        });
    }

    @NotNull
    public static Marker eatUnless(@NotNull final PsiBuilder builder, @NotNull final TokenSet unexpectedTypes) {
        return eat(builder, new Condition<PsiBuilder>() {
            @Override
            public boolean value(@NotNull PsiBuilder builder) {
                return !unexpectedTypes.contains(builder.getTokenType());
            }
        });
    }

    @Nullable
    public static Marker tryParseToken(@NotNull final PsiBuilder builder, @NotNull final IElementType type, @NotNull final TokenSet tokenSet) {
        final Marker marker = builder.mark();
        if (expect(builder, tokenSet)) {
            marker.done(type);
            return marker;
        }
        rollbackTo(marker);
        return null;
    }
}
