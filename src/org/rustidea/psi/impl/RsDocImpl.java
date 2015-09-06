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

import com.intellij.lang.ASTNode;
import com.intellij.psi.PsiElement;
import com.intellij.psi.tree.IElementType;
import com.intellij.psi.util.PsiTreeUtil;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.rustidea.psi.IRsAttributeOwner;
import org.rustidea.psi.RsDoc;
import org.rustidea.psi.RsElementVisitor;
import org.rustidea.psi.RsToken;
import org.rustidea.psi.types.RsTypes;
import org.rustidea.stubs.RsDocStub;

public class RsDocImpl extends IRsStubPsiElement<RsDocStub> implements RsDoc {
    public RsDocImpl(@NotNull final RsDocStub stub) {
        super(stub, RsTypes.DOC);
    }

    public RsDocImpl(@NotNull final ASTNode node) {
        super(node);
    }

    @NotNull
    @Override
    public RsToken[] getDoc() {
        return findChildrenByType(RsTypes.DOC_TOKEN_SET, RsToken.class);
    }

    @Nullable
    @Override
    public Type getType() {
        IElementType type = getTokenType();
        return type == RsTypes.LINE_DOC || type == RsTypes.LINE_PARENT_DOC ? Type.LINE : Type.BLOCK;
    }

    @Override
    public boolean isParentAttribute() {
        IElementType type = getTokenType();
        return type == RsTypes.LINE_PARENT_DOC || type == RsTypes.BLOCK_PARENT_DOC;
    }

    @Nullable
    @Override
    public IRsAttributeOwner getOwner() {
        return PsiTreeUtil.getStubOrPsiParentOfType(this, IRsAttributeOwner.class);
    }

    @Nullable
    @Override
    public IElementType getTokenType() {
        PsiElement child = findChildByType(RsTypes.DOC_TOKEN_SET);
        if (child != null) {
            return child.getNode().getElementType();
        }
        return null;
    }

    @Override
    public void accept(@NotNull RsElementVisitor visitor) {
        visitor.visitDoc(this);
    }
}
