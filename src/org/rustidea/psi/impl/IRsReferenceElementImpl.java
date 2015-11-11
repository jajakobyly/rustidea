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

import com.intellij.openapi.util.TextRange;
import com.intellij.psi.PsiElement;
import com.intellij.psi.ResolveResult;
import com.intellij.psi.tree.IElementType;
import com.intellij.util.IncorrectOperationException;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.rustidea.psi.IRsReferenceElement;
import org.rustidea.psi.types.RsPsiTypes;
import org.rustidea.psi.util.RsPsiUtil;
import org.rustidea.util.NotImplementedException;

public abstract class IRsReferenceElementImpl extends IRsCompositePsiElement implements IRsReferenceElement {
    public IRsReferenceElementImpl(@NotNull IElementType type) {
        super(type);
    }

    @Nullable
    @Override
    public String getReferenceName() {
        final PsiElement element = getReferenceNameElement();
        return element == null ? "" : element.getText();
    }

    @Nullable
    @Override
    public IRsReferenceElement getQualifier() {
        return (IRsReferenceElement) findPsiChildByType(RsPsiTypes.REFERENCE_ELEMENT);
    }

    @Override
    public boolean isQualified() {
        return getQualifier() != null;
    }

    @Nullable
    @Override
    public String getQualifiedName() {
        final IRsReferenceElement qualifier = getQualifier();
        return qualifier == null ? "" : qualifier.getText();
    }

    @NotNull
    @Override
    public PsiElement getElement() {
        // TODO Implement this
        throw new NotImplementedException();
    }

    @NotNull
    @Override
    public TextRange getRangeInElement() {
        // TODO Implement this
        throw new NotImplementedException();
    }

    @Nullable
    @Override
    public PsiElement resolve() {
        // TODO Implement this
        throw new NotImplementedException();
    }

    @NotNull
    @Override
    public String getCanonicalText() {
        // TODO Implement this
        throw new NotImplementedException();
    }

    @NotNull
    @Override
    public PsiElement handleElementRename(String newElementName) throws IncorrectOperationException {
        // TODO Implement this
        throw new NotImplementedException();
    }

    @NotNull
    @Override
    public PsiElement bindToElement(@NotNull PsiElement element) throws IncorrectOperationException {
        // TODO Implement this
        throw new NotImplementedException();
    }

    @Override
    public boolean isReferenceTo(PsiElement element) {
        // TODO Implement this
        throw new NotImplementedException();
    }

    @NotNull
    @Override
    public Object[] getVariants() {
        // TODO Implement this
        throw new NotImplementedException();
    }

    @NotNull
    @Override
    public ResolveResult[] multiResolve(boolean incompleteCode) {
        // TODO Implement this
        throw new NotImplementedException();
    }

    @Override
    public boolean isSoft() {
        return false;
    }

    @NotNull
    @Override
    public String toString() {
        return RsPsiUtil.getPsiClassName(this) + ":" + getText();
    }
}
