package org.rustidea.psi.types;

import com.intellij.psi.tree.IElementType;
import org.rustidea.psi.impl.*;

public interface RsCompositeTypes {
    IElementType ATTRIBUTE = new IRsCompositeElementType("ATTRIBUTE", RsAttributeImpl.class);
    IElementType ATTRIBUTE_ITEM = new IRsCompositeElementType("ATTRIBUTE_ITEM", RsAttributeItemImpl.class);
    IElementType ATTRIBUTE_ITEM_LIST = new IRsCompositeElementType("ATTRIBUTE_ITEM_LIST", RsAttributeItemListImpl.class);
    IElementType DOC = new IRsCompositeElementType("DOC", RsDocImpl.class);
    IElementType GLOB_REFERENCE_ELEMENT = new IRsCompositeElementType("GLOB_REFERENCE_ELEMENT", RsGlobReferenceElementImpl.class);
    IElementType LIFETIME = new IRsCompositeElementType("LIFETIME", RsLifetimeImpl.class);
    IElementType LIFETIME_TYPE_PARAMETER = new IRsCompositeElementType("LIFETIME_TYPE_PARAMETER", RsLifetimeTypeParameterImpl.class);
    IElementType LIST_REFERENCE_ELEMENT = new IRsCompositeElementType("LIST_REFERENCE_ELEMENT", RsListReferenceElementImpl.class);
    IElementType LITERAL = new IRsCompositeElementType("LITERAL", "literal", RsLiteralImpl.class);
    IElementType MODIFIER_LIST = new IRsCompositeElementType("MODIFIER_LIST", RsModifierListImpl.class);
    IElementType REFERENCE_ELEMENT = new IRsCompositeElementType("REFERENCE_ELEMENT", RsReferenceElementImpl.class);
    IElementType RELATION_REFERENCE_ELEMENT = new IRsCompositeElementType("RELATION_REFERENCE_ELEMENT", RsRelationReferenceElementImpl.class);
    IElementType TYPE_PARAMETER = new IRsCompositeElementType("TYPE_PARAMETER", RsTypeParameterImpl.class);
    IElementType TYPE_PARAMETER_LIST = new IRsCompositeElementType("TYPE_PARAMETER_LIST", RsTypeParameterListImpl.class);
    IElementType WHERE_CLAUSE = new IRsCompositeElementType("WHERE_CLAUSE", RsWhereClauseImpl.class);
}
