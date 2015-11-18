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
import com.intellij.psi.stubs.IStubElementType;
import com.intellij.util.ArrayUtil;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.rustidea.psi.IRsAttribute;
import org.rustidea.psi.IRsItem;
import org.rustidea.psi.IRsItemOwner;
import org.rustidea.psi.RsModifierList;
import org.rustidea.psi.types.RsPsiTypes;
import org.rustidea.stubs.IRsItemStub;
import org.rustidea.util.ArrayFactories;

public abstract class IRsItemPsiElement<StubT extends IRsItemStub>
    extends IRsStubPsiElement<StubT> implements IRsItem<StubT> {
    public IRsItemPsiElement(@NotNull StubT stub, @NotNull IStubElementType nodeType) {
        super(stub, nodeType);
    }

    public IRsItemPsiElement(@NotNull ASTNode node) {
        super(node);
    }

    @Nullable
    @Override
    public IRsItemOwner getOwner() {
        return getStubOrPsiParentOfType(IRsItemOwner.class);
    }

    @Nullable
    @Override
    public RsModifierList getModifierList() {
        return findChildByType(RsPsiTypes.MODIFIER_LIST);
    }

    @NotNull
    @Override
    public IRsAttribute[] getAttributes() {
        RsModifierList modifierList = this.getModifierList();
        IRsAttribute[] a1 = modifierList != null ? modifierList.getAttributes() : ArrayFactories.empty(IRsAttribute.class);
        IRsAttribute[] a2 = this.getStubOrPsiChildren(RsPsiTypes.ATTRIBUTE_OR_DOC_TOKEN_SET, ArrayFactories.get(IRsAttribute.class));
        return ArrayUtil.mergeArrays(a1, a2, ArrayFactories.get(IRsAttribute.class));
    }
}
