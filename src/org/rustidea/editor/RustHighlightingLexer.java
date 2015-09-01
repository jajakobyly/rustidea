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

package org.rustidea.editor;

import com.intellij.lexer.LayeredLexer;
import com.intellij.psi.tree.IElementType;
import org.rustidea.parser.RustLexer;
import org.rustidea.parser.RustStringLiteralLexer;
import org.rustidea.psi.types.RustTypes;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;

import static org.rustidea.parser.RustStringLiteralLexer.ESCAPE.*;

public class RustHighlightingLexer extends LayeredLexer {
    private static final Map<IElementType, EnumSet<RustStringLiteralLexer.ESCAPE>> STRING_LAYERS = new HashMap<IElementType, EnumSet<RustStringLiteralLexer.ESCAPE>>();

    static {
        STRING_LAYERS.put(RustTypes.CHAR_LIT, EnumSet.of(BYTE_ESCAPE, UNICODE_ESCAPE));
        STRING_LAYERS.put(RustTypes.BYTE_LIT, EnumSet.of(BYTE_ESCAPE));
        STRING_LAYERS.put(RustTypes.STRING_LIT, EnumSet.of(BYTE_ESCAPE, UNICODE_ESCAPE, EOL_ESCAPE));
        STRING_LAYERS.put(RustTypes.BYTE_STRING_LIT, EnumSet.of(BYTE_ESCAPE, EOL_ESCAPE));
    }

    public RustHighlightingLexer() {
        super(new RustLexer());
        for (Map.Entry<IElementType, EnumSet<RustStringLiteralLexer.ESCAPE>> layer : STRING_LAYERS.entrySet()) {
            IElementType literal = layer.getKey();
            EnumSet<RustStringLiteralLexer.ESCAPE> escapes = layer.getValue();
            registerLayer(new RustStringLiteralLexer(literal, escapes), literal);
        }
    }
}
