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

import static com.intellij.psi.TokenType.WHITE_SPACE;
import static org.rustidea.psi.types.RsTokenTypes.*;

public class RsLexerTest extends IRsLexerTestCase {
    public void testUnfinishedCharacters() {
        MultiTest test = new MultiTest()
            .test("'", CHAR_LIT)
            .test("''", CHAR_LIT)
            .test("'\\", CHAR_LIT);
        doTest(test);
    }

    public void testUnfinishedStrings() {
        MultiTest test = new MultiTest()
            .test("\"", STRING_LIT)
            .test("\"a", STRING_LIT)
            .test("\"\\", STRING_LIT);
        doTest(test);
    }

    public void testUnfinishedCharAndLifetimeConflicts() {
        MultiTest test = new MultiTest()
            .test("'", CHAR_LIT)
            .test("'a", LIFETIME_TOKEN)
            .test("'1", CHAR_LIT)
            .test("'aaaa", LIFETIME_TOKEN)
            .test("'a'", CHAR_LIT);
        doTest(test);
    }

    // Following tests are based on Rust's test suite
    // https://github.com/rust-lang/rust/blob/405c616eaf4e58a8bed67924c364c8e9c83b2581/src/libsyntax/parse/lexer/mod.rs

    public void testRust1() {
        CompositeTest test = new CompositeTest(
            "/* my source file */ ",
            "fn main() { println!(\"zebra\"); }",
            "")
            .test("/* my source file */", BLOCK_COMMENT)
            .test(" \n", WHITE_SPACE)
            .test("fn", KW_FN)
            .test(" ", WHITE_SPACE)
            .test("main", IDENTIFIER)
            .test("(", OP_LPAREN)
            .test(")", OP_RPAREN)
            .test(" ", WHITE_SPACE)
            .test("{", OP_LBRACE)
            .test(" ", WHITE_SPACE)
            .test("println", IDENTIFIER)
            .test("!", OP_BANG)
            .test("(", OP_LPAREN)
            .test("\"zebra\"", STRING_LIT)
            .test(")", OP_RPAREN)
            .test(";", OP_SEMICOLON)
            .test(" ", WHITE_SPACE)
            .test("}", OP_RBRACE)
            .test("\n", WHITE_SPACE);
        doTest(test);
    }

    public void testRustCharacters() {
        MultiTest test = new MultiTest()
            .test("'a'", CHAR_LIT)
            .test("' '", CHAR_LIT)
            .test("'\\n'", CHAR_LIT);
        doTest(test);
    }

    public void testRustLifetimeName() {
        doTest("'abc", LIFETIME_TOKEN);
    }

    public void testRustRawString() {
        doTest("r###\"\"a\\b\12\"\"###", RAW_STRING_LIT);
    }

    public void testRustLiteralSuffixes() {
        MultiTest test = new MultiTest()
            .test("'a'", CHAR_LIT)
            .test("b'a'", BYTE_LIT)
            .test("\"a\"", STRING_LIT)
            .test("b\"a\"", BYTE_STRING_LIT)
            .test("1234", DEC_LIT)
            .test("0b101", BIN_LIT)
            .test("0xABC", HEX_LIT)
            .test("1.0", FLOAT_LIT)
            .test("1.0e10", FLOAT_LIT)
            .test("2us", DEC_LIT)
            .test("r###\"raw\"###suffix", RAW_STRING_LIT)
            .test("br###\"raw\"###suffix", RAW_BYTE_STRING_LIT);
        doTest(test);
    }

    public void testRustLineDocComments() {
        MultiTest test = new MultiTest()
            .test("///", LINE_DOC)
            .test("/// blah", LINE_DOC)
            .test("////", LINE_COMMENT)
            .test("///////", LINE_COMMENT);
        doTest(test);
    }

    public void testRustNestedBlockComments() {
        CompositeTest test = new CompositeTest("/* /* */ */'a'")
            .test("/* /* */ */", BLOCK_COMMENT)
            .test("'a'", CHAR_LIT);
        doTest(test);
    }

    public void testRustCrlfComments() {
        CompositeTest test = new CompositeTest("// test\r\n/// test\r\n")
            .test("// test", LINE_COMMENT)
            .test("\r\n", WHITE_SPACE)
            .test("/// test", LINE_DOC)
            .test("\r\n", WHITE_SPACE);
        doTest(test);
    }

    @Override
    protected Lexer createLexer() {
        return new RsLexer();
    }
}
