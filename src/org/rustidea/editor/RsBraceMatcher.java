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

package org.rustidea.editor;

import com.intellij.lang.BracePair;
import com.intellij.lang.PairedBraceMatcher;
import com.intellij.psi.PsiFile;
import com.intellij.psi.tree.IElementType;
import com.intellij.psi.tree.TokenSet;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.rustidea.psi.types.IRsElementType;

import static org.rustidea.psi.types.RsTypes.*;

public class RsBraceMatcher implements PairedBraceMatcher {
    private static final BracePair[] PAIRS = new BracePair[]{
        new BracePair(OP_LPAREN, OP_RPAREN, false),
        new BracePair(OP_LBRACKET, OP_RBRACKET, false),
        new BracePair(OP_LBRACE, OP_RBRACE, true)
        // TODO Match < & > in type parameters lists
    };

    private static final TokenSet TOKENS_BEFORE = TokenSet.orSet(
        WHITE_SPACE_TOKEN_SET,
        COMMENT_TOKEN_SET,
        BRACE_TOKEN_SET,
        TokenSet.create(OP_SEMICOLON, OP_COMMA, OP_RPAREN, OP_RBRACKET));

    @Override
    public BracePair[] getPairs() {
        return PAIRS;
    }

    @Override
    public boolean isPairedBracesAllowedBeforeType(@NotNull final IElementType lbraceType,
                                                   @Nullable final IElementType contextType) {
        return !(contextType instanceof IRsElementType) || TOKENS_BEFORE.contains(contextType);
    }

    @Override
    public int getCodeConstructStart(PsiFile file, int openingBraceOffset) {
        return openingBraceOffset;
    }
}
