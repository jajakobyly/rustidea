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
public class QuotedLitSuffixExtractingTest {
    private final String input;
    private final String suffix;

    public QuotedLitSuffixExtractingTest(String input, String suffix) {
        this.input = input;
        this.suffix = suffix;
    }

    @Parameters(name = "{index}: {0} -> {1}")
    public static Collection<Object[]> data() {
        return ContainerUtil.immutableList(new Object[][]{
            {"'a'suf", "suf"},
            {"b'a'suf", "suf"},
            {"'a'", ""},
            {"'aaa", ""},
            {"''", ""},
            {"'\\'", ""},
            {"''a", "a"},
            {"'\\'a", ""},
            {"'\\'a'", ""},
            {"'\\'a'a", "a"}
        });
    }

    @Test
    public void test() {
        Assert.assertEquals(suffix, RsLiteralUtil.extractSuffixFromQuotedLit(input, '\''));
    }
}
