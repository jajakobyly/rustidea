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
import com.intellij.psi.impl.source.tree.CompositeElement;
import com.intellij.psi.stubs.StubElement;
import com.intellij.psi.stubs.StubInputStream;
import com.intellij.psi.stubs.StubOutputStream;
import org.jetbrains.annotations.NotNull;
import org.rustidea.psi.RsPath;
import org.rustidea.psi.RsPathRelation;
import org.rustidea.psi.impl.RsPathImpl;
import org.rustidea.psi.types.RsTypes;
import org.rustidea.stubs.RsPathStub;
import org.rustidea.stubs.impl.RsPathStubImpl;

import java.io.IOException;

public class RsPathElementType extends IRsStubElementType<RsPathStub, RsPath> {
    public static final RsPathElementType INSTANCE = new RsPathElementType();

    private RsPathElementType() {
        super("PATH");
    }

    @NotNull
    @Override
    public RsPath createPsi(@NotNull RsPathStub stub) {
        return new RsPathImpl(stub);
    }

    @NotNull
    @Override
    public RsPath createPsi(@NotNull ASTNode node) {
        return new RsPathImpl(node);
    }

    @NotNull
    @Override
    public RsPathStub createStub(@NotNull RsPath psi, StubElement parentStub) {
        return new RsPathStubImpl(parentStub, psi.getRelationType());
    }

    @Override
    public void serialize(@NotNull RsPathStub stub, @NotNull StubOutputStream dataStream) throws IOException {
        dataStream.writeVarInt(stub.getRelationType().toInt());
    }

    @NotNull
    @Override
    public RsPathStub deserialize(@NotNull StubInputStream dataStream, StubElement parentStub) throws IOException {
        return new RsPathStubImpl(parentStub, RsPathRelation.Type.fromInt(dataStream.readVarInt()));
    }

    @NotNull
    @Override
    public ASTNode createCompositeNode() {
        return new CompositeElement(RsTypes.PATH);
    }
}
