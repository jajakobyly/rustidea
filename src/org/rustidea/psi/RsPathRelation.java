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

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public interface RsPathRelation extends IRsPsiElement {
    @Nullable
    RsKeyword getKwSelf();

    @Nullable
    RsKeyword getKwSuper();

    @Nullable
    RsToken getDoubleColon();

    /**
     * It is recommended to use {@link RsPath#getRelationType()} instead, because it is able to obtain data from stubs.
     */
    @NotNull
    Type getType();

    enum Type {
        UNSPECIFIED, SELF, SUPER, GLOBAL;

        @NotNull
        public static Type fromInt(int i) {
            return i == 1 ? SELF
                : i == 2 ? SUPER
                : i == 3 ? GLOBAL
                : UNSPECIFIED;
        }

        public int toInt() {
            return this == SELF ? 1
                : this == SUPER ? 2
                : this == GLOBAL ? 3
                : 0;
        }
    }
}
