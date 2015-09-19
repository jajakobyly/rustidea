package org.rustidea.psi.types;

import com.intellij.psi.tree.IElementType;
import org.rustidea.psi.impl.RsLiteralImpl;
import org.rustidea.psi.impl.RsPathRelationImpl;

public interface RsCompositeTypes {
    IElementType LITERAL = new IRsCompositeElementType("LITERAL", RsLiteralImpl.class);
    IElementType PATH_RELATION = new IRsCompositeElementType("PATH_RELATION", RsPathRelationImpl.class);
}
