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
import org.rustidea.psi.RsLifetimeTypeParameter;
import org.rustidea.psi.impl.RsLifetimeTypeParameterImpl;
import org.rustidea.psi.types.RsTypes;
import org.rustidea.stubs.RsLifetimeTypeParameterStub;
import org.rustidea.stubs.impl.RsLifetimeTypeParameterStubImpl;

import java.io.IOException;

public class RsLifetimeTypeParameterElementType extends IRsStubElementType<RsLifetimeTypeParameterStub, RsLifetimeTypeParameter> {
    public static final RsLifetimeTypeParameterElementType INSTANCE = new RsLifetimeTypeParameterElementType();

    private RsLifetimeTypeParameterElementType() {
        super("LITETIME_TYPE_PARAMETER");
    }

    @NotNull
    @Override
    public RsLifetimeTypeParameter createPsi(@NotNull RsLifetimeTypeParameterStub stub) {
        return new RsLifetimeTypeParameterImpl(stub);
    }

    @NotNull
    @Override
    public RsLifetimeTypeParameter createPsi(ASTNode node) {
        return new RsLifetimeTypeParameterImpl(node);
    }

    @NotNull
    @Override
    public RsLifetimeTypeParameterStub createStub(@NotNull RsLifetimeTypeParameter psi, StubElement parentStub) {
        return new RsLifetimeTypeParameterStubImpl(parentStub);
    }

    @NotNull
    @Override
    public RsLifetimeTypeParameterStub createStub(LighterAST tree, LighterASTNode node, StubElement parentStub) {
        return new RsLifetimeTypeParameterStubImpl(parentStub);
    }

    @Override
    public void serialize(@NotNull RsLifetimeTypeParameterStub stub, @NotNull StubOutputStream dataStream) throws IOException {
    }

    @NotNull
    @Override
    public RsLifetimeTypeParameterStub deserialize(@NotNull StubInputStream dataStream, StubElement parentStub) throws IOException {
        return new RsLifetimeTypeParameterStubImpl(parentStub);
    }

    @NotNull
    @Override
    public ASTNode createCompositeNode() {
        return new CompositeElement(RsTypes.LIFETIME_TYPE_PARAMETER);
    }
}
