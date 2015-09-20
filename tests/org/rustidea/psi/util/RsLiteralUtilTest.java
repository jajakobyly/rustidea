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

package org.rustidea.psi.util;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class RsLiteralUtilTest {
    @Test
    public void testRemoveRawStringHashes() {
        assertEquals("1 #\"# foo", RsLiteralUtil.removeRawStringHashes("r###\"1 #\"# foo\"###"));
        assertEquals("2 #\"# foo\"##", RsLiteralUtil.removeRawStringHashes("r###\"2 #\"# foo\"##"));
        assertEquals("3 #\"# foo\"", RsLiteralUtil.removeRawStringHashes("r###\"3 #\"# foo\""));
        assertEquals("4 #\"# foo", RsLiteralUtil.removeRawStringHashes("r###\"4 #\"# foo"));
        assertEquals("5 #\"# foo", RsLiteralUtil.removeRawStringHashes("r\"5 #\"# foo\""));
        assertEquals("6 #\"# foo", RsLiteralUtil.removeRawStringHashes("r\"6 #\"# foo"));
    }
}
