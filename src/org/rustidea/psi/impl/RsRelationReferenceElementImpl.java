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
import org.rustidea.psi.IRsReferenceElement;
import org.rustidea.psi.RsElementVisitor;
import org.rustidea.psi.RsRelationReferenceElement;
import org.rustidea.psi.RsToken;
import org.rustidea.psi.types.RsPsiTypes;
import org.rustidea.psi.util.RsPsiUtil;
import org.rustidea.util.UnreachableException;

public class RsRelationReferenceElementImpl extends IRsReferenceElementImpl implements RsRelationReferenceElement {
    public RsRelationReferenceElementImpl() {
        super(RsPsiTypes.RELATION_REFERENCE_ELEMENT);
    }

    @NotNull
    @Override
    public RelationType getRelationType() {
        final RsToken element = getReferenceNameElement();

        if (element == null) {
            return RelationType.GLOBAL;
        }

        if (element.getTokenType() == RsPsiTypes.KW_SELF) {
            return RelationType.SELF;
        }

        if (element.getTokenType() == RsPsiTypes.KW_SUPER) {
            return RelationType.SUPER;
        }

        throw new UnreachableException();
    }

    @Nullable
    @Override
    public RsToken getReferenceNameElement() {
        return (RsToken) findPsiChildByType(RsPsiTypes.SELF_OR_SUPER);
    }

    @Nullable
    @Override
    public IRsReferenceElement getQualifier() {
        return null;
    }

    @Override
    public boolean isQualified() {
        return false;
    }

    @Override
    public String getQualifiedName() {
        return "";
    }

    @Override
    public void accept(@NotNull RsElementVisitor visitor) {
        visitor.visitRelationReferenceElement(this);
    }

    @NotNull
    @Override
    public String toString() {
        return RsPsiUtil.getPsiClassName(this) + ":" + getRelationType().toString();
    }
}
