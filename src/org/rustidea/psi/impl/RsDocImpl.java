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

import com.intellij.psi.tree.IElementType;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.rustidea.psi.IRsAttributeOwner;
import org.rustidea.psi.RsDoc;
import org.rustidea.psi.RsElementVisitor;
import org.rustidea.psi.RsToken;
import org.rustidea.psi.types.RsPsiTypes;
import org.rustidea.psi.util.RsPsiTreeUtil;

public class RsDocImpl extends IRsCompositePsiElement implements RsDoc {
    public RsDocImpl() {
        super(RsPsiTypes.DOC);
    }

    @NotNull
    @Override
    public RsToken getToken() {
        final RsToken child = (RsToken) findPsiChildByType(RsPsiTypes.DOC_TOKEN_SET);
        assert child != null;
        return child;
    }

    @Nullable
    @Override
    public Type getType() {
        IElementType type = getTokenType();
        return type == RsPsiTypes.LINE_DOC || type == RsPsiTypes.LINE_PARENT_DOC ? Type.LINE : Type.BLOCK;
    }

    @Override
    public boolean isParentAttribute() {
        IElementType type = getTokenType();
        return type == RsPsiTypes.LINE_PARENT_DOC || type == RsPsiTypes.BLOCK_PARENT_DOC;
    }

    @Nullable
    @Override
    public IRsAttributeOwner getOwner() {
        return RsPsiTreeUtil.getStubOrPsiParentOfType(this, IRsAttributeOwner.class);
    }

    @NotNull
    @Override
    public IElementType getTokenType() {
        final IElementType type = getToken().getNode().getElementType();
        assert RsPsiTypes.DOC_TOKEN_SET.contains(type);
        return type;
    }

    @Override
    public void accept(@NotNull RsElementVisitor visitor) {
        visitor.visitDoc(this);
    }
}
