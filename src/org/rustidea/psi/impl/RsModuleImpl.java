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
import com.intellij.util.IncorrectOperationException;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.rustidea.psi.*;
import org.rustidea.psi.types.RsTypes;
import org.rustidea.psi.util.PsiImplUtil;
import org.rustidea.psi.util.RsPsiTreeUtil;
import org.rustidea.stubs.RsModuleStub;
import org.rustidea.util.NotImplementedException;

public class RsModuleImpl extends IRsStubPsiElement<RsModuleStub> implements RsModule {
    public RsModuleImpl(@NotNull RsModuleStub stub) {
        super(stub, RsTypes.MODULE);
    }

    public RsModuleImpl(@NotNull ASTNode node) {
        super(node);
    }

    @NotNull
    @Override
    public RsIdentifier getNameIdentifier() {
        return RsPsiTreeUtil.getRequiredChildOfType(this, RsIdentifier.class);
    }

    @Override
    public String getName() {
        RsModuleStub stub = getStub();
        if (stub != null) {
            return stub.getName();
        }

        return getNameIdentifier().getText();
    }

    @NotNull
    @Override
    public PsiElement setName(@NotNull String name) throws IncorrectOperationException {
        // TODO Implement this
        throw new IncorrectOperationException(new NotImplementedException());
    }

    @Nullable
    @Override
    public RsModifierList getModifierList() {
        return getStubOrPsiChild(RsTypes.MODIFIER_LIST);
    }

    @NotNull
    @Override
    public IRsAttribute[] getAttributes() {
        return PsiImplUtil.getAttributes(this);
    }

    @NotNull
    @Override
    public IRsItem[] getItems() {
        return new IRsItem[0];
    }

    @Override
    public void accept(@NotNull RsElementVisitor visitor) {
        visitor.visitModule(this);
    }
}
