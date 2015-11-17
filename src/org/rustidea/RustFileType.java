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

package org.rustidea;

import com.intellij.openapi.fileTypes.LanguageFileType;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.rustidea.ui.Icons;

import javax.swing.*;

public class RustFileType extends LanguageFileType {
    public static final RustFileType INSTANCE = new RustFileType();
    public static final String LANG_NAME = "Rust";
    public static final String EXT = "rs";
    public static final String DOT_EXT = ".rs";

    protected RustFileType() {
        super(RustLanguage.INSTANCE);
    }

    @NotNull
    @Override
    public String getName() {
        return LANG_NAME;
    }

    @NotNull
    @Override
    public String getDescription() {
        return LANG_NAME;
    }

    @NotNull
    @Override
    public String getDefaultExtension() {
        return EXT;
    }

    @Nullable
    @Override
    public Icon getIcon() {
        return Icons.RUST_FILE;
    }
}
