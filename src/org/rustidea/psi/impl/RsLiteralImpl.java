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

package org.rustidea.psi.impl;

import com.intellij.openapi.util.text.StringUtil;
import com.intellij.psi.tree.IElementType;
import com.intellij.psi.util.PsiTreeUtil;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.rustidea.psi.RsElementVisitor;
import org.rustidea.psi.RsLiteral;
import org.rustidea.psi.RsToken;
import org.rustidea.psi.types.RsTypes;
import org.rustidea.psi.util.PsiImplUtil;
import org.rustidea.psi.util.RsLiteralUtil;
import org.rustidea.psi.util.RsStringUtil;

import static org.rustidea.psi.types.RsTokenTypes.*;

public class RsLiteralImpl extends IRsCompositePsiElement implements RsLiteral {
    private static final String TRUE = "true";
    private static final String FALSE = "false";

    public RsLiteralImpl() {
        super(RsTypes.LITERAL);
    }

    @NotNull
    @Override
    public IElementType getTokenType() {
        RsToken token = PsiTreeUtil.getRequiredChildOfType(this, RsToken.class);
        return token.getTokenType();
    }

    @Nullable
    @Override
    public Object getValue() {
        // TODO Implement this.
        throw new UnsupportedOperationException("not implemented yet");
    }

    @Nullable
    @Override
    public String getValueString() {
        IElementType tokenType = getTokenType();

        if (tokenType == KW_TRUE) return TRUE;
        if (tokenType == KW_FALSE) return FALSE;

        String text = getText();
        String noSuffix = StringUtil.trimEnd(text, getSuffix());

        if (tokenType == INT_LIT || tokenType == FLOAT_LIT) {
            return noSuffix;
        }

        boolean isByte = noSuffix.startsWith("b");
        String noB = StringUtil.trimStart(noSuffix, "b");

        if (tokenType == CHAR_LIT || tokenType == BYTE_LIT) {
            return RsStringUtil.unescapeRust(RsStringUtil.unquote(noB, '\''), !isByte, false);
        }

        if (tokenType == STRING_LIT || tokenType == BYTE_STRING_LIT) {
            return RsStringUtil.unescapeRust(RsStringUtil.unquote(noB, '"'), !isByte, true);
        }

        if (tokenType == RAW_STRING_LIT || tokenType == RAW_BYTE_STRING_LIT) {
            // TODO Implement this.
            throw new UnsupportedOperationException("not implemented yet");
        }

        return null;
    }

    @NotNull
    @Override
    public String getSuffix() {
        IElementType tokenType = getTokenType();

        if (tokenType == KW_TRUE || tokenType == KW_FALSE) return "";

        String text = getText();

        if (tokenType == INT_LIT || tokenType == FLOAT_LIT) {
            return RsLiteralUtil.extractSuffixFromNumLit(text);
        }

        if (tokenType == CHAR_LIT || tokenType == BYTE_LIT) {
            return RsLiteralUtil.extractSuffixFromQuotedLit(text, '\'');
        }

        if (tokenType == STRING_LIT || tokenType == BYTE_STRING_LIT) {
            return RsLiteralUtil.extractSuffixFromQuotedLit(text, '"');
        }

        if (tokenType == RAW_STRING_LIT || tokenType == RAW_BYTE_STRING_LIT) {
            return RsLiteralUtil.extractSuffixFromRawStr(text);
        }

        throw new UnsupportedOperationException("unreachable");
    }

    @Override
    public void accept(@NotNull RsElementVisitor visitor) {
        visitor.visitLiteral(this);
    }

    @NotNull
    @Override
    public String toString() {
        return PsiImplUtil.getPsiClassName(this) + ":" + getText();
    }
}
