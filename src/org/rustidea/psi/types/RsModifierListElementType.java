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
import org.rustidea.psi.RsModifierList;
import org.rustidea.psi.impl.RsModifierListImpl;
import org.rustidea.stubs.RsModifierListStub;
import org.rustidea.stubs.impl.RsModifierListStubImpl;

import java.io.IOException;

public class RsModifierListElementType extends IRsStubElementType<RsModifierListStub, RsModifierList> {
    public static final RsModifierListElementType INSTANCE = new RsModifierListElementType();

    protected RsModifierListElementType() {
        super("MODIFIER_LIST");
    }

    @NotNull
    @Override
    public RsModifierList createPsi(@NotNull RsModifierListStub stub) {
        return new RsModifierListImpl(stub);
    }

    @NotNull
    @Override
    public RsModifierList createPsi(@NotNull ASTNode node) {
        return new RsModifierListImpl(node);
    }

    @NotNull
    @Override
    public RsModifierListStub createStub(@NotNull RsModifierList psi, StubElement parentStub) {
        return new RsModifierListStubImpl(parentStub, psi.hasPub());
    }

    @Override
    public void serialize(@NotNull RsModifierListStub stub, @NotNull StubOutputStream dataStream) throws IOException {
        dataStream.writeBoolean(stub.hasPub());
    }

    @NotNull
    @Override
    public RsModifierListStub deserialize(@NotNull StubInputStream dataStream, StubElement parentStub) throws IOException {
        return new RsModifierListStubImpl(parentStub, dataStream.readBoolean());
    }
}
