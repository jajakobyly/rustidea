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

package org.rustidea.psi.util;

import com.intellij.psi.PsiElement;
import com.intellij.psi.StubBasedPsiElement;
import com.intellij.psi.stubs.StubElement;
import com.intellij.psi.util.PsiTreeUtil;
import com.intellij.util.SmartList;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Collections;
import java.util.List;

public final class RsPsiTreeUtil extends PsiTreeUtil {
    private RsPsiTreeUtil() {
    }

    @Nullable
    public static <T extends PsiElement> T findLastChildByClass(@NotNull final PsiElement element, @NotNull final Class<T> cls) {
        for (PsiElement e = element.getLastChild(); e != null; e = e.getPrevSibling()) {
            if (cls.isInstance(e)) {
                //noinspection unchecked
                return (T) e;
            }
        }
        return null;
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

    @NotNull
    public static <T extends PsiElement> List<T> getChildrenOfTypeAsListRecursive(@Nullable PsiElement element, @NotNull Class<T> aClass) {
        if (element == null) return Collections.emptyList();

        List<T> result = new SmartList<T>();
        for (PsiElement child = element.getFirstChild(); child != null; child = child.getNextSibling()) {
            if (aClass.isInstance(child)) {
                //noinspection unchecked
                result.add((T) child);
            }
            result.addAll(getChildrenOfTypeAsListRecursive(child, aClass));
        }
        return result;
    }
}
