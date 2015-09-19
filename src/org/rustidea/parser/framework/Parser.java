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

import com.intellij.psi.tree.IElementType;
import com.intellij.psi.tree.TokenSet;
import org.jetbrains.annotations.NotNull;
import org.rustidea.parser.framework.Combinators.AlternativeParser;
import org.rustidea.parser.framework.Combinators.SequenceParser;

/**
 * Basic interface for parser function.
 */
public abstract class Parser {
    /**
     * Perform parsing.
     *
     * @param ctx parser context
     * @return {@code true} if parsing succeeded; otherwise, {@code false}
     */
    public abstract boolean parse(@NotNull final ParserContext ctx);

    /**
     * {@code p.then(q) ::= p q}
     *
     * <p>This combinator should be used only if two parsers are combined,
     * otherwise use {@link Combinators#seq(Parser...)}. For tokens
     * use {@link Scanners#token(IElementType...)}.</p>
     */
    @NotNull
    public Parser then(@NotNull final Parser q) {
        return new SequenceParser(this, q);
    }

    /**
     * {@code p.evenThen(q) ::= p | q | p q}
     *
     * <p>Matches {@code p} or {@code q} or {@code p q} but does not match empty input.</p>
     *
     * <p><b>Beware of using parsers which can match empty input such as
     * {@link Combinators#many(Parser)} or {@link Scanners#maybeToken(IElementType)}!</b>
     * They will cause nothing to be matched successfully which may lead to infinite loops.</p>
     */
    // TODO Invent better name
    @NotNull
    public Parser evenThen(@NotNull final Parser q) {
        return new WrapperParser(this) {
            @Override
            public boolean parse(@NotNull ParserContext ctx) {
                Section section = Section.begin(ctx);
                if (section.callWrapped(parser)) {
                    if (section.callWrapped(q)) {
                        // matched `p q`
                        return section.end();
                    }

                    // matched `p`
                    section.endGreedy();
                    return true;
                }

                // matched `q`
                section.forceCall(q);
                return section.end();
            }
        };
    }

    /**
     * {@code p.or(q) ::= p | q}
     *
     * <p>This combinator should be used only if two parsers are combined, otherwise use {@link Combinators#or(Parser...)}. For tokens use {@link Scanners#token(TokenSet)}.</p>
     */
    @NotNull
    public Parser or(@NotNull final Parser q) {
        return new AlternativeParser(this, q);
    }

    /**
     * Create psi element for matched text or rollback if parsing failed.
     *
     * @param type type of psi element to create
     * @see #markGreedy(IElementType)
     */
    @NotNull
    public Parser mark(@NotNull final IElementType type) {
        return new WrapperParser(this) {
            @Override
            public boolean parse(@NotNull ParserContext ctx) {
                Section section = Section.begin(ctx);
                section.call(parser);
                return section.end(type, null);
            }
        };
    }

    /**
     * Create psi element for matched text.
     *
     * @param type type of psi element to create
     * @see #mark(IElementType)
     */
    @NotNull
    public Parser markGreedy(@NotNull final IElementType type) {
        return new WrapperParser(this) {
            @Override
            public boolean parse(@NotNull ParserContext ctx) {
                Section section = Section.begin(ctx);
                section.call(parser);
                return section.endGreedy(type, null);
            }
        };
    }

    /**
     * Produce error marker when parsing fails.
     *
     * @param errorMessage error marker message
     * @see #warn(String)
     */
    @NotNull
    public Parser fail(@NotNull final String errorMessage) {
        return new WrapperParser(this) {
            @Override
            public boolean parse(@NotNull ParserContext ctx) {
                Section section = Section.begin(ctx);
                section.forceCall(parser);
                return section.endGreedy(null, errorMessage);
            }
        };
    }

    /**
     * Silently produce error marker when parsing fails and continue.
     *
     * @param errorMessage error marker message
     * @see #fail(String)
     */
    @NotNull
    public Parser warn(@NotNull final String errorMessage) {
        return new WrapperParser(this) {
            @Override
            public boolean parse(@NotNull ParserContext ctx) {
                Section section = Section.begin(ctx);
                section.forceCall(parser);
                section.endGreedy(null, errorMessage);
                return true;
            }
        };
    }
}
