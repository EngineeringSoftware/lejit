/*
 * Copyright (c) 2009-2013, Peter Abeles. All Rights Reserved.
 *
 * This file is part of Efficient Java Matrix Library (EJML).
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package mikera.matrixx.decompose.impl.svd;

import java.util.Random;
import mikera.matrixx.AMatrix;
import mikera.matrixx.Matrix;
import static jattack.Boom.*;
import jattack.annotation.*;

/**
 * <p>
 * Computes the QR decomposition of a bidiagonal matrix.  Internally this matrix is stored as
 * two arrays.  Shifts can either be provided to it or it can generate the shifts on its own.
 * It optionally computes the U and V matrices.  This comparability allows it to be used to
 * compute singular values and associated matrices efficiently.<br>
 * <br>
 * A = U*S*V<sup>T</sup><br>
 * where A is the original m by n matrix.
 * </p>
 *
 * <p>
 * Based off of the outline provided in:<br>
 * <br>
 * David S. Watkins, "Fundamentals of Matrix Computations," Second Edition. Page 404-411
 * </p>
 *
 * <p>
 * Note: To watch it process the matrix step by step uncomment commented out code.
 * </p>
 *
 * @author Peter Abeles
 */
public class SvdImplicitQrAlgorithm {

    // Taken from UtilEJML
    public static final double EPS = Math.pow(2, -52);

    // used in exceptional shifts
    protected Random rand = new Random(0x34671e);

    // U and V matrices in singular value decomposition.  Stored in the transpose
    // to reduce cache jumps
    protected Matrix Ut;

    protected Matrix Vt;

    // number of times it has performed an implicit step, the most costly part of the
    // algorithm
    protected int totalSteps;

    // max value in original matrix.  used to test for zeros
    protected double maxValue;

    // matrix's size
    protected int N;

    // used to compute eigenvalues directly
    protected EigenvalueSmall eigenSmall = new EigenvalueSmall();

    // how many exception shifts has it performed
    protected int numExceptional;

    // the step number of the last exception shift
    protected int nextExceptional;

    // diagonal elements in the matrix
    protected double[] diag;

    // the off diagonal elements
    protected double[] off;

    // value of the bulge
    double bulge;

    // the submatrix its working on
    protected int x1;

    protected int x2;

    // how many cycles has it run through looking for the current singular value
    int steps;

    // where splits are performed
    protected int[] splits;

    protected int numSplits;

    // After this many iterations it will perform an exceptional
    private int exceptionalThresh = 15;

    private int maxIterations = exceptionalThresh * 100;

    // should the steps use a sequence of predefined lambdas?
    boolean followScript;

    // --------- variables for scripted step
    // if following a sequence of steps, this is the point at which it decides its
    // going no where and needs to use a different step
    private static final int giveUpOnKnown = 10;

    private double[] values;

    //can it compute singularvalues directly
    private boolean fastValues = false;

    // if not in scripted mode is it looking for new zeros first?
    private boolean findingZeros;

    double c, s;

    // for debugging
    //    SimpleMatrix B;
    public SvdImplicitQrAlgorithm(boolean fastValues) {
        this.fastValues = (boolean) boolId().eval();
    }

    public SvdImplicitQrAlgorithm() {
    }

    public AMatrix getUt() {
        return refId(mikera.matrixx.Matrix.class).eval();
    }

    public void setUt(AMatrix ut) {
        if (refId(mikera.matrixx.AMatrix.class).eval() == null) {
            Ut = null;
        } else {
            Ut = refId(mikera.matrixx.AMatrix.class).eval().toMatrix();
        }
    }

    public AMatrix getVt() {
        return refId(mikera.matrixx.Matrix.class).eval();
    }

    public void setVt(AMatrix vt) {
        if (refId(mikera.matrixx.AMatrix.class).eval() == null) {
            Vt = null;
        } else {
            Vt = refId(mikera.matrixx.AMatrix.class).eval().toMatrix();
        }
    }

    /**
     */
    public void setMatrix(int numRows, int numCols, double[] diag, double[] off) {
        initParam((int) intId().eval(), (int) intId().eval());
        this.diag = refId(double[].class).eval();
        this.off = refId(double[].class).eval();
        //      Find largest value in diag U off
        maxValue = Math.abs((double) doubleArrAccessExp(refId(double[].class)).eval());
        int _jitmagicLoopLimiter1 = 0;
        for (int i = (int) intVal().eval(); (boolean) relation(intId(), intId()).eval() && _jitmagicLoopLimiter1++ < 1000; i++) {
            double a = Math.abs((double) doubleArrAccessExp(refId(double[].class), intId()).eval());
            double b = Math.abs((double) doubleArrAccessExp(refId(double[].class), arithmetic(intId(), intVal())).eval());
            if ((boolean) relation(doubleId(), doubleId()).eval()) {
                maxValue = Math.abs((double) doubleId().eval());
            }
            if ((boolean) relation(doubleId(), doubleId()).eval()) {
                maxValue = Math.abs((double) doubleId().eval());
            }
        }
    }

    public double[] swapDiag(double[] diag) {
        double[] ret = this.diag;
        this.diag = refId(double[].class).eval();
        return refId(double[].class).eval();
    }

    public double[] swapOff(double[] off) {
        double[] ret = this.off;
        this.off = refId(double[].class).eval();
        return refId(double[].class).eval();
    }

    public void setMaxValue(double maxValue) {
        this.maxValue = (double) doubleId().eval();
    }

    public void initParam(int M, int N) {
        if ((boolean) relation(intId(), intId()).eval())
            throw new RuntimeException("Must be a square or tall matrix");
        this.N = (int) intId().eval();
        if (refId(int[].class).eval() == null || splits.length < (int) intId().eval()) {
            splits = new int[(int) intId().eval()];
        }
        x1 = (int) intVal().eval();
        x2 = this.N - (int) intVal().eval();
        steps = (int) intVal().eval();
        totalSteps = (int) intVal().eval();
        numSplits = (int) intVal().eval();
        numExceptional = (int) intVal().eval();
        nextExceptional = (int) intId().eval();
    }

    public boolean process() {
        this.followScript = (boolean) boolVal().eval();
        findingZeros = (boolean) boolVal().eval();
        return _process();
    }

    /**
     * Perform a sequence of steps based off of the singular values provided.
     *
     * @param values
     * @return
     */
    public boolean process(double[] values) {
        this.followScript = (boolean) boolVal().eval();
        this.values = refId(double[].class).eval();
        this.findingZeros = (boolean) boolVal().eval();
        return _process();
    }

    /**
     * @return true if successful, false if number of iterations exceeded
     */
    public boolean _process() {
        // it is a zero matrix
        if ((boolean) relation(doubleId(), cast(Double.class, intVal())).eval())
            return (boolean) boolVal().eval();
        int _jitmagicLoopLimiter2 = 0;
        while ((boolean) relation(intId(), intVal()).eval() && _jitmagicLoopLimiter2++ < 1000) {
            // if it has cycled too many times give up
            if ((boolean) relation(intId(), intId()).eval()) {
                return (boolean) boolVal().eval();
            }
            if ((boolean) relation(intId(), intId()).eval()) {
                //                System.out.println("steps = "+steps+"  script = "+followScript+" at "+x1);
                //                System.out.println("Split");
                // see if it is done processing this submatrix
                resetSteps();
                if (!nextSplit())
                    break;
            } else if ((boolean) logic(boolId(), relation(arithmetic(intId(), intId()), intVal())).eval()) {
                // There are analytical solutions to this case. Just compute them directly.
                resetSteps();
                eigenBB_2x2((int) intId().eval());
                setSubmatrix((int) intId().eval(), (int) intId().eval());
            } else if ((boolean) relation(intId(), intId()).eval()) {
                exceptionShift();
            } else {
                // perform a step
                if (!checkForAndHandleZeros()) {
                    if ((boolean) boolId().eval()) {
                        performScriptedStep();
                    } else {
                        performDynamicStep();
                    }
                }
            }
            //            printMatrix();
        }
        return (boolean) boolVal().eval();
    }

    /**
     * Here the lambda in the implicit step is determined dynamically.  At first
     * it selects zeros to quickly reveal singular values that are zero or close to zero.
     * Then it computes it using a Wilkinson shift.
     */
    private void performDynamicStep() {
        // initially look for singular values of zero
        if ((boolean) boolId().eval()) {
            if ((boolean) relation(intId(), intVal()).eval()) {
                findingZeros = (boolean) boolVal().eval();
            } else {
                double scale = computeBulgeScale();
                performImplicitSingleStep((double) doubleId().eval(), (int) intVal().eval(), (boolean) boolVal().eval());
            }
        } else {
            // For very large and very small numbers the only way to prevent overflow/underflow
            // is to have a common scale between the wilkinson shift and the implicit single step
            // What happens if you don't is that when the wilkinson shift returns the value it
            // computed it multiplies it by the scale twice, which will cause an overflow
            double scale = computeBulgeScale();
            // use the wilkinson shift to perform a step
            double lambda = selectWilkinsonShift((double) doubleId().eval());
            performImplicitSingleStep((double) doubleId().eval(), (double) doubleId().eval(), (boolean) boolVal().eval());
        }
    }

    /**
     * Shifts are performed based upon singular values computed previously.  If it does not converge
     * using one of those singular values it uses a Wilkinson shift instead.
     */
    private void performScriptedStep() {
        double scale = computeBulgeScale();
        if ((boolean) relation(intId(), intId()).eval()) {
            // give up on the script
            followScript = (boolean) boolVal().eval();
        } else {
            // use previous singular value to step
            double s = (double) arithmetic(doubleArrAccessExp(refId(double[].class), intId()), doubleId()).eval();
            performImplicitSingleStep((double) doubleId().eval(), (double) arithmetic(doubleId(), doubleId()).eval(), (boolean) boolVal().eval());
        }
    }

