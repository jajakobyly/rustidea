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
import org.rustidea.parser.framework.*
import org.rustidea.psi.types.RsStubElementTypes.EXTERN_CRATE_DECL
import org.rustidea.psi.types.RsTokenTypes.IDENTIFIER
import org.rustidea.psi.types.RsTokenTypes.KW_AS
import org.rustidea.psi.types.RsTokenTypes.KW_CRATE
import org.rustidea.psi.types.RsTokenTypes.KW_EXTERN

/**
 * crateName ::= /[IDENTIFIER]/
 */
val crateName = token(IDENTIFIER) fail "expected crate name"

/**
 * externCrateDecl ::= "extern" "crate" [crateName] ( "as" [crateName] )? ";"
 * @return [org.rustidea.psi.RsExternCrateDecl]
 */
val externCrateDecl =
    seq(token(KW_EXTERN, KW_CRATE),
        crateName,
        maybe(token(KW_AS) * crateName),
        semicolon) markGreedy EXTERN_CRATE_DECL

/**
 * item ::= [externCrateDecl]
 * @return [org.rustidea.psi.IRsItem]
 */
public val item: (PsiBuilder) -> Boolean = externCrateDecl
