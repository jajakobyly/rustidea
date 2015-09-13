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

package org.rustidea.parser;

import org.rustidea.parser.framework.Parser;

import static org.rustidea.parser.RsParserUtil.*;
import static org.rustidea.parser.framework.Combinators.*;
import static org.rustidea.parser.framework.Combinators.maybe;
import static org.rustidea.parser.framework.Combinators.seq;
import static org.rustidea.parser.framework.Helpers.lazy;
import static org.rustidea.parser.framework.Helpers.wrap;
import static org.rustidea.parser.framework.Scanners.maybe;
import static org.rustidea.parser.framework.Scanners.seq;
import static org.rustidea.parser.framework.Scanners.*;
import static org.rustidea.psi.types.RsTypes.*;

public final class RsItemParser {
    /**
     * <pre>attrItem ::= {@link RsParserUtil#ident} [ "=" {@link RsExpressionParser#literal} | {@link #attrItemList} ]</pre>
     * <p><b>Returns:</b> {@link org.rustidea.psi.RsAttributeItem}</p>
     */
    public static final Parser attrItem =
        ident.then(maybe(token(OP_EQ).or(lazy(RsItemParser.class, "attrItemList")))).mark(ATTRIBUTE_ITEM);

    /**
     * <pre>attrItemList ::= "(" [ attrItem ("," attrItem)* ] ")"</pre>
     * <p><b>Returns:</b> {@link org.rustidea.psi.RsAttributeItemList}</p>
     */
    @SuppressWarnings("unused") // used as lazy parser
    public static final Parser attrItemList =
        wrap(OP_LPAREN, OP_RPAREN, commaSep(attrItem)).mark(ATTRIBUTE_ITEM_LIST);

    /**
     * <pre>parentDoc ::= BLOCK_PARENT_DOC | LINE_PARENT_DOC+</pre>
     * <p><b>Returns:</b> {@link org.rustidea.psi.RsDoc}</p>
     */
    public static final Parser parentDoc =
        token(BLOCK_PARENT_DOC).or(many1(token(LINE_PARENT_DOC))).mark(DOC);

    /**
     * <pre>parentAttr ::= "#" "!" "[" {@link #attrItem} "]"</pre>
     * <p><b>Returns:</b> {@link org.rustidea.psi.RsAttribute}</p>
     */
    public static final Parser parentAttr =
        seq(OP_HASH, OP_BANG).then(wrap(OP_LBRACKET, OP_RBRACKET, attrItem)).mark(ATTRIBUTE);

    /**
     * <pre>parentAttrOrDoc ::= {@link #parentDoc} | {@link #parentAttr}</pre>
     */
    public static final Parser parentAttrOrDoc =
        parentDoc.or(parentAttr);

    /**
     * <pre>itemPrelude ::= {@link #attr}* "pub"?</pre>
     */
    public static final Parser itemPrelude =
        many(token(OP_HASH)).then(maybe(KW_PUB));

    /**
     * <pre>externCrateDecl ::= "extern" "crate" {@link RsParserUtil#ident} [ "as" {@link RsParserUtil#ident} ] ";"</pre>
     * <p><b>Returns:</b> {@link org.rustidea.psi.RsExternCrateDecl}</p>
     */
    public static final Parser externCrateDecl =
        seq(
            seq(KW_EXTERN, KW_CRATE),
            ident,
            maybe(token(KW_AS).then(ident)),
            semicolon
        ).markGreedy(EXTERN_CRATE_DECL);

    /**
     * <pre>item ::= {@link #itemPrelude} {@link #externCrateDecl}</pre>
     * <p><b>Returns:</b> {@link org.rustidea.psi.IRsItem}</p>
     */
    public static final Parser item =
        itemPrelude.then(externCrateDecl);

    private RsItemParser() {
    }
}