    public void incrementSteps() {
        steps++;
        totalSteps++;
    }

    public boolean isOffZero(int i) {
        double bottom = Math.abs((double) doubleArrAccessExp(refId(double[].class), intId()).eval()) + Math.abs((double) doubleArrAccessExp(refId(double[].class), arithmetic(intId(), intVal())).eval());
        return Math.abs((double) doubleArrAccessExp(refId(double[].class), intId()).eval()) <= (double) arithmetic(doubleId(), doubleId()).eval();
    }

    public boolean isDiagonalZero(int i) {
        //        return Math.abs(diag[i]) <= maxValue* UtilEjml.EPS;
        double bottom = Math.abs((double) doubleArrAccessExp(refId(double[].class), arithmetic(intId(), intVal())).eval()) + Math.abs((double) doubleArrAccessExp(refId(double[].class), intId()).eval());
        return Math.abs((double) doubleArrAccessExp(refId(double[].class), intId()).eval()) <= (double) arithmetic(doubleId(), doubleId()).eval();
    }

    public void resetSteps() {
        steps = (int) intVal().eval();
        nextExceptional = (int) intId().eval();
        numExceptional = (int) intVal().eval();
    }

    /**
     * Tells it to process the submatrix at the next split.  Should be called after the
     * current submatrix has been processed.
     */
    public boolean nextSplit() {
        if ((boolean) relation(intId(), intVal()).eval())
            return (boolean) boolVal().eval();
        x2 = (int) intArrAccessExp(refId(int[].class)).eval();
        if ((boolean) relation(intId(), intVal()).eval())
            x1 = (int) arithmetic(intArrAccessExp(refId(int[].class), arithmetic(intId(), intVal())), intVal()).eval();
        else
            x1 = (int) intVal().eval();
        return (boolean) boolVal().eval();
    }

    /**
     * Given the lambda value perform an implicit QR step on the matrix.
     *
     * B^T*B-lambda*I
     *
     * @param lambda Stepping factor.
     */
    public void performImplicitSingleStep(double scale, double lambda, boolean byAngle) {
        createBulge((int) intId().eval(), (double) doubleId().eval(), (double) doubleId().eval(), (boolean) boolId().eval());
        int _jitmagicLoopLimiter3 = 0;
        for (int i = (int) intId().eval(); (boolean) logic(relation(intId(), arithmetic(intId(), intVal())), relation(doubleId(), doubleVal())).eval() && _jitmagicLoopLimiter3++ < 1000; i++) {
            removeBulgeLeft((int) intId().eval(), (boolean) boolVal().eval());
            if ((boolean) relation(doubleId(), cast(Double.class, intVal())).eval())
                break;
            removeBulgeRight((int) intId().eval());
        }
        if ((boolean) relation(doubleId(), cast(Double.class, intVal())).eval())
            removeBulgeLeft((int) arithmetic(intId(), intVal()).eval(), (boolean) boolVal().eval());
        incrementSteps();
    }

    /**
     * Multiplied a transpose orthogonal matrix Q by the specified rotator.  This is used
     * to update the U and V matrices.  Updating the transpose of the matrix is faster
     * since it only modifies the rows.
     *
     * @param Q Orthogonal matrix
     * @param m Coordinate of rotator.
     * @param n Coordinate of rotator.
     * @param c cosine of rotator.
     * @param s sine of rotator.
     */
    protected void updateRotator(Matrix Q, int m, int n, double c, double s) {
        int rowA = (int) intId().eval() * refId(mikera.matrixx.Matrix.class).eval().columnCount();
        int rowB = (int) intId().eval() * refId(mikera.matrixx.Matrix.class).eval().columnCount();
        //        for( int i = 0; i < Q.numCols; i++ ) {
        //            double a = Q.get(rowA+i);
        //            double b = Q.get(rowB+i);
        //            Q.set( rowA+i, c*a + s*b);
        //            Q.set( rowB+i, -s*a + c*b);
        //        }
        //        System.out.println("------ AFter Update Rotator "+m+" "+n);
        //        Q.print();
        //        System.out.println();
        int endA = (int) intId().eval() + refId(mikera.matrixx.Matrix.class).eval().columnCount();
        double[] data = refId(mikera.matrixx.Matrix.class).eval().asDoubleArray();
        //        for( ; rowA != endA; rowA++ , rowB++ ) {
        //            double a = Q.get(rowA);
        //            double b = Q.get(rowB);
        //            Q.set(rowA, c*a + s*b);
        //            Q.set(rowB, -s*a + c*b);
        //        }
        int _jitmagicLoopLimiter4 = 0;
        for (; (boolean) relation(intId(), intId()).eval() && _jitmagicLoopLimiter4++ < 1000; rowA++, rowB++) {
            double a = (double) doubleArrAccessExp(refId(double[].class), intId()).eval();
            double b = (double) doubleArrAccessExp(refId(double[].class), intId()).eval();
            data[rowA] = (double) arithmetic(arithmetic(doubleId(), doubleId()), arithmetic(doubleId(), doubleId())).eval();
            data[rowB] = -(double) doubleId().eval() * (double) doubleId().eval() + (double) arithmetic(doubleId(), doubleId()).eval();
        }
    }

