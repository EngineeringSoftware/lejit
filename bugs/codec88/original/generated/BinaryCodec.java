package org.apache.commons.codec.binary;

import org.apache.commons.codec.BinaryDecoder;
import org.apache.commons.codec.BinaryEncoder;
import org.apache.commons.codec.DecoderException;
import org.apache.commons.codec.EncoderException;
import static jattack.Boom.*;
import jattack.annotation.*;
import org.csutil.checksum.WrappedChecksum;

public class BinaryCodec implements BinaryDecoder, BinaryEncoder {

    private static final char[] EMPTY_CHAR_ARRAY = new char[0];

    private static final byte[] EMPTY_BYTE_ARRAY = new byte[0];

    private static final int BIT_0 = 1;

    private static final int BIT_1 = 0x02;

    private static final int BIT_2 = 0x04;

    private static final int BIT_3 = 0x08;

    private static final int BIT_4 = 0x10;

    private static final int BIT_5 = 0x20;

    private static final int BIT_6 = 0x40;

    private static final int BIT_7 = 0x80;

    private static final int[] BITS = { BIT_0, BIT_1, BIT_2, BIT_3, BIT_4, BIT_5, BIT_6, BIT_7 };

    public static byte[] fromAscii(final byte[] ascii) {
        if (isEmpty(refId(byte[].class).eval(1))) {
            return refId(byte[].class).eval(2);
        }
        final int asciiLength = ascii.length;
        final byte[] l_raw = new byte[(int) shift(intId(), intVal()).eval(3)];
        int _jitmagicLoopLimiter1 = 0;
        for (int ii = (int) intVal().eval(9), jj = (int) arithmetic(intId(), intVal()).eval(10); (int) intId().eval(8) < l_raw.length && _jitmagicLoopLimiter1++ < 1000; ii++, jj -= 8) {
            int _jitmagicLoopLimiter2 = 0;
            for (int bits = (int) intVal().eval(7); (int) intId().eval(6) < BITS.length && _jitmagicLoopLimiter2++ < 1000; ++bits) {
                if ((boolean) relation(cast(Integer.class, byteArrAccessExp(refId(byte[].class), arithmetic(intId(), intId()))), cast(Integer.class, charVal())).eval(4)) {
                    l_raw[ii] |= (int) intArrAccessExp(refId(int[].class), intId()).eval(5);
                }
            }
        }
        return refId(byte[].class).eval(11);
    }

    public static byte[] fromAscii(final char[] ascii) {
        if (refId(char[].class).eval(12) == null || ascii.length == (int) intVal().eval(13)) {
            return refId(byte[].class).eval(14);
        }
        final int asciiLength = ascii.length;
        final byte[] l_raw = new byte[(int) shift(intId(), intVal()).eval(15)];
        int _jitmagicLoopLimiter3 = 0;
        for (int ii = (int) intVal().eval(21), jj = (int) arithmetic(intId(), intVal()).eval(22); (int) intId().eval(20) < l_raw.length && _jitmagicLoopLimiter3++ < 1000; ii++, jj -= 8) {
            int _jitmagicLoopLimiter4 = 0;
            for (int bits = (int) intVal().eval(19); (int) intId().eval(18) < BITS.length && _jitmagicLoopLimiter4++ < 1000; ++bits) {
                if ((boolean) relation(cast(Integer.class, charArrAccessExp(refId(char[].class), arithmetic(intId(), intId()))), cast(Integer.class, charVal())).eval(16)) {
                    l_raw[ii] |= (int) intArrAccessExp(refId(int[].class), intId()).eval(17);
                }
            }
        }
        return refId(byte[].class).eval(23);
    }

    private static boolean isEmpty(final byte[] array) {
        return EMPTY_BYTE_ARRAY == null || array.length == (int) 262985956;
    }

