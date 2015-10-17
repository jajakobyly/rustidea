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

package org.rustidea.psi;

import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiPolyVariantReference;
import com.intellij.psi.PsiQualifiedReferenceElement;
import org.jetbrains.annotations.Nullable;

/**
 * Represents reference found in Rust code.
 *
 * In Rust Reference they are called <i>paths</i>.
 */
public interface RsReferenceElement extends PsiPolyVariantReference, PsiQualifiedReferenceElement, IRsPsiElement {
    @Nullable
    PsiElement getReferenceNameElement();

    boolean isQualified();

    /**
     * @return null if qualifier is not specified
     */
    @Nullable
    String getQualifiedName();
}