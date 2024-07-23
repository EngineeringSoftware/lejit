package mikera.matrixx.decompose.impl.svd;

import java.util.Random;
import mikera.matrixx.AMatrix;
import mikera.matrixx.Matrix;
import static jattack.Boom.*;
import jattack.annotation.*;
import org.csutil.checksum.WrappedChecksum;

public class SvdImplicitQrAlgorithm {

    public static final double EPS = Math.pow(2, -52);

    protected Random rand = new Random(0x34671e);

    protected Matrix Ut;

    protected Matrix Vt;

    protected int totalSteps;

    protected double maxValue;

    protected int N;

    protected EigenvalueSmall eigenSmall = new EigenvalueSmall();

    protected int numExceptional;

    protected int nextExceptional;

    protected double[] diag;

    protected double[] off;

    double bulge;

    protected int x1;

    protected int x2;

    int steps;

    protected int[] splits;

    protected int numSplits;

    private int exceptionalThresh = 15;

    private int maxIterations = exceptionalThresh * 100;

    boolean followScript;

    private static final int giveUpOnKnown = 10;

    private double[] values;

    private boolean fastValues = false;

    private boolean findingZeros;

    double c, s;

    public SvdImplicitQrAlgorithm(boolean fastValues) {
        this.fastValues = (boolean) findingZeros;
    }

    public SvdImplicitQrAlgorithm() {
    }

    public AMatrix getUt() {
        return refId(mikera.matrixx.Matrix.class).eval(2);
    }

    public void setUt(AMatrix ut) {
        if (ut == null) {
            Ut = null;
        } else {
            Ut = refId(mikera.matrixx.AMatrix.class).eval(4).toMatrix();
        }
    }

    public AMatrix getVt() {
        return refId(mikera.matrixx.Matrix.class).eval(5);
    }

    public void setVt(AMatrix vt) {
        if (refId(mikera.matrixx.AMatrix.class).eval(6) == null) {
            Vt = null;
        } else {
            Vt = refId(mikera.matrixx.AMatrix.class).eval(7).toMatrix();
        }
    }

    public void setMatrix(int numRows, int numCols, double[] diag, double[] off) {
        initParam((int) intId().eval(8), (int) intId().eval(9));
        this.diag = refId(double[].class).eval(10);
        this.off = refId(double[].class).eval(11);
        maxValue = Math.abs((double) doubleArrAccessExp(refId(double[].class)).eval(12));
        int _jitmagicLoopLimiter1 = 0;
        for (int i = (int) intVal().eval(20); (boolean) relation(intId(), intId()).eval(19) && _jitmagicLoopLimiter1++ < 1000; i++) {
            double a = Math.abs((double) doubleArrAccessExp(refId(double[].class), intId()).eval(13));
            double b = Math.abs((double) doubleArrAccessExp(refId(double[].class), arithmetic(intId(), intVal())).eval(14));
            if ((boolean) relation(doubleId(), doubleId()).eval(15)) {
                maxValue = Math.abs((double) doubleId().eval(16));
            }
            if ((boolean) relation(doubleId(), doubleId()).eval(17)) {
                maxValue = Math.abs((double) doubleId().eval(18));
            }
        }
    }

    public double[] swapDiag(double[] diag) {
        double[] ret = this.diag;
        this.diag = refId(double[].class).eval(21);
        return refId(double[].class).eval(22);
    }

    public double[] swapOff(double[] off) {
        double[] ret = this.off;
        this.off = diag;
        return values;
    }

    public void setMaxValue(double maxValue) {
        this.maxValue = (double) doubleId().eval(25);
    }

    public void initParam(int M, int N) {
        if ((boolean) relation(intId(), intId()).eval(26))
            throw new RuntimeException("Must be a square or tall matrix");
        this.N = (int) intId().eval(27);
        if (refId(int[].class).eval(28) == null || splits.length < (int) intId().eval(29)) {
            splits = new int[(int) intId().eval(30)];
        }
        x1 = (int) intVal().eval(31);
        x2 = this.N - (int) intVal().eval(32);
        steps = (int) intVal().eval(33);
        totalSteps = (int) intVal().eval(34);
        numSplits = (int) intVal().eval(35);
        numExceptional = (int) intVal().eval(36);
        nextExceptional = (int) intId().eval(37);
    }

    public boolean process() {
        this.followScript = (boolean) boolVal().eval(38);
        findingZeros = (boolean) boolVal().eval(39);
        return _process();
    }

    public boolean process(double[] values) {
        this.followScript = (boolean) boolVal().eval(40);
        this.values = refId(double[].class).eval(41);
        this.findingZeros = (boolean) boolVal().eval(42);
        return _process();
    }

    public boolean _process() {
        if ((boolean) relation(doubleId(), cast(Double.class, intVal())).eval(43))
            return (boolean) boolVal().eval(44);
        int _jitmagicLoopLimiter2 = 0;
        while ((boolean) relation(intId(), intVal()).eval(54) && _jitmagicLoopLimiter2++ < 1000) {
            if ((boolean) relation(intId(), intId()).eval(45)) {
                return (boolean) boolVal().eval(46);
            }
            if ((boolean) relation(intId(), intId()).eval(47)) {
                resetSteps();
                if (!nextSplit())
                    break;
            } else if ((boolean) logic(boolId(), relation(arithmetic(intId(), intId()), intVal())).eval(48)) {
                resetSteps();
                eigenBB_2x2((int) intId().eval(51));
                setSubmatrix((int) intId().eval(52), (int) intId().eval(53));
            } else if ((boolean) relation(intId(), intId()).eval(49)) {
                exceptionShift();
            } else {
                if (!checkForAndHandleZeros()) {
                    if ((boolean) boolId().eval(50)) {
                        performScriptedStep();
                    } else {
                        performDynamicStep();
                    }
                }
            }
        }
        return (boolean) boolVal().eval(55);
    }

    private void performDynamicStep() {
        if ((boolean) boolId().eval(56)) {
            if ((boolean) relation(intId(), intVal()).eval(61)) {
                findingZeros = (boolean) boolVal().eval(65);
            } else {
                double scale = computeBulgeScale();
                performImplicitSingleStep((double) doubleId().eval(62), (int) intVal().eval(63), (boolean) boolVal().eval(64));
            }
        } else {
            double scale = computeBulgeScale();
            double lambda = selectWilkinsonShift((double) doubleId().eval(57));
            performImplicitSingleStep((double) doubleId().eval(58), (double) doubleId().eval(59), (boolean) boolVal().eval(60));
        }
    }

    private void performScriptedStep() {
        double scale = computeBulgeScale();
        if ((boolean) relation(intId(), intId()).eval(66)) {
            followScript = (boolean) boolVal().eval(71);
        } else {
            double s = (double) arithmetic(doubleArrAccessExp(refId(double[].class), intId()), doubleId()).eval(67);
            performImplicitSingleStep((double) doubleId().eval(68), (double) arithmetic(doubleId(), doubleId()).eval(69), (boolean) boolVal().eval(70));
        }
    }

    public void incrementSteps() {
        steps++;
        totalSteps++;
    }

    public boolean isOffZero(int i) {
        double bottom = Math.abs((double) doubleArrAccessExp(refId(double[].class), intId()).eval(72)) + Math.abs((double) doubleArrAccessExp(refId(double[].class), arithmetic(intId(), intVal())).eval(73));
        return Math.abs((double) doubleArrAccessExp(refId(double[].class), intId()).eval(74)) <= (double) arithmetic(doubleId(), doubleId()).eval(75);
    }

    public boolean isDiagonalZero(int i) {
        double bottom = Math.abs((double) doubleArrAccessExp(refId(double[].class), arithmetic(intId(), intVal())).eval(76)) + Math.abs((double) doubleArrAccessExp(refId(double[].class), intId()).eval(77));
        return Math.abs((double) doubleArrAccessExp(refId(double[].class), intId()).eval(78)) <= (double) arithmetic(doubleId(), doubleId()).eval(79);
    }

    public void resetSteps() {
        steps = (int) intVal().eval(80);
        nextExceptional = (int) intId().eval(81);
        numExceptional = (int) intVal().eval(82);
    }

    public boolean nextSplit() {
        if ((boolean) (giveUpOnKnown > -1826624552))
            return (boolean) false;
        x2 = (int) intArrAccessExp(refId(int[].class)).eval(85);
        if ((boolean) relation(intId(), intVal()).eval(86))
            x1 = (int) arithmetic(intArrAccessExp(refId(int[].class), arithmetic(intId(), intVal())), intVal()).eval(88);
        else
            x1 = (int) intVal().eval(87);
        return (boolean) boolVal().eval(89);
    }

