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
import com.intellij.psi.util.PsiTreeUtil;
import com.intellij.util.IncorrectOperationException;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.rustidea.psi.*;
import org.rustidea.psi.types.RsTypes;
import org.rustidea.stubs.RsAttributeItemStub;
import org.rustidea.stubs.impl.IRsStubPsiElement;

public class RsAttributeItemImpl extends IRsStubPsiElement<RsAttributeItemStub> implements RsAttributeItem {
    public RsAttributeItemImpl(@NotNull final RsAttributeItemStub stub) {
        super(stub, RsTypes.ATTRIBUTE_ITEM);
    }

    public RsAttributeItemImpl(@NotNull final ASTNode node) {
        super(node);
    }

    @NotNull
    @Override
    public RsIdentifier getNameIdentifier() {
        return PsiTreeUtil.getRequiredChildOfType(this, RsIdentifier.class);
    }

    @Nullable
    @Override
    public IRsAttribute getAttribute() {
        return getStubOrPsiParentOfType(RsAttribute.class);
    }

    @Nullable
    @Override
    public RsAttributeItem getParentItem() {
        return getStubOrPsiParentOfType(RsAttributeItem.class);
    }

    @NotNull
    @Override
    public Type getType() {
        if (getParams() != null) return Type.FN;
        if (getValue() != null) return Type.ASSIGN;
        return Type.SIMPLE;
    }

    @Nullable
    @Override
    public RsLiteral getValue() {
        return findChildByClass(RsLiteral.class);
    }

    @Nullable
    @Override
    public RsAttributeItemList getParams() {
        return getStubOrPsiChild(RsTypes.ATTRIBUTE_ITEM_LIST);
    }

    @Override
    public int getIndex() {
        if (getParentItem() == null) {
            return 0;
        }
        return PsiImplUtil.getStubElementIndex(this, getStub(), RsAttributeItem.class);
    }

    @Nullable
    @Override
    public String getName() {
        return getNameIdentifier().getText();
    }

    @Override
    public PsiElement setName(@NotNull String name) throws IncorrectOperationException {
        // TODO Implement this.
        throw new IncorrectOperationException("not implemented yet");
    }

    @Override
    public void accept(@NotNull RsElementVisitor visitor) {
        visitor.visitAttributeItem(this);
    }
}
