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
import com.intellij.psi.tree.IElementType;
import com.intellij.psi.tree.TokenSet;
import org.jetbrains.annotations.NotNull;
import org.rustidea.psi.util.RsPsiUtil;
import org.rustidea.util.UnreachableException;

import static com.intellij.lang.PsiBuilderUtil.expect;
import static org.rustidea.parser.RsParserUtil.error;
import static org.rustidea.psi.types.RsCompositeTypes.REFERENCE_ELEMENT;
import static org.rustidea.psi.types.RsCompositeTypes.RELATION_REFERENCE_ELEMENT;
import static org.rustidea.psi.types.RsTokenTypes.IDENTIFIER;
import static org.rustidea.psi.types.RsTokenTypes.OP_DOUBLE_COLON;
import static org.rustidea.psi.types.RsTypes.SELF_OR_SUPER;

public class RsReferenceParser {
    private static final TokenSet PATH_FIRST_TOKEN = TokenSet.orSet(SELF_OR_SUPER, TokenSet.create(IDENTIFIER, OP_DOUBLE_COLON));

    @NotNull
    private final RsParser parser;
    private final PsiBuilder builder;

    public RsReferenceParser(@NotNull final RsParser parser) {
        this.parser = parser;
        this.builder = parser.getBuilder();
    }

    // TODO Maybe rename to `reference` to keep consistent with PSI classes?
    public boolean path() {
        // I         a::b::c::d
        // II      (a::b::c)::d
        // III   ((a::b)::c)::d
        // IV  (((a)::b)::c)::d

        Marker beginMarker = builder.mark();

        if (PATH_FIRST_TOKEN.contains(builder.getTokenType())) {
            if (builder.getTokenType() == IDENTIFIER) {
                final Marker qualifierMarker = builder.mark();

                assert builder.getTokenType() == IDENTIFIER;
                builder.advanceLexer();

                qualifierMarker.done(REFERENCE_ELEMENT);
            } else if (SELF_OR_SUPER.contains(builder.getTokenType())) {
                final Marker qualifierMarker = builder.mark();

                assert SELF_OR_SUPER.contains(builder.getTokenType());
                builder.advanceLexer();

                qualifierMarker.done(RELATION_REFERENCE_ELEMENT);
            } else if (builder.getTokenType() == OP_DOUBLE_COLON) {
                builder.mark().done(RELATION_REFERENCE_ELEMENT);
            } else {
                throw new UnreachableException();
            }

            while (builder.getTokenType() == OP_DOUBLE_COLON) {
                final Marker qualifierMarker = beginMarker;
                beginMarker = qualifierMarker.precede();

                assert builder.getTokenType() == OP_DOUBLE_COLON;
                builder.advanceLexer();

                if (expect(builder, IDENTIFIER)) {
                    qualifierMarker.done(REFERENCE_ELEMENT);
                } else if (SELF_OR_SUPER.contains(builder.getTokenType())) {
                    // Leave error if we encounter `self` or `super` in the middle of the path.
                    // Beware that RsRelationReferenceElement does not take into account qualifiers
                    // in its methods.
                    final IElementType token = builder.getTokenType();
                    final Marker errorMarker = builder.mark();

                    assert SELF_OR_SUPER.contains(builder.getTokenType());
                    builder.advanceLexer();

                    errorMarker.error("expected identifier, not " + RsPsiUtil.getHumanReadableName(token));
                    qualifierMarker.done(RELATION_REFERENCE_ELEMENT);
                } else {
                    error(builder, "expected identifier");
                    qualifierMarker.drop(); // don't rollback in order to leave error message
                }
            }

            beginMarker.drop();
            return true;
        } else {
            beginMarker.rollbackTo();
            return false;
        }
    }
}
