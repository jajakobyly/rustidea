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

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.rustidea.stubs.RsUseDeclStub;
import org.rustidea.util.UnreachableException;

public interface RsUseDecl extends IRsItem<RsUseDeclStub> {
    @Nullable
    IRsReferenceElement getUseReference();

    @NotNull
    Type getType();

    enum Type {
        /** Packed as {@code 0b00}. */
        SIMPLE,
        /** Packed as {@code 0b01}. */
        RENAMED,
        /** Packed as {@code 0b10}. */
        LIST,
        /** Packed as {@code 0b11}. */
        GLOB;

        @NotNull
        @Contract(pure = true)
        public static Type unpack(final byte flags) {
            assert flags >= 0 && flags <= 3;
            if ((flags & 2) == 0) {
                // Single variants
                if ((flags & 1) == 0) {
                    // 00
                    return SIMPLE;
                } else {
                    // 01
                    return RENAMED;
                }
            } else {
                // Poly variants
                if ((flags & 1) == 0) {
                    // 10
                    return LIST;
                } else {
                    // 11
                    return GLOB;
                }
            }
        }

        @Contract(pure = true)
        public byte pack() {
            switch (this) {
                case SIMPLE:
                    return 0;
                case RENAMED:
                    return 1;
                case LIST:
                    return 2;
                case GLOB:
                    return 3;
                default:
                    throw new UnreachableException();
            }
        }

        @Contract(pure = true)
        public boolean isSingle() {
            return this == SIMPLE || this == RENAMED;
        }

        @Contract(pure = true)
        public boolean isPoly() {
            return this == LIST || this == GLOB;
        }
    }
}
