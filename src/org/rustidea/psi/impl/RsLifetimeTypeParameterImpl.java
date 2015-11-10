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

import com.intellij.psi.PsiElement;
import com.intellij.util.IncorrectOperationException;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.rustidea.psi.*;
import org.rustidea.psi.types.RsPsiTypes;
import org.rustidea.psi.util.RsPsiTreeUtil;

public class RsLifetimeTypeParameterImpl extends IRsCompositePsiElement implements RsLifetimeTypeParameter {
    public RsLifetimeTypeParameterImpl() {
        super(RsPsiTypes.LIFETIME_TYPE_PARAMETER);
    }

    @NotNull
    @Override
    public RsIdentifier getNameIdentifier() {
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
        return RsPsiTreeUtil.getElementIndex(this, RsLifetimeTypeParameter.class);
    }

    @Override
    public void accept(@NotNull RsElementVisitor visitor) {
        visitor.visitLifetimeTypeParameter(this);
    }
}
