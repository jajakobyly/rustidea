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

/**
 * `maybe{p} ::= p?`
 * @return always true
 */
public fun maybe(p: (PsiBuilder) -> Boolean): (PsiBuilder) -> Boolean = { builder ->
    p(builder)
    true
}

/**
 * Negate parser result.
 */
public fun ((PsiBuilder) -> Boolean).not(): (PsiBuilder) -> Boolean = { builder ->
    !builder.section { this(builder) }
}

/**
 * `p * q ::= p q`
 *
 * This combinator should bu used only if two parsers are concatenated, otherwise use `seq`.
 */
public fun ((PsiBuilder) -> Boolean).times(q: (PsiBuilder) -> Boolean): (PsiBuilder) -> Boolean = { builder ->
    this(builder) && q(builder)
}

/**
 * `seq{ps...} ::= ps{0} ps{1} ...`
 *
 * This combinator should bu used only if more than two parsers are concatenated, otherwise use `p * q`.
 */
public fun seq(vararg ps: (PsiBuilder) -> Boolean): (PsiBuilder) -> Boolean = { builder ->
    ps.asSequence()
        .map { it(builder) }
        .all { it }
}

/**
 * `p or q` ::= p | q
 *
 * This combinator should bu used only if two parsers are concatenated, otherwise use `or`.
 */
public fun ((PsiBuilder) -> Boolean).or(q: (PsiBuilder) -> Boolean): (PsiBuilder) -> Boolean = { builder ->
    builder.section { this(builder) } || builder.section { q(builder) }
}

/**
 * `or{ps...} ::= ps{0} | ps{1} | ...`
 *
 * This combinator should bu used only if more than two parsers are concatenated, otherwise use `p or q`.
 */
public fun or(vararg ps: (PsiBuilder) -> Boolean): (PsiBuilder) -> Boolean = { builder ->
    ps.asSequence()
        .map { builder.section { it(builder) } }
        .any { it }
}

/**
 * `many{p} ::= p*`
 * @return always true
 */
public fun many(p: (PsiBuilder) -> Boolean): (PsiBuilder) -> Boolean = { builder ->
    while (!builder.eof() && p(builder));
    true
}

/**
 * `many1{p} ::= p+`
 * @return true if at least matched `p` once
 */
public fun many1(p: (PsiBuilder) -> Boolean): (PsiBuilder) -> Boolean = { builder ->
    builder.section {
        var result = false
        while (!builder.eof() && p(builder)) {
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
public fun many1Greedy(p: (PsiBuilder) -> Boolean): (PsiBuilder) -> Boolean = { builder ->
    while (!builder.eof()) {
        if (!p(builder)) {
            builder.unexpectedToken()
        }
    }
    true
}
