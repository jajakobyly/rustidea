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

import org.jetbrains.annotations.NotNull;
import org.rustidea.psi.RsElementVisitor;
import org.rustidea.psi.RsLifetimeTypeParameter;
import org.rustidea.psi.RsTypeParameter;
import org.rustidea.psi.RsTypeParameterList;
import org.rustidea.psi.types.RsPsiTypes;
import org.rustidea.psi.util.RsPsiTreeUtil;
import org.rustidea.util.ArrayFactories;

public class RsTypeParameterListImpl extends IRsCompositePsiElement implements RsTypeParameterList {
    public RsTypeParameterListImpl() {
        super(RsPsiTypes.TYPE_PARAMETER_LIST);
    }

    @NotNull
    @Override
    public RsLifetimeTypeParameter[] getLifetimes() {
        return getChildrenAsPsiElements(RsPsiTypes.LIFETIME_TYPE_PARAMETER, ArrayFactories.get(RsLifetimeTypeParameter.class));
    }

    @NotNull
    @Override
    public RsTypeParameter[] getTypeParameters() {
        return getChildrenAsPsiElements(RsPsiTypes.TYPE_PARAMETER, ArrayFactories.get(RsTypeParameter.class));
    }

    @Override
    public int indexOf(@NotNull RsLifetimeTypeParameter lifetime) {
        assert lifetime.getParent() == this;
        return RsPsiTreeUtil.getElementIndex(lifetime, RsLifetimeTypeParameter.class);
    }

    @Override
    public int indexOf(@NotNull RsTypeParameter typeParameter) {
        assert typeParameter.getParent() == this;
        return RsPsiTreeUtil.getElementIndex(typeParameter, RsTypeParameter.class);
    }

    @Override
    public void accept(@NotNull RsElementVisitor visitor) {
        visitor.visitTypeParameterList(this);
    }
}
