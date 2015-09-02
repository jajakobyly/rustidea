package org.rustidea.psi.types;

import com.intellij.psi.tree.IElementType;
import org.rustidea.psi.impl.RsDocImpl;
import org.rustidea.psi.impl.RsParentDocImpl;

public interface RsCompositeTypes {
    IElementType PARENT_DOC = new IRsCompositeElementType("PARENT_DOC", RsParentDocImpl.class);
    IElementType DOC = new IRsCompositeElementType("DOC", RsDocImpl.class);
}
