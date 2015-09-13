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

import com.google.common.base.Strings;
import com.intellij.lang.PsiBuilder;
import com.intellij.lang.PsiBuilder.Marker;
import com.intellij.psi.tree.IElementType;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

// TODO Write some JavaDoc
public class Section {
    private final Marker marker;
    public boolean result;

    private Section(boolean result, Marker marker) {
        this.result = result;
        this.marker = marker;
    }

    @NotNull
    public static Section begin(@NotNull final PsiBuilder builder) {
        return begin(builder, true);
    }

    @NotNull
    public static Section begin(@NotNull final PsiBuilder builder, boolean result) {
        return new Section(result, builder.mark());
    }

    public boolean end(final boolean rollback, @Nullable final IElementType type, @Nullable final String errorMessage) {
        return end(result, rollback, type, errorMessage);
    }

    public boolean end(final boolean result, final boolean rollback, @Nullable final IElementType type, @Nullable final String errorMessage) {
        if (this.result) {
            if (type != null) {
                marker.done(type);
            } else {
                marker.drop();
            }
        } else {
            if (!Strings.isNullOrEmpty(errorMessage)) {
                marker.error(errorMessage);
            } else if (rollback) {
                marker.rollbackTo();
            } else {
                marker.drop();
            }
        }
        return result;
    }
}
