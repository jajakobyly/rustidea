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

import com.intellij.psi.tree.TokenSet;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.rustidea.psi.*;
import org.rustidea.psi.types.RsPsiTypes;
import org.rustidea.psi.util.RsPsiTreeUtil;

public class RsFunctionTypeImpl extends IRsCompositePsiElement implements RsFunctionType {
    public static final TokenSet EXTERN_OR_UNSAFE = TokenSet.create(RsPsiTypes.EXTERN_MODIFIER, RsPsiTypes.KW_UNSAFE);

    public RsFunctionTypeImpl() {
        super(RsPsiTypes.FUNCTION_TYPE);
    }

    @NotNull
    @Override
    public IRsPsiElement getKindElement() {
        final IRsPsiElement el = (IRsPsiElement) findPsiChildByType(RsPsiTypes.KW_FN);
        return el != null ? el : RsPsiTreeUtil.getRequiredChildOfType(this, IRsReferenceElement.class);
    }

    @Nullable
    @Override
    public RsInputTypeList getInputTypeList() {
        return (RsInputTypeList) findPsiChildByType(RsPsiTypes.INPUT_TYPE_LIST);
    }

    @Nullable
    @Override
    public IRsType getOutputType() {
        return RsPsiTreeUtil.getChildOfType(this, IRsType.class);
    }

    @Override
    public boolean hasOutputType() {
        return getOutputType() != null;
    }

    @NotNull
    @Override
    public Kind getKind() {
        final IRsPsiElement kindElem = getKindElement();
        if (kindElem instanceof RsKeyword) {
            assert ((RsKeyword) kindElem).getTokenType() == RsPsiTypes.KW_FN;
            return Kind.FUNCTION;
        } else {
            assert kindElem instanceof IRsReferenceElement;
            return Kind.CLOSURE;
        }
    }

    @Nullable
    @Override
    public IRsPsiElement getModifier() {
        return (IRsPsiElement) findPsiChildByType(EXTERN_OR_UNSAFE);
    }

    @Override
    public boolean hasModifier() {
        return getModifier() != null;
    }

    @Override
    public void accept(@NotNull RsElementVisitor visitor) {
        visitor.visitFunctionType(this);
    }
}
