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
import org.csutil.checksum.WrappedChecksum;

@Deprecated
public class StrBuilder implements CharSequence, Appendable, Serializable, Builder<String> {

    class StrBuilderReader extends Reader {

        private int pos;

        private int mark;

        StrBuilderReader() {
        }

        @Override
        public void close() {
        }

        @Override
        public void mark(final int readAheadLimit) {
            mark = pos;
        }

        @Override
        public boolean markSupported() {
            return true;
        }

        @Override
        public int read() {
            if (!ready()) {
                return -1;
            }
            return StrBuilder.this.charAt(pos++);
        }

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

        @Override
        public boolean ready() {
            return pos < StrBuilder.this.size();
        }

        @Override
        public void reset() {
            pos = mark;
        }

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

    class StrBuilderTokenizer extends StrTokenizer {

        StrBuilderTokenizer() {
        }

        @Override
        public String getContent() {
            final String str = super.getContent();
            if (str == null) {
                return StrBuilder.this.toString();
            }
            return str;
        }

        @Override
        protected List<String> tokenize(final char[] chars, final int offset, final int count) {
            if (chars == null) {
                return super.tokenize(StrBuilder.this.buffer, 0, StrBuilder.this.size());
            }
            return super.tokenize(chars, offset, count);
        }
    }

    class StrBuilderWriter extends Writer {

        StrBuilderWriter() {
        }

        @Override
        public void close() {
        }

        @Override
        public void flush() {
        }

        @Override
        public void write(final char[] cbuf) {
            StrBuilder.this.append(cbuf);
        }

        @Override
        public void write(final char[] cbuf, final int off, final int len) {
            StrBuilder.this.append(cbuf, off, len);
        }

        @Override
        public void write(final int c) {
            StrBuilder.this.append((char) c);
        }

        @Override
        public void write(final String str) {
            StrBuilder.this.append(str);
        }

        @Override
        public void write(final String str, final int off, final int len) {
            StrBuilder.this.append(str, off, len);
        }
    }

    static final int CAPACITY = 32;

    private static final long serialVersionUID = 7628716375283629643L;

    char[] buffer;

    private int size;

    private String newLine;

    private String nullText;

    public StrBuilder() {
        this((int) intId().eval(1));
    }

    public StrBuilder(int initialCapacity) {
        if ((boolean) (CAPACITY <= 0)) {
            initialCapacity = (int) intId().eval(3);
        }
        buffer = new char[(int) initialCapacity];
    }

    public StrBuilder(final String str) {
        if (refId(java.lang.String.class).eval(5) == null) {
            buffer = new char[(int) intId().eval(9)];
        } else {
            buffer = new char[refId(java.lang.String.class).eval(6).length() + (int) intId().eval(7)];
            append(refId(java.lang.String.class).eval(8));
        }
    }

    public StrBuilder append(final boolean value) {
        if ((boolean) boolId().eval(10)) {
            ensureCapacity((int) arithmetic(intId(), asInt(4), ADD).eval(17));
            buffer[size++] = (char) asChar('t').eval(18);
            buffer[size++] = (char) asChar('r').eval(19);
            buffer[size++] = (char) asChar('u').eval(20);
            buffer[size++] = (char) asChar('e').eval(21);
        } else {
            ensureCapacity((int) arithmetic(intId(), asInt(5), ADD).eval(11));
            buffer[size++] = (char) asChar('f').eval(12);
            buffer[size++] = (char) asChar('a').eval(13);
            buffer[size++] = (char) asChar('l').eval(14);
            buffer[size++] = (char) asChar('s').eval(15);
            buffer[size++] = (char) asChar('e').eval(16);
        }
        return this;
    }

    @Override
    public StrBuilder append(final char ch) {
        final int len = length();
        ensureCapacity((int) arithmetic(intId(), asInt(1), ADD).eval(22));
        buffer[size++] = (char) charId().eval(23);
        return this;
    }

    public StrBuilder append(final char[] chars) {
        if (refId(char[].class).eval(24) == null) {
            return appendNull();
        }
        final int strLen = chars.length;
        if ((boolean) relation(intId(), asInt(0), GT).eval(25)) {
            final int len = length();
            ensureCapacity((int) arithmetic(intId(), intId(), ADD).eval(26));
            System.arraycopy(refId(char[].class).eval(27), (int) asInt(0).eval(28), refId(char[].class).eval(29), (int) intId().eval(30), (int) intId().eval(31));
            size += (int) intId().eval(32);
        }
        return this;
    }

    public StrBuilder append(final char[] chars, final int startIndex, final int length) {
        if (refId(char[].class).eval(33) == null) {
            return appendNull();
        }
        if ((boolean) relation(intId(), asInt(0), LT).eval(34) || (int) intId().eval(35) > chars.length) {
            throw new StringIndexOutOfBoundsException("Invalid startIndex: " + (int) intId().eval(36));
        }
        if ((boolean) relation(intId(), asInt(0), LT).eval(37) || (int) arithmetic(intId(), intId(), ADD).eval(38) > chars.length) {
            throw new StringIndexOutOfBoundsException("Invalid length: " + (int) intId().eval(39));
        }
        if ((boolean) relation(intId(), asInt(0), GT).eval(40)) {
            final int len = length();
            ensureCapacity((int) arithmetic(intId(), intId(), ADD).eval(41));
            System.arraycopy(refId(char[].class).eval(42), (int) intId().eval(43), refId(char[].class).eval(44), (int) intId().eval(45), (int) intId().eval(46));
            size += (int) intId().eval(47);
        }
        return this;
    }

    public StrBuilder append(final CharBuffer buf) {
        if (refId(java.nio.CharBuffer.class).eval(48) == null) {
            return appendNull();
        }
        if (refId(java.nio.CharBuffer.class).eval(49).hasArray()) {
            final int length = refId(java.nio.CharBuffer.class).eval(51).remaining();
            final int len = length();
            ensureCapacity((int) arithmetic(intId(), intId(), ADD).eval(52));
            System.arraycopy(refId(java.nio.CharBuffer.class).eval(53).array(), refId(java.nio.CharBuffer.class).eval(54).arrayOffset() + refId(java.nio.CharBuffer.class).eval(55).position(), refId(char[].class).eval(56), (int) intId().eval(57), (int) intId().eval(58));
            size += (int) intId().eval(59);
        } else {
            append(refId(java.nio.CharBuffer.class).eval(50).toString());
        }
        return this;
    }

    public StrBuilder append(final CharBuffer buf, final int startIndex, final int length) {
        if (refId(java.nio.CharBuffer.class).eval(60) == null) {
            return appendNull();
        }
        if (refId(java.nio.CharBuffer.class).eval(61).hasArray()) {
            final int totalLength = refId(java.nio.CharBuffer.class).eval(65).remaining();
            if ((boolean) logic(relation(intId(), asInt(0), LT), relation(intId(), intId(), GT), OR).eval(66)) {
                throw new StringIndexOutOfBoundsException("startIndex must be valid");
            }
            if ((boolean) logic(relation(intId(), asInt(0), LT), relation(arithmetic(intId(), intId(), ADD), intId(), GT), OR).eval(67)) {
                throw new StringIndexOutOfBoundsException("length must be valid");
            }
            final int len = length();
            ensureCapacity((int) arithmetic(intId(), intId(), ADD).eval(68));
            System.arraycopy(refId(java.nio.CharBuffer.class).eval(69).array(), refId(java.nio.CharBuffer.class).eval(70).arrayOffset() + refId(java.nio.CharBuffer.class).eval(71).position() + (int) intId().eval(72), refId(char[].class).eval(73), (int) intId().eval(74), (int) intId().eval(75));
            size += (int) intId().eval(76);
        } else {
            append(refId(java.nio.CharBuffer.class).eval(62).toString(), (int) intId().eval(63), (int) intId().eval(64));
        }
        return this;
    }

    @Override
    public StrBuilder append(final CharSequence seq) {
        if (refId(java.lang.CharSequence.class).eval(77) == null) {
            return appendNull();
        }
        if (seq instanceof StrBuilder) {
            return append(cast(StrBuilder.class, refId(java.lang.CharSequence.class)).eval(78));
        }
        if (seq instanceof StringBuilder) {
            return append(cast(StringBuilder.class, refId(java.lang.CharSequence.class)).eval(79));
        }
        if (seq instanceof StringBuffer) {
            return append(cast(StringBuffer.class, refId(java.lang.CharSequence.class)).eval(80));
        }
        if (seq instanceof CharBuffer) {
            return append(cast(CharBuffer.class, refId(java.lang.CharSequence.class)).eval(81));
        }
        return append(refId(java.lang.CharSequence.class).eval(82).toString());
    }

    @Override
    public StrBuilder append(final CharSequence seq, final int startIndex, final int length) {
        if (refId(java.lang.CharSequence.class).eval(83) == null) {
            return appendNull();
        }
        return append(refId(java.lang.CharSequence.class).eval(84).toString(), (int) intId().eval(85), (int) intId().eval(86));
    }

    public StrBuilder append(final double value) {
        return append(String.valueOf((double) doubleId().eval(87)));
    }

    public StrBuilder append(final float value) {
        return append(String.valueOf((float) floatId().eval(88)));
    }

    public StrBuilder append(final int value) {
        return append(String.valueOf((int) intId().eval(89)));
    }

    public StrBuilder append(final long value) {
        return append(String.valueOf((long) longId().eval(90)));
    }

    public StrBuilder append(final Object obj) {
        if (refId(java.lang.Object.class).eval(91) == null) {
            return appendNull();
        }
        if (obj instanceof CharSequence) {
            return append(cast(CharSequence.class, refId(java.lang.Object.class)).eval(92));
        }
        return append(refId(java.lang.Object.class).eval(93).toString());
    }

