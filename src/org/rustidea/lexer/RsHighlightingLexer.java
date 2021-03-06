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

package org.rustidea.lexer;

import com.google.common.collect.ImmutableMap;
import com.intellij.lexer.LayeredLexer;
import com.intellij.psi.tree.IElementType;
import org.rustidea.psi.types.RsPsiTypes;

import java.util.EnumSet;
import java.util.Map;

import static org.rustidea.lexer.RsStringLiteralLexer.ESCAPE.*;

public class RsHighlightingLexer extends LayeredLexer {
    private static final Map<IElementType, EnumSet<RsStringLiteralLexer.ESCAPE>> STRING_LAYERS =
        new ImmutableMap.Builder<IElementType, EnumSet<RsStringLiteralLexer.ESCAPE>>()
            .put(RsPsiTypes.CHAR_LIT, EnumSet.of(BYTE_ESCAPE, UNICODE_ESCAPE))
            .put(RsPsiTypes.BYTE_LIT, EnumSet.of(BYTE_ESCAPE))
            .put(RsPsiTypes.STRING_LIT, EnumSet.of(BYTE_ESCAPE, UNICODE_ESCAPE, EOL_ESCAPE))
            .put(RsPsiTypes.BYTE_STRING_LIT, EnumSet.of(BYTE_ESCAPE, EOL_ESCAPE))
            .build();

    public RsHighlightingLexer() {
        super(new RsLexer());
        for (Map.Entry<IElementType, EnumSet<RsStringLiteralLexer.ESCAPE>> layer : STRING_LAYERS.entrySet()) {
            IElementType literal = layer.getKey();
            EnumSet<RsStringLiteralLexer.ESCAPE> escapes = layer.getValue();
            registerLayer(new RsStringLiteralLexer(literal, escapes), literal);
        }
    }
}
