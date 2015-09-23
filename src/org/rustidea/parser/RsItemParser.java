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

import com.intellij.psi.tree.IElementType;
import org.jetbrains.annotations.NotNull;
import org.rustidea.parser.framework.Parser;
import org.rustidea.parser.framework.ParserContext;
import org.rustidea.parser.framework.Section;

import static org.rustidea.parser.RsExprParser.literal;
import static org.rustidea.parser.RsExprParser.path;
import static org.rustidea.parser.RsParserUtil.*;
import static org.rustidea.parser.framework.Combinators.*;
import static org.rustidea.parser.framework.Helpers.*;
import static org.rustidea.parser.framework.Scanners.token;
import static org.rustidea.psi.types.RsTypes.*;

public final class RsItemParser extends Parser {
    /**
     * {@code item ::= <<rust item>>}
     *
     * <p>Singleton instance of {@link RsItemParser}.</p>
     *
     * @see #itemGreedy
     */
    public static final Parser item = new RsItemParser(false);

    /**
     * {@code itemGreedy ::= <<rust item>>}
     *
     * <p>Singleton instance of {@link RsItemParser}. Performs greedy matching:
     * leaves error markers when matching fails.</p>
     *
     * @see #item
     */
    public static final Parser itemGreedy = new RsItemParser(true);

    /** <pre>attrItem ::= {@link RsParserUtil#identRequired} [ "=" {@link RsExprParser#literal} | {@link #attrItemList} ]</pre> */
    public static final Parser attrItem =
        identRequired.then(maybe((token(OP_EQ).then(literal)).or(lazy(RsItemParser.class, "attrItemList")))).mark(ATTRIBUTE_ITEM);

    /** <pre>attrItemList ::= "(" [ {@link #attrItem } ("," {@link #attrItem})* ] ")"</pre> */
    @SuppressWarnings("unused") // lazily loaded in #attrItem
    public static final Parser attrItemList =
        wrap(OP_LPAREN, OP_RPAREN, maybe(sep(OP_COMMA, attrItem))).mark(ATTRIBUTE_ITEM_LIST);

    /** {@code doc ::= BLOCK_DOC | LINE_DOC} */
    public static final Parser doc =
        token(BLOCK_DOC).or(token(LINE_DOC)).mark(DOC);

    /** <pre>attr ::= "#" "[" {@link #attrItem} "]"</pre> */
    public static final Parser attr =
        token(OP_HASH).then(wrap(OP_LBRACKET, OP_RBRACKET, attrItem)).mark(ATTRIBUTE);

    /** <pre>attrOrDoc ::= {@link #doc} | {@link #attr}</pre> */
    public static final Parser attrOrDoc =
        doc.or(attr);

    /** {@code parentDoc ::= BLOCK_PARENT_DOC | LINE_PARENT_DOC} */
    public static final Parser parentDoc =
        token(BLOCK_PARENT_DOC).or(token(LINE_PARENT_DOC)).mark(DOC);

    /** <pre>parentAttr ::= "#" "!" "[" {@link #attrItem} "]"</pre> */
    public static final Parser parentAttr =
        token(OP_HASH, OP_BANG).then(wrap(OP_LBRACKET, OP_RBRACKET, attrItem)).mark(ATTRIBUTE);

    /** <pre>parentAttrOrDoc ::= {@link #parentDoc} | {@link #parentAttr}</pre> */
    public static final Parser parentAttrOrDoc =
        parentDoc.or(parentAttr);

    /** <pre>modElem ::= {@link #parentAttrOrDoc} | {@link #itemGreedy}</pre> */
    public static final Parser modElem = parentAttrOrDoc.or(itemGreedy);

    /** <pre>modifierList ::= {@link #attr}+ | "pub" | {@link #attr}+ "pub"</pre> */
    private static final Parser modifierList =
        many1(attrOrDoc).evenThen(token(KW_PUB)).mark(MODIFIER_LIST);

    /** <pre>externCrateDecl ::= "extern" "crate" {@link RsParserUtil#identRequired} [ "as" {@link RsParserUtil#ident} ] ";"</pre> */
    private static final Parser externCrateDecl =
        seq(token(KW_EXTERN, KW_CRATE),
            identRequired,
            maybe(token(KW_AS).then(ident)),
            semicolon);

    private static final Parser useDeclPath =
        path;

    private static final Parser useDecl =
        seq(token(KW_USE),
            useDeclPath,
            maybe(token(KW_AS).then(ident)),
            semicolon);

    /** <pre>module ::= "mod" {@link RsParserUtil#identRequired} ( "{" {@link #itemGreedy}* "}" | ";" )</pre> */
    private static final Parser module =
        seq(token(KW_MOD),
            identRequired,
            wrap(OP_LBRACE, OP_RBRACE, many(modElem))
                .or(token(OP_SEMICOLON))
                .warn("missing '{' or ';'"));

    private static final Object[][] itemParsers = new Object[][]{
        {externCrateDecl, EXTERN_CRATE_DECL},
        {useDecl, USE_DECL},
        {module, MODULE},
    };

    private final boolean greedy;

    private RsItemParser(boolean greedy) {
        this.greedy = greedy;
    }

    @Override
    public boolean parse(@NotNull ParserContext ctx) {
        Section section = Section.begin(ctx);

        boolean hasModifierList = section.callWrapped(modifierList);
        section.setState(true); // ignore result of parsing modifierList

        for (Object[] p : itemParsers) {
            assert p.length == 2 && p[0] instanceof Parser && p[1] instanceof IElementType;

            Parser parser = (Parser) p[0];
            IElementType type = (IElementType) p[1];

            if (section.callWrapped(parser)) {
                return section.end(type, null);
            }
            section.setState(true); // ignore result of parsing item
        }
        section.setState(false);

        if (greedy && hasModifierList) {
            ctx.error("expected item");
            section.endGreedy();
            return true; // prevent rolling back
        }

        return section.end(null, null);
    }
}
