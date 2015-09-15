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
import com.intellij.psi.tree.TokenSet;
import org.jetbrains.annotations.NotNull;

/**
 * The most basic parsers that match tokens.
 */
public final class Scanners {
    private Scanners() {
    }

    /**
     * {@code token(TOKEN) ::= TOKEN}
     */
    @NotNull
    public static Parser token(@NotNull final IElementType token) {
        return new Parser() {
            @Override
            public boolean parse(@NotNull PsiBuilder builder) {
                return PsiBuilderUtil.expect(builder, token);
            }
        };
    }

    /**
     * {@code token(TOKENS) ::= TOKENS[0] | TOKENS[1] | ...}
     */
    @NotNull
    public static Parser token(@NotNull final TokenSet tokens) {
        return new Parser() {
            @Override
            public boolean parse(@NotNull PsiBuilder builder) {
                return PsiBuilderUtil.expect(builder, tokens);
            }
        };
    }

    /**
     * {@code token(TOKENS...) ::= TOKENS[0] TOKENS[1] ...}
     */
    @NotNull
    public static Parser token(@NotNull final IElementType... tokens) {
        return new Parser() {
            @Override
            public boolean parse(@NotNull PsiBuilder builder) {
                Section section = Section.begin(builder);
                for (IElementType token : tokens) {
                    if (!PsiBuilderUtil.expect(builder, token)) {
                        section.setState(false);
                        break;
                    }
                }
                return section.end();
            }
        };
    }

    /**
     * {@code notToken(TOKEN) ::= !TOKEN}
     */
    @NotNull
    public static Parser notToken(@NotNull final IElementType token) {
        return new Parser() {
            @Override
            public boolean parse(@NotNull PsiBuilder builder) {
                return PsiBuilderUtilEx.expectNot(builder, token);
            }
        };
    }

    /**
     * {@code notToken(TOKENS) ::= !( TOKENS[0] | TOKENS[1] | ... )}
     */
    @NotNull
    public static Parser notToken(@NotNull final TokenSet tokens) {
        return new Parser() {
            @Override
            public boolean parse(@NotNull PsiBuilder builder) {
                return PsiBuilderUtilEx.expectNot(builder, tokens);
            }
        };
    }

    /**
     * {@code maybeToken(TOKEN) ::= TOKEN?}
     */
    @NotNull
    public static Parser maybeToken(@NotNull final IElementType token) {
        return new Parser() {
            @Override
            public boolean parse(@NotNull PsiBuilder builder) {
                return PsiBuilderUtilEx.maybe(builder, token);
            }
        };
    }

    /**
     * {@code maybeToken(TOKENS) ::= [ TOKENS[0] | TOKENS[1] | ... ]}
     */
    @NotNull
    public static Parser maybeToken(@NotNull final TokenSet tokens) {
        return new Parser() {
            @Override
            public boolean parse(@NotNull PsiBuilder builder) {
                return PsiBuilderUtilEx.maybe(builder, tokens);
            }
        };
    }
}