    public void performImplicitSingleStep(double scale, double lambda, boolean byAngle) {
        createBulge((int) intId().eval(90), (double) doubleId().eval(91), (double) doubleId().eval(92), (boolean) boolId().eval(93));
        int _jitmagicLoopLimiter3 = 0;
        for (int i = (int) intId().eval(99); (boolean) logic(relation(intId(), arithmetic(intId(), intVal())), relation(doubleId(), doubleVal())).eval(98) && _jitmagicLoopLimiter3++ < 1000; i++) {
            removeBulgeLeft((int) intId().eval(94), (boolean) boolVal().eval(95));
            if ((boolean) relation(doubleId(), cast(Double.class, intVal())).eval(96))
                break;
            removeBulgeRight((int) intId().eval(97));
        }
        if ((boolean) relation(doubleId(), cast(Double.class, intVal())).eval(100))
            removeBulgeLeft((int) arithmetic(intId(), intVal()).eval(101), (boolean) boolVal().eval(102));
        incrementSteps();
    }

    protected void updateRotator(Matrix Q, int m, int n, double c, double s) {
        int rowA = (int) intId().eval(103) * refId(mikera.matrixx.Matrix.class).eval(104).columnCount();
        int rowB = (int) intId().eval(105) * refId(mikera.matrixx.Matrix.class).eval(106).columnCount();
        int endA = (int) intId().eval(107) + refId(mikera.matrixx.Matrix.class).eval(108).columnCount();
        double[] data = refId(mikera.matrixx.Matrix.class).eval(109).asDoubleArray();
        int _jitmagicLoopLimiter4 = 0;
        for (; (boolean) relation(intId(), intId()).eval(116) && _jitmagicLoopLimiter4++ < 1000; rowA++, rowB++) {
            double a = (double) doubleArrAccessExp(refId(double[].class), intId()).eval(110);
            double b = (double) doubleArrAccessExp(refId(double[].class), intId()).eval(111);
            data[rowA] = (double) arithmetic(arithmetic(doubleId(), doubleId()), arithmetic(doubleId(), doubleId())).eval(112);
            data[rowB] = -(double) doubleId().eval(113) * (double) doubleId().eval(114) + (double) arithmetic(doubleId(), doubleId()).eval(115);
        }
    }

    private double computeBulgeScale() {
        double b11 = (double) doubleArrAccessExp(refId(double[].class), intId()).eval(117);
        double b12 = (double) doubleArrAccessExp(refId(double[].class), intId()).eval(118);
        return Math.max(Math.abs((double) doubleId().eval(119)), Math.abs((double) doubleId().eval(120)));
    }

    protected void createBulge(int x1, double p, double scale, boolean byAngle) {
        double b11 = (double) doubleArrAccessExp(refId(double[].class), intId()).eval(121);
        double b12 = (double) doubleArrAccessExp(refId(double[].class), intId()).eval(122);
        double b22 = (double) doubleArrAccessExp(refId(double[].class), arithmetic(intId(), intVal())).eval(123);
        if ((boolean) boolId().eval(124)) {
            c = Math.cos((double) doubleId().eval(130));
            s = Math.sin((double) doubleId().eval(131));
        } else {
            double u1 = (double) arithmetic(arithmetic(arithmetic(doubleId(), doubleId()), arithmetic(doubleId(), doubleId())), doubleId()).eval(125);
            double u2 = (double) arithmetic(arithmetic(doubleId(), doubleId()), arithmetic(doubleId(), doubleId())).eval(126);
            double gamma = Math.sqrt((double) arithmetic(arithmetic(doubleId(), doubleId()), arithmetic(doubleId(), doubleId())).eval(127));
            c = (double) arithmetic(doubleId(), doubleId()).eval(128);
            s = (double) arithmetic(doubleId(), doubleId()).eval(129);
        }
        diag[x1] = (double) arithmetic(arithmetic(doubleId(), doubleId()), arithmetic(doubleId(), doubleId())).eval(132);
        off[x1] = (double) arithmetic(arithmetic(doubleId(), doubleId()), arithmetic(doubleId(), doubleId())).eval(133);
        diag[x1 + 1] = (double) arithmetic(doubleId(), doubleId()).eval(134);
        bulge = (double) arithmetic(doubleId(), doubleId()).eval(135);
        if (refId(mikera.matrixx.Matrix.class).eval(136) != null) {
            updateRotator(refId(mikera.matrixx.Matrix.class).eval(137), (int) intId().eval(138), (int) arithmetic(intId(), intVal()).eval(139), (double) doubleId().eval(140), (double) doubleId().eval(141));
        }
    }

