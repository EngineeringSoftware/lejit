package org.apache.commons.math4.util;

import java.math.BigInteger;
import org.apache.commons.math4.exception.MathArithmeticException;
import org.apache.commons.math4.exception.NotPositiveException;
import org.apache.commons.math4.exception.util.Localizable;
import org.apache.commons.math4.exception.util.LocalizedFormats;
import static jattack.Boom.*;
import jattack.annotation.*;
import org.csutil.checksum.WrappedChecksum;

public final class ArithmeticUtils {

    private ArithmeticUtils() {
        super();
    }

    public static int addAndCheck(int x, int y) throws MathArithmeticException {
        long s = (long) arithmetic(cast(Long.class, intId()), cast(Long.class, intId())).eval(1);
        if ((long) longId().eval(2) < Integer.MIN_VALUE || (long) longId().eval(3) > Integer.MAX_VALUE) {
            throw new MathArithmeticException(LocalizedFormats.OVERFLOW_IN_ADDITION, (int) intId().eval(4), (int) intId().eval(5));
        }
        return (int) cast(Integer.class, longId()).eval(6);
    }

    public static long addAndCheck(long a, long b) throws MathArithmeticException {
        return addAndCheck((long) longId().eval(7), (long) longId().eval(8), LocalizedFormats.OVERFLOW_IN_ADDITION);
    }

    public static int gcd(int p, int q) throws MathArithmeticException {
        int a = (int) intId().eval(9);
        int b = (int) intId().eval(10);
        if ((boolean) logic(relation(intId(), intVal()), relation(intId(), intVal())).eval(11)) {
            if ((int) intId().eval(12) == Integer.MIN_VALUE || (int) intId().eval(13) == Integer.MIN_VALUE) {
                throw new MathArithmeticException(LocalizedFormats.GCD_OVERFLOW_32_BITS, (int) intId().eval(14), (int) intId().eval(15));
            }
            return FastMath.abs((int) arithmetic(intId(), intId()).eval(16));
        }
        long al = (int) intId().eval(17);
        long bl = (int) intId().eval(18);
        boolean useLong = (boolean) boolVal().eval(19);
        if ((boolean) relation(intId(), intVal()).eval(20)) {
            if (Integer.MIN_VALUE == (int) intId().eval(21)) {
                useLong = (boolean) boolVal().eval(23);
            } else {
                a = -(int) intId().eval(22);
            }
            al = -(long) longId().eval(24);
        }
        if ((boolean) relation(intId(), intVal()).eval(25)) {
            if (Integer.MIN_VALUE == (int) intId().eval(26)) {
                useLong = (boolean) boolVal().eval(28);
            } else {
                b = -(int) intId().eval(27);
            }
            bl = -(long) longId().eval(29);
        }
        if ((boolean) boolId().eval(30)) {
            if ((boolean) relation(longId(), longId()).eval(31)) {
                throw new MathArithmeticException(LocalizedFormats.GCD_OVERFLOW_32_BITS, (int) intId().eval(32), (int) intId().eval(33));
            }
            long blbu = (long) longId().eval(34);
            bl = (long) longId().eval(35);
            al = (long) arithmetic(longId(), longId()).eval(36);
            if ((boolean) relation(longId(), cast(Long.class, intVal())).eval(37)) {
                if ((long) longId().eval(38) > Integer.MAX_VALUE) {
                    throw new MathArithmeticException(LocalizedFormats.GCD_OVERFLOW_32_BITS, (int) intId().eval(39), (int) intId().eval(40));
                }
                return (int) cast(Integer.class, longId()).eval(41);
            }
            blbu = (long) longId().eval(42);
            b = (int) cast(Integer.class, longId()).eval(43);
            a = (int) cast(Integer.class, arithmetic(longId(), longId())).eval(44);
        }
        return gcdPositive((int) intId().eval(45), (int) intId().eval(46));
    }

    private static int gcdPositive(int a, int b) {
        if ((boolean) relation(intId(), intVal()).eval(47)) {
            return (int) intId().eval(50);
        } else if ((boolean) relation(intId(), intVal()).eval(48)) {
            return (int) intId().eval(49);
        }
        final int aTwos = Integer.numberOfTrailingZeros((int) intId().eval(51));
        a >>= (int) intId().eval(52);
        final int bTwos = Integer.numberOfTrailingZeros((int) intId().eval(53));
        b >>= (int) intId().eval(54);
        final int shift = FastMath.min((int) intId().eval(55), (int) intId().eval(56));
        int _jitmagicLoopLimiter1 = 0;
        while ((boolean) relation(intId(), intId()).eval(62) && _jitmagicLoopLimiter1++ < 1000) {
            final int delta = (int) arithmetic(intId(), intId()).eval(57);
            b = Math.min((int) intId().eval(58), (int) intId().eval(59));
            a = Math.abs((int) intId().eval(60));
            a >>= Integer.numberOfTrailingZeros((int) intId().eval(61));
        }
        return (int) shift(intId(), intId()).eval(63);
    }

