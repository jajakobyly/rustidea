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

package org.rustidea.parser;

import com.google.common.base.Stopwatch;
import com.intellij.lang.ASTNode;
import com.intellij.lang.PsiBuilder;
import com.intellij.lang.PsiParser;
import com.intellij.openapi.diagnostic.Logger;
import com.intellij.psi.tree.IElementType;
import org.jetbrains.annotations.NotNull;

public class RsParser {
    public static final Proxy PROXY = new Proxy();
    private static final Logger LOG = Logger.getInstance(RsParser.class);

    @NotNull
    private final PsiBuilder builder;
    @NotNull
    private final RsModuleParser moduleParser;
    @NotNull
    private final RsExpressionParser expressionParser;

    public RsParser(@NotNull final PsiBuilder builder) {
        builder.setDebugMode(true);
        this.builder = builder;

        moduleParser = new RsModuleParser(this);
        expressionParser = new RsExpressionParser(this);
    }

    @NotNull
    public PsiBuilder getBuilder() {
        return builder;
    }

    @NotNull
    public RsModuleParser getModuleParser() {
        return moduleParser;
    }

    @NotNull
    public RsExpressionParser getExpressionParser() {
        return expressionParser;
    }

    private ASTNode doParse(IElementType root) {
        final Stopwatch stopwatch = Stopwatch.createStarted();

        PsiBuilder.Marker marker = builder.mark();

        moduleParser.file();

        marker.done(root);

        stopwatch.stop();
        final double size = builder.getCurrentOffset() / 1000.0;
        LOG.info(String.format("Parsed %.1f kb file in %s.", size, stopwatch));

        return builder.getTreeBuilt();
    }

    static class Proxy implements PsiParser {
        @NotNull
        @Override
        public ASTNode parse(IElementType root, @NotNull PsiBuilder builder) {
            RsParser parser = new RsParser(builder);
            return parser.doParse(root);
        }
    }
}