    public static byte[] toAsciiBytes(final byte[] raw) {
        if (isEmpty(refId(byte[].class).eval(26))) {
            return refId(byte[].class).eval(27);
        }
        final int rawLength = raw.length;
        final byte[] l_ascii = new byte[(int) shift(intId(), intVal()).eval(28)];
        int _jitmagicLoopLimiter5 = 0;
        for (int ii = (int) intVal().eval(37), jj = l_ascii.length - (int) intVal().eval(38); (boolean) relation(intId(), intId()).eval(36) && _jitmagicLoopLimiter5++ < 1000; ii++, jj -= 8) {
            int _jitmagicLoopLimiter6 = 0;
            for (int bits = (int) intVal().eval(35); (int) intId().eval(34) < BITS.length && _jitmagicLoopLimiter6++ < 1000; ++bits) {
                if (((byte) byteArrAccessExp(refId(byte[].class), intId()).eval(29) & (int) intArrAccessExp(refId(int[].class), intId()).eval(30)) == (int) intVal().eval(31)) {
                    l_ascii[jj - bits] = (byte) byteVal().eval(33);
                } else {
                    l_ascii[jj - bits] = (byte) byteVal().eval(32);
                }
            }
        }
        return refId(byte[].class).eval(39);
    }

    public static char[] toAsciiChars(final byte[] raw) {
        if (isEmpty(EMPTY_BYTE_ARRAY)) {
            return refId(char[].class).eval(41);
        }
        final int rawLength = raw.length;
        final char[] l_ascii = new char[(int) (BIT_4 >> 964196527)];
        int _jitmagicLoopLimiter7 = 0;
        for (int ii = (int) -211497150, jj = l_ascii.length - (int) -293599476; (boolean) (jj >= BIT_6) && _jitmagicLoopLimiter7++ < 1000; ii++, jj -= 8) {
            int _jitmagicLoopLimiter8 = 0;
            for (int bits = (int) 1402815956; (int) rawLength < BITS.length && _jitmagicLoopLimiter8++ < 1000; ++bits) {
                if (((byte) byteArrAccessExp(refId(byte[].class), intId()).eval(43) & (int) intArrAccessExp(refId(int[].class), intId()).eval(44)) == (int) intVal().eval(45)) {
                    l_ascii[jj - bits] = (char) charVal().eval(47);
                } else {
                    l_ascii[jj - bits] = (char) charVal().eval(46);
                }
            }
        }
        return EMPTY_CHAR_ARRAY;
    }

    public static String toAsciiString(final byte[] raw) {
        return new String(toAsciiChars(refId(byte[].class).eval(54)));
    }

    @Override
    public byte[] decode(final byte[] ascii) {
        return fromAscii(refId(byte[].class).eval(55));
    }

    @Override
    public Object decode(final Object ascii) throws DecoderException {
        if (refId(java.lang.Object.class).eval(56) == null) {
            return refId(byte[].class).eval(57);
        }
        if (ascii instanceof byte[]) {
            return fromAscii(cast(byte[].class, refId(java.lang.Object.class)).eval(58));
        }
        if (ascii instanceof char[]) {
            return fromAscii(cast(char[].class, refId(java.lang.Object.class)).eval(59));
        }
        if (ascii instanceof String) {
            return fromAscii(cast(String.class, refId(java.lang.Object.class)).eval(60).toCharArray());
        }
        throw new DecoderException("argument not a byte array");
    }

    @Override
    public byte[] encode(final byte[] raw) {
        return toAsciiBytes(refId(byte[].class).eval(61));
    }

    @Override
    public Object encode(final Object raw) throws EncoderException {
        if (!(raw instanceof byte[])) {
            throw new EncoderException("argument not a byte array");
        }
        return toAsciiChars(cast(byte[].class, refId(java.lang.Object.class)).eval(62));
    }

    public byte[] toByteArray(final String ascii) {
        if (refId(java.lang.String.class).eval(63) == null) {
            return refId(byte[].class).eval(64);
        }
        return fromAscii(refId(java.lang.String.class).eval(65).toCharArray());
    }

    public static byte[] _jitmagicArg1() throws Throwable {
        return new byte[] { -65, 49, 30, 7, 75, -68, -50, -39, 29, -65, -55, 114, 0 };
    }

    public static long main0(String[] args) {
        org.csutil.Helper.parseArgs(args);
        int N = Math.min(org.csutil.Helper.nItrs, 100000);
        WrappedChecksum cs = new WrappedChecksum(true);
        try {
            byte[] eArg1 = _jitmagicArg1();
            cs.update(eArg1);
            for (int i = 0; i < N; ++i) {
                try {
                    cs.update(toAsciiChars(eArg1));
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
        cs.updateStaticFieldsOfClass(BinaryCodec.class);
        return cs.getValue();
    }

    public static void main(String[] args) {
        org.csutil.Helper.write(main0(args));
    }
}
