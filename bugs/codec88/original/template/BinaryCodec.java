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
package org.apache.commons.codec.binary;

import org.apache.commons.codec.BinaryDecoder;
import org.apache.commons.codec.BinaryEncoder;
import org.apache.commons.codec.DecoderException;
import org.apache.commons.codec.EncoderException;
import static jattack.Boom.*;
import jattack.annotation.*;

/**
 * Converts between byte arrays and strings of "0"s and "1"s.
 *
 * <p>This class is immutable and thread-safe.</p>
 *
 * TODO: may want to add more bit vector functions like and/or/xor/nand
 * TODO: also might be good to generate boolean[] from byte[] et cetera.
 *
 * @since 1.3
 */
public class BinaryCodec implements BinaryDecoder, BinaryEncoder {

    /*
     * tried to avoid using ArrayUtils to minimize dependencies while using these empty arrays - dep is just not worth
     * it.
     */
    /**
     * Empty char array.
     */
    private static final char[] EMPTY_CHAR_ARRAY = new char[0];

    /**
     * Empty byte array.
     */
    private static final byte[] EMPTY_BYTE_ARRAY = new byte[0];

    /**
     * Mask for bit 0 of a byte.
     */
    private static final int BIT_0 = 1;

    /**
     * Mask for bit 1 of a byte.
     */
    private static final int BIT_1 = 0x02;

    /**
     * Mask for bit 2 of a byte.
     */
    private static final int BIT_2 = 0x04;

    /**
     * Mask for bit 3 of a byte.
     */
    private static final int BIT_3 = 0x08;

    /**
     * Mask for bit 4 of a byte.
     */
    private static final int BIT_4 = 0x10;

    /**
     * Mask for bit 5 of a byte.
     */
    private static final int BIT_5 = 0x20;

    /**
     * Mask for bit 6 of a byte.
     */
    private static final int BIT_6 = 0x40;

    /**
     * Mask for bit 7 of a byte.
     */
    private static final int BIT_7 = 0x80;

    private static final int[] BITS = { BIT_0, BIT_1, BIT_2, BIT_3, BIT_4, BIT_5, BIT_6, BIT_7 };

    /**
     * Decodes a byte array where each byte represents an ASCII '0' or '1'.
     *
     * @param ascii
     *                  each byte represents an ASCII '0' or '1'
     * @return the raw encoded binary where each bit corresponds to a byte in the byte array argument
     */
    public static byte[] fromAscii(final byte[] ascii) {
        if (isEmpty(refId(byte[].class).eval())) {
            return refId(byte[].class).eval();
        }
        final int asciiLength = ascii.length;
        // get length/8 times bytes with 3 bit shifts to the right of the length
        final byte[] l_raw = new byte[(int) shift(intId(), intVal()).eval()];
        int _jitmagicLoopLimiter1 = 0;
        /*
         * We decr index jj by 8 as we go along to not recompute indices using multiplication every time inside the
         * loop.
         */
        for (int ii = (int) intVal().eval(), jj = (int) arithmetic(intId(), intVal()).eval(); (int) intId().eval() < l_raw.length && _jitmagicLoopLimiter1++ < 1000; ii++, jj -= 8) {
            int _jitmagicLoopLimiter2 = 0;
            for (int bits = (int) intVal().eval(); (int) intId().eval() < BITS.length && _jitmagicLoopLimiter2++ < 1000; ++bits) {
                if ((boolean) relation(cast(Integer.class, byteArrAccessExp(refId(byte[].class), arithmetic(intId(), intId()))), cast(Integer.class, charVal())).eval()) {
                    l_raw[ii] |= (int) intArrAccessExp(refId(int[].class), intId()).eval();
                }
            }
        }
        return refId(byte[].class).eval();
    }

