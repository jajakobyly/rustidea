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
import org.jetbrains.annotations.NotNull;
import org.rustidea.psi.RsAttribute;
import org.rustidea.psi.impl.RsAttributeImpl;
import org.rustidea.stubs.RsAttributeStub;
import org.rustidea.stubs.impl.RsAttributeStubImpl;

import java.io.IOException;

public class RsAttributeElementType extends IRsStubElementType<RsAttributeStub, RsAttribute> {
    public static final RsAttributeElementType INSTANCE = new RsAttributeElementType();

    private RsAttributeElementType() {
        super("ATTRIBUTE");
    }

    @NotNull
    @Override
    public RsAttribute createPsi(@NotNull RsAttributeStub stub) {
        return new RsAttributeImpl(stub);
    }

    @NotNull
    @Override
    public RsAttribute createPsi(@NotNull ASTNode node) {
        return new RsAttributeImpl(node);
    }

    @NotNull
    @Override
    public RsAttributeStub createStub(@NotNull RsAttribute psi, StubElement parentStub) {
        return new RsAttributeStubImpl(parentStub, psi.isParentAttribute());
    }

    @Override
    public void serialize(@NotNull RsAttributeStub stub, @NotNull StubOutputStream dataStream) throws IOException {
        dataStream.writeBoolean(stub.isParentAttribute());
    }

    @NotNull
    @Override
    public RsAttributeStub deserialize(@NotNull StubInputStream dataStream, StubElement parentStub) throws IOException {
        return new RsAttributeStubImpl(parentStub, dataStream.readBoolean());
    }
}
