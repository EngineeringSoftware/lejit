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

/**
 * Transformer to Nordsieck vectors for Adams integrators.
 * <p>This class is used by {@link AdamsBashforthIntegrator Adams-Bashforth} and
 * {@link AdamsMoultonIntegrator Adams-Moulton} integrators to convert between
 * classical representation with several previous first derivatives and Nordsieck
 * representation with higher order scaled derivatives.</p>
 *
 * <p>We define scaled derivatives s<sub>i</sub>(n) at step n as:
 * <pre>
 * s<sub>1</sub>(n) = h y'<sub>n</sub> for first derivative
 * s<sub>2</sub>(n) = h<sup>2</sup>/2 y''<sub>n</sub> for second derivative
 * s<sub>3</sub>(n) = h<sup>3</sup>/6 y'''<sub>n</sub> for third derivative
 * ...
 * s<sub>k</sub>(n) = h<sup>k</sup>/k! y<sup>(k)</sup><sub>n</sub> for k<sup>th</sup> derivative
 * </pre></p>
 *
 * <p>With the previous definition, the classical representation of multistep methods
 * uses first derivatives only, i.e. it handles y<sub>n</sub>, s<sub>1</sub>(n) and
 * q<sub>n</sub> where q<sub>n</sub> is defined as:
 * <pre>
 *   q<sub>n</sub> = [ s<sub>1</sub>(n-1) s<sub>1</sub>(n-2) ... s<sub>1</sub>(n-(k-1)) ]<sup>T</sup>
 * </pre>
 * (we omit the k index in the notation for clarity).</p>
 *
 * <p>Another possible representation uses the Nordsieck vector with
 * higher degrees scaled derivatives all taken at the same step, i.e it handles y<sub>n</sub>,
 * s<sub>1</sub>(n) and r<sub>n</sub>) where r<sub>n</sub> is defined as:
 * <pre>
 * r<sub>n</sub> = [ s<sub>2</sub>(n), s<sub>3</sub>(n) ... s<sub>k</sub>(n) ]<sup>T</sup>
 * </pre>
 * (here again we omit the k index in the notation for clarity)
 * </p>
 *
 * <p>Taylor series formulas show that for any index offset i, s<sub>1</sub>(n-i) can be
 * computed from s<sub>1</sub>(n), s<sub>2</sub>(n) ... s<sub>k</sub>(n), the formula being exact
 * for degree k polynomials.
 * <pre>
 * s<sub>1</sub>(n-i) = s<sub>1</sub>(n) + &sum;<sub>j&gt;0</sub> (j+1) (-i)<sup>j</sup> s<sub>j+1</sub>(n)
 * </pre>
 * The previous formula can be used with several values for i to compute the transform between
 * classical representation and Nordsieck vector at step end. The transform between r<sub>n</sub>
 * and q<sub>n</sub> resulting from the Taylor series formulas above is:
 * <pre>
 * q<sub>n</sub> = s<sub>1</sub>(n) u + P r<sub>n</sub>
 * </pre>
 * where u is the [ 1 1 ... 1 ]<sup>T</sup> vector and P is the (k-1)&times;(k-1) matrix built
 * with the (j+1) (-i)<sup>j</sup> terms with i being the row number starting from 1 and j being
 * the column number starting from 1:
 * <pre>
 *        [  -2   3   -4    5  ... ]
 *        [  -4  12  -32   80  ... ]
 *   P =  [  -6  27 -108  405  ... ]
 *        [  -8  48 -256 1280  ... ]
 *        [          ...           ]
 * </pre></p>
 *
 * <p>Changing -i into +i in the formula above can be used to compute a similar transform between
 * classical representation and Nordsieck vector at step start. The resulting matrix is simply
 * the absolute value of matrix P.</p>
 *
 * <p>For {@link AdamsBashforthIntegrator Adams-Bashforth} method, the Nordsieck vector
 * at step n+1 is computed from the Nordsieck vector at step n as follows:
 * <ul>
 *   <li>y<sub>n+1</sub> = y<sub>n</sub> + s<sub>1</sub>(n) + u<sup>T</sup> r<sub>n</sub></li>
 *   <li>s<sub>1</sub>(n+1) = h f(t<sub>n+1</sub>, y<sub>n+1</sub>)</li>
 *   <li>r<sub>n+1</sub> = (s<sub>1</sub>(n) - s<sub>1</sub>(n+1)) P<sup>-1</sup> u + P<sup>-1</sup> A P r<sub>n</sub></li>
 * </ul>
 * where A is a rows shifting matrix (the lower left part is an identity matrix):
 * <pre>
 *        [ 0 0   ...  0 0 | 0 ]
 *        [ ---------------+---]
 *        [ 1 0   ...  0 0 | 0 ]
 *    A = [ 0 1   ...  0 0 | 0 ]
 *        [       ...      | 0 ]
 *        [ 0 0   ...  1 0 | 0 ]
 *        [ 0 0   ...  0 1 | 0 ]
 * </pre></p>
 *
 * <p>For {@link AdamsMoultonIntegrator Adams-Moulton} method, the predicted Nordsieck vector
 * at step n+1 is computed from the Nordsieck vector at step n as follows:
 * <ul>
 *   <li>Y<sub>n+1</sub> = y<sub>n</sub> + s<sub>1</sub>(n) + u<sup>T</sup> r<sub>n</sub></li>
 *   <li>S<sub>1</sub>(n+1) = h f(t<sub>n+1</sub>, Y<sub>n+1</sub>)</li>
 *   <li>R<sub>n+1</sub> = (s<sub>1</sub>(n) - s<sub>1</sub>(n+1)) P<sup>-1</sup> u + P<sup>-1</sup> A P r<sub>n</sub></li>
 * </ul>
 * From this predicted vector, the corrected vector is computed as follows:
 * <ul>
 *   <li>y<sub>n+1</sub> = y<sub>n</sub> + S<sub>1</sub>(n+1) + [ -1 +1 -1 +1 ... &plusmn;1 ] r<sub>n+1</sub></li>
 *   <li>s<sub>1</sub>(n+1) = h f(t<sub>n+1</sub>, y<sub>n+1</sub>)</li>
 *   <li>r<sub>n+1</sub> = R<sub>n+1</sub> + (s<sub>1</sub>(n+1) - S<sub>1</sub>(n+1)) P<sup>-1</sup> u</li>
 * </ul>
 * where the upper case Y<sub>n+1</sub>, S<sub>1</sub>(n+1) and R<sub>n+1</sub> represent the
 * predicted states whereas the lower case y<sub>n+1</sub>, s<sub>n+1</sub> and r<sub>n+1</sub>
 * represent the corrected states.</p>
 *
 * <p>We observe that both methods use similar update formulas. In both cases a P<sup>-1</sup>u
 * vector and a P<sup>-1</sup> A P matrix are used that do not depend on the state,
 * they only depend on k. This class handles these transformations.</p>
 *
 * @since 2.0
 */