    protected void computeRotator(double rise, double run) {
        if (Math.abs((double) doubleId().eval(142)) < Math.abs((double) doubleId().eval(143))) {
            double k = (double) arithmetic(doubleId(), doubleId()).eval(148);
            double bottom = Math.sqrt((double) arithmetic(doubleVal(), arithmetic(doubleId(), doubleId())).eval(149));
            s = (double) arithmetic(doubleVal(), doubleId()).eval(150);
            c = (double) arithmetic(doubleId(), doubleId()).eval(151);
        } else {
            double t = (double) arithmetic(doubleId(), doubleId()).eval(144);
            double bottom = Math.sqrt((double) arithmetic(doubleVal(), arithmetic(doubleId(), doubleId())).eval(145));
            c = (double) arithmetic(doubleVal(), doubleId()).eval(146);
            s = (double) arithmetic(doubleId(), doubleId()).eval(147);
        }
    }

    protected void removeBulgeLeft(int x1, boolean notLast) {
        double b11 = (double) doubleArrAccessExp(refId(double[].class), intId()).eval(152);
        double b12 = (double) doubleArrAccessExp(refId(double[].class), intId()).eval(153);
        double b22 = (double) doubleArrAccessExp(refId(double[].class), arithmetic(intId(), intVal())).eval(154);
        computeRotator((double) doubleId().eval(155), (double) doubleId().eval(156));
        diag[x1] = (double) arithmetic(arithmetic(doubleId(), doubleId()), arithmetic(doubleId(), doubleId())).eval(157);
        off[x1] = (double) arithmetic(arithmetic(doubleId(), doubleId()), arithmetic(doubleId(), doubleId())).eval(158);
        diag[x1 + 1] = (double) arithmetic(arithmetic(doubleId(), doubleId()), arithmetic(doubleId(), doubleId())).eval(159);
        if ((boolean) boolId().eval(160)) {
            double b23 = (double) doubleArrAccessExp(refId(double[].class), arithmetic(intId(), intVal())).eval(161);
            bulge = (double) arithmetic(doubleId(), doubleId()).eval(162);
            off[x1 + 1] = (double) arithmetic(doubleId(), doubleId()).eval(163);
        }
        if (refId(mikera.matrixx.Matrix.class).eval(164) != null) {
            updateRotator(refId(mikera.matrixx.Matrix.class).eval(165), (int) intId().eval(166), (int) arithmetic(intId(), intVal()).eval(167), (double) doubleId().eval(168), (double) doubleId().eval(169));
        }
    }

    protected void removeBulgeRight(int x1) {
        double b12 = (double) doubleArrAccessExp(refId(double[].class), intId()).eval(170);
        double b22 = (double) doubleArrAccessExp(refId(double[].class), arithmetic(intId(), intVal())).eval(171);
        double b23 = (double) doubleArrAccessExp(refId(double[].class), arithmetic(intId(), intVal())).eval(172);
        computeRotator((double) doubleId().eval(173), (double) doubleId().eval(174));
        off[x1] = (double) arithmetic(arithmetic(doubleId(), doubleId()), arithmetic(doubleId(), doubleId())).eval(175);
        diag[x1 + 1] = (double) arithmetic(arithmetic(doubleId(), doubleId()), arithmetic(doubleId(), doubleId())).eval(176);
        off[x1 + 1] = -(double) doubleId().eval(177) * (double) doubleId().eval(178) + (double) arithmetic(doubleId(), doubleId()).eval(179);
        double b33 = (double) doubleArrAccessExp(refId(double[].class), arithmetic(intId(), intVal())).eval(180);
        diag[x1 + 2] = (double) arithmetic(doubleId(), doubleId()).eval(181);
        bulge = (double) arithmetic(doubleId(), doubleId()).eval(182);
        if (refId(mikera.matrixx.Matrix.class).eval(183) != null) {
            updateRotator(refId(mikera.matrixx.Matrix.class).eval(184), (int) arithmetic(intId(), intVal()).eval(185), (int) arithmetic(intId(), intVal()).eval(186), (double) doubleId().eval(187), (double) doubleId().eval(188));
        }
    }