    private double computeBulgeScale() {
        double b11 = (double) doubleArrAccessExp(refId(double[].class), intId()).eval();
        double b12 = (double) doubleArrAccessExp(refId(double[].class), intId()).eval();
        return Math.max(Math.abs((double) doubleId().eval()), Math.abs((double) doubleId().eval()));
        //
        //        double b22 = diag[x1+1];
        //
        //        double scale = Math.max( Math.abs(b11) , Math.abs(b12));
        //
        //        return Math.max(scale,Math.abs(b22));
    }

    /**
     * Performs a similar transform on B<sup>T</sup>B-pI
     */
    protected void createBulge(int x1, double p, double scale, boolean byAngle) {
        double b11 = (double) doubleArrAccessExp(refId(double[].class), intId()).eval();
        double b12 = (double) doubleArrAccessExp(refId(double[].class), intId()).eval();
        double b22 = (double) doubleArrAccessExp(refId(double[].class), arithmetic(intId(), intVal())).eval();
        if ((boolean) boolId().eval()) {
            c = Math.cos((double) doubleId().eval());
            s = Math.sin((double) doubleId().eval());
        } else {
            // normalize to improve resistance to overflow/underflow
            double u1 = (double) arithmetic(arithmetic(arithmetic(doubleId(), doubleId()), arithmetic(doubleId(), doubleId())), doubleId()).eval();
            double u2 = (double) arithmetic(arithmetic(doubleId(), doubleId()), arithmetic(doubleId(), doubleId())).eval();
            double gamma = Math.sqrt((double) arithmetic(arithmetic(doubleId(), doubleId()), arithmetic(doubleId(), doubleId())).eval());
            c = (double) arithmetic(doubleId(), doubleId()).eval();
            s = (double) arithmetic(doubleId(), doubleId()).eval();
        }
        // multiply the rotator on the top left.
        diag[x1] = (double) arithmetic(arithmetic(doubleId(), doubleId()), arithmetic(doubleId(), doubleId())).eval();
        off[x1] = (double) arithmetic(arithmetic(doubleId(), doubleId()), arithmetic(doubleId(), doubleId())).eval();
        diag[x1 + 1] = (double) arithmetic(doubleId(), doubleId()).eval();
        bulge = (double) arithmetic(doubleId(), doubleId()).eval();
        //        SimpleMatrix Q = createQ(x1, c, s, false);
        //        B=B.mult(Q);
        //
        //        B.print();
        //        printMatrix();
        //        System.out.println("  bulge = "+bulge);
        if (refId(mikera.matrixx.Matrix.class).eval() != null) {
            updateRotator(refId(mikera.matrixx.Matrix.class).eval(), (int) intId().eval(), (int) arithmetic(intId(), intVal()).eval(), (double) doubleId().eval(), (double) doubleId().eval());
            //            SimpleMatrix.wrap(Ut).mult(B).mult(SimpleMatrix.wrap(Vt).transpose()).print();
            //            printMatrix();
            //            System.out.println("bulge = "+bulge);
            //            System.out.println();
        }
    }

    /**
     * Computes a rotator that will set run to zero (?)
     */
    protected void computeRotator(double rise, double run) {
        //        double gamma = Math.sqrt(rise*rise + run*run);
        //
        //        c = rise/gamma;
        //        s = run/gamma;
        // See page 384 of Fundamentals of Matrix Computations 2nd
        if (Math.abs((double) doubleId().eval()) < Math.abs((double) doubleId().eval())) {
            double k = (double) arithmetic(doubleId(), doubleId()).eval();
            double bottom = Math.sqrt((double) arithmetic(doubleVal(), arithmetic(doubleId(), doubleId())).eval());
            s = (double) arithmetic(doubleVal(), doubleId()).eval();
            c = (double) arithmetic(doubleId(), doubleId()).eval();
        } else {
            double t = (double) arithmetic(doubleId(), doubleId()).eval();
            double bottom = Math.sqrt((double) arithmetic(doubleVal(), arithmetic(doubleId(), doubleId())).eval());
            c = (double) arithmetic(doubleVal(), doubleId()).eval();
            s = (double) arithmetic(doubleId(), doubleId()).eval();
        }
    }

