package org.rustidea.psi.types;

import com.intellij.psi.tree.IElementType;
import org.rustidea.psi.impl.RsLiteralImpl;
import org.rustidea.psi.impl.RsReferenceElementImpl;
import org.rustidea.psi.impl.RsRelationReferenceElementImpl;

public interface RsCompositeTypes {
    IElementType LITERAL = new IRsCompositeElementType("LITERAL", RsLiteralImpl.class);
    IElementType RELATION_REFERENCE_ELEMENT = new IRsCompositeElementType("RELATION_REFERENCE_ELEMENT", RsRelationReferenceElementImpl.class);
    IElementType REFERENCE_ELEMENT = new IRsCompositeElementType("REFERENCE_ELEMENT", RsReferenceElementImpl.class);
}