    public static long gcd(final long p, final long q) throws MathArithmeticException {
        long u = (long) longId().eval(64);
        long v = (long) longId().eval(65);
        if ((boolean) logic(relation(longId(), cast(Long.class, intVal())), relation(longId(), cast(Long.class, intVal()))).eval(66)) {
            if (((long) longId().eval(67) == Long.MIN_VALUE) || ((long) longId().eval(68) == Long.MIN_VALUE)) {
                throw new MathArithmeticException(LocalizedFormats.GCD_OVERFLOW_64_BITS, (long) longId().eval(69), (long) longId().eval(70));
            }
            return FastMath.abs((long) longId().eval(71)) + FastMath.abs((long) longId().eval(72));
        }
        if ((boolean) relation(longId(), cast(Long.class, intVal())).eval(73)) {
            u = -(long) longId().eval(74);
        }
        if ((boolean) relation(longId(), cast(Long.class, intVal())).eval(75)) {
            v = -(long) longId().eval(76);
        }
        int k = (int) intVal().eval(77);
        int _jitmagicLoopLimiter2 = 0;
        while (((long) longId().eval(80) & (int) intVal().eval(81)) == (int) intVal().eval(82) && ((long) longId().eval(83) & (int) intVal().eval(84)) == (int) intVal().eval(85) && (boolean) relation(intId(), intVal()).eval(86) && _jitmagicLoopLimiter2++ < 1000) {
            u /= (int) intVal().eval(78);
            v /= (int) intVal().eval(79);
            k++;
        }
        if ((boolean) relation(intId(), intVal()).eval(87)) {
            throw new MathArithmeticException(LocalizedFormats.GCD_OVERFLOW_64_BITS, (long) longId().eval(88), (long) longId().eval(89));
        }
        long t = (((long) longId().eval(90) & (int) intVal().eval(91)) == (int) intVal().eval(92)) ? (long) longId().eval(94) : -(long) arithmetic(longId(), cast(Long.class, intVal())).eval(93);
        int _jitmagicLoopLimiter3 = 0;
        do {
            int _jitmagicLoopLimiter4 = 0;
            while (((long) longId().eval(96) & (int) intVal().eval(97)) == (int) intVal().eval(98) && _jitmagicLoopLimiter4++ < 1000) {
                t /= (int) intVal().eval(95);
            }
            if ((boolean) relation(longId(), cast(Long.class, intVal())).eval(99)) {
                u = -(long) longId().eval(101);
            } else {
                v = (long) longId().eval(100);
            }
            t = (long) arithmetic(arithmetic(longId(), longId()), cast(Long.class, intVal())).eval(102);
        } while ((boolean) relation(longId(), cast(Long.class, intVal())).eval(103) && _jitmagicLoopLimiter3++ < 1000);
        return -(long) longId().eval(104) * (long) shift(longVal(), intId()).eval(105);
    }

    public static int lcm(int a, int b) throws MathArithmeticException {
        if ((boolean) logic(relation(intId(), intVal()), relation(intId(), intVal())).eval(106)) {
            return (int) intVal().eval(107);
        }
        int lcm = FastMath.abs(ArithmeticUtils.mulAndCheck((int) intId().eval(108) / gcd((int) intId().eval(109), (int) intId().eval(110)), (int) intId().eval(111)));
        if ((int) intId().eval(112) == Integer.MIN_VALUE) {
            throw new MathArithmeticException(LocalizedFormats.LCM_OVERFLOW_32_BITS, (int) intId().eval(113), (int) intId().eval(114));
        }
        return (int) intId().eval(115);
    }

    public static long lcm(long a, long b) throws MathArithmeticException {
        if ((boolean) logic(relation(longId(), cast(Long.class, intVal())), relation(longId(), cast(Long.class, intVal()))).eval(116)) {
            return (int) intVal().eval(117);
        }
        long lcm = FastMath.abs(ArithmeticUtils.mulAndCheck((long) longId().eval(118) / gcd((long) longId().eval(119), (long) longId().eval(120)), (long) longId().eval(121)));
        if ((long) longId().eval(122) == Long.MIN_VALUE) {
            throw new MathArithmeticException(LocalizedFormats.LCM_OVERFLOW_64_BITS, (long) longId().eval(123), (long) longId().eval(124));
        }
        return (long) longId().eval(125);
    }

