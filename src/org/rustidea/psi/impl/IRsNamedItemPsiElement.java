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
import com.intellij.psi.stubs.IStubElementType;
import com.intellij.util.IncorrectOperationException;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.rustidea.psi.IRsNamedItem;
import org.rustidea.psi.RsIdentifier;
import org.rustidea.psi.util.RsPsiTreeUtil;
import org.rustidea.stubs.IRsNamedItemStub;
import org.rustidea.util.NotImplementedException;

public abstract class IRsNamedItemPsiElement<StubT extends IRsNamedItemStub>
    extends IRsItemPsiElement<StubT> implements IRsNamedItem<StubT> {
    public IRsNamedItemPsiElement(@NotNull StubT stub, @NotNull IStubElementType nodeType) {
        super(stub, nodeType);
    }

    public IRsNamedItemPsiElement(@NotNull ASTNode node) {
        super(node);
    }

    @NotNull
    @Override
    public RsIdentifier getNameIdentifier() {
        return RsPsiTreeUtil.getRequiredChildOfType(this, RsIdentifier.class);
    }

    @Nullable
    @Override
    public String getName() {
        final StubT stub = getStub();
        if (stub != null) {
            return stub.getName();
        }

        return getNameIdentifier().getText();
    }

    @Override
    public PsiElement setName(@NotNull String name) throws IncorrectOperationException {
        // TODO Implement this
        throw new IncorrectOperationException(new NotImplementedException());
    }
}
