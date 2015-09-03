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

import org.rustidea.psi.RsPathRelation.Type;
import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;

public class RsPathRelationTypeTest {
    @Test
    public void testFromInt() throws Exception {
        assertEquals(Type.fromInt(0), Type.UNSPECIFIED);
        assertEquals(Type.fromInt(1), Type.SELF);
        assertEquals(Type.fromInt(2), Type.SUPER);
        assertEquals(Type.fromInt(3), Type.GLOBAL);
        assertEquals(Type.fromInt(4), Type.UNSPECIFIED);
    }

    @Test
    public void testToInt() throws Exception {
        assertEquals(Type.UNSPECIFIED.toInt(), 0);
        assertEquals(Type.SELF.toInt(), 1);
        assertEquals(Type.SUPER.toInt(), 2);
        assertEquals(Type.GLOBAL.toInt(), 3);
    }

    @Test
    public void testFromIntEqualsToInt() throws Exception {
        for (Type rel : Type.values()) {
            assertEquals(Type.fromInt(rel.toInt()), rel);
        }
    }
}
