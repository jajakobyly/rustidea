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
import com.intellij.util.io.StringRef;
import org.jetbrains.annotations.NotNull;
import org.rustidea.psi.RsExternCrateDecl;
import org.rustidea.psi.impl.RsExternCrateDeclImpl;
import org.rustidea.stubs.RsExternCrateDeclStub;

import java.io.IOException;

public class RsExternCrateDeclElementType extends IRsStubElementType<RsExternCrateDeclStub, RsExternCrateDecl> {
    public static final RsExternCrateDeclElementType INSTANCE = new RsExternCrateDeclElementType();

    protected RsExternCrateDeclElementType() {
        super("EXTERN_CRATE_DECL");
    }

    @NotNull
    @Override
    public RsExternCrateDecl createPsi(@NotNull RsExternCrateDeclStub stub) {
        return new RsExternCrateDeclImpl(stub);
    }

    @NotNull
    @Override
    public RsExternCrateDecl createPsi(@NotNull ASTNode node) {
        return new RsExternCrateDeclImpl(node);
    }

    @NotNull
    @Override
    public RsExternCrateDeclStub createStub(@NotNull RsExternCrateDecl psi, StubElement parentStub) {
        return new RsExternCrateDeclStub(
            parentStub,
            StringRef.fromString(psi.getName()),
            StringRef.fromString(psi.getCrateName()));
    }

    @Override
    public void serialize(@NotNull RsExternCrateDeclStub stub, @NotNull StubOutputStream dataStream) throws IOException {
        dataStream.writeName(stub.getName());
        dataStream.writeName(stub.getCrateName());
    }

    @NotNull
    @Override
    public RsExternCrateDeclStub deserialize(@NotNull StubInputStream dataStream, StubElement parentStub) throws IOException {
        return new RsExternCrateDeclStub(
            parentStub,
            dataStream.readName(),
            dataStream.readName());
    }
}
