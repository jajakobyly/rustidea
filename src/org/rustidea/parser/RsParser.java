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

import com.intellij.lang.PsiParser;
import com.intellij.openapi.diagnostic.Logger;
import org.jetbrains.annotations.NotNull;
import org.rustidea.parser.framework.Parser;
import org.rustidea.parser.framework.PsiParserImpl;

import static org.rustidea.parser.RsItemParser.itemGreedy;
import static org.rustidea.parser.RsItemParser.parentAttrOrDoc;
import static org.rustidea.parser.framework.Combinators.manyGreedy;

public class RsParser extends PsiParserImpl implements PsiParser {
    public static final Parser fileContents = manyGreedy(parentAttrOrDoc.or(itemGreedy));
    private static final Logger LOG = Logger.getInstance(RsParser.class);

    @NotNull
    @Override
    public Parser getEntryRule() {
        return fileContents;
    }

    @NotNull
    @Override
    protected Logger getLogger() {
        return LOG;
    }
}
