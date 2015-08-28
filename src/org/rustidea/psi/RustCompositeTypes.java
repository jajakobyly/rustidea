package org.rustidea.psi;

import com.intellij.psi.tree.IElementType;

public interface RustCompositeTypes {
    IElementType ASSIGN_ATTRIBUTE_ITEM = new RustCompositeElementType("ASSIGN_ATTRIBUTE_ITEM");
    IElementType ATTRIBUTE = new RustCompositeElementType("ATTRIBUTE");
    IElementType ATTRIBUTE_ITEM = new RustCompositeElementType("ATTRIBUTE_ITEM");
    IElementType DOC = new RustCompositeElementType("DOC");
    IElementType FLAG_ATTRIBUTE_ITEM = new RustCompositeElementType("FLAG_ATTRIBUTE_ITEM");
    IElementType FN_ATTRIBUTE_ITEM = new RustCompositeElementType("FN_ATTRIBUTE_ITEM");
    IElementType FN_ITEM = new RustCompositeElementType("FN_ITEM");
    IElementType ITEM = new RustCompositeElementType("ITEM");
    IElementType ITEM_ATTRS = new RustCompositeElementType("ITEM_ATTRS");
    IElementType LITERAL = new RustCompositeElementType("LITERAL");
    IElementType MOD_ITEM = new RustCompositeElementType("MOD_ITEM");
    IElementType PARENT_ATTRIBUTE = new RustCompositeElementType("PARENT_ATTRIBUTE");
    IElementType PARENT_DOC = new RustCompositeElementType("PARENT_DOC");
    IElementType VISIBILITY = new RustCompositeElementType("VISIBILITY");

}
