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

import java.lang.ref.Reference
import java.util.ResourceBundle

import com.intellij.CommonBundle
import com.intellij.reference.SoftReference
import org.jetbrains.annotations.PropertyKey

object RustBundle {
  private final val BUNDLE_PATH = "messages.RustBundle"
  private var _bundle: Reference[ResourceBundle] = null

  def message(@PropertyKey(resourceBundle = BUNDLE_PATH) key: String,
              params: Any*): String =
    CommonBundle.message(getBundle, key, params)

  private def getBundle: ResourceBundle =
    Option(SoftReference.dereference(_bundle)) getOrElse {
      val bundle = ResourceBundle.getBundle(BUNDLE_PATH)
      _bundle = new SoftReference[ResourceBundle](bundle)
      bundle
    }
}
