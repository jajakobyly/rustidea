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
import org.rustidea.psi.*;
import org.rustidea.psi.types.RustStubElementTypes;
import org.rustidea.psi.types.RustTypes;
import org.rustidea.stubs.RustTypeParameterListStub;
import org.rustidea.stubs.RustTypeParameterStub;
import org.rustidea.stubs.impl.IRustStubPsiElement;

public class RustTypeParameterImpl extends IRustStubPsiElement<RustTypeParameterStub> implements RustTypeParameter {
    public RustTypeParameterImpl(@NotNull RustTypeParameterStub stub) {
        super(stub, RustStubElementTypes.TYPE_PARAMETER);
    }

    public RustTypeParameterImpl(@NotNull ASTNode node) {
        super(node);
    }

    @NotNull
    @Override
    public RustIdentifier getNameIdentifier() {
        return PsiTreeUtil.getRequiredChildOfType(this, RustIdentifier.class);
    }

    @Override
    public String getName() {
        final RustTypeParameterStub stub = getStub();
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
    public RustWhereClause getWhereClause() {
        return getStubOrPsiChild(RustTypes.WHERE_CLAUSE);
    }

    @Nullable
    @Override
    public RustTypeParameterListOwner getOwner() {
        return PsiTreeUtil.getStubOrPsiParentOfType(this, RustTypeParameterListOwner.class);
    }

    @Override
    public int getIndex() {
        final RustTypeParameterStub stub = getStub();
        if (stub != null) {
            final RustTypeParameterListStub parentStub = (RustTypeParameterListStub) stub.getParentStub();
            return parentStub.getChildrenStubs().indexOf(stub);
        }

        int result = 0;
        for (PsiElement elem = getPrevSibling(); elem != null; elem = elem.getPrevSibling()) {
            if (elem instanceof RustTypeParameter) {
                result++;
            }
        }
        return result;
    }

    @Override
    public void accept(@NotNull PsiElementVisitor visitor) {
        if (visitor instanceof RustElementVisitor) {
            ((RustElementVisitor) visitor).visitTypeParameter(this);
        } else {
            visitor.visitElement(this);
        }
    }

    @Nullable
    @Override
    public String toString() {
        return "RustTypeParameter:" + getName();
    }
}
