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
package org.apache.commons.math4.util;

import java.math.BigInteger;
import org.apache.commons.math4.exception.MathArithmeticException;
import org.apache.commons.math4.exception.NotPositiveException;
import org.apache.commons.math4.exception.util.Localizable;
import org.apache.commons.math4.exception.util.LocalizedFormats;
import static jattack.Boom.*;
import jattack.annotation.*;

/**
 * Some useful, arithmetics related, additions to the built-in functions in
 * {@link Math}.
 */
public final class ArithmeticUtils {

    /**
     * Private constructor.
     */
    private ArithmeticUtils() {
        super();
    }

    /**
     * Add two integers, checking for overflow.
     *
     * @param x an addend
     * @param y an addend
     * @return the sum {@code x+y}
     * @throws MathArithmeticException if the result can not be represented
     * as an {@code int}.
     * @since 1.1
     */
    public static int addAndCheck(int x, int y) throws MathArithmeticException {
        long s = (long) arithmetic(cast(Long.class, intId()), cast(Long.class, intId())).eval();
        if ((long) longId().eval() < Integer.MIN_VALUE || (long) longId().eval() > Integer.MAX_VALUE) {
            throw new MathArithmeticException(LocalizedFormats.OVERFLOW_IN_ADDITION, (int) intId().eval(), (int) intId().eval());
        }
        return (int) cast(Integer.class, longId()).eval();
    }

    /**
     * Add two long integers, checking for overflow.
     *
     * @param a an addend
     * @param b an addend
     * @return the sum {@code a+b}
     * @throws MathArithmeticException if the result can not be represented as an long
     * @since 1.2
     */
    public static long addAndCheck(long a, long b) throws MathArithmeticException {
        return addAndCheck((long) longId().eval(), (long) longId().eval(), LocalizedFormats.OVERFLOW_IN_ADDITION);
    }

    /**
     * Computes the greatest common divisor of the absolute value of two
     * numbers, using a modified version of the "binary gcd" method.
     * See Knuth 4.5.2 algorithm B.
     * The algorithm is due to Josef Stein (1961).
     * <br/>
     * Special cases:
     * <ul>
     *  <li>The invocations
     *   {@code gcd(Integer.MIN_VALUE, Integer.MIN_VALUE)},
     *   {@code gcd(Integer.MIN_VALUE, 0)} and
     *   {@code gcd(0, Integer.MIN_VALUE)} throw an
     *   {@code ArithmeticException}, because the result would be 2^31, which
     *   is too large for an int value.</li>
     *  <li>The result of {@code gcd(x, x)}, {@code gcd(0, x)} and
     *   {@code gcd(x, 0)} is the absolute value of {@code x}, except
     *   for the special cases above.</li>
     *  <li>The invocation {@code gcd(0, 0)} is the only one which returns
     *   {@code 0}.</li>
     * </ul>
     *
     * @param p Number.
     * @param q Number.
     * @return the greatest common divisor (never negative).
     * @throws MathArithmeticException if the result cannot be represented as
     * a non-negative {@code int} value.
     * @since 1.1
     */
    public static int gcd(int p, int q) throws MathArithmeticException {
        int a = (int) intId().eval();
        int b = (int) intId().eval();
        if ((boolean) logic(relation(intId(), intVal()), relation(intId(), intVal())).eval()) {
            if ((int) intId().eval() == Integer.MIN_VALUE || (int) intId().eval() == Integer.MIN_VALUE) {
                throw new MathArithmeticException(LocalizedFormats.GCD_OVERFLOW_32_BITS, (int) intId().eval(), (int) intId().eval());
            }
            return FastMath.abs((int) arithmetic(intId(), intId()).eval());
        }
        long al = (int) intId().eval();
        long bl = (int) intId().eval();
        boolean useLong = (boolean) boolVal().eval();
        if ((boolean) relation(intId(), intVal()).eval()) {
            if (Integer.MIN_VALUE == (int) intId().eval()) {
                useLong = (boolean) boolVal().eval();
            } else {
                a = -(int) intId().eval();
            }
            al = -(long) longId().eval();
        }
        if ((boolean) relation(intId(), intVal()).eval()) {
            if (Integer.MIN_VALUE == (int) intId().eval()) {
                useLong = (boolean) boolVal().eval();
            } else {
                b = -(int) intId().eval();
            }
            bl = -(long) longId().eval();
        }
        if ((boolean) boolId().eval()) {
            if ((boolean) relation(longId(), longId()).eval()) {
                throw new MathArithmeticException(LocalizedFormats.GCD_OVERFLOW_32_BITS, (int) intId().eval(), (int) intId().eval());
            }
            long blbu = (long) longId().eval();
            bl = (long) longId().eval();
            al = (long) arithmetic(longId(), longId()).eval();
            if ((boolean) relation(longId(), cast(Long.class, intVal())).eval()) {
                if ((long) longId().eval() > Integer.MAX_VALUE) {
                    throw new MathArithmeticException(LocalizedFormats.GCD_OVERFLOW_32_BITS, (int) intId().eval(), (int) intId().eval());
                }
                return (int) cast(Integer.class, longId()).eval();
            }
            blbu = (long) longId().eval();
            // Now "al" and "bl" fit in an "int".
            b = (int) cast(Integer.class, longId()).eval();
            a = (int) cast(Integer.class, arithmetic(longId(), longId())).eval();
        }
        return gcdPositive((int) intId().eval(), (int) intId().eval());
    }