    public StrBuilder append(final StrBuilder str) {
        if (refId(org.apache.commons.text.StrBuilder.class).eval(94) == null) {
            return appendNull();
        }
        final int strLen = refId(org.apache.commons.text.StrBuilder.class).eval(95).length();
        if ((boolean) relation(intId(), asInt(0), GT).eval(96)) {
            final int len = length();
            ensureCapacity((int) arithmetic(intId(), intId(), ADD).eval(97));
            System.arraycopy(str.buffer, (int) asInt(0).eval(98), refId(char[].class).eval(99), (int) intId().eval(100), (int) intId().eval(101));
            size += (int) intId().eval(102);
        }
        return this;
    }

    public StrBuilder append(final StrBuilder str, final int startIndex, final int length) {
        if (refId(org.apache.commons.text.StrBuilder.class).eval(103) == null) {
            return appendNull();
        }
        if ((boolean) relation(intId(), asInt(0), LT).eval(104) || (int) intId().eval(105) > refId(org.apache.commons.text.StrBuilder.class).eval(106).length()) {
            throw new StringIndexOutOfBoundsException("startIndex must be valid");
        }
        if ((boolean) relation(intId(), asInt(0), LT).eval(107) || (int) arithmetic(intId(), intId(), ADD).eval(108) > refId(org.apache.commons.text.StrBuilder.class).eval(109).length()) {
            throw new StringIndexOutOfBoundsException("length must be valid");
        }
        if ((boolean) relation(intId(), asInt(0), GT).eval(110)) {
            final int len = length();
            ensureCapacity((int) arithmetic(intId(), intId(), ADD).eval(111));
            refId(org.apache.commons.text.StrBuilder.class).eval(116).getChars((int) intId().eval(112), (int) arithmetic(intId(), intId(), ADD).eval(113), refId(char[].class).eval(114), (int) intId().eval(115));
            size += (int) intId().eval(117);
        }
        return this;
    }

    public StrBuilder append(final String str) {
        if (refId(java.lang.String.class).eval(118) == null) {
            return appendNull();
        }
        final int strLen = refId(java.lang.String.class).eval(119).length();
        if ((boolean) relation(intId(), asInt(0), GT).eval(120)) {
            final int len = length();
            ensureCapacity((int) arithmetic(intId(), intId(), ADD).eval(121));
            refId(java.lang.String.class).eval(126).getChars((int) asInt(0).eval(122), (int) intId().eval(123), refId(char[].class).eval(124), (int) intId().eval(125));
            size += (int) intId().eval(127);
        }
        return this;
    }

    public StrBuilder append(final String str, final int startIndex, final int length) {
        if (refId(java.lang.String.class).eval(128) == null) {
            return appendNull();
        }
        if ((boolean) relation(intId(), asInt(0), LT).eval(129) || (int) intId().eval(130) > refId(java.lang.String.class).eval(131).length()) {
            throw new StringIndexOutOfBoundsException("startIndex must be valid");
        }
        if ((boolean) relation(intId(), asInt(0), LT).eval(132) || (int) arithmetic(intId(), intId(), ADD).eval(133) > refId(java.lang.String.class).eval(134).length()) {
            throw new StringIndexOutOfBoundsException("length must be valid");
        }
        if ((boolean) relation(intId(), asInt(0), GT).eval(135)) {
            final int len = length();
            ensureCapacity((int) arithmetic(intId(), intId(), ADD).eval(136));
            refId(java.lang.String.class).eval(141).getChars((int) intId().eval(137), (int) arithmetic(intId(), intId(), ADD).eval(138), refId(char[].class).eval(139), (int) intId().eval(140));
            size += (int) intId().eval(142);
        }
        return this;
    }

    public StrBuilder append(final String format, final Object... objs) {
        return append(String.format(refId(java.lang.String.class).eval(143), refId(java.lang.Object[].class).eval(144)));
    }

    public StrBuilder append(final StringBuffer str) {
        if (refId(java.lang.StringBuffer.class).eval(145) == null) {
            return appendNull();
        }
        final int strLen = refId(java.lang.StringBuffer.class).eval(146).length();
        if ((boolean) relation(intId(), asInt(0), GT).eval(147)) {
            final int len = length();
            ensureCapacity((int) arithmetic(intId(), intId(), ADD).eval(148));
            refId(java.lang.StringBuffer.class).eval(153).getChars((int) asInt(0).eval(149), (int) intId().eval(150), refId(char[].class).eval(151), (int) intId().eval(152));
            size += (int) intId().eval(154);
        }
        return this;
    }

    public StrBuilder append(final StringBuffer str, final int startIndex, final int length) {
        if (refId(java.lang.StringBuffer.class).eval(155) == null) {
            return appendNull();
        }
        if ((boolean) relation(intId(), asInt(0), LT).eval(156) || (int) intId().eval(157) > refId(java.lang.StringBuffer.class).eval(158).length()) {
            throw new StringIndexOutOfBoundsException("startIndex must be valid");
        }
        if ((boolean) relation(intId(), asInt(0), LT).eval(159) || (int) arithmetic(intId(), intId(), ADD).eval(160) > refId(java.lang.StringBuffer.class).eval(161).length()) {
            throw new StringIndexOutOfBoundsException("length must be valid");
        }
        if ((boolean) relation(intId(), asInt(0), GT).eval(162)) {
            final int len = length();
            ensureCapacity((int) arithmetic(intId(), intId(), ADD).eval(163));
            refId(java.lang.StringBuffer.class).eval(168).getChars((int) intId().eval(164), (int) arithmetic(intId(), intId(), ADD).eval(165), refId(char[].class).eval(166), (int) intId().eval(167));
            size += (int) intId().eval(169);
        }
        return this;
    }

    public StrBuilder append(final StringBuilder str) {
        if (refId(java.lang.StringBuilder.class).eval(170) == null) {
            return appendNull();
        }
        final int strLen = refId(java.lang.StringBuilder.class).eval(171).length();
        if ((boolean) relation(intId(), asInt(0), GT).eval(172)) {
            final int len = length();
            ensureCapacity((int) arithmetic(intId(), intId(), ADD).eval(173));
            refId(java.lang.StringBuilder.class).eval(178).getChars((int) asInt(0).eval(174), (int) intId().eval(175), refId(char[].class).eval(176), (int) intId().eval(177));
            size += (int) intId().eval(179);
        }
        return this;
    }

    public StrBuilder append(final StringBuilder str, final int startIndex, final int length) {
        if (refId(java.lang.StringBuilder.class).eval(180) == null) {
            return appendNull();
        }
        if ((boolean) relation(intId(), asInt(0), LT).eval(181) || (int) intId().eval(182) > refId(java.lang.StringBuilder.class).eval(183).length()) {
            throw new StringIndexOutOfBoundsException("startIndex must be valid");
        }
        if ((boolean) relation(intId(), asInt(0), LT).eval(184) || (int) arithmetic(intId(), intId(), ADD).eval(185) > refId(java.lang.StringBuilder.class).eval(186).length()) {
            throw new StringIndexOutOfBoundsException("length must be valid");
        }
        if ((boolean) relation(intId(), asInt(0), GT).eval(187)) {
            final int len = length();
            ensureCapacity((int) arithmetic(intId(), intId(), ADD).eval(188));
            refId(java.lang.StringBuilder.class).eval(193).getChars((int) intId().eval(189), (int) arithmetic(intId(), intId(), ADD).eval(190), refId(char[].class).eval(191), (int) intId().eval(192));
            size += (int) intId().eval(194);
        }
        return this;
    }

    public StrBuilder appendAll(final Iterable<?> iterable) {
        if (iterable != null) {
            iterable.forEach(this::append);
        }
        return this;
    }

    public StrBuilder appendAll(final Iterator<?> it) {
        if (it != null) {
            int _jitmagicLoopLimiter1 = 0;
            while (it.hasNext() && _jitmagicLoopLimiter1++ < 1000) {
                append(it.next());
            }
        }
        return this;
    }

    public <T> StrBuilder appendAll(@SuppressWarnings("unchecked") final T... array) {
        if (array != null && array.length > (int) asInt(0).eval(195)) {
            for (final Object element : array) {
                append(refId(java.lang.Object.class).eval(196));
            }
        }
        return this;
    }

    public StrBuilder appendFixedWidthPadLeft(final int value, final int width, final char padChar) {
        return appendFixedWidthPadLeft(String.valueOf((int) intId().eval(197)), (int) intId().eval(198), (char) charId().eval(199));
    }

    public StrBuilder appendFixedWidthPadLeft(final Object obj, final int width, final char padChar) {
        if ((boolean) relation(intId(), asInt(0), GT).eval(200)) {
            ensureCapacity((int) arithmetic(intId(), intId(), ADD).eval(201));
            String str = refId(java.lang.Object.class).eval(202) == null ? getNullText() : refId(java.lang.Object.class).eval(203).toString();
            if (refId(java.lang.String.class).eval(204) == null) {
                str = StringUtils.EMPTY;
            }
            final int strLen = refId(java.lang.String.class).eval(205).length();
            if ((boolean) relation(intId(), intId(), GE).eval(206)) {
                refId(java.lang.String.class).eval(220).getChars((int) arithmetic(intId(), intId(), SUB).eval(216), (int) intId().eval(217), refId(char[].class).eval(218), (int) intId().eval(219));
            } else {
                final int padLen = (int) arithmetic(intId(), intId(), SUB).eval(207);
                int _jitmagicLoopLimiter2 = 0;
                for (int i = (int) asInt(0).eval(210); (boolean) relation(intId(), intId(), LT).eval(209) && _jitmagicLoopLimiter2++ < 1000; i++) {
                    buffer[size + i] = (char) charId().eval(208);
                }
                refId(java.lang.String.class).eval(215).getChars((int) asInt(0).eval(211), (int) intId().eval(212), refId(char[].class).eval(213), (int) arithmetic(intId(), intId(), ADD).eval(214));
            }
            size += (int) intId().eval(221);
        }
        return this;
    }

