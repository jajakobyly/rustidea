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
import org.jetbrains.annotations.NotNull;

import java.lang.reflect.Array;

public class SimpleArrayFactory {
    private SimpleArrayFactory() {
    }

    @NotNull
    @SuppressWarnings("unchecked")
    public static <T> T[] empty(@NotNull final Class<T> cls) {
        return (T[]) Array.newInstance(cls, 0);
    }

    @NotNull
    public static <T> ArrayFactory<T> get(@NotNull final Class<T> cls) {
        return new ArrayFactory<T>() {
            @NotNull
            @Override
            @SuppressWarnings("unchecked")
            public T[] create(int count) {
                return (T[]) Array.newInstance(cls, count);
            }
        };
    }
}
