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
import static org.rustidea.psi.types.RsTokenTypes.OP_SEMICOLON;
import static org.rustidea.psi.types.RsTypes.IDENTIFIER;

public final class RsParserUtil {
    /**
     * <pre>ident ::= IDENTIFIER</pre>
     */
    public static final Parser ident = token(IDENTIFIER).fail("expected identifier");

    /**
     * <pre>semicolon ::= ";"</pre>
     */
    public static final Parser semicolon = token(OP_SEMICOLON).warn("missing semicolon");

    private RsParserUtil() {
    }
}
