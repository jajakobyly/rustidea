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
import com.intellij.psi.util.PsiTreeUtil;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class RsPsiTreeUtil extends PsiTreeUtil {
    @Nullable
    public static <T extends PsiElement> T findLastChildByClass(@NotNull final PsiElement element, @NotNull final Class<T> cls) {
        for (PsiElement cur = element.getLastChild(); cur != null; cur = cur.getPrevSibling()) {
            if (cls.isInstance(cur)) return (T) cur;
        }
        return null;
    }
}
