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
import org.rustidea.psi.RsStructField;
import org.rustidea.psi.impl.RsStructFieldImpl;
import org.rustidea.stubs.RsStructFieldStub;

import java.io.IOException;

public class RsStructFieldElementType extends IRsStubElementType<RsStructFieldStub, RsStructField> {
    public static final RsStructFieldElementType INSTANCE = new RsStructFieldElementType();

    private RsStructFieldElementType() {
        super("STRUCT_FIELD");
    }

    @NotNull
    @Override
    public RsStructField createPsi(@NotNull RsStructFieldStub stub) {
        return new RsStructFieldImpl(stub);
    }

    @NotNull
    @Override
    public RsStructField createPsi(@NotNull ASTNode node) {
        return new RsStructFieldImpl(node);
    }

    @Nullable
    @Override
    public RsStructFieldStub createStub(@NotNull RsStructField psi, StubElement parentStub) {
        return new RsStructFieldStub(parentStub, StringRef.fromString(psi.getName()));
    }

    @Override
    public void serialize(@NotNull RsStructFieldStub stub, @NotNull StubOutputStream dataStream) throws IOException {
        dataStream.writeName(stub.getName());
    }

    @NotNull
    @Override
    public RsStructFieldStub deserialize(@NotNull StubInputStream dataStream, StubElement parentStub) throws IOException {
        return new RsStructFieldStub(parentStub, dataStream.readName());
    }
}
