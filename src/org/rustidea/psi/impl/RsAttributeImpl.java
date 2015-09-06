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
import com.intellij.psi.util.PsiTreeUtil;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.rustidea.psi.IRsAttributeOwner;
import org.rustidea.psi.RsAttribute;
import org.rustidea.psi.RsAttributeItem;
import org.rustidea.psi.RsElementVisitor;
import org.rustidea.psi.types.RsTypes;
import org.rustidea.stubs.RsAttributeStub;

public class RsAttributeImpl extends IRsStubPsiElement<RsAttributeStub> implements RsAttribute {
    public RsAttributeImpl(@NotNull final RsAttributeStub stub) {
        super(stub, RsTypes.ATTRIBUTE);
    }

    public RsAttributeImpl(@NotNull final ASTNode node) {
        super(node);
    }

    @Nullable
    @Override
    public RsAttributeItem getItem() {
        return getStubOrPsiChild(RsTypes.ATTRIBUTE_ITEM);
    }

    @Override
    public boolean isParentAttribute() {
        return findChildByType(RsTypes.OP_BANG) != null;
    }

    @Nullable
    @Override
    public IRsAttributeOwner getOwner() {
        return PsiTreeUtil.getStubOrPsiParentOfType(this, IRsAttributeOwner.class);
    }

    @Override
    public void accept(@NotNull RsElementVisitor visitor) {
        visitor.visitAttribute(this);
    }
}