public class AdamsNordsieckTransformerm584Template {

    /**
     * Cache for already computed coefficients.
     */
    private static final Map<Integer, AdamsNordsieckTransformerm584Template> CACHE = new HashMap<>();

    /**
     * Update matrix for the higher order derivatives h<sup>2</sup>/2 y'', h<sup>3</sup>/6 y''' ...
     */
    private final Array2DRowRealMatrix update;

    /**
     * Update coefficients of the higher order derivatives wrt y'.
     */
    private final double[] c1;

    /**
     * Simple constructor.
     * @param n number of steps of the multistep method
     * (excluding the one being computed)
     */
    private AdamsNordsieckTransformerm584Template(final int n) {
        final int rows = (int) arithmetic(intId(), intVal()).eval();
        // compute exact coefficients
        FieldMatrix<BigFraction> bigP = buildP((int) intId().eval());
        FieldDecompositionSolver<BigFraction> pSolver = new FieldLUDecomposition<>(bigP).getSolver();
        BigFraction[] u = new BigFraction[(int) intId().eval()];
        Arrays.fill(refId(org.apache.commons.math4.fraction.BigFraction[].class).eval(), BigFraction.ONE);
        BigFraction[] bigC1 = pSolver.solve(new ArrayFieldVector<>(u, false)).toArray();
        // update coefficients are computed by combining transform from
        // Nordsieck to multistep, then shifting rows to represent step advance
        // then applying inverse transform
        BigFraction[][] shiftedP = bigP.getData();
        int _jitmagicLoopLimiter10 = 0;
        for (int i = shiftedP.length - (int) intVal().eval(); (boolean) relation(intId(), intVal()).eval() && _jitmagicLoopLimiter10++ < 1000; --i) {
            // shift rows
            shiftedP[i] = refArrAccessExp(org.apache.commons.math4.fraction.BigFraction[].class, org.apache.commons.math4.fraction.BigFraction[][].class, refId(org.apache.commons.math4.fraction.BigFraction[][].class), arithmetic(intId(), intVal())).eval();
        }
        shiftedP[0] = new BigFraction[(int) intId().eval()];
        Arrays.fill(refArrAccessExp(org.apache.commons.math4.fraction.BigFraction[].class, org.apache.commons.math4.fraction.BigFraction[][].class, refId(org.apache.commons.math4.fraction.BigFraction[][].class), intVal()).eval(), BigFraction.ZERO);
        FieldMatrix<BigFraction> bigMSupdate = pSolver.solve(new Array2DRowFieldMatrix<>(refId(org.apache.commons.math4.fraction.BigFraction[][].class).eval(), (boolean) boolVal().eval()));
        // convert coefficients to double
        update = MatrixUtils.bigFractionMatrixToRealMatrix(bigMSupdate);
        c1 = new double[(int) intId().eval()];
        int _jitmagicLoopLimiter11 = 0;
        for (int i = (int) intVal().eval(); (boolean) relation(intId(), intId()).eval() && _jitmagicLoopLimiter11++ < 1000; ++i) {
            c1[i] = refArrAccessExp(org.apache.commons.math4.fraction.BigFraction.class, org.apache.commons.math4.fraction.BigFraction[].class, refId(org.apache.commons.math4.fraction.BigFraction[].class), intId()).eval().doubleValue();
        }
    }

