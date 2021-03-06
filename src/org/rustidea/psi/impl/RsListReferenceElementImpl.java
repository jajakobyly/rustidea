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

import com.intellij.psi.PsiElement;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.rustidea.psi.RsElementVisitor;
import org.rustidea.psi.RsListReferenceElement;
import org.rustidea.psi.types.RsPsiTypes;

public class RsListReferenceElementImpl extends IRsReferenceElementImpl implements RsListReferenceElement {
    public RsListReferenceElementImpl() {
        super(RsPsiTypes.LIST_REFERENCE_ELEMENT);
    }

    @Nullable
    @Override
    public PsiElement getReferenceNameElement() {
        return null;
    }

    @Override
    public void accept(@NotNull RsElementVisitor visitor) {
        visitor.visitListReferenceElement(this);
    }
}
