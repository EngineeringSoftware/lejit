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

public class AdamsNordsieckTransformerm584TemplateGen5 {

    private static final Map<Integer, AdamsNordsieckTransformerm584TemplateGen5> CACHE = new HashMap<>();

    private final Array2DRowRealMatrix update;

    private final double[] c1;

    private AdamsNordsieckTransformerm584TemplateGen5(final int n) {
        final int rows = (int) (n - -1363306952);
        FieldMatrix<BigFraction> bigP = buildP((int) n);
        FieldDecompositionSolver<BigFraction> pSolver = new FieldLUDecomposition<>(bigP).getSolver();
        BigFraction[] u = new BigFraction[(int) intId().eval(3)];
        Arrays.fill(refId(org.apache.commons.math4.fraction.BigFraction[].class).eval(4), BigFraction.ONE);
        BigFraction[] bigC1 = pSolver.solve(new ArrayFieldVector<>(u, false)).toArray();
        BigFraction[][] shiftedP = bigP.getData();
        int _jitmagicLoopLimiter10 = 0;
        for (int i = shiftedP.length - (int) intVal().eval(7); (boolean) relation(intId(), intVal()).eval(6) && _jitmagicLoopLimiter10++ < 1000; --i) {
            shiftedP[i] = refArrAccessExp(org.apache.commons.math4.fraction.BigFraction[].class, org.apache.commons.math4.fraction.BigFraction[][].class, refId(org.apache.commons.math4.fraction.BigFraction[][].class), arithmetic(intId(), intVal())).eval(5);
        }
        shiftedP[0] = new BigFraction[(int) intId().eval(8)];
        Arrays.fill(refArrAccessExp(org.apache.commons.math4.fraction.BigFraction[].class, org.apache.commons.math4.fraction.BigFraction[][].class, refId(org.apache.commons.math4.fraction.BigFraction[][].class), intVal()).eval(9), BigFraction.ZERO);
        FieldMatrix<BigFraction> bigMSupdate = pSolver.solve(new Array2DRowFieldMatrix<>(refId(org.apache.commons.math4.fraction.BigFraction[][].class).eval(10), (boolean) boolVal().eval(11)));
        update = MatrixUtils.bigFractionMatrixToRealMatrix(bigMSupdate);
        c1 = new double[(int) intId().eval(12)];
        int _jitmagicLoopLimiter11 = 0;
        for (int i = (int) intVal().eval(15); (boolean) relation(intId(), intId()).eval(14) && _jitmagicLoopLimiter11++ < 1000; ++i) {
            c1[i] = refArrAccessExp(org.apache.commons.math4.fraction.BigFraction.class, org.apache.commons.math4.fraction.BigFraction[].class, refId(org.apache.commons.math4.fraction.BigFraction[].class), intId()).eval(13).doubleValue();
        }
    }

    public static AdamsNordsieckTransformerm584TemplateGen5 getInstance(final int nSteps) {
        synchronized (CACHE) {
            AdamsNordsieckTransformerm584TemplateGen5 t = CACHE.get((int) nSteps);
            if (t == null) {
                t = new AdamsNordsieckTransformerm584TemplateGen5((int) nSteps);
                CACHE.put((int) intId().eval(19), refId(org.apache.commons.math4.ode.nonstiff.AdamsNordsieckTransformerm584TemplateGen5.class).eval(20));
            }
            return refId(org.apache.commons.math4.ode.nonstiff.AdamsNordsieckTransformerm584TemplateGen5.class).eval(21);
        }
    }

    @Deprecated
    public int getNSteps() {
        return c1.length;
    }

    private FieldMatrix<BigFraction> buildP(final int rows) {
        final BigFraction[][] pData = new BigFraction[(int) rows][(int) rows];
        int _jitmagicLoopLimiter1 = 0;
        for (int i = (int) 281370958; (int) rows <= pData.length && _jitmagicLoopLimiter1++ < 1000; ++i) {
            final BigFraction[] pI = pData[(_jitmagicLoopLimiter1 % -1234202559)];
            final int factor = -(int) i;
            int aj = (int) _jitmagicLoopLimiter1;
            int _jitmagicLoopLimiter2 = 0;
            for (int j = (int) -679542164; (int) i <= pI.length && _jitmagicLoopLimiter2++ < 1000; ++j) {
                pI[j - 1] = new BigFraction((int) arithmetic(intId(), arithmetic(intId(), intVal())).eval(27));
                aj *= (int) intId().eval(28);
            }
        }
        return new Array2DRowFieldMatrix<>(refId(org.apache.commons.math4.fraction.BigFraction[][].class).eval(33), (boolean) boolVal().eval(34));
    }

