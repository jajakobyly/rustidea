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

package org.rustidea.psi;

import org.jetbrains.annotations.NonNls;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public interface RsAttributeItem extends IRsPsiElement {
    @NotNull
    RsIdentifier getNameIdentifier();

    @Nullable
    @NonNls
    String getName();

    @Nullable
    IRsAttribute getAttribute();

    @Nullable
    RsAttributeItem getParentItem();

    @NotNull
    Type getType();

    @Nullable
    RsLiteral getValue();

    @Nullable
    RsAttributeItemList getParams();

    /**
     * @return 0 if the item is attribute root
     */
    int getIndex();

    enum Type {
        SIMPLE, ASSIGN, FN
    }
}
