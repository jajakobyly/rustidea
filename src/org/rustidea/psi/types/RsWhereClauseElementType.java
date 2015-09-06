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

package org.rustidea.psi.types;

import com.intellij.lang.ASTNode;
import com.intellij.psi.impl.source.tree.CompositeElement;
import com.intellij.psi.stubs.StubElement;
import com.intellij.psi.stubs.StubInputStream;
import com.intellij.psi.stubs.StubOutputStream;
import org.jetbrains.annotations.NotNull;
import org.rustidea.psi.RsWhereClause;
import org.rustidea.psi.impl.RsWhereClauseImpl;
import org.rustidea.stubs.RsWhereClauseStub;
import org.rustidea.stubs.impl.RsWhereClauseStubImpl;

import java.io.IOException;

public class RsWhereClauseElementType extends IRsStubElementType<RsWhereClauseStub, RsWhereClause> {
    public static final RsWhereClauseElementType INSTANCE = new RsWhereClauseElementType();

    protected RsWhereClauseElementType() {
        super("WHERE_CLAUSE");
    }

    @NotNull
    @Override
    public RsWhereClause createPsi(@NotNull RsWhereClauseStub stub) {
        return new RsWhereClauseImpl(stub);
    }

    @NotNull
    @Override
    public RsWhereClause createPsi(@NotNull ASTNode node) {
        return new RsWhereClauseImpl(node);
    }

    @NotNull
    @Override
    public RsWhereClauseStub createStub(@NotNull RsWhereClause psi, StubElement parentStub) {
        return new RsWhereClauseStubImpl(parentStub);
    }

    @Override
    public void serialize(@NotNull RsWhereClauseStub stub, @NotNull StubOutputStream dataStream) throws IOException {
    }

    @NotNull
    @Override
    public RsWhereClauseStub deserialize(@NotNull StubInputStream dataStream, StubElement parentStub) throws IOException {
        return new RsWhereClauseStubImpl(parentStub);
    }

    @NotNull
    @Override
    public ASTNode createCompositeNode() {
        return new CompositeElement(RsTypes.WHERE_CLAUSE);
    }
}
