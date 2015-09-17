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

// https://doc.rust-lang.org/nightly/reference.html#literals

package org.rustidea.lexer;

import com.intellij.lexer.FlexLexer;
import com.intellij.psi.tree.IElementType;

import java.util.EnumSet;

import static org.rustidea.lexer.RsStringLiteralLexer.ESCAPE;
import static com.intellij.psi.StringEscapesTokenTypes.VALID_STRING_ESCAPE_TOKEN;
import static com.intellij.psi.StringEscapesTokenTypes.INVALID_CHARACTER_ESCAPE_TOKEN;
import static com.intellij.psi.StringEscapesTokenTypes.INVALID_UNICODE_ESCAPE_TOKEN;


%%


%{
    private IElementType defaultToken = null;
    private EnumSet<ESCAPE> escapes = null;

    private static final int MAX_BYTE = Integer.decode("0x7f");
    private static final int MAX_UNICODE = Integer.decode("0x10ffff"); // found by divide & conquer

    public _RsStringLiteralLexer(IElementType defaultToken, EnumSet<ESCAPE> escapes) {
        this((java.io.Reader)null);
        this.defaultToken = defaultToken;
        this.escapes = escapes;
    }

    private IElementType esc(ESCAPE escape) {
        return (escapes.contains(escape) ? VALID_STRING_ESCAPE_TOKEN : INVALID_CHARACTER_ESCAPE_TOKEN);
    }
%}

%class _RsStringLiteralLexer
%implements FlexLexer
%unicode
%function advance
%type IElementType


EOL = \n | \r | \r\n
HEX = [a-fA-F0-9]


%%


<YYINITIAL> {
    // \" \' \0 are undocumented
    "\\" [nrt\\0'\"] { return esc(ESCAPE.BYTE_ESCAPE); }

    "\\x" {HEX}{2} {
        if(Integer.decode("0x" + yytext().subSequence(2, yylength())) > MAX_BYTE) {
            return INVALID_CHARACTER_ESCAPE_TOKEN;
        }

        return esc(ESCAPE.BYTE_ESCAPE);
    }

    "\\u{" {HEX}{1,6} "}" {
        if(Integer.decode("0x" + yytext().subSequence(3, yylength() - 1)) > MAX_UNICODE) {
            return INVALID_UNICODE_ESCAPE_TOKEN;
        }

        return esc(ESCAPE.UNICODE_ESCAPE);
    }

    "\\" {EOL} { return esc(ESCAPE.EOL_ESCAPE); }

    "\\x" ([:letter:] | [:digit:])+ |
    "\\" [^]                        { return INVALID_CHARACTER_ESCAPE_TOKEN; }

    "\\u{" ([:letter:] | [:digit:])* "}" { return INVALID_UNICODE_ESCAPE_TOKEN; }

    [^]      { return defaultToken; }
}
