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

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.rustidea.psi.RsElementVisitor;
import org.rustidea.psi.RsGlobReferenceElement;
import org.rustidea.psi.RsToken;
import org.rustidea.psi.types.RsPsiTypes;
import org.rustidea.psi.util.RsPsiUtil;

public class RsGlobReferenceElementImpl extends IRsReferenceElementImpl implements RsGlobReferenceElement {
    public RsGlobReferenceElementImpl() {
        super(RsPsiTypes.GLOB_REFERENCE_ELEMENT);
    }

    @Nullable
    @Override
    public RsToken getReferenceNameElement() {
        return (RsToken) findPsiChildByType(RsPsiTypes.OP_ASTERISK);
    }

    @Override
    public void accept(@NotNull RsElementVisitor visitor) {
        visitor.visitGlobReferenceElement(this);
    }

    @NotNull
    @Override
    public String toString() {
        return RsPsiUtil.getPsiClassName(this);
    }
}
