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

import com.intellij.lang.ASTNode
import com.intellij.lang.PsiBuilder
import com.intellij.lang.PsiParser
import com.intellij.openapi.diagnostic.Logger
import com.intellij.psi.tree.IElementType
import org.rustidea.parser.framework.many1Greedy
import org.rustidea.parser.framework.or
import org.rustidea.parser.framework.wrapMarker
import kotlin.reflect.jvm.java
import kotlin.util.measureTimeMillis

public class RsParser : PsiParser {
    override fun parse(root: IElementType, builder: PsiBuilder): ASTNode {
        val time = measureTimeMillis {
            builder.wrapMarker(root) {
                fileContents(builder)
            }
        }
        val size = builder.getCurrentOffset() / 1000.0;
        LOG.info("Parsed ${"%.1f".format(size)}Kb file in ${time}ms.")
        return builder.getTreeBuilt()
    }

    companion object {
        final val LOG = Logger.getInstance(RsParser::class.java);
        final val fileContents = many1Greedy(parentAttr or item)
    }
}
