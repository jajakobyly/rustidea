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

package org.rustidea.parser.framework;

import com.intellij.lang.PsiBuilder;
import org.jetbrains.annotations.NotNull;

/**
 * Provides access to {@link PsiBuilder} and some convenience methods.
 *
 * <p>Custom parsers can extend this interface to provide more functionality.</p>
 */
public interface ParserContext {
    @NotNull
    PsiBuilder getBuilder();

    /**
     * Shortcut for <code>{@link #getBuilder()}.mark().error(message)</code>
     *
     * @param message for error element
     */
    void error(@NotNull String message);

    /**
     * Shortcut for <code>{@link #getBuilder()}.eof()</code>
     *
     * @return true if the lexer is at end of file, false otherwise.
     */
    boolean eof();
}
