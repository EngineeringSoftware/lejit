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
package org.apache.commons.lang3;

import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.BitSet;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.apache.commons.lang3.math.NumberUtils;
import org.apache.commons.lang3.mutable.MutableInt;
import static jattack.Boom.*;
import jattack.annotation.*;

/**
 * <p>Operations on arrays, primitive arrays (like {@code int[]}) and
 * primitive wrapper arrays (like {@code Integer[]}).
 *
 * <p>This class tries to handle {@code null} input gracefully.
 * An exception will not be thrown for a {@code null}
 * array input. However, an Object array that contains a {@code null}
 * element may throw an exception. Each method documents its behavior.
 *
 * <p>#ThreadSafe#
 * @since 2.0
 */
public class ArrayUtils {

    /**
     * An empty immutable {@code boolean} array.
     */
    public static final boolean[] EMPTY_BOOLEAN_ARRAY = {};

    /**
     * An empty immutable {@code Boolean} array.
     */
    public static final Boolean[] EMPTY_BOOLEAN_OBJECT_ARRAY = {};

    /**
     * An empty immutable {@code byte} array.
     */
    public static final byte[] EMPTY_BYTE_ARRAY = {};

    /**
     * An empty immutable {@code Byte} array.
     */
    public static final Byte[] EMPTY_BYTE_OBJECT_ARRAY = {};

    /**
     * An empty immutable {@code char} array.
     */
    public static final char[] EMPTY_CHAR_ARRAY = {};

    /**
     * An empty immutable {@code Character} array.
     */
    public static final Character[] EMPTY_CHARACTER_OBJECT_ARRAY = {};

    /**
     * An empty immutable {@code Class} array.
     */
    public static final Class<?>[] EMPTY_CLASS_ARRAY = new Class[0];

    /**
     * An empty immutable {@code double} array.
     */
    public static final double[] EMPTY_DOUBLE_ARRAY = {};

    /**
     * An empty immutable {@code Double} array.
     */
    public static final Double[] EMPTY_DOUBLE_OBJECT_ARRAY = {};

    /**
     * An empty immutable {@code Field} array.
     *
     * @since 3.10
     */
    public static final Field[] EMPTY_FIELD_ARRAY = {};

    /**
     * An empty immutable {@code float} array.
     */
    public static final float[] EMPTY_FLOAT_ARRAY = {};

    /**
     * An empty immutable {@code Float} array.
     */
    public static final Float[] EMPTY_FLOAT_OBJECT_ARRAY = {};

    /**
     * An empty immutable {@code int} array.
     */
    public static final int[] EMPTY_INT_ARRAY = {};

    /**
     * An empty immutable {@code Integer} array.
     */
    public static final Integer[] EMPTY_INTEGER_OBJECT_ARRAY = {};

    /**
     * An empty immutable {@code long} array.
     */
    public static final long[] EMPTY_LONG_ARRAY = {};

    /**
     * An empty immutable {@code Long} array.
     */
    public static final Long[] EMPTY_LONG_OBJECT_ARRAY = {};

    /**
     * An empty immutable {@code Method} array.
     *
     * @since 3.10
     */
    public static final Method[] EMPTY_METHOD_ARRAY = {};

    /**
     * An empty immutable {@code Object} array.
     */
    public static final Object[] EMPTY_OBJECT_ARRAY = {};

    /**
     * An empty immutable {@code short} array.
     */
    public static final short[] EMPTY_SHORT_ARRAY = {};

    /**
     * An empty immutable {@code Short} array.
     */
    public static final Short[] EMPTY_SHORT_OBJECT_ARRAY = {};

    /**
     * An empty immutable {@code String} array.
     */
    public static final String[] EMPTY_STRING_ARRAY = {};

    /**
     * An empty immutable {@code Throwable} array.
     *
     * @since 3.10
     */
    public static final Throwable[] EMPTY_THROWABLE_ARRAY = {};

    /**
     * An empty immutable {@code Type} array.
     *
     * @since 3.10
     */
    public static final Type[] EMPTY_TYPE_ARRAY = {};

    /**
     * The index value when an element is not found in a list or array: {@code -1}.
     * This value is returned by methods in this class and can also be used in comparisons with values returned by
     * various method from {@link java.util.List}.
     */
    public static final int INDEX_NOT_FOUND = -1;

    /**
     * <p>Copies the given array and adds the given element at the end of the new array.
     *
     * <p>The new array contains the same elements of the input
     * array plus the given element in the last position. The component type of
     * the new array is the same as that of the input array.
     *
     * <p>If the input array is {@code null}, a new one element array is returned
     *  whose component type is the same as the element.
     *
     * <pre>
     * ArrayUtils.add(null, true)          = [true]
     * ArrayUtils.add([true], false)       = [true, false]
     * ArrayUtils.add([true, false], true) = [true, false, true]
     * </pre>
     *
     * @param array  the array to copy and add the element to, may be {@code null}
     * @param element  the object to add at the last index of the new array
     * @return A new array containing the existing elements plus the new element
     * @since 2.1
     */
    public static boolean[] add(final boolean[] array, final boolean element) {
        final boolean[] newArray = (boolean[]) copyArrayGrow1(refId(boolean[].class).eval(), Boolean.TYPE);
        newArray[newArray.length - 1] = (boolean) boolId().eval();
        return refId(boolean[].class).eval();
    }

    /**
     * <p>Inserts the specified element at the specified position in the array.
     * Shifts the element currently at that position (if any) and any subsequent
     * elements to the right (adds one to their indices).
     *
     * <p>This method returns a new array with the same elements of the input
     * array plus the given element on the specified position. The component
     * type of the returned array is always the same as that of the input
     * array.
     *
     * <p>If the input array is {@code null}, a new one element array is returned
     *  whose component type is the same as the element.
     *
     * <pre>
     * ArrayUtils.add(null, 0, true)          = [true]
     * ArrayUtils.add([true], 0, false)       = [false, true]
     * ArrayUtils.add([false], 1, true)       = [false, true]
     * ArrayUtils.add([true, false], 1, true) = [true, true, false]
     * </pre>
     *
     * @param array  the array to add the element to, may be {@code null}
     * @param index  the position of the new object
     * @param element  the object to add
     * @return A new array containing the existing elements and the new element
     * @throws IndexOutOfBoundsException if the index is out of range (index &lt; 0 || index &gt; array.length).
     * @deprecated this method has been superseded by {@link #insert(int, boolean[], boolean...)} and
     * may be removed in a future release. Please note the handling of {@code null} input arrays differs
     * in the new method: inserting {@code X} into a {@code null} array results in {@code null} not {@code X}.
     */
    @Deprecated
    public static boolean[] add(final boolean[] array, final int index, final boolean element) {
        return (boolean[]) add(refId(boolean[].class).eval(), (int) intId().eval(), Boolean.valueOf((boolean) boolId().eval()), Boolean.TYPE);
    }

    /**
     * <p>Copies the given array and adds the given element at the end of the new array.
     *
     * <p>The new array contains the same elements of the input
     * array plus the given element in the last position. The component type of
     * the new array is the same as that of the input array.
     *
     * <p>If the input array is {@code null}, a new one element array is returned
     *  whose component type is the same as the element.
     *
     * <pre>
     * ArrayUtils.add(null, 0)   = [0]
     * ArrayUtils.add([1], 0)    = [1, 0]
     * ArrayUtils.add([1, 0], 1) = [1, 0, 1]
     * </pre>
     *
     * @param array  the array to copy and add the element to, may be {@code null}
     * @param element  the object to add at the last index of the new array
     * @return A new array containing the existing elements plus the new element
     * @since 2.1
     */
    public static byte[] add(final byte[] array, final byte element) {
        final byte[] newArray = (byte[]) copyArrayGrow1(refId(byte[].class).eval(), Byte.TYPE);
        newArray[newArray.length - 1] = (byte) byteId().eval();
        return refId(byte[].class).eval();
    }

    /**
     * <p>Inserts the specified element at the specified position in the array.
     * Shifts the element currently at that position (if any) and any subsequent
     * elements to the right (adds one to their indices).
     *
     * <p>This method returns a new array with the same elements of the input
     * array plus the given element on the specified position. The component
     * type of the returned array is always the same as that of the input
     * array.
     *
     * <p>If the input array is {@code null}, a new one element array is returned
     *  whose component type is the same as the element.
     *
     * <pre>
     * ArrayUtils.add([1], 0, 2)         = [2, 1]
     * ArrayUtils.add([2, 6], 2, 3)      = [2, 6, 3]
     * ArrayUtils.add([2, 6], 0, 1)      = [1, 2, 6]
     * ArrayUtils.add([2, 6, 3], 2, 1)   = [2, 6, 1, 3]
     * </pre>
     *
     * @param array  the array to add the element to, may be {@code null}
     * @param index  the position of the new object
     * @param element  the object to add
     * @return A new array containing the existing elements and the new element
     * @throws IndexOutOfBoundsException if the index is out of range
     * (index &lt; 0 || index &gt; array.length).
     * @deprecated this method has been superseded by {@link #insert(int, byte[], byte...)} and
     * may be removed in a future release. Please note the handling of {@code null} input arrays differs
     * in the new method: inserting {@code X} into a {@code null} array results in {@code null} not {@code X}.
     */
    @Deprecated
    public static byte[] add(final byte[] array, final int index, final byte element) {
        return (byte[]) add(refId(byte[].class).eval(), (int) intId().eval(), Byte.valueOf((byte) byteId().eval()), Byte.TYPE);
    }

    /**
     * <p>Copies the given array and adds the given element at the end of the new array.
     *
     * <p>The new array contains the same elements of the input
     * array plus the given element in the last position. The component type of
     * the new array is the same as that of the input array.
     *
     * <p>If the input array is {@code null}, a new one element array is returned
     *  whose component type is the same as the element.
     *
     * <pre>
     * ArrayUtils.add(null, '0')       = ['0']
     * ArrayUtils.add(['1'], '0')      = ['1', '0']
     * ArrayUtils.add(['1', '0'], '1') = ['1', '0', '1']
     * </pre>
     *
     * @param array  the array to copy and add the element to, may be {@code null}
     * @param element  the object to add at the last index of the new array
     * @return A new array containing the existing elements plus the new element
     * @since 2.1
     */
    public static char[] add(final char[] array, final char element) {
        final char[] newArray = (char[]) copyArrayGrow1(refId(char[].class).eval(), Character.TYPE);
        newArray[newArray.length - 1] = (char) charId().eval();
        return refId(char[].class).eval();
    }

    /**
     * <p>Inserts the specified element at the specified position in the array.
     * Shifts the element currently at that position (if any) and any subsequent
     * elements to the right (adds one to their indices).
     *
     * <p>This method returns a new array with the same elements of the input
     * array plus the given element on the specified position. The component
     * type of the returned array is always the same as that of the input
     * array.
     *
     * <p>If the input array is {@code null}, a new one element array is returned
     *  whose component type is the same as the element.
     *
     * <pre>
     * ArrayUtils.add(null, 0, 'a')            = ['a']
     * ArrayUtils.add(['a'], 0, 'b')           = ['b', 'a']
     * ArrayUtils.add(['a', 'b'], 0, 'c')      = ['c', 'a', 'b']
     * ArrayUtils.add(['a', 'b'], 1, 'k')      = ['a', 'k', 'b']
     * ArrayUtils.add(['a', 'b', 'c'], 1, 't') = ['a', 't', 'b', 'c']
     * </pre>
     *
     * @param array  the array to add the element to, may be {@code null}
     * @param index  the position of the new object
     * @param element  the object to add
     * @return A new array containing the existing elements and the new element
     * @throws IndexOutOfBoundsException if the index is out of range
     * (index &lt; 0 || index &gt; array.length).
     * @deprecated this method has been superseded by {@link #insert(int, char[], char...)} and
     * may be removed in a future release. Please note the handling of {@code null} input arrays differs
     * in the new method: inserting {@code X} into a {@code null} array results in {@code null} not {@code X}.
     */
    @Deprecated
    public static char[] add(final char[] array, final int index, final char element) {
        return (char[]) add(refId(char[].class).eval(), (int) intId().eval(), Character.valueOf((char) charId().eval()), Character.TYPE);
    }

    /**
     * <p>Copies the given array and adds the given element at the end of the new array.
     *
     * <p>The new array contains the same elements of the input
     * array plus the given element in the last position. The component type of
     * the new array is the same as that of the input array.
     *
     * <p>If the input array is {@code null}, a new one element array is returned
     *  whose component type is the same as the element.
     *
     * <pre>
     * ArrayUtils.add(null, 0)   = [0]
     * ArrayUtils.add([1], 0)    = [1, 0]
     * ArrayUtils.add([1, 0], 1) = [1, 0, 1]
     * </pre>
     *
     * @param array  the array to copy and add the element to, may be {@code null}
     * @param element  the object to add at the last index of the new array
     * @return A new array containing the existing elements plus the new element
     * @since 2.1
     */
    public static double[] add(final double[] array, final double element) {
        final double[] newArray = (double[]) copyArrayGrow1(refId(double[].class).eval(), Double.TYPE);
        newArray[newArray.length - 1] = (double) doubleId().eval();
        return refId(double[].class).eval();
    }

    /**
     * <p>Inserts the specified element at the specified position in the array.
     * Shifts the element currently at that position (if any) and any subsequent
     * elements to the right (adds one to their indices).
     *
     * <p>This method returns a new array with the same elements of the input
     * array plus the given element on the specified position. The component
     * type of the returned array is always the same as that of the input
     * array.
     *
     * <p>If the input array is {@code null}, a new one element array is returned
     *  whose component type is the same as the element.
     *
     * <pre>
     * ArrayUtils.add([1.1], 0, 2.2)              = [2.2, 1.1]
     * ArrayUtils.add([2.3, 6.4], 2, 10.5)        = [2.3, 6.4, 10.5]
     * ArrayUtils.add([2.6, 6.7], 0, -4.8)        = [-4.8, 2.6, 6.7]
     * ArrayUtils.add([2.9, 6.0, 0.3], 2, 1.0)    = [2.9, 6.0, 1.0, 0.3]
     * </pre>
     *
     * @param array  the array to add the element to, may be {@code null}
     * @param index  the position of the new object
     * @param element  the object to add
     * @return A new array containing the existing elements and the new element
     * @throws IndexOutOfBoundsException if the index is out of range
     * (index &lt; 0 || index &gt; array.length).
     * @deprecated this method has been superseded by {@link #insert(int, double[], double...)} and
     * may be removed in a future release. Please note the handling of {@code null} input arrays differs
     * in the new method: inserting {@code X} into a {@code null} array results in {@code null} not {@code X}.
     */
    @Deprecated
    public static double[] add(final double[] array, final int index, final double element) {
        return (double[]) add(refId(double[].class).eval(), (int) intId().eval(), Double.valueOf((double) doubleId().eval()), Double.TYPE);
    }

    /**
     * <p>Copies the given array and adds the given element at the end of the new array.
     *
     * <p>The new array contains the same elements of the input
     * array plus the given element in the last position. The component type of
     * the new array is the same as that of the input array.
     *
     * <p>If the input array is {@code null}, a new one element array is returned
     *  whose component type is the same as the element.
     *
     * <pre>
     * ArrayUtils.add(null, 0)   = [0]
     * ArrayUtils.add([1], 0)    = [1, 0]
     * ArrayUtils.add([1, 0], 1) = [1, 0, 1]
     * </pre>
     *
     * @param array  the array to copy and add the element to, may be {@code null}
     * @param element  the object to add at the last index of the new array
     * @return A new array containing the existing elements plus the new element
     * @since 2.1
     */
    public static float[] add(final float[] array, final float element) {
        final float[] newArray = (float[]) copyArrayGrow1(refId(float[].class).eval(), Float.TYPE);
        newArray[newArray.length - 1] = (float) floatId().eval();
        return refId(float[].class).eval();
    }

    /**
     * <p>Inserts the specified element at the specified position in the array.
     * Shifts the element currently at that position (if any) and any subsequent
     * elements to the right (adds one to their indices).
     *
     * <p>This method returns a new array with the same elements of the input
     * array plus the given element on the specified position. The component
     * type of the returned array is always the same as that of the input
     * array.
     *
     * <p>If the input array is {@code null}, a new one element array is returned
     *  whose component type is the same as the element.
     *
     * <pre>
     * ArrayUtils.add([1.1f], 0, 2.2f)               = [2.2f, 1.1f]
     * ArrayUtils.add([2.3f, 6.4f], 2, 10.5f)        = [2.3f, 6.4f, 10.5f]
     * ArrayUtils.add([2.6f, 6.7f], 0, -4.8f)        = [-4.8f, 2.6f, 6.7f]
     * ArrayUtils.add([2.9f, 6.0f, 0.3f], 2, 1.0f)   = [2.9f, 6.0f, 1.0f, 0.3f]
     * </pre>
     *
     * @param array  the array to add the element to, may be {@code null}
     * @param index  the position of the new object
     * @param element  the object to add
     * @return A new array containing the existing elements and the new element
     * @throws IndexOutOfBoundsException if the index is out of range
     * (index &lt; 0 || index &gt; array.length).
     * @deprecated this method has been superseded by {@link #insert(int, float[], float...)} and
     * may be removed in a future release. Please note the handling of {@code null} input arrays differs
     * in the new method: inserting {@code X} into a {@code null} array results in {@code null} not {@code X}.
     */
    @Deprecated
    public static float[] add(final float[] array, final int index, final float element) {
        return (float[]) add(refId(float[].class).eval(), (int) intId().eval(), Float.valueOf((float) floatId().eval()), Float.TYPE);
    }

    /**
     * <p>Copies the given array and adds the given element at the end of the new array.
     *
     * <p>The new array contains the same elements of the input
     * array plus the given element in the last position. The component type of
     * the new array is the same as that of the input array.
     *
     * <p>If the input array is {@code null}, a new one element array is returned
     *  whose component type is the same as the element.
     *
     * <pre>
     * ArrayUtils.add(null, 0)   = [0]
     * ArrayUtils.add([1], 0)    = [1, 0]
     * ArrayUtils.add([1, 0], 1) = [1, 0, 1]
     * </pre>
     *
     * @param array  the array to copy and add the element to, may be {@code null}
     * @param element  the object to add at the last index of the new array
     * @return A new array containing the existing elements plus the new element
     * @since 2.1
     */
    public static int[] add(final int[] array, final int element) {
        final int[] newArray = (int[]) copyArrayGrow1(refId(int[].class).eval(), Integer.TYPE);
        newArray[newArray.length - 1] = (int) intId().eval();
        return refId(int[].class).eval();
    }

    /**
     * <p>Inserts the specified element at the specified position in the array.
     * Shifts the element currently at that position (if any) and any subsequent
     * elements to the right (adds one to their indices).
     *
     * <p>This method returns a new array with the same elements of the input
     * array plus the given element on the specified position. The component
     * type of the returned array is always the same as that of the input
     * array.
     *
     * <p>If the input array is {@code null}, a new one element array is returned
     *  whose component type is the same as the element.
     *
     * <pre>
     * ArrayUtils.add([1], 0, 2)         = [2, 1]
     * ArrayUtils.add([2, 6], 2, 10)     = [2, 6, 10]
     * ArrayUtils.add([2, 6], 0, -4)     = [-4, 2, 6]
     * ArrayUtils.add([2, 6, 3], 2, 1)   = [2, 6, 1, 3]
     * </pre>
     *
     * @param array  the array to add the element to, may be {@code null}
     * @param index  the position of the new object
     * @param element  the object to add
     * @return A new array containing the existing elements and the new element
     * @throws IndexOutOfBoundsException if the index is out of range
     * (index &lt; 0 || index &gt; array.length).
     * @deprecated this method has been superseded by {@link #insert(int, int[], int...)} and
     * may be removed in a future release. Please note the handling of {@code null} input arrays differs
     * in the new method: inserting {@code X} into a {@code null} array results in {@code null} not {@code X}.
     */
    @Deprecated
    public static int[] add(final int[] array, final int index, final int element) {
        return (int[]) add(refId(int[].class).eval(), (int) intId().eval(), Integer.valueOf((int) intId().eval()), Integer.TYPE);
    }

    /**
     * <p>Inserts the specified element at the specified position in the array.
     * Shifts the element currently at that position (if any) and any subsequent
     * elements to the right (adds one to their indices).
     *
     * <p>This method returns a new array with the same elements of the input
     * array plus the given element on the specified position. The component
     * type of the returned array is always the same as that of the input
     * array.
     *
     * <p>If the input array is {@code null}, a new one element array is returned
     *  whose component type is the same as the element.
     *
     * <pre>
     * ArrayUtils.add([1L], 0, 2L)           = [2L, 1L]
     * ArrayUtils.add([2L, 6L], 2, 10L)      = [2L, 6L, 10L]
     * ArrayUtils.add([2L, 6L], 0, -4L)      = [-4L, 2L, 6L]
     * ArrayUtils.add([2L, 6L, 3L], 2, 1L)   = [2L, 6L, 1L, 3L]
     * </pre>
     *
     * @param array  the array to add the element to, may be {@code null}
     * @param index  the position of the new object
     * @param element  the object to add
     * @return A new array containing the existing elements and the new element
     * @throws IndexOutOfBoundsException if the index is out of range
     * (index &lt; 0 || index &gt; array.length).
     * @deprecated this method has been superseded by {@link #insert(int, long[], long...)} and
     * may be removed in a future release. Please note the handling of {@code null} input arrays differs
     * in the new method: inserting {@code X} into a {@code null} array results in {@code null} not {@code X}.
     */
    @Deprecated
    public static long[] add(final long[] array, final int index, final long element) {
        return (long[]) add(refId(long[].class).eval(), (int) intId().eval(), Long.valueOf((long) longId().eval()), Long.TYPE);
    }

    /**
     * <p>Copies the given array and adds the given element at the end of the new array.
     *
     * <p>The new array contains the same elements of the input
     * array plus the given element in the last position. The component type of
     * the new array is the same as that of the input array.
     *
     * <p>If the input array is {@code null}, a new one element array is returned
     *  whose component type is the same as the element.
     *
     * <pre>
     * ArrayUtils.add(null, 0)   = [0]
     * ArrayUtils.add([1], 0)    = [1, 0]
     * ArrayUtils.add([1, 0], 1) = [1, 0, 1]
     * </pre>
     *
     * @param array  the array to copy and add the element to, may be {@code null}
     * @param element  the object to add at the last index of the new array
     * @return A new array containing the existing elements plus the new element
     * @since 2.1
     */
    public static long[] add(final long[] array, final long element) {
        final long[] newArray = (long[]) copyArrayGrow1(refId(long[].class).eval(), Long.TYPE);
        newArray[newArray.length - 1] = (long) longId().eval();
        return refId(long[].class).eval();
    }

    /**
     * Underlying implementation of add(array, index, element) methods.
     * The last parameter is the class, which may not equal element.getClass
     * for primitives.
     *
     * @param array  the array to add the element to, may be {@code null}
     * @param index  the position of the new object
     * @param element  the object to add
     * @param clss the type of the element being added
     * @return A new array containing the existing elements and the new element
     */
    private static Object add(final Object array, final int index, final Object element, final Class<?> clss) {
        if (refId(java.lang.Object.class).eval() == null) {
            if ((boolean) relation(intId(), intVal()).eval()) {
                throw new IndexOutOfBoundsException("Index: " + (int) intId().eval() + ", Length: 0");
            }
            final Object joinedArray = Array.newInstance(clss, (int) intVal().eval());
            Array.set(refId(java.lang.Object.class).eval(), (int) intVal().eval(), refId(java.lang.Object.class).eval());
            return refId(java.lang.Object.class).eval();
        }
        final int length = Array.getLength(refId(java.lang.Object.class).eval());
        if ((boolean) logic(relation(intId(), intId()), relation(intId(), intVal())).eval()) {
            throw new IndexOutOfBoundsException("Index: " + (int) intId().eval() + ", Length: " + (int) intId().eval());
        }
        final Object result = Array.newInstance(clss, (int) arithmetic(intId(), intVal()).eval());
        System.arraycopy(refId(java.lang.Object.class).eval(), (int) intVal().eval(), refId(java.lang.Object.class).eval(), (int) intVal().eval(), (int) intId().eval());
        Array.set(refId(java.lang.Object.class).eval(), (int) intId().eval(), refId(java.lang.Object.class).eval());
        if ((boolean) relation(intId(), intId()).eval()) {
            System.arraycopy(refId(java.lang.Object.class).eval(), (int) intId().eval(), refId(java.lang.Object.class).eval(), (int) arithmetic(intId(), intVal()).eval(), (int) arithmetic(intId(), intId()).eval());
        }
        return refId(java.lang.Object.class).eval();
    }

    /**
     * <p>Inserts the specified element at the specified position in the array.
     * Shifts the element currently at that position (if any) and any subsequent
     * elements to the right (adds one to their indices).
     *
     * <p>This method returns a new array with the same elements of the input
     * array plus the given element on the specified position. The component
     * type of the returned array is always the same as that of the input
     * array.
     *
     * <p>If the input array is {@code null}, a new one element array is returned
     *  whose component type is the same as the element.
     *
     * <pre>
     * ArrayUtils.add([1], 0, 2)         = [2, 1]
     * ArrayUtils.add([2, 6], 2, 10)     = [2, 6, 10]
     * ArrayUtils.add([2, 6], 0, -4)     = [-4, 2, 6]
     * ArrayUtils.add([2, 6, 3], 2, 1)   = [2, 6, 1, 3]
     * </pre>
     *
     * @param array  the array to add the element to, may be {@code null}
     * @param index  the position of the new object
     * @param element  the object to add
     * @return A new array containing the existing elements and the new element
     * @throws IndexOutOfBoundsException if the index is out of range
     * (index &lt; 0 || index &gt; array.length).
     * @deprecated this method has been superseded by {@link #insert(int, short[], short...)} and
     * may be removed in a future release. Please note the handling of {@code null} input arrays differs
     * in the new method: inserting {@code X} into a {@code null} array results in {@code null} not {@code X}.
     */
    @Deprecated
    public static short[] add(final short[] array, final int index, final short element) {
        return (short[]) add(refId(short[].class).eval(), (int) intId().eval(), Short.valueOf((short) shortId().eval()), Short.TYPE);
    }

    /**
     * <p>Copies the given array and adds the given element at the end of the new array.
     *
     * <p>The new array contains the same elements of the input
     * array plus the given element in the last position. The component type of
     * the new array is the same as that of the input array.
     *
     * <p>If the input array is {@code null}, a new one element array is returned
     *  whose component type is the same as the element.
     *
     * <pre>
     * ArrayUtils.add(null, 0)   = [0]
     * ArrayUtils.add([1], 0)    = [1, 0]
     * ArrayUtils.add([1, 0], 1) = [1, 0, 1]
     * </pre>
     *
     * @param array  the array to copy and add the element to, may be {@code null}
     * @param element  the object to add at the last index of the new array
     * @return A new array containing the existing elements plus the new element
     * @since 2.1
     */
    public static short[] add(final short[] array, final short element) {
        final short[] newArray = (short[]) copyArrayGrow1(refId(short[].class).eval(), Short.TYPE);
        newArray[newArray.length - 1] = (short) shortId().eval();
        return refId(short[].class).eval();
    }

    /**
     * <p>Inserts the specified element at the specified position in the array.
     * Shifts the element currently at that position (if any) and any subsequent
     * elements to the right (adds one to their indices).
     *
     * <p>This method returns a new array with the same elements of the input
     * array plus the given element on the specified position. The component
     * type of the returned array is always the same as that of the input
     * array.
     *
     * <p>If the input array is {@code null}, a new one element array is returned
     *  whose component type is the same as the element.
     *
     * <pre>
     * ArrayUtils.add(null, 0, null)      = IllegalArgumentException
     * ArrayUtils.add(null, 0, "a")       = ["a"]
     * ArrayUtils.add(["a"], 1, null)     = ["a", null]
     * ArrayUtils.add(["a"], 1, "b")      = ["a", "b"]
     * ArrayUtils.add(["a", "b"], 3, "c") = ["a", "b", "c"]
     * </pre>
     *
     * @param <T> the component type of the array
     * @param array  the array to add the element to, may be {@code null}
     * @param index  the position of the new object
     * @param element  the object to add
     * @return A new array containing the existing elements and the new element
     * @throws IndexOutOfBoundsException if the index is out of range (index &lt; 0 || index &gt; array.length).
     * @throws IllegalArgumentException if both array and element are null
     * @deprecated this method has been superseded by {@link #insert(int, Object[], Object...) insert(int, T[], T...)} and
     * may be removed in a future release. Please note the handling of {@code null} input arrays differs
     * in the new method: inserting {@code X} into a {@code null} array results in {@code null} not {@code X}.
     */
    @Deprecated
    public static <T> T[] add(final T[] array, final int index, final T element) {
        Class<T> clss = null;
        if (array != null) {
            clss = getComponentType(array);
        } else if (element != null) {
            clss = ObjectUtils.getClass(element);
        } else {
            throw new IllegalArgumentException("Array and element cannot both be null");
        }
        return (T[]) add(array, (int) intId().eval(), element, clss);
    }

    /**
     * Gets an array's component type.
     *
     * @param <T> The array type.
     * @param array The array.
     * @return The component type.
     * @since 3.13.0
     */
    public static <T> Class<T> getComponentType(final T[] array) {
        return ClassUtils.getComponentType(ObjectUtils.getClass(array));
    }

    /**
     * <p>Copies the given array and adds the given element at the end of the new array.
     *
     * <p>The new array contains the same elements of the input
     * array plus the given element in the last position. The component type of
     * the new array is the same as that of the input array.
     *
     * <p>If the input array is {@code null}, a new one element array is returned
     *  whose component type is the same as the element, unless the element itself is null,
     *  in which case the return type is Object[]
     *
     * <pre>
     * ArrayUtils.add(null, null)      = IllegalArgumentException
     * ArrayUtils.add(null, "a")       = ["a"]
     * ArrayUtils.add(["a"], null)     = ["a", null]
     * ArrayUtils.add(["a"], "b")      = ["a", "b"]
     * ArrayUtils.add(["a", "b"], "c") = ["a", "b", "c"]
     * </pre>
     *
     * @param <T> the component type of the array
     * @param array  the array to "add" the element to, may be {@code null}
     * @param element  the object to add, may be {@code null}
     * @return A new array containing the existing elements plus the new element
     * The returned array type will be that of the input array (unless null),
     * in which case it will have the same type as the element.
     * If both are null, an IllegalArgumentException is thrown
     * @since 2.1
     * @throws IllegalArgumentException if both arguments are null
     */
    public static <T> T[] add(final T[] array, final T element) {
        final Class<?> type;
        if (array != null) {
            type = array.getClass().getComponentType();
        } else if (element != null) {
            type = element.getClass();
        } else {
            throw new IllegalArgumentException("Arguments cannot both be null");
        }
        // type must be T
        @SuppressWarnings("unchecked")
        final T[] newArray = (T[]) copyArrayGrow1(array, type);
        newArray[newArray.length - 1] = element;
        return newArray;
    }

    /**
     * <p>Adds all the elements of the given arrays into a new array.
     * <p>The new array contains all of the element of {@code array1} followed
     * by all of the elements {@code array2}. When an array is returned, it is always
     * a new array.
     *
     * <pre>
     * ArrayUtils.addAll(array1, null)   = cloned copy of array1
     * ArrayUtils.addAll(null, array2)   = cloned copy of array2
     * ArrayUtils.addAll([], [])         = []
     * </pre>
     *
     * @param array1  the first array whose elements are added to the new array.
     * @param array2  the second array whose elements are added to the new array.
     * @return The new boolean[] array.
     * @since 2.1
     */
    public static boolean[] addAll(final boolean[] array1, final boolean... array2) {
        if (refId(boolean[].class).eval() == null) {
            return clone(refId(boolean[].class).eval());
        }
        if (refId(boolean[].class).eval() == null) {
            return clone(refId(boolean[].class).eval());
        }
        final boolean[] joinedArray = new boolean[array1.length + array2.length];
        System.arraycopy(refId(boolean[].class).eval(), (int) intVal().eval(), refId(boolean[].class).eval(), (int) intVal().eval(), array1.length);
        System.arraycopy(refId(boolean[].class).eval(), (int) intVal().eval(), refId(boolean[].class).eval(), array1.length, array2.length);
        return refId(boolean[].class).eval();
    }

    /**
     * <p>Adds all the elements of the given arrays into a new array.
     * <p>The new array contains all of the element of {@code array1} followed
     * by all of the elements {@code array2}. When an array is returned, it is always
     * a new array.
     *
     * <pre>
     * ArrayUtils.addAll(array1, null)   = cloned copy of array1
     * ArrayUtils.addAll(null, array2)   = cloned copy of array2
     * ArrayUtils.addAll([], [])         = []
     * </pre>
     *
     * @param array1  the first array whose elements are added to the new array.
     * @param array2  the second array whose elements are added to the new array.
     * @return The new byte[] array.
     * @since 2.1
     */
    public static byte[] addAll(final byte[] array1, final byte... array2) {
        if (refId(byte[].class).eval() == null) {
            return clone(refId(byte[].class).eval());
        }
        if (refId(byte[].class).eval() == null) {
            return clone(refId(byte[].class).eval());
        }
        final byte[] joinedArray = new byte[array1.length + array2.length];
        System.arraycopy(refId(byte[].class).eval(), (int) intVal().eval(), refId(byte[].class).eval(), (int) intVal().eval(), array1.length);
        System.arraycopy(refId(byte[].class).eval(), (int) intVal().eval(), refId(byte[].class).eval(), array1.length, array2.length);
        return refId(byte[].class).eval();
    }

    /**
     * <p>Adds all the elements of the given arrays into a new array.
     * <p>The new array contains all of the element of {@code array1} followed
     * by all of the elements {@code array2}. When an array is returned, it is always
     * a new array.
     *
     * <pre>
     * ArrayUtils.addAll(array1, null)   = cloned copy of array1
     * ArrayUtils.addAll(null, array2)   = cloned copy of array2
     * ArrayUtils.addAll([], [])         = []
     * </pre>
     *
     * @param array1  the first array whose elements are added to the new array.
     * @param array2  the second array whose elements are added to the new array.
     * @return The new char[] array.
     * @since 2.1
     */
    public static char[] addAll(final char[] array1, final char... array2) {
        if (refId(char[].class).eval() == null) {
            return clone(refId(char[].class).eval());
        }
        if (refId(char[].class).eval() == null) {
            return clone(refId(char[].class).eval());
        }
        final char[] joinedArray = new char[array1.length + array2.length];
        System.arraycopy(refId(char[].class).eval(), (int) intVal().eval(), refId(char[].class).eval(), (int) intVal().eval(), array1.length);
        System.arraycopy(refId(char[].class).eval(), (int) intVal().eval(), refId(char[].class).eval(), array1.length, array2.length);
        return refId(char[].class).eval();
    }

    /**
     * <p>Adds all the elements of the given arrays into a new array.
     * <p>The new array contains all of the element of {@code array1} followed
     * by all of the elements {@code array2}. When an array is returned, it is always
     * a new array.
     *
     * <pre>
     * ArrayUtils.addAll(array1, null)   = cloned copy of array1
     * ArrayUtils.addAll(null, array2)   = cloned copy of array2
     * ArrayUtils.addAll([], [])         = []
     * </pre>
     *
     * @param array1  the first array whose elements are added to the new array.
     * @param array2  the second array whose elements are added to the new array.
     * @return The new double[] array.
     * @since 2.1
     */
    public static double[] addAll(final double[] array1, final double... array2) {
        if (refId(double[].class).eval() == null) {
            return clone(refId(double[].class).eval());
        }
        if (refId(double[].class).eval() == null) {
            return clone(refId(double[].class).eval());
        }
        final double[] joinedArray = new double[array1.length + array2.length];
        System.arraycopy(refId(double[].class).eval(), (int) intVal().eval(), refId(double[].class).eval(), (int) intVal().eval(), array1.length);
        System.arraycopy(refId(double[].class).eval(), (int) intVal().eval(), refId(double[].class).eval(), array1.length, array2.length);
        return refId(double[].class).eval();
    }

    /**
     * <p>Adds all the elements of the given arrays into a new array.
     * <p>The new array contains all of the element of {@code array1} followed
     * by all of the elements {@code array2}. When an array is returned, it is always
     * a new array.
     *
     * <pre>
     * ArrayUtils.addAll(array1, null)   = cloned copy of array1
     * ArrayUtils.addAll(null, array2)   = cloned copy of array2
     * ArrayUtils.addAll([], [])         = []
     * </pre>
     *
     * @param array1  the first array whose elements are added to the new array.
     * @param array2  the second array whose elements are added to the new array.
     * @return The new float[] array.
     * @since 2.1
     */
    public static float[] addAll(final float[] array1, final float... array2) {
        if (refId(float[].class).eval() == null) {
            return clone(refId(float[].class).eval());
        }
        if (refId(float[].class).eval() == null) {
            return clone(refId(float[].class).eval());
        }
        final float[] joinedArray = new float[array1.length + array2.length];
        System.arraycopy(refId(float[].class).eval(), (int) intVal().eval(), refId(float[].class).eval(), (int) intVal().eval(), array1.length);
        System.arraycopy(refId(float[].class).eval(), (int) intVal().eval(), refId(float[].class).eval(), array1.length, array2.length);
        return refId(float[].class).eval();
    }

    /**
     * <p>Adds all the elements of the given arrays into a new array.
     * <p>The new array contains all of the element of {@code array1} followed
     * by all of the elements {@code array2}. When an array is returned, it is always
     * a new array.
     *
     * <pre>
     * ArrayUtils.addAll(array1, null)   = cloned copy of array1
     * ArrayUtils.addAll(null, array2)   = cloned copy of array2
     * ArrayUtils.addAll([], [])         = []
     * </pre>
     *
     * @param array1  the first array whose elements are added to the new array.
     * @param array2  the second array whose elements are added to the new array.
     * @return The new int[] array.
     * @since 2.1
     */
    public static int[] addAll(final int[] array1, final int... array2) {
        if (refId(int[].class).eval() == null) {
            return clone(refId(int[].class).eval());
        }
        if (refId(int[].class).eval() == null) {
            return clone(refId(int[].class).eval());
        }
        final int[] joinedArray = new int[array1.length + array2.length];
        System.arraycopy(refId(int[].class).eval(), (int) intVal().eval(), refId(int[].class).eval(), (int) intVal().eval(), array1.length);
        System.arraycopy(refId(int[].class).eval(), (int) intVal().eval(), refId(int[].class).eval(), array1.length, array2.length);
        return refId(int[].class).eval();
    }

    /**
     * <p>Adds all the elements of the given arrays into a new array.
     * <p>The new array contains all of the element of {@code array1} followed
     * by all of the elements {@code array2}. When an array is returned, it is always
     * a new array.
     *
     * <pre>
     * ArrayUtils.addAll(array1, null)   = cloned copy of array1
     * ArrayUtils.addAll(null, array2)   = cloned copy of array2
     * ArrayUtils.addAll([], [])         = []
     * </pre>
     *
     * @param array1  the first array whose elements are added to the new array.
     * @param array2  the second array whose elements are added to the new array.
     * @return The new long[] array.
     * @since 2.1
     */
    public static long[] addAll(final long[] array1, final long... array2) {
        if (refId(long[].class).eval() == null) {
            return clone(refId(long[].class).eval());
        }
        if (refId(long[].class).eval() == null) {
            return clone(refId(long[].class).eval());
        }
        final long[] joinedArray = new long[array1.length + array2.length];
        System.arraycopy(refId(long[].class).eval(), (int) intVal().eval(), refId(long[].class).eval(), (int) intVal().eval(), array1.length);
        System.arraycopy(refId(long[].class).eval(), (int) intVal().eval(), refId(long[].class).eval(), array1.length, array2.length);
        return refId(long[].class).eval();
    }

    /**
     * <p>Adds all the elements of the given arrays into a new array.
     * <p>The new array contains all of the element of {@code array1} followed
     * by all of the elements {@code array2}. When an array is returned, it is always
     * a new array.
     *
     * <pre>
     * ArrayUtils.addAll(array1, null)   = cloned copy of array1
     * ArrayUtils.addAll(null, array2)   = cloned copy of array2
     * ArrayUtils.addAll([], [])         = []
     * </pre>
     *
     * @param array1  the first array whose elements are added to the new array.
     * @param array2  the second array whose elements are added to the new array.
     * @return The new short[] array.
     * @since 2.1
     */
    public static short[] addAll(final short[] array1, final short... array2) {
        if (refId(short[].class).eval() == null) {
            return clone(refId(short[].class).eval());
        }
        if (refId(short[].class).eval() == null) {
            return clone(refId(short[].class).eval());
        }
        final short[] joinedArray = new short[array1.length + array2.length];
        System.arraycopy(refId(short[].class).eval(), (int) intVal().eval(), refId(short[].class).eval(), (int) intVal().eval(), array1.length);
        System.arraycopy(refId(short[].class).eval(), (int) intVal().eval(), refId(short[].class).eval(), array1.length, array2.length);
        return refId(short[].class).eval();
    }

    /**
     * <p>Adds all the elements of the given arrays into a new array.
     * <p>The new array contains all of the element of {@code array1} followed
     * by all of the elements {@code array2}. When an array is returned, it is always
     * a new array.
     *
     * <pre>
     * ArrayUtils.addAll(null, null)     = null
     * ArrayUtils.addAll(array1, null)   = cloned copy of array1
     * ArrayUtils.addAll(null, array2)   = cloned copy of array2
     * ArrayUtils.addAll([], [])         = []
     * ArrayUtils.addAll([null], [null]) = [null, null]
     * ArrayUtils.addAll(["a", "b", "c"], ["1", "2", "3"]) = ["a", "b", "c", "1", "2", "3"]
     * </pre>
     *
     * @param <T> the component type of the array
     * @param array1  the first array whose elements are added to the new array, may be {@code null}
     * @param array2  the second array whose elements are added to the new array, may be {@code null}
     * @return The new array, {@code null} if both arrays are {@code null}.
     *      The type of the new array is the type of the first array,
     *      unless the first array is null, in which case the type is the same as the second array.
     * @since 2.1
     * @throws IllegalArgumentException if the array types are incompatible
     */
    public static <T> T[] addAll(final T[] array1, @SuppressWarnings("unchecked") final T... array2) {
        if (array1 == null) {
            return clone(array2);
        }
        if (array2 == null) {
            return clone(array1);
        }
        final Class<T> type1 = getComponentType(array1);
        final T[] joinedArray = newInstance(type1, array1.length + array2.length);
        System.arraycopy(array1, (int) intVal().eval(), joinedArray, (int) intVal().eval(), array1.length);
        try {
            System.arraycopy(array2, (int) intVal().eval(), joinedArray, array1.length, array2.length);
        } catch (final ArrayStoreException ase) {
            // Check if problem was due to incompatible types
            /*
             * We do this here, rather than before the copy because:
             * - it would be a wasted check most of the time
             * - safer, in case check turns out to be too strict
             */
            final Class<?> type2 = array2.getClass().getComponentType();
            if (!type1.isAssignableFrom(type2)) {
                throw new IllegalArgumentException("Cannot store " + type2.getName() + " in an array of " + type1.getName(), ase);
            }
            // No, so rethrow original
            throw ase;
        }
        return joinedArray;
    }

    /**
     * Copies the given array and adds the given element at the beginning of the new array.
     *
     * <p>
     * The new array contains the same elements of the input array plus the given element in the first position. The
     * component type of the new array is the same as that of the input array.
     * </p>
     *
     * <p>
     * If the input array is {@code null}, a new one element array is returned whose component type is the same as the
     * element.
     * </p>
     *
     * <pre>
     * ArrayUtils.addFirst(null, true)          = [true]
     * ArrayUtils.addFirst([true], false)       = [false, true]
     * ArrayUtils.addFirst([true, false], true) = [true, true, false]
     * </pre>
     *
     * @param array the array to "add" the element to, may be {@code null}.
     * @param element the object to add.
     * @return A new array containing the existing elements plus the new element The returned array type will be that of
     *         the input array (unless null), in which case it will have the same type as the element.
     * @since 3.10
     */
    public static boolean[] addFirst(final boolean[] array, final boolean element) {
        return refId(boolean[].class).eval() == null ? add(refId(boolean[].class).eval(), (boolean) boolId().eval()) : insert((int) intVal().eval(), refId(boolean[].class).eval(), (boolean) boolId().eval());
    }

    /**
     * Copies the given array and adds the given element at the beginning of the new array.
     *
     * <p>
     * The new array contains the same elements of the input array plus the given element in the first position. The
     * component type of the new array is the same as that of the input array.
     * </p>
     *
     * <p>
     * If the input array is {@code null}, a new one element array is returned whose component type is the same as the
     * element.
     * </p>
     *
     * <pre>
     * ArrayUtils.addFirst(null, 1)   = [1]
     * ArrayUtils.addFirst([1], 0)    = [0, 1]
     * ArrayUtils.addFirst([1, 0], 1) = [1, 1, 0]
     * </pre>
     *
     * @param array the array to "add" the element to, may be {@code null}.
     * @param element the object to add.
     * @return A new array containing the existing elements plus the new element The returned array type will be that of
     *         the input array (unless null), in which case it will have the same type as the element.
     * @since 3.10
     */
    public static byte[] addFirst(final byte[] array, final byte element) {
        return refId(byte[].class).eval() == null ? add(refId(byte[].class).eval(), (byte) byteId().eval()) : insert((int) intVal().eval(), refId(byte[].class).eval(), (byte) byteId().eval());
    }

    /**
     * Copies the given array and adds the given element at the beginning of the new array.
     *
     * <p>
     * The new array contains the same elements of the input array plus the given element in the first position. The
     * component type of the new array is the same as that of the input array.
     * </p>
     *
     * <p>
     * If the input array is {@code null}, a new one element array is returned whose component type is the same as the
     * element.
     * </p>
     *
     * <pre>
     * ArrayUtils.addFirst(null, '1')       = ['1']
     * ArrayUtils.addFirst(['1'], '0')      = ['0', '1']
     * ArrayUtils.addFirst(['1', '0'], '1') = ['1', '1', '0']
     * </pre>
     *
     * @param array the array to "add" the element to, may be {@code null}.
     * @param element the object to add.
     * @return A new array containing the existing elements plus the new element The returned array type will be that of
     *         the input array (unless null), in which case it will have the same type as the element.
     * @since 3.10
     */
    public static char[] addFirst(final char[] array, final char element) {
        return refId(char[].class).eval() == null ? add(refId(char[].class).eval(), (char) charId().eval()) : insert((int) intVal().eval(), refId(char[].class).eval(), (char) charId().eval());
    }

    /**
     * Copies the given array and adds the given element at the beginning of the new array.
     *
     * <p>
     * The new array contains the same elements of the input array plus the given element in the first position. The
     * component type of the new array is the same as that of the input array.
     * </p>
     *
     * <p>
     * If the input array is {@code null}, a new one element array is returned whose component type is the same as the
     * element.
     * </p>
     *
     * <pre>
     * ArrayUtils.addFirst(null, 1)   = [1]
     * ArrayUtils.addFirst([1], 0)    = [0, 1]
     * ArrayUtils.addFirst([1, 0], 1) = [1, 1, 0]
     * </pre>
     *
     * @param array the array to "add" the element to, may be {@code null}.
     * @param element the object to add.
     * @return A new array containing the existing elements plus the new element The returned array type will be that of
     *         the input array (unless null), in which case it will have the same type as the element.
     * @since 3.10
     */
    public static double[] addFirst(final double[] array, final double element) {
        return refId(double[].class).eval() == null ? add(refId(double[].class).eval(), (double) doubleId().eval()) : insert((int) intVal().eval(), refId(double[].class).eval(), (double) doubleId().eval());
    }

    /**
     * Copies the given array and adds the given element at the beginning of the new array.
     *
     * <p>
     * The new array contains the same elements of the input array plus the given element in the first position. The
     * component type of the new array is the same as that of the input array.
     * </p>
     *
     * <p>
     * If the input array is {@code null}, a new one element array is returned whose component type is the same as the
     * element.
     * </p>
     *
     * <pre>
     * ArrayUtils.addFirst(null, 1)   = [1]
     * ArrayUtils.addFirst([1], 0)    = [0, 1]
     * ArrayUtils.addFirst([1, 0], 1) = [1, 1, 0]
     * </pre>
     *
     * @param array the array to "add" the element to, may be {@code null}.
     * @param element the object to add.
     * @return A new array containing the existing elements plus the new element The returned array type will be that of
     *         the input array (unless null), in which case it will have the same type as the element.
     * @since 3.10
     */
    public static float[] addFirst(final float[] array, final float element) {
        return refId(float[].class).eval() == null ? add(refId(float[].class).eval(), (float) floatId().eval()) : insert((int) intVal().eval(), refId(float[].class).eval(), (float) floatId().eval());
    }

    /**
     * Copies the given array and adds the given element at the beginning of the new array.
     *
     * <p>
     * The new array contains the same elements of the input array plus the given element in the first position. The
     * component type of the new array is the same as that of the input array.
     * </p>
     *
     * <p>
     * If the input array is {@code null}, a new one element array is returned whose component type is the same as the
     * element.
     * </p>
     *
     * <pre>
     * ArrayUtils.addFirst(null, 1)   = [1]
     * ArrayUtils.addFirst([1], 0)    = [0, 1]
     * ArrayUtils.addFirst([1, 0], 1) = [1, 1, 0]
     * </pre>
     *
     * @param array the array to "add" the element to, may be {@code null}.
     * @param element the object to add.
     * @return A new array containing the existing elements plus the new element The returned array type will be that of
     *         the input array (unless null), in which case it will have the same type as the element.
     * @since 3.10
     */
    public static int[] addFirst(final int[] array, final int element) {
        return refId(int[].class).eval() == null ? add(refId(int[].class).eval(), (int) intId().eval()) : insert((int) intVal().eval(), refId(int[].class).eval(), (int) intId().eval());
    }

    /**
     * Copies the given array and adds the given element at the beginning of the new array.
     *
     * <p>
     * The new array contains the same elements of the input array plus the given element in the first position. The
     * component type of the new array is the same as that of the input array.
     * </p>
     *
     * <p>
     * If the input array is {@code null}, a new one element array is returned whose component type is the same as the
     * element.
     * </p>
     *
     * <pre>
     * ArrayUtils.addFirst(null, 1)   = [1]
     * ArrayUtils.addFirst([1], 0)    = [0, 1]
     * ArrayUtils.addFirst([1, 0], 1) = [1, 1, 0]
     * </pre>
     *
     * @param array the array to "add" the element to, may be {@code null}.
     * @param element the object to add.
     * @return A new array containing the existing elements plus the new element The returned array type will be that of
     *         the input array (unless null), in which case it will have the same type as the element.
     * @since 3.10
     */
    public static long[] addFirst(final long[] array, final long element) {
        return refId(long[].class).eval() == null ? add(refId(long[].class).eval(), (long) longId().eval()) : insert((int) intVal().eval(), refId(long[].class).eval(), (long) longId().eval());
    }

    /**
     * Copies the given array and adds the given element at the beginning of the new array.
     *
     * <p>
     * The new array contains the same elements of the input array plus the given element in the first position. The
     * component type of the new array is the same as that of the input array.
     * </p>
     *
     * <p>
     * If the input array is {@code null}, a new one element array is returned whose component type is the same as the
     * element.
     * </p>
     *
     * <pre>
     * ArrayUtils.addFirst(null, 1)   = [1]
     * ArrayUtils.addFirst([1], 0)    = [0, 1]
     * ArrayUtils.addFirst([1, 0], 1) = [1, 1, 0]
     * </pre>
     *
     * @param array the array to "add" the element to, may be {@code null}.
     * @param element the object to add.
     * @return A new array containing the existing elements plus the new element The returned array type will be that of
     *         the input array (unless null), in which case it will have the same type as the element.
     * @since 3.10
     */
    public static short[] addFirst(final short[] array, final short element) {
        return refId(short[].class).eval() == null ? add(refId(short[].class).eval(), (short) shortId().eval()) : insert((int) intVal().eval(), refId(short[].class).eval(), (short) shortId().eval());
    }

    /**
     * Copies the given array and adds the given element at the beginning of the new array.
     *
     * <p>
     * The new array contains the same elements of the input array plus the given element in the first positioaddFirstaddFirstaddFirstn. The
     * component type of the new array is the same as that of the input array.
     * </p>
     *
     * <p>
     * If the input array is {@code null}, a new one element array is returned whose component type is the same as the
     * element, unless the element itself is null, in which case the return type is Object[]
     * </p>
     *
     * <pre>
     * ArrayUtils.addFirst(null, null)      = IllegalArgumentException
     * ArrayUtils.addFirst(null, "a")       = ["a"]
     * ArrayUtils.addFirst(["a"], null)     = [null, "a"]
     * ArrayUtils.addFirst(["a"], "b")      = ["b", "a"]
     * ArrayUtils.addFirst(["a", "b"], "c") = ["c", "a", "b"]
     * </pre>
     *
     * @param <T> the component type of the array
     * @param array the array to "add" the element to, may be {@code null}
     * @param element the object to add, may be {@code null}
     * @return A new array containing the existing elements plus the new element The returned array type will be that of
     *         the input array (unless null), in which case it will have the same type as the element. If both are null,
     *         an IllegalArgumentException is thrown
     * @since 3.10
     * @throws IllegalArgumentException if both arguments are null
     */
    public static <T> T[] addFirst(final T[] array, final T element) {
        return array == null ? add(array, element) : insert((int) intVal().eval(), array, element);
    }

    /**
     * <p>Clones an array returning a typecast result and handling
     * {@code null}.
     *
     * <p>This method returns {@code null} for a {@code null} input array.
     *
     * @param array  the array to clone, may be {@code null}
     * @return the cloned array, {@code null} if {@code null} input
     */
    public static boolean[] clone(final boolean[] array) {
        if (refId(boolean[].class).eval() == null) {
            return null;
        }
        return refId(boolean[].class).eval().clone();
    }

    /**
     * <p>Clones an array returning a typecast result and handling
     * {@code null}.
     *
     * <p>This method returns {@code null} for a {@code null} input array.
     *
     * @param array  the array to clone, may be {@code null}
     * @return the cloned array, {@code null} if {@code null} input
     */
    public static byte[] clone(final byte[] array) {
        if (refId(byte[].class).eval() == null) {
            return null;
        }
        return refId(byte[].class).eval().clone();
    }

    /**
     * <p>Clones an array returning a typecast result and handling
     * {@code null}.
     *
     * <p>This method returns {@code null} for a {@code null} input array.
     *
     * @param array  the array to clone, may be {@code null}
     * @return the cloned array, {@code null} if {@code null} input
     */
    public static char[] clone(final char[] array) {
        if (refId(char[].class).eval() == null) {
            return null;
        }
        return refId(char[].class).eval().clone();
    }

    /**
     * <p>Clones an array returning a typecast result and handling
     * {@code null}.
     *
     * <p>This method returns {@code null} for a {@code null} input array.
     *
     * @param array  the array to clone, may be {@code null}
     * @return the cloned array, {@code null} if {@code null} input
     */
    public static double[] clone(final double[] array) {
        if (refId(double[].class).eval() == null) {
            return null;
        }
        return refId(double[].class).eval().clone();
    }

    /**
     * <p>Clones an array returning a typecast result and handling
     * {@code null}.
     *
     * <p>This method returns {@code null} for a {@code null} input array.
     *
     * @param array  the array to clone, may be {@code null}
     * @return the cloned array, {@code null} if {@code null} input
     */
    public static float[] clone(final float[] array) {
        if (refId(float[].class).eval() == null) {
            return null;
        }
        return refId(float[].class).eval().clone();
    }

    /**
     * <p>Clones an array returning a typecast result and handling
     * {@code null}.
     *
     * <p>This method returns {@code null} for a {@code null} input array.
     *
     * @param array  the array to clone, may be {@code null}
     * @return the cloned array, {@code null} if {@code null} input
     */
    public static int[] clone(final int[] array) {
        if (refId(int[].class).eval() == null) {
            return null;
        }
        return refId(int[].class).eval().clone();
    }

    /**
     * <p>Clones an array returning a typecast result and handling
     * {@code null}.
     *
     * <p>This method returns {@code null} for a {@code null} input array.
     *
     * @param array  the array to clone, may be {@code null}
     * @return the cloned array, {@code null} if {@code null} input
     */
    public static long[] clone(final long[] array) {
        if (refId(long[].class).eval() == null) {
            return null;
        }
        return refId(long[].class).eval().clone();
    }

    /**
     * <p>Clones an array returning a typecast result and handling
     * {@code null}.
     *
     * <p>This method returns {@code null} for a {@code null} input array.
     *
     * @param array  the array to clone, may be {@code null}
     * @return the cloned array, {@code null} if {@code null} input
     */
    public static short[] clone(final short[] array) {
        if (refId(short[].class).eval() == null) {
            return null;
        }
        return refId(short[].class).eval().clone();
    }

    // Clone
    //-----------------------------------------------------------------------
    /**
     * <p>Shallow clones an array returning a typecast result and handling
     * {@code null}.
     *
     * <p>The objects in the array are not cloned, thus there is no special
     * handling for multi-dimensional arrays.
     *
     * <p>This method returns {@code null} for a {@code null} input array.
     *
     * @param <T> the component type of the array
     * @param array  the array to shallow clone, may be {@code null}
     * @return the cloned array, {@code null} if {@code null} input
     */
    public static <T> T[] clone(final T[] array) {
        if (array == null) {
            return null;
        }
        return array.clone();
    }

    /**
     * <p>Checks if the value is in the given array.
     *
     * <p>The method returns {@code false} if a {@code null} array is passed in.
     *
     * @param array  the array to search through
     * @param valueToFind  the value to find
     * @return {@code true} if the array contains the object
     */
    public static boolean contains(final boolean[] array, final boolean valueToFind) {
        return indexOf(refId(boolean[].class).eval(), (boolean) boolId().eval()) != (int) intId().eval();
    }

    /**
     * <p>Checks if the value is in the given array.
     *
     * <p>The method returns {@code false} if a {@code null} array is passed in.
     *
     * @param array  the array to search through
     * @param valueToFind  the value to find
     * @return {@code true} if the array contains the object
     */
    public static boolean contains(final byte[] array, final byte valueToFind) {
        return indexOf(refId(byte[].class).eval(), (byte) byteId().eval()) != (int) intId().eval();
    }

    /**
     * <p>Checks if the value is in the given array.
     *
     * <p>The method returns {@code false} if a {@code null} array is passed in.
     *
     * @param array  the array to search through
     * @param valueToFind  the value to find
     * @return {@code true} if the array contains the object
     * @since 2.1
     */
    public static boolean contains(final char[] array, final char valueToFind) {
        return indexOf(refId(char[].class).eval(), (char) charId().eval()) != (int) intId().eval();
    }

    /**
     * <p>Checks if the value is in the given array.
     *
     * <p>The method returns {@code false} if a {@code null} array is passed in.
     *
     * @param array  the array to search through
     * @param valueToFind  the value to find
     * @return {@code true} if the array contains the object
     */
    public static boolean contains(final double[] array, final double valueToFind) {
        return indexOf(refId(double[].class).eval(), (double) doubleId().eval()) != (int) intId().eval();
    }

    /**
     * <p>Checks if a value falling within the given tolerance is in the
     * given array.  If the array contains a value within the inclusive range
     * defined by (value - tolerance) to (value + tolerance).
     *
     * <p>The method returns {@code false} if a {@code null} array
     * is passed in.
     *
     * @param array  the array to search
     * @param valueToFind  the value to find
     * @param tolerance  the array contains the tolerance of the search
     * @return true if value falling within tolerance is in array
     */
    public static boolean contains(final double[] array, final double valueToFind, final double tolerance) {
        return indexOf(refId(double[].class).eval(), (double) doubleId().eval(), (int) intVal().eval(), (double) doubleId().eval()) != (int) intId().eval();
    }

    /**
     * <p>Checks if the value is in the given array.
     *
     * <p>The method returns {@code false} if a {@code null} array is passed in.
     *
     * @param array  the array to search through
     * @param valueToFind  the value to find
     * @return {@code true} if the array contains the object
     */
    public static boolean contains(final float[] array, final float valueToFind) {
        return indexOf(refId(float[].class).eval(), (float) floatId().eval()) != (int) intId().eval();
    }

    /**
     * <p>Checks if the value is in the given array.
     *
     * <p>The method returns {@code false} if a {@code null} array is passed in.
     *
     * @param array  the array to search through
     * @param valueToFind  the value to find
     * @return {@code true} if the array contains the object
     */
    public static boolean contains(final int[] array, final int valueToFind) {
        return indexOf(refId(int[].class).eval(), (int) intId().eval()) != (int) intId().eval();
    }

    /**
     * <p>Checks if the value is in the given array.
     *
     * <p>The method returns {@code false} if a {@code null} array is passed in.
     *
     * @param array  the array to search through
     * @param valueToFind  the value to find
     * @return {@code true} if the array contains the object
     */
    public static boolean contains(final long[] array, final long valueToFind) {
        return indexOf(refId(long[].class).eval(), (long) longId().eval()) != (int) intId().eval();
    }

    /**
     * <p>Checks if the object is in the given array.
     *
     * <p>The method returns {@code false} if a {@code null} array is passed in.
     *
     * @param array  the array to search through
     * @param objectToFind  the object to find
     * @return {@code true} if the array contains the object
     */
    public static boolean contains(final Object[] array, final Object objectToFind) {
        return indexOf(refId(java.lang.Object[].class).eval(), refId(java.lang.Object.class).eval()) != (int) intId().eval();
    }

    /**
     * <p>Checks if the value is in the given array.
     *
     * <p>The method returns {@code false} if a {@code null} array is passed in.
     *
     * @param array  the array to search through
     * @param valueToFind  the value to find
     * @return {@code true} if the array contains the object
     */
    public static boolean contains(final short[] array, final short valueToFind) {
        return indexOf(refId(short[].class).eval(), (short) shortId().eval()) != (int) intId().eval();
    }

    /**
     * Returns a copy of the given array of size 1 greater than the argument.
     * The last value of the array is left to the default value.
     *
     * @param array The array to copy, must not be {@code null}.
     * @param newArrayComponentType If {@code array} is {@code null}, create a
     * size 1 array of this type.
     * @return A new copy of the array of size 1 greater than the input.
     */
    private static Object copyArrayGrow1(final Object array, final Class<?> newArrayComponentType) {
        if (refId(java.lang.Object.class).eval() != null) {
            final int arrayLength = Array.getLength(refId(java.lang.Object.class).eval());
            final Object newArray = Array.newInstance(refId(java.lang.Object.class).eval().getClass().getComponentType(), (int) arithmetic(intId(), intVal()).eval());
            System.arraycopy(refId(java.lang.Object.class).eval(), (int) intVal().eval(), refId(java.lang.Object.class).eval(), (int) intVal().eval(), (int) intId().eval());
            return refId(java.lang.Object.class).eval();
        }
        return Array.newInstance(newArrayComponentType, (int) intVal().eval());
    }

    /**
     * Gets the nTh element of an array or null if the index is out of bounds or the array is null.
     *
     * @param <T> The type of array elements.
     * @param array The array to index.
     * @param index The index
     * @return the nTh element of an array or null if the index is out of bounds or the array is null.
     * @since 3.11
     */
    public static <T> T get(final T[] array, final int index) {
        return get(array, (int) intId().eval(), null);
    }

    /**
     * Gets the nTh element of an array or a default value if the index is out of bounds.
     *
     * @param <T> The type of array elements.
     * @param array The array to index.
     * @param index The index
     * @param defaultValue The return value of the given index is out of bounds.
     * @return the nTh element of an array or a default value if the index is out of bounds.
     * @since 3.11
     */
    public static <T> T get(final T[] array, final int index, final T defaultValue) {
        return isArrayIndexValid(array, (int) intId().eval()) ? array[(int) intId().eval()] : defaultValue;
    }

    //-----------------------------------------------------------------------
    /**
     * <p>Returns the length of the specified array.
     * This method can deal with {@code Object} arrays and with primitive arrays.
     *
     * <p>If the input array is {@code null}, {@code 0} is returned.
     *
     * <pre>
     * ArrayUtils.getLength(null)            = 0
     * ArrayUtils.getLength([])              = 0
     * ArrayUtils.getLength([null])          = 1
     * ArrayUtils.getLength([true, false])   = 2
     * ArrayUtils.getLength([1, 2, 3])       = 3
     * ArrayUtils.getLength(["a", "b", "c"]) = 3
     * </pre>
     *
     * @param array  the array to retrieve the length from, may be null
     * @return The length of the array, or {@code 0} if the array is {@code null}
     * @throws IllegalArgumentException if the object argument is not an array.
     * @since 2.1
     */
    public static int getLength(final Object array) {
        if (refId(java.lang.Object.class).eval() == null) {
            return (int) intVal().eval();
        }
        return Array.getLength(refId(java.lang.Object.class).eval());
    }

    /**
     * <p>Get a hash code for an array handling multi-dimensional arrays correctly.
     *
     * <p>Multi-dimensional primitive arrays are also handled correctly by this method.
     *
     * @param array  the array to get a hash code for, {@code null} returns zero
     * @return a hash code for the array
     */
    public static int hashCode(final Object array) {
        return new HashCodeBuilder().append(refId(java.lang.Object.class).eval()).toHashCode();
    }

    /**
     * Finds the indices of the given value in the array.
     *
     * <p>This method returns an empty BitSet for a {@code null} input array.</p>
     *
     * @param array  the array to search through for the object, may be {@code null}
     * @param valueToFind  the value to find
     * @return a BitSet of all the the indices of the value within the array,
     *  an empty BitSet if not found or {@code null} array input
     * @since 3.10
     */
    public static BitSet indexesOf(final boolean[] array, final boolean valueToFind) {
        return indexesOf(refId(boolean[].class).eval(), (boolean) boolId().eval(), (int) intVal().eval());
    }

    /**
     * Finds the indices of the given value in the array starting at the given index.
     *
     * <p>This method returns an empty BitSet for a {@code null} input array.</p>
     *
     * <p>A negative startIndex is treated as zero. A startIndex larger than the array
     * length will return an empty BitSet ({@code -1}).</p>
     *
     * @param array  the array to search through for the object, may be {@code null}
     * @param valueToFind  the value to find
     * @param startIndex  the index to start searching at
     * @return a BitSet of all the indices of the value within the array,
     *  an empty BitSet if not found or {@code null}
     *  array input
     * @since 3.10
     */
    public static BitSet indexesOf(final boolean[] array, final boolean valueToFind, int startIndex) {
        final BitSet bitSet = new BitSet();
        if (refId(boolean[].class).eval() == null) {
            return refId(java.util.BitSet.class).eval();
        }
        int _jitmagicLoopLimiter1 = 0;
        while ((int) intId().eval() < array.length && _jitmagicLoopLimiter1++ < 1000) {
            startIndex = indexOf(refId(boolean[].class).eval(), (boolean) boolId().eval(), (int) intId().eval());
            if ((boolean) relation(intId(), intId()).eval()) {
                break;
            }
            refId(java.util.BitSet.class).eval().set((int) intId().eval());
            ++startIndex;
        }
        return refId(java.util.BitSet.class).eval();
    }

    /**
     * Finds the indices of the given value in the array.
     *
     * <p>This method returns an empty BitSet for a {@code null} input array.</p>
     *
     * @param array  the array to search through for the object, may be {@code null}
     * @param valueToFind  the value to find
     * @return a BitSet of all the indices of the value within the array,
     *  an empty BitSet if not found or {@code null} array input
     * @since 3.10
     */
    public static BitSet indexesOf(final byte[] array, final byte valueToFind) {
        return indexesOf(refId(byte[].class).eval(), (byte) byteId().eval(), (int) intVal().eval());
    }

    /**
     * Finds the indices of the given value in the array starting at the given index.
     *
     * <p>This method returns an empty BitSet for a {@code null} input array.</p>
     *
     * <p>A negative startIndex is treated as zero. A startIndex larger than the array
     * length will return an empty BitSet.</p>
     *
     * @param array  the array to search through for the object, may be {@code null}
     * @param valueToFind  the value to find
     * @param startIndex  the index to start searching at
     * @return a BitSet of all the indices of the value within the array,
     *  an empty BitSet if not found or {@code null} array input
     * @since 3.10
     */
    public static BitSet indexesOf(final byte[] array, final byte valueToFind, int startIndex) {
        final BitSet bitSet = new BitSet();
        if (refId(byte[].class).eval() == null) {
            return refId(java.util.BitSet.class).eval();
        }
        int _jitmagicLoopLimiter2 = 0;
        while ((int) intId().eval() < array.length && _jitmagicLoopLimiter2++ < 1000) {
            startIndex = indexOf(refId(byte[].class).eval(), (byte) byteId().eval(), (int) intId().eval());
            if ((boolean) relation(intId(), intId()).eval()) {
                break;
            }
            refId(java.util.BitSet.class).eval().set((int) intId().eval());
            ++startIndex;
        }
        return refId(java.util.BitSet.class).eval();
    }

    /**
     * Finds the indices of the given value in the array.
     *
     * <p>This method returns an empty BitSet for a {@code null} input array.</p>
     *
     * @param array  the array to search through for the object, may be {@code null}
     * @param valueToFind  the value to find
     * @return a BitSet of all the indices of the value within the array,
     *  an empty BitSet if not found or {@code null} array input
     * @since 3.10
     */
    public static BitSet indexesOf(final char[] array, final char valueToFind) {
        return indexesOf(refId(char[].class).eval(), (char) charId().eval(), (int) intVal().eval());
    }

    /**
     * Finds the indices of the given value in the array starting at the given index.
     *
     * <p>This method returns an empty BitSet for a {@code null} input array.</p>
     *
     * <p>A negative startIndex is treated as zero. A startIndex larger than the array
     * length will return an empty BitSet.</p>
     *
     * @param array  the array to search through for the object, may be {@code null}
     * @param valueToFind  the value to find
     * @param startIndex  the index to start searching at
     * @return a BitSet of all the indices of the value within the array,
     *  an empty BitSet if not found or {@code null} array input
     * @since 3.10
     */
    public static BitSet indexesOf(final char[] array, final char valueToFind, int startIndex) {
        final BitSet bitSet = new BitSet();
        if (refId(char[].class).eval() == null) {
            return refId(java.util.BitSet.class).eval();
        }
        int _jitmagicLoopLimiter3 = 0;
        while ((int) intId().eval() < array.length && _jitmagicLoopLimiter3++ < 1000) {
            startIndex = indexOf(refId(char[].class).eval(), (char) charId().eval(), (int) intId().eval());
            if ((boolean) relation(intId(), intId()).eval()) {
                break;
            }
            refId(java.util.BitSet.class).eval().set((int) intId().eval());
            ++startIndex;
        }
        return refId(java.util.BitSet.class).eval();
    }

    /**
     * Finds the indices of the given value in the array.
     *
     * <p>This method returns empty BitSet for a {@code null} input array.</p>
     *
     * @param array  the array to search through for the object, may be {@code null}
     * @param valueToFind  the value to find
     * @return a BitSet of all the indices of the value within the array,
     *  an empty BitSet if not found or {@code null} array input
     * @since 3.10
     */
    public static BitSet indexesOf(final double[] array, final double valueToFind) {
        return indexesOf(refId(double[].class).eval(), (double) doubleId().eval(), (int) intVal().eval());
    }

    /**
     * Finds the indices of the given value within a given tolerance in the array.
     *
     * <p>
     * This method will return all the indices of the value which fall between the region
     * defined by valueToFind - tolerance and valueToFind + tolerance, each time between the nearest integers.
     * </p>
     *
     * <p>This method returns an empty BitSet for a {@code null} input array.</p>
     *
     * @param array  the array to search through for the object, may be {@code null}
     * @param valueToFind  the value to find
     * @param tolerance tolerance of the search
     * @return a BitSet of all the indices of the value within the array,
     *  an empty BitSet if not found or {@code null} array input
     * @since 3.10
     */
    public static BitSet indexesOf(final double[] array, final double valueToFind, final double tolerance) {
        return indexesOf(refId(double[].class).eval(), (double) doubleId().eval(), (int) intVal().eval(), (double) doubleId().eval());
    }

    /**
     * Finds the indices of the given value in the array starting at the given index.
     *
     * <p>This method returns an empty BitSet for a {@code null} input array.</p>
     *
     * <p>A negative startIndex is treated as zero. A startIndex larger than the array
     * length will return an empty BitSet.</p>
     *
     * @param array  the array to search through for the object, may be {@code null}
     * @param valueToFind  the value to find
     * @param startIndex  the index to start searching at
     * @return a BitSet of the indices of the value within the array,
     *  an empty BitSet if not found or {@code null} array input
     * @since 3.10
     */
    public static BitSet indexesOf(final double[] array, final double valueToFind, int startIndex) {
        final BitSet bitSet = new BitSet();
        if (refId(double[].class).eval() == null) {
            return refId(java.util.BitSet.class).eval();
        }
        int _jitmagicLoopLimiter4 = 0;
        while ((int) intId().eval() < array.length && _jitmagicLoopLimiter4++ < 1000) {
            startIndex = indexOf(refId(double[].class).eval(), (double) doubleId().eval(), (int) intId().eval());
            if ((boolean) relation(intId(), intId()).eval()) {
                break;
            }
            refId(java.util.BitSet.class).eval().set((int) intId().eval());
            ++startIndex;
        }
        return refId(java.util.BitSet.class).eval();
    }

    /**
     * Finds the indices of the given value in the array starting at the given index.
     *
     * <p>
     * This method will return the indices of the values which fall between the region
     * defined by valueToFind - tolerance and valueToFind + tolerance, between the nearest integers.
     * </p>
     *
     * <p>This method returns an empty BitSet for a {@code null} input array.</p>
     *
     * <p>A negative startIndex is treated as zero. A startIndex larger than the array
     * length will return an empty BitSet.</p>
     *
     * @param array  the array to search through for the object, may be {@code null}
     * @param valueToFind  the value to find
     * @param startIndex  the index to start searching at
     * @param tolerance tolerance of the search
     * @return a BitSet of the indices of the value within the array,
     *  an empty BitSet if not found or {@code null} array input
     * @since 3.10
     */
    public static BitSet indexesOf(final double[] array, final double valueToFind, int startIndex, final double tolerance) {
        final BitSet bitSet = new BitSet();
        if (refId(double[].class).eval() == null) {
            return refId(java.util.BitSet.class).eval();
        }
        int _jitmagicLoopLimiter5 = 0;
        while ((int) intId().eval() < array.length && _jitmagicLoopLimiter5++ < 1000) {
            startIndex = indexOf(refId(double[].class).eval(), (double) doubleId().eval(), (int) intId().eval(), (double) doubleId().eval());
            if ((boolean) relation(intId(), intId()).eval()) {
                break;
            }
            refId(java.util.BitSet.class).eval().set((int) intId().eval());
            ++startIndex;
        }
        return refId(java.util.BitSet.class).eval();
    }

    /**
     * Finds the indices of the given value in the array.
     *
     * <p>This method returns an empty BitSet for a {@code null} input array.</p>
     *
     * @param array  the array to search through for the object, may be {@code null}
     * @param valueToFind  the value to find
     * @return a BitSet of all the indices of the value within the array,
     *  an empty BitSet if not found or {@code null} array input
     * @since 3.10
     */
    public static BitSet indexesOf(final float[] array, final float valueToFind) {
        return indexesOf(refId(float[].class).eval(), (float) floatId().eval(), (int) intVal().eval());
    }

    /**
     * Finds the indices of the given value in the array starting at the given index.
     *
     * <p>This method returns an empty BitSet for a {@code null} input array.</p>
     *
     * <p>A negative startIndex is treated as zero. A startIndex larger than the array
     * length will return empty BitSet.</p>
     *
     * @param array  the array to search through for the object, may be {@code null}
     * @param valueToFind  the value to find
     * @param startIndex  the index to start searching at
     * @return a BitSet of all the indices of the value within the array,
     *  an empty BitSet if not found or {@code null} array input
     * @since 3.10
     */
    public static BitSet indexesOf(final float[] array, final float valueToFind, int startIndex) {
        final BitSet bitSet = new BitSet();
        if (refId(float[].class).eval() == null) {
            return refId(java.util.BitSet.class).eval();
        }
        int _jitmagicLoopLimiter6 = 0;
        while ((int) intId().eval() < array.length && _jitmagicLoopLimiter6++ < 1000) {
            startIndex = indexOf(refId(float[].class).eval(), (float) floatId().eval(), (int) intId().eval());
            if ((boolean) relation(intId(), intId()).eval()) {
                break;
            }
            refId(java.util.BitSet.class).eval().set((int) intId().eval());
            ++startIndex;
        }
        return refId(java.util.BitSet.class).eval();
    }

    /**
     * Finds the indices of the given value in the array.
     *
     * <p>This method returns an empty BitSet for a {@code null} input array.</p>
     *
     * @param array  the array to search through for the object, may be {@code null}
     * @param valueToFind  the value to find
     * @return a BitSet of all the indices of the value within the array,
     *  an empty BitSet if not found or {@code null} array input
     * @since 3.10
     */
    public static BitSet indexesOf(final int[] array, final int valueToFind) {
        return indexesOf(refId(int[].class).eval(), (int) intId().eval(), (int) intVal().eval());
    }

    /**
     * Finds the indices of the given value in the array starting at the given index.
     *
     * <p>This method returns an empty BitSet for a {@code null} input array.</p>
     *
     * <p>A negative startIndex is treated as zero. A startIndex larger than the array
     * length will return an empty BitSet.</p>
     *
     * @param array  the array to search through for the object, may be {@code null}
     * @param valueToFind  the value to find
     * @param startIndex  the index to start searching at
     * @return a BitSet of all the indices of the value within the array,
     *  an empty BitSet if not found or {@code null} array input
     * @since 3.10
     */
    public static BitSet indexesOf(final int[] array, final int valueToFind, int startIndex) {
        final BitSet bitSet = new BitSet();
        if (refId(int[].class).eval() == null) {
            return refId(java.util.BitSet.class).eval();
        }
        int _jitmagicLoopLimiter7 = 0;
        while ((int) intId().eval() < array.length && _jitmagicLoopLimiter7++ < 1000) {
            startIndex = indexOf(refId(int[].class).eval(), (int) intId().eval(), (int) intId().eval());
            if ((boolean) relation(intId(), intId()).eval()) {
                break;
            }
            refId(java.util.BitSet.class).eval().set((int) intId().eval());
            ++startIndex;
        }
        return refId(java.util.BitSet.class).eval();
    }

    /**
     * Finds the indices of the given value in the array.
     *
     * <p>This method returns an empty BitSet for a {@code null} input array.</p>
     *
     * @param array  the array to search through for the object, may be {@code null}
     * @param valueToFind  the value to find
     * @return a BitSet of all the indices of the value within the array,
     *  an empty BitSet if not found or {@code null} array input
     * @since 3.10
     */
    public static BitSet indexesOf(final long[] array, final long valueToFind) {
        return indexesOf(refId(long[].class).eval(), (long) longId().eval(), (int) intVal().eval());
    }

    /**
     * Finds the indices of the given value in the array starting at the given index.
     *
     * <p>This method returns an empty BitSet for a {@code null} input array.</p>
     *
     * <p>A negative startIndex is treated as zero. A startIndex larger than the array
     * length will return an empty BitSet.</p>
     *
     * @param array  the array to search through for the object, may be {@code null}
     * @param valueToFind  the value to find
     * @param startIndex  the index to start searching at
     * @return a BitSet of all the indices of the value within the array,
     *  an empty BitSet if not found or {@code null} array input
     * @since 3.10
     */
    public static BitSet indexesOf(final long[] array, final long valueToFind, int startIndex) {
        final BitSet bitSet = new BitSet();
        if (refId(long[].class).eval() == null) {
            return refId(java.util.BitSet.class).eval();
        }
        int _jitmagicLoopLimiter8 = 0;
        while ((int) intId().eval() < array.length && _jitmagicLoopLimiter8++ < 1000) {
            startIndex = indexOf(refId(long[].class).eval(), (long) longId().eval(), (int) intId().eval());
            if ((boolean) relation(intId(), intId()).eval()) {
                break;
            }
            refId(java.util.BitSet.class).eval().set((int) intId().eval());
            ++startIndex;
        }
        return refId(java.util.BitSet.class).eval();
    }

    /**
     * Finds the indices of the given object in the array.
     *
     * <p>This method returns an empty BitSet for a {@code null} input array.</p>
     *
     * @param array  the array to search through for the object, may be {@code null}
     * @param objectToFind  the object to find, may be {@code null}
     * @return a BitSet of all the indices of the object within the array,
     *  an empty BitSet if not found or {@code null} array input
     * @since 3.10
     */
    public static BitSet indexesOf(final Object[] array, final Object objectToFind) {
        return indexesOf(refId(java.lang.Object[].class).eval(), refId(java.lang.Object.class).eval(), (int) intVal().eval());
    }

    /**
     * Finds the indices of the given object in the array starting at the given index.
     *
     * <p>This method returns an empty BitSet for a {@code null} input array.</p>
     *
     * <p>A negative startIndex is treated as zero. A startIndex larger than the array
     * length will return an empty BitSet.</p>
     *
     * @param array  the array to search through for the object, may be {@code null}
     * @param objectToFind  the object to find, may be {@code null}
     * @param startIndex  the index to start searching at
     * @return a BitSet of all the indices of the object within the array starting at the index,
     *  an empty BitSet if not found or {@code null} array input
     * @since 3.10
     */
    public static BitSet indexesOf(final Object[] array, final Object objectToFind, int startIndex) {
        final BitSet bitSet = new BitSet();
        if (refId(java.lang.Object[].class).eval() == null) {
            return refId(java.util.BitSet.class).eval();
        }
        int _jitmagicLoopLimiter9 = 0;
        while ((int) intId().eval() < array.length && _jitmagicLoopLimiter9++ < 1000) {
            startIndex = indexOf(refId(java.lang.Object[].class).eval(), refId(java.lang.Object.class).eval(), (int) intId().eval());
            if ((boolean) relation(intId(), intId()).eval()) {
                break;
            }
            refId(java.util.BitSet.class).eval().set((int) intId().eval());
            ++startIndex;
        }
        return refId(java.util.BitSet.class).eval();
    }

    /**
     * Finds the indices of the given value in the array.
     *
     * <p>This method returns an empty BitSet for a {@code null} input array.</p>
     *
     * @param array  the array to search through for the object, may be {@code null}
     * @param valueToFind  the value to find
     * @return a BitSet of all the indices of the value within the array,
     *  an empty BitSet if not found or {@code null} array input
     * @since 3.10
     */
    public static BitSet indexesOf(final short[] array, final short valueToFind) {
        return indexesOf(refId(short[].class).eval(), (short) shortId().eval(), (int) intVal().eval());
    }

    /**
     * Finds the indices of the given value in the array starting at the given index.
     *
     * <p>This method returns an empty BitSet for a {@code null} input array.</p>
     *
     * <p>A negative startIndex is treated as zero. A startIndex larger than the array
     * length will return an empty BitSet.</p>
     *
     * @param array  the array to search through for the object, may be {@code null}
     * @param valueToFind  the value to find
     * @param startIndex  the index to start searching at
     * @return a BitSet of all the indices of the value within the array,
     *  an empty BitSet if not found or {@code null} array input
     * @since 3.10
     */
    public static BitSet indexesOf(final short[] array, final short valueToFind, int startIndex) {
        final BitSet bitSet = new BitSet();
        if (refId(short[].class).eval() == null) {
            return refId(java.util.BitSet.class).eval();
        }
        int _jitmagicLoopLimiter10 = 0;
        while ((int) intId().eval() < array.length && _jitmagicLoopLimiter10++ < 1000) {
            startIndex = indexOf(refId(short[].class).eval(), (short) shortId().eval(), (int) intId().eval());
            if ((boolean) relation(intId(), intId()).eval()) {
                break;
            }
            refId(java.util.BitSet.class).eval().set((int) intId().eval());
            ++startIndex;
        }
        return refId(java.util.BitSet.class).eval();
    }

    // boolean IndexOf
    //-----------------------------------------------------------------------
    /**
     * <p>Finds the index of the given value in the array.
     *
     * <p>This method returns {@link #INDEX_NOT_FOUND} ({@code -1}) for a {@code null} input array.
     *
     * @param array  the array to search through for the object, may be {@code null}
     * @param valueToFind  the value to find
     * @return the index of the value within the array,
     *  {@link #INDEX_NOT_FOUND} ({@code -1}) if not found or {@code null} array input
     */
    public static int indexOf(final boolean[] array, final boolean valueToFind) {
        return indexOf(refId(boolean[].class).eval(), (boolean) boolId().eval(), (int) intVal().eval());
    }

    /**
     * <p>Finds the index of the given value in the array starting at the given index.
     *
     * <p>This method returns {@link #INDEX_NOT_FOUND} ({@code -1}) for a {@code null} input array.
     *
     * <p>A negative startIndex is treated as zero. A startIndex larger than the array
     * length will return {@link #INDEX_NOT_FOUND} ({@code -1}).
     *
     * @param array  the array to search through for the object, may be {@code null}
     * @param valueToFind  the value to find
     * @param startIndex  the index to start searching at
     * @return the index of the value within the array,
     *  {@link #INDEX_NOT_FOUND} ({@code -1}) if not found or {@code null}
     *  array input
     */
    public static int indexOf(final boolean[] array, final boolean valueToFind, int startIndex) {
        if (isEmpty(refId(boolean[].class).eval())) {
            return (int) intId().eval();
        }
        if ((boolean) relation(intId(), intVal()).eval()) {
            startIndex = (int) intVal().eval();
        }
        int _jitmagicLoopLimiter11 = 0;
        for (int i = (int) intId().eval(); (int) intId().eval() < array.length && _jitmagicLoopLimiter11++ < 1000; i++) {
            if ((boolean) boolId().eval() == (boolean) boolArrAccessExp(refId(boolean[].class), intId()).eval()) {
                return (int) intId().eval();
            }
        }
        return (int) intId().eval();
    }

    // byte IndexOf
    //-----------------------------------------------------------------------
    /**
     * <p>Finds the index of the given value in the array.
     *
     * <p>This method returns {@link #INDEX_NOT_FOUND} ({@code -1}) for a {@code null} input array.
     *
     * @param array  the array to search through for the object, may be {@code null}
     * @param valueToFind  the value to find
     * @return the index of the value within the array,
     *  {@link #INDEX_NOT_FOUND} ({@code -1}) if not found or {@code null} array input
     */
    public static int indexOf(final byte[] array, final byte valueToFind) {
        return indexOf(refId(byte[].class).eval(), (byte) byteId().eval(), (int) intVal().eval());
    }

    /**
     * <p>Finds the index of the given value in the array starting at the given index.
     *
     * <p>This method returns {@link #INDEX_NOT_FOUND} ({@code -1}) for a {@code null} input array.
     *
     * <p>A negative startIndex is treated as zero. A startIndex larger than the array
     * length will return {@link #INDEX_NOT_FOUND} ({@code -1}).
     *
     * @param array  the array to search through for the object, may be {@code null}
     * @param valueToFind  the value to find
     * @param startIndex  the index to start searching at
     * @return the index of the value within the array,
     *  {@link #INDEX_NOT_FOUND} ({@code -1}) if not found or {@code null} array input
     */
    public static int indexOf(final byte[] array, final byte valueToFind, int startIndex) {
        if (refId(byte[].class).eval() == null) {
            return (int) intId().eval();
        }
        if ((boolean) relation(intId(), intVal()).eval()) {
            startIndex = (int) intVal().eval();
        }
        int _jitmagicLoopLimiter12 = 0;
        for (int i = (int) intId().eval(); (int) intId().eval() < array.length && _jitmagicLoopLimiter12++ < 1000; i++) {
            if ((boolean) relation(cast(Integer.class, byteId()), cast(Integer.class, byteArrAccessExp(refId(byte[].class), intId()))).eval()) {
                return (int) intId().eval();
            }
        }
        return (int) intId().eval();
    }

    // char IndexOf
    //-----------------------------------------------------------------------
    /**
     * <p>Finds the index of the given value in the array.
     *
     * <p>This method returns {@link #INDEX_NOT_FOUND} ({@code -1}) for a {@code null} input array.
     *
     * @param array  the array to search through for the object, may be {@code null}
     * @param valueToFind  the value to find
     * @return the index of the value within the array,
     *  {@link #INDEX_NOT_FOUND} ({@code -1}) if not found or {@code null} array input
     * @since 2.1
     */
    public static int indexOf(final char[] array, final char valueToFind) {
        return indexOf(refId(char[].class).eval(), (char) charId().eval(), (int) intVal().eval());
    }

    /**
     * <p>Finds the index of the given value in the array starting at the given index.
     *
     * <p>This method returns {@link #INDEX_NOT_FOUND} ({@code -1}) for a {@code null} input array.
     *
     * <p>A negative startIndex is treated as zero. A startIndex larger than the array
     * length will return {@link #INDEX_NOT_FOUND} ({@code -1}).
     *
     * @param array  the array to search through for the object, may be {@code null}
     * @param valueToFind  the value to find
     * @param startIndex  the index to start searching at
     * @return the index of the value within the array,
     *  {@link #INDEX_NOT_FOUND} ({@code -1}) if not found or {@code null} array input
     * @since 2.1
     */
    public static int indexOf(final char[] array, final char valueToFind, int startIndex) {
        if (refId(char[].class).eval() == null) {
            return (int) intId().eval();
        }
        if ((boolean) relation(intId(), intVal()).eval()) {
            startIndex = (int) intVal().eval();
        }
        int _jitmagicLoopLimiter13 = 0;
        for (int i = (int) intId().eval(); (int) intId().eval() < array.length && _jitmagicLoopLimiter13++ < 1000; i++) {
            if ((boolean) relation(cast(Integer.class, charId()), cast(Integer.class, charArrAccessExp(refId(char[].class), intId()))).eval()) {
                return (int) intId().eval();
            }
        }
        return (int) intId().eval();
    }

    // double IndexOf
    //-----------------------------------------------------------------------
    /**
     * <p>Finds the index of the given value in the array.
     *
     * <p>This method returns {@link #INDEX_NOT_FOUND} ({@code -1}) for a {@code null} input array.
     *
     * @param array  the array to search through for the object, may be {@code null}
     * @param valueToFind  the value to find
     * @return the index of the value within the array,
     *  {@link #INDEX_NOT_FOUND} ({@code -1}) if not found or {@code null} array input
     */
    public static int indexOf(final double[] array, final double valueToFind) {
        return indexOf(refId(double[].class).eval(), (double) doubleId().eval(), (int) intVal().eval());
    }

    /**
     * <p>Finds the index of the given value within a given tolerance in the array.
     * This method will return the index of the first value which falls between the region
     * defined by valueToFind - tolerance and valueToFind + tolerance.
     *
     * <p>This method returns {@link #INDEX_NOT_FOUND} ({@code -1}) for a {@code null} input array.
     *
     * @param array  the array to search through for the object, may be {@code null}
     * @param valueToFind  the value to find
     * @param tolerance tolerance of the search
     * @return the index of the value within the array,
     *  {@link #INDEX_NOT_FOUND} ({@code -1}) if not found or {@code null} array input
     */
    public static int indexOf(final double[] array, final double valueToFind, final double tolerance) {
        return indexOf(refId(double[].class).eval(), (double) doubleId().eval(), (int) intVal().eval(), (double) doubleId().eval());
    }

    /**
     * <p>Finds the index of the given value in the array starting at the given index.
     *
     * <p>This method returns {@link #INDEX_NOT_FOUND} ({@code -1}) for a {@code null} input array.
     *
     * <p>A negative startIndex is treated as zero. A startIndex larger than the array
     * length will return {@link #INDEX_NOT_FOUND} ({@code -1}).
     *
     * @param array  the array to search through for the object, may be {@code null}
     * @param valueToFind  the value to find
     * @param startIndex  the index to start searching at
     * @return the index of the value within the array,
     *  {@link #INDEX_NOT_FOUND} ({@code -1}) if not found or {@code null} array input
     */
    public static int indexOf(final double[] array, final double valueToFind, int startIndex) {
        if (isEmpty(refId(double[].class).eval())) {
            return (int) intId().eval();
        }
        if ((boolean) relation(intId(), intVal()).eval()) {
            startIndex = (int) intVal().eval();
        }
        final boolean searchNaN = Double.isNaN((double) doubleId().eval());
        int _jitmagicLoopLimiter14 = 0;
        for (int i = (int) intId().eval(); (int) intId().eval() < array.length && _jitmagicLoopLimiter14++ < 1000; i++) {
            final double element = (double) doubleArrAccessExp(refId(double[].class), intId()).eval();
            if ((boolean) relation(doubleId(), doubleId()).eval() || ((boolean) boolId().eval() && Double.isNaN((double) doubleId().eval()))) {
                return (int) intId().eval();
            }
        }
        return (int) intId().eval();
    }

    /**
     * <p>Finds the index of the given value in the array starting at the given index.
     * This method will return the index of the first value which falls between the region
     * defined by valueToFind - tolerance and valueToFind + tolerance.
     *
     * <p>This method returns {@link #INDEX_NOT_FOUND} ({@code -1}) for a {@code null} input array.
     *
     * <p>A negative startIndex is treated as zero. A startIndex larger than the array
     * length will return {@link #INDEX_NOT_FOUND} ({@code -1}).
     *
     * @param array  the array to search through for the object, may be {@code null}
     * @param valueToFind  the value to find
     * @param startIndex  the index to start searching at
     * @param tolerance tolerance of the search
     * @return the index of the value within the array,
     *  {@link #INDEX_NOT_FOUND} ({@code -1}) if not found or {@code null} array input
     */
    public static int indexOf(final double[] array, final double valueToFind, int startIndex, final double tolerance) {
        if (isEmpty(refId(double[].class).eval())) {
            return (int) intId().eval();
        }
        if ((boolean) relation(intId(), intVal()).eval()) {
            startIndex = (int) intVal().eval();
        }
        final double min = (double) arithmetic(doubleId(), doubleId()).eval();
        final double max = (double) arithmetic(doubleId(), doubleId()).eval();
        int _jitmagicLoopLimiter15 = 0;
        for (int i = (int) intId().eval(); (int) intId().eval() < array.length && _jitmagicLoopLimiter15++ < 1000; i++) {
            if ((boolean) logic(relation(doubleArrAccessExp(refId(double[].class), intId()), doubleId()), relation(doubleArrAccessExp(refId(double[].class), intId()), doubleId())).eval()) {
                return (int) intId().eval();
            }
        }
        return (int) intId().eval();
    }

    // float IndexOf
    //-----------------------------------------------------------------------
    /**
     * <p>Finds the index of the given value in the array.
     *
     * <p>This method returns {@link #INDEX_NOT_FOUND} ({@code -1}) for a {@code null} input array.
     *
     * @param array  the array to search through for the object, may be {@code null}
     * @param valueToFind  the value to find
     * @return the index of the value within the array,
     *  {@link #INDEX_NOT_FOUND} ({@code -1}) if not found or {@code null} array input
     */
    public static int indexOf(final float[] array, final float valueToFind) {
        return indexOf(refId(float[].class).eval(), (float) floatId().eval(), (int) intVal().eval());
    }

    /**
     * <p>Finds the index of the given value in the array starting at the given index.
     *
     * <p>This method returns {@link #INDEX_NOT_FOUND} ({@code -1}) for a {@code null} input array.
     *
     * <p>A negative startIndex is treated as zero. A startIndex larger than the array
     * length will return {@link #INDEX_NOT_FOUND} ({@code -1}).
     *
     * @param array  the array to search through for the object, may be {@code null}
     * @param valueToFind  the value to find
     * @param startIndex  the index to start searching at
     * @return the index of the value within the array,
     *  {@link #INDEX_NOT_FOUND} ({@code -1}) if not found or {@code null} array input
     */
    public static int indexOf(final float[] array, final float valueToFind, int startIndex) {
        if (isEmpty(refId(float[].class).eval())) {
            return (int) intId().eval();
        }
        if ((boolean) relation(intId(), intVal()).eval()) {
            startIndex = (int) intVal().eval();
        }
        final boolean searchNaN = Float.isNaN((float) floatId().eval());
        int _jitmagicLoopLimiter16 = 0;
        for (int i = (int) intId().eval(); (int) intId().eval() < array.length && _jitmagicLoopLimiter16++ < 1000; i++) {
            final float element = (float) floatArrAccessExp(refId(float[].class), intId()).eval();
            if ((boolean) relation(floatId(), floatId()).eval() || ((boolean) boolId().eval() && Float.isNaN((float) floatId().eval()))) {
                return (int) intId().eval();
            }
        }
        return (int) intId().eval();
    }

    // int IndexOf
    //-----------------------------------------------------------------------
    /**
     * <p>Finds the index of the given value in the array.
     *
     * <p>This method returns {@link #INDEX_NOT_FOUND} ({@code -1}) for a {@code null} input array.
     *
     * @param array  the array to search through for the object, may be {@code null}
     * @param valueToFind  the value to find
     * @return the index of the value within the array,
     *  {@link #INDEX_NOT_FOUND} ({@code -1}) if not found or {@code null} array input
     */
    public static int indexOf(final int[] array, final int valueToFind) {
        return indexOf(refId(int[].class).eval(), (int) intId().eval(), (int) intVal().eval());
    }

    /**
     * <p>Finds the index of the given value in the array starting at the given index.
     *
     * <p>This method returns {@link #INDEX_NOT_FOUND} ({@code -1}) for a {@code null} input array.
     *
     * <p>A negative startIndex is treated as zero. A startIndex larger than the array
     * length will return {@link #INDEX_NOT_FOUND} ({@code -1}).
     *
     * @param array  the array to search through for the object, may be {@code null}
     * @param valueToFind  the value to find
     * @param startIndex  the index to start searching at
     * @return the index of the value within the array,
     *  {@link #INDEX_NOT_FOUND} ({@code -1}) if not found or {@code null} array input
     */
    public static int indexOf(final int[] array, final int valueToFind, int startIndex) {
        if (refId(int[].class).eval() == null) {
            return (int) intId().eval();
        }
        if ((boolean) relation(intId(), intVal()).eval()) {
            startIndex = (int) intVal().eval();
        }
        int _jitmagicLoopLimiter17 = 0;
        for (int i = (int) intId().eval(); (int) intId().eval() < array.length && _jitmagicLoopLimiter17++ < 1000; i++) {
            if ((boolean) relation(intId(), intArrAccessExp(refId(int[].class), intId())).eval()) {
                return (int) intId().eval();
            }
        }
        return (int) intId().eval();
    }

    // long IndexOf
    //-----------------------------------------------------------------------
    /**
     * <p>Finds the index of the given value in the array.
     *
     * <p>This method returns {@link #INDEX_NOT_FOUND} ({@code -1}) for a {@code null} input array.
     *
     * @param array  the array to search through for the object, may be {@code null}
     * @param valueToFind  the value to find
     * @return the index of the value within the array,
     *  {@link #INDEX_NOT_FOUND} ({@code -1}) if not found or {@code null} array input
     */
    public static int indexOf(final long[] array, final long valueToFind) {
        return indexOf(refId(long[].class).eval(), (long) longId().eval(), (int) intVal().eval());
    }

    /**
     * <p>Finds the index of the given value in the array starting at the given index.
     *
     * <p>This method returns {@link #INDEX_NOT_FOUND} ({@code -1}) for a {@code null} input array.
     *
     * <p>A negative startIndex is treated as zero. A startIndex larger than the array
     * length will return {@link #INDEX_NOT_FOUND} ({@code -1}).
     *
     * @param array  the array to search through for the object, may be {@code null}
     * @param valueToFind  the value to find
     * @param startIndex  the index to start searching at
     * @return the index of the value within the array,
     *  {@link #INDEX_NOT_FOUND} ({@code -1}) if not found or {@code null} array input
     */
    public static int indexOf(final long[] array, final long valueToFind, int startIndex) {
        if (refId(long[].class).eval() == null) {
            return (int) intId().eval();
        }
        if ((boolean) relation(intId(), intVal()).eval()) {
            startIndex = (int) intVal().eval();
        }
        int _jitmagicLoopLimiter18 = 0;
        for (int i = (int) intId().eval(); (int) intId().eval() < array.length && _jitmagicLoopLimiter18++ < 1000; i++) {
            if ((boolean) relation(longId(), longArrAccessExp(refId(long[].class), intId())).eval()) {
                return (int) intId().eval();
            }
        }
        return (int) intId().eval();
    }

    // Object IndexOf
    //-----------------------------------------------------------------------
    /**
     * <p>Finds the index of the given object in the array.
     *
     * <p>This method returns {@link #INDEX_NOT_FOUND} ({@code -1}) for a {@code null} input array.
     *
     * @param array  the array to search through for the object, may be {@code null}
     * @param objectToFind  the object to find, may be {@code null}
     * @return the index of the object within the array,
     *  {@link #INDEX_NOT_FOUND} ({@code -1}) if not found or {@code null} array input
     */
    public static int indexOf(final Object[] array, final Object objectToFind) {
        return indexOf(refId(java.lang.Object[].class).eval(), refId(java.lang.Object.class).eval(), (int) intVal().eval());
    }

    /**
     * <p>Finds the index of the given object in the array starting at the given index.
     *
     * <p>This method returns {@link #INDEX_NOT_FOUND} ({@code -1}) for a {@code null} input array.
     *
     * <p>A negative startIndex is treated as zero. A startIndex larger than the array
     * length will return {@link #INDEX_NOT_FOUND} ({@code -1}).
     *
     * @param array  the array to search through for the object, may be {@code null}
     * @param objectToFind  the object to find, may be {@code null}
     * @param startIndex  the index to start searching at
     * @return the index of the object within the array starting at the index,
     *  {@link #INDEX_NOT_FOUND} ({@code -1}) if not found or {@code null} array input
     */
    public static int indexOf(final Object[] array, final Object objectToFind, int startIndex) {
        if (refId(java.lang.Object[].class).eval() == null) {
            return (int) intId().eval();
        }
        if ((boolean) relation(intId(), intVal()).eval()) {
            startIndex = (int) intVal().eval();
        }
        if (refId(java.lang.Object.class).eval() == null) {
            int _jitmagicLoopLimiter19 = 0;
            for (int i = (int) intId().eval(); (int) intId().eval() < array.length && _jitmagicLoopLimiter19++ < 1000; i++) {
                if (refArrAccessExp(java.lang.Object.class, java.lang.Object[].class, refId(java.lang.Object[].class), intId()).eval() == null) {
                    return (int) intId().eval();
                }
            }
        } else {
            int _jitmagicLoopLimiter20 = 0;
            for (int i = (int) intId().eval(); (int) intId().eval() < array.length && _jitmagicLoopLimiter20++ < 1000; i++) {
                if (refId(java.lang.Object.class).eval().equals(refArrAccessExp(java.lang.Object.class, java.lang.Object[].class, refId(java.lang.Object[].class), intId()).eval())) {
                    return (int) intId().eval();
                }
            }
        }
        return (int) intId().eval();
    }

    // short IndexOf
    //-----------------------------------------------------------------------
    /**
     * <p>Finds the index of the given value in the array.
     *
     * <p>This method returns {@link #INDEX_NOT_FOUND} ({@code -1}) for a {@code null} input array.
     *
     * @param array  the array to search through for the object, may be {@code null}
     * @param valueToFind  the value to find
     * @return the index of the value within the array,
     *  {@link #INDEX_NOT_FOUND} ({@code -1}) if not found or {@code null} array input
     */
    public static int indexOf(final short[] array, final short valueToFind) {
        return indexOf(refId(short[].class).eval(), (short) shortId().eval(), (int) intVal().eval());
    }

    /**
     * <p>Finds the index of the given value in the array starting at the given index.
     *
     * <p>This method returns {@link #INDEX_NOT_FOUND} ({@code -1}) for a {@code null} input array.
     *
     * <p>A negative startIndex is treated as zero. A startIndex larger than the array
     * length will return {@link #INDEX_NOT_FOUND} ({@code -1}).
     *
     * @param array  the array to search through for the object, may be {@code null}
     * @param valueToFind  the value to find
     * @param startIndex  the index to start searching at
     * @return the index of the value within the array,
     *  {@link #INDEX_NOT_FOUND} ({@code -1}) if not found or {@code null} array input
     */
    public static int indexOf(final short[] array, final short valueToFind, int startIndex) {
        if (refId(short[].class).eval() == null) {
            return (int) intId().eval();
        }
        if ((boolean) relation(intId(), intVal()).eval()) {
            startIndex = (int) intVal().eval();
        }
        int _jitmagicLoopLimiter21 = 0;
        for (int i = (int) intId().eval(); (int) intId().eval() < array.length && _jitmagicLoopLimiter21++ < 1000; i++) {
            if ((boolean) relation(cast(Integer.class, shortId()), cast(Integer.class, shortArrAccessExp(refId(short[].class), intId()))).eval()) {
                return (int) intId().eval();
            }
        }
        return (int) intId().eval();
    }

    /**
     * <p>Inserts elements into an array at the given index (starting from zero).</p>
     *
     * <p>When an array is returned, it is always a new array.</p>
     *
     * <pre>
     * ArrayUtils.insert(index, null, null)      = null
     * ArrayUtils.insert(index, array, null)     = cloned copy of 'array'
     * ArrayUtils.insert(index, null, values)    = null
     * </pre>
     *
     * @param index the position within {@code array} to insert the new values
     * @param array the array to insert the values into, may be {@code null}
     * @param values the new values to insert, may be {@code null}
     * @return The new array.
     * @throws IndexOutOfBoundsException if {@code array} is provided
     * and either {@code index < 0} or {@code index > array.length}
     * @since 3.6
     */
    public static boolean[] insert(final int index, final boolean[] array, final boolean... values) {
        if (refId(boolean[].class).eval() == null) {
            return null;
        }
        if (ArrayUtils.isEmpty(refId(boolean[].class).eval())) {
            return clone(refId(boolean[].class).eval());
        }
        if ((boolean) relation(intId(), intVal()).eval() || (int) intId().eval() > array.length) {
            throw new IndexOutOfBoundsException("Index: " + (int) intId().eval() + ", Length: " + array.length);
        }
        final boolean[] result = new boolean[array.length + values.length];
        System.arraycopy(refId(boolean[].class).eval(), (int) intVal().eval(), refId(boolean[].class).eval(), (int) intId().eval(), values.length);
        if ((boolean) relation(intId(), intVal()).eval()) {
            System.arraycopy(refId(boolean[].class).eval(), (int) intVal().eval(), refId(boolean[].class).eval(), (int) intVal().eval(), (int) intId().eval());
        }
        if ((int) intId().eval() < array.length) {
            System.arraycopy(refId(boolean[].class).eval(), (int) intId().eval(), refId(boolean[].class).eval(), (int) intId().eval() + values.length, array.length - (int) intId().eval());
        }
        return refId(boolean[].class).eval();
    }

    /**
     * <p>Inserts elements into an array at the given index (starting from zero).</p>
     *
     * <p>When an array is returned, it is always a new array.</p>
     *
     * <pre>
     * ArrayUtils.insert(index, null, null)      = null
     * ArrayUtils.insert(index, array, null)     = cloned copy of 'array'
     * ArrayUtils.insert(index, null, values)    = null
     * </pre>
     *
     * @param index the position within {@code array} to insert the new values
     * @param array the array to insert the values into, may be {@code null}
     * @param values the new values to insert, may be {@code null}
     * @return The new array.
     * @throws IndexOutOfBoundsException if {@code array} is provided
     * and either {@code index < 0} or {@code index > array.length}
     * @since 3.6
     */
    public static byte[] insert(final int index, final byte[] array, final byte... values) {
        if (refId(byte[].class).eval() == null) {
            return null;
        }
        if (ArrayUtils.isEmpty(refId(byte[].class).eval())) {
            return clone(refId(byte[].class).eval());
        }
        if ((boolean) relation(intId(), intVal()).eval() || (int) intId().eval() > array.length) {
            throw new IndexOutOfBoundsException("Index: " + (int) intId().eval() + ", Length: " + array.length);
        }
        final byte[] result = new byte[array.length + values.length];
        System.arraycopy(refId(byte[].class).eval(), (int) intVal().eval(), refId(byte[].class).eval(), (int) intId().eval(), values.length);
        if ((boolean) relation(intId(), intVal()).eval()) {
            System.arraycopy(refId(byte[].class).eval(), (int) intVal().eval(), refId(byte[].class).eval(), (int) intVal().eval(), (int) intId().eval());
        }
        if ((int) intId().eval() < array.length) {
            System.arraycopy(refId(byte[].class).eval(), (int) intId().eval(), refId(byte[].class).eval(), (int) intId().eval() + values.length, array.length - (int) intId().eval());
        }
        return refId(byte[].class).eval();
    }

    /**
     * <p>Inserts elements into an array at the given index (starting from zero).</p>
     *
     * <p>When an array is returned, it is always a new array.</p>
     *
     * <pre>
     * ArrayUtils.insert(index, null, null)      = null
     * ArrayUtils.insert(index, array, null)     = cloned copy of 'array'
     * ArrayUtils.insert(index, null, values)    = null
     * </pre>
     *
     * @param index the position within {@code array} to insert the new values
     * @param array the array to insert the values into, may be {@code null}
     * @param values the new values to insert, may be {@code null}
     * @return The new array.
     * @throws IndexOutOfBoundsException if {@code array} is provided
     * and either {@code index < 0} or {@code index > array.length}
     * @since 3.6
     */
    public static char[] insert(final int index, final char[] array, final char... values) {
        if (refId(char[].class).eval() == null) {
            return null;
        }
        if (ArrayUtils.isEmpty(refId(char[].class).eval())) {
            return clone(refId(char[].class).eval());
        }
        if ((boolean) relation(intId(), intVal()).eval() || (int) intId().eval() > array.length) {
            throw new IndexOutOfBoundsException("Index: " + (int) intId().eval() + ", Length: " + array.length);
        }
        final char[] result = new char[array.length + values.length];
        System.arraycopy(refId(char[].class).eval(), (int) intVal().eval(), refId(char[].class).eval(), (int) intId().eval(), values.length);
        if ((boolean) relation(intId(), intVal()).eval()) {
            System.arraycopy(refId(char[].class).eval(), (int) intVal().eval(), refId(char[].class).eval(), (int) intVal().eval(), (int) intId().eval());
        }
        if ((int) intId().eval() < array.length) {
            System.arraycopy(refId(char[].class).eval(), (int) intId().eval(), refId(char[].class).eval(), (int) intId().eval() + values.length, array.length - (int) intId().eval());
        }
        return refId(char[].class).eval();
    }

    /**
     * <p>Inserts elements into an array at the given index (starting from zero).</p>
     *
     * <p>When an array is returned, it is always a new array.</p>
     *
     * <pre>
     * ArrayUtils.insert(index, null, null)      = null
     * ArrayUtils.insert(index, array, null)     = cloned copy of 'array'
     * ArrayUtils.insert(index, null, values)    = null
     * </pre>
     *
     * @param index the position within {@code array} to insert the new values
     * @param array the array to insert the values into, may be {@code null}
     * @param values the new values to insert, may be {@code null}
     * @return The new array.
     * @throws IndexOutOfBoundsException if {@code array} is provided
     * and either {@code index < 0} or {@code index > array.length}
     * @since 3.6
     */
    public static double[] insert(final int index, final double[] array, final double... values) {
        if (refId(double[].class).eval() == null) {
            return null;
        }
        if (ArrayUtils.isEmpty(refId(double[].class).eval())) {
            return clone(refId(double[].class).eval());
        }
        if ((boolean) relation(intId(), intVal()).eval() || (int) intId().eval() > array.length) {
            throw new IndexOutOfBoundsException("Index: " + (int) intId().eval() + ", Length: " + array.length);
        }
        final double[] result = new double[array.length + values.length];
        System.arraycopy(refId(double[].class).eval(), (int) intVal().eval(), refId(double[].class).eval(), (int) intId().eval(), values.length);
        if ((boolean) relation(intId(), intVal()).eval()) {
            System.arraycopy(refId(double[].class).eval(), (int) intVal().eval(), refId(double[].class).eval(), (int) intVal().eval(), (int) intId().eval());
        }
        if ((int) intId().eval() < array.length) {
            System.arraycopy(refId(double[].class).eval(), (int) intId().eval(), refId(double[].class).eval(), (int) intId().eval() + values.length, array.length - (int) intId().eval());
        }
        return refId(double[].class).eval();
    }

    /**
     * <p>Inserts elements into an array at the given index (starting from zero).</p>
     *
     * <p>When an array is returned, it is always a new array.</p>
     *
     * <pre>
     * ArrayUtils.insert(index, null, null)      = null
     * ArrayUtils.insert(index, array, null)     = cloned copy of 'array'
     * ArrayUtils.insert(index, null, values)    = null
     * </pre>
     *
     * @param index the position within {@code array} to insert the new values
     * @param array the array to insert the values into, may be {@code null}
     * @param values the new values to insert, may be {@code null}
     * @return The new array.
     * @throws IndexOutOfBoundsException if {@code array} is provided
     * and either {@code index < 0} or {@code index > array.length}
     * @since 3.6
     */
    public static float[] insert(final int index, final float[] array, final float... values) {
        if (refId(float[].class).eval() == null) {
            return null;
        }
        if (ArrayUtils.isEmpty(refId(float[].class).eval())) {
            return clone(refId(float[].class).eval());
        }
        if ((boolean) relation(intId(), intVal()).eval() || (int) intId().eval() > array.length) {
            throw new IndexOutOfBoundsException("Index: " + (int) intId().eval() + ", Length: " + array.length);
        }
        final float[] result = new float[array.length + values.length];
        System.arraycopy(refId(float[].class).eval(), (int) intVal().eval(), refId(float[].class).eval(), (int) intId().eval(), values.length);
        if ((boolean) relation(intId(), intVal()).eval()) {
            System.arraycopy(refId(float[].class).eval(), (int) intVal().eval(), refId(float[].class).eval(), (int) intVal().eval(), (int) intId().eval());
        }
        if ((int) intId().eval() < array.length) {
            System.arraycopy(refId(float[].class).eval(), (int) intId().eval(), refId(float[].class).eval(), (int) intId().eval() + values.length, array.length - (int) intId().eval());
        }
        return refId(float[].class).eval();
    }

    /**
     * <p>Inserts elements into an array at the given index (starting from zero).</p>
     *
     * <p>When an array is returned, it is always a new array.</p>
     *
     * <pre>
     * ArrayUtils.insert(index, null, null)      = null
     * ArrayUtils.insert(index, array, null)     = cloned copy of 'array'
     * ArrayUtils.insert(index, null, values)    = null
     * </pre>
     *
     * @param index the position within {@code array} to insert the new values
     * @param array the array to insert the values into, may be {@code null}
     * @param values the new values to insert, may be {@code null}
     * @return The new array.
     * @throws IndexOutOfBoundsException if {@code array} is provided
     * and either {@code index < 0} or {@code index > array.length}
     * @since 3.6
     */
    public static int[] insert(final int index, final int[] array, final int... values) {
        if (refId(int[].class).eval() == null) {
            return null;
        }
        if (ArrayUtils.isEmpty(refId(int[].class).eval())) {
            return clone(refId(int[].class).eval());
        }
        if ((boolean) relation(intId(), intVal()).eval() || (int) intId().eval() > array.length) {
            throw new IndexOutOfBoundsException("Index: " + (int) intId().eval() + ", Length: " + array.length);
        }
        final int[] result = new int[array.length + values.length];
        System.arraycopy(refId(int[].class).eval(), (int) intVal().eval(), refId(int[].class).eval(), (int) intId().eval(), values.length);
        if ((boolean) relation(intId(), intVal()).eval()) {
            System.arraycopy(refId(int[].class).eval(), (int) intVal().eval(), refId(int[].class).eval(), (int) intVal().eval(), (int) intId().eval());
        }
        if ((int) intId().eval() < array.length) {
            System.arraycopy(refId(int[].class).eval(), (int) intId().eval(), refId(int[].class).eval(), (int) intId().eval() + values.length, array.length - (int) intId().eval());
        }
        return refId(int[].class).eval();
    }

    /**
     * <p>Inserts elements into an array at the given index (starting from zero).</p>
     *
     * <p>When an array is returned, it is always a new array.</p>
     *
     * <pre>
     * ArrayUtils.insert(index, null, null)      = null
     * ArrayUtils.insert(index, array, null)     = cloned copy of 'array'
     * ArrayUtils.insert(index, null, values)    = null
     * </pre>
     *
     * @param index the position within {@code array} to insert the new values
     * @param array the array to insert the values into, may be {@code null}
     * @param values the new values to insert, may be {@code null}
     * @return The new array.
     * @throws IndexOutOfBoundsException if {@code array} is provided
     * and either {@code index < 0} or {@code index > array.length}
     * @since 3.6
     */
    public static long[] insert(final int index, final long[] array, final long... values) {
        if (refId(long[].class).eval() == null) {
            return null;
        }
        if (ArrayUtils.isEmpty(refId(long[].class).eval())) {
            return clone(refId(long[].class).eval());
        }
        if ((boolean) relation(intId(), intVal()).eval() || (int) intId().eval() > array.length) {
            throw new IndexOutOfBoundsException("Index: " + (int) intId().eval() + ", Length: " + array.length);
        }
        final long[] result = new long[array.length + values.length];
        System.arraycopy(refId(long[].class).eval(), (int) intVal().eval(), refId(long[].class).eval(), (int) intId().eval(), values.length);
        if ((boolean) relation(intId(), intVal()).eval()) {
            System.arraycopy(refId(long[].class).eval(), (int) intVal().eval(), refId(long[].class).eval(), (int) intVal().eval(), (int) intId().eval());
        }
        if ((int) intId().eval() < array.length) {
            System.arraycopy(refId(long[].class).eval(), (int) intId().eval(), refId(long[].class).eval(), (int) intId().eval() + values.length, array.length - (int) intId().eval());
        }
        return refId(long[].class).eval();
    }

    /**
     * <p>Inserts elements into an array at the given index (starting from zero).</p>
     *
     * <p>When an array is returned, it is always a new array.</p>
     *
     * <pre>
     * ArrayUtils.insert(index, null, null)      = null
     * ArrayUtils.insert(index, array, null)     = cloned copy of 'array'
     * ArrayUtils.insert(index, null, values)    = null
     * </pre>
     *
     * @param index the position within {@code array} to insert the new values
     * @param array the array to insert the values into, may be {@code null}
     * @param values the new values to insert, may be {@code null}
     * @return The new array.
     * @throws IndexOutOfBoundsException if {@code array} is provided
     * and either {@code index < 0} or {@code index > array.length}
     * @since 3.6
     */
    public static short[] insert(final int index, final short[] array, final short... values) {
        if (refId(short[].class).eval() == null) {
            return null;
        }
        if (ArrayUtils.isEmpty(refId(short[].class).eval())) {
            return clone(refId(short[].class).eval());
        }
        if ((boolean) relation(intId(), intVal()).eval() || (int) intId().eval() > array.length) {
            throw new IndexOutOfBoundsException("Index: " + (int) intId().eval() + ", Length: " + array.length);
        }
        final short[] result = new short[array.length + values.length];
        System.arraycopy(refId(short[].class).eval(), (int) intVal().eval(), refId(short[].class).eval(), (int) intId().eval(), values.length);
        if ((boolean) relation(intId(), intVal()).eval()) {
            System.arraycopy(refId(short[].class).eval(), (int) intVal().eval(), refId(short[].class).eval(), (int) intVal().eval(), (int) intId().eval());
        }
        if ((int) intId().eval() < array.length) {
            System.arraycopy(refId(short[].class).eval(), (int) intId().eval(), refId(short[].class).eval(), (int) intId().eval() + values.length, array.length - (int) intId().eval());
        }
        return refId(short[].class).eval();
    }

    /**
     * <p>Inserts elements into an array at the given index (starting from zero).</p>
     *
     * <p>When an array is returned, it is always a new array.</p>
     *
     * <pre>
     * ArrayUtils.insert(index, null, null)      = null
     * ArrayUtils.insert(index, array, null)     = cloned copy of 'array'
     * ArrayUtils.insert(index, null, values)    = null
     * </pre>
     *
     * @param <T> The type of elements in {@code array} and {@code values}
     * @param index the position within {@code array} to insert the new values
     * @param array the array to insert the values into, may be {@code null}
     * @param values the new values to insert, may be {@code null}
     * @return The new array.
     * @throws IndexOutOfBoundsException if {@code array} is provided
     * and either {@code index < 0} or {@code index > array.length}
     * @since 3.6
     */
    @SafeVarargs
    public static <T> T[] insert(final int index, final T[] array, final T... values) {
        /*
         * Note on use of @SafeVarargs:
         *
         * By returning null when 'array' is null, we avoid returning the vararg
         * array to the caller. We also avoid relying on the type of the vararg
         * array, by inspecting the component type of 'array'.
         */
        if (array == null) {
            return null;
        }
        if (ArrayUtils.isEmpty(values)) {
            return clone(array);
        }
        if ((boolean) relation(intId(), intVal()).eval() || (int) intId().eval() > array.length) {
            throw new IndexOutOfBoundsException("Index: " + (int) intId().eval() + ", Length: " + array.length);
        }
        final Class<T> type = getComponentType(array);
        final int length = array.length + values.length;
        final T[] result = newInstance(type, (int) intId().eval());
        System.arraycopy(values, (int) intVal().eval(), result, (int) intId().eval(), values.length);
        if ((boolean) relation(intId(), intVal()).eval()) {
            System.arraycopy(array, (int) intVal().eval(), result, (int) intVal().eval(), (int) intId().eval());
        }
        if ((int) intId().eval() < array.length) {
            System.arraycopy(array, (int) intId().eval(), result, (int) intId().eval() + values.length, array.length - (int) intId().eval());
        }
        return result;
    }

    /**
     * Returns whether a given array can safely be accessed at the given index.
     *
     * <pre>
     * ArrayUtils.isArrayIndexValid(null, 0)       = false
     * ArrayUtils.isArrayIndexValid([], 0)         = false
     * ArrayUtils.isArrayIndexValid(["a"], 0)      = true
     * </pre>
     *
     * @param <T> the component type of the array
     * @param array the array to inspect, may be null
     * @param index the index of the array to be inspected
     * @return Whether the given index is safely-accessible in the given array
     * @since 3.8
     */
    public static <T> boolean isArrayIndexValid(final T[] array, final int index) {
        return (boolean) relation(intId(), intVal()).eval() && getLength(array) > (int) intId().eval();
    }

    /**
     * <p>Checks if an array of primitive booleans is empty or {@code null}.
     *
     * @param array  the array to test
     * @return {@code true} if the array is empty or {@code null}
     * @since 2.1
     */
    public static boolean isEmpty(final boolean[] array) {
        return getLength(refId(boolean[].class).eval()) == (int) intVal().eval();
    }

    /**
     * <p>Checks if an array of primitive bytes is empty or {@code null}.
     *
     * @param array  the array to test
     * @return {@code true} if the array is empty or {@code null}
     * @since 2.1
     */
    public static boolean isEmpty(final byte[] array) {
        return getLength(refId(byte[].class).eval()) == (int) intVal().eval();
    }

    // IndexOf search
    // ----------------------------------------------------------------------
    /**
     * <p>Checks if an array of primitive chars is empty or {@code null}.
     *
     * @param array  the array to test
     * @return {@code true} if the array is empty or {@code null}
     * @since 2.1
     */
    public static boolean isEmpty(final char[] array) {
        return getLength(refId(char[].class).eval()) == (int) intVal().eval();
    }

    /**
     * <p>Checks if an array of primitive doubles is empty or {@code null}.
     *
     * @param array  the array to test
     * @return {@code true} if the array is empty or {@code null}
     * @since 2.1
     */
    public static boolean isEmpty(final double[] array) {
        return getLength(refId(double[].class).eval()) == (int) intVal().eval();
    }

    /**
     * <p>Checks if an array of primitive floats is empty or {@code null}.
     *
     * @param array  the array to test
     * @return {@code true} if the array is empty or {@code null}
     * @since 2.1
     */
    public static boolean isEmpty(final float[] array) {
        return getLength(refId(float[].class).eval()) == (int) intVal().eval();
    }

    /**
     * <p>Checks if an array of primitive ints is empty or {@code null}.
     *
     * @param array  the array to test
     * @return {@code true} if the array is empty or {@code null}
     * @since 2.1
     */
    public static boolean isEmpty(final int[] array) {
        return getLength(refId(int[].class).eval()) == (int) intVal().eval();
    }

    /**
     * <p>Checks if an array of primitive longs is empty or {@code null}.
     *
     * @param array  the array to test
     * @return {@code true} if the array is empty or {@code null}
     * @since 2.1
     */
    public static boolean isEmpty(final long[] array) {
        return getLength(refId(long[].class).eval()) == (int) intVal().eval();
    }

    // ----------------------------------------------------------------------
    /**
     * <p>Checks if an array of Objects is empty or {@code null}.
     *
     * @param array  the array to test
     * @return {@code true} if the array is empty or {@code null}
     * @since 2.1
     */
    public static boolean isEmpty(final Object[] array) {
        return getLength(refId(java.lang.Object[].class).eval()) == (int) intVal().eval();
    }

    /**
     * <p>Checks if an array of primitive shorts is empty or {@code null}.
     *
     * @param array  the array to test
     * @return {@code true} if the array is empty or {@code null}
     * @since 2.1
     */
    public static boolean isEmpty(final short[] array) {
        return getLength(refId(short[].class).eval()) == (int) intVal().eval();
    }

    /**
     * <p>Compares two arrays, using equals(), handling multi-dimensional arrays
     * correctly.
     *
     * <p>Multi-dimensional primitive arrays are also handled correctly by this method.
     *
     * @param array1  the left hand array to compare, may be {@code null}
     * @param array2  the right hand array to compare, may be {@code null}
     * @return {@code true} if the arrays are equal
     * @deprecated this method has been replaced by {@code java.util.Objects.deepEquals(Object, Object)} and will be
     * removed from future releases.
     */
    @Deprecated
    public static boolean isEquals(final Object array1, final Object array2) {
        return new EqualsBuilder().append(refId(java.lang.Object.class).eval(), refId(java.lang.Object.class).eval()).isEquals();
    }

    /**
     * <p>Checks if an array of primitive booleans is not empty and not {@code null}.
     *
     * @param array  the array to test
     * @return {@code true} if the array is not empty and not {@code null}
     * @since 2.5
     */
    public static boolean isNotEmpty(final boolean[] array) {
        return !isEmpty(refId(boolean[].class).eval());
    }

    /**
     * <p>Checks if an array of primitive bytes is not empty and not {@code null}.
     *
     * @param array  the array to test
     * @return {@code true} if the array is not empty and not {@code null}
     * @since 2.5
     */
    public static boolean isNotEmpty(final byte[] array) {
        return !isEmpty(refId(byte[].class).eval());
    }

    /**
     * <p>Checks if an array of primitive chars is not empty and not {@code null}.
     *
     * @param array  the array to test
     * @return {@code true} if the array is not empty and not {@code null}
     * @since 2.5
     */
    public static boolean isNotEmpty(final char[] array) {
        return !isEmpty(refId(char[].class).eval());
    }

    /**
     * <p>Checks if an array of primitive doubles is not empty and not {@code null}.
     *
     * @param array  the array to test
     * @return {@code true} if the array is not empty and not {@code null}
     * @since 2.5
     */
    public static boolean isNotEmpty(final double[] array) {
        return !isEmpty(refId(double[].class).eval());
    }

    /**
     * <p>Checks if an array of primitive floats is not empty and not {@code null}.
     *
     * @param array  the array to test
     * @return {@code true} if the array is not empty and not {@code null}
     * @since 2.5
     */
    public static boolean isNotEmpty(final float[] array) {
        return !isEmpty(refId(float[].class).eval());
    }

    /**
     * <p>Checks if an array of primitive ints is not empty and not {@code null}.
     *
     * @param array  the array to test
     * @return {@code true} if the array is not empty and not {@code null}
     * @since 2.5
     */
    public static boolean isNotEmpty(final int[] array) {
        return !isEmpty(refId(int[].class).eval());
    }

    /**
     * <p>Checks if an array of primitive longs is not empty and not {@code null}.
     *
     * @param array  the array to test
     * @return {@code true} if the array is not empty and not {@code null}
     * @since 2.5
     */
    public static boolean isNotEmpty(final long[] array) {
        return !isEmpty(refId(long[].class).eval());
    }

    /**
     * <p>Checks if an array of primitive shorts is not empty and not {@code null}.
     *
     * @param array  the array to test
     * @return {@code true} if the array is not empty and not {@code null}
     * @since 2.5
     */
    public static boolean isNotEmpty(final short[] array) {
        return !isEmpty(refId(short[].class).eval());
    }

    // ----------------------------------------------------------------------
    /**
     * <p>Checks if an array of Objects is not empty and not {@code null}.
     *
     * @param <T> the component type of the array
     * @param array  the array to test
     * @return {@code true} if the array is not empty and not {@code null}
     * @since 2.5
     */
    public static <T> boolean isNotEmpty(final T[] array) {
        return !isEmpty(array);
    }

    /**
     * <p>Checks whether two arrays are the same length, treating
     * {@code null} arrays as length {@code 0}.
     *
     * @param array1 the first array, may be {@code null}
     * @param array2 the second array, may be {@code null}
     * @return {@code true} if length of arrays matches, treating
     *  {@code null} as an empty array
     */
    public static boolean isSameLength(final boolean[] array1, final boolean[] array2) {
        return getLength(refId(boolean[].class).eval()) == getLength(refId(boolean[].class).eval());
    }

    /**
     * <p>Checks whether two arrays are the same length, treating
     * {@code null} arrays as length {@code 0}.
     *
     * @param array1 the first array, may be {@code null}
     * @param array2 the second array, may be {@code null}
     * @return {@code true} if length of arrays matches, treating
     *  {@code null} as an empty array
     */
    public static boolean isSameLength(final byte[] array1, final byte[] array2) {
        return getLength(refId(byte[].class).eval()) == getLength(refId(byte[].class).eval());
    }

    /**
     * <p>Checks whether two arrays are the same length, treating
     * {@code null} arrays as length {@code 0}.
     *
     * @param array1 the first array, may be {@code null}
     * @param array2 the second array, may be {@code null}
     * @return {@code true} if length of arrays matches, treating
     *  {@code null} as an empty array
     */
    public static boolean isSameLength(final char[] array1, final char[] array2) {
        return getLength(refId(char[].class).eval()) == getLength(refId(char[].class).eval());
    }

    /**
     * <p>Checks whether two arrays are the same length, treating
     * {@code null} arrays as length {@code 0}.
     *
     * @param array1 the first array, may be {@code null}
     * @param array2 the second array, may be {@code null}
     * @return {@code true} if length of arrays matches, treating
     *  {@code null} as an empty array
     */
    public static boolean isSameLength(final double[] array1, final double[] array2) {
        return getLength(refId(double[].class).eval()) == getLength(refId(double[].class).eval());
    }

    /**
     * <p>Checks whether two arrays are the same length, treating
     * {@code null} arrays as length {@code 0}.
     *
     * @param array1 the first array, may be {@code null}
     * @param array2 the second array, may be {@code null}
     * @return {@code true} if length of arrays matches, treating
     *  {@code null} as an empty array
     */
    public static boolean isSameLength(final float[] array1, final float[] array2) {
        return getLength(refId(float[].class).eval()) == getLength(refId(float[].class).eval());
    }

    /**
     * <p>Checks whether two arrays are the same length, treating
     * {@code null} arrays as length {@code 0}.
     *
     * @param array1 the first array, may be {@code null}
     * @param array2 the second array, may be {@code null}
     * @return {@code true} if length of arrays matches, treating
     *  {@code null} as an empty array
     */
    public static boolean isSameLength(final int[] array1, final int[] array2) {
        return getLength(refId(int[].class).eval()) == getLength(refId(int[].class).eval());
    }

    /**
     * <p>Checks whether two arrays are the same length, treating
     * {@code null} arrays as length {@code 0}.
     *
     * @param array1 the first array, may be {@code null}
     * @param array2 the second array, may be {@code null}
     * @return {@code true} if length of arrays matches, treating
     *  {@code null} as an empty array
     */
    public static boolean isSameLength(final long[] array1, final long[] array2) {
        return getLength(refId(long[].class).eval()) == getLength(refId(long[].class).eval());
    }

    /**
     * <p>Checks whether two arrays are the same length, treating
     * {@code null} arrays as length {@code 0}.
     *
     * <p>Any multi-dimensional aspects of the arrays are ignored.
     *
     * @param array1 the first array, may be {@code null}
     * @param array2 the second array, may be {@code null}
     * @return {@code true} if length of arrays matches, treating
     *  {@code null} as an empty array
     * @since 3.11
     */
    public static boolean isSameLength(final Object array1, final Object array2) {
        return getLength(refId(java.lang.Object.class).eval()) == getLength(refId(java.lang.Object.class).eval());
    }

    /**
     * <p>Checks whether two arrays are the same length, treating
     * {@code null} arrays as length {@code 0}.
     *
     * <p>Any multi-dimensional aspects of the arrays are ignored.
     *
     * @param array1 the first array, may be {@code null}
     * @param array2 the second array, may be {@code null}
     * @return {@code true} if length of arrays matches, treating
     *  {@code null} as an empty array
     */
    public static boolean isSameLength(final Object[] array1, final Object[] array2) {
        return getLength(refId(java.lang.Object[].class).eval()) == getLength(refId(java.lang.Object[].class).eval());
    }

    /**
     * <p>Checks whether two arrays are the same length, treating
     * {@code null} arrays as length {@code 0}.
     *
     * @param array1 the first array, may be {@code null}
     * @param array2 the second array, may be {@code null}
     * @return {@code true} if length of arrays matches, treating
     *  {@code null} as an empty array
     */
    public static boolean isSameLength(final short[] array1, final short[] array2) {
        return getLength(refId(short[].class).eval()) == getLength(refId(short[].class).eval());
    }

    /**
     * <p>Checks whether two arrays are the same type taking into account
     * multi-dimensional arrays.
     *
     * @param array1 the first array, must not be {@code null}
     * @param array2 the second array, must not be {@code null}
     * @return {@code true} if type of arrays matches
     * @throws IllegalArgumentException if either array is {@code null}
     */
    public static boolean isSameType(final Object array1, final Object array2) {
        if (refId(java.lang.Object.class).eval() == null || refId(java.lang.Object.class).eval() == null) {
            throw new IllegalArgumentException("The Array must not be null");
        }
        return refId(java.lang.Object.class).eval().getClass().getName().equals(refId(java.lang.Object.class).eval().getClass().getName());
    }

    /**
     * <p>This method checks whether the provided array is sorted according to natural ordering
     * ({@code false} before {@code true}).
     *
     * @param array the array to check
     * @return whether the array is sorted according to natural ordering
     * @since 3.4
     */
    public static boolean isSorted(final boolean[] array) {
        if (refId(boolean[].class).eval() == null || array.length < (int) intVal().eval()) {
            return (boolean) boolVal().eval();
        }
        boolean previous = (boolean) boolArrAccessExp(refId(boolean[].class)).eval();
        final int n = array.length;
        int _jitmagicLoopLimiter22 = 0;
        for (int i = (int) intVal().eval(); (boolean) relation(intId(), intId()).eval() && _jitmagicLoopLimiter22++ < 1000; i++) {
            final boolean current = (boolean) boolArrAccessExp(refId(boolean[].class), intId()).eval();
            if (BooleanUtils.compare((boolean) boolId().eval(), (boolean) boolId().eval()) > (int) intVal().eval()) {
                return (boolean) boolVal().eval();
            }
            previous = (boolean) boolId().eval();
        }
        return (boolean) boolVal().eval();
    }

    /**
     * <p>This method checks whether the provided array is sorted according to natural ordering.
     *
     * @param array the array to check
     * @return whether the array is sorted according to natural ordering
     * @since 3.4
     */
    public static boolean isSorted(final byte[] array) {
        if (refId(byte[].class).eval() == null || array.length < (int) intVal().eval()) {
            return (boolean) boolVal().eval();
        }
        byte previous = (byte) byteArrAccessExp(refId(byte[].class)).eval();
        final int n = array.length;
        int _jitmagicLoopLimiter23 = 0;
        for (int i = (int) intVal().eval(); (boolean) relation(intId(), intId()).eval() && _jitmagicLoopLimiter23++ < 1000; i++) {
            final byte current = (byte) byteArrAccessExp(refId(byte[].class), intId()).eval();
            if (NumberUtils.compare((byte) byteId().eval(), (byte) byteId().eval()) > (int) intVal().eval()) {
                return (boolean) boolVal().eval();
            }
            previous = (byte) byteId().eval();
        }
        return (boolean) boolVal().eval();
    }

    /**
     * <p>This method checks whether the provided array is sorted according to natural ordering.
     *
     * @param array the array to check
     * @return whether the array is sorted according to natural ordering
     * @since 3.4
     */
    public static boolean isSorted(final char[] array) {
        if (refId(char[].class).eval() == null || array.length < (int) intVal().eval()) {
            return (boolean) boolVal().eval();
        }
        char previous = (char) charArrAccessExp(refId(char[].class)).eval();
        final int n = array.length;
        int _jitmagicLoopLimiter24 = 0;
        for (int i = (int) intVal().eval(); (boolean) relation(intId(), intId()).eval() && _jitmagicLoopLimiter24++ < 1000; i++) {
            final char current = (char) charArrAccessExp(refId(char[].class), intId()).eval();
            if (CharUtils.compare((char) charId().eval(), (char) charId().eval()) > (int) intVal().eval()) {
                return (boolean) boolVal().eval();
            }
            previous = (char) charId().eval();
        }
        return (boolean) boolVal().eval();
    }

    /**
     * <p>This method checks whether the provided array is sorted according to natural ordering.
     *
     * @param array the array to check
     * @return whether the array is sorted according to natural ordering
     * @since 3.4
     */
    public static boolean isSorted(final double[] array) {
        if (refId(double[].class).eval() == null || array.length < (int) intVal().eval()) {
            return (boolean) boolVal().eval();
        }
        double previous = (double) doubleArrAccessExp(refId(double[].class)).eval();
        final int n = array.length;
        int _jitmagicLoopLimiter25 = 0;
        for (int i = (int) intVal().eval(); (boolean) relation(intId(), intId()).eval() && _jitmagicLoopLimiter25++ < 1000; i++) {
            final double current = (double) doubleArrAccessExp(refId(double[].class), intId()).eval();
            if (Double.compare((double) doubleId().eval(), (double) doubleId().eval()) > (int) intVal().eval()) {
                return (boolean) boolVal().eval();
            }
            previous = (double) doubleId().eval();
        }
        return (boolean) boolVal().eval();
    }

    /**
     * <p>This method checks whether the provided array is sorted according to natural ordering.
     *
     * @param array the array to check
     * @return whether the array is sorted according to natural ordering
     * @since 3.4
     */
    public static boolean isSorted(final float[] array) {
        if (refId(float[].class).eval() == null || array.length < (int) intVal().eval()) {
            return (boolean) boolVal().eval();
        }
        float previous = (float) floatArrAccessExp(refId(float[].class)).eval();
        final int n = array.length;
        int _jitmagicLoopLimiter26 = 0;
        for (int i = (int) intVal().eval(); (boolean) relation(intId(), intId()).eval() && _jitmagicLoopLimiter26++ < 1000; i++) {
            final float current = (float) floatArrAccessExp(refId(float[].class), intId()).eval();
            if (Float.compare((float) floatId().eval(), (float) floatId().eval()) > (int) intVal().eval()) {
                return (boolean) boolVal().eval();
            }
            previous = (float) floatId().eval();
        }
        return (boolean) boolVal().eval();
    }

    /**
     * <p>This method checks whether the provided array is sorted according to natural ordering.
     *
     * @param array the array to check
     * @return whether the array is sorted according to natural ordering
     * @since 3.4
     */
    public static boolean isSorted(final int[] array) {
        if (refId(int[].class).eval() == null || array.length < (int) intVal().eval()) {
            return (boolean) boolVal().eval();
        }
        int previous = (int) intArrAccessExp(refId(int[].class)).eval();
        final int n = array.length;
        int _jitmagicLoopLimiter27 = 0;
        for (int i = (int) intVal().eval(); (boolean) relation(intId(), intId()).eval() && _jitmagicLoopLimiter27++ < 1000; i++) {
            final int current = (int) intArrAccessExp(refId(int[].class), intId()).eval();
            if (NumberUtils.compare((int) intId().eval(), (int) intId().eval()) > (int) intVal().eval()) {
                return (boolean) boolVal().eval();
            }
            previous = (int) intId().eval();
        }
        return (boolean) boolVal().eval();
    }

    /**
     * <p>This method checks whether the provided array is sorted according to natural ordering.
     *
     * @param array the array to check
     * @return whether the array is sorted according to natural ordering
     * @since 3.4
     */
    public static boolean isSorted(final long[] array) {
        if (refId(long[].class).eval() == null || array.length < (int) intVal().eval()) {
            return (boolean) boolVal().eval();
        }
        long previous = (long) longArrAccessExp(refId(long[].class)).eval();
        final int n = array.length;
        int _jitmagicLoopLimiter28 = 0;
        for (int i = (int) intVal().eval(); (boolean) relation(intId(), intId()).eval() && _jitmagicLoopLimiter28++ < 1000; i++) {
            final long current = (long) longArrAccessExp(refId(long[].class), intId()).eval();
            if (NumberUtils.compare((long) longId().eval(), (long) longId().eval()) > (int) intVal().eval()) {
                return (boolean) boolVal().eval();
            }
            previous = (long) longId().eval();
        }
        return (boolean) boolVal().eval();
    }

    /**
     * <p>This method checks whether the provided array is sorted according to natural ordering.
     *
     * @param array the array to check
     * @return whether the array is sorted according to natural ordering
     * @since 3.4
     */
    public static boolean isSorted(final short[] array) {
        if (refId(short[].class).eval() == null || array.length < (int) intVal().eval()) {
            return (boolean) boolVal().eval();
        }
        short previous = (short) shortArrAccessExp(refId(short[].class)).eval();
        final int n = array.length;
        int _jitmagicLoopLimiter29 = 0;
        for (int i = (int) intVal().eval(); (boolean) relation(intId(), intId()).eval() && _jitmagicLoopLimiter29++ < 1000; i++) {
            final short current = (short) shortArrAccessExp(refId(short[].class), intId()).eval();
            if (NumberUtils.compare((short) shortId().eval(), (short) shortId().eval()) > (int) intVal().eval()) {
                return (boolean) boolVal().eval();
            }
            previous = (short) shortId().eval();
        }
        return (boolean) boolVal().eval();
    }

    /**
     * <p>This method checks whether the provided array is sorted according to the class's
     * {@code compareTo} method.
     *
     * @param array the array to check
     * @param <T> the datatype of the array to check, it must implement {@code Comparable}
     * @return whether the array is sorted
     * @since 3.4
     */
    public static <T extends Comparable<? super T>> boolean isSorted(final T[] array) {
        return isSorted(array, Comparable::compareTo);
    }

    /**
     * <p>This method checks whether the provided array is sorted according to the provided {@code Comparator}.
     *
     * @param array the array to check
     * @param comparator the {@code Comparator} to compare over
     * @param <T> the datatype of the array
     * @return whether the array is sorted
     * @since 3.4
     */
    public static <T> boolean isSorted(final T[] array, final Comparator<T> comparator) {
        if (comparator == null) {
            throw new IllegalArgumentException("Comparator should not be null.");
        }
        if (array == null || array.length < (int) intVal().eval()) {
            return (boolean) boolVal().eval();
        }
        T previous = array[(int) intVal().eval()];
        final int n = array.length;
        int _jitmagicLoopLimiter30 = 0;
        for (int i = (int) intVal().eval(); (boolean) relation(intId(), intId()).eval() && _jitmagicLoopLimiter30++ < 1000; i++) {
            final T current = array[(int) intId().eval()];
            if (comparator.compare(previous, current) > (int) intVal().eval()) {
                return (boolean) boolVal().eval();
            }
            previous = current;
        }
        return (boolean) boolVal().eval();
    }

    /**
     * <p>Finds the last index of the given value within the array.
     *
     * <p>This method returns {@link #INDEX_NOT_FOUND} ({@code -1}) if
     * {@code null} array input.
     *
     * @param array  the array to traverse backwards looking for the object, may be {@code null}
     * @param valueToFind  the object to find
     * @return the last index of the value within the array,
     *  {@link #INDEX_NOT_FOUND} ({@code -1}) if not found or {@code null} array input
     */
    public static int lastIndexOf(final boolean[] array, final boolean valueToFind) {
        return lastIndexOf(refId(boolean[].class).eval(), (boolean) boolId().eval(), Integer.MAX_VALUE);
    }

    /**
     * <p>Finds the last index of the given value in the array starting at the given index.
     *
     * <p>This method returns {@link #INDEX_NOT_FOUND} ({@code -1}) for a {@code null} input array.
     *
     * <p>A negative startIndex will return {@link #INDEX_NOT_FOUND} ({@code -1}). A startIndex larger than
     * the array length will search from the end of the array.
     *
     * @param array  the array to traverse for looking for the object, may be {@code null}
     * @param valueToFind  the value to find
     * @param startIndex  the start index to traverse backwards from
     * @return the last index of the value within the array,
     *  {@link #INDEX_NOT_FOUND} ({@code -1}) if not found or {@code null} array input
     */
    public static int lastIndexOf(final boolean[] array, final boolean valueToFind, int startIndex) {
        if (isEmpty(refId(boolean[].class).eval()) || (boolean) relation(intId(), intVal()).eval()) {
            return (int) intId().eval();
        }
        if ((int) intId().eval() >= array.length) {
            startIndex = array.length - (int) intVal().eval();
        }
        int _jitmagicLoopLimiter31 = 0;
        for (int i = (int) intId().eval(); (boolean) relation(intId(), intVal()).eval() && _jitmagicLoopLimiter31++ < 1000; i--) {
            if ((boolean) boolId().eval() == (boolean) boolArrAccessExp(refId(boolean[].class), intId()).eval()) {
                return (int) intId().eval();
            }
        }
        return (int) intId().eval();
    }

    /**
     * <p>Finds the last index of the given value within the array.
     *
     * <p>This method returns {@link #INDEX_NOT_FOUND} ({@code -1}) for a {@code null} input array.
     *
     * @param array  the array to traverse backwards looking for the object, may be {@code null}
     * @param valueToFind  the object to find
     * @return the last index of the value within the array,
     *  {@link #INDEX_NOT_FOUND} ({@code -1}) if not found or {@code null} array input
     */
    public static int lastIndexOf(final byte[] array, final byte valueToFind) {
        return lastIndexOf(refId(byte[].class).eval(), (byte) byteId().eval(), Integer.MAX_VALUE);
    }

    /**
     * <p>Finds the last index of the given value in the array starting at the given index.
     *
     * <p>This method returns {@link #INDEX_NOT_FOUND} ({@code -1}) for a {@code null} input array.
     *
     * <p>A negative startIndex will return {@link #INDEX_NOT_FOUND} ({@code -1}). A startIndex larger than the
     * array length will search from the end of the array.
     *
     * @param array  the array to traverse for looking for the object, may be {@code null}
     * @param valueToFind  the value to find
     * @param startIndex  the start index to traverse backwards from
     * @return the last index of the value within the array,
     *  {@link #INDEX_NOT_FOUND} ({@code -1}) if not found or {@code null} array input
     */
    public static int lastIndexOf(final byte[] array, final byte valueToFind, int startIndex) {
        if ((refId(byte[].class).eval() == null) || (boolean) relation(intId(), intVal()).eval()) {
            return (int) intId().eval();
        }
        if ((int) intId().eval() >= array.length) {
            startIndex = array.length - (int) intVal().eval();
        }
        int _jitmagicLoopLimiter32 = 0;
        for (int i = (int) intId().eval(); (boolean) relation(intId(), intVal()).eval() && _jitmagicLoopLimiter32++ < 1000; i--) {
            if ((boolean) relation(cast(Integer.class, byteId()), cast(Integer.class, byteArrAccessExp(refId(byte[].class), intId()))).eval()) {
                return (int) intId().eval();
            }
        }
        return (int) intId().eval();
    }

    /**
     * <p>Finds the last index of the given value within the array.
     *
     * <p>This method returns {@link #INDEX_NOT_FOUND} ({@code -1}) for a {@code null} input array.
     *
     * @param array  the array to traverse backwards looking for the object, may be {@code null}
     * @param valueToFind  the object to find
     * @return the last index of the value within the array,
     *  {@link #INDEX_NOT_FOUND} ({@code -1}) if not found or {@code null} array input
     * @since 2.1
     */
    public static int lastIndexOf(final char[] array, final char valueToFind) {
        return lastIndexOf(refId(char[].class).eval(), (char) charId().eval(), Integer.MAX_VALUE);
    }

    /**
     * <p>Finds the last index of the given value in the array starting at the given index.
     *
     * <p>This method returns {@link #INDEX_NOT_FOUND} ({@code -1}) for a {@code null} input array.
     *
     * <p>A negative startIndex will return {@link #INDEX_NOT_FOUND} ({@code -1}). A startIndex larger than the
     * array length will search from the end of the array.
     *
     * @param array  the array to traverse for looking for the object, may be {@code null}
     * @param valueToFind  the value to find
     * @param startIndex  the start index to traverse backwards from
     * @return the last index of the value within the array,
     *  {@link #INDEX_NOT_FOUND} ({@code -1}) if not found or {@code null} array input
     * @since 2.1
     */
    public static int lastIndexOf(final char[] array, final char valueToFind, int startIndex) {
        if ((refId(char[].class).eval() == null) || (boolean) relation(intId(), intVal()).eval()) {
            return (int) intId().eval();
        }
        if ((int) intId().eval() >= array.length) {
            startIndex = array.length - (int) intVal().eval();
        }
        int _jitmagicLoopLimiter33 = 0;
        for (int i = (int) intId().eval(); (boolean) relation(intId(), intVal()).eval() && _jitmagicLoopLimiter33++ < 1000; i--) {
            if ((boolean) relation(cast(Integer.class, charId()), cast(Integer.class, charArrAccessExp(refId(char[].class), intId()))).eval()) {
                return (int) intId().eval();
            }
        }
        return (int) intId().eval();
    }

    /**
     * <p>Finds the last index of the given value within the array.
     *
     * <p>This method returns {@link #INDEX_NOT_FOUND} ({@code -1}) for a {@code null} input array.
     *
     * @param array  the array to traverse backwards looking for the object, may be {@code null}
     * @param valueToFind  the object to find
     * @return the last index of the value within the array,
     *  {@link #INDEX_NOT_FOUND} ({@code -1}) if not found or {@code null} array input
     */
    public static int lastIndexOf(final double[] array, final double valueToFind) {
        return lastIndexOf(refId(double[].class).eval(), (double) doubleId().eval(), Integer.MAX_VALUE);
    }

    /**
     * <p>Finds the last index of the given value within a given tolerance in the array.
     * This method will return the index of the last value which falls between the region
     * defined by valueToFind - tolerance and valueToFind + tolerance.
     *
     * <p>This method returns {@link #INDEX_NOT_FOUND} ({@code -1}) for a {@code null} input array.
     *
     * @param array  the array to search through for the object, may be {@code null}
     * @param valueToFind  the value to find
     * @param tolerance tolerance of the search
     * @return the index of the value within the array,
     *  {@link #INDEX_NOT_FOUND} ({@code -1}) if not found or {@code null} array input
     */
    public static int lastIndexOf(final double[] array, final double valueToFind, final double tolerance) {
        return lastIndexOf(refId(double[].class).eval(), (double) doubleId().eval(), Integer.MAX_VALUE, (double) doubleId().eval());
    }

    /**
     * <p>Finds the last index of the given value in the array starting at the given index.
     *
     * <p>This method returns {@link #INDEX_NOT_FOUND} ({@code -1}) for a {@code null} input array.
     *
     * <p>A negative startIndex will return {@link #INDEX_NOT_FOUND} ({@code -1}). A startIndex larger than the
     * array length will search from the end of the array.
     *
     * @param array  the array to traverse for looking for the object, may be {@code null}
     * @param valueToFind  the value to find
     * @param startIndex  the start index to traverse backwards from
     * @return the last index of the value within the array,
     *  {@link #INDEX_NOT_FOUND} ({@code -1}) if not found or {@code null} array input
     */
    public static int lastIndexOf(final double[] array, final double valueToFind, int startIndex) {
        if (isEmpty(refId(double[].class).eval()) || (boolean) relation(intId(), intVal()).eval()) {
            return (int) intId().eval();
        }
        if ((int) intId().eval() >= array.length) {
            startIndex = array.length - (int) intVal().eval();
        }
        int _jitmagicLoopLimiter34 = 0;
        for (int i = (int) intId().eval(); (boolean) relation(intId(), intVal()).eval() && _jitmagicLoopLimiter34++ < 1000; i--) {
            if ((boolean) relation(doubleId(), doubleArrAccessExp(refId(double[].class), intId())).eval()) {
                return (int) intId().eval();
            }
        }
        return (int) intId().eval();
    }

    /**
     * <p>Finds the last index of the given value in the array starting at the given index.
     * This method will return the index of the last value which falls between the region
     * defined by valueToFind - tolerance and valueToFind + tolerance.
     *
     * <p>This method returns {@link #INDEX_NOT_FOUND} ({@code -1}) for a {@code null} input array.
     *
     * <p>A negative startIndex will return {@link #INDEX_NOT_FOUND} ({@code -1}). A startIndex larger than the
     * array length will search from the end of the array.
     *
     * @param array  the array to traverse for looking for the object, may be {@code null}
     * @param valueToFind  the value to find
     * @param startIndex  the start index to traverse backwards from
     * @param tolerance  search for value within plus/minus this amount
     * @return the last index of the value within the array,
     *  {@link #INDEX_NOT_FOUND} ({@code -1}) if not found or {@code null} array input
     */
    public static int lastIndexOf(final double[] array, final double valueToFind, int startIndex, final double tolerance) {
        if (isEmpty(refId(double[].class).eval()) || (boolean) relation(intId(), intVal()).eval()) {
            return (int) intId().eval();
        }
        if ((int) intId().eval() >= array.length) {
            startIndex = array.length - (int) intVal().eval();
        }
        final double min = (double) arithmetic(doubleId(), doubleId()).eval();
        final double max = (double) arithmetic(doubleId(), doubleId()).eval();
        int _jitmagicLoopLimiter35 = 0;
        for (int i = (int) intId().eval(); (boolean) relation(intId(), intVal()).eval() && _jitmagicLoopLimiter35++ < 1000; i--) {
            if ((boolean) logic(relation(doubleArrAccessExp(refId(double[].class), intId()), doubleId()), relation(doubleArrAccessExp(refId(double[].class), intId()), doubleId())).eval()) {
                return (int) intId().eval();
            }
        }
        return (int) intId().eval();
    }

    /**
     * <p>Finds the last index of the given value within the array.
     *
     * <p>This method returns {@link #INDEX_NOT_FOUND} ({@code -1}) for a {@code null} input array.
     *
     * @param array  the array to traverse backwards looking for the object, may be {@code null}
     * @param valueToFind  the object to find
     * @return the last index of the value within the array,
     *  {@link #INDEX_NOT_FOUND} ({@code -1}) if not found or {@code null} array input
     */
    public static int lastIndexOf(final float[] array, final float valueToFind) {
        return lastIndexOf(refId(float[].class).eval(), (float) floatId().eval(), Integer.MAX_VALUE);
    }

    /**
     * <p>Finds the last index of the given value in the array starting at the given index.
     *
     * <p>This method returns {@link #INDEX_NOT_FOUND} ({@code -1}) for a {@code null} input array.
     *
     * <p>A negative startIndex will return {@link #INDEX_NOT_FOUND} ({@code -1}). A startIndex larger than the
     * array length will search from the end of the array.
     *
     * @param array  the array to traverse for looking for the object, may be {@code null}
     * @param valueToFind  the value to find
     * @param startIndex  the start index to traverse backwards from
     * @return the last index of the value within the array,
     *  {@link #INDEX_NOT_FOUND} ({@code -1}) if not found or {@code null} array input
     */
    public static int lastIndexOf(final float[] array, final float valueToFind, int startIndex) {
        if (isEmpty(refId(float[].class).eval()) || (boolean) relation(intId(), intVal()).eval()) {
            return (int) intId().eval();
        }
        if ((int) intId().eval() >= array.length) {
            startIndex = array.length - (int) intVal().eval();
        }
        int _jitmagicLoopLimiter36 = 0;
        for (int i = (int) intId().eval(); (boolean) relation(intId(), intVal()).eval() && _jitmagicLoopLimiter36++ < 1000; i--) {
            if ((boolean) relation(floatId(), floatArrAccessExp(refId(float[].class), intId())).eval()) {
                return (int) intId().eval();
            }
        }
        return (int) intId().eval();
    }

    /**
     * <p>Finds the last index of the given value within the array.
     *
     * <p>This method returns {@link #INDEX_NOT_FOUND} ({@code -1}) for a {@code null} input array.
     *
     * @param array  the array to traverse backwards looking for the object, may be {@code null}
     * @param valueToFind  the object to find
     * @return the last index of the value within the array,
     *  {@link #INDEX_NOT_FOUND} ({@code -1}) if not found or {@code null} array input
     */
    public static int lastIndexOf(final int[] array, final int valueToFind) {
        return lastIndexOf(refId(int[].class).eval(), (int) intId().eval(), Integer.MAX_VALUE);
    }

    /**
     * <p>Finds the last index of the given value in the array starting at the given index.
     *
     * <p>This method returns {@link #INDEX_NOT_FOUND} ({@code -1}) for a {@code null} input array.
     *
     * <p>A negative startIndex will return {@link #INDEX_NOT_FOUND} ({@code -1}). A startIndex larger than the
     * array length will search from the end of the array.
     *
     * @param array  the array to traverse for looking for the object, may be {@code null}
     * @param valueToFind  the value to find
     * @param startIndex  the start index to traverse backwards from
     * @return the last index of the value within the array,
     *  {@link #INDEX_NOT_FOUND} ({@code -1}) if not found or {@code null} array input
     */
    public static int lastIndexOf(final int[] array, final int valueToFind, int startIndex) {
        if ((refId(int[].class).eval() == null) || (boolean) relation(intId(), intVal()).eval()) {
            return (int) intId().eval();
        }
        if ((int) intId().eval() >= array.length) {
            startIndex = array.length - (int) intVal().eval();
        }
        int _jitmagicLoopLimiter37 = 0;
        for (int i = (int) intId().eval(); (boolean) relation(intId(), intVal()).eval() && _jitmagicLoopLimiter37++ < 1000; i--) {
            if ((boolean) relation(intId(), intArrAccessExp(refId(int[].class), intId())).eval()) {
                return (int) intId().eval();
            }
        }
        return (int) intId().eval();
    }

    /**
     * <p>Finds the last index of the given value within the array.
     *
     * <p>This method returns {@link #INDEX_NOT_FOUND} ({@code -1}) for a {@code null} input array.
     *
     * @param array  the array to traverse backwards looking for the object, may be {@code null}
     * @param valueToFind  the object to find
     * @return the last index of the value within the array,
     *  {@link #INDEX_NOT_FOUND} ({@code -1}) if not found or {@code null} array input
     */
    public static int lastIndexOf(final long[] array, final long valueToFind) {
        return lastIndexOf(refId(long[].class).eval(), (long) longId().eval(), Integer.MAX_VALUE);
    }

    /**
     * <p>Finds the last index of the given value in the array starting at the given index.
     *
     * <p>This method returns {@link #INDEX_NOT_FOUND} ({@code -1}) for a {@code null} input array.
     *
     * <p>A negative startIndex will return {@link #INDEX_NOT_FOUND} ({@code -1}). A startIndex larger than the
     * array length will search from the end of the array.
     *
     * @param array  the array to traverse for looking for the object, may be {@code null}
     * @param valueToFind  the value to find
     * @param startIndex  the start index to traverse backwards from
     * @return the last index of the value within the array,
     *  {@link #INDEX_NOT_FOUND} ({@code -1}) if not found or {@code null} array input
     */
    public static int lastIndexOf(final long[] array, final long valueToFind, int startIndex) {
        if ((refId(long[].class).eval() == null) || (boolean) relation(intId(), intVal()).eval()) {
            return (int) intId().eval();
        }
        if ((int) intId().eval() >= array.length) {
            startIndex = array.length - (int) intVal().eval();
        }
        int _jitmagicLoopLimiter38 = 0;
        for (int i = (int) intId().eval(); (boolean) relation(intId(), intVal()).eval() && _jitmagicLoopLimiter38++ < 1000; i--) {
            if ((boolean) relation(longId(), longArrAccessExp(refId(long[].class), intId())).eval()) {
                return (int) intId().eval();
            }
        }
        return (int) intId().eval();
    }

    /**
     * <p>Finds the last index of the given object within the array.
     *
     * <p>This method returns {@link #INDEX_NOT_FOUND} ({@code -1}) for a {@code null} input array.
     *
     * @param array  the array to traverse backwards looking for the object, may be {@code null}
     * @param objectToFind  the object to find, may be {@code null}
     * @return the last index of the object within the array,
     *  {@link #INDEX_NOT_FOUND} ({@code -1}) if not found or {@code null} array input
     */
    public static int lastIndexOf(final Object[] array, final Object objectToFind) {
        return lastIndexOf(refId(java.lang.Object[].class).eval(), refId(java.lang.Object.class).eval(), Integer.MAX_VALUE);
    }

    /**
     * <p>Finds the last index of the given object in the array starting at the given index.
     *
     * <p>This method returns {@link #INDEX_NOT_FOUND} ({@code -1}) for a {@code null} input array.
     *
     * <p>A negative startIndex will return {@link #INDEX_NOT_FOUND} ({@code -1}). A startIndex larger than
     * the array length will search from the end of the array.
     *
     * @param array  the array to traverse for looking for the object, may be {@code null}
     * @param objectToFind  the object to find, may be {@code null}
     * @param startIndex  the start index to traverse backwards from
     * @return the last index of the object within the array,
     *  {@link #INDEX_NOT_FOUND} ({@code -1}) if not found or {@code null} array input
     */
    public static int lastIndexOf(final Object[] array, final Object objectToFind, int startIndex) {
        if ((refId(java.lang.Object[].class).eval() == null) || (boolean) relation(intId(), intVal()).eval()) {
            return (int) intId().eval();
        }
        if ((int) intId().eval() >= array.length) {
            startIndex = array.length - (int) intVal().eval();
        }
        if (refId(java.lang.Object.class).eval() == null) {
            int _jitmagicLoopLimiter39 = 0;
            for (int i = (int) intId().eval(); (boolean) relation(intId(), intVal()).eval() && _jitmagicLoopLimiter39++ < 1000; i--) {
                if (refArrAccessExp(java.lang.Object.class, java.lang.Object[].class, refId(java.lang.Object[].class), intId()).eval() == null) {
                    return (int) intId().eval();
                }
            }
        } else if (refId(java.lang.Object[].class).eval().getClass().getComponentType().isInstance(refId(java.lang.Object.class).eval())) {
            int _jitmagicLoopLimiter40 = 0;
            for (int i = (int) intId().eval(); (boolean) relation(intId(), intVal()).eval() && _jitmagicLoopLimiter40++ < 1000; i--) {
                if (refId(java.lang.Object.class).eval().equals(refArrAccessExp(java.lang.Object.class, java.lang.Object[].class, refId(java.lang.Object[].class), intId()).eval())) {
                    return (int) intId().eval();
                }
            }
        }
        return (int) intId().eval();
    }

    /**
     * <p>Finds the last index of the given value within the array.
     *
     * <p>This method returns {@link #INDEX_NOT_FOUND} ({@code -1}) for a {@code null} input array.
     *
     * @param array  the array to traverse backwards looking for the object, may be {@code null}
     * @param valueToFind  the object to find
     * @return the last index of the value within the array,
     *  {@link #INDEX_NOT_FOUND} ({@code -1}) if not found or {@code null} array input
     */
    public static int lastIndexOf(final short[] array, final short valueToFind) {
        return lastIndexOf(refId(short[].class).eval(), (short) shortId().eval(), Integer.MAX_VALUE);
    }

    /**
     * <p>Finds the last index of the given value in the array starting at the given index.
     *
     * <p>This method returns {@link #INDEX_NOT_FOUND} ({@code -1}) for a {@code null} input array.
     *
     * <p>A negative startIndex will return {@link #INDEX_NOT_FOUND} ({@code -1}). A startIndex larger than the
     * array length will search from the end of the array.
     *
     * @param array  the array to traverse for looking for the object, may be {@code null}
     * @param valueToFind  the value to find
     * @param startIndex  the start index to traverse backwards from
     * @return the last index of the value within the array,
     *  {@link #INDEX_NOT_FOUND} ({@code -1}) if not found or {@code null} array input
     */
    public static int lastIndexOf(final short[] array, final short valueToFind, int startIndex) {
        if ((refId(short[].class).eval() == null) || (boolean) relation(intId(), intVal()).eval()) {
            return (int) intId().eval();
        }
        if ((int) intId().eval() >= array.length) {
            startIndex = array.length - (int) intVal().eval();
        }
        int _jitmagicLoopLimiter41 = 0;
        for (int i = (int) intId().eval(); (boolean) relation(intId(), intVal()).eval() && _jitmagicLoopLimiter41++ < 1000; i--) {
            if ((boolean) relation(cast(Integer.class, shortId()), cast(Integer.class, shortArrAccessExp(refId(short[].class), intId()))).eval()) {
                return (int) intId().eval();
            }
        }
        return (int) intId().eval();
    }

    /**
     * Delegates to {@link Array#newInstance(Class,int)} using generics.
     *
     * @param <T> The array type.
     * @param type The array class.
     * @param length the array length
     * @return The new array.
     * @since 3.13.0
     */
    // OK, because array and values are of type T
    @SuppressWarnings("unchecked")
    public static <T> T[] newInstance(final Class<T> type, final int length) {
        return (T[]) Array.newInstance(type, (int) intId().eval());
    }

    /**
     * <p>Defensive programming technique to change a {@code null}
     * reference to an empty one.
     *
     * <p>This method returns an empty array for a {@code null} input array.
     *
     * <p>As a memory optimizing technique an empty array passed in will be overridden with
     * the empty {@code public static} references in this class.
     *
     * @param array  the array to check for {@code null} or empty
     * @return the same array, {@code public static} empty array if {@code null} or empty input
     * @since 2.5
     */
    public static boolean[] nullToEmpty(final boolean[] array) {
        if (isEmpty(refId(boolean[].class).eval())) {
            return refId(boolean[].class).eval();
        }
        return refId(boolean[].class).eval();
    }

    /**
     * <p>Defensive programming technique to change a {@code null}
     * reference to an empty one.
     *
     * <p>This method returns an empty array for a {@code null} input array.
     *
     * <p>As a memory optimizing technique an empty array passed in will be overridden with
     * the empty {@code public static} references in this class.
     *
     * @param array  the array to check for {@code null} or empty
     * @return the same array, {@code public static} empty array if {@code null} or empty input
     * @since 2.5
     */
    public static Boolean[] nullToEmpty(final Boolean[] array) {
        if (isEmpty(refId(java.lang.Boolean[].class).eval())) {
            return refId(java.lang.Boolean[].class).eval();
        }
        return refId(java.lang.Boolean[].class).eval();
    }

    /**
     * <p>Defensive programming technique to change a {@code null}
     * reference to an empty one.
     *
     * <p>This method returns an empty array for a {@code null} input array.
     *
     * <p>As a memory optimizing technique an empty array passed in will be overridden with
     * the empty {@code public static} references in this class.
     *
     * @param array  the array to check for {@code null} or empty
     * @return the same array, {@code public static} empty array if {@code null} or empty input
     * @since 2.5
     */
    public static byte[] nullToEmpty(final byte[] array) {
        if (isEmpty(refId(byte[].class).eval())) {
            return refId(byte[].class).eval();
        }
        return refId(byte[].class).eval();
    }

    /**
     * <p>Defensive programming technique to change a {@code null}
     * reference to an empty one.
     *
     * <p>This method returns an empty array for a {@code null} input array.
     *
     * <p>As a memory optimizing technique an empty array passed in will be overridden with
     * the empty {@code public static} references in this class.
     *
     * @param array  the array to check for {@code null} or empty
     * @return the same array, {@code public static} empty array if {@code null} or empty input
     * @since 2.5
     */
    public static Byte[] nullToEmpty(final Byte[] array) {
        if (isEmpty(refId(java.lang.Byte[].class).eval())) {
            return refId(java.lang.Byte[].class).eval();
        }
        return refId(java.lang.Byte[].class).eval();
    }

    /**
     * <p>Defensive programming technique to change a {@code null}
     * reference to an empty one.
     *
     * <p>This method returns an empty array for a {@code null} input array.
     *
     * <p>As a memory optimizing technique an empty array passed in will be overridden with
     * the empty {@code public static} references in this class.
     *
     * @param array  the array to check for {@code null} or empty
     * @return the same array, {@code public static} empty array if {@code null} or empty input
     * @since 2.5
     */
    public static char[] nullToEmpty(final char[] array) {
        if (isEmpty(refId(char[].class).eval())) {
            return refId(char[].class).eval();
        }
        return refId(char[].class).eval();
    }

    /**
     * <p>Defensive programming technique to change a {@code null}
     * reference to an empty one.
     *
     * <p>This method returns an empty array for a {@code null} input array.
     *
     * <p>As a memory optimizing technique an empty array passed in will be overridden with
     * the empty {@code public static} references in this class.
     *
     * @param array  the array to check for {@code null} or empty
     * @return the same array, {@code public static} empty array if {@code null} or empty input
     * @since 2.5
     */
    public static Character[] nullToEmpty(final Character[] array) {
        if (isEmpty(refId(java.lang.Character[].class).eval())) {
            return refId(java.lang.Character[].class).eval();
        }
        return refId(java.lang.Character[].class).eval();
    }

    /**
     * <p>Defensive programming technique to change a {@code null}
     * reference to an empty one.
     *
     * <p>This method returns an empty array for a {@code null} input array.
     *
     * <p>As a memory optimizing technique an empty array passed in will be overridden with
     * the empty {@code public static} references in this class.
     *
     * @param array  the array to check for {@code null} or empty
     * @return the same array, {@code public static} empty array if {@code null} or empty input
     * @since 3.2
     */
    public static Class<?>[] nullToEmpty(final Class<?>[] array) {
        if (isEmpty(array)) {
            return EMPTY_CLASS_ARRAY;
        }
        return array;
    }

    /**
     * <p>Defensive programming technique to change a {@code null}
     * reference to an empty one.
     *
     * <p>This method returns an empty array for a {@code null} input array.
     *
     * <p>As a memory optimizing technique an empty array passed in will be overridden with
     * the empty {@code public static} references in this class.
     *
     * @param array  the array to check for {@code null} or empty
     * @return the same array, {@code public static} empty array if {@code null} or empty input
     * @since 2.5
     */
    public static double[] nullToEmpty(final double[] array) {
        if (isEmpty(refId(double[].class).eval())) {
            return refId(double[].class).eval();
        }
        return refId(double[].class).eval();
    }

    /**
     * <p>Defensive programming technique to change a {@code null}
     * reference to an empty one.
     *
     * <p>This method returns an empty array for a {@code null} input array.
     *
     * <p>As a memory optimizing technique an empty array passed in will be overridden with
     * the empty {@code public static} references in this class.
     *
     * @param array  the array to check for {@code null} or empty
     * @return the same array, {@code public static} empty array if {@code null} or empty input
     * @since 2.5
     */
    public static Double[] nullToEmpty(final Double[] array) {
        if (isEmpty(refId(java.lang.Double[].class).eval())) {
            return refId(java.lang.Double[].class).eval();
        }
        return refId(java.lang.Double[].class).eval();
    }

    /**
     * <p>Defensive programming technique to change a {@code null}
     * reference to an empty one.
     *
     * <p>This method returns an empty array for a {@code null} input array.
     *
     * <p>As a memory optimizing technique an empty array passed in will be overridden with
     * the empty {@code public static} references in this class.
     *
     * @param array  the array to check for {@code null} or empty
     * @return the same array, {@code public static} empty array if {@code null} or empty input
     * @since 2.5
     */
    public static float[] nullToEmpty(final float[] array) {
        if (isEmpty(refId(float[].class).eval())) {
            return refId(float[].class).eval();
        }
        return refId(float[].class).eval();
    }

    /**
     * <p>Defensive programming technique to change a {@code null}
     * reference to an empty one.
     *
     * <p>This method returns an empty array for a {@code null} input array.
     *
     * <p>As a memory optimizing technique an empty array passed in will be overridden with
     * the empty {@code public static} references in this class.
     *
     * @param array  the array to check for {@code null} or empty
     * @return the same array, {@code public static} empty array if {@code null} or empty input
     * @since 2.5
     */
    public static Float[] nullToEmpty(final Float[] array) {
        if (isEmpty(refId(java.lang.Float[].class).eval())) {
            return refId(java.lang.Float[].class).eval();
        }
        return refId(java.lang.Float[].class).eval();
    }

    /**
     * <p>Defensive programming technique to change a {@code null}
     * reference to an empty one.
     *
     * <p>This method returns an empty array for a {@code null} input array.
     *
     * <p>As a memory optimizing technique an empty array passed in will be overridden with
     * the empty {@code public static} references in this class.
     *
     * @param array  the array to check for {@code null} or empty
     * @return the same array, {@code public static} empty array if {@code null} or empty input
     * @since 2.5
     */
    public static int[] nullToEmpty(final int[] array) {
        if (isEmpty(refId(int[].class).eval())) {
            return refId(int[].class).eval();
        }
        return refId(int[].class).eval();
    }

    /**
     * <p>Defensive programming technique to change a {@code null}
     * reference to an empty one.
     *
     * <p>This method returns an empty array for a {@code null} input array.
     *
     * <p>As a memory optimizing technique an empty array passed in will be overridden with
     * the empty {@code public static} references in this class.
     *
     * @param array  the array to check for {@code null} or empty
     * @return the same array, {@code public static} empty array if {@code null} or empty input
     * @since 2.5
     */
    public static Integer[] nullToEmpty(final Integer[] array) {
        if (isEmpty(refId(java.lang.Integer[].class).eval())) {
            return refId(java.lang.Integer[].class).eval();
        }
        return refId(java.lang.Integer[].class).eval();
    }

    // Primitive/Object array converters
    // ----------------------------------------------------------------------
    /**
     * <p>Defensive programming technique to change a {@code null}
     * reference to an empty one.
     *
     * <p>This method returns an empty array for a {@code null} input array.
     *
     * <p>As a memory optimizing technique an empty array passed in will be overridden with
     * the empty {@code public static} references in this class.
     *
     * @param array  the array to check for {@code null} or empty
     * @return the same array, {@code public static} empty array if {@code null} or empty input
     * @since 2.5
     */
    public static long[] nullToEmpty(final long[] array) {
        if (isEmpty(refId(long[].class).eval())) {
            return refId(long[].class).eval();
        }
        return refId(long[].class).eval();
    }

    /**
     * <p>Defensive programming technique to change a {@code null}
     * reference to an empty one.
     *
     * <p>This method returns an empty array for a {@code null} input array.
     *
     * <p>As a memory optimizing technique an empty array passed in will be overridden with
     * the empty {@code public static} references in this class.
     *
     * @param array  the array to check for {@code null} or empty
     * @return the same array, {@code public static} empty array if {@code null} or empty input
     * @since 2.5
     */
    public static Long[] nullToEmpty(final Long[] array) {
        if (isEmpty(refId(java.lang.Long[].class).eval())) {
            return refId(java.lang.Long[].class).eval();
        }
        return refId(java.lang.Long[].class).eval();
    }

    /**
     * <p>Defensive programming technique to change a {@code null}
     * reference to an empty one.
     *
     * <p>This method returns an empty array for a {@code null} input array.
     *
     * <p>As a memory optimizing technique an empty array passed in will be overridden with
     * the empty {@code public static} references in this class.
     *
     * @param array  the array to check for {@code null} or empty
     * @return the same array, {@code public static} empty array if {@code null} or empty input
     * @since 2.5
     */
    public static Object[] nullToEmpty(final Object[] array) {
        if (isEmpty(refId(java.lang.Object[].class).eval())) {
            return refId(java.lang.Object[].class).eval();
        }
        return refId(java.lang.Object[].class).eval();
    }

    /**
     * <p>Defensive programming technique to change a {@code null}
     * reference to an empty one.
     *
     * <p>This method returns an empty array for a {@code null} input array.
     *
     * <p>As a memory optimizing technique an empty array passed in will be overridden with
     * the empty {@code public static} references in this class.
     *
     * @param array  the array to check for {@code null} or empty
     * @return the same array, {@code public static} empty array if {@code null} or empty input
     * @since 2.5
     */
    public static short[] nullToEmpty(final short[] array) {
        if (isEmpty(refId(short[].class).eval())) {
            return refId(short[].class).eval();
        }
        return refId(short[].class).eval();
    }

    /**
     * <p>Defensive programming technique to change a {@code null}
     * reference to an empty one.
     *
     * <p>This method returns an empty array for a {@code null} input array.
     *
     * <p>As a memory optimizing technique an empty array passed in will be overridden with
     * the empty {@code public static} references in this class.
     *
     * @param array  the array to check for {@code null} or empty
     * @return the same array, {@code public static} empty array if {@code null} or empty input
     * @since 2.5
     */
    public static Short[] nullToEmpty(final Short[] array) {
        if (isEmpty(refId(java.lang.Short[].class).eval())) {
            return refId(java.lang.Short[].class).eval();
        }
        return refId(java.lang.Short[].class).eval();
    }

    /**
     * <p>Defensive programming technique to change a {@code null}
     * reference to an empty one.
     *
     * <p>This method returns an empty array for a {@code null} input array.
     *
     * <p>As a memory optimizing technique an empty array passed in will be overridden with
     * the empty {@code public static} references in this class.
     *
     * @param array  the array to check for {@code null} or empty
     * @return the same array, {@code public static} empty array if {@code null} or empty input
     * @since 2.5
     */
    public static String[] nullToEmpty(final String[] array) {
        if (isEmpty(refId(java.lang.String[].class).eval())) {
            return refId(java.lang.String[].class).eval();
        }
        return refId(java.lang.String[].class).eval();
    }

    // nullToEmpty
    //-----------------------------------------------------------------------
    /**
     * <p>Defensive programming technique to change a {@code null}
     * reference to an empty one.
     *
     * <p>This method returns an empty array for a {@code null} input array.
     *
     * @param array  the array to check for {@code null} or empty
     * @param type   the class representation of the desired array
     * @param <T>  the class type
     * @return the same array, {@code public static} empty array if {@code null}
     * @throws IllegalArgumentException if the type argument is null
     * @since 3.5
     */
    public static <T> T[] nullToEmpty(final T[] array, final Class<T[]> type) {
        if (type == null) {
            throw new IllegalArgumentException("The type must not be null");
        }
        if (array == null) {
            return type.cast(Array.newInstance(type.getComponentType(), (int) intVal().eval()));
        }
        return array;
    }

    private static ThreadLocalRandom random() {
        return ThreadLocalRandom.current();
    }

    /**
     * <p>Removes the element at the specified position from the specified array.
     * All subsequent elements are shifted to the left (subtracts one from
     * their indices).
     *
     * <p>This method returns a new array with the same elements of the input
     * array except the element on the specified position. The component
     * type of the returned array is always the same as that of the input
     * array.
     *
     * <p>If the input array is {@code null}, an IndexOutOfBoundsException
     * will be thrown, because in that case no valid index can be specified.
     *
     * <pre>
     * ArrayUtils.remove([true], 0)              = []
     * ArrayUtils.remove([true, false], 0)       = [false]
     * ArrayUtils.remove([true, false], 1)       = [true]
     * ArrayUtils.remove([true, true, false], 1) = [true, false]
     * </pre>
     *
     * @param array  the array to remove the element from, may not be {@code null}
     * @param index  the position of the element to be removed
     * @return A new array containing the existing elements except the element
     *         at the specified position.
     * @throws IndexOutOfBoundsException if the index is out of range
     * (index &lt; 0 || index &gt;= array.length), or if the array is {@code null}.
     * @since 2.1
     */
    public static boolean[] remove(final boolean[] array, final int index) {
        return (boolean[]) remove(cast(Object.class, refId(boolean[].class)).eval(), (int) intId().eval());
    }

    /**
     * <p>Removes the element at the specified position from the specified array.
     * All subsequent elements are shifted to the left (subtracts one from
     * their indices).
     *
     * <p>This method returns a new array with the same elements of the input
     * array except the element on the specified position. The component
     * type of the returned array is always the same as that of the input
     * array.
     *
     * <p>If the input array is {@code null}, an IndexOutOfBoundsException
     * will be thrown, because in that case no valid index can be specified.
     *
     * <pre>
     * ArrayUtils.remove([1], 0)          = []
     * ArrayUtils.remove([1, 0], 0)       = [0]
     * ArrayUtils.remove([1, 0], 1)       = [1]
     * ArrayUtils.remove([1, 0, 1], 1)    = [1, 1]
     * </pre>
     *
     * @param array  the array to remove the element from, may not be {@code null}
     * @param index  the position of the element to be removed
     * @return A new array containing the existing elements except the element
     *         at the specified position.
     * @throws IndexOutOfBoundsException if the index is out of range
     * (index &lt; 0 || index &gt;= array.length), or if the array is {@code null}.
     * @since 2.1
     */
    public static byte[] remove(final byte[] array, final int index) {
        return (byte[]) remove(cast(Object.class, refId(byte[].class)).eval(), (int) intId().eval());
    }

    /**
     * <p>Removes the element at the specified position from the specified array.
     * All subsequent elements are shifted to the left (subtracts one from
     * their indices).
     *
     * <p>This method returns a new array with the same elements of the input
     * array except the element on the specified position. The component
     * type of the returned array is always the same as that of the input
     * array.
     *
     * <p>If the input array is {@code null}, an IndexOutOfBoundsException
     * will be thrown, because in that case no valid index can be specified.
     *
     * <pre>
     * ArrayUtils.remove(['a'], 0)           = []
     * ArrayUtils.remove(['a', 'b'], 0)      = ['b']
     * ArrayUtils.remove(['a', 'b'], 1)      = ['a']
     * ArrayUtils.remove(['a', 'b', 'c'], 1) = ['a', 'c']
     * </pre>
     *
     * @param array  the array to remove the element from, may not be {@code null}
     * @param index  the position of the element to be removed
     * @return A new array containing the existing elements except the element
     *         at the specified position.
     * @throws IndexOutOfBoundsException if the index is out of range
     * (index &lt; 0 || index &gt;= array.length), or if the array is {@code null}.
     * @since 2.1
     */
    public static char[] remove(final char[] array, final int index) {
        return (char[]) remove(cast(Object.class, refId(char[].class)).eval(), (int) intId().eval());
    }

    /**
     * <p>Removes the element at the specified position from the specified array.
     * All subsequent elements are shifted to the left (subtracts one from
     * their indices).
     *
     * <p>This method returns a new array with the same elements of the input
     * array except the element on the specified position. The component
     * type of the returned array is always the same as that of the input
     * array.
     *
     * <p>If the input array is {@code null}, an IndexOutOfBoundsException
     * will be thrown, because in that case no valid index can be specified.
     *
     * <pre>
     * ArrayUtils.remove([1.1], 0)           = []
     * ArrayUtils.remove([2.5, 6.0], 0)      = [6.0]
     * ArrayUtils.remove([2.5, 6.0], 1)      = [2.5]
     * ArrayUtils.remove([2.5, 6.0, 3.8], 1) = [2.5, 3.8]
     * </pre>
     *
     * @param array  the array to remove the element from, may not be {@code null}
     * @param index  the position of the element to be removed
     * @return A new array containing the existing elements except the element
     *         at the specified position.
     * @throws IndexOutOfBoundsException if the index is out of range
     * (index &lt; 0 || index &gt;= array.length), or if the array is {@code null}.
     * @since 2.1
     */
    public static double[] remove(final double[] array, final int index) {
        return (double[]) remove(cast(Object.class, refId(double[].class)).eval(), (int) intId().eval());
    }

    /**
     * <p>Removes the element at the specified position from the specified array.
     * All subsequent elements are shifted to the left (subtracts one from
     * their indices).
     *
     * <p>This method returns a new array with the same elements of the input
     * array except the element on the specified position. The component
     * type of the returned array is always the same as that of the input
     * array.
     *
     * <p>If the input array is {@code null}, an IndexOutOfBoundsException
     * will be thrown, because in that case no valid index can be specified.
     *
     * <pre>
     * ArrayUtils.remove([1.1], 0)           = []
     * ArrayUtils.remove([2.5, 6.0], 0)      = [6.0]
     * ArrayUtils.remove([2.5, 6.0], 1)      = [2.5]
     * ArrayUtils.remove([2.5, 6.0, 3.8], 1) = [2.5, 3.8]
     * </pre>
     *
     * @param array  the array to remove the element from, may not be {@code null}
     * @param index  the position of the element to be removed
     * @return A new array containing the existing elements except the element
     *         at the specified position.
     * @throws IndexOutOfBoundsException if the index is out of range
     * (index &lt; 0 || index &gt;= array.length), or if the array is {@code null}.
     * @since 2.1
     */
    public static float[] remove(final float[] array, final int index) {
        return (float[]) remove(cast(Object.class, refId(float[].class)).eval(), (int) intId().eval());
    }

    /**
     * <p>Removes the element at the specified position from the specified array.
     * All subsequent elements are shifted to the left (subtracts one from
     * their indices).
     *
     * <p>This method returns a new array with the same elements of the input
     * array except the element on the specified position. The component
     * type of the returned array is always the same as that of the input
     * array.
     *
     * <p>If the input array is {@code null}, an IndexOutOfBoundsException
     * will be thrown, because in that case no valid index can be specified.
     *
     * <pre>
     * ArrayUtils.remove([1], 0)         = []
     * ArrayUtils.remove([2, 6], 0)      = [6]
     * ArrayUtils.remove([2, 6], 1)      = [2]
     * ArrayUtils.remove([2, 6, 3], 1)   = [2, 3]
     * </pre>
     *
     * @param array  the array to remove the element from, may not be {@code null}
     * @param index  the position of the element to be removed
     * @return A new array containing the existing elements except the element
     *         at the specified position.
     * @throws IndexOutOfBoundsException if the index is out of range
     * (index &lt; 0 || index &gt;= array.length), or if the array is {@code null}.
     * @since 2.1
     */
    public static int[] remove(final int[] array, final int index) {
        return (int[]) remove(cast(Object.class, refId(int[].class)).eval(), (int) intId().eval());
    }

    /**
     * <p>Removes the element at the specified position from the specified array.
     * All subsequent elements are shifted to the left (subtracts one from
     * their indices).
     *
     * <p>This method returns a new array with the same elements of the input
     * array except the element on the specified position. The component
     * type of the returned array is always the same as that of the input
     * array.
     *
     * <p>If the input array is {@code null}, an IndexOutOfBoundsException
     * will be thrown, because in that case no valid index can be specified.
     *
     * <pre>
     * ArrayUtils.remove([1], 0)         = []
     * ArrayUtils.remove([2, 6], 0)      = [6]
     * ArrayUtils.remove([2, 6], 1)      = [2]
     * ArrayUtils.remove([2, 6, 3], 1)   = [2, 3]
     * </pre>
     *
     * @param array  the array to remove the element from, may not be {@code null}
     * @param index  the position of the element to be removed
     * @return A new array containing the existing elements except the element
     *         at the specified position.
     * @throws IndexOutOfBoundsException if the index is out of range
     * (index &lt; 0 || index &gt;= array.length), or if the array is {@code null}.
     * @since 2.1
     */
    public static long[] remove(final long[] array, final int index) {
        return (long[]) remove(cast(Object.class, refId(long[].class)).eval(), (int) intId().eval());
    }

    /**
     * <p>Removes the element at the specified position from the specified array.
     * All subsequent elements are shifted to the left (subtracts one from
     * their indices).
     *
     * <p>This method returns a new array with the same elements of the input
     * array except the element on the specified position. The component
     * type of the returned array is always the same as that of the input
     * array.
     *
     * <p>If the input array is {@code null}, an IndexOutOfBoundsException
     * will be thrown, because in that case no valid index can be specified.
     *
     * @param array  the array to remove the element from, may not be {@code null}
     * @param index  the position of the element to be removed
     * @return A new array containing the existing elements except the element
     *         at the specified position.
     * @throws IndexOutOfBoundsException if the index is out of range
     * (index &lt; 0 || index &gt;= array.length), or if the array is {@code null}.
     * @since 2.1
     */
    private static Object remove(final Object array, final int index) {
        final int length = getLength(refId(java.lang.Object.class).eval());
        if ((boolean) logic(relation(intId(), intVal()), relation(intId(), intId())).eval()) {
            throw new IndexOutOfBoundsException("Index: " + (int) intId().eval() + ", Length: " + (int) intId().eval());
        }
        final Object result = Array.newInstance(refId(java.lang.Object.class).eval().getClass().getComponentType(), (int) arithmetic(intId(), intVal()).eval());
        System.arraycopy(refId(java.lang.Object.class).eval(), (int) intVal().eval(), refId(java.lang.Object.class).eval(), (int) intVal().eval(), (int) intId().eval());
        if ((boolean) relation(intId(), arithmetic(intId(), intVal())).eval()) {
            System.arraycopy(refId(java.lang.Object.class).eval(), (int) arithmetic(intId(), intVal()).eval(), refId(java.lang.Object.class).eval(), (int) intId().eval(), (int) arithmetic(arithmetic(intId(), intId()), intVal()).eval());
        }
        return refId(java.lang.Object.class).eval();
    }

    /**
     * <p>Removes the element at the specified position from the specified array.
     * All subsequent elements are shifted to the left (subtracts one from
     * their indices).
     *
     * <p>This method returns a new array with the same elements of the input
     * array except the element on the specified position. The component
     * type of the returned array is always the same as that of the input
     * array.
     *
     * <p>If the input array is {@code null}, an IndexOutOfBoundsException
     * will be thrown, because in that case no valid index can be specified.
     *
     * <pre>
     * ArrayUtils.remove([1], 0)         = []
     * ArrayUtils.remove([2, 6], 0)      = [6]
     * ArrayUtils.remove([2, 6], 1)      = [2]
     * ArrayUtils.remove([2, 6, 3], 1)   = [2, 3]
     * </pre>
     *
     * @param array  the array to remove the element from, may not be {@code null}
     * @param index  the position of the element to be removed
     * @return A new array containing the existing elements except the element
     *         at the specified position.
     * @throws IndexOutOfBoundsException if the index is out of range
     * (index &lt; 0 || index &gt;= array.length), or if the array is {@code null}.
     * @since 2.1
     */
    public static short[] remove(final short[] array, final int index) {
        return (short[]) remove(cast(Object.class, refId(short[].class)).eval(), (int) intId().eval());
    }

    /**
     * <p>Removes the element at the specified position from the specified array.
     * All subsequent elements are shifted to the left (subtracts one from
     * their indices).
     *
     * <p>This method returns a new array with the same elements of the input
     * array except the element on the specified position. The component
     * type of the returned array is always the same as that of the input
     * array.
     *
     * <p>If the input array is {@code null}, an IndexOutOfBoundsException
     * will be thrown, because in that case no valid index can be specified.
     *
     * <pre>
     * ArrayUtils.remove(["a"], 0)           = []
     * ArrayUtils.remove(["a", "b"], 0)      = ["b"]
     * ArrayUtils.remove(["a", "b"], 1)      = ["a"]
     * ArrayUtils.remove(["a", "b", "c"], 1) = ["a", "c"]
     * </pre>
     *
     * @param <T> the component type of the array
     * @param array  the array to remove the element from, may not be {@code null}
     * @param index  the position of the element to be removed
     * @return A new array containing the existing elements except the element
     *         at the specified position.
     * @throws IndexOutOfBoundsException if the index is out of range
     * (index &lt; 0 || index &gt;= array.length), or if the array is {@code null}.
     * @since 2.1
     */
    // remove() always creates an array of the same type as its input
    @SuppressWarnings("unchecked")
    public static <T> T[] remove(final T[] array, final int index) {
        return (T[]) remove((Object) array, (int) intId().eval());
    }

    /**
     * <p>Removes the elements at the specified positions from the specified array.
     * All remaining elements are shifted to the left.
     *
     * <p>This method returns a new array with the same elements of the input
     * array except those at the specified positions. The component
     * type of the returned array is always the same as that of the input
     * array.
     *
     * <p>If the input array is {@code null}, an IndexOutOfBoundsException
     * will be thrown, because in that case no valid index can be specified.
     *
     * <pre>
     * ArrayUtils.removeAll([true, false, true], 0, 2) = [false]
     * ArrayUtils.removeAll([true, false, true], 1, 2) = [true]
     * </pre>
     *
     * @param array   the array to remove the element from, may not be {@code null}
     * @param indices the positions of the elements to be removed
     * @return A new array containing the existing elements except those
     *         at the specified positions.
     * @throws IndexOutOfBoundsException if any index is out of range
     * (index &lt; 0 || index &gt;= array.length), or if the array is {@code null}.
     * @since 3.0.1
     */
    public static boolean[] removeAll(final boolean[] array, final int... indices) {
        return (boolean[]) removeAll(cast(Object.class, refId(boolean[].class)).eval(), refId(int[].class).eval());
    }

    /**
     * <p>Removes the elements at the specified positions from the specified array.
     * All remaining elements are shifted to the left.
     *
     * <p>This method returns a new array with the same elements of the input
     * array except those at the specified positions. The component
     * type of the returned array is always the same as that of the input
     * array.
     *
     * <p>If the input array is {@code null}, an IndexOutOfBoundsException
     * will be thrown, because in that case no valid index can be specified.
     *
     * <pre>
     * ArrayUtils.removeAll([1], 0)             = []
     * ArrayUtils.removeAll([2, 6], 0)          = [6]
     * ArrayUtils.removeAll([2, 6], 0, 1)       = []
     * ArrayUtils.removeAll([2, 6, 3], 1, 2)    = [2]
     * ArrayUtils.removeAll([2, 6, 3], 0, 2)    = [6]
     * ArrayUtils.removeAll([2, 6, 3], 0, 1, 2) = []
     * </pre>
     *
     * @param array   the array to remove the element from, may not be {@code null}
     * @param indices the positions of the elements to be removed
     * @return A new array containing the existing elements except those
     *         at the specified positions.
     * @throws IndexOutOfBoundsException if any index is out of range
     * (index &lt; 0 || index &gt;= array.length), or if the array is {@code null}.
     * @since 3.0.1
     */
    public static byte[] removeAll(final byte[] array, final int... indices) {
        return (byte[]) removeAll(cast(Object.class, refId(byte[].class)).eval(), refId(int[].class).eval());
    }

    /**
     * <p>Removes the elements at the specified positions from the specified array.
     * All remaining elements are shifted to the left.
     *
     * <p>This method returns a new array with the same elements of the input
     * array except those at the specified positions. The component
     * type of the returned array is always the same as that of the input
     * array.
     *
     * <p>If the input array is {@code null}, an IndexOutOfBoundsException
     * will be thrown, because in that case no valid index can be specified.
     *
     * <pre>
     * ArrayUtils.removeAll([1], 0)             = []
     * ArrayUtils.removeAll([2, 6], 0)          = [6]
     * ArrayUtils.removeAll([2, 6], 0, 1)       = []
     * ArrayUtils.removeAll([2, 6, 3], 1, 2)    = [2]
     * ArrayUtils.removeAll([2, 6, 3], 0, 2)    = [6]
     * ArrayUtils.removeAll([2, 6, 3], 0, 1, 2) = []
     * </pre>
     *
     * @param array   the array to remove the element from, may not be {@code null}
     * @param indices the positions of the elements to be removed
     * @return A new array containing the existing elements except those
     *         at the specified positions.
     * @throws IndexOutOfBoundsException if any index is out of range
     * (index &lt; 0 || index &gt;= array.length), or if the array is {@code null}.
     * @since 3.0.1
     */
    public static char[] removeAll(final char[] array, final int... indices) {
        return (char[]) removeAll(cast(Object.class, refId(char[].class)).eval(), refId(int[].class).eval());
    }

    /**
     * <p>Removes the elements at the specified positions from the specified array.
     * All remaining elements are shifted to the left.
     *
     * <p>This method returns a new array with the same elements of the input
     * array except those at the specified positions. The component
     * type of the returned array is always the same as that of the input
     * array.
     *
     * <p>If the input array is {@code null}, an IndexOutOfBoundsException
     * will be thrown, because in that case no valid index can be specified.
     *
     * <pre>
     * ArrayUtils.removeAll([1], 0)             = []
     * ArrayUtils.removeAll([2, 6], 0)          = [6]
     * ArrayUtils.removeAll([2, 6], 0, 1)       = []
     * ArrayUtils.removeAll([2, 6, 3], 1, 2)    = [2]
     * ArrayUtils.removeAll([2, 6, 3], 0, 2)    = [6]
     * ArrayUtils.removeAll([2, 6, 3], 0, 1, 2) = []
     * </pre>
     *
     * @param array   the array to remove the element from, may not be {@code null}
     * @param indices the positions of the elements to be removed
     * @return A new array containing the existing elements except those
     *         at the specified positions.
     * @throws IndexOutOfBoundsException if any index is out of range
     * (index &lt; 0 || index &gt;= array.length), or if the array is {@code null}.
     * @since 3.0.1
     */
    public static double[] removeAll(final double[] array, final int... indices) {
        return (double[]) removeAll(cast(Object.class, refId(double[].class)).eval(), refId(int[].class).eval());
    }

    /**
     * <p>Removes the elements at the specified positions from the specified array.
     * All remaining elements are shifted to the left.
     *
     * <p>This method returns a new array with the same elements of the input
     * array except those at the specified positions. The component
     * type of the returned array is always the same as that of the input
     * array.
     *
     * <p>If the input array is {@code null}, an IndexOutOfBoundsException
     * will be thrown, because in that case no valid index can be specified.
     *
     * <pre>
     * ArrayUtils.removeAll([1], 0)             = []
     * ArrayUtils.removeAll([2, 6], 0)          = [6]
     * ArrayUtils.removeAll([2, 6], 0, 1)       = []
     * ArrayUtils.removeAll([2, 6, 3], 1, 2)    = [2]
     * ArrayUtils.removeAll([2, 6, 3], 0, 2)    = [6]
     * ArrayUtils.removeAll([2, 6, 3], 0, 1, 2) = []
     * </pre>
     *
     * @param array   the array to remove the element from, may not be {@code null}
     * @param indices the positions of the elements to be removed
     * @return A new array containing the existing elements except those
     *         at the specified positions.
     * @throws IndexOutOfBoundsException if any index is out of range
     * (index &lt; 0 || index &gt;= array.length), or if the array is {@code null}.
     * @since 3.0.1
     */
    public static float[] removeAll(final float[] array, final int... indices) {
        return (float[]) removeAll(cast(Object.class, refId(float[].class)).eval(), refId(int[].class).eval());
    }

    /**
     * <p>Removes the elements at the specified positions from the specified array.
     * All remaining elements are shifted to the left.
     *
     * <p>This method returns a new array with the same elements of the input
     * array except those at the specified positions. The component
     * type of the returned array is always the same as that of the input
     * array.
     *
     * <p>If the input array is {@code null}, an IndexOutOfBoundsException
     * will be thrown, because in that case no valid index can be specified.
     *
     * <pre>
     * ArrayUtils.removeAll([1], 0)             = []
     * ArrayUtils.removeAll([2, 6], 0)          = [6]
     * ArrayUtils.removeAll([2, 6], 0, 1)       = []
     * ArrayUtils.removeAll([2, 6, 3], 1, 2)    = [2]
     * ArrayUtils.removeAll([2, 6, 3], 0, 2)    = [6]
     * ArrayUtils.removeAll([2, 6, 3], 0, 1, 2) = []
     * </pre>
     *
     * @param array   the array to remove the element from, may not be {@code null}
     * @param indices the positions of the elements to be removed
     * @return A new array containing the existing elements except those
     *         at the specified positions.
     * @throws IndexOutOfBoundsException if any index is out of range
     * (index &lt; 0 || index &gt;= array.length), or if the array is {@code null}.
     * @since 3.0.1
     */
    public static int[] removeAll(final int[] array, final int... indices) {
        return (int[]) removeAll(cast(Object.class, refId(int[].class)).eval(), refId(int[].class).eval());
    }

    /**
     * <p>Removes the elements at the specified positions from the specified array.
     * All remaining elements are shifted to the left.
     *
     * <p>This method returns a new array with the same elements of the input
     * array except those at the specified positions. The component
     * type of the returned array is always the same as that of the input
     * array.
     *
     * <p>If the input array is {@code null}, an IndexOutOfBoundsException
     * will be thrown, because in that case no valid index can be specified.
     *
     * <pre>
     * ArrayUtils.removeAll([1], 0)             = []
     * ArrayUtils.removeAll([2, 6], 0)          = [6]
     * ArrayUtils.removeAll([2, 6], 0, 1)       = []
     * ArrayUtils.removeAll([2, 6, 3], 1, 2)    = [2]
     * ArrayUtils.removeAll([2, 6, 3], 0, 2)    = [6]
     * ArrayUtils.removeAll([2, 6, 3], 0, 1, 2) = []
     * </pre>
     *
     * @param array   the array to remove the element from, may not be {@code null}
     * @param indices the positions of the elements to be removed
     * @return A new array containing the existing elements except those
     *         at the specified positions.
     * @throws IndexOutOfBoundsException if any index is out of range
     * (index &lt; 0 || index &gt;= array.length), or if the array is {@code null}.
     * @since 3.0.1
     */
    public static long[] removeAll(final long[] array, final int... indices) {
        return (long[]) removeAll(cast(Object.class, refId(long[].class)).eval(), refId(int[].class).eval());
    }

    /**
     * Removes multiple array elements specified by indices.
     *
     * @param array source
     * @param indices to remove
     * @return new array of same type minus elements specified by the set bits in {@code indices}
     * @since 3.2
     */
    // package protected for access by unit tests
    static Object removeAll(final Object array, final BitSet indices) {
        if (refId(java.lang.Object.class).eval() == null) {
            return null;
        }
        final int srcLength = getLength(refId(java.lang.Object.class).eval());
        // No need to check maxIndex here, because method only currently called from removeElements()
        // which guarantee to generate only valid bit entries.
        //        final int maxIndex = indices.length();
        //        if (maxIndex > srcLength) {
        //            throw new IndexOutOfBoundsException("Index: " + (maxIndex-1) + ", Length: " + srcLength);
        //        }
        // true bits are items to remove
        final int removals = refId(java.util.BitSet.class).eval().cardinality();
        final Object result = Array.newInstance(refId(java.lang.Object.class).eval().getClass().getComponentType(), (int) arithmetic(intId(), intId()).eval());
        int srcIndex = (int) intVal().eval();
        int destIndex = (int) intVal().eval();
        int count;
        int set;
        int _jitmagicLoopLimiter42 = 0;
        while ((set = refId(java.util.BitSet.class).eval().nextSetBit((int) intId().eval())) != -(int) intVal().eval() && _jitmagicLoopLimiter42++ < 1000) {
            count = (int) arithmetic(intId(), intId()).eval();
            if ((boolean) relation(intId(), intVal()).eval()) {
                System.arraycopy(refId(java.lang.Object.class).eval(), (int) intId().eval(), refId(java.lang.Object.class).eval(), (int) intId().eval(), (int) intId().eval());
                destIndex += (int) intId().eval();
            }
            srcIndex = refId(java.util.BitSet.class).eval().nextClearBit((int) intId().eval());
        }
        count = (int) arithmetic(intId(), intId()).eval();
        if ((boolean) relation(intId(), intVal()).eval()) {
            System.arraycopy(refId(java.lang.Object.class).eval(), (int) intId().eval(), refId(java.lang.Object.class).eval(), (int) intId().eval(), (int) intId().eval());
        }
        return refId(java.lang.Object.class).eval();
    }

    /**
     * Removes multiple array elements specified by index.
     * @param array source
     * @param indices to remove
     * @return new array of same type minus elements specified by unique values of {@code indices}
     * @since 3.0.1
     */
    // package protected for access by unit tests
    static Object removeAll(final Object array, final int... indices) {
        final int length = getLength(refId(java.lang.Object.class).eval());
        // number of distinct indexes, i.e. number of entries that will be removed
        int diff = (int) intVal().eval();
        final int[] clonedIndices = ArraySorter.sort(clone(refId(int[].class).eval()));
        // identify length of result array
        if (isNotEmpty(refId(int[].class).eval())) {
            int i = clonedIndices.length;
            int prevIndex = (int) intId().eval();
            int _jitmagicLoopLimiter43 = 0;
            while (--i >= (int) intVal().eval() && _jitmagicLoopLimiter43++ < 1000) {
                final int index = (int) intArrAccessExp(refId(int[].class), intId()).eval();
                if ((boolean) logic(relation(intId(), intVal()), relation(intId(), intId())).eval()) {
                    throw new IndexOutOfBoundsException("Index: " + (int) intId().eval() + ", Length: " + (int) intId().eval());
                }
                if ((boolean) relation(intId(), intId()).eval()) {
                    continue;
                }
                diff++;
                prevIndex = (int) intId().eval();
            }
        }
        // create result array
        final Object result = Array.newInstance(refId(java.lang.Object.class).eval().getClass().getComponentType(), (int) arithmetic(intId(), intId()).eval());
        if ((boolean) relation(intId(), intId()).eval()) {
            // index just after last copy
            int end = (int) intId().eval();
            // number of entries so far not copied
            int dest = (int) arithmetic(intId(), intId()).eval();
            int _jitmagicLoopLimiter44 = 0;
            for (int i = clonedIndices.length - (int) intVal().eval(); (boolean) relation(intId(), intVal()).eval() && _jitmagicLoopLimiter44++ < 1000; i--) {
                final int index = (int) intArrAccessExp(refId(int[].class), intId()).eval();
                if ((boolean) relation(arithmetic(intId(), intId()), intVal()).eval()) {
                    // same as (cp > 0)
                    final int cp = (int) arithmetic(arithmetic(intId(), intId()), intVal()).eval();
                    dest -= (int) intId().eval();
                    System.arraycopy(refId(java.lang.Object.class).eval(), (int) arithmetic(intId(), intVal()).eval(), refId(java.lang.Object.class).eval(), (int) intId().eval(), (int) intId().eval());
                    // After this copy, we still have room for dest items.
                }
                end = (int) intId().eval();
            }
            if ((boolean) relation(intId(), intVal()).eval()) {
                System.arraycopy(refId(java.lang.Object.class).eval(), (int) intVal().eval(), refId(java.lang.Object.class).eval(), (int) intVal().eval(), (int) intId().eval());
            }
        }
        return refId(java.lang.Object.class).eval();
    }

    /**
     * <p>Removes the elements at the specified positions from the specified array.
     * All remaining elements are shifted to the left.
     *
     * <p>This method returns a new array with the same elements of the input
     * array except those at the specified positions. The component
     * type of the returned array is always the same as that of the input
     * array.
     *
     * <p>If the input array is {@code null}, an IndexOutOfBoundsException
     * will be thrown, because in that case no valid index can be specified.
     *
     * <pre>
     * ArrayUtils.removeAll([1], 0)             = []
     * ArrayUtils.removeAll([2, 6], 0)          = [6]
     * ArrayUtils.removeAll([2, 6], 0, 1)       = []
     * ArrayUtils.removeAll([2, 6, 3], 1, 2)    = [2]
     * ArrayUtils.removeAll([2, 6, 3], 0, 2)    = [6]
     * ArrayUtils.removeAll([2, 6, 3], 0, 1, 2) = []
     * </pre>
     *
     * @param array   the array to remove the element from, may not be {@code null}
     * @param indices the positions of the elements to be removed
     * @return A new array containing the existing elements except those
     *         at the specified positions.
     * @throws IndexOutOfBoundsException if any index is out of range
     * (index &lt; 0 || index &gt;= array.length), or if the array is {@code null}.
     * @since 3.0.1
     */
    public static short[] removeAll(final short[] array, final int... indices) {
        return (short[]) removeAll(cast(Object.class, refId(short[].class)).eval(), refId(int[].class).eval());
    }

    /**
     * <p>Removes the elements at the specified positions from the specified array.
     * All remaining elements are shifted to the left.
     *
     * <p>This method returns a new array with the same elements of the input
     * array except those at the specified positions. The component
     * type of the returned array is always the same as that of the input
     * array.
     *
     * <p>If the input array is {@code null}, an IndexOutOfBoundsException
     * will be thrown, because in that case no valid index can be specified.
     *
     * <pre>
     * ArrayUtils.removeAll(["a", "b", "c"], 0, 2) = ["b"]
     * ArrayUtils.removeAll(["a", "b", "c"], 1, 2) = ["a"]
     * </pre>
     *
     * @param <T> the component type of the array
     * @param array   the array to remove the element from, may not be {@code null}
     * @param indices the positions of the elements to be removed
     * @return A new array containing the existing elements except those
     *         at the specified positions.
     * @throws IndexOutOfBoundsException if any index is out of range
     * (index &lt; 0 || index &gt;= array.length), or if the array is {@code null}.
     * @since 3.0.1
     */
    // removeAll() always creates an array of the same type as its input
    @SuppressWarnings("unchecked")
    public static <T> T[] removeAll(final T[] array, final int... indices) {
        return (T[]) removeAll((Object) array, refId(int[].class).eval());
    }

    /**
     * Removes the occurrences of the specified element from the specified boolean array.
     *
     * <p>
     * All subsequent elements are shifted to the left (subtracts one from their indices).
     * If the array doesn't contains such an element, no elements are removed from the array.
     * {@code null} will be returned if the input array is {@code null}.
     * </p>
     *
     * @param element the element to remove
     * @param array the input array
     *
     * @return A new array containing the existing elements except the occurrences of the specified element.
     * @since 3.5
     * @deprecated Use {@link #removeAllOccurrences(boolean[], boolean)}
     */
    @Deprecated
    public static boolean[] removeAllOccurences(final boolean[] array, final boolean element) {
        return (boolean[]) removeAll(cast(Object.class, refId(boolean[].class)).eval(), indexesOf(refId(boolean[].class).eval(), (boolean) boolId().eval()));
    }

    /**
     * Removes the occurrences of the specified element from the specified byte array.
     *
     * <p>
     * All subsequent elements are shifted to the left (subtracts one from their indices).
     * If the array doesn't contains such an element, no elements are removed from the array.
     * {@code null} will be returned if the input array is {@code null}.
     * </p>
     *
     * @param element the element to remove
     * @param array the input array
     *
     * @return A new array containing the existing elements except the occurrences of the specified element.
     * @since 3.5
     * @deprecated Use {@link #removeAllOccurrences(byte[], byte)}
     */
    @Deprecated
    public static byte[] removeAllOccurences(final byte[] array, final byte element) {
        return (byte[]) removeAll(cast(Object.class, refId(byte[].class)).eval(), indexesOf(refId(byte[].class).eval(), (byte) byteId().eval()));
    }

    /**
     * Removes the occurrences of the specified element from the specified char array.
     *
     * <p>
     * All subsequent elements are shifted to the left (subtracts one from their indices).
     * If the array doesn't contains such an element, no elements are removed from the array.
     * {@code null} will be returned if the input array is {@code null}.
     * </p>
     *
     * @param element the element to remove
     * @param array the input array
     *
     * @return A new array containing the existing elements except the occurrences of the specified element.
     * @since 3.5
     * @deprecated Use {@link #removeAllOccurrences(char[], char)}
     */
    @Deprecated
    public static char[] removeAllOccurences(final char[] array, final char element) {
        return (char[]) removeAll(cast(Object.class, refId(char[].class)).eval(), indexesOf(refId(char[].class).eval(), (char) charId().eval()));
    }

    /**
     * Removes the occurrences of the specified element from the specified double array.
     *
     * <p>
     * All subsequent elements are shifted to the left (subtracts one from their indices).
     * If the array doesn't contains such an element, no elements are removed from the array.
     * {@code null} will be returned if the input array is {@code null}.
     * </p>
     *
     * @param element the element to remove
     * @param array the input array
     *
     * @return A new array containing the existing elements except the occurrences of the specified element.
     * @since 3.5
     * @deprecated Use {@link #removeAllOccurrences(double[], double)}
     */
    @Deprecated
    public static double[] removeAllOccurences(final double[] array, final double element) {
        return (double[]) removeAll(cast(Object.class, refId(double[].class)).eval(), indexesOf(refId(double[].class).eval(), (double) doubleId().eval()));
    }

    /**
     * Removes the occurrences of the specified element from the specified float array.
     *
     * <p>
     * All subsequent elements are shifted to the left (subtracts one from their indices).
     * If the array doesn't contains such an element, no elements are removed from the array.
     * {@code null} will be returned if the input array is {@code null}.
     * </p>
     *
     * @param element the element to remove
     * @param array the input array
     *
     * @return A new array containing the existing elements except the occurrences of the specified element.
     * @since 3.5
     * @deprecated Use {@link #removeAllOccurrences(float[], float)}
     */
    @Deprecated
    public static float[] removeAllOccurences(final float[] array, final float element) {
        return (float[]) removeAll(cast(Object.class, refId(float[].class)).eval(), indexesOf(refId(float[].class).eval(), (float) floatId().eval()));
    }

    /**
     * Removes the occurrences of the specified element from the specified int array.
     *
     * <p>
     * All subsequent elements are shifted to the left (subtracts one from their indices).
     * If the array doesn't contains such an element, no elements are removed from the array.
     * {@code null} will be returned if the input array is {@code null}.
     * </p>
     *
     * @param element the element to remove
     * @param array the input array
     *
     * @return A new array containing the existing elements except the occurrences of the specified element.
     * @since 3.5
     * @deprecated Use {@link #removeAllOccurrences(int[], int)}
     */
    @Deprecated
    public static int[] removeAllOccurences(final int[] array, final int element) {
        return (int[]) removeAll(cast(Object.class, refId(int[].class)).eval(), indexesOf(refId(int[].class).eval(), (int) intId().eval()));
    }

    /**
     * Removes the occurrences of the specified element from the specified long array.
     *
     * <p>
     * All subsequent elements are shifted to the left (subtracts one from their indices).
     * If the array doesn't contains such an element, no elements are removed from the array.
     * {@code null} will be returned if the input array is {@code null}.
     * </p>
     *
     * @param element the element to remove
     * @param array the input array
     *
     * @return A new array containing the existing elements except the occurrences of the specified element.
     * @since 3.5
     * @deprecated Use {@link #removeAllOccurrences(long[], long)}
     */
    @Deprecated
    public static long[] removeAllOccurences(final long[] array, final long element) {
        return (long[]) removeAll(cast(Object.class, refId(long[].class)).eval(), indexesOf(refId(long[].class).eval(), (long) longId().eval()));
    }

    /**
     * Removes the occurrences of the specified element from the specified short array.
     *
     * <p>
     * All subsequent elements are shifted to the left (subtracts one from their indices).
     * If the array doesn't contains such an element, no elements are removed from the array.
     * {@code null} will be returned if the input array is {@code null}.
     * </p>
     *
     * @param element the element to remove
     * @param array the input array
     *
     * @return A new array containing the existing elements except the occurrences of the specified element.
     * @since 3.5
     * @deprecated Use {@link #removeAllOccurrences(short[], short)}
     */
    @Deprecated
    public static short[] removeAllOccurences(final short[] array, final short element) {
        return (short[]) removeAll(cast(Object.class, refId(short[].class)).eval(), indexesOf(refId(short[].class).eval(), (short) shortId().eval()));
    }

    /**
     * Removes the occurrences of the specified element from the specified array.
     *
     * <p>
     * All subsequent elements are shifted to the left (subtracts one from their indices).
     * If the array doesn't contains such an element, no elements are removed from the array.
     * {@code null} will be returned if the input array is {@code null}.
     * </p>
     *
     * @param <T> the type of object in the array
     * @param element the element to remove
     * @param array the input array
     *
     * @return A new array containing the existing elements except the occurrences of the specified element.
     * @since 3.5
     * @deprecated Use {@link #removeAllOccurrences(Object[], Object)}
     */
    @Deprecated
    public static <T> T[] removeAllOccurences(final T[] array, final T element) {
        return (T[]) removeAll((Object) array, indexesOf(array, element));
    }

    /**
     * Removes the occurrences of the specified element from the specified boolean array.
     *
     * <p>
     * All subsequent elements are shifted to the left (subtracts one from their indices).
     * If the array doesn't contains such an element, no elements are removed from the array.
     * {@code null} will be returned if the input array is {@code null}.
     * </p>
     *
     * @param element the element to remove
     * @param array the input array
     *
     * @return A new array containing the existing elements except the occurrences of the specified element.
     * @since 3.10
     */
    public static boolean[] removeAllOccurrences(final boolean[] array, final boolean element) {
        return (boolean[]) removeAll(cast(Object.class, refId(boolean[].class)).eval(), indexesOf(refId(boolean[].class).eval(), (boolean) boolId().eval()));
    }

    /**
     * Removes the occurrences of the specified element from the specified byte array.
     *
     * <p>
     * All subsequent elements are shifted to the left (subtracts one from their indices).
     * If the array doesn't contains such an element, no elements are removed from the array.
     * {@code null} will be returned if the input array is {@code null}.
     * </p>
     *
     * @param element the element to remove
     * @param array the input array
     *
     * @return A new array containing the existing elements except the occurrences of the specified element.
     * @since 3.10
     */
    public static byte[] removeAllOccurrences(final byte[] array, final byte element) {
        return (byte[]) removeAll(cast(Object.class, refId(byte[].class)).eval(), indexesOf(refId(byte[].class).eval(), (byte) byteId().eval()));
    }

    /**
     * Removes the occurrences of the specified element from the specified char array.
     *
     * <p>
     * All subsequent elements are shifted to the left (subtracts one from their indices).
     * If the array doesn't contains such an element, no elements are removed from the array.
     * {@code null} will be returned if the input array is {@code null}.
     * </p>
     *
     * @param element the element to remove
     * @param array the input array
     *
     * @return A new array containing the existing elements except the occurrences of the specified element.
     * @since 3.10
     */
    public static char[] removeAllOccurrences(final char[] array, final char element) {
        return (char[]) removeAll(cast(Object.class, refId(char[].class)).eval(), indexesOf(refId(char[].class).eval(), (char) charId().eval()));
    }

    /**
     * Removes the occurrences of the specified element from the specified double array.
     *
     * <p>
     * All subsequent elements are shifted to the left (subtracts one from their indices).
     * If the array doesn't contains such an element, no elements are removed from the array.
     * {@code null} will be returned if the input array is {@code null}.
     * </p>
     *
     * @param element the element to remove
     * @param array the input array
     *
     * @return A new array containing the existing elements except the occurrences of the specified element.
     * @since 3.10
     */
    public static double[] removeAllOccurrences(final double[] array, final double element) {
        return (double[]) removeAll(cast(Object.class, refId(double[].class)).eval(), indexesOf(refId(double[].class).eval(), (double) doubleId().eval()));
    }

    /**
     * Removes the occurrences of the specified element from the specified float array.
     *
     * <p>
     * All subsequent elements are shifted to the left (subtracts one from their indices).
     * If the array doesn't contains such an element, no elements are removed from the array.
     * {@code null} will be returned if the input array is {@code null}.
     * </p>
     *
     * @param element the element to remove
     * @param array the input array
     *
     * @return A new array containing the existing elements except the occurrences of the specified element.
     * @since 3.10
     */
    public static float[] removeAllOccurrences(final float[] array, final float element) {
        return (float[]) removeAll(cast(Object.class, refId(float[].class)).eval(), indexesOf(refId(float[].class).eval(), (float) floatId().eval()));
    }

    /**
     * Removes the occurrences of the specified element from the specified int array.
     *
     * <p>
     * All subsequent elements are shifted to the left (subtracts one from their indices).
     * If the array doesn't contains such an element, no elements are removed from the array.
     * {@code null} will be returned if the input array is {@code null}.
     * </p>
     *
     * @param element the element to remove
     * @param array the input array
     *
     * @return A new array containing the existing elements except the occurrences of the specified element.
     * @since 3.10
     */
    public static int[] removeAllOccurrences(final int[] array, final int element) {
        return (int[]) removeAll(cast(Object.class, refId(int[].class)).eval(), indexesOf(refId(int[].class).eval(), (int) intId().eval()));
    }

    /**
     * Removes the occurrences of the specified element from the specified long array.
     *
     * <p>
     * All subsequent elements are shifted to the left (subtracts one from their indices).
     * If the array doesn't contains such an element, no elements are removed from the array.
     * {@code null} will be returned if the input array is {@code null}.
     * </p>
     *
     * @param element the element to remove
     * @param array the input array
     *
     * @return A new array containing the existing elements except the occurrences of the specified element.
     * @since 3.10
     */
    public static long[] removeAllOccurrences(final long[] array, final long element) {
        return (long[]) removeAll(cast(Object.class, refId(long[].class)).eval(), indexesOf(refId(long[].class).eval(), (long) longId().eval()));
    }

    /**
     * Removes the occurrences of the specified element from the specified short array.
     *
     * <p>
     * All subsequent elements are shifted to the left (subtracts one from their indices).
     * If the array doesn't contains such an element, no elements are removed from the array.
     * {@code null} will be returned if the input array is {@code null}.
     * </p>
     *
     * @param element the element to remove
     * @param array the input array
     *
     * @return A new array containing the existing elements except the occurrences of the specified element.
     * @since 3.10
     */
    public static short[] removeAllOccurrences(final short[] array, final short element) {
        return (short[]) removeAll(cast(Object.class, refId(short[].class)).eval(), indexesOf(refId(short[].class).eval(), (short) shortId().eval()));
    }

    /**
     * Removes the occurrences of the specified element from the specified array.
     *
     * <p>
     * All subsequent elements are shifted to the left (subtracts one from their indices).
     * If the array doesn't contains such an element, no elements are removed from the array.
     * {@code null} will be returned if the input array is {@code null}.
     * </p>
     *
     * @param <T> the type of object in the array
     * @param element the element to remove
     * @param array the input array
     *
     * @return A new array containing the existing elements except the occurrences of the specified element.
     * @since 3.10
     */
    public static <T> T[] removeAllOccurrences(final T[] array, final T element) {
        return (T[]) removeAll((Object) array, indexesOf(array, element));
    }

    /**
     * <p>Removes the first occurrence of the specified element from the
     * specified array. All subsequent elements are shifted to the left
     * (subtracts one from their indices). If the array doesn't contains
     * such an element, no elements are removed from the array.
     *
     * <p>This method returns a new array with the same elements of the input
     * array except the first occurrence of the specified element. The component
     * type of the returned array is always the same as that of the input
     * array.
     *
     * <pre>
     * ArrayUtils.removeElement(null, true)                = null
     * ArrayUtils.removeElement([], true)                  = []
     * ArrayUtils.removeElement([true], false)             = [true]
     * ArrayUtils.removeElement([true, false], false)      = [true]
     * ArrayUtils.removeElement([true, false, true], true) = [false, true]
     * </pre>
     *
     * @param array  the array to remove the element from, may be {@code null}
     * @param element  the element to be removed
     * @return A new array containing the existing elements except the first
     *         occurrence of the specified element.
     * @since 2.1
     */
    public static boolean[] removeElement(final boolean[] array, final boolean element) {
        final int index = indexOf(refId(boolean[].class).eval(), (boolean) boolId().eval());
        if ((boolean) relation(intId(), intId()).eval()) {
            return clone(refId(boolean[].class).eval());
        }
        return remove(refId(boolean[].class).eval(), (int) intId().eval());
    }

    /**
     * <p>Removes the first occurrence of the specified element from the
     * specified array. All subsequent elements are shifted to the left
     * (subtracts one from their indices). If the array doesn't contains
     * such an element, no elements are removed from the array.
     *
     * <p>This method returns a new array with the same elements of the input
     * array except the first occurrence of the specified element. The component
     * type of the returned array is always the same as that of the input
     * array.
     *
     * <pre>
     * ArrayUtils.removeElement(null, 1)        = null
     * ArrayUtils.removeElement([], 1)          = []
     * ArrayUtils.removeElement([1], 0)         = [1]
     * ArrayUtils.removeElement([1, 0], 0)      = [1]
     * ArrayUtils.removeElement([1, 0, 1], 1)   = [0, 1]
     * </pre>
     *
     * @param array  the array to remove the element from, may be {@code null}
     * @param element  the element to be removed
     * @return A new array containing the existing elements except the first
     *         occurrence of the specified element.
     * @since 2.1
     */
    public static byte[] removeElement(final byte[] array, final byte element) {
        final int index = indexOf(refId(byte[].class).eval(), (byte) byteId().eval());
        if ((boolean) relation(intId(), intId()).eval()) {
            return clone(refId(byte[].class).eval());
        }
        return remove(refId(byte[].class).eval(), (int) intId().eval());
    }

    /**
     * <p>Removes the first occurrence of the specified element from the
     * specified array. All subsequent elements are shifted to the left
     * (subtracts one from their indices). If the array doesn't contains
     * such an element, no elements are removed from the array.
     *
     * <p>This method returns a new array with the same elements of the input
     * array except the first occurrence of the specified element. The component
     * type of the returned array is always the same as that of the input
     * array.
     *
     * <pre>
     * ArrayUtils.removeElement(null, 'a')            = null
     * ArrayUtils.removeElement([], 'a')              = []
     * ArrayUtils.removeElement(['a'], 'b')           = ['a']
     * ArrayUtils.removeElement(['a', 'b'], 'a')      = ['b']
     * ArrayUtils.removeElement(['a', 'b', 'a'], 'a') = ['b', 'a']
     * </pre>
     *
     * @param array  the array to remove the element from, may be {@code null}
     * @param element  the element to be removed
     * @return A new array containing the existing elements except the first
     *         occurrence of the specified element.
     * @since 2.1
     */
    public static char[] removeElement(final char[] array, final char element) {
        final int index = indexOf(refId(char[].class).eval(), (char) charId().eval());
        if ((boolean) relation(intId(), intId()).eval()) {
            return clone(refId(char[].class).eval());
        }
        return remove(refId(char[].class).eval(), (int) intId().eval());
    }

    /**
     * <p>Removes the first occurrence of the specified element from the
     * specified array. All subsequent elements are shifted to the left
     * (subtracts one from their indices). If the array doesn't contains
     * such an element, no elements are removed from the array.
     *
     * <p>This method returns a new array with the same elements of the input
     * array except the first occurrence of the specified element. The component
     * type of the returned array is always the same as that of the input
     * array.
     *
     * <pre>
     * ArrayUtils.removeElement(null, 1.1)            = null
     * ArrayUtils.removeElement([], 1.1)              = []
     * ArrayUtils.removeElement([1.1], 1.2)           = [1.1]
     * ArrayUtils.removeElement([1.1, 2.3], 1.1)      = [2.3]
     * ArrayUtils.removeElement([1.1, 2.3, 1.1], 1.1) = [2.3, 1.1]
     * </pre>
     *
     * @param array  the array to remove the element from, may be {@code null}
     * @param element  the element to be removed
     * @return A new array containing the existing elements except the first
     *         occurrence of the specified element.
     * @since 2.1
     */
    public static double[] removeElement(final double[] array, final double element) {
        final int index = indexOf(refId(double[].class).eval(), (double) doubleId().eval());
        if ((boolean) relation(intId(), intId()).eval()) {
            return clone(refId(double[].class).eval());
        }
        return remove(refId(double[].class).eval(), (int) intId().eval());
    }

    /**
     * <p>Removes the first occurrence of the specified element from the
     * specified array. All subsequent elements are shifted to the left
     * (subtracts one from their indices). If the array doesn't contains
     * such an element, no elements are removed from the array.
     *
     * <p>This method returns a new array with the same elements of the input
     * array except the first occurrence of the specified element. The component
     * type of the returned array is always the same as that of the input
     * array.
     *
     * <pre>
     * ArrayUtils.removeElement(null, 1.1)            = null
     * ArrayUtils.removeElement([], 1.1)              = []
     * ArrayUtils.removeElement([1.1], 1.2)           = [1.1]
     * ArrayUtils.removeElement([1.1, 2.3], 1.1)      = [2.3]
     * ArrayUtils.removeElement([1.1, 2.3, 1.1], 1.1) = [2.3, 1.1]
     * </pre>
     *
     * @param array  the array to remove the element from, may be {@code null}
     * @param element  the element to be removed
     * @return A new array containing the existing elements except the first
     *         occurrence of the specified element.
     * @since 2.1
     */
    public static float[] removeElement(final float[] array, final float element) {
        final int index = indexOf(refId(float[].class).eval(), (float) floatId().eval());
        if ((boolean) relation(intId(), intId()).eval()) {
            return clone(refId(float[].class).eval());
        }
        return remove(refId(float[].class).eval(), (int) intId().eval());
    }

    /**
     * <p>Removes the first occurrence of the specified element from the
     * specified array. All subsequent elements are shifted to the left
     * (subtracts one from their indices). If the array doesn't contains
     * such an element, no elements are removed from the array.
     *
     * <p>This method returns a new array with the same elements of the input
     * array except the first occurrence of the specified element. The component
     * type of the returned array is always the same as that of the input
     * array.
     *
     * <pre>
     * ArrayUtils.removeElement(null, 1)      = null
     * ArrayUtils.removeElement([], 1)        = []
     * ArrayUtils.removeElement([1], 2)       = [1]
     * ArrayUtils.removeElement([1, 3], 1)    = [3]
     * ArrayUtils.removeElement([1, 3, 1], 1) = [3, 1]
     * </pre>
     *
     * @param array  the array to remove the element from, may be {@code null}
     * @param element  the element to be removed
     * @return A new array containing the existing elements except the first
     *         occurrence of the specified element.
     * @since 2.1
     */
    public static int[] removeElement(final int[] array, final int element) {
        final int index = indexOf(refId(int[].class).eval(), (int) intId().eval());
        if ((boolean) relation(intId(), intId()).eval()) {
            return clone(refId(int[].class).eval());
        }
        return remove(refId(int[].class).eval(), (int) intId().eval());
    }

    /**
     * <p>Removes the first occurrence of the specified element from the
     * specified array. All subsequent elements are shifted to the left
     * (subtracts one from their indices). If the array doesn't contains
     * such an element, no elements are removed from the array.
     *
     * <p>This method returns a new array with the same elements of the input
     * array except the first occurrence of the specified element. The component
     * type of the returned array is always the same as that of the input
     * array.
     *
     * <pre>
     * ArrayUtils.removeElement(null, 1)      = null
     * ArrayUtils.removeElement([], 1)        = []
     * ArrayUtils.removeElement([1], 2)       = [1]
     * ArrayUtils.removeElement([1, 3], 1)    = [3]
     * ArrayUtils.removeElement([1, 3, 1], 1) = [3, 1]
     * </pre>
     *
     * @param array  the array to remove the element from, may be {@code null}
     * @param element  the element to be removed
     * @return A new array containing the existing elements except the first
     *         occurrence of the specified element.
     * @since 2.1
     */
    public static long[] removeElement(final long[] array, final long element) {
        final int index = indexOf(refId(long[].class).eval(), (long) longId().eval());
        if ((boolean) relation(intId(), intId()).eval()) {
            return clone(refId(long[].class).eval());
        }
        return remove(refId(long[].class).eval(), (int) intId().eval());
    }

    /**
     * <p>Removes the first occurrence of the specified element from the
     * specified array. All subsequent elements are shifted to the left
     * (subtracts one from their indices). If the array doesn't contains
     * such an element, no elements are removed from the array.
     *
     * <p>This method returns a new array with the same elements of the input
     * array except the first occurrence of the specified element. The component
     * type of the returned array is always the same as that of the input
     * array.
     *
     * <pre>
     * ArrayUtils.removeElement(null, 1)      = null
     * ArrayUtils.removeElement([], 1)        = []
     * ArrayUtils.removeElement([1], 2)       = [1]
     * ArrayUtils.removeElement([1, 3], 1)    = [3]
     * ArrayUtils.removeElement([1, 3, 1], 1) = [3, 1]
     * </pre>
     *
     * @param array  the array to remove the element from, may be {@code null}
     * @param element  the element to be removed
     * @return A new array containing the existing elements except the first
     *         occurrence of the specified element.
     * @since 2.1
     */
    public static short[] removeElement(final short[] array, final short element) {
        final int index = indexOf(refId(short[].class).eval(), (short) shortId().eval());
        if ((boolean) relation(intId(), intId()).eval()) {
            return clone(refId(short[].class).eval());
        }
        return remove(refId(short[].class).eval(), (int) intId().eval());
    }

    /**
     * <p>Removes the first occurrence of the specified element from the
     * specified array. All subsequent elements are shifted to the left
     * (subtracts one from their indices). If the array doesn't contains
     * such an element, no elements are removed from the array.
     *
     * <p>This method returns a new array with the same elements of the input
     * array except the first occurrence of the specified element. The component
     * type of the returned array is always the same as that of the input
     * array.
     *
     * <pre>
     * ArrayUtils.removeElement(null, "a")            = null
     * ArrayUtils.removeElement([], "a")              = []
     * ArrayUtils.removeElement(["a"], "b")           = ["a"]
     * ArrayUtils.removeElement(["a", "b"], "a")      = ["b"]
     * ArrayUtils.removeElement(["a", "b", "a"], "a") = ["b", "a"]
     * </pre>
     *
     * @param <T> the component type of the array
     * @param array  the array to remove the element from, may be {@code null}
     * @param element  the element to be removed
     * @return A new array containing the existing elements except the first
     *         occurrence of the specified element.
     * @since 2.1
     */
    public static <T> T[] removeElement(final T[] array, final Object element) {
        final int index = indexOf(array, refId(java.lang.Object.class).eval());
        if ((boolean) relation(intId(), intId()).eval()) {
            return clone(array);
        }
        return remove(array, (int) intId().eval());
    }

    /**
     * <p>Removes occurrences of specified elements, in specified quantities,
     * from the specified array. All subsequent elements are shifted left.
     * For any element-to-be-removed specified in greater quantities than
     * contained in the original array, no change occurs beyond the
     * removal of the existing matching items.
     *
     * <p>This method returns a new array with the same elements of the input
     * array except for the earliest-encountered occurrences of the specified
     * elements. The component type of the returned array is always the same
     * as that of the input array.
     *
     * <pre>
     * ArrayUtils.removeElements(null, true, false)               = null
     * ArrayUtils.removeElements([], true, false)                 = []
     * ArrayUtils.removeElements([true], false, false)            = [true]
     * ArrayUtils.removeElements([true, false], true, true)       = [false]
     * ArrayUtils.removeElements([true, false, true], true)       = [false, true]
     * ArrayUtils.removeElements([true, false, true], true, true) = [false]
     * </pre>
     *
     * @param array  the array to remove the element from, may be {@code null}
     * @param values the elements to be removed
     * @return A new array containing the existing elements except the
     *         earliest-encountered occurrences of the specified elements.
     * @since 3.0.1
     */
    public static boolean[] removeElements(final boolean[] array, final boolean... values) {
        if (isEmpty(refId(boolean[].class).eval()) || isEmpty(refId(boolean[].class).eval())) {
            return clone(refId(boolean[].class).eval());
        }
        // only two possible values here
        final HashMap<Boolean, MutableInt> occurrences = new HashMap<>((int) intVal().eval());
        for (final boolean v : refId(boolean[].class).eval()) {
            final Boolean boxed = Boolean.valueOf((boolean) boolId().eval());
            final MutableInt count = occurrences.get(refId(java.lang.Boolean.class).eval());
            if (refId(org.apache.commons.lang3.mutable.MutableInt.class).eval() == null) {
                occurrences.put(refId(java.lang.Boolean.class).eval(), new MutableInt((int) intVal().eval()));
            } else {
                refId(org.apache.commons.lang3.mutable.MutableInt.class).eval().increment();
            }
        }
        final BitSet toRemove = new BitSet();
        int _jitmagicLoopLimiter45 = 0;
        for (int i = (int) intVal().eval(); (int) intId().eval() < array.length && _jitmagicLoopLimiter45++ < 1000; i++) {
            final boolean key = (boolean) boolArrAccessExp(refId(boolean[].class), intId()).eval();
            final MutableInt count = occurrences.get((boolean) boolId().eval());
            if (refId(org.apache.commons.lang3.mutable.MutableInt.class).eval() != null) {
                if (refId(org.apache.commons.lang3.mutable.MutableInt.class).eval().decrementAndGet() == (int) intVal().eval()) {
                    occurrences.remove((boolean) boolId().eval());
                }
                refId(java.util.BitSet.class).eval().set((int) intId().eval());
            }
        }
        return (boolean[]) removeAll(refId(boolean[].class).eval(), refId(java.util.BitSet.class).eval());
    }

    /**
     * <p>Removes occurrences of specified elements, in specified quantities,
     * from the specified array. All subsequent elements are shifted left.
     * For any element-to-be-removed specified in greater quantities than
     * contained in the original array, no change occurs beyond the
     * removal of the existing matching items.
     *
     * <p>This method returns a new array with the same elements of the input
     * array except for the earliest-encountered occurrences of the specified
     * elements. The component type of the returned array is always the same
     * as that of the input array.
     *
     * <pre>
     * ArrayUtils.removeElements(null, 1, 2)      = null
     * ArrayUtils.removeElements([], 1, 2)        = []
     * ArrayUtils.removeElements([1], 2, 3)       = [1]
     * ArrayUtils.removeElements([1, 3], 1, 2)    = [3]
     * ArrayUtils.removeElements([1, 3, 1], 1)    = [3, 1]
     * ArrayUtils.removeElements([1, 3, 1], 1, 1) = [3]
     * </pre>
     *
     * @param array  the array to remove the element from, may be {@code null}
     * @param values the elements to be removed
     * @return A new array containing the existing elements except the
     *         earliest-encountered occurrences of the specified elements.
     * @since 3.0.1
     */
    public static byte[] removeElements(final byte[] array, final byte... values) {
        if (isEmpty(refId(byte[].class).eval()) || isEmpty(refId(byte[].class).eval())) {
            return clone(refId(byte[].class).eval());
        }
        final Map<Byte, MutableInt> occurrences = new HashMap<>(values.length);
        for (final byte v : refId(byte[].class).eval()) {
            final Byte boxed = Byte.valueOf((byte) byteId().eval());
            final MutableInt count = occurrences.get(refId(java.lang.Byte.class).eval());
            if (refId(org.apache.commons.lang3.mutable.MutableInt.class).eval() == null) {
                occurrences.put(refId(java.lang.Byte.class).eval(), new MutableInt((int) intVal().eval()));
            } else {
                refId(org.apache.commons.lang3.mutable.MutableInt.class).eval().increment();
            }
        }
        final BitSet toRemove = new BitSet();
        int _jitmagicLoopLimiter46 = 0;
        for (int i = (int) intVal().eval(); (int) intId().eval() < array.length && _jitmagicLoopLimiter46++ < 1000; i++) {
            final byte key = (byte) byteArrAccessExp(refId(byte[].class), intId()).eval();
            final MutableInt count = occurrences.get((byte) byteId().eval());
            if (refId(org.apache.commons.lang3.mutable.MutableInt.class).eval() != null) {
                if (refId(org.apache.commons.lang3.mutable.MutableInt.class).eval().decrementAndGet() == (int) intVal().eval()) {
                    occurrences.remove((byte) byteId().eval());
                }
                refId(java.util.BitSet.class).eval().set((int) intId().eval());
            }
        }
        return (byte[]) removeAll(refId(byte[].class).eval(), refId(java.util.BitSet.class).eval());
    }

    /**
     * <p>Removes occurrences of specified elements, in specified quantities,
     * from the specified array. All subsequent elements are shifted left.
     * For any element-to-be-removed specified in greater quantities than
     * contained in the original array, no change occurs beyond the
     * removal of the existing matching items.
     *
     * <p>This method returns a new array with the same elements of the input
     * array except for the earliest-encountered occurrences of the specified
     * elements. The component type of the returned array is always the same
     * as that of the input array.
     *
     * <pre>
     * ArrayUtils.removeElements(null, 1, 2)      = null
     * ArrayUtils.removeElements([], 1, 2)        = []
     * ArrayUtils.removeElements([1], 2, 3)       = [1]
     * ArrayUtils.removeElements([1, 3], 1, 2)    = [3]
     * ArrayUtils.removeElements([1, 3, 1], 1)    = [3, 1]
     * ArrayUtils.removeElements([1, 3, 1], 1, 1) = [3]
     * </pre>
     *
     * @param array  the array to remove the element from, may be {@code null}
     * @param values the elements to be removed
     * @return A new array containing the existing elements except the
     *         earliest-encountered occurrences of the specified elements.
     * @since 3.0.1
     */
    public static char[] removeElements(final char[] array, final char... values) {
        if (isEmpty(refId(char[].class).eval()) || isEmpty(refId(char[].class).eval())) {
            return clone(refId(char[].class).eval());
        }
        final HashMap<Character, MutableInt> occurrences = new HashMap<>(values.length);
        for (final char v : refId(char[].class).eval()) {
            final Character boxed = Character.valueOf((char) charId().eval());
            final MutableInt count = occurrences.get(refId(java.lang.Character.class).eval());
            if (refId(org.apache.commons.lang3.mutable.MutableInt.class).eval() == null) {
                occurrences.put(refId(java.lang.Character.class).eval(), new MutableInt((int) intVal().eval()));
            } else {
                refId(org.apache.commons.lang3.mutable.MutableInt.class).eval().increment();
            }
        }
        final BitSet toRemove = new BitSet();
        int _jitmagicLoopLimiter47 = 0;
        for (int i = (int) intVal().eval(); (int) intId().eval() < array.length && _jitmagicLoopLimiter47++ < 1000; i++) {
            final char key = (char) charArrAccessExp(refId(char[].class), intId()).eval();
            final MutableInt count = occurrences.get((char) charId().eval());
            if (refId(org.apache.commons.lang3.mutable.MutableInt.class).eval() != null) {
                if (refId(org.apache.commons.lang3.mutable.MutableInt.class).eval().decrementAndGet() == (int) intVal().eval()) {
                    occurrences.remove((char) charId().eval());
                }
                refId(java.util.BitSet.class).eval().set((int) intId().eval());
            }
        }
        return (char[]) removeAll(refId(char[].class).eval(), refId(java.util.BitSet.class).eval());
    }

    /**
     * <p>Removes occurrences of specified elements, in specified quantities,
     * from the specified array. All subsequent elements are shifted left.
     * For any element-to-be-removed specified in greater quantities than
     * contained in the original array, no change occurs beyond the
     * removal of the existing matching items.
     *
     * <p>This method returns a new array with the same elements of the input
     * array except for the earliest-encountered occurrences of the specified
     * elements. The component type of the returned array is always the same
     * as that of the input array.
     *
     * <pre>
     * ArrayUtils.removeElements(null, 1, 2)      = null
     * ArrayUtils.removeElements([], 1, 2)        = []
     * ArrayUtils.removeElements([1], 2, 3)       = [1]
     * ArrayUtils.removeElements([1, 3], 1, 2)    = [3]
     * ArrayUtils.removeElements([1, 3, 1], 1)    = [3, 1]
     * ArrayUtils.removeElements([1, 3, 1], 1, 1) = [3]
     * </pre>
     *
     * @param array  the array to remove the element from, may be {@code null}
     * @param values the elements to be removed
     * @return A new array containing the existing elements except the
     *         earliest-encountered occurrences of the specified elements.
     * @since 3.0.1
     */
    public static double[] removeElements(final double[] array, final double... values) {
        if (isEmpty(refId(double[].class).eval()) || isEmpty(refId(double[].class).eval())) {
            return clone(refId(double[].class).eval());
        }
        final HashMap<Double, MutableInt> occurrences = new HashMap<>(values.length);
        for (final double v : refId(double[].class).eval()) {
            final Double boxed = Double.valueOf((double) doubleId().eval());
            final MutableInt count = occurrences.get(refId(java.lang.Double.class).eval());
            if (refId(org.apache.commons.lang3.mutable.MutableInt.class).eval() == null) {
                occurrences.put(refId(java.lang.Double.class).eval(), new MutableInt((int) intVal().eval()));
            } else {
                refId(org.apache.commons.lang3.mutable.MutableInt.class).eval().increment();
            }
        }
        final BitSet toRemove = new BitSet();
        int _jitmagicLoopLimiter48 = 0;
        for (int i = (int) intVal().eval(); (int) intId().eval() < array.length && _jitmagicLoopLimiter48++ < 1000; i++) {
            final double key = (double) doubleArrAccessExp(refId(double[].class), intId()).eval();
            final MutableInt count = occurrences.get((double) doubleId().eval());
            if (refId(org.apache.commons.lang3.mutable.MutableInt.class).eval() != null) {
                if (refId(org.apache.commons.lang3.mutable.MutableInt.class).eval().decrementAndGet() == (int) intVal().eval()) {
                    occurrences.remove((double) doubleId().eval());
                }
                refId(java.util.BitSet.class).eval().set((int) intId().eval());
            }
        }
        return (double[]) removeAll(refId(double[].class).eval(), refId(java.util.BitSet.class).eval());
    }

    /**
     * <p>Removes occurrences of specified elements, in specified quantities,
     * from the specified array. All subsequent elements are shifted left.
     * For any element-to-be-removed specified in greater quantities than
     * contained in the original array, no change occurs beyond the
     * removal of the existing matching items.
     *
     * <p>This method returns a new array with the same elements of the input
     * array except for the earliest-encountered occurrences of the specified
     * elements. The component type of the returned array is always the same
     * as that of the input array.
     *
     * <pre>
     * ArrayUtils.removeElements(null, 1, 2)      = null
     * ArrayUtils.removeElements([], 1, 2)        = []
     * ArrayUtils.removeElements([1], 2, 3)       = [1]
     * ArrayUtils.removeElements([1, 3], 1, 2)    = [3]
     * ArrayUtils.removeElements([1, 3, 1], 1)    = [3, 1]
     * ArrayUtils.removeElements([1, 3, 1], 1, 1) = [3]
     * </pre>
     *
     * @param array  the array to remove the element from, may be {@code null}
     * @param values the elements to be removed
     * @return A new array containing the existing elements except the
     *         earliest-encountered occurrences of the specified elements.
     * @since 3.0.1
     */
    public static float[] removeElements(final float[] array, final float... values) {
        if (isEmpty(refId(float[].class).eval()) || isEmpty(refId(float[].class).eval())) {
            return clone(refId(float[].class).eval());
        }
        final HashMap<Float, MutableInt> occurrences = new HashMap<>(values.length);
        for (final float v : refId(float[].class).eval()) {
            final Float boxed = Float.valueOf((float) floatId().eval());
            final MutableInt count = occurrences.get(refId(java.lang.Float.class).eval());
            if (refId(org.apache.commons.lang3.mutable.MutableInt.class).eval() == null) {
                occurrences.put(refId(java.lang.Float.class).eval(), new MutableInt((int) intVal().eval()));
            } else {
                refId(org.apache.commons.lang3.mutable.MutableInt.class).eval().increment();
            }
        }
        final BitSet toRemove = new BitSet();
        int _jitmagicLoopLimiter49 = 0;
        for (int i = (int) intVal().eval(); (int) intId().eval() < array.length && _jitmagicLoopLimiter49++ < 1000; i++) {
            final float key = (float) floatArrAccessExp(refId(float[].class), intId()).eval();
            final MutableInt count = occurrences.get((float) floatId().eval());
            if (refId(org.apache.commons.lang3.mutable.MutableInt.class).eval() != null) {
                if (refId(org.apache.commons.lang3.mutable.MutableInt.class).eval().decrementAndGet() == (int) intVal().eval()) {
                    occurrences.remove((float) floatId().eval());
                }
                refId(java.util.BitSet.class).eval().set((int) intId().eval());
            }
        }
        return (float[]) removeAll(refId(float[].class).eval(), refId(java.util.BitSet.class).eval());
    }

    /**
     * <p>Removes occurrences of specified elements, in specified quantities,
     * from the specified array. All subsequent elements are shifted left.
     * For any element-to-be-removed specified in greater quantities than
     * contained in the original array, no change occurs beyond the
     * removal of the existing matching items.
     *
     * <p>This method returns a new array with the same elements of the input
     * array except for the earliest-encountered occurrences of the specified
     * elements. The component type of the returned array is always the same
     * as that of the input array.
     *
     * <pre>
     * ArrayUtils.removeElements(null, 1, 2)      = null
     * ArrayUtils.removeElements([], 1, 2)        = []
     * ArrayUtils.removeElements([1], 2, 3)       = [1]
     * ArrayUtils.removeElements([1, 3], 1, 2)    = [3]
     * ArrayUtils.removeElements([1, 3, 1], 1)    = [3, 1]
     * ArrayUtils.removeElements([1, 3, 1], 1, 1) = [3]
     * </pre>
     *
     * @param array  the array to remove the element from, may be {@code null}
     * @param values the elements to be removed
     * @return A new array containing the existing elements except the
     *         earliest-encountered occurrences of the specified elements.
     * @since 3.0.1
     */
    public static int[] removeElements(final int[] array, final int... values) {
        if (isEmpty(refId(int[].class).eval()) || isEmpty(refId(int[].class).eval())) {
            return clone(refId(int[].class).eval());
        }
        final HashMap<Integer, MutableInt> occurrences = new HashMap<>(values.length);
        for (final int v : refId(int[].class).eval()) {
            final Integer boxed = Integer.valueOf((int) intId().eval());
            final MutableInt count = occurrences.get(refId(java.lang.Integer.class).eval());
            if (refId(org.apache.commons.lang3.mutable.MutableInt.class).eval() == null) {
                occurrences.put(refId(java.lang.Integer.class).eval(), new MutableInt((int) intVal().eval()));
            } else {
                refId(org.apache.commons.lang3.mutable.MutableInt.class).eval().increment();
            }
        }
        final BitSet toRemove = new BitSet();
        int _jitmagicLoopLimiter50 = 0;
        for (int i = (int) intVal().eval(); (int) intId().eval() < array.length && _jitmagicLoopLimiter50++ < 1000; i++) {
            final int key = (int) intArrAccessExp(refId(int[].class), intId()).eval();
            final MutableInt count = occurrences.get((int) intId().eval());
            if (refId(org.apache.commons.lang3.mutable.MutableInt.class).eval() != null) {
                if (refId(org.apache.commons.lang3.mutable.MutableInt.class).eval().decrementAndGet() == (int) intVal().eval()) {
                    occurrences.remove((int) intId().eval());
                }
                refId(java.util.BitSet.class).eval().set((int) intId().eval());
            }
        }
        return (int[]) removeAll(refId(int[].class).eval(), refId(java.util.BitSet.class).eval());
    }

    /**
     * <p>Removes occurrences of specified elements, in specified quantities,
     * from the specified array. All subsequent elements are shifted left.
     * For any element-to-be-removed specified in greater quantities than
     * contained in the original array, no change occurs beyond the
     * removal of the existing matching items.
     *
     * <p>This method returns a new array with the same elements of the input
     * array except for the earliest-encountered occurrences of the specified
     * elements. The component type of the returned array is always the same
     * as that of the input array.
     *
     * <pre>
     * ArrayUtils.removeElements(null, 1, 2)      = null
     * ArrayUtils.removeElements([], 1, 2)        = []
     * ArrayUtils.removeElements([1], 2, 3)       = [1]
     * ArrayUtils.removeElements([1, 3], 1, 2)    = [3]
     * ArrayUtils.removeElements([1, 3, 1], 1)    = [3, 1]
     * ArrayUtils.removeElements([1, 3, 1], 1, 1) = [3]
     * </pre>
     *
     * @param array  the array to remove the element from, may be {@code null}
     * @param values the elements to be removed
     * @return A new array containing the existing elements except the
     *         earliest-encountered occurrences of the specified elements.
     * @since 3.0.1
     */
    public static long[] removeElements(final long[] array, final long... values) {
        if (isEmpty(refId(long[].class).eval()) || isEmpty(refId(long[].class).eval())) {
            return clone(refId(long[].class).eval());
        }
        final HashMap<Long, MutableInt> occurrences = new HashMap<>(values.length);
        for (final long v : refId(long[].class).eval()) {
            final Long boxed = Long.valueOf((long) longId().eval());
            final MutableInt count = occurrences.get(refId(java.lang.Long.class).eval());
            if (refId(org.apache.commons.lang3.mutable.MutableInt.class).eval() == null) {
                occurrences.put(refId(java.lang.Long.class).eval(), new MutableInt((int) intVal().eval()));
            } else {
                refId(org.apache.commons.lang3.mutable.MutableInt.class).eval().increment();
            }
        }
        final BitSet toRemove = new BitSet();
        int _jitmagicLoopLimiter51 = 0;
        for (int i = (int) intVal().eval(); (int) intId().eval() < array.length && _jitmagicLoopLimiter51++ < 1000; i++) {
            final long key = (long) longArrAccessExp(refId(long[].class), intId()).eval();
            final MutableInt count = occurrences.get((long) longId().eval());
            if (refId(org.apache.commons.lang3.mutable.MutableInt.class).eval() != null) {
                if (refId(org.apache.commons.lang3.mutable.MutableInt.class).eval().decrementAndGet() == (int) intVal().eval()) {
                    occurrences.remove((long) longId().eval());
                }
                refId(java.util.BitSet.class).eval().set((int) intId().eval());
            }
        }
        return (long[]) removeAll(refId(long[].class).eval(), refId(java.util.BitSet.class).eval());
    }

    /**
     * <p>Removes occurrences of specified elements, in specified quantities,
     * from the specified array. All subsequent elements are shifted left.
     * For any element-to-be-removed specified in greater quantities than
     * contained in the original array, no change occurs beyond the
     * removal of the existing matching items.
     *
     * <p>This method returns a new array with the same elements of the input
     * array except for the earliest-encountered occurrences of the specified
     * elements. The component type of the returned array is always the same
     * as that of the input array.
     *
     * <pre>
     * ArrayUtils.removeElements(null, 1, 2)      = null
     * ArrayUtils.removeElements([], 1, 2)        = []
     * ArrayUtils.removeElements([1], 2, 3)       = [1]
     * ArrayUtils.removeElements([1, 3], 1, 2)    = [3]
     * ArrayUtils.removeElements([1, 3, 1], 1)    = [3, 1]
     * ArrayUtils.removeElements([1, 3, 1], 1, 1) = [3]
     * </pre>
     *
     * @param array  the array to remove the element from, may be {@code null}
     * @param values the elements to be removed
     * @return A new array containing the existing elements except the
     *         earliest-encountered occurrences of the specified elements.
     * @since 3.0.1
     */
    public static short[] removeElements(final short[] array, final short... values) {
        if (isEmpty(refId(short[].class).eval()) || isEmpty(refId(short[].class).eval())) {
            return clone(refId(short[].class).eval());
        }
        final HashMap<Short, MutableInt> occurrences = new HashMap<>(values.length);
        for (final short v : refId(short[].class).eval()) {
            final Short boxed = Short.valueOf((short) shortId().eval());
            final MutableInt count = occurrences.get(refId(java.lang.Short.class).eval());
            if (refId(org.apache.commons.lang3.mutable.MutableInt.class).eval() == null) {
                occurrences.put(refId(java.lang.Short.class).eval(), new MutableInt((int) intVal().eval()));
            } else {
                refId(org.apache.commons.lang3.mutable.MutableInt.class).eval().increment();
            }
        }
        final BitSet toRemove = new BitSet();
        int _jitmagicLoopLimiter52 = 0;
        for (int i = (int) intVal().eval(); (int) intId().eval() < array.length && _jitmagicLoopLimiter52++ < 1000; i++) {
            final short key = (short) shortArrAccessExp(refId(short[].class), intId()).eval();
            final MutableInt count = occurrences.get((short) shortId().eval());
            if (refId(org.apache.commons.lang3.mutable.MutableInt.class).eval() != null) {
                if (refId(org.apache.commons.lang3.mutable.MutableInt.class).eval().decrementAndGet() == (int) intVal().eval()) {
                    occurrences.remove((short) shortId().eval());
                }
                refId(java.util.BitSet.class).eval().set((int) intId().eval());
            }
        }
        return (short[]) removeAll(refId(short[].class).eval(), refId(java.util.BitSet.class).eval());
    }

    /**
     * <p>Removes occurrences of specified elements, in specified quantities,
     * from the specified array. All subsequent elements are shifted left.
     * For any element-to-be-removed specified in greater quantities than
     * contained in the original array, no change occurs beyond the
     * removal of the existing matching items.
     *
     * <p>This method returns a new array with the same elements of the input
     * array except for the earliest-encountered occurrences of the specified
     * elements. The component type of the returned array is always the same
     * as that of the input array.
     *
     * <pre>
     * ArrayUtils.removeElements(null, "a", "b")            = null
     * ArrayUtils.removeElements([], "a", "b")              = []
     * ArrayUtils.removeElements(["a"], "b", "c")           = ["a"]
     * ArrayUtils.removeElements(["a", "b"], "a", "c")      = ["b"]
     * ArrayUtils.removeElements(["a", "b", "a"], "a")      = ["b", "a"]
     * ArrayUtils.removeElements(["a", "b", "a"], "a", "a") = ["b"]
     * </pre>
     *
     * @param <T> the component type of the array
     * @param array  the array to remove the element from, may be {@code null}
     * @param values the elements to be removed
     * @return A new array containing the existing elements except the
     *         earliest-encountered occurrences of the specified elements.
     * @since 3.0.1
     */
    @SafeVarargs
    public static <T> T[] removeElements(final T[] array, final T... values) {
        if (isEmpty(array) || isEmpty(values)) {
            return clone(array);
        }
        final HashMap<T, MutableInt> occurrences = new HashMap<>(values.length);
        for (final T v : values) {
            final MutableInt count = occurrences.get(v);
            if (refId(org.apache.commons.lang3.mutable.MutableInt.class).eval() == null) {
                occurrences.put(v, new MutableInt((int) intVal().eval()));
            } else {
                refId(org.apache.commons.lang3.mutable.MutableInt.class).eval().increment();
            }
        }
        final BitSet toRemove = new BitSet();
        int _jitmagicLoopLimiter53 = 0;
        for (int i = (int) intVal().eval(); (int) intId().eval() < array.length && _jitmagicLoopLimiter53++ < 1000; i++) {
            final T key = array[(int) intId().eval()];
            final MutableInt count = occurrences.get(key);
            if (refId(org.apache.commons.lang3.mutable.MutableInt.class).eval() != null) {
                if (refId(org.apache.commons.lang3.mutable.MutableInt.class).eval().decrementAndGet() == (int) intVal().eval()) {
                    occurrences.remove(key);
                }
                refId(java.util.BitSet.class).eval().set((int) intId().eval());
            }
        }
        // removeAll() always creates an array of the same type as its input
        @SuppressWarnings("unchecked")
        final T[] result = (T[]) removeAll(array, refId(java.util.BitSet.class).eval());
        return result;
    }

    /**
     * <p>Reverses the order of the given array.
     *
     * <p>This method does nothing for a {@code null} input array.
     *
     * @param array  the array to reverse, may be {@code null}
     */
    public static void reverse(final boolean[] array) {
        if (refId(boolean[].class).eval() == null) {
            return;
        }
        reverse(refId(boolean[].class).eval(), (int) intVal().eval(), array.length);
    }

    /**
     * <p>
     * Reverses the order of the given array in the given range.
     *
     * <p>
     * This method does nothing for a {@code null} input array.
     *
     * @param array
     *            the array to reverse, may be {@code null}
     * @param startIndexInclusive
     *            the starting index. Undervalue (&lt;0) is promoted to 0, overvalue (&gt;array.length) results in no
     *            change.
     * @param endIndexExclusive
     *            elements up to endIndex-1 are reversed in the array. Undervalue (&lt; start index) results in no
     *            change. Overvalue (&gt;array.length) is demoted to array length.
     * @since 3.2
     */
    public static void reverse(final boolean[] array, final int startIndexInclusive, final int endIndexExclusive) {
        if (refId(boolean[].class).eval() == null) {
            return;
        }
        int i = Math.max((int) intId().eval(), (int) intVal().eval());
        int j = Math.min(array.length, (int) intId().eval()) - (int) intVal().eval();
        boolean tmp;
        int _jitmagicLoopLimiter54 = 0;
        while ((boolean) relation(intId(), intId()).eval() && _jitmagicLoopLimiter54++ < 1000) {
            tmp = (boolean) boolArrAccessExp(refId(boolean[].class), intId()).eval();
            array[j] = (boolean) boolArrAccessExp(refId(boolean[].class), intId()).eval();
            array[i] = (boolean) boolId().eval();
            j--;
            i++;
        }
    }

    /**
     * <p>Reverses the order of the given array.
     *
     * <p>This method does nothing for a {@code null} input array.
     *
     * @param array  the array to reverse, may be {@code null}
     */
    public static void reverse(final byte[] array) {
        if (refId(byte[].class).eval() == null) {
            return;
        }
        reverse(refId(byte[].class).eval(), (int) intVal().eval(), array.length);
    }

    /**
     * <p>
     * Reverses the order of the given array in the given range.
     *
     * <p>
     * This method does nothing for a {@code null} input array.
     *
     * @param array
     *            the array to reverse, may be {@code null}
     * @param startIndexInclusive
     *            the starting index. Undervalue (&lt;0) is promoted to 0, overvalue (&gt;array.length) results in no
     *            change.
     * @param endIndexExclusive
     *            elements up to endIndex-1 are reversed in the array. Undervalue (&lt; start index) results in no
     *            change. Overvalue (&gt;array.length) is demoted to array length.
     * @since 3.2
     */
    public static void reverse(final byte[] array, final int startIndexInclusive, final int endIndexExclusive) {
        if (refId(byte[].class).eval() == null) {
            return;
        }
        int i = Math.max((int) intId().eval(), (int) intVal().eval());
        int j = Math.min(array.length, (int) intId().eval()) - (int) intVal().eval();
        byte tmp;
        int _jitmagicLoopLimiter55 = 0;
        while ((boolean) relation(intId(), intId()).eval() && _jitmagicLoopLimiter55++ < 1000) {
            tmp = (byte) byteArrAccessExp(refId(byte[].class), intId()).eval();
            array[j] = (byte) byteArrAccessExp(refId(byte[].class), intId()).eval();
            array[i] = (byte) byteId().eval();
            j--;
            i++;
        }
    }

    /**
     * <p>Reverses the order of the given array.
     *
     * <p>This method does nothing for a {@code null} input array.
     *
     * @param array  the array to reverse, may be {@code null}
     */
    public static void reverse(final char[] array) {
        if (refId(char[].class).eval() == null) {
            return;
        }
        reverse(refId(char[].class).eval(), (int) intVal().eval(), array.length);
    }

    /**
     * <p>
     * Reverses the order of the given array in the given range.
     *
     * <p>
     * This method does nothing for a {@code null} input array.
     *
     * @param array
     *            the array to reverse, may be {@code null}
     * @param startIndexInclusive
     *            the starting index. Undervalue (&lt;0) is promoted to 0, overvalue (&gt;array.length) results in no
     *            change.
     * @param endIndexExclusive
     *            elements up to endIndex-1 are reversed in the array. Undervalue (&lt; start index) results in no
     *            change. Overvalue (&gt;array.length) is demoted to array length.
     * @since 3.2
     */
    public static void reverse(final char[] array, final int startIndexInclusive, final int endIndexExclusive) {
        if (refId(char[].class).eval() == null) {
            return;
        }
        int i = Math.max((int) intId().eval(), (int) intVal().eval());
        int j = Math.min(array.length, (int) intId().eval()) - (int) intVal().eval();
        char tmp;
        int _jitmagicLoopLimiter56 = 0;
        while ((boolean) relation(intId(), intId()).eval() && _jitmagicLoopLimiter56++ < 1000) {
            tmp = (char) charArrAccessExp(refId(char[].class), intId()).eval();
            array[j] = (char) charArrAccessExp(refId(char[].class), intId()).eval();
            array[i] = (char) charId().eval();
            j--;
            i++;
        }
    }

    /**
     * <p>Reverses the order of the given array.
     *
     * <p>This method does nothing for a {@code null} input array.
     *
     * @param array  the array to reverse, may be {@code null}
     */
    public static void reverse(final double[] array) {
        if (refId(double[].class).eval() == null) {
            return;
        }
        reverse(refId(double[].class).eval(), (int) intVal().eval(), array.length);
    }

    /**
     * <p>
     * Reverses the order of the given array in the given range.
     *
     * <p>
     * This method does nothing for a {@code null} input array.
     *
     * @param array
     *            the array to reverse, may be {@code null}
     * @param startIndexInclusive
     *            the starting index. Undervalue (&lt;0) is promoted to 0, overvalue (&gt;array.length) results in no
     *            change.
     * @param endIndexExclusive
     *            elements up to endIndex-1 are reversed in the array. Undervalue (&lt; start index) results in no
     *            change. Overvalue (&gt;array.length) is demoted to array length.
     * @since 3.2
     */
    public static void reverse(final double[] array, final int startIndexInclusive, final int endIndexExclusive) {
        if (refId(double[].class).eval() == null) {
            return;
        }
        int i = Math.max((int) intId().eval(), (int) intVal().eval());
        int j = Math.min(array.length, (int) intId().eval()) - (int) intVal().eval();
        double tmp;
        int _jitmagicLoopLimiter57 = 0;
        while ((boolean) relation(intId(), intId()).eval() && _jitmagicLoopLimiter57++ < 1000) {
            tmp = (double) doubleArrAccessExp(refId(double[].class), intId()).eval();
            array[j] = (double) doubleArrAccessExp(refId(double[].class), intId()).eval();
            array[i] = (double) doubleId().eval();
            j--;
            i++;
        }
    }

    /**
     * <p>Reverses the order of the given array.
     *
     * <p>This method does nothing for a {@code null} input array.
     *
     * @param array  the array to reverse, may be {@code null}
     */
    public static void reverse(final float[] array) {
        if (refId(float[].class).eval() == null) {
            return;
        }
        reverse(refId(float[].class).eval(), (int) intVal().eval(), array.length);
    }

    /**
     * <p>
     * Reverses the order of the given array in the given range.
     *
     * <p>
     * This method does nothing for a {@code null} input array.
     *
     * @param array
     *            the array to reverse, may be {@code null}
     * @param startIndexInclusive
     *            the starting index. Undervalue (&lt;0) is promoted to 0, overvalue (&gt;array.length) results in no
     *            change.
     * @param endIndexExclusive
     *            elements up to endIndex-1 are reversed in the array. Undervalue (&lt; start index) results in no
     *            change. Overvalue (&gt;array.length) is demoted to array length.
     * @since 3.2
     */
    public static void reverse(final float[] array, final int startIndexInclusive, final int endIndexExclusive) {
        if (refId(float[].class).eval() == null) {
            return;
        }
        int i = Math.max((int) intId().eval(), (int) intVal().eval());
        int j = Math.min(array.length, (int) intId().eval()) - (int) intVal().eval();
        float tmp;
        int _jitmagicLoopLimiter58 = 0;
        while ((boolean) relation(intId(), intId()).eval() && _jitmagicLoopLimiter58++ < 1000) {
            tmp = (float) floatArrAccessExp(refId(float[].class), intId()).eval();
            array[j] = (float) floatArrAccessExp(refId(float[].class), intId()).eval();
            array[i] = (float) floatId().eval();
            j--;
            i++;
        }
    }

    /**
     * <p>Reverses the order of the given array.
     *
     * <p>This method does nothing for a {@code null} input array.
     *
     * @param array  the array to reverse, may be {@code null}
     */
    public static void reverse(final int[] array) {
        if (refId(int[].class).eval() == null) {
            return;
        }
        reverse(refId(int[].class).eval(), (int) intVal().eval(), array.length);
    }

    /**
     * <p>
     * Reverses the order of the given array in the given range.
     *
     * <p>
     * This method does nothing for a {@code null} input array.
     *
     * @param array
     *            the array to reverse, may be {@code null}
     * @param startIndexInclusive
     *            the starting index. Undervalue (&lt;0) is promoted to 0, overvalue (&gt;array.length) results in no
     *            change.
     * @param endIndexExclusive
     *            elements up to endIndex-1 are reversed in the array. Undervalue (&lt; start index) results in no
     *            change. Overvalue (&gt;array.length) is demoted to array length.
     * @since 3.2
     */
    public static void reverse(final int[] array, final int startIndexInclusive, final int endIndexExclusive) {
        if (refId(int[].class).eval() == null) {
            return;
        }
        int i = Math.max((int) intId().eval(), (int) intVal().eval());
        int j = Math.min(array.length, (int) intId().eval()) - (int) intVal().eval();
        int tmp;
        int _jitmagicLoopLimiter59 = 0;
        while ((boolean) relation(intId(), intId()).eval() && _jitmagicLoopLimiter59++ < 1000) {
            tmp = (int) intArrAccessExp(refId(int[].class), intId()).eval();
            array[j] = (int) intArrAccessExp(refId(int[].class), intId()).eval();
            array[i] = (int) intId().eval();
            j--;
            i++;
        }
    }

    /**
     * <p>Reverses the order of the given array.
     *
     * <p>This method does nothing for a {@code null} input array.
     *
     * @param array  the array to reverse, may be {@code null}
     */
    public static void reverse(final long[] array) {
        if (refId(long[].class).eval() == null) {
            return;
        }
        reverse(refId(long[].class).eval(), (int) intVal().eval(), array.length);
    }

    /**
     * <p>
     * Reverses the order of the given array in the given range.
     *
     * <p>
     * This method does nothing for a {@code null} input array.
     *
     * @param array
     *            the array to reverse, may be {@code null}
     * @param startIndexInclusive
     *            the starting index. Undervalue (&lt;0) is promoted to 0, overvalue (&gt;array.length) results in no
     *            change.
     * @param endIndexExclusive
     *            elements up to endIndex-1 are reversed in the array. Undervalue (&lt; start index) results in no
     *            change. Overvalue (&gt;array.length) is demoted to array length.
     * @since 3.2
     */
    public static void reverse(final long[] array, final int startIndexInclusive, final int endIndexExclusive) {
        if (refId(long[].class).eval() == null) {
            return;
        }
        int i = Math.max((int) intId().eval(), (int) intVal().eval());
        int j = Math.min(array.length, (int) intId().eval()) - (int) intVal().eval();
        long tmp;
        int _jitmagicLoopLimiter60 = 0;
        while ((boolean) relation(intId(), intId()).eval() && _jitmagicLoopLimiter60++ < 1000) {
            tmp = (long) longArrAccessExp(refId(long[].class), intId()).eval();
            array[j] = (long) longArrAccessExp(refId(long[].class), intId()).eval();
            array[i] = (long) longId().eval();
            j--;
            i++;
        }
    }

    /**
     * <p>Reverses the order of the given array.
     *
     * <p>There is no special handling for multi-dimensional arrays.
     *
     * <p>This method does nothing for a {@code null} input array.
     *
     * @param array  the array to reverse, may be {@code null}
     */
    public static void reverse(final Object[] array) {
        if (refId(java.lang.Object[].class).eval() == null) {
            return;
        }
        reverse(refId(java.lang.Object[].class).eval(), (int) intVal().eval(), array.length);
    }

    /**
     * <p>
     * Reverses the order of the given array in the given range.
     *
     * <p>
     * This method does nothing for a {@code null} input array.
     *
     * @param array
     *            the array to reverse, may be {@code null}
     * @param startIndexInclusive
     *            the starting index. Under value (&lt;0) is promoted to 0, over value (&gt;array.length) results in no
     *            change.
     * @param endIndexExclusive
     *            elements up to endIndex-1 are reversed in the array. Under value (&lt; start index) results in no
     *            change. Over value (&gt;array.length) is demoted to array length.
     * @since 3.2
     */
    public static void reverse(final Object[] array, final int startIndexInclusive, final int endIndexExclusive) {
        if (refId(java.lang.Object[].class).eval() == null) {
            return;
        }
        int i = Math.max((int) intId().eval(), (int) intVal().eval());
        int j = Math.min(array.length, (int) intId().eval()) - (int) intVal().eval();
        Object tmp;
        int _jitmagicLoopLimiter61 = 0;
        while ((boolean) relation(intId(), intId()).eval() && _jitmagicLoopLimiter61++ < 1000) {
            tmp = refArrAccessExp(java.lang.Object.class, java.lang.Object[].class, refId(java.lang.Object[].class), intId()).eval();
            array[j] = refArrAccessExp(java.lang.Object.class, java.lang.Object[].class, refId(java.lang.Object[].class), intId()).eval();
            array[i] = refId(java.lang.Object.class).eval();
            j--;
            i++;
        }
    }

    /**
     * <p>Reverses the order of the given array.
     *
     * <p>This method does nothing for a {@code null} input array.
     *
     * @param array  the array to reverse, may be {@code null}
     */
    public static void reverse(final short[] array) {
        if (refId(short[].class).eval() == null) {
            return;
        }
        reverse(refId(short[].class).eval(), (int) intVal().eval(), array.length);
    }

    /**
     * <p>
     * Reverses the order of the given array in the given range.
     *
     * <p>
     * This method does nothing for a {@code null} input array.
     *
     * @param array
     *            the array to reverse, may be {@code null}
     * @param startIndexInclusive
     *            the starting index. Undervalue (&lt;0) is promoted to 0, overvalue (&gt;array.length) results in no
     *            change.
     * @param endIndexExclusive
     *            elements up to endIndex-1 are reversed in the array. Undervalue (&lt; start index) results in no
     *            change. Overvalue (&gt;array.length) is demoted to array length.
     * @since 3.2
     */
    public static void reverse(final short[] array, final int startIndexInclusive, final int endIndexExclusive) {
        if (refId(short[].class).eval() == null) {
            return;
        }
        int i = Math.max((int) intId().eval(), (int) intVal().eval());
        int j = Math.min(array.length, (int) intId().eval()) - (int) intVal().eval();
        short tmp;
        int _jitmagicLoopLimiter62 = 0;
        while ((boolean) relation(intId(), intId()).eval() && _jitmagicLoopLimiter62++ < 1000) {
            tmp = (short) shortArrAccessExp(refId(short[].class), intId()).eval();
            array[j] = (short) shortArrAccessExp(refId(short[].class), intId()).eval();
            array[i] = (short) shortId().eval();
            j--;
            i++;
        }
    }

    /**
     * Shifts the order of the given boolean array.
     *
     * <p>There is no special handling for multi-dimensional arrays. This method
     * does nothing for {@code null} or empty input arrays.</p>
     *
     * @param array  the array to shift, may be {@code null}
     * @param offset
     *          The number of positions to rotate the elements.  If the offset is larger than the number of elements to
     *          rotate, than the effective offset is modulo the number of elements to rotate.
     * @since 3.5
     */
    public static void shift(final boolean[] array, final int offset) {
        if (refId(boolean[].class).eval() == null) {
            return;
        }
        shift(refId(boolean[].class).eval(), (int) intVal().eval(), array.length, (int) intId().eval());
    }

    /**
     * Shifts the order of a series of elements in the given boolean array.
     *
     * <p>There is no special handling for multi-dimensional arrays. This method
     * does nothing for {@code null} or empty input arrays.</p>
     *
     * @param array
     *            the array to shift, may be {@code null}
     * @param startIndexInclusive
     *            the starting index. Undervalue (&lt;0) is promoted to 0, overvalue (&gt;array.length) results in no
     *            change.
     * @param endIndexExclusive
     *            elements up to endIndex-1 are shifted in the array. Undervalue (&lt; start index) results in no
     *            change. Overvalue (&gt;array.length) is demoted to array length.
     * @param offset
     *          The number of positions to rotate the elements.  If the offset is larger than the number of elements to
     *          rotate, than the effective offset is modulo the number of elements to rotate.
     * @since 3.5
     */
    public static void shift(final boolean[] array, int startIndexInclusive, int endIndexExclusive, int offset) {
        if ((refId(boolean[].class).eval() == null) || (int) intId().eval() >= array.length - (int) intVal().eval() || (boolean) relation(intId(), intVal()).eval()) {
            return;
        }
        if ((boolean) relation(intId(), intVal()).eval()) {
            startIndexInclusive = (int) intVal().eval();
        }
        if ((int) intId().eval() >= array.length) {
            endIndexExclusive = array.length;
        }
        int n = (int) arithmetic(intId(), intId()).eval();
        if ((boolean) relation(intId(), intVal()).eval()) {
            return;
        }
        offset %= (int) intId().eval();
        if ((boolean) relation(intId(), intVal()).eval()) {
            offset += (int) intId().eval();
        }
        int _jitmagicLoopLimiter63 = 0;
        // For algorithm explanations and proof of O(n) time complexity and O(1) space complexity
        // see https://beradrian.wordpress.com/2015/04/07/shift-an-array-in-on-in-place/
        while ((boolean) logic(relation(intId(), intVal()), relation(intId(), intVal())).eval() && _jitmagicLoopLimiter63++ < 1000) {
            final int n_offset = (int) arithmetic(intId(), intId()).eval();
            if ((boolean) relation(intId(), intId()).eval()) {
                swap(refId(boolean[].class).eval(), (int) intId().eval(), (int) arithmetic(arithmetic(intId(), intId()), intId()).eval(), (int) intId().eval());
                n = (int) intId().eval();
                offset -= (int) intId().eval();
            } else if ((boolean) relation(intId(), intId()).eval()) {
                swap(refId(boolean[].class).eval(), (int) intId().eval(), (int) arithmetic(intId(), intId()).eval(), (int) intId().eval());
                startIndexInclusive += (int) intId().eval();
                n = (int) intId().eval();
            } else {
                swap(refId(boolean[].class).eval(), (int) intId().eval(), (int) arithmetic(intId(), intId()).eval(), (int) intId().eval());
                break;
            }
        }
    }

    /**
     * Shifts the order of the given byte array.
     *
     * <p>There is no special handling for multi-dimensional arrays. This method
     * does nothing for {@code null} or empty input arrays.</p>
     *
     * @param array  the array to shift, may be {@code null}
     * @param offset
     *          The number of positions to rotate the elements.  If the offset is larger than the number of elements to
     *          rotate, than the effective offset is modulo the number of elements to rotate.
     * @since 3.5
     */
    public static void shift(final byte[] array, final int offset) {
        if (refId(byte[].class).eval() == null) {
            return;
        }
        shift(refId(byte[].class).eval(), (int) intVal().eval(), array.length, (int) intId().eval());
    }

    /**
     * Shifts the order of a series of elements in the given byte array.
     *
     * <p>There is no special handling for multi-dimensional arrays. This method
     * does nothing for {@code null} or empty input arrays.</p>
     *
     * @param array
     *            the array to shift, may be {@code null}
     * @param startIndexInclusive
     *            the starting index. Undervalue (&lt;0) is promoted to 0, overvalue (&gt;array.length) results in no
     *            change.
     * @param endIndexExclusive
     *            elements up to endIndex-1 are shifted in the array. Undervalue (&lt; start index) results in no
     *            change. Overvalue (&gt;array.length) is demoted to array length.
     * @param offset
     *          The number of positions to rotate the elements.  If the offset is larger than the number of elements to
     *          rotate, than the effective offset is modulo the number of elements to rotate.
     * @since 3.5
     */
    public static void shift(final byte[] array, int startIndexInclusive, int endIndexExclusive, int offset) {
        if ((refId(byte[].class).eval() == null) || (int) intId().eval() >= array.length - (int) intVal().eval() || (boolean) relation(intId(), intVal()).eval()) {
            return;
        }
        if ((boolean) relation(intId(), intVal()).eval()) {
            startIndexInclusive = (int) intVal().eval();
        }
        if ((int) intId().eval() >= array.length) {
            endIndexExclusive = array.length;
        }
        int n = (int) arithmetic(intId(), intId()).eval();
        if ((boolean) relation(intId(), intVal()).eval()) {
            return;
        }
        offset %= (int) intId().eval();
        if ((boolean) relation(intId(), intVal()).eval()) {
            offset += (int) intId().eval();
        }
        int _jitmagicLoopLimiter64 = 0;
        // For algorithm explanations and proof of O(n) time complexity and O(1) space complexity
        // see https://beradrian.wordpress.com/2015/04/07/shift-an-array-in-on-in-place/
        while ((boolean) logic(relation(intId(), intVal()), relation(intId(), intVal())).eval() && _jitmagicLoopLimiter64++ < 1000) {
            final int n_offset = (int) arithmetic(intId(), intId()).eval();
            if ((boolean) relation(intId(), intId()).eval()) {
                swap(refId(byte[].class).eval(), (int) intId().eval(), (int) arithmetic(arithmetic(intId(), intId()), intId()).eval(), (int) intId().eval());
                n = (int) intId().eval();
                offset -= (int) intId().eval();
            } else if ((boolean) relation(intId(), intId()).eval()) {
                swap(refId(byte[].class).eval(), (int) intId().eval(), (int) arithmetic(intId(), intId()).eval(), (int) intId().eval());
                startIndexInclusive += (int) intId().eval();
                n = (int) intId().eval();
            } else {
                swap(refId(byte[].class).eval(), (int) intId().eval(), (int) arithmetic(intId(), intId()).eval(), (int) intId().eval());
                break;
            }
        }
    }

    /**
     * Shifts the order of the given char array.
     *
     * <p>There is no special handling for multi-dimensional arrays. This method
     * does nothing for {@code null} or empty input arrays.</p>
     *
     * @param array  the array to shift, may be {@code null}
     * @param offset
     *          The number of positions to rotate the elements.  If the offset is larger than the number of elements to
     *          rotate, than the effective offset is modulo the number of elements to rotate.
     * @since 3.5
     */
    public static void shift(final char[] array, final int offset) {
        if (refId(char[].class).eval() == null) {
            return;
        }
        shift(refId(char[].class).eval(), (int) intVal().eval(), array.length, (int) intId().eval());
    }

    /**
     * Shifts the order of a series of elements in the given char array.
     *
     * <p>There is no special handling for multi-dimensional arrays. This method
     * does nothing for {@code null} or empty input arrays.</p>
     *
     * @param array
     *            the array to shift, may be {@code null}
     * @param startIndexInclusive
     *            the starting index. Undervalue (&lt;0) is promoted to 0, overvalue (&gt;array.length) results in no
     *            change.
     * @param endIndexExclusive
     *            elements up to endIndex-1 are shifted in the array. Undervalue (&lt; start index) results in no
     *            change. Overvalue (&gt;array.length) is demoted to array length.
     * @param offset
     *          The number of positions to rotate the elements.  If the offset is larger than the number of elements to
     *          rotate, than the effective offset is modulo the number of elements to rotate.
     * @since 3.5
     */
    public static void shift(final char[] array, int startIndexInclusive, int endIndexExclusive, int offset) {
        if ((refId(char[].class).eval() == null) || (int) intId().eval() >= array.length - (int) intVal().eval() || (boolean) relation(intId(), intVal()).eval()) {
            return;
        }
        if ((boolean) relation(intId(), intVal()).eval()) {
            startIndexInclusive = (int) intVal().eval();
        }
        if ((int) intId().eval() >= array.length) {
            endIndexExclusive = array.length;
        }
        int n = (int) arithmetic(intId(), intId()).eval();
        if ((boolean) relation(intId(), intVal()).eval()) {
            return;
        }
        offset %= (int) intId().eval();
        if ((boolean) relation(intId(), intVal()).eval()) {
            offset += (int) intId().eval();
        }
        int _jitmagicLoopLimiter65 = 0;
        // For algorithm explanations and proof of O(n) time complexity and O(1) space complexity
        // see https://beradrian.wordpress.com/2015/04/07/shift-an-array-in-on-in-place/
        while ((boolean) logic(relation(intId(), intVal()), relation(intId(), intVal())).eval() && _jitmagicLoopLimiter65++ < 1000) {
            final int n_offset = (int) arithmetic(intId(), intId()).eval();
            if ((boolean) relation(intId(), intId()).eval()) {
                swap(refId(char[].class).eval(), (int) intId().eval(), (int) arithmetic(arithmetic(intId(), intId()), intId()).eval(), (int) intId().eval());
                n = (int) intId().eval();
                offset -= (int) intId().eval();
            } else if ((boolean) relation(intId(), intId()).eval()) {
                swap(refId(char[].class).eval(), (int) intId().eval(), (int) arithmetic(intId(), intId()).eval(), (int) intId().eval());
                startIndexInclusive += (int) intId().eval();
                n = (int) intId().eval();
            } else {
                swap(refId(char[].class).eval(), (int) intId().eval(), (int) arithmetic(intId(), intId()).eval(), (int) intId().eval());
                break;
            }
        }
    }

    /**
     * Shifts the order of the given double array.
     *
     * <p>There is no special handling for multi-dimensional arrays. This method
     * does nothing for {@code null} or empty input arrays.</p>
     *
     * @param array  the array to shift, may be {@code null}
     * @param offset
     *          The number of positions to rotate the elements.  If the offset is larger than the number of elements to
     *          rotate, than the effective offset is modulo the number of elements to rotate.
     * @since 3.5
     */
    public static void shift(final double[] array, final int offset) {
        if (refId(double[].class).eval() == null) {
            return;
        }
        shift(refId(double[].class).eval(), (int) intVal().eval(), array.length, (int) intId().eval());
    }

    /**
     * Shifts the order of a series of elements in the given double array.
     *
     * <p>There is no special handling for multi-dimensional arrays. This method
     * does nothing for {@code null} or empty input arrays.</p>
     *
     * @param array
     *            the array to shift, may be {@code null}
     * @param startIndexInclusive
     *            the starting index. Undervalue (&lt;0) is promoted to 0, overvalue (&gt;array.length) results in no
     *            change.
     * @param endIndexExclusive
     *            elements up to endIndex-1 are shifted in the array. Undervalue (&lt; start index) results in no
     *            change. Overvalue (&gt;array.length) is demoted to array length.
     * @param offset
     *          The number of positions to rotate the elements.  If the offset is larger than the number of elements to
     *          rotate, than the effective offset is modulo the number of elements to rotate.
     * @since 3.5
     */
    public static void shift(final double[] array, int startIndexInclusive, int endIndexExclusive, int offset) {
        if ((refId(double[].class).eval() == null) || (int) intId().eval() >= array.length - (int) intVal().eval() || (boolean) relation(intId(), intVal()).eval()) {
            return;
        }
        if ((boolean) relation(intId(), intVal()).eval()) {
            startIndexInclusive = (int) intVal().eval();
        }
        if ((int) intId().eval() >= array.length) {
            endIndexExclusive = array.length;
        }
        int n = (int) arithmetic(intId(), intId()).eval();
        if ((boolean) relation(intId(), intVal()).eval()) {
            return;
        }
        offset %= (int) intId().eval();
        if ((boolean) relation(intId(), intVal()).eval()) {
            offset += (int) intId().eval();
        }
        int _jitmagicLoopLimiter66 = 0;
        // For algorithm explanations and proof of O(n) time complexity and O(1) space complexity
        // see https://beradrian.wordpress.com/2015/04/07/shift-an-array-in-on-in-place/
        while ((boolean) logic(relation(intId(), intVal()), relation(intId(), intVal())).eval() && _jitmagicLoopLimiter66++ < 1000) {
            final int n_offset = (int) arithmetic(intId(), intId()).eval();
            if ((boolean) relation(intId(), intId()).eval()) {
                swap(refId(double[].class).eval(), (int) intId().eval(), (int) arithmetic(arithmetic(intId(), intId()), intId()).eval(), (int) intId().eval());
                n = (int) intId().eval();
                offset -= (int) intId().eval();
            } else if ((boolean) relation(intId(), intId()).eval()) {
                swap(refId(double[].class).eval(), (int) intId().eval(), (int) arithmetic(intId(), intId()).eval(), (int) intId().eval());
                startIndexInclusive += (int) intId().eval();
                n = (int) intId().eval();
            } else {
                swap(refId(double[].class).eval(), (int) intId().eval(), (int) arithmetic(intId(), intId()).eval(), (int) intId().eval());
                break;
            }
        }
    }

    /**
     * Shifts the order of the given float array.
     *
     * <p>There is no special handling for multi-dimensional arrays. This method
     * does nothing for {@code null} or empty input arrays.</p>
     *
     * @param array  the array to shift, may be {@code null}
     * @param offset
     *          The number of positions to rotate the elements.  If the offset is larger than the number of elements to
     *          rotate, than the effective offset is modulo the number of elements to rotate.
     * @since 3.5
     */
    public static void shift(final float[] array, final int offset) {
        if (refId(float[].class).eval() == null) {
            return;
        }
        shift(refId(float[].class).eval(), (int) intVal().eval(), array.length, (int) intId().eval());
    }

    /**
     * Shifts the order of a series of elements in the given float array.
     *
     * <p>There is no special handling for multi-dimensional arrays. This method
     * does nothing for {@code null} or empty input arrays.</p>
     *
     * @param array
     *            the array to shift, may be {@code null}
     * @param startIndexInclusive
     *            the starting index. Undervalue (&lt;0) is promoted to 0, overvalue (&gt;array.length) results in no
     *            change.
     * @param endIndexExclusive
     *            elements up to endIndex-1 are shifted in the array. Undervalue (&lt; start index) results in no
     *            change. Overvalue (&gt;array.length) is demoted to array length.
     * @param offset
     *          The number of positions to rotate the elements.  If the offset is larger than the number of elements to
     *          rotate, than the effective offset is modulo the number of elements to rotate.
     * @since 3.5
     */
    public static void shift(final float[] array, int startIndexInclusive, int endIndexExclusive, int offset) {
        if ((refId(float[].class).eval() == null) || (int) intId().eval() >= array.length - (int) intVal().eval() || (boolean) relation(intId(), intVal()).eval()) {
            return;
        }
        if ((boolean) relation(intId(), intVal()).eval()) {
            startIndexInclusive = (int) intVal().eval();
        }
        if ((int) intId().eval() >= array.length) {
            endIndexExclusive = array.length;
        }
        int n = (int) arithmetic(intId(), intId()).eval();
        if ((boolean) relation(intId(), intVal()).eval()) {
            return;
        }
        offset %= (int) intId().eval();
        if ((boolean) relation(intId(), intVal()).eval()) {
            offset += (int) intId().eval();
        }
        int _jitmagicLoopLimiter67 = 0;
        // For algorithm explanations and proof of O(n) time complexity and O(1) space complexity
        // see https://beradrian.wordpress.com/2015/04/07/shift-an-array-in-on-in-place/
        while ((boolean) logic(relation(intId(), intVal()), relation(intId(), intVal())).eval() && _jitmagicLoopLimiter67++ < 1000) {
            final int n_offset = (int) arithmetic(intId(), intId()).eval();
            if ((boolean) relation(intId(), intId()).eval()) {
                swap(refId(float[].class).eval(), (int) intId().eval(), (int) arithmetic(arithmetic(intId(), intId()), intId()).eval(), (int) intId().eval());
                n = (int) intId().eval();
                offset -= (int) intId().eval();
            } else if ((boolean) relation(intId(), intId()).eval()) {
                swap(refId(float[].class).eval(), (int) intId().eval(), (int) arithmetic(intId(), intId()).eval(), (int) intId().eval());
                startIndexInclusive += (int) intId().eval();
                n = (int) intId().eval();
            } else {
                swap(refId(float[].class).eval(), (int) intId().eval(), (int) arithmetic(intId(), intId()).eval(), (int) intId().eval());
                break;
            }
        }
    }

    /**
     * Shifts the order of the given int array.
     *
     * <p>There is no special handling for multi-dimensional arrays. This method
     * does nothing for {@code null} or empty input arrays.</p>
     *
     * @param array  the array to shift, may be {@code null}
     * @param offset
     *          The number of positions to rotate the elements.  If the offset is larger than the number of elements to
     *          rotate, than the effective offset is modulo the number of elements to rotate.
     * @since 3.5
     */
    public static void shift(final int[] array, final int offset) {
        if (refId(int[].class).eval() == null) {
            return;
        }
        shift(refId(int[].class).eval(), (int) intVal().eval(), array.length, (int) intId().eval());
    }

    /**
     * Shifts the order of a series of elements in the given int array.
     *
     * <p>There is no special handling for multi-dimensional arrays. This method
     * does nothing for {@code null} or empty input arrays.</p>
     *
     * @param array
     *            the array to shift, may be {@code null}
     * @param startIndexInclusive
     *            the starting index. Undervalue (&lt;0) is promoted to 0, overvalue (&gt;array.length) results in no
     *            change.
     * @param endIndexExclusive
     *            elements up to endIndex-1 are shifted in the array. Undervalue (&lt; start index) results in no
     *            change. Overvalue (&gt;array.length) is demoted to array length.
     * @param offset
     *          The number of positions to rotate the elements.  If the offset is larger than the number of elements to
     *          rotate, than the effective offset is modulo the number of elements to rotate.
     * @since 3.5
     */
    public static void shift(final int[] array, int startIndexInclusive, int endIndexExclusive, int offset) {
        if ((refId(int[].class).eval() == null) || (int) intId().eval() >= array.length - (int) intVal().eval() || (boolean) relation(intId(), intVal()).eval()) {
            return;
        }
        if ((boolean) relation(intId(), intVal()).eval()) {
            startIndexInclusive = (int) intVal().eval();
        }
        if ((int) intId().eval() >= array.length) {
            endIndexExclusive = array.length;
        }
        int n = (int) arithmetic(intId(), intId()).eval();
        if ((boolean) relation(intId(), intVal()).eval()) {
            return;
        }
        offset %= (int) intId().eval();
        if ((boolean) relation(intId(), intVal()).eval()) {
            offset += (int) intId().eval();
        }
        int _jitmagicLoopLimiter68 = 0;
        // For algorithm explanations and proof of O(n) time complexity and O(1) space complexity
        // see https://beradrian.wordpress.com/2015/04/07/shift-an-array-in-on-in-place/
        while ((boolean) logic(relation(intId(), intVal()), relation(intId(), intVal())).eval() && _jitmagicLoopLimiter68++ < 1000) {
            final int n_offset = (int) arithmetic(intId(), intId()).eval();
            if ((boolean) relation(intId(), intId()).eval()) {
                swap(refId(int[].class).eval(), (int) intId().eval(), (int) arithmetic(arithmetic(intId(), intId()), intId()).eval(), (int) intId().eval());
                n = (int) intId().eval();
                offset -= (int) intId().eval();
            } else if ((boolean) relation(intId(), intId()).eval()) {
                swap(refId(int[].class).eval(), (int) intId().eval(), (int) arithmetic(intId(), intId()).eval(), (int) intId().eval());
                startIndexInclusive += (int) intId().eval();
                n = (int) intId().eval();
            } else {
                swap(refId(int[].class).eval(), (int) intId().eval(), (int) arithmetic(intId(), intId()).eval(), (int) intId().eval());
                break;
            }
        }
    }

    /**
     * Shifts the order of the given long array.
     *
     * <p>There is no special handling for multi-dimensional arrays. This method
     * does nothing for {@code null} or empty input arrays.</p>
     *
     * @param array  the array to shift, may be {@code null}
     * @param offset
     *          The number of positions to rotate the elements.  If the offset is larger than the number of elements to
     *          rotate, than the effective offset is modulo the number of elements to rotate.
     * @since 3.5
     */
    public static void shift(final long[] array, final int offset) {
        if (refId(long[].class).eval() == null) {
            return;
        }
        shift(refId(long[].class).eval(), (int) intVal().eval(), array.length, (int) intId().eval());
    }

    /**
     * Shifts the order of a series of elements in the given long array.
     *
     * <p>There is no special handling for multi-dimensional arrays. This method
     * does nothing for {@code null} or empty input arrays.</p>
     *
     * @param array
     *            the array to shift, may be {@code null}
     * @param startIndexInclusive
     *            the starting index. Undervalue (&lt;0) is promoted to 0, overvalue (&gt;array.length) results in no
     *            change.
     * @param endIndexExclusive
     *            elements up to endIndex-1 are shifted in the array. Undervalue (&lt; start index) results in no
     *            change. Overvalue (&gt;array.length) is demoted to array length.
     * @param offset
     *          The number of positions to rotate the elements.  If the offset is larger than the number of elements to
     *          rotate, than the effective offset is modulo the number of elements to rotate.
     * @since 3.5
     */
    public static void shift(final long[] array, int startIndexInclusive, int endIndexExclusive, int offset) {
        if ((refId(long[].class).eval() == null) || (int) intId().eval() >= array.length - (int) intVal().eval() || (boolean) relation(intId(), intVal()).eval()) {
            return;
        }
        if ((boolean) relation(intId(), intVal()).eval()) {
            startIndexInclusive = (int) intVal().eval();
        }
        if ((int) intId().eval() >= array.length) {
            endIndexExclusive = array.length;
        }
        int n = (int) arithmetic(intId(), intId()).eval();
        if ((boolean) relation(intId(), intVal()).eval()) {
            return;
        }
        offset %= (int) intId().eval();
        if ((boolean) relation(intId(), intVal()).eval()) {
            offset += (int) intId().eval();
        }
        int _jitmagicLoopLimiter69 = 0;
        // For algorithm explanations and proof of O(n) time complexity and O(1) space complexity
        // see https://beradrian.wordpress.com/2015/04/07/shift-an-array-in-on-in-place/
        while ((boolean) logic(relation(intId(), intVal()), relation(intId(), intVal())).eval() && _jitmagicLoopLimiter69++ < 1000) {
            final int n_offset = (int) arithmetic(intId(), intId()).eval();
            if ((boolean) relation(intId(), intId()).eval()) {
                swap(refId(long[].class).eval(), (int) intId().eval(), (int) arithmetic(arithmetic(intId(), intId()), intId()).eval(), (int) intId().eval());
                n = (int) intId().eval();
                offset -= (int) intId().eval();
            } else if ((boolean) relation(intId(), intId()).eval()) {
                swap(refId(long[].class).eval(), (int) intId().eval(), (int) arithmetic(intId(), intId()).eval(), (int) intId().eval());
                startIndexInclusive += (int) intId().eval();
                n = (int) intId().eval();
            } else {
                swap(refId(long[].class).eval(), (int) intId().eval(), (int) arithmetic(intId(), intId()).eval(), (int) intId().eval());
                break;
            }
        }
    }

    // Shift
    //-----------------------------------------------------------------------
    /**
     * Shifts the order of the given array.
     *
     * <p>There is no special handling for multi-dimensional arrays. This method
     * does nothing for {@code null} or empty input arrays.</p>
     *
     * @param array  the array to shift, may be {@code null}
     * @param offset
     *          The number of positions to rotate the elements.  If the offset is larger than the number of elements to
     *          rotate, than the effective offset is modulo the number of elements to rotate.
     * @since 3.5
     */
    public static void shift(final Object[] array, final int offset) {
        if (refId(java.lang.Object[].class).eval() == null) {
            return;
        }
        shift(refId(java.lang.Object[].class).eval(), (int) intVal().eval(), array.length, (int) intId().eval());
    }

    /**
     * Shifts the order of a series of elements in the given array.
     *
     * <p>There is no special handling for multi-dimensional arrays. This method
     * does nothing for {@code null} or empty input arrays.</p>
     *
     * @param array
     *            the array to shift, may be {@code null}
     * @param startIndexInclusive
     *            the starting index. Undervalue (&lt;0) is promoted to 0, overvalue (&gt;array.length) results in no
     *            change.
     * @param endIndexExclusive
     *            elements up to endIndex-1 are shifted in the array. Undervalue (&lt; start index) results in no
     *            change. Overvalue (&gt;array.length) is demoted to array length.
     * @param offset
     *          The number of positions to rotate the elements.  If the offset is larger than the number of elements to
     *          rotate, than the effective offset is modulo the number of elements to rotate.
     * @since 3.5
     */
    public static void shift(final Object[] array, int startIndexInclusive, int endIndexExclusive, int offset) {
        if ((refId(java.lang.Object[].class).eval() == null) || (int) intId().eval() >= array.length - (int) intVal().eval() || (boolean) relation(intId(), intVal()).eval()) {
            return;
        }
        if ((boolean) relation(intId(), intVal()).eval()) {
            startIndexInclusive = (int) intVal().eval();
        }
        if ((int) intId().eval() >= array.length) {
            endIndexExclusive = array.length;
        }
        int n = (int) arithmetic(intId(), intId()).eval();
        if ((boolean) relation(intId(), intVal()).eval()) {
            return;
        }
        offset %= (int) intId().eval();
        if ((boolean) relation(intId(), intVal()).eval()) {
            offset += (int) intId().eval();
        }
        int _jitmagicLoopLimiter70 = 0;
        // For algorithm explanations and proof of O(n) time complexity and O(1) space complexity
        // see https://beradrian.wordpress.com/2015/04/07/shift-an-array-in-on-in-place/
        while ((boolean) logic(relation(intId(), intVal()), relation(intId(), intVal())).eval() && _jitmagicLoopLimiter70++ < 1000) {
            final int n_offset = (int) arithmetic(intId(), intId()).eval();
            if ((boolean) relation(intId(), intId()).eval()) {
                swap(refId(java.lang.Object[].class).eval(), (int) intId().eval(), (int) arithmetic(arithmetic(intId(), intId()), intId()).eval(), (int) intId().eval());
                n = (int) intId().eval();
                offset -= (int) intId().eval();
            } else if ((boolean) relation(intId(), intId()).eval()) {
                swap(refId(java.lang.Object[].class).eval(), (int) intId().eval(), (int) arithmetic(intId(), intId()).eval(), (int) intId().eval());
                startIndexInclusive += (int) intId().eval();
                n = (int) intId().eval();
            } else {
                swap(refId(java.lang.Object[].class).eval(), (int) intId().eval(), (int) arithmetic(intId(), intId()).eval(), (int) intId().eval());
                break;
            }
        }
    }

    /**
     * Shifts the order of the given short array.
     *
     * <p>There is no special handling for multi-dimensional arrays. This method
     * does nothing for {@code null} or empty input arrays.</p>
     *
     * @param array  the array to shift, may be {@code null}
     * @param offset
     *          The number of positions to rotate the elements.  If the offset is larger than the number of elements to
     *          rotate, than the effective offset is modulo the number of elements to rotate.
     * @since 3.5
     */
    public static void shift(final short[] array, final int offset) {
        if (refId(short[].class).eval() == null) {
            return;
        }
        shift(refId(short[].class).eval(), (int) intVal().eval(), array.length, (int) intId().eval());
    }

    /**
     * Shifts the order of a series of elements in the given short array.
     *
     * <p>There is no special handling for multi-dimensional arrays. This method
     * does nothing for {@code null} or empty input arrays.</p>
     *
     * @param array
     *            the array to shift, may be {@code null}
     * @param startIndexInclusive
     *            the starting index. Undervalue (&lt;0) is promoted to 0, overvalue (&gt;array.length) results in no
     *            change.
     * @param endIndexExclusive
     *            elements up to endIndex-1 are shifted in the array. Undervalue (&lt; start index) results in no
     *            change. Overvalue (&gt;array.length) is demoted to array length.
     * @param offset
     *          The number of positions to rotate the elements.  If the offset is larger than the number of elements to
     *          rotate, than the effective offset is modulo the number of elements to rotate.
     * @since 3.5
     */
    public static void shift(final short[] array, int startIndexInclusive, int endIndexExclusive, int offset) {
        if ((refId(short[].class).eval() == null) || (int) intId().eval() >= array.length - (int) intVal().eval() || (boolean) relation(intId(), intVal()).eval()) {
            return;
        }
        if ((boolean) relation(intId(), intVal()).eval()) {
            startIndexInclusive = (int) intVal().eval();
        }
        if ((int) intId().eval() >= array.length) {
            endIndexExclusive = array.length;
        }
        int n = (int) arithmetic(intId(), intId()).eval();
        if ((boolean) relation(intId(), intVal()).eval()) {
            return;
        }
        offset %= (int) intId().eval();
        if ((boolean) relation(intId(), intVal()).eval()) {
            offset += (int) intId().eval();
        }
        int _jitmagicLoopLimiter71 = 0;
        // For algorithm explanations and proof of O(n) time complexity and O(1) space complexity
        // see https://beradrian.wordpress.com/2015/04/07/shift-an-array-in-on-in-place/
        while ((boolean) logic(relation(intId(), intVal()), relation(intId(), intVal())).eval() && _jitmagicLoopLimiter71++ < 1000) {
            final int n_offset = (int) arithmetic(intId(), intId()).eval();
            if ((boolean) relation(intId(), intId()).eval()) {
                swap(refId(short[].class).eval(), (int) intId().eval(), (int) arithmetic(arithmetic(intId(), intId()), intId()).eval(), (int) intId().eval());
                n = (int) intId().eval();
                offset -= (int) intId().eval();
            } else if ((boolean) relation(intId(), intId()).eval()) {
                swap(refId(short[].class).eval(), (int) intId().eval(), (int) arithmetic(intId(), intId()).eval(), (int) intId().eval());
                startIndexInclusive += (int) intId().eval();
                n = (int) intId().eval();
            } else {
                swap(refId(short[].class).eval(), (int) intId().eval(), (int) arithmetic(intId(), intId()).eval(), (int) intId().eval());
                break;
            }
        }
    }

    /**
     * Randomly permutes the elements of the specified array using the Fisher-Yates algorithm.
     *
     * @param array   the array to shuffle
     * @see <a href="https://en.wikipedia.org/wiki/Fisher%E2%80%93Yates_shuffle">Fisher-Yates shuffle algorithm</a>
     * @since 3.6
     */
    public static void shuffle(final boolean[] array) {
        shuffle(refId(boolean[].class).eval(), random());
    }

    /**
     * Randomly permutes the elements of the specified array using the Fisher-Yates algorithm.
     *
     * @param array   the array to shuffle
     * @param random  the source of randomness used to permute the elements
     * @see <a href="https://en.wikipedia.org/wiki/Fisher%E2%80%93Yates_shuffle">Fisher-Yates shuffle algorithm</a>
     * @since 3.6
     */
    public static void shuffle(final boolean[] array, final Random random) {
        int _jitmagicLoopLimiter72 = 0;
        for (int i = array.length; (boolean) relation(intId(), intVal()).eval() && _jitmagicLoopLimiter72++ < 1000; i--) {
            swap(refId(boolean[].class).eval(), (int) arithmetic(intId(), intVal()).eval(), refId(java.util.Random.class).eval().nextInt((int) intId().eval()), (int) intVal().eval());
        }
    }

    /**
     * Randomly permutes the elements of the specified array using the Fisher-Yates algorithm.
     *
     * @param array   the array to shuffle
     * @see <a href="https://en.wikipedia.org/wiki/Fisher%E2%80%93Yates_shuffle">Fisher-Yates shuffle algorithm</a>
     * @since 3.6
     */
    public static void shuffle(final byte[] array) {
        shuffle(refId(byte[].class).eval(), random());
    }

    /**
     * Randomly permutes the elements of the specified array using the Fisher-Yates algorithm.
     *
     * @param array   the array to shuffle
     * @param random  the source of randomness used to permute the elements
     * @see <a href="https://en.wikipedia.org/wiki/Fisher%E2%80%93Yates_shuffle">Fisher-Yates shuffle algorithm</a>
     * @since 3.6
     */
    public static void shuffle(final byte[] array, final Random random) {
        int _jitmagicLoopLimiter73 = 0;
        for (int i = array.length; (boolean) relation(intId(), intVal()).eval() && _jitmagicLoopLimiter73++ < 1000; i--) {
            swap(refId(byte[].class).eval(), (int) arithmetic(intId(), intVal()).eval(), refId(java.util.Random.class).eval().nextInt((int) intId().eval()), (int) intVal().eval());
        }
    }

    /**
     * Randomly permutes the elements of the specified array using the Fisher-Yates algorithm.
     *
     * @param array   the array to shuffle
     * @see <a href="https://en.wikipedia.org/wiki/Fisher%E2%80%93Yates_shuffle">Fisher-Yates shuffle algorithm</a>
     * @since 3.6
     */
    public static void shuffle(final char[] array) {
        shuffle(refId(char[].class).eval(), random());
    }

    /**
     * Randomly permutes the elements of the specified array using the Fisher-Yates algorithm.
     *
     * @param array   the array to shuffle
     * @param random  the source of randomness used to permute the elements
     * @see <a href="https://en.wikipedia.org/wiki/Fisher%E2%80%93Yates_shuffle">Fisher-Yates shuffle algorithm</a>
     * @since 3.6
     */
    public static void shuffle(final char[] array, final Random random) {
        int _jitmagicLoopLimiter74 = 0;
        for (int i = array.length; (boolean) relation(intId(), intVal()).eval() && _jitmagicLoopLimiter74++ < 1000; i--) {
            swap(refId(char[].class).eval(), (int) arithmetic(intId(), intVal()).eval(), refId(java.util.Random.class).eval().nextInt((int) intId().eval()), (int) intVal().eval());
        }
    }

    /**
     * Randomly permutes the elements of the specified array using the Fisher-Yates algorithm.
     *
     * @param array   the array to shuffle
     * @see <a href="https://en.wikipedia.org/wiki/Fisher%E2%80%93Yates_shuffle">Fisher-Yates shuffle algorithm</a>
     * @since 3.6
     */
    public static void shuffle(final double[] array) {
        shuffle(refId(double[].class).eval(), random());
    }

    /**
     * Randomly permutes the elements of the specified array using the Fisher-Yates algorithm.
     *
     * @param array   the array to shuffle
     * @param random  the source of randomness used to permute the elements
     * @see <a href="https://en.wikipedia.org/wiki/Fisher%E2%80%93Yates_shuffle">Fisher-Yates shuffle algorithm</a>
     * @since 3.6
     */
    public static void shuffle(final double[] array, final Random random) {
        int _jitmagicLoopLimiter75 = 0;
        for (int i = array.length; (boolean) relation(intId(), intVal()).eval() && _jitmagicLoopLimiter75++ < 1000; i--) {
            swap(refId(double[].class).eval(), (int) arithmetic(intId(), intVal()).eval(), refId(java.util.Random.class).eval().nextInt((int) intId().eval()), (int) intVal().eval());
        }
    }

    /**
     * Randomly permutes the elements of the specified array using the Fisher-Yates algorithm.
     *
     * @param array   the array to shuffle
     * @see <a href="https://en.wikipedia.org/wiki/Fisher%E2%80%93Yates_shuffle">Fisher-Yates shuffle algorithm</a>
     * @since 3.6
     */
    public static void shuffle(final float[] array) {
        shuffle(refId(float[].class).eval(), random());
    }

    /**
     * Randomly permutes the elements of the specified array using the Fisher-Yates algorithm.
     *
     * @param array   the array to shuffle
     * @param random  the source of randomness used to permute the elements
     * @see <a href="https://en.wikipedia.org/wiki/Fisher%E2%80%93Yates_shuffle">Fisher-Yates shuffle algorithm</a>
     * @since 3.6
     */
    public static void shuffle(final float[] array, final Random random) {
        int _jitmagicLoopLimiter76 = 0;
        for (int i = array.length; (boolean) relation(intId(), intVal()).eval() && _jitmagicLoopLimiter76++ < 1000; i--) {
            swap(refId(float[].class).eval(), (int) arithmetic(intId(), intVal()).eval(), refId(java.util.Random.class).eval().nextInt((int) intId().eval()), (int) intVal().eval());
        }
    }

    /**
     * Randomly permutes the elements of the specified array using the Fisher-Yates algorithm.
     *
     * @param array   the array to shuffle
     * @see <a href="https://en.wikipedia.org/wiki/Fisher%E2%80%93Yates_shuffle">Fisher-Yates shuffle algorithm</a>
     * @since 3.6
     */
    public static void shuffle(final int[] array) {
        shuffle(refId(int[].class).eval(), random());
    }

    /**
     * Randomly permutes the elements of the specified array using the Fisher-Yates algorithm.
     *
     * @param array   the array to shuffle
     * @param random  the source of randomness used to permute the elements
     * @see <a href="https://en.wikipedia.org/wiki/Fisher%E2%80%93Yates_shuffle">Fisher-Yates shuffle algorithm</a>
     * @since 3.6
     */
    public static void shuffle(final int[] array, final Random random) {
        int _jitmagicLoopLimiter77 = 0;
        for (int i = array.length; (boolean) relation(intId(), intVal()).eval() && _jitmagicLoopLimiter77++ < 1000; i--) {
            swap(refId(int[].class).eval(), (int) arithmetic(intId(), intVal()).eval(), refId(java.util.Random.class).eval().nextInt((int) intId().eval()), (int) intVal().eval());
        }
    }

    /**
     * Randomly permutes the elements of the specified array using the Fisher-Yates algorithm.
     *
     * @param array   the array to shuffle
     * @see <a href="https://en.wikipedia.org/wiki/Fisher%E2%80%93Yates_shuffle">Fisher-Yates shuffle algorithm</a>
     * @since 3.6
     */
    public static void shuffle(final long[] array) {
        shuffle(refId(long[].class).eval(), random());
    }

    /**
     * Randomly permutes the elements of the specified array using the Fisher-Yates algorithm.
     *
     * @param array   the array to shuffle
     * @param random  the source of randomness used to permute the elements
     * @see <a href="https://en.wikipedia.org/wiki/Fisher%E2%80%93Yates_shuffle">Fisher-Yates shuffle algorithm</a>
     * @since 3.6
     */
    public static void shuffle(final long[] array, final Random random) {
        int _jitmagicLoopLimiter78 = 0;
        for (int i = array.length; (boolean) relation(intId(), intVal()).eval() && _jitmagicLoopLimiter78++ < 1000; i--) {
            swap(refId(long[].class).eval(), (int) arithmetic(intId(), intVal()).eval(), refId(java.util.Random.class).eval().nextInt((int) intId().eval()), (int) intVal().eval());
        }
    }

    /**
     * Randomly permutes the elements of the specified array using the Fisher-Yates algorithm.
     *
     * @param array   the array to shuffle
     * @see <a href="https://en.wikipedia.org/wiki/Fisher%E2%80%93Yates_shuffle">Fisher-Yates shuffle algorithm</a>
     * @since 3.6
     */
    public static void shuffle(final Object[] array) {
        shuffle(refId(java.lang.Object[].class).eval(), random());
    }

    /**
     * Randomly permutes the elements of the specified array using the Fisher-Yates algorithm.
     *
     * @param array   the array to shuffle
     * @param random  the source of randomness used to permute the elements
     * @see <a href="https://en.wikipedia.org/wiki/Fisher%E2%80%93Yates_shuffle">Fisher-Yates shuffle algorithm</a>
     * @since 3.6
     */
    public static void shuffle(final Object[] array, final Random random) {
        int _jitmagicLoopLimiter79 = 0;
        for (int i = array.length; (boolean) relation(intId(), intVal()).eval() && _jitmagicLoopLimiter79++ < 1000; i--) {
            swap(refId(java.lang.Object[].class).eval(), (int) arithmetic(intId(), intVal()).eval(), refId(java.util.Random.class).eval().nextInt((int) intId().eval()), (int) intVal().eval());
        }
    }

    /**
     * Randomly permutes the elements of the specified array using the Fisher-Yates algorithm.
     *
     * @param array   the array to shuffle
     * @see <a href="https://en.wikipedia.org/wiki/Fisher%E2%80%93Yates_shuffle">Fisher-Yates shuffle algorithm</a>
     * @since 3.6
     */
    public static void shuffle(final short[] array) {
        shuffle(refId(short[].class).eval(), random());
    }

    /**
     * Randomly permutes the elements of the specified array using the Fisher-Yates algorithm.
     *
     * @param array   the array to shuffle
     * @param random  the source of randomness used to permute the elements
     * @see <a href="https://en.wikipedia.org/wiki/Fisher%E2%80%93Yates_shuffle">Fisher-Yates shuffle algorithm</a>
     * @since 3.6
     */
    public static void shuffle(final short[] array, final Random random) {
        int _jitmagicLoopLimiter80 = 0;
        for (int i = array.length; (boolean) relation(intId(), intVal()).eval() && _jitmagicLoopLimiter80++ < 1000; i--) {
            swap(refId(short[].class).eval(), (int) arithmetic(intId(), intVal()).eval(), refId(java.util.Random.class).eval().nextInt((int) intId().eval()), (int) intVal().eval());
        }
    }

    /**
     * <p>Produces a new {@code boolean} array containing the elements
     * between the start and end indices.
     *
     * <p>The start index is inclusive, the end index exclusive.
     * Null array input produces null output.
     *
     * @param array  the array
     * @param startIndexInclusive  the starting index. Undervalue (&lt;0)
     *      is promoted to 0, overvalue (&gt;array.length) results
     *      in an empty array.
     * @param endIndexExclusive  elements up to endIndex-1 are present in the
     *      returned subarray. Undervalue (&lt; startIndex) produces
     *      empty array, overvalue (&gt;array.length) is demoted to
     *      array length.
     * @return a new array containing the elements between
     *      the start and end indices.
     * @since 2.1
     * @see Arrays#copyOfRange(boolean[], int, int)
     */
    public static boolean[] subarray(final boolean[] array, int startIndexInclusive, int endIndexExclusive) {
        if (refId(boolean[].class).eval() == null) {
            return null;
        }
        if ((boolean) relation(intId(), intVal()).eval()) {
            startIndexInclusive = (int) intVal().eval();
        }
        if ((int) intId().eval() > array.length) {
            endIndexExclusive = array.length;
        }
        final int newSize = (int) arithmetic(intId(), intId()).eval();
        if ((boolean) relation(intId(), intVal()).eval()) {
            return refId(boolean[].class).eval();
        }
        final boolean[] subarray = new boolean[(int) intId().eval()];
        System.arraycopy(refId(boolean[].class).eval(), (int) intId().eval(), refId(boolean[].class).eval(), (int) intVal().eval(), (int) intId().eval());
        return refId(boolean[].class).eval();
    }

    /**
     * <p>Produces a new {@code byte} array containing the elements
     * between the start and end indices.
     *
     * <p>The start index is inclusive, the end index exclusive.
     * Null array input produces null output.
     *
     * @param array  the array
     * @param startIndexInclusive  the starting index. Undervalue (&lt;0)
     *      is promoted to 0, overvalue (&gt;array.length) results
     *      in an empty array.
     * @param endIndexExclusive  elements up to endIndex-1 are present in the
     *      returned subarray. Undervalue (&lt; startIndex) produces
     *      empty array, overvalue (&gt;array.length) is demoted to
     *      array length.
     * @return a new array containing the elements between
     *      the start and end indices.
     * @since 2.1
     * @see Arrays#copyOfRange(byte[], int, int)
     */
    public static byte[] subarray(final byte[] array, int startIndexInclusive, int endIndexExclusive) {
        if (refId(byte[].class).eval() == null) {
            return null;
        }
        if ((boolean) relation(intId(), intVal()).eval()) {
            startIndexInclusive = (int) intVal().eval();
        }
        if ((int) intId().eval() > array.length) {
            endIndexExclusive = array.length;
        }
        final int newSize = (int) arithmetic(intId(), intId()).eval();
        if ((boolean) relation(intId(), intVal()).eval()) {
            return refId(byte[].class).eval();
        }
        final byte[] subarray = new byte[(int) intId().eval()];
        System.arraycopy(refId(byte[].class).eval(), (int) intId().eval(), refId(byte[].class).eval(), (int) intVal().eval(), (int) intId().eval());
        return refId(byte[].class).eval();
    }

    /**
     * <p>Produces a new {@code char} array containing the elements
     * between the start and end indices.
     *
     * <p>The start index is inclusive, the end index exclusive.
     * Null array input produces null output.
     *
     * @param array  the array
     * @param startIndexInclusive  the starting index. Undervalue (&lt;0)
     *      is promoted to 0, overvalue (&gt;array.length) results
     *      in an empty array.
     * @param endIndexExclusive  elements up to endIndex-1 are present in the
     *      returned subarray. Undervalue (&lt; startIndex) produces
     *      empty array, overvalue (&gt;array.length) is demoted to
     *      array length.
     * @return a new array containing the elements between
     *      the start and end indices.
     * @since 2.1
     * @see Arrays#copyOfRange(char[], int, int)
     */
    public static char[] subarray(final char[] array, int startIndexInclusive, int endIndexExclusive) {
        if (refId(char[].class).eval() == null) {
            return null;
        }
        if ((boolean) relation(intId(), intVal()).eval()) {
            startIndexInclusive = (int) intVal().eval();
        }
        if ((int) intId().eval() > array.length) {
            endIndexExclusive = array.length;
        }
        final int newSize = (int) arithmetic(intId(), intId()).eval();
        if ((boolean) relation(intId(), intVal()).eval()) {
            return refId(char[].class).eval();
        }
        final char[] subarray = new char[(int) intId().eval()];
        System.arraycopy(refId(char[].class).eval(), (int) intId().eval(), refId(char[].class).eval(), (int) intVal().eval(), (int) intId().eval());
        return refId(char[].class).eval();
    }

    /**
     * <p>Produces a new {@code double} array containing the elements
     * between the start and end indices.
     *
     * <p>The start index is inclusive, the end index exclusive.
     * Null array input produces null output.
     *
     * @param array  the array
     * @param startIndexInclusive  the starting index. Undervalue (&lt;0)
     *      is promoted to 0, overvalue (&gt;array.length) results
     *      in an empty array.
     * @param endIndexExclusive  elements up to endIndex-1 are present in the
     *      returned subarray. Undervalue (&lt; startIndex) produces
     *      empty array, overvalue (&gt;array.length) is demoted to
     *      array length.
     * @return a new array containing the elements between
     *      the start and end indices.
     * @since 2.1
     * @see Arrays#copyOfRange(double[], int, int)
     */
    public static double[] subarray(final double[] array, int startIndexInclusive, int endIndexExclusive) {
        if (refId(double[].class).eval() == null) {
            return null;
        }
        if ((boolean) relation(intId(), intVal()).eval()) {
            startIndexInclusive = (int) intVal().eval();
        }
        if ((int) intId().eval() > array.length) {
            endIndexExclusive = array.length;
        }
        final int newSize = (int) arithmetic(intId(), intId()).eval();
        if ((boolean) relation(intId(), intVal()).eval()) {
            return refId(double[].class).eval();
        }
        final double[] subarray = new double[(int) intId().eval()];
        System.arraycopy(refId(double[].class).eval(), (int) intId().eval(), refId(double[].class).eval(), (int) intVal().eval(), (int) intId().eval());
        return refId(double[].class).eval();
    }

    /**
     * <p>Produces a new {@code float} array containing the elements
     * between the start and end indices.
     *
     * <p>The start index is inclusive, the end index exclusive.
     * Null array input produces null output.
     *
     * @param array  the array
     * @param startIndexInclusive  the starting index. Undervalue (&lt;0)
     *      is promoted to 0, overvalue (&gt;array.length) results
     *      in an empty array.
     * @param endIndexExclusive  elements up to endIndex-1 are present in the
     *      returned subarray. Undervalue (&lt; startIndex) produces
     *      empty array, overvalue (&gt;array.length) is demoted to
     *      array length.
     * @return a new array containing the elements between
     *      the start and end indices.
     * @since 2.1
     * @see Arrays#copyOfRange(float[], int, int)
     */
    @jattack.annotation.Entry
    public static float[] subarray(final float[] array, int startIndexInclusive, int endIndexExclusive) {
        if (refId(float[].class).eval() == null) {
            return null;
        }
        if ((boolean) relation(intId(), intVal()).eval()) {
            startIndexInclusive = (int) intVal().eval();
        }
        if ((int) intId().eval() > array.length) {
            endIndexExclusive = array.length;
        }
        final int newSize = (int) arithmetic(intId(), intId()).eval();
        if ((boolean) relation(intId(), intVal()).eval()) {
            return refId(float[].class).eval();
        }
        final float[] subarray = new float[(int) intId().eval()];
        System.arraycopy(refId(float[].class).eval(), (int) intId().eval(), refId(float[].class).eval(), (int) intVal().eval(), (int) intId().eval());
        return refId(float[].class).eval();
    }

    /**
     * <p>Produces a new {@code int} array containing the elements
     * between the start and end indices.
     *
     * <p>The start index is inclusive, the end index exclusive.
     * Null array input produces null output.
     *
     * @param array  the array
     * @param startIndexInclusive  the starting index. Undervalue (&lt;0)
     *      is promoted to 0, overvalue (&gt;array.length) results
     *      in an empty array.
     * @param endIndexExclusive  elements up to endIndex-1 are present in the
     *      returned subarray. Undervalue (&lt; startIndex) produces
     *      empty array, overvalue (&gt;array.length) is demoted to
     *      array length.
     * @return a new array containing the elements between
     *      the start and end indices.
     * @since 2.1
     * @see Arrays#copyOfRange(int[], int, int)
     */
    public static int[] subarray(final int[] array, int startIndexInclusive, int endIndexExclusive) {
        if (refId(int[].class).eval() == null) {
            return null;
        }
        if ((boolean) relation(intId(), intVal()).eval()) {
            startIndexInclusive = (int) intVal().eval();
        }
        if ((int) intId().eval() > array.length) {
            endIndexExclusive = array.length;
        }
        final int newSize = (int) arithmetic(intId(), intId()).eval();
        if ((boolean) relation(intId(), intVal()).eval()) {
            return refId(int[].class).eval();
        }
        final int[] subarray = new int[(int) intId().eval()];
        System.arraycopy(refId(int[].class).eval(), (int) intId().eval(), refId(int[].class).eval(), (int) intVal().eval(), (int) intId().eval());
        return refId(int[].class).eval();
    }

    /**
     * <p>Produces a new {@code long} array containing the elements
     * between the start and end indices.
     *
     * <p>The start index is inclusive, the end index exclusive.
     * Null array input produces null output.
     *
     * @param array  the array
     * @param startIndexInclusive  the starting index. Undervalue (&lt;0)
     *      is promoted to 0, overvalue (&gt;array.length) results
     *      in an empty array.
     * @param endIndexExclusive  elements up to endIndex-1 are present in the
     *      returned subarray. Undervalue (&lt; startIndex) produces
     *      empty array, overvalue (&gt;array.length) is demoted to
     *      array length.
     * @return a new array containing the elements between
     *      the start and end indices.
     * @since 2.1
     * @see Arrays#copyOfRange(long[], int, int)
     */
    public static long[] subarray(final long[] array, int startIndexInclusive, int endIndexExclusive) {
        if (refId(long[].class).eval() == null) {
            return null;
        }
        if ((boolean) relation(intId(), intVal()).eval()) {
            startIndexInclusive = (int) intVal().eval();
        }
        if ((int) intId().eval() > array.length) {
            endIndexExclusive = array.length;
        }
        final int newSize = (int) arithmetic(intId(), intId()).eval();
        if ((boolean) relation(intId(), intVal()).eval()) {
            return refId(long[].class).eval();
        }
        final long[] subarray = new long[(int) intId().eval()];
        System.arraycopy(refId(long[].class).eval(), (int) intId().eval(), refId(long[].class).eval(), (int) intVal().eval(), (int) intId().eval());
        return refId(long[].class).eval();
    }

    /**
     * <p>Produces a new {@code short} array containing the elements
     * between the start and end indices.
     *
     * <p>The start index is inclusive, the end index exclusive.
     * Null array input produces null output.
     *
     * @param array  the array
     * @param startIndexInclusive  the starting index. Undervalue (&lt;0)
     *      is promoted to 0, overvalue (&gt;array.length) results
     *      in an empty array.
     * @param endIndexExclusive  elements up to endIndex-1 are present in the
     *      returned subarray. Undervalue (&lt; startIndex) produces
     *      empty array, overvalue (&gt;array.length) is demoted to
     *      array length.
     * @return a new array containing the elements between
     *      the start and end indices.
     * @since 2.1
     * @see Arrays#copyOfRange(short[], int, int)
     */
    public static short[] subarray(final short[] array, int startIndexInclusive, int endIndexExclusive) {
        if (refId(short[].class).eval() == null) {
            return null;
        }
        if ((boolean) relation(intId(), intVal()).eval()) {
            startIndexInclusive = (int) intVal().eval();
        }
        if ((int) intId().eval() > array.length) {
            endIndexExclusive = array.length;
        }
        final int newSize = (int) arithmetic(intId(), intId()).eval();
        if ((boolean) relation(intId(), intVal()).eval()) {
            return refId(short[].class).eval();
        }
        final short[] subarray = new short[(int) intId().eval()];
        System.arraycopy(refId(short[].class).eval(), (int) intId().eval(), refId(short[].class).eval(), (int) intVal().eval(), (int) intId().eval());
        return refId(short[].class).eval();
    }

    // Subarrays
    //-----------------------------------------------------------------------
    /**
     * <p>Produces a new array containing the elements between
     * the start and end indices.
     *
     * <p>The start index is inclusive, the end index exclusive.
     * Null array input produces null output.
     *
     * <p>The component type of the subarray is always the same as
     * that of the input array. Thus, if the input is an array of type
     * {@code Date}, the following usage is envisaged:
     *
     * <pre>
     * Date[] someDates = (Date[]) ArrayUtils.subarray(allDates, 2, 5);
     * </pre>
     *
     * @param <T> the component type of the array
     * @param array  the array
     * @param startIndexInclusive  the starting index. Undervalue (&lt;0)
     *      is promoted to 0, overvalue (&gt;array.length) results
     *      in an empty array.
     * @param endIndexExclusive  elements up to endIndex-1 are present in the
     *      returned subarray. Undervalue (&lt; startIndex) produces
     *      empty array, overvalue (&gt;array.length) is demoted to
     *      array length.
     * @return a new array containing the elements between
     *      the start and end indices.
     * @since 2.1
     * @see Arrays#copyOfRange(Object[], int, int)
     */
    public static <T> T[] subarray(final T[] array, int startIndexInclusive, int endIndexExclusive) {
        if (array == null) {
            return null;
        }
        if ((boolean) relation(intId(), intVal()).eval()) {
            startIndexInclusive = (int) intVal().eval();
        }
        if ((int) intId().eval() > array.length) {
            endIndexExclusive = array.length;
        }
        final int newSize = (int) arithmetic(intId(), intId()).eval();
        final Class<T> type = getComponentType(array);
        if ((boolean) relation(intId(), intVal()).eval()) {
            return newInstance(type, (int) intVal().eval());
        }
        final T[] subarray = newInstance(type, (int) intId().eval());
        System.arraycopy(array, (int) intId().eval(), subarray, (int) intVal().eval(), (int) intId().eval());
        return subarray;
    }

    /**
     * Swaps two elements in the given boolean array.
     *
     * <p>There is no special handling for multi-dimensional arrays. This method
     * does nothing for a {@code null} or empty input array or for overflow indices.
     * Negative indices are promoted to 0(zero).</p>
     *
     * Examples:
     * <ul>
     *     <li>ArrayUtils.swap([1, 2, 3], 0, 2) -&gt; [3, 2, 1]</li>
     *     <li>ArrayUtils.swap([1, 2, 3], 0, 0) -&gt; [1, 2, 3]</li>
     *     <li>ArrayUtils.swap([1, 2, 3], 1, 0) -&gt; [2, 1, 3]</li>
     *     <li>ArrayUtils.swap([1, 2, 3], 0, 5) -&gt; [1, 2, 3]</li>
     *     <li>ArrayUtils.swap([1, 2, 3], -1, 1) -&gt; [2, 1, 3]</li>
     * </ul>
     *
     * @param array  the array to swap, may be {@code null}
     * @param offset1 the index of the first element to swap
     * @param offset2 the index of the second element to swap
     * @since 3.5
     */
    public static void swap(final boolean[] array, final int offset1, final int offset2) {
        if (isEmpty(refId(boolean[].class).eval())) {
            return;
        }
        swap(refId(boolean[].class).eval(), (int) intId().eval(), (int) intId().eval(), (int) intVal().eval());
    }

    /**
     * Swaps a series of elements in the given boolean array.
     *
     * <p>This method does nothing for a {@code null} or empty input array or
     * for overflow indices. Negative indices are promoted to 0(zero). If any
     * of the sub-arrays to swap falls outside of the given array, then the
     * swap is stopped at the end of the array and as many as possible elements
     * are swapped.</p>
     *
     * Examples:
     * <ul>
     *     <li>ArrayUtils.swap([true, false, true, false], 0, 2, 1) -&gt; [true, false, true, false]</li>
     *     <li>ArrayUtils.swap([true, false, true, false], 0, 0, 1) -&gt; [true, false, true, false]</li>
     *     <li>ArrayUtils.swap([true, false, true, false], 0, 2, 2) -&gt; [true, false, true, false]</li>
     *     <li>ArrayUtils.swap([true, false, true, false], -3, 2, 2) -&gt; [true, false, true, false]</li>
     *     <li>ArrayUtils.swap([true, false, true, false], 0, 3, 3) -&gt; [false, false, true, true]</li>
     * </ul>
     *
     * @param array the array to swap, may be {@code null}
     * @param offset1 the index of the first element in the series to swap
     * @param offset2 the index of the second element in the series to swap
     * @param len the number of elements to swap starting with the given indices
     * @since 3.5
     */
    public static void swap(final boolean[] array, int offset1, int offset2, int len) {
        if (isEmpty(refId(boolean[].class).eval()) || (int) intId().eval() >= array.length || (int) intId().eval() >= array.length) {
            return;
        }
        if ((boolean) relation(intId(), intVal()).eval()) {
            offset1 = (int) intVal().eval();
        }
        if ((boolean) relation(intId(), intVal()).eval()) {
            offset2 = (int) intVal().eval();
        }
        len = Math.min(Math.min((int) intId().eval(), array.length - (int) intId().eval()), array.length - (int) intId().eval());
        int _jitmagicLoopLimiter81 = 0;
        for (int i = (int) intVal().eval(); (boolean) relation(intId(), intId()).eval() && _jitmagicLoopLimiter81++ < 1000; i++, offset1++, offset2++) {
            final boolean aux = (boolean) boolArrAccessExp(refId(boolean[].class), intId()).eval();
            array[offset1] = (boolean) boolArrAccessExp(refId(boolean[].class), intId()).eval();
            array[offset2] = (boolean) boolId().eval();
        }
    }

    /**
     * Swaps two elements in the given byte array.
     *
     * <p>There is no special handling for multi-dimensional arrays. This method
     * does nothing for a {@code null} or empty input array or for overflow indices.
     * Negative indices are promoted to 0(zero).</p>
     *
     * Examples:
     * <ul>
     *     <li>ArrayUtils.swap([1, 2, 3], 0, 2) -&gt; [3, 2, 1]</li>
     *     <li>ArrayUtils.swap([1, 2, 3], 0, 0) -&gt; [1, 2, 3]</li>
     *     <li>ArrayUtils.swap([1, 2, 3], 1, 0) -&gt; [2, 1, 3]</li>
     *     <li>ArrayUtils.swap([1, 2, 3], 0, 5) -&gt; [1, 2, 3]</li>
     *     <li>ArrayUtils.swap([1, 2, 3], -1, 1) -&gt; [2, 1, 3]</li>
     * </ul>
     *
     * @param array  the array to swap, may be {@code null}
     * @param offset1 the index of the first element to swap
     * @param offset2 the index of the second element to swap
     * @since 3.5
     */
    public static void swap(final byte[] array, final int offset1, final int offset2) {
        if (isEmpty(refId(byte[].class).eval())) {
            return;
        }
        swap(refId(byte[].class).eval(), (int) intId().eval(), (int) intId().eval(), (int) intVal().eval());
    }

    /**
     * Swaps a series of elements in the given byte array.
     *
     * <p>This method does nothing for a {@code null} or empty input array or
     * for overflow indices. Negative indices are promoted to 0(zero). If any
     * of the sub-arrays to swap falls outside of the given array, then the
     * swap is stopped at the end of the array and as many as possible elements
     * are swapped.</p>
     *
     * Examples:
     * <ul>
     *     <li>ArrayUtils.swap([1, 2, 3, 4], 0, 2, 1) -&gt; [3, 2, 1, 4]</li>
     *     <li>ArrayUtils.swap([1, 2, 3, 4], 0, 0, 1) -&gt; [1, 2, 3, 4]</li>
     *     <li>ArrayUtils.swap([1, 2, 3, 4], 2, 0, 2) -&gt; [3, 4, 1, 2]</li>
     *     <li>ArrayUtils.swap([1, 2, 3, 4], -3, 2, 2) -&gt; [3, 4, 1, 2]</li>
     *     <li>ArrayUtils.swap([1, 2, 3, 4], 0, 3, 3) -&gt; [4, 2, 3, 1]</li>
     * </ul>
     *
     * @param array the array to swap, may be {@code null}
     * @param offset1 the index of the first element in the series to swap
     * @param offset2 the index of the second element in the series to swap
     * @param len the number of elements to swap starting with the given indices
     * @since 3.5
     */
    public static void swap(final byte[] array, int offset1, int offset2, int len) {
        if (isEmpty(refId(byte[].class).eval()) || (int) intId().eval() >= array.length || (int) intId().eval() >= array.length) {
            return;
        }
        if ((boolean) relation(intId(), intVal()).eval()) {
            offset1 = (int) intVal().eval();
        }
        if ((boolean) relation(intId(), intVal()).eval()) {
            offset2 = (int) intVal().eval();
        }
        len = Math.min(Math.min((int) intId().eval(), array.length - (int) intId().eval()), array.length - (int) intId().eval());
        int _jitmagicLoopLimiter82 = 0;
        for (int i = (int) intVal().eval(); (boolean) relation(intId(), intId()).eval() && _jitmagicLoopLimiter82++ < 1000; i++, offset1++, offset2++) {
            final byte aux = (byte) byteArrAccessExp(refId(byte[].class), intId()).eval();
            array[offset1] = (byte) byteArrAccessExp(refId(byte[].class), intId()).eval();
            array[offset2] = (byte) byteId().eval();
        }
    }

    /**
     * Swaps two elements in the given char array.
     *
     * <p>There is no special handling for multi-dimensional arrays. This method
     * does nothing for a {@code null} or empty input array or for overflow indices.
     * Negative indices are promoted to 0(zero).</p>
     *
     * Examples:
     * <ul>
     *     <li>ArrayUtils.swap([1, 2, 3], 0, 2) -&gt; [3, 2, 1]</li>
     *     <li>ArrayUtils.swap([1, 2, 3], 0, 0) -&gt; [1, 2, 3]</li>
     *     <li>ArrayUtils.swap([1, 2, 3], 1, 0) -&gt; [2, 1, 3]</li>
     *     <li>ArrayUtils.swap([1, 2, 3], 0, 5) -&gt; [1, 2, 3]</li>
     *     <li>ArrayUtils.swap([1, 2, 3], -1, 1) -&gt; [2, 1, 3]</li>
     * </ul>
     *
     * @param array  the array to swap, may be {@code null}
     * @param offset1 the index of the first element to swap
     * @param offset2 the index of the second element to swap
     * @since 3.5
     */
    public static void swap(final char[] array, final int offset1, final int offset2) {
        if (isEmpty(refId(char[].class).eval())) {
            return;
        }
        swap(refId(char[].class).eval(), (int) intId().eval(), (int) intId().eval(), (int) intVal().eval());
    }

    /**
     * Swaps a series of elements in the given char array.
     *
     * <p>This method does nothing for a {@code null} or empty input array or
     * for overflow indices. Negative indices are promoted to 0(zero). If any
     * of the sub-arrays to swap falls outside of the given array, then the
     * swap is stopped at the end of the array and as many as possible elements
     * are swapped.</p>
     *
     * Examples:
     * <ul>
     *     <li>ArrayUtils.swap([1, 2, 3, 4], 0, 2, 1) -&gt; [3, 2, 1, 4]</li>
     *     <li>ArrayUtils.swap([1, 2, 3, 4], 0, 0, 1) -&gt; [1, 2, 3, 4]</li>
     *     <li>ArrayUtils.swap([1, 2, 3, 4], 2, 0, 2) -&gt; [3, 4, 1, 2]</li>
     *     <li>ArrayUtils.swap([1, 2, 3, 4], -3, 2, 2) -&gt; [3, 4, 1, 2]</li>
     *     <li>ArrayUtils.swap([1, 2, 3, 4], 0, 3, 3) -&gt; [4, 2, 3, 1]</li>
     * </ul>
     *
     * @param array the array to swap, may be {@code null}
     * @param offset1 the index of the first element in the series to swap
     * @param offset2 the index of the second element in the series to swap
     * @param len the number of elements to swap starting with the given indices
     * @since 3.5
     */
    public static void swap(final char[] array, int offset1, int offset2, int len) {
        if (isEmpty(refId(char[].class).eval()) || (int) intId().eval() >= array.length || (int) intId().eval() >= array.length) {
            return;
        }
        if ((boolean) relation(intId(), intVal()).eval()) {
            offset1 = (int) intVal().eval();
        }
        if ((boolean) relation(intId(), intVal()).eval()) {
            offset2 = (int) intVal().eval();
        }
        len = Math.min(Math.min((int) intId().eval(), array.length - (int) intId().eval()), array.length - (int) intId().eval());
        int _jitmagicLoopLimiter83 = 0;
        for (int i = (int) intVal().eval(); (boolean) relation(intId(), intId()).eval() && _jitmagicLoopLimiter83++ < 1000; i++, offset1++, offset2++) {
            final char aux = (char) charArrAccessExp(refId(char[].class), intId()).eval();
            array[offset1] = (char) charArrAccessExp(refId(char[].class), intId()).eval();
            array[offset2] = (char) charId().eval();
        }
    }

    /**
     * Swaps two elements in the given double array.
     *
     * <p>There is no special handling for multi-dimensional arrays. This method
     * does nothing for a {@code null} or empty input array or for overflow indices.
     * Negative indices are promoted to 0(zero).</p>
     *
     * Examples:
     * <ul>
     *     <li>ArrayUtils.swap([1, 2, 3], 0, 2) -&gt; [3, 2, 1]</li>
     *     <li>ArrayUtils.swap([1, 2, 3], 0, 0) -&gt; [1, 2, 3]</li>
     *     <li>ArrayUtils.swap([1, 2, 3], 1, 0) -&gt; [2, 1, 3]</li>
     *     <li>ArrayUtils.swap([1, 2, 3], 0, 5) -&gt; [1, 2, 3]</li>
     *     <li>ArrayUtils.swap([1, 2, 3], -1, 1) -&gt; [2, 1, 3]</li>
     * </ul>
     *
     * @param array  the array to swap, may be {@code null}
     * @param offset1 the index of the first element to swap
     * @param offset2 the index of the second element to swap
     * @since 3.5
     */
    public static void swap(final double[] array, final int offset1, final int offset2) {
        if (isEmpty(refId(double[].class).eval())) {
            return;
        }
        swap(refId(double[].class).eval(), (int) intId().eval(), (int) intId().eval(), (int) intVal().eval());
    }

    /**
     * Swaps a series of elements in the given double array.
     *
     * <p>This method does nothing for a {@code null} or empty input array or
     * for overflow indices. Negative indices are promoted to 0(zero). If any
     * of the sub-arrays to swap falls outside of the given array, then the
     * swap is stopped at the end of the array and as many as possible elements
     * are swapped.</p>
     *
     * Examples:
     * <ul>
     *     <li>ArrayUtils.swap([1, 2, 3, 4], 0, 2, 1) -&gt; [3, 2, 1, 4]</li>
     *     <li>ArrayUtils.swap([1, 2, 3, 4], 0, 0, 1) -&gt; [1, 2, 3, 4]</li>
     *     <li>ArrayUtils.swap([1, 2, 3, 4], 2, 0, 2) -&gt; [3, 4, 1, 2]</li>
     *     <li>ArrayUtils.swap([1, 2, 3, 4], -3, 2, 2) -&gt; [3, 4, 1, 2]</li>
     *     <li>ArrayUtils.swap([1, 2, 3, 4], 0, 3, 3) -&gt; [4, 2, 3, 1]</li>
     * </ul>
     *
     * @param array the array to swap, may be {@code null}
     * @param offset1 the index of the first element in the series to swap
     * @param offset2 the index of the second element in the series to swap
     * @param len the number of elements to swap starting with the given indices
     * @since 3.5
     */
    public static void swap(final double[] array, int offset1, int offset2, int len) {
        if (isEmpty(refId(double[].class).eval()) || (int) intId().eval() >= array.length || (int) intId().eval() >= array.length) {
            return;
        }
        if ((boolean) relation(intId(), intVal()).eval()) {
            offset1 = (int) intVal().eval();
        }
        if ((boolean) relation(intId(), intVal()).eval()) {
            offset2 = (int) intVal().eval();
        }
        len = Math.min(Math.min((int) intId().eval(), array.length - (int) intId().eval()), array.length - (int) intId().eval());
        int _jitmagicLoopLimiter84 = 0;
        for (int i = (int) intVal().eval(); (boolean) relation(intId(), intId()).eval() && _jitmagicLoopLimiter84++ < 1000; i++, offset1++, offset2++) {
            final double aux = (double) doubleArrAccessExp(refId(double[].class), intId()).eval();
            array[offset1] = (double) doubleArrAccessExp(refId(double[].class), intId()).eval();
            array[offset2] = (double) doubleId().eval();
        }
    }

    /**
     * Swaps two elements in the given float array.
     *
     * <p>There is no special handling for multi-dimensional arrays. This method
     * does nothing for a {@code null} or empty input array or for overflow indices.
     * Negative indices are promoted to 0(zero).</p>
     *
     * Examples:
     * <ul>
     *     <li>ArrayUtils.swap([1, 2, 3], 0, 2) -&gt; [3, 2, 1]</li>
     *     <li>ArrayUtils.swap([1, 2, 3], 0, 0) -&gt; [1, 2, 3]</li>
     *     <li>ArrayUtils.swap([1, 2, 3], 1, 0) -&gt; [2, 1, 3]</li>
     *     <li>ArrayUtils.swap([1, 2, 3], 0, 5) -&gt; [1, 2, 3]</li>
     *     <li>ArrayUtils.swap([1, 2, 3], -1, 1) -&gt; [2, 1, 3]</li>
     * </ul>
     *
     * @param array  the array to swap, may be {@code null}
     * @param offset1 the index of the first element to swap
     * @param offset2 the index of the second element to swap
     * @since 3.5
     */
    public static void swap(final float[] array, final int offset1, final int offset2) {
        if (isEmpty(refId(float[].class).eval())) {
            return;
        }
        swap(refId(float[].class).eval(), (int) intId().eval(), (int) intId().eval(), (int) intVal().eval());
    }

    /**
     * Swaps a series of elements in the given float array.
     *
     * <p>This method does nothing for a {@code null} or empty input array or
     * for overflow indices. Negative indices are promoted to 0(zero). If any
     * of the sub-arrays to swap falls outside of the given array, then the
     * swap is stopped at the end of the array and as many as possible elements
     * are swapped.</p>
     *
     * Examples:
     * <ul>
     *     <li>ArrayUtils.swap([1, 2, 3, 4], 0, 2, 1) -&gt; [3, 2, 1, 4]</li>
     *     <li>ArrayUtils.swap([1, 2, 3, 4], 0, 0, 1) -&gt; [1, 2, 3, 4]</li>
     *     <li>ArrayUtils.swap([1, 2, 3, 4], 2, 0, 2) -&gt; [3, 4, 1, 2]</li>
     *     <li>ArrayUtils.swap([1, 2, 3, 4], -3, 2, 2) -&gt; [3, 4, 1, 2]</li>
     *     <li>ArrayUtils.swap([1, 2, 3, 4], 0, 3, 3) -&gt; [4, 2, 3, 1]</li>
     * </ul>
     *
     * @param array the array to swap, may be {@code null}
     * @param offset1 the index of the first element in the series to swap
     * @param offset2 the index of the second element in the series to swap
     * @param len the number of elements to swap starting with the given indices
     * @since 3.5
     */
    public static void swap(final float[] array, int offset1, int offset2, int len) {
        if (isEmpty(refId(float[].class).eval()) || (int) intId().eval() >= array.length || (int) intId().eval() >= array.length) {
            return;
        }
        if ((boolean) relation(intId(), intVal()).eval()) {
            offset1 = (int) intVal().eval();
        }
        if ((boolean) relation(intId(), intVal()).eval()) {
            offset2 = (int) intVal().eval();
        }
        len = Math.min(Math.min((int) intId().eval(), array.length - (int) intId().eval()), array.length - (int) intId().eval());
        int _jitmagicLoopLimiter85 = 0;
        for (int i = (int) intVal().eval(); (boolean) relation(intId(), intId()).eval() && _jitmagicLoopLimiter85++ < 1000; i++, offset1++, offset2++) {
            final float aux = (float) floatArrAccessExp(refId(float[].class), intId()).eval();
            array[offset1] = (float) floatArrAccessExp(refId(float[].class), intId()).eval();
            array[offset2] = (float) floatId().eval();
        }
    }

    /**
     * Swaps two elements in the given int array.
     *
     * <p>There is no special handling for multi-dimensional arrays. This method
     * does nothing for a {@code null} or empty input array or for overflow indices.
     * Negative indices are promoted to 0(zero).</p>
     *
     * Examples:
     * <ul>
     *     <li>ArrayUtils.swap([1, 2, 3], 0, 2) -&gt; [3, 2, 1]</li>
     *     <li>ArrayUtils.swap([1, 2, 3], 0, 0) -&gt; [1, 2, 3]</li>
     *     <li>ArrayUtils.swap([1, 2, 3], 1, 0) -&gt; [2, 1, 3]</li>
     *     <li>ArrayUtils.swap([1, 2, 3], 0, 5) -&gt; [1, 2, 3]</li>
     *     <li>ArrayUtils.swap([1, 2, 3], -1, 1) -&gt; [2, 1, 3]</li>
     * </ul>
     *
     * @param array  the array to swap, may be {@code null}
     * @param offset1 the index of the first element to swap
     * @param offset2 the index of the second element to swap
     * @since 3.5
     */
    public static void swap(final int[] array, final int offset1, final int offset2) {
        if (isEmpty(refId(int[].class).eval())) {
            return;
        }
        swap(refId(int[].class).eval(), (int) intId().eval(), (int) intId().eval(), (int) intVal().eval());
    }

    /**
     * Swaps a series of elements in the given int array.
     *
     * <p>This method does nothing for a {@code null} or empty input array or
     * for overflow indices. Negative indices are promoted to 0(zero). If any
     * of the sub-arrays to swap falls outside of the given array, then the
     * swap is stopped at the end of the array and as many as possible elements
     * are swapped.</p>
     *
     * Examples:
     * <ul>
     *     <li>ArrayUtils.swap([1, 2, 3, 4], 0, 2, 1) -&gt; [3, 2, 1, 4]</li>
     *     <li>ArrayUtils.swap([1, 2, 3, 4], 0, 0, 1) -&gt; [1, 2, 3, 4]</li>
     *     <li>ArrayUtils.swap([1, 2, 3, 4], 2, 0, 2) -&gt; [3, 4, 1, 2]</li>
     *     <li>ArrayUtils.swap([1, 2, 3, 4], -3, 2, 2) -&gt; [3, 4, 1, 2]</li>
     *     <li>ArrayUtils.swap([1, 2, 3, 4], 0, 3, 3) -&gt; [4, 2, 3, 1]</li>
     * </ul>
     *
     * @param array the array to swap, may be {@code null}
     * @param offset1 the index of the first element in the series to swap
     * @param offset2 the index of the second element in the series to swap
     * @param len the number of elements to swap starting with the given indices
     * @since 3.5
     */
    public static void swap(final int[] array, int offset1, int offset2, int len) {
        if (isEmpty(refId(int[].class).eval()) || (int) intId().eval() >= array.length || (int) intId().eval() >= array.length) {
            return;
        }
        if ((boolean) relation(intId(), intVal()).eval()) {
            offset1 = (int) intVal().eval();
        }
        if ((boolean) relation(intId(), intVal()).eval()) {
            offset2 = (int) intVal().eval();
        }
        len = Math.min(Math.min((int) intId().eval(), array.length - (int) intId().eval()), array.length - (int) intId().eval());
        int _jitmagicLoopLimiter86 = 0;
        for (int i = (int) intVal().eval(); (boolean) relation(intId(), intId()).eval() && _jitmagicLoopLimiter86++ < 1000; i++, offset1++, offset2++) {
            final int aux = (int) intArrAccessExp(refId(int[].class), intId()).eval();
            array[offset1] = (int) intArrAccessExp(refId(int[].class), intId()).eval();
            array[offset2] = (int) intId().eval();
        }
    }

    /**
     * Swaps two elements in the given long array.
     *
     * <p>There is no special handling for multi-dimensional arrays. This method
     * does nothing for a {@code null} or empty input array or for overflow indices.
     * Negative indices are promoted to 0(zero).</p>
     *
     * Examples:
     * <ul>
     *     <li>ArrayUtils.swap([true, false, true], 0, 2) -&gt; [true, false, true]</li>
     *     <li>ArrayUtils.swap([true, false, true], 0, 0) -&gt; [true, false, true]</li>
     *     <li>ArrayUtils.swap([true, false, true], 1, 0) -&gt; [false, true, true]</li>
     *     <li>ArrayUtils.swap([true, false, true], 0, 5) -&gt; [true, false, true]</li>
     *     <li>ArrayUtils.swap([true, false, true], -1, 1) -&gt; [false, true, true]</li>
     * </ul>
     *
     * @param array  the array to swap, may be {@code null}
     * @param offset1 the index of the first element to swap
     * @param offset2 the index of the second element to swap
     * @since 3.5
     */
    public static void swap(final long[] array, final int offset1, final int offset2) {
        if (isEmpty(refId(long[].class).eval())) {
            return;
        }
        swap(refId(long[].class).eval(), (int) intId().eval(), (int) intId().eval(), (int) intVal().eval());
    }

    /**
     * Swaps a series of elements in the given long array.
     *
     * <p>This method does nothing for a {@code null} or empty input array or
     * for overflow indices. Negative indices are promoted to 0(zero). If any
     * of the sub-arrays to swap falls outside of the given array, then the
     * swap is stopped at the end of the array and as many as possible elements
     * are swapped.</p>
     *
     * Examples:
     * <ul>
     *     <li>ArrayUtils.swap([1, 2, 3, 4], 0, 2, 1) -&gt; [3, 2, 1, 4]</li>
     *     <li>ArrayUtils.swap([1, 2, 3, 4], 0, 0, 1) -&gt; [1, 2, 3, 4]</li>
     *     <li>ArrayUtils.swap([1, 2, 3, 4], 2, 0, 2) -&gt; [3, 4, 1, 2]</li>
     *     <li>ArrayUtils.swap([1, 2, 3, 4], -3, 2, 2) -&gt; [3, 4, 1, 2]</li>
     *     <li>ArrayUtils.swap([1, 2, 3, 4], 0, 3, 3) -&gt; [4, 2, 3, 1]</li>
     * </ul>
     *
     * @param array the array to swap, may be {@code null}
     * @param offset1 the index of the first element in the series to swap
     * @param offset2 the index of the second element in the series to swap
     * @param len the number of elements to swap starting with the given indices
     * @since 3.5
     */
    public static void swap(final long[] array, int offset1, int offset2, int len) {
        if (isEmpty(refId(long[].class).eval()) || (int) intId().eval() >= array.length || (int) intId().eval() >= array.length) {
            return;
        }
        if ((boolean) relation(intId(), intVal()).eval()) {
            offset1 = (int) intVal().eval();
        }
        if ((boolean) relation(intId(), intVal()).eval()) {
            offset2 = (int) intVal().eval();
        }
        len = Math.min(Math.min((int) intId().eval(), array.length - (int) intId().eval()), array.length - (int) intId().eval());
        int _jitmagicLoopLimiter87 = 0;
        for (int i = (int) intVal().eval(); (boolean) relation(intId(), intId()).eval() && _jitmagicLoopLimiter87++ < 1000; i++, offset1++, offset2++) {
            final long aux = (long) longArrAccessExp(refId(long[].class), intId()).eval();
            array[offset1] = (long) longArrAccessExp(refId(long[].class), intId()).eval();
            array[offset2] = (long) longId().eval();
        }
    }

    // Swap
    //-----------------------------------------------------------------------
    /**
     * Swaps two elements in the given array.
     *
     * <p>There is no special handling for multi-dimensional arrays. This method
     * does nothing for a {@code null} or empty input array or for overflow indices.
     * Negative indices are promoted to 0(zero).</p>
     *
     * Examples:
     * <ul>
     *     <li>ArrayUtils.swap(["1", "2", "3"], 0, 2) -&gt; ["3", "2", "1"]</li>
     *     <li>ArrayUtils.swap(["1", "2", "3"], 0, 0) -&gt; ["1", "2", "3"]</li>
     *     <li>ArrayUtils.swap(["1", "2", "3"], 1, 0) -&gt; ["2", "1", "3"]</li>
     *     <li>ArrayUtils.swap(["1", "2", "3"], 0, 5) -&gt; ["1", "2", "3"]</li>
     *     <li>ArrayUtils.swap(["1", "2", "3"], -1, 1) -&gt; ["2", "1", "3"]</li>
     * </ul>
     *
     * @param array the array to swap, may be {@code null}
     * @param offset1 the index of the first element to swap
     * @param offset2 the index of the second element to swap
     * @since 3.5
     */
    public static void swap(final Object[] array, final int offset1, final int offset2) {
        if (isEmpty(refId(java.lang.Object[].class).eval())) {
            return;
        }
        swap(refId(java.lang.Object[].class).eval(), (int) intId().eval(), (int) intId().eval(), (int) intVal().eval());
    }

    /**
     * Swaps a series of elements in the given array.
     *
     * <p>This method does nothing for a {@code null} or empty input array or
     * for overflow indices. Negative indices are promoted to 0(zero). If any
     * of the sub-arrays to swap falls outside of the given array, then the
     * swap is stopped at the end of the array and as many as possible elements
     * are swapped.</p>
     *
     * Examples:
     * <ul>
     *     <li>ArrayUtils.swap(["1", "2", "3", "4"], 0, 2, 1) -&gt; ["3", "2", "1", "4"]</li>
     *     <li>ArrayUtils.swap(["1", "2", "3", "4"], 0, 0, 1) -&gt; ["1", "2", "3", "4"]</li>
     *     <li>ArrayUtils.swap(["1", "2", "3", "4"], 2, 0, 2) -&gt; ["3", "4", "1", "2"]</li>
     *     <li>ArrayUtils.swap(["1", "2", "3", "4"], -3, 2, 2) -&gt; ["3", "4", "1", "2"]</li>
     *     <li>ArrayUtils.swap(["1", "2", "3", "4"], 0, 3, 3) -&gt; ["4", "2", "3", "1"]</li>
     * </ul>
     *
     * @param array the array to swap, may be {@code null}
     * @param offset1 the index of the first element in the series to swap
     * @param offset2 the index of the second element in the series to swap
     * @param len the number of elements to swap starting with the given indices
     * @since 3.5
     */
    public static void swap(final Object[] array, int offset1, int offset2, int len) {
        if (isEmpty(refId(java.lang.Object[].class).eval()) || (int) intId().eval() >= array.length || (int) intId().eval() >= array.length) {
            return;
        }
        if ((boolean) relation(intId(), intVal()).eval()) {
            offset1 = (int) intVal().eval();
        }
        if ((boolean) relation(intId(), intVal()).eval()) {
            offset2 = (int) intVal().eval();
        }
        len = Math.min(Math.min((int) intId().eval(), array.length - (int) intId().eval()), array.length - (int) intId().eval());
        int _jitmagicLoopLimiter88 = 0;
        for (int i = (int) intVal().eval(); (boolean) relation(intId(), intId()).eval() && _jitmagicLoopLimiter88++ < 1000; i++, offset1++, offset2++) {
            final Object aux = refArrAccessExp(java.lang.Object.class, java.lang.Object[].class, refId(java.lang.Object[].class), intId()).eval();
            array[offset1] = refArrAccessExp(java.lang.Object.class, java.lang.Object[].class, refId(java.lang.Object[].class), intId()).eval();
            array[offset2] = refId(java.lang.Object.class).eval();
        }
    }

    /**
     * Swaps two elements in the given short array.
     *
     * <p>There is no special handling for multi-dimensional arrays. This method
     * does nothing for a {@code null} or empty input array or for overflow indices.
     * Negative indices are promoted to 0(zero).</p>
     *
     * Examples:
     * <ul>
     *     <li>ArrayUtils.swap([1, 2, 3], 0, 2) -&gt; [3, 2, 1]</li>
     *     <li>ArrayUtils.swap([1, 2, 3], 0, 0) -&gt; [1, 2, 3]</li>
     *     <li>ArrayUtils.swap([1, 2, 3], 1, 0) -&gt; [2, 1, 3]</li>
     *     <li>ArrayUtils.swap([1, 2, 3], 0, 5) -&gt; [1, 2, 3]</li>
     *     <li>ArrayUtils.swap([1, 2, 3], -1, 1) -&gt; [2, 1, 3]</li>
     * </ul>
     *
     * @param array  the array to swap, may be {@code null}
     * @param offset1 the index of the first element to swap
     * @param offset2 the index of the second element to swap
     * @since 3.5
     */
    public static void swap(final short[] array, final int offset1, final int offset2) {
        if (isEmpty(refId(short[].class).eval())) {
            return;
        }
        swap(refId(short[].class).eval(), (int) intId().eval(), (int) intId().eval(), (int) intVal().eval());
    }

    /**
     * Swaps a series of elements in the given short array.
     *
     * <p>This method does nothing for a {@code null} or empty input array or
     * for overflow indices. Negative indices are promoted to 0(zero). If any
     * of the sub-arrays to swap falls outside of the given array, then the
     * swap is stopped at the end of the array and as many as possible elements
     * are swapped.</p>
     *
     * Examples:
     * <ul>
     *     <li>ArrayUtils.swap([1, 2, 3, 4], 0, 2, 1) -&gt; [3, 2, 1, 4]</li>
     *     <li>ArrayUtils.swap([1, 2, 3, 4], 0, 0, 1) -&gt; [1, 2, 3, 4]</li>
     *     <li>ArrayUtils.swap([1, 2, 3, 4], 2, 0, 2) -&gt; [3, 4, 1, 2]</li>
     *     <li>ArrayUtils.swap([1, 2, 3, 4], -3, 2, 2) -&gt; [3, 4, 1, 2]</li>
     *     <li>ArrayUtils.swap([1, 2, 3, 4], 0, 3, 3) -&gt; [4, 2, 3, 1]</li>
     * </ul>
     *
     * @param array the array to swap, may be {@code null}
     * @param offset1 the index of the first element in the series to swap
     * @param offset2 the index of the second element in the series to swap
     * @param len the number of elements to swap starting with the given indices
     * @since 3.5
     */
    public static void swap(final short[] array, int offset1, int offset2, int len) {
        if (isEmpty(refId(short[].class).eval()) || (int) intId().eval() >= array.length || (int) intId().eval() >= array.length) {
            return;
        }
        if ((boolean) relation(intId(), intVal()).eval()) {
            offset1 = (int) intVal().eval();
        }
        if ((boolean) relation(intId(), intVal()).eval()) {
            offset2 = (int) intVal().eval();
        }
        if ((boolean) relation(intId(), intId()).eval()) {
            return;
        }
        len = Math.min(Math.min((int) intId().eval(), array.length - (int) intId().eval()), array.length - (int) intId().eval());
        int _jitmagicLoopLimiter89 = 0;
        for (int i = (int) intVal().eval(); (boolean) relation(intId(), intId()).eval() && _jitmagicLoopLimiter89++ < 1000; i++, offset1++, offset2++) {
            final short aux = (short) shortArrAccessExp(refId(short[].class), intId()).eval();
            array[offset1] = (short) shortArrAccessExp(refId(short[].class), intId()).eval();
            array[offset2] = (short) shortId().eval();
        }
    }

    // Generic array
    //-----------------------------------------------------------------------
    /**
     * <p>Create a type-safe generic array.
     *
     * <p>The Java language does not allow an array to be created from a generic type:
     *
     * <pre>
     *    public static &lt;T&gt; T[] createAnArray(int size) {
     *        return new T[size]; // compiler error here
     *    }
     *    public static &lt;T&gt; T[] createAnArray(int size) {
     *        return (T[]) new Object[size]; // ClassCastException at runtime
     *    }
     * </pre>
     *
     * <p>Therefore new arrays of generic types can be created with this method.
     * For example, an array of Strings can be created:
     *
     * <pre>
     *    String[] array = ArrayUtils.toArray("1", "2");
     *    String[] emptyArray = ArrayUtils.&lt;String&gt;toArray();
     * </pre>
     *
     * <p>The method is typically used in scenarios, where the caller itself uses generic types
     * that have to be combined into an array.
     *
     * <p>Note, this method makes only sense to provide arguments of the same type so that the
     * compiler can deduce the type of the array itself. While it is possible to select the
     * type explicitly like in
     * {@code Number[] array = ArrayUtils.&lt;Number&gt;toArray(Integer.valueOf(42), Double.valueOf(Math.PI))},
     * there is no real advantage when compared to
     * {@code new Number[] {Integer.valueOf(42), Double.valueOf(Math.PI)}}.
     *
     * @param  <T>   the array's element type
     * @param  items  the varargs array items, null allowed
     * @return the array, not null unless a null array is passed in
     * @since  3.0
     */
    public static <T> T[] toArray(@SuppressWarnings("unchecked") final T... items) {
        return items;
    }

    // To map
    //-----------------------------------------------------------------------
    /**
     * <p>Converts the given array into a {@link java.util.Map}. Each element of the array
     * must be either a {@link java.util.Map.Entry} or an Array, containing at least two
     * elements, where the first element is used as key and the second as
     * value.
     *
     * <p>This method can be used to initialize:
     * <pre>
     * // Create a Map mapping colors.
     * Map colorMap = ArrayUtils.toMap(new String[][] {
     *     {"RED", "#FF0000"},
     *     {"GREEN", "#00FF00"},
     *     {"BLUE", "#0000FF"}});
     * </pre>
     *
     * <p>This method returns {@code null} for a {@code null} input array.
     *
     * @param array  an array whose elements are either a {@link java.util.Map.Entry} or
     *  an Array containing at least two elements, may be {@code null}
     * @return a {@code Map} that was created from the array
     * @throws IllegalArgumentException  if one element of this Array is
     *  itself an Array containing less then two elements
     * @throws IllegalArgumentException  if the array contains elements other
     *  than {@link java.util.Map.Entry} and an Array
     */
    public static Map<Object, Object> toMap(final Object[] array) {
        if (refId(java.lang.Object[].class).eval() == null) {
            return null;
        }
        final Map<Object, Object> map = new HashMap<>((int) (array.length * (double) doubleVal().eval()));
        int _jitmagicLoopLimiter90 = 0;
        for (int i = (int) intVal().eval(); (int) intId().eval() < array.length && _jitmagicLoopLimiter90++ < 1000; i++) {
            final Object object = refArrAccessExp(java.lang.Object.class, java.lang.Object[].class, refId(java.lang.Object[].class), intId()).eval();
            if (object instanceof Map.Entry<?, ?>) {
                final Map.Entry<?, ?> entry = cast(Map.Entry.class, refId(java.lang.Object.class)).eval();
                map.put(entry.getKey(), entry.getValue());
            } else if (object instanceof Object[]) {
                final Object[] entry = cast(Object[].class, refId(java.lang.Object.class)).eval();
                if (entry.length < (int) intVal().eval()) {
                    throw new IllegalArgumentException("Array element " + (int) intId().eval() + ", '" + refId(java.lang.Object.class).eval() + "', has a length less than 2");
                }
                map.put(refArrAccessExp(java.lang.Object.class, java.lang.Object[].class, refId(java.lang.Object[].class)).eval(), refArrAccessExp(java.lang.Object.class, java.lang.Object[].class, refId(java.lang.Object[].class)).eval());
            } else {
                throw new IllegalArgumentException("Array element " + (int) intId().eval() + ", '" + refId(java.lang.Object.class).eval() + "', is neither of type Map.Entry nor an Array");
            }
        }
        return map;
    }

    /**
     * <p>Converts an array of primitive booleans to objects.
     *
     * <p>This method returns {@code null} for a {@code null} input array.
     *
     * @param array  a {@code boolean} array
     * @return a {@code Boolean} array, {@code null} if null array input
     */
    public static Boolean[] toObject(final boolean[] array) {
        if (refId(boolean[].class).eval() == null) {
            return null;
        }
        if (array.length == (int) intVal().eval()) {
            return refId(java.lang.Boolean[].class).eval();
        }
        final Boolean[] result = new Boolean[array.length];
        int _jitmagicLoopLimiter91 = 0;
        for (int i = (int) intVal().eval(); (int) intId().eval() < array.length && _jitmagicLoopLimiter91++ < 1000; i++) {
            result[i] = ((boolean) boolArrAccessExp(refId(boolean[].class), intId()).eval() ? Boolean.TRUE : Boolean.FALSE);
        }
        return refId(java.lang.Boolean[].class).eval();
    }

    /**
     * <p>Converts an array of primitive bytes to objects.
     *
     * <p>This method returns {@code null} for a {@code null} input array.
     *
     * @param array  a {@code byte} array
     * @return a {@code Byte} array, {@code null} if null array input
     */
    public static Byte[] toObject(final byte[] array) {
        if (refId(byte[].class).eval() == null) {
            return null;
        }
        if (array.length == (int) intVal().eval()) {
            return refId(java.lang.Byte[].class).eval();
        }
        final Byte[] result = new Byte[array.length];
        int _jitmagicLoopLimiter92 = 0;
        for (int i = (int) intVal().eval(); (int) intId().eval() < array.length && _jitmagicLoopLimiter92++ < 1000; i++) {
            result[i] = Byte.valueOf((byte) byteArrAccessExp(refId(byte[].class), intId()).eval());
        }
        return refId(java.lang.Byte[].class).eval();
    }

    /**
     * <p>Converts an array of primitive chars to objects.
     *
     * <p>This method returns {@code null} for a {@code null} input array.
     *
     * @param array a {@code char} array
     * @return a {@code Character} array, {@code null} if null array input
     */
    public static Character[] toObject(final char[] array) {
        if (refId(char[].class).eval() == null) {
            return null;
        }
        if (array.length == (int) intVal().eval()) {
            return refId(java.lang.Character[].class).eval();
        }
        final Character[] result = new Character[array.length];
        int _jitmagicLoopLimiter93 = 0;
        for (int i = (int) intVal().eval(); (int) intId().eval() < array.length && _jitmagicLoopLimiter93++ < 1000; i++) {
            result[i] = Character.valueOf((char) charArrAccessExp(refId(char[].class), intId()).eval());
        }
        return refId(java.lang.Character[].class).eval();
    }

    /**
     * <p>Converts an array of primitive doubles to objects.
     *
     * <p>This method returns {@code null} for a {@code null} input array.
     *
     * @param array  a {@code double} array
     * @return a {@code Double} array, {@code null} if null array input
     */
    public static Double[] toObject(final double[] array) {
        if (refId(double[].class).eval() == null) {
            return null;
        }
        if (array.length == (int) intVal().eval()) {
            return refId(java.lang.Double[].class).eval();
        }
        final Double[] result = new Double[array.length];
        int _jitmagicLoopLimiter94 = 0;
        for (int i = (int) intVal().eval(); (int) intId().eval() < array.length && _jitmagicLoopLimiter94++ < 1000; i++) {
            result[i] = Double.valueOf((double) doubleArrAccessExp(refId(double[].class), intId()).eval());
        }
        return refId(java.lang.Double[].class).eval();
    }

    /**
     * <p>Converts an array of primitive floats to objects.
     *
     * <p>This method returns {@code null} for a {@code null} input array.
     *
     * @param array  a {@code float} array
     * @return a {@code Float} array, {@code null} if null array input
     */
    public static Float[] toObject(final float[] array) {
        if (refId(float[].class).eval() == null) {
            return null;
        }
        if (array.length == (int) intVal().eval()) {
            return refId(java.lang.Float[].class).eval();
        }
        final Float[] result = new Float[array.length];
        int _jitmagicLoopLimiter95 = 0;
        for (int i = (int) intVal().eval(); (int) intId().eval() < array.length && _jitmagicLoopLimiter95++ < 1000; i++) {
            result[i] = Float.valueOf((float) floatArrAccessExp(refId(float[].class), intId()).eval());
        }
        return refId(java.lang.Float[].class).eval();
    }

    /**
     * <p>Converts an array of primitive ints to objects.
     *
     * <p>This method returns {@code null} for a {@code null} input array.
     *
     * @param array  an {@code int} array
     * @return an {@code Integer} array, {@code null} if null array input
     */
    public static Integer[] toObject(final int[] array) {
        if (refId(int[].class).eval() == null) {
            return null;
        }
        if (array.length == (int) intVal().eval()) {
            return refId(java.lang.Integer[].class).eval();
        }
        final Integer[] result = new Integer[array.length];
        int _jitmagicLoopLimiter96 = 0;
        for (int i = (int) intVal().eval(); (int) intId().eval() < array.length && _jitmagicLoopLimiter96++ < 1000; i++) {
            result[i] = Integer.valueOf((int) intArrAccessExp(refId(int[].class), intId()).eval());
        }
        return refId(java.lang.Integer[].class).eval();
    }

    /**
     * <p>Converts an array of primitive longs to objects.
     *
     * <p>This method returns {@code null} for a {@code null} input array.
     *
     * @param array  a {@code long} array
     * @return a {@code Long} array, {@code null} if null array input
     */
    public static Long[] toObject(final long[] array) {
        if (refId(long[].class).eval() == null) {
            return null;
        }
        if (array.length == (int) intVal().eval()) {
            return refId(java.lang.Long[].class).eval();
        }
        final Long[] result = new Long[array.length];
        int _jitmagicLoopLimiter97 = 0;
        for (int i = (int) intVal().eval(); (int) intId().eval() < array.length && _jitmagicLoopLimiter97++ < 1000; i++) {
            result[i] = Long.valueOf((long) longArrAccessExp(refId(long[].class), intId()).eval());
        }
        return refId(java.lang.Long[].class).eval();
    }

    /**
     * <p>Converts an array of primitive shorts to objects.
     *
     * <p>This method returns {@code null} for a {@code null} input array.
     *
     * @param array  a {@code short} array
     * @return a {@code Short} array, {@code null} if null array input
     */
    public static Short[] toObject(final short[] array) {
        if (refId(short[].class).eval() == null) {
            return null;
        }
        if (array.length == (int) intVal().eval()) {
            return refId(java.lang.Short[].class).eval();
        }
        final Short[] result = new Short[array.length];
        int _jitmagicLoopLimiter98 = 0;
        for (int i = (int) intVal().eval(); (int) intId().eval() < array.length && _jitmagicLoopLimiter98++ < 1000; i++) {
            result[i] = Short.valueOf((short) shortArrAccessExp(refId(short[].class), intId()).eval());
        }
        return refId(java.lang.Short[].class).eval();
    }

    // Boolean array converters
    // ----------------------------------------------------------------------
    /**
     * <p>Converts an array of object Booleans to primitives.
     *
     * <p>This method returns {@code null} for a {@code null} input array.
     *
     * @param array  a {@code Boolean} array, may be {@code null}
     * @return a {@code boolean} array, {@code null} if null array input
     * @throws NullPointerException if array content is {@code null}
     */
    public static boolean[] toPrimitive(final Boolean[] array) {
        if (refId(java.lang.Boolean[].class).eval() == null) {
            return null;
        }
        if (array.length == (int) intVal().eval()) {
            return refId(boolean[].class).eval();
        }
        final boolean[] result = new boolean[array.length];
        int _jitmagicLoopLimiter99 = 0;
        for (int i = (int) intVal().eval(); (int) intId().eval() < array.length && _jitmagicLoopLimiter99++ < 1000; i++) {
            result[i] = refArrAccessExp(java.lang.Boolean.class, java.lang.Boolean[].class, refId(java.lang.Boolean[].class), intId()).eval().booleanValue();
        }
        return refId(boolean[].class).eval();
    }

    /**
     * <p>Converts an array of object Booleans to primitives handling {@code null}.
     *
     * <p>This method returns {@code null} for a {@code null} input array.
     *
     * @param array  a {@code Boolean} array, may be {@code null}
     * @param valueForNull  the value to insert if {@code null} found
     * @return a {@code boolean} array, {@code null} if null array input
     */
    public static boolean[] toPrimitive(final Boolean[] array, final boolean valueForNull) {
        if (refId(java.lang.Boolean[].class).eval() == null) {
            return null;
        }
        if (array.length == (int) intVal().eval()) {
            return refId(boolean[].class).eval();
        }
        final boolean[] result = new boolean[array.length];
        int _jitmagicLoopLimiter100 = 0;
        for (int i = (int) intVal().eval(); (int) intId().eval() < array.length && _jitmagicLoopLimiter100++ < 1000; i++) {
            final Boolean b = refArrAccessExp(java.lang.Boolean.class, java.lang.Boolean[].class, refId(java.lang.Boolean[].class), intId()).eval();
            result[i] = (refId(java.lang.Boolean.class).eval() == null ? (boolean) boolId().eval() : refId(java.lang.Boolean.class).eval().booleanValue());
        }
        return refId(boolean[].class).eval();
    }

    // Byte array converters
    // ----------------------------------------------------------------------
    /**
     * <p>Converts an array of object Bytes to primitives.
     *
     * <p>This method returns {@code null} for a {@code null} input array.
     *
     * @param array  a {@code Byte} array, may be {@code null}
     * @return a {@code byte} array, {@code null} if null array input
     * @throws NullPointerException if array content is {@code null}
     */
    public static byte[] toPrimitive(final Byte[] array) {
        if (refId(java.lang.Byte[].class).eval() == null) {
            return null;
        }
        if (array.length == (int) intVal().eval()) {
            return refId(byte[].class).eval();
        }
        final byte[] result = new byte[array.length];
        int _jitmagicLoopLimiter101 = 0;
        for (int i = (int) intVal().eval(); (int) intId().eval() < array.length && _jitmagicLoopLimiter101++ < 1000; i++) {
            result[i] = (byte) refArrAccessExp(java.lang.Byte.class, java.lang.Byte[].class, refId(java.lang.Byte[].class), intId()).eval().byteValue();
        }
        return refId(byte[].class).eval();
    }

    /**
     * <p>Converts an array of object Bytes to primitives handling {@code null}.
     *
     * <p>This method returns {@code null} for a {@code null} input array.
     *
     * @param array  a {@code Byte} array, may be {@code null}
     * @param valueForNull  the value to insert if {@code null} found
     * @return a {@code byte} array, {@code null} if null array input
     */
    public static byte[] toPrimitive(final Byte[] array, final byte valueForNull) {
        if (refId(java.lang.Byte[].class).eval() == null) {
            return null;
        }
        if (array.length == (int) intVal().eval()) {
            return refId(byte[].class).eval();
        }
        final byte[] result = new byte[array.length];
        int _jitmagicLoopLimiter102 = 0;
        for (int i = (int) intVal().eval(); (int) intId().eval() < array.length && _jitmagicLoopLimiter102++ < 1000; i++) {
            final Byte b = refArrAccessExp(java.lang.Byte.class, java.lang.Byte[].class, refId(java.lang.Byte[].class), intId()).eval();
            result[i] = (byte) ((refId(java.lang.Byte.class).eval() == null ? (byte) byteId().eval() : refId(java.lang.Byte.class).eval().byteValue()));
        }
        return refId(byte[].class).eval();
    }

    // Character array converters
    // ----------------------------------------------------------------------
    /**
     * <p>Converts an array of object Characters to primitives.
     *
     * <p>This method returns {@code null} for a {@code null} input array.
     *
     * @param array  a {@code Character} array, may be {@code null}
     * @return a {@code char} array, {@code null} if null array input
     * @throws NullPointerException if array content is {@code null}
     */
    public static char[] toPrimitive(final Character[] array) {
        if (refId(java.lang.Character[].class).eval() == null) {
            return null;
        }
        if (array.length == (int) intVal().eval()) {
            return refId(char[].class).eval();
        }
        final char[] result = new char[array.length];
        int _jitmagicLoopLimiter103 = 0;
        for (int i = (int) intVal().eval(); (int) intId().eval() < array.length && _jitmagicLoopLimiter103++ < 1000; i++) {
            result[i] = (char) refArrAccessExp(java.lang.Character.class, java.lang.Character[].class, refId(java.lang.Character[].class), intId()).eval().charValue();
        }
        return refId(char[].class).eval();
    }

    /**
     * <p>Converts an array of object Character to primitives handling {@code null}.
     *
     * <p>This method returns {@code null} for a {@code null} input array.
     *
     * @param array  a {@code Character} array, may be {@code null}
     * @param valueForNull  the value to insert if {@code null} found
     * @return a {@code char} array, {@code null} if null array input
     */
    public static char[] toPrimitive(final Character[] array, final char valueForNull) {
        if (refId(java.lang.Character[].class).eval() == null) {
            return null;
        }
        if (array.length == (int) intVal().eval()) {
            return refId(char[].class).eval();
        }
        final char[] result = new char[array.length];
        int _jitmagicLoopLimiter104 = 0;
        for (int i = (int) intVal().eval(); (int) intId().eval() < array.length && _jitmagicLoopLimiter104++ < 1000; i++) {
            final Character b = refArrAccessExp(java.lang.Character.class, java.lang.Character[].class, refId(java.lang.Character[].class), intId()).eval();
            result[i] = (char) ((refId(java.lang.Character.class).eval() == null ? (char) charId().eval() : refId(java.lang.Character.class).eval().charValue()));
        }
        return refId(char[].class).eval();
    }

    // Double array converters
    // ----------------------------------------------------------------------
    /**
     * <p>Converts an array of object Doubles to primitives.
     *
     * <p>This method returns {@code null} for a {@code null} input array.
     *
     * @param array  a {@code Double} array, may be {@code null}
     * @return a {@code double} array, {@code null} if null array input
     * @throws NullPointerException if array content is {@code null}
     */
    public static double[] toPrimitive(final Double[] array) {
        if (refId(java.lang.Double[].class).eval() == null) {
            return null;
        }
        if (array.length == (int) intVal().eval()) {
            return refId(double[].class).eval();
        }
        final double[] result = new double[array.length];
        int _jitmagicLoopLimiter105 = 0;
        for (int i = (int) intVal().eval(); (int) intId().eval() < array.length && _jitmagicLoopLimiter105++ < 1000; i++) {
            result[i] = refArrAccessExp(java.lang.Double.class, java.lang.Double[].class, refId(java.lang.Double[].class), intId()).eval().doubleValue();
        }
        return refId(double[].class).eval();
    }

    /**
     * <p>Converts an array of object Doubles to primitives handling {@code null}.
     *
     * <p>This method returns {@code null} for a {@code null} input array.
     *
     * @param array  a {@code Double} array, may be {@code null}
     * @param valueForNull  the value to insert if {@code null} found
     * @return a {@code double} array, {@code null} if null array input
     */
    public static double[] toPrimitive(final Double[] array, final double valueForNull) {
        if (refId(java.lang.Double[].class).eval() == null) {
            return null;
        }
        if (array.length == (int) intVal().eval()) {
            return refId(double[].class).eval();
        }
        final double[] result = new double[array.length];
        int _jitmagicLoopLimiter106 = 0;
        for (int i = (int) intVal().eval(); (int) intId().eval() < array.length && _jitmagicLoopLimiter106++ < 1000; i++) {
            final Double b = refArrAccessExp(java.lang.Double.class, java.lang.Double[].class, refId(java.lang.Double[].class), intId()).eval();
            result[i] = (refId(java.lang.Double.class).eval() == null ? (double) doubleId().eval() : refId(java.lang.Double.class).eval().doubleValue());
        }
        return refId(double[].class).eval();
    }

    //   Float array converters
    // ----------------------------------------------------------------------
    /**
     * <p>Converts an array of object Floats to primitives.
     *
     * <p>This method returns {@code null} for a {@code null} input array.
     *
     * @param array  a {@code Float} array, may be {@code null}
     * @return a {@code float} array, {@code null} if null array input
     * @throws NullPointerException if array content is {@code null}
     */
    public static float[] toPrimitive(final Float[] array) {
        if (refId(java.lang.Float[].class).eval() == null) {
            return null;
        }
        if (array.length == (int) intVal().eval()) {
            return refId(float[].class).eval();
        }
        final float[] result = new float[array.length];
        int _jitmagicLoopLimiter107 = 0;
        for (int i = (int) intVal().eval(); (int) intId().eval() < array.length && _jitmagicLoopLimiter107++ < 1000; i++) {
            result[i] = refArrAccessExp(java.lang.Float.class, java.lang.Float[].class, refId(java.lang.Float[].class), intId()).eval().floatValue();
        }
        return refId(float[].class).eval();
    }

    /**
     * <p>Converts an array of object Floats to primitives handling {@code null}.
     *
     * <p>This method returns {@code null} for a {@code null} input array.
     *
     * @param array  a {@code Float} array, may be {@code null}
     * @param valueForNull  the value to insert if {@code null} found
     * @return a {@code float} array, {@code null} if null array input
     */
    public static float[] toPrimitive(final Float[] array, final float valueForNull) {
        if (refId(java.lang.Float[].class).eval() == null) {
            return null;
        }
        if (array.length == (int) intVal().eval()) {
            return refId(float[].class).eval();
        }
        final float[] result = new float[array.length];
        int _jitmagicLoopLimiter108 = 0;
        for (int i = (int) intVal().eval(); (int) intId().eval() < array.length && _jitmagicLoopLimiter108++ < 1000; i++) {
            final Float b = refArrAccessExp(java.lang.Float.class, java.lang.Float[].class, refId(java.lang.Float[].class), intId()).eval();
            result[i] = (refId(java.lang.Float.class).eval() == null ? (float) floatId().eval() : refId(java.lang.Float.class).eval().floatValue());
        }
        return refId(float[].class).eval();
    }

    // Int array converters
    // ----------------------------------------------------------------------
    /**
     * <p>Converts an array of object Integers to primitives.
     *
     * <p>This method returns {@code null} for a {@code null} input array.
     *
     * @param array  a {@code Integer} array, may be {@code null}
     * @return an {@code int} array, {@code null} if null array input
     * @throws NullPointerException if array content is {@code null}
     */
    public static int[] toPrimitive(final Integer[] array) {
        if (refId(java.lang.Integer[].class).eval() == null) {
            return null;
        }
        if (array.length == (int) intVal().eval()) {
            return refId(int[].class).eval();
        }
        final int[] result = new int[array.length];
        int _jitmagicLoopLimiter109 = 0;
        for (int i = (int) intVal().eval(); (int) intId().eval() < array.length && _jitmagicLoopLimiter109++ < 1000; i++) {
            result[i] = refArrAccessExp(java.lang.Integer.class, java.lang.Integer[].class, refId(java.lang.Integer[].class), intId()).eval().intValue();
        }
        return refId(int[].class).eval();
    }

    /**
     * <p>Converts an array of object Integer to primitives handling {@code null}.
     *
     * <p>This method returns {@code null} for a {@code null} input array.
     *
     * @param array  a {@code Integer} array, may be {@code null}
     * @param valueForNull  the value to insert if {@code null} found
     * @return an {@code int} array, {@code null} if null array input
     */
    public static int[] toPrimitive(final Integer[] array, final int valueForNull) {
        if (refId(java.lang.Integer[].class).eval() == null) {
            return null;
        }
        if (array.length == (int) intVal().eval()) {
            return refId(int[].class).eval();
        }
        final int[] result = new int[array.length];
        int _jitmagicLoopLimiter110 = 0;
        for (int i = (int) intVal().eval(); (int) intId().eval() < array.length && _jitmagicLoopLimiter110++ < 1000; i++) {
            final Integer b = refArrAccessExp(java.lang.Integer.class, java.lang.Integer[].class, refId(java.lang.Integer[].class), intId()).eval();
            result[i] = (refId(java.lang.Integer.class).eval() == null ? (int) intId().eval() : refId(java.lang.Integer.class).eval().intValue());
        }
        return refId(int[].class).eval();
    }

    // Long array converters
    // ----------------------------------------------------------------------
    /**
     * <p>Converts an array of object Longs to primitives.
     *
     * <p>This method returns {@code null} for a {@code null} input array.
     *
     * @param array  a {@code Long} array, may be {@code null}
     * @return a {@code long} array, {@code null} if null array input
     * @throws NullPointerException if array content is {@code null}
     */
    public static long[] toPrimitive(final Long[] array) {
        if (refId(java.lang.Long[].class).eval() == null) {
            return null;
        }
        if (array.length == (int) intVal().eval()) {
            return refId(long[].class).eval();
        }
        final long[] result = new long[array.length];
        int _jitmagicLoopLimiter111 = 0;
        for (int i = (int) intVal().eval(); (int) intId().eval() < array.length && _jitmagicLoopLimiter111++ < 1000; i++) {
            result[i] = refArrAccessExp(java.lang.Long.class, java.lang.Long[].class, refId(java.lang.Long[].class), intId()).eval().longValue();
        }
        return refId(long[].class).eval();
    }

    /**
     * <p>Converts an array of object Long to primitives handling {@code null}.
     *
     * <p>This method returns {@code null} for a {@code null} input array.
     *
     * @param array  a {@code Long} array, may be {@code null}
     * @param valueForNull  the value to insert if {@code null} found
     * @return a {@code long} array, {@code null} if null array input
     */
    public static long[] toPrimitive(final Long[] array, final long valueForNull) {
        if (refId(java.lang.Long[].class).eval() == null) {
            return null;
        }
        if (array.length == (int) intVal().eval()) {
            return refId(long[].class).eval();
        }
        final long[] result = new long[array.length];
        int _jitmagicLoopLimiter112 = 0;
        for (int i = (int) intVal().eval(); (int) intId().eval() < array.length && _jitmagicLoopLimiter112++ < 1000; i++) {
            final Long b = refArrAccessExp(java.lang.Long.class, java.lang.Long[].class, refId(java.lang.Long[].class), intId()).eval();
            result[i] = (refId(java.lang.Long.class).eval() == null ? (long) longId().eval() : refId(java.lang.Long.class).eval().longValue());
        }
        return refId(long[].class).eval();
    }

    /**
     * <p>Create an array of primitive type from an array of wrapper types.
     *
     * <p>This method returns {@code null} for a {@code null} input array.
     *
     * @param array  an array of wrapper object
     * @return an array of the corresponding primitive type, or the original array
     * @since 3.5
     */
    public static Object toPrimitive(final Object array) {
        if (refId(java.lang.Object.class).eval() == null) {
            return null;
        }
        final Class<?> ct = refId(java.lang.Object.class).eval().getClass().getComponentType();
        final Class<?> pt = ClassUtils.wrapperToPrimitive(ct);
        if (Boolean.TYPE.equals(pt)) {
            return toPrimitive(cast(Boolean[].class, refId(java.lang.Object.class)).eval());
        }
        if (Character.TYPE.equals(pt)) {
            return toPrimitive(cast(Character[].class, refId(java.lang.Object.class)).eval());
        }
        if (Byte.TYPE.equals(pt)) {
            return toPrimitive(cast(Byte[].class, refId(java.lang.Object.class)).eval());
        }
        if (Integer.TYPE.equals(pt)) {
            return toPrimitive(cast(Integer[].class, refId(java.lang.Object.class)).eval());
        }
        if (Long.TYPE.equals(pt)) {
            return toPrimitive(cast(Long[].class, refId(java.lang.Object.class)).eval());
        }
        if (Short.TYPE.equals(pt)) {
            return toPrimitive(cast(Short[].class, refId(java.lang.Object.class)).eval());
        }
        if (Double.TYPE.equals(pt)) {
            return toPrimitive(cast(Double[].class, refId(java.lang.Object.class)).eval());
        }
        if (Float.TYPE.equals(pt)) {
            return toPrimitive(cast(Float[].class, refId(java.lang.Object.class)).eval());
        }
        return refId(java.lang.Object.class).eval();
    }

    // Short array converters
    // ----------------------------------------------------------------------
    /**
     * <p>Converts an array of object Shorts to primitives.
     *
     * <p>This method returns {@code null} for a {@code null} input array.
     *
     * @param array  a {@code Short} array, may be {@code null}
     * @return a {@code byte} array, {@code null} if null array input
     * @throws NullPointerException if array content is {@code null}
     */
    public static short[] toPrimitive(final Short[] array) {
        if (refId(java.lang.Short[].class).eval() == null) {
            return null;
        }
        if (array.length == (int) intVal().eval()) {
            return refId(short[].class).eval();
        }
        final short[] result = new short[array.length];
        int _jitmagicLoopLimiter113 = 0;
        for (int i = (int) intVal().eval(); (int) intId().eval() < array.length && _jitmagicLoopLimiter113++ < 1000; i++) {
            result[i] = (short) refArrAccessExp(java.lang.Short.class, java.lang.Short[].class, refId(java.lang.Short[].class), intId()).eval().shortValue();
        }
        return refId(short[].class).eval();
    }

    /**
     * <p>Converts an array of object Short to primitives handling {@code null}.
     *
     * <p>This method returns {@code null} for a {@code null} input array.
     *
     * @param array  a {@code Short} array, may be {@code null}
     * @param valueForNull  the value to insert if {@code null} found
     * @return a {@code byte} array, {@code null} if null array input
     */
    public static short[] toPrimitive(final Short[] array, final short valueForNull) {
        if (refId(java.lang.Short[].class).eval() == null) {
            return null;
        }
        if (array.length == (int) intVal().eval()) {
            return refId(short[].class).eval();
        }
        final short[] result = new short[array.length];
        int _jitmagicLoopLimiter114 = 0;
        for (int i = (int) intVal().eval(); (int) intId().eval() < array.length && _jitmagicLoopLimiter114++ < 1000; i++) {
            final Short b = refArrAccessExp(java.lang.Short.class, java.lang.Short[].class, refId(java.lang.Short[].class), intId()).eval();
            result[i] = (short) ((refId(java.lang.Short.class).eval() == null ? (short) shortId().eval() : refId(java.lang.Short.class).eval().shortValue()));
        }
        return refId(short[].class).eval();
    }

    // Basic methods handling multi-dimensional arrays
    //-----------------------------------------------------------------------
    /**
     * <p>Outputs an array as a String, treating {@code null} as an empty array.
     *
     * <p>Multi-dimensional arrays are handled correctly, including
     * multi-dimensional primitive arrays.
     *
     * <p>The format is that of Java source code, for example {@code {a,b}}.
     *
     * @param array  the array to get a toString for, may be {@code null}
     * @return a String representation of the array, '{}' if null array input
     */
    public static String toString(final Object array) {
        return toString(refId(java.lang.Object.class).eval(), "{}");
    }

    /**
     * <p>Outputs an array as a String handling {@code null}s.
     *
     * <p>Multi-dimensional arrays are handled correctly, including
     * multi-dimensional primitive arrays.
     *
     * <p>The format is that of Java source code, for example {@code {a,b}}.
     *
     * @param array  the array to get a toString for, may be {@code null}
     * @param stringIfNull  the String to return if the array is {@code null}
     * @return a String representation of the array
     */
    public static String toString(final Object array, final String stringIfNull) {
        if (refId(java.lang.Object.class).eval() == null) {
            return refId(java.lang.String.class).eval();
        }
        return new ToStringBuilder(refId(java.lang.Object.class).eval(), ToStringStyle.SIMPLE_STYLE).append(refId(java.lang.Object.class).eval()).toString();
    }

    /**
     * <p>Returns an array containing the string representation of each element in the argument array.</p>
     *
     * <p>This method returns {@code null} for a {@code null} input array.</p>
     *
     * @param array the {@code Object[]} to be processed, may be null
     * @return {@code String[]} of the same size as the source with its element's string representation,
     * {@code null} if null array input
     * @throws NullPointerException if array contains {@code null}
     * @since 3.6
     */
    public static String[] toStringArray(final Object[] array) {
        if (refId(java.lang.Object[].class).eval() == null) {
            return null;
        }
        if (array.length == (int) intVal().eval()) {
            return refId(java.lang.String[].class).eval();
        }
        final String[] result = new String[array.length];
        int _jitmagicLoopLimiter115 = 0;
        for (int i = (int) intVal().eval(); (int) intId().eval() < array.length && _jitmagicLoopLimiter115++ < 1000; i++) {
            result[i] = refArrAccessExp(java.lang.Object.class, java.lang.Object[].class, refId(java.lang.Object[].class), intId()).eval().toString();
        }
        return refId(java.lang.String[].class).eval();
    }

    /**
     * <p>Returns an array containing the string representation of each element in the argument
     * array handling {@code null} elements.</p>
     *
     * <p>This method returns {@code null} for a {@code null} input array.</p>
     *
     * @param array the Object[] to be processed, may be null
     * @param valueForNullElements the value to insert if {@code null} is found
     * @return a {@code String} array, {@code null} if null array input
     * @since 3.6
     */
    public static String[] toStringArray(final Object[] array, final String valueForNullElements) {
        if (null == refId(java.lang.Object[].class).eval()) {
            return null;
        }
        if (array.length == (int) intVal().eval()) {
            return refId(java.lang.String[].class).eval();
        }
        final String[] result = new String[array.length];
        int _jitmagicLoopLimiter116 = 0;
        for (int i = (int) intVal().eval(); (int) intId().eval() < array.length && _jitmagicLoopLimiter116++ < 1000; i++) {
            final Object object = refArrAccessExp(java.lang.Object.class, java.lang.Object[].class, refId(java.lang.Object[].class), intId()).eval();
            result[i] = (refId(java.lang.Object.class).eval() == null ? refId(java.lang.String.class).eval() : refId(java.lang.Object.class).eval().toString());
        }
        return refId(java.lang.String[].class).eval();
    }

    /**
     * <p>ArrayUtils instances should NOT be constructed in standard programming.
     * Instead, the class should be used as {@code ArrayUtils.clone(new int[] {2})}.
     *
     * <p>This constructor is public to permit tools that require a JavaBean instance
     * to operate.
     */
    public ArrayUtils() {
    }

    @jattack.annotation.Arguments
    public static Object[] _jitmagicArgs() throws Throwable {
        float[] floatArray0 = org.apache.commons.lang3.ArrayUtils.EMPTY_FLOAT_ARRAY;
        return new Object[] { floatArray0, (int) (byte) 111, 65 };
    }
}
