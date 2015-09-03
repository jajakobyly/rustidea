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

package org.rustidea.stubs.impl;

import com.intellij.psi.stubs.StubBase;
import com.intellij.psi.stubs.StubElement;
import org.jetbrains.annotations.NotNull;
import org.rustidea.psi.RsPath;
import org.rustidea.psi.types.RsTypes;
import org.rustidea.stubs.RsPathStub;

public class RsPathStubImpl extends StubBase<RsPath> implements RsPathStub {
    private final RsPath.RelationType relationType;

    public RsPathStubImpl(StubElement parent, RsPath.RelationType relationType) {
        super(parent, RsTypes.PATH);
        this.relationType = relationType;
    }

    @NotNull
    @Override
    public RsPath.RelationType getRelationType() {
        return relationType;
    }
}