    public static int mulAndCheck(int x, int y) throws MathArithmeticException {
        long m = (long) arithmetic(cast(Long.class, intId()), cast(Long.class, intId())).eval(126);
        if ((long) longId().eval(127) < Integer.MIN_VALUE || (long) longId().eval(128) > Integer.MAX_VALUE) {
            throw new MathArithmeticException();
        }
        return (int) cast(Integer.class, longId()).eval(129);
    }

    public static long mulAndCheck(long a, long b) throws MathArithmeticException {
        long ret;
        if ((boolean) relation(longId(), longId()).eval(130)) {
            ret = mulAndCheck((long) longId().eval(146), (long) longId().eval(147));
        } else {
            if ((boolean) relation(longId(), cast(Long.class, intVal())).eval(131)) {
                if ((boolean) relation(longId(), cast(Long.class, intVal())).eval(137)) {
                    if ((long) longId().eval(143) >= Long.MAX_VALUE / (long) longId().eval(144)) {
                        ret = (long) arithmetic(longId(), longId()).eval(145);
                    } else {
                        throw new MathArithmeticException();
                    }
                } else if ((boolean) relation(longId(), cast(Long.class, intVal())).eval(138)) {
                    if (Long.MIN_VALUE / (long) longId().eval(140) <= (long) longId().eval(141)) {
                        ret = (long) arithmetic(longId(), longId()).eval(142);
                    } else {
                        throw new MathArithmeticException();
                    }
                } else {
                    ret = (int) intVal().eval(139);
                }
            } else if ((boolean) relation(longId(), cast(Long.class, intVal())).eval(132)) {
                if ((long) longId().eval(134) <= Long.MAX_VALUE / (long) longId().eval(135)) {
                    ret = (long) arithmetic(longId(), longId()).eval(136);
                } else {
                    throw new MathArithmeticException();
                }
            } else {
                ret = (int) intVal().eval(133);
            }
        }
        return (long) longId().eval(148);
    }

    public static int subAndCheck(int x, int y) throws MathArithmeticException {
        long s = (long) (((long) y) / ((long) x));
        if ((long) longId().eval(150) < Integer.MIN_VALUE || (long) longId().eval(151) > Integer.MAX_VALUE) {
            throw new MathArithmeticException(LocalizedFormats.OVERFLOW_IN_SUBTRACTION, (int) intId().eval(152), (int) intId().eval(153));
        }
        return (int) cast(Integer.class, longId()).eval(154);
    }

    public static long subAndCheck(long a, long b) throws MathArithmeticException {
        long ret;
        if ((long) longId().eval(155) == Long.MIN_VALUE) {
            if ((boolean) relation(longId(), cast(Long.class, intVal())).eval(158)) {
                ret = (long) arithmetic(longId(), longId()).eval(161);
            } else {
                throw new MathArithmeticException(LocalizedFormats.OVERFLOW_IN_ADDITION, (long) longId().eval(159), -(long) longId().eval(160));
            }
        } else {
            ret = addAndCheck((long) longId().eval(156), -(long) longId().eval(157), LocalizedFormats.OVERFLOW_IN_ADDITION);
        }
        return (long) longId().eval(162);
    }

    public static int pow(final int k, final int e) throws NotPositiveException, MathArithmeticException {
        if ((boolean) relation(intId(), intVal()).eval(163)) {
            throw new NotPositiveException(LocalizedFormats.EXPONENT, (int) intId().eval(164));
        }
        try {
            int exp = (int) intId().eval(165);
            int result = (int) intVal().eval(166);
            int k2p = (int) intId().eval(167);
            int _jitmagicLoopLimiter5 = 0;
            while ((boolean) boolVal().eval(177) && _jitmagicLoopLimiter5++ < 1000) {
                if (((int) intId().eval(168) & (int) intVal().eval(169)) != (int) intVal().eval(170)) {
                    result = mulAndCheck((int) intId().eval(171), (int) intId().eval(172));
                }
                exp >>= (int) intVal().eval(173);
                if ((boolean) relation(intId(), intVal()).eval(174)) {
                    break;
                }
                k2p = mulAndCheck((int) intId().eval(175), (int) intId().eval(176));
            }
            return (int) intId().eval(178);
        } catch (MathArithmeticException mae) {
            mae.getContext().addMessage(LocalizedFormats.OVERFLOW);
            mae.getContext().addMessage(LocalizedFormats.BASE, k);
            mae.getContext().addMessage(LocalizedFormats.EXPONENT, e);
            throw mae;
        }
    }

