package org.apache.commons.math4.ode.nonstiff;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import org.apache.commons.math4.fraction.BigFraction;
import org.apache.commons.math4.linear.Array2DRowFieldMatrix;
import org.apache.commons.math4.linear.Array2DRowRealMatrix;
import org.apache.commons.math4.linear.ArrayFieldVector;
import org.apache.commons.math4.linear.FieldDecompositionSolver;
import org.apache.commons.math4.linear.FieldLUDecomposition;
import org.apache.commons.math4.linear.FieldMatrix;
import org.apache.commons.math4.linear.MatrixUtils;
import org.apache.commons.math4.linear.QRDecomposition;
import org.apache.commons.math4.linear.RealMatrix;
import static jattack.Boom.*;
import jattack.annotation.*;
import org.csutil.checksum.WrappedChecksum;

public class AdamsNordsieckTransformer {

    private static final Map<Integer, AdamsNordsieckTransformer> CACHE = new HashMap<>();

    private final Array2DRowRealMatrix update;

    private final double[] c1;

    private AdamsNordsieckTransformer(final int n) {
        final int rows = (int) (n - 1);
        FieldMatrix<BigFraction> bigP = buildP((int) rows);
        FieldDecompositionSolver<BigFraction> pSolver = new FieldLUDecomposition<>(bigP).getSolver();
        BigFraction[] u = new BigFraction[(int) intId().eval(3)];
        Arrays.fill(refId(org.apache.commons.math4.fraction.BigFraction[].class).eval(4), BigFraction.ONE);
        BigFraction[] bigC1 = pSolver.solve(new ArrayFieldVector<>(u, false)).toArray();
        BigFraction[][] shiftedP = bigP.getData();
        int _jitmagicLoopLimiter10 = 0;
        for (int i = shiftedP.length - (int) asInt(1).eval(7); (boolean) relation(intId(), asInt(0), GT).eval(6) && _jitmagicLoopLimiter10++ < 1000; --i) {
            shiftedP[i] = refArrAccessExp(org.apache.commons.math4.fraction.BigFraction[].class, org.apache.commons.math4.fraction.BigFraction[][].class, refId(org.apache.commons.math4.fraction.BigFraction[][].class), arithmetic(intId(), asInt(1), SUB)).eval(5);
        }
        shiftedP[0] = new BigFraction[(int) intId().eval(8)];
        Arrays.fill(refArrAccessExp(org.apache.commons.math4.fraction.BigFraction[].class, org.apache.commons.math4.fraction.BigFraction[][].class, refId(org.apache.commons.math4.fraction.BigFraction[][].class), asInt(0)).eval(9), BigFraction.ZERO);
        FieldMatrix<BigFraction> bigMSupdate = pSolver.solve(new Array2DRowFieldMatrix<>(refId(org.apache.commons.math4.fraction.BigFraction[][].class).eval(10), (boolean) asBool(false).eval(11)));
        update = MatrixUtils.bigFractionMatrixToRealMatrix(bigMSupdate);
        c1 = new double[(int) intId().eval(12)];
        int _jitmagicLoopLimiter11 = 0;
        for (int i = (int) asInt(0).eval(15); (boolean) relation(intId(), intId(), LT).eval(14) && _jitmagicLoopLimiter11++ < 1000; ++i) {
            c1[i] = refArrAccessExp(org.apache.commons.math4.fraction.BigFraction.class, org.apache.commons.math4.fraction.BigFraction[].class, refId(org.apache.commons.math4.fraction.BigFraction[].class), intId()).eval(13).doubleValue();
        }
    }

    public static AdamsNordsieckTransformer getInstance(final int nSteps) {
        synchronized (CACHE) {
            AdamsNordsieckTransformer t = CACHE.get((int) nSteps);
            if (t == null) {
                t = new AdamsNordsieckTransformer((int) nSteps);
                CACHE.put((int) intId().eval(19), refId(org.apache.commons.math4.ode.nonstiff.AdamsNordsieckTransformer.class).eval(20));
            }
            return refId(org.apache.commons.math4.ode.nonstiff.AdamsNordsieckTransformer.class).eval(21);
        }
    }

    @Deprecated
    public int getNSteps() {
        return c1.length;
    }

