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
import com.intellij.psi.util.PsiTreeUtil;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.rustidea.psi.RsElementVisitor;
import org.rustidea.psi.RsPath;
import org.rustidea.psi.RsTypeParameter;
import org.rustidea.psi.RsWhereClause;
import org.rustidea.psi.types.RsTypes;
import org.rustidea.stubs.RsWhereClauseStub;
import org.rustidea.stubs.impl.IRsStubPsiElement;
import org.rustidea.util.SimpleArrayFactory;

public class RsWhereClauseImpl extends IRsStubPsiElement<RsWhereClauseStub> implements RsWhereClause {
    public RsWhereClauseImpl(@NotNull RsWhereClauseStub stub) {
        super(stub, RsTypes.WHERE_CLAUSE);
    }

    public RsWhereClauseImpl(@NotNull ASTNode node) {
        super(node);
    }

    @NotNull
    @Override
    public RsPath[] getBounds() {
        return getStubOrPsiChildren(RsTypes.PATH, SimpleArrayFactory.get(RsPath.class));
    }

    @Nullable
    @Override
    public RsTypeParameter getOwner() {
        return PsiTreeUtil.getStubOrPsiParentOfType(this, RsTypeParameter.class);
    }

    @Override
    public void accept(@NotNull RsElementVisitor visitor) {
        visitor.visitWhereClause(this);
    }
}