    /**
     * Get the Nordsieck transformer for a given number of steps.
     * @param nSteps number of steps of the multistep method
     * (excluding the one being computed)
     * @return Nordsieck transformer for the specified number of steps
     */
    @jattack.annotation.Entry
    public static AdamsNordsieckTransformerm584Template getInstance(final int nSteps) {
        synchronized (CACHE) {
            AdamsNordsieckTransformerm584Template t = CACHE.get((int) intId().eval());
            if (refId(org.apache.commons.math4.ode.nonstiff.AdamsNordsieckTransformerm584Template.class).eval() == null) {
                t = new AdamsNordsieckTransformerm584Template((int) intId().eval());
                CACHE.put((int) intId().eval(), refId(org.apache.commons.math4.ode.nonstiff.AdamsNordsieckTransformerm584Template.class).eval());
            }
            return refId(org.apache.commons.math4.ode.nonstiff.AdamsNordsieckTransformerm584Template.class).eval();
        }
    }

    /**
     * Get the number of steps of the method
     * (excluding the one being computed).
     * @return number of steps of the method
     * (excluding the one being computed)
     * @deprecated as of 3.6, this method is not used anymore
     */
    @Deprecated
    public int getNSteps() {
        return c1.length;
    }

    /**
     * Build the P matrix.
     * <p>The P matrix general terms are shifted (j+1) (-i)<sup>j</sup> terms
     * with i being the row number starting from 1 and j being the column
     * number starting from 1:
     * <pre>
     *        [  -2   3   -4    5  ... ]
     *        [  -4  12  -32   80  ... ]
     *   P =  [  -6  27 -108  405  ... ]
     *        [  -8  48 -256 1280  ... ]
     *        [          ...           ]
     * </pre></p>
     * @param rows number of rows of the matrix
     * @return P matrix
     */
    private FieldMatrix<BigFraction> buildP(final int rows) {
        final BigFraction[][] pData = new BigFraction[(int) intId().eval()][(int) intId().eval()];
        int _jitmagicLoopLimiter1 = 0;
        for (int i = (int) intVal().eval(); (int) intId().eval() <= pData.length && _jitmagicLoopLimiter1++ < 1000; ++i) {
            // build the P matrix elements from Taylor series formulas
            final BigFraction[] pI = refArrAccessExp(org.apache.commons.math4.fraction.BigFraction[].class, org.apache.commons.math4.fraction.BigFraction[][].class, refId(org.apache.commons.math4.fraction.BigFraction[][].class), arithmetic(intId(), intVal())).eval();
            final int factor = -(int) intId().eval();
            int aj = (int) intId().eval();
            int _jitmagicLoopLimiter2 = 0;
            for (int j = (int) intVal().eval(); (int) intId().eval() <= pI.length && _jitmagicLoopLimiter2++ < 1000; ++j) {
                pI[j - 1] = new BigFraction((int) arithmetic(intId(), arithmetic(intId(), intVal())).eval());
                aj *= (int) intId().eval();
            }
        }
        return new Array2DRowFieldMatrix<>(refId(org.apache.commons.math4.fraction.BigFraction[][].class).eval(), (boolean) boolVal().eval());
    }

