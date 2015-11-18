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

package org.rustidea.psi.impl;

import com.intellij.lang.ASTNode;
import com.intellij.psi.stubs.EmptyStub;
import org.jetbrains.annotations.NotNull;
import org.rustidea.psi.RsElementVisitor;
import org.rustidea.psi.RsStructField;
import org.rustidea.psi.RsStructType;
import org.rustidea.psi.types.RsPsiTypes;
import org.rustidea.util.ArrayFactories;

public class RsStructTypeImpl extends IRsStubPsiElement<EmptyStub> implements RsStructType {
    public RsStructTypeImpl(@NotNull EmptyStub stub) {
        super(stub, RsPsiTypes.STRUCT_TYPE);
    }

    public RsStructTypeImpl(@NotNull ASTNode node) {
        super(node);
    }

    @NotNull
    @Override
    public RsStructField[] getFields() {
        return getStubOrPsiChildren(RsPsiTypes.STRUCT_FIELD, ArrayFactories.get(RsStructField.class));
    }

    @Override
    public void accept(@NotNull RsElementVisitor visitor) {
        visitor.visitStructType(this);
    }
}
