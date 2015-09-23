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
import com.intellij.psi.stubs.StubElement;
import com.intellij.psi.stubs.StubInputStream;
import com.intellij.psi.stubs.StubOutputStream;
import com.intellij.psi.util.QualifiedName;
import com.intellij.util.io.StringRef;
import org.jetbrains.annotations.NotNull;
import org.rustidea.psi.RsUseDecl;
import org.rustidea.psi.impl.RsUseDeclImpl;
import org.rustidea.stubs.RsUseDeclStub;
import org.rustidea.stubs.impl.RsUseDeclStubImpl;

import java.io.IOException;
import java.util.ArrayList;

public class RsUseDeclElementType extends IRsStubElementType<RsUseDeclStub, RsUseDecl> {
    public static final RsUseDeclElementType INSTANCE = new RsUseDeclElementType();

    private RsUseDeclElementType() {
        super("USE_DECL");
    }

    @Override
    public RsUseDecl createPsi(@NotNull RsUseDeclStub stub) {
        return new RsUseDeclImpl(stub);
    }

    @NotNull
    @Override
    public RsUseDecl createPsi(ASTNode node) {
        return new RsUseDeclImpl(node);
    }

    @Override
    public RsUseDeclStub createStub(@NotNull RsUseDecl psi, StubElement parentStub) {
        return new RsUseDeclStubImpl(parentStub, StringRef.fromString(psi.getName()), psi.resolveAll());
    }

    @Override
    public void serialize(@NotNull RsUseDeclStub stub, @NotNull StubOutputStream dataStream) throws IOException {
        dataStream.writeName(stub.getName());
        final QualifiedName[] resolvedNames = stub.getResolvedNames();
        dataStream.writeVarInt(resolvedNames.length);
        for (QualifiedName name : resolvedNames) {
            QualifiedName.serialize(name, dataStream);
        }
    }

    @NotNull
    @Override
    public RsUseDeclStub deserialize(@NotNull StubInputStream dataStream, StubElement parentStub) throws IOException {
        final StringRef name = dataStream.readName();

        final int rNameCount = dataStream.readVarInt();
        final ArrayList<QualifiedName> rNames = new ArrayList<QualifiedName>(rNameCount);
        for (int i = 0; i < rNameCount; i++) {
            rNames.add(QualifiedName.deserialize(dataStream));
        }

        return new RsUseDeclStubImpl(parentStub, name, (QualifiedName[]) rNames.toArray());
    }
}
