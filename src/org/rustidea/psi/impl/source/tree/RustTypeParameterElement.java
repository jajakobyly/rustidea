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
import org.rustidea.psi.types.RustStubElementTypes;
import org.rustidea.psi.types.RustTypes;

public class RustTypeParameterElement extends CompositeElement {
    public RustTypeParameterElement() {
        super(RustStubElementTypes.TYPE_PARAMETER);
    }

    @Override
    public int getChildRole(@NotNull ASTNode child) {
        assert child.getTreeParent() == this;
        final IElementType type = child.getElementType();
        if (type == RustTypes.IDENTIFIER) return RustChildRole.NAME;
        if (type == RustTypes.WHERE_CLAUSE) return RustChildRole.WHERE_CLAUSE;
        return RustChildRole.NONE;
    }

    @Nullable
    @Override
    public ASTNode findChildByRole(int role) {
        if (role == RustChildRole.NAME) return findChildByType(RustTypes.IDENTIFIER);
        if (role == RustChildRole.WHERE_CLAUSE) return findChildByType(RustTypes.WHERE_CLAUSE);
        return null;
    }
}
