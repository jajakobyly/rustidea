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

import com.intellij.testFramework.ParsingTestCase;
import org.jetbrains.annotations.NotNull;
import org.rustidea.RustParserDefinition;

public class RsParserTest extends ParsingTestCase {
    public RsParserTest() {
        super(
            RsParserTest.class.getPackage().getName().replace('.', '/'),
            "rs", new RustParserDefinition());
    }

    public void testAttrs0001() {
        doTest(true);
    }

    public void testAttrs0002() {
        doTest(true);
    }

    public void testAttrs0003() {
        doTest(true);
    }

    public void testConstAndStatic0001() {
        doTest(true);
    }

    public void testConstAndStatic0002() {
        doTest(true);
    }

    public void testExternCrate0001() {
        doTest(true);
    }

    public void testExternCrate0002() {
        doTest(true);
    }

    public void testMod0001() {
        doTest(true);
    }

    public void testMod0002() {
        doTest(true);
    }

    public void testMod0003() {
        doTest(true);
    }

    public void testUse0001() {
        doTest(true);
    }

    public void testUse0002() {
        doTest(true);
    }

    public void testUse0003() {
        doTest(true);
    }

    public void testUse0004() {
        doTest(true);
    }

    public void testUse0005() {
        doTest(true);
    }

    public void testStruct0001() {
        doTest(true);
    }

    public void testStruct0002() {
        doTest(true);
    }

    public void testStruct0003() {
        doTest(true);
    }

    public void testStruct0004() {
        doTest(true);
    }

    public void testStruct0005() {
        doTest(true);
    }

    @NotNull
    @Override
    protected String getTestDataPath() {
        return "testData";
    }

    @Override
    protected boolean includeRanges() {
        return true;
    }
}
