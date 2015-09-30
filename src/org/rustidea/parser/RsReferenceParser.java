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
import com.intellij.psi.tree.TokenSet;
import com.intellij.util.BooleanFunction;
import org.jetbrains.annotations.NotNull;

import static com.intellij.lang.PsiBuilderUtil.expect;
import static org.rustidea.parser.RsParserUtil.*;
import static org.rustidea.psi.types.RsCompositeTypes.PATH_RELATION;
import static org.rustidea.psi.types.RsStubElementTypes.PATH;
import static org.rustidea.psi.types.RsStubElementTypes.PATH_COMPONENT;
import static org.rustidea.psi.types.RsTokenTypes.*;

public class RsReferenceParser {
    private static final TokenSet SELF_OR_SUPER = TokenSet.create(KW_SELF, KW_SUPER);

    @NotNull
    private final RsParser parser;
    private final PsiBuilder builder;

    public RsReferenceParser(@NotNull final RsParser parser) {
        this.parser = parser;
        this.builder = parser.getBuilder();
    }

    public boolean path() {
        final Marker marker = builder.mark();

        final Marker relationMarker = builder.mark();
        final boolean hasSelfOrSuper = expect(builder, SELF_OR_SUPER);
        if (!hasSelfOrSuper) {
            expect(builder, OP_DOUBLE_COLON);
        }
        relationMarker.done(PATH_RELATION);

        if (hasSelfOrSuper) {
            expectOrWarnMissing(builder, OP_DOUBLE_COLON);
        }

        sep(builder, OP_DOUBLE_COLON, PathComponentParser.INSTANCE);

        marker.done(PATH);
        return true;
    }

    private enum PathComponentParser implements BooleanFunction<PsiBuilder> {
        INSTANCE;

        @Override
        public boolean fun(PsiBuilder builder) {
            return identifier(builder, PATH_COMPONENT);
        }
    }
}
