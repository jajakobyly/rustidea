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
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.rustidea.psi.RsElementVisitor;
import org.rustidea.psi.RsLiteral;
import org.rustidea.psi.RsToken;
import org.rustidea.psi.types.RsPsiTypes;
import org.rustidea.psi.util.RsLiteralUtil;
import org.rustidea.psi.util.RsPsiTreeUtil;
import org.rustidea.psi.util.RsPsiUtil;
import org.rustidea.psi.util.RsStringUtil;
import org.rustidea.util.NotImplementedException;
import org.rustidea.util.UnreachableException;

import static org.rustidea.psi.types.RsTokenTypes.KW_FALSE;
import static org.rustidea.psi.types.RsTokenTypes.KW_TRUE;

public class RsLiteralImpl extends IRsCompositePsiElement implements RsLiteral {
    public RsLiteralImpl() {
        super(RsPsiTypes.LITERAL);
    }

    @NotNull
    @Override
    public IElementType getTokenType() {
        RsToken token = RsPsiTreeUtil.getRequiredChildOfType(this, RsToken.class);
        return token.getTokenType();
    }

    @Nullable
    @Override
    public Object getValue() {
        // TODO:RJP-40 Implement this.
        throw new NotImplementedException();
    }

    @NotNull
    @Override
    public String getValueString() {
        final IElementType tokenType = getTokenType();
        final String text = getText();

        if (tokenType == KW_TRUE || tokenType == KW_FALSE) return text;

        boolean isByte = text.startsWith("b");
        String raw = RsLiteralUtil.removeDecoration(this);

        if (RsPsiTypes.NUMBER_TOKEN_SET.contains(tokenType)) {
            return raw;
        }

        if (RsPsiTypes.CHAR_TOKEN_SET.contains(tokenType)) {
            return RsStringUtil.unescapeRust(RsStringUtil.unquote(raw, '\''), !isByte, false);
        }

        if (RsPsiTypes.STRING_TOKEN_SET.contains(tokenType)) {
            return RsStringUtil.unescapeRust(RsStringUtil.unquote(raw, '"'), !isByte, true);
        }

        if (RsPsiTypes.RAW_STRING_TOKEN_SET.contains(tokenType)) {
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

        if (RsPsiTypes.NUMBER_TOKEN_SET.contains(tokenType)) {
            return RsLiteralUtil.extractSuffixFromNumLit(text);
        }

        if (RsPsiTypes.CHAR_TOKEN_SET.contains(tokenType)) {
            return RsLiteralUtil.extractSuffixFromQuotedLit(text, '\'');
        }

        if (RsPsiTypes.STRING_TOKEN_SET.contains(tokenType)) {
            return RsLiteralUtil.extractSuffixFromQuotedLit(text, '"');
        }

        if (RsPsiTypes.RAW_STRING_TOKEN_SET.contains(tokenType)) {
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
        return RsPsiUtil.getPsiClassName(this) + ":" + getText();
    }
}