    public StrBuilder appendFixedWidthPadRight(final int value, final int width, final char padChar) {
        return appendFixedWidthPadRight(String.valueOf((int) intId().eval(222)), (int) intId().eval(223), (char) charId().eval(224));
    }

    public StrBuilder appendFixedWidthPadRight(final Object obj, final int width, final char padChar) {
        if ((boolean) relation(intId(), asInt(0), GT).eval(225)) {
            ensureCapacity((int) arithmetic(intId(), intId(), ADD).eval(226));
            String str = refId(java.lang.Object.class).eval(227) == null ? getNullText() : refId(java.lang.Object.class).eval(228).toString();
            if (refId(java.lang.String.class).eval(229) == null) {
                str = StringUtils.EMPTY;
            }
            final int strLen = refId(java.lang.String.class).eval(230).length();
            if ((boolean) relation(intId(), intId(), GE).eval(231)) {
                refId(java.lang.String.class).eval(245).getChars((int) asInt(0).eval(241), (int) intId().eval(242), refId(char[].class).eval(243), (int) intId().eval(244));
            } else {
                final int padLen = (int) arithmetic(intId(), intId(), SUB).eval(232);
                refId(java.lang.String.class).eval(237).getChars((int) asInt(0).eval(233), (int) intId().eval(234), refId(char[].class).eval(235), (int) intId().eval(236));
                int _jitmagicLoopLimiter3 = 0;
                for (int i = (int) asInt(0).eval(240); (boolean) relation(intId(), intId(), LT).eval(239) && _jitmagicLoopLimiter3++ < 1000; i++) {
                    buffer[size + strLen + i] = (char) charId().eval(238);
                }
            }
            size += (int) intId().eval(246);
        }
        return this;
    }

    public StrBuilder appendln(final boolean value) {
        return append((boolean) boolId().eval(247)).appendNewLine();
    }

    public StrBuilder appendln(final char ch) {
        return append((char) charId().eval(248)).appendNewLine();
    }

    public StrBuilder appendln(final char[] chars) {
        return append(refId(char[].class).eval(249)).appendNewLine();
    }

    public StrBuilder appendln(final char[] chars, final int startIndex, final int length) {
        return append(refId(char[].class).eval(250), (int) intId().eval(251), (int) intId().eval(252)).appendNewLine();
    }

    public StrBuilder appendln(final double value) {
        return append((double) doubleId().eval(253)).appendNewLine();
    }

    public StrBuilder appendln(final float value) {
        return append((float) floatId().eval(254)).appendNewLine();
    }

    public StrBuilder appendln(final int value) {
        return append((int) intId().eval(255)).appendNewLine();
    }

    public StrBuilder appendln(final long value) {
        return append((long) longId().eval(256)).appendNewLine();
    }

    public StrBuilder appendln(final Object obj) {
        return append(refId(java.lang.Object.class).eval(257)).appendNewLine();
    }

    public StrBuilder appendln(final StrBuilder str) {
        return append(refId(org.apache.commons.text.StrBuilder.class).eval(258)).appendNewLine();
    }

    public StrBuilder appendln(final StrBuilder str, final int startIndex, final int length) {
        return append(refId(org.apache.commons.text.StrBuilder.class).eval(259), (int) intId().eval(260), (int) intId().eval(261)).appendNewLine();
    }

    public StrBuilder appendln(final String str) {
        return append(refId(java.lang.String.class).eval(262)).appendNewLine();
    }

    public StrBuilder appendln(final String str, final int startIndex, final int length) {
        return append(refId(java.lang.String.class).eval(263), (int) intId().eval(264), (int) intId().eval(265)).appendNewLine();
    }

    public StrBuilder appendln(final String format, final Object... objs) {
        return append(refId(java.lang.String.class).eval(266), refId(java.lang.Object[].class).eval(267)).appendNewLine();
    }

    public StrBuilder appendln(final StringBuffer str) {
        return append(refId(java.lang.StringBuffer.class).eval(268)).appendNewLine();
    }

    public StrBuilder appendln(final StringBuffer str, final int startIndex, final int length) {
        return append(refId(java.lang.StringBuffer.class).eval(269), (int) intId().eval(270), (int) intId().eval(271)).appendNewLine();
    }

    public StrBuilder appendln(final StringBuilder str) {
        return append(refId(java.lang.StringBuilder.class).eval(272)).appendNewLine();
    }

    public StrBuilder appendln(final StringBuilder str, final int startIndex, final int length) {
        return append(refId(java.lang.StringBuilder.class).eval(273), (int) intId().eval(274), (int) intId().eval(275)).appendNewLine();
    }

    public StrBuilder appendNewLine() {
        if (refId(java.lang.String.class).eval(276) == null) {
            append(System.lineSeparator());
            return this;
        }
        return append(refId(java.lang.String.class).eval(277));
    }

    public StrBuilder appendNull() {
        if (refId(java.lang.String.class).eval(278) == null) {
            return this;
        }
        return append(refId(java.lang.String.class).eval(279));
    }

    public StrBuilder appendPadding(final int length, final char padChar) {
        if ((boolean) relation(intId(), asInt(0), GE).eval(280)) {
            ensureCapacity((int) arithmetic(intId(), intId(), ADD).eval(281));
            int _jitmagicLoopLimiter4 = 0;
            for (int i = (int) asInt(0).eval(284); (boolean) relation(intId(), intId(), LT).eval(283) && _jitmagicLoopLimiter4++ < 1000; i++) {
                buffer[size++] = (char) charId().eval(282);
            }
        }
        return this;
    }

    public StrBuilder appendSeparator(final char separator) {
        if (isNotEmpty()) {
            append((char) charId().eval(285));
        }
        return this;
    }

    public StrBuilder appendSeparator(final char standard, final char defaultIfEmpty) {
        if (isNotEmpty()) {
            append((char) charId().eval(287));
        } else {
            append((char) charId().eval(286));
        }
        return this;
    }

    public StrBuilder appendSeparator(final char separator, final int loopIndex) {
        if ((boolean) relation(intId(), asInt(0), GT).eval(288)) {
            append((char) charId().eval(289));
        }
        return this;
    }

    public StrBuilder appendSeparator(final String separator) {
        return appendSeparator(refId(java.lang.String.class).eval(290), null);
    }

    public StrBuilder appendSeparator(final String separator, final int loopIndex) {
        if (refId(java.lang.String.class).eval(291) != null && (boolean) relation(intId(), asInt(0), GT).eval(292)) {
            append(refId(java.lang.String.class).eval(293));
        }
        return this;
    }

    public StrBuilder appendSeparator(final String standard, final String defaultIfEmpty) {
        final String str = isEmpty() ? refId(java.lang.String.class).eval(295) : refId(java.lang.String.class).eval(294);
        if (refId(java.lang.String.class).eval(296) != null) {
            append(refId(java.lang.String.class).eval(297));
        }
        return this;
    }

    public void appendTo(final Appendable appendable) throws IOException {
        if (appendable instanceof Writer) {
            cast(Writer.class, refId(java.lang.Appendable.class)).eval(314).write(refId(char[].class).eval(311), (int) asInt(0).eval(312), (int) intId().eval(313));
        } else if (appendable instanceof StringBuilder) {
            cast(StringBuilder.class, refId(java.lang.Appendable.class)).eval(310).append(refId(char[].class).eval(307), (int) asInt(0).eval(308), (int) intId().eval(309));
        } else if (appendable instanceof StringBuffer) {
            cast(StringBuffer.class, refId(java.lang.Appendable.class)).eval(306).append(refId(char[].class).eval(303), (int) asInt(0).eval(304), (int) intId().eval(305));
        } else if (appendable instanceof CharBuffer) {
            cast(CharBuffer.class, refId(java.lang.Appendable.class)).eval(302).put(refId(char[].class).eval(299), (int) asInt(0).eval(300), (int) intId().eval(301));
        } else {
            refId(java.lang.Appendable.class).eval(298).append(this);
        }
    }

    public StrBuilder appendWithSeparators(final Iterable<?> iterable, final String separator) {
        if (iterable != null) {
            appendWithSeparators(iterable.iterator(), refId(java.lang.String.class).eval(315));
        }
        return this;
    }

    public StrBuilder appendWithSeparators(final Iterator<?> iterator, final String separator) {
        if (iterator != null) {
            final String sep = Objects.toString(refId(java.lang.String.class).eval(316), StringUtils.EMPTY);
            int _jitmagicLoopLimiter5 = 0;
            while (iterator.hasNext() && _jitmagicLoopLimiter5++ < 1000) {
                append(iterator.next());
                if (iterator.hasNext()) {
                    append(refId(java.lang.String.class).eval(317));
                }
            }
        }
        return this;
    }

