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
import org.rustidea.psi.RsAttributeItemList;
import org.rustidea.psi.impl.RsAttributeItemListImpl;
import org.rustidea.stubs.RsAttributeItemListStub;
import org.rustidea.stubs.impl.RsAttributeItemListStubImpl;

import java.io.IOException;

public class RsAttributeItemListElementType extends IRsStubElementType<RsAttributeItemListStub, RsAttributeItemList> {
    public static final RsAttributeItemListElementType INSTANCE = new RsAttributeItemListElementType();

    private RsAttributeItemListElementType() {
        super("ATTRIBUTE_ITEM_LIST");
    }

    @NotNull
    @Override
    public RsAttributeItemList createPsi(@NotNull RsAttributeItemListStub stub) {
        return new RsAttributeItemListImpl(stub);
    }

    @NotNull
    @Override
    public RsAttributeItemList createPsi(@NotNull ASTNode node) {
        return new RsAttributeItemListImpl(node);
    }

    @NotNull
    @Override
    public RsAttributeItemListStub createStub(@NotNull RsAttributeItemList psi, StubElement parentStub) {
        return new RsAttributeItemListStubImpl(parentStub);
    }

    @Override
    public void serialize(@NotNull RsAttributeItemListStub stub, @NotNull StubOutputStream dataStream) throws IOException {
    }

    @NotNull
    @Override
    public RsAttributeItemListStub deserialize(@NotNull StubInputStream dataStream, StubElement parentStub) throws IOException {
        return new RsAttributeItemListStubImpl(parentStub);
    }

    @NotNull
    @Override
    public ASTNode createCompositeNode() {
        return new CompositeElement(RsTypes.ATTRIBUTE_ITEM_LIST);
    }
}
