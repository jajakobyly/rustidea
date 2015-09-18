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
import org.rustidea.psi.types.RsTypes;

import java.util.EnumSet;

import static com.intellij.psi.StringEscapesTokenTypes.*;
import static org.rustidea.lexer.RsStringLiteralLexer.ESCAPE.*;

public class RsStringLiteralLexerTest extends RsLexerTestCaseBase {
    public void testShortByteEscapes() {
        TestBuilder data = new TestBuilder()
            .add("\\\\", VALID_STRING_ESCAPE_TOKEN)
            .add("\\n", VALID_STRING_ESCAPE_TOKEN)
            .add("\\r", VALID_STRING_ESCAPE_TOKEN)
            .add("\\t", VALID_STRING_ESCAPE_TOKEN)
            .add("\\0", VALID_STRING_ESCAPE_TOKEN)
            .add("\\'", VALID_STRING_ESCAPE_TOKEN)
            .add("\\\"", VALID_STRING_ESCAPE_TOKEN);
        doTest(data);
    }

    public void testLongByteEscapes() {
        TestBuilder data = new TestBuilder()
            .add("\\x00", VALID_STRING_ESCAPE_TOKEN)
            .add("\\x7f", VALID_STRING_ESCAPE_TOKEN)
            .add("\\x7F", VALID_STRING_ESCAPE_TOKEN)
            .add("\\x2a", VALID_STRING_ESCAPE_TOKEN);
        doTest(data);
    }

    public void testSampleUnicodeEscapes() {
        TestBuilder data = new TestBuilder()
            .add("\\u{aaa}", VALID_STRING_ESCAPE_TOKEN)
            .add("\\u{7fff}", VALID_STRING_ESCAPE_TOKEN)
            .add("\\u{7FFF}", VALID_STRING_ESCAPE_TOKEN)
            .add("\\u{007fff}", VALID_STRING_ESCAPE_TOKEN);
        doTest(data);
    }

    public void testVariableLengthUnicodeEscapes() {
        TestBuilder data = new TestBuilder()
            .add("\\u{0}", VALID_STRING_ESCAPE_TOKEN)
            .add("\\u{00}", VALID_STRING_ESCAPE_TOKEN)
            .add("\\u{000}", VALID_STRING_ESCAPE_TOKEN)
            .add("\\u{0000}", VALID_STRING_ESCAPE_TOKEN)
            .add("\\u{00000}", VALID_STRING_ESCAPE_TOKEN)
            .add("\\u{000000}", VALID_STRING_ESCAPE_TOKEN)
            .add("\\u{0000000}", INVALID_UNICODE_ESCAPE_TOKEN)
            .add("\\u{00000000}", INVALID_UNICODE_ESCAPE_TOKEN);
        doTest(data);
    }

    public void testInvalidShortByteEscapes() {
        TestBuilder data = new TestBuilder()
            .add("\\a", INVALID_CHARACTER_ESCAPE_TOKEN)
            .add("\\b", INVALID_CHARACTER_ESCAPE_TOKEN)
            .add("\\c", INVALID_CHARACTER_ESCAPE_TOKEN);
        doTest(data);
    }

    public void testInvalidLongByteEscapes() {
        TestBuilder data = new TestBuilder()
            .add("\\xaa", INVALID_CHARACTER_ESCAPE_TOKEN)
            .add("\\x80", INVALID_CHARACTER_ESCAPE_TOKEN)
            .add("\\xa", INVALID_CHARACTER_ESCAPE_TOKEN)
            .add("\\x9", INVALID_CHARACTER_ESCAPE_TOKEN)
            .add("\\xz", INVALID_CHARACTER_ESCAPE_TOKEN)
            .add("\\xzz", INVALID_CHARACTER_ESCAPE_TOKEN);
        doTest(data);
    }

    public void testInvalidUnicodeEscapes() {
        TestBuilder data = new TestBuilder()
            .add("\\u{7fffff}", INVALID_UNICODE_ESCAPE_TOKEN)
            .add("\\u{000000000}", INVALID_UNICODE_ESCAPE_TOKEN)
            .add("\\u{}", INVALID_UNICODE_ESCAPE_TOKEN)
            .add("\\u{800000}", INVALID_UNICODE_ESCAPE_TOKEN);
        doTest(data);
    }

    public void testMixed() {
        TestBuilder data = new TestBuilder("foo\\x20bar\\u{}")
            .add("foo", RsTypes.STRING_LIT)
            .add("\\x20", VALID_STRING_ESCAPE_TOKEN)
            .add("bar", RsTypes.STRING_LIT)
            .add("\\u{}", INVALID_UNICODE_ESCAPE_TOKEN);
        doTest(data);
    }

    public void testMixedQuoted() {
        TestBuilder data = new TestBuilder("\"foo\\u{aaa}\"")
            .add("\"foo", RsTypes.STRING_LIT)
            .add("\\u{aaa}", VALID_STRING_ESCAPE_TOKEN)
            .add("\"", RsTypes.STRING_LIT);
        doTest(data);
    }

    @Override
    protected Lexer createLexer() {
        return new RsStringLiteralLexer(RsTypes.STRING_LIT, EnumSet.of(BYTE_ESCAPE, UNICODE_ESCAPE, EOL_ESCAPE));
    }
}
