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

import static org.rustidea.parser.RsExprParser.literal;
import static org.rustidea.parser.RsParserUtil.*;
import static org.rustidea.parser.framework.Combinators.*;
import static org.rustidea.parser.framework.Helpers.*;
import static org.rustidea.parser.framework.Scanners.maybeToken;
import static org.rustidea.parser.framework.Scanners.token;
import static org.rustidea.psi.types.RsTypes.*;

public final class RsItemParser {
    /**
     * <pre>attrItem ::= {@link RsParserUtil#identRequired} [ "=" {@link RsExprParser#literal} | {@link #attrItemList} ]</pre>
     * <p><b>Returns:</b> {@link org.rustidea.psi.RsAttributeItem}</p>
     */
    public static final Parser attrItem =
        identRequired.then(maybe((token(OP_EQ).then(literal)).or(lazy(RsItemParser.class, "attrItemList")))).mark(ATTRIBUTE_ITEM);

    /**
     * <pre>attrItemList ::= "(" [ attrItem ("," attrItem)* ] ")"</pre>
     * <p><b>Returns:</b> {@link org.rustidea.psi.RsAttributeItemList}</p>
     */
    @SuppressWarnings("unused") // used as lazy parser
    public static final Parser attrItemList =
        wrap(OP_LPAREN, OP_RPAREN, sep(OP_COMMA, attrItem)).mark(ATTRIBUTE_ITEM_LIST);

    /**
     * <pre>doc ::= BLOCK_DOC | LINE_DOC+</pre>
     * <p><b>Returns:</b> {@link org.rustidea.psi.RsDoc}</p>
     */
    public static final Parser doc =
        token(BLOCK_DOC).or(many1(token(LINE_DOC))).mark(DOC);

    /**
     * <pre>attr ::= "#" "[" {@link #attrItem} "]"</pre>
     * <p><b>Returns:</b> {@link org.rustidea.psi.RsAttribute}</p>
     */
    public static final Parser attr =
        token(OP_HASH).then(wrap(OP_LBRACKET, OP_RBRACKET, attrItem)).mark(ATTRIBUTE);

    /**
     * <pre>attrOrDoc ::= {@link #doc} | {@link #attr}</pre>
     */
    public static final Parser attrOrDoc =
        doc.or(attr);

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
        token(OP_HASH, OP_BANG).then(wrap(OP_LBRACKET, OP_RBRACKET, attrItem)).mark(ATTRIBUTE);

    /**
     * <pre>parentAttrOrDoc ::= {@link #parentDoc} | {@link #parentAttr}</pre>
     */
    public static final Parser parentAttrOrDoc =
        parentDoc.or(parentAttr);

    /**
     * <pre>itemPrelude ::= {@link #attr}* "pub"?</pre>
     */
    public static final Parser itemPrelude =
        many(attrOrDoc).then(maybeToken(KW_PUB));

    /**
     * <pre>externCrateDecl ::= "extern" "crate" {@link RsParserUtil#identRequired} [ "as" {@link RsParserUtil#ident} ] ";"</pre>
     * <p><b>Returns:</b> {@link org.rustidea.psi.RsExternCrateDecl}</p>
     */
    public static final Parser externCrateDecl =
        seq(
            token(KW_EXTERN, KW_CRATE),
            identRequired,
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
