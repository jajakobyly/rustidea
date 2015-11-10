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
import org.rustidea.psi.RsConstItem;
import org.rustidea.psi.impl.RsConstItemImpl;
import org.rustidea.stubs.RsConstItemStub;

import java.io.IOException;

public class RsConstItemElementType extends IRsStubElementType<RsConstItemStub, RsConstItem> {
    public static final RsConstItemElementType INSTANCE = new RsConstItemElementType();

    private RsConstItemElementType() {
        super("CONST_ITEM");
    }

    @NotNull
    @Override
    public RsConstItem createPsi(@NotNull RsConstItemStub stub) {
        return new RsConstItemImpl(stub);
    }

    @NotNull
    @Override
    public RsConstItem createPsi(@NotNull ASTNode node) {
        return new RsConstItemImpl(node);
    }

    @Nullable
    @Override
    public RsConstItemStub createStub(@NotNull RsConstItem psi, StubElement parentStub) {
        return new RsConstItemStub(parentStub, StringRef.fromString(psi.getName()));
    }

    @Override
    public void serialize(@NotNull RsConstItemStub stub, @NotNull StubOutputStream dataStream) throws IOException {
        dataStream.writeName(stub.getName());
    }

    @NotNull
    @Override
    public RsConstItemStub deserialize(@NotNull StubInputStream dataStream, StubElement parentStub) throws IOException {
        return new RsConstItemStub(parentStub, dataStream.readName());
    }

    @NotNull
    @Override
    public String getHumanReadableName() {
        return "const item";
    }
}
