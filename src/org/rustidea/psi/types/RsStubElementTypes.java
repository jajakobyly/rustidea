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

import org.rustidea.stubs.types.*;

public interface RsStubElementTypes {
    RsLifetimeElementType LIFETIME = RsLifetimeElementType.INSTANCE;
    RsTypeParameterElementType TYPE_PARAMETER = RsTypeParameterElementType.INSTANCE;
    RsLifetimeTypeParameterElementType LIFETIME_TYPE_PARAMETER = RsLifetimeTypeParameterElementType.INSTANCE;
    RsTypeParameterListElementType TYPE_PARAMETER_LIST = RsTypeParameterListElementType.INSTANCE;
    RsWhereClauseElementType WHERE_CLAUSE = RsWhereClauseElementType.INSTANCE;
    RsPathElementType PATH = RsPathElementType.INSTANCE;
    RsPathComponentElementType PATH_COMPONENT = RsPathComponentElementType.INSTANCE;
}