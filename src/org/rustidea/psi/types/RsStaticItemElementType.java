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
import org.rustidea.psi.RsStaticItem;
import org.rustidea.psi.impl.RsStaticItemImpl;
import org.rustidea.stubs.RsStaticItemStub;
import org.rustidea.stubs.impl.RsStaticItemStubImpl;

import java.io.IOException;

public class RsStaticItemElementType extends IRsStubElementType<RsStaticItemStub, RsStaticItem> {
    public static final RsStaticItemElementType INSTANCE = new RsStaticItemElementType();

    private RsStaticItemElementType() {
        super("STATIC_ITEM");
    }

    @NotNull
    @Override
    public RsStaticItem createPsi(@NotNull RsStaticItemStub stub) {
        return new RsStaticItemImpl(stub);
    }

    @NotNull
    @Override
    public RsStaticItem createPsi(@NotNull ASTNode node) {
        return new RsStaticItemImpl(node);
    }

    @Nullable
    @Override
    public RsStaticItemStub createStub(@NotNull RsStaticItem psi, StubElement parentStub) {
        return new RsStaticItemStubImpl(parentStub, StringRef.fromString(psi.getName()));
    }

    @Override
    public void serialize(@NotNull RsStaticItemStub stub, @NotNull StubOutputStream dataStream) throws IOException {
        dataStream.writeName(stub.getName());
    }

    @NotNull
    @Override
    public RsStaticItemStub deserialize(@NotNull StubInputStream dataStream, StubElement parentStub) throws IOException {
        return new RsStaticItemStubImpl(parentStub, dataStream.readName());
    }
}
