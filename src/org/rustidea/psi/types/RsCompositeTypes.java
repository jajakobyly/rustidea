package org.rustidea.psi.types;

import com.intellij.psi.tree.IElementType;
import org.rustidea.psi.impl.*;

public interface RsCompositeTypes {
    IElementType ATTRIBUTE = new IRsCompositeElementType("ATTRIBUTE", RsAttributeImpl.class);
    IElementType DOC = new IRsCompositeElementType("DOC", RsDocImpl.class);
    IElementType GLOB_REFERENCE_ELEMENT = new IRsCompositeElementType("GLOB_REFERENCE_ELEMENT", RsGlobReferenceElementImpl.class);
    IElementType LIFETIME = new IRsCompositeElementType("LIFETIME", "lifetime", RsLifetimeImpl.class);
    IElementType LIFETIME_TYPE_PARAMETER = new IRsCompositeElementType("LIFETIME_TYPE_PARAMETER", RsLifetimeTypeParameterImpl.class);
    IElementType LIST_REFERENCE_ELEMENT = new IRsCompositeElementType("LIST_REFERENCE_ELEMENT", RsListReferenceElementImpl.class);
    IElementType LITERAL = new IRsCompositeElementType("LITERAL", "literal", RsLiteralImpl.class);
    IElementType META = new IRsCompositeElementType("META", RsMetaImpl.class);
    IElementType META_LIST = new IRsCompositeElementType("META_LIST", RsMetaListImpl.class);
    IElementType MODIFIER_LIST = new IRsCompositeElementType("MODIFIER_LIST", RsModifierListImpl.class);
    IElementType PATH_TYPE = new IRsCompositeElementType("PATH_TYPE", RsPathTypeImpl.class);
    IElementType REFERENCE_ELEMENT = new IRsCompositeElementType("REFERENCE_ELEMENT", RsReferenceElementImpl.class);
    IElementType RELATION_REFERENCE_ELEMENT = new IRsCompositeElementType("RELATION_REFERENCE_ELEMENT", RsRelationReferenceElementImpl.class);
    IElementType TUPLE_TYPE = new IRsCompositeElementType("TUPLE_TYPE", RsTupleTypeImpl.class);
    IElementType TYPE_LIST = new IRsCompositeElementType("TYPE_LIST", RsTypeListImpl.class);
    IElementType TYPE_PARAMETER = new IRsCompositeElementType("TYPE_PARAMETER", RsTypeParameterImpl.class);
    IElementType TYPE_PARAMETER_LIST = new IRsCompositeElementType("TYPE_PARAMETER_LIST", RsTypeParameterListImpl.class);
    IElementType TYPED_REFERENCE_ELEMENT = new IRsCompositeElementType("TYPED_REFERENCE_ELEMENT", RsTypedReferenceElementImpl.class);
    IElementType UNIT_TYPE = new IRsCompositeElementType("UNIT_TYPE", RsUnitTypeImpl.class);
    IElementType WHERE_CLAUSE = new IRsCompositeElementType("WHERE_CLAUSE", RsWhereClauseImpl.class);
}
