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
import org.rustidea.util.NotImplementedException;
import org.rustidea.util.UnreachableException;

import static org.rustidea.psi.types.RsTokenTypes.KW_FALSE;
import static org.rustidea.psi.types.RsTokenTypes.KW_TRUE;

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
        throw new NotImplementedException();
    }

    @NotNull
    @Override
    public String getValueString() {
        final IElementType tokenType = getTokenType();

        if (tokenType == KW_TRUE) return TRUE;
        if (tokenType == KW_FALSE) return FALSE;

        String text = getText();
        boolean isByte = text.startsWith("b");
        String raw = RsLiteralUtil.removeDecoration(this);

        if (RsTypes.NUMBER_TOKEN_SET.contains(tokenType)) {
            return raw;
        }

        if (RsTypes.CHAR_TOKEN_SET.contains(tokenType)) {
            return RsStringUtil.unescapeRust(RsStringUtil.unquote(raw, '\''), !isByte, false);
        }

        if (RsTypes.STRING_TOKEN_SET.contains(tokenType)) {
            return RsStringUtil.unescapeRust(RsStringUtil.unquote(raw, '"'), !isByte, true);
        }

        if (RsTypes.RAW_STRING_TOKEN_SET.contains(tokenType)) {
            return RsLiteralUtil.removeRawStringHashes(raw);
        }

        throw new UnreachableException();
    }

    @NotNull
    @Override
    public String getSuffix() {
        final IElementType tokenType = getTokenType();

        if (tokenType == KW_TRUE || tokenType == KW_FALSE) return "";

        String text = getText();

        if (RsTypes.NUMBER_TOKEN_SET.contains(tokenType)) {
            return RsLiteralUtil.extractSuffixFromNumLit(text);
        }

        if (RsTypes.CHAR_TOKEN_SET.contains(tokenType)) {
            return RsLiteralUtil.extractSuffixFromQuotedLit(text, '\'');
        }

        if (RsTypes.STRING_TOKEN_SET.contains(tokenType)) {
            return RsLiteralUtil.extractSuffixFromQuotedLit(text, '"');
        }

        if (RsTypes.RAW_STRING_TOKEN_SET.contains(tokenType)) {
            return RsLiteralUtil.extractSuffixFromRawStr(text);
        }

        throw new UnreachableException();
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
