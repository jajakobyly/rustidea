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
import com.intellij.psi.tree.IElementType

/**
 * Wraps parser in [section].
 */
public inline fun ((PsiBuilder) -> Boolean).mark(type: IElementType): (PsiBuilder) -> Boolean = { builder ->
    builder.section(type) { this(builder) }
}

/**
 * Wraps parser in [sectionGreedy].
 */
public inline fun ((PsiBuilder) -> Boolean).markGreedy(type: IElementType): (PsiBuilder) -> Boolean = { builder ->
    builder.sectionGreedy(type) { this(builder) }
}

/**
 * Produce error marker if parser fails and fail.
 */
public inline fun ((PsiBuilder) -> Boolean).fail(errorMessage: String): (PsiBuilder) -> Boolean = { builder ->
    val marker = builder.mark()
    val result = this(builder)
    if (result) {
        marker.drop()
    } else {
        marker.error(errorMessage)
    }
    result
}

/**
 * Produce error marker if parser fails and continue.
 */
public inline fun ((PsiBuilder) -> Boolean).warn(errorMessage: String): (PsiBuilder) -> Boolean = { builder ->
    val marker = builder.mark()
    if (this(builder)) {
        marker.drop()
    } else {
        marker.error(errorMessage)
    }
    true
}
