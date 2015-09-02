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
import org.rustidea.psi.RsTypeParameter;
import org.rustidea.psi.impl.RsTypeParameterImpl;
import org.rustidea.psi.impl.source.tree.TypeParameterElement;
import org.rustidea.psi.types.RsTypes;
import org.rustidea.stubs.RsTypeParameterStub;
import org.rustidea.stubs.impl.RsTypeParameterStubImpl;

import java.io.IOException;

public class RsTypeParameterElementType extends IRsStubElementType<RsTypeParameterStub, RsTypeParameter> {
    public static final RsTypeParameterElementType INSTANCE = new RsTypeParameterElementType();

    private RsTypeParameterElementType() {
        super("TYPE_PARAMETER");
    }

    @Override
    public RsTypeParameter createPsi(@NotNull RsTypeParameterStub stub) {
        return new RsTypeParameterImpl(stub);
    }

    @Override
    public RsTypeParameter createPsi(ASTNode node) {
        return new RsTypeParameterImpl(node);
    }

    @Override
    public RsTypeParameterStub createStub(@NotNull RsTypeParameter psi, StubElement parentStub) {
        return new RsTypeParameterStubImpl(parentStub, StringRef.fromString(psi.getName()));
    }

    @Override
    public RsTypeParameterStub createStub(LighterAST tree, LighterASTNode node, StubElement parentStub) {
        LighterASTTokenNode nameNode = (LighterASTTokenNode) LightTreeUtil.requiredChildOfType(tree, node, RsTypes.IDENTIFIER);
        String name = nameNode.getText().toString();
        return new RsTypeParameterStubImpl(parentStub, StringRef.fromString(name));
    }

    @Override
    public void serialize(@NotNull RsTypeParameterStub stub, @NotNull StubOutputStream dataStream) throws IOException {
        dataStream.writeName(stub.getName());
    }

    @NotNull
    @Override
    public RsTypeParameterStub deserialize(@NotNull StubInputStream dataStream, StubElement parentStub) throws IOException {
        return new RsTypeParameterStubImpl(parentStub, dataStream.readName());
    }

    @NotNull
    @Override
    public ASTNode createCompositeNode() {
        return new TypeParameterElement();
    }
}
