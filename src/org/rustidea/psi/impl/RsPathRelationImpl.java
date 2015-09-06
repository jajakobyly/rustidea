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
import org.rustidea.psi.RsKeyword;
import org.rustidea.psi.RsPathRelation;
import org.rustidea.psi.RsToken;
import org.rustidea.psi.types.RsTypes;

public class RsPathRelationImpl extends IRsCompositePsiElement implements RsPathRelation {
    public RsPathRelationImpl() {
        super(RsTypes.PATH_RELATION);
    }

    @Nullable
    @Override
    public RsKeyword getKwSelf() {
        return (RsKeyword) findPsiChildByType(RsTypes.KW_SELF);
    }

    @Nullable
    @Override
    public RsKeyword getKwSuper() {
        return (RsKeyword) findPsiChildByType(RsTypes.KW_SUPER);
    }

    @Nullable
    @Override
    public RsToken getDoubleColon() {
        return (RsToken) findPsiChildByType(RsTypes.OP_DOUBLE_COLON);
    }

    @NotNull
    @Override
    public Type getType() {
        if (getKwSelf() != null) return Type.SELF;
        if (getKwSuper() != null) return Type.SUPER;
        if (getDoubleColon() != null) return Type.GLOBAL;
        return Type.UNSPECIFIED;
    }

    @Override
    public void accept(@NotNull RsElementVisitor visitor) {
        visitor.visitPathRelation(this);
    }
}
