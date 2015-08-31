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

package org.rustidea.psi.impl;

import com.intellij.lang.DefaultASTFactoryImpl;
import com.intellij.lang.LanguageParserDefinitions;
import com.intellij.lang.ParserDefinition;
import com.intellij.psi.impl.source.tree.LeafElement;
import com.intellij.psi.tree.IElementType;
import org.jetbrains.annotations.NotNull;
import org.rustidea.RustLanguage;
import org.rustidea.psi.types.IRustIdentifierType;
import org.rustidea.psi.types.IRustKeywordType;
import org.rustidea.psi.types.IRustTokenType;
import org.rustidea.psi.types.RustType;

public class RustASTFactory extends DefaultASTFactoryImpl {
    @NotNull
    @Override
    public LeafElement createLeaf(@NotNull IElementType type, CharSequence text) {
        final ParserDefinition pd = LanguageParserDefinitions.INSTANCE.forLanguage(RustLanguage.INSTANCE);

        if (pd.getCommentTokens().contains(type)) return createComment(type, text);
        if (type instanceof IRustIdentifierType) return createIdentifier(type, text);
        if (type instanceof IRustKeywordType) return new RustKeywordImpl(type, text);
        if (type instanceof IRustTokenType) return new RustTokenImpl(type, text);

        return super.createLeaf(type, text);
    }

    @NotNull
    public LeafElement createIdentifier(@NotNull IElementType type, CharSequence text) {
        if (type == RustType.LIFETIME) return new RustLifetimeImpl(type, text);
        return new RustIdentifierImpl(type, text);
    }
}
