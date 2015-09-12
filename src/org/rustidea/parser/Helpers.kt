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

package org.rustidea.parser

import com.intellij.lang.PsiBuilder
import org.rustidea.parser.framework.fail
import org.rustidea.parser.framework.sep
import org.rustidea.parser.framework.token
import org.rustidea.parser.framework.warn
import org.rustidea.psi.types.RsTokenTypes.IDENTIFIER
import org.rustidea.psi.types.RsTokenTypes.OP_COMMA
import org.rustidea.psi.types.RsTokenTypes.OP_SEMICOLON

/**
 * `ident ::= IDENTIFIER`
 */
public val ident: (PsiBuilder) -> Boolean =
    token(IDENTIFIER) fail "expected identifier"

/**
 * `semicolon ::= ";"`
 */
public val semicolon: (PsiBuilder) -> Boolean =
    token(OP_SEMICOLON) warn "missing semicolon"

/**
 * `commaSep{p} ::= p ("," p)*`
 */
public fun commaSep(p: (PsiBuilder) -> Boolean): (PsiBuilder) -> Boolean =
    sep(OP_COMMA, p)
