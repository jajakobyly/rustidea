/*
 * Copyright 2015 Marek Kaput
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.rustidea.psi.util;

import com.google.common.base.Strings;
import com.google.common.collect.ImmutableMap;
import com.intellij.openapi.util.text.StringUtil;
import com.intellij.psi.PsiElement;
import com.intellij.psi.StubBasedPsiElement;
import com.intellij.psi.stubs.StubElement;
import com.intellij.psi.tree.IElementType;
import com.intellij.psi.util.PsiUtilBase;
import com.intellij.util.ArrayUtil;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.rustidea.psi.*;
import org.rustidea.psi.impl.IRsStubPsiElement;
import org.rustidea.psi.types.RsTypes;
import org.rustidea.util.SimpleArrayFactory;

import java.util.Map;

import static org.rustidea.psi.types.RsCompositeTypes.LITERAL;
import static org.rustidea.psi.types.RsTokenTypes.*;

public final class RsPsiUtil extends PsiUtilBase {
    private static final Map<IElementType, String> HUMAN_READABLE_NAMES =
        new ImmutableMap.Builder<IElementType, String>()
            .put(INT_LIT, "numeric literal")
            .put(FLOAT_LIT, "float literal")
            .put(CHAR_LIT, "char literal")
            .put(BYTE_LIT, "byte literal")
            .put(STRING_LIT, "string literal")
            .put(BYTE_STRING_LIT, "byte string literal")
            .put(RAW_STRING_LIT, "raw string literal")
            .put(RAW_BYTE_STRING_LIT, "raw byte string literal")
            .put(IDENTIFIER, "identifier")
            .put(LIFETIME_TOKEN, "lifetime")
            .put(LITERAL, "literal")
            .build();

    private RsPsiUtil() {
    }

    @Nullable
    @Contract(value = "null -> null", pure = true)
    public static String getHumanReadableName(@Nullable final IElementType tokenType) {
        if (tokenType == null) return null;
        if (HUMAN_READABLE_NAMES.containsKey(tokenType)) {
            return HUMAN_READABLE_NAMES.get(tokenType);
        }
        if (RsTypes.KEYWORD_TOKEN_SET.contains(tokenType) || RsTypes.OPERATOR_TOKEN_SET.contains(tokenType)) {
            return '\'' + tokenType.toString() + '\'';
        }
        return tokenType.toString(); // fallback to debug name
    }

    @Nullable
    @Contract(value = "null, _ -> null", pure = true)
    public static String getHumanReadableName(@Nullable final IElementType tokenType, final int n) {
        if (tokenType == null) return null;
        final String name = getHumanReadableName(tokenType);
        if (Strings.isNullOrEmpty(name)) return null;
        return StringUtil.pluralize(name, n);
    }

    public static boolean hasTypeParameters(@NotNull final IRsTypeParameterListOwner owner) {
        final RsTypeParameterList list = owner.getTypeParameterList();
        return list != null && list.getTypeParameters().length > 0;
    }

    @NotNull
    public static RsTypeParameter[] getTypeParameters(@NotNull final IRsTypeParameterListOwner owner) {
        final RsTypeParameterList list = owner.getTypeParameterList();
        if (list != null) {
            return list.getTypeParameters();
        }
        return SimpleArrayFactory.empty(RsTypeParameter.class);
    }

    public static <ElemT extends PsiElement> int getElementIndex(
        @NotNull final ElemT element, @NotNull final Class<ElemT> cls) {
        int result = 0;
        for (PsiElement e = element.getPrevSibling(); e != null; e = e.getPrevSibling()) {
            if (cls.isInstance(e)) {
                result++;
            }
        }
        return result;
    }

    public static <ElemT extends StubBasedPsiElement> int getStubElementIndex(
        @NotNull final ElemT element, @Nullable final StubElement stub, @NotNull final Class<ElemT> elemCls) {
        if (stub != null) {
            return stub.getParentStub().getChildrenStubs().indexOf(stub);
        }
        return getElementIndex(element, elemCls);
    }

    @NotNull
    public static String getPsiClassName(@NotNull final Object element) {
        return StringUtil.trimEnd(element.getClass().getSimpleName(), "Impl");
    }

    @NotNull
    public static <T extends IRsStubPsiElement & IRsModifierListOwner> IRsAttribute[] getAttributes(@NotNull final T element) {
        RsModifierList modifierList = element.getModifierList();
        IRsAttribute[] a1 = modifierList != null ? modifierList.getAttributes() : SimpleArrayFactory.empty(IRsAttribute.class);

        IRsAttribute[] a2 = (IRsAttribute[]) element.getStubOrPsiChildren(
            RsTypes.ATTRIBUTE_OR_DOC_TOKEN_SET, SimpleArrayFactory.get(IRsAttribute.class));

        return ArrayUtil.mergeArrays(a1, a2, SimpleArrayFactory.get(IRsAttribute.class));
    }
}
