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
import com.intellij.lang.PsiBuilder.Marker;
import com.intellij.psi.tree.IElementType;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

// TODO Write some JavaDoc
public class Section {
    @NotNull
    private final ParserContext ctx;
    @Nullable
    private Marker marker;
    private boolean state;

    private Section(@NotNull final ParserContext ctx,
                    @SuppressWarnings("NullableProblems") @NotNull final Marker marker,
                    final boolean initialState) {
        this.ctx = ctx;
        this.marker = marker;
        this.setState(initialState);
    }

    public static boolean wrap(@NotNull final ParserContext ctx, @NotNull final Parser parser) {
        Section section = begin(ctx);
        section.forceCall(parser);
        return section.end();
    }

    @NotNull
    public static Section begin(@NotNull final ParserContext ctx) {
        return begin(ctx, true);
    }

    @NotNull
    public static Section begin(@NotNull final ParserContext ctx, final boolean initialResult) {
        return new Section(ctx, ctx.getBuilder().mark(), initialResult);
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

    private boolean doEnd(final boolean rollback,
                          @Nullable final IElementType type,
                          @Nullable final String errorMessage) {
        if (marker == null) {
            throw new UnsupportedOperationException("Trying to end section multiple times.");
        }

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

        marker = null;
        return getState();
    }

    public boolean call(@NotNull final Parser parser) {
        if (getState()) {
            forceCall(parser);
        }
        return getState();
    }

    public boolean forceCall(@NotNull final Parser parser) {
        setState(parser.parse(ctx));
        return getState();
    }

    public boolean callWrapped(@NotNull final Parser parser) {
        if (getState()) {
            setState(wrap(ctx, parser));
        }
        return getState();
    }
}