    public StrBuilder appendWithSeparators(final Object[] array, final String separator) {
        if (refId(java.lang.Object[].class).eval(318) != null && array.length > (int) asInt(0).eval(319)) {
            final String sep = Objects.toString(refId(java.lang.String.class).eval(320), StringUtils.EMPTY);
            append(refArrAccessExp(java.lang.Object.class, java.lang.Object[].class, refId(java.lang.Object[].class), asInt(0)).eval(321));
            int _jitmagicLoopLimiter6 = 0;
            for (int i = (int) asInt(1).eval(325); (int) intId().eval(324) < array.length && _jitmagicLoopLimiter6++ < 1000; i++) {
                append(refId(java.lang.String.class).eval(322));
                append(refArrAccessExp(java.lang.Object.class, java.lang.Object[].class, refId(java.lang.Object[].class), intId()).eval(323));
            }
        }
        return this;
    }

    public Reader asReader() {
        return new StrBuilderReader();
    }

    public StrTokenizer asTokenizer() {
        return new StrBuilderTokenizer();
    }

    public Writer asWriter() {
        return new StrBuilderWriter();
    }

    @Override
    public String build() {
        return toString();
    }

    public int capacity() {
        return buffer.length;
    }

    @Override
    public char charAt(final int index) {
        if ((boolean) relation(intId(), asInt(0), LT).eval(326) || (int) intId().eval(327) >= length()) {
            throw new StringIndexOutOfBoundsException((int) intId().eval(328));
        }
        return (char) charArrAccessExp(refId(char[].class), intId()).eval(329);
    }

    public StrBuilder clear() {
        size = (int) asInt(0).eval(330);
        return this;
    }

    public boolean contains(final char ch) {
        final char[] thisBuf = refId(char[].class).eval(331);
        int _jitmagicLoopLimiter7 = 0;
        for (int i = (int) asInt(0).eval(335); (int) intId().eval(334) < this.size && _jitmagicLoopLimiter7++ < 1000; i++) {
            if ((boolean) relation(cast(Integer.class, charArrAccessExp(refId(char[].class), intId())), cast(Integer.class, charId()), EQ).eval(332)) {
                return (boolean) asBool(true).eval(333);
            }
        }
        return (boolean) asBool(false).eval(336);
    }

    public boolean contains(final String str) {
        return indexOf(refId(java.lang.String.class).eval(337), (int) asInt(0).eval(338)) >= (int) asInt(0).eval(339);
    }

    public boolean contains(final StrMatcher matcher) {
        return indexOf(refId(org.apache.commons.text.StrMatcher.class).eval(340), (int) asInt(0).eval(341)) >= (int) asInt(0).eval(342);
    }

    public StrBuilder delete(final int startIndex, int endIndex) {
        endIndex = validateRange((int) intId().eval(343), (int) intId().eval(344));
        final int len = (int) arithmetic(intId(), intId(), SUB).eval(345);
        if ((boolean) relation(intId(), asInt(0), GT).eval(346)) {
            deleteImpl((int) intId().eval(347), (int) intId().eval(348), (int) intId().eval(349));
        }
        return this;
    }

    public StrBuilder deleteAll(final char ch) {
        int _jitmagicLoopLimiter8 = 0;
        for (int i = (int) asInt(0).eval(360); (boolean) relation(intId(), intId(), LT).eval(359) && _jitmagicLoopLimiter8++ < 1000; i++) {
            if ((boolean) relation(cast(Integer.class, charArrAccessExp(refId(char[].class), intId())), cast(Integer.class, charId()), EQ).eval(350)) {
                final int start = (int) intId().eval(351);
                int _jitmagicLoopLimiter9 = 0;
                while (++i < (int) intId().eval(353) && _jitmagicLoopLimiter9++ < 1000) {
                    if ((boolean) relation(cast(Integer.class, charArrAccessExp(refId(char[].class), intId())), cast(Integer.class, charId()), NE).eval(352)) {
                        break;
                    }
                }
                final int len = (int) arithmetic(intId(), intId(), SUB).eval(354);
                deleteImpl((int) intId().eval(355), (int) intId().eval(356), (int) intId().eval(357));
                i -= (int) intId().eval(358);
            }
        }
        return this;
    }

    public StrBuilder deleteAll(final String str) {
        final int len = refId(java.lang.String.class).eval(361) == null ? (int) asInt(0).eval(363) : refId(java.lang.String.class).eval(362).length();
        if ((boolean) relation(intId(), asInt(0), GT).eval(364)) {
            int index = indexOf(refId(java.lang.String.class).eval(365), (int) asInt(0).eval(366));
            int _jitmagicLoopLimiter10 = 0;
            while ((boolean) relation(intId(), asInt(0), GE).eval(372) && _jitmagicLoopLimiter10++ < 1000) {
                deleteImpl((int) intId().eval(367), (int) arithmetic(intId(), intId(), ADD).eval(368), (int) intId().eval(369));
                index = indexOf(refId(java.lang.String.class).eval(370), (int) intId().eval(371));
            }
        }
        return this;
    }

    public StrBuilder deleteAll(final StrMatcher matcher) {
        return replace(refId(org.apache.commons.text.StrMatcher.class).eval(373), null, (int) asInt(0).eval(374), (int) intId().eval(375), (int) asInt(-1).eval(376));
    }

    public StrBuilder deleteCharAt(final int index) {
        if ((boolean) logic(relation(intId(), asInt(0), LT), relation(intId(), intId(), GE), OR).eval(377)) {
            throw new StringIndexOutOfBoundsException((int) intId().eval(378));
        }
        deleteImpl((int) intId().eval(379), (int) arithmetic(intId(), asInt(1), ADD).eval(380), (int) asInt(1).eval(381));
        return this;
    }

    public StrBuilder deleteFirst(final char ch) {
        int _jitmagicLoopLimiter11 = 0;
        for (int i = (int) asInt(0).eval(387); (boolean) relation(intId(), intId(), LT).eval(386) && _jitmagicLoopLimiter11++ < 1000; i++) {
            if ((boolean) relation(cast(Integer.class, charArrAccessExp(refId(char[].class), intId())), cast(Integer.class, charId()), EQ).eval(382)) {
                deleteImpl((int) intId().eval(383), (int) arithmetic(intId(), asInt(1), ADD).eval(384), (int) asInt(1).eval(385));
                break;
            }
        }
        return this;
    }

    public StrBuilder deleteFirst(final String str) {
        final int len = refId(java.lang.String.class).eval(388) == null ? (int) asInt(0).eval(390) : refId(java.lang.String.class).eval(389).length();
        if ((boolean) relation(intId(), asInt(0), GT).eval(391)) {
            final int index = indexOf(refId(java.lang.String.class).eval(392), (int) asInt(0).eval(393));
            if ((boolean) relation(intId(), asInt(0), GE).eval(394)) {
                deleteImpl((int) intId().eval(395), (int) arithmetic(intId(), intId(), ADD).eval(396), (int) intId().eval(397));
            }
        }
        return this;
    }

    public StrBuilder deleteFirst(final StrMatcher matcher) {
        return replace(refId(org.apache.commons.text.StrMatcher.class).eval(398), null, (int) asInt(0).eval(399), (int) intId().eval(400), (int) asInt(1).eval(401));
    }

    private void deleteImpl(final int startIndex, final int endIndex, final int len) {
        System.arraycopy(refId(char[].class).eval(402), (int) intId().eval(403), refId(char[].class).eval(404), (int) intId().eval(405), (int) arithmetic(intId(), intId(), SUB).eval(406));
        size -= (int) intId().eval(407);
    }

    public boolean endsWith(final String str) {
        if (refId(java.lang.String.class).eval(408) == null) {
            return (boolean) asBool(false).eval(409);
        }
        final int len = refId(java.lang.String.class).eval(410).length();
        if ((boolean) relation(intId(), asInt(0), EQ).eval(411)) {
            return (boolean) asBool(true).eval(412);
        }
        if ((boolean) relation(intId(), intId(), GT).eval(413)) {
            return (boolean) asBool(false).eval(414);
        }
        int pos = (int) arithmetic(intId(), intId(), SUB).eval(415);
        int _jitmagicLoopLimiter12 = 0;
        for (int i = (int) asInt(0).eval(421); (boolean) relation(intId(), intId(), LT).eval(420) && _jitmagicLoopLimiter12++ < 1000; i++, pos++) {
            if ((char) charArrAccessExp(refId(char[].class), intId()).eval(416) != refId(java.lang.String.class).eval(418).charAt((int) intId().eval(417))) {
                return (boolean) asBool(false).eval(419);
            }
        }
        return (boolean) asBool(true).eval(422);
    }

    public StrBuilder ensureCapacity(final int capacity) {
        if ((int) intId().eval(423) > buffer.length) {
            final char[] old = refId(char[].class).eval(424);
            buffer = new char[(int) arithmetic(intId(), asInt(2), MUL).eval(425)];
            System.arraycopy(refId(char[].class).eval(426), (int) asInt(0).eval(427), refId(char[].class).eval(428), (int) asInt(0).eval(429), (int) intId().eval(430));
        }
        return this;
    }

    @Override
    public boolean equals(final Object obj) {
        return obj instanceof StrBuilder && equals(cast(StrBuilder.class, refId(java.lang.Object.class)).eval(431));
    }

    public boolean equals(final StrBuilder other) {
        if (this == refId(org.apache.commons.text.StrBuilder.class).eval(432)) {
            return (boolean) asBool(true).eval(433);
        }
        if (refId(org.apache.commons.text.StrBuilder.class).eval(434) == null) {
            return (boolean) asBool(false).eval(435);
        }
        if (this.size != other.size) {
            return (boolean) asBool(false).eval(436);
        }
        final char[] thisBuf = this.buffer;
        final char[] otherBuf = other.buffer;
        int _jitmagicLoopLimiter13 = 0;
        for (int i = (int) arithmetic(intId(), asInt(1), SUB).eval(440); (boolean) relation(intId(), asInt(0), GE).eval(439) && _jitmagicLoopLimiter13++ < 1000; i--) {
            if ((boolean) relation(cast(Integer.class, charArrAccessExp(refId(char[].class), intId())), cast(Integer.class, charArrAccessExp(refId(char[].class), intId())), NE).eval(437)) {
                return (boolean) asBool(false).eval(438);
            }
        }
        return (boolean) asBool(true).eval(441);
    }

