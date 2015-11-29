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

import com.google.common.base.Function;
import com.google.common.base.Strings;
import com.intellij.openapi.util.TextRange;
import com.intellij.openapi.util.text.StringUtil;
import com.intellij.psi.PsiElement;
import com.intellij.psi.tree.IElementType;
import com.intellij.psi.util.PsiUtilBase;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.rustidea.psi.types.IRsElementType;

public final class RsPsiUtil extends PsiUtilBase {
    public static final Function<PsiElement, TextRange> TEXT_RANGE_EXTRACTOR = new Function<PsiElement, TextRange>() {
        @Contract(pure = true)
        @Override
        public TextRange apply(PsiElement elem) {
            return elem.getTextRange();
        }
    };

    private RsPsiUtil() {
    }

    @Nullable
    @Contract(value = "null -> null", pure = true)
    public static String getHumanReadableName(@Nullable final IElementType tokenType) {
        if (tokenType == null) return null;
        if (tokenType instanceof IRsElementType) {
            return ((IRsElementType) tokenType).getHumanReadableName();
        }
        return tokenType.toString(); // fallback to debug name
    }

    @Nullable
    @Contract(value = "null, _ -> null", pure = true)
    public static String getHumanReadableName(@Nullable final IElementType tokenType, final int n) {
        if (tokenType == null) return null;
        final String name = getHumanReadableName(tokenType);
        if (Strings.isNullOrEmpty(name)) return null;
        return StringUtil.pluralize(name, n);
    }

    @NotNull
    public static String getPsiClassName(@NotNull final Object element) {
        return StringUtil.trimEnd(element.getClass().getSimpleName(), "Impl");
    }
}