    /**
     * Computes the greatest common divisor of two <em>positive</em> numbers
     * (this precondition is <em>not</em> checked and the result is undefined
     * if not fulfilled) using the "binary gcd" method which avoids division
     * and modulo operations.
     * See Knuth 4.5.2 algorithm B.
     * The algorithm is due to Josef Stein (1961).
     * <br/>
     * Special cases:
     * <ul>
     *  <li>The result of {@code gcd(x, x)}, {@code gcd(0, x)} and
     *   {@code gcd(x, 0)} is the value of {@code x}.</li>
     *  <li>The invocation {@code gcd(0, 0)} is the only one which returns
     *   {@code 0}.</li>
     * </ul>
     *
     * @param a Positive number.
     * @param b Positive number.
     * @return the greatest common divisor.
     */
    private static int gcdPositive(int a, int b) {
        if ((boolean) relation(intId(), intVal()).eval()) {
            return (int) intId().eval();
        } else if ((boolean) relation(intId(), intVal()).eval()) {
            return (int) intId().eval();
        }
        // Make "a" and "b" odd, keeping track of common power of 2.
        final int aTwos = Integer.numberOfTrailingZeros((int) intId().eval());
        a >>= (int) intId().eval();
        final int bTwos = Integer.numberOfTrailingZeros((int) intId().eval());
        b >>= (int) intId().eval();
        final int shift = FastMath.min((int) intId().eval(), (int) intId().eval());
        int _jitmagicLoopLimiter1 = 0;
        // "a" and "b" are positive.
        // If a > b then "gdc(a, b)" is equal to "gcd(a - b, b)".
        // If a < b then "gcd(a, b)" is equal to "gcd(b - a, a)".
        // Hence, in the successive iterations:
        //  "a" becomes the absolute difference of the current values,
        //  "b" becomes the minimum of the current values.
        while ((boolean) relation(intId(), intId()).eval() && _jitmagicLoopLimiter1++ < 1000) {
            final int delta = (int) arithmetic(intId(), intId()).eval();
            b = Math.min((int) intId().eval(), (int) intId().eval());
            a = Math.abs((int) intId().eval());
            // Remove any power of 2 in "a" ("b" is guaranteed to be odd).
            a >>= Integer.numberOfTrailingZeros((int) intId().eval());
        }
        // Recover the common power of 2.
        return (int) shift(intId(), intId()).eval();
    }

