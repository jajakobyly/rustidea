package org.rustidea.psi.types;

import com.intellij.psi.tree.IElementType;
import org.rustidea.psi.impl.*;

public interface RsCompositeTypes {
    IElementType GLOB_REFERENCE_ELEMENT = new IRsCompositeElementType("GLOB_REFERENCE_ELEMENT", RsGlobReferenceElementImpl.class);
    IElementType LIST_REFERENCE_ELEMENT = new IRsCompositeElementType("LIST_REFERENCE_ELEMENT", RsListReferenceElementImpl.class);
    IElementType LITERAL = new IRsCompositeElementType("LITERAL", "literal", RsLiteralImpl.class);
    IElementType RELATION_REFERENCE_ELEMENT = new IRsCompositeElementType("RELATION_REFERENCE_ELEMENT", RsRelationReferenceElementImpl.class);
    IElementType REFERENCE_ELEMENT = new IRsCompositeElementType("REFERENCE_ELEMENT", RsReferenceElementImpl.class);
}
