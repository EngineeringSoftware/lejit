package org.apache.commons.codec.binary;

import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import org.apache.commons.codec.CharEncoding;
import static jattack.Boom.*;
import jattack.annotation.*;
import org.csutil.checksum.WrappedChecksum;

public class StringUtils {

    public static boolean equals(final CharSequence cs1, final CharSequence cs2) {
        if (refId(java.lang.CharSequence.class).eval(1) == refId(java.lang.CharSequence.class).eval(2)) {
            return (boolean) boolVal().eval(3);
        }
        if (refId(java.lang.CharSequence.class).eval(4) == null || refId(java.lang.CharSequence.class).eval(5) == null) {
            return (boolean) boolVal().eval(6);
        }
        if (cs1 instanceof String && cs2 instanceof String) {
            return refId(java.lang.CharSequence.class).eval(8).equals(refId(java.lang.CharSequence.class).eval(7));
        }
        return refId(java.lang.CharSequence.class).eval(9).length() == refId(java.lang.CharSequence.class).eval(10).length() && CharSequenceUtils.regionMatches(refId(java.lang.CharSequence.class).eval(11), (boolean) boolVal().eval(12), (int) intVal().eval(13), refId(java.lang.CharSequence.class).eval(14), (int) intVal().eval(15), refId(java.lang.CharSequence.class).eval(16).length());
    }

    private static ByteBuffer getByteBuffer(final String string, final Charset charset) {
        if (refId(java.lang.String.class).eval(17) == null) {
            return null;
        }
        return ByteBuffer.wrap(refId(java.lang.String.class).eval(19).getBytes(refId(java.nio.charset.Charset.class).eval(18)));
    }

    public static ByteBuffer getByteBufferUtf8(final String string) {
        return getByteBuffer(refId(java.lang.String.class).eval(20), StandardCharsets.UTF_8);
    }

    private static byte[] getBytes(final String string, final Charset charset) {
        if (string == null) {
            return null;
        }
        return string.getBytes(charset);
    }

    public static byte[] getBytesIso8859_1(final String string) {
        return getBytes(string, StandardCharsets.ISO_8859_1);
    }

    public static byte[] getBytesUnchecked(final String string, final String charsetName) {
        if (refId(java.lang.String.class).eval(25) == null) {
            return null;
        }
        try {
            return refId(java.lang.String.class).eval(27).getBytes(refId(java.lang.String.class).eval(26));
        } catch (final UnsupportedEncodingException e) {
            throw StringUtils.newIllegalStateException(charsetName, e);
        }
    }

    public static byte[] getBytesUsAscii(final String string) {
        return getBytes(refId(java.lang.String.class).eval(28), StandardCharsets.US_ASCII);
    }

    public static byte[] getBytesUtf16(final String string) {
        return getBytes(refId(java.lang.String.class).eval(29), StandardCharsets.UTF_16);
    }

    public static byte[] getBytesUtf16Be(final String string) {
        return getBytes(refId(java.lang.String.class).eval(30), StandardCharsets.UTF_16BE);
    }

    public static byte[] getBytesUtf16Le(final String string) {
        return getBytes(refId(java.lang.String.class).eval(31), StandardCharsets.UTF_16LE);
    }

    public static byte[] getBytesUtf8(final String string) {
        return getBytes(refId(java.lang.String.class).eval(32), StandardCharsets.UTF_8);
    }

    private static IllegalStateException newIllegalStateException(final String charsetName, final UnsupportedEncodingException e) {
        return new IllegalStateException(refId(java.lang.String.class).eval(33) + ": " + refId(java.io.UnsupportedEncodingException.class).eval(34));
    }

    private static String newString(final byte[] bytes, final Charset charset) {
        return refId(byte[].class).eval(35) == null ? null : new String(refId(byte[].class).eval(36), refId(java.nio.charset.Charset.class).eval(37));
    }

    public static String newString(final byte[] bytes, final String charsetName) {
        if (refId(byte[].class).eval(38) == null) {
            return null;
        }
        try {
            return new String(refId(byte[].class).eval(39), refId(java.lang.String.class).eval(40));
        } catch (final UnsupportedEncodingException e) {
            throw StringUtils.newIllegalStateException(charsetName, e);
        }
    }

    public static String newStringIso8859_1(final byte[] bytes) {
        return newString(refId(byte[].class).eval(41), StandardCharsets.ISO_8859_1);
    }

    public static String newStringUsAscii(final byte[] bytes) {
        return newString(refId(byte[].class).eval(42), StandardCharsets.US_ASCII);
    }

    public static String newStringUtf16(final byte[] bytes) {
        return newString(refId(byte[].class).eval(43), StandardCharsets.UTF_16);
    }

    public static String newStringUtf16Be(final byte[] bytes) {
        return newString(refId(byte[].class).eval(44), StandardCharsets.UTF_16BE);
    }

    public static String newStringUtf16Le(final byte[] bytes) {
        return newString(refId(byte[].class).eval(45), StandardCharsets.UTF_16LE);
    }

    public static String newStringUtf8(final byte[] bytes) {
        return newString(refId(byte[].class).eval(46), StandardCharsets.UTF_8);
    }

    @jattack.annotation.Arguments
    public static Object[] _jitmagicArgs() throws Throwable {
        return new Object[] { "\u8020\000\000\020" };
    }

    public static long main0(String[] args) {
        org.csutil.Helper.parseArgs(args);
        int N = Math.min(org.csutil.Helper.nItrs, 100000);
        WrappedChecksum cs = new WrappedChecksum(true);
        try {
            Object[] eArgs = _jitmagicArgs();
            cs.update(eArgs);
            String eArg1 = (String) eArgs[0];
            for (int i = 0; i < N; ++i) {
                try {
                    cs.update(getBytesIso8859_1(eArg1));
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
        cs.updateStaticFieldsOfClass(StringUtils.class);
        return cs.getValue();
    }

    public static void main(String[] args) {
        org.csutil.Helper.write(main0(args));
    }
}
