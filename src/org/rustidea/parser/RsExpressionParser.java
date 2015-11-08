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
import org.jetbrains.annotations.NotNull;
import org.rustidea.util.NotImplementedException;

import static com.intellij.lang.PsiBuilderUtil.expect;
import static org.rustidea.parser.RsParserUtil.error;
import static org.rustidea.parser.RsParserUtil.errorExpected;
import static org.rustidea.psi.types.RsCompositeTypes.LITERAL;
import static org.rustidea.psi.types.RsPsiTypes.LITERAL_TOKEN_SET;

class RsExpressionParser extends IRsParserBase {
    private static final Logger LOG = Logger.getInstance(RsExpressionParser.class);

    public RsExpressionParser(@NotNull final RsParser parser) {
        super(parser);
    }

    public boolean expression() {
        // TODO Implement this.
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
        final Marker marker = builder.mark();
        if (expect(builder, LITERAL_TOKEN_SET)) {
            marker.done(LITERAL);
            return true;
        } else {
            marker.drop();
            errorExpected(builder, LITERAL);
            return false;
        }
    }
}
