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

import com.intellij.lang.ASTNode;
import com.intellij.lang.LanguageUtil;
import com.intellij.lang.ParserDefinition;
import com.intellij.lang.PsiParser;
import com.intellij.lexer.Lexer;
import com.intellij.openapi.project.Project;
import com.intellij.psi.FileViewProvider;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiFile;
import com.intellij.psi.TokenType;
import com.intellij.psi.tree.IFileElementType;
import com.intellij.psi.tree.TokenSet;
import org.jetbrains.annotations.NotNull;
import org.rustidea.lexer.RustLexer;
import org.rustidea.psi.RustTypes;
import org.rustidea.psi.impl.RustFileImpl;
import org.rustidea.stubs.types.RustStubFileElementType;

public class RustParserDefinition implements ParserDefinition {
    public static final TokenSet WHITE_SPACE = TokenSet.create(TokenType.WHITE_SPACE);
    public static final TokenSet COMMENTS = TokenSet.create(RustTypes.BLOCK_COMMENT, RustTypes.LINE_COMMENT);
    // FIXME Find in literals does not work.
    public static final TokenSet STRING_LITERALS = TokenSet.create(
        RustTypes.STR_LIT_TOKEN,
        RustTypes.BYTE_STR_LIT_TOKEN,
        RustTypes.RAW_STR_LIT_TOKEN,
        RustTypes.RAW_BYTE_STR_LIT_TOKEN,
        RustTypes.INVALID_BYTE_STR_LIT_TOKEN,
        RustTypes.INVALID_RAW_BYTE_STR_LIT_TOKEN);

    @NotNull
    @Override
    public Lexer createLexer(Project project) {
        return new RustLexer();
    }

    @Override
    public PsiParser createParser(Project project) {
        return new RustParser();
    }

    @Override
    public IFileElementType getFileNodeType() {
        return RustStubFileElementType.INSTANCE;
    }

    @NotNull
    @Override
    public TokenSet getWhitespaceTokens() {
        return WHITE_SPACE;
    }

    @NotNull
    @Override
    public TokenSet getCommentTokens() {
        return COMMENTS;
    }

    @NotNull
    @Override
    public TokenSet getStringLiteralElements() {
        return STRING_LITERALS;
    }

    @Override
    public PsiFile createFile(FileViewProvider viewProvider) {
        return new RustFileImpl(viewProvider);
    }

    @NotNull
    @Override
    public PsiElement createElement(ASTNode node) {
        return RustTypes.Factory.createElement(node);
    }

    @Override
    public SpaceRequirements spaceExistanceTypeBetweenTokens(ASTNode left, ASTNode right) {
        return LanguageUtil.canStickTokensTogetherByLexer(left, right, new RustLexer());
    }
}
