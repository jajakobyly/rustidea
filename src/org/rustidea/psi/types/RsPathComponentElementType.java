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
import com.intellij.psi.impl.source.tree.CompositeElement;
import com.intellij.psi.stubs.StubElement;
import com.intellij.psi.stubs.StubInputStream;
import com.intellij.psi.stubs.StubOutputStream;
import com.intellij.util.io.StringRef;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.rustidea.psi.RsPathComponent;
import org.rustidea.psi.impl.RsPathComponentImpl;
import org.rustidea.stubs.RsPathComponentStub;
import org.rustidea.stubs.impl.RsPathComponentStubImpl;

import java.io.IOException;

public class RsPathComponentElementType extends IRsStubElementType<RsPathComponentStub, RsPathComponent> {
    public static final RsPathComponentElementType INSTANCE = new RsPathComponentElementType();

    private RsPathComponentElementType() {
        super("PATH_COMPONENT");
    }

    @NotNull
    @Override
    public RsPathComponent createPsi(@NotNull final RsPathComponentStub stub) {
        return new RsPathComponentImpl(stub);
    }

    @NotNull
    @Override
    public RsPathComponent createPsi(@NotNull ASTNode node) {
        return new RsPathComponentImpl(node);
    }

    @Nullable
    @Override
    public RsPathComponentStub createStub(@NotNull final RsPathComponent psi, final StubElement parentStub) {
        return new RsPathComponentStubImpl(parentStub, StringRef.fromString(psi.getName()));
    }

    @Override
    public void serialize(@NotNull RsPathComponentStub stub, @NotNull StubOutputStream dataStream) throws IOException {
        dataStream.writeName(stub.getName());
    }

    @NotNull
    @Override
    public RsPathComponentStub deserialize(@NotNull StubInputStream dataStream, StubElement parentStub) throws IOException {
        return new RsPathComponentStubImpl(parentStub, dataStream.readName());
    }

    @NotNull
    @Override
    public ASTNode createCompositeNode() {
        return new CompositeElement(RsTypes.PATH_COMPONENT);
    }
}
