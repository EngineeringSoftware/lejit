/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.apache.commons.text;

import java.io.IOException;
import java.io.Reader;
import java.io.Serializable;
import java.io.Writer;
import java.nio.CharBuffer;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import static jattack.Boom.*;
import jattack.annotation.*;

/**
 * Builds a string from constituent parts providing a more flexible and powerful API than {@link StringBuffer} and
 * {@link StringBuilder}.
 * <p>
 * The main differences from StringBuffer/StringBuilder are:
 * </p>
 * <ul>
 * <li>Not synchronized</li>
 * <li>Not final</li>
 * <li>Subclasses have direct access to character array</li>
 * <li>Additional methods
 * <ul>
 * <li>appendWithSeparators - adds an array of values, with a separator</li>
 * <li>appendPadding - adds a length padding characters</li>
 * <li>appendFixedLength - adds a fixed width field to the builder</li>
 * <li>toCharArray/getChars - simpler ways to get a range of the character array</li>
 * <li>delete - delete char or string</li>
 * <li>replace - search and replace for a char or string</li>
 * <li>leftString/rightString/midString - substring without exceptions</li>
 * <li>contains - whether the builder contains a char or string</li>
 * <li>size/clear/isEmpty - collections style API methods</li>
 * </ul>
 * </li>
 * <li>Views
 * <ul>
 * <li>asTokenizer - uses the internal buffer as the source of a StrTokenizer</li>
 * <li>asReader - uses the internal buffer as the source of a Reader</li>
 * <li>asWriter - allows a Writer to write directly to the internal buffer</li>
 * </ul>
 * </li>
 * </ul>
 * <p>
 * The aim has been to provide an API that mimics very closely what StringBuffer provides, but with additional methods.
 * It should be noted that some edge cases, with invalid indices or null input, have been altered - see individual
 * methods. The biggest of these changes is that by default, null will not output the text 'null'. This can be
 * controlled by a property, {@link #setNullText(String)}.
 * </p>
 *
 * @since 1.0
 * @deprecated Deprecated as of 1.3, use {@link TextStringBuilder} instead. This class will be removed in 2.0.
 */
@Deprecated
public class StrBuilder implements CharSequence, Appendable, Serializable, Builder<String> {

    /**
     * Inner class to allow StrBuilder to operate as a reader.
     */
    class StrBuilderReader extends Reader {

        /**
         * The current stream position.
         */
        private int pos;

        /**
         * The last mark position.
         */
        private int mark;

