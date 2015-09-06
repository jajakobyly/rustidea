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

import com.intellij.psi.PsiElement;
import com.intellij.psi.stubs.EmptyStub;
import com.intellij.psi.stubs.StubElement;
import com.intellij.psi.stubs.StubInputStream;
import com.intellij.psi.stubs.StubOutputStream;
import org.jetbrains.annotations.NonNls;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;

/**
 * Rewrite of {@link com.intellij.psi.stubs.EmptyStubElementType} derived from {@link IRsStubElementType}.
 */
public abstract class IRsEmptyStubElementType<T extends PsiElement> extends IRsStubElementType<EmptyStub, T> {
    protected IRsEmptyStubElementType(@NotNull @NonNls String debugName) {
        super(debugName);
    }

    @NotNull
    @Override
    public EmptyStub createStub(@NotNull T psi, @NotNull StubElement parentStub) {
        return createStub(parentStub);
    }

    @NotNull
    protected EmptyStub createStub(@NotNull StubElement parentStub) {
        return new EmptyStub(parentStub, this);
    }

    @Override
    public void serialize(@NotNull EmptyStub stub, @NotNull StubOutputStream dataStream) throws IOException {
    }

    @NotNull
    @Override
    public EmptyStub deserialize(@NotNull StubInputStream dataStream, @NotNull StubElement parentStub) throws IOException {
        return createStub(parentStub);
    }
}
