package org.rustidea.psi.types;

import com.intellij.psi.tree.IElementType;
import org.rustidea.psi.impl.*;

public interface RsCompositeTypes {
    IElementType ARRAY_TYPE = new IRsCompositeElementType("ARRAY_TYPE", RsArrayTypeImpl.class);
    IElementType ATTRIBUTE = new IRsCompositeElementType("ATTRIBUTE", RsAttributeImpl.class);
    IElementType DIVERGING_TYPE = new IRsCompositeElementType("DIVERGING_TYPE", RsDivergingTypeImpl.class);
    IElementType DOC = new IRsCompositeElementType("DOC", RsDocImpl.class);
    IElementType EXTERN_MODIFIER = new IRsCompositeElementType("EXTERN_MODIFIER", RsExternModifierImpl.class);
    IElementType FUNCTION_TYPE = new IRsCompositeElementType("FUNCTION_TYPE", RsFunctionTypeImpl.class);
    IElementType GLOB_REFERENCE_ELEMENT = new IRsCompositeElementType("GLOB_REFERENCE_ELEMENT", RsGlobReferenceElementImpl.class);
    IElementType INPUT_TYPE_LIST = new IRsCompositeElementType("INPUT_TYPE_LIST", "input types list", RsInputTypeListImpl.class);
    IElementType LIFETIME = new IRsCompositeElementType("LIFETIME", "lifetime", RsLifetimeImpl.class);
    IElementType LIFETIME_TYPE_PARAMETER = new IRsCompositeElementType("LIFETIME_TYPE_PARAMETER", RsLifetimeTypeParameterImpl.class);
    IElementType LIST_REFERENCE_ELEMENT = new IRsCompositeElementType("LIST_REFERENCE_ELEMENT", RsListReferenceElementImpl.class);
    IElementType LITERAL = new IRsCompositeElementType("LITERAL", "literal", RsLiteralImpl.class);
    IElementType META = new IRsCompositeElementType("META", RsMetaImpl.class);
    IElementType META_LIST = new IRsCompositeElementType("META_LIST", RsMetaListImpl.class);
    IElementType MODIFIER_LIST = new IRsCompositeElementType("MODIFIER_LIST", RsModifierListImpl.class);
    IElementType PATH_TYPE = new IRsCompositeElementType("PATH_TYPE", RsPathTypeImpl.class);
    IElementType RAW_POINTER_TYPE = new IRsCompositeElementType("RAW_POINTER_TYPE", RsRawPointerTypeImpl.class);
    IElementType REFERENCE_ELEMENT = new IRsCompositeElementType("REFERENCE_ELEMENT", RsReferenceElementImpl.class);
    IElementType REFERENCE_TYPE = new IRsCompositeElementType("REFERENCE_TYPE", RsReferenceTypeImpl.class);
    IElementType RELATION_REFERENCE_ELEMENT = new IRsCompositeElementType("RELATION_REFERENCE_ELEMENT", RsRelationReferenceElementImpl.class);
    IElementType SLICE_TYPE = new IRsCompositeElementType("SLICE_TYPE", RsSliceTypeImpl.class);
    IElementType TUPLE_TYPE = new IRsCompositeElementType("TUPLE_TYPE", RsTupleTypeImpl.class);
    IElementType TYPE_LIST = new IRsCompositeElementType("TYPE_LIST", RsTypeListImpl.class);
    IElementType TYPE_PARAMETER = new IRsCompositeElementType("TYPE_PARAMETER", RsTypeParameterImpl.class);
    IElementType TYPE_PARAMETER_LIST = new IRsCompositeElementType("TYPE_PARAMETER_LIST", RsTypeParameterListImpl.class);
    IElementType TYPED_REFERENCE_ELEMENT = new IRsCompositeElementType("TYPED_REFERENCE_ELEMENT", RsTypedReferenceElementImpl.class);
    IElementType UNIT_TYPE = new IRsCompositeElementType("UNIT_TYPE", RsUnitTypeImpl.class);
    IElementType WILDCARD_TYPE = new IRsCompositeElementType("WILDCARD_TYPE", RsWildcardTypeImpl.class);
    IElementType WHERE_CLAUSE = new IRsCompositeElementType("WHERE_CLAUSE", RsWhereClauseImpl.class);
}
