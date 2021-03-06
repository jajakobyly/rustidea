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

import org.jetbrains.annotations.NotNull;
import org.rustidea.psi.RsElementVisitor;
import org.rustidea.psi.RsMeta;
import org.rustidea.psi.RsMetaList;
import org.rustidea.psi.types.RsPsiTypes;
import org.rustidea.psi.util.RsPsiTreeUtil;
import org.rustidea.util.ArrayFactories;

public class RsMetaListImpl extends IRsCompositePsiElement implements RsMetaList {
    public RsMetaListImpl() {
        super(RsPsiTypes.META_LIST);
    }

    @NotNull
    @Override
    public RsMeta[] getMetas() {
        return getChildrenAsPsiElements(RsPsiTypes.META, ArrayFactories.get(RsMeta.class));
    }

    @Override
    public int indexOf(@NotNull RsMeta meta) {
        assert meta.getParent() == this;
        return RsPsiTreeUtil.getElementIndex(meta, RsMeta.class);
    }

    @Override
    public void accept(@NotNull RsElementVisitor visitor) {
        visitor.visitMetaList(this);
    }
}
