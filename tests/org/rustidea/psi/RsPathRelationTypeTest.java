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

import org.junit.Test;
import org.rustidea.psi.RsPathRelation.Type;

import static org.junit.Assert.assertEquals;

public class RsPathRelationTypeTest {
    @Test
    public void testFromInt() throws Exception {
        assertEquals(Type.UNSPECIFIED, Type.fromInt(0));
        assertEquals(Type.SELF, Type.fromInt(1));
        assertEquals(Type.SUPER, Type.fromInt(2));
        assertEquals(Type.GLOBAL, Type.fromInt(3));
        assertEquals(Type.UNSPECIFIED, Type.fromInt(4));
    }

    @Test
    public void testToInt() throws Exception {
        assertEquals(0, Type.UNSPECIFIED.toInt());
        assertEquals(1, Type.SELF.toInt());
        assertEquals(2, Type.SUPER.toInt());
        assertEquals(3, Type.GLOBAL.toInt());
    }

    @Test
    public void testFromIntEqualsToInt() throws Exception {
        for (Type rel : Type.values()) {
            assertEquals(rel, Type.fromInt(rel.toInt()));
        }
    }
}