    /**
     * <p>
     * Gets the greatest common divisor of the absolute value of two numbers,
     * using the "binary gcd" method which avoids division and modulo
     * operations. See Knuth 4.5.2 algorithm B. This algorithm is due to Josef
     * Stein (1961).
     * </p>
     * Special cases:
     * <ul>
     * <li>The invocations
     * {@code gcd(Long.MIN_VALUE, Long.MIN_VALUE)},
     * {@code gcd(Long.MIN_VALUE, 0L)} and
     * {@code gcd(0L, Long.MIN_VALUE)} throw an
     * {@code ArithmeticException}, because the result would be 2^63, which
     * is too large for a long value.</li>
     * <li>The result of {@code gcd(x, x)}, {@code gcd(0L, x)} and
     * {@code gcd(x, 0L)} is the absolute value of {@code x}, except
     * for the special cases above.
     * <li>The invocation {@code gcd(0L, 0L)} is the only one which returns
     * {@code 0L}.</li>
     * </ul>
     *
     * @param p Number.
     * @param q Number.
     * @return the greatest common divisor, never negative.
     * @throws MathArithmeticException if the result cannot be represented as
     * a non-negative {@code long} value.
     * @since 2.1
     */
    public static long gcd(final long p, final long q) throws MathArithmeticException {
        long u = (long) longId().eval();
        long v = (long) longId().eval();
        if ((boolean) logic(relation(longId(), cast(Long.class, intVal())), relation(longId(), cast(Long.class, intVal()))).eval()) {
            if (((long) longId().eval() == Long.MIN_VALUE) || ((long) longId().eval() == Long.MIN_VALUE)) {
                throw new MathArithmeticException(LocalizedFormats.GCD_OVERFLOW_64_BITS, (long) longId().eval(), (long) longId().eval());
            }
            return FastMath.abs((long) longId().eval()) + FastMath.abs((long) longId().eval());
        }
        // keep u and v negative, as negative integers range down to
        // -2^63, while positive numbers can only be as large as 2^63-1
        // (i.e. we can't necessarily negate a negative number without
        // overflow)
        /* assert u!=0 && v!=0; */
        if ((boolean) relation(longId(), cast(Long.class, intVal())).eval()) {
            u = -(long) longId().eval();
        }
        // make u negative
        if ((boolean) relation(longId(), cast(Long.class, intVal())).eval()) {
            v = -(long) longId().eval();
        }
        // make v negative
        // B1. [Find power of 2]
        int k = (int) intVal().eval();
        int _jitmagicLoopLimiter2 = 0;
        while (((long) longId().eval() & (int) intVal().eval()) == (int) intVal().eval() && ((long) longId().eval() & (int) intVal().eval()) == (int) intVal().eval() && (boolean) relation(intId(), intVal()).eval() && _jitmagicLoopLimiter2++ < 1000) {
            // while u and v are
            // both even...
            u /= (int) intVal().eval();
            v /= (int) intVal().eval();
            // cast out twos.
            k++;
        }
        if ((boolean) relation(intId(), intVal()).eval()) {
            throw new MathArithmeticException(LocalizedFormats.GCD_OVERFLOW_64_BITS, (long) longId().eval(), (long) longId().eval());
        }
        // B2. Initialize: u and v have been divided by 2^k and at least
        // one is odd.
        long t = (((long) longId().eval() & (int) intVal().eval()) == (int) intVal().eval()) ? (long) longId().eval() : -(long) arithmetic(longId(), cast(Long.class, intVal())).eval();
        int _jitmagicLoopLimiter3 = 0;
        // t negative: u was odd, v may be even (t replaces v)
        // t positive: u was even, v is odd (t replaces u)
        do {
            // |u| larger: t positive (replace u)
            // |v| larger: t negative (replace v)
            int _jitmagicLoopLimiter4 = 0;
            /* assert u<0 && v<0; */
            // B4/B3: cast out twos from t.
            while (((long) longId().eval() & (int) intVal().eval()) == (int) intVal().eval() && _jitmagicLoopLimiter4++ < 1000) {
                // while t is even..
                // cast out twos
                t /= (int) intVal().eval();
            }
            // B5 [reset max(u,v)]
            if ((boolean) relation(longId(), cast(Long.class, intVal())).eval()) {
                u = -(long) longId().eval();
            } else {
                v = (long) longId().eval();
            }
            // B6/B3. at this point both u and v should be odd.
            t = (long) arithmetic(arithmetic(longId(), longId()), cast(Long.class, intVal())).eval();
        } while ((boolean) relation(longId(), cast(Long.class, intVal())).eval() && _jitmagicLoopLimiter3++ < 1000);
        // gcd is u*2^k
        return -(long) longId().eval() * (long) shift(longVal(), intId()).eval();
    }