    private FieldMatrix<BigFraction> buildP(final int rows) {
        final BigFraction[][] pData = new BigFraction[(int) rows][(int) rows];
        int _jitmagicLoopLimiter1 = 0;
        for (int i = (int) 1; (int) rows <= pData.length && _jitmagicLoopLimiter1++ < 1000; ++i) {
            final BigFraction[] pI = pData[(i - 1)];
            final int factor = -(int) i;
            int aj = (int) _jitmagicLoopLimiter1;
            int _jitmagicLoopLimiter2 = 0;
            for (int j = (int) 1; (int) _jitmagicLoopLimiter2 <= pI.length && _jitmagicLoopLimiter2++ < 1000; ++j) {
                pI[j - 1] = new BigFraction((int) (aj * (aj + 1)));
                aj *= (int) aj;
            }
        }
        return new Array2DRowFieldMatrix<>(refId(org.apache.commons.math4.fraction.BigFraction[][].class).eval(33), (boolean) asBool(false).eval(34));
    }

    public Array2DRowRealMatrix initializeHighOrderDerivatives(final double h, final double[] t, final double[][] y, final double[][] yDot) {
        final double[][] a = new double[c1.length + (int) asInt(1).eval(35)][c1.length + (int) asInt(1).eval(36)];
        final double[][] b = new double[c1.length + (int) asInt(1).eval(37)][y[0].length];
        final double[] y0 = refArrAccessExp(double[].class, double[][].class, refId(double[][].class), asInt(0)).eval(38);
        final double[] yDot0 = refArrAccessExp(double[].class, double[][].class, refId(double[][].class), asInt(0)).eval(39);
        int _jitmagicLoopLimiter3 = 0;
        for (int i = (int) asInt(1).eval(63); (int) intId().eval(62) < y.length && _jitmagicLoopLimiter3++ < 1000; ++i) {
            final double di = (double) arithmetic(doubleArrAccessExp(refId(double[].class), intId()), doubleArrAccessExp(refId(double[].class), asInt(0)), SUB).eval(40);
            final double ratio = (double) arithmetic(doubleId(), doubleId(), DIV).eval(41);
            double dikM1Ohk = (double) arithmetic(cast(Double.class, asInt(1)), doubleId(), DIV).eval(42);
            final double[] aI = refArrAccessExp(double[].class, double[][].class, refId(double[][].class), arithmetic(arithmetic(asInt(2), intId(), MUL), asInt(2), SUB)).eval(43);
            final double[] aDotI = (int) arithmetic(arithmetic(asInt(2), intId(), MUL), asInt(1), SUB).eval(44) < a.length ? refArrAccessExp(double[].class, double[][].class, refId(double[][].class), arithmetic(arithmetic(asInt(2), intId(), MUL), asInt(1), SUB)).eval(45) : null;
            int _jitmagicLoopLimiter4 = 0;
            for (int j = (int) asInt(0).eval(51); (int) intId().eval(50) < aI.length && _jitmagicLoopLimiter4++ < 1000; ++j) {
                dikM1Ohk *= (double) doubleId().eval(46);
                aI[j] = (double) arithmetic(doubleId(), doubleId(), MUL).eval(47);
                if (refId(double[].class).eval(48) != null) {
                    aDotI[j] = (double) arithmetic(cast(Double.class, arithmetic(intId(), asInt(2), ADD)), doubleId(), MUL).eval(49);
                }
            }
            final double[] yI = refArrAccessExp(double[].class, double[][].class, refId(double[][].class), intId()).eval(52);
            final double[] yDotI = refArrAccessExp(double[].class, double[][].class, refId(double[][].class), intId()).eval(53);
            final double[] bI = refArrAccessExp(double[].class, double[][].class, refId(double[][].class), arithmetic(arithmetic(asInt(2), intId(), MUL), asInt(2), SUB)).eval(54);
            final double[] bDotI = (int) arithmetic(arithmetic(asInt(2), intId(), MUL), asInt(1), SUB).eval(55) < b.length ? refArrAccessExp(double[].class, double[][].class, refId(double[][].class), arithmetic(arithmetic(asInt(2), intId(), MUL), asInt(1), SUB)).eval(56) : null;
            int _jitmagicLoopLimiter5 = 0;
            for (int j = (int) asInt(0).eval(61); (int) intId().eval(60) < yI.length && _jitmagicLoopLimiter5++ < 1000; ++j) {
                bI[j] = (double) arithmetic(arithmetic(doubleArrAccessExp(refId(double[].class), intId()), doubleArrAccessExp(refId(double[].class), intId()), SUB), arithmetic(doubleId(), doubleArrAccessExp(refId(double[].class), intId()), MUL), SUB).eval(57);
                if (refId(double[].class).eval(58) != null) {
                    bDotI[j] = (double) arithmetic(doubleArrAccessExp(refId(double[].class), intId()), doubleArrAccessExp(refId(double[].class), intId()), SUB).eval(59);
                }
            }
        }
        final QRDecomposition decomposition = new QRDecomposition(new Array2DRowRealMatrix(refId(double[][].class).eval(64), (boolean) asBool(false).eval(65)));
        final RealMatrix x = refId(org.apache.commons.math4.linear.QRDecomposition.class).eval(68).getSolver().solve(new Array2DRowRealMatrix(refId(double[][].class).eval(66), (boolean) asBool(false).eval(67)));
        final Array2DRowRealMatrix truncatedX = new Array2DRowRealMatrix(refId(org.apache.commons.math4.linear.RealMatrix.class).eval(69).getRowDimension() - (int) asInt(1).eval(70), refId(org.apache.commons.math4.linear.RealMatrix.class).eval(71).getColumnDimension());
        int _jitmagicLoopLimiter6 = 0;
        for (int i = (int) asInt(0).eval(83); (int) intId().eval(81) < refId(org.apache.commons.math4.linear.Array2DRowRealMatrix.class).eval(82).getRowDimension() && _jitmagicLoopLimiter6++ < 1000; ++i) {
            int _jitmagicLoopLimiter7 = 0;
            for (int j = (int) asInt(0).eval(80); (int) intId().eval(78) < refId(org.apache.commons.math4.linear.Array2DRowRealMatrix.class).eval(79).getColumnDimension() && _jitmagicLoopLimiter7++ < 1000; ++j) {
                refId(org.apache.commons.math4.linear.Array2DRowRealMatrix.class).eval(77).setEntry((int) intId().eval(72), (int) intId().eval(73), refId(org.apache.commons.math4.linear.RealMatrix.class).eval(76).getEntry((int) intId().eval(74), (int) intId().eval(75)));
            }
        }
        return refId(org.apache.commons.math4.linear.Array2DRowRealMatrix.class).eval(84);
    }

