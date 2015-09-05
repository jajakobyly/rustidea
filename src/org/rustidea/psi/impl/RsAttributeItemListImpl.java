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
import org.rustidea.psi.RsAttributeItem;
import org.rustidea.psi.RsAttributeItemList;
import org.rustidea.psi.RsElementVisitor;
import org.rustidea.psi.types.RsTypes;
import org.rustidea.stubs.RsAttributeItemListStub;
import org.rustidea.stubs.impl.IRsStubPsiElement;
import org.rustidea.util.SimpleArrayFactory;

public class RsAttributeItemListImpl extends IRsStubPsiElement<RsAttributeItemListStub> implements RsAttributeItemList {
    public RsAttributeItemListImpl(@NotNull final RsAttributeItemListStub stub) {
        super(stub, RsTypes.ATTRIBUTE_ITEM_LIST);
    }

    public RsAttributeItemListImpl(@NotNull final ASTNode node) {
        super(node);
    }

    @NotNull
    @Override
    public RsAttributeItem[] getItems() {
        return getStubOrPsiChildren(RsTypes.ATTRIBUTE_ITEM, SimpleArrayFactory.get(RsAttributeItem.class));
    }

    @Override
    public int getItemIndex(@NotNull RsAttributeItem item) {
        assert item.getParent() == this;
        return item.getIndex();
    }

    @Override
    public void accept(@NotNull PsiElementVisitor visitor) {
        if (visitor instanceof RsElementVisitor) {
            ((RsElementVisitor) visitor).visitAttributeItemList(this);
        } else {
            visitor.visitElement(this);
        }
    }

    @NotNull
    @Override
    public String toString() {
        return "RsAttributeItemList";
    }
}
