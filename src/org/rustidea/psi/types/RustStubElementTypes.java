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

public interface RustStubElementTypes {
    RustLifetimeElementType LIFETIME = RustLifetimeElementType.INSTANCE;
    RustTypeParameterElementType TYPE_PARAMETER = RustTypeParameterElementType.INSTANCE;
    RustLifetimeTypeParameterElementType LIFETIME_TYPE_PARAMETER = RustLifetimeTypeParameterElementType.INSTANCE;
    RustTypeParameterListElementType TYPE_PARAMETER_LIST = RustTypeParameterListElementType.INSTANCE;
    RustWhereClauseElementType WHERE_CLAUSE = RustWhereClauseElementType.INSTANCE;
}
