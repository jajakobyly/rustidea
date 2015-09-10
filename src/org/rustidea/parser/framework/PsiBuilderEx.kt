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
import kotlin.InlineOption.ONLY_LOCAL_RETURN

/**
 * Wrap parser logic with marker which produces given [type].
 */
public inline fun PsiBuilder.wrapMarker(type: IElementType,
                                        inlineOptions(ONLY_LOCAL_RETURN) fn: () -> Any) {
    val marker = mark()
    fn()
    marker.done(type)
}

/**
 * Wrap parser logic with marker which produces error.
 */
public inline fun PsiBuilder.wrapError(message: String,
                                       inlineOptions(ONLY_LOCAL_RETURN) fn: () -> Any) {
    val marker = mark()
    fn()
    marker.error(message)
}

/**
 * Mark current token as unexpected.
 */
public fun PsiBuilder.unexpectedToken() {
    wrapError("unexpected `${getTokenText()}`") {
        advanceLexer()
    }
}

/**
 * Wrap parser logic with marker which produces given [type] or is rollbacked otherwise.
 */
public inline fun PsiBuilder.section(type: IElementType? = null,
                                     inlineOptions(ONLY_LOCAL_RETURN) fn: () -> Boolean): Boolean {
    val marker = mark()
    val result = fn()
    if (result) {
        if (type != null) {
            marker.done(type)
        } else {
            marker.drop()
        }
    } else {
        marker.rollbackTo()
    }
    return result
}

/**
 * Wrap parser logic with marker which produces given [type] or is dropped otherwise.
 */
public inline fun PsiBuilder.sectionGreedy(type: IElementType? = null,
                                           inlineOptions(ONLY_LOCAL_RETURN) fn: () -> Boolean): Boolean {
    val marker = mark()
    val result = fn()
    if (result && type != null) {
        marker.done(type)
    } else {
        marker.drop()
    }
    return result
}
