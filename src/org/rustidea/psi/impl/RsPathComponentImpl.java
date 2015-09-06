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
import org.rustidea.stubs.RsPathComponentStub;

public class RsPathComponentImpl extends IRsStubPsiElement<RsPathComponentStub> implements RsPathComponent {
    public RsPathComponentImpl(@NotNull RsPathComponentStub stub) {
        super(stub, RsTypes.PATH_COMPONENT);
    }

    public RsPathComponentImpl(@NotNull ASTNode node) {
        super(node);
    }

    @NotNull
    @Override
    public RsIdentifier getNameIdentifier() {
        return PsiTreeUtil.getRequiredChildOfType(this, RsIdentifier.class);
    }

    @Override
    public String getName() {
        final RsPathComponentStub stub = getStub();
        if (stub != null) {
            return stub.getName();
        }

        return getNameIdentifier().getText();
    }

    @NotNull
    @Override
    public PsiElement setName(@NotNull String name) throws IncorrectOperationException {
        // TODO Implement this.
        throw new IncorrectOperationException("not implemented yet");
    }

    @Nullable
    @Override
    public RsPath getOwner() {
        return PsiTreeUtil.getStubOrPsiParentOfType(this, RsPath.class);
    }

    @Override
    public int getIndex() {
        return PsiImplUtil.getStubElementIndex(this, getStub(), RsPathComponent.class);
    }

    @Override
    public boolean hasTypeParameters() {
        return PsiImplUtil.hasTypeParameters(this);
    }

    @Nullable
    @Override
    public RsTypeParameterList getTypeParameterList() {
        return getStubOrPsiChild(RsTypes.TYPE_PARAMETER_LIST);
    }

    @NotNull
    @Override
    public RsTypeParameter[] getTypeParameters() {
        return PsiImplUtil.getTypeParameters(this);
    }

    @Override
    public void accept(@NotNull RsElementVisitor visitor) {
        visitor.visitPathComponent(this);
    }
}
