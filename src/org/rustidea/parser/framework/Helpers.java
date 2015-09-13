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

import com.google.common.base.Supplier;
import com.google.common.base.Suppliers;
import com.intellij.lang.PsiBuilder;
import com.intellij.lang.PsiBuilderUtil;
import com.intellij.psi.tree.IElementType;
import org.jetbrains.annotations.NotNull;

/**
 * Various common complex rules which could be made by combining primitives.
 */
public final class Helpers {
    private Helpers() {
    }

    /**
     * Create parser which lazily loads other parser and calls it. Useful for dealing with circular dependencies.
     *
     * @param supplier parser supplier
     * @return parser which lazily loads specified parser and calls it
     */
    @NotNull
    public static Parser lazy(@NotNull final Supplier<Parser> supplier) {
        final Supplier<Parser> mem = Suppliers.memoize(supplier);
        return new Parser() {
            @Override
            public boolean parse(@NotNull PsiBuilder builder) {
                return mem.get().parse(builder);
            }
        };
    }

    /**
     * <pre>wrap(OPEN, CLOSE, p) ::= OPEN p CLOSE</pre>
     */
    @NotNull
    public static Parser wrap(@NotNull final IElementType open,
                              @NotNull final IElementType close,
                              @NotNull final Parser p) {
        return new WrapperParser(p) {
            @Override
            public boolean parse(@NotNull PsiBuilder builder) {
                Section section = Section.begin(builder);
                section.result = PsiBuilderUtil.expect(builder, open);
                section.result = section.result && parser.parse(builder);
                PsiBuilderUtilEx.expectOrWarn(
                    builder, close, "missing " + close);
                return section.end(true, null, null);
            }
        };
    }

    /**
     * <pre>sep(SEPARATOR, p) ::= [ p (SEPARATOR p)* ]</pre>
     * <p>Marks trailing separator as unexpected token.</p>
     */
    @NotNull
    public static Parser sep(@NotNull final IElementType separator, @NotNull final Parser p) {
        return new WrapperParser(p) {
            @Override
            public boolean parse(@NotNull PsiBuilder builder) {
                Section section = Section.begin(builder);

                if (parser.parse(builder)) {
                    while (!builder.eof()) {
                        Section section1 = Section.begin(builder);
                        PsiBuilderUtilEx.expectOrWarn(
                            builder, separator, "missing " + separator.toString());
                        section1.result = parser.parse(builder);
                        if (!section1.end(true, null, null)) {
                            break;
                        }
                    }

                    // Check for trailing separator
                    if (!builder.eof() && builder.getTokenType() == separator) {
                        PsiBuilderUtilEx.unexpected(builder);
                    }
                }

                return section.end(true, true, null, null);
            }
        };
    }
}
