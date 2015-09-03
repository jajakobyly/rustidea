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

import com.intellij.lang.ASTFactory;
import com.intellij.lang.DefaultASTFactory;
import com.intellij.lang.LanguageParserDefinitions;
import com.intellij.lang.ParserDefinition;
import com.intellij.openapi.components.ServiceManager;
import com.intellij.psi.impl.source.tree.LeafElement;
import com.intellij.psi.tree.IElementType;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.rustidea.RustLanguage;
import org.rustidea.psi.types.IRsIdentifierType;
import org.rustidea.psi.types.IRsKeywordType;
import org.rustidea.psi.types.IRsTokenType;

public class RsASTFactory extends ASTFactory {
    private static final ParserDefinition rustParserDef = LanguageParserDefinitions.INSTANCE.forLanguage(RustLanguage.INSTANCE);
    private static final DefaultASTFactory defaultASTFactory = ServiceManager.getService(DefaultASTFactory.class);

    @Nullable
    @Override
    public LeafElement createLeaf(@NotNull final IElementType type, final CharSequence text) {
        if (rustParserDef.getCommentTokens().contains(type)) {
            return defaultASTFactory.createComment(type, text);
        }
        if (type instanceof IRsIdentifierType) return new RsIdentifierImpl(type, text);
        if (type instanceof IRsKeywordType) return new RsKeywordImpl(type, text);
        if (type instanceof IRsTokenType) return new RsTokenImpl(type, text);

        return null;
    }
}