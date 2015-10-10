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

package org.rustidea.psi;

import com.intellij.openapi.util.text.StringUtil;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

public class RsUseDeclTypeTest {
    @Test
    public void testPacking() throws Exception {
        for (RsUseDecl.Type type1 : RsUseDecl.Type.values()) {
            assertEquals(type1, RsUseDecl.Type.unpack(type1.pack()));

            for (RsUseDecl.Type type2 : RsUseDecl.Type.values()) {
                if (type1 != type2) {
                    assertNotEquals(
                        StringUtil.join(type1.toString(), " <> ", type2.toString()),
                        type1.pack(),
                        type2.pack());
                } else {
                    assertEquals(
                        StringUtil.join(type1.toString(), " == ", type2.toString()),
                        type1.pack(),
                        type2.pack());
                }
            }
        }
    }
}
