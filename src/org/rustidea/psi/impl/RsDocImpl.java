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
import com.intellij.psi.PsiElementVisitor;
import com.intellij.psi.impl.source.tree.CompositePsiElement;
import com.intellij.psi.tree.IElementType;
import org.jetbrains.annotations.NotNull;
import org.rustidea.psi.RsDoc;
import org.rustidea.psi.RsElementVisitor;
import org.rustidea.psi.types.RsTypes;

import static org.rustidea.psi.types.RsTypes.DOC_TOKEN_SET;

public class RsDocImpl extends CompositePsiElement implements RsDoc {
    public RsDocImpl() {
        super(RsTypes.DOC);
    }

    @NotNull
    @Override
    public PsiElement getToken() {
        PsiElement token = findPsiChildByType(DOC_TOKEN_SET);
        assert token != null;
        return token;
    }

    @NotNull
    @Override
    public IElementType getTokenType() {
        ASTNode token = findChildByType(DOC_TOKEN_SET);
        assert token != null;
        return token.getElementType();
    }

    @Override
    public void accept(@NotNull PsiElementVisitor visitor) {
        if (visitor instanceof RsElementVisitor) {
            ((RsElementVisitor) visitor).visitDoc(this);
        } else {
            visitor.visitElement(this);
        }
    }
}
