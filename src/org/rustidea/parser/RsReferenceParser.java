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
import com.intellij.psi.tree.TokenSet;
import org.jetbrains.annotations.NotNull;
import org.rustidea.parser.RsParserUtil.*;
import org.rustidea.util.UnreachableException;

import java.util.EnumSet;

import static com.intellij.lang.PsiBuilderUtil.expect;
import static org.rustidea.parser.RsParserUtil.*;
import static org.rustidea.psi.types.RsCompositeTypes.*;
import static org.rustidea.psi.types.RsPsiTypes.SELF_OR_SUPER;
import static org.rustidea.psi.types.RsTokenTypes.*;

class RsReferenceParser extends IRsParserBase {
    private static final Logger LOG = Logger.getInstance(RsReferenceParser.class);
    private static final TokenSet IDENTIFIER_OR_SELF = TokenSet.create(IDENTIFIER, KW_SELF);
    private static final TokenSet IDENTIFIER_OR_SELF_T = TokenSet.create(IDENTIFIER, KW_SELF_T);
    private static final TokenSet PATH_FIRST_TOKEN = TokenSet.create(KW_SELF, KW_SUPER, IDENTIFIER, OP_DOUBLE_COLON, KW_SELF_T);

    public RsReferenceParser(@NotNull final RsParser parser) {
        super(parser);
    }

    public boolean path() {
        // Paths/references are represented recursively as [qualifier]::referenceName. The parsing algorithm builds
        // this structure iteratively using PsiBuilder.Marker#precede() method. Here is a visualisation of
        // how markers are placed during consecutive parsing loop iterations:
        // 1)        (a::b::c::d)
        // 2)      ((a::b::c)::d)
        // 3)    (((a::b)::c)::d)
        // 4)  ((((a)::b)::c)::d)
        // We also have to treat specially `Self` keyword and convert it to identifier.

        Marker beginMarker = builder.mark();

        if (!PATH_FIRST_TOKEN.contains(builder.getTokenType())) {
            beginMarker.rollbackTo();
            return false;
        }

        // Parsing loop expects :: at the beginning of the stream,
        // so we must handle first tokens of the path explicitly.
        if (IDENTIFIER_OR_SELF_T.contains(builder.getTokenType())) { // ident [ <...> ]?
            final Marker qualifierMarker = builder.mark();

            if (builder.getTokenType() == IDENTIFIER) {
                builder.advanceLexer();
            } else if (builder.getTokenType() == KW_SELF_T) {
                pathCollapseSelfT();
            } else {
                throw new UnreachableException(); // bug trap
            }

            pathFinishIdentRefElem(qualifierMarker);
        } else if (SELF_OR_SUPER.contains(builder.getTokenType())) { // self | super
            final Marker qualifierMarker = builder.mark();

            assert SELF_OR_SUPER.contains(builder.getTokenType());
            builder.advanceLexer();

            qualifierMarker.done(RELATION_REFERENCE_ELEMENT);
        } else if (builder.getTokenType() == OP_DOUBLE_COLON) { // ::
            builder.mark().done(RELATION_REFERENCE_ELEMENT);
        } else {
            throw new UnreachableException(); // bug trap
        }

        // Here is the parsing loop
        while (builder.getTokenType() == OP_DOUBLE_COLON) {
            final Marker qualifierMarker = beginMarker;
            beginMarker = qualifierMarker.precede();

            assert builder.getTokenType() == OP_DOUBLE_COLON;
            builder.advanceLexer();

            if (expect(builder, IDENTIFIER) || pathCollapseSelfT()) { // ident [ <...> ]?
                pathFinishIdentRefElem(qualifierMarker);
            } else if (expect(builder, OP_ASTERISK)) { // *
                qualifierMarker.done(GLOB_REFERENCE_ELEMENT);
                break;
            } else if (expect(builder, OP_LBRACE)) { // {ident, ident, ...}
                sep(builder, OP_COMMA, new ParserWrapper() {
                    @Override
                    public boolean parse() {
                        return expect(builder, IDENTIFIER_OR_SELF);
                    }
                }, EnumSet.of(SepCfg.TOLERATE_EMPTY));

                expectOrWarn(builder, OP_RBRACE);

                qualifierMarker.done(LIST_REFERENCE_ELEMENT);
                break;
            } else { // leave syntax error
                errorExpected(builder, IDENTIFIER);
                qualifierMarker.drop(); // don't rollback, otherwise the error message will be destroyed
            }
        }

        beginMarker.drop();
        return true;
    }

    private boolean pathCollapseSelfT() {
        if (builder.getTokenType() != KW_SELF_T) {
            return false;
        }

        final Marker marker = builder.mark();
        builder.advanceLexer();
        marker.collapse(IDENTIFIER);
        return true;
    }

    private void pathFinishIdentRefElem(Marker qualifierMarker) {
        if (parser.getTypeParser().typeList()) {
            qualifierMarker.done(TYPED_REFERENCE_ELEMENT);
        } else {
            qualifierMarker.done(REFERENCE_ELEMENT);
        }
    }
}
