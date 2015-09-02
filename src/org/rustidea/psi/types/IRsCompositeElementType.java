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
import sun.reflect.ConstructorAccessor;

import java.lang.reflect.Constructor;

public class IRsCompositeElementType extends IRsElementType implements ICompositeElementType {
    private final ConstructorAccessor constructor;

    public IRsCompositeElementType(final String debugName, @NotNull final Class<? extends ASTNode> psiImplCls) {
        super(debugName);
        Constructor<? extends ASTNode> constructor = ReflectionUtil.getDefaultConstructor(psiImplCls);
        this.constructor = ReflectionUtil.getConstructorAccessor(constructor);
    }

    @NotNull
    @Override
    public ASTNode createCompositeNode() {
        return ReflectionUtil.createInstanceViaConstructorAccessor(constructor);
    }
}
