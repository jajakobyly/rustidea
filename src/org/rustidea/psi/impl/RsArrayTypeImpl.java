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
import org.rustidea.psi.IRsType;
import org.rustidea.psi.RsArrayType;
import org.rustidea.psi.RsElementVisitor;
import org.rustidea.psi.RsLiteral;
import org.rustidea.psi.types.RsPsiTypes;
import org.rustidea.psi.util.RsPsiTreeUtil;
import org.rustidea.util.NotImplementedException;

public class RsArrayTypeImpl extends IRsCompositePsiElement implements RsArrayType {
    public RsArrayTypeImpl() {
        super(RsPsiTypes.ARRAY_TYPE);
    }

    @NotNull
    @Override
    public IRsType getItemType() {
        return RsPsiTreeUtil.getRequiredChildOfType(this, IRsType.class);
    }

    @Override
    public int getSize() {
        final RsLiteral sizeLiteral = (RsLiteral) findPsiChildByType(RsPsiTypes.LITERAL);
        if (sizeLiteral == null) return -1;

        // TODO:RJP-40 This code will rely on RsLiteral#getValue which semantics aren't fully specified yet.
        throw new NotImplementedException();
    }

    @Override
    public void accept(@NotNull RsElementVisitor visitor) {
        visitor.visitArrayType(this);
    }
}
