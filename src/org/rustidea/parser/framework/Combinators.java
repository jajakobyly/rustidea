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
import com.intellij.psi.tree.IElementType;
import com.intellij.psi.tree.TokenSet;
import org.jetbrains.annotations.NotNull;

/**
 * Various elementary functions which create more advanced parsers through composition.
 */
public final class Combinators {
    private Combinators() {
    }

    /**
     * <pre>maybe(p) ::= p?</pre>
     * <p>For tokens use {@link Scanners#maybeToken}</p>
     */
    @NotNull
    public static Parser maybe(@NotNull final Parser p) {
        return new WrapperParser(p) {
            @Override
            public boolean parse(@NotNull PsiBuilder builder) {
                Section.wrap(builder, parser);
                return true;
            }
        };
    }

    /**
     * <pre>seq(ps...) ::= ps[0] ps[1] ...</pre>
     *
     * <p>For tokens use {@link Scanners#token(IElementType...)}.</p>
     */
    @NotNull
    public static Parser seq(@NotNull final Parser... ps) {
        return new SequenceParser(ps);
    }

    /**
     * <pre>or(ps...) ::= ps[0] | ps[1] | ...</pre>
     *
     * <p>For tokens use {@link Scanners#token(TokenSet)}.</p>
     */
    @NotNull
    public static Parser or(@NotNull final Parser... ps) {
        return new AlternativeParser(ps);
    }

    /**
     * <pre>many(p) ::= p*</pre>
     *
     * @see #manyGreedy(Parser)
     */
    @NotNull
    public static Parser many(@NotNull final Parser p) {
        return new WrapperParser(p) {
            @Override
            public boolean parse(@NotNull PsiBuilder builder) {
                //noinspection StatementWithEmptyBody
                while (!builder.eof() && Section.wrap(builder, parser)) ;
                return true;
            }
        };
    }

    /**
     * <pre>many1(p) ::= p+</pre>
     */
    @NotNull
    public static Parser many1(@NotNull final Parser p) {
        return new WrapperParser(p) {
            @Override
            public boolean parse(@NotNull PsiBuilder builder) {
                // p+ ::= p p*
                Section section = Section.begin(builder);
                if (section.call(parser)) {
                    //noinspection StatementWithEmptyBody
                    while (!builder.eof() && Section.wrap(builder, parser)) ;
                }
                return section.end();
            }
        };
    }

    /**
     * <pre>manyGreedy(p) ::= &lt;&lt;garbage&gt;&gt; (p &lt;&lt;garbage&gt;&gt;)*</pre>
     *
     * @see #many(Parser)
     */
    @NotNull
    public static Parser manyGreedy(@NotNull final Parser p) {
        return new WrapperParser(p) {
            @Override
            public boolean parse(@NotNull PsiBuilder builder) {
                while (!builder.eof()) {
                    if (!parser.parse(builder)) {
                        PsiBuilderUtilEx.unexpected(builder);
                    }
                }
                return true;
            }
        };
    }

    static class SequenceParser extends CombiningParser {
        public SequenceParser(Parser... parsers) {
            super(parsers);
        }

        @Override
        public boolean parse(@NotNull PsiBuilder builder) {
            Section section = Section.begin(builder);
            for (Parser parser : parsers) {
                if (!section.call(parser)) break;
            }
            return section.end();
        }
    }

    static class AlternativeParser extends CombiningParser {
        public AlternativeParser(Parser... parsers) {
            super(parsers);
        }

        @Override
        public boolean parse(@NotNull PsiBuilder builder) {
            for (Parser parser : parsers) {
                if (Section.wrap(builder, parser)) return true;
            }
            return false;
        }
    }
}
