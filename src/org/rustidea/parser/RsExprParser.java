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

import static org.rustidea.parser.framework.Scanners.token;
import static org.rustidea.psi.types.RsCompositeTypes.LITERAL;
import static org.rustidea.psi.types.RsTypes.LITERAL_TOKEN_SET;

public final class RsExprParser {
    /**
     * {@code literalRequired ::= BIN_LIT | DEC_LIT | FLOAT_LIT | HEX_LIT | OCT_LIT | BYTE_LIT | CHAR_LIT | BYTE_STRING_LIT | STRING_LIT | RAW_BYTE_STRING_LIT | RAW_STRING_LIT}
     *
     * <p>Fails if parsing failed.</p>
     *
     * @see #literal
     */
    public static final Parser literalRequired =
        token(LITERAL_TOKEN_SET).markGreedy(LITERAL).fail("expected literal");

    /**
     * {@code literal ::= BIN_LIT | DEC_LIT | FLOAT_LIT | HEX_LIT | OCT_LIT | BYTE_LIT | CHAR_LIT | BYTE_STRING_LIT | STRING_LIT | RAW_BYTE_STRING_LIT | RAW_STRING_LIT}
     *
     * <p>Warns if parsing fails.</p>
     *
     * @see #literalRequired
     */
    public static final Parser literal =
        token(LITERAL_TOKEN_SET).markGreedy(LITERAL).warn("expected literal");

    private RsExprParser() {
    }
}
