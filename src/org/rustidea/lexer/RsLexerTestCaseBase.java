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

import com.intellij.psi.tree.IElementType;
import com.intellij.testFramework.LexerTestCase;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public abstract class RsLexerTestCaseBase extends LexerTestCase {
    protected void doTest(@NotNull final TestBuilder builder) {
        doTest(builder.buildTest(), builder.buildKey());
    }

    @NotNull
    @Override
    protected String getDirPath() {
        throw new UnsupportedOperationException("should not be called");
    }

    protected static class TestBuilder {
        @Nullable
        private final String expectedTest;
        private final StringBuilder testBuilder = new StringBuilder();
        private final StringBuilder keyBuilder = new StringBuilder();

        public TestBuilder() {
            this(null);
        }

        public TestBuilder(@Nullable final String expectedTest) {
            this.expectedTest = expectedTest;
        }

        @NotNull
        public TestBuilder add(@NotNull final String test, @NotNull final IElementType expectedType) {
            testBuilder.append(test);
            keyBuilder.append(String.format("%s ('%s')\n", expectedType, test));
            return this;
        }

        @NotNull
        public String buildTest() {
            String test = testBuilder.toString();
            assert expectedTest == null || expectedTest.equals(test) : "key is different than test";
            return test;
        }

        @NotNull
        public String buildKey() {
            return keyBuilder.toString();
        }
    }
}