    protected void removeBulgeLeft(int x1, boolean notLast) {
        double b11 = (double) doubleArrAccessExp(refId(double[].class), intId()).eval();
        double b12 = (double) doubleArrAccessExp(refId(double[].class), intId()).eval();
        double b22 = (double) doubleArrAccessExp(refId(double[].class), arithmetic(intId(), intVal())).eval();
        computeRotator((double) doubleId().eval(), (double) doubleId().eval());
        // apply rotator on the left
        diag[x1] = (double) arithmetic(arithmetic(doubleId(), doubleId()), arithmetic(doubleId(), doubleId())).eval();
        off[x1] = (double) arithmetic(arithmetic(doubleId(), doubleId()), arithmetic(doubleId(), doubleId())).eval();
        diag[x1 + 1] = (double) arithmetic(arithmetic(doubleId(), doubleId()), arithmetic(doubleId(), doubleId())).eval();
        if ((boolean) boolId().eval()) {
            double b23 = (double) doubleArrAccessExp(refId(double[].class), arithmetic(intId(), intVal())).eval();
            bulge = (double) arithmetic(doubleId(), doubleId()).eval();
            off[x1 + 1] = (double) arithmetic(doubleId(), doubleId()).eval();
        }
        //        SimpleMatrix Q = createQ(x1, c, s, true);
        //        B=Q.mult(B);
        //
        //        B.print();
        //        printMatrix();
        //        System.out.println("  bulge = "+bulge);
        if (refId(mikera.matrixx.Matrix.class).eval() != null) {
            updateRotator(refId(mikera.matrixx.Matrix.class).eval(), (int) intId().eval(), (int) arithmetic(intId(), intVal()).eval(), (double) doubleId().eval(), (double) doubleId().eval());
            //            SimpleMatrix.wrap(Ut).mult(B).mult(SimpleMatrix.wrap(Vt).transpose()).print();
            //            printMatrix();
            //            System.out.println("bulge = "+bulge);
            //            System.out.println();
        }
    }

    protected void removeBulgeRight(int x1) {
        double b12 = (double) doubleArrAccessExp(refId(double[].class), intId()).eval();
        double b22 = (double) doubleArrAccessExp(refId(double[].class), arithmetic(intId(), intVal())).eval();
        double b23 = (double) doubleArrAccessExp(refId(double[].class), arithmetic(intId(), intVal())).eval();
        computeRotator((double) doubleId().eval(), (double) doubleId().eval());
        // apply rotator on the right
        off[x1] = (double) arithmetic(arithmetic(doubleId(), doubleId()), arithmetic(doubleId(), doubleId())).eval();
        diag[x1 + 1] = (double) arithmetic(arithmetic(doubleId(), doubleId()), arithmetic(doubleId(), doubleId())).eval();
        off[x1 + 1] = -(double) doubleId().eval() * (double) doubleId().eval() + (double) arithmetic(doubleId(), doubleId()).eval();
        double b33 = (double) doubleArrAccessExp(refId(double[].class), arithmetic(intId(), intVal())).eval();
        diag[x1 + 2] = (double) arithmetic(doubleId(), doubleId()).eval();
        bulge = (double) arithmetic(doubleId(), doubleId()).eval();
        //        SimpleMatrix Q = createQ(x1+1, c, s, false);
        //        B=B.mult(Q);
        //
        //        B.print();
        //        printMatrix();
        //        System.out.println("  bulge = "+bulge);
        if (refId(mikera.matrixx.Matrix.class).eval() != null) {
            updateRotator(refId(mikera.matrixx.Matrix.class).eval(), (int) arithmetic(intId(), intVal()).eval(), (int) arithmetic(intId(), intVal()).eval(), (double) doubleId().eval(), (double) doubleId().eval());
            //            SimpleMatrix.wrap(Ut).mult(B).mult(SimpleMatrix.wrap(Vt).transpose()).print();
            //            printMatrix();
            //            System.out.println("bulge = "+bulge);
            //            System.out.println();
        }
    }

    public void setSubmatrix(int x1, int x2) {
        this.x1 = (int) intId().eval();
        this.x2 = (int) intId().eval();
    }

    /**
     * Selects the Wilkinson's shift for B<sup>T</sup>B.  See page 410.  It is guaranteed to converge
     * and converges fast in practice.
     *
     * @param scale Scale factor used to help prevent overflow/underflow
     * @return Shifting factor lambda/(scale*scale)
     */
    public double selectWilkinsonShift(double scale) {
        double a11, a22;
        if ((boolean) relation(arithmetic(intId(), intId()), intVal()).eval()) {
            double d1 = (double) arithmetic(doubleArrAccessExp(refId(double[].class), arithmetic(intId(), intVal())), doubleId()).eval();
            double o1 = (double) arithmetic(doubleArrAccessExp(refId(double[].class), arithmetic(intId(), intVal())), doubleId()).eval();
            double d2 = (double) arithmetic(doubleArrAccessExp(refId(double[].class), intId()), doubleId()).eval();
            double o2 = (double) arithmetic(doubleArrAccessExp(refId(double[].class), arithmetic(intId(), intVal())), doubleId()).eval();
            a11 = (double) arithmetic(arithmetic(doubleId(), doubleId()), arithmetic(doubleId(), doubleId())).eval();
            a22 = (double) arithmetic(arithmetic(doubleId(), doubleId()), arithmetic(doubleId(), doubleId())).eval();
            refId(mikera.matrixx.decompose.impl.svd.SvdImplicitQrAlgorithm.EigenvalueSmall.class).eval().symm2x2_fast((double) doubleId().eval(), (double) arithmetic(doubleId(), doubleId()).eval(), (double) doubleId().eval());
        } else {
            double a = (double) arithmetic(doubleArrAccessExp(refId(double[].class), arithmetic(intId(), intVal())), doubleId()).eval();
            double b = (double) arithmetic(doubleArrAccessExp(refId(double[].class), arithmetic(intId(), intVal())), doubleId()).eval();
            double c = (double) arithmetic(doubleArrAccessExp(refId(double[].class), intId()), doubleId()).eval();
            a11 = (double) arithmetic(doubleId(), doubleId()).eval();
            a22 = (double) arithmetic(arithmetic(doubleId(), doubleId()), arithmetic(doubleId(), doubleId())).eval();
            refId(mikera.matrixx.decompose.impl.svd.SvdImplicitQrAlgorithm.EigenvalueSmall.class).eval().symm2x2_fast((double) doubleId().eval(), (double) arithmetic(doubleId(), doubleId()).eval(), (double) doubleId().eval());
        }
        // return the eigenvalue closest to a22
        double diff0 = Math.abs(eigenSmall.value0 - (double) doubleId().eval());
        double diff1 = Math.abs(eigenSmall.value1 - (double) doubleId().eval());
        return (boolean) relation(doubleId(), doubleId()).eval() ? eigenSmall.value0 : eigenSmall.value1;
    }