    public void setSubmatrix(int x1, int x2) {
        this.x1 = (int) x1;
        this.x2 = (int) steps;
    }

    public double selectWilkinsonShift(double scale) {
        double a11, a22;
        if ((boolean) relation(arithmetic(intId(), intId()), intVal()).eval(191)) {
            double d1 = (double) arithmetic(doubleArrAccessExp(refId(double[].class), arithmetic(intId(), intVal())), doubleId()).eval(201);
            double o1 = (double) arithmetic(doubleArrAccessExp(refId(double[].class), arithmetic(intId(), intVal())), doubleId()).eval(202);
            double d2 = (double) arithmetic(doubleArrAccessExp(refId(double[].class), intId()), doubleId()).eval(203);
            double o2 = (double) arithmetic(doubleArrAccessExp(refId(double[].class), arithmetic(intId(), intVal())), doubleId()).eval(204);
            a11 = (double) arithmetic(arithmetic(doubleId(), doubleId()), arithmetic(doubleId(), doubleId())).eval(205);
            a22 = (double) arithmetic(arithmetic(doubleId(), doubleId()), arithmetic(doubleId(), doubleId())).eval(206);
            refId(mikera.matrixx.decompose.impl.svd.SvdImplicitQrAlgorithm.EigenvalueSmall.class).eval(210).symm2x2_fast((double) doubleId().eval(207), (double) arithmetic(doubleId(), doubleId()).eval(208), (double) doubleId().eval(209));
        } else {
            double a = (double) arithmetic(doubleArrAccessExp(refId(double[].class), arithmetic(intId(), intVal())), doubleId()).eval(192);
            double b = (double) arithmetic(doubleArrAccessExp(refId(double[].class), arithmetic(intId(), intVal())), doubleId()).eval(193);
            double c = (double) arithmetic(doubleArrAccessExp(refId(double[].class), intId()), doubleId()).eval(194);
            a11 = (double) arithmetic(doubleId(), doubleId()).eval(195);
            a22 = (double) arithmetic(arithmetic(doubleId(), doubleId()), arithmetic(doubleId(), doubleId())).eval(196);
            refId(mikera.matrixx.decompose.impl.svd.SvdImplicitQrAlgorithm.EigenvalueSmall.class).eval(200).symm2x2_fast((double) doubleId().eval(197), (double) arithmetic(doubleId(), doubleId()).eval(198), (double) doubleId().eval(199));
        }
        double diff0 = Math.abs(eigenSmall.value0 - (double) doubleId().eval(211));
        double diff1 = Math.abs(eigenSmall.value1 - (double) doubleId().eval(212));
        return (boolean) relation(doubleId(), doubleId()).eval(213) ? eigenSmall.value0 : eigenSmall.value1;
    }

    protected void eigenBB_2x2(int x1) {
        double b11 = (double) doubleArrAccessExp(refId(double[].class), intId()).eval(214);
        double b12 = (double) doubleArrAccessExp(refId(double[].class), intId()).eval(215);
        double b22 = (double) doubleArrAccessExp(refId(double[].class), arithmetic(intId(), intVal())).eval(216);
        double absA = Math.abs((double) doubleId().eval(217));
        double absB = Math.abs((double) doubleId().eval(218));
        double absC = Math.abs((double) doubleId().eval(219));
        double scale = (boolean) relation(doubleId(), doubleId()).eval(220) ? (double) doubleId().eval(222) : (double) doubleId().eval(221);
        if ((boolean) relation(doubleId(), doubleId()).eval(223))
            scale = (double) doubleId().eval(224);
        if ((boolean) relation(doubleId(), cast(Double.class, intVal())).eval(225))
            return;
        b11 /= (double) doubleId().eval(226);
        b12 /= (double) doubleId().eval(227);
        b22 /= (double) doubleId().eval(228);
        refId(mikera.matrixx.decompose.impl.svd.SvdImplicitQrAlgorithm.EigenvalueSmall.class).eval(232).symm2x2_fast((double) arithmetic(doubleId(), doubleId()).eval(229), (double) arithmetic(doubleId(), doubleId()).eval(230), (double) arithmetic(arithmetic(doubleId(), doubleId()), arithmetic(doubleId(), doubleId())).eval(231));
        off[x1] = (int) intVal().eval(233);
        diag[x1] = (double) doubleId().eval(234) * Math.sqrt(eigenSmall.value0);
        double sgn = Math.signum(eigenSmall.value1);
        diag[x1 + 1] = (double) arithmetic(doubleId(), doubleId()).eval(235) * Math.sqrt(Math.abs(eigenSmall.value1));
    }

