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
import org.jetbrains.annotations.Nullable;
import org.rustidea.psi.*;
import org.rustidea.psi.types.RsTypes;
import org.rustidea.psi.util.RsPsiUtil;
import org.rustidea.stubs.RsUseDeclStub;

public class RsUseDeclImpl extends IRsItemPsiElement<RsUseDeclStub> implements RsUseDecl {
    public RsUseDeclImpl(@NotNull RsUseDeclStub stub) {
        super(stub, RsTypes.USE_DECL);
    }

    public RsUseDeclImpl(@NotNull ASTNode node) {
        super(node);
    }

    @Nullable
    @Override
    public RsReferenceElement getUseReference() {
        RsPsiUtil.ensureValid(this);
        final RsUseDeclStub stub = getStub();
        if (stub != null) {
            return stub.getUseReference();
        }
        return findChildByType(RsTypes.REFERENCE_ELEMENT);
    }

    @NotNull
    @Override
    public Type getType() {
        // TODO Lists and globs.

        final RsToken asToken = findChildByType(RsTypes.KW_AS);
        if (asToken != null && asToken.getNextSibling() instanceof RsIdentifier) {
            return Type.RENAMED;
        }

        return Type.SIMPLE;
    }

    @Override
    public void accept(@NotNull RsElementVisitor visitor) {
        visitor.visitUseDecl(this);
    }
}
