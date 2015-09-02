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

package org.rustidea.psi.impl.source.tree;

import com.intellij.lang.ASTNode;
import com.intellij.psi.impl.source.tree.CompositeElement;
import com.intellij.psi.tree.IElementType;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.rustidea.psi.types.RsStubElementTypes;
import org.rustidea.psi.types.RsTypes;

public class TypeParameterElement extends CompositeElement {
    public TypeParameterElement() {
        super(RsStubElementTypes.TYPE_PARAMETER);
    }

    @Override
    public int getChildRole(@NotNull ASTNode child) {
        assert child.getTreeParent() == this;
        final IElementType type = child.getElementType();
        if (type == RsTypes.IDENTIFIER) return ChildRole.NAME;
        if (type == RsTypes.WHERE_CLAUSE) return ChildRole.WHERE_CLAUSE;
        return ChildRole.NONE;
    }

    @Nullable
    @Override
    public ASTNode findChildByRole(int role) {
        if (role == ChildRole.NAME) return findChildByType(RsTypes.IDENTIFIER);
        if (role == ChildRole.WHERE_CLAUSE) return findChildByType(RsTypes.WHERE_CLAUSE);
        return null;
    }
}
