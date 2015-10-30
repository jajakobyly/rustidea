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
import org.jetbrains.annotations.NotNull;
import org.rustidea.psi.RsElementVisitor;
import org.rustidea.psi.RsExternCrateDecl;
import org.rustidea.psi.RsIdentifier;
import org.rustidea.psi.types.RsPsiTypes;
import org.rustidea.psi.util.RsPsiTreeUtil;
import org.rustidea.psi.util.RsPsiUtil;
import org.rustidea.stubs.RsExternCrateDeclStub;

public class RsExternCrateDeclImpl extends IRsNamedItemPsiElement<RsExternCrateDeclStub> implements RsExternCrateDecl {
    public RsExternCrateDeclImpl(@NotNull RsExternCrateDeclStub stub) {
        super(stub, RsPsiTypes.EXTERN_CRATE_DECL);
    }

    public RsExternCrateDeclImpl(@NotNull ASTNode node) {
        super(node);
    }

    @NotNull
    @Override
    public RsIdentifier getCrateNameIdentifier() {
        return RsPsiTreeUtil.getRequiredChildOfType(this, RsIdentifier.class);
    }

    @NotNull
    @Override
    public RsIdentifier getNameIdentifier() {
        RsIdentifier identifier = findLastChildByType(RsPsiTypes.IDENTIFIER);
        assert identifier != null : "BUG ALERT: crate name is required"; // see #getCrateNameIdentifier()
        return identifier;
    }

    @NotNull
    @Override
    public String getCrateName() {
        RsExternCrateDeclStub stub = getStub();
        if (stub != null) {
            return stub.getCrateName();
        }

        return getCrateNameIdentifier().getText();
    }

    @Override
    public boolean isRenamed() {
        String name = getName();
        String crateName = getCrateName();
        return name != null && !name.equals(crateName);
    }

    @Override
    public void accept(@NotNull RsElementVisitor visitor) {
        visitor.visitExternCrateDecl(this);
    }

    @NotNull
    @Override
    public String toString() {
        if (isRenamed()) {
            return String.format("%s:%s as %s", RsPsiUtil.getPsiClassName(this), getCrateName(), getName());
        }
        return super.toString();
    }
}
