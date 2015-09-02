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
import com.intellij.psi.impl.source.tree.LightTreeUtil;
import com.intellij.psi.stubs.StubElement;
import com.intellij.psi.stubs.StubInputStream;
import com.intellij.psi.stubs.StubOutputStream;
import com.intellij.util.io.StringRef;
import org.jetbrains.annotations.NotNull;
import org.rustidea.psi.RustTypeParameter;
import org.rustidea.psi.impl.RustTypeParameterImpl;
import org.rustidea.psi.impl.source.tree.RustTypeParameterElement;
import org.rustidea.psi.types.RustTypes;
import org.rustidea.stubs.RustTypeParameterStub;
import org.rustidea.stubs.impl.RustTypeParameterStubImpl;

import java.io.IOException;

public class RustTypeParameterElementType extends IRustStubElementType<RustTypeParameterStub, RustTypeParameter> {
    public static final RustTypeParameterElementType INSTANCE = new RustTypeParameterElementType();

    private RustTypeParameterElementType() {
        super("TYPE_PARAMETER");
    }

    @Override
    public RustTypeParameter createPsi(@NotNull RustTypeParameterStub stub) {
        return new RustTypeParameterImpl(stub);
    }

    @Override
    public RustTypeParameter createPsi(ASTNode node) {
        return new RustTypeParameterImpl(node);
    }

    @Override
    public RustTypeParameterStub createStub(@NotNull RustTypeParameter psi, StubElement parentStub) {
        return new RustTypeParameterStubImpl(parentStub, StringRef.fromString(psi.getName()));
    }

    @Override
    public RustTypeParameterStub createStub(LighterAST tree, LighterASTNode node, StubElement parentStub) {
        LighterASTTokenNode nameNode = (LighterASTTokenNode) LightTreeUtil.requiredChildOfType(tree, node, RustTypes.IDENTIFIER);
        String name = nameNode.getText().toString();
        return new RustTypeParameterStubImpl(parentStub, StringRef.fromString(name));
    }

    @Override
    public void serialize(@NotNull RustTypeParameterStub stub, @NotNull StubOutputStream dataStream) throws IOException {
        dataStream.writeName(stub.getName());
    }

    @NotNull
    @Override
    public RustTypeParameterStub deserialize(@NotNull StubInputStream dataStream, StubElement parentStub) throws IOException {
        return new RustTypeParameterStubImpl(parentStub, dataStream.readName());
    }

    @NotNull
    @Override
    public ASTNode createCompositeNode() {
        return new RustTypeParameterElement();
    }
}
