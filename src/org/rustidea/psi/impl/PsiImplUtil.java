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
import com.intellij.psi.StubBasedPsiElement;
import com.intellij.psi.stubs.StubElement;
import com.intellij.psi.util.PsiUtilBase;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.rustidea.psi.IRsTypeParameterListOwner;
import org.rustidea.psi.RsTypeParameter;
import org.rustidea.psi.RsTypeParameterList;
import org.rustidea.util.SimpleArrayFactory;

public final class PsiImplUtil extends PsiUtilBase {
    private PsiImplUtil() {
    }

    public static boolean hasTypeParameters(@NotNull final IRsTypeParameterListOwner owner) {
        final RsTypeParameterList list = owner.getTypeParameterList();
        return list != null && list.getTypeParameters().length > 0;
    }

    @NotNull
    public static RsTypeParameter[] getTypeParameters(@NotNull final IRsTypeParameterListOwner owner) {
        final RsTypeParameterList list = owner.getTypeParameterList();
        if (list != null) {
            return list.getTypeParameters();
        }
        return SimpleArrayFactory.empty(RsTypeParameter.class);
    }

    public static <ElemT extends PsiElement> int getElementIndex(
        @NotNull final ElemT element, @NotNull final Class<ElemT> cls) {
        int result = 0;
        for (PsiElement e = element.getPrevSibling(); e != null; e = e.getPrevSibling()) {
            if (cls.isInstance(e)) {
                result++;
            }
        }
        return result;
    }

    public static <ElemT extends StubBasedPsiElement> int getStubElementIndex(
        @NotNull final ElemT element, @Nullable final StubElement stub, @NotNull final Class<ElemT> elemCls) {
        if (stub != null) {
            return stub.getParentStub().getChildrenStubs().indexOf(stub);
        }
        return getElementIndex(element, elemCls);
    }
}
