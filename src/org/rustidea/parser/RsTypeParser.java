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
import com.intellij.lang.PsiBuilder.Marker;
import com.intellij.openapi.diagnostic.Logger;
import com.intellij.util.BooleanFunction;
import org.jetbrains.annotations.NotNull;
import org.rustidea.util.NotImplementedException;

import java.util.EnumSet;

import static com.intellij.lang.PsiBuilderUtil.expect;
import static org.rustidea.parser.RsParserUtil.*;
import static org.rustidea.psi.types.RsPsiTypes.*;

class RsTypeParser extends IRsParserBase {
    private static final Logger LOG = Logger.getInstance(RsTypeParser.class);

    public RsTypeParser(@NotNull final RsParser parser) {
        super(parser);
    }

    public boolean lifetime() {
        final Marker marker = builder.mark();
        if (expect(builder, LIFETIME_TOKEN)) {
            marker.done(LIFETIME);
            return true;
        } else {
            marker.rollbackTo();
            return false;
        }
    }

    public boolean lifetimeTypeParameter() {
        final Marker marker = builder.mark();
        if (lifetime()) {
            marker.done(LIFETIME_TYPE_PARAMETER);
            return true;
        } else {
            marker.rollbackTo();
            return false;
        }
    }

    public boolean typeParemeter() {
        final Marker marker = builder.mark();

        if (!identifier(builder)) {
            marker.rollbackTo();
            return false;
        }

        // TODO Implement type bounds

        marker.done(TYPE_PARAMETER);
        return true;
    }

    public boolean typeParameterList() {
        final Marker marker = builder.mark();

        if (!expect(builder, OP_LT)) {
            marker.rollbackTo();
            return false;
        }

        sep(builder, OP_COMMA, new BooleanFunction<PsiBuilder>() {
            @Override
            public boolean fun(PsiBuilder psiBuilder) {
                if (lifetimeTypeParameter() || typeParemeter()) {
                    return true;
                } else {
                    error(builder, "expected type parameter or lifetime");
                    return false;
                }
            }
        }, EnumSet.of(SepCfg.TOLERATE_EMPTY));

        expectOrWarn(builder, OP_GT);

        marker.done(TYPE_PARAMETER_LIST);
        return true;
    }

    public boolean type() {
        boolean result = false;
        result = result || tupleType();

        // TODO Implement the rest
        if (!result) throw new NotImplementedException();

        return result;
    }

    public boolean expectType() {
        final Marker marker = builder.mark();
        if (type()) {
            marker.drop();
            return true;
        } else {
            marker.rollbackTo();
            error(builder, "expected type");
            return false;
        }
    }

    public boolean typeList() {
        final Marker marker = builder.mark();

        if (!expect(builder, OP_LT)) {
            marker.rollbackTo();
            return false;
        }

        sep(builder, OP_COMMA, new BooleanFunction<PsiBuilder>() {
            @Override
            public boolean fun(PsiBuilder psiBuilder) {
                return expectType();
            }
        }, EnumSet.of(SepCfg.TOLERATE_EMPTY));

        expectOrWarn(builder, OP_GT);

        marker.done(TYPE_LIST);
        return true;
    }

    public boolean structureType() {
        final Marker marker = builder.mark();

        if (!expect(builder, OP_LBRACE)) {
            marker.rollbackTo();
            return false;
        }

        sep(builder, OP_COMMA, new BooleanFunction<PsiBuilder>() {
            @Override
            public boolean fun(@NotNull PsiBuilder builder) {
                if (structField()) {
                    return true;
                } else {
                    errorExpected(builder, STRUCT_FIELD);
                    return false;
                }
            }
        }, EnumSet.of(SepCfg.ALLOW_TRAILING, SepCfg.TOLERATE_EMPTY));

        expectOrWarn(builder, OP_RBRACE);

        marker.done(STRUCT_TYPE);
        return true;
    }

    public boolean tupleType() {
        final Marker marker = builder.mark();

        if (!expect(builder, OP_LPAREN)) {
            marker.rollbackTo();
            return false;
        }

        // Check if we have unit type
        if (expect(builder, OP_RPAREN)) {
            marker.done(UNIT_TYPE);
            return true;
        }

        sep(builder, OP_COMMA, new BooleanFunction<PsiBuilder>() {
            @Override
            public boolean fun(PsiBuilder builder) {
                return expectType();
            }
        }, EnumSet.of(SepCfg.TOLERATE_EMPTY));

        expectOrWarn(builder, OP_RPAREN);

        marker.done(TUPLE_TYPE);
        return true;
    }

    public boolean structField() {
        final Marker marker = builder.mark();

        if (!expect(builder, IDENTIFIER)) {
            marker.rollbackTo();
            return false;
        }

        expectOrWarn(builder, OP_COLON);

        expectType();

        marker.done(STRUCT_FIELD);
        return true;
    }
}
