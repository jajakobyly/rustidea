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
import com.intellij.psi.stubs.EmptyStub;
import com.intellij.util.IncorrectOperationException;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.rustidea.psi.IRsTypeParameterListOwner;
import org.rustidea.psi.RsElementVisitor;
import org.rustidea.psi.RsLifetime;
import org.rustidea.psi.RsLifetimeTypeParameter;
import org.rustidea.psi.types.RsPsiTypes;
import org.rustidea.psi.util.RsPsiTreeUtil;
import org.rustidea.psi.util.RsPsiUtil;

public class RsLifetimeTypeParameterImpl extends IRsStubPsiElement<EmptyStub> implements RsLifetimeTypeParameter {
    public RsLifetimeTypeParameterImpl(@NotNull EmptyStub stub) {
        super(stub, RsPsiTypes.LIFETIME_TYPE_PARAMETER);
    }

    public RsLifetimeTypeParameterImpl(@NotNull ASTNode node) {
        super(node);
    }

    @Nullable
    @Override
    public PsiElement getNameIdentifier() {
        return getLifetime().getNameIdentifier();
    }

    @Override
    public String getName() {
        return getLifetime().getName();
    }

    @Override
    public PsiElement setName(@NotNull String name) throws IncorrectOperationException {
        return getLifetime().setName(name);
    }

    @NotNull
    @Override
    public RsLifetime getLifetime() {
        return RsPsiTreeUtil.getRequiredChildOfType(this, RsLifetime.class);
    }

    @Nullable
    @Override
    public IRsTypeParameterListOwner getOwner() {
        return RsPsiTreeUtil.getStubOrPsiParentOfType(this, IRsTypeParameterListOwner.class);
    }

    @Override
    public int getIndex() {
        return RsPsiUtil.getStubElementIndex(this, getStub(), RsLifetimeTypeParameter.class);
    }

    @Override
    public void accept(@NotNull RsElementVisitor visitor) {
        visitor.visitLifetimeTypeParameter(this);
    }
}
