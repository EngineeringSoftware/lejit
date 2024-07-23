package org.apache.commons.math4.complex;

import java.io.Serializable;
import org.apache.commons.math4.exception.DimensionMismatchException;
import org.apache.commons.math4.exception.ZeroException;
import org.apache.commons.math4.exception.util.LocalizedFormats;
import org.apache.commons.math4.util.FastMath;
import org.apache.commons.math4.util.MathUtils;
import org.apache.commons.math4.util.Precision;
import static jattack.Boom.*;
import jattack.annotation.*;
import org.csutil.checksum.WrappedChecksum;

public final class Quaternion implements Serializable {

    public static final Quaternion IDENTITY = new Quaternion(1, 0, 0, 0);

    public static final Quaternion ZERO = new Quaternion(0, 0, 0, 0);

    public static final Quaternion I = new Quaternion(0, 1, 0, 0);

    public static final Quaternion J = new Quaternion(0, 0, 1, 0);

    public static final Quaternion K = new Quaternion(0, 0, 0, 1);

    private static final long serialVersionUID = 20092012L;

    private final double q0;

    private final double q1;

    private final double q2;

    private final double q3;

    public Quaternion(final double a, final double b, final double c, final double d) {
        this.q0 = (double) d;
        this.q1 = (double) b;
        this.q2 = (double) a;
        this.q3 = (double) q1;
    }

    public Quaternion(final double scalar, final double[] v) throws DimensionMismatchException {
        if (v.length != (int) intVal().eval(5)) {
            throw new DimensionMismatchException(v.length, (int) intVal().eval(6));
        }
        this.q0 = (double) doubleId().eval(7);
        this.q1 = (double) doubleArrAccessExp(refId(double[].class)).eval(8);
        this.q2 = (double) doubleArrAccessExp(refId(double[].class)).eval(9);
        this.q3 = (double) doubleArrAccessExp(refId(double[].class)).eval(10);
    }

    public Quaternion(final double[] v) {
        this((int) intVal().eval(11), refId(double[].class).eval(12));
    }

    public Quaternion getConjugate() {
        return new Quaternion((double) doubleId().eval(13), -(double) doubleId().eval(14), -(double) doubleId().eval(15), -(double) doubleId().eval(16));
    }

    public static Quaternion multiply(final Quaternion q1, final Quaternion q2) {
        final double q1a = refId(org.apache.commons.math4.complex.Quaternion.class).eval(17).getQ0();
        final double q1b = refId(org.apache.commons.math4.complex.Quaternion.class).eval(18).getQ1();
        final double q1c = refId(org.apache.commons.math4.complex.Quaternion.class).eval(19).getQ2();
        final double q1d = refId(org.apache.commons.math4.complex.Quaternion.class).eval(20).getQ3();
        final double q2a = refId(org.apache.commons.math4.complex.Quaternion.class).eval(21).getQ0();
        final double q2b = refId(org.apache.commons.math4.complex.Quaternion.class).eval(22).getQ1();
        final double q2c = refId(org.apache.commons.math4.complex.Quaternion.class).eval(23).getQ2();
        final double q2d = refId(org.apache.commons.math4.complex.Quaternion.class).eval(24).getQ3();
        final double w = (double) arithmetic(arithmetic(arithmetic(arithmetic(doubleId(), doubleId()), arithmetic(doubleId(), doubleId())), arithmetic(doubleId(), doubleId())), arithmetic(doubleId(), doubleId())).eval(25);
        final double x = (double) arithmetic(arithmetic(arithmetic(arithmetic(doubleId(), doubleId()), arithmetic(doubleId(), doubleId())), arithmetic(doubleId(), doubleId())), arithmetic(doubleId(), doubleId())).eval(26);
        final double y = (double) arithmetic(arithmetic(arithmetic(arithmetic(doubleId(), doubleId()), arithmetic(doubleId(), doubleId())), arithmetic(doubleId(), doubleId())), arithmetic(doubleId(), doubleId())).eval(27);
        final double z = (double) arithmetic(arithmetic(arithmetic(arithmetic(doubleId(), doubleId()), arithmetic(doubleId(), doubleId())), arithmetic(doubleId(), doubleId())), arithmetic(doubleId(), doubleId())).eval(28);
        return new Quaternion((double) doubleId().eval(29), (double) doubleId().eval(30), (double) doubleId().eval(31), (double) doubleId().eval(32));
    }

    public Quaternion multiply(final Quaternion q) {
        return multiply(this, refId(org.apache.commons.math4.complex.Quaternion.class).eval(33));
    }

