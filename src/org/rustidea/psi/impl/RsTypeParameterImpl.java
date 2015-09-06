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
import org.rustidea.stubs.RsTypeParameterStub;

public class RsTypeParameterImpl extends IRsStubPsiElement<RsTypeParameterStub> implements RsTypeParameter {
    public RsTypeParameterImpl(@NotNull RsTypeParameterStub stub) {
        super(stub, RsTypes.TYPE_PARAMETER);
    }

    public RsTypeParameterImpl(@NotNull ASTNode node) {
        super(node);
    }

    @NotNull
    @Override
    public RsIdentifier getNameIdentifier() {
        return PsiTreeUtil.getRequiredChildOfType(this, RsIdentifier.class);
    }

    @Override
    public String getName() {
        final RsTypeParameterStub stub = getStub();
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
    public RsWhereClause getWhereClause() {
        return getStubOrPsiChild(RsTypes.WHERE_CLAUSE);
    }

    @Nullable
    @Override
    public IRsTypeParameterListOwner getOwner() {
        return PsiTreeUtil.getStubOrPsiParentOfType(this, IRsTypeParameterListOwner.class);
    }

    @Override
    public int getIndex() {
        return PsiImplUtil.getStubElementIndex(this, getStub(), RsTypeParameter.class);
    }

    @Override
    public void accept(@NotNull RsElementVisitor visitor) {
        visitor.visitTypeParameter(this);
    }
}
