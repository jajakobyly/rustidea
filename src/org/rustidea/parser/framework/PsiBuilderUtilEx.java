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

package org.rustidea.parser.framework;

import com.intellij.lang.PsiBuilder;
import com.intellij.lang.PsiBuilder.Marker;
import com.intellij.lang.PsiBuilderUtil;
import com.intellij.psi.tree.IElementType;
import com.intellij.psi.tree.TokenSet;
import org.jetbrains.annotations.NotNull;

public class PsiBuilderUtilEx {
    public static boolean expectNot(@NotNull final PsiBuilder builder, @NotNull final IElementType token) {
        if (builder.getTokenType() != token) {
            builder.advanceLexer();
            return true;
        }
        return false;
    }

    public static boolean expectNot(@NotNull final PsiBuilder builder, @NotNull final TokenSet tokens) {
        if (!tokens.contains(builder.getTokenType())) {
            builder.advanceLexer();
            return true;
        }
        return false;
    }

    public static boolean maybe(@NotNull final PsiBuilder builder, @NotNull final IElementType token) {
        if (builder.getTokenType() == token) {
            builder.advanceLexer();
        }
        return true;
    }

    public static boolean maybe(@NotNull final PsiBuilder builder, @NotNull final TokenSet tokens) {
        if (tokens.contains(builder.getTokenType())) {
            builder.advanceLexer();
        }
        return true;
    }

    public static boolean expectOrWarn(@NotNull final PsiBuilder builder,
                                       @NotNull final IElementType token,
                                       @NotNull final String errMsg) {
        if(!PsiBuilderUtil.expect(builder, token)) {
            builder.mark().error(errMsg);
        }
        return true;
    }

    public static void unexpected(@NotNull final PsiBuilder builder) {
        Marker marker = builder.mark();
        String tokenText = builder.getTokenText();
        builder.advanceLexer();
        marker.error("unexpected " + tokenText);
    }
}
