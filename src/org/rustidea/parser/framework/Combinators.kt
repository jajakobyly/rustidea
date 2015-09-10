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
import kotlin.InlineOption.ONLY_LOCAL_RETURN

/**
 * `maybe{p} ::= p?`
 * @return always true
 */
public inline fun maybe(inlineOptions(ONLY_LOCAL_RETURN) parser: (PsiBuilder) -> Boolean): (PsiBuilder) -> Boolean = { builder ->
    parser(builder)
    true
}

/**
 * Negate parser result.
 */
public inline fun ((PsiBuilder) -> Boolean).not(): (PsiBuilder) -> Boolean = { builder ->
    !builder.section { this(builder) }
}

/**
 * `p * q ::= p q`
 *
 * This combinator should bu used only if two parsers are concatenated, otherwise use [seq].
 */
public inline fun ((PsiBuilder) -> Boolean).times(
    inlineOptions(ONLY_LOCAL_RETURN) next: (PsiBuilder) -> Boolean): (PsiBuilder) -> Boolean = { builder ->
    this(builder) && next(builder)
}

/**
 * `seq{p...} ::= p{0} p{1} p{2} ...`
 *
 * This combinator should bu used only if more than two parsers are concatenated, otherwise use `p * q`.
 */
public fun seq(vararg parsers: (PsiBuilder) -> Boolean): (PsiBuilder) -> Boolean = { builder ->
    parsers.asSequence()
        .map { it(builder) }
        .all { it }
}

/**
 * `many{p} ::= p*`
 * @return always true
 */
public fun many(parser: (PsiBuilder) -> Boolean): (PsiBuilder) -> Boolean = { builder ->
    while (!builder.eof() && parser(builder));
    true
}

/**
 * `many1{p} ::= p+`
 * @return true if at least matched `p` once
 */
public fun many1(parser: (PsiBuilder) -> Boolean): (PsiBuilder) -> Boolean = { builder ->
    builder.section {
        var result = false
        while (!builder.eof() && parser(builder)) {
            result = true
        }
        result
    }
}

/**
 * `many1Greedy{p} ::= {garbage}? ( p {garbage}? )+`
 *
 * Variant of [many1] which tries to consume whole input marking tokens between matches as errors.
 *
 * @return always true
 */
public fun many1Greedy(parser: (PsiBuilder) -> Boolean): (PsiBuilder) -> Boolean = { builder ->
    while (!builder.eof()) {
        if (!parser(builder)) {
            builder.unexpectedToken()
        }
    }
    true
}

