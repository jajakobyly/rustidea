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

package org.rustidea.util;

import com.intellij.util.ArrayFactory;
import org.junit.Test;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

public class ArrayFactoriesTest {
    @Test
    public void testEmpty() throws Exception {
        Object[] created = ArrayFactories.empty(Object.class);
        assertEquals(0, created.length);
        assertArrayEquals(new Object[0], created);
    }

    @Test
    public void testGet() throws Exception {
        ArrayFactory<Object> factory = ArrayFactories.get(Object.class);
        Object[] arr5 = factory.create(5);
        assertEquals(5, arr5.length);
        assertArrayEquals(new Object[5], arr5);

        Object[] arr10 = factory.create(10);
        assertEquals(10, arr10.length);
        assertArrayEquals(new Object[10], arr10);
    }
}
