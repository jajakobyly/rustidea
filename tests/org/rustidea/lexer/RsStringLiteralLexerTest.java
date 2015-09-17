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

import com.intellij.lexer.Lexer;
import com.intellij.testFramework.LexerTestCase;
import org.rustidea.psi.types.RsTypes;
import org.rustidea.testUtils.LexerTestKeyBuilder;

import java.util.EnumSet;

import static com.intellij.psi.StringEscapesTokenTypes.*;
import static org.rustidea.lexer.RsStringLiteralLexer.ESCAPE.*;

public class RsStringLiteralLexerTest extends LexerTestCase {
    public void testShortByteEscapes() {
        doTest(
            "\\\\\\n\\r\\t\\0\\'\\\"",
            new LexerTestKeyBuilder()
                .add(VALID_STRING_ESCAPE_TOKEN, "\\\\")
                .add(VALID_STRING_ESCAPE_TOKEN, "\\n")
                .add(VALID_STRING_ESCAPE_TOKEN, "\\r")
                .add(VALID_STRING_ESCAPE_TOKEN, "\\t")
                .add(VALID_STRING_ESCAPE_TOKEN, "\\0")
                .add(VALID_STRING_ESCAPE_TOKEN, "\\'")
                .add(VALID_STRING_ESCAPE_TOKEN, "\\\"")
                .toString()
        );
    }

    public void testLongByteEscapes() {
        doTest(
            "\\x00\\x7f\\x7F\\x2a",
            new LexerTestKeyBuilder()
                .add(VALID_STRING_ESCAPE_TOKEN, "\\x00")
                .add(VALID_STRING_ESCAPE_TOKEN, "\\x7f")
                .add(VALID_STRING_ESCAPE_TOKEN, "\\x7F")
                .add(VALID_STRING_ESCAPE_TOKEN, "\\x2a")
                .toString()
        );
    }

    public void testSampleUnicodeEscapes() {
        doTest(
            "\\u{aaa}\\u{7fff}\\u{7FFF}\\u{007fff}",
            new LexerTestKeyBuilder()
                .add(VALID_STRING_ESCAPE_TOKEN, "\\u{aaa}")
                .add(VALID_STRING_ESCAPE_TOKEN, "\\u{7fff}")
                .add(VALID_STRING_ESCAPE_TOKEN, "\\u{7FFF}")
                .add(VALID_STRING_ESCAPE_TOKEN, "\\u{007fff}")
                .toString()
        );
    }

    public void testVariableLengthUnicodeEscapes() {
        doTest(
            "\\u{0}\\u{00}\\u{000}\\u{0000}\\u{00000}\\u{000000}",
            new LexerTestKeyBuilder()
                .add(VALID_STRING_ESCAPE_TOKEN, "\\u{0}")
                .add(VALID_STRING_ESCAPE_TOKEN, "\\u{00}")
                .add(VALID_STRING_ESCAPE_TOKEN, "\\u{000}")
                .add(VALID_STRING_ESCAPE_TOKEN, "\\u{0000}")
                .add(VALID_STRING_ESCAPE_TOKEN, "\\u{00000}")
                .add(VALID_STRING_ESCAPE_TOKEN, "\\u{000000}")
                .toString()
        );
    }

    public void testInvalidShortByteEscapes() {
        doTest(
            "\\a\\b\\c",
            new LexerTestKeyBuilder()
                .add(INVALID_CHARACTER_ESCAPE_TOKEN, "\\a")
                .add(INVALID_CHARACTER_ESCAPE_TOKEN, "\\b")
                .add(INVALID_CHARACTER_ESCAPE_TOKEN, "\\c")
                .toString()
        );
    }

    public void testInvalidLongByteEscapes() {
        doTest(
            "\\xaa\\x80\\\\xa\\x9\\xz\\xzz",
            new LexerTestKeyBuilder()
                .add(INVALID_CHARACTER_ESCAPE_TOKEN, "\\xaa")
                .add(INVALID_CHARACTER_ESCAPE_TOKEN, "\\x80")
                .add(INVALID_CHARACTER_ESCAPE_TOKEN, "\\xa")
                .add(INVALID_CHARACTER_ESCAPE_TOKEN, "\\x9")
                .add(INVALID_CHARACTER_ESCAPE_TOKEN, "\\xz")
                .add(INVALID_CHARACTER_ESCAPE_TOKEN, "\\xzz")
                .toString()
        );
    }

    public void testInvalidUnicodeEscapes() {
        doTest(
            "\\u{7fffff}\\u{000000000}\\u{}\\u{800000}",
            new LexerTestKeyBuilder()
                .add(INVALID_UNICODE_ESCAPE_TOKEN, "\\u{7fffff}")
                .add(INVALID_UNICODE_ESCAPE_TOKEN, "\\u{000000000}")
                .add(INVALID_UNICODE_ESCAPE_TOKEN, "\\u{}")
                .add(INVALID_UNICODE_ESCAPE_TOKEN, "\\u{800000}")
                .toString()
        );
    }

    @Override
    protected Lexer createLexer() {
        return new RsStringLiteralLexer(RsTypes.STRING_LIT, EnumSet.of(BYTE_ESCAPE, UNICODE_ESCAPE, EOL_ESCAPE));
    }

    @Override
    protected String getDirPath() {
        throw new UnsupportedOperationException("should not be called");
    }
}
