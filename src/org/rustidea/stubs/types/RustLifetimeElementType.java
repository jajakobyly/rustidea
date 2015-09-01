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
import com.intellij.lang.LighterASTTokenNode;
import com.intellij.psi.impl.source.tree.CompositeElement;
import com.intellij.psi.impl.source.tree.LightTreeUtil;
import com.intellij.psi.stubs.StubElement;
import com.intellij.psi.stubs.StubInputStream;
import com.intellij.psi.stubs.StubOutputStream;
import org.jetbrains.annotations.NotNull;
import org.rustidea.psi.RustLifetime;
import org.rustidea.psi.impl.RustLifetimeImpl;
import org.rustidea.psi.types.RustStubElementTypes;
import org.rustidea.psi.types.RustTypes;
import org.rustidea.stubs.RustLifetimeStub;
import org.rustidea.stubs.impl.RustLifetimeStubImpl;

import java.io.IOException;

public class RustLifetimeElementType extends IRustStubElementType<RustLifetimeStub, RustLifetime> {
    public static final RustLifetimeElementType INSTANCE = new RustLifetimeElementType();

    private RustLifetimeElementType() {
        super("TYPE_PARAMETER_LIST");
    }

    @Override
    public RustLifetime createPsi(@NotNull final RustLifetimeStub stub) {
        return new RustLifetimeImpl(stub);
    }

    @Override
    public RustLifetime createPsi(ASTNode node) {
        return new RustLifetimeImpl(node);
    }

    @Override
    public RustLifetimeStub createStub(@NotNull final RustLifetime psi, final StubElement parentStub) {
        return new RustLifetimeStubImpl(parentStub, psi.getName());
    }

    @Override
    public RustLifetimeStub createStub(LighterAST tree, LighterASTNode node, StubElement parentStub) {
        LighterASTTokenNode nameNode = (LighterASTTokenNode) LightTreeUtil.requiredChildOfType(tree, node, RustTypes.LIFETIME_TOKEN);
        String name = nameNode.getText().toString().substring(1);
        return new RustLifetimeStubImpl(parentStub, name);
    }

    @Override
    public void serialize(@NotNull RustLifetimeStub stub, @NotNull StubOutputStream dataStream) throws IOException {
        dataStream.writeName(stub.getName());
    }

    @NotNull
    @Override
    public RustLifetimeStub deserialize(@NotNull StubInputStream dataStream, StubElement parentStub) throws IOException {
        return new RustLifetimeStubImpl(parentStub, dataStream.readName().getString());
    }

    @NotNull
    @Override
    public ASTNode createCompositeNode() {
        return new CompositeElement(RustStubElementTypes.LIFETIME);
    }
}
