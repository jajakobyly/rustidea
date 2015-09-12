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
import org.rustidea.psi.types.RsStubElementTypes.*
import org.rustidea.psi.types.RsTokenTypes.*

/**
 * `attrItem ::= ident
 * @return [org.rustidea.psi.RsAttributeItem]
 */
private val attrItem =
    ( ident * maybe(token(OP_EQ) or lazy { token(OP_LBRACE) }) ).mark(ATTRIBUTE_ITEM)

/**
 * `attrItemList ::= "(" sepList{",", attrItem} ")"`
 * @return [org.rustidea.psi.RsAttributeItemList]
 */
private val attrItemList: (PsiBuilder) -> Boolean =
    wrap(OP_LBRACE, OP_RBRACE, commaSep(attrItem)).mark(ATTRIBUTE_ITEM_LIST)

/**
 * `parentDoc ::= BLOCK_PARENT_DOC | LINE_PARENT_DOC+`
 * @return [org.rustidea.psi.RsDoc]
 */
private val parentDoc =
    ( token(BLOCK_PARENT_DOC) or many1(token(LINE_PARENT_DOC)) ).mark(DOC)

/**
 * parentAttrImpl ::= "#" "!" "[" ... "]"
 * @return [org.rustidea.psi.RsAttribute]
 */
private val parentAttrImpl =
    ( seq(OP_HASH, OP_BANG) * wrap(OP_LBRACKET, OP_RBRACKET, attrItem) ).mark(ATTRIBUTE)

/**
 * `parentAttr ::= parentDoc | parentAttrImpl`
 */
public val parentAttr: (PsiBuilder) -> Boolean =
    parentDoc or parentAttrImpl

/**
 * `itemPrelude ::= attr* "pub"?`
 */
private val itemPrelude = many(token(OP_HASH)) * maybe(KW_PUB)

/**
 * `externCrateDecl ::= "extern" "crate" ident ( "as" ident )? :";":`
 * @return [org.rustidea.psi.RsExternCrateDecl]
 */
private val externCrateDecl =
    seq(
        seq(KW_EXTERN, KW_CRATE),
        ident,
        maybe(token(KW_AS) * ident),
        semicolon
    ).markGreedy(EXTERN_CRATE_DECL)

/**
 * `item ::= itemPrelude externCrateDecl`
 * @return [org.rustidea.psi.IRsItem]
 */
public val item: (PsiBuilder) -> Boolean =
    itemPrelude * externCrateDecl
