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
import org.jetbrains.annotations.NotNull;
import org.rustidea.psi.RsDoc;
import org.rustidea.psi.impl.RsDocImpl;
import org.rustidea.stubs.RsDocStub;
import org.rustidea.stubs.impl.RsDocStubImpl;

import java.io.IOException;

public class RsDocElementType extends IRsStubElementType<RsDocStub, RsDoc> {
    public static final RsDocElementType INSTANCE = new RsDocElementType();

    private RsDocElementType() {
        super("DOC");
    }

    @NotNull
    @Override
    public RsDoc createPsi(@NotNull RsDocStub stub) {
        return new RsDocImpl(stub);
    }

    @NotNull
    @Override
    public RsDoc createPsi(@NotNull ASTNode node) {
        return new RsDocImpl(node);
    }

    @NotNull
    @Override
    public RsDocStub createStub(@NotNull RsDoc psi, StubElement parentStub) {
        return new RsDocStubImpl(parentStub, psi.isParentAttribute());
    }

    @Override
    public void serialize(@NotNull RsDocStub stub, @NotNull StubOutputStream dataStream) throws IOException {
        dataStream.writeBoolean(stub.isParentAttribute());
    }

    @NotNull
    @Override
    public RsDocStub deserialize(@NotNull StubInputStream dataStream, StubElement parentStub) throws IOException {
        return new RsDocStubImpl(parentStub, dataStream.readBoolean());
    }

    @NotNull
    @Override
    public ASTNode createCompositeNode() {
        return new CompositeElement(RsTypes.DOC);
    }
}
