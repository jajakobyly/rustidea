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
import com.intellij.psi.tree.TokenSet;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.rustidea.psi.IRsType;
import org.rustidea.psi.RsElementVisitor;
import org.rustidea.psi.RsStruct;
import org.rustidea.psi.RsTypeParameterList;
import org.rustidea.psi.types.RsPsiTypes;
import org.rustidea.stubs.RsStructStub;

public class RsStructImpl extends IRsNamedItemPsiElement<RsStructStub> implements RsStruct {
    private static final TokenSet STRUCT_OR_TUPLE_TYPE = TokenSet.create(RsPsiTypes.STRUCT_TYPE, RsPsiTypes.TUPLE_TYPE);

    public RsStructImpl(@NotNull RsStructStub stub) {
        super(stub, RsPsiTypes.STRUCT);
    }

    public RsStructImpl(@NotNull ASTNode node) {
        super(node);
    }

    @Nullable
    @Override
    public RsTypeParameterList getTypeParameterList() {
        return findChildByType(RsPsiTypes.TYPE_PARAMETER_LIST);
    }

    @Nullable
    @Override
    public IRsType getDefinition() {
        return findChildByType(STRUCT_OR_TUPLE_TYPE);
    }

    @Override
    public void accept(@NotNull RsElementVisitor visitor) {
        visitor.visitStruct(this);
    }
}
