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

import com.intellij.lang.PsiBuilder.Marker;
import com.intellij.openapi.diagnostic.Logger;
import com.intellij.psi.tree.IElementType;
import com.intellij.psi.tree.TokenSet;
import org.jetbrains.annotations.NotNull;
import org.rustidea.util.NotImplementedException;

import static com.intellij.lang.PsiBuilderUtil.expect;
import static org.rustidea.parser.RsParserUtil.error;
import static org.rustidea.parser.RsParserUtil.errorExpected;
import static org.rustidea.psi.types.RsCompositeTypes.LITERAL;
import static org.rustidea.psi.types.RsPsiTypes.LITERAL_TOKEN_SET;
import static org.rustidea.psi.types.RsTokenTypes.INT_LIT;
import static org.rustidea.psi.types.RsTokenTypes.STRING_LIT;

class RsExpressionParser extends IRsParserBase {
    private static final Logger LOG = Logger.getInstance(RsExpressionParser.class);
    private static final TokenSet INT_LIT_TOKEN_SET = TokenSet.create(INT_LIT);
    private static final TokenSet STRING_LIT_TOKEN_SET = TokenSet.create(STRING_LIT);

    public RsExpressionParser(@NotNull final RsParser parser) {
        super(parser);
    }

    public boolean expression() {
        // TODO:RJP-12 Implement this.
        throw new NotImplementedException();
    }

    public boolean expectExpression() {
        final Marker marker = builder.mark();
        if (expression()) {
            marker.drop();
            return true;
        } else {
            marker.rollbackTo();
            error(builder, "expected expression");
            return false;
        }
    }

    public boolean literal() {
        return literal(LITERAL_TOKEN_SET, LITERAL);
    }

    public boolean integer() {
        return literal(INT_LIT_TOKEN_SET, LITERAL);
    }

    public boolean string() {
        return literal(STRING_LIT_TOKEN_SET, LITERAL);
    }

    public boolean expectLiteral() {
        return expectLiteral(LITERAL_TOKEN_SET, LITERAL, LITERAL);
    }

    public boolean expectInteger() {
        return expectLiteral(INT_LIT_TOKEN_SET, LITERAL, INT_LIT);
    }

    public boolean expectString() {
        return expectLiteral(STRING_LIT_TOKEN_SET, LITERAL, STRING_LIT);
    }

    private boolean literal(@NotNull final TokenSet tokenSet,
                            @NotNull final IElementType elementType) {
        final Marker marker = builder.mark();
        if (expect(builder, tokenSet)) {
            marker.done(elementType);
            return true;
        } else {
            marker.rollbackTo();
            return false;
        }
    }

    private boolean expectLiteral(@NotNull final TokenSet tokenSet,
                                  @NotNull final IElementType elementType,
                                  @NotNull final IElementType errorType) {
        final Marker marker = builder.mark();
        if (expect(builder, tokenSet)) {
            marker.done(elementType);
            return true;
        } else {
            marker.drop();
            errorExpected(builder, errorType);
            return false;
        }
    }
}
