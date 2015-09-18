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

import com.intellij.openapi.util.text.StringUtil;
import com.intellij.psi.tree.IElementType;
import com.intellij.testFramework.LexerTestCase;
import com.intellij.util.containers.ContainerUtil;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NonNls;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Map;

public abstract class IRsLexerTestCase extends LexerTestCase {
    private static final String FORMAT = "%s ('%s')\n";
    private static final String SEPARATOR = "-----\n";

    @NotNull
    @Contract(pure = true)
    private static String escapeTest(@NotNull final String test) {
        return StringUtil.replace(test, "\n", "\\n");
    }

    @NotNull
    private static String formatExpected(@NonNls @NotNull final String text, @NotNull final IElementType expectedType) {
        return String.format(FORMAT, expectedType, escapeTest(text));
    }

    protected void doTest(@NonNls @NotNull final String text, @NotNull final IElementType expectedType) {
        doTest(text, formatExpected(text, expectedType));
    }

    protected void doTest(@NotNull final CompositeTest test) {
        doTest(test.buildText(), test.buildKey());
    }

    protected void doTest(@NotNull final MultiTest builder) {
        List<String> actual = ContainerUtil.newLinkedList();
        List<String> expected = ContainerUtil.newLinkedList();

        for (Map.Entry<String, IElementType> test : builder.getData().entrySet()) {
            actual.add(printTokens(test.getKey(), 0));
            expected.add(formatExpected(test.getKey(), test.getValue()));
        }

        assertSameLines(StringUtil.join(expected, SEPARATOR), StringUtil.join(actual, SEPARATOR));
    }

    @NotNull
    @Override
    protected String getDirPath() {
        throw new UnsupportedOperationException("should not be called");
    }

    protected static class CompositeTest {
        @Nullable
        private final String expectedText;
        private final StringBuilder textBuilder = new StringBuilder();
        private final StringBuilder keyBuilder = new StringBuilder();

        public CompositeTest() {
            this.expectedText = null;
        }

        public CompositeTest(@Nullable final String expectedText) {
            this.expectedText = expectedText;
        }

        public CompositeTest(@NotNull final String... expectedTestLines) {
            this.expectedText = StringUtil.join(expectedTestLines, "\n");
        }

        @NotNull
        public CompositeTest test(@NotNull final String text, @NotNull final IElementType expectedType) {
            textBuilder.append(text);
            keyBuilder.append(formatExpected(text, expectedType));
            return this;
        }

        @NotNull
        public String buildText() {
            String text = textBuilder.toString();
            assert expectedText == null || expectedText.equals(text) : "key does not match expectedText";
            return text;
        }

        @NotNull
        public String buildKey() {
            return keyBuilder.toString();
        }
    }

    protected static class MultiTest {
        private final Map<String, IElementType> data = ContainerUtil.newLinkedHashMap();

        @NotNull
        public MultiTest test(@NotNull final String text, @NotNull final IElementType expectedType) {
            this.data.put(text, expectedType);
            return this;
        }

        @NotNull
        public Map<String, IElementType> getData() {
            return data;
        }
    }
}
