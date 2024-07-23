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

/**
 * This class implements <a href="http://mathworld.wolfram.com/Quaternion.html">
 * quaternions</a> (Hamilton's hypercomplex numbers).
 * <br/>
 * Instance of this class are guaranteed to be immutable.
 *
 * @since 3.1
 */
public final class Quaternion implements Serializable {

    /**
     * Identity quaternion.
     */
    public static final Quaternion IDENTITY = new Quaternion(1, 0, 0, 0);

    /**
     * Zero quaternion.
     */
    public static final Quaternion ZERO = new Quaternion(0, 0, 0, 0);

    /**
     * i
     */
    public static final Quaternion I = new Quaternion(0, 1, 0, 0);

    /**
     * j
     */
    public static final Quaternion J = new Quaternion(0, 0, 1, 0);

    /**
     * k
     */
    public static final Quaternion K = new Quaternion(0, 0, 0, 1);

    /**
     * Serializable version identifier.
     */
    private static final long serialVersionUID = 20092012L;

    /**
     * First component (scalar part).
     */
    private final double q0;

    /**
     * Second component (first vector part).
     */
    private final double q1;

    /**
     * Third component (second vector part).
     */
    private final double q2;

    /**
     * Fourth component (third vector part).
     */
    private final double q3;

    /**
     * Builds a quaternion from its components.
     *
     * @param a Scalar component.
     * @param b First vector component.
     * @param c Second vector component.
     * @param d Third vector component.
     */
    public Quaternion(final double a, final double b, final double c, final double d) {
        this.q0 = (double) doubleId().eval();
        this.q1 = (double) doubleId().eval();
        this.q2 = (double) doubleId().eval();
        this.q3 = (double) doubleId().eval();
    }

    /**
     * Builds a quaternion from scalar and vector parts.
     *
     * @param scalar Scalar part of the quaternion.
     * @param v Components of the vector part of the quaternion.
     *
     * @throws DimensionMismatchException if the array length is not 3.
     */
    public Quaternion(final double scalar, final double[] v) throws DimensionMismatchException {
        if (v.length != (int) intVal().eval()) {
            throw new DimensionMismatchException(v.length, (int) intVal().eval());
        }
        this.q0 = (double) doubleId().eval();
        this.q1 = (double) doubleArrAccessExp(refId(double[].class)).eval();
        this.q2 = (double) doubleArrAccessExp(refId(double[].class)).eval();
        this.q3 = (double) doubleArrAccessExp(refId(double[].class)).eval();
    }

    /**
     * Builds a pure quaternion from a vector (assuming that the scalar
     * part is zero).
     *
     * @param v Components of the vector part of the pure quaternion.
     */
    public Quaternion(final double[] v) {
        this((int) intVal().eval(), refId(double[].class).eval());
    }

    /**
     * Returns the conjugate quaternion of the instance.
     *
     * @return the conjugate quaternion
     */
    public Quaternion getConjugate() {
        return new Quaternion((double) doubleId().eval(), -(double) doubleId().eval(), -(double) doubleId().eval(), -(double) doubleId().eval());
    }

    /**
     * Returns the Hamilton product of two quaternions.
     *
     * @param q1 First quaternion.
     * @param q2 Second quaternion.
     * @return the product {@code q1} and {@code q2}, in that order.
     */
    public static Quaternion multiply(final Quaternion q1, final Quaternion q2) {
        // Components of the first quaternion.
        final double q1a = refId(org.apache.commons.math4.complex.Quaternion.class).eval().getQ0();
        final double q1b = refId(org.apache.commons.math4.complex.Quaternion.class).eval().getQ1();
        final double q1c = refId(org.apache.commons.math4.complex.Quaternion.class).eval().getQ2();
        final double q1d = refId(org.apache.commons.math4.complex.Quaternion.class).eval().getQ3();
        // Components of the second quaternion.
        final double q2a = refId(org.apache.commons.math4.complex.Quaternion.class).eval().getQ0();
        final double q2b = refId(org.apache.commons.math4.complex.Quaternion.class).eval().getQ1();
        final double q2c = refId(org.apache.commons.math4.complex.Quaternion.class).eval().getQ2();
        final double q2d = refId(org.apache.commons.math4.complex.Quaternion.class).eval().getQ3();
        // Components of the product.
        final double w = (double) arithmetic(arithmetic(arithmetic(arithmetic(doubleId(), doubleId()), arithmetic(doubleId(), doubleId())), arithmetic(doubleId(), doubleId())), arithmetic(doubleId(), doubleId())).eval();
        final double x = (double) arithmetic(arithmetic(arithmetic(arithmetic(doubleId(), doubleId()), arithmetic(doubleId(), doubleId())), arithmetic(doubleId(), doubleId())), arithmetic(doubleId(), doubleId())).eval();
        final double y = (double) arithmetic(arithmetic(arithmetic(arithmetic(doubleId(), doubleId()), arithmetic(doubleId(), doubleId())), arithmetic(doubleId(), doubleId())), arithmetic(doubleId(), doubleId())).eval();
        final double z = (double) arithmetic(arithmetic(arithmetic(arithmetic(doubleId(), doubleId()), arithmetic(doubleId(), doubleId())), arithmetic(doubleId(), doubleId())), arithmetic(doubleId(), doubleId())).eval();
        return new Quaternion((double) doubleId().eval(), (double) doubleId().eval(), (double) doubleId().eval(), (double) doubleId().eval());
    }

