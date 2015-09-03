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

import com.intellij.lang.ASTNode;
import com.intellij.lang.LightPsiParser;
import com.intellij.lang.PsiBuilder;
import com.intellij.lang.PsiBuilder.Marker;
import com.intellij.lang.PsiParser;
import com.intellij.psi.tree.IElementType;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import static org.rustidea.parser.RsParserUtil.eatUnless;
import static org.rustidea.parser.RsParserUtil.tryParseToken;
import static org.rustidea.psi.types.RsTypes.PARENT_DOC;
import static org.rustidea.psi.types.RsTypes.PARENT_DOC_TOKEN_SET;

public class RsParser implements PsiParser, LightPsiParser {
    public static void file(IElementType root, @NotNull PsiBuilder builder) {
        Marker mRoot = builder.mark();
        while (!builder.eof()) {
            if (item(builder) == null) {
                eatUnless(builder, PARENT_DOC_TOKEN_SET).error("expected item");
            }
        }
        mRoot.done(root);
    }

    @Nullable
    private static Marker item(@NotNull PsiBuilder builder) {
        return parentDoc(builder);
    }

    @Nullable
    private static Marker parentDoc(@NotNull PsiBuilder builder) {
        return tryParseToken(builder, PARENT_DOC, PARENT_DOC_TOKEN_SET);
    }

    @Override
    @NotNull
    public ASTNode parse(@NotNull IElementType root, @NotNull PsiBuilder builder) {
        parseLight(root, builder);
        return builder.getTreeBuilt();
    }

    @Override
    public void parseLight(IElementType root, @NotNull PsiBuilder builder) {
        file(root, builder);
    }
}
