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

package org.rustidea.psi.types;

import com.intellij.lang.ASTNode;
import com.intellij.psi.tree.ILeafElementType;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.rustidea.psi.impl.RsTokenImpl;

public class IRsTokenType extends IRsElementType implements ILeafElementType {
    @Nullable
    private final String humanReadableName;

    public IRsTokenType(@NotNull final String debugName) {
        this(debugName, null);
    }

    public IRsTokenType(@NotNull final String debugName, @Nullable final String humanReadableName) {
        super(debugName);
        this.humanReadableName = humanReadableName;
    }

    @NotNull
    @Override
    public ASTNode createLeafNode(CharSequence leafText) {
        return new RsTokenImpl(this, leafText);
    }

    @NotNull
    @Override
    public String getHumanReadableName() {
        return humanReadableName != null ? humanReadableName : super.getHumanReadableName();
    }
}
