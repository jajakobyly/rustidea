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

import com.intellij.extapi.psi.PsiFileBase;
import com.intellij.openapi.fileTypes.FileType;
import com.intellij.psi.FileViewProvider;
import org.jetbrains.annotations.NotNull;
import org.rustidea.RustFileType;
import org.rustidea.RustLanguage;
import org.rustidea.psi.RustFile;

public class RustFileImpl extends PsiFileBase implements RustFile {
    public RustFileImpl(@NotNull FileViewProvider viewProvider) {
        super(viewProvider, RustLanguage.INSTANCE);
    }

    @NotNull
    @Override
    public FileType getFileType() {
        return RustFileType.INSTANCE;
    }

    @Override
    public String toString() {
        return "Rust File";
    }
}