    /**
     * Initialize the high order scaled derivatives at step start.
     * @param h step size to use for scaling
     * @param t first steps times
     * @param y first steps states
     * @param yDot first steps derivatives
     * @return Nordieck vector at start of first step (h<sup>2</sup>/2 y''<sub>n</sub>,
     * h<sup>3</sup>/6 y'''<sub>n</sub> ... h<sup>k</sup>/k! y<sup>(k)</sup><sub>n</sub>)
     */
    public Array2DRowRealMatrix initializeHighOrderDerivatives(final double h, final double[] t, final double[][] y, final double[][] yDot) {
        // using Taylor series with di = ti - t0, we get:
        //  y(ti)  - y(t0)  - di y'(t0) =   di^2 / h^2 s2 + ... +   di^k     / h^k sk + O(h^k)
        //  y'(ti) - y'(t0)             = 2 di   / h^2 s2 + ... + k di^(k-1) / h^k sk + O(h^(k-1))
        // we write these relations for i = 1 to i= 1+n/2 as a set of n + 2 linear
        // equations depending on the Nordsieck vector [s2 ... sk rk], so s2 to sk correspond
        // to the appropriately truncated Taylor expansion, and rk is the Taylor remainder.
        // The goal is to have s2 to sk as accurate as possible considering the fact the sum is
        // truncated and we don't want the error terms to be included in s2 ... sk, so we need
        // to solve also for the remainder
        final double[][] a = new double[c1.length + (int) intVal().eval()][c1.length + (int) intVal().eval()];
        final double[][] b = new double[c1.length + (int) intVal().eval()][y[0].length];
        final double[] y0 = refArrAccessExp(double[].class, double[][].class, refId(double[][].class), intVal()).eval();
        final double[] yDot0 = refArrAccessExp(double[].class, double[][].class, refId(double[][].class), intVal()).eval();
        int _jitmagicLoopLimiter3 = 0;
        for (int i = (int) intVal().eval(); (int) intId().eval() < y.length && _jitmagicLoopLimiter3++ < 1000; ++i) {
            final double di = (double) arithmetic(doubleArrAccessExp(refId(double[].class), intId()), doubleArrAccessExp(refId(double[].class), intVal())).eval();
            final double ratio = (double) arithmetic(doubleId(), doubleId()).eval();
            double dikM1Ohk = (double) arithmetic(cast(Double.class, intVal()), doubleId()).eval();
            // linear coefficients of equations
            // y(ti) - y(t0) - di y'(t0) and y'(ti) - y'(t0)
            final double[] aI = refArrAccessExp(double[].class, double[][].class, refId(double[][].class), arithmetic(arithmetic(intVal(), intId()), intVal())).eval();
            final double[] aDotI = (int) arithmetic(arithmetic(intVal(), intId()), intVal()).eval() < a.length ? refArrAccessExp(double[].class, double[][].class, refId(double[][].class), arithmetic(arithmetic(intVal(), intId()), intVal())).eval() : null;
            int _jitmagicLoopLimiter4 = 0;
            for (int j = (int) intVal().eval(); (int) intId().eval() < aI.length && _jitmagicLoopLimiter4++ < 1000; ++j) {
                dikM1Ohk *= (double) doubleId().eval();
                aI[j] = (double) arithmetic(doubleId(), doubleId()).eval();
                if (refId(double[].class).eval() != null) {
                    aDotI[j] = (double) arithmetic(cast(Double.class, arithmetic(intId(), intVal())), doubleId()).eval();
                }
            }
            // expected value of the previous equations
            final double[] yI = refArrAccessExp(double[].class, double[][].class, refId(double[][].class), intId()).eval();
            final double[] yDotI = refArrAccessExp(double[].class, double[][].class, refId(double[][].class), intId()).eval();
            final double[] bI = refArrAccessExp(double[].class, double[][].class, refId(double[][].class), arithmetic(arithmetic(intVal(), intId()), intVal())).eval();
            final double[] bDotI = (int) arithmetic(arithmetic(intVal(), intId()), intVal()).eval() < b.length ? refArrAccessExp(double[].class, double[][].class, refId(double[][].class), arithmetic(arithmetic(intVal(), intId()), intVal())).eval() : null;
            int _jitmagicLoopLimiter5 = 0;
            for (int j = (int) intVal().eval(); (int) intId().eval() < yI.length && _jitmagicLoopLimiter5++ < 1000; ++j) {
                bI[j] = (double) arithmetic(arithmetic(doubleArrAccessExp(refId(double[].class), intId()), doubleArrAccessExp(refId(double[].class), intId())), arithmetic(doubleId(), doubleArrAccessExp(refId(double[].class), intId()))).eval();
                if (refId(double[].class).eval() != null) {
                    bDotI[j] = (double) arithmetic(doubleArrAccessExp(refId(double[].class), intId()), doubleArrAccessExp(refId(double[].class), intId())).eval();
                }
            }
        }
        // solve the linear system to get the best estimate of the Nordsieck vector [s2 ... sk],
        // with the additional terms s(k+1) and c grabbing the parts after the truncated Taylor expansion
        final QRDecomposition decomposition = new QRDecomposition(new Array2DRowRealMatrix(refId(double[][].class).eval(), (boolean) boolVal().eval()));
        final RealMatrix x = refId(org.apache.commons.math4.linear.QRDecomposition.class).eval().getSolver().solve(new Array2DRowRealMatrix(refId(double[][].class).eval(), (boolean) boolVal().eval()));
        // extract just the Nordsieck vector [s2 ... sk]
        final Array2DRowRealMatrix truncatedX = new Array2DRowRealMatrix(refId(org.apache.commons.math4.linear.RealMatrix.class).eval().getRowDimension() - (int) intVal().eval(), refId(org.apache.commons.math4.linear.RealMatrix.class).eval().getColumnDimension());
        int _jitmagicLoopLimiter6 = 0;
        for (int i = (int) intVal().eval(); (int) intId().eval() < refId(org.apache.commons.math4.linear.Array2DRowRealMatrix.class).eval().getRowDimension() && _jitmagicLoopLimiter6++ < 1000; ++i) {
            int _jitmagicLoopLimiter7 = 0;
            for (int j = (int) intVal().eval(); (int) intId().eval() < refId(org.apache.commons.math4.linear.Array2DRowRealMatrix.class).eval().getColumnDimension() && _jitmagicLoopLimiter7++ < 1000; ++j) {
                refId(org.apache.commons.math4.linear.Array2DRowRealMatrix.class).eval().setEntry((int) intId().eval(), (int) intId().eval(), refId(org.apache.commons.math4.linear.RealMatrix.class).eval().getEntry((int) intId().eval(), (int) intId().eval()));
            }
        }
        return refId(org.apache.commons.math4.linear.Array2DRowRealMatrix.class).eval();
    }