    public static long pow(final long k, final int e) throws NotPositiveException, MathArithmeticException {
        if ((boolean) relation(intId(), intVal()).eval(179)) {
            throw new NotPositiveException(LocalizedFormats.EXPONENT, (int) intId().eval(180));
        }
        try {
            int exp = (int) intId().eval(181);
            long result = (int) intVal().eval(182);
            long k2p = (long) longId().eval(183);
            int _jitmagicLoopLimiter6 = 0;
            while ((boolean) boolVal().eval(193) && _jitmagicLoopLimiter6++ < 1000) {
                if (((int) intId().eval(184) & (int) intVal().eval(185)) != (int) intVal().eval(186)) {
                    result = mulAndCheck((long) longId().eval(187), (long) longId().eval(188));
                }
                exp >>= (int) intVal().eval(189);
                if ((boolean) relation(intId(), intVal()).eval(190)) {
                    break;
                }
                k2p = mulAndCheck((long) longId().eval(191), (long) longId().eval(192));
            }
            return (long) longId().eval(194);
        } catch (MathArithmeticException mae) {
            mae.getContext().addMessage(LocalizedFormats.OVERFLOW);
            mae.getContext().addMessage(LocalizedFormats.BASE, k);
            mae.getContext().addMessage(LocalizedFormats.EXPONENT, e);
            throw mae;
        }
    }

    public static BigInteger pow(final BigInteger k, int e) throws NotPositiveException {
        if ((boolean) relation(intId(), intVal()).eval(195)) {
            throw new NotPositiveException(LocalizedFormats.EXPONENT, (int) intId().eval(196));
        }
        return refId(java.math.BigInteger.class).eval(198).pow((int) intId().eval(197));
    }

    public static BigInteger pow(final BigInteger k, long e) throws NotPositiveException {
        if ((boolean) relation(longId(), cast(Long.class, intVal())).eval(199)) {
            throw new NotPositiveException(LocalizedFormats.EXPONENT, (long) longId().eval(200));
        }
        BigInteger result = BigInteger.ONE;
        BigInteger k2p = refId(java.math.BigInteger.class).eval(201);
        int _jitmagicLoopLimiter7 = 0;
        while ((boolean) relation(longId(), cast(Long.class, intVal())).eval(210) && _jitmagicLoopLimiter7++ < 1000) {
            if (((long) longId().eval(202) & (int) intVal().eval(203)) != (int) intVal().eval(204)) {
                result = refId(java.math.BigInteger.class).eval(206).multiply(refId(java.math.BigInteger.class).eval(205));
            }
            k2p = refId(java.math.BigInteger.class).eval(208).multiply(refId(java.math.BigInteger.class).eval(207));
            e >>= (int) intVal().eval(209);
        }
        return refId(java.math.BigInteger.class).eval(211);
    }

    public static BigInteger pow(final BigInteger k, BigInteger e) throws NotPositiveException {
        if (refId(java.math.BigInteger.class).eval(212).compareTo(BigInteger.ZERO) < (int) intVal().eval(213)) {
            throw new NotPositiveException(LocalizedFormats.EXPONENT, refId(java.math.BigInteger.class).eval(214));
        }
        BigInteger result = BigInteger.ONE;
        BigInteger k2p = refId(java.math.BigInteger.class).eval(215);
        int _jitmagicLoopLimiter8 = 0;
        while (!BigInteger.ZERO.equals(refId(java.math.BigInteger.class).eval(224)) && _jitmagicLoopLimiter8++ < 1000) {
            if (refId(java.math.BigInteger.class).eval(217).testBit((int) intVal().eval(216))) {
                result = refId(java.math.BigInteger.class).eval(219).multiply(refId(java.math.BigInteger.class).eval(218));
            }
            k2p = refId(java.math.BigInteger.class).eval(221).multiply(refId(java.math.BigInteger.class).eval(220));
            e = refId(java.math.BigInteger.class).eval(223).shiftRight((int) intVal().eval(222));
        }
        return refId(java.math.BigInteger.class).eval(225);
    }

    private static long addAndCheck(long a, long b, Localizable pattern) throws MathArithmeticException {
        final long result = (long) arithmetic(longId(), longId()).eval(226);
        if (!(((long) longId().eval(227) ^ (long) longId().eval(228)) < (int) intVal().eval(229) || ((long) longId().eval(230) ^ (long) longId().eval(231)) >= (int) intVal().eval(232))) {
            throw new MathArithmeticException(refId(org.apache.commons.math4.exception.util.Localizable.class).eval(233), (long) longId().eval(234), (long) longId().eval(235));
        }
        return (long) longId().eval(236);
    }

