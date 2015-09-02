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
import com.intellij.psi.PsiElementVisitor;
import org.jetbrains.annotations.NotNull;
import org.rustidea.psi.RustElementVisitor;
import org.rustidea.psi.RustLifetimeTypeParameter;
import org.rustidea.psi.RustTypeParameter;
import org.rustidea.psi.RustTypeParameterList;
import org.rustidea.psi.types.RustTypes;
import org.rustidea.stubs.RustTypeParameterListStub;
import org.rustidea.stubs.impl.IRustStubPsiElement;
import org.rustidea.util.SimpleArrayFactory;

public class RustTypeParameterListImpl extends IRustStubPsiElement<RustTypeParameterListStub> implements RustTypeParameterList {
    public RustTypeParameterListImpl(@NotNull RustTypeParameterListStub stub) {
        super(stub, RustTypes.TYPE_PARAMETER_LIST);
    }

    public RustTypeParameterListImpl(@NotNull ASTNode node) {
        super(node);
    }

    @NotNull
    @Override
    public RustLifetimeTypeParameter[] getLifetimes() {
        return getStubOrPsiChildren(RustTypes.LIFETIME_TYPE_PARAMETER, SimpleArrayFactory.get(RustLifetimeTypeParameter.class));
    }

    @NotNull
    @Override
    public RustTypeParameter[] getTypeParameters() {
        return getStubOrPsiChildren(RustTypes.TYPE_PARAMETER, SimpleArrayFactory.get(RustTypeParameter.class));
    }

    @Override
    public int getLifetimeIndex(@NotNull RustLifetimeTypeParameter lifetime) {
        assert lifetime.getParent() == this;
        return lifetime.getIndex();
    }

    @Override
    public int getTypeParameterIndex(@NotNull RustTypeParameter param) {
        assert param.getParent() == this;
        return param.getIndex();
    }

    @Override
    public void accept(@NotNull PsiElementVisitor visitor) {
        if (visitor instanceof RustElementVisitor) {
            ((RustElementVisitor) visitor).visitTypeParameterList(this);
        } else {
            visitor.visitElement(this);
        }
    }

    @NotNull
    @Override
    public String toString() {
        return "RustTypeParameterList";
    }
}
