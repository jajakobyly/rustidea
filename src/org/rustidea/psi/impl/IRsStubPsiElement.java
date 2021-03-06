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

import com.intellij.extapi.psi.StubBasedPsiElementBase;
import com.intellij.lang.ASTNode;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiElementVisitor;
import com.intellij.psi.StubBasedPsiElement;
import com.intellij.psi.stubs.IStubElementType;
import com.intellij.psi.stubs.StubElement;
import org.jetbrains.annotations.NotNull;
import org.rustidea.psi.IRsPsiElement;
import org.rustidea.psi.RsElementVisitor;
import org.rustidea.psi.util.RsPsiUtil;

public abstract class IRsStubPsiElement<StubT extends StubElement>
    extends StubBasedPsiElementBase<StubT> implements StubBasedPsiElement<StubT>, IRsPsiElement {
    public IRsStubPsiElement(@NotNull final StubT stub, @NotNull final IStubElementType nodeType) {
        super(stub, nodeType);
    }

    public IRsStubPsiElement(@NotNull final ASTNode node) {
        super(node);
    }

    @Override
    public PsiElement getParent() {
        // This is copy-pasted from Java-psi-impl; is this ok?
        return getParentByStub();
    }

    @Override
    public void accept(@NotNull final PsiElementVisitor visitor) {
        if (visitor instanceof RsElementVisitor) {
            this.accept((RsElementVisitor) visitor);
        } else {
            visitor.visitElement(this);
        }
    }

    @NotNull
    @Override
    public String toString() {
        String clsName = RsPsiUtil.getPsiClassName(this);
        String psiName = getName();
        if (psiName != null) {
            return clsName + ":" + psiName;
        }
        return clsName;
    }
}
