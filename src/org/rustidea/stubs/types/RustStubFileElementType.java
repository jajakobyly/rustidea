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

package org.rustidea.stubs.types;

import com.intellij.psi.PsiFile;
import com.intellij.psi.StubBuilder;
import com.intellij.psi.stubs.DefaultStubBuilder;
import com.intellij.psi.stubs.StubElement;
import com.intellij.psi.stubs.StubInputStream;
import com.intellij.psi.stubs.StubOutputStream;
import com.intellij.psi.tree.IStubFileElementType;
import org.jetbrains.annotations.NotNull;
import org.rustidea.RustLanguage;
import org.rustidea.psi.RustFile;
import org.rustidea.stubs.RustFileStub;

import java.io.IOException;

public class RustStubFileElementType extends IStubFileElementType<RustFileStub> {
    public static final RustStubFileElementType INSTANCE = new RustStubFileElementType();

    public static final int VERSION = 0;

    public RustStubFileElementType() {
        super("FILE", RustLanguage.INSTANCE);
    }

    @Override
    public int getStubVersion() {
        return VERSION;
    }

    @Override
    public StubBuilder getBuilder() {
        return new DefaultStubBuilder() {
            @NotNull
            @Override
            protected StubElement createStubForFile(@NotNull PsiFile file) {
                if (file instanceof RustFile) {
                    return new RustFileStub((RustFile) file);
                }

                return super.createStubForFile(file);
            }
        };
    }

    @Override
    public void serialize(@NotNull RustFileStub stub, @NotNull StubOutputStream dataStream) throws IOException {
        // Nothing to do here
    }

    @NotNull
    @Override
    public RustFileStub deserialize(@NotNull StubInputStream dataStream, StubElement parentStub) throws IOException {
        return new RustFileStub(null);
    }

    @NotNull
    @Override
    public String getExternalId() {
        return "rust.FILE";
    }
}
