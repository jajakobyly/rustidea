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
import org.rustidea.psi.IRsAttribute;
import org.rustidea.psi.RsElementVisitor;
import org.rustidea.psi.RsModifierList;
import org.rustidea.psi.types.RsTypes;
import org.rustidea.stubs.RsModifierListStub;
import org.rustidea.util.SimpleArrayFactory;

public class RsModifierListImpl extends IRsStubPsiElement<RsModifierListStub> implements RsModifierList {
    public RsModifierListImpl(@NotNull RsModifierListStub stub) {
        super(stub, RsTypes.MODIFIER_LIST);
    }

    public RsModifierListImpl(@NotNull ASTNode node) {
        super(node);
    }

    @Override
    public boolean hasPub() {
        RsModifierListStub stub = getStub();
        if (stub != null) {
            return stub.hasPub();
        }

        return findChildByType(RsTypes.KW_PUB) != null;
    }

    @NotNull
    @Override
    public IRsAttribute[] getAttributes() {
        return getStubOrPsiChildren(RsTypes.ATTRIBUTE_OR_DOC_TOKEN_SET, SimpleArrayFactory.get(IRsAttribute.class));
    }

    @Override
    public void accept(@NotNull RsElementVisitor visitor) {
        visitor.visitModifierList(this);
    }
}
