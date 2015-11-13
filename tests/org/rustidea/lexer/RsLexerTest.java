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

import static com.intellij.psi.TokenType.WHITE_SPACE;
import static org.rustidea.psi.types.RsTokenTypes.*;

public class RsLexerTest extends IRsLexerTestCase {
    // Tests prefixed with "testRust" are based on Rust's test suite
    // https://github.com/rust-lang/rust/blob/405c616eaf4e58a8bed67924c364c8e9c83b2581/src/libsyntax/parse/lexer/mod.rs

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
            .test("'a", PRIM_IDENT)
            .test("'1", CHAR_LIT)
            .test("'aaaa", PRIM_IDENT)
            .test("'a'", CHAR_LIT);
        doTest(test);
    }

    public void testRawStringWithTooManyHashes() {
        CompositeTest test = new CompositeTest(
            "r###\"foo\"####")
            .test("r###\"foo\"###", RAW_STRING_LIT)
            .test("#", OP_HASH);
        doTest(test);
    }

    public void testRawStringWithTooManyHashes2() {
        CompositeTest test = new CompositeTest(
            "r###\"foo\"######")
            .test("r###\"foo\"###", RAW_STRING_LIT)
            .test("#", OP_HASH)
            .test("#", OP_HASH)
            .test("#", OP_HASH);
        doTest(test);
    }

    public void testRawStringWithTooManyHashes3() {
        CompositeTest test = new CompositeTest(
            "r\"foo\"###")
            .test("r\"foo\"", RAW_STRING_LIT)
            .test("#", OP_HASH)
            .test("#", OP_HASH)
            .test("#", OP_HASH);
        doTest(test);
    }

    public void testRawStringWithTooManyHashesAndSuffix() {
        CompositeTest test = new CompositeTest(
            "r\"foo\"###suffix")
            .test("r\"foo\"", RAW_STRING_LIT)
            .test("#", OP_HASH)
            .test("#", OP_HASH)
            .test("#", OP_HASH)
            .test("suffix", IDENTIFIER);
        doTest(test);
    }

    public void testRawStringWithTooFewHashesAtEOF() {
        MultiTest test = new MultiTest()
            .test("r###\"foo", RAW_STRING_LIT)
            .test("r###\"foo\"", RAW_STRING_LIT)
            .test("r###\"foo\"##", RAW_STRING_LIT);
        doTest(test);
    }

    // https://github.com/rust-lang/rust/issues/7048
    public void testFlipTable() {
        CompositeTest test = new CompositeTest(
            "fn _\u256f\u00b0\u25a1\u00b0\u256f\ufe35\u253b\u2501\u253b() {}")
            .test("fn", KW_FN)
            .test(" ", WHITE_SPACE)
            .test("_", OP_UNDERSCORE)
            .test("\u256f", BAD_CHARACTER)
            .test("\u00b0", BAD_CHARACTER)
            .test("\u25a1", BAD_CHARACTER)
            .test("\u00b0", BAD_CHARACTER)
            .test("\u256f", BAD_CHARACTER)
            .test("\ufe35", BAD_CHARACTER)
            .test("\u253b", BAD_CHARACTER)
            .test("\u2501", BAD_CHARACTER)
            .test("\u253b", BAD_CHARACTER)
            .test("(", OP_LPAREN)
            .test(")", OP_RPAREN)
            .test(" ", WHITE_SPACE)
            .test("{", OP_LBRACE)
            .test("}", OP_RBRACE);
        doTest(test);
    }

    public void testMultilineLineDoc() {
        CompositeTest test = new CompositeTest(
            "/// foo",
            "/// bar",
            "/// baz")
            .test("/// foo\n/// bar\n/// baz", LINE_DOC);
        doTest(test);
    }

    public void testMultilineLineDoc2() {
        CompositeTest test = new CompositeTest(
            "//! foo",
            "//! bar",
            "//! baz")
            .test("//! foo\n//! bar\n//! baz", LINE_INNER_DOC);
        doTest(test);
    }

    public void testMultilineLineDoc3() {
        CompositeTest test = new CompositeTest(
            "//! foo",
            "//! bar",
            "/// foo",
            "/// bar")
            .test("//! foo\n//! bar", LINE_INNER_DOC).test("\n", WHITE_SPACE)
            .test("/// foo\n/// bar", LINE_DOC);
        doTest(test);
    }

    public void testMultilineLineDoc4() {
        CompositeTest test = new CompositeTest(
            "// foo",
            "/// foo",
            "/// bar",
            "// bar")
            .test("// foo", LINE_COMMENT).test("\n", WHITE_SPACE)
            .test("/// foo\n/// bar", LINE_DOC).test("\n", WHITE_SPACE)
            .test("// bar", LINE_COMMENT);
        doTest(test);
    }

    public void testMultilineLineDoc5() {
        CompositeTest test = new CompositeTest(
            "/// foo",
            "// foo",
            "/// bar",
            "// bar")
            .test("/// foo", LINE_DOC).test("\n", WHITE_SPACE)
            .test("// foo", LINE_COMMENT).test("\n", WHITE_SPACE)
            .test("/// bar", LINE_DOC).test("\n", WHITE_SPACE)
            .test("// bar", LINE_COMMENT);
        doTest(test);
    }

    public void testMultilineLineDoc6() {
        CompositeTest test = new CompositeTest(
            "/// foo",
            "///",
            "/// bar",
            "// bar")
            .test("/// foo\n///\n/// bar", LINE_DOC).test("\n", WHITE_SPACE)
            .test("// bar", LINE_COMMENT);
        doTest(test);
    }

    public void testMultilineLineDoc7() {
        CompositeTest test = new CompositeTest(
            "///////",
            "/// foo",
            "///////",
            "/// bar",
            "///////",
            "")
            .test("///////\n/// foo\n///////\n/// bar\n///////", LINE_DOC).test("\n", WHITE_SPACE);
        doTest(test);
    }

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
            .test("}", OP_RBRACE).test("\n", WHITE_SPACE);
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
        doTest("'abc", PRIM_IDENT);
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
            .test("1234", INT_LIT)
            .test("0b101", INT_LIT)
            .test("0xABC", INT_LIT)
            .test("1.0", FLOAT_LIT)
            .test("1.0e10", FLOAT_LIT)
            .test("2us", INT_LIT)
            .test("r###\"raw\"###suffix", RAW_STRING_LIT)
            .test("br###\"raw\"###suffix", RAW_BYTE_STRING_LIT);
        doTest(test);
    }

    public void testRustLineDocComments() {
        MultiTest test = new MultiTest()
            .test("///", LINE_DOC)
            .test("/// blah", LINE_DOC)
            .test("////", LINE_COMMENT);
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
            .test("// test", LINE_COMMENT).test("\r\n", WHITE_SPACE)
            .test("/// test", LINE_DOC).test("\r\n", WHITE_SPACE);
        doTest(test);
    }

    @NotNull
    @Override
    protected Lexer createLexer() {
        return new RsLexer();
    }
}
