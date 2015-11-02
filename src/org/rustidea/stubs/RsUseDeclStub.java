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

package org.rustidea.stubs;

import com.intellij.psi.stubs.StubBase;
import com.intellij.psi.stubs.StubElement;
import com.intellij.reference.SoftReference;
import com.intellij.util.io.StringRef;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.rustidea.psi.RsReferenceElement;
import org.rustidea.psi.RsUseDecl;
import org.rustidea.psi.types.RsPsiTypes;
import org.rustidea.util.NotImplementedException;

import java.lang.ref.Reference;

public class RsUseDeclStub extends StubBase<RsUseDecl> implements IRsItemStub<RsUseDecl> {
    private final StringRef text;
    private final byte flags;
    @Nullable
    private Reference<RsReferenceElement> reference = null;

    public RsUseDeclStub(StubElement parent, final StringRef referenceText, final byte flags) {
        super(parent, RsPsiTypes.USE_DECL);
        this.text = referenceText;
        this.flags = flags;
    }

    @NotNull
    public RsUseDecl.Type getType() {
        return RsUseDecl.Type.unpack(flags);
    }

    @Nullable
    public String getReferenceText() {
        return StringRef.toString(text);
    }

    @Nullable
    public RsReferenceElement getUseReference() {
        RsReferenceElement ref = SoftReference.dereference(reference);
        if (ref == null) {
            // TODO Implement this (build reference PSI from `text` with `getPsi()` as context).
            throw new NotImplementedException();
        }
        return ref;
    }

    public byte getFlags() {
        return flags;
    }
}
