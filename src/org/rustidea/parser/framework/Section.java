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
    @NotNull
    private final PsiBuilder builder;
    @NotNull
    private final Marker marker;
    private boolean state;

    private Section(@NotNull final PsiBuilder builder,
                    @NotNull final Marker marker,
                    final boolean initialState) {
        this.builder = builder;
        this.marker = marker;
        this.setState(initialState);
    }

    public static boolean wrap(@NotNull final PsiBuilder builder, @NotNull final Parser parser) {
        Section section = begin(builder);
        section.forceCall(parser);
        return section.end();
    }

    @NotNull
    public static Section begin(@NotNull final PsiBuilder builder) {
        return begin(builder, true);
    }

    @NotNull
    public static Section begin(@NotNull PsiBuilder builder, boolean initialResult) {
        return new Section(builder, builder.mark(), initialResult);
    }

    public boolean getState() {
        return state;
    }

    public void setState(boolean result) {
        this.state = result;
    }

    public boolean end() {
        return end(null, null);
    }

    public boolean end(@Nullable final IElementType type, @Nullable final String errorMessage) {
        return doEnd(true, type, errorMessage);
    }

    public boolean endGreedy() {
        return endGreedy(null, null);
    }

    public boolean endGreedy(@Nullable final IElementType type, @Nullable final String errorMessage) {
        return doEnd(false, type, errorMessage);
    }

    private boolean doEnd(boolean rollback,
                          @Nullable IElementType type,
                          @Nullable String errorMessage) {
        if (getState()) {
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
        return getState();
    }

    public boolean call(@NotNull final Parser parser) {
        if (getState()) {
            forceCall(parser);
        }
        return getState();
    }

    public boolean forceCall(@NotNull final Parser parser) {
        setState(parser.parse(builder));
        return getState();
    }

    public boolean callWrapped(@NotNull final Parser parser) {
        if (getState()) {
            setState(wrap(builder, parser));
        }
        return getState();
    }
}
