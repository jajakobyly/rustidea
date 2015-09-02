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
import com.intellij.psi.PsiElementVisitor;
import com.intellij.psi.util.PsiTreeUtil;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.rustidea.psi.RustElementVisitor;
import org.rustidea.psi.RustPath;
import org.rustidea.psi.RustTypeParameter;
import org.rustidea.psi.RustWhereClause;
import org.rustidea.psi.types.RustTypes;
import org.rustidea.stubs.RustWhereClauseStub;

public class RustWhereClauseImpl extends StubBasedPsiElementBase<RustWhereClauseStub> implements RustWhereClause {
    public RustWhereClauseImpl(@NotNull RustWhereClauseStub stub) {
        super(stub, RustTypes.WHERE_CLAUSE);
    }

    public RustWhereClauseImpl(@NotNull ASTNode node) {
        super(node);
    }

    @NotNull
    @Override
    public RustPath[] getBounds() {
//        return getStubOrPsiChildren(RustTypes.PATH, SimpleArrayFactory.get(RustPath.class));
        throw new UnsupportedOperationException("not implemented yet");
    }

    @Nullable
    @Override
    public RustTypeParameter getOwner() {
        return PsiTreeUtil.getStubOrPsiParentOfType(this, RustTypeParameter.class);
    }

    @Override
    public void accept(@NotNull PsiElementVisitor visitor) {
        if (visitor instanceof RustElementVisitor) {
            ((RustElementVisitor) visitor).visitWhereClause(this);
        } else {
            visitor.visitElement(this);
        }
    }

    @NotNull
    @Override
    public String toString() {
        return "RustWhereClause";
    }
}