    public boolean equalsIgnoreCase(final StrBuilder other) {
        if (this == refId(org.apache.commons.text.StrBuilder.class).eval(442)) {
            return (boolean) asBool(true).eval(443);
        }
        if (this.size != other.size) {
            return (boolean) asBool(false).eval(444);
        }
        final char[] thisBuf = this.buffer;
        final char[] otherBuf = other.buffer;
        int _jitmagicLoopLimiter14 = 0;
        for (int i = (int) arithmetic(intId(), asInt(1), SUB).eval(452); (boolean) relation(intId(), asInt(0), GE).eval(451) && _jitmagicLoopLimiter14++ < 1000; i--) {
            final char c1 = (char) charArrAccessExp(refId(char[].class), intId()).eval(445);
            final char c2 = (char) charArrAccessExp(refId(char[].class), intId()).eval(446);
            if ((boolean) relation(cast(Integer.class, charId()), cast(Integer.class, charId()), NE).eval(447) && Character.toUpperCase((char) charId().eval(448)) != Character.toUpperCase((char) charId().eval(449))) {
                return (boolean) asBool(false).eval(450);
            }
        }
        return (boolean) asBool(true).eval(453);
    }

    public char[] getChars(char[] destination) {
        final int len = length();
        if (refId(char[].class).eval(454) == null || destination.length < (int) intId().eval(455)) {
            destination = new char[(int) intId().eval(456)];
        }
        System.arraycopy(refId(char[].class).eval(457), (int) asInt(0).eval(458), refId(char[].class).eval(459), (int) asInt(0).eval(460), (int) intId().eval(461));
        return refId(char[].class).eval(462);
    }

    public void getChars(final int startIndex, final int endIndex, final char[] destination, final int destinationIndex) {
        if ((boolean) relation(intId(), asInt(0), LT).eval(463)) {
            throw new StringIndexOutOfBoundsException((int) intId().eval(464));
        }
        if ((boolean) relation(intId(), asInt(0), LT).eval(465) || (int) intId().eval(466) > length()) {
            throw new StringIndexOutOfBoundsException((int) intId().eval(467));
        }
        if ((boolean) relation(intId(), intId(), GT).eval(468)) {
            throw new StringIndexOutOfBoundsException("end < start");
        }
        System.arraycopy(refId(char[].class).eval(469), (int) intId().eval(470), refId(char[].class).eval(471), (int) intId().eval(472), (int) arithmetic(intId(), intId(), SUB).eval(473));
    }

    public String getNewLineText() {
        return refId(java.lang.String.class).eval(474);
    }

    public String getNullText() {
        return refId(java.lang.String.class).eval(475);
    }

    @Override
    public int hashCode() {
        final char[] buf = refId(char[].class).eval(476);
        int hash = (int) asInt(0).eval(477);
        int _jitmagicLoopLimiter15 = 0;
        for (int i = (int) arithmetic(intId(), asInt(1), SUB).eval(480); (boolean) relation(intId(), asInt(0), GE).eval(479) && _jitmagicLoopLimiter15++ < 1000; i--) {
            hash = (int) arithmetic(arithmetic(asInt(31), intId(), MUL), cast(Integer.class, charArrAccessExp(refId(char[].class), intId())), ADD).eval(478);
        }
        return (int) intId().eval(481);
    }

    public int indexOf(final char ch) {
        return indexOf((char) charId().eval(482), (int) asInt(0).eval(483));
    }

    public int indexOf(final char ch, int startIndex) {
        startIndex = Math.max((int) intId().eval(484), (int) asInt(0).eval(485));
        if ((boolean) relation(intId(), intId(), GE).eval(486)) {
            return (int) asInt(-1).eval(487);
        }
        final char[] thisBuf = refId(char[].class).eval(488);
        int _jitmagicLoopLimiter16 = 0;
        for (int i = (int) intId().eval(492); (boolean) relation(intId(), intId(), LT).eval(491) && _jitmagicLoopLimiter16++ < 1000; i++) {
            if ((boolean) relation(cast(Integer.class, charArrAccessExp(refId(char[].class), intId())), cast(Integer.class, charId()), EQ).eval(489)) {
                return (int) intId().eval(490);
            }
        }
        return (int) asInt(-1).eval(493);
    }

    public int indexOf(final String str) {
        return indexOf(refId(java.lang.String.class).eval(494), (int) asInt(0).eval(495));
    }

    public int indexOf(final String str, int startIndex) {
        startIndex = Math.max((int) intId().eval(496), (int) asInt(0).eval(497));
        if (refId(java.lang.String.class).eval(498) == null || (boolean) relation(intId(), intId(), GE).eval(499)) {
            return (int) asInt(-1).eval(500);
        }
        final int strLen = refId(java.lang.String.class).eval(501).length();
        if ((boolean) relation(intId(), asInt(1), EQ).eval(502)) {
            return indexOf(refId(java.lang.String.class).eval(504).charAt((int) asInt(0).eval(503)), (int) intId().eval(505));
        }
        if ((boolean) relation(intId(), asInt(0), EQ).eval(506)) {
            return (int) intId().eval(507);
        }
        if ((boolean) relation(intId(), intId(), GT).eval(508)) {
            return (int) asInt(-1).eval(509);
        }
        final char[] thisBuf = refId(char[].class).eval(510);
        final int len = (int) arithmetic(arithmetic(intId(), intId(), SUB), asInt(1), ADD).eval(511);
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
        return (int) asInt(-1).eval(512);
    }

    public int indexOf(final StrMatcher matcher) {
        return indexOf(refId(org.apache.commons.text.StrMatcher.class).eval(513), (int) asInt(0).eval(514));
    }

    public int indexOf(final StrMatcher matcher, int startIndex) {
        startIndex = Math.max((int) intId().eval(515), (int) asInt(0).eval(516));
        if (refId(org.apache.commons.text.StrMatcher.class).eval(517) == null || (boolean) relation(intId(), intId(), GE).eval(518)) {
            return (int) asInt(-1).eval(519);
        }
        final int len = (int) intId().eval(520);
        final char[] buf = refId(char[].class).eval(521);
        int _jitmagicLoopLimiter19 = 0;
        for (int i = (int) intId().eval(530); (boolean) relation(intId(), intId(), LT).eval(529) && _jitmagicLoopLimiter19++ < 1000; i++) {
            if (refId(org.apache.commons.text.StrMatcher.class).eval(526).isMatch(refId(char[].class).eval(522), (int) intId().eval(523), (int) intId().eval(524), (int) intId().eval(525)) > (int) asInt(0).eval(527)) {
                return (int) intId().eval(528);
            }
        }
        return (int) asInt(-1).eval(531);
    }

    public StrBuilder insert(int index, final boolean value) {
        validateIndex((int) intId().eval(532));
        if ((boolean) boolId().eval(533)) {
            ensureCapacity((int) arithmetic(intId(), asInt(4), ADD).eval(546));
            System.arraycopy(refId(char[].class).eval(547), (int) intId().eval(548), refId(char[].class).eval(549), (int) arithmetic(intId(), asInt(4), ADD).eval(550), (int) arithmetic(intId(), intId(), SUB).eval(551));
            buffer[index++] = (char) asChar('t').eval(552);
            buffer[index++] = (char) asChar('r').eval(553);
            buffer[index++] = (char) asChar('u').eval(554);
            buffer[index] = (char) asChar('e').eval(555);
            size += (int) asInt(4).eval(556);
        } else {
            ensureCapacity((int) arithmetic(intId(), asInt(5), ADD).eval(534));
            System.arraycopy(refId(char[].class).eval(535), (int) intId().eval(536), refId(char[].class).eval(537), (int) arithmetic(intId(), asInt(5), ADD).eval(538), (int) arithmetic(intId(), intId(), SUB).eval(539));
            buffer[index++] = (char) asChar('f').eval(540);
            buffer[index++] = (char) asChar('a').eval(541);
            buffer[index++] = (char) asChar('l').eval(542);
            buffer[index++] = (char) asChar('s').eval(543);
            buffer[index] = (char) asChar('e').eval(544);
            size += (int) asInt(5).eval(545);
        }
        return this;
    }

    public StrBuilder insert(final int index, final char value) {
        validateIndex((int) intId().eval(557));
        ensureCapacity((int) arithmetic(intId(), asInt(1), ADD).eval(558));
        System.arraycopy(refId(char[].class).eval(559), (int) intId().eval(560), refId(char[].class).eval(561), (int) arithmetic(intId(), asInt(1), ADD).eval(562), (int) arithmetic(intId(), intId(), SUB).eval(563));
        buffer[index] = (char) charId().eval(564);
        size++;
        return this;
    }

    public StrBuilder insert(final int index, final char[] chars) {
        validateIndex((int) intId().eval(565));
        if (refId(char[].class).eval(566) == null) {
            return insert((int) intId().eval(567), refId(java.lang.String.class).eval(568));
        }
        final int len = chars.length;
        if ((boolean) relation(intId(), asInt(0), GT).eval(569)) {
            ensureCapacity((int) arithmetic(intId(), intId(), ADD).eval(570));
            System.arraycopy(refId(char[].class).eval(571), (int) intId().eval(572), refId(char[].class).eval(573), (int) arithmetic(intId(), intId(), ADD).eval(574), (int) arithmetic(intId(), intId(), SUB).eval(575));
            System.arraycopy(refId(char[].class).eval(576), (int) asInt(0).eval(577), refId(char[].class).eval(578), (int) intId().eval(579), (int) intId().eval(580));
            size += (int) intId().eval(581);
        }
        return this;
    }

