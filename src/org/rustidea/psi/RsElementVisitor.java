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

package org.rustidea.psi;

import com.intellij.psi.PsiElementVisitor;

public abstract class RsElementVisitor extends PsiElementVisitor {
    public void visitDoc(RsDoc doc) {
        visitElement(doc);
    }

    public void visitIdentifier(RsIdentifier identifier) {
        visitRustToken(identifier);
    }

    public void visitKeyword(RsKeyword keyword) {
        visitRustToken(keyword);
    }

    public void visitLifetime(RsLifetime lifetime) {
        visitElement(lifetime);
    }

    public void visitLifetimeTypeParameter(RsLifetimeTypeParameter lifetimeTypeParameter) {
        visitElement(lifetimeTypeParameter);
    }

    public void visitParentDoc(RsParentDoc parentDoc) {
        visitDoc(parentDoc);
    }

    public void visitPath(RsPath path) {
        visitElement(path);
    }

    public void visitPathComponent(RsPathComponent pathComponent) {
        visitElement(pathComponent);
    }

    public void visitRustToken(RsToken token) {
        visitElement(token);
    }

    public void visitTypeParameter(RsTypeParameter typeParameter) {
        visitElement(typeParameter);
    }

    public void visitTypeParameterList(RsTypeParameterList typeParameterList) {
        visitElement(typeParameterList);
    }

    public void visitWhereClause(RsWhereClause whereClause) {
        visitElement(whereClause);
    }
}
