package org.rustidea.psi;

import com.intellij.psi.tree.IElementType;
import org.rustidea.psi.impl.RustDocImpl;
import org.rustidea.psi.impl.RustParentDocImpl;

public interface RustCompositeType {
    IElementType PARENT_DOC = new IRustCompositeElementType("PARENT_DOC", RustParentDocImpl.class);
    IElementType DOC = new IRustCompositeElementType("DOC", RustDocImpl.class);
}