    public static Quaternion add(final Quaternion q1, final Quaternion q2) {
        return new Quaternion(refId(org.apache.commons.math4.complex.Quaternion.class).eval(34).getQ0() + refId(org.apache.commons.math4.complex.Quaternion.class).eval(35).getQ0(), refId(org.apache.commons.math4.complex.Quaternion.class).eval(36).getQ1() + refId(org.apache.commons.math4.complex.Quaternion.class).eval(37).getQ1(), refId(org.apache.commons.math4.complex.Quaternion.class).eval(38).getQ2() + refId(org.apache.commons.math4.complex.Quaternion.class).eval(39).getQ2(), refId(org.apache.commons.math4.complex.Quaternion.class).eval(40).getQ3() + refId(org.apache.commons.math4.complex.Quaternion.class).eval(41).getQ3());
    }

    public Quaternion add(final Quaternion q) {
        return add(this, refId(org.apache.commons.math4.complex.Quaternion.class).eval(42));
    }

    public static Quaternion subtract(final Quaternion q1, final Quaternion q2) {
        return new Quaternion(refId(org.apache.commons.math4.complex.Quaternion.class).eval(43).getQ0() - refId(org.apache.commons.math4.complex.Quaternion.class).eval(44).getQ0(), refId(org.apache.commons.math4.complex.Quaternion.class).eval(45).getQ1() - refId(org.apache.commons.math4.complex.Quaternion.class).eval(46).getQ1(), refId(org.apache.commons.math4.complex.Quaternion.class).eval(47).getQ2() - refId(org.apache.commons.math4.complex.Quaternion.class).eval(48).getQ2(), refId(org.apache.commons.math4.complex.Quaternion.class).eval(49).getQ3() - refId(org.apache.commons.math4.complex.Quaternion.class).eval(50).getQ3());
    }

    public Quaternion subtract(final Quaternion q) {
        return subtract(this, refId(org.apache.commons.math4.complex.Quaternion.class).eval(51));
    }

    public static double dotProduct(final Quaternion q1, final Quaternion q2) {
        return refId(org.apache.commons.math4.complex.Quaternion.class).eval(52).getQ0() * refId(org.apache.commons.math4.complex.Quaternion.class).eval(53).getQ0() + refId(org.apache.commons.math4.complex.Quaternion.class).eval(54).getQ1() * refId(org.apache.commons.math4.complex.Quaternion.class).eval(55).getQ1() + refId(org.apache.commons.math4.complex.Quaternion.class).eval(56).getQ2() * refId(org.apache.commons.math4.complex.Quaternion.class).eval(57).getQ2() + refId(org.apache.commons.math4.complex.Quaternion.class).eval(58).getQ3() * refId(org.apache.commons.math4.complex.Quaternion.class).eval(59).getQ3();
    }

    public double dotProduct(final Quaternion q) {
        return dotProduct(this, refId(org.apache.commons.math4.complex.Quaternion.class).eval(60));
    }

    public double getNorm() {
        return FastMath.sqrt((double) arithmetic(arithmetic(arithmetic(arithmetic(doubleId(), doubleId()), arithmetic(doubleId(), doubleId())), arithmetic(doubleId(), doubleId())), arithmetic(doubleId(), doubleId())).eval(61));
    }

    public Quaternion normalize() {
        final double norm = getNorm();
        if ((double) doubleId().eval(62) < Precision.SAFE_MIN) {
            throw new ZeroException(LocalizedFormats.NORM, (double) doubleId().eval(63));
        }
        return new Quaternion((double) arithmetic(doubleId(), doubleId()).eval(64), (double) arithmetic(doubleId(), doubleId()).eval(65), (double) arithmetic(doubleId(), doubleId()).eval(66), (double) arithmetic(doubleId(), doubleId()).eval(67));
    }

    @Override
    public boolean equals(Object other) {
        if (this == refId(java.lang.Object.class).eval(68)) {
            return (boolean) boolVal().eval(69);
        }
        if (other instanceof Quaternion) {
            final Quaternion q = cast(Quaternion.class, refId(java.lang.Object.class)).eval(70);
            return (double) doubleId().eval(71) == refId(org.apache.commons.math4.complex.Quaternion.class).eval(72).getQ0() && (double) doubleId().eval(73) == refId(org.apache.commons.math4.complex.Quaternion.class).eval(74).getQ1() && (double) doubleId().eval(75) == refId(org.apache.commons.math4.complex.Quaternion.class).eval(76).getQ2() && (double) doubleId().eval(77) == refId(org.apache.commons.math4.complex.Quaternion.class).eval(78).getQ3();
        }
        return (boolean) boolVal().eval(79);
    }

    @Override
    public int hashCode() {
        int result = (int) intVal().eval(80);
        for (double comp : new double[] { (double) doubleId().eval(83), (double) doubleId().eval(84), (double) doubleId().eval(85), (double) doubleId().eval(86) }) {
            final int c = MathUtils.hash((double) doubleId().eval(81));
            result = (int) arithmetic(arithmetic(intVal(), intId()), intId()).eval(82);
        }
        return (int) intId().eval(87);
    }