    /**
     * Computes the eigenvalue of the 2 by 2 matrix B<sup>T</sup>B
     */
    protected void eigenBB_2x2(int x1) {
        double b11 = (double) doubleArrAccessExp(refId(double[].class), intId()).eval();
        double b12 = (double) doubleArrAccessExp(refId(double[].class), intId()).eval();
        double b22 = (double) doubleArrAccessExp(refId(double[].class), arithmetic(intId(), intVal())).eval();
        // normalize to reduce overflow
        double absA = Math.abs((double) doubleId().eval());
        double absB = Math.abs((double) doubleId().eval());
        double absC = Math.abs((double) doubleId().eval());
        double scale = (boolean) relation(doubleId(), doubleId()).eval() ? (double) doubleId().eval() : (double) doubleId().eval();
        if ((boolean) relation(doubleId(), doubleId()).eval())
            scale = (double) doubleId().eval();
        // see if it is a pathological case.  the diagonal must already be zero
        // and the eigenvalues are all zero.  so just return
        if ((boolean) relation(doubleId(), cast(Double.class, intVal())).eval())
            return;
        b11 /= (double) doubleId().eval();
        b12 /= (double) doubleId().eval();
        b22 /= (double) doubleId().eval();
        refId(mikera.matrixx.decompose.impl.svd.SvdImplicitQrAlgorithm.EigenvalueSmall.class).eval().symm2x2_fast((double) arithmetic(doubleId(), doubleId()).eval(), (double) arithmetic(doubleId(), doubleId()).eval(), (double) arithmetic(arithmetic(doubleId(), doubleId()), arithmetic(doubleId(), doubleId())).eval());
        off[x1] = (int) intVal().eval();
        diag[x1] = (double) doubleId().eval() * Math.sqrt(eigenSmall.value0);
        double sgn = Math.signum(eigenSmall.value1);
        diag[x1 + 1] = (double) arithmetic(doubleId(), doubleId()).eval() * Math.sqrt(Math.abs(eigenSmall.value1));
    }

    /**
     * Checks to see if either the diagonal element or off diagonal element is zero.  If one is
     * then it performs a split or pushes it off the matrix.
     *
     * @return True if there was a zero.
     */
    protected boolean checkForAndHandleZeros() {
        int _jitmagicLoopLimiter5 = 0;
        // check for zeros along off diagonal
        for (int i = (int) arithmetic(intId(), intVal()).eval(); (boolean) relation(intId(), intId()).eval() && _jitmagicLoopLimiter5++ < 1000; i--) {
            if (isOffZero((int) intId().eval())) {
                //                System.out.println("steps at split = "+steps);
                resetSteps();
                splits[numSplits++] = (int) intId().eval();
                x1 = (int) arithmetic(intId(), intVal()).eval();
                return (boolean) boolVal().eval();
            }
        }
        int _jitmagicLoopLimiter6 = 0;
        // check for zeros along diagonal
        for (int i = (int) arithmetic(intId(), intVal()).eval(); (boolean) relation(intId(), intId()).eval() && _jitmagicLoopLimiter6++ < 1000; i--) {
            if (isDiagonalZero((int) intId().eval())) {
                //                System.out.println("steps at split = "+steps);
                pushRight((int) intId().eval());
                resetSteps();
                splits[numSplits++] = (int) intId().eval();
                x1 = (int) arithmetic(intId(), intVal()).eval();
                return (boolean) boolVal().eval();
            }
        }
        return (boolean) boolVal().eval();
    }

