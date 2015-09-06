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
import org.rustidea.psi.RsLifetime;
import org.rustidea.psi.impl.RsLifetimeImpl;
import org.rustidea.stubs.RsLifetimeStub;
import org.rustidea.stubs.impl.RsLifetimeStubImpl;

import java.io.IOException;

public class RsLifetimeElementType extends IRsStubElementType<RsLifetimeStub, RsLifetime> {
    public static final RsLifetimeElementType INSTANCE = new RsLifetimeElementType();

    private RsLifetimeElementType() {
        super("LIFETIME");
    }

    @NotNull
    @Override
    public RsLifetime createPsi(@NotNull final RsLifetimeStub stub) {
        return new RsLifetimeImpl(stub);
    }

    @NotNull
    @Override
    public RsLifetime createPsi(@NotNull ASTNode node) {
        return new RsLifetimeImpl(node);
    }

    @Nullable
    @Override
    public RsLifetimeStub createStub(@NotNull final RsLifetime psi, final StubElement parentStub) {
        return new RsLifetimeStubImpl(parentStub, StringRef.fromString(psi.getName()));
    }

    @Override
    public void serialize(@NotNull RsLifetimeStub stub, @NotNull StubOutputStream dataStream) throws IOException {
        dataStream.writeName(stub.getName());
    }

    @NotNull
    @Override
    public RsLifetimeStub deserialize(@NotNull StubInputStream dataStream, StubElement parentStub) throws IOException {
        return new RsLifetimeStubImpl(parentStub, dataStream.readName());
    }
}