    public Array2DRowRealMatrix initializeHighOrderDerivatives(final double h, final double[] t, final double[][] y, final double[][] yDot) {
        final double[][] a = new double[c1.length + (int) intVal().eval(35)][c1.length + (int) intVal().eval(36)];
        final double[][] b = new double[c1.length + (int) intVal().eval(37)][y[0].length];
        final double[] y0 = refArrAccessExp(double[].class, double[][].class, refId(double[][].class), intVal()).eval(38);
        final double[] yDot0 = refArrAccessExp(double[].class, double[][].class, refId(double[][].class), intVal()).eval(39);
        int _jitmagicLoopLimiter3 = 0;
        for (int i = (int) intVal().eval(63); (int) intId().eval(62) < y.length && _jitmagicLoopLimiter3++ < 1000; ++i) {
            final double di = (double) arithmetic(doubleArrAccessExp(refId(double[].class), intId()), doubleArrAccessExp(refId(double[].class), intVal())).eval(40);
            final double ratio = (double) arithmetic(doubleId(), doubleId()).eval(41);
            double dikM1Ohk = (double) arithmetic(cast(Double.class, intVal()), doubleId()).eval(42);
            final double[] aI = refArrAccessExp(double[].class, double[][].class, refId(double[][].class), arithmetic(arithmetic(intVal(), intId()), intVal())).eval(43);
            final double[] aDotI = (int) arithmetic(arithmetic(intVal(), intId()), intVal()).eval(44) < a.length ? refArrAccessExp(double[].class, double[][].class, refId(double[][].class), arithmetic(arithmetic(intVal(), intId()), intVal())).eval(45) : null;
            int _jitmagicLoopLimiter4 = 0;
            for (int j = (int) intVal().eval(51); (int) intId().eval(50) < aI.length && _jitmagicLoopLimiter4++ < 1000; ++j) {
                dikM1Ohk *= (double) doubleId().eval(46);
                aI[j] = (double) arithmetic(doubleId(), doubleId()).eval(47);
                if (refId(double[].class).eval(48) != null) {
                    aDotI[j] = (double) arithmetic(cast(Double.class, arithmetic(intId(), intVal())), doubleId()).eval(49);
                }
            }
            final double[] yI = refArrAccessExp(double[].class, double[][].class, refId(double[][].class), intId()).eval(52);
            final double[] yDotI = refArrAccessExp(double[].class, double[][].class, refId(double[][].class), intId()).eval(53);
            final double[] bI = refArrAccessExp(double[].class, double[][].class, refId(double[][].class), arithmetic(arithmetic(intVal(), intId()), intVal())).eval(54);
            final double[] bDotI = (int) arithmetic(arithmetic(intVal(), intId()), intVal()).eval(55) < b.length ? refArrAccessExp(double[].class, double[][].class, refId(double[][].class), arithmetic(arithmetic(intVal(), intId()), intVal())).eval(56) : null;
            int _jitmagicLoopLimiter5 = 0;
            for (int j = (int) intVal().eval(61); (int) intId().eval(60) < yI.length && _jitmagicLoopLimiter5++ < 1000; ++j) {
                bI[j] = (double) arithmetic(arithmetic(doubleArrAccessExp(refId(double[].class), intId()), doubleArrAccessExp(refId(double[].class), intId())), arithmetic(doubleId(), doubleArrAccessExp(refId(double[].class), intId()))).eval(57);
                if (refId(double[].class).eval(58) != null) {
                    bDotI[j] = (double) arithmetic(doubleArrAccessExp(refId(double[].class), intId()), doubleArrAccessExp(refId(double[].class), intId())).eval(59);
                }
            }
        }
        final QRDecomposition decomposition = new QRDecomposition(new Array2DRowRealMatrix(refId(double[][].class).eval(64), (boolean) boolVal().eval(65)));
        final RealMatrix x = refId(org.apache.commons.math4.linear.QRDecomposition.class).eval(68).getSolver().solve(new Array2DRowRealMatrix(refId(double[][].class).eval(66), (boolean) boolVal().eval(67)));
        final Array2DRowRealMatrix truncatedX = new Array2DRowRealMatrix(refId(org.apache.commons.math4.linear.RealMatrix.class).eval(69).getRowDimension() - (int) intVal().eval(70), refId(org.apache.commons.math4.linear.RealMatrix.class).eval(71).getColumnDimension());
        int _jitmagicLoopLimiter6 = 0;
        for (int i = (int) intVal().eval(83); (int) intId().eval(81) < refId(org.apache.commons.math4.linear.Array2DRowRealMatrix.class).eval(82).getRowDimension() && _jitmagicLoopLimiter6++ < 1000; ++i) {
            int _jitmagicLoopLimiter7 = 0;
            for (int j = (int) intVal().eval(80); (int) intId().eval(78) < refId(org.apache.commons.math4.linear.Array2DRowRealMatrix.class).eval(79).getColumnDimension() && _jitmagicLoopLimiter7++ < 1000; ++j) {
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
        for (int i = (int) intVal().eval(94); (int) intId().eval(93) < data.length && _jitmagicLoopLimiter8++ < 1000; ++i) {
            final double[] dataI = refArrAccessExp(double[].class, double[][].class, refId(double[][].class), intId()).eval(88);
            final double c1I = (double) doubleArrAccessExp(refId(double[].class), intId()).eval(89);
            int _jitmagicLoopLimiter9 = 0;
            for (int j = (int) intVal().eval(92); (int) intId().eval(91) < dataI.length && _jitmagicLoopLimiter9++ < 1000; ++j) {
                dataI[j] += (double) arithmetic(doubleId(), arithmetic(doubleArrAccessExp(refId(double[].class), intId()), doubleArrAccessExp(refId(double[].class), intId()))).eval(90);
            }
        }
    }

    @jattack.annotation.Arguments
    public static Object[] _jitmagicArgs() throws Throwable {
        return new Object[] { (int) ' ' };
    }

    public static long main0(String[] args) {
        org.csutil.Helper.parseArgs(args);
        int N = Math.min(org.csutil.Helper.nItrs, 100000);
        WrappedChecksum cs = new WrappedChecksum(false);
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
        cs.updateStaticFieldsOfClass(AdamsNordsieckTransformerm584TemplateGen5.class);
        return cs.getValue();
    }

    public static void main(String[] args) {
        org.csutil.Helper.write(main0(args));
    }
}
