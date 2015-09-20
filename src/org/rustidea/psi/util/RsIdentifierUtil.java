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

package org.rustidea.psi.util;

import org.jetbrains.annotations.Contract;

public final class RsIdentifierUtil {
    private RsIdentifierUtil() {
    }

    @Contract(pure = true)
    public static boolean isRustIdentifierStart(final char ch) {
        // XID_START ::= [[:L:][:Nl:][:Other_ID_Start:]--[:Pattern_Syntax:]--[:Pattern_White_Space:]]
        // FIXME Does not comply in 100% with spec
        return Character.isLetter(ch);
    }

    @Contract(pure = true)
    public static boolean isRustIdentifierPart(final char ch) {
        // XID_CONTINUE ::= [[:ID_Start:][:Mn:][:Mc:][:Nd:][:Pc:][:Other_ID_Continue:]--[:Pattern_Syntax:]--[:Pattern_White_Space:]]
        // FIXME Does not comply in 100% with spec
        return Character.isLetterOrDigit(ch) || ch == '_';
    }
}
