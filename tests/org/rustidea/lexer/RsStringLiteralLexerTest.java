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
import org.jetbrains.annotations.NotNull;
import org.rustidea.psi.types.RsTypes;

import java.util.EnumSet;

import static com.intellij.psi.StringEscapesTokenTypes.*;
import static org.rustidea.lexer.RsStringLiteralLexer.ESCAPE.*;

public class RsStringLiteralLexerTest extends IRsLexerTestCase {
    public void testShortByteEscapes() {
        CompositeTest data = new CompositeTest()
            .test("\\\\", VALID_STRING_ESCAPE_TOKEN)
            .test("\\n", VALID_STRING_ESCAPE_TOKEN)
            .test("\\r", VALID_STRING_ESCAPE_TOKEN)
            .test("\\t", VALID_STRING_ESCAPE_TOKEN)
            .test("\\0", VALID_STRING_ESCAPE_TOKEN)
            .test("\\'", VALID_STRING_ESCAPE_TOKEN)
            .test("\\\"", VALID_STRING_ESCAPE_TOKEN);
        doTest(data);
    }

    public void testLongByteEscapes() {
        CompositeTest data = new CompositeTest()
            .test("\\x00", VALID_STRING_ESCAPE_TOKEN)
            .test("\\x7f", VALID_STRING_ESCAPE_TOKEN)
            .test("\\x7F", VALID_STRING_ESCAPE_TOKEN)
            .test("\\x2a", VALID_STRING_ESCAPE_TOKEN);
        doTest(data);
    }

    public void testSampleUnicodeEscapes() {
        CompositeTest data = new CompositeTest()
            .test("\\u{aaa}", VALID_STRING_ESCAPE_TOKEN)
            .test("\\u{7fff}", VALID_STRING_ESCAPE_TOKEN)
            .test("\\u{7FFF}", VALID_STRING_ESCAPE_TOKEN)
            .test("\\u{007fff}", VALID_STRING_ESCAPE_TOKEN);
        doTest(data);
    }

    public void testVariableLengthUnicodeEscapes() {
        CompositeTest data = new CompositeTest()
            .test("\\u{0}", VALID_STRING_ESCAPE_TOKEN)
            .test("\\u{00}", VALID_STRING_ESCAPE_TOKEN)
            .test("\\u{000}", VALID_STRING_ESCAPE_TOKEN)
            .test("\\u{0000}", VALID_STRING_ESCAPE_TOKEN)
            .test("\\u{00000}", VALID_STRING_ESCAPE_TOKEN)
            .test("\\u{000000}", VALID_STRING_ESCAPE_TOKEN)
            .test("\\u{0000000}", INVALID_UNICODE_ESCAPE_TOKEN)
            .test("\\u{00000000}", INVALID_UNICODE_ESCAPE_TOKEN);
        doTest(data);
    }

    public void testInvalidShortByteEscapes() {
        CompositeTest data = new CompositeTest()
            .test("\\a", INVALID_CHARACTER_ESCAPE_TOKEN)
            .test("\\b", INVALID_CHARACTER_ESCAPE_TOKEN)
            .test("\\c", INVALID_CHARACTER_ESCAPE_TOKEN);
        doTest(data);
    }

    public void testInvalidLongByteEscapes() {
        CompositeTest data = new CompositeTest()
            .test("\\xaa", INVALID_CHARACTER_ESCAPE_TOKEN)
            .test("\\x80", INVALID_CHARACTER_ESCAPE_TOKEN)
            .test("\\xa", INVALID_CHARACTER_ESCAPE_TOKEN)
            .test("\\x9", INVALID_CHARACTER_ESCAPE_TOKEN)
            .test("\\xz", INVALID_CHARACTER_ESCAPE_TOKEN)
            .test("\\xzz", INVALID_CHARACTER_ESCAPE_TOKEN);
        doTest(data);
    }

    public void testInvalidUnicodeEscapes() {
        CompositeTest data = new CompositeTest()
            .test("\\u{7fffff}", INVALID_UNICODE_ESCAPE_TOKEN)
            .test("\\u{000000000}", INVALID_UNICODE_ESCAPE_TOKEN)
            .test("\\u{}", INVALID_UNICODE_ESCAPE_TOKEN)
            .test("\\u{800000}", INVALID_UNICODE_ESCAPE_TOKEN);
        doTest(data);
    }

    public void testMixed() {
        CompositeTest data = new CompositeTest("foo\\x20bar\\u{}")
            .test("foo", RsTypes.STRING_LIT)
            .test("\\x20", VALID_STRING_ESCAPE_TOKEN)
            .test("bar", RsTypes.STRING_LIT)
            .test("\\u{}", INVALID_UNICODE_ESCAPE_TOKEN);
        doTest(data);
    }

    public void testMixedQuoted() {
        CompositeTest data = new CompositeTest("\"foo\\u{aaa}\"")
            .test("\"foo", RsTypes.STRING_LIT)
            .test("\\u{aaa}", VALID_STRING_ESCAPE_TOKEN)
            .test("\"", RsTypes.STRING_LIT);
        doTest(data);
    }

    @NotNull
    @Override
    protected Lexer createLexer() {
        return new RsStringLiteralLexer(RsTypes.STRING_LIT, EnumSet.of(BYTE_ESCAPE, UNICODE_ESCAPE, EOL_ESCAPE));
    }
}