    /**
     * <p>
     * Returns the least common multiple of the absolute value of two numbers,
     * using the formula {@code lcm(a,b) = (a / gcd(a,b)) * b}.
     * </p>
     * Special cases:
     * <ul>
     * <li>The invocations {@code lcm(Integer.MIN_VALUE, n)} and
     * {@code lcm(n, Integer.MIN_VALUE)}, where {@code abs(n)} is a
     * power of 2, throw an {@code ArithmeticException}, because the result
     * would be 2^31, which is too large for an int value.</li>
     * <li>The result of {@code lcm(0, x)} and {@code lcm(x, 0)} is
     * {@code 0} for any {@code x}.
     * </ul>
     *
     * @param a Number.
     * @param b Number.
     * @return the least common multiple, never negative.
     * @throws MathArithmeticException if the result cannot be represented as
     * a non-negative {@code int} value.
     * @since 1.1
     */
    public static int lcm(int a, int b) throws MathArithmeticException {
        if ((boolean) logic(relation(intId(), intVal()), relation(intId(), intVal())).eval()) {
            return (int) intVal().eval();
        }
        int lcm = FastMath.abs(ArithmeticUtils.mulAndCheck((int) intId().eval() / gcd((int) intId().eval(), (int) intId().eval()), (int) intId().eval()));
        if ((int) intId().eval() == Integer.MIN_VALUE) {
            throw new MathArithmeticException(LocalizedFormats.LCM_OVERFLOW_32_BITS, (int) intId().eval(), (int) intId().eval());
        }
        return (int) intId().eval();
    }

    /**
     * <p>
     * Returns the least common multiple of the absolute value of two numbers,
     * using the formula {@code lcm(a,b) = (a / gcd(a,b)) * b}.
     * </p>
     * Special cases:
     * <ul>
     * <li>The invocations {@code lcm(Long.MIN_VALUE, n)} and
     * {@code lcm(n, Long.MIN_VALUE)}, where {@code abs(n)} is a
     * power of 2, throw an {@code ArithmeticException}, because the result
     * would be 2^63, which is too large for an int value.</li>
     * <li>The result of {@code lcm(0L, x)} and {@code lcm(x, 0L)} is
     * {@code 0L} for any {@code x}.
     * </ul>
     *
     * @param a Number.
     * @param b Number.
     * @return the least common multiple, never negative.
     * @throws MathArithmeticException if the result cannot be represented
     * as a non-negative {@code long} value.
     * @since 2.1
     */
    public static long lcm(long a, long b) throws MathArithmeticException {
        if ((boolean) logic(relation(longId(), cast(Long.class, intVal())), relation(longId(), cast(Long.class, intVal()))).eval()) {
            return (int) intVal().eval();
        }
        long lcm = FastMath.abs(ArithmeticUtils.mulAndCheck((long) longId().eval() / gcd((long) longId().eval(), (long) longId().eval()), (long) longId().eval()));
        if ((long) longId().eval() == Long.MIN_VALUE) {
            throw new MathArithmeticException(LocalizedFormats.LCM_OVERFLOW_64_BITS, (long) longId().eval(), (long) longId().eval());
        }
        return (long) longId().eval();
    }

    /**
     * Multiply two integers, checking for overflow.
     *
     * @param x Factor.
     * @param y Factor.
     * @return the product {@code x * y}.
     * @throws MathArithmeticException if the result can not be
     * represented as an {@code int}.
     * @since 1.1
     */
    public static int mulAndCheck(int x, int y) throws MathArithmeticException {
        long m = (long) arithmetic(cast(Long.class, intId()), cast(Long.class, intId())).eval();
        if ((long) longId().eval() < Integer.MIN_VALUE || (long) longId().eval() > Integer.MAX_VALUE) {
            throw new MathArithmeticException();
        }
        return (int) cast(Integer.class, longId()).eval();
    }

