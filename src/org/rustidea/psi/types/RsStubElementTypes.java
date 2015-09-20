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
import org.jetbrains.annotations.NotNull;
import org.rustidea.psi.RsLifetimeTypeParameter;
import org.rustidea.psi.RsTypeParameterList;
import org.rustidea.psi.RsWhereClause;
import org.rustidea.psi.impl.RsLifetimeTypeParameterImpl;
import org.rustidea.psi.impl.RsTypeParameterListImpl;
import org.rustidea.psi.impl.RsWhereClauseImpl;

public interface RsStubElementTypes {
    RsAttributeElementType ATTRIBUTE = RsAttributeElementType.INSTANCE;
    RsAttributeItemElementType ATTRIBUTE_ITEM = RsAttributeItemElementType.INSTANCE;
    RsAttributeItemListElementType ATTRIBUTE_ITEM_LIST = RsAttributeItemListElementType.INSTANCE;
    RsDocElementType DOC = RsDocElementType.INSTANCE;
    RsExternCrateDeclElementType EXTERN_CRATE_DECL = RsExternCrateDeclElementType.INSTANCE;
    RsLifetimeElementType LIFETIME = RsLifetimeElementType.INSTANCE;
    RsModifierListElementType MODIFIER_LIST = RsModifierListElementType.INSTANCE;
    RsModuleElementType MODULE = RsModuleElementType.INSTANCE;
    RsPathElementType PATH = RsPathElementType.INSTANCE;
    RsPathComponentElementType PATH_COMPONENT = RsPathComponentElementType.INSTANCE;
    RsTypeParameterElementType TYPE_PARAMETER = RsTypeParameterElementType.INSTANCE;

    IRsEmptyStubElementType<RsLifetimeTypeParameter> LIFETIME_TYPE_PARAMETER =
        new IRsEmptyStubElementType<RsLifetimeTypeParameter>("LIFETIME_TYPE_PARAMETER") {
            @NotNull
            @Override
            public RsLifetimeTypeParameter createPsi(@NotNull EmptyStub stub) {
                return new RsLifetimeTypeParameterImpl(stub);
            }

            @NotNull
            @Override
            public RsLifetimeTypeParameter createPsi(@NotNull ASTNode node) {
                return new RsLifetimeTypeParameterImpl(node);
            }
        };

    IRsEmptyStubElementType<RsTypeParameterList> TYPE_PARAMETER_LIST =
        new IRsEmptyStubElementType<RsTypeParameterList>("TYPE_PARAMETER_LIST") {
            @NotNull
            @Override
            public RsTypeParameterList createPsi(@NotNull EmptyStub stub) {
                return new RsTypeParameterListImpl(stub);
            }

            @NotNull
            @Override
            public RsTypeParameterList createPsi(@NotNull ASTNode node) {
                return new RsTypeParameterListImpl(node);
            }
        };

    IRsEmptyStubElementType<RsWhereClause> WHERE_CLAUSE =
        new IRsEmptyStubElementType<RsWhereClause>("WHERE_CLAUSE") {
            @NotNull
            @Override
            public RsWhereClause createPsi(@NotNull EmptyStub stub) {
                return new RsWhereClauseImpl(stub);
            }

            @NotNull
            @Override
            public RsWhereClause createPsi(@NotNull ASTNode node) {
                return new RsWhereClauseImpl(node);
            }
        };
}
