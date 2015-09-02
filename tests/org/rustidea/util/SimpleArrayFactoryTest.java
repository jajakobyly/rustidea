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
import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;

public class SimpleArrayFactoryTest {
    @Test
    public void testEmpty() throws Exception {
        Object[] created = SimpleArrayFactory.empty(Object.class);
        assertEquals(created.length, 0);
        assertEquals(created, new Object[0]);
    }

    @Test
    public void testGet() throws Exception {
        ArrayFactory<Object> factory = SimpleArrayFactory.get(Object.class);
        Object[] arr5 = factory.create(5);
        assertEquals(arr5.length, 5);
        assertEquals(arr5, new Object[5]);

        Object[] arr10 = factory.create(10);
        assertEquals(arr10.length, 10);
        assertEquals(arr10, new Object[10]);
    }
}
