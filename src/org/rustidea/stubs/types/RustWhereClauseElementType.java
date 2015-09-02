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

package org.rustidea.stubs.types;

import com.intellij.lang.ASTNode;
import com.intellij.lang.LighterAST;
import com.intellij.lang.LighterASTNode;
import com.intellij.psi.impl.source.tree.CompositeElement;
import com.intellij.psi.stubs.StubElement;
import com.intellij.psi.stubs.StubInputStream;
import com.intellij.psi.stubs.StubOutputStream;
import org.jetbrains.annotations.NotNull;
import org.rustidea.psi.RustWhereClause;
import org.rustidea.psi.impl.RustWhereClauseImpl;
import org.rustidea.psi.types.RustTypes;
import org.rustidea.stubs.RustWhereClauseStub;
import org.rustidea.stubs.impl.RustWhereClauseStubImpl;

import java.io.IOException;

public class RustWhereClauseElementType extends IRustStubElementType<RustWhereClauseStub, RustWhereClause> {
    public static final RustWhereClauseElementType INSTANCE = new RustWhereClauseElementType();

    protected RustWhereClauseElementType() {
        super("WHERE_CLAUSE");
    }

    @Override
    public RustWhereClause createPsi(@NotNull RustWhereClauseStub stub) {
        return new RustWhereClauseImpl(stub);
    }

    @Override
    public RustWhereClause createPsi(ASTNode node) {
        return new RustWhereClauseImpl(node);
    }

    @Override
    public RustWhereClauseStub createStub(@NotNull RustWhereClause psi, StubElement parentStub) {
        return new RustWhereClauseStubImpl(parentStub);
    }

    @Override
    public RustWhereClauseStub createStub(LighterAST tree, LighterASTNode node, StubElement parentStub) {
        return new RustWhereClauseStubImpl(parentStub);
    }

    @Override
    public void serialize(@NotNull RustWhereClauseStub stub, @NotNull StubOutputStream dataStream) throws IOException {
    }

    @NotNull
    @Override
    public RustWhereClauseStub deserialize(@NotNull StubInputStream dataStream, StubElement parentStub) throws IOException {
        return new RustWhereClauseStubImpl(parentStub);
    }

    @NotNull
    @Override
    public ASTNode createCompositeNode() {
        return new CompositeElement(RustTypes.WHERE_CLAUSE);
    }
}
