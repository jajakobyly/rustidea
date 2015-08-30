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

import com.intellij.psi.PsiElementVisitor;
import com.intellij.psi.impl.source.tree.CompositePsiElement;
import com.intellij.psi.tree.IElementType;
import org.jetbrains.annotations.NotNull;
import org.rustidea.psi.RustCompositeType;
import org.rustidea.psi.RustElementVisitor;
import org.rustidea.psi.RustParentDoc;
import org.rustidea.psi.RustTokenType;

public class RustParentDocImpl extends CompositePsiElement implements RustParentDoc {
    public RustParentDocImpl() {
        super(RustCompositeType.PARENT_DOC);
    }

    @NotNull
    @Override
    public IElementType getTokenType() {
        IElementType type = getNode().getElementType();
        assert type == RustTokenType.LINE_PARENT_DOC || type == RustTokenType.BLOCK_PARENT_DOC;
        return type;
    }

    @Override
    public void accept(@NotNull PsiElementVisitor visitor) {
        if (visitor instanceof RustElementVisitor) {
            ((RustElementVisitor) visitor).visitParentDoc(this);
        } else {
            visitor.visitElement(this);
        }
    }
}