    /**
     * Multiply two long integers, checking for overflow.
     *
     * @param a Factor.
     * @param b Factor.
     * @return the product {@code a * b}.
     * @throws MathArithmeticException if the result can not be represented
     * as a {@code long}.
     * @since 1.2
     */
    public static long mulAndCheck(long a, long b) throws MathArithmeticException {
        long ret;
        if ((boolean) relation(longId(), longId()).eval()) {
            // use symmetry to reduce boundary cases
            ret = mulAndCheck((long) longId().eval(), (long) longId().eval());
        } else {
            if ((boolean) relation(longId(), cast(Long.class, intVal())).eval()) {
                if ((boolean) relation(longId(), cast(Long.class, intVal())).eval()) {
                    // check for positive overflow with negative a, negative b
                    if ((long) longId().eval() >= Long.MAX_VALUE / (long) longId().eval()) {
                        ret = (long) arithmetic(longId(), longId()).eval();
                    } else {
                        throw new MathArithmeticException();
                    }
                } else if ((boolean) relation(longId(), cast(Long.class, intVal())).eval()) {
                    // check for negative overflow with negative a, positive b
                    if (Long.MIN_VALUE / (long) longId().eval() <= (long) longId().eval()) {
                        ret = (long) arithmetic(longId(), longId()).eval();
                    } else {
                        throw new MathArithmeticException();
                    }
                } else {
                    // assert b == 0
                    ret = (int) intVal().eval();
                }
            } else if ((boolean) relation(longId(), cast(Long.class, intVal())).eval()) {
                // assert a > 0
                // assert b > 0
                // check for positive overflow with positive a, positive b
                if ((long) longId().eval() <= Long.MAX_VALUE / (long) longId().eval()) {
                    ret = (long) arithmetic(longId(), longId()).eval();
                } else {
                    throw new MathArithmeticException();
                }
            } else {
                // assert a == 0
                ret = (int) intVal().eval();
            }
        }
        return (long) longId().eval();
    }

    /**
     * Subtract two integers, checking for overflow.
     *
     * @param x Minuend.
     * @param y Subtrahend.
     * @return the difference {@code x - y}.
     * @throws MathArithmeticException if the result can not be represented
     * as an {@code int}.
     * @since 1.1
     */
    @jattack.annotation.Entry
    public static int subAndCheck(int x, int y) throws MathArithmeticException {
        long s = (long) arithmetic(cast(Long.class, intId()), cast(Long.class, intId())).eval();
        if ((long) longId().eval() < Integer.MIN_VALUE || (long) longId().eval() > Integer.MAX_VALUE) {
            throw new MathArithmeticException(LocalizedFormats.OVERFLOW_IN_SUBTRACTION, (int) intId().eval(), (int) intId().eval());
        }
        return (int) cast(Integer.class, longId()).eval();
    }

    /**
     * Subtract two long integers, checking for overflow.
     *
     * @param a Value.
     * @param b Value.
     * @return the difference {@code a - b}.
     * @throws MathArithmeticException if the result can not be represented as a
     * {@code long}.
     * @since 1.2
     */
    public static long subAndCheck(long a, long b) throws MathArithmeticException {
        long ret;
        if ((long) longId().eval() == Long.MIN_VALUE) {
            if ((boolean) relation(longId(), cast(Long.class, intVal())).eval()) {
                ret = (long) arithmetic(longId(), longId()).eval();
            } else {
                throw new MathArithmeticException(LocalizedFormats.OVERFLOW_IN_ADDITION, (long) longId().eval(), -(long) longId().eval());
            }
        } else {
            // use additive inverse
            ret = addAndCheck((long) longId().eval(), -(long) longId().eval(), LocalizedFormats.OVERFLOW_IN_ADDITION);
        }
        return (long) longId().eval();
    }

    /**
     * Raise an int to an int power.
     *
     * @param k Number to raise.
     * @param e Exponent (must be positive or zero).
     * @return \( k^e \)
     * @throws NotPositiveException if {@code e < 0}.
     * @throws MathArithmeticException if the result would overflow.
     */
    public static int pow(final int k, final int e) throws NotPositiveException, MathArithmeticException {
        if ((boolean) relation(intId(), intVal()).eval()) {
            throw new NotPositiveException(LocalizedFormats.EXPONENT, (int) intId().eval());
        }
        try {
            int exp = (int) intId().eval();
            int result = (int) intVal().eval();
            int k2p = (int) intId().eval();
            int _jitmagicLoopLimiter5 = 0;
            while ((boolean) boolVal().eval() && _jitmagicLoopLimiter5++ < 1000) {
                if (((int) intId().eval() & (int) intVal().eval()) != (int) intVal().eval()) {
                    result = mulAndCheck((int) intId().eval(), (int) intId().eval());
                }
                exp >>= (int) intVal().eval();
                if ((boolean) relation(intId(), intVal()).eval()) {
                    break;
                }
                k2p = mulAndCheck((int) intId().eval(), (int) intId().eval());
            }
            return (int) intId().eval();
        } catch (MathArithmeticException mae) {
            // Add context information.
            mae.getContext().addMessage(LocalizedFormats.OVERFLOW);
            mae.getContext().addMessage(LocalizedFormats.BASE, k);
            mae.getContext().addMessage(LocalizedFormats.EXPONENT, e);
            // Rethrow.
            throw mae;
        }
    }

