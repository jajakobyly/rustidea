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
import com.intellij.psi.PsiElementVisitor;
import com.intellij.util.IncorrectOperationException;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.rustidea.psi.RustElementVisitor;
import org.rustidea.psi.RustIdentifier;
import org.rustidea.psi.RustLifetime;
import org.rustidea.psi.types.RustStubElementTypes;
import org.rustidea.psi.types.RustType;
import org.rustidea.stubs.RustLifetimeStub;
import org.rustidea.stubs.impl.IRustStubPsiElement;

public class RustLifetimeImpl extends IRustStubPsiElement<RustLifetimeStub> implements RustLifetime {
    public RustLifetimeImpl(@NotNull RustLifetimeStub stub) {
        super(stub, RustStubElementTypes.LIFETIME);
    }

    public RustLifetimeImpl(@NotNull ASTNode node) {
        super(node);
    }

    @Nullable
    @Override
    public RustIdentifier getNameIdentifier() {
        return findChildByType(RustType.LIFETIME_TOKEN);
    }

    @Nullable
    @Override
    public String getName() {
        final RustLifetimeStub stub = getStub();
        if (stub != null) {
            return stub.getName();
        }

        final RustIdentifier node = getNameIdentifier();
        if (node != null) {
            return node.getText().substring(1); // Trim initial '
        }

        return null;
    }

    @NotNull
    @Override
    public PsiElement setName(@NotNull String name) throws IncorrectOperationException {
        // TODO Implement this.
        throw new IncorrectOperationException("not implemented yet");
    }

    @Override
    public void accept(@NotNull PsiElementVisitor visitor) {
        if (visitor instanceof RustElementVisitor) {
            ((RustElementVisitor) visitor).visitLifetime(this);
        } else {
            visitor.visitElement(this);
        }
    }

    @Override
    public String toString() {
        return "RustLifetime:" + getName();
    }
}
