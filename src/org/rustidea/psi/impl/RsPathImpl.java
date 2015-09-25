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
import org.rustidea.psi.RsPath;
import org.rustidea.psi.RsPathComponent;
import org.rustidea.psi.RsPathRelation;
import org.rustidea.psi.types.RsTypes;
import org.rustidea.psi.util.RsPsiTreeUtil;
import org.rustidea.stubs.RsPathStub;
import org.rustidea.util.SimpleArrayFactory;

public class RsPathImpl extends IRsStubPsiElement<RsPathStub> implements RsPath {
    public RsPathImpl(@NotNull RsPathStub stub) {
        super(stub, RsTypes.PATH);
    }

    public RsPathImpl(@NotNull ASTNode node) {
        super(node);
    }

    @NotNull
    @Override
    public RsPathRelation getRelation() {
        return RsPsiTreeUtil.getRequiredChildOfType(this, RsPathRelation.class);
    }

    @NotNull
    @Override
    public RsPathRelation.Type getRelationType() {
        final RsPathStub stub = getStub();
        if (stub != null) {
            return stub.getRelationType();
        }

        return getRelation().getType();
    }

    @NotNull
    @Override
    public RsPathComponent[] getComponents() {
        return getStubOrPsiChildren(RsTypes.PATH_COMPONENT, SimpleArrayFactory.get(RsPathComponent.class));
    }

    @Override
    public int getComponentIndex(@NotNull RsPathComponent component) {
        assert component.getParent() == this;
        return component.getIndex();
    }

    @Override
    public void accept(@NotNull RsElementVisitor visitor) {
        visitor.visitPath(this);
    }
}