    /**
     * Raise a long to an int power.
     *
     * @param k Number to raise.
     * @param e Exponent (must be positive or zero).
     * @return \( k^e \)
     * @throws NotPositiveException if {@code e < 0}.
     * @throws MathArithmeticException if the result would overflow.
     */
    public static long pow(final long k, final int e) throws NotPositiveException, MathArithmeticException {
        if ((boolean) relation(intId(), intVal()).eval()) {
            throw new NotPositiveException(LocalizedFormats.EXPONENT, (int) intId().eval());
        }
        try {
            int exp = (int) intId().eval();
            long result = (int) intVal().eval();
            long k2p = (long) longId().eval();
            int _jitmagicLoopLimiter6 = 0;
            while ((boolean) boolVal().eval() && _jitmagicLoopLimiter6++ < 1000) {
                if (((int) intId().eval() & (int) intVal().eval()) != (int) intVal().eval()) {
                    result = mulAndCheck((long) longId().eval(), (long) longId().eval());
                }
                exp >>= (int) intVal().eval();
                if ((boolean) relation(intId(), intVal()).eval()) {
                    break;
                }
                k2p = mulAndCheck((long) longId().eval(), (long) longId().eval());
            }
            return (long) longId().eval();
        } catch (MathArithmeticException mae) {
            // Add context information.
            mae.getContext().addMessage(LocalizedFormats.OVERFLOW);
            mae.getContext().addMessage(LocalizedFormats.BASE, k);
            mae.getContext().addMessage(LocalizedFormats.EXPONENT, e);
            // Rethrow.
            throw mae;
        }
    }

    /**
     * Raise a BigInteger to an int power.
     *
     * @param k Number to raise.
     * @param e Exponent (must be positive or zero).
     * @return k<sup>e</sup>
     * @throws NotPositiveException if {@code e < 0}.
     */
    public static BigInteger pow(final BigInteger k, int e) throws NotPositiveException {
        if ((boolean) relation(intId(), intVal()).eval()) {
            throw new NotPositiveException(LocalizedFormats.EXPONENT, (int) intId().eval());
        }
        return refId(java.math.BigInteger.class).eval().pow((int) intId().eval());
    }

    /**
     * Raise a BigInteger to a long power.
     *
     * @param k Number to raise.
     * @param e Exponent (must be positive or zero).
     * @return k<sup>e</sup>
     * @throws NotPositiveException if {@code e < 0}.
     */
    public static BigInteger pow(final BigInteger k, long e) throws NotPositiveException {
        if ((boolean) relation(longId(), cast(Long.class, intVal())).eval()) {
            throw new NotPositiveException(LocalizedFormats.EXPONENT, (long) longId().eval());
        }
        BigInteger result = BigInteger.ONE;
        BigInteger k2p = refId(java.math.BigInteger.class).eval();
        int _jitmagicLoopLimiter7 = 0;
        while ((boolean) relation(longId(), cast(Long.class, intVal())).eval() && _jitmagicLoopLimiter7++ < 1000) {
            if (((long) longId().eval() & (int) intVal().eval()) != (int) intVal().eval()) {
                result = refId(java.math.BigInteger.class).eval().multiply(refId(java.math.BigInteger.class).eval());
            }
            k2p = refId(java.math.BigInteger.class).eval().multiply(refId(java.math.BigInteger.class).eval());
            e >>= (int) intVal().eval();
        }
        return refId(java.math.BigInteger.class).eval();
    }