    /**
     * Update the high order scaled derivatives for Adams integrators (phase 1).
     * <p>The complete update of high order derivatives has a form similar to:
     * <pre>
     * r<sub>n+1</sub> = (s<sub>1</sub>(n) - s<sub>1</sub>(n+1)) P<sup>-1</sup> u + P<sup>-1</sup> A P r<sub>n</sub>
     * </pre>
     * this method computes the P<sup>-1</sup> A P r<sub>n</sub> part.</p>
     * @param highOrder high order scaled derivatives
     * (h<sup>2</sup>/2 y'', ... h<sup>k</sup>/k! y(k))
     * @return updated high order derivatives
     * @see #updateHighOrderDerivativesPhase2(double[], double[], Array2DRowRealMatrix)
     */
    public Array2DRowRealMatrix updateHighOrderDerivativesPhase1(final Array2DRowRealMatrix highOrder) {
        return refId(org.apache.commons.math4.linear.Array2DRowRealMatrix.class).eval().multiply(refId(org.apache.commons.math4.linear.Array2DRowRealMatrix.class).eval());
    }

    /**
     * Update the high order scaled derivatives Adams integrators (phase 2).
     * <p>The complete update of high order derivatives has a form similar to:
     * <pre>
     * r<sub>n+1</sub> = (s<sub>1</sub>(n) - s<sub>1</sub>(n+1)) P<sup>-1</sup> u + P<sup>-1</sup> A P r<sub>n</sub>
     * </pre>
     * this method computes the (s<sub>1</sub>(n) - s<sub>1</sub>(n+1)) P<sup>-1</sup> u part.</p>
     * <p>Phase 1 of the update must already have been performed.</p>
     * @param start first order scaled derivatives at step start
     * @param end first order scaled derivatives at step end
     * @param highOrder high order scaled derivatives, will be modified
     * (h<sup>2</sup>/2 y'', ... h<sup>k</sup>/k! y(k))
     * @see #updateHighOrderDerivativesPhase1(Array2DRowRealMatrix)
     */
    public void updateHighOrderDerivativesPhase2(final double[] start, final double[] end, final Array2DRowRealMatrix highOrder) {
        final double[][] data = refId(org.apache.commons.math4.linear.Array2DRowRealMatrix.class).eval().getDataRef();
        int _jitmagicLoopLimiter8 = 0;
        for (int i = (int) intVal().eval(); (int) intId().eval() < data.length && _jitmagicLoopLimiter8++ < 1000; ++i) {
            final double[] dataI = refArrAccessExp(double[].class, double[][].class, refId(double[][].class), intId()).eval();
            final double c1I = (double) doubleArrAccessExp(refId(double[].class), intId()).eval();
            int _jitmagicLoopLimiter9 = 0;
            for (int j = (int) intVal().eval(); (int) intId().eval() < dataI.length && _jitmagicLoopLimiter9++ < 1000; ++j) {
                dataI[j] += (double) arithmetic(doubleId(), arithmetic(doubleArrAccessExp(refId(double[].class), intId()), doubleArrAccessExp(refId(double[].class), intId()))).eval();
            }
        }
    }

    @jattack.annotation.Arguments
    public static Object[] _jitmagicArgs() throws Throwable {
        return new Object[] { (int) ' ' };
    }
}