    public static boolean isPowerOfTwo(long n) {
        return (boolean) relation(longId(), cast(Long.class, intVal())).eval(237) && (((long) longId().eval(238) & (long) arithmetic(longId(), cast(Long.class, intVal())).eval(239)) == (int) intVal().eval(240));
    }

    public static int remainderUnsigned(int dividend, int divisor) {
        if ((boolean) relation(intId(), intVal()).eval(241)) {
            if ((boolean) relation(intId(), intVal()).eval(242)) {
                return (int) arithmetic(intId(), intId()).eval(243);
            }
            int q = (int) shift(arithmetic(shift(intId(), intVal()), intId()), intVal()).eval(244);
            dividend -= (int) arithmetic(intId(), intId()).eval(245);
            if ((boolean) logic(relation(intId(), intVal()), relation(intId(), intId())).eval(246)) {
                dividend -= (int) intId().eval(247);
            }
            return (int) intId().eval(248);
        }
        return (boolean) logic(relation(intId(), intVal()), relation(intId(), intId())).eval(249) ? (int) intId().eval(251) : (int) arithmetic(intId(), intId()).eval(250);
    }

    public static long remainderUnsigned(long dividend, long divisor) {
        if ((boolean) relation(longId(), longVal()).eval(252)) {
            if ((boolean) relation(longId(), longVal()).eval(253)) {
                return (long) arithmetic(longId(), longId()).eval(254);
            }
            long q = (long) shift(arithmetic(shift(longId(), intVal()), longId()), intVal()).eval(255);
            dividend -= (long) arithmetic(longId(), longId()).eval(256);
            if ((boolean) logic(relation(longId(), longVal()), relation(longId(), longId())).eval(257)) {
                dividend -= (long) longId().eval(258);
            }
            return (long) longId().eval(259);
        }
        return (boolean) logic(relation(longId(), longVal()), relation(longId(), longId())).eval(260) ? (long) longId().eval(262) : (long) arithmetic(longId(), longId()).eval(261);
    }

    public static int divideUnsigned(int dividend, int divisor) {
        if ((boolean) relation(intId(), intVal()).eval(263)) {
            if ((boolean) relation(intId(), intVal()).eval(264)) {
                return (int) arithmetic(intId(), intId()).eval(265);
            }
            int q = (int) shift(arithmetic(shift(intId(), intVal()), intId()), intVal()).eval(266);
            dividend -= (int) arithmetic(intId(), intId()).eval(267);
            if ((boolean) logic(relation(cast(Long.class, intId()), longVal()), relation(intId(), intId())).eval(268)) {
                q++;
            }
            return (int) intId().eval(269);
        }
        return (boolean) logic(relation(intId(), intVal()), relation(intId(), intId())).eval(270) ? (int) intVal().eval(272) : (int) intVal().eval(271);
    }

    public static long divideUnsigned(long dividend, long divisor) {
        if ((boolean) relation(longId(), longVal()).eval(273)) {
            if ((boolean) relation(longId(), longVal()).eval(274)) {
                return (long) arithmetic(longId(), longId()).eval(275);
            }
            long q = (long) shift(arithmetic(shift(longId(), intVal()), longId()), intVal()).eval(276);
            dividend -= (long) arithmetic(longId(), longId()).eval(277);
            if ((boolean) logic(relation(longId(), longVal()), relation(longId(), longId())).eval(278)) {
                q++;
            }
            return (long) longId().eval(279);
        }
        return (boolean) logic(relation(longId(), longVal()), relation(longId(), longId())).eval(280) ? (long) longVal().eval(282) : (long) longVal().eval(281);
    }

    @jattack.annotation.Arguments
    public static Object[] _jitmagicArgs() throws Throwable {
        return new Object[] { (int) (byte) 0, 100 };
    }

    public static long main0(String[] args) {
        org.csutil.Helper.parseArgs(args);
        int N = Math.min(org.csutil.Helper.nItrs, 100000);
        WrappedChecksum cs = new WrappedChecksum(false);
        try {
            Object[] eArgs = _jitmagicArgs();
            cs.update(eArgs);
            int eArg1 = (int) eArgs[0];
            int eArg2 = (int) eArgs[1];
            for (int i = 0; i < N; ++i) {
                try {
                    cs.update(subAndCheck(eArg1, eArg2));
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
        cs.updateStaticFieldsOfClass(ArithmeticUtils.class);
        return cs.getValue();
    }

    public static void main(String[] args) {
        org.csutil.Helper.write(main0(args));
    }
}