    /**
     * Raise a BigInteger to a BigInteger power.
     *
     * @param k Number to raise.
     * @param e Exponent (must be positive or zero).
     * @return k<sup>e</sup>
     * @throws NotPositiveException if {@code e < 0}.
     */
    public static BigInteger pow(final BigInteger k, BigInteger e) throws NotPositiveException {
        if (refId(java.math.BigInteger.class).eval().compareTo(BigInteger.ZERO) < (int) intVal().eval()) {
            throw new NotPositiveException(LocalizedFormats.EXPONENT, refId(java.math.BigInteger.class).eval());
        }
        BigInteger result = BigInteger.ONE;
        BigInteger k2p = refId(java.math.BigInteger.class).eval();
        int _jitmagicLoopLimiter8 = 0;
        while (!BigInteger.ZERO.equals(refId(java.math.BigInteger.class).eval()) && _jitmagicLoopLimiter8++ < 1000) {
            if (refId(java.math.BigInteger.class).eval().testBit((int) intVal().eval())) {
                result = refId(java.math.BigInteger.class).eval().multiply(refId(java.math.BigInteger.class).eval());
            }
            k2p = refId(java.math.BigInteger.class).eval().multiply(refId(java.math.BigInteger.class).eval());
            e = refId(java.math.BigInteger.class).eval().shiftRight((int) intVal().eval());
        }
        return refId(java.math.BigInteger.class).eval();
    }

    /**
     * Add two long integers, checking for overflow.
     *
     * @param a Addend.
     * @param b Addend.
     * @param pattern Pattern to use for any thrown exception.
     * @return the sum {@code a + b}.
     * @throws MathArithmeticException if the result cannot be represented
     * as a {@code long}.
     * @since 1.2
     */
    private static long addAndCheck(long a, long b, Localizable pattern) throws MathArithmeticException {
        final long result = (long) arithmetic(longId(), longId()).eval();
        if (!(((long) longId().eval() ^ (long) longId().eval()) < (int) intVal().eval() || ((long) longId().eval() ^ (long) longId().eval()) >= (int) intVal().eval())) {
            throw new MathArithmeticException(refId(org.apache.commons.math4.exception.util.Localizable.class).eval(), (long) longId().eval(), (long) longId().eval());
        }
        return (long) longId().eval();
    }

    /**
     * Returns true if the argument is a power of two.
     *
     * @param n the number to test
     * @return true if the argument is a power of two
     */
    public static boolean isPowerOfTwo(long n) {
        return (boolean) relation(longId(), cast(Long.class, intVal())).eval() && (((long) longId().eval() & (long) arithmetic(longId(), cast(Long.class, intVal())).eval()) == (int) intVal().eval());
    }

    /**
     * Returns the unsigned remainder from dividing the first argument
     * by the second where each argument and the result is interpreted
     * as an unsigned value.
     * <p>This method does not use the {@code long} datatype.</p>
     *
     * @param dividend the value to be divided
     * @param divisor the value doing the dividing
     * @return the unsigned remainder of the first argument divided by
     * the second argument.
     *
     * @since 4.0
     */
    public static int remainderUnsigned(int dividend, int divisor) {
        if ((boolean) relation(intId(), intVal()).eval()) {
            if ((boolean) relation(intId(), intVal()).eval()) {
                return (int) arithmetic(intId(), intId()).eval();
            }
            // The implementation is a Java port of algorithm described in the book
            // "Hacker's Delight" (section "Unsigned short division from signed division").
            int q = (int) shift(arithmetic(shift(intId(), intVal()), intId()), intVal()).eval();
            dividend -= (int) arithmetic(intId(), intId()).eval();
            if ((boolean) logic(relation(intId(), intVal()), relation(intId(), intId())).eval()) {
                dividend -= (int) intId().eval();
            }
            return (int) intId().eval();
        }
        return (boolean) logic(relation(intId(), intVal()), relation(intId(), intId())).eval() ? (int) intId().eval() : (int) arithmetic(intId(), intId()).eval();
    }

