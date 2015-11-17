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

import com.intellij.lang.PsiBuilder.Marker;
import com.intellij.openapi.diagnostic.Logger;
import com.intellij.psi.tree.TokenSet;
import org.jetbrains.annotations.NotNull;

import java.util.EnumSet;

import static com.intellij.lang.PsiBuilderUtil.expect;
import static org.rustidea.parser.RsParserUtil.*;
import static org.rustidea.psi.types.RsPsiTypes.*;

class RsModuleParser extends IRsParserBase {
    private static final Logger LOG = Logger.getInstance(RsModuleParser.class);
    private static final TokenSet LINE_OR_BLOCK_DOC = TokenSet.create(LINE_DOC, BLOCK_DOC);
    private static final TokenSet LINE_OR_BLOCK_INNER_DOC = TokenSet.create(LINE_INNER_DOC, BLOCK_INNER_DOC);

    public RsModuleParser(@NotNull final RsParser parser) {
        super(parser);
    }

    public void file() {
        while (!builder.eof()) {
            if (!(attribute(true) || item())) {
                unexpected(builder);
            }
        }
    }

    public boolean item() {
        final Marker marker = builder.mark();

        final boolean hasModifierList = modifierList();

        if (externCrateDecl()) {
            marker.done(EXTERN_CRATE_DECL);
            return true;
        }

        if (useDecl()) {
            marker.done(USE_DECL);
            return true;
        }

        if (mod()) {
            marker.done(MODULE);
            return true;
        }

        if (constItem()) {
            marker.done(CONST_ITEM);
            return true;
        }

        if (staticItem()) {
            marker.done(STATIC_ITEM);
            return true;
        }

        if (structItem()) {
            marker.done(STRUCT);
            return true;
        }

        if (typeAliasItem()) {
            marker.done(TYPE_ALIAS);
            return true;
        }

        if (hasModifierList) {
            RsParserUtil.error(builder, "expected item");
            marker.drop();
            return true;
        }

        marker.rollbackTo();
        return false;
    }

    private boolean modifierList() {
        final Marker marker = builder.mark();
        final boolean hasAttrs = attributeList();
        final boolean hasKwPub = expect(builder, KW_PUB);
        if (hasAttrs || hasKwPub) {
            marker.done(MODIFIER_LIST);
            return true;
        } else {
            marker.rollbackTo();
            return false;
        }
    }

    private boolean externCrateDecl() {
        final Marker marker = builder.mark();

        if (!expect(builder, KW_EXTERN) || !expect(builder, KW_CRATE)) {
            marker.rollbackTo();
            return false;
        }

        if (!expectOrWarn(builder, IDENTIFIER)) {
            semicolon(builder);
            marker.drop();
            return false;
        }

        if (expect(builder, KW_AS)) {
            expectOrWarn(builder, IDENTIFIER);
        }

        semicolon(builder);

        marker.drop(); // PSI element will be marked in #item()
        return true;
    }

    private boolean useDecl() {
        final Marker marker = builder.mark();

        if (!expect(builder, KW_USE) || !parser.getReferenceParser().path()) {
            marker.rollbackTo();
            return false;
        }

        if (expect(builder, KW_AS)) {
            identifier(builder);
        }

        semicolon(builder);

        marker.drop(); // PSI element will be marked in #item()
        return true;
    }

    private boolean mod() {
        final Marker marker = builder.mark();

        if (!expect(builder, KW_MOD) || !identifier(builder)) {
            marker.rollbackTo();
            return false;
        }

        //noinspection StatementWithEmptyBody
        if (expect(builder, OP_SEMICOLON)) {
            // do nothing
        } else if (expect(builder, OP_LBRACE)) {
            //noinspection StatementWithEmptyBody
            while (attribute(true) || item()) ;
            expectOrWarnMissing(builder, OP_RBRACE);
        } else {
            error(builder, "missing '{' or ';'");
        }

        marker.drop(); // PSI element will be marked in #item()
        return true;
    }

