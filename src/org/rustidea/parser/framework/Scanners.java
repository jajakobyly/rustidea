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
import org.rustidea.psi.util.RsTokenUtil;

/**
 * The most basic parsers which match tokens.
 */
public final class Scanners {
    private Scanners() {
    }

    /** {@code token(TOKEN) ::= TOKEN} */
    @NotNull
    public static Parser token(@NotNull final IElementType token) {
        return new Parser() {
            @Override
            public boolean parse(@NotNull ParserContext ctx) {
                return PsiBuilderUtil.expect(ctx.getBuilder(), token);
            }
        };
    }

    /** {@code token(TOKENS) ::= TOKENS[0] | TOKENS[1] | ...} */
    @NotNull
    public static Parser token(@NotNull final TokenSet tokens) {
        return new Parser() {
            @Override
            public boolean parse(@NotNull ParserContext ctx) {
                return PsiBuilderUtil.expect(ctx.getBuilder(), tokens);
            }
        };
    }

    /** {@code token(TOKENS...) ::= TOKENS[0] TOKENS[1] ...} */
    @NotNull
    public static Parser token(@NotNull final IElementType... tokens) {
        return new Parser() {
            @Override
            public boolean parse(@NotNull ParserContext ctx) {
                Section section = Section.begin(ctx);
                for (IElementType token : tokens) {
                    if (!PsiBuilderUtil.expect(ctx.getBuilder(), token)) {
                        section.setState(false);
                        break;
                    }
                }
                return section.end();
            }
        };
    }

    /** {@code notToken(TOKEN) ::= !TOKEN} */
    @NotNull
    public static Parser notToken(@NotNull final IElementType token) {
        return new Parser() {
            @Override
            public boolean parse(@NotNull ParserContext ctx) {
                return PsiBuilderUtilEx.expectNot(ctx.getBuilder(), token);
            }
        };
    }

    /** {@code notToken(TOKENS) ::= !( TOKENS[0] | TOKENS[1] | ... )} */
    @NotNull
    public static Parser notToken(@NotNull final TokenSet tokens) {
        return new Parser() {
            @Override
            public boolean parse(@NotNull ParserContext ctx) {
                return PsiBuilderUtilEx.expectNot(ctx.getBuilder(), tokens);
            }
        };
    }

    /** {@code maybeToken(TOKEN) ::= TOKEN?} */
    @NotNull
    public static Parser maybeToken(@NotNull final IElementType token) {
        return new Parser() {
            @Override
            public boolean parse(@NotNull ParserContext ctx) {
                return PsiBuilderUtilEx.maybe(ctx.getBuilder(), token);
            }
        };
    }

    /** {@code maybeToken(TOKENS) ::= [ TOKENS[0] | TOKENS[1] | ... ]} */
    @NotNull
    public static Parser maybeToken(@NotNull final TokenSet tokens) {
        return new Parser() {
            @Override
            public boolean parse(@NotNull ParserContext ctx) {
                return PsiBuilderUtilEx.maybe(ctx.getBuilder(), tokens);
            }
        };
    }

    /** <pre>tokenFail(TOKEN) = {@link #tokenFail(IElementType, String)}(TOKEN, ...)</pre> */
    @NotNull
    public static Parser tokenFail(@NotNull final IElementType token) {
        return tokenFail(token, "expected " + RsTokenUtil.getHumanReadableName(token));
    }

    /** {@code tokenFail(TOKEN, msg) ::= TOKEN} **/
    @NotNull
    public static Parser tokenFail(@NotNull final IElementType token, @NotNull final String errorMessage) {
        return new Parser() {
            @Override
            public boolean parse(@NotNull ParserContext ctx) {
                PsiBuilder.Marker marker = ctx.getBuilder().mark();
                if (PsiBuilderUtil.expect(ctx.getBuilder(), token)) {
                    marker.drop();
                    return true;
                } else {
                    marker.error(errorMessage);
                    return false;
                }
            }
        };
    }

    /** <pre>tokenWarn(TOKEN) = {@link #tokenWarn(IElementType, String)}(TOKEN, ...)</pre> */
    @NotNull
    public static Parser tokenWarn(@NotNull final IElementType token) {
        return tokenWarn(token, "expected " + RsTokenUtil.getHumanReadableName(token));
    }

    /** {@code tokenWarn(TOKEN, msg) ::= TOKEN} **/
    @NotNull
    public static Parser tokenWarn(@NotNull final IElementType token, @NotNull final String errorMessage) {
        return new Parser() {
            @Override
            public boolean parse(@NotNull ParserContext ctx) {
                PsiBuilder.Marker marker = ctx.getBuilder().mark();
                if (PsiBuilderUtil.expect(ctx.getBuilder(), token)) {
                    marker.drop();
                } else {
                    marker.error(errorMessage);
                }
                return true;
            }
        };
    }
}
