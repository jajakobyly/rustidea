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

import com.google.common.base.Stopwatch;
import com.intellij.lang.ASTNode;
import com.intellij.lang.PsiBuilder;
import com.intellij.lang.PsiParser;
import com.intellij.openapi.diagnostic.Logger;
import com.intellij.psi.tree.IElementType;
import org.jetbrains.annotations.NotNull;

public abstract class PsiParserImpl implements PsiParser {
    @NotNull
    public abstract Parser getFileRule();

    @NotNull
    protected abstract Logger getLogger();

    @NotNull
    protected ParserContext createParserContext(@NotNull final PsiBuilder builder) {
        return new ParserContextImpl(builder);
    }

    @NotNull
    @Override
    public ASTNode parse(@NotNull final IElementType root, @NotNull final PsiBuilder builder) {
        final Stopwatch stopwatch = Stopwatch.createStarted();

        final ParserContext ctx = createParserContext(builder);
        PsiBuilder.Marker marker = builder.mark();
        getFileRule().parse(ctx);
        marker.done(root);

        stopwatch.stop();
        final double size = builder.getCurrentOffset() / 1000.0;
        getLogger().info(String.format("Parsed %.1f kb file in %s.", size, stopwatch));

        return builder.getTreeBuilt();
    }
}
