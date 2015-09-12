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
import kotlin.InlineOption.ONLY_LOCAL_RETURN

/**
 * Utility function for dealing with circular dependencies.
 */
public inline fun lazy(
    inlineOptions(ONLY_LOCAL_RETURN) initializer: () -> (PsiBuilder) -> Boolean): (PsiBuilder) -> Boolean = { builder ->
    initializer()(builder)
}

/**
 * `wrap{OPEN, CLOSE, p} ::= OPEN p CLOSE`
 */
public fun wrap(open: IElementType, close: IElementType,
                p: (PsiBuilder) -> Boolean): (PsiBuilder) -> Boolean = { builder ->
    PsiBuilderUtil.expect(builder, open)
        && p(builder)
        && PsiBuilderUtilEx.expectOrWarn(builder, close, "missing `${close.toString()}`")
}

/**
 * `sep{SEPARATOR, p} ::= ( p (SEPARATOR p)* )?`
 */
public fun sep(separator: IElementType,
               p: (PsiBuilder) -> Boolean): (PsiBuilder) -> Boolean = { builder ->
    if (p(builder)) {
        while (!builder.eof() && builder.section {
            PsiBuilderUtilEx.expectOrWarn(builder, separator, "missing ${separator.toString()}")
            p(builder)
        });

        // TODO Is this good behaviour?
        if (!builder.eof() && builder.getTokenType() == separator) {
            builder.unexpectedToken()
        }
    }
    true
}
