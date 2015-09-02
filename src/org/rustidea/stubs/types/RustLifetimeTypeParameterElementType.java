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
import org.rustidea.psi.RustLifetimeTypeParameter;
import org.rustidea.psi.impl.RustLifetimeTypeParameterImpl;
import org.rustidea.psi.types.RustTypes;
import org.rustidea.stubs.RustLifetimeTypeParameterStub;
import org.rustidea.stubs.impl.RustLifetimeTypeParameterStubImpl;

import java.io.IOException;

public class RustLifetimeTypeParameterElementType extends IRustStubElementType<RustLifetimeTypeParameterStub, RustLifetimeTypeParameter> {
    public static final RustLifetimeTypeParameterElementType INSTANCE = new RustLifetimeTypeParameterElementType();

    private RustLifetimeTypeParameterElementType() {
        super("LITETIME_TYPE_PARAMETER");
    }

    @NotNull
    @Override
    public RustLifetimeTypeParameter createPsi(@NotNull RustLifetimeTypeParameterStub stub) {
        return new RustLifetimeTypeParameterImpl(stub);
    }

    @NotNull
    @Override
    public RustLifetimeTypeParameter createPsi(ASTNode node) {
        return new RustLifetimeTypeParameterImpl(node);
    }

    @NotNull
    @Override
    public RustLifetimeTypeParameterStub createStub(@NotNull RustLifetimeTypeParameter psi, StubElement parentStub) {
        return new RustLifetimeTypeParameterStubImpl(parentStub);
    }

    @NotNull
    @Override
    public RustLifetimeTypeParameterStub createStub(LighterAST tree, LighterASTNode node, StubElement parentStub) {
        return new RustLifetimeTypeParameterStubImpl(parentStub);
    }

    @Override
    public void serialize(@NotNull RustLifetimeTypeParameterStub stub, @NotNull StubOutputStream dataStream) throws IOException {
    }

    @NotNull
    @Override
    public RustLifetimeTypeParameterStub deserialize(@NotNull StubInputStream dataStream, StubElement parentStub) throws IOException {
        return new RustLifetimeTypeParameterStubImpl(parentStub);
    }

    @NotNull
    @Override
    public ASTNode createCompositeNode() {
        return new CompositeElement(RustTypes.LIFETIME_TYPE_PARAMETER);
    }
}
