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

package org.rustidea.parser.framework

import com.intellij.lang.PsiBuilder
import com.intellij.lang.PsiBuilderUtil
import com.intellij.psi.tree.IElementType
import com.intellij.psi.tree.TokenSet

public fun token(token: IElementType): (PsiBuilder) -> Boolean = { builder ->
    PsiBuilderUtil.expect(builder, token)
}

public fun token(tokens: TokenSet): (PsiBuilder) -> Boolean = { builder ->
    PsiBuilderUtil.expect(builder, tokens)
}

public fun token(vararg tokens: IElementType): (PsiBuilder) -> Boolean = { builder ->
    tokens.asSequence()
        .map { PsiBuilderUtil.expect(builder, it) }
        .all { it }
}


public fun notToken(token: IElementType): (PsiBuilder) -> Boolean = { builder ->
    PsiBuilderUtilEx.expectNot(builder, token)
}

public fun notToken(tokens: TokenSet): (PsiBuilder) -> Boolean = { builder ->
    PsiBuilderUtilEx.expectNot(builder, tokens)
}