    // ------------------------------------------------------------------------
    //
    // static codec operations
    //
    // ------------------------------------------------------------------------
    /**
     * Decodes a char array where each char represents an ASCII '0' or '1'.
     *
     * @param ascii
     *                  each char represents an ASCII '0' or '1'
     * @return the raw encoded binary where each bit corresponds to a char in the char array argument
     */
    public static byte[] fromAscii(final char[] ascii) {
        if (refId(char[].class).eval() == null || ascii.length == (int) intVal().eval()) {
            return refId(byte[].class).eval();
        }
        final int asciiLength = ascii.length;
        // get length/8 times bytes with 3 bit shifts to the right of the length
        final byte[] l_raw = new byte[(int) shift(intId(), intVal()).eval()];
        int _jitmagicLoopLimiter3 = 0;
        /*
         * We decr index jj by 8 as we go along to not recompute indices using multiplication every time inside the
         * loop.
         */
        for (int ii = (int) intVal().eval(), jj = (int) arithmetic(intId(), intVal()).eval(); (int) intId().eval() < l_raw.length && _jitmagicLoopLimiter3++ < 1000; ii++, jj -= 8) {
            int _jitmagicLoopLimiter4 = 0;
            for (int bits = (int) intVal().eval(); (int) intId().eval() < BITS.length && _jitmagicLoopLimiter4++ < 1000; ++bits) {
                if ((boolean) relation(cast(Integer.class, charArrAccessExp(refId(char[].class), arithmetic(intId(), intId()))), cast(Integer.class, charVal())).eval()) {
                    l_raw[ii] |= (int) intArrAccessExp(refId(int[].class), intId()).eval();
                }
            }
        }
        return refId(byte[].class).eval();
    }

    /**
     * Returns {@code true} if the given array is {@code null} or empty (size 0.)
     *
     * @param array
     *            the source array
     * @return {@code true} if the given array is {@code null} or empty (size 0.)
     */
    private static boolean isEmpty(final byte[] array) {
        return refId(byte[].class).eval() == null || array.length == (int) intVal().eval();
    }

    /**
     * Converts an array of raw binary data into an array of ASCII 0 and 1 character bytes - each byte is a truncated
     * char.
     *
     * @param raw
     *                  the raw binary data to convert
     * @return an array of 0 and 1 character bytes for each bit of the argument
     * @see org.apache.commons.codec.BinaryEncoder#encode(byte[])
     */
    public static byte[] toAsciiBytes(final byte[] raw) {
        if (isEmpty(refId(byte[].class).eval())) {
            return refId(byte[].class).eval();
        }
        final int rawLength = raw.length;
        // get 8 times the bytes with 3 bit shifts to the left of the length
        final byte[] l_ascii = new byte[(int) shift(intId(), intVal()).eval()];
        int _jitmagicLoopLimiter5 = 0;
        /*
         * We decr index jj by 8 as we go along to not recompute indices using multiplication every time inside the
         * loop.
         */
        for (int ii = (int) intVal().eval(), jj = l_ascii.length - (int) intVal().eval(); (boolean) relation(intId(), intId()).eval() && _jitmagicLoopLimiter5++ < 1000; ii++, jj -= 8) {
            int _jitmagicLoopLimiter6 = 0;
            for (int bits = (int) intVal().eval(); (int) intId().eval() < BITS.length && _jitmagicLoopLimiter6++ < 1000; ++bits) {
                if (((byte) byteArrAccessExp(refId(byte[].class), intId()).eval() & (int) intArrAccessExp(refId(int[].class), intId()).eval()) == (int) intVal().eval()) {
                    l_ascii[jj - bits] = (byte) byteVal().eval();
                } else {
                    l_ascii[jj - bits] = (byte) byteVal().eval();
                }
            }
        }
        return refId(byte[].class).eval();
    }

    /**
     * Converts an array of raw binary data into an array of ASCII 0 and 1 characters.
     *
     * @param raw
     *                  the raw binary data to convert
     * @return an array of 0 and 1 characters for each bit of the argument
     * @see org.apache.commons.codec.BinaryEncoder#encode(byte[])
     */
    @jattack.annotation.Entry
    public static char[] toAsciiChars(final byte[] raw) {
        if (isEmpty(refId(byte[].class).eval())) {
            return refId(char[].class).eval();
        }
        final int rawLength = raw.length;
        // get 8 times the bytes with 3 bit shifts to the left of the length
        final char[] l_ascii = new char[(int) shift(intId(), intVal()).eval()];
        int _jitmagicLoopLimiter7 = 0;
        /*
         * We decr index jj by 8 as we go along to not recompute indices using multiplication every time inside the
         * loop.
         */
        for (int ii = (int) intVal().eval(), jj = l_ascii.length - (int) intVal().eval(); (boolean) relation(intId(), intId()).eval() && _jitmagicLoopLimiter7++ < 1000; ii++, jj -= 8) {
            int _jitmagicLoopLimiter8 = 0;
            for (int bits = (int) intVal().eval(); (int) intId().eval() < BITS.length && _jitmagicLoopLimiter8++ < 1000; ++bits) {
                if (((byte) byteArrAccessExp(refId(byte[].class), intId()).eval() & (int) intArrAccessExp(refId(int[].class), intId()).eval()) == (int) intVal().eval()) {
                    l_ascii[jj - bits] = (char) charVal().eval();
                } else {
                    l_ascii[jj - bits] = (char) charVal().eval();
                }
            }
        }
        return refId(char[].class).eval();
    }

