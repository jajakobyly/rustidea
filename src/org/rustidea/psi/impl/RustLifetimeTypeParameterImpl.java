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
import com.intellij.psi.PsiElementVisitor;
import com.intellij.psi.util.PsiTreeUtil;
import com.intellij.util.IncorrectOperationException;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.rustidea.psi.RustElementVisitor;
import org.rustidea.psi.RustLifetime;
import org.rustidea.psi.RustLifetimeTypeParameter;
import org.rustidea.psi.RustTypeParameterListOwner;
import org.rustidea.psi.types.RustTypes;
import org.rustidea.stubs.RustLifetimeTypeParameterStub;
import org.rustidea.stubs.impl.IRustStubPsiElement;

public class RustLifetimeTypeParameterImpl extends IRustStubPsiElement<RustLifetimeTypeParameterStub> implements RustLifetimeTypeParameter {
    public RustLifetimeTypeParameterImpl(@NotNull RustLifetimeTypeParameterStub stub) {
        super(stub, RustTypes.LIFETIME_TYPE_PARAMETER);
    }

    public RustLifetimeTypeParameterImpl(@NotNull ASTNode node) {
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
    public RustLifetime getLifetime() {
        return PsiTreeUtil.getRequiredChildOfType(this, RustLifetime.class);
    }

    @Nullable
    @Override
    public RustTypeParameterListOwner getOwner() {
        return PsiTreeUtil.getStubOrPsiParentOfType(this, RustTypeParameterListOwner.class);
    }

    @Override
    public int getIndex() {
        final RustLifetimeTypeParameterStub stub = getStub();
        if (stub != null) {
            final RustLifetimeTypeParameterStub parentStub = (RustLifetimeTypeParameterStub) stub.getParentStub();
            return parentStub.getChildrenStubs().indexOf(stub);
        }

        int result = 0;
        for (PsiElement elem = getPrevSibling(); elem != null; elem = elem.getPrevSibling()) {
            if (elem instanceof RustLifetimeTypeParameter) {
                result++;
            }
        }
        return result;
    }

    @Override
    public void accept(@NotNull PsiElementVisitor visitor) {
        if (visitor instanceof RustElementVisitor) {
            ((RustElementVisitor) visitor).visitLifetimeTypeParameter(this);
        } else {
            visitor.visitElement(this);
        }
    }

    @Override
    public String toString() {
        return "RustLifetimeTypeParameter:" + getName();
    }
}