    private boolean constItem() {
        final Marker marker = builder.mark();

        if (!expect(builder, KW_CONST) || !identifier(builder)) {
            marker.rollbackTo();
            return false;
        }
        expectOrWarn(builder, OP_COLON);
        parser.getTypeParser().expectType();
        expectOrWarn(builder, OP_EQ);
        parser.getExpressionParser().expectExpression();
        semicolon(builder);

        marker.drop(); // PSI element will be marked in #item()
        return true;
    }

    private boolean staticItem() {
        final Marker marker = builder.mark();

        if (!expect(builder, KW_STATIC)) {
            marker.rollbackTo();
            return false;
        }
        expect(builder, KW_MUT);
        if (!identifier(builder)) {
            marker.rollbackTo();
            return false;
        }
        expectOrWarn(builder, OP_COLON);
        parser.getTypeParser().expectType();
        expectOrWarn(builder, OP_EQ);
        parser.getExpressionParser().expectExpression();
        semicolon(builder);

        marker.drop(); // PSI element will be marked in #item()
        return true;
    }

    private boolean structItem() {
        final Marker marker = builder.mark();

        if (!expect(builder, KW_STRUCT)) {
            marker.rollbackTo();
            return false;
        }

        if (!identifier(builder)) {
            marker.rollbackTo();
            return false;
        }

        parser.getTypeParser().typeParameterList();

        //noinspection StatementWithEmptyBody
        if (parser.getTypeParser().structureType()) {
            // - structure type: struct Foo{x: i32, y: i32}
        } else if (parser.getTypeParser().tupleType()) {
            // - tuple type: struct Foo(i32, i32);
            semicolon(builder);
        } else {
            // - unit-like struct: struct Foo;
            semicolon(builder);
        }


        marker.drop(); // PSI element will be marked in #item()
        return true;
    }

    private boolean typeAliasItem() {
        final Marker marker = builder.mark();

        if (!expect(builder, KW_TYPE)) {
            marker.rollbackTo();
            return false;
        }

        if (!identifier(builder)) {
            marker.rollbackTo();
            return false;
        }

        parser.getTypeParser().typeParameterList();

        expectOrWarn(builder, OP_EQ);
        parser.getTypeParser().expectType();
        semicolon(builder);

        marker.drop(); // PSI element will be marked in #item()
        return true;
    }

    public boolean attributeList() {
        final Marker marker = builder.mark();
        if (attribute(false)) {
            while (!builder.eof()) {
                if (!attribute(false)) break;
            }
            marker.drop();
            return true;
        } else {
            marker.rollbackTo();
            return false;
        }
    }

    public boolean attribute(boolean inner) {
        final Marker marker = builder.mark();

        if (expect(builder, inner ? LINE_OR_BLOCK_INNER_DOC : LINE_OR_BLOCK_DOC)) {
            marker.done(DOC);
            return true;
        }

        if (!expect(builder, OP_HASH)) {
            marker.rollbackTo();
            return false;
        }

        if (inner && !expect(builder, OP_BANG)) {
            marker.rollbackTo();
            return false;
        }

        expectOrWarn(builder, OP_LBRACKET);

        meta();

        expectOrWarnMissing(builder, OP_RBRACKET);

        marker.done(ATTRIBUTE);
        return true;
    }

    private boolean meta() {
        final Marker marker = builder.mark();

        if (!identifier(builder)) {
            marker.drop();
            return false;
        }

        final Marker value = builder.mark();

        if (expect(builder, OP_EQ)) {
            parser.getExpressionParser().literal();
            value.drop();
        } else if (expect(builder, OP_LPAREN)) {
            sep(builder, OP_COMMA, new ParserWrapper() {
                @Override
                public boolean parse() {
                    return meta();
                }
            }, EnumSet.of(SepCfg.TOLERATE_EMPTY));

            expectOrWarnMissing(builder, OP_RPAREN);
            value.done(META_LIST);
        } else {
            value.rollbackTo();
        }

        marker.done(META);
        return true;
    }
}