    public StrBuilder insert(final int index, final char[] chars, final int offset, final int length) {
        validateIndex((int) intId().eval(582));
        if (refId(char[].class).eval(583) == null) {
            return insert((int) intId().eval(584), refId(java.lang.String.class).eval(585));
        }
        if ((boolean) relation(intId(), asInt(0), LT).eval(586) || (int) intId().eval(587) > chars.length) {
            throw new StringIndexOutOfBoundsException("Invalid offset: " + (int) intId().eval(588));
        }
        if ((boolean) relation(intId(), asInt(0), LT).eval(589) || (int) arithmetic(intId(), intId(), ADD).eval(590) > chars.length) {
            throw new StringIndexOutOfBoundsException("Invalid length: " + (int) intId().eval(591));
        }
        if ((boolean) relation(intId(), asInt(0), GT).eval(592)) {
            ensureCapacity((int) arithmetic(intId(), intId(), ADD).eval(593));
            System.arraycopy(refId(char[].class).eval(594), (int) intId().eval(595), refId(char[].class).eval(596), (int) arithmetic(intId(), intId(), ADD).eval(597), (int) arithmetic(intId(), intId(), SUB).eval(598));
            System.arraycopy(refId(char[].class).eval(599), (int) intId().eval(600), refId(char[].class).eval(601), (int) intId().eval(602), (int) intId().eval(603));
            size += (int) intId().eval(604);
        }
        return this;
    }

    public StrBuilder insert(final int index, final double value) {
        return insert((int) intId().eval(605), String.valueOf((double) doubleId().eval(606)));
    }

    public StrBuilder insert(final int index, final float value) {
        return insert((int) intId().eval(607), String.valueOf((float) floatId().eval(608)));
    }

    public StrBuilder insert(final int index, final int value) {
        return insert((int) intId().eval(609), String.valueOf((int) intId().eval(610)));
    }

    public StrBuilder insert(final int index, final long value) {
        return insert((int) intId().eval(611), String.valueOf((long) longId().eval(612)));
    }

    public StrBuilder insert(final int index, final Object obj) {
        if (refId(java.lang.Object.class).eval(613) == null) {
            return insert((int) intId().eval(614), refId(java.lang.String.class).eval(615));
        }
        return insert((int) intId().eval(616), refId(java.lang.Object.class).eval(617).toString());
    }

    public StrBuilder insert(final int index, String str) {
        validateIndex((int) intId().eval(618));
        if (refId(java.lang.String.class).eval(619) == null) {
            str = refId(java.lang.String.class).eval(620);
        }
        if (refId(java.lang.String.class).eval(621) != null) {
            final int strLen = refId(java.lang.String.class).eval(622).length();
            if ((boolean) relation(intId(), asInt(0), GT).eval(623)) {
                final int newSize = (int) arithmetic(intId(), intId(), ADD).eval(624);
                ensureCapacity((int) intId().eval(625));
                System.arraycopy(refId(char[].class).eval(626), (int) intId().eval(627), refId(char[].class).eval(628), (int) arithmetic(intId(), intId(), ADD).eval(629), (int) arithmetic(intId(), intId(), SUB).eval(630));
                size = (int) intId().eval(631);
                refId(java.lang.String.class).eval(636).getChars((int) asInt(0).eval(632), (int) intId().eval(633), refId(char[].class).eval(634), (int) intId().eval(635));
            }
        }
        return this;
    }

    public boolean isEmpty() {
        return (boolean) relation(intId(), asInt(0), EQ).eval(637);
    }

    public boolean isNotEmpty() {
        return (boolean) relation(intId(), asInt(0), GT).eval(638);
    }

    public int lastIndexOf(final char ch) {
        return lastIndexOf((char) charId().eval(639), (int) arithmetic(intId(), asInt(1), SUB).eval(640));
    }

    public int lastIndexOf(final char ch, int startIndex) {
        startIndex = (boolean) (CAPACITY >= size) ? (int) (size - 1) : (int) intId().eval(642);
        if ((boolean) (CAPACITY < 0)) {
            return (int) asInt(-1).eval(645);
        }
        int _jitmagicLoopLimiter20 = 0;
        for (int i = (int) size; (boolean) (_jitmagicLoopLimiter20 >= 0) && _jitmagicLoopLimiter20++ < 1000; i--) {
            if ((boolean) (((int) buffer[i]) == ((int) ch))) {
                return (int) intId().eval(647);
            }
        }
        return (int) asInt(-1).eval(650);
    }

    public int lastIndexOf(final String str) {
        return lastIndexOf(refId(java.lang.String.class).eval(651), (int) arithmetic(intId(), asInt(1), SUB).eval(652));
    }