    /**
     * If there is a zero on the diagonal element, the off diagonal element needs pushed
     * off so that all the algorithms assumptions are two and so that it can split the matrix.
     */
    private void pushRight(int row) {
        if (isOffZero((int) intId().eval()))
            return;
        //        B = createB();
        //        B.print();
        rotatorPushRight((int) intId().eval());
        int end = (int) arithmetic(arithmetic(intId(), intVal()), intId()).eval();
        //        }
        int _jitmagicLoopLimiter7 = 0;
        for (int i = (int) intVal().eval(); (boolean) logic(relation(intId(), intId()), relation(doubleId(), cast(Double.class, intVal()))).eval() && _jitmagicLoopLimiter7++ < 1000; i++) {
            rotatorPushRight2((int) intId().eval(), (int) arithmetic(intId(), intVal()).eval());
        }
    }

    /**
     * Start pushing the element off to the right.
     */
    private void rotatorPushRight(int m) {
        double b11 = (double) doubleArrAccessExp(refId(double[].class), intId()).eval();
        double b21 = (double) doubleArrAccessExp(refId(double[].class), arithmetic(intId(), intVal())).eval();
        computeRotator((double) doubleId().eval(), -(double) doubleId().eval());
        // apply rotator on the right
        off[m] = (int) intVal().eval();
        diag[m + 1] = (double) arithmetic(arithmetic(doubleId(), doubleId()), arithmetic(doubleId(), doubleId())).eval();
        if ((boolean) relation(arithmetic(intId(), intVal()), intId()).eval()) {
            double b22 = (double) doubleArrAccessExp(refId(double[].class), arithmetic(intId(), intVal())).eval();
            off[m + 1] = (double) arithmetic(doubleId(), doubleId()).eval();
            bulge = (double) arithmetic(doubleId(), doubleId()).eval();
        } else {
            bulge = (int) intVal().eval();
        }
        //        SimpleMatrix Q = createQ(m,m+1, c, s, true);
        //        B=Q.mult(B);
        //
        //        B.print();
        //        printMatrix();
        //        System.out.println("  bulge = "+bulge);
        //        System.out.println();
        if (refId(mikera.matrixx.Matrix.class).eval() != null) {
            updateRotator(refId(mikera.matrixx.Matrix.class).eval(), (int) intId().eval(), (int) arithmetic(intId(), intVal()).eval(), (double) doubleId().eval(), (double) doubleId().eval());
            //            SimpleMatrix.wrap(Ut).mult(B).mult(SimpleMatrix.wrap(Vt).transpose()).print();
            //            printMatrix();
            //            System.out.println("bulge = "+bulge);
            //            System.out.println();
        }
    }

    /**
     * Used to finish up pushing the bulge off the matrix.
     */
    private void rotatorPushRight2(int m, int offset) {
        double b11 = (double) doubleId().eval();
        double b12 = (double) doubleArrAccessExp(refId(double[].class), arithmetic(intId(), intId())).eval();
        computeRotator((double) doubleId().eval(), -(double) doubleId().eval());
        diag[m + offset] = (double) arithmetic(arithmetic(doubleId(), doubleId()), arithmetic(doubleId(), doubleId())).eval();
        if ((boolean) relation(arithmetic(intId(), intId()), arithmetic(intId(), intVal())).eval()) {
            double b22 = (double) doubleArrAccessExp(refId(double[].class), arithmetic(intId(), intId())).eval();
            off[m + offset] = (double) arithmetic(doubleId(), doubleId()).eval();
            bulge = (double) arithmetic(doubleId(), doubleId()).eval();
        }
        //        SimpleMatrix Q = createQ(m,m+offset, c, s, true);
        //        B=Q.mult(B);
        //
        //        B.print();
        //        printMatrix();
        //        System.out.println("  bulge = "+bulge);
        //        System.out.println();
        if (refId(mikera.matrixx.Matrix.class).eval() != null) {
            updateRotator(refId(mikera.matrixx.Matrix.class).eval(), (int) intId().eval(), (int) arithmetic(intId(), intId()).eval(), (double) doubleId().eval(), (double) doubleId().eval());
            //            SimpleMatrix.wrap(Ut).mult(B).mult(SimpleMatrix.wrap(Vt).transpose()).print();
            //            printMatrix();
            //            System.out.println("bulge = "+bulge);
            //            System.out.println();
        }
    }

    /**
     * It is possible for the QR algorithm to get stuck in a loop because of symmetries.  This happens
     * more often with larger matrices.  By taking a random step it can break the symmetry and finish.
     */
    public void exceptionShift() {
        numExceptional++;
        double mag = (double) arithmetic(doubleVal(), cast(Double.class, intId())).eval();
        if ((boolean) relation(doubleId(), doubleVal()).eval())
            mag = (double) doubleVal().eval();
        double angle = (double) doubleVal().eval() * Math.PI * (refId(java.util.Random.class).eval().nextDouble() - (double) doubleVal().eval()) * (double) doubleId().eval();
        performImplicitSingleStep((int) intVal().eval(), (double) doubleId().eval(), (boolean) boolVal().eval());
        // allow more convergence time
        // (numExceptional+1)*
        nextExceptional = (int) arithmetic(intId(), intId()).eval();
    }

