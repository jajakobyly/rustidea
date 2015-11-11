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
import org.rustidea.psi.IRsReferenceElement;
import org.rustidea.psi.RsUseDecl;
import org.rustidea.psi.impl.RsUseDeclImpl;
import org.rustidea.stubs.RsUseDeclStub;

import java.io.IOException;

public class RsUseDeclElementType extends IRsStubElementType<RsUseDeclStub, RsUseDecl> {
    public static final RsUseDeclElementType INSTANCE = new RsUseDeclElementType();

    private RsUseDeclElementType() {
        super("USE_DECL");
    }

    @NotNull
    @Override
    public RsUseDecl createPsi(@NotNull RsUseDeclStub stub) {
        return new RsUseDeclImpl(stub);
    }

    @NotNull
    @Override
    public RsUseDecl createPsi(@NotNull ASTNode node) {
        return new RsUseDeclImpl(node);
    }

    @NotNull
    @Override
    public RsUseDeclStub createStub(@NotNull RsUseDecl psi, StubElement parentStub) {
        final IRsReferenceElement referenceElement = psi.getUseReference();
        final String refText = referenceElement == null ? null : referenceElement.getText();
        return new RsUseDeclStub(parentStub, StringRef.fromNullableString(refText), psi.getType().pack());
    }

    @Override
    public void serialize(@NotNull RsUseDeclStub stub, @NotNull StubOutputStream dataStream) throws IOException {
        dataStream.writeByte(stub.getFlags());
        dataStream.writeName(stub.getReferenceText());
    }

    @NotNull
    @Override
    public RsUseDeclStub deserialize(@NotNull StubInputStream dataStream, StubElement parentStub) throws IOException {
        final byte flags = dataStream.readByte();
        final StringRef text = dataStream.readName();
        return new RsUseDeclStub(parentStub, text, flags);
    }

    @NotNull
    @Override
    public String getHumanReadableName() {
        return "use declaration";
    }
}