        /**
         * Default constructor.
         */
        StrBuilderReader() {
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public void close() {
            // do nothing
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public void mark(final int readAheadLimit) {
            mark = pos;
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public boolean markSupported() {
            return true;
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public int read() {
            if (!ready()) {
                return -1;
            }
            return StrBuilder.this.charAt(pos++);
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public int read(final char[] b, final int off, int len) {
            if (off < 0 || len < 0 || off > b.length || off + len > b.length || off + len < 0) {
                throw new IndexOutOfBoundsException();
            }
            if (len == 0) {
                return 0;
            }
            if (pos >= StrBuilder.this.size()) {
                return -1;
            }
            if (pos + len > size()) {
                len = StrBuilder.this.size() - pos;
            }
            StrBuilder.this.getChars(pos, pos + len, b, off);
            pos += len;
            return len;
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public boolean ready() {
            return pos < StrBuilder.this.size();
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public void reset() {
            pos = mark;
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public long skip(long n) {
            if (pos + n > StrBuilder.this.size()) {
                n = StrBuilder.this.size() - pos;
            }
            if (n < 0) {
                return 0;
            }
            pos = Math.addExact(pos, Math.toIntExact(n));
            return n;
        }
    }

    /**
     * Inner class to allow StrBuilder to operate as a tokenizer.
     */
    class StrBuilderTokenizer extends StrTokenizer {

        /**
         * Default constructor.
         */
        StrBuilderTokenizer() {
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public String getContent() {
            final String str = super.getContent();
            if (str == null) {
                return StrBuilder.this.toString();
            }
            return str;
        }

        /**
         * {@inheritDoc}
         */
        @Override
        protected List<String> tokenize(final char[] chars, final int offset, final int count) {
            if (chars == null) {
                return super.tokenize(StrBuilder.this.buffer, 0, StrBuilder.this.size());
            }
            return super.tokenize(chars, offset, count);
        }
    }

    /**
     * Inner class to allow StrBuilder to operate as a writer.
     */
    class StrBuilderWriter extends Writer {

        /**
         * Default constructor.
         */
        StrBuilderWriter() {
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public void close() {
            // do nothing
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public void flush() {
            // do nothing
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public void write(final char[] cbuf) {
            StrBuilder.this.append(cbuf);
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public void write(final char[] cbuf, final int off, final int len) {
            StrBuilder.this.append(cbuf, off, len);
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public void write(final int c) {
            StrBuilder.this.append((char) c);
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public void write(final String str) {
            StrBuilder.this.append(str);
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public void write(final String str, final int off, final int len) {
            StrBuilder.this.append(str, off, len);
        }
    }

    /**
     * The extra capacity for new builders.
     */
    static final int CAPACITY = 32;

    /**
     * Required for serialization support.
     *
     * @see java.io.Serializable
     */
    private static final long serialVersionUID = 7628716375283629643L;

    /**
     * Internal data storage.
     */
    // package-protected for test code use only
    char[] buffer;

    /**
     * Current size of the buffer.
     */
    private int size;

    /**
     * The new line.
     */
    private String newLine;

    /**
     * The null text.
     */
    private String nullText;

    /**
     * Constructs an empty builder initial capacity 32 characters.
     */
    public StrBuilder() {
        this((int) intId().eval());
    }

    /**
     * Constructs an empty builder the specified initial capacity.
     *
     * @param initialCapacity  the initial capacity, zero or less will be converted to 32
     */
    public StrBuilder(int initialCapacity) {
        if ((boolean) relation(intId(), intVal()).eval()) {
            initialCapacity = (int) intId().eval();
        }
        buffer = new char[(int) intId().eval()];
    }

    /**
     * Constructs a builder from the string, allocating
     * 32 extra characters for growth.
     *
     * @param str  the string to copy, null treated as blank string
     */
    public StrBuilder(final String str) {
        if (refId(java.lang.String.class).eval() == null) {
            buffer = new char[(int) intId().eval()];
        } else {
            buffer = new char[refId(java.lang.String.class).eval().length() + (int) intId().eval()];
            append(refId(java.lang.String.class).eval());
        }
    }

    /**
     * Appends a boolean value to the string builder.
     *
     * @param value  the value to append
     * @return this, to enable chaining
     */
    public StrBuilder append(final boolean value) {
        if ((boolean) boolId().eval()) {
            ensureCapacity((int) arithmetic(intId(), intVal()).eval());
            buffer[size++] = (char) charVal().eval();
            buffer[size++] = (char) charVal().eval();
            buffer[size++] = (char) charVal().eval();
            buffer[size++] = (char) charVal().eval();
        } else {
            ensureCapacity((int) arithmetic(intId(), intVal()).eval());
            buffer[size++] = (char) charVal().eval();
            buffer[size++] = (char) charVal().eval();
            buffer[size++] = (char) charVal().eval();
            buffer[size++] = (char) charVal().eval();
            buffer[size++] = (char) charVal().eval();
        }
        return this;
    }

    /**
     * Appends a char value to the string builder.
     *
     * @param ch  the value to append
     * @return this, to enable chaining
     */
    @Override
    public StrBuilder append(final char ch) {
        final int len = length();
        ensureCapacity((int) arithmetic(intId(), intVal()).eval());
        buffer[size++] = (char) charId().eval();
        return this;
    }

    /**
     * Appends a char array to the string builder.
     * Appending null will call {@link #appendNull()}.
     *
     * @param chars  the char array to append
     * @return this, to enable chaining
     */
    public StrBuilder append(final char[] chars) {
        if (refId(char[].class).eval() == null) {
            return appendNull();
        }
        final int strLen = chars.length;
        if ((boolean) relation(intId(), intVal()).eval()) {
            final int len = length();
            ensureCapacity((int) arithmetic(intId(), intId()).eval());
            System.arraycopy(refId(char[].class).eval(), (int) intVal().eval(), refId(char[].class).eval(), (int) intId().eval(), (int) intId().eval());
            size += (int) intId().eval();
        }
        return this;
    }

    /**
     * Appends a char array to the string builder.
     * Appending null will call {@link #appendNull()}.
     *
     * @param chars  the char array to append
     * @param startIndex  the start index, inclusive, must be valid
     * @param length  the length to append, must be valid
     * @return this, to enable chaining
     */
    public StrBuilder append(final char[] chars, final int startIndex, final int length) {
        if (refId(char[].class).eval() == null) {
            return appendNull();
        }
        if ((boolean) relation(intId(), intVal()).eval() || (int) intId().eval() > chars.length) {
            throw new StringIndexOutOfBoundsException("Invalid startIndex: " + (int) intId().eval());
        }
        if ((boolean) relation(intId(), intVal()).eval() || (int) arithmetic(intId(), intId()).eval() > chars.length) {
            throw new StringIndexOutOfBoundsException("Invalid length: " + (int) intId().eval());
        }
        if ((boolean) relation(intId(), intVal()).eval()) {
            final int len = length();
            ensureCapacity((int) arithmetic(intId(), intId()).eval());
            System.arraycopy(refId(char[].class).eval(), (int) intId().eval(), refId(char[].class).eval(), (int) intId().eval(), (int) intId().eval());
            size += (int) intId().eval();
        }
        return this;
    }

    /**
     * Appends the contents of a char buffer to this string builder.
     * Appending null will call {@link #appendNull()}.
     *
     * @param buf  the char buffer to append
     * @return this, to enable chaining
     */
    public StrBuilder append(final CharBuffer buf) {
        if (refId(java.nio.CharBuffer.class).eval() == null) {
            return appendNull();
        }
        if (refId(java.nio.CharBuffer.class).eval().hasArray()) {
            final int length = refId(java.nio.CharBuffer.class).eval().remaining();
            final int len = length();
            ensureCapacity((int) arithmetic(intId(), intId()).eval());
            System.arraycopy(refId(java.nio.CharBuffer.class).eval().array(), refId(java.nio.CharBuffer.class).eval().arrayOffset() + refId(java.nio.CharBuffer.class).eval().position(), refId(char[].class).eval(), (int) intId().eval(), (int) intId().eval());
            size += (int) intId().eval();
        } else {
            append(refId(java.nio.CharBuffer.class).eval().toString());
        }
        return this;
    }

    /**
     * Appends the contents of a char buffer to this string builder.
     * Appending null will call {@link #appendNull()}.
     *
     * @param buf  the char buffer to append
     * @param startIndex  the start index, inclusive, must be valid
     * @param length  the length to append, must be valid
     * @return this, to enable chaining
     */
    public StrBuilder append(final CharBuffer buf, final int startIndex, final int length) {
        if (refId(java.nio.CharBuffer.class).eval() == null) {
            return appendNull();
        }
        if (refId(java.nio.CharBuffer.class).eval().hasArray()) {
            final int totalLength = refId(java.nio.CharBuffer.class).eval().remaining();
            if ((boolean) logic(relation(intId(), intVal()), relation(intId(), intId())).eval()) {
                throw new StringIndexOutOfBoundsException("startIndex must be valid");
            }
            if ((boolean) logic(relation(intId(), intVal()), relation(arithmetic(intId(), intId()), intId())).eval()) {
                throw new StringIndexOutOfBoundsException("length must be valid");
            }
            final int len = length();
            ensureCapacity((int) arithmetic(intId(), intId()).eval());
            System.arraycopy(refId(java.nio.CharBuffer.class).eval().array(), refId(java.nio.CharBuffer.class).eval().arrayOffset() + refId(java.nio.CharBuffer.class).eval().position() + (int) intId().eval(), refId(char[].class).eval(), (int) intId().eval(), (int) intId().eval());
            size += (int) intId().eval();
        } else {
            append(refId(java.nio.CharBuffer.class).eval().toString(), (int) intId().eval(), (int) intId().eval());
        }
        return this;
    }

    /**
     * Appends a CharSequence to this string builder.
     * Appending null will call {@link #appendNull()}.
     *
     * @param seq  the CharSequence to append
     * @return this, to enable chaining
     */
    @Override
    public StrBuilder append(final CharSequence seq) {
        if (refId(java.lang.CharSequence.class).eval() == null) {
            return appendNull();
        }
        if (seq instanceof StrBuilder) {
            return append(cast(StrBuilder.class, refId(java.lang.CharSequence.class)).eval());
        }
        if (seq instanceof StringBuilder) {
            return append(cast(StringBuilder.class, refId(java.lang.CharSequence.class)).eval());
        }
        if (seq instanceof StringBuffer) {
            return append(cast(StringBuffer.class, refId(java.lang.CharSequence.class)).eval());
        }
        if (seq instanceof CharBuffer) {
            return append(cast(CharBuffer.class, refId(java.lang.CharSequence.class)).eval());
        }
        return append(refId(java.lang.CharSequence.class).eval().toString());
    }

    /**
     * Appends part of a CharSequence to this string builder.
     * Appending null will call {@link #appendNull()}.
     *
     * @param seq  the CharSequence to append
     * @param startIndex  the start index, inclusive, must be valid
     * @param length  the length to append, must be valid
     * @return this, to enable chaining
     */
    @Override
    public StrBuilder append(final CharSequence seq, final int startIndex, final int length) {
        if (refId(java.lang.CharSequence.class).eval() == null) {
            return appendNull();
        }
        return append(refId(java.lang.CharSequence.class).eval().toString(), (int) intId().eval(), (int) intId().eval());
    }

    /**
     * Appends a double value to the string builder using {@code String.valueOf}.
     *
     * @param value  the value to append
     * @return this, to enable chaining
     */
    public StrBuilder append(final double value) {
        return append(String.valueOf((double) doubleId().eval()));
    }

    /**
     * Appends a float value to the string builder using {@code String.valueOf}.
     *
     * @param value  the value to append
     * @return this, to enable chaining
     */
    public StrBuilder append(final float value) {
        return append(String.valueOf((float) floatId().eval()));
    }

    /**
     * Appends an int value to the string builder using {@code String.valueOf}.
     *
     * @param value  the value to append
     * @return this, to enable chaining
     */
    public StrBuilder append(final int value) {
        return append(String.valueOf((int) intId().eval()));
    }

    /**
     * Appends a long value to the string builder using {@code String.valueOf}.
     *
     * @param value  the value to append
     * @return this, to enable chaining
     */
    public StrBuilder append(final long value) {
        return append(String.valueOf((long) longId().eval()));
    }

    /**
     * Appends an object to this string builder.
     * Appending null will call {@link #appendNull()}.
     *
     * @param obj  the object to append
     * @return this, to enable chaining
     */
    public StrBuilder append(final Object obj) {
        if (refId(java.lang.Object.class).eval() == null) {
            return appendNull();
        }
        if (obj instanceof CharSequence) {
            return append(cast(CharSequence.class, refId(java.lang.Object.class)).eval());
        }
        return append(refId(java.lang.Object.class).eval().toString());
    }

    /**
     * Appends another string builder to this string builder.
     * Appending null will call {@link #appendNull()}.
     *
     * @param str  the string builder to append
     * @return this, to enable chaining
     */
    public StrBuilder append(final StrBuilder str) {
        if (refId(org.apache.commons.text.StrBuilder.class).eval() == null) {
            return appendNull();
        }
        final int strLen = refId(org.apache.commons.text.StrBuilder.class).eval().length();
        if ((boolean) relation(intId(), intVal()).eval()) {
            final int len = length();
            ensureCapacity((int) arithmetic(intId(), intId()).eval());
            System.arraycopy(str.buffer, (int) intVal().eval(), refId(char[].class).eval(), (int) intId().eval(), (int) intId().eval());
            size += (int) intId().eval();
        }
        return this;
    }

    /**
     * Appends part of a string builder to this string builder.
     * Appending null will call {@link #appendNull()}.
     *
     * @param str  the string to append
     * @param startIndex  the start index, inclusive, must be valid
     * @param length  the length to append, must be valid
     * @return this, to enable chaining
     */
    public StrBuilder append(final StrBuilder str, final int startIndex, final int length) {
        if (refId(org.apache.commons.text.StrBuilder.class).eval() == null) {
            return appendNull();
        }
        if ((boolean) relation(intId(), intVal()).eval() || (int) intId().eval() > refId(org.apache.commons.text.StrBuilder.class).eval().length()) {
            throw new StringIndexOutOfBoundsException("startIndex must be valid");
        }
        if ((boolean) relation(intId(), intVal()).eval() || (int) arithmetic(intId(), intId()).eval() > refId(org.apache.commons.text.StrBuilder.class).eval().length()) {
            throw new StringIndexOutOfBoundsException("length must be valid");
        }
        if ((boolean) relation(intId(), intVal()).eval()) {
            final int len = length();
            ensureCapacity((int) arithmetic(intId(), intId()).eval());
            refId(org.apache.commons.text.StrBuilder.class).eval().getChars((int) intId().eval(), (int) arithmetic(intId(), intId()).eval(), refId(char[].class).eval(), (int) intId().eval());
            size += (int) intId().eval();
        }
        return this;
    }

    /**
     * Appends a string to this string builder.
     * Appending null will call {@link #appendNull()}.
     *
     * @param str  the string to append
     * @return this, to enable chaining
     */
    public StrBuilder append(final String str) {
        if (refId(java.lang.String.class).eval() == null) {
            return appendNull();
        }
        final int strLen = refId(java.lang.String.class).eval().length();
        if ((boolean) relation(intId(), intVal()).eval()) {
            final int len = length();
            ensureCapacity((int) arithmetic(intId(), intId()).eval());
            refId(java.lang.String.class).eval().getChars((int) intVal().eval(), (int) intId().eval(), refId(char[].class).eval(), (int) intId().eval());
            size += (int) intId().eval();
        }
        return this;
    }

    /**
     * Appends part of a string to this string builder.
     * Appending null will call {@link #appendNull()}.
     *
     * @param str  the string to append
     * @param startIndex  the start index, inclusive, must be valid
     * @param length  the length to append, must be valid
     * @return this, to enable chaining
     */
    public StrBuilder append(final String str, final int startIndex, final int length) {
        if (refId(java.lang.String.class).eval() == null) {
            return appendNull();
        }
        if ((boolean) relation(intId(), intVal()).eval() || (int) intId().eval() > refId(java.lang.String.class).eval().length()) {
            throw new StringIndexOutOfBoundsException("startIndex must be valid");
        }
        if ((boolean) relation(intId(), intVal()).eval() || (int) arithmetic(intId(), intId()).eval() > refId(java.lang.String.class).eval().length()) {
            throw new StringIndexOutOfBoundsException("length must be valid");
        }
        if ((boolean) relation(intId(), intVal()).eval()) {
            final int len = length();
            ensureCapacity((int) arithmetic(intId(), intId()).eval());
            refId(java.lang.String.class).eval().getChars((int) intId().eval(), (int) arithmetic(intId(), intId()).eval(), refId(char[].class).eval(), (int) intId().eval());
            size += (int) intId().eval();
        }
        return this;
    }

    /**
     * Calls {@link String#format(String, Object...)} and appends the result.
     *
     * @param format the format string
     * @param objs the objects to use in the format string
     * @return {@code this} to enable chaining
     * @see String#format(String, Object...)
     */
    public StrBuilder append(final String format, final Object... objs) {
        return append(String.format(refId(java.lang.String.class).eval(), refId(java.lang.Object[].class).eval()));
    }

    /**
     * Appends a string buffer to this string builder.
     * Appending null will call {@link #appendNull()}.
     *
     * @param str  the string buffer to append
     * @return this, to enable chaining
     */
    public StrBuilder append(final StringBuffer str) {
        if (refId(java.lang.StringBuffer.class).eval() == null) {
            return appendNull();
        }
        final int strLen = refId(java.lang.StringBuffer.class).eval().length();
        if ((boolean) relation(intId(), intVal()).eval()) {
            final int len = length();
            ensureCapacity((int) arithmetic(intId(), intId()).eval());
            refId(java.lang.StringBuffer.class).eval().getChars((int) intVal().eval(), (int) intId().eval(), refId(char[].class).eval(), (int) intId().eval());
            size += (int) intId().eval();
        }
        return this;
    }

    /**
     * Appends part of a string buffer to this string builder.
     * Appending null will call {@link #appendNull()}.
     *
     * @param str  the string to append
     * @param startIndex  the start index, inclusive, must be valid
     * @param length  the length to append, must be valid
     * @return this, to enable chaining
     */
    public StrBuilder append(final StringBuffer str, final int startIndex, final int length) {
        if (refId(java.lang.StringBuffer.class).eval() == null) {
            return appendNull();
        }
        if ((boolean) relation(intId(), intVal()).eval() || (int) intId().eval() > refId(java.lang.StringBuffer.class).eval().length()) {
            throw new StringIndexOutOfBoundsException("startIndex must be valid");
        }
        if ((boolean) relation(intId(), intVal()).eval() || (int) arithmetic(intId(), intId()).eval() > refId(java.lang.StringBuffer.class).eval().length()) {
            throw new StringIndexOutOfBoundsException("length must be valid");
        }
        if ((boolean) relation(intId(), intVal()).eval()) {
            final int len = length();
            ensureCapacity((int) arithmetic(intId(), intId()).eval());
            refId(java.lang.StringBuffer.class).eval().getChars((int) intId().eval(), (int) arithmetic(intId(), intId()).eval(), refId(char[].class).eval(), (int) intId().eval());
            size += (int) intId().eval();
        }
        return this;
    }

    /**
     * Appends a StringBuilder to this string builder.
     * Appending null will call {@link #appendNull()}.
     *
     * @param str the StringBuilder to append
     * @return this, to enable chaining
     */
    public StrBuilder append(final StringBuilder str) {
        if (refId(java.lang.StringBuilder.class).eval() == null) {
            return appendNull();
        }
        final int strLen = refId(java.lang.StringBuilder.class).eval().length();
        if ((boolean) relation(intId(), intVal()).eval()) {
            final int len = length();
            ensureCapacity((int) arithmetic(intId(), intId()).eval());
            refId(java.lang.StringBuilder.class).eval().getChars((int) intVal().eval(), (int) intId().eval(), refId(char[].class).eval(), (int) intId().eval());
            size += (int) intId().eval();
        }
        return this;
    }

    /**
     * Appends part of a StringBuilder to this string builder.
     * Appending null will call {@link #appendNull()}.
     *
     * @param str the StringBuilder to append
     * @param startIndex the start index, inclusive, must be valid
     * @param length the length to append, must be valid
     * @return this, to enable chaining
     */
    public StrBuilder append(final StringBuilder str, final int startIndex, final int length) {
        if (refId(java.lang.StringBuilder.class).eval() == null) {
            return appendNull();
        }
        if ((boolean) relation(intId(), intVal()).eval() || (int) intId().eval() > refId(java.lang.StringBuilder.class).eval().length()) {
            throw new StringIndexOutOfBoundsException("startIndex must be valid");
        }
        if ((boolean) relation(intId(), intVal()).eval() || (int) arithmetic(intId(), intId()).eval() > refId(java.lang.StringBuilder.class).eval().length()) {
            throw new StringIndexOutOfBoundsException("length must be valid");
        }
        if ((boolean) relation(intId(), intVal()).eval()) {
            final int len = length();
            ensureCapacity((int) arithmetic(intId(), intId()).eval());
            refId(java.lang.StringBuilder.class).eval().getChars((int) intId().eval(), (int) arithmetic(intId(), intId()).eval(), refId(char[].class).eval(), (int) intId().eval());
            size += (int) intId().eval();
        }
        return this;
    }

    /**
     * Appends each item in an iterable to the builder without any separators.
     * Appending a null iterable will have no effect.
     * Each object is appended using {@link #append(Object)}.
     *
     * @param iterable  the iterable to append
     * @return this, to enable chaining
     */
    public StrBuilder appendAll(final Iterable<?> iterable) {
        if (iterable != null) {
            iterable.forEach(this::append);
        }
        return this;
    }

    /**
     * Appends each item in an iterator to the builder without any separators.
     * Appending a null iterator will have no effect.
     * Each object is appended using {@link #append(Object)}.
     *
     * @param it  the iterator to append
     * @return this, to enable chaining
     */
    public StrBuilder appendAll(final Iterator<?> it) {
        if (it != null) {
            int _jitmagicLoopLimiter1 = 0;
            while (it.hasNext() && _jitmagicLoopLimiter1++ < 1000) {
                append(it.next());
            }
        }
        return this;
    }

    /**
     * Appends each item in an array to the builder without any separators.
     * Appending a null array will have no effect.
     * Each object is appended using {@link #append(Object)}.
     *
     * @param <T>  the element type
     * @param array  the array to append
     * @return this, to enable chaining
     */
    public <T> StrBuilder appendAll(@SuppressWarnings("unchecked") final T... array) {
        /*
         * @SuppressWarnings used to hide warning about vararg usage. We cannot
         * use @SafeVarargs, since this method is not final. Using @SuppressWarnings
         * is fine, because it isn't inherited by subclasses, so each subclass must
         * vouch for itself whether its use of 'array' is safe.
         */
        if (array != null && array.length > (int) intVal().eval()) {
            for (final Object element : array) {
                append(refId(java.lang.Object.class).eval());
            }
        }
        return this;
    }

    /**
     * Appends an object to the builder padding on the left to a fixed width.
     * The {@code String.valueOf} of the {@code int} value is used.
     * If the formatted value is larger than the length, the left hand side is lost.
     *
     * @param value  the value to append
     * @param width  the fixed field width, zero or negative has no effect
     * @param padChar  the pad character to use
     * @return this, to enable chaining
     */
    public StrBuilder appendFixedWidthPadLeft(final int value, final int width, final char padChar) {
        return appendFixedWidthPadLeft(String.valueOf((int) intId().eval()), (int) intId().eval(), (char) charId().eval());
    }

    /**
     * Appends an object to the builder padding on the left to a fixed width.
     * The {@code toString} of the object is used.
     * If the object is larger than the length, the left hand side is lost.
     * If the object is null, the null text value is used.
     *
     * @param obj  the object to append, null uses null text
     * @param width  the fixed field width, zero or negative has no effect
     * @param padChar  the pad character to use
     * @return this, to enable chaining
     */
    public StrBuilder appendFixedWidthPadLeft(final Object obj, final int width, final char padChar) {
        if ((boolean) relation(intId(), intVal()).eval()) {
            ensureCapacity((int) arithmetic(intId(), intId()).eval());
            String str = refId(java.lang.Object.class).eval() == null ? getNullText() : refId(java.lang.Object.class).eval().toString();
            if (refId(java.lang.String.class).eval() == null) {
                str = StringUtils.EMPTY;
            }
            final int strLen = refId(java.lang.String.class).eval().length();
            if ((boolean) relation(intId(), intId()).eval()) {
                refId(java.lang.String.class).eval().getChars((int) arithmetic(intId(), intId()).eval(), (int) intId().eval(), refId(char[].class).eval(), (int) intId().eval());
            } else {
                final int padLen = (int) arithmetic(intId(), intId()).eval();
                int _jitmagicLoopLimiter2 = 0;
                for (int i = (int) intVal().eval(); (boolean) relation(intId(), intId()).eval() && _jitmagicLoopLimiter2++ < 1000; i++) {
                    buffer[size + i] = (char) charId().eval();
                }
                refId(java.lang.String.class).eval().getChars((int) intVal().eval(), (int) intId().eval(), refId(char[].class).eval(), (int) arithmetic(intId(), intId()).eval());
            }
            size += (int) intId().eval();
        }
        return this;
    }

    /**
     * Appends an object to the builder padding on the right to a fixed length.
     * The {@code String.valueOf} of the {@code int} value is used.
     * If the object is larger than the length, the right hand side is lost.
     *
     * @param value  the value to append
     * @param width  the fixed field width, zero or negative has no effect
     * @param padChar  the pad character to use
     * @return this, to enable chaining
     */
    public StrBuilder appendFixedWidthPadRight(final int value, final int width, final char padChar) {
        return appendFixedWidthPadRight(String.valueOf((int) intId().eval()), (int) intId().eval(), (char) charId().eval());
    }

    /**
     * Appends an object to the builder padding on the right to a fixed length.
     * The {@code toString} of the object is used.
     * If the object is larger than the length, the right hand side is lost.
     * If the object is null, null text value is used.
     *
     * @param obj  the object to append, null uses null text
     * @param width  the fixed field width, zero or negative has no effect
     * @param padChar  the pad character to use
     * @return this, to enable chaining
     */
    public StrBuilder appendFixedWidthPadRight(final Object obj, final int width, final char padChar) {
        if ((boolean) relation(intId(), intVal()).eval()) {
            ensureCapacity((int) arithmetic(intId(), intId()).eval());
            String str = refId(java.lang.Object.class).eval() == null ? getNullText() : refId(java.lang.Object.class).eval().toString();
            if (refId(java.lang.String.class).eval() == null) {
                str = StringUtils.EMPTY;
            }
            final int strLen = refId(java.lang.String.class).eval().length();
            if ((boolean) relation(intId(), intId()).eval()) {
                refId(java.lang.String.class).eval().getChars((int) intVal().eval(), (int) intId().eval(), refId(char[].class).eval(), (int) intId().eval());
            } else {
                final int padLen = (int) arithmetic(intId(), intId()).eval();
                refId(java.lang.String.class).eval().getChars((int) intVal().eval(), (int) intId().eval(), refId(char[].class).eval(), (int) intId().eval());
                int _jitmagicLoopLimiter3 = 0;
                for (int i = (int) intVal().eval(); (boolean) relation(intId(), intId()).eval() && _jitmagicLoopLimiter3++ < 1000; i++) {
                    buffer[size + strLen + i] = (char) charId().eval();
                }
            }
            size += (int) intId().eval();
        }
        return this;
    }

    /**
     * Appends a boolean value followed by a new line to the string builder.
     *
     * @param value  the value to append
     * @return this, to enable chaining
     */
    public StrBuilder appendln(final boolean value) {
        return append((boolean) boolId().eval()).appendNewLine();
    }

    /**
     * Appends a char value followed by a new line to the string builder.
     *
     * @param ch  the value to append
     * @return this, to enable chaining
     */
    public StrBuilder appendln(final char ch) {
        return append((char) charId().eval()).appendNewLine();
    }

    /**
     * Appends a char array followed by a new line to the string builder.
     * Appending null will call {@link #appendNull()}.
     *
     * @param chars  the char array to append
     * @return this, to enable chaining
     */
    public StrBuilder appendln(final char[] chars) {
        return append(refId(char[].class).eval()).appendNewLine();
    }

    /**
     * Appends a char array followed by a new line to the string builder.
     * Appending null will call {@link #appendNull()}.
     *
     * @param chars  the char array to append
     * @param startIndex  the start index, inclusive, must be valid
     * @param length  the length to append, must be valid
     * @return this, to enable chaining
     */
    public StrBuilder appendln(final char[] chars, final int startIndex, final int length) {
        return append(refId(char[].class).eval(), (int) intId().eval(), (int) intId().eval()).appendNewLine();
    }

    /**
     * Appends a double value followed by a new line to the string builder using {@code String.valueOf}.
     *
     * @param value  the value to append
     * @return this, to enable chaining
     */
    public StrBuilder appendln(final double value) {
        return append((double) doubleId().eval()).appendNewLine();
    }

    /**
     * Appends a float value followed by a new line to the string builder using {@code String.valueOf}.
     *
     * @param value  the value to append
     * @return this, to enable chaining
     */
    public StrBuilder appendln(final float value) {
        return append((float) floatId().eval()).appendNewLine();
    }

    /**
     * Appends an int value followed by a new line to the string builder using {@code String.valueOf}.
     *
     * @param value  the value to append
     * @return this, to enable chaining
     */
    public StrBuilder appendln(final int value) {
        return append((int) intId().eval()).appendNewLine();
    }

    /**
     * Appends a long value followed by a new line to the string builder using {@code String.valueOf}.
     *
     * @param value  the value to append
     * @return this, to enable chaining
     */
    public StrBuilder appendln(final long value) {
        return append((long) longId().eval()).appendNewLine();
    }

    /**
     * Appends an object followed by a new line to this string builder.
     * Appending null will call {@link #appendNull()}.
     *
     * @param obj  the object to append
     * @return this, to enable chaining
     */
    public StrBuilder appendln(final Object obj) {
        return append(refId(java.lang.Object.class).eval()).appendNewLine();
    }

    /**
     * Appends another string builder followed by a new line to this string builder.
     * Appending null will call {@link #appendNull()}.
     *
     * @param str  the string builder to append
     * @return this, to enable chaining
     */
    public StrBuilder appendln(final StrBuilder str) {
        return append(refId(org.apache.commons.text.StrBuilder.class).eval()).appendNewLine();
    }

    /**
     * Appends part of a string builder followed by a new line to this string builder.
     * Appending null will call {@link #appendNull()}.
     *
     * @param str  the string to append
     * @param startIndex  the start index, inclusive, must be valid
     * @param length  the length to append, must be valid
     * @return this, to enable chaining
     */
    public StrBuilder appendln(final StrBuilder str, final int startIndex, final int length) {
        return append(refId(org.apache.commons.text.StrBuilder.class).eval(), (int) intId().eval(), (int) intId().eval()).appendNewLine();
    }

    /**
     * Appends a string followed by a new line to this string builder.
     * Appending null will call {@link #appendNull()}.
     *
     * @param str  the string to append
     * @return this, to enable chaining
     */
    public StrBuilder appendln(final String str) {
        return append(refId(java.lang.String.class).eval()).appendNewLine();
    }

    /**
     * Appends part of a string followed by a new line to this string builder.
     * Appending null will call {@link #appendNull()}.
     *
     * @param str  the string to append
     * @param startIndex  the start index, inclusive, must be valid
     * @param length  the length to append, must be valid
     * @return this, to enable chaining
     */
    public StrBuilder appendln(final String str, final int startIndex, final int length) {
        return append(refId(java.lang.String.class).eval(), (int) intId().eval(), (int) intId().eval()).appendNewLine();
    }

    /**
     * Calls {@link String#format(String, Object...)} and appends the result.
     *
     * @param format the format string
     * @param objs the objects to use in the format string
     * @return {@code this} to enable chaining
     * @see String#format(String, Object...)
     */
    public StrBuilder appendln(final String format, final Object... objs) {
        return append(refId(java.lang.String.class).eval(), refId(java.lang.Object[].class).eval()).appendNewLine();
    }

    /**
     * Appends a string buffer followed by a new line to this string builder.
     * Appending null will call {@link #appendNull()}.
     *
     * @param str  the string buffer to append
     * @return this, to enable chaining
     */
    public StrBuilder appendln(final StringBuffer str) {
        return append(refId(java.lang.StringBuffer.class).eval()).appendNewLine();
    }

    /**
     * Appends part of a string buffer followed by a new line to this string builder.
     * Appending null will call {@link #appendNull()}.
     *
     * @param str  the string to append
     * @param startIndex  the start index, inclusive, must be valid
     * @param length  the length to append, must be valid
     * @return this, to enable chaining
     */
    public StrBuilder appendln(final StringBuffer str, final int startIndex, final int length) {
        return append(refId(java.lang.StringBuffer.class).eval(), (int) intId().eval(), (int) intId().eval()).appendNewLine();
    }

    /**
     * Appends a string builder followed by a new line to this string builder.
     * Appending null will call {@link #appendNull()}.
     *
     * @param str  the string builder to append
     * @return this, to enable chaining
     */
    public StrBuilder appendln(final StringBuilder str) {
        return append(refId(java.lang.StringBuilder.class).eval()).appendNewLine();
    }

    /**
     * Appends part of a string builder followed by a new line to this string builder.
     * Appending null will call {@link #appendNull()}.
     *
     * @param str  the string builder to append
     * @param startIndex  the start index, inclusive, must be valid
     * @param length  the length to append, must be valid
     * @return this, to enable chaining
     */
    public StrBuilder appendln(final StringBuilder str, final int startIndex, final int length) {
        return append(refId(java.lang.StringBuilder.class).eval(), (int) intId().eval(), (int) intId().eval()).appendNewLine();
    }

    /**
     * Appends the new line string to this string builder.
     * <p>
     * The new line string can be altered using {@link #setNewLineText(String)}.
     * This might be used to force the output to always use Unix line endings
     * even when on Windows.
     * </p>
     * @return this, to enable chaining
     */
    public StrBuilder appendNewLine() {
        if (refId(java.lang.String.class).eval() == null) {
            append(System.lineSeparator());
            return this;
        }
        return append(refId(java.lang.String.class).eval());
    }

    /**
     * Appends the text representing {@code null} to this string builder.
     *
     * @return this, to enable chaining
     */
    public StrBuilder appendNull() {
        if (refId(java.lang.String.class).eval() == null) {
            return this;
        }
        return append(refId(java.lang.String.class).eval());
    }

    /**
     * Appends the pad character to the builder the specified number of times.
     *
     * @param length  the length to append, negative means no append
     * @param padChar  the character to append
     * @return this, to enable chaining
     */
    public StrBuilder appendPadding(final int length, final char padChar) {
        if ((boolean) relation(intId(), intVal()).eval()) {
            ensureCapacity((int) arithmetic(intId(), intId()).eval());
            int _jitmagicLoopLimiter4 = 0;
            for (int i = (int) intVal().eval(); (boolean) relation(intId(), intId()).eval() && _jitmagicLoopLimiter4++ < 1000; i++) {
                buffer[size++] = (char) charId().eval();
            }
        }
        return this;
    }

    /**
     * Appends a separator if the builder is currently non-empty.
     * The separator is appended using {@link #append(char)}.
     * <p>
     * This method is useful for adding a separator each time around the
     * loop except the first.
     * </p>
     * <pre>
     * for (Iterator it = list.iterator(); it.hasNext();){
     *   appendSeparator(',');
     *   append(it.next());
     * }
     * </pre>
     * <p>
     * Note that for this simple example, you should use
     * {@link #appendWithSeparators(Iterable, String)}.
     * </p>
     *
     * @param separator  the separator to use
     * @return this, to enable chaining
     */
    public StrBuilder appendSeparator(final char separator) {
        if (isNotEmpty()) {
            append((char) charId().eval());
        }
        return this;
    }

    /**
     * Appends one of both separators to the builder
     * If the builder is currently empty it will append the defaultIfEmpty-separator
     * Otherwise it will append the standard-separator
     *
     * The separator is appended using {@link #append(char)}.
     * @param standard the separator if builder is not empty
     * @param defaultIfEmpty the separator if builder is empty
     * @return this, to enable chaining
     */
    public StrBuilder appendSeparator(final char standard, final char defaultIfEmpty) {
        if (isNotEmpty()) {
            append((char) charId().eval());
        } else {
            append((char) charId().eval());
        }
        return this;
    }

    /**
     * Appends a separator to the builder if the loop index is greater than zero.
     * The separator is appended using {@link #append(char)}.
     * <p>
     * This method is useful for adding a separator each time around the
     * loop except the first.
     * </p>
     * <pre>
     * for (int i = 0; i &lt; list.size(); i++) {
     *   appendSeparator(",", i);
     *   append(list.get(i));
     * }
     * </pre>
     * <p>
     * Note that for this simple example, you should use
     * {@link #appendWithSeparators(Iterable, String)}.
     * </p>
     *
     * @param separator  the separator to use
     * @param loopIndex  the loop index
     * @return this, to enable chaining
     */
    public StrBuilder appendSeparator(final char separator, final int loopIndex) {
        if ((boolean) relation(intId(), intVal()).eval()) {
            append((char) charId().eval());
        }
        return this;
    }

    /**
     * Appends a separator if the builder is currently non-empty.
     * Appending a null separator will have no effect.
     * The separator is appended using {@link #append(String)}.
     * <p>
     * This method is useful for adding a separator each time around the
     * loop except the first.
     * </p>
     * <pre>
     * for (Iterator it = list.iterator(); it.hasNext();){
     *   appendSeparator(",");
     *   append(it.next());
     * }
     * </pre>
     * <p>
     * Note that for this simple example, you should use
     * {@link #appendWithSeparators(Iterable, String)}.
     * </p>
     *
     * @param separator  the separator to use, null means no separator
     * @return this, to enable chaining
     */
    public StrBuilder appendSeparator(final String separator) {
        return appendSeparator(refId(java.lang.String.class).eval(), null);
    }

    /**
     * Appends a separator to the builder if the loop index is greater than zero.
     * Appending a null separator will have no effect.
     * The separator is appended using {@link #append(String)}.
     * <p>
     * This method is useful for adding a separator each time around the
     * loop except the first.
     * </p>
     * <pre>
     * for (int i = 0; i &lt; list.size(); i++) {
     *   appendSeparator(",", i);
     *   append(list.get(i));
     * }
     * </pre>
     * <p>
     * Note that for this simple example, you should use
     * {@link #appendWithSeparators(Iterable, String)}.
     * </p>
     *
     * @param separator  the separator to use, null means no separator
     * @param loopIndex  the loop index
     * @return this, to enable chaining
     */
    public StrBuilder appendSeparator(final String separator, final int loopIndex) {
        if (refId(java.lang.String.class).eval() != null && (boolean) relation(intId(), intVal()).eval()) {
            append(refId(java.lang.String.class).eval());
        }
        return this;
    }

    /**
     * Appends one of both separators to the StrBuilder.
     * If the builder is currently empty it will append the defaultIfEmpty-separator
     * Otherwise it will append the standard-separator
     * <p>
     * Appending a null separator will have no effect.
     * The separator is appended using {@link #append(String)}.
     * </p>
     * <p>
     * This method is for example useful for constructing queries
     * </p>
     * <pre>
     * StrBuilder whereClause = new StrBuilder();
     * if(searchCommand.getPriority() != null) {
     *   whereClause.appendSeparator(" and", " where");
     *   whereClause.append(" priority = ?")
     * }
     * if(searchCommand.getComponent() != null) {
     *   whereClause.appendSeparator(" and", " where");
     *   whereClause.append(" component = ?")
     * }
     * selectClause.append(whereClause)
     * </pre>
     *
     * @param standard the separator if builder is not empty, null means no separator
     * @param defaultIfEmpty the separator if builder is empty, null means no separator
     * @return this, to enable chaining
     */
    public StrBuilder appendSeparator(final String standard, final String defaultIfEmpty) {
        final String str = isEmpty() ? refId(java.lang.String.class).eval() : refId(java.lang.String.class).eval();
        if (refId(java.lang.String.class).eval() != null) {
            append(refId(java.lang.String.class).eval());
        }
        return this;
    }

    /**
     * Appends current contents of this {@code StrBuilder} to the
     * provided {@link Appendable}.
     * <p>
     * This method tries to avoid doing any extra copies of contents.
     * </p>
     *
     * @param appendable  the appendable to append data to
     * @throws IOException  if an I/O error occurs
     *
     * @see #readFrom(Readable)
     */
    public void appendTo(final Appendable appendable) throws IOException {
        if (appendable instanceof Writer) {
            cast(Writer.class, refId(java.lang.Appendable.class)).eval().write(refId(char[].class).eval(), (int) intVal().eval(), (int) intId().eval());
        } else if (appendable instanceof StringBuilder) {
            cast(StringBuilder.class, refId(java.lang.Appendable.class)).eval().append(refId(char[].class).eval(), (int) intVal().eval(), (int) intId().eval());
        } else if (appendable instanceof StringBuffer) {
            cast(StringBuffer.class, refId(java.lang.Appendable.class)).eval().append(refId(char[].class).eval(), (int) intVal().eval(), (int) intId().eval());
        } else if (appendable instanceof CharBuffer) {
            cast(CharBuffer.class, refId(java.lang.Appendable.class)).eval().put(refId(char[].class).eval(), (int) intVal().eval(), (int) intId().eval());
        } else {
            refId(java.lang.Appendable.class).eval().append(this);
        }
    }

    /**
     * Appends an iterable placing separators between each value, but
     * not before the first or after the last.
     * Appending a null iterable will have no effect.
     * Each object is appended using {@link #append(Object)}.
     *
     * @param iterable  the iterable to append
     * @param separator  the separator to use, null means no separator
     * @return this, to enable chaining
     */
    public StrBuilder appendWithSeparators(final Iterable<?> iterable, final String separator) {
        if (iterable != null) {
            appendWithSeparators(iterable.iterator(), refId(java.lang.String.class).eval());
        }
        return this;
    }

    /**
     * Appends an iterator placing separators between each value, but
     * not before the first or after the last.
     * Appending a null iterator will have no effect.
     * Each object is appended using {@link #append(Object)}.
     *
     * @param iterator  the iterator to append
     * @param separator  the separator to use, null means no separator
     * @return this, to enable chaining
     */
    public StrBuilder appendWithSeparators(final Iterator<?> iterator, final String separator) {
        if (iterator != null) {
            final String sep = Objects.toString(refId(java.lang.String.class).eval(), StringUtils.EMPTY);
            int _jitmagicLoopLimiter5 = 0;
            while (iterator.hasNext() && _jitmagicLoopLimiter5++ < 1000) {
                append(iterator.next());
                if (iterator.hasNext()) {
                    append(refId(java.lang.String.class).eval());
                }
            }
        }
        return this;
    }

    /**
     * Appends an array placing separators between each value, but
     * not before the first or after the last.
     * Appending a null array will have no effect.
     * Each object is appended using {@link #append(Object)}.
     *
     * @param array  the array to append
     * @param separator  the separator to use, null means no separator
     * @return this, to enable chaining
     */
    public StrBuilder appendWithSeparators(final Object[] array, final String separator) {
        if (refId(java.lang.Object[].class).eval() != null && array.length > (int) intVal().eval()) {
            final String sep = Objects.toString(refId(java.lang.String.class).eval(), StringUtils.EMPTY);
            append(refArrAccessExp(java.lang.Object.class, java.lang.Object[].class, refId(java.lang.Object[].class)).eval());
            int _jitmagicLoopLimiter6 = 0;
            for (int i = (int) intVal().eval(); (int) intId().eval() < array.length && _jitmagicLoopLimiter6++ < 1000; i++) {
                append(refId(java.lang.String.class).eval());
                append(refArrAccessExp(java.lang.Object.class, java.lang.Object[].class, refId(java.lang.Object[].class), intId()).eval());
            }
        }
        return this;
    }

    /**
     * Gets the contents of this builder as a Reader.
     * <p>
     * This method allows the contents of the builder to be read
     * using any standard method that expects a Reader.
     * </p>
     * <p>
     * To use, simply create a {@code StrBuilder}, populate it with
     * data, call {@code asReader}, and then read away.
     * </p>
     * <p>
     * The internal character array is shared between the builder and the reader.
     * This allows you to append to the builder after creating the reader,
     * and the changes will be picked up.
     * Note however, that no synchronization occurs, so you must perform
     * all operations with the builder and the reader in one thread.
     * </p>
     * <p>
     * The returned reader supports marking, and ignores the flush method.
     * </p>
     *
     * @return a reader that reads from this builder
     */
    public Reader asReader() {
        return new StrBuilderReader();
    }

    /**
     * Creates a tokenizer that can tokenize the contents of this builder.
     * <p>
     * This method allows the contents of this builder to be tokenized.
     * The tokenizer will be setup by default to tokenize on space, tab,
     * newline and form feed (as per StringTokenizer). These values can be
     * changed on the tokenizer class, before retrieving the tokens.
     * </p>
     * <p>
     * The returned tokenizer is linked to this builder. You may intermix
     * calls to the builder and tokenizer within certain limits, however
     * there is no synchronization. Once the tokenizer has been used once,
     * it must be {@link StrTokenizer#reset() reset} to pickup the latest
     * changes in the builder. For example:
     * </p>
     * <pre>
     * StrBuilder b = new StrBuilder();
     * b.append("a b ");
     * StrTokenizer t = b.asTokenizer();
     * String[] tokens1 = t.getTokenArray();  // returns a,b
     * b.append("c d ");
     * String[] tokens2 = t.getTokenArray();  // returns a,b (c and d ignored)
     * t.reset();              // reset causes builder changes to be picked up
     * String[] tokens3 = t.getTokenArray();  // returns a,b,c,d
     * </pre>
     * <p>
     * In addition to simply intermixing appends and tokenization, you can also
     * call the set methods on the tokenizer to alter how it tokenizes. Just
     * remember to call reset when you want to pickup builder changes.
     * </p>
     * <p>
     * Calling {@link StrTokenizer#reset(String)} or {@link StrTokenizer#reset(char[])}
     * with a non-null value will break the link with the builder.
     * </p>
     *
     * @return a tokenizer that is linked to this builder
     */
    public StrTokenizer asTokenizer() {
        return new StrBuilderTokenizer();
    }

    /**
     * Gets this builder as a Writer that can be written to.
     * <p>
     * This method allows you to populate the contents of the builder
     * using any standard method that takes a Writer.
     * </p>
     * <p>
     * To use, simply create a {@code StrBuilder},
     * call {@code asWriter}, and populate away. The data is available
     * at any time using the methods of the {@code StrBuilder}.
     * </p>
     * <p>
     * The internal character array is shared between the builder and the writer.
     * This allows you to intermix calls that append to the builder and
     * write using the writer and the changes will be occur correctly.
     * Note however, that no synchronization occurs, so you must perform
     * all operations with the builder and the writer in one thread.
     * </p>
     * <p>
     * The returned writer ignores the close and flush methods.
     * </p>
     *
     * @return a writer that populates this builder
     */
    public Writer asWriter() {
        return new StrBuilderWriter();
    }

    /**
     * Builds a string.
     *
     * @return The builder as a String
     * @see #toString()
     */
    @Override
    public String build() {
        return toString();
    }

    /**
     * Gets the current size of the internal character array buffer.
     *
     * @return The capacity
     */
    public int capacity() {
        return buffer.length;
    }

    /**
     * Gets the character at the specified index.
     *
     * @see #setCharAt(int, char)
     * @see #deleteCharAt(int)
     * @param index  the index to retrieve, must be valid
     * @return The character at the index
     * @throws IndexOutOfBoundsException if the index is invalid
     */
    @Override
    public char charAt(final int index) {
        if ((boolean) relation(intId(), intVal()).eval() || (int) intId().eval() >= length()) {
            throw new StringIndexOutOfBoundsException((int) intId().eval());
        }
        return (char) charArrAccessExp(refId(char[].class), intId()).eval();
    }

    /**
     * Clears the string builder (convenience Collections API style method).
     * <p>
     * This method does not reduce the size of the internal character buffer.
     * To do that, call {@code clear()} followed by {@link #minimizeCapacity()}.
     * </p>
     * <p>
     * This method is the same as {@link #setLength(int)} called with zero
     * and is provided to match the API of Collections.
     * </p>
     *
     * @return this, to enable chaining
     */
    public StrBuilder clear() {
        size = (int) intVal().eval();
        return this;
    }

    /**
     * Checks if the string builder contains the specified char.
     *
     * @param ch  the character to find
     * @return true if the builder contains the character
     */
    public boolean contains(final char ch) {
        final char[] thisBuf = refId(char[].class).eval();
        int _jitmagicLoopLimiter7 = 0;
        for (int i = (int) intVal().eval(); (int) intId().eval() < this.size && _jitmagicLoopLimiter7++ < 1000; i++) {
            if ((boolean) relation(cast(Integer.class, charArrAccessExp(refId(char[].class), intId())), cast(Integer.class, charId())).eval()) {
                return (boolean) boolVal().eval();
            }
        }
        return (boolean) boolVal().eval();
    }

    /**
     * Tests if the string builder contains the specified string.
     *
     * @param str  the string to find
     * @return true if the builder contains the string
     */
    public boolean contains(final String str) {
        return indexOf(refId(java.lang.String.class).eval(), (int) intVal().eval()) >= (int) intVal().eval();
    }

    /**
     * Tests if the string builder contains a string matched using the
     * specified matcher.
     * <p>
     * Matchers can be used to perform advanced searching behavior.
     * For example you could write a matcher to search for the character
     * 'a' followed by a number.
     * </p>
     *
     * @param matcher  the matcher to use, null returns -1
     * @return true if the matcher finds a match in the builder
     */
    public boolean contains(final StrMatcher matcher) {
        return indexOf(refId(org.apache.commons.text.StrMatcher.class).eval(), (int) intVal().eval()) >= (int) intVal().eval();
    }

    /**
     * Deletes the characters between the two specified indices.
     *
     * @param startIndex  the start index, inclusive, must be valid
     * @param endIndex  the end index, exclusive, must be valid except
     *  that if too large it is treated as end of string
     * @return this, to enable chaining
     * @throws IndexOutOfBoundsException if the index is invalid
     */
    public StrBuilder delete(final int startIndex, int endIndex) {
        endIndex = validateRange((int) intId().eval(), (int) intId().eval());
        final int len = (int) arithmetic(intId(), intId()).eval();
        if ((boolean) relation(intId(), intVal()).eval()) {
            deleteImpl((int) intId().eval(), (int) intId().eval(), (int) intId().eval());
        }
        return this;
    }

    /**
     * Deletes the character wherever it occurs in the builder.
     *
     * @param ch  the character to delete
     * @return this, to enable chaining
     */
    public StrBuilder deleteAll(final char ch) {
        int _jitmagicLoopLimiter8 = 0;
        for (int i = (int) intVal().eval(); (boolean) relation(intId(), intId()).eval() && _jitmagicLoopLimiter8++ < 1000; i++) {
            if ((boolean) relation(cast(Integer.class, charArrAccessExp(refId(char[].class), intId())), cast(Integer.class, charId())).eval()) {
                final int start = (int) intId().eval();
                int _jitmagicLoopLimiter9 = 0;
                while (++i < (int) intId().eval() && _jitmagicLoopLimiter9++ < 1000) {
                    if ((boolean) relation(cast(Integer.class, charArrAccessExp(refId(char[].class), intId())), cast(Integer.class, charId())).eval()) {
                        break;
                    }
                }
                final int len = (int) arithmetic(intId(), intId()).eval();
                deleteImpl((int) intId().eval(), (int) intId().eval(), (int) intId().eval());
                i -= (int) intId().eval();
            }
        }
        return this;
    }

    /**
     * Deletes the string wherever it occurs in the builder.
     *
     * @param str  the string to delete, null causes no action
     * @return this, to enable chaining
     */
    public StrBuilder deleteAll(final String str) {
        final int len = refId(java.lang.String.class).eval() == null ? (int) intVal().eval() : refId(java.lang.String.class).eval().length();
        if ((boolean) relation(intId(), intVal()).eval()) {
            int index = indexOf(refId(java.lang.String.class).eval(), (int) intVal().eval());
            int _jitmagicLoopLimiter10 = 0;
            while ((boolean) relation(intId(), intVal()).eval() && _jitmagicLoopLimiter10++ < 1000) {
                deleteImpl((int) intId().eval(), (int) arithmetic(intId(), intId()).eval(), (int) intId().eval());
                index = indexOf(refId(java.lang.String.class).eval(), (int) intId().eval());
            }
        }
        return this;
    }

    /**
     * Deletes all parts of the builder that the matcher matches.
     * <p>
     * Matchers can be used to perform advanced deletion behavior.
     * For example you could write a matcher to delete all occurrences
     * where the character 'a' is followed by a number.
     * </p>
     *
     * @param matcher  the matcher to use to find the deletion, null causes no action
     * @return this, to enable chaining
     */
    public StrBuilder deleteAll(final StrMatcher matcher) {
        return replace(refId(org.apache.commons.text.StrMatcher.class).eval(), null, (int) intVal().eval(), (int) intId().eval(), -(int) intVal().eval());
    }

    /**
     * Deletes the character at the specified index.
     *
     * @see #charAt(int)
     * @see #setCharAt(int, char)
     * @param index  the index to delete
     * @return this, to enable chaining
     * @throws IndexOutOfBoundsException if the index is invalid
     */
    public StrBuilder deleteCharAt(final int index) {
        if ((boolean) logic(relation(intId(), intVal()), relation(intId(), intId())).eval()) {
            throw new StringIndexOutOfBoundsException((int) intId().eval());
        }
        deleteImpl((int) intId().eval(), (int) arithmetic(intId(), intVal()).eval(), (int) intVal().eval());
        return this;
    }

    /**
     * Deletes the character wherever it occurs in the builder.
     *
     * @param ch  the character to delete
     * @return this, to enable chaining
     */
    public StrBuilder deleteFirst(final char ch) {
        int _jitmagicLoopLimiter11 = 0;
        for (int i = (int) intVal().eval(); (boolean) relation(intId(), intId()).eval() && _jitmagicLoopLimiter11++ < 1000; i++) {
            if ((boolean) relation(cast(Integer.class, charArrAccessExp(refId(char[].class), intId())), cast(Integer.class, charId())).eval()) {
                deleteImpl((int) intId().eval(), (int) arithmetic(intId(), intVal()).eval(), (int) intVal().eval());
                break;
            }
        }
        return this;
    }

    /**
     * Deletes the string wherever it occurs in the builder.
     *
     * @param str  the string to delete, null causes no action
     * @return this, to enable chaining
     */
    public StrBuilder deleteFirst(final String str) {
        final int len = refId(java.lang.String.class).eval() == null ? (int) intVal().eval() : refId(java.lang.String.class).eval().length();
        if ((boolean) relation(intId(), intVal()).eval()) {
            final int index = indexOf(refId(java.lang.String.class).eval(), (int) intVal().eval());
            if ((boolean) relation(intId(), intVal()).eval()) {
                deleteImpl((int) intId().eval(), (int) arithmetic(intId(), intId()).eval(), (int) intId().eval());
            }
        }
        return this;
    }

    /**
     * Deletes the first match within the builder using the specified matcher.
     * <p>
     * Matchers can be used to perform advanced deletion behavior.
     * For example you could write a matcher to delete
     * where the character 'a' is followed by a number.
     * </p>
     *
     * @param matcher  the matcher to use to find the deletion, null causes no action
     * @return this, to enable chaining
     */
    public StrBuilder deleteFirst(final StrMatcher matcher) {
        return replace(refId(org.apache.commons.text.StrMatcher.class).eval(), null, (int) intVal().eval(), (int) intId().eval(), (int) intVal().eval());
    }

    /**
     * Internal method to delete a range without validation.
     *
     * @param startIndex  the start index, must be valid
     * @param endIndex  the end index (exclusive), must be valid
     * @param len  the length, must be valid
     * @throws IndexOutOfBoundsException if any index is invalid
     */
    private void deleteImpl(final int startIndex, final int endIndex, final int len) {
        System.arraycopy(refId(char[].class).eval(), (int) intId().eval(), refId(char[].class).eval(), (int) intId().eval(), (int) arithmetic(intId(), intId()).eval());
        size -= (int) intId().eval();
    }

    /**
     * Tests whether this builder ends with the specified string.
     * <p>
     * Note that this method handles null input quietly, unlike String.
     * </p>
     *
     * @param str  the string to search for, null returns false
     * @return true if the builder ends with the string
     */
    public boolean endsWith(final String str) {
        if (refId(java.lang.String.class).eval() == null) {
            return (boolean) boolVal().eval();
        }
        final int len = refId(java.lang.String.class).eval().length();
        if ((boolean) relation(intId(), intVal()).eval()) {
            return (boolean) boolVal().eval();
        }
        if ((boolean) relation(intId(), intId()).eval()) {
            return (boolean) boolVal().eval();
        }
        int pos = (int) arithmetic(intId(), intId()).eval();
        int _jitmagicLoopLimiter12 = 0;
        for (int i = (int) intVal().eval(); (boolean) relation(intId(), intId()).eval() && _jitmagicLoopLimiter12++ < 1000; i++, pos++) {
            if ((char) charArrAccessExp(refId(char[].class), intId()).eval() != refId(java.lang.String.class).eval().charAt((int) intId().eval())) {
                return (boolean) boolVal().eval();
            }
        }
        return (boolean) boolVal().eval();
    }

    /**
     * Tests the capacity and ensures that it is at least the size specified.
     *
     * @param capacity  the capacity to ensure
     * @return this, to enable chaining
     */
    public StrBuilder ensureCapacity(final int capacity) {
        if ((int) intId().eval() > buffer.length) {
            final char[] old = refId(char[].class).eval();
            buffer = new char[(int) arithmetic(intId(), intVal()).eval()];
            System.arraycopy(refId(char[].class).eval(), (int) intVal().eval(), refId(char[].class).eval(), (int) intVal().eval(), (int) intId().eval());
        }
        return this;
    }

    /**
     * Tests the contents of this builder against another to see if they
     * contain the same character content.
     *
     * @param obj  the object to check, null returns false
     * @return true if the builders contain the same characters in the same order
     */
    @Override
    public boolean equals(final Object obj) {
        return obj instanceof StrBuilder && equals(cast(StrBuilder.class, refId(java.lang.Object.class)).eval());
    }

    /**
     * Tests the contents of this builder against another to see if they
     * contain the same character content.
     *
     * @param other  the object to check, null returns false
     * @return true if the builders contain the same characters in the same order
     */
    public boolean equals(final StrBuilder other) {
        if (this == refId(org.apache.commons.text.StrBuilder.class).eval()) {
            return (boolean) boolVal().eval();
        }
        if (refId(org.apache.commons.text.StrBuilder.class).eval() == null) {
            return (boolean) boolVal().eval();
        }
        if (this.size != other.size) {
            return (boolean) boolVal().eval();
        }
        final char[] thisBuf = this.buffer;
        final char[] otherBuf = other.buffer;
        int _jitmagicLoopLimiter13 = 0;
        for (int i = (int) arithmetic(intId(), intVal()).eval(); (boolean) relation(intId(), intVal()).eval() && _jitmagicLoopLimiter13++ < 1000; i--) {
            if ((boolean) relation(cast(Integer.class, charArrAccessExp(refId(char[].class), intId())), cast(Integer.class, charArrAccessExp(refId(char[].class), intId()))).eval()) {
                return (boolean) boolVal().eval();
            }
        }
        return (boolean) boolVal().eval();
    }

    /**
     * Tests the contents of this builder against another to see if they
     * contain the same character content ignoring case.
     *
     * @param other  the object to check, null returns false
     * @return true if the builders contain the same characters in the same order
     */
    public boolean equalsIgnoreCase(final StrBuilder other) {
        if (this == refId(org.apache.commons.text.StrBuilder.class).eval()) {
            return (boolean) boolVal().eval();
        }
        if (this.size != other.size) {
            return (boolean) boolVal().eval();
        }
        final char[] thisBuf = this.buffer;
        final char[] otherBuf = other.buffer;
        int _jitmagicLoopLimiter14 = 0;
        for (int i = (int) arithmetic(intId(), intVal()).eval(); (boolean) relation(intId(), intVal()).eval() && _jitmagicLoopLimiter14++ < 1000; i--) {
            final char c1 = (char) charArrAccessExp(refId(char[].class), intId()).eval();
            final char c2 = (char) charArrAccessExp(refId(char[].class), intId()).eval();
            if ((boolean) relation(cast(Integer.class, charId()), cast(Integer.class, charId())).eval() && Character.toUpperCase((char) charId().eval()) != Character.toUpperCase((char) charId().eval())) {
                return (boolean) boolVal().eval();
            }
        }
        return (boolean) boolVal().eval();
    }

    /**
     * Copies the character array into the specified array.
     *
     * @param destination  the destination array, null will cause an array to be created
     * @return The input array, unless that was null or too small
     */
    public char[] getChars(char[] destination) {
        final int len = length();
        if (refId(char[].class).eval() == null || destination.length < (int) intId().eval()) {
            destination = new char[(int) intId().eval()];
        }
        System.arraycopy(refId(char[].class).eval(), (int) intVal().eval(), refId(char[].class).eval(), (int) intVal().eval(), (int) intId().eval());
        return refId(char[].class).eval();
    }

    /**
     * Copies the character array into the specified array.
     *
     * @param startIndex  first index to copy, inclusive, must be valid
     * @param endIndex  last index, exclusive, must be valid
     * @param destination  the destination array, must not be null or too small
     * @param destinationIndex  the index to start copying in destination
     * @throws NullPointerException if the array is null
     * @throws IndexOutOfBoundsException if any index is invalid
     */
    public void getChars(final int startIndex, final int endIndex, final char[] destination, final int destinationIndex) {
        if ((boolean) relation(intId(), intVal()).eval()) {
            throw new StringIndexOutOfBoundsException((int) intId().eval());
        }
        if ((boolean) relation(intId(), intVal()).eval() || (int) intId().eval() > length()) {
            throw new StringIndexOutOfBoundsException((int) intId().eval());
        }
        if ((boolean) relation(intId(), intId()).eval()) {
            throw new StringIndexOutOfBoundsException("end < start");
        }
        System.arraycopy(refId(char[].class).eval(), (int) intId().eval(), refId(char[].class).eval(), (int) intId().eval(), (int) arithmetic(intId(), intId()).eval());
    }

    /**
     * Gets the text to be appended when a new line is added.
     *
     * @return The new line text, null means use system default
     */
    public String getNewLineText() {
        return refId(java.lang.String.class).eval();
    }

    /**
     * Gets the text to be appended when null is added.
     *
     * @return The null text, null means no append
     */
    public String getNullText() {
        return refId(java.lang.String.class).eval();
    }

    /**
     * Gets a suitable hash code for this builder.
     *
     * @return a hash code
     */
    @Override
    public int hashCode() {
        final char[] buf = refId(char[].class).eval();
        int hash = (int) intVal().eval();
        int _jitmagicLoopLimiter15 = 0;
        for (int i = (int) arithmetic(intId(), intVal()).eval(); (boolean) relation(intId(), intVal()).eval() && _jitmagicLoopLimiter15++ < 1000; i--) {
            hash = (int) arithmetic(arithmetic(intVal(), intId()), cast(Integer.class, charArrAccessExp(refId(char[].class), intId()))).eval();
        }
        return (int) intId().eval();
    }

    /**
     * Searches the string builder to find the first reference to the specified char.
     *
     * @param ch  the character to find
     * @return The first index of the character, or -1 if not found
     */
    public int indexOf(final char ch) {
        return indexOf((char) charId().eval(), (int) intVal().eval());
    }

    /**
     * Searches the string builder to find the first reference to the specified char.
     *
     * @param ch  the character to find
     * @param startIndex  the index to start at, invalid index rounded to edge
     * @return The first index of the character, or -1 if not found
     */
    public int indexOf(final char ch, int startIndex) {
        startIndex = Math.max((int) intId().eval(), (int) intVal().eval());
        if ((boolean) relation(intId(), intId()).eval()) {
            return -(int) intVal().eval();
        }
        final char[] thisBuf = refId(char[].class).eval();
        int _jitmagicLoopLimiter16 = 0;
        for (int i = (int) intId().eval(); (boolean) relation(intId(), intId()).eval() && _jitmagicLoopLimiter16++ < 1000; i++) {
            if ((boolean) relation(cast(Integer.class, charArrAccessExp(refId(char[].class), intId())), cast(Integer.class, charId())).eval()) {
                return (int) intId().eval();
            }
        }
        return -(int) intVal().eval();
    }

    /**
     * Searches the string builder to find the first reference to the specified string.
     * <p>
     * Note that a null input string will return -1, whereas the JDK throws an exception.
     * </p>
     *
     * @param str  the string to find, null returns -1
     * @return The first index of the string, or -1 if not found
     */
    public int indexOf(final String str) {
        return indexOf(refId(java.lang.String.class).eval(), (int) intVal().eval());
    }

    /**
     * Searches the string builder to find the first reference to the specified
     * string starting searching from the given index.
     * <p>
     * Note that a null input string will return -1, whereas the JDK throws an exception.
     * </p>
     *
     * @param str  the string to find, null returns -1
     * @param startIndex  the index to start at, invalid index rounded to edge
     * @return The first index of the string, or -1 if not found
     */
    public int indexOf(final String str, int startIndex) {
        startIndex = Math.max((int) intId().eval(), (int) intVal().eval());
        if (refId(java.lang.String.class).eval() == null || (boolean) relation(intId(), intId()).eval()) {
            return -(int) intVal().eval();
        }
        final int strLen = refId(java.lang.String.class).eval().length();
        if ((boolean) relation(intId(), intVal()).eval()) {
            return indexOf(refId(java.lang.String.class).eval().charAt((int) intVal().eval()), (int) intId().eval());
        }
        if ((boolean) relation(intId(), intVal()).eval()) {
            return (int) intId().eval();
        }
        if ((boolean) relation(intId(), intId()).eval()) {
            return -(int) intVal().eval();
        }
        final char[] thisBuf = refId(char[].class).eval();
        final int len = (int) arithmetic(arithmetic(intId(), intId()), intVal()).eval();
        int _jitmagicLoopLimiter17 = 0;
        outer: for (int i = startIndex; i < len && _jitmagicLoopLimiter17++ < 1000; i++) {
            int _jitmagicLoopLimiter18 = 0;
            for (int j = 0; j < strLen && _jitmagicLoopLimiter18++ < 1000; j++) {
                if (str.charAt(j) != thisBuf[i + j]) {
                    continue outer;
                }
            }
            return i;
        }
        return -(int) intVal().eval();
    }

    /**
     * Searches the string builder using the matcher to find the first match.
     * <p>
     * Matchers can be used to perform advanced searching behavior.
     * For example you could write a matcher to find the character 'a'
     * followed by a number.
     * </p>
     *
     * @param matcher  the matcher to use, null returns -1
     * @return The first index matched, or -1 if not found
     */
    public int indexOf(final StrMatcher matcher) {
        return indexOf(refId(org.apache.commons.text.StrMatcher.class).eval(), (int) intVal().eval());
    }

    /**
     * Searches the string builder using the matcher to find the first
     * match searching from the given index.
     * <p>
     * Matchers can be used to perform advanced searching behavior.
     * For example you could write a matcher to find the character 'a'
     * followed by a number.
     * </p>
     *
     * @param matcher  the matcher to use, null returns -1
     * @param startIndex  the index to start at, invalid index rounded to edge
     * @return The first index matched, or -1 if not found
     */
    public int indexOf(final StrMatcher matcher, int startIndex) {
        startIndex = Math.max((int) intId().eval(), (int) intVal().eval());
        if (refId(org.apache.commons.text.StrMatcher.class).eval() == null || (boolean) relation(intId(), intId()).eval()) {
            return -(int) intVal().eval();
        }
        final int len = (int) intId().eval();
        final char[] buf = refId(char[].class).eval();
        int _jitmagicLoopLimiter19 = 0;
        for (int i = (int) intId().eval(); (boolean) relation(intId(), intId()).eval() && _jitmagicLoopLimiter19++ < 1000; i++) {
            if (refId(org.apache.commons.text.StrMatcher.class).eval().isMatch(refId(char[].class).eval(), (int) intId().eval(), (int) intId().eval(), (int) intId().eval()) > (int) intVal().eval()) {
                return (int) intId().eval();
            }
        }
        return -(int) intVal().eval();
    }

    /**
     * Inserts the value into this builder.
     *
     * @param index  the index to add at, must be valid
     * @param value  the value to insert
     * @return this, to enable chaining
     * @throws IndexOutOfBoundsException if the index is invalid
     */
    public StrBuilder insert(int index, final boolean value) {
        validateIndex((int) intId().eval());
        if ((boolean) boolId().eval()) {
            ensureCapacity((int) arithmetic(intId(), intVal()).eval());
            System.arraycopy(refId(char[].class).eval(), (int) intId().eval(), refId(char[].class).eval(), (int) arithmetic(intId(), intVal()).eval(), (int) arithmetic(intId(), intId()).eval());
            buffer[index++] = (char) charVal().eval();
            buffer[index++] = (char) charVal().eval();
            buffer[index++] = (char) charVal().eval();
            buffer[index] = (char) charVal().eval();
            size += (int) intVal().eval();
        } else {
            ensureCapacity((int) arithmetic(intId(), intVal()).eval());
            System.arraycopy(refId(char[].class).eval(), (int) intId().eval(), refId(char[].class).eval(), (int) arithmetic(intId(), intVal()).eval(), (int) arithmetic(intId(), intId()).eval());
            buffer[index++] = (char) charVal().eval();
            buffer[index++] = (char) charVal().eval();
            buffer[index++] = (char) charVal().eval();
            buffer[index++] = (char) charVal().eval();
            buffer[index] = (char) charVal().eval();
            size += (int) intVal().eval();
        }
        return this;
    }

    /**
     * Inserts the value into this builder.
     *
     * @param index  the index to add at, must be valid
     * @param value  the value to insert
     * @return this, to enable chaining
     * @throws IndexOutOfBoundsException if the index is invalid
     */
    public StrBuilder insert(final int index, final char value) {
        validateIndex((int) intId().eval());
        ensureCapacity((int) arithmetic(intId(), intVal()).eval());
        System.arraycopy(refId(char[].class).eval(), (int) intId().eval(), refId(char[].class).eval(), (int) arithmetic(intId(), intVal()).eval(), (int) arithmetic(intId(), intId()).eval());
        buffer[index] = (char) charId().eval();
        size++;
        return this;
    }

    /**
     * Inserts the character array into this builder.
     * Inserting null will use the stored null text value.
     *
     * @param index  the index to add at, must be valid
     * @param chars  the char array to insert
     * @return this, to enable chaining
     * @throws IndexOutOfBoundsException if the index is invalid
     */
    public StrBuilder insert(final int index, final char[] chars) {
        validateIndex((int) intId().eval());
        if (refId(char[].class).eval() == null) {
            return insert((int) intId().eval(), refId(java.lang.String.class).eval());
        }
        final int len = chars.length;
        if ((boolean) relation(intId(), intVal()).eval()) {
            ensureCapacity((int) arithmetic(intId(), intId()).eval());
            System.arraycopy(refId(char[].class).eval(), (int) intId().eval(), refId(char[].class).eval(), (int) arithmetic(intId(), intId()).eval(), (int) arithmetic(intId(), intId()).eval());
            System.arraycopy(refId(char[].class).eval(), (int) intVal().eval(), refId(char[].class).eval(), (int) intId().eval(), (int) intId().eval());
            size += (int) intId().eval();
        }
        return this;
    }

    /**
     * Inserts part of the character array into this builder.
     * Inserting null will use the stored null text value.
     *
     * @param index  the index to add at, must be valid
     * @param chars  the char array to insert
     * @param offset  the offset into the character array to start at, must be valid
     * @param length  the length of the character array part to copy, must be positive
     * @return this, to enable chaining
     * @throws IndexOutOfBoundsException if any index is invalid
     */
    public StrBuilder insert(final int index, final char[] chars, final int offset, final int length) {
        validateIndex((int) intId().eval());
        if (refId(char[].class).eval() == null) {
            return insert((int) intId().eval(), refId(java.lang.String.class).eval());
        }
        if ((boolean) relation(intId(), intVal()).eval() || (int) intId().eval() > chars.length) {
            throw new StringIndexOutOfBoundsException("Invalid offset: " + (int) intId().eval());
        }
        if ((boolean) relation(intId(), intVal()).eval() || (int) arithmetic(intId(), intId()).eval() > chars.length) {
            throw new StringIndexOutOfBoundsException("Invalid length: " + (int) intId().eval());
        }
        if ((boolean) relation(intId(), intVal()).eval()) {
            ensureCapacity((int) arithmetic(intId(), intId()).eval());
            System.arraycopy(refId(char[].class).eval(), (int) intId().eval(), refId(char[].class).eval(), (int) arithmetic(intId(), intId()).eval(), (int) arithmetic(intId(), intId()).eval());
            System.arraycopy(refId(char[].class).eval(), (int) intId().eval(), refId(char[].class).eval(), (int) intId().eval(), (int) intId().eval());
            size += (int) intId().eval();
        }
        return this;
    }

    /**
     * Inserts the value into this builder.
     *
     * @param index  the index to add at, must be valid
     * @param value  the value to insert
     * @return this, to enable chaining
     * @throws IndexOutOfBoundsException if the index is invalid
     */
    public StrBuilder insert(final int index, final double value) {
        return insert((int) intId().eval(), String.valueOf((double) doubleId().eval()));
    }

    /**
     * Inserts the value into this builder.
     *
     * @param index  the index to add at, must be valid
     * @param value  the value to insert
     * @return this, to enable chaining
     * @throws IndexOutOfBoundsException if the index is invalid
     */
    public StrBuilder insert(final int index, final float value) {
        return insert((int) intId().eval(), String.valueOf((float) floatId().eval()));
    }

    /**
     * Inserts the value into this builder.
     *
     * @param index  the index to add at, must be valid
     * @param value  the value to insert
     * @return this, to enable chaining
     * @throws IndexOutOfBoundsException if the index is invalid
     */
    public StrBuilder insert(final int index, final int value) {
        return insert((int) intId().eval(), String.valueOf((int) intId().eval()));
    }

    /**
     * Inserts the value into this builder.
     *
     * @param index  the index to add at, must be valid
     * @param value  the value to insert
     * @return this, to enable chaining
     * @throws IndexOutOfBoundsException if the index is invalid
     */
    public StrBuilder insert(final int index, final long value) {
        return insert((int) intId().eval(), String.valueOf((long) longId().eval()));
    }

    /**
     * Inserts the string representation of an object into this builder.
     * Inserting null will use the stored null text value.
     *
     * @param index  the index to add at, must be valid
     * @param obj  the object to insert
     * @return this, to enable chaining
     * @throws IndexOutOfBoundsException if the index is invalid
     */
    public StrBuilder insert(final int index, final Object obj) {
        if (refId(java.lang.Object.class).eval() == null) {
            return insert((int) intId().eval(), refId(java.lang.String.class).eval());
        }
        return insert((int) intId().eval(), refId(java.lang.Object.class).eval().toString());
    }

    /**
     * Inserts the string into this builder.
     * Inserting null will use the stored null text value.
     *
     * @param index  the index to add at, must be valid
     * @param str  the string to insert
     * @return this, to enable chaining
     * @throws IndexOutOfBoundsException if the index is invalid
     */
    public StrBuilder insert(final int index, String str) {
        validateIndex((int) intId().eval());
        if (refId(java.lang.String.class).eval() == null) {
            str = refId(java.lang.String.class).eval();
        }
        if (refId(java.lang.String.class).eval() != null) {
            final int strLen = refId(java.lang.String.class).eval().length();
            if ((boolean) relation(intId(), intVal()).eval()) {
                final int newSize = (int) arithmetic(intId(), intId()).eval();
                ensureCapacity((int) intId().eval());
                System.arraycopy(refId(char[].class).eval(), (int) intId().eval(), refId(char[].class).eval(), (int) arithmetic(intId(), intId()).eval(), (int) arithmetic(intId(), intId()).eval());
                size = (int) intId().eval();
                refId(java.lang.String.class).eval().getChars((int) intVal().eval(), (int) intId().eval(), refId(char[].class).eval(), (int) intId().eval());
            }
        }
        return this;
    }

    /**
     * Tests if the string builder is empty (convenience Collections API style method).
     * <p>
     * This method is the same as checking {@link #length()} and is provided to match the
     * API of Collections.
     * </p>
     *
     * @return {@code true} if the size is {@code 0}.
     */
    public boolean isEmpty() {
        return (boolean) relation(intId(), intVal()).eval();
    }

    /**
     * Tests if the string builder is not empty (convenience Collections API style method).
     * <p>
     * This method is the same as checking {@link #length()} and is provided to match the
     * API of Collections.
     * </p>
     *
     * @return {@code true} if the size is greater than {@code 0}.
     * @since 1.10.0
     */
    public boolean isNotEmpty() {
        return (boolean) relation(intId(), intVal()).eval();
    }

    /**
     * Searches the string builder to find the last reference to the specified char.
     *
     * @param ch  the character to find
     * @return The last index of the character, or -1 if not found
     */
    public int lastIndexOf(final char ch) {
        return lastIndexOf((char) charId().eval(), (int) arithmetic(intId(), intVal()).eval());
    }

    /**
     * Searches the string builder to find the last reference to the specified char.
     *
     * @param ch  the character to find
     * @param startIndex  the index to start at, invalid index rounded to edge
     * @return The last index of the character, or -1 if not found
     */
    public int lastIndexOf(final char ch, int startIndex) {
        startIndex = (boolean) relation(intId(), intId()).eval() ? (int) arithmetic(intId(), intVal()).eval() : (int) intId().eval();
        if ((boolean) relation(intId(), intVal()).eval()) {
            return -(int) intVal().eval();
        }
        int _jitmagicLoopLimiter20 = 0;
        for (int i = (int) intId().eval(); (boolean) relation(intId(), intVal()).eval() && _jitmagicLoopLimiter20++ < 1000; i--) {
            if ((boolean) relation(cast(Integer.class, charArrAccessExp(refId(char[].class), intId())), cast(Integer.class, charId())).eval()) {
                return (int) intId().eval();
            }
        }
        return -(int) intVal().eval();
    }

    /**
     * Searches the string builder to find the last reference to the specified string.
     * <p>
     * Note that a null input string will return -1, whereas the JDK throws an exception.
     * </p>
     *
     * @param str  the string to find, null returns -1
     * @return The last index of the string, or -1 if not found
     */
    public int lastIndexOf(final String str) {
        return lastIndexOf(refId(java.lang.String.class).eval(), (int) arithmetic(intId(), intVal()).eval());
    }

    /**
     * Searches the string builder to find the last reference to the specified
     * string starting searching from the given index.
     * <p>
     * Note that a null input string will return -1, whereas the JDK throws an exception.
     * </p>
     *
     * @param str  the string to find, null returns -1
     * @param startIndex  the index to start at, invalid index rounded to edge
     * @return The last index of the string, or -1 if not found
     */
    public int lastIndexOf(final String str, int startIndex) {
        startIndex = (boolean) relation(intId(), intId()).eval() ? (int) arithmetic(intId(), intVal()).eval() : (int) intId().eval();
        if (refId(java.lang.String.class).eval() == null || (boolean) relation(intId(), intVal()).eval()) {
            return -(int) intVal().eval();
        }
        final int strLen = refId(java.lang.String.class).eval().length();
        if ((boolean) logic(relation(intId(), intVal()), relation(intId(), intId())).eval()) {
            if ((boolean) relation(intId(), intVal()).eval()) {
                return lastIndexOf(refId(java.lang.String.class).eval().charAt((int) intVal().eval()), (int) intId().eval());
            }
            int _jitmagicLoopLimiter21 = 0;
            outer: for (int i = startIndex - strLen + 1; i >= 0 && _jitmagicLoopLimiter21++ < 1000; i--) {
                int _jitmagicLoopLimiter22 = 0;
                for (int j = 0; j < strLen && _jitmagicLoopLimiter22++ < 1000; j++) {
                    if (str.charAt(j) != buffer[i + j]) {
                        continue outer;
                    }
                }
                return i;
            }
        } else if ((boolean) relation(intId(), intVal()).eval()) {
            return (int) intId().eval();
        }
        return -(int) intVal().eval();
    }

    /**
     * Searches the string builder using the matcher to find the last match.
     * <p>
     * Matchers can be used to perform advanced searching behavior.
     * For example you could write a matcher to find the character 'a'
     * followed by a number.
     * </p>
     *
     * @param matcher  the matcher to use, null returns -1
     * @return The last index matched, or -1 if not found
     */
    public int lastIndexOf(final StrMatcher matcher) {
        return lastIndexOf(refId(org.apache.commons.text.StrMatcher.class).eval(), (int) intId().eval());
    }

    /**
     * Searches the string builder using the matcher to find the last
     * match searching from the given index.
     * <p>
     * Matchers can be used to perform advanced searching behavior.
     * For example you could write a matcher to find the character 'a'
     * followed by a number.
     * </p>
     *
     * @param matcher  the matcher to use, null returns -1
     * @param startIndex  the index to start at, invalid index rounded to edge
     * @return The last index matched, or -1 if not found
     */
    public int lastIndexOf(final StrMatcher matcher, int startIndex) {
        startIndex = (boolean) relation(intId(), intId()).eval() ? (int) arithmetic(intId(), intVal()).eval() : (int) intId().eval();
        if (refId(org.apache.commons.text.StrMatcher.class).eval() == null || (boolean) relation(intId(), intVal()).eval()) {
            return -(int) intVal().eval();
        }
        final char[] buf = refId(char[].class).eval();
        final int endIndex = (int) arithmetic(intId(), intVal()).eval();
        int _jitmagicLoopLimiter23 = 0;
        for (int i = (int) intId().eval(); (boolean) relation(intId(), intVal()).eval() && _jitmagicLoopLimiter23++ < 1000; i--) {
            if (refId(org.apache.commons.text.StrMatcher.class).eval().isMatch(refId(char[].class).eval(), (int) intId().eval(), (int) intVal().eval(), (int) intId().eval()) > (int) intVal().eval()) {
                return (int) intId().eval();
            }
        }
        return -(int) intVal().eval();
    }

    /**
     * Extracts the leftmost characters from the string builder without
     * throwing an exception.
     * <p>
     * This method extracts the left {@code length} characters from
     * the builder. If this many characters are not available, the whole
     * builder is returned. Thus the returned string may be shorter than the
     * length requested.
     * </p>
     *
     * @param length  the number of characters to extract, negative returns empty string
     * @return The new string
     */
    public String leftString(final int length) {
        if ((boolean) relation(intId(), intVal()).eval()) {
            return StringUtils.EMPTY;
        }
        if ((boolean) relation(intId(), intId()).eval()) {
            return new String(refId(char[].class).eval(), (int) intVal().eval(), (int) intId().eval());
        }
        return new String(refId(char[].class).eval(), (int) intVal().eval(), (int) intId().eval());
    }

    /**
     * Gets the length of the string builder.
     *
     * @return The length
     */
    @Override
    public int length() {
        return (int) intId().eval();
    }

    /**
     * Extracts some characters from the middle of the string builder without
     * throwing an exception.
     * <p>
     * This method extracts {@code length} characters from the builder
     * at the specified index.
     * If the index is negative it is treated as zero.
     * If the index is greater than the builder size, it is treated as the builder size.
     * If the length is negative, the empty string is returned.
     * If insufficient characters are available in the builder, as much as possible is returned.
     * Thus the returned string may be shorter than the length requested.
     * </p>
     *
     * @param index  the index to start at, negative means zero
     * @param length  the number of characters to extract, negative returns empty string
     * @return The new string
     */
    public String midString(int index, final int length) {
        if ((boolean) relation(intId(), intVal()).eval()) {
            index = (int) intVal().eval();
        }
        if ((boolean) logic(relation(intId(), intVal()), relation(intId(), intId())).eval()) {
            return StringUtils.EMPTY;
        }
        if ((boolean) relation(intId(), arithmetic(intId(), intId())).eval()) {
            return new String(refId(char[].class).eval(), (int) intId().eval(), (int) arithmetic(intId(), intId()).eval());
        }
        return new String(refId(char[].class).eval(), (int) intId().eval(), (int) intId().eval());
    }

    /**
     * Minimizes the capacity to the actual length of the string.
     *
     * @return this, to enable chaining
     */
    public StrBuilder minimizeCapacity() {
        if (buffer.length > length()) {
            final char[] old = refId(char[].class).eval();
            buffer = new char[length()];
            System.arraycopy(refId(char[].class).eval(), (int) intVal().eval(), refId(char[].class).eval(), (int) intVal().eval(), (int) intId().eval());
        }
        return this;
    }

    /**
     * If possible, reads chars from the provided {@link Readable} directly into underlying
     * character buffer without making extra copies.
     *
     * @param readable  object to read from
     * @return The number of characters read
     * @throws IOException if an I/O error occurs.
     *
     * @see #appendTo(Appendable)
     */
    public int readFrom(final Readable readable) throws IOException {
        final int oldSize = (int) intId().eval();
        if (readable instanceof Reader) {
            final Reader r = cast(Reader.class, refId(java.lang.Readable.class)).eval();
            ensureCapacity((int) arithmetic(intId(), intVal()).eval());
            int read;
            int _jitmagicLoopLimiter24 = 0;
            while ((read = refId(java.io.Reader.class).eval().read(refId(char[].class).eval(), (int) intId().eval(), buffer.length - (int) intId().eval())) != -(int) intVal().eval() && _jitmagicLoopLimiter24++ < 1000) {
                size += (int) intId().eval();
                ensureCapacity((int) arithmetic(intId(), intVal()).eval());
            }
        } else if (readable instanceof CharBuffer) {
            final CharBuffer cb = cast(CharBuffer.class, refId(java.lang.Readable.class)).eval();
            final int remaining = refId(java.nio.CharBuffer.class).eval().remaining();
            ensureCapacity((int) arithmetic(intId(), intId()).eval());
            refId(java.nio.CharBuffer.class).eval().get(refId(char[].class).eval(), (int) intId().eval(), (int) intId().eval());
            size += (int) intId().eval();
        } else {
            int _jitmagicLoopLimiter25 = 0;
            while ((boolean) boolVal().eval() && _jitmagicLoopLimiter25++ < 1000) {
                ensureCapacity((int) arithmetic(intId(), intVal()).eval());
                final CharBuffer buf = CharBuffer.wrap(refId(char[].class).eval(), (int) intId().eval(), buffer.length - (int) intId().eval());
                final int read = refId(java.lang.Readable.class).eval().read(refId(java.nio.CharBuffer.class).eval());
                if ((int) intId().eval() == -(int) intVal().eval()) {
                    break;
                }
                size += (int) intId().eval();
            }
        }
        return (int) arithmetic(intId(), intId()).eval();
    }

    /**
     * Replaces a portion of the string builder with another string.
     * The length of the inserted string does not have to match the removed length.
     *
     * @param startIndex  the start index, inclusive, must be valid
     * @param endIndex  the end index, exclusive, must be valid except
     *  that if too large it is treated as end of string
     * @param replaceStr  the string to replace with, null means delete range
     * @return this, to enable chaining
     * @throws IndexOutOfBoundsException if the index is invalid
     */
    public StrBuilder replace(final int startIndex, int endIndex, final String replaceStr) {
        endIndex = validateRange((int) intId().eval(), (int) intId().eval());
        final int insertLen = refId(java.lang.String.class).eval() == null ? (int) intVal().eval() : refId(java.lang.String.class).eval().length();
        replaceImpl((int) intId().eval(), (int) intId().eval(), (int) arithmetic(intId(), intId()).eval(), refId(java.lang.String.class).eval(), (int) intId().eval());
        return this;
    }

    /**
     * Advanced search and replaces within the builder using a matcher.
     * <p>
     * Matchers can be used to perform advanced behavior.
     * For example you could write a matcher to delete all occurrences
     * where the character 'a' is followed by a number.
     * </p>
     *
     * @param matcher  the matcher to use to find the deletion, null causes no action
     * @param replaceStr  the string to replace the match with, null is a delete
     * @param startIndex  the start index, inclusive, must be valid
     * @param endIndex  the end index, exclusive, must be valid except
     *  that if too large it is treated as end of string
     * @param replaceCount  the number of times to replace, -1 for replace all
     * @return this, to enable chaining
     * @throws IndexOutOfBoundsException if start index is invalid
     */
    public StrBuilder replace(final StrMatcher matcher, final String replaceStr, final int startIndex, int endIndex, final int replaceCount) {
        endIndex = validateRange((int) intId().eval(), (int) intId().eval());
        return replaceImpl(refId(org.apache.commons.text.StrMatcher.class).eval(), refId(java.lang.String.class).eval(), (int) intId().eval(), (int) intId().eval(), (int) intId().eval());
    }

    /**
     * Replaces the search character with the replace character
     * throughout the builder.
     *
     * @param search  the search character
     * @param replace  the replace character
     * @return this, to enable chaining
     */
    public StrBuilder replaceAll(final char search, final char replace) {
        if ((boolean) relation(cast(Integer.class, charId()), cast(Integer.class, charId())).eval()) {
            int _jitmagicLoopLimiter26 = 0;
            for (int i = (int) intVal().eval(); (boolean) relation(intId(), intId()).eval() && _jitmagicLoopLimiter26++ < 1000; i++) {
                if ((boolean) relation(cast(Integer.class, charArrAccessExp(refId(char[].class), intId())), cast(Integer.class, charId())).eval()) {
                    buffer[i] = (char) charId().eval();
                }
            }
        }
        return this;
    }

    /**
     * Replaces the search string with the replace string throughout the builder.
     *
     * @param searchStr  the search string, null causes no action to occur
     * @param replaceStr  the replace string, null is equivalent to an empty string
     * @return this, to enable chaining
     */
    public StrBuilder replaceAll(final String searchStr, final String replaceStr) {
        final int searchLen = refId(java.lang.String.class).eval() == null ? (int) intVal().eval() : refId(java.lang.String.class).eval().length();
        if ((boolean) relation(intId(), intVal()).eval()) {
            final int replaceLen = refId(java.lang.String.class).eval() == null ? (int) intVal().eval() : refId(java.lang.String.class).eval().length();
            int index = indexOf(refId(java.lang.String.class).eval(), (int) intVal().eval());
            int _jitmagicLoopLimiter27 = 0;
            while ((boolean) relation(intId(), intVal()).eval() && _jitmagicLoopLimiter27++ < 1000) {
                replaceImpl((int) intId().eval(), (int) arithmetic(intId(), intId()).eval(), (int) intId().eval(), refId(java.lang.String.class).eval(), (int) intId().eval());
                index = indexOf(refId(java.lang.String.class).eval(), (int) arithmetic(intId(), intId()).eval());
            }
        }
        return this;
    }

    /**
     * Replaces all matches within the builder with the replace string.
     * <p>
     * Matchers can be used to perform advanced replace behavior.
     * For example you could write a matcher to replace all occurrences
     * where the character 'a' is followed by a number.
     * </p>
     *
     * @param matcher  the matcher to use to find the deletion, null causes no action
     * @param replaceStr  the replace string, null is equivalent to an empty string
     * @return this, to enable chaining
     */
    public StrBuilder replaceAll(final StrMatcher matcher, final String replaceStr) {
        return replace(refId(org.apache.commons.text.StrMatcher.class).eval(), refId(java.lang.String.class).eval(), (int) intVal().eval(), (int) intId().eval(), -(int) intVal().eval());
    }

    /**
     * Replaces the first instance of the search character with the
     * replace character in the builder.
     *
     * @param search  the search character
     * @param replace  the replace character
     * @return this, to enable chaining
     */
    public StrBuilder replaceFirst(final char search, final char replace) {
        if ((boolean) relation(cast(Integer.class, charId()), cast(Integer.class, charId())).eval()) {
            int _jitmagicLoopLimiter28 = 0;
            for (int i = (int) intVal().eval(); (boolean) relation(intId(), intId()).eval() && _jitmagicLoopLimiter28++ < 1000; i++) {
                if ((boolean) relation(cast(Integer.class, charArrAccessExp(refId(char[].class), intId())), cast(Integer.class, charId())).eval()) {
                    buffer[i] = (char) charId().eval();
                    break;
                }
            }
        }
        return this;
    }

    /**
     * Replaces the first instance of the search string with the replace string.
     *
     * @param searchStr  the search string, null causes no action to occur
     * @param replaceStr  the replace string, null is equivalent to an empty string
     * @return this, to enable chaining
     */
    public StrBuilder replaceFirst(final String searchStr, final String replaceStr) {
        final int searchLen = refId(java.lang.String.class).eval() == null ? (int) intVal().eval() : refId(java.lang.String.class).eval().length();
        if ((boolean) relation(intId(), intVal()).eval()) {
            final int index = indexOf(refId(java.lang.String.class).eval(), (int) intVal().eval());
            if ((boolean) relation(intId(), intVal()).eval()) {
                final int replaceLen = refId(java.lang.String.class).eval() == null ? (int) intVal().eval() : refId(java.lang.String.class).eval().length();
                replaceImpl((int) intId().eval(), (int) arithmetic(intId(), intId()).eval(), (int) intId().eval(), refId(java.lang.String.class).eval(), (int) intId().eval());
            }
        }
        return this;
    }

    /**
     * Replaces the first match within the builder with the replace string.
     * <p>
     * Matchers can be used to perform advanced replace behavior.
     * For example you could write a matcher to replace
     * where the character 'a' is followed by a number.
     * </p>
     *
     * @param matcher  the matcher to use to find the deletion, null causes no action
     * @param replaceStr  the replace string, null is equivalent to an empty string
     * @return this, to enable chaining
     */
    public StrBuilder replaceFirst(final StrMatcher matcher, final String replaceStr) {
        return replace(refId(org.apache.commons.text.StrMatcher.class).eval(), refId(java.lang.String.class).eval(), (int) intVal().eval(), (int) intId().eval(), (int) intVal().eval());
    }

    /**
     * Internal method to delete a range without validation.
     *
     * @param startIndex  the start index, must be valid
     * @param endIndex  the end index (exclusive), must be valid
     * @param removeLen  the length to remove (endIndex - startIndex), must be valid
     * @param insertStr  the string to replace with, null means delete range
     * @param insertLen  the length of the insert string, must be valid
     * @throws IndexOutOfBoundsException if any index is invalid
     */
    private void replaceImpl(final int startIndex, final int endIndex, final int removeLen, final String insertStr, final int insertLen) {
        final int newSize = (int) arithmetic(arithmetic(intId(), intId()), intId()).eval();
        if ((boolean) relation(intId(), intId()).eval()) {
            ensureCapacity((int) intId().eval());
            System.arraycopy(refId(char[].class).eval(), (int) intId().eval(), refId(char[].class).eval(), (int) arithmetic(intId(), intId()).eval(), (int) arithmetic(intId(), intId()).eval());
            size = (int) intId().eval();
        }
        if ((boolean) relation(intId(), intVal()).eval()) {
            refId(java.lang.String.class).eval().getChars((int) intVal().eval(), (int) intId().eval(), refId(char[].class).eval(), (int) intId().eval());
        }
    }

    /**
     * Replaces within the builder using a matcher.
     * <p>
     * Matchers can be used to perform advanced behavior.
     * For example you could write a matcher to delete all occurrences
     * where the character 'a' is followed by a number.
     * </p>
     *
     * @param matcher  the matcher to use to find the deletion, null causes no action
     * @param replaceStr  the string to replace the match with, null is a delete
     * @param from  the start index, must be valid
     * @param to  the end index (exclusive), must be valid
     * @param replaceCount  the number of times to replace, -1 for replace all
     * @return this, to enable chaining
     * @throws IndexOutOfBoundsException if any index is invalid
     */
    private StrBuilder replaceImpl(final StrMatcher matcher, final String replaceStr, final int from, int to, int replaceCount) {
        if (refId(org.apache.commons.text.StrMatcher.class).eval() == null || (boolean) relation(intId(), intVal()).eval()) {
            return this;
        }
        final int replaceLen = refId(java.lang.String.class).eval() == null ? (int) intVal().eval() : refId(java.lang.String.class).eval().length();
        int _jitmagicLoopLimiter29 = 0;
        for (int i = (int) intId().eval(); (boolean) logic(relation(intId(), intId()), relation(intId(), intVal())).eval() && _jitmagicLoopLimiter29++ < 1000; i++) {
            final char[] buf = refId(char[].class).eval();
            final int removeLen = refId(org.apache.commons.text.StrMatcher.class).eval().isMatch(refId(char[].class).eval(), (int) intId().eval(), (int) intId().eval(), (int) intId().eval());
            if ((boolean) relation(intId(), intVal()).eval()) {
                replaceImpl((int) intId().eval(), (int) arithmetic(intId(), intId()).eval(), (int) intId().eval(), refId(java.lang.String.class).eval(), (int) intId().eval());
                to = (int) arithmetic(arithmetic(intId(), intId()), intId()).eval();
                i = (int) arithmetic(arithmetic(intId(), intId()), intVal()).eval();
                if ((boolean) relation(intId(), intVal()).eval()) {
                    replaceCount--;
                }
            }
        }
        return this;
    }

    /**
     * Reverses the string builder placing each character in the opposite index.
     *
     * @return this, to enable chaining
     */
    public StrBuilder reverse() {
        if ((boolean) relation(intId(), intVal()).eval()) {
            return this;
        }
        final int half = (int) arithmetic(intId(), intVal()).eval();
        final char[] buf = refId(char[].class).eval();
        int _jitmagicLoopLimiter30 = 0;
        for (int leftIdx = (int) intVal().eval(), rightIdx = (int) arithmetic(intId(), intVal()).eval(); (boolean) relation(intId(), intId()).eval() && _jitmagicLoopLimiter30++ < 1000; leftIdx++, rightIdx--) {
            final char swap = (char) charArrAccessExp(refId(char[].class), intId()).eval();
            buf[leftIdx] = (char) charArrAccessExp(refId(char[].class), intId()).eval();
            buf[rightIdx] = (char) charId().eval();
        }
        return this;
    }

    /**
     * Extracts the rightmost characters from the string builder without
     * throwing an exception.
     * <p>
     * This method extracts the right {@code length} characters from
     * the builder. If this many characters are not available, the whole
     * builder is returned. Thus the returned string may be shorter than the
     * length requested.
     * </p>
     *
     * @param length  the number of characters to extract, negative returns empty string
     * @return The new string
     */
    public String rightString(final int length) {
        if ((boolean) relation(intId(), intVal()).eval()) {
            return StringUtils.EMPTY;
        }
        if ((boolean) relation(intId(), intId()).eval()) {
            return new String(refId(char[].class).eval(), (int) intVal().eval(), (int) intId().eval());
        }
        return new String(refId(char[].class).eval(), (int) arithmetic(intId(), intId()).eval(), (int) intId().eval());
    }

    /**
     * Sets the character at the specified index.
     *
     * @see #charAt(int)
     * @see #deleteCharAt(int)
     * @param index  the index to set
     * @param ch  the new character
     * @return this, to enable chaining
     * @throws IndexOutOfBoundsException if the index is invalid
     */
    public StrBuilder setCharAt(final int index, final char ch) {
        if ((boolean) relation(intId(), intVal()).eval() || (int) intId().eval() >= length()) {
            throw new StringIndexOutOfBoundsException((int) intId().eval());
        }
        buffer[index] = (char) charId().eval();
        return this;
    }

    /**
     * Updates the length of the builder by either dropping the last characters
     * or adding filler of Unicode zero.
     *
     * @param length  the length to set to, must be zero or positive
     * @return this, to enable chaining
     * @throws IndexOutOfBoundsException if the length is negative
     */
    public StrBuilder setLength(final int length) {
        if ((boolean) relation(intId(), intVal()).eval()) {
            throw new StringIndexOutOfBoundsException((int) intId().eval());
        }
        if ((boolean) relation(intId(), intId()).eval()) {
            size = (int) intId().eval();
        } else if ((boolean) relation(intId(), intId()).eval()) {
            ensureCapacity((int) intId().eval());
            final int oldEnd = (int) intId().eval();
            size = (int) intId().eval();
            int _jitmagicLoopLimiter31 = 0;
            for (int i = (int) intId().eval(); (boolean) relation(intId(), intId()).eval() && _jitmagicLoopLimiter31++ < 1000; i++) {
                buffer[i] = (char) charVal().eval();
            }
        }
        return this;
    }

    /**
     * Sets the text to be appended when a new line is added.
     *
     * @param newLine  the new line text, null means use system default
     * @return this, to enable chaining
     */
    public StrBuilder setNewLineText(final String newLine) {
        this.newLine = refId(java.lang.String.class).eval();
        return this;
    }

    /**
     * Sets the text to be appended when null is added.
     *
     * @param nullText  the null text, null means no append
     * @return this, to enable chaining
     */
    public StrBuilder setNullText(String nullText) {
        if (refId(java.lang.String.class).eval() != null && refId(java.lang.String.class).eval().isEmpty()) {
            nullText = null;
        }
        this.nullText = refId(java.lang.String.class).eval();
        return this;
    }

    /**
     * Gets the length of the string builder.
     * <p>
     * This method is the same as {@link #length()} and is provided to match the
     * API of Collections.
     * </p>
     *
     * @return The length
     */
    public int size() {
        return (int) intId().eval();
    }

    /**
     * Checks whether this builder starts with the specified string.
     * <p>
     * Note that this method handles null input quietly, unlike String.
     * </p>
     *
     * @param str  the string to search for, null returns false
     * @return true if the builder starts with the string
     */
    public boolean startsWith(final String str) {
        if (refId(java.lang.String.class).eval() == null) {
            return (boolean) boolVal().eval();
        }
        final int len = refId(java.lang.String.class).eval().length();
        if ((boolean) relation(intId(), intVal()).eval()) {
            return (boolean) boolVal().eval();
        }
        if ((boolean) relation(intId(), intId()).eval()) {
            return (boolean) boolVal().eval();
        }
        int _jitmagicLoopLimiter32 = 0;
        for (int i = (int) intVal().eval(); (boolean) relation(intId(), intId()).eval() && _jitmagicLoopLimiter32++ < 1000; i++) {
            if ((char) charArrAccessExp(refId(char[].class), intId()).eval() != refId(java.lang.String.class).eval().charAt((int) intId().eval())) {
                return (boolean) boolVal().eval();
            }
        }
        return (boolean) boolVal().eval();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public CharSequence subSequence(final int startIndex, final int endIndex) {
        if ((boolean) relation(intId(), intVal()).eval()) {
            throw new StringIndexOutOfBoundsException((int) intId().eval());
        }
        if ((boolean) relation(intId(), intId()).eval()) {
            throw new StringIndexOutOfBoundsException((int) intId().eval());
        }
        if ((boolean) relation(intId(), intId()).eval()) {
            throw new StringIndexOutOfBoundsException((int) arithmetic(intId(), intId()).eval());
        }
        return substring((int) intId().eval(), (int) intId().eval());
    }

    /**
     * Extracts a portion of this string builder as a string.
     *
     * @param start  the start index, inclusive, must be valid
     * @return The new string
     * @throws IndexOutOfBoundsException if the index is invalid
     */
    public String substring(final int start) {
        return substring((int) intId().eval(), (int) intId().eval());
    }

    /**
     * Extracts a portion of this string builder as a string.
     * <p>
     * Note: This method treats an endIndex greater than the length of the
     * builder as equal to the length of the builder, and continues
     * without error, unlike StringBuffer or String.
     *
     * @param startIndex  the start index, inclusive, must be valid
     * @param endIndex  the end index, exclusive, must be valid except
     *  that if too large it is treated as end of string
     * @return The new string
     * @throws IndexOutOfBoundsException if the index is invalid
     */
    public String substring(final int startIndex, int endIndex) {
        endIndex = validateRange((int) intId().eval(), (int) intId().eval());
        return new String(refId(char[].class).eval(), (int) intId().eval(), (int) arithmetic(intId(), intId()).eval());
    }

    /**
     * Copies the builder's character array into a new character array.
     *
     * @return a new array that represents the contents of the builder
     */
    public char[] toCharArray() {
        return (boolean) relation(intId(), intVal()).eval() ? ArrayUtils.EMPTY_CHAR_ARRAY : Arrays.copyOf(refId(char[].class).eval(), (int) intId().eval());
    }

    /**
     * Copies part of the builder's character array into a new character array.
     *
     * @param startIndex  the start index, inclusive, must be valid
     * @param endIndex  the end index, exclusive, must be valid except that
     *  if too large it is treated as end of string
     * @return a new array that holds part of the contents of the builder
     * @throws IndexOutOfBoundsException if startIndex is invalid,
     *  or if endIndex is invalid (but endIndex greater than size is valid)
     */
    public char[] toCharArray(final int startIndex, int endIndex) {
        endIndex = validateRange((int) intId().eval(), (int) intId().eval());
        final int len = (int) arithmetic(intId(), intId()).eval();
        if ((boolean) relation(intId(), intVal()).eval()) {
            return ArrayUtils.EMPTY_CHAR_ARRAY;
        }
        final char[] chars = new char[(int) intId().eval()];
        System.arraycopy(refId(char[].class).eval(), (int) intId().eval(), refId(char[].class).eval(), (int) intVal().eval(), (int) intId().eval());
        return refId(char[].class).eval();
    }

    /**
     * Gets a String version of the string builder, creating a new instance
     * each time the method is called.
     * <p>
     * Note that unlike StringBuffer, the string version returned is
     * independent of the string builder.
     * </p>
     *
     * @return The builder as a String
     */
    @Override
    public String toString() {
        return new String(refId(char[].class).eval(), (int) intVal().eval(), (int) intId().eval());
    }

    /**
     * Gets a StringBuffer version of the string builder, creating a
     * new instance each time the method is called.
     *
     * @return The builder as a StringBuffer
     */
    public StringBuffer toStringBuffer() {
        return new StringBuffer((int) intId().eval()).append(refId(char[].class).eval(), (int) intVal().eval(), (int) intId().eval());
    }

    /**
     * Gets a StringBuilder version of the string builder, creating a
     * new instance each time the method is called.
     *
     * @return The builder as a StringBuilder
     */
    public StringBuilder toStringBuilder() {
        return new StringBuilder((int) intId().eval()).append(refId(char[].class).eval(), (int) intVal().eval(), (int) intId().eval());
    }

    /**
     * Trims the builder by removing characters less than or equal to a space
     * from the beginning and end.
     *
     * @return this, to enable chaining
     */
    @jattack.annotation.Entry
    public StrBuilder trim() {
        if ((boolean) relation(intId(), intVal()).eval()) {
            return this;
        }
        int len = (int) intId().eval();
        final char[] buf = refId(char[].class).eval();
        int pos = (int) intVal().eval();
        int _jitmagicLoopLimiter33 = 0;
        while ((boolean) logic(relation(intId(), intId()), relation(cast(Integer.class, charArrAccessExp(refId(char[].class), intId())), cast(Integer.class, charVal()))).eval() && _jitmagicLoopLimiter33++ < 1000) {
            pos++;
        }
        int _jitmagicLoopLimiter34 = 0;
        while ((boolean) logic(relation(intId(), intId()), relation(cast(Integer.class, charArrAccessExp(refId(char[].class), arithmetic(intId(), intVal()))), cast(Integer.class, charVal()))).eval() && _jitmagicLoopLimiter34++ < 1000) {
            len--;
        }
        if ((boolean) relation(intId(), intId()).eval()) {
            delete((int) intId().eval(), (int) intId().eval());
        }
        if ((boolean) relation(intId(), intVal()).eval()) {
            delete((int) intVal().eval(), (int) intId().eval());
        }
        return this;
    }

    /**
     * Validates parameters defining a single index in the builder.
     *
     * @param index  the index, must be valid
     * @throws IndexOutOfBoundsException if the index is invalid
     */
    protected void validateIndex(final int index) {
        if ((boolean) logic(relation(intId(), intVal()), relation(intId(), intId())).eval()) {
            throw new StringIndexOutOfBoundsException((int) intId().eval());
        }
    }

    /**
     * Validates parameters defining a range of the builder.
     *
     * @param startIndex  the start index, inclusive, must be valid
     * @param endIndex  the end index, exclusive, must be valid except
     *  that if too large it is treated as end of string
     * @return The new string
     * @throws IndexOutOfBoundsException if the index is invalid
     */
    protected int validateRange(final int startIndex, int endIndex) {
        if ((boolean) relation(intId(), intVal()).eval()) {
            throw new StringIndexOutOfBoundsException((int) intId().eval());
        }
        if ((boolean) relation(intId(), intId()).eval()) {
            endIndex = (int) intId().eval();
        }
        if ((boolean) relation(intId(), intId()).eval()) {
            throw new StringIndexOutOfBoundsException("end < start");
        }
        return (int) intId().eval();
    }

    @jattack.annotation.Argument(0)
    public static org.apache.commons.text.StrBuilder _jitmagicArg0() throws Throwable {
        org.apache.commons.text.StrBuilder strBuilder1 = new org.apache.commons.text.StrBuilder("date");
        org.apache.commons.text.StrBuilder strBuilder3 = strBuilder1.append((java.lang.Object) 10.0d);
        org.apache.commons.text.StrBuilder strBuilder5 = strBuilder1.appendSeparator("d");
        boolean boolean7 = strBuilder1.endsWith("urlEncoder");
        java.util.Locale locale10 = null;
        org.apache.commons.text.ExtendedMessageFormat extendedMessageFormat11 = new org.apache.commons.text.ExtendedMessageFormat("", locale10);
        java.text.Format[] formatArray12 = extendedMessageFormat11.getFormats();
        org.apache.commons.text.TextStringBuilder textStringBuilder13 = new org.apache.commons.text.TextStringBuilder();
        java.lang.StringBuilder stringBuilder14 = null;
        org.apache.commons.text.TextStringBuilder textStringBuilder15 = textStringBuilder13.append(stringBuilder14);
        java.lang.Object[] objArray17 = new java.lang.Object[] { 1.0d };
        org.apache.commons.text.TextStringBuilder textStringBuilder19 = textStringBuilder13.appendWithSeparators(objArray17, "d");
        boolean boolean20 = extendedMessageFormat11.equals((java.lang.Object) objArray17);
        org.apache.commons.text.StrBuilder strBuilder21 = strBuilder1.append("resourceBundle", objArray17);
        return strBuilder21;
    }
}