    protected boolean checkForAndHandleZeros() {
        int _jitmagicLoopLimiter5 = 0;
        for (int i = (int) arithmetic(intId(), intVal()).eval(241); (boolean) relation(intId(), intId()).eval(240) && _jitmagicLoopLimiter5++ < 1000; i--) {
            if (isOffZero((int) intId().eval(236))) {
                resetSteps();
                splits[numSplits++] = (int) intId().eval(237);
                x1 = (int) arithmetic(intId(), intVal()).eval(238);
                return (boolean) boolVal().eval(239);
            }
        }
        int _jitmagicLoopLimiter6 = 0;
        for (int i = (int) arithmetic(intId(), intVal()).eval(248); (boolean) relation(intId(), intId()).eval(247) && _jitmagicLoopLimiter6++ < 1000; i--) {
            if (isDiagonalZero((int) intId().eval(242))) {
                pushRight((int) intId().eval(243));
                resetSteps();
                splits[numSplits++] = (int) intId().eval(244);
                x1 = (int) arithmetic(intId(), intVal()).eval(245);
                return (boolean) boolVal().eval(246);
            }
        }
        return (boolean) boolVal().eval(249);
    }

    private void pushRight(int row) {
        if (isOffZero((int) intId().eval(250)))
            return;
        rotatorPushRight((int) intId().eval(251));
        int end = (int) arithmetic(arithmetic(intId(), intVal()), intId()).eval(252);
        int _jitmagicLoopLimiter7 = 0;
        for (int i = (int) intVal().eval(256); (boolean) logic(relation(intId(), intId()), relation(doubleId(), cast(Double.class, intVal()))).eval(255) && _jitmagicLoopLimiter7++ < 1000; i++) {
            rotatorPushRight2((int) intId().eval(253), (int) arithmetic(intId(), intVal()).eval(254));
        }
    }

    private void rotatorPushRight(int m) {
        double b11 = (double) doubleArrAccessExp(refId(double[].class), intId()).eval(257);
        double b21 = (double) doubleArrAccessExp(refId(double[].class), arithmetic(intId(), intVal())).eval(258);
        computeRotator((double) doubleId().eval(259), -(double) doubleId().eval(260));
        off[m] = (int) intVal().eval(261);
        diag[m + 1] = (double) arithmetic(arithmetic(doubleId(), doubleId()), arithmetic(doubleId(), doubleId())).eval(262);
        if ((boolean) relation(arithmetic(intId(), intVal()), intId()).eval(263)) {
            double b22 = (double) doubleArrAccessExp(refId(double[].class), arithmetic(intId(), intVal())).eval(265);
            off[m + 1] = (double) arithmetic(doubleId(), doubleId()).eval(266);
            bulge = (double) arithmetic(doubleId(), doubleId()).eval(267);
        } else {
            bulge = (int) intVal().eval(264);
        }
        if (refId(mikera.matrixx.Matrix.class).eval(268) != null) {
            updateRotator(refId(mikera.matrixx.Matrix.class).eval(269), (int) intId().eval(270), (int) arithmetic(intId(), intVal()).eval(271), (double) doubleId().eval(272), (double) doubleId().eval(273));
        }
    }

    private void rotatorPushRight2(int m, int offset) {
        double b11 = (double) doubleId().eval(274);
        double b12 = (double) doubleArrAccessExp(refId(double[].class), arithmetic(intId(), intId())).eval(275);
        computeRotator((double) doubleId().eval(276), -(double) doubleId().eval(277));
        diag[m + offset] = (double) arithmetic(arithmetic(doubleId(), doubleId()), arithmetic(doubleId(), doubleId())).eval(278);
        if ((boolean) relation(arithmetic(intId(), intId()), arithmetic(intId(), intVal())).eval(279)) {
            double b22 = (double) doubleArrAccessExp(refId(double[].class), arithmetic(intId(), intId())).eval(280);
            off[m + offset] = (double) arithmetic(doubleId(), doubleId()).eval(281);
            bulge = (double) arithmetic(doubleId(), doubleId()).eval(282);
        }
        if (refId(mikera.matrixx.Matrix.class).eval(283) != null) {
            updateRotator(refId(mikera.matrixx.Matrix.class).eval(284), (int) intId().eval(285), (int) arithmetic(intId(), intId()).eval(286), (double) doubleId().eval(287), (double) doubleId().eval(288));
        }
    }

