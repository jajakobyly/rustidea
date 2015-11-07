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
import org.jetbrains.annotations.Nullable;
import org.rustidea.psi.RsStruct;
import org.rustidea.psi.impl.RsStructImpl;
import org.rustidea.stubs.RsStructStub;

import java.io.IOException;

public class RsStructElementType extends IRsStubElementType<RsStructStub, RsStruct> {
    public static final RsStructElementType INSTANCE = new RsStructElementType();

    private RsStructElementType() {
        super("STRUCT");
    }

    @NotNull
    @Override
    public RsStruct createPsi(@NotNull RsStructStub stub) {
        return new RsStructImpl(stub);
    }

    @NotNull
    @Override
    public RsStruct createPsi(@NotNull ASTNode node) {
        return new RsStructImpl(node);
    }

    @Nullable
    @Override
    public RsStructStub createStub(@NotNull RsStruct psi, StubElement parentStub) {
        return new RsStructStub(parentStub, StringRef.fromString(psi.getName()));
    }

    @Override
    public void serialize(@NotNull RsStructStub stub, @NotNull StubOutputStream dataStream) throws IOException {
        dataStream.writeName(stub.getName());
    }

    @NotNull
    @Override
    public RsStructStub deserialize(@NotNull StubInputStream dataStream, StubElement parentStub) throws IOException {
        return new RsStructStub(parentStub, dataStream.readName());
    }
}
