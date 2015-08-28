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

package org.rustidea.psi;

import com.intellij.lang.ASTNode;
import com.intellij.psi.PsiElement;
import com.intellij.psi.tree.IElementType;
import org.rustidea.psi.impl.*;

import static org.rustidea.psi.RustCompositeTypes.*;

public class RustCompositeElementFactory {
    private RustCompositeElementFactory() {
    }

    public static PsiElement create(ASTNode node) {
        final IElementType type = node.getElementType();
        if (type == ASSIGN_ATTRIBUTE_ITEM) return new RustAssignAttributeItemImpl(node);
        if (type == ATTRIBUTE) return new RustAttributeImpl(node);
        if (type == ATTRIBUTE_ITEM) return new RustAttributeItemImpl(node);
        if (type == DOC) return new RustDocImpl(node);
        if (type == FLAG_ATTRIBUTE_ITEM) return new RustFlagAttributeItemImpl(node);
        if (type == FN_ATTRIBUTE_ITEM) return new RustFnAttributeItemImpl(node);
        if (type == FN_ITEM) return new RustFnItemImpl(node);
        if (type == ITEM) return new RustItemImpl(node);
        if (type == ITEM_ATTRS) return new RustItemAttrsImpl(node);
        if (type == LITERAL) return new RustLiteralImpl(node);
        if (type == MOD_ITEM) return new RustModItemImpl(node);
        if (type == PARENT_ATTRIBUTE) return new RustParentAttributeImpl(node);
        if (type == PARENT_DOC) return new RustParentDocImpl(node);
        if (type == VISIBILITY) return new RustVisibilityImpl(node);
        throw new AssertionError("Unknown element type: " + type);
    }
}