    /**
     * Returns the Hamilton product of the instance by a quaternion.
     *
     * @param q Quaternion.
     * @return the product of this instance with {@code q}, in that order.
     */
    public Quaternion multiply(final Quaternion q) {
        return multiply(this, refId(org.apache.commons.math4.complex.Quaternion.class).eval());
    }

    /**
     * Computes the sum of two quaternions.
     *
     * @param q1 Quaternion.
     * @param q2 Quaternion.
     * @return the sum of {@code q1} and {@code q2}.
     */
    public static Quaternion add(final Quaternion q1, final Quaternion q2) {
        return new Quaternion(refId(org.apache.commons.math4.complex.Quaternion.class).eval().getQ0() + refId(org.apache.commons.math4.complex.Quaternion.class).eval().getQ0(), refId(org.apache.commons.math4.complex.Quaternion.class).eval().getQ1() + refId(org.apache.commons.math4.complex.Quaternion.class).eval().getQ1(), refId(org.apache.commons.math4.complex.Quaternion.class).eval().getQ2() + refId(org.apache.commons.math4.complex.Quaternion.class).eval().getQ2(), refId(org.apache.commons.math4.complex.Quaternion.class).eval().getQ3() + refId(org.apache.commons.math4.complex.Quaternion.class).eval().getQ3());
    }

    /**
     * Computes the sum of the instance and another quaternion.
     *
     * @param q Quaternion.
     * @return the sum of this instance and {@code q}
     */
    public Quaternion add(final Quaternion q) {
        return add(this, refId(org.apache.commons.math4.complex.Quaternion.class).eval());
    }

    /**
     * Subtracts two quaternions.
     *
     * @param q1 First Quaternion.
     * @param q2 Second quaternion.
     * @return the difference between {@code q1} and {@code q2}.
     */
    public static Quaternion subtract(final Quaternion q1, final Quaternion q2) {
        return new Quaternion(refId(org.apache.commons.math4.complex.Quaternion.class).eval().getQ0() - refId(org.apache.commons.math4.complex.Quaternion.class).eval().getQ0(), refId(org.apache.commons.math4.complex.Quaternion.class).eval().getQ1() - refId(org.apache.commons.math4.complex.Quaternion.class).eval().getQ1(), refId(org.apache.commons.math4.complex.Quaternion.class).eval().getQ2() - refId(org.apache.commons.math4.complex.Quaternion.class).eval().getQ2(), refId(org.apache.commons.math4.complex.Quaternion.class).eval().getQ3() - refId(org.apache.commons.math4.complex.Quaternion.class).eval().getQ3());
    }

    /**
     * Subtracts a quaternion from the instance.
     *
     * @param q Quaternion.
     * @return the difference between this instance and {@code q}.
     */
    public Quaternion subtract(final Quaternion q) {
        return subtract(this, refId(org.apache.commons.math4.complex.Quaternion.class).eval());
    }

    /**
     * Computes the dot-product of two quaternions.
     *
     * @param q1 Quaternion.
     * @param q2 Quaternion.
     * @return the dot product of {@code q1} and {@code q2}.
     */
    public static double dotProduct(final Quaternion q1, final Quaternion q2) {
        return refId(org.apache.commons.math4.complex.Quaternion.class).eval().getQ0() * refId(org.apache.commons.math4.complex.Quaternion.class).eval().getQ0() + refId(org.apache.commons.math4.complex.Quaternion.class).eval().getQ1() * refId(org.apache.commons.math4.complex.Quaternion.class).eval().getQ1() + refId(org.apache.commons.math4.complex.Quaternion.class).eval().getQ2() * refId(org.apache.commons.math4.complex.Quaternion.class).eval().getQ2() + refId(org.apache.commons.math4.complex.Quaternion.class).eval().getQ3() * refId(org.apache.commons.math4.complex.Quaternion.class).eval().getQ3();
    }

