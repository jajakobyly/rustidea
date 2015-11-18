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

import com.intellij.openapi.project.Project;
import com.intellij.psi.PsiFileFactory;
import org.jetbrains.annotations.NotNull;
import org.rustidea.RustFileType;
import org.rustidea.psi.RsElementFactory;
import org.rustidea.psi.RsFile;

public class RsElementFactoryImpl implements RsElementFactory {
    @NotNull
    private final Project project;

    public RsElementFactoryImpl(@NotNull Project project) {
        this.project = project;
    }

    @Override
    @NotNull
    public RsFile createFileFromText(@NotNull final String text) {
        final PsiFileFactory ff = PsiFileFactory.getInstance(project);
        return (RsFile) ff.createFileFromText(DUMMY_FILE_NAME, RustFileType.INSTANCE, text);
    }
}
