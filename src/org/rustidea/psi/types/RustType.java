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

public interface RustType extends RustStubElementTypes, RustCompositeType, RustTokenType {
    TokenSet KEYWORD_TOKEN_SET = TokenSet.create(KW_ABSTRACT, KW_ALIGNOF, KW_AS, KW_BECOME, KW_BOX, KW_BREAK, KW_CONST, KW_CONTINUE, KW_CRATE, KW_DO, KW_ELSE, KW_ENUM, KW_EXTERN, KW_FALSE, KW_FINAL, KW_FN, KW_FOR, KW_IF, KW_IMPL, KW_IN, KW_LET, KW_LOOP, KW_MACRO, KW_MATCH, KW_MOD, KW_MOVE, KW_MUT, KW_OFFSETOF, KW_OVERRIDE, KW_PRIV, KW_PROC, KW_PUB, KW_PURE, KW_REF, KW_RETURN, KW_SELF, KW_SELF_T, KW_SIZEOF, KW_STATIC, KW_STRUCT, KW_SUPER, KW_TRAIT, KW_TRUE, KW_TYPE, KW_TYPEOF, KW_UNSAFE, KW_UNSIZED, KW_USE, KW_VIRTUAL, KW_WHERE, KW_WHILE, KW_YIELD);

    TokenSet OPERATION_TOKEN_SET = TokenSet.create(OP_AMPERSAND, OP_ARROW, OP_ASSIGN, OP_ASTERISK, OP_AT, OP_CARET, OP_COLON, OP_DOLLAR, OP_DOUBLE_AMPERSAND, OP_DOUBLE_COLON, OP_DOUBLE_DOT, OP_DOUBLE_PIPE, OP_EQUALS, OP_EXCLAMATION_MARK, OP_FAT_ARROW, OP_GREATER_THAN, OP_GREATER_THAN_EQ, OP_HASH, OP_LEFT_SHIFT, OP_LESS_THAN, OP_LESS_THAN_EQ, OP_MINUS, OP_NOT_EQUALS, OP_PERCENT, OP_PIPE, OP_PLUS, OP_RIGHT_SHIFT, OP_SLASH, OP_TRIPLE_DOT, OP_UNDERSCORE);

    TokenSet BRACE_TOKEN_SET = TokenSet.create(OP_OPEN_BRACE, OP_CLOSE_BRACE);
    TokenSet BRACKET_TOKEN_SET = TokenSet.create(OP_OPEN_BRACKET, OP_CLOSE_BRACKET);
    TokenSet PAREN_TOKEN_SET = TokenSet.create(OP_OPEN_PAREN, OP_CLOSE_PAREN);

    TokenSet NUMBER_TOKEN_SET = TokenSet.create(DEC_LIT, BIN_LIT, OCT_LIT, HEX_LIT, FLOAT_LIT);
    TokenSet CHAR_TOKEN_SET = TokenSet.create(CHAR_LIT, BYTE_LIT);
    TokenSet STRING_TOKEN_SET = TokenSet.create(STRING_LIT, BYTE_STRING_LIT);
    TokenSet RAW_STRING_TOKEN_SET = TokenSet.create(RAW_STRING_LIT, RAW_BYTE_STRING_LIT);

    TokenSet WHITE_SPACE_TOKEN_SET = TokenSet.create(WHITE_SPACE);
    TokenSet COMMENT_TOKEN_SET = TokenSet.create(BLOCK_COMMENT, LINE_COMMENT);
    TokenSet STRING_LITERAL_TOKEN_SET = TokenSet.orSet(STRING_TOKEN_SET, RAW_STRING_TOKEN_SET);

    TokenSet DOC_TOKEN_SET = TokenSet.create(BLOCK_DOC, LINE_DOC);
    TokenSet PARENT_DOC_TOKEN_SET = TokenSet.create(BLOCK_PARENT_DOC, LINE_PARENT_DOC);
}
