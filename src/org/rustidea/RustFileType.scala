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

package org.rustidea

import javax.swing.Icon

import com.intellij.openapi.fileTypes.LanguageFileType

object RustFileType extends LanguageFileType(RustLanguage) {
  final val DEFAULT_EXTENSION = "rs"

  override def getName: String =
    RustBundle.message("rust.file.type.name")

  override def getDescription: String =
    RustBundle.message("rust.file.type.description")

  override def getDefaultExtension: String = DEFAULT_EXTENSION

  override def getIcon: Icon = RustIcons.FILE
}
