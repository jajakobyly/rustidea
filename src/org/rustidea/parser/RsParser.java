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
    public static final Factory FACTORY = new Factory();
    private static final Logger LOG = Logger.getInstance(RsParser.class);

    @NotNull
    private final PsiBuilder builder;
    @NotNull
    private final RsModuleParser moduleParser;
    @NotNull
    private final RsExpressionParser expressionParser;
    @NotNull
    private final RsReferenceParser referenceParser;
    @NotNull
    private final RsStatementParser statementParser;
    @NotNull
    private final RsTypeParser typeParser;

    public RsParser(@NotNull final PsiBuilder builder) {
//        builder.setDebugMode(true);
        this.builder = builder;

        this.moduleParser = new RsModuleParser(this);
        this.expressionParser = new RsExpressionParser(this);
        this.referenceParser = new RsReferenceParser(this);
        this.statementParser = new RsStatementParser(this);
        this.typeParser = new RsTypeParser(this);
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

    @NotNull
    public RsReferenceParser getReferenceParser() {
        return referenceParser;
    }

    @NotNull
    public RsStatementParser getStatementParser() {
        return statementParser;
    }

    @NotNull
    public RsTypeParser getTypeParser() {
        return typeParser;
    }

    @NotNull
    private ASTNode doParse(@NotNull IElementType root) {
        final Stopwatch stopwatch = Stopwatch.createStarted();

        PsiBuilder.Marker marker = getBuilder().mark();

        getModuleParser().file();

        marker.done(root);

        stopwatch.stop();
        final double size = getBuilder().getCurrentOffset() / 1000.0;
        LOG.info(String.format("Parsed %.1f kb file in %s.", size, stopwatch));

        return getBuilder().getTreeBuilt();
    }

    private static class Factory implements PsiParser {
        @NotNull
        @Override
        public ASTNode parse(@NotNull IElementType root, @NotNull PsiBuilder builder) {
            RsParser parser = new RsParser(builder);
            return parser.doParse(root);
        }
    }
}
