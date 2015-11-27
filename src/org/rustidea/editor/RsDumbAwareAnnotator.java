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

package org.rustidea.editor;

import com.google.common.base.Strings;
import com.google.common.collect.Lists;
import com.intellij.lang.annotation.AnnotationHolder;
import com.intellij.lang.annotation.Annotator;
import com.intellij.openapi.editor.colors.TextAttributesKey;
import com.intellij.openapi.project.DumbAware;
import com.intellij.openapi.util.TextRange;
import com.intellij.psi.PsiElement;
import com.intellij.psi.tree.IElementType;
import org.jetbrains.annotations.NotNull;
import org.rustidea.psi.RsAttribute;
import org.rustidea.psi.RsElementVisitor;
import org.rustidea.psi.RsLiteral;
import org.rustidea.psi.types.RsPsiTypes;
import org.rustidea.psi.util.RsLiteralUtil;
import org.rustidea.psi.util.RsPsiTreeUtil;
import org.rustidea.psi.util.RsPsiUtil;

import java.util.Collection;
import java.util.List;

public class RsDumbAwareAnnotator implements Annotator, DumbAware {
    @Override
    public void annotate(@NotNull PsiElement element, @NotNull AnnotationHolder holder) {
        element.accept(new Visitor(holder));
    }

    private class Visitor extends RsElementVisitor {
        @NotNull
        private final AnnotationHolder holder;

        public Visitor(@NotNull final AnnotationHolder holder) {
            this.holder = holder;
        }

        @Override
        public void visitAttribute(@NotNull RsAttribute attribute) {
            // Putting text attribute key on the whole attribute won't work,
            // because it will override text attributes of literals etc

            final TextAttributesKey textAttributesKey =
                attribute.isInner() ? RsSyntaxHighlighter.INNER_ATTRIBUTE : RsSyntaxHighlighter.ATTRIBUTE;

            TextRange attrRange = attribute.getTextRange();
            final List<TextRange> exclude = Lists.transform(
                RsPsiTreeUtil.getChildrenOfTypeAsListRecursive(attribute, RsLiteral.class),
                RsPsiUtil.TEXT_RANGE_EXTRACTOR);

            for (TextRange rangeToExclude : exclude) {
                assert attrRange.contains(rangeToExclude);

                final TextRange rangeToHighlight =
                    TextRange.create(attrRange.getStartOffset(), rangeToExclude.getStartOffset());
                holder.createInfoAnnotation(rangeToHighlight, null).setTextAttributes(textAttributesKey);

                attrRange = TextRange.create(rangeToExclude.getEndOffset(), attrRange.getEndOffset());
            }

            if (!attrRange.isEmpty()) {
                holder.createInfoAnnotation(attrRange, null).setTextAttributes(textAttributesKey);
            }
        }

        @Override
        public void visitLiteral(@NotNull RsLiteral literal) {
            final IElementType tokenType = literal.getTokenType();
            final String humanReadableName = RsPsiUtil.getHumanReadableName(tokenType);

            // Check character literal length
            if (RsPsiTypes.CHAR_TOKEN_SET.contains(tokenType)) {
                final String value = literal.getValueString();
                if (Strings.isNullOrEmpty(value)) {
                    holder.createErrorAnnotation(literal, "empty " + humanReadableName);
                } else if (value.length() > 1) {
                    holder.createErrorAnnotation(literal, "too many characters in " + humanReadableName);
                }
            }

            // check quotes
            if (!RsLiteralUtil.hasClosedQuotes(literal)) {
                holder.createErrorAnnotation(literal, "unclosed " + humanReadableName);
            }

            // check suffix
            if (!RsLiteralUtil.hasValidSuffix(literal)) {
                final String suffix = literal.getSuffix();
                final Collection<String> possibleSuffixes = RsLiteralUtil.getValidSuffixesFor(literal);
                final StringBuilder sb = new StringBuilder();

                if (!possibleSuffixes.isEmpty()) {
                    sb.append("invalid suffix '")
                        .append(suffix)
                        .append("' for ")
                        .append(humanReadableName)
                        .append("; the suffix must be one of: ");
                    boolean isFirst = true;
                    for (String ps : possibleSuffixes) {
                        if (isFirst) {
                            isFirst = false;
                        } else {
                            sb.append(", ");
                        }
                        sb.append('\'').append(ps).append('\'');
                    }
                } else {
                    sb.append(humanReadableName).append(" with a suffix is invalid");
                }

                holder.createErrorAnnotation(literal, sb.toString());
            }
        }
    }
}