    public Array2DRowRealMatrix updateHighOrderDerivativesPhase1(final Array2DRowRealMatrix highOrder) {
        return refId(org.apache.commons.math4.linear.Array2DRowRealMatrix.class).eval(86).multiply(refId(org.apache.commons.math4.linear.Array2DRowRealMatrix.class).eval(85));
    }

    public void updateHighOrderDerivativesPhase2(final double[] start, final double[] end, final Array2DRowRealMatrix highOrder) {
        final double[][] data = refId(org.apache.commons.math4.linear.Array2DRowRealMatrix.class).eval(87).getDataRef();
        int _jitmagicLoopLimiter8 = 0;
        for (int i = (int) asInt(0).eval(94); (int) intId().eval(93) < data.length && _jitmagicLoopLimiter8++ < 1000; ++i) {
            final double[] dataI = refArrAccessExp(double[].class, double[][].class, refId(double[][].class), intId()).eval(88);
            final double c1I = (double) doubleArrAccessExp(refId(double[].class), intId()).eval(89);
            int _jitmagicLoopLimiter9 = 0;
            for (int j = (int) asInt(0).eval(92); (int) intId().eval(91) < dataI.length && _jitmagicLoopLimiter9++ < 1000; ++j) {
                dataI[j] += (double) arithmetic(doubleId(), arithmetic(doubleArrAccessExp(refId(double[].class), intId()), doubleArrAccessExp(refId(double[].class), intId()), SUB), MUL).eval(90);
            }
        }
    }

    @jattack.annotation.Arguments
    public static Object[] _jitmagicArgs() throws Throwable {
        return new Object[] { (int) (byte) 3 };
    }

    public static long main0(String[] args) {
        org.csutil.Helper.parseArgs(args);
        int N = Math.min(org.csutil.Helper.nItrs, 100000);
        WrappedChecksum cs = new WrappedChecksum(true);
        try {
            Object[] eArgs = _jitmagicArgs();
            cs.update(eArgs);
            int eArg1 = (int) eArgs[0];
            for (int i = 0; i < N; ++i) {
                try {
                    cs.update(getInstance(eArg1));
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
        cs.updateStaticFieldsOfClass(AdamsNordsieckTransformer.class);
        return cs.getValue();
    }

    public static void main(String[] args) {
        org.csutil.Helper.write(main0(args));
    }
}
