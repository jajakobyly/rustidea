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

package org.rustidea.psi;

import com.intellij.psi.tree.IElementType;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.rustidea.psi.types.RsPsiTypes;

public interface RsLiteral extends IRsPsiElement {
    /**
     * Get {@link IElementType} of underlying token.
     */
    @NotNull
    IElementType getTokenType();

    /**
     * Get literal value converted into Java counterpart.
     *
     * <p><table>
     * <tr><th>Token</th><th>Java type</th></tr>
     * <tr>
     * <td>{@link RsPsiTypes#KW_TRUE}, {@link RsPsiTypes#KW_FALSE}</td>
     * <td>{@link Boolean}</td>
     * </tr>
     * <tr>
     * <td>{@link RsPsiTypes#INT_LIT}</td>
     * <td>{@link java.math.BigInteger} (Java 6 {@link Long} is too small)</td>
     * </tr>
     * <tr>
     * <td>{@link RsPsiTypes#FLOAT_LIT}</td>
     * <td>{@link Double}</td>
     * </tr>
     * <tr>
     * <td>{@link RsPsiTypes#CHAR_LIT}, {@link RsPsiTypes#BYTE_LIT}</td>
     * <td>{@link Character}</td>
     * </tr>
     * <tr>
     * <td>{@link RsPsiTypes#STRING_LIT}, {@link RsPsiTypes#BYTE_STRING_LIT},
     * {@link RsPsiTypes#RAW_STRING_LIT}, {@link RsPsiTypes#RAW_BYTE_STRING_LIT}</td>
     * <td>{@link String}</td>
     * </tr>
     * </table></p>
     *
     * @return literal value converted into Java counterpart or null if operation failed.
     */
    @Nullable
    Object getValue();

    /**
     * Get literal value.
     *
     * <p><table>
     * <tr><th>Token</th><th>Example</th></tr>
     * <tr>
     * <td>{@link RsPsiTypes#KW_TRUE}, {@link RsPsiTypes#KW_FALSE}</td>
     * <td>{@code true} -> {@code true}</td>
     * </tr>
     * <tr>
     * <td>{@link RsPsiTypes#INT_LIT}, {@link RsPsiTypes#FLOAT_LIT}</td>
     * <td>{@code 100usize} -> {@code 100}, {@code 0xAafi32} -> {@code 0xAaf},
     * {@code 0b100_111} -> {@code 0b100_111}, {@code 123.0E+77f32} -> {@code 123.0E+77}</td>
     * </tr>
     * <tr>
     * <td>{@link RsPsiTypes#CHAR_LIT}, {@link RsPsiTypes#BYTE_LIT}</td>
     * <td>{@code 'a'} -> {@code a}, {@code b'b'suffix} -> {@code b}</td>
     * </tr>
     * <tr>
     * <td>{@link RsPsiTypes#STRING_LIT}, {@link RsPsiTypes#BYTE_STRING_LIT}</td>
     * <td>{@code "foo"} -> {@code foo}, {@code b"foo\nbar"} -> <code>foo<br/>bar</code></td>
     * </tr>
     * <tr>
     * <td>{@link RsPsiTypes#RAW_STRING_LIT}, {@link RsPsiTypes#RAW_BYTE_STRING_LIT}</td>
     * <td>{@code r###"foo " bar"###} -> {@code foo " bar}</td>
     * </tr>
     * </table></p>
     *
     * @return literal value without any decorations and suffixes or null if operation failed.
     */
    @NotNull
    String getValueString();

    /**
     * Get literal suffix.
     *
     * @return suffix string or {@code null} if literal has not suffix.
     * @see org.rustidea.psi.util.RsLiteralUtil#hasValidSuffix(RsLiteral)
     */
    @NotNull
    String getSuffix();
}
