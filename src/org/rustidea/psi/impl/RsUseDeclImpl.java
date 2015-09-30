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
import com.intellij.psi.util.QualifiedName;
import com.intellij.util.IncorrectOperationException;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.rustidea.psi.RsElementVisitor;
import org.rustidea.psi.RsIdentifier;
import org.rustidea.psi.RsPath;
import org.rustidea.psi.RsUseDecl;
import org.rustidea.psi.types.RsTypes;
import org.rustidea.psi.util.RsPsiTreeUtil;
import org.rustidea.stubs.RsUseDeclStub;
import org.rustidea.util.NotImplementedException;

public class RsUseDeclImpl extends IRsItemPsiElement<RsUseDeclStub> implements RsUseDecl {
    public RsUseDeclImpl(@NotNull RsUseDeclStub stub) {
        super(stub, RsTypes.USE_DECL);
    }

    public RsUseDeclImpl(@NotNull ASTNode node) {
        super(node);
    }

    @Nullable
    @Override
    public RsIdentifier getNameIdentifier() {
        return getType() != Type.SINGLE ? null : (RsIdentifier) findLastChildByType(RsTypes.IDENTIFIER);
    }

    @Override
    public String getName() {
        RsUseDeclStub stub = getStub();
        if (stub != null) {
            return stub.getName();
        }

        RsIdentifier nameIdentifier = getNameIdentifier();
        return nameIdentifier == null ? null : nameIdentifier.getText();
    }

    @NotNull
    @Override
    public PsiElement setName(@NotNull String name) throws IncorrectOperationException {
        if (getType() == Type.SINGLE) {
            // TODO Implement this
            throw new IncorrectOperationException(new NotImplementedException());
        } else {
            throw new IncorrectOperationException("cannot rename multiple name binding");
        }
    }

    @NotNull
    @Override
    public RsPath getPath() {
        return getRequiredStubOrPsiChild(RsTypes.PATH);
    }

    @Nullable
    @Override
    public Type getType() {
        RsPath path = RsPsiTreeUtil.findLastChildByClass(this, RsPath.class);
        if (path == null) return null;
        // TODO Implement this for list and glob declarations.
        return Type.SINGLE;
    }

    @Override
    public boolean isRenamed() {
        return getName() != null;
    }

    @NotNull
    @Override
    public QualifiedName resolve() {
        // TODO Implement this
        throw new NotImplementedException();
    }

    @NotNull
    @Override
    public QualifiedName[] resolveAll() {
        // TODO Implement this
        throw new NotImplementedException();
    }

    @Override
    public void accept(@NotNull RsElementVisitor visitor) {
        visitor.visitUseDecl(this);
    }
}
