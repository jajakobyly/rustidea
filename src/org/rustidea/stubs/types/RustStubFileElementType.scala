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

package org.rustidea.stubs.types

import com.intellij.psi.stubs._
import com.intellij.psi.tree.IStubFileElementType
import com.intellij.psi.{PsiElement, PsiFile, StubBuilder}
import org.rustidea.RustLanguage
import org.rustidea.psi.RustFile
import org.rustidea.stubs.RustFileStub

object RustStubFileElementType
  extends IStubFileElementType[RustFileStub]("FILE", RustLanguage) {

  final val VERSION = 0

  override def getStubVersion: Int = VERSION

  override def getBuilder: StubBuilder =
    new DefaultStubBuilder {
      override def createStubForFile(file: PsiFile): StubElement[_ <: PsiElement] =
        file match {
          case f: RustFile => new RustFileStub(f)
          case f => super.createStubForFile(f)
        }
    }

  override def serialize(stub: RustFileStub, dataStream: StubOutputStream): Unit = {
    // Nothing to do here
  }

  override def deserialize(dataStream: StubInputStream, parentStub: StubElement[_ <: PsiElement]): RustFileStub =
    new RustFileStub(null)

  override def getExternalId: String = "rust.FILE"

  // Scala's type system cannot handle this object without this override
  override def indexStub(stub: RustFileStub, sink: IndexSink): Unit =
    super.indexStub(stub.asInstanceOf[PsiFileStub[_ <: PsiFile]], sink)
}
