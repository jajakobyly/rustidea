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
import com.intellij.psi.stubs.EmptyStub;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.rustidea.psi.RsStructType;
import org.rustidea.psi.impl.RsStructTypeImpl;

public interface RsStubElementTypes {
    RsConstItemElementType CONST_ITEM = RsConstItemElementType.INSTANCE;
    RsExternCrateDeclElementType EXTERN_CRATE_DECL = RsExternCrateDeclElementType.INSTANCE;
    RsModuleElementType MODULE = RsModuleElementType.INSTANCE;
    RsStaticItemElementType STATIC_ITEM = RsStaticItemElementType.INSTANCE;
    RsStructElementType STRUCT = RsStructElementType.INSTANCE;
    RsStructFieldElementType STRUCT_FIELD = RsStructFieldElementType.INSTANCE;
    RsTypeAliasElementType TYPE_ALIAS = RsTypeAliasElementType.INSTANCE;
    RsUseDeclElementType USE_DECL = RsUseDeclElementType.INSTANCE;

    IRsEmptyStubElementType<RsStructType> STRUCT_TYPE = new IRsEmptyStubElementType<RsStructType>("STRUCT_TYPE") {
        @NotNull
        @Override
        public RsStructType createPsi(@NotNull ASTNode node) {
            return new RsStructTypeImpl(node);
        }

        @NotNull
        @Override
        public RsStructType createPsi(@NotNull EmptyStub stub) {
            return new RsStructTypeImpl(stub);
        }

        @NotNull
        @Override
        @Contract(pure = true)
        public String getHumanReadableName() {
            return "structure type";
        }
    };
}
