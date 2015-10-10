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
import com.intellij.util.IncorrectOperationException;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.rustidea.psi.RsElementVisitor;
import org.rustidea.psi.RsReferenceElement;
import org.rustidea.psi.RsRelationReferenceElement;
import org.rustidea.psi.RsToken;
import org.rustidea.psi.types.RsTypes;
import org.rustidea.psi.util.RsPsiUtil;
import org.rustidea.util.NotImplementedException;
import org.rustidea.util.UnreachableException;

import static org.rustidea.psi.types.RsTokenTypes.KW_SELF;
import static org.rustidea.psi.types.RsTokenTypes.KW_SUPER;
import static org.rustidea.psi.types.RsTypes.SELF_OR_SUPER;

public class RsRelationReferenceElementImpl extends IRsCompositePsiElement implements RsRelationReferenceElement {
    public RsRelationReferenceElementImpl() {
        super(RsTypes.RELATION_REFERENCE_ELEMENT);
    }

    @NotNull
    @Override
    public Type getType() {
        final RsToken element = getReferenceNameElement();

        if (element == null) {
            return Type.GLOBAL;
        }

        if (element.getTokenType() == KW_SELF) {
            return Type.SELF;
        }

        if (element.getTokenType() == KW_SUPER) {
            return Type.SUPER;
        }

        throw new UnreachableException();
    }

    @Nullable
    @Override
    public RsToken getReferenceNameElement() {
        return (RsToken) findPsiChildByType(SELF_OR_SUPER);
    }

    @Nullable
    @Override
    public RsReferenceElement getQualifier() {
        return (RsReferenceElement) findPsiChildByType(RsTypes.REFERENCE_ELEMENT);
    }

    @Nullable
    @Override
    public String getReferenceName() {
        final RsToken element = getReferenceNameElement();
        return element == null ? "" : element.getText();
    }

    @Override
    public PsiElement getElement() {
        // TODO Implement this
        throw new NotImplementedException();
    }

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

    @Override
    public PsiElement handleElementRename(String newElementName) throws IncorrectOperationException {
        // TODO Implement this
        throw new NotImplementedException();
    }

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

    @Override
    public boolean isSoft() {
        return false;
    }

    @Override
    public boolean isQualified() {
        return false;
    }

    @Override
    public String getQualifiedName() {
        return "";
    }

    @NotNull
    @Override
    public ResolveResult[] multiResolve(boolean incompleteCode) {
        // TODO Implement this
        throw new NotImplementedException();
    }

    @Override
    public void accept(@NotNull RsElementVisitor visitor) {
        visitor.visitMagicReferenceElement(this);
    }

    @NotNull
    @Override
    public String toString() {
        return RsPsiUtil.getPsiClassName(this) + ":" + getType().toString();
    }
}