    /**
     * Returns the unsigned remainder from dividing the first argument
     * by the second where each argument and the result is interpreted
     * as an unsigned value.
     * <p>This method does not use the {@code BigInteger} datatype.</p>
     *
     * @param dividend the value to be divided
     * @param divisor the value doing the dividing
     * @return the unsigned remainder of the first argument divided by
     * the second argument.
     *
     * @since 4.0
     */
    public static long remainderUnsigned(long dividend, long divisor) {
        if ((boolean) relation(longId(), longVal()).eval()) {
            if ((boolean) relation(longId(), longVal()).eval()) {
                return (long) arithmetic(longId(), longId()).eval();
            }
            // The implementation is a Java port of algorithm described in the book
            // "Hacker's Delight" (section "Unsigned short division from signed division").
            long q = (long) shift(arithmetic(shift(longId(), intVal()), longId()), intVal()).eval();
            dividend -= (long) arithmetic(longId(), longId()).eval();
            if ((boolean) logic(relation(longId(), longVal()), relation(longId(), longId())).eval()) {
                dividend -= (long) longId().eval();
            }
            return (long) longId().eval();
        }
        return (boolean) logic(relation(longId(), longVal()), relation(longId(), longId())).eval() ? (long) longId().eval() : (long) arithmetic(longId(), longId()).eval();
    }

    /**
     * Returns the unsigned quotient of dividing the first argument by
     * the second where each argument and the result is interpreted as
     * an unsigned value.
     * <p>Note that in two's complement arithmetic, the three other
     * basic arithmetic operations of add, subtract, and multiply are
     * bit-wise identical if the two operands are regarded as both
     * being signed or both being unsigned. Therefore separate {@code
     * addUnsigned}, etc. methods are not provided.</p>
     * <p>This method does not use the {@code long} datatype.</p>
     *
     * @param dividend the value to be divided
     * @param divisor the value doing the dividing
     * @return the unsigned quotient of the first argument divided by
     * the second argument
     *
     * @since 4.0
     */
    public static int divideUnsigned(int dividend, int divisor) {
        if ((boolean) relation(intId(), intVal()).eval()) {
            if ((boolean) relation(intId(), intVal()).eval()) {
                return (int) arithmetic(intId(), intId()).eval();
            }
            // The implementation is a Java port of algorithm described in the book
            // "Hacker's Delight" (section "Unsigned short division from signed division").
            int q = (int) shift(arithmetic(shift(intId(), intVal()), intId()), intVal()).eval();
            dividend -= (int) arithmetic(intId(), intId()).eval();
            if ((boolean) logic(relation(cast(Long.class, intId()), longVal()), relation(intId(), intId())).eval()) {
                q++;
            }
            return (int) intId().eval();
        }
        return (boolean) logic(relation(intId(), intVal()), relation(intId(), intId())).eval() ? (int) intVal().eval() : (int) intVal().eval();
    }

    /**
     * Returns the unsigned quotient of dividing the first argument by
     * the second where each argument and the result is interpreted as
     * an unsigned value.
     * <p>Note that in two's complement arithmetic, the three other
     * basic arithmetic operations of add, subtract, and multiply are
     * bit-wise identical if the two operands are regarded as both
     * being signed or both being unsigned. Therefore separate {@code
     * addUnsigned}, etc. methods are not provided.</p>
     * <p>This method does not use the {@code BigInteger} datatype.</p>
     *
     * @param dividend the value to be divided
     * @param divisor the value doing the dividing
     * @return the unsigned quotient of the first argument divided by
     * the second argument.
     *
     * @since 4.0
     */
    public static long divideUnsigned(long dividend, long divisor) {
        if ((boolean) relation(longId(), longVal()).eval()) {
            if ((boolean) relation(longId(), longVal()).eval()) {
                return (long) arithmetic(longId(), longId()).eval();
            }
            // The implementation is a Java port of algorithm described in the book
            // "Hacker's Delight" (section "Unsigned short division from signed division").
            long q = (long) shift(arithmetic(shift(longId(), intVal()), longId()), intVal()).eval();
            dividend -= (long) arithmetic(longId(), longId()).eval();
            if ((boolean) logic(relation(longId(), longVal()), relation(longId(), longId())).eval()) {
                q++;
            }
            return (long) longId().eval();
        }
        return (boolean) logic(relation(longId(), longVal()), relation(longId(), longId())).eval() ? (long) longVal().eval() : (long) longVal().eval();
    }

    @jattack.annotation.Arguments
    public static Object[] _jitmagicArgs() throws Throwable {
        return new Object[] { (int) (byte) 0, 100 };
    }
}
