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

package org.rustidea.psi.impl;

import com.intellij.psi.tree.IElementType;
import org.jetbrains.annotations.NotNull;
import org.rustidea.psi.RsElementVisitor;
import org.rustidea.psi.RsToken;

public class RsTokenImpl extends IRsLeafPsiElement implements RsToken {
    public RsTokenImpl(@NotNull IElementType type, CharSequence text) {
        super(type, text);
    }

    @NotNull
    @Override
    public IElementType getTokenType() {
        return getElementType();
    }

    @Override
    public void accept(@NotNull RsElementVisitor visitor) {
        visitor.visitRustToken(this);
    }

    @NotNull
    @Override
    protected String getDebugName() {
        return getElementType().toString();
    }
}