    public int lastIndexOf(final String str, int startIndex) {
        startIndex = (boolean) relation(intId(), intId(), GE).eval(653) ? (int) arithmetic(intId(), asInt(1), SUB).eval(655) : (int) intId().eval(654);
        if (refId(java.lang.String.class).eval(656) == null || (boolean) relation(intId(), asInt(0), LT).eval(657)) {
            return (int) asInt(-1).eval(658);
        }
        final int strLen = refId(java.lang.String.class).eval(659).length();
        if ((boolean) logic(relation(intId(), asInt(0), GT), relation(intId(), intId(), LE), AND).eval(660)) {
            if ((boolean) relation(intId(), asInt(1), EQ).eval(663)) {
                return lastIndexOf(refId(java.lang.String.class).eval(665).charAt((int) asInt(0).eval(664)), (int) intId().eval(666));
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
        } else if ((boolean) relation(intId(), asInt(0), EQ).eval(661)) {
            return (int) intId().eval(662);
        }
        return (int) asInt(-1).eval(667);
    }

    public int lastIndexOf(final StrMatcher matcher) {
        return lastIndexOf(refId(org.apache.commons.text.StrMatcher.class).eval(668), (int) intId().eval(669));
    }

    public int lastIndexOf(final StrMatcher matcher, int startIndex) {
        startIndex = (boolean) relation(intId(), intId(), GE).eval(670) ? (int) arithmetic(intId(), asInt(1), SUB).eval(672) : (int) intId().eval(671);
        if (refId(org.apache.commons.text.StrMatcher.class).eval(673) == null || (boolean) relation(intId(), asInt(0), LT).eval(674)) {
            return (int) asInt(-1).eval(675);
        }
        final char[] buf = refId(char[].class).eval(676);
        final int endIndex = (int) arithmetic(intId(), asInt(1), ADD).eval(677);
        int _jitmagicLoopLimiter23 = 0;
        for (int i = (int) intId().eval(686); (boolean) relation(intId(), asInt(0), GE).eval(685) && _jitmagicLoopLimiter23++ < 1000; i--) {
            if (refId(org.apache.commons.text.StrMatcher.class).eval(682).isMatch(refId(char[].class).eval(678), (int) intId().eval(679), (int) asInt(0).eval(680), (int) intId().eval(681)) > (int) asInt(0).eval(683)) {
                return (int) intId().eval(684);
            }
        }
        return (int) asInt(-1).eval(687);
    }

    public String leftString(final int length) {
        if ((boolean) relation(intId(), asInt(0), LE).eval(688)) {
            return StringUtils.EMPTY;
        }
        if ((boolean) relation(intId(), intId(), GE).eval(689)) {
            return new String(refId(char[].class).eval(690), (int) asInt(0).eval(691), (int) intId().eval(692));
        }
        return new String(refId(char[].class).eval(693), (int) asInt(0).eval(694), (int) intId().eval(695));
    }

    @Override
    public int length() {
        return (int) intId().eval(696);
    }

    public String midString(int index, final int length) {
        if ((boolean) relation(intId(), asInt(0), LT).eval(697)) {
            index = (int) asInt(0).eval(698);
        }
        if ((boolean) logic(relation(intId(), asInt(0), LE), relation(intId(), intId(), GE), OR).eval(699)) {
            return StringUtils.EMPTY;
        }
        if ((boolean) relation(intId(), arithmetic(intId(), intId(), ADD), LE).eval(700)) {
            return new String(refId(char[].class).eval(701), (int) intId().eval(702), (int) arithmetic(intId(), intId(), SUB).eval(703));
        }
        return new String(refId(char[].class).eval(704), (int) intId().eval(705), (int) intId().eval(706));
    }

    public StrBuilder minimizeCapacity() {
        if (buffer.length > length()) {
            final char[] old = refId(char[].class).eval(707);
            buffer = new char[length()];
            System.arraycopy(refId(char[].class).eval(708), (int) asInt(0).eval(709), refId(char[].class).eval(710), (int) asInt(0).eval(711), (int) intId().eval(712));
        }
        return this;
    }

    public int readFrom(final Readable readable) throws IOException {
        final int oldSize = (int) intId().eval(713);
        if (readable instanceof Reader) {
            final Reader r = cast(Reader.class, refId(java.lang.Readable.class)).eval(731);
            ensureCapacity((int) arithmetic(intId(), asInt(1), ADD).eval(732));
            int read;
            int _jitmagicLoopLimiter24 = 0;
            while ((read = refId(java.io.Reader.class).eval(738).read(refId(char[].class).eval(735), (int) intId().eval(736), buffer.length - (int) intId().eval(737))) != (int) asInt(-1).eval(739) && _jitmagicLoopLimiter24++ < 1000) {
                size += (int) intId().eval(733);
                ensureCapacity((int) arithmetic(intId(), asInt(1), ADD).eval(734));
            }
        } else if (readable instanceof CharBuffer) {
            final CharBuffer cb = cast(CharBuffer.class, refId(java.lang.Readable.class)).eval(723);
            final int remaining = refId(java.nio.CharBuffer.class).eval(724).remaining();
            ensureCapacity((int) arithmetic(intId(), intId(), ADD).eval(725));
            refId(java.nio.CharBuffer.class).eval(729).get(refId(char[].class).eval(726), (int) intId().eval(727), (int) intId().eval(728));
            size += (int) intId().eval(730);
        } else {
            int _jitmagicLoopLimiter25 = 0;
            while ((boolean) asBool(true).eval(722) && _jitmagicLoopLimiter25++ < 1000) {
                ensureCapacity((int) arithmetic(intId(), asInt(1), ADD).eval(714));
                final CharBuffer buf = CharBuffer.wrap(refId(char[].class).eval(715), (int) intId().eval(716), buffer.length - (int) intId().eval(717));
                final int read = refId(java.lang.Readable.class).eval(719).read(refId(java.nio.CharBuffer.class).eval(718));
                if ((boolean) relation(intId(), asInt(-1), EQ).eval(720)) {
                    break;
                }
                size += (int) intId().eval(721);
            }
        }
        return (int) arithmetic(intId(), intId(), SUB).eval(740);
    }

    public StrBuilder replace(final int startIndex, int endIndex, final String replaceStr) {
        endIndex = validateRange((int) intId().eval(741), (int) intId().eval(742));
        final int insertLen = refId(java.lang.String.class).eval(743) == null ? (int) asInt(0).eval(745) : refId(java.lang.String.class).eval(744).length();
        replaceImpl((int) intId().eval(746), (int) intId().eval(747), (int) arithmetic(intId(), intId(), SUB).eval(748), refId(java.lang.String.class).eval(749), (int) intId().eval(750));
        return this;
    }

    public StrBuilder replace(final StrMatcher matcher, final String replaceStr, final int startIndex, int endIndex, final int replaceCount) {
        endIndex = validateRange((int) intId().eval(751), (int) intId().eval(752));
        return replaceImpl(refId(org.apache.commons.text.StrMatcher.class).eval(753), refId(java.lang.String.class).eval(754), (int) intId().eval(755), (int) intId().eval(756), (int) intId().eval(757));
    }

    public StrBuilder replaceAll(final char search, final char replace) {
        if ((boolean) relation(cast(Integer.class, charId()), cast(Integer.class, charId()), NE).eval(758)) {
            int _jitmagicLoopLimiter26 = 0;
            for (int i = (int) asInt(0).eval(762); (boolean) relation(intId(), intId(), LT).eval(761) && _jitmagicLoopLimiter26++ < 1000; i++) {
                if ((boolean) relation(cast(Integer.class, charArrAccessExp(refId(char[].class), intId())), cast(Integer.class, charId()), EQ).eval(759)) {
                    buffer[i] = (char) charId().eval(760);
                }
            }
        }
        return this;
    }

    public StrBuilder replaceAll(final String searchStr, final String replaceStr) {
        final int searchLen = refId(java.lang.String.class).eval(763) == null ? (int) asInt(0).eval(765) : refId(java.lang.String.class).eval(764).length();
        if ((boolean) relation(intId(), asInt(0), GT).eval(766)) {
            final int replaceLen = refId(java.lang.String.class).eval(767) == null ? (int) asInt(0).eval(769) : refId(java.lang.String.class).eval(768).length();
            int index = indexOf(refId(java.lang.String.class).eval(770), (int) asInt(0).eval(771));
            int _jitmagicLoopLimiter27 = 0;
            while ((boolean) relation(intId(), asInt(0), GE).eval(779) && _jitmagicLoopLimiter27++ < 1000) {
                replaceImpl((int) intId().eval(772), (int) arithmetic(intId(), intId(), ADD).eval(773), (int) intId().eval(774), refId(java.lang.String.class).eval(775), (int) intId().eval(776));
                index = indexOf(refId(java.lang.String.class).eval(777), (int) arithmetic(intId(), intId(), ADD).eval(778));
            }
        }
        return this;
    }

    public StrBuilder replaceAll(final StrMatcher matcher, final String replaceStr) {
        return replace(refId(org.apache.commons.text.StrMatcher.class).eval(780), refId(java.lang.String.class).eval(781), (int) asInt(0).eval(782), (int) intId().eval(783), (int) asInt(-1).eval(784));
    }

    public StrBuilder replaceFirst(final char search, final char replace) {
        if ((boolean) relation(cast(Integer.class, charId()), cast(Integer.class, charId()), NE).eval(785)) {
            int _jitmagicLoopLimiter28 = 0;
            for (int i = (int) asInt(0).eval(789); (boolean) relation(intId(), intId(), LT).eval(788) && _jitmagicLoopLimiter28++ < 1000; i++) {
                if ((boolean) relation(cast(Integer.class, charArrAccessExp(refId(char[].class), intId())), cast(Integer.class, charId()), EQ).eval(786)) {
                    buffer[i] = (char) charId().eval(787);
                    break;
                }
            }
        }
        return this;
    }

    public StrBuilder replaceFirst(final String searchStr, final String replaceStr) {
        final int searchLen = refId(java.lang.String.class).eval(790) == null ? (int) asInt(0).eval(792) : refId(java.lang.String.class).eval(791).length();
        if ((boolean) relation(intId(), asInt(0), GT).eval(793)) {
            final int index = indexOf(refId(java.lang.String.class).eval(794), (int) asInt(0).eval(795));
            if ((boolean) relation(intId(), asInt(0), GE).eval(796)) {
                final int replaceLen = refId(java.lang.String.class).eval(797) == null ? (int) asInt(0).eval(799) : refId(java.lang.String.class).eval(798).length();
                replaceImpl((int) intId().eval(800), (int) arithmetic(intId(), intId(), ADD).eval(801), (int) intId().eval(802), refId(java.lang.String.class).eval(803), (int) intId().eval(804));
            }
        }
        return this;
    }

    public StrBuilder replaceFirst(final StrMatcher matcher, final String replaceStr) {
        return replace(refId(org.apache.commons.text.StrMatcher.class).eval(805), refId(java.lang.String.class).eval(806), (int) asInt(0).eval(807), (int) intId().eval(808), (int) asInt(1).eval(809));
    }

    private void replaceImpl(final int startIndex, final int endIndex, final int removeLen, final String insertStr, final int insertLen) {
        final int newSize = (int) arithmetic(arithmetic(intId(), intId(), SUB), intId(), ADD).eval(810);
        if ((boolean) relation(intId(), intId(), NE).eval(811)) {
            ensureCapacity((int) intId().eval(812));
            System.arraycopy(refId(char[].class).eval(813), (int) intId().eval(814), refId(char[].class).eval(815), (int) arithmetic(intId(), intId(), ADD).eval(816), (int) arithmetic(intId(), intId(), SUB).eval(817));
            size = (int) intId().eval(818);
        }
        if ((boolean) relation(intId(), asInt(0), GT).eval(819)) {
            refId(java.lang.String.class).eval(824).getChars((int) asInt(0).eval(820), (int) intId().eval(821), refId(char[].class).eval(822), (int) intId().eval(823));
        }
    }

    private StrBuilder replaceImpl(final StrMatcher matcher, final String replaceStr, final int from, int to, int replaceCount) {
        if (refId(org.apache.commons.text.StrMatcher.class).eval(825) == null || (boolean) relation(intId(), asInt(0), EQ).eval(826)) {
            return this;
        }
        final int replaceLen = refId(java.lang.String.class).eval(827) == null ? (int) asInt(0).eval(829) : refId(java.lang.String.class).eval(828).length();
        int _jitmagicLoopLimiter29 = 0;
        for (int i = (int) intId().eval(846); (boolean) logic(relation(intId(), intId(), LT), relation(intId(), asInt(0), NE), AND).eval(845) && _jitmagicLoopLimiter29++ < 1000; i++) {
            final char[] buf = refId(char[].class).eval(830);
            final int removeLen = refId(org.apache.commons.text.StrMatcher.class).eval(835).isMatch(refId(char[].class).eval(831), (int) intId().eval(832), (int) intId().eval(833), (int) intId().eval(834));
            if ((boolean) relation(intId(), asInt(0), GT).eval(836)) {
                replaceImpl((int) intId().eval(837), (int) arithmetic(intId(), intId(), ADD).eval(838), (int) intId().eval(839), refId(java.lang.String.class).eval(840), (int) intId().eval(841));
                to = (int) arithmetic(arithmetic(intId(), intId(), SUB), intId(), ADD).eval(842);
                i = (int) arithmetic(arithmetic(intId(), intId(), ADD), asInt(1), SUB).eval(843);
                if ((boolean) relation(intId(), asInt(0), GT).eval(844)) {
                    replaceCount--;
                }
            }
        }
        return this;
    }

    public StrBuilder reverse() {
        if ((boolean) relation(intId(), asInt(0), EQ).eval(847)) {
            return this;
        }
        final int half = (int) arithmetic(intId(), asInt(2), DIV).eval(848);
        final char[] buf = refId(char[].class).eval(849);
        int _jitmagicLoopLimiter30 = 0;
        for (int leftIdx = (int) asInt(0).eval(854), rightIdx = (int) arithmetic(intId(), asInt(1), SUB).eval(855); (boolean) relation(intId(), intId(), LT).eval(853) && _jitmagicLoopLimiter30++ < 1000; leftIdx++, rightIdx--) {
            final char swap = (char) charArrAccessExp(refId(char[].class), intId()).eval(850);
            buf[leftIdx] = (char) charArrAccessExp(refId(char[].class), intId()).eval(851);
            buf[rightIdx] = (char) charId().eval(852);
        }
        return this;
    }

    public String rightString(final int length) {
        if ((boolean) relation(intId(), asInt(0), LE).eval(856)) {
            return StringUtils.EMPTY;
        }
        if ((boolean) relation(intId(), intId(), GE).eval(857)) {
            return new String(refId(char[].class).eval(858), (int) asInt(0).eval(859), (int) intId().eval(860));
        }
        return new String(refId(char[].class).eval(861), (int) arithmetic(intId(), intId(), SUB).eval(862), (int) intId().eval(863));
    }

    public StrBuilder setCharAt(final int index, final char ch) {
        if ((boolean) relation(intId(), asInt(0), LT).eval(864) || (int) intId().eval(865) >= length()) {
            throw new StringIndexOutOfBoundsException((int) intId().eval(866));
        }
        buffer[index] = (char) charId().eval(867);
        return this;
    }

    public StrBuilder setLength(final int length) {
        if ((boolean) relation(intId(), asInt(0), LT).eval(868)) {
            throw new StringIndexOutOfBoundsException((int) intId().eval(869));
        }
        if ((boolean) relation(intId(), intId(), LT).eval(870)) {
            size = (int) intId().eval(878);
        } else if ((boolean) relation(intId(), intId(), GT).eval(871)) {
            ensureCapacity((int) intId().eval(872));
            final int oldEnd = (int) intId().eval(873);
            size = (int) intId().eval(874);
            int _jitmagicLoopLimiter31 = 0;
            for (int i = (int) intId().eval(877); (boolean) relation(intId(), intId(), LT).eval(876) && _jitmagicLoopLimiter31++ < 1000; i++) {
                buffer[i] = (char) asChar('\0').eval(875);
            }
        }
        return this;
    }

    public StrBuilder setNewLineText(final String newLine) {
        this.newLine = refId(java.lang.String.class).eval(879);
        return this;
    }

    public StrBuilder setNullText(String nullText) {
        if (refId(java.lang.String.class).eval(880) != null && refId(java.lang.String.class).eval(881).isEmpty()) {
            nullText = null;
        }
        this.nullText = refId(java.lang.String.class).eval(882);
        return this;
    }

    public int size() {
        return (int) intId().eval(883);
    }

    public boolean startsWith(final String str) {
        if (refId(java.lang.String.class).eval(884) == null) {
            return (boolean) asBool(false).eval(885);
        }
        final int len = refId(java.lang.String.class).eval(886).length();
        if ((boolean) relation(intId(), asInt(0), EQ).eval(887)) {
            return (boolean) asBool(true).eval(888);
        }
        if ((boolean) relation(intId(), intId(), GT).eval(889)) {
            return (boolean) asBool(false).eval(890);
        }
        int _jitmagicLoopLimiter32 = 0;
        for (int i = (int) asInt(0).eval(896); (boolean) relation(intId(), intId(), LT).eval(895) && _jitmagicLoopLimiter32++ < 1000; i++) {
            if ((char) charArrAccessExp(refId(char[].class), intId()).eval(891) != refId(java.lang.String.class).eval(893).charAt((int) intId().eval(892))) {
                return (boolean) asBool(false).eval(894);
            }
        }
        return (boolean) asBool(true).eval(897);
    }

    @Override
    public CharSequence subSequence(final int startIndex, final int endIndex) {
        if ((boolean) relation(intId(), asInt(0), LT).eval(898)) {
            throw new StringIndexOutOfBoundsException((int) intId().eval(899));
        }
        if ((boolean) relation(intId(), intId(), GT).eval(900)) {
            throw new StringIndexOutOfBoundsException((int) intId().eval(901));
        }
        if ((boolean) relation(intId(), intId(), GT).eval(902)) {
            throw new StringIndexOutOfBoundsException((int) arithmetic(intId(), intId(), SUB).eval(903));
        }
        return substring((int) intId().eval(904), (int) intId().eval(905));
    }

    public String substring(final int start) {
        return substring((int) intId().eval(906), (int) intId().eval(907));
    }

    public String substring(final int startIndex, int endIndex) {
        endIndex = validateRange((int) intId().eval(908), (int) intId().eval(909));
        return new String(refId(char[].class).eval(910), (int) intId().eval(911), (int) arithmetic(intId(), intId(), SUB).eval(912));
    }

    public char[] toCharArray() {
        return (boolean) relation(intId(), asInt(0), EQ).eval(913) ? ArrayUtils.EMPTY_CHAR_ARRAY : Arrays.copyOf(refId(char[].class).eval(914), (int) intId().eval(915));
    }

    public char[] toCharArray(final int startIndex, int endIndex) {
        endIndex = validateRange((int) intId().eval(916), (int) intId().eval(917));
        final int len = (int) arithmetic(intId(), intId(), SUB).eval(918);
        if ((boolean) relation(intId(), asInt(0), EQ).eval(919)) {
            return ArrayUtils.EMPTY_CHAR_ARRAY;
        }
        final char[] chars = new char[(int) intId().eval(920)];
        System.arraycopy(refId(char[].class).eval(921), (int) intId().eval(922), refId(char[].class).eval(923), (int) asInt(0).eval(924), (int) intId().eval(925));
        return refId(char[].class).eval(926);
    }

    @Override
    public String toString() {
        return new String(refId(char[].class).eval(927), (int) asInt(0).eval(928), (int) intId().eval(929));
    }

    public StringBuffer toStringBuffer() {
        return new StringBuffer((int) intId().eval(933)).append(refId(char[].class).eval(930), (int) asInt(0).eval(931), (int) intId().eval(932));
    }

    public StringBuilder toStringBuilder() {
        return new StringBuilder((int) intId().eval(937)).append(refId(char[].class).eval(934), (int) asInt(0).eval(935), (int) intId().eval(936));
    }

    public StrBuilder trim() {
        if ((boolean) relation(intId(), asInt(0), EQ).eval(938)) {
            return this;
        }
        int len = (int) intId().eval(939);
        final char[] buf = refId(char[].class).eval(940);
        int pos = (int) asInt(0).eval(941);
        int _jitmagicLoopLimiter33 = 0;
        while ((boolean) logic(relation(intId(), intId(), LT), relation(cast(Integer.class, charArrAccessExp(refId(char[].class), intId())), cast(Integer.class, asChar(' ')), LE), AND).eval(942) && _jitmagicLoopLimiter33++ < 1000) {
            pos++;
        }
        int _jitmagicLoopLimiter34 = 0;
        while ((boolean) logic(relation(intId(), intId(), LT), relation(cast(Integer.class, charArrAccessExp(refId(char[].class), arithmetic(intId(), asInt(1), SUB))), cast(Integer.class, asChar(' ')), LE), AND).eval(943) && _jitmagicLoopLimiter34++ < 1000) {
            len--;
        }
        if ((boolean) relation(intId(), intId(), LT).eval(944)) {
            delete((int) intId().eval(945), (int) intId().eval(946));
        }
        if ((boolean) relation(intId(), asInt(0), GT).eval(947)) {
            delete((int) asInt(0).eval(948), (int) intId().eval(949));
        }
        return this;
    }

    protected void validateIndex(final int index) {
        if ((boolean) logic(relation(intId(), asInt(0), LT), relation(intId(), intId(), GT), OR).eval(950)) {
            throw new StringIndexOutOfBoundsException((int) intId().eval(951));
        }
    }

    protected int validateRange(final int startIndex, int endIndex) {
        if ((boolean) relation(intId(), asInt(0), LT).eval(952)) {
            throw new StringIndexOutOfBoundsException((int) intId().eval(953));
        }
        if ((boolean) relation(intId(), intId(), GT).eval(954)) {
            endIndex = (int) intId().eval(955);
        }
        if ((boolean) relation(intId(), intId(), GT).eval(956)) {
            throw new StringIndexOutOfBoundsException("end < start");
        }
        return (int) intId().eval(957);
    }

    @jattack.annotation.Arguments
    public static Object[] _jitmagicArgs() throws Throwable {
        org.apache.commons.text.StrBuilder strBuilder1 = new org.apache.commons.text.StrBuilder(5);
        return new Object[] { strBuilder1, '-', 36 };
    }

    public static long main0(String[] args) {
        org.csutil.Helper.parseArgs(args);
        int N = Math.min(org.csutil.Helper.nItrs, 100000);
        WrappedChecksum cs = new WrappedChecksum(true);
        try {
            Object[] eArgs = _jitmagicArgs();
            cs.update(eArgs);
            StrBuilder rcvr = (StrBuilder) eArgs[0];
            char eArg1 = (char) eArgs[1];
            int eArg2 = (int) eArgs[2];
            for (int i = 0; i < N; ++i) {
                try {
                    cs.update(rcvr.lastIndexOf(eArg1, eArg2));
                } catch (Throwable e) {
                    if (e instanceof jattack.exception.InvokedFromNotDriverException) {
                        throw (jattack.exception.InvokedFromNotDriverException) e;
                    }
                    cs.update(e.getClass().getName());
                }
            }
        } catch (Throwable e) {
            if (e instanceof jattack.exception.InvokedFromNotDriverException) {
                throw (jattack.exception.InvokedFromNotDriverException) e;
            }
            cs.update(e.getClass().getName());
        }
        cs.updateStaticFieldsOfClass(StrBuilder.class);
        return cs.getValue();
    }

    public static void main(String[] args) {
        org.csutil.Helper.write(main0(args));
    }
}
