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
import com.intellij.psi.PsiElement;
import com.intellij.util.IncorrectOperationException;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.rustidea.psi.*;
import org.rustidea.psi.types.RsTypes;
import org.rustidea.stubs.RsExternCrateDeclStub;

public class RsExternCrateDeclImpl extends IRsStubPsiElement<RsExternCrateDeclStub> implements RsExternCrateDecl {
    public RsExternCrateDeclImpl(@NotNull RsExternCrateDeclStub stub) {
        super(stub, RsTypes.EXTERN_CRATE_DECL);
    }

    public RsExternCrateDeclImpl(@NotNull ASTNode node) {
        super(node);
    }

    @Nullable
    @Override
    public RsIdentifier getCrateNameIdentifier() {
        return findChildByType(RsTypes.IDENTIFIER);
    }

    @Nullable
    @Override
    public RsIdentifier getNameIdentifier() {
        return findLastChildByType(RsTypes.IDENTIFIER);
    }

    @Nullable
    @Override
    public String getCrateName() {
        RsExternCrateDeclStub stub = getStub();
        if (stub != null) {
            return stub.getCrateName();
        }

        RsIdentifier node = getCrateNameIdentifier();
        return node == null ? null : node.getText();
    }

    @Override
    public String getName() {
        RsExternCrateDeclStub stub = getStub();
        if (stub != null) {
            return stub.getName();
        }

        RsIdentifier node = getNameIdentifier();
        return node == null ? null : node.getText();
    }

    @NotNull
    @Override
    public PsiElement setName(@NotNull String name) throws IncorrectOperationException {
        // TODO Implement this
        throw new IncorrectOperationException("not implemented yet");
    }

    @Override
    public boolean isRenamed() {
        String name = getName();
        String crateName = getCrateName();
        return name != null && crateName != null && !name.equals(crateName);
    }

    @Nullable
    @Override
    public IRsItemOwner getOwner() {
        return getStubOrPsiParentOfType(IRsItemOwner.class);
    }

    @Override
    public boolean isPublic() {
        return findChildByType(RsTypes.KW_PUB) != null;
    }

    @NotNull
    @Override
    public IRsAttribute[] getAttributes() {
        // FIXME Does this method operate on stubs wherever possible?
        return findChildrenByClass(IRsAttribute.class);
    }

    @Override
    public void accept(@NotNull RsElementVisitor visitor) {
        visitor.visitExternCrateDecl(this);
    }

    @NotNull
    @Override
    public String toString() {
        if (isRenamed()) {
            return String.format("%s:%s as %s", PsiImplUtil.getPsiClassName(this), getCrateName(), getName());
        }
        return super.toString();
    }
}
