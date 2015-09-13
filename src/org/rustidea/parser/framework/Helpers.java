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
import com.intellij.lang.PsiBuilderUtil;
import com.intellij.psi.tree.IElementType;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.lang.reflect.Field;

/**
 * Various common complex rules which could be made by combining primitives.
 */
public final class Helpers {
    private Helpers() {
    }

    /**
     * Create parser which lazily loads other parser and calls it. Useful for dealing with circular dependencies.
     *
     * @param container  class which contains given parser
     * @param parserName name of container's field in which the parser is stored
     * @return parser which lazily loads specified parser and calls it
     */
    @NotNull
    public static Parser lazy(@NotNull final Class<?> container, @NotNull final String parserName) {
        return new Parser() {
            @Nullable
            private Parser parser = null;

            @Override
            public boolean parse(@NotNull PsiBuilder builder) {
                if (parser == null) {
                    parser = initParser();
                }
                return parser.parse(builder);
            }

            @NotNull
            private Parser initParser() {
                String fieldName = container.getCanonicalName() + "#" + parserName;
                try {
                    Field parserField = container.getDeclaredField(parserName);

                    if (!Parser.class.isAssignableFrom(parserField.getType())) {
                        throw new RuntimeException(fieldName + " is not a parser");
                    }

                    return (Parser) parserField.get(null);
                } catch (NoSuchFieldException e) {
                    throw new RuntimeException(fieldName + " does not exist", e);
                } catch (IllegalAccessException e) {
                    throw new RuntimeException(fieldName + " is inaccessible", e);
                }
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
