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
import org.jetbrains.annotations.Nullable;
import org.rustidea.psi.IRsType;
import org.rustidea.psi.RsElementVisitor;
import org.rustidea.psi.RsLifetime;
import org.rustidea.psi.RsReferenceType;
import org.rustidea.psi.types.RsPsiTypes;
import org.rustidea.psi.util.RsPsiTreeUtil;

public class RsReferenceTypeImpl extends IRsCompositePsiElement implements RsReferenceType {
    public RsReferenceTypeImpl() {
        super(RsPsiTypes.REFERENCE_TYPE);
    }

    @Nullable
    @Override
    public IRsType getPointedType() {
        return RsPsiTreeUtil.getChildOfType(this, IRsType.class);
    }

    @Nullable
    @Override
    public RsLifetime getExplicitLifetime() {
        return (RsLifetime) findPsiChildByType(RsPsiTypes.LIFETIME);
    }

    @Override
    public boolean hasExplicitLifetime() {
        return getExplicitLifetime() != null;
    }

    @Override
    public void accept(@NotNull RsElementVisitor visitor) {
        visitor.visitReferenceType(this);
    }
}