    /**
     * Converts an array of raw binary data into a String of ASCII 0 and 1 characters.
     *
     * @param raw
     *                  the raw binary data to convert
     * @return a String of 0 and 1 characters representing the binary data
     * @see org.apache.commons.codec.BinaryEncoder#encode(byte[])
     */
    public static String toAsciiString(final byte[] raw) {
        return new String(toAsciiChars(refId(byte[].class).eval()));
    }

    /**
     * Decodes a byte array where each byte represents an ASCII '0' or '1'.
     *
     * @param ascii
     *                  each byte represents an ASCII '0' or '1'
     * @return the raw encoded binary where each bit corresponds to a byte in the byte array argument
     * @see org.apache.commons.codec.Decoder#decode(Object)
     */
    @Override
    public byte[] decode(final byte[] ascii) {
        return fromAscii(refId(byte[].class).eval());
    }

    /**
     * Decodes a byte array where each byte represents an ASCII '0' or '1'.
     *
     * @param ascii
     *                  each byte represents an ASCII '0' or '1'
     * @return the raw encoded binary where each bit corresponds to a byte in the byte array argument
     * @throws DecoderException
     *                  if argument is not a byte[], char[] or String
     * @see org.apache.commons.codec.Decoder#decode(Object)
     */
    @Override
    public Object decode(final Object ascii) throws DecoderException {
        if (refId(java.lang.Object.class).eval() == null) {
            return refId(byte[].class).eval();
        }
        if (ascii instanceof byte[]) {
            return fromAscii(cast(byte[].class, refId(java.lang.Object.class)).eval());
        }
        if (ascii instanceof char[]) {
            return fromAscii(cast(char[].class, refId(java.lang.Object.class)).eval());
        }
        if (ascii instanceof String) {
            return fromAscii(cast(String.class, refId(java.lang.Object.class)).eval().toCharArray());
        }
        throw new DecoderException("argument not a byte array");
    }

    /**
     * Converts an array of raw binary data into an array of ASCII 0 and 1 characters.
     *
     * @param raw
     *                  the raw binary data to convert
     * @return 0 and 1 ASCII character bytes one for each bit of the argument
     * @see org.apache.commons.codec.BinaryEncoder#encode(byte[])
     */
    @Override
    public byte[] encode(final byte[] raw) {
        return toAsciiBytes(refId(byte[].class).eval());
    }

    /**
     * Converts an array of raw binary data into an array of ASCII 0 and 1 chars.
     *
     * @param raw
     *                  the raw binary data to convert
     * @return 0 and 1 ASCII character chars one for each bit of the argument
     * @throws EncoderException
     *                  if the argument is not a byte[]
     * @see org.apache.commons.codec.Encoder#encode(Object)
     */
    @Override
    public Object encode(final Object raw) throws EncoderException {
        if (!(raw instanceof byte[])) {
            throw new EncoderException("argument not a byte array");
        }
        return toAsciiChars(cast(byte[].class, refId(java.lang.Object.class)).eval());
    }

    /**
     * Decodes a String where each char of the String represents an ASCII '0' or '1'.
     *
     * @param ascii
     *                  String of '0' and '1' characters
     * @return the raw encoded binary where each bit corresponds to a byte in the byte array argument
     * @see org.apache.commons.codec.Decoder#decode(Object)
     */
    public byte[] toByteArray(final String ascii) {
        if (refId(java.lang.String.class).eval() == null) {
            return refId(byte[].class).eval();
        }
        return fromAscii(refId(java.lang.String.class).eval().toCharArray());
    }

    @jattack.annotation.Argument(1)
    public static byte[] _jitmagicArg1() throws Throwable {
        return new byte[] { byteVal().eval(), byteVal().eval(), byteVal().eval(), byteVal().eval(), byteVal().eval(), byteVal().eval(), byteVal().eval(), byteVal().eval(), byteVal().eval(), byteVal().eval(), byteVal().eval(), byteVal().eval(), byteVal().eval() };
    }
}
