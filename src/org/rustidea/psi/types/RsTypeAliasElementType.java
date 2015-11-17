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
import org.rustidea.psi.RsTypeAlias;
import org.rustidea.psi.impl.RsTypeAliasImpl;
import org.rustidea.stubs.RsTypeAliasStub;

import java.io.IOException;

public class RsTypeAliasElementType extends IRsStubElementType<RsTypeAliasStub, RsTypeAlias> {
    public static final RsTypeAliasElementType INSTANCE = new RsTypeAliasElementType();

    private RsTypeAliasElementType() {
        super("TYPE_ALIAS");
    }

    @NotNull
    @Override
    public RsTypeAlias createPsi(@NotNull RsTypeAliasStub stub) {
        return new RsTypeAliasImpl(stub);
    }

    @NotNull
    @Override
    public RsTypeAlias createPsi(@NotNull ASTNode node) {
        return new RsTypeAliasImpl(node);
    }

    @Nullable
    @Override
    public RsTypeAliasStub createStub(@NotNull RsTypeAlias psi, StubElement parentStub) {
        return new RsTypeAliasStub(parentStub, StringRef.fromString(psi.getName()));
    }

    @Override
    public void serialize(@NotNull RsTypeAliasStub stub, @NotNull StubOutputStream dataStream) throws IOException {
        dataStream.writeName(stub.getName());
    }

    @NotNull
    @Override
    public RsTypeAliasStub deserialize(@NotNull StubInputStream dataStream, StubElement parentStub) throws IOException {
        return new RsTypeAliasStub(parentStub, dataStream.readName());
    }

    @NotNull
    @Override
    public String getHumanReadableName() {
        return "type alias";
    }
}
