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

import com.intellij.lang.PsiBuilder;
import com.intellij.openapi.util.Pair;
import com.intellij.psi.tree.IElementType;
import com.intellij.util.containers.ContainerUtil;
import org.jetbrains.annotations.NotNull;
import org.rustidea.parser.framework.Parser;
import org.rustidea.parser.framework.Section;

import java.util.List;

import static org.rustidea.parser.RsExprParser.literal;
import static org.rustidea.parser.RsParserUtil.*;
import static org.rustidea.parser.framework.Combinators.*;
import static org.rustidea.parser.framework.Helpers.*;
import static org.rustidea.parser.framework.Scanners.maybeToken;
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
     * <p>Singleton instance of {@link RsItemParser}. Performs greedy matching: leaves error marked if matching fails.</p>
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
        wrap(OP_LPAREN, OP_RPAREN, sep(OP_COMMA, attrItem)).mark(ATTRIBUTE_ITEM_LIST);

    /** {@code doc ::= BLOCK_DOC | LINE_DOC+} */
    public static final Parser doc =
        token(BLOCK_DOC).or(many1(token(LINE_DOC))).mark(DOC);

    /** <pre>attr ::= "#" "[" {@link #attrItem} "]"</pre> */
    public static final Parser attr =
        token(OP_HASH).then(wrap(OP_LBRACKET, OP_RBRACKET, attrItem)).mark(ATTRIBUTE);

    /** <pre>attrOrDoc ::= {@link #doc} | {@link #attr}</pre> */
    public static final Parser attrOrDoc =
        doc.or(attr);

    /** {@code parentDoc ::= BLOCK_PARENT_DOC | LINE_PARENT_DOC+} */
    public static final Parser parentDoc =
        token(BLOCK_PARENT_DOC).or(many1(token(LINE_PARENT_DOC))).mark(DOC);

    /** <pre>parentAttr ::= "#" "!" "[" {@link #attrItem} "]"</pre> */
    public static final Parser parentAttr =
        token(OP_HASH, OP_BANG).then(wrap(OP_LBRACKET, OP_RBRACKET, attrItem)).mark(ATTRIBUTE);

    /** <pre>parentAttrOrDoc ::= {@link #parentDoc} | {@link #parentAttr}</pre> */
    public static final Parser parentAttrOrDoc =
        parentDoc.or(parentAttr);

    /** <pre>modifierList ::= {@link #attr}* "pub"?</pre> */
    private static final Parser modifierList =
        many(attrOrDoc).evenThen(maybeToken(KW_PUB)).mark(MODIFIER_LIST);

    /** <pre>externCrateDecl ::= "extern" "crate" {@link RsParserUtil#identRequired} [ "as" {@link RsParserUtil#ident} ] ";"</pre> */
    private static final Parser externCrateDecl =
        seq(token(KW_EXTERN, KW_CRATE),
            identRequired,
            maybe(token(KW_AS).then(ident)),
            semicolon);

    @SuppressWarnings("unchecked") // don't care about array type
    private static final List<Pair<Parser, IElementType>> itemParsers = ContainerUtil.immutableList(
        Pair.create(externCrateDecl, (IElementType) EXTERN_CRATE_DECL)
    );

    private final boolean greedy;

    private RsItemParser(boolean greedy) {
        this.greedy = greedy;
    }

    @Override
    public boolean parse(@NotNull PsiBuilder builder) {
        Section section = Section.begin(builder);
        if (section.call(modifierList)) {
            for (Pair<Parser, IElementType> p : itemParsers) {
                if (section.callWrapped(p.getFirst())) {
                    return section.end(p.getSecond(), null);
                }
            }
            // FIXME This commented code generates infinite loop
//
//            if (greedy) {
//                builder.mark().error("expected item");
//                section.endGreedy();
//                return true; // prevent rolling back
//            }
        }
        return section.end();
    }
}
