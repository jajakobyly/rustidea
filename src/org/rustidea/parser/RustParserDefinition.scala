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

package org.rustidea.parser

import com.intellij.lang.ParserDefinition.SpaceRequirements
import com.intellij.lang.{ASTNode, LanguageUtil, ParserDefinition, PsiParser}
import com.intellij.lexer.Lexer
import com.intellij.openapi.project.Project
import com.intellij.psi.tree.{IFileElementType, TokenSet}
import com.intellij.psi.{FileViewProvider, PsiElement, PsiFile, TokenType}
import org.rustidea.psi.RustTypes
import org.rustidea.psi.impl.RustFileImpl
import org.rustidea.stubs.types.RustStubFileElementType

class RustParserDefinition extends ParserDefinition {
  override def createLexer(project: Project): Lexer =
    new RustLexer()

  override def createParser(project: Project): PsiParser =
    new RustParser()

  override def getFileNodeType: IFileElementType =
    RustStubFileElementType

  override def getWhitespaceTokens: TokenSet =
    RustParserDefinition.WHITE_SPACE

  override def getCommentTokens: TokenSet =
    RustParserDefinition.COMMENTS

  override def getStringLiteralElements: TokenSet =
    RustParserDefinition.STRING_LITERALS

  override def createFile(fileViewProvider: FileViewProvider): PsiFile =
    new RustFileImpl(fileViewProvider)

  override def createElement(node: ASTNode): PsiElement =
    RustTypes.Factory.createElement(node)

  override def spaceExistanceTypeBetweenTokens(left: ASTNode, right: ASTNode): SpaceRequirements =
    LanguageUtil.canStickTokensTogetherByLexer(left, right, new RustLexer())
}

object RustParserDefinition {
  final val WHITE_SPACE = TokenSet.create(TokenType.WHITE_SPACE)
  final val COMMENTS = TokenSet.create(
    // Docs are not comments
    RustTypes.BLOCK_COMMENT,
    RustTypes.LINE_COMMENT)
  // FIXME Find in literals does not work.
  final val STRING_LITERALS = TokenSet.create(
    RustTypes.STR_LIT_TOKEN,
    RustTypes.BYTE_STR_LIT_TOKEN,
    RustTypes.RAW_STR_LIT_TOKEN,
    RustTypes.RAW_BYTE_STR_LIT_TOKEN,
    RustTypes.INVALID_BYTE_STR_LIT_TOKEN,
    RustTypes.INVALID_RAW_BYTE_STR_LIT_TOKEN)
}
