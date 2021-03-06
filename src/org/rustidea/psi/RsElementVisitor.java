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
import com.intellij.psi.PsiFile;
import org.rustidea.util.UnreachableException;

public abstract class RsElementVisitor extends PsiElementVisitor {
    public void visitAttribute(RsAttribute attribute) {
        visitAttributeOrDoc(attribute);
    }

    public void visitAttributeOrDoc(IRsAttribute attributeOrDoc) {
        visitElement(attributeOrDoc);
    }

    public void visitConstItem(RsConstItem constItem) {
        visitItem(constItem);
    }

    public void visitDoc(RsDoc doc) {
        visitAttributeOrDoc(doc);
    }

    public void visitExternCrateDecl(RsExternCrateDecl externCrateDecl) {
        visitItem(externCrateDecl);
    }

    public void visitFile(RsFile file) {
        visitModuleOrFile(file);
    }

    public void visitGlobReferenceElement(RsGlobReferenceElement globReferenceElement) {
        visitIReferenceElement(globReferenceElement);
    }

    public void visitIdentifier(RsIdentifier identifier) {
        visitRustToken(identifier);
    }

    public void visitItem(IRsItem item) {
        visitElement(item);
    }

    public void visitIReferenceElement(IRsReferenceElement referenceElement) {
        visitElement(referenceElement);
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

    public void visitListReferenceElement(RsListReferenceElement listReferenceElement) {
        visitIReferenceElement(listReferenceElement);
    }

    public void visitLiteral(RsLiteral literal) {
        visitElement(literal); // TODO:RJP-12 visit expression
    }

    public void visitMeta(RsMeta meta) {
        visitElement(meta);
    }

    public void visitMetaList(RsMetaList metaList) {
        visitElement(metaList);
    }

    public void visitModifierList(RsModifierList modifierList) {
        visitElement(modifierList);
    }

    public void visitModule(RsModule module) {
        visitModuleOrFile(module);
    }

    public void visitModuleOrFile(IRsModule module) {
        if (module instanceof PsiFile) {
            super.visitFile((PsiFile) module);
        } else if (module instanceof RsModule) {
            visitItem((RsModule) module);
        } else {
            throw new UnreachableException();
        }
    }

    public void visitPathType(RsPathType pathType) {
        visitType(pathType);
    }

    public void visitReferenceElement(RsReferenceElement referenceElement) {
        visitIReferenceElement(referenceElement);
    }

    public void visitRelationReferenceElement(RsRelationReferenceElement relationReferenceElement) {
        visitIReferenceElement(relationReferenceElement);
    }

    public void visitRustToken(RsToken token) {
        visitElement(token);
    }

    public void visitStaticItem(RsStaticItem staticItem) {
        visitItem(staticItem);
    }

    public void visitStruct(RsStruct struct) {
        visitItem(struct);
    }

    public void visitStructField(RsStructField field) {
        visitElement(field);
    }

    public void visitStructType(RsStructType structType) {
        visitType(structType);
    }

    public void visitTupleType(RsTupleType tupleType) {
        visitType(tupleType);
    }

    public void visitType(IRsType type) {
        visitElement(type);
    }

    public void visitTypeAlias(RsTypeAlias typeAlias) {
        visitItem(typeAlias);
    }

    public void visitTypedReferenceElement(RsTypedReferenceElement typedReferenceElement) {
        visitIReferenceElement(typedReferenceElement);
    }

    public void visitTypeList(RsTypeList typeList) {
        visitElement(typeList);
    }

    public void visitTypeParameter(RsTypeParameter typeParameter) {
        visitElement(typeParameter);
    }

    public void visitTypeParameterList(RsTypeParameterList typeParameterList) {
        visitElement(typeParameterList);
    }

    public void visitUnitType(RsUnitType unitType) {
        visitTupleType(unitType);
    }

    public void visitUseDecl(RsUseDecl useDecl) {
        visitItem(useDecl);
    }

    public void visitWhereClause(RsWhereClause whereClause) {
        visitElement(whereClause);
    }
}