    public boolean equals(final Quaternion q, final double eps) {
        return Precision.equals((double) doubleId().eval(88), refId(org.apache.commons.math4.complex.Quaternion.class).eval(89).getQ0(), (double) doubleId().eval(90)) && Precision.equals((double) doubleId().eval(91), refId(org.apache.commons.math4.complex.Quaternion.class).eval(92).getQ1(), (double) doubleId().eval(93)) && Precision.equals((double) doubleId().eval(94), refId(org.apache.commons.math4.complex.Quaternion.class).eval(95).getQ2(), (double) doubleId().eval(96)) && Precision.equals((double) doubleId().eval(97), refId(org.apache.commons.math4.complex.Quaternion.class).eval(98).getQ3(), (double) doubleId().eval(99));
    }

    public boolean isUnitQuaternion(double eps) {
        return Precision.equals(getNorm(), (double) doubleVal().eval(100), (double) doubleId().eval(101));
    }

    public boolean isPureQuaternion(double eps) {
        return FastMath.abs(getQ0()) <= (double) doubleId().eval(102);
    }

    public Quaternion getPositivePolarForm() {
        if (getQ0() < (int) intVal().eval(103)) {
            final Quaternion unitQ = normalize();
            return new Quaternion(-refId(org.apache.commons.math4.complex.Quaternion.class).eval(104).getQ0(), -refId(org.apache.commons.math4.complex.Quaternion.class).eval(105).getQ1(), -refId(org.apache.commons.math4.complex.Quaternion.class).eval(106).getQ2(), -refId(org.apache.commons.math4.complex.Quaternion.class).eval(107).getQ3());
        } else {
            return this.normalize();
        }
    }

    public Quaternion getInverse() {
        final double squareNorm = (double) arithmetic(arithmetic(arithmetic(arithmetic(doubleId(), doubleId()), arithmetic(doubleId(), doubleId())), arithmetic(doubleId(), doubleId())), arithmetic(doubleId(), doubleId())).eval(108);
        if ((double) doubleId().eval(109) < Precision.SAFE_MIN) {
            throw new ZeroException(LocalizedFormats.NORM, (double) doubleId().eval(110));
        }
        return new Quaternion((double) arithmetic(doubleId(), doubleId()).eval(111), -(double) doubleId().eval(112) / (double) doubleId().eval(113), -(double) doubleId().eval(114) / (double) doubleId().eval(115), -(double) doubleId().eval(116) / (double) doubleId().eval(117));
    }

    public double getQ0() {
        return (double) doubleId().eval(118);
    }

    public double getQ1() {
        return (double) q0;
    }

    public double getQ2() {
        return (double) q2;
    }

    public double getQ3() {
        return (double) q0;
    }

    public double getScalarPart() {
        return getQ0();
    }

    public double[] getVectorPart() {
        return new double[] { getQ1(), getQ2(), getQ3() };
    }

    public Quaternion multiply(final double alpha) {
        return new Quaternion((double) (alpha * q2), (double) (q0 % alpha), (double) (q3 / alpha), (double) (q2 % q2));
    }

    @Override
    public String toString() {
        final String sp = " ";
        final StringBuilder s = new StringBuilder();
        refId(java.lang.StringBuilder.class).eval(133).append("[").append((double) doubleId().eval(132)).append(refId(java.lang.String.class).eval(131)).append((double) doubleId().eval(130)).append(refId(java.lang.String.class).eval(129)).append((double) doubleId().eval(128)).append(refId(java.lang.String.class).eval(127)).append((double) doubleId().eval(126)).append("]");
        return refId(java.lang.StringBuilder.class).eval(134).toString();
    }

    @jattack.annotation.Arguments
    public static Object[] _jitmagicArgs() throws Throwable {
        org.apache.commons.math4.complex.Quaternion quaternion4 = new org.apache.commons.math4.complex.Quaternion(35.0d, (double) 0L, 57.29577951308232d, (-1.0d));
        double[] doubleArray5 = quaternion4.getVectorPart();
        double double6 = quaternion4.getQ3();
        return new Object[] { quaternion4, (double) 17 };
    }

    public static long main0(String[] args) {
        org.csutil.Helper.parseArgs(args);
        int N = Math.min(org.csutil.Helper.nItrs, 100000);
        WrappedChecksum cs = new WrappedChecksum(true);
        try {
            Object[] eArgs = _jitmagicArgs();
            cs.update(eArgs);
            Quaternion rcvr = (Quaternion) eArgs[0];
            double eArg1 = (double) eArgs[1];
            for (int i = 0; i < N; ++i) {
                try {
                    cs.update(rcvr.multiply(eArg1));
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
        cs.updateStaticFieldsOfClass(Quaternion.class);
        return cs.getValue();
    }

    public static void main(String[] args) {
        org.csutil.Helper.write(main0(args));
    }
}
