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

package org.rustidea.stubs.types;

import com.intellij.lang.ASTNode;
import com.intellij.psi.impl.source.tree.CompositeElement;
import com.intellij.psi.stubs.StubElement;
import com.intellij.psi.stubs.StubInputStream;
import com.intellij.psi.stubs.StubOutputStream;
import com.intellij.util.io.StringRef;
import org.jetbrains.annotations.NotNull;
import org.rustidea.psi.RsAttributeItem;
import org.rustidea.psi.impl.RsAttributeItemImpl;
import org.rustidea.psi.types.RsTypes;
import org.rustidea.stubs.RsAttributeItemStub;
import org.rustidea.stubs.impl.RsAttributeItemStubImpl;

import java.io.IOException;

public class RsAttributeItemElementType extends IRsStubElementType<RsAttributeItemStub, RsAttributeItem> {
    public static final RsAttributeItemElementType INSTANCE = new RsAttributeItemElementType();

    private RsAttributeItemElementType() {
        super("ATTRIBUTE_ITEM");
    }

    @NotNull
    @Override
    public RsAttributeItem createPsi(@NotNull RsAttributeItemStub stub) {
        return new RsAttributeItemImpl(stub);
    }

    @NotNull
    @Override
    public RsAttributeItem createPsi(@NotNull ASTNode node) {
        return new RsAttributeItemImpl(node);
    }

    @NotNull
    @Override
    public RsAttributeItemStub createStub(@NotNull RsAttributeItem psi, StubElement parentStub) {
        return new RsAttributeItemStubImpl(parentStub, StringRef.fromString(psi.getName()));
    }

    @Override
    public void serialize(@NotNull RsAttributeItemStub stub, @NotNull StubOutputStream dataStream) throws IOException {
        dataStream.writeName(stub.getName());
    }

    @NotNull
    @Override
    public RsAttributeItemStub deserialize(@NotNull StubInputStream dataStream, StubElement parentStub) throws IOException {
        return new RsAttributeItemStubImpl(parentStub, dataStream.readName());
    }

    @NotNull
    @Override
    public ASTNode createCompositeNode() {
        return new CompositeElement(RsTypes.ATTRIBUTE_ITEM);
    }
}