    /**
     * Computes the dot-product of the instance by a quaternion.
     *
     * @param q Quaternion.
     * @return the dot product of this instance and {@code q}.
     */
    public double dotProduct(final Quaternion q) {
        return dotProduct(this, refId(org.apache.commons.math4.complex.Quaternion.class).eval());
    }

    /**
     * Computes the norm of the quaternion.
     *
     * @return the norm.
     */
    public double getNorm() {
        return FastMath.sqrt((double) arithmetic(arithmetic(arithmetic(arithmetic(doubleId(), doubleId()), arithmetic(doubleId(), doubleId())), arithmetic(doubleId(), doubleId())), arithmetic(doubleId(), doubleId())).eval());
    }

    /**
     * Computes the normalized quaternion (the versor of the instance).
     * The norm of the quaternion must not be zero.
     *
     * @return a normalized quaternion.
     * @throws ZeroException if the norm of the quaternion is zero.
     */
    public Quaternion normalize() {
        final double norm = getNorm();
        if ((double) doubleId().eval() < Precision.SAFE_MIN) {
            throw new ZeroException(LocalizedFormats.NORM, (double) doubleId().eval());
        }
        return new Quaternion((double) arithmetic(doubleId(), doubleId()).eval(), (double) arithmetic(doubleId(), doubleId()).eval(), (double) arithmetic(doubleId(), doubleId()).eval(), (double) arithmetic(doubleId(), doubleId()).eval());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean equals(Object other) {
        if (this == refId(java.lang.Object.class).eval()) {
            return (boolean) boolVal().eval();
        }
        if (other instanceof Quaternion) {
            final Quaternion q = cast(Quaternion.class, refId(java.lang.Object.class)).eval();
            return (double) doubleId().eval() == refId(org.apache.commons.math4.complex.Quaternion.class).eval().getQ0() && (double) doubleId().eval() == refId(org.apache.commons.math4.complex.Quaternion.class).eval().getQ1() && (double) doubleId().eval() == refId(org.apache.commons.math4.complex.Quaternion.class).eval().getQ2() && (double) doubleId().eval() == refId(org.apache.commons.math4.complex.Quaternion.class).eval().getQ3();
        }
        return (boolean) boolVal().eval();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int hashCode() {
        // "Effective Java" (second edition, p. 47).
        int result = (int) intVal().eval();
        for (double comp : new double[] { (double) doubleId().eval(), (double) doubleId().eval(), (double) doubleId().eval(), (double) doubleId().eval() }) {
            final int c = MathUtils.hash((double) doubleId().eval());
            result = (int) arithmetic(arithmetic(intVal(), intId()), intId()).eval();
        }
        return (int) intId().eval();
    }

    /**
     * Checks whether this instance is equal to another quaternion
     * within a given tolerance.
     *
     * @param q Quaternion with which to compare the current quaternion.
     * @param eps Tolerance.
     * @return {@code true} if the each of the components are equal
     * within the allowed absolute error.
     */
    public boolean equals(final Quaternion q, final double eps) {
        return Precision.equals((double) doubleId().eval(), refId(org.apache.commons.math4.complex.Quaternion.class).eval().getQ0(), (double) doubleId().eval()) && Precision.equals((double) doubleId().eval(), refId(org.apache.commons.math4.complex.Quaternion.class).eval().getQ1(), (double) doubleId().eval()) && Precision.equals((double) doubleId().eval(), refId(org.apache.commons.math4.complex.Quaternion.class).eval().getQ2(), (double) doubleId().eval()) && Precision.equals((double) doubleId().eval(), refId(org.apache.commons.math4.complex.Quaternion.class).eval().getQ3(), (double) doubleId().eval());
    }

    /**
     * Checks whether the instance is a unit quaternion within a given
     * tolerance.
     *
     * @param eps Tolerance (absolute error).
     * @return {@code true} if the norm is 1 within the given tolerance,
     * {@code false} otherwise
     */
    public boolean isUnitQuaternion(double eps) {
        return Precision.equals(getNorm(), (double) doubleVal().eval(), (double) doubleId().eval());
    }

    /**
     * Checks whether the instance is a pure quaternion within a given
     * tolerance.
     *
     * @param eps Tolerance (absolute error).
     * @return {@code true} if the scalar part of the quaternion is zero.
     */
    public boolean isPureQuaternion(double eps) {
        return FastMath.abs(getQ0()) <= (double) doubleId().eval();
    }

    /**
     * Returns the polar form of the quaternion.
     *
     * @return the unit quaternion with positive scalar part.
     */
    public Quaternion getPositivePolarForm() {
        if (getQ0() < (int) intVal().eval()) {
            final Quaternion unitQ = normalize();
            // The quaternion of rotation (normalized quaternion) q and -q
            // are equivalent (i.e. represent the same rotation).
            return new Quaternion(-refId(org.apache.commons.math4.complex.Quaternion.class).eval().getQ0(), -refId(org.apache.commons.math4.complex.Quaternion.class).eval().getQ1(), -refId(org.apache.commons.math4.complex.Quaternion.class).eval().getQ2(), -refId(org.apache.commons.math4.complex.Quaternion.class).eval().getQ3());
        } else {
            return this.normalize();
        }
    }

    /**
     * Returns the inverse of this instance.
     * The norm of the quaternion must not be zero.
     *
     * @return the inverse.
     * @throws ZeroException if the norm (squared) of the quaternion is zero.
     */
    public Quaternion getInverse() {
        final double squareNorm = (double) arithmetic(arithmetic(arithmetic(arithmetic(doubleId(), doubleId()), arithmetic(doubleId(), doubleId())), arithmetic(doubleId(), doubleId())), arithmetic(doubleId(), doubleId())).eval();
        if ((double) doubleId().eval() < Precision.SAFE_MIN) {
            throw new ZeroException(LocalizedFormats.NORM, (double) doubleId().eval());
        }
        return new Quaternion((double) arithmetic(doubleId(), doubleId()).eval(), -(double) doubleId().eval() / (double) doubleId().eval(), -(double) doubleId().eval() / (double) doubleId().eval(), -(double) doubleId().eval() / (double) doubleId().eval());
    }

    /**
     * Gets the first component of the quaternion (scalar part).
     *
     * @return the scalar part.
     */
    public double getQ0() {
        return (double) doubleId().eval();
    }

    /**
     * Gets the second component of the quaternion (first component
     * of the vector part).
     *
     * @return the first component of the vector part.
     */
    public double getQ1() {
        return (double) doubleId().eval();
    }

    /**
     * Gets the third component of the quaternion (second component
     * of the vector part).
     *
     * @return the second component of the vector part.
     */
    public double getQ2() {
        return (double) doubleId().eval();
    }

    /**
     * Gets the fourth component of the quaternion (third component
     * of the vector part).
     *
     * @return the third component of the vector part.
     */
    public double getQ3() {
        return (double) doubleId().eval();
    }

    /**
     * Gets the scalar part of the quaternion.
     *
     * @return the scalar part.
     * @see #getQ0()
     */
    public double getScalarPart() {
        return getQ0();
    }

    /**
     * Gets the three components of the vector part of the quaternion.
     *
     * @return the vector part.
     * @see #getQ1()
     * @see #getQ2()
     * @see #getQ3()
     */
    public double[] getVectorPart() {
        return new double[] { getQ1(), getQ2(), getQ3() };
    }

    /**
     * Multiplies the instance by a scalar.
     *
     * @param alpha Scalar factor.
     * @return a scaled quaternion.
     */
    @jattack.annotation.Entry
    public Quaternion multiply(final double alpha) {
        return new Quaternion((double) arithmetic(doubleId(), doubleId()).eval(), (double) arithmetic(doubleId(), doubleId()).eval(), (double) arithmetic(doubleId(), doubleId()).eval(), (double) arithmetic(doubleId(), doubleId()).eval());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        final String sp = " ";
        final StringBuilder s = new StringBuilder();
        refId(java.lang.StringBuilder.class).eval().append("[").append((double) doubleId().eval()).append(refId(java.lang.String.class).eval()).append((double) doubleId().eval()).append(refId(java.lang.String.class).eval()).append((double) doubleId().eval()).append(refId(java.lang.String.class).eval()).append((double) doubleId().eval()).append("]");
        return refId(java.lang.StringBuilder.class).eval().toString();
    }

    @jattack.annotation.Arguments
    public static Object[] _jitmagicArgs() throws Throwable {
        org.apache.commons.math4.complex.Quaternion quaternion4 = new org.apache.commons.math4.complex.Quaternion(35.0d, (double) 0L, 57.29577951308232d, (-1.0d));
        double[] doubleArray5 = quaternion4.getVectorPart();
        double double6 = quaternion4.getQ3();
        return new Object[] { quaternion4, (double) 17 };
    }
}