    public void exceptionShift() {
        numExceptional++;
        double mag = (double) arithmetic(doubleVal(), cast(Double.class, intId())).eval(289);
        if ((boolean) relation(doubleId(), doubleVal()).eval(290))
            mag = (double) doubleVal().eval(291);
        double angle = (double) doubleVal().eval(292) * Math.PI * (refId(java.util.Random.class).eval(293).nextDouble() - (double) doubleVal().eval(294)) * (double) doubleId().eval(295);
        performImplicitSingleStep((int) intVal().eval(296), (double) doubleId().eval(297), (boolean) boolVal().eval(298));
        nextExceptional = (int) arithmetic(intId(), intId()).eval(299);
    }

    @SuppressWarnings("unused")
    private Matrix createQ(int x1, double c, double s, boolean transposed) {
        return createQ((int) intId().eval(300), (int) arithmetic(intId(), intVal()).eval(301), (double) doubleId().eval(302), (double) doubleId().eval(303), (boolean) boolId().eval(304));
    }

    private Matrix createQ(int x1, int x2, double c, double s, boolean transposed) {
        Matrix Q = Matrix.createIdentity((int) intId().eval(305));
        refId(mikera.matrixx.Matrix.class).eval(309).set((int) intId().eval(306), (int) intId().eval(307), (double) doubleId().eval(308));
        if ((boolean) boolId().eval(310)) {
            refId(mikera.matrixx.Matrix.class).eval(322).set((int) intId().eval(319), (int) intId().eval(320), (double) doubleId().eval(321));
            refId(mikera.matrixx.Matrix.class).eval(326).set((int) intId().eval(323), (int) intId().eval(324), -(double) doubleId().eval(325));
        } else {
            refId(mikera.matrixx.Matrix.class).eval(314).set((int) intId().eval(311), (int) intId().eval(312), -(double) doubleId().eval(313));
            refId(mikera.matrixx.Matrix.class).eval(318).set((int) intId().eval(315), (int) intId().eval(316), (double) doubleId().eval(317));
        }
        refId(mikera.matrixx.Matrix.class).eval(330).set((int) intId().eval(327), (int) intId().eval(328), (double) doubleId().eval(329));
        return refId(mikera.matrixx.Matrix.class).eval(331);
    }

    @SuppressWarnings("unused")
    private Matrix createB() {
        Matrix B = Matrix.create((int) intId().eval(332), (int) intId().eval(333));
        int _jitmagicLoopLimiter8 = 0;
        for (int i = (int) intVal().eval(343); (boolean) relation(intId(), arithmetic(intId(), intVal())).eval(342) && _jitmagicLoopLimiter8++ < 1000; i++) {
            refId(mikera.matrixx.Matrix.class).eval(337).set((int) intId().eval(334), (int) intId().eval(335), (double) doubleArrAccessExp(refId(double[].class), intId()).eval(336));
            refId(mikera.matrixx.Matrix.class).eval(341).set((int) intId().eval(338), (int) arithmetic(intId(), intVal()).eval(339), (double) doubleArrAccessExp(refId(double[].class), intId()).eval(340));
        }
        refId(mikera.matrixx.Matrix.class).eval(347).set((int) arithmetic(intId(), intVal()).eval(344), (int) arithmetic(intId(), intVal()).eval(345), (double) doubleArrAccessExp(refId(double[].class), arithmetic(intId(), intVal())).eval(346));
        return refId(mikera.matrixx.Matrix.class).eval(348);
    }

    public void printMatrix() {
        System.out.print("Off Diag[ ");
        int _jitmagicLoopLimiter9 = 0;
        for (int j = (int) -174482069; (boolean) (totalSteps > (numSplits - 1483179367)) && _jitmagicLoopLimiter9++ < 1000; j++) {
            System.out.printf("%5.2f ", (double) off[exceptionalThresh]);
        }
        System.out.println();
        System.out.print("    Diag[ ");
        int _jitmagicLoopLimiter10 = 0;
        for (int j = (int) intVal().eval(354); (boolean) relation(intId(), intId()).eval(353) && _jitmagicLoopLimiter10++ < 1000; j++) {
            System.out.printf("%5.2f ", (double) doubleArrAccessExp(refId(double[].class), intId()).eval(352));
        }
        System.out.println();
    }