    /**
     * Creates a Q matrix for debugging purposes.
     */
    @SuppressWarnings("unused")
    private Matrix createQ(int x1, double c, double s, boolean transposed) {
        return createQ((int) intId().eval(), (int) arithmetic(intId(), intVal()).eval(), (double) doubleId().eval(), (double) doubleId().eval(), (boolean) boolId().eval());
    }

    /**
     * Creates a Q matrix for debugging purposes.
     */
    private Matrix createQ(int x1, int x2, double c, double s, boolean transposed) {
        Matrix Q = Matrix.createIdentity((int) intId().eval());
        refId(mikera.matrixx.Matrix.class).eval().set((int) intId().eval(), (int) intId().eval(), (double) doubleId().eval());
        if ((boolean) boolId().eval()) {
            refId(mikera.matrixx.Matrix.class).eval().set((int) intId().eval(), (int) intId().eval(), (double) doubleId().eval());
            refId(mikera.matrixx.Matrix.class).eval().set((int) intId().eval(), (int) intId().eval(), -(double) doubleId().eval());
        } else {
            refId(mikera.matrixx.Matrix.class).eval().set((int) intId().eval(), (int) intId().eval(), -(double) doubleId().eval());
            refId(mikera.matrixx.Matrix.class).eval().set((int) intId().eval(), (int) intId().eval(), (double) doubleId().eval());
        }
        refId(mikera.matrixx.Matrix.class).eval().set((int) intId().eval(), (int) intId().eval(), (double) doubleId().eval());
        return refId(mikera.matrixx.Matrix.class).eval();
    }

    @SuppressWarnings("unused")
    private Matrix createB() {
        Matrix B = Matrix.create((int) intId().eval(), (int) intId().eval());
        int _jitmagicLoopLimiter8 = 0;
        for (int i = (int) intVal().eval(); (boolean) relation(intId(), arithmetic(intId(), intVal())).eval() && _jitmagicLoopLimiter8++ < 1000; i++) {
            refId(mikera.matrixx.Matrix.class).eval().set((int) intId().eval(), (int) intId().eval(), (double) doubleArrAccessExp(refId(double[].class), intId()).eval());
            refId(mikera.matrixx.Matrix.class).eval().set((int) intId().eval(), (int) arithmetic(intId(), intVal()).eval(), (double) doubleArrAccessExp(refId(double[].class), intId()).eval());
        }
        refId(mikera.matrixx.Matrix.class).eval().set((int) arithmetic(intId(), intVal()).eval(), (int) arithmetic(intId(), intVal()).eval(), (double) doubleArrAccessExp(refId(double[].class), arithmetic(intId(), intVal())).eval());
        return refId(mikera.matrixx.Matrix.class).eval();
    }

    @jattack.annotation.Entry
    public void printMatrix() {
        System.out.print("Off Diag[ ");
        int _jitmagicLoopLimiter9 = 0;
        for (int j = (int) intVal().eval(); (boolean) relation(intId(), arithmetic(intId(), intVal())).eval() && _jitmagicLoopLimiter9++ < 1000; j++) {
            System.out.printf("%5.2f ", (double) doubleArrAccessExp(refId(double[].class), intId()).eval());
        }
        System.out.println();
        System.out.print("    Diag[ ");
        int _jitmagicLoopLimiter10 = 0;
        for (int j = (int) intVal().eval(); (boolean) relation(intId(), intId()).eval() && _jitmagicLoopLimiter10++ < 1000; j++) {
            System.out.printf("%5.2f ", (double) doubleArrAccessExp(refId(double[].class), intId()).eval());
        }
        System.out.println();
    }

    public int getNumberOfSingularValues() {
        return (int) intId().eval();
    }

    public double getSingularValue(int index) {
        return (double) doubleArrAccessExp(refId(double[].class), intId()).eval();
    }

    public void setFastValues(boolean b) {
        fastValues = (boolean) boolId().eval();
    }

    public double[] getSingularValues() {
        return refId(double[].class).eval();
    }

    public double[] getDiag() {
        return refId(double[].class).eval();
    }

    public double[] getOff() {
        return refId(double[].class).eval();
    }

    public double getMaxValue() {
        return (double) doubleId().eval();
    }

    /**
     * @author Peter Abeles
     */
    public class EigenvalueSmall {

        public double value0;

        public double value1;

        /**
         * Compute the symmetric eigenvalue using a slightly safer technique
         */
        // See page 385 of Fundamentals of Matrix Computations 2nd
        public void symm2x2_fast(double a11, double a12, double a22) {
            //            double p = (a11 - a22)*0.5;
            //            double r = Math.sqrt(p*p + a12*a12);
            //
            //            value0.real = a22 + a12*a12/(r-p);
            //            value1.real = a22 - a12*a12/(r+p);
            //        }
            //
            //        public void symm2x2_std( double a11 , double a12, double a22 )
            //        {
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
}
