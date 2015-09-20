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

import com.intellij.util.containers.ContainerUtil;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

import java.util.Collection;

@RunWith(Parameterized.class)
public class UnescapeRustTest {
    private final String input;
    private final String expected;
    private final boolean unicode;
    private final boolean eol;

    public UnescapeRustTest(String input, String expected, boolean unicode, boolean eol) {
        this.input = input;
        this.expected = expected;
        this.unicode = unicode;
        this.eol = eol;
    }

    @Parameters(name = "{index}: {0} -> {1}")
    public static Collection<Object[]> data() {
        return ContainerUtil.immutableList(new Object[][]{
            {"aaa", "aaa", true, true},
            {"aaa", "aaa", false, false},
            {"a\\na", "a\na", true, true},
            {"a\\ra", "a\ra", true, true},
            {"a\\ta", "a\ta", true, true},
            {"a\\0a", "a\0a", true, true},
            {"a\\'a", "a'a", true, true},
            {"a\\\"a", "a\"a", true, true},
            {"a\\\\a", "a\\a", true, true},
            {"a\\x20a", "a a", true, true},
            {"a\\x0aa", "a\na", true, true},
            {"a\\x0Aa", "a\na", true, true},
            {"foo\\\n    bar", "foobar", true, true},
            {"foo\\\r\n    bar", "foobar", true, true},
            {"foo\\\r\n    bar", "foo\\\r\n    bar", true, false},
            {"\\u{0119}dw\\u{0105}rd", "\u0119dw\u0105rd", true, true},
            {"\\u{0119}dw\\u{0105}rd", "\\u{0119}dw\\u{0105}rd", false, true},
            {"\\u{0}", "\0", true, true},
            {"\\u{00}", "\0", true, true},
            {"\\u{000}", "\0", true, true},
            {"\\u{0000}", "\0", true, true},
            {"\\u{00000}", "\0", true, true},
            {"\\u{000000}", "\0", true, true},
            {"\\u{0000000}", "\\u{0000000}", true, true},
            {"\\u{00000000}", "\\u{00000000}", true, true},
            {"\\u{}", "\\u{}", true, true},
            {"\\u{", "\\u{", true, true},
            {"\\u", "\\u", true, true},
            {"\\u{zzzz}", "\\u{zzzz}", true, true},
        });
    }

    @Test
    public void test() {
        Assert.assertEquals(expected, RsStringUtil.unescapeRust(input, unicode, eol));
    }
}