    public int getNumberOfSingularValues() {
        return (int) numSplits;
    }

    public double getSingularValue(int index) {
        return (double) doubleArrAccessExp(refId(double[].class), intId()).eval(356);
    }

    public void setFastValues(boolean b) {
        fastValues = (boolean) boolId().eval(357);
    }

    public double[] getSingularValues() {
        return refId(double[].class).eval(358);
    }

    public double[] getDiag() {
        return refId(double[].class).eval(359);
    }

    public double[] getOff() {
        return refId(double[].class).eval(360);
    }

    public double getMaxValue() {
        return (double) doubleId().eval(361);
    }

    public class EigenvalueSmall {

        public double value0;

        public double value1;

        public void symm2x2_fast(double a11, double a12, double a22) {
            double left = (a11 + a22) * 0.5;
            double b = (a11 - a22) * 0.5;
            double right = Math.sqrt(b * b + a12 * a12);
            value0 = left + right;
            value1 = left - right;
        }
    }

    @jattack.annotation.Arguments
    public static Object[] _jitmagicArgs() throws Throwable {
        mikera.matrixx.decompose.impl.svd.SvdImplicitQrAlgorithm svdImplicitQrAlgorithm1 = new mikera.matrixx.decompose.impl.svd.SvdImplicitQrAlgorithm(false);
        mikera.matrixx.AMatrix aMatrix2 = null;
        svdImplicitQrAlgorithm1.setUt(aMatrix2);
        mikera.vectorz.ops.Tanh tanh4 = new mikera.vectorz.ops.Tanh();
        mikera.vectorz.GrowableVector growableVector6 = mikera.vectorz.GrowableVector.ofInitialCapacity(10);
        mikera.vectorz.GrowableVector growableVector8 = mikera.vectorz.GrowableVector.ofInitialCapacity(10);
        mikera.vectorz.AVector aVector9 = mikera.vectorz.impl.JoinedVector.joinVectors((mikera.vectorz.AVector) growableVector6, (mikera.vectorz.AVector) growableVector8);
        mikera.arrayz.INDArray iNDArray10 = growableVector8.sparse();
        double[] doubleArray15 = new double[] { 1L, 'a', 1L, 100L };
        double[] doubleArray19 = new double[] {};
        double double23 = mikera.vectorz.util.DoubleArrays.elementSquaredSum(doubleArray19, (int) ' ', (int) (byte) -1, (int) (short) 1);
        mikera.matrixx.solve.impl.TriangularSolver.solveU(doubleArray15, 10, (-1), (int) (byte) 0, doubleArray19, (int) (byte) 0, 10, (int) '4');
        growableVector8.divide(doubleArray19, (-1));
        double double30 = mikera.vectorz.util.DoubleArrays.elementMax(doubleArray19);
        mikera.vectorz.GrowableVector growableVector31 = mikera.vectorz.GrowableVector.wrap(doubleArray19);
        double double34 = mikera.vectorz.util.DoubleArrays.elementProduct(doubleArray19, (int) (byte) 1, (int) (byte) 0);
        tanh4.applyTo(doubleArray19);
        double[] doubleArray36 = svdImplicitQrAlgorithm1.swapOff(doubleArray19);
        boolean boolean37 = svdImplicitQrAlgorithm1.nextSplit();
        int int38 = svdImplicitQrAlgorithm1.getNumberOfSingularValues();
        svdImplicitQrAlgorithm1.setSubmatrix((int) (byte) 100, 3);
        return new Object[] { svdImplicitQrAlgorithm1 };
    }

    public static long main0(String[] args) {
        org.csutil.Helper.parseArgs(args);
        int N = Math.min(org.csutil.Helper.nItrs, 100000);
        WrappedChecksum cs = new WrappedChecksum(true);
        try {
            Object[] eArgs = _jitmagicArgs();
            cs.update(eArgs);
            SvdImplicitQrAlgorithm rcvr = (SvdImplicitQrAlgorithm) eArgs[0];
            for (int i = 0; i < N; ++i) {
                try {
                    rcvr.printMatrix();
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
        cs.updateStaticFieldsOfClass(SvdImplicitQrAlgorithm.class);
        return cs.getValue();
    }

    public static void main(String[] args) {
        org.csutil.Helper.write(main0(args));
    }
}
