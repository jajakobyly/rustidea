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

package org.rustidea.psi.types;

import com.intellij.psi.tree.TokenSet;

public interface RsPsiTypes extends RsStubElementTypes, RsCompositeTypes, RsTokenTypes {
    TokenSet KEYWORD_TOKEN_SET = TokenSet.create(KW_ABSTRACT, KW_ALIGNOF, KW_AS, KW_BECOME, KW_BOX, KW_BREAK, KW_CONST, KW_CONTINUE, KW_CRATE, KW_DO, KW_ELSE, KW_ENUM, KW_EXTERN, KW_FALSE, KW_FINAL, KW_FN, KW_FOR, KW_IF, KW_IMPL, KW_IN, KW_LET, KW_LOOP, KW_MACRO, KW_MATCH, KW_MOD, KW_MOVE, KW_MUT, KW_OFFSETOF, KW_OVERRIDE, KW_PRIV, KW_PROC, KW_PUB, KW_PURE, KW_REF, KW_RETURN, KW_SELF, KW_SELF_T, KW_SIZEOF, KW_STATIC, KW_STRUCT, KW_SUPER, KW_TRAIT, KW_TRUE, KW_TYPE, KW_TYPEOF, KW_UNSAFE, KW_UNSIZED, KW_USE, KW_VIRTUAL, KW_WHERE, KW_WHILE, KW_YIELD);

    TokenSet OPERATOR_TOKEN_SET = TokenSet.create(OP_AND, OP_ANDAND, OP_ANDEQ, OP_ARROW, OP_ASTERISK, OP_ASTERISKEQ, OP_AT, OP_BANG, OP_COLON, OP_COMMA, OP_DIV, OP_DIVEQ, OP_DOLLAR, OP_DOT, OP_DOTDOT, OP_DOUBLE_COLON, OP_ELLIPSIS, OP_EQ, OP_EQEQ, OP_FAT_ARROW, OP_GT, OP_GTEQ, OP_HASH, OP_LBRACE, OP_LBRACKET, OP_LPAREN, OP_LT, OP_LTEQ, OP_MINUS, OP_MINUSEQ, OP_NE, OP_OR, OP_OREQ, OP_OROR, OP_PLUS, OP_PLUSEQ, OP_RBRACE, OP_RBRACKET, OP_REM, OP_REMEQ, OP_RPAREN, OP_SEMICOLON, OP_SHL, OP_SHLEQ, OP_SHR, OP_SHREQ, OP_UNDERSCORE, OP_XOR, OP_XOREQ);

    TokenSet WHITE_SPACE_TOKEN_SET = TokenSet.create(WHITE_SPACE);
    TokenSet COMMENT_TOKEN_SET = TokenSet.create(BLOCK_COMMENT, LINE_COMMENT);

    TokenSet BRACE_TOKEN_SET = TokenSet.create(OP_LBRACE, OP_RBRACE);
    TokenSet BRACKET_TOKEN_SET = TokenSet.create(OP_LBRACKET, OP_RBRACKET);
    TokenSet PAREN_TOKEN_SET = TokenSet.create(OP_LPAREN, OP_RPAREN);

    TokenSet BOOL_TOKEN_SET = TokenSet.create(KW_TRUE, KW_FALSE);
    TokenSet NUMBER_TOKEN_SET = TokenSet.create(INT_LIT, FLOAT_LIT);
    TokenSet CHAR_TOKEN_SET = TokenSet.create(CHAR_LIT, BYTE_LIT);
    TokenSet STRING_TOKEN_SET = TokenSet.create(STRING_LIT, BYTE_STRING_LIT);
    TokenSet RAW_STRING_TOKEN_SET = TokenSet.create(RAW_STRING_LIT, RAW_BYTE_STRING_LIT);
    TokenSet STRING_LITERAL_TOKEN_SET = TokenSet.orSet(STRING_TOKEN_SET, RAW_STRING_TOKEN_SET);
    TokenSet LITERAL_TOKEN_SET = TokenSet.orSet(BOOL_TOKEN_SET, NUMBER_TOKEN_SET, CHAR_TOKEN_SET, STRING_LITERAL_TOKEN_SET);

    TokenSet DOC_TOKEN_SET = TokenSet.create(BLOCK_DOC, LINE_DOC, BLOCK_PARENT_DOC, LINE_PARENT_DOC);
    TokenSet ATTRIBUTE_OR_DOC_TOKEN_SET = TokenSet.create(ATTRIBUTE, DOC);

    TokenSet SELF_OR_SUPER = TokenSet.create(KW_SELF, KW_SUPER);
}
