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
import org.rustidea.psi.*;
import org.rustidea.psi.types.RsPsiTypes;
import org.rustidea.psi.util.RsPsiTreeUtil;

public class RsAttributeItemImpl extends IRsCompositePsiElement implements RsAttributeItem {
    public RsAttributeItemImpl() {
        super(RsPsiTypes.ATTRIBUTE_ITEM);
    }

    @NotNull
    @Override
    public RsIdentifier getNameIdentifier() {
        return RsPsiTreeUtil.getRequiredChildOfType(this, RsIdentifier.class);
    }

    @Nullable
    @Override
    public String getName() {
        return getNameIdentifier().getText();
    }

    @Nullable
    @Override
    public IRsAttribute getAttribute() {
        return RsPsiTreeUtil.getParentOfType(this, RsAttribute.class);
    }

    @Nullable
    @Override
    public RsAttributeItem getParentItem() {
        return RsPsiTreeUtil.getParentOfType(this, RsAttributeItem.class);
    }

    @NotNull
    @Override
    public Type getType() {
        if (getParams() != null) return Type.FN;
        if (getValue() != null) return Type.ASSIGN;
        return Type.SIMPLE;
    }

    @Nullable
    @Override
    public RsLiteral getValue() {
        return RsPsiTreeUtil.getChildOfType(this, RsLiteral.class);
    }

    @Nullable
    @Override
    public RsAttributeItemList getParams() {
        return (RsAttributeItemList) findPsiChildByType(RsPsiTypes.ATTRIBUTE_ITEM_LIST);
    }

    @Override
    public int getIndex() {
        if (getParentItem() == null) {
            return 0;
        }
        return RsPsiTreeUtil.getElementIndex(this, RsAttributeItem.class);
    }

    @Override
    public void accept(@NotNull RsElementVisitor visitor) {
        visitor.visitAttributeItem(this);
    }
}
