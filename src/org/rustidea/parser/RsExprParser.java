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

package org.rustidea.parser;

import com.intellij.lang.PsiBuilder;
import com.intellij.lang.PsiBuilderUtil;
import com.intellij.psi.tree.TokenSet;
import org.jetbrains.annotations.NotNull;
import org.rustidea.parser.framework.Parser;
import org.rustidea.parser.framework.ParserContext;
import org.rustidea.parser.framework.PsiBuilderUtilEx;
import org.rustidea.parser.framework.Section;
import org.rustidea.psi.types.RsTypes;

import static org.rustidea.parser.framework.Helpers.sep;
import static org.rustidea.parser.framework.Scanners.token;
import static org.rustidea.psi.types.RsCompositeTypes.LITERAL;
import static org.rustidea.psi.types.RsCompositeTypes.PATH_RELATION;
import static org.rustidea.psi.types.RsStubElementTypes.PATH;
import static org.rustidea.psi.types.RsStubElementTypes.PATH_COMPONENT;
import static org.rustidea.psi.types.RsTokenTypes.*;
import static org.rustidea.psi.types.RsTypes.LITERAL_TOKEN_SET;

public final class RsExprParser {
    /**
     * {@code literalRequired ::= "true" | "false" | BIN_LIT | DEC_LIT | FLOAT_LIT | HEX_LIT | OCT_LIT | BYTE_LIT | CHAR_LIT | BYTE_STRING_LIT | STRING_LIT | RAW_BYTE_STRING_LIT | RAW_STRING_LIT}
     *
     * <p>Fails if parsing failed.</p>
     *
     * @see #literal
     */
    public static final Parser literalRequired =
        token(LITERAL_TOKEN_SET).markGreedy(LITERAL).fail("expected literal");

    /**
     * {@code literal ::= "true" | "false" | BIN_LIT | DEC_LIT | FLOAT_LIT | HEX_LIT | OCT_LIT | BYTE_LIT | CHAR_LIT | BYTE_STRING_LIT | STRING_LIT | RAW_BYTE_STRING_LIT | RAW_STRING_LIT}
     *
     * <p>Warns if parsing fails.</p>
     *
     * @see #literalRequired
     */
    public static final Parser literal =
        token(LITERAL_TOKEN_SET).markGreedy(LITERAL).warn("expected literal");

    /**
     * {@code pathBase ::= [ [ "self" | "super" ] "::" ] IDENTIFIER ( "::" IDENTIFIER )*}
     *
     * <p>Base for more specialized path rules, does not produce {@link RsTypes#PATH} marker.</p>
     *
     * @see #path
     */
    public static final Parser pathBase = new Parser() {
        private final TokenSet SELF_OR_SUPER = TokenSet.create(KW_SELF, KW_SUPER);
        private final Parser components = sep(OP_DOUBLE_COLON, token(IDENTIFIER).mark(PATH_COMPONENT));

        @Override
        public boolean parse(@NotNull ParserContext ctx) {
            final PsiBuilder builder = ctx.getBuilder();
            final Section section = Section.begin(ctx);

            final Section relationSection = Section.begin(ctx);
            final boolean hasSelfOrSuper = PsiBuilderUtil.expect(builder, SELF_OR_SUPER);
            if (!hasSelfOrSuper) {
                PsiBuilderUtil.expect(builder, OP_DOUBLE_COLON);
            }
            relationSection.end(PATH_RELATION, null);

            if (hasSelfOrSuper) {
                PsiBuilderUtilEx.expectOrWarn(builder, OP_DOUBLE_COLON);
            }

            section.call(components);
            return section.end();
        }
    };

    /**
     * <pre>path ::= {@link #pathBase}</pre>
     *
     * <p>Wraps {@link #pathBase} and produces {@link RsTypes#PATH} marker.</p>
     *
     * @see #pathBase
     */
    public static final Parser path =
        pathBase.mark(PATH);

    private RsExprParser() {
    }
}
