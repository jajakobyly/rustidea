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
import com.intellij.psi.tree.ICompositeElementType;
import com.intellij.util.ReflectionUtil;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class IRsCompositeElementType extends IRsElementTypeImpl implements ICompositeElementType, IRsElementType {
    @Nullable
    private final String humanReadableName;

    @NotNull
    private final Class<? extends ASTNode> implClass;

    public IRsCompositeElementType(@NotNull final String debugName,
                                   @NotNull final Class<? extends ASTNode> implClass) {
        this(debugName, null, implClass);
    }

    public IRsCompositeElementType(@NotNull final String debugName,
                                   @Nullable final String humanReadableName,
                                   @NotNull final Class<? extends ASTNode> implClass) {
        super(debugName);
        this.humanReadableName = humanReadableName;
        this.implClass = implClass;
    }

    @NotNull
    @Override
    public ASTNode createCompositeNode() {
        return ReflectionUtil.newInstance(implClass);
    }

    @NotNull
    @Override
    public String getHumanReadableName() {
        return humanReadableName != null ? humanReadableName : toString();
    }
}
