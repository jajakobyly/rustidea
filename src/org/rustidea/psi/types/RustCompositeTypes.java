package org.rustidea.psi.types;

import com.intellij.psi.tree.IElementType;
import org.rustidea.psi.impl.RustDocImpl;
import org.rustidea.psi.impl.RustParentDocImpl;

public interface RustCompositeTypes {
    IElementType PARENT_DOC = new IRustCompositeElementType("PARENT_DOC", RustParentDocImpl.class);
    IElementType DOC = new IRustCompositeElementType("DOC", RustDocImpl.class);
}
