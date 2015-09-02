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
import org.rustidea.psi.RustTypeParameterList;
import org.rustidea.psi.impl.RustTypeParameterListImpl;
import org.rustidea.psi.types.RustTypes;
import org.rustidea.stubs.RustTypeParameterListStub;
import org.rustidea.stubs.impl.RustTypeParameterListStubImpl;

import java.io.IOException;

public class RustTypeParameterListElementType extends IRustStubElementType<RustTypeParameterListStub, RustTypeParameterList> {
    public static final RustTypeParameterListElementType INSTANCE = new RustTypeParameterListElementType();

    private RustTypeParameterListElementType() {
        super("TYPE_PARAMETER_LIST");
    }

    @NotNull
    @Override
    public RustTypeParameterList createPsi(@NotNull RustTypeParameterListStub stub) {
        return new RustTypeParameterListImpl(stub);
    }

    @NotNull
    @Override
    public RustTypeParameterList createPsi(ASTNode node) {
        return new RustTypeParameterListImpl(node);
    }

    @NotNull
    @Override
    public RustTypeParameterListStub createStub(@NotNull RustTypeParameterList psi, StubElement parentStub) {
        return new RustTypeParameterListStubImpl(parentStub);
    }

    @NotNull
    @Override
    public RustTypeParameterListStub createStub(LighterAST tree, LighterASTNode node, StubElement parentStub) {
        return new RustTypeParameterListStubImpl(parentStub);
    }

    @Override
    public void serialize(@NotNull RustTypeParameterListStub stub, @NotNull StubOutputStream dataStream) throws IOException {
    }

    @NotNull
    @Override
    public RustTypeParameterListStub deserialize(@NotNull StubInputStream dataStream, StubElement parentStub) throws IOException {
        return new RustTypeParameterListStubImpl(parentStub);
    }

    @NotNull
    @Override
    public ASTNode createCompositeNode() {
        return new CompositeElement(RustTypes.TYPE_PARAMETER_LIST);
    }
}
