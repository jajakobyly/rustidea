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
import org.jetbrains.annotations.NotNull;
import org.rustidea.psi.RsElementVisitor;
import org.rustidea.psi.RsLifetimeTypeParameter;
import org.rustidea.psi.RsTypeParameter;
import org.rustidea.psi.RsTypeParameterList;
import org.rustidea.psi.types.RsTypes;
import org.rustidea.stubs.RsTypeParameterListStub;
import org.rustidea.stubs.impl.IRsStubPsiElement;
import org.rustidea.util.SimpleArrayFactory;

public class RsTypeParameterListImpl extends IRsStubPsiElement<RsTypeParameterListStub> implements RsTypeParameterList {
    public RsTypeParameterListImpl(@NotNull RsTypeParameterListStub stub) {
        super(stub, RsTypes.TYPE_PARAMETER_LIST);
    }

    public RsTypeParameterListImpl(@NotNull ASTNode node) {
        super(node);
    }

    @NotNull
    @Override
    public RsLifetimeTypeParameter[] getLifetimes() {
        return getStubOrPsiChildren(RsTypes.LIFETIME_TYPE_PARAMETER, SimpleArrayFactory.get(RsLifetimeTypeParameter.class));
    }

    @NotNull
    @Override
    public RsTypeParameter[] getTypeParameters() {
        return getStubOrPsiChildren(RsTypes.TYPE_PARAMETER, SimpleArrayFactory.get(RsTypeParameter.class));
    }

    @Override
    public int getLifetimeIndex(@NotNull RsLifetimeTypeParameter lifetime) {
        assert lifetime.getParent() == this;
        return lifetime.getIndex();
    }

    @Override
    public int getTypeParameterIndex(@NotNull RsTypeParameter param) {
        assert param.getParent() == this;
        return param.getIndex();
    }

    @Override
    public void accept(@NotNull RsElementVisitor visitor) {
        visitor.visitTypeParameterList(this);
    }
}
