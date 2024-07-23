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
import org.csutil.checksum.WrappedChecksum;

public class ArrayUtils {

    public static final boolean[] EMPTY_BOOLEAN_ARRAY = {};

    public static final Boolean[] EMPTY_BOOLEAN_OBJECT_ARRAY = {};

    public static final byte[] EMPTY_BYTE_ARRAY = {};

    public static final Byte[] EMPTY_BYTE_OBJECT_ARRAY = {};

    public static final char[] EMPTY_CHAR_ARRAY = {};

    public static final Character[] EMPTY_CHARACTER_OBJECT_ARRAY = {};

    public static final Class<?>[] EMPTY_CLASS_ARRAY = new Class[0];

    public static final double[] EMPTY_DOUBLE_ARRAY = {};

    public static final Double[] EMPTY_DOUBLE_OBJECT_ARRAY = {};

    public static final Field[] EMPTY_FIELD_ARRAY = {};

    public static final float[] EMPTY_FLOAT_ARRAY = {};

    public static final Float[] EMPTY_FLOAT_OBJECT_ARRAY = {};

    public static final int[] EMPTY_INT_ARRAY = {};

    public static final Integer[] EMPTY_INTEGER_OBJECT_ARRAY = {};

    public static final long[] EMPTY_LONG_ARRAY = {};

    public static final Long[] EMPTY_LONG_OBJECT_ARRAY = {};

    public static final Method[] EMPTY_METHOD_ARRAY = {};

    public static final Object[] EMPTY_OBJECT_ARRAY = {};

    public static final short[] EMPTY_SHORT_ARRAY = {};

    public static final Short[] EMPTY_SHORT_OBJECT_ARRAY = {};

    public static final String[] EMPTY_STRING_ARRAY = {};

    public static final Throwable[] EMPTY_THROWABLE_ARRAY = {};

    public static final Type[] EMPTY_TYPE_ARRAY = {};

    public static final int INDEX_NOT_FOUND = -1;

    public static boolean[] add(final boolean[] array, final boolean element) {
        final boolean[] newArray = (boolean[]) copyArrayGrow1(refId(boolean[].class).eval(1), Boolean.TYPE);
        newArray[newArray.length - 1] = (boolean) boolId().eval(2);
        return refId(boolean[].class).eval(3);
    }

    @Deprecated
    public static boolean[] add(final boolean[] array, final int index, final boolean element) {
        return (boolean[]) add(refId(boolean[].class).eval(4), (int) intId().eval(5), Boolean.valueOf((boolean) boolId().eval(6)), Boolean.TYPE);
    }

    public static byte[] add(final byte[] array, final byte element) {
        final byte[] newArray = (byte[]) copyArrayGrow1(refId(byte[].class).eval(7), Byte.TYPE);
        newArray[newArray.length - 1] = (byte) byteId().eval(8);
        return refId(byte[].class).eval(9);
    }

    @Deprecated
    public static byte[] add(final byte[] array, final int index, final byte element) {
        return (byte[]) add(refId(byte[].class).eval(10), (int) intId().eval(11), Byte.valueOf((byte) byteId().eval(12)), Byte.TYPE);
    }

    public static char[] add(final char[] array, final char element) {
        final char[] newArray = (char[]) copyArrayGrow1(refId(char[].class).eval(13), Character.TYPE);
        newArray[newArray.length - 1] = (char) charId().eval(14);
        return refId(char[].class).eval(15);
    }

    @Deprecated
    public static char[] add(final char[] array, final int index, final char element) {
        return (char[]) add(refId(char[].class).eval(16), (int) intId().eval(17), Character.valueOf((char) charId().eval(18)), Character.TYPE);
    }

    public static double[] add(final double[] array, final double element) {
        final double[] newArray = (double[]) copyArrayGrow1(refId(double[].class).eval(19), Double.TYPE);
        newArray[newArray.length - 1] = (double) doubleId().eval(20);
        return refId(double[].class).eval(21);
    }

    @Deprecated
    public static double[] add(final double[] array, final int index, final double element) {
        return (double[]) add(refId(double[].class).eval(22), (int) intId().eval(23), Double.valueOf((double) doubleId().eval(24)), Double.TYPE);
    }

    public static float[] add(final float[] array, final float element) {
        final float[] newArray = (float[]) copyArrayGrow1(refId(float[].class).eval(25), Float.TYPE);
        newArray[newArray.length - 1] = (float) floatId().eval(26);
        return refId(float[].class).eval(27);
    }

    @Deprecated
    public static float[] add(final float[] array, final int index, final float element) {
        return (float[]) add(refId(float[].class).eval(28), (int) intId().eval(29), Float.valueOf((float) floatId().eval(30)), Float.TYPE);
    }

    public static int[] add(final int[] array, final int element) {
        final int[] newArray = (int[]) copyArrayGrow1(refId(int[].class).eval(31), Integer.TYPE);
        newArray[newArray.length - 1] = (int) intId().eval(32);
        return refId(int[].class).eval(33);
    }

    @Deprecated
    public static int[] add(final int[] array, final int index, final int element) {
        return (int[]) add(refId(int[].class).eval(34), (int) intId().eval(35), Integer.valueOf((int) intId().eval(36)), Integer.TYPE);
    }

    @Deprecated
    public static long[] add(final long[] array, final int index, final long element) {
        return (long[]) add(refId(long[].class).eval(37), (int) intId().eval(38), Long.valueOf((long) longId().eval(39)), Long.TYPE);
    }

    public static long[] add(final long[] array, final long element) {
        final long[] newArray = (long[]) copyArrayGrow1(refId(long[].class).eval(40), Long.TYPE);
        newArray[newArray.length - 1] = (long) longId().eval(41);
        return refId(long[].class).eval(42);
    }

    private static Object add(final Object array, final int index, final Object element, final Class<?> clss) {
        if (refId(java.lang.Object.class).eval(43) == null) {
            if ((boolean) relation(intId(), intVal()).eval(44)) {
                throw new IndexOutOfBoundsException("Index: " + (int) intId().eval(45) + ", Length: 0");
            }
            final Object joinedArray = Array.newInstance(clss, (int) intVal().eval(46));
            Array.set(refId(java.lang.Object.class).eval(47), (int) intVal().eval(48), refId(java.lang.Object.class).eval(49));
            return refId(java.lang.Object.class).eval(50);
        }
        final int length = Array.getLength(refId(java.lang.Object.class).eval(51));
        if ((boolean) logic(relation(intId(), intId()), relation(intId(), intVal())).eval(52)) {
            throw new IndexOutOfBoundsException("Index: " + (int) intId().eval(53) + ", Length: " + (int) intId().eval(54));
        }
        final Object result = Array.newInstance(clss, (int) arithmetic(intId(), intVal()).eval(55));
        System.arraycopy(refId(java.lang.Object.class).eval(56), (int) intVal().eval(57), refId(java.lang.Object.class).eval(58), (int) intVal().eval(59), (int) intId().eval(60));
        Array.set(refId(java.lang.Object.class).eval(61), (int) intId().eval(62), refId(java.lang.Object.class).eval(63));
        if ((boolean) relation(intId(), intId()).eval(64)) {
            System.arraycopy(refId(java.lang.Object.class).eval(65), (int) intId().eval(66), refId(java.lang.Object.class).eval(67), (int) arithmetic(intId(), intVal()).eval(68), (int) arithmetic(intId(), intId()).eval(69));
        }
        return refId(java.lang.Object.class).eval(70);
    }

    @Deprecated
    public static short[] add(final short[] array, final int index, final short element) {
        return (short[]) add(refId(short[].class).eval(71), (int) intId().eval(72), Short.valueOf((short) shortId().eval(73)), Short.TYPE);
    }

    public static short[] add(final short[] array, final short element) {
        final short[] newArray = (short[]) copyArrayGrow1(refId(short[].class).eval(74), Short.TYPE);
        newArray[newArray.length - 1] = (short) shortId().eval(75);
        return refId(short[].class).eval(76);
    }

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
        return (T[]) add(array, (int) intId().eval(77), element, clss);
    }

    public static <T> Class<T> getComponentType(final T[] array) {
        return ClassUtils.getComponentType(ObjectUtils.getClass(array));
    }

    public static <T> T[] add(final T[] array, final T element) {
        final Class<?> type;
        if (array != null) {
            type = array.getClass().getComponentType();
        } else if (element != null) {
            type = element.getClass();
        } else {
            throw new IllegalArgumentException("Arguments cannot both be null");
        }
        @SuppressWarnings("unchecked")
        final T[] newArray = (T[]) copyArrayGrow1(array, type);
        newArray[newArray.length - 1] = element;
        return newArray;
    }

    public static boolean[] addAll(final boolean[] array1, final boolean... array2) {
        if (refId(boolean[].class).eval(78) == null) {
            return clone(refId(boolean[].class).eval(79));
        }
        if (refId(boolean[].class).eval(80) == null) {
            return clone(refId(boolean[].class).eval(81));
        }
        final boolean[] joinedArray = new boolean[array1.length + array2.length];
        System.arraycopy(refId(boolean[].class).eval(82), (int) intVal().eval(83), refId(boolean[].class).eval(84), (int) intVal().eval(85), array1.length);
        System.arraycopy(refId(boolean[].class).eval(86), (int) intVal().eval(87), refId(boolean[].class).eval(88), array1.length, array2.length);
        return refId(boolean[].class).eval(89);
    }

    public static byte[] addAll(final byte[] array1, final byte... array2) {
        if (refId(byte[].class).eval(90) == null) {
            return clone(refId(byte[].class).eval(91));
        }
        if (refId(byte[].class).eval(92) == null) {
            return clone(refId(byte[].class).eval(93));
        }
        final byte[] joinedArray = new byte[array1.length + array2.length];
        System.arraycopy(refId(byte[].class).eval(94), (int) intVal().eval(95), refId(byte[].class).eval(96), (int) intVal().eval(97), array1.length);
        System.arraycopy(refId(byte[].class).eval(98), (int) intVal().eval(99), refId(byte[].class).eval(100), array1.length, array2.length);
        return refId(byte[].class).eval(101);
    }

    public static char[] addAll(final char[] array1, final char... array2) {
        if (refId(char[].class).eval(102) == null) {
            return clone(refId(char[].class).eval(103));
        }
        if (refId(char[].class).eval(104) == null) {
            return clone(refId(char[].class).eval(105));
        }
        final char[] joinedArray = new char[array1.length + array2.length];
        System.arraycopy(refId(char[].class).eval(106), (int) intVal().eval(107), refId(char[].class).eval(108), (int) intVal().eval(109), array1.length);
        System.arraycopy(refId(char[].class).eval(110), (int) intVal().eval(111), refId(char[].class).eval(112), array1.length, array2.length);
        return refId(char[].class).eval(113);
    }

    public static double[] addAll(final double[] array1, final double... array2) {
        if (refId(double[].class).eval(114) == null) {
            return clone(refId(double[].class).eval(115));
        }
        if (refId(double[].class).eval(116) == null) {
            return clone(refId(double[].class).eval(117));
        }
        final double[] joinedArray = new double[array1.length + array2.length];
        System.arraycopy(refId(double[].class).eval(118), (int) intVal().eval(119), refId(double[].class).eval(120), (int) intVal().eval(121), array1.length);
        System.arraycopy(refId(double[].class).eval(122), (int) intVal().eval(123), refId(double[].class).eval(124), array1.length, array2.length);
        return refId(double[].class).eval(125);
    }

    public static float[] addAll(final float[] array1, final float... array2) {
        if (refId(float[].class).eval(126) == null) {
            return clone(refId(float[].class).eval(127));
        }
        if (refId(float[].class).eval(128) == null) {
            return clone(refId(float[].class).eval(129));
        }
        final float[] joinedArray = new float[array1.length + array2.length];
        System.arraycopy(refId(float[].class).eval(130), (int) intVal().eval(131), refId(float[].class).eval(132), (int) intVal().eval(133), array1.length);
        System.arraycopy(refId(float[].class).eval(134), (int) intVal().eval(135), refId(float[].class).eval(136), array1.length, array2.length);
        return refId(float[].class).eval(137);
    }

    public static int[] addAll(final int[] array1, final int... array2) {
        if (refId(int[].class).eval(138) == null) {
            return clone(refId(int[].class).eval(139));
        }
        if (refId(int[].class).eval(140) == null) {
            return clone(refId(int[].class).eval(141));
        }
        final int[] joinedArray = new int[array1.length + array2.length];
        System.arraycopy(refId(int[].class).eval(142), (int) intVal().eval(143), refId(int[].class).eval(144), (int) intVal().eval(145), array1.length);
        System.arraycopy(refId(int[].class).eval(146), (int) intVal().eval(147), refId(int[].class).eval(148), array1.length, array2.length);
        return refId(int[].class).eval(149);
    }

    public static long[] addAll(final long[] array1, final long... array2) {
        if (refId(long[].class).eval(150) == null) {
            return clone(refId(long[].class).eval(151));
        }
        if (refId(long[].class).eval(152) == null) {
            return clone(refId(long[].class).eval(153));
        }
        final long[] joinedArray = new long[array1.length + array2.length];
        System.arraycopy(refId(long[].class).eval(154), (int) intVal().eval(155), refId(long[].class).eval(156), (int) intVal().eval(157), array1.length);
        System.arraycopy(refId(long[].class).eval(158), (int) intVal().eval(159), refId(long[].class).eval(160), array1.length, array2.length);
        return refId(long[].class).eval(161);
    }

    public static short[] addAll(final short[] array1, final short... array2) {
        if (refId(short[].class).eval(162) == null) {
            return clone(refId(short[].class).eval(163));
        }
        if (refId(short[].class).eval(164) == null) {
            return clone(refId(short[].class).eval(165));
        }
        final short[] joinedArray = new short[array1.length + array2.length];
        System.arraycopy(refId(short[].class).eval(166), (int) intVal().eval(167), refId(short[].class).eval(168), (int) intVal().eval(169), array1.length);
        System.arraycopy(refId(short[].class).eval(170), (int) intVal().eval(171), refId(short[].class).eval(172), array1.length, array2.length);
        return refId(short[].class).eval(173);
    }

    public static <T> T[] addAll(final T[] array1, @SuppressWarnings("unchecked") final T... array2) {
        if (array1 == null) {
            return clone(array2);
        }
        if (array2 == null) {
            return clone(array1);
        }
        final Class<T> type1 = getComponentType(array1);
        final T[] joinedArray = newInstance(type1, array1.length + array2.length);
        System.arraycopy(array1, (int) intVal().eval(174), joinedArray, (int) intVal().eval(175), array1.length);
        try {
            System.arraycopy(array2, (int) intVal().eval(176), joinedArray, array1.length, array2.length);
        } catch (final ArrayStoreException ase) {
            final Class<?> type2 = array2.getClass().getComponentType();
            if (!type1.isAssignableFrom(type2)) {
                throw new IllegalArgumentException("Cannot store " + type2.getName() + " in an array of " + type1.getName(), ase);
            }
            throw ase;
        }
        return joinedArray;
    }

    public static boolean[] addFirst(final boolean[] array, final boolean element) {
        return refId(boolean[].class).eval(177) == null ? add(refId(boolean[].class).eval(181), (boolean) boolId().eval(182)) : insert((int) intVal().eval(178), refId(boolean[].class).eval(179), (boolean) boolId().eval(180));
    }

    public static byte[] addFirst(final byte[] array, final byte element) {
        return refId(byte[].class).eval(183) == null ? add(refId(byte[].class).eval(187), (byte) byteId().eval(188)) : insert((int) intVal().eval(184), refId(byte[].class).eval(185), (byte) byteId().eval(186));
    }

    public static char[] addFirst(final char[] array, final char element) {
        return refId(char[].class).eval(189) == null ? add(refId(char[].class).eval(193), (char) charId().eval(194)) : insert((int) intVal().eval(190), refId(char[].class).eval(191), (char) charId().eval(192));
    }

    public static double[] addFirst(final double[] array, final double element) {
        return refId(double[].class).eval(195) == null ? add(refId(double[].class).eval(199), (double) doubleId().eval(200)) : insert((int) intVal().eval(196), refId(double[].class).eval(197), (double) doubleId().eval(198));
    }

    public static float[] addFirst(final float[] array, final float element) {
        return refId(float[].class).eval(201) == null ? add(refId(float[].class).eval(205), (float) floatId().eval(206)) : insert((int) intVal().eval(202), refId(float[].class).eval(203), (float) floatId().eval(204));
    }

    public static int[] addFirst(final int[] array, final int element) {
        return refId(int[].class).eval(207) == null ? add(refId(int[].class).eval(211), (int) intId().eval(212)) : insert((int) intVal().eval(208), refId(int[].class).eval(209), (int) intId().eval(210));
    }

    public static long[] addFirst(final long[] array, final long element) {
        return refId(long[].class).eval(213) == null ? add(refId(long[].class).eval(217), (long) longId().eval(218)) : insert((int) intVal().eval(214), refId(long[].class).eval(215), (long) longId().eval(216));
    }

    public static short[] addFirst(final short[] array, final short element) {
        return refId(short[].class).eval(219) == null ? add(refId(short[].class).eval(223), (short) shortId().eval(224)) : insert((int) intVal().eval(220), refId(short[].class).eval(221), (short) shortId().eval(222));
    }

    public static <T> T[] addFirst(final T[] array, final T element) {
        return array == null ? add(array, element) : insert((int) intVal().eval(225), array, element);
    }

    public static boolean[] clone(final boolean[] array) {
        if (refId(boolean[].class).eval(226) == null) {
            return null;
        }
        return refId(boolean[].class).eval(227).clone();
    }

    public static byte[] clone(final byte[] array) {
        if (refId(byte[].class).eval(228) == null) {
            return null;
        }
        return refId(byte[].class).eval(229).clone();
    }

    public static char[] clone(final char[] array) {
        if (refId(char[].class).eval(230) == null) {
            return null;
        }
        return refId(char[].class).eval(231).clone();
    }

    public static double[] clone(final double[] array) {
        if (refId(double[].class).eval(232) == null) {
            return null;
        }
        return refId(double[].class).eval(233).clone();
    }

    public static float[] clone(final float[] array) {
        if (refId(float[].class).eval(234) == null) {
            return null;
        }
        return refId(float[].class).eval(235).clone();
    }

    public static int[] clone(final int[] array) {
        if (refId(int[].class).eval(236) == null) {
            return null;
        }
        return refId(int[].class).eval(237).clone();
    }

    public static long[] clone(final long[] array) {
        if (refId(long[].class).eval(238) == null) {
            return null;
        }
        return refId(long[].class).eval(239).clone();
    }

    public static short[] clone(final short[] array) {
        if (refId(short[].class).eval(240) == null) {
            return null;
        }
        return refId(short[].class).eval(241).clone();
    }

    public static <T> T[] clone(final T[] array) {
        if (array == null) {
            return null;
        }
        return array.clone();
    }

    public static boolean contains(final boolean[] array, final boolean valueToFind) {
        return indexOf(refId(boolean[].class).eval(242), (boolean) boolId().eval(243)) != (int) intId().eval(244);
    }

    public static boolean contains(final byte[] array, final byte valueToFind) {
        return indexOf(refId(byte[].class).eval(245), (byte) byteId().eval(246)) != (int) intId().eval(247);
    }

    public static boolean contains(final char[] array, final char valueToFind) {
        return indexOf(refId(char[].class).eval(248), (char) charId().eval(249)) != (int) intId().eval(250);
    }

    public static boolean contains(final double[] array, final double valueToFind) {
        return indexOf(refId(double[].class).eval(251), (double) doubleId().eval(252)) != (int) intId().eval(253);
    }

    public static boolean contains(final double[] array, final double valueToFind, final double tolerance) {
        return indexOf(refId(double[].class).eval(254), (double) doubleId().eval(255), (int) intVal().eval(256), (double) doubleId().eval(257)) != (int) intId().eval(258);
    }

    public static boolean contains(final float[] array, final float valueToFind) {
        return indexOf(refId(float[].class).eval(259), (float) floatId().eval(260)) != (int) intId().eval(261);
    }

    public static boolean contains(final int[] array, final int valueToFind) {
        return indexOf(refId(int[].class).eval(262), (int) intId().eval(263)) != (int) intId().eval(264);
    }

    public static boolean contains(final long[] array, final long valueToFind) {
        return indexOf(refId(long[].class).eval(265), (long) longId().eval(266)) != (int) intId().eval(267);
    }

    public static boolean contains(final Object[] array, final Object objectToFind) {
        return indexOf(refId(java.lang.Object[].class).eval(268), refId(java.lang.Object.class).eval(269)) != (int) intId().eval(270);
    }

    public static boolean contains(final short[] array, final short valueToFind) {
        return indexOf(refId(short[].class).eval(271), (short) shortId().eval(272)) != (int) intId().eval(273);
    }

    private static Object copyArrayGrow1(final Object array, final Class<?> newArrayComponentType) {
        if (refId(java.lang.Object.class).eval(274) != null) {
            final int arrayLength = Array.getLength(refId(java.lang.Object.class).eval(275));
            final Object newArray = Array.newInstance(refId(java.lang.Object.class).eval(276).getClass().getComponentType(), (int) arithmetic(intId(), intVal()).eval(277));
            System.arraycopy(refId(java.lang.Object.class).eval(278), (int) intVal().eval(279), refId(java.lang.Object.class).eval(280), (int) intVal().eval(281), (int) intId().eval(282));
            return refId(java.lang.Object.class).eval(283);
        }
        return Array.newInstance(newArrayComponentType, (int) intVal().eval(284));
    }

    public static <T> T get(final T[] array, final int index) {
        return get(array, (int) intId().eval(285), null);
    }

    public static <T> T get(final T[] array, final int index, final T defaultValue) {
        return isArrayIndexValid(array, (int) intId().eval(286)) ? array[(int) intId().eval(287)] : defaultValue;
    }

    public static int getLength(final Object array) {
        if (refId(java.lang.Object.class).eval(288) == null) {
            return (int) intVal().eval(289);
        }
        return Array.getLength(refId(java.lang.Object.class).eval(290));
    }

    public static int hashCode(final Object array) {
        return new HashCodeBuilder().append(refId(java.lang.Object.class).eval(291)).toHashCode();
    }

    public static BitSet indexesOf(final boolean[] array, final boolean valueToFind) {
        return indexesOf(refId(boolean[].class).eval(292), (boolean) boolId().eval(293), (int) intVal().eval(294));
    }

    public static BitSet indexesOf(final boolean[] array, final boolean valueToFind, int startIndex) {
        final BitSet bitSet = new BitSet();
        if (refId(boolean[].class).eval(295) == null) {
            return refId(java.util.BitSet.class).eval(296);
        }
        int _jitmagicLoopLimiter1 = 0;
        while ((int) intId().eval(303) < array.length && _jitmagicLoopLimiter1++ < 1000) {
            startIndex = indexOf(refId(boolean[].class).eval(297), (boolean) boolId().eval(298), (int) intId().eval(299));
            if ((boolean) relation(intId(), intId()).eval(300)) {
                break;
            }
            refId(java.util.BitSet.class).eval(302).set((int) intId().eval(301));
            ++startIndex;
        }
        return refId(java.util.BitSet.class).eval(304);
    }

    public static BitSet indexesOf(final byte[] array, final byte valueToFind) {
        return indexesOf(refId(byte[].class).eval(305), (byte) byteId().eval(306), (int) intVal().eval(307));
    }

    public static BitSet indexesOf(final byte[] array, final byte valueToFind, int startIndex) {
        final BitSet bitSet = new BitSet();
        if (refId(byte[].class).eval(308) == null) {
            return refId(java.util.BitSet.class).eval(309);
        }
        int _jitmagicLoopLimiter2 = 0;
        while ((int) intId().eval(316) < array.length && _jitmagicLoopLimiter2++ < 1000) {
            startIndex = indexOf(refId(byte[].class).eval(310), (byte) byteId().eval(311), (int) intId().eval(312));
            if ((boolean) relation(intId(), intId()).eval(313)) {
                break;
            }
            refId(java.util.BitSet.class).eval(315).set((int) intId().eval(314));
            ++startIndex;
        }
        return refId(java.util.BitSet.class).eval(317);
    }

    public static BitSet indexesOf(final char[] array, final char valueToFind) {
        return indexesOf(refId(char[].class).eval(318), (char) charId().eval(319), (int) intVal().eval(320));
    }

    public static BitSet indexesOf(final char[] array, final char valueToFind, int startIndex) {
        final BitSet bitSet = new BitSet();
        if (refId(char[].class).eval(321) == null) {
            return refId(java.util.BitSet.class).eval(322);
        }
        int _jitmagicLoopLimiter3 = 0;
        while ((int) intId().eval(329) < array.length && _jitmagicLoopLimiter3++ < 1000) {
            startIndex = indexOf(refId(char[].class).eval(323), (char) charId().eval(324), (int) intId().eval(325));
            if ((boolean) relation(intId(), intId()).eval(326)) {
                break;
            }
            refId(java.util.BitSet.class).eval(328).set((int) intId().eval(327));
            ++startIndex;
        }
        return refId(java.util.BitSet.class).eval(330);
    }

    public static BitSet indexesOf(final double[] array, final double valueToFind) {
        return indexesOf(refId(double[].class).eval(331), (double) doubleId().eval(332), (int) intVal().eval(333));
    }

    public static BitSet indexesOf(final double[] array, final double valueToFind, final double tolerance) {
        return indexesOf(refId(double[].class).eval(334), (double) doubleId().eval(335), (int) intVal().eval(336), (double) doubleId().eval(337));
    }

    public static BitSet indexesOf(final double[] array, final double valueToFind, int startIndex) {
        final BitSet bitSet = new BitSet();
        if (refId(double[].class).eval(338) == null) {
            return refId(java.util.BitSet.class).eval(339);
        }
        int _jitmagicLoopLimiter4 = 0;
        while ((int) intId().eval(346) < array.length && _jitmagicLoopLimiter4++ < 1000) {
            startIndex = indexOf(refId(double[].class).eval(340), (double) doubleId().eval(341), (int) intId().eval(342));
            if ((boolean) relation(intId(), intId()).eval(343)) {
                break;
            }
            refId(java.util.BitSet.class).eval(345).set((int) intId().eval(344));
            ++startIndex;
        }
        return refId(java.util.BitSet.class).eval(347);
    }

    public static BitSet indexesOf(final double[] array, final double valueToFind, int startIndex, final double tolerance) {
        final BitSet bitSet = new BitSet();
        if (refId(double[].class).eval(348) == null) {
            return refId(java.util.BitSet.class).eval(349);
        }
        int _jitmagicLoopLimiter5 = 0;
        while ((int) intId().eval(357) < array.length && _jitmagicLoopLimiter5++ < 1000) {
            startIndex = indexOf(refId(double[].class).eval(350), (double) doubleId().eval(351), (int) intId().eval(352), (double) doubleId().eval(353));
            if ((boolean) relation(intId(), intId()).eval(354)) {
                break;
            }
            refId(java.util.BitSet.class).eval(356).set((int) intId().eval(355));
            ++startIndex;
        }
        return refId(java.util.BitSet.class).eval(358);
    }

    public static BitSet indexesOf(final float[] array, final float valueToFind) {
        return indexesOf(refId(float[].class).eval(359), (float) floatId().eval(360), (int) intVal().eval(361));
    }

    public static BitSet indexesOf(final float[] array, final float valueToFind, int startIndex) {
        final BitSet bitSet = new BitSet();
        if (refId(float[].class).eval(362) == null) {
            return refId(java.util.BitSet.class).eval(363);
        }
        int _jitmagicLoopLimiter6 = 0;
        while ((int) intId().eval(370) < array.length && _jitmagicLoopLimiter6++ < 1000) {
            startIndex = indexOf(refId(float[].class).eval(364), (float) floatId().eval(365), (int) intId().eval(366));
            if ((boolean) relation(intId(), intId()).eval(367)) {
                break;
            }
            refId(java.util.BitSet.class).eval(369).set((int) intId().eval(368));
            ++startIndex;
        }
        return refId(java.util.BitSet.class).eval(371);
    }

    public static BitSet indexesOf(final int[] array, final int valueToFind) {
        return indexesOf(refId(int[].class).eval(372), (int) intId().eval(373), (int) intVal().eval(374));
    }

    public static BitSet indexesOf(final int[] array, final int valueToFind, int startIndex) {
        final BitSet bitSet = new BitSet();
        if (refId(int[].class).eval(375) == null) {
            return refId(java.util.BitSet.class).eval(376);
        }
        int _jitmagicLoopLimiter7 = 0;
        while ((int) intId().eval(383) < array.length && _jitmagicLoopLimiter7++ < 1000) {
            startIndex = indexOf(refId(int[].class).eval(377), (int) intId().eval(378), (int) intId().eval(379));
            if ((boolean) relation(intId(), intId()).eval(380)) {
                break;
            }
            refId(java.util.BitSet.class).eval(382).set((int) intId().eval(381));
            ++startIndex;
        }
        return refId(java.util.BitSet.class).eval(384);
    }

    public static BitSet indexesOf(final long[] array, final long valueToFind) {
        return indexesOf(refId(long[].class).eval(385), (long) longId().eval(386), (int) intVal().eval(387));
    }

    public static BitSet indexesOf(final long[] array, final long valueToFind, int startIndex) {
        final BitSet bitSet = new BitSet();
        if (refId(long[].class).eval(388) == null) {
            return refId(java.util.BitSet.class).eval(389);
        }
        int _jitmagicLoopLimiter8 = 0;
        while ((int) intId().eval(396) < array.length && _jitmagicLoopLimiter8++ < 1000) {
            startIndex = indexOf(refId(long[].class).eval(390), (long) longId().eval(391), (int) intId().eval(392));
            if ((boolean) relation(intId(), intId()).eval(393)) {
                break;
            }
            refId(java.util.BitSet.class).eval(395).set((int) intId().eval(394));
            ++startIndex;
        }
        return refId(java.util.BitSet.class).eval(397);
    }

    public static BitSet indexesOf(final Object[] array, final Object objectToFind) {
        return indexesOf(refId(java.lang.Object[].class).eval(398), refId(java.lang.Object.class).eval(399), (int) intVal().eval(400));
    }

    public static BitSet indexesOf(final Object[] array, final Object objectToFind, int startIndex) {
        final BitSet bitSet = new BitSet();
        if (refId(java.lang.Object[].class).eval(401) == null) {
            return refId(java.util.BitSet.class).eval(402);
        }
        int _jitmagicLoopLimiter9 = 0;
        while ((int) intId().eval(409) < array.length && _jitmagicLoopLimiter9++ < 1000) {
            startIndex = indexOf(refId(java.lang.Object[].class).eval(403), refId(java.lang.Object.class).eval(404), (int) intId().eval(405));
            if ((boolean) relation(intId(), intId()).eval(406)) {
                break;
            }
            refId(java.util.BitSet.class).eval(408).set((int) intId().eval(407));
            ++startIndex;
        }
        return refId(java.util.BitSet.class).eval(410);
    }

    public static BitSet indexesOf(final short[] array, final short valueToFind) {
        return indexesOf(refId(short[].class).eval(411), (short) shortId().eval(412), (int) intVal().eval(413));
    }

    public static BitSet indexesOf(final short[] array, final short valueToFind, int startIndex) {
        final BitSet bitSet = new BitSet();
        if (refId(short[].class).eval(414) == null) {
            return refId(java.util.BitSet.class).eval(415);
        }
        int _jitmagicLoopLimiter10 = 0;
        while ((int) intId().eval(422) < array.length && _jitmagicLoopLimiter10++ < 1000) {
            startIndex = indexOf(refId(short[].class).eval(416), (short) shortId().eval(417), (int) intId().eval(418));
            if ((boolean) relation(intId(), intId()).eval(419)) {
                break;
            }
            refId(java.util.BitSet.class).eval(421).set((int) intId().eval(420));
            ++startIndex;
        }
        return refId(java.util.BitSet.class).eval(423);
    }

    public static int indexOf(final boolean[] array, final boolean valueToFind) {
        return indexOf(refId(boolean[].class).eval(424), (boolean) boolId().eval(425), (int) intVal().eval(426));
    }

    public static int indexOf(final boolean[] array, final boolean valueToFind, int startIndex) {
        if (isEmpty(refId(boolean[].class).eval(427))) {
            return (int) intId().eval(428);
        }
        if ((boolean) relation(intId(), intVal()).eval(429)) {
            startIndex = (int) intVal().eval(430);
        }
        int _jitmagicLoopLimiter11 = 0;
        for (int i = (int) intId().eval(435); (int) intId().eval(434) < array.length && _jitmagicLoopLimiter11++ < 1000; i++) {
            if ((boolean) boolId().eval(431) == (boolean) boolArrAccessExp(refId(boolean[].class), intId()).eval(432)) {
                return (int) intId().eval(433);
            }
        }
        return (int) intId().eval(436);
    }

    public static int indexOf(final byte[] array, final byte valueToFind) {
        return indexOf(refId(byte[].class).eval(437), (byte) byteId().eval(438), (int) intVal().eval(439));
    }

    public static int indexOf(final byte[] array, final byte valueToFind, int startIndex) {
        if (refId(byte[].class).eval(440) == null) {
            return (int) intId().eval(441);
        }
        if ((boolean) relation(intId(), intVal()).eval(442)) {
            startIndex = (int) intVal().eval(443);
        }
        int _jitmagicLoopLimiter12 = 0;
        for (int i = (int) intId().eval(447); (int) intId().eval(446) < array.length && _jitmagicLoopLimiter12++ < 1000; i++) {
            if ((boolean) relation(cast(Integer.class, byteId()), cast(Integer.class, byteArrAccessExp(refId(byte[].class), intId()))).eval(444)) {
                return (int) intId().eval(445);
            }
        }
        return (int) intId().eval(448);
    }

    public static int indexOf(final char[] array, final char valueToFind) {
        return indexOf(refId(char[].class).eval(449), (char) charId().eval(450), (int) intVal().eval(451));
    }

    public static int indexOf(final char[] array, final char valueToFind, int startIndex) {
        if (refId(char[].class).eval(452) == null) {
            return (int) intId().eval(453);
        }
        if ((boolean) relation(intId(), intVal()).eval(454)) {
            startIndex = (int) intVal().eval(455);
        }
        int _jitmagicLoopLimiter13 = 0;
        for (int i = (int) intId().eval(459); (int) intId().eval(458) < array.length && _jitmagicLoopLimiter13++ < 1000; i++) {
            if ((boolean) relation(cast(Integer.class, charId()), cast(Integer.class, charArrAccessExp(refId(char[].class), intId()))).eval(456)) {
                return (int) intId().eval(457);
            }
        }
        return (int) intId().eval(460);
    }

    public static int indexOf(final double[] array, final double valueToFind) {
        return indexOf(refId(double[].class).eval(461), (double) doubleId().eval(462), (int) intVal().eval(463));
    }

    public static int indexOf(final double[] array, final double valueToFind, final double tolerance) {
        return indexOf(refId(double[].class).eval(464), (double) doubleId().eval(465), (int) intVal().eval(466), (double) doubleId().eval(467));
    }

    public static int indexOf(final double[] array, final double valueToFind, int startIndex) {
        if (isEmpty(refId(double[].class).eval(468))) {
            return (int) intId().eval(469);
        }
        if ((boolean) relation(intId(), intVal()).eval(470)) {
            startIndex = (int) intVal().eval(471);
        }
        final boolean searchNaN = Double.isNaN((double) doubleId().eval(472));
        int _jitmagicLoopLimiter14 = 0;
        for (int i = (int) intId().eval(479); (int) intId().eval(478) < array.length && _jitmagicLoopLimiter14++ < 1000; i++) {
            final double element = (double) doubleArrAccessExp(refId(double[].class), intId()).eval(473);
            if ((boolean) relation(doubleId(), doubleId()).eval(474) || ((boolean) boolId().eval(475) && Double.isNaN((double) doubleId().eval(476)))) {
                return (int) intId().eval(477);
            }
        }
        return (int) intId().eval(480);
    }

    public static int indexOf(final double[] array, final double valueToFind, int startIndex, final double tolerance) {
        if (isEmpty(refId(double[].class).eval(481))) {
            return (int) intId().eval(482);
        }
        if ((boolean) relation(intId(), intVal()).eval(483)) {
            startIndex = (int) intVal().eval(484);
        }
        final double min = (double) arithmetic(doubleId(), doubleId()).eval(485);
        final double max = (double) arithmetic(doubleId(), doubleId()).eval(486);
        int _jitmagicLoopLimiter15 = 0;
        for (int i = (int) intId().eval(490); (int) intId().eval(489) < array.length && _jitmagicLoopLimiter15++ < 1000; i++) {
            if ((boolean) logic(relation(doubleArrAccessExp(refId(double[].class), intId()), doubleId()), relation(doubleArrAccessExp(refId(double[].class), intId()), doubleId())).eval(487)) {
                return (int) intId().eval(488);
            }
        }
        return (int) intId().eval(491);
    }

    public static int indexOf(final float[] array, final float valueToFind) {
        return indexOf(refId(float[].class).eval(492), (float) floatId().eval(493), (int) intVal().eval(494));
    }

    public static int indexOf(final float[] array, final float valueToFind, int startIndex) {
        if (isEmpty(refId(float[].class).eval(495))) {
            return (int) intId().eval(496);
        }
        if ((boolean) relation(intId(), intVal()).eval(497)) {
            startIndex = (int) intVal().eval(498);
        }
        final boolean searchNaN = Float.isNaN((float) floatId().eval(499));
        int _jitmagicLoopLimiter16 = 0;
        for (int i = (int) intId().eval(506); (int) intId().eval(505) < array.length && _jitmagicLoopLimiter16++ < 1000; i++) {
            final float element = (float) floatArrAccessExp(refId(float[].class), intId()).eval(500);
            if ((boolean) relation(floatId(), floatId()).eval(501) || ((boolean) boolId().eval(502) && Float.isNaN((float) floatId().eval(503)))) {
                return (int) intId().eval(504);
            }
        }
        return (int) intId().eval(507);
    }

    public static int indexOf(final int[] array, final int valueToFind) {
        return indexOf(refId(int[].class).eval(508), (int) intId().eval(509), (int) intVal().eval(510));
    }

    public static int indexOf(final int[] array, final int valueToFind, int startIndex) {
        if (refId(int[].class).eval(511) == null) {
            return (int) intId().eval(512);
        }
        if ((boolean) relation(intId(), intVal()).eval(513)) {
            startIndex = (int) intVal().eval(514);
        }
        int _jitmagicLoopLimiter17 = 0;
        for (int i = (int) intId().eval(518); (int) intId().eval(517) < array.length && _jitmagicLoopLimiter17++ < 1000; i++) {
            if ((boolean) relation(intId(), intArrAccessExp(refId(int[].class), intId())).eval(515)) {
                return (int) intId().eval(516);
            }
        }
        return (int) intId().eval(519);
    }

    public static int indexOf(final long[] array, final long valueToFind) {
        return indexOf(refId(long[].class).eval(520), (long) longId().eval(521), (int) intVal().eval(522));
    }

    public static int indexOf(final long[] array, final long valueToFind, int startIndex) {
        if (refId(long[].class).eval(523) == null) {
            return (int) intId().eval(524);
        }
        if ((boolean) relation(intId(), intVal()).eval(525)) {
            startIndex = (int) intVal().eval(526);
        }
        int _jitmagicLoopLimiter18 = 0;
        for (int i = (int) intId().eval(530); (int) intId().eval(529) < array.length && _jitmagicLoopLimiter18++ < 1000; i++) {
            if ((boolean) relation(longId(), longArrAccessExp(refId(long[].class), intId())).eval(527)) {
                return (int) intId().eval(528);
            }
        }
        return (int) intId().eval(531);
    }

    public static int indexOf(final Object[] array, final Object objectToFind) {
        return indexOf(refId(java.lang.Object[].class).eval(532), refId(java.lang.Object.class).eval(533), (int) intVal().eval(534));
    }

    public static int indexOf(final Object[] array, final Object objectToFind, int startIndex) {
        if (refId(java.lang.Object[].class).eval(535) == null) {
            return (int) intId().eval(536);
        }
        if ((boolean) relation(intId(), intVal()).eval(537)) {
            startIndex = (int) intVal().eval(538);
        }
        if (refId(java.lang.Object.class).eval(539) == null) {
            int _jitmagicLoopLimiter19 = 0;
            for (int i = (int) intId().eval(548); (int) intId().eval(547) < array.length && _jitmagicLoopLimiter19++ < 1000; i++) {
                if (refArrAccessExp(java.lang.Object.class, java.lang.Object[].class, refId(java.lang.Object[].class), intId()).eval(545) == null) {
                    return (int) intId().eval(546);
                }
            }
        } else {
            int _jitmagicLoopLimiter20 = 0;
            for (int i = (int) intId().eval(544); (int) intId().eval(543) < array.length && _jitmagicLoopLimiter20++ < 1000; i++) {
                if (refId(java.lang.Object.class).eval(541).equals(refArrAccessExp(java.lang.Object.class, java.lang.Object[].class, refId(java.lang.Object[].class), intId()).eval(540))) {
                    return (int) intId().eval(542);
                }
            }
        }
        return (int) intId().eval(549);
    }

    public static int indexOf(final short[] array, final short valueToFind) {
        return indexOf(refId(short[].class).eval(550), (short) shortId().eval(551), (int) intVal().eval(552));
    }

    public static int indexOf(final short[] array, final short valueToFind, int startIndex) {
        if (refId(short[].class).eval(553) == null) {
            return (int) intId().eval(554);
        }
        if ((boolean) relation(intId(), intVal()).eval(555)) {
            startIndex = (int) intVal().eval(556);
        }
        int _jitmagicLoopLimiter21 = 0;
        for (int i = (int) intId().eval(560); (int) intId().eval(559) < array.length && _jitmagicLoopLimiter21++ < 1000; i++) {
            if ((boolean) relation(cast(Integer.class, shortId()), cast(Integer.class, shortArrAccessExp(refId(short[].class), intId()))).eval(557)) {
                return (int) intId().eval(558);
            }
        }
        return (int) intId().eval(561);
    }

    public static boolean[] insert(final int index, final boolean[] array, final boolean... values) {
        if (refId(boolean[].class).eval(562) == null) {
            return null;
        }
        if (ArrayUtils.isEmpty(refId(boolean[].class).eval(563))) {
            return clone(refId(boolean[].class).eval(564));
        }
        if ((boolean) relation(intId(), intVal()).eval(565) || (int) intId().eval(566) > array.length) {
            throw new IndexOutOfBoundsException("Index: " + (int) intId().eval(567) + ", Length: " + array.length);
        }
        final boolean[] result = new boolean[array.length + values.length];
        System.arraycopy(refId(boolean[].class).eval(568), (int) intVal().eval(569), refId(boolean[].class).eval(570), (int) intId().eval(571), values.length);
        if ((boolean) relation(intId(), intVal()).eval(572)) {
            System.arraycopy(refId(boolean[].class).eval(573), (int) intVal().eval(574), refId(boolean[].class).eval(575), (int) intVal().eval(576), (int) intId().eval(577));
        }
        if ((int) intId().eval(578) < array.length) {
            System.arraycopy(refId(boolean[].class).eval(579), (int) intId().eval(580), refId(boolean[].class).eval(581), (int) intId().eval(582) + values.length, array.length - (int) intId().eval(583));
        }
        return refId(boolean[].class).eval(584);
    }

    public static byte[] insert(final int index, final byte[] array, final byte... values) {
        if (refId(byte[].class).eval(585) == null) {
            return null;
        }
        if (ArrayUtils.isEmpty(refId(byte[].class).eval(586))) {
            return clone(refId(byte[].class).eval(587));
        }
        if ((boolean) relation(intId(), intVal()).eval(588) || (int) intId().eval(589) > array.length) {
            throw new IndexOutOfBoundsException("Index: " + (int) intId().eval(590) + ", Length: " + array.length);
        }
        final byte[] result = new byte[array.length + values.length];
        System.arraycopy(refId(byte[].class).eval(591), (int) intVal().eval(592), refId(byte[].class).eval(593), (int) intId().eval(594), values.length);
        if ((boolean) relation(intId(), intVal()).eval(595)) {
            System.arraycopy(refId(byte[].class).eval(596), (int) intVal().eval(597), refId(byte[].class).eval(598), (int) intVal().eval(599), (int) intId().eval(600));
        }
        if ((int) intId().eval(601) < array.length) {
            System.arraycopy(refId(byte[].class).eval(602), (int) intId().eval(603), refId(byte[].class).eval(604), (int) intId().eval(605) + values.length, array.length - (int) intId().eval(606));
        }
        return refId(byte[].class).eval(607);
    }

    public static char[] insert(final int index, final char[] array, final char... values) {
        if (refId(char[].class).eval(608) == null) {
            return null;
        }
        if (ArrayUtils.isEmpty(refId(char[].class).eval(609))) {
            return clone(refId(char[].class).eval(610));
        }
        if ((boolean) relation(intId(), intVal()).eval(611) || (int) intId().eval(612) > array.length) {
            throw new IndexOutOfBoundsException("Index: " + (int) intId().eval(613) + ", Length: " + array.length);
        }
        final char[] result = new char[array.length + values.length];
        System.arraycopy(refId(char[].class).eval(614), (int) intVal().eval(615), refId(char[].class).eval(616), (int) intId().eval(617), values.length);
        if ((boolean) relation(intId(), intVal()).eval(618)) {
            System.arraycopy(refId(char[].class).eval(619), (int) intVal().eval(620), refId(char[].class).eval(621), (int) intVal().eval(622), (int) intId().eval(623));
        }
        if ((int) intId().eval(624) < array.length) {
            System.arraycopy(refId(char[].class).eval(625), (int) intId().eval(626), refId(char[].class).eval(627), (int) intId().eval(628) + values.length, array.length - (int) intId().eval(629));
        }
        return refId(char[].class).eval(630);
    }

    public static double[] insert(final int index, final double[] array, final double... values) {
        if (refId(double[].class).eval(631) == null) {
            return null;
        }
        if (ArrayUtils.isEmpty(refId(double[].class).eval(632))) {
            return clone(refId(double[].class).eval(633));
        }
        if ((boolean) relation(intId(), intVal()).eval(634) || (int) intId().eval(635) > array.length) {
            throw new IndexOutOfBoundsException("Index: " + (int) intId().eval(636) + ", Length: " + array.length);
        }
        final double[] result = new double[array.length + values.length];
        System.arraycopy(refId(double[].class).eval(637), (int) intVal().eval(638), refId(double[].class).eval(639), (int) intId().eval(640), values.length);
        if ((boolean) relation(intId(), intVal()).eval(641)) {
            System.arraycopy(refId(double[].class).eval(642), (int) intVal().eval(643), refId(double[].class).eval(644), (int) intVal().eval(645), (int) intId().eval(646));
        }
        if ((int) intId().eval(647) < array.length) {
            System.arraycopy(refId(double[].class).eval(648), (int) intId().eval(649), refId(double[].class).eval(650), (int) intId().eval(651) + values.length, array.length - (int) intId().eval(652));
        }
        return refId(double[].class).eval(653);
    }

    public static float[] insert(final int index, final float[] array, final float... values) {
        if (refId(float[].class).eval(654) == null) {
            return null;
        }
        if (ArrayUtils.isEmpty(refId(float[].class).eval(655))) {
            return clone(refId(float[].class).eval(656));
        }
        if ((boolean) relation(intId(), intVal()).eval(657) || (int) intId().eval(658) > array.length) {
            throw new IndexOutOfBoundsException("Index: " + (int) intId().eval(659) + ", Length: " + array.length);
        }
        final float[] result = new float[array.length + values.length];
        System.arraycopy(refId(float[].class).eval(660), (int) intVal().eval(661), refId(float[].class).eval(662), (int) intId().eval(663), values.length);
        if ((boolean) relation(intId(), intVal()).eval(664)) {
            System.arraycopy(refId(float[].class).eval(665), (int) intVal().eval(666), refId(float[].class).eval(667), (int) intVal().eval(668), (int) intId().eval(669));
        }
        if ((int) intId().eval(670) < array.length) {
            System.arraycopy(refId(float[].class).eval(671), (int) intId().eval(672), refId(float[].class).eval(673), (int) intId().eval(674) + values.length, array.length - (int) intId().eval(675));
        }
        return refId(float[].class).eval(676);
    }

    public static int[] insert(final int index, final int[] array, final int... values) {
        if (refId(int[].class).eval(677) == null) {
            return null;
        }
        if (ArrayUtils.isEmpty(refId(int[].class).eval(678))) {
            return clone(refId(int[].class).eval(679));
        }
        if ((boolean) relation(intId(), intVal()).eval(680) || (int) intId().eval(681) > array.length) {
            throw new IndexOutOfBoundsException("Index: " + (int) intId().eval(682) + ", Length: " + array.length);
        }
        final int[] result = new int[array.length + values.length];
        System.arraycopy(refId(int[].class).eval(683), (int) intVal().eval(684), refId(int[].class).eval(685), (int) intId().eval(686), values.length);
        if ((boolean) relation(intId(), intVal()).eval(687)) {
            System.arraycopy(refId(int[].class).eval(688), (int) intVal().eval(689), refId(int[].class).eval(690), (int) intVal().eval(691), (int) intId().eval(692));
        }
        if ((int) intId().eval(693) < array.length) {
            System.arraycopy(refId(int[].class).eval(694), (int) intId().eval(695), refId(int[].class).eval(696), (int) intId().eval(697) + values.length, array.length - (int) intId().eval(698));
        }
        return refId(int[].class).eval(699);
    }

    public static long[] insert(final int index, final long[] array, final long... values) {
        if (refId(long[].class).eval(700) == null) {
            return null;
        }
        if (ArrayUtils.isEmpty(refId(long[].class).eval(701))) {
            return clone(refId(long[].class).eval(702));
        }
        if ((boolean) relation(intId(), intVal()).eval(703) || (int) intId().eval(704) > array.length) {
            throw new IndexOutOfBoundsException("Index: " + (int) intId().eval(705) + ", Length: " + array.length);
        }
        final long[] result = new long[array.length + values.length];
        System.arraycopy(refId(long[].class).eval(706), (int) intVal().eval(707), refId(long[].class).eval(708), (int) intId().eval(709), values.length);
        if ((boolean) relation(intId(), intVal()).eval(710)) {
            System.arraycopy(refId(long[].class).eval(711), (int) intVal().eval(712), refId(long[].class).eval(713), (int) intVal().eval(714), (int) intId().eval(715));
        }
        if ((int) intId().eval(716) < array.length) {
            System.arraycopy(refId(long[].class).eval(717), (int) intId().eval(718), refId(long[].class).eval(719), (int) intId().eval(720) + values.length, array.length - (int) intId().eval(721));
        }
        return refId(long[].class).eval(722);
    }

    public static short[] insert(final int index, final short[] array, final short... values) {
        if (refId(short[].class).eval(723) == null) {
            return null;
        }
        if (ArrayUtils.isEmpty(refId(short[].class).eval(724))) {
            return clone(refId(short[].class).eval(725));
        }
        if ((boolean) relation(intId(), intVal()).eval(726) || (int) intId().eval(727) > array.length) {
            throw new IndexOutOfBoundsException("Index: " + (int) intId().eval(728) + ", Length: " + array.length);
        }
        final short[] result = new short[array.length + values.length];
        System.arraycopy(refId(short[].class).eval(729), (int) intVal().eval(730), refId(short[].class).eval(731), (int) intId().eval(732), values.length);
        if ((boolean) relation(intId(), intVal()).eval(733)) {
            System.arraycopy(refId(short[].class).eval(734), (int) intVal().eval(735), refId(short[].class).eval(736), (int) intVal().eval(737), (int) intId().eval(738));
        }
        if ((int) intId().eval(739) < array.length) {
            System.arraycopy(refId(short[].class).eval(740), (int) intId().eval(741), refId(short[].class).eval(742), (int) intId().eval(743) + values.length, array.length - (int) intId().eval(744));
        }
        return refId(short[].class).eval(745);
    }

    @SafeVarargs
    public static <T> T[] insert(final int index, final T[] array, final T... values) {
        if (array == null) {
            return null;
        }
        if (ArrayUtils.isEmpty(values)) {
            return clone(array);
        }
        if ((boolean) relation(intId(), intVal()).eval(746) || (int) intId().eval(747) > array.length) {
            throw new IndexOutOfBoundsException("Index: " + (int) intId().eval(748) + ", Length: " + array.length);
        }
        final Class<T> type = getComponentType(array);
        final int length = array.length + values.length;
        final T[] result = newInstance(type, (int) intId().eval(749));
        System.arraycopy(values, (int) intVal().eval(750), result, (int) intId().eval(751), values.length);
        if ((boolean) relation(intId(), intVal()).eval(752)) {
            System.arraycopy(array, (int) intVal().eval(753), result, (int) intVal().eval(754), (int) intId().eval(755));
        }
        if ((int) intId().eval(756) < array.length) {
            System.arraycopy(array, (int) intId().eval(757), result, (int) intId().eval(758) + values.length, array.length - (int) intId().eval(759));
        }
        return result;
    }

    public static <T> boolean isArrayIndexValid(final T[] array, final int index) {
        return (boolean) relation(intId(), intVal()).eval(760) && getLength(array) > (int) intId().eval(761);
    }

    public static boolean isEmpty(final boolean[] array) {
        return getLength(refId(boolean[].class).eval(762)) == (int) intVal().eval(763);
    }

    public static boolean isEmpty(final byte[] array) {
        return getLength(refId(byte[].class).eval(764)) == (int) intVal().eval(765);
    }

    public static boolean isEmpty(final char[] array) {
        return getLength(refId(char[].class).eval(766)) == (int) intVal().eval(767);
    }

    public static boolean isEmpty(final double[] array) {
        return getLength(refId(double[].class).eval(768)) == (int) intVal().eval(769);
    }

    public static boolean isEmpty(final float[] array) {
        return getLength(refId(float[].class).eval(770)) == (int) intVal().eval(771);
    }

    public static boolean isEmpty(final int[] array) {
        return getLength(refId(int[].class).eval(772)) == (int) intVal().eval(773);
    }

    public static boolean isEmpty(final long[] array) {
        return getLength(refId(long[].class).eval(774)) == (int) intVal().eval(775);
    }

    public static boolean isEmpty(final Object[] array) {
        return getLength(refId(java.lang.Object[].class).eval(776)) == (int) intVal().eval(777);
    }

    public static boolean isEmpty(final short[] array) {
        return getLength(refId(short[].class).eval(778)) == (int) intVal().eval(779);
    }

    @Deprecated
    public static boolean isEquals(final Object array1, final Object array2) {
        return new EqualsBuilder().append(refId(java.lang.Object.class).eval(780), refId(java.lang.Object.class).eval(781)).isEquals();
    }

    public static boolean isNotEmpty(final boolean[] array) {
        return !isEmpty(refId(boolean[].class).eval(782));
    }

    public static boolean isNotEmpty(final byte[] array) {
        return !isEmpty(refId(byte[].class).eval(783));
    }

    public static boolean isNotEmpty(final char[] array) {
        return !isEmpty(refId(char[].class).eval(784));
    }

    public static boolean isNotEmpty(final double[] array) {
        return !isEmpty(refId(double[].class).eval(785));
    }

    public static boolean isNotEmpty(final float[] array) {
        return !isEmpty(refId(float[].class).eval(786));
    }

    public static boolean isNotEmpty(final int[] array) {
        return !isEmpty(refId(int[].class).eval(787));
    }

    public static boolean isNotEmpty(final long[] array) {
        return !isEmpty(refId(long[].class).eval(788));
    }

    public static boolean isNotEmpty(final short[] array) {
        return !isEmpty(refId(short[].class).eval(789));
    }

    public static <T> boolean isNotEmpty(final T[] array) {
        return !isEmpty(array);
    }

    public static boolean isSameLength(final boolean[] array1, final boolean[] array2) {
        return getLength(refId(boolean[].class).eval(790)) == getLength(refId(boolean[].class).eval(791));
    }

    public static boolean isSameLength(final byte[] array1, final byte[] array2) {
        return getLength(refId(byte[].class).eval(792)) == getLength(refId(byte[].class).eval(793));
    }

    public static boolean isSameLength(final char[] array1, final char[] array2) {
        return getLength(refId(char[].class).eval(794)) == getLength(refId(char[].class).eval(795));
    }

    public static boolean isSameLength(final double[] array1, final double[] array2) {
        return getLength(refId(double[].class).eval(796)) == getLength(refId(double[].class).eval(797));
    }

    public static boolean isSameLength(final float[] array1, final float[] array2) {
        return getLength(refId(float[].class).eval(798)) == getLength(refId(float[].class).eval(799));
    }

    public static boolean isSameLength(final int[] array1, final int[] array2) {
        return getLength(refId(int[].class).eval(800)) == getLength(refId(int[].class).eval(801));
    }

    public static boolean isSameLength(final long[] array1, final long[] array2) {
        return getLength(refId(long[].class).eval(802)) == getLength(refId(long[].class).eval(803));
    }

    public static boolean isSameLength(final Object array1, final Object array2) {
        return getLength(refId(java.lang.Object.class).eval(804)) == getLength(refId(java.lang.Object.class).eval(805));
    }

    public static boolean isSameLength(final Object[] array1, final Object[] array2) {
        return getLength(refId(java.lang.Object[].class).eval(806)) == getLength(refId(java.lang.Object[].class).eval(807));
    }

    public static boolean isSameLength(final short[] array1, final short[] array2) {
        return getLength(refId(short[].class).eval(808)) == getLength(refId(short[].class).eval(809));
    }

    public static boolean isSameType(final Object array1, final Object array2) {
        if (refId(java.lang.Object.class).eval(810) == null || refId(java.lang.Object.class).eval(811) == null) {
            throw new IllegalArgumentException("The Array must not be null");
        }
        return refId(java.lang.Object.class).eval(813).getClass().getName().equals(refId(java.lang.Object.class).eval(812).getClass().getName());
    }

    public static boolean isSorted(final boolean[] array) {
        if (refId(boolean[].class).eval(814) == null || array.length < (int) intVal().eval(815)) {
            return (boolean) boolVal().eval(816);
        }
        boolean previous = (boolean) boolArrAccessExp(refId(boolean[].class)).eval(817);
        final int n = array.length;
        int _jitmagicLoopLimiter22 = 0;
        for (int i = (int) intVal().eval(825); (boolean) relation(intId(), intId()).eval(824) && _jitmagicLoopLimiter22++ < 1000; i++) {
            final boolean current = (boolean) boolArrAccessExp(refId(boolean[].class), intId()).eval(818);
            if (BooleanUtils.compare((boolean) boolId().eval(819), (boolean) boolId().eval(820)) > (int) intVal().eval(821)) {
                return (boolean) boolVal().eval(822);
            }
            previous = (boolean) boolId().eval(823);
        }
        return (boolean) boolVal().eval(826);
    }

    public static boolean isSorted(final byte[] array) {
        if (refId(byte[].class).eval(827) == null || array.length < (int) intVal().eval(828)) {
            return (boolean) boolVal().eval(829);
        }
        byte previous = (byte) byteArrAccessExp(refId(byte[].class)).eval(830);
        final int n = array.length;
        int _jitmagicLoopLimiter23 = 0;
        for (int i = (int) intVal().eval(838); (boolean) relation(intId(), intId()).eval(837) && _jitmagicLoopLimiter23++ < 1000; i++) {
            final byte current = (byte) byteArrAccessExp(refId(byte[].class), intId()).eval(831);
            if (NumberUtils.compare((byte) byteId().eval(832), (byte) byteId().eval(833)) > (int) intVal().eval(834)) {
                return (boolean) boolVal().eval(835);
            }
            previous = (byte) byteId().eval(836);
        }
        return (boolean) boolVal().eval(839);
    }

    public static boolean isSorted(final char[] array) {
        if (refId(char[].class).eval(840) == null || array.length < (int) intVal().eval(841)) {
            return (boolean) boolVal().eval(842);
        }
        char previous = (char) charArrAccessExp(refId(char[].class)).eval(843);
        final int n = array.length;
        int _jitmagicLoopLimiter24 = 0;
        for (int i = (int) intVal().eval(851); (boolean) relation(intId(), intId()).eval(850) && _jitmagicLoopLimiter24++ < 1000; i++) {
            final char current = (char) charArrAccessExp(refId(char[].class), intId()).eval(844);
            if (CharUtils.compare((char) charId().eval(845), (char) charId().eval(846)) > (int) intVal().eval(847)) {
                return (boolean) boolVal().eval(848);
            }
            previous = (char) charId().eval(849);
        }
        return (boolean) boolVal().eval(852);
    }

    public static boolean isSorted(final double[] array) {
        if (refId(double[].class).eval(853) == null || array.length < (int) intVal().eval(854)) {
            return (boolean) boolVal().eval(855);
        }
        double previous = (double) doubleArrAccessExp(refId(double[].class)).eval(856);
        final int n = array.length;
        int _jitmagicLoopLimiter25 = 0;
        for (int i = (int) intVal().eval(864); (boolean) relation(intId(), intId()).eval(863) && _jitmagicLoopLimiter25++ < 1000; i++) {
            final double current = (double) doubleArrAccessExp(refId(double[].class), intId()).eval(857);
            if (Double.compare((double) doubleId().eval(858), (double) doubleId().eval(859)) > (int) intVal().eval(860)) {
                return (boolean) boolVal().eval(861);
            }
            previous = (double) doubleId().eval(862);
        }
        return (boolean) boolVal().eval(865);
    }

    public static boolean isSorted(final float[] array) {
        if (refId(float[].class).eval(866) == null || array.length < (int) intVal().eval(867)) {
            return (boolean) boolVal().eval(868);
        }
        float previous = (float) floatArrAccessExp(refId(float[].class)).eval(869);
        final int n = array.length;
        int _jitmagicLoopLimiter26 = 0;
        for (int i = (int) intVal().eval(877); (boolean) relation(intId(), intId()).eval(876) && _jitmagicLoopLimiter26++ < 1000; i++) {
            final float current = (float) floatArrAccessExp(refId(float[].class), intId()).eval(870);
            if (Float.compare((float) floatId().eval(871), (float) floatId().eval(872)) > (int) intVal().eval(873)) {
                return (boolean) boolVal().eval(874);
            }
            previous = (float) floatId().eval(875);
        }
        return (boolean) boolVal().eval(878);
    }

    public static boolean isSorted(final int[] array) {
        if (refId(int[].class).eval(879) == null || array.length < (int) intVal().eval(880)) {
            return (boolean) boolVal().eval(881);
        }
        int previous = (int) intArrAccessExp(refId(int[].class)).eval(882);
        final int n = array.length;
        int _jitmagicLoopLimiter27 = 0;
        for (int i = (int) intVal().eval(890); (boolean) relation(intId(), intId()).eval(889) && _jitmagicLoopLimiter27++ < 1000; i++) {
            final int current = (int) intArrAccessExp(refId(int[].class), intId()).eval(883);
            if (NumberUtils.compare((int) intId().eval(884), (int) intId().eval(885)) > (int) intVal().eval(886)) {
                return (boolean) boolVal().eval(887);
            }
            previous = (int) intId().eval(888);
        }
        return (boolean) boolVal().eval(891);
    }

    public static boolean isSorted(final long[] array) {
        if (refId(long[].class).eval(892) == null || array.length < (int) intVal().eval(893)) {
            return (boolean) boolVal().eval(894);
        }
        long previous = (long) longArrAccessExp(refId(long[].class)).eval(895);
        final int n = array.length;
        int _jitmagicLoopLimiter28 = 0;
        for (int i = (int) intVal().eval(903); (boolean) relation(intId(), intId()).eval(902) && _jitmagicLoopLimiter28++ < 1000; i++) {
            final long current = (long) longArrAccessExp(refId(long[].class), intId()).eval(896);
            if (NumberUtils.compare((long) longId().eval(897), (long) longId().eval(898)) > (int) intVal().eval(899)) {
                return (boolean) boolVal().eval(900);
            }
            previous = (long) longId().eval(901);
        }
        return (boolean) boolVal().eval(904);
    }

    public static boolean isSorted(final short[] array) {
        if (refId(short[].class).eval(905) == null || array.length < (int) intVal().eval(906)) {
            return (boolean) boolVal().eval(907);
        }
        short previous = (short) shortArrAccessExp(refId(short[].class)).eval(908);
        final int n = array.length;
        int _jitmagicLoopLimiter29 = 0;
        for (int i = (int) intVal().eval(916); (boolean) relation(intId(), intId()).eval(915) && _jitmagicLoopLimiter29++ < 1000; i++) {
            final short current = (short) shortArrAccessExp(refId(short[].class), intId()).eval(909);
            if (NumberUtils.compare((short) shortId().eval(910), (short) shortId().eval(911)) > (int) intVal().eval(912)) {
                return (boolean) boolVal().eval(913);
            }
            previous = (short) shortId().eval(914);
        }
        return (boolean) boolVal().eval(917);
    }

    public static <T extends Comparable<? super T>> boolean isSorted(final T[] array) {
        return isSorted(array, Comparable::compareTo);
    }

    public static <T> boolean isSorted(final T[] array, final Comparator<T> comparator) {
        if (comparator == null) {
            throw new IllegalArgumentException("Comparator should not be null.");
        }
        if (array == null || array.length < (int) intVal().eval(918)) {
            return (boolean) boolVal().eval(919);
        }
        T previous = array[(int) intVal().eval(920)];
        final int n = array.length;
        int _jitmagicLoopLimiter30 = 0;
        for (int i = (int) intVal().eval(925); (boolean) relation(intId(), intId()).eval(924) && _jitmagicLoopLimiter30++ < 1000; i++) {
            final T current = array[(int) intId().eval(921)];
            if (comparator.compare(previous, current) > (int) intVal().eval(922)) {
                return (boolean) boolVal().eval(923);
            }
            previous = current;
        }
        return (boolean) boolVal().eval(926);
    }

    public static int lastIndexOf(final boolean[] array, final boolean valueToFind) {
        return lastIndexOf(refId(boolean[].class).eval(927), (boolean) boolId().eval(928), Integer.MAX_VALUE);
    }

    public static int lastIndexOf(final boolean[] array, final boolean valueToFind, int startIndex) {
        if (isEmpty(refId(boolean[].class).eval(929)) || (boolean) relation(intId(), intVal()).eval(930)) {
            return (int) intId().eval(931);
        }
        if ((int) intId().eval(932) >= array.length) {
            startIndex = array.length - (int) intVal().eval(933);
        }
        int _jitmagicLoopLimiter31 = 0;
        for (int i = (int) intId().eval(938); (boolean) relation(intId(), intVal()).eval(937) && _jitmagicLoopLimiter31++ < 1000; i--) {
            if ((boolean) boolId().eval(934) == (boolean) boolArrAccessExp(refId(boolean[].class), intId()).eval(935)) {
                return (int) intId().eval(936);
            }
        }
        return (int) intId().eval(939);
    }

    public static int lastIndexOf(final byte[] array, final byte valueToFind) {
        return lastIndexOf(refId(byte[].class).eval(940), (byte) byteId().eval(941), Integer.MAX_VALUE);
    }

    public static int lastIndexOf(final byte[] array, final byte valueToFind, int startIndex) {
        if ((refId(byte[].class).eval(942) == null) || (boolean) relation(intId(), intVal()).eval(943)) {
            return (int) intId().eval(944);
        }
        if ((int) intId().eval(945) >= array.length) {
            startIndex = array.length - (int) intVal().eval(946);
        }
        int _jitmagicLoopLimiter32 = 0;
        for (int i = (int) intId().eval(950); (boolean) relation(intId(), intVal()).eval(949) && _jitmagicLoopLimiter32++ < 1000; i--) {
            if ((boolean) relation(cast(Integer.class, byteId()), cast(Integer.class, byteArrAccessExp(refId(byte[].class), intId()))).eval(947)) {
                return (int) intId().eval(948);
            }
        }
        return (int) intId().eval(951);
    }

    public static int lastIndexOf(final char[] array, final char valueToFind) {
        return lastIndexOf(refId(char[].class).eval(952), (char) charId().eval(953), Integer.MAX_VALUE);
    }

    public static int lastIndexOf(final char[] array, final char valueToFind, int startIndex) {
        if ((refId(char[].class).eval(954) == null) || (boolean) relation(intId(), intVal()).eval(955)) {
            return (int) intId().eval(956);
        }
        if ((int) intId().eval(957) >= array.length) {
            startIndex = array.length - (int) intVal().eval(958);
        }
        int _jitmagicLoopLimiter33 = 0;
        for (int i = (int) intId().eval(962); (boolean) relation(intId(), intVal()).eval(961) && _jitmagicLoopLimiter33++ < 1000; i--) {
            if ((boolean) relation(cast(Integer.class, charId()), cast(Integer.class, charArrAccessExp(refId(char[].class), intId()))).eval(959)) {
                return (int) intId().eval(960);
            }
        }
        return (int) intId().eval(963);
    }

    public static int lastIndexOf(final double[] array, final double valueToFind) {
        return lastIndexOf(refId(double[].class).eval(964), (double) doubleId().eval(965), Integer.MAX_VALUE);
    }

    public static int lastIndexOf(final double[] array, final double valueToFind, final double tolerance) {
        return lastIndexOf(refId(double[].class).eval(966), (double) doubleId().eval(967), Integer.MAX_VALUE, (double) doubleId().eval(968));
    }

    public static int lastIndexOf(final double[] array, final double valueToFind, int startIndex) {
        if (isEmpty(refId(double[].class).eval(969)) || (boolean) relation(intId(), intVal()).eval(970)) {
            return (int) intId().eval(971);
        }
        if ((int) intId().eval(972) >= array.length) {
            startIndex = array.length - (int) intVal().eval(973);
        }
        int _jitmagicLoopLimiter34 = 0;
        for (int i = (int) intId().eval(977); (boolean) relation(intId(), intVal()).eval(976) && _jitmagicLoopLimiter34++ < 1000; i--) {
            if ((boolean) relation(doubleId(), doubleArrAccessExp(refId(double[].class), intId())).eval(974)) {
                return (int) intId().eval(975);
            }
        }
        return (int) intId().eval(978);
    }

    public static int lastIndexOf(final double[] array, final double valueToFind, int startIndex, final double tolerance) {
        if (isEmpty(refId(double[].class).eval(979)) || (boolean) relation(intId(), intVal()).eval(980)) {
            return (int) intId().eval(981);
        }
        if ((int) intId().eval(982) >= array.length) {
            startIndex = array.length - (int) intVal().eval(983);
        }
        final double min = (double) arithmetic(doubleId(), doubleId()).eval(984);
        final double max = (double) arithmetic(doubleId(), doubleId()).eval(985);
        int _jitmagicLoopLimiter35 = 0;
        for (int i = (int) intId().eval(989); (boolean) relation(intId(), intVal()).eval(988) && _jitmagicLoopLimiter35++ < 1000; i--) {
            if ((boolean) logic(relation(doubleArrAccessExp(refId(double[].class), intId()), doubleId()), relation(doubleArrAccessExp(refId(double[].class), intId()), doubleId())).eval(986)) {
                return (int) intId().eval(987);
            }
        }
        return (int) intId().eval(990);
    }

    public static int lastIndexOf(final float[] array, final float valueToFind) {
        return lastIndexOf(refId(float[].class).eval(991), (float) floatId().eval(992), Integer.MAX_VALUE);
    }

    public static int lastIndexOf(final float[] array, final float valueToFind, int startIndex) {
        if (isEmpty(refId(float[].class).eval(993)) || (boolean) relation(intId(), intVal()).eval(994)) {
            return (int) intId().eval(995);
        }
        if ((int) intId().eval(996) >= array.length) {
            startIndex = array.length - (int) intVal().eval(997);
        }
        int _jitmagicLoopLimiter36 = 0;
        for (int i = (int) intId().eval(1001); (boolean) relation(intId(), intVal()).eval(1000) && _jitmagicLoopLimiter36++ < 1000; i--) {
            if ((boolean) relation(floatId(), floatArrAccessExp(refId(float[].class), intId())).eval(998)) {
                return (int) intId().eval(999);
            }
        }
        return (int) intId().eval(1002);
    }

    public static int lastIndexOf(final int[] array, final int valueToFind) {
        return lastIndexOf(refId(int[].class).eval(1003), (int) intId().eval(1004), Integer.MAX_VALUE);
    }

    public static int lastIndexOf(final int[] array, final int valueToFind, int startIndex) {
        if ((refId(int[].class).eval(1005) == null) || (boolean) relation(intId(), intVal()).eval(1006)) {
            return (int) intId().eval(1007);
        }
        if ((int) intId().eval(1008) >= array.length) {
            startIndex = array.length - (int) intVal().eval(1009);
        }
        int _jitmagicLoopLimiter37 = 0;
        for (int i = (int) intId().eval(1013); (boolean) relation(intId(), intVal()).eval(1012) && _jitmagicLoopLimiter37++ < 1000; i--) {
            if ((boolean) relation(intId(), intArrAccessExp(refId(int[].class), intId())).eval(1010)) {
                return (int) intId().eval(1011);
            }
        }
        return (int) intId().eval(1014);
    }

    public static int lastIndexOf(final long[] array, final long valueToFind) {
        return lastIndexOf(refId(long[].class).eval(1015), (long) longId().eval(1016), Integer.MAX_VALUE);
    }

    public static int lastIndexOf(final long[] array, final long valueToFind, int startIndex) {
        if ((refId(long[].class).eval(1017) == null) || (boolean) relation(intId(), intVal()).eval(1018)) {
            return (int) intId().eval(1019);
        }
        if ((int) intId().eval(1020) >= array.length) {
            startIndex = array.length - (int) intVal().eval(1021);
        }
        int _jitmagicLoopLimiter38 = 0;
        for (int i = (int) intId().eval(1025); (boolean) relation(intId(), intVal()).eval(1024) && _jitmagicLoopLimiter38++ < 1000; i--) {
            if ((boolean) relation(longId(), longArrAccessExp(refId(long[].class), intId())).eval(1022)) {
                return (int) intId().eval(1023);
            }
        }
        return (int) intId().eval(1026);
    }

    public static int lastIndexOf(final Object[] array, final Object objectToFind) {
        return lastIndexOf(refId(java.lang.Object[].class).eval(1027), refId(java.lang.Object.class).eval(1028), Integer.MAX_VALUE);
    }

    public static int lastIndexOf(final Object[] array, final Object objectToFind, int startIndex) {
        if ((refId(java.lang.Object[].class).eval(1029) == null) || (boolean) relation(intId(), intVal()).eval(1030)) {
            return (int) intId().eval(1031);
        }
        if ((int) intId().eval(1032) >= array.length) {
            startIndex = array.length - (int) intVal().eval(1033);
        }
        if (refId(java.lang.Object.class).eval(1034) == null) {
            int _jitmagicLoopLimiter39 = 0;
            for (int i = (int) intId().eval(1045); (boolean) relation(intId(), intVal()).eval(1044) && _jitmagicLoopLimiter39++ < 1000; i--) {
                if (refArrAccessExp(java.lang.Object.class, java.lang.Object[].class, refId(java.lang.Object[].class), intId()).eval(1042) == null) {
                    return (int) intId().eval(1043);
                }
            }
        } else if (refId(java.lang.Object[].class).eval(1036).getClass().getComponentType().isInstance(refId(java.lang.Object.class).eval(1035))) {
            int _jitmagicLoopLimiter40 = 0;
            for (int i = (int) intId().eval(1041); (boolean) relation(intId(), intVal()).eval(1040) && _jitmagicLoopLimiter40++ < 1000; i--) {
                if (refId(java.lang.Object.class).eval(1038).equals(refArrAccessExp(java.lang.Object.class, java.lang.Object[].class, refId(java.lang.Object[].class), intId()).eval(1037))) {
                    return (int) intId().eval(1039);
                }
            }
        }
        return (int) intId().eval(1046);
    }

    public static int lastIndexOf(final short[] array, final short valueToFind) {
        return lastIndexOf(refId(short[].class).eval(1047), (short) shortId().eval(1048), Integer.MAX_VALUE);
    }

    public static int lastIndexOf(final short[] array, final short valueToFind, int startIndex) {
        if ((refId(short[].class).eval(1049) == null) || (boolean) relation(intId(), intVal()).eval(1050)) {
            return (int) intId().eval(1051);
        }
        if ((int) intId().eval(1052) >= array.length) {
            startIndex = array.length - (int) intVal().eval(1053);
        }
        int _jitmagicLoopLimiter41 = 0;
        for (int i = (int) intId().eval(1057); (boolean) relation(intId(), intVal()).eval(1056) && _jitmagicLoopLimiter41++ < 1000; i--) {
            if ((boolean) relation(cast(Integer.class, shortId()), cast(Integer.class, shortArrAccessExp(refId(short[].class), intId()))).eval(1054)) {
                return (int) intId().eval(1055);
            }
        }
        return (int) intId().eval(1058);
    }

    @SuppressWarnings("unchecked")
    public static <T> T[] newInstance(final Class<T> type, final int length) {
        return (T[]) Array.newInstance(type, (int) intId().eval(1059));
    }

    public static boolean[] nullToEmpty(final boolean[] array) {
        if (isEmpty(refId(boolean[].class).eval(1060))) {
            return refId(boolean[].class).eval(1061);
        }
        return refId(boolean[].class).eval(1062);
    }

    public static Boolean[] nullToEmpty(final Boolean[] array) {
        if (isEmpty(refId(java.lang.Boolean[].class).eval(1063))) {
            return refId(java.lang.Boolean[].class).eval(1064);
        }
        return refId(java.lang.Boolean[].class).eval(1065);
    }

    public static byte[] nullToEmpty(final byte[] array) {
        if (isEmpty(refId(byte[].class).eval(1066))) {
            return refId(byte[].class).eval(1067);
        }
        return refId(byte[].class).eval(1068);
    }

    public static Byte[] nullToEmpty(final Byte[] array) {
        if (isEmpty(refId(java.lang.Byte[].class).eval(1069))) {
            return refId(java.lang.Byte[].class).eval(1070);
        }
        return refId(java.lang.Byte[].class).eval(1071);
    }

    public static char[] nullToEmpty(final char[] array) {
        if (isEmpty(refId(char[].class).eval(1072))) {
            return refId(char[].class).eval(1073);
        }
        return refId(char[].class).eval(1074);
    }

    public static Character[] nullToEmpty(final Character[] array) {
        if (isEmpty(refId(java.lang.Character[].class).eval(1075))) {
            return refId(java.lang.Character[].class).eval(1076);
        }
        return refId(java.lang.Character[].class).eval(1077);
    }

    public static Class<?>[] nullToEmpty(final Class<?>[] array) {
        if (isEmpty(array)) {
            return EMPTY_CLASS_ARRAY;
        }
        return array;
    }

    public static double[] nullToEmpty(final double[] array) {
        if (isEmpty(refId(double[].class).eval(1078))) {
            return refId(double[].class).eval(1079);
        }
        return refId(double[].class).eval(1080);
    }

    public static Double[] nullToEmpty(final Double[] array) {
        if (isEmpty(refId(java.lang.Double[].class).eval(1081))) {
            return refId(java.lang.Double[].class).eval(1082);
        }
        return refId(java.lang.Double[].class).eval(1083);
    }

    public static float[] nullToEmpty(final float[] array) {
        if (isEmpty(refId(float[].class).eval(1084))) {
            return refId(float[].class).eval(1085);
        }
        return refId(float[].class).eval(1086);
    }

    public static Float[] nullToEmpty(final Float[] array) {
        if (isEmpty(refId(java.lang.Float[].class).eval(1087))) {
            return refId(java.lang.Float[].class).eval(1088);
        }
        return refId(java.lang.Float[].class).eval(1089);
    }

    public static int[] nullToEmpty(final int[] array) {
        if (isEmpty(refId(int[].class).eval(1090))) {
            return refId(int[].class).eval(1091);
        }
        return refId(int[].class).eval(1092);
    }

    public static Integer[] nullToEmpty(final Integer[] array) {
        if (isEmpty(refId(java.lang.Integer[].class).eval(1093))) {
            return refId(java.lang.Integer[].class).eval(1094);
        }
        return refId(java.lang.Integer[].class).eval(1095);
    }

    public static long[] nullToEmpty(final long[] array) {
        if (isEmpty(refId(long[].class).eval(1096))) {
            return refId(long[].class).eval(1097);
        }
        return refId(long[].class).eval(1098);
    }

    public static Long[] nullToEmpty(final Long[] array) {
        if (isEmpty(refId(java.lang.Long[].class).eval(1099))) {
            return refId(java.lang.Long[].class).eval(1100);
        }
        return refId(java.lang.Long[].class).eval(1101);
    }

    public static Object[] nullToEmpty(final Object[] array) {
        if (isEmpty(refId(java.lang.Object[].class).eval(1102))) {
            return refId(java.lang.Object[].class).eval(1103);
        }
        return refId(java.lang.Object[].class).eval(1104);
    }

    public static short[] nullToEmpty(final short[] array) {
        if (isEmpty(refId(short[].class).eval(1105))) {
            return refId(short[].class).eval(1106);
        }
        return refId(short[].class).eval(1107);
    }

    public static Short[] nullToEmpty(final Short[] array) {
        if (isEmpty(refId(java.lang.Short[].class).eval(1108))) {
            return refId(java.lang.Short[].class).eval(1109);
        }
        return refId(java.lang.Short[].class).eval(1110);
    }

    public static String[] nullToEmpty(final String[] array) {
        if (isEmpty(refId(java.lang.String[].class).eval(1111))) {
            return refId(java.lang.String[].class).eval(1112);
        }
        return refId(java.lang.String[].class).eval(1113);
    }

    public static <T> T[] nullToEmpty(final T[] array, final Class<T[]> type) {
        if (type == null) {
            throw new IllegalArgumentException("The type must not be null");
        }
        if (array == null) {
            return type.cast(Array.newInstance(type.getComponentType(), (int) intVal().eval(1114)));
        }
        return array;
    }

    private static ThreadLocalRandom random() {
        return ThreadLocalRandom.current();
    }

    public static boolean[] remove(final boolean[] array, final int index) {
        return (boolean[]) remove(cast(Object.class, refId(boolean[].class)).eval(1115), (int) intId().eval(1116));
    }

    public static byte[] remove(final byte[] array, final int index) {
        return (byte[]) remove(cast(Object.class, refId(byte[].class)).eval(1117), (int) intId().eval(1118));
    }

    public static char[] remove(final char[] array, final int index) {
        return (char[]) remove(cast(Object.class, refId(char[].class)).eval(1119), (int) intId().eval(1120));
    }

    public static double[] remove(final double[] array, final int index) {
        return (double[]) remove(cast(Object.class, refId(double[].class)).eval(1121), (int) intId().eval(1122));
    }

    public static float[] remove(final float[] array, final int index) {
        return (float[]) remove(cast(Object.class, refId(float[].class)).eval(1123), (int) intId().eval(1124));
    }

    public static int[] remove(final int[] array, final int index) {
        return (int[]) remove(cast(Object.class, refId(int[].class)).eval(1125), (int) intId().eval(1126));
    }

    public static long[] remove(final long[] array, final int index) {
        return (long[]) remove(cast(Object.class, refId(long[].class)).eval(1127), (int) intId().eval(1128));
    }

    private static Object remove(final Object array, final int index) {
        final int length = getLength(refId(java.lang.Object.class).eval(1129));
        if ((boolean) logic(relation(intId(), intVal()), relation(intId(), intId())).eval(1130)) {
            throw new IndexOutOfBoundsException("Index: " + (int) intId().eval(1131) + ", Length: " + (int) intId().eval(1132));
        }
        final Object result = Array.newInstance(refId(java.lang.Object.class).eval(1133).getClass().getComponentType(), (int) arithmetic(intId(), intVal()).eval(1134));
        System.arraycopy(refId(java.lang.Object.class).eval(1135), (int) intVal().eval(1136), refId(java.lang.Object.class).eval(1137), (int) intVal().eval(1138), (int) intId().eval(1139));
        if ((boolean) relation(intId(), arithmetic(intId(), intVal())).eval(1140)) {
            System.arraycopy(refId(java.lang.Object.class).eval(1141), (int) arithmetic(intId(), intVal()).eval(1142), refId(java.lang.Object.class).eval(1143), (int) intId().eval(1144), (int) arithmetic(arithmetic(intId(), intId()), intVal()).eval(1145));
        }
        return refId(java.lang.Object.class).eval(1146);
    }

    public static short[] remove(final short[] array, final int index) {
        return (short[]) remove(cast(Object.class, refId(short[].class)).eval(1147), (int) intId().eval(1148));
    }

    @SuppressWarnings("unchecked")
    public static <T> T[] remove(final T[] array, final int index) {
        return (T[]) remove((Object) array, (int) intId().eval(1149));
    }

    public static boolean[] removeAll(final boolean[] array, final int... indices) {
        return (boolean[]) removeAll(cast(Object.class, refId(boolean[].class)).eval(1150), refId(int[].class).eval(1151));
    }

    public static byte[] removeAll(final byte[] array, final int... indices) {
        return (byte[]) removeAll(cast(Object.class, refId(byte[].class)).eval(1152), refId(int[].class).eval(1153));
    }

    public static char[] removeAll(final char[] array, final int... indices) {
        return (char[]) removeAll(cast(Object.class, refId(char[].class)).eval(1154), refId(int[].class).eval(1155));
    }

    public static double[] removeAll(final double[] array, final int... indices) {
        return (double[]) removeAll(cast(Object.class, refId(double[].class)).eval(1156), refId(int[].class).eval(1157));
    }

    public static float[] removeAll(final float[] array, final int... indices) {
        return (float[]) removeAll(cast(Object.class, refId(float[].class)).eval(1158), refId(int[].class).eval(1159));
    }

    public static int[] removeAll(final int[] array, final int... indices) {
        return (int[]) removeAll(cast(Object.class, refId(int[].class)).eval(1160), refId(int[].class).eval(1161));
    }

    public static long[] removeAll(final long[] array, final int... indices) {
        return (long[]) removeAll(cast(Object.class, refId(long[].class)).eval(1162), refId(int[].class).eval(1163));
    }

    static Object removeAll(final Object array, final BitSet indices) {
        if (refId(java.lang.Object.class).eval(1164) == null) {
            return null;
        }
        final int srcLength = getLength(refId(java.lang.Object.class).eval(1165));
        final int removals = refId(java.util.BitSet.class).eval(1166).cardinality();
        final Object result = Array.newInstance(refId(java.lang.Object.class).eval(1167).getClass().getComponentType(), (int) arithmetic(intId(), intId()).eval(1168));
        int srcIndex = (int) intVal().eval(1169);
        int destIndex = (int) intVal().eval(1170);
        int count;
        int set;
        int _jitmagicLoopLimiter42 = 0;
        while ((set = refId(java.util.BitSet.class).eval(1182).nextSetBit((int) intId().eval(1181))) != -(int) intVal().eval(1183) && _jitmagicLoopLimiter42++ < 1000) {
            count = (int) arithmetic(intId(), intId()).eval(1171);
            if ((boolean) relation(intId(), intVal()).eval(1172)) {
                System.arraycopy(refId(java.lang.Object.class).eval(1173), (int) intId().eval(1174), refId(java.lang.Object.class).eval(1175), (int) intId().eval(1176), (int) intId().eval(1177));
                destIndex += (int) intId().eval(1178);
            }
            srcIndex = refId(java.util.BitSet.class).eval(1180).nextClearBit((int) intId().eval(1179));
        }
        count = (int) arithmetic(intId(), intId()).eval(1184);
        if ((boolean) relation(intId(), intVal()).eval(1185)) {
            System.arraycopy(refId(java.lang.Object.class).eval(1186), (int) intId().eval(1187), refId(java.lang.Object.class).eval(1188), (int) intId().eval(1189), (int) intId().eval(1190));
        }
        return refId(java.lang.Object.class).eval(1191);
    }

    static Object removeAll(final Object array, final int... indices) {
        final int length = getLength(refId(java.lang.Object.class).eval(1192));
        int diff = (int) intVal().eval(1193);
        final int[] clonedIndices = ArraySorter.sort(clone(refId(int[].class).eval(1194)));
        if (isNotEmpty(refId(int[].class).eval(1195))) {
            int i = clonedIndices.length;
            int prevIndex = (int) intId().eval(1196);
            int _jitmagicLoopLimiter43 = 0;
            while (--i >= (int) intVal().eval(1203) && _jitmagicLoopLimiter43++ < 1000) {
                final int index = (int) intArrAccessExp(refId(int[].class), intId()).eval(1197);
                if ((boolean) logic(relation(intId(), intVal()), relation(intId(), intId())).eval(1198)) {
                    throw new IndexOutOfBoundsException("Index: " + (int) intId().eval(1199) + ", Length: " + (int) intId().eval(1200));
                }
                if ((boolean) relation(intId(), intId()).eval(1201)) {
                    continue;
                }
                diff++;
                prevIndex = (int) intId().eval(1202);
            }
        }
        final Object result = Array.newInstance(refId(java.lang.Object.class).eval(1204).getClass().getComponentType(), (int) arithmetic(intId(), intId()).eval(1205));
        if ((boolean) relation(intId(), intId()).eval(1206)) {
            int end = (int) intId().eval(1207);
            int dest = (int) arithmetic(intId(), intId()).eval(1208);
            int _jitmagicLoopLimiter44 = 0;
            for (int i = clonedIndices.length - (int) intVal().eval(1220); (boolean) relation(intId(), intVal()).eval(1219) && _jitmagicLoopLimiter44++ < 1000; i--) {
                final int index = (int) intArrAccessExp(refId(int[].class), intId()).eval(1209);
                if ((boolean) relation(arithmetic(intId(), intId()), intVal()).eval(1210)) {
                    final int cp = (int) arithmetic(arithmetic(intId(), intId()), intVal()).eval(1211);
                    dest -= (int) intId().eval(1212);
                    System.arraycopy(refId(java.lang.Object.class).eval(1213), (int) arithmetic(intId(), intVal()).eval(1214), refId(java.lang.Object.class).eval(1215), (int) intId().eval(1216), (int) intId().eval(1217));
                }
                end = (int) intId().eval(1218);
            }
            if ((boolean) relation(intId(), intVal()).eval(1221)) {
                System.arraycopy(refId(java.lang.Object.class).eval(1222), (int) intVal().eval(1223), refId(java.lang.Object.class).eval(1224), (int) intVal().eval(1225), (int) intId().eval(1226));
            }
        }
        return refId(java.lang.Object.class).eval(1227);
    }

    public static short[] removeAll(final short[] array, final int... indices) {
        return (short[]) removeAll(cast(Object.class, refId(short[].class)).eval(1228), refId(int[].class).eval(1229));
    }

    @SuppressWarnings("unchecked")
    public static <T> T[] removeAll(final T[] array, final int... indices) {
        return (T[]) removeAll((Object) array, refId(int[].class).eval(1230));
    }

    @Deprecated
    public static boolean[] removeAllOccurences(final boolean[] array, final boolean element) {
        return (boolean[]) removeAll(cast(Object.class, refId(boolean[].class)).eval(1231), indexesOf(refId(boolean[].class).eval(1232), (boolean) boolId().eval(1233)));
    }

    @Deprecated
    public static byte[] removeAllOccurences(final byte[] array, final byte element) {
        return (byte[]) removeAll(cast(Object.class, refId(byte[].class)).eval(1234), indexesOf(refId(byte[].class).eval(1235), (byte) byteId().eval(1236)));
    }

    @Deprecated
    public static char[] removeAllOccurences(final char[] array, final char element) {
        return (char[]) removeAll(cast(Object.class, refId(char[].class)).eval(1237), indexesOf(refId(char[].class).eval(1238), (char) charId().eval(1239)));
    }

    @Deprecated
    public static double[] removeAllOccurences(final double[] array, final double element) {
        return (double[]) removeAll(cast(Object.class, refId(double[].class)).eval(1240), indexesOf(refId(double[].class).eval(1241), (double) doubleId().eval(1242)));
    }

    @Deprecated
    public static float[] removeAllOccurences(final float[] array, final float element) {
        return (float[]) removeAll(cast(Object.class, refId(float[].class)).eval(1243), indexesOf(refId(float[].class).eval(1244), (float) floatId().eval(1245)));
    }

    @Deprecated
    public static int[] removeAllOccurences(final int[] array, final int element) {
        return (int[]) removeAll(cast(Object.class, refId(int[].class)).eval(1246), indexesOf(refId(int[].class).eval(1247), (int) intId().eval(1248)));
    }

    @Deprecated
    public static long[] removeAllOccurences(final long[] array, final long element) {
        return (long[]) removeAll(cast(Object.class, refId(long[].class)).eval(1249), indexesOf(refId(long[].class).eval(1250), (long) longId().eval(1251)));
    }

    @Deprecated
    public static short[] removeAllOccurences(final short[] array, final short element) {
        return (short[]) removeAll(cast(Object.class, refId(short[].class)).eval(1252), indexesOf(refId(short[].class).eval(1253), (short) shortId().eval(1254)));
    }

    @Deprecated
    public static <T> T[] removeAllOccurences(final T[] array, final T element) {
        return (T[]) removeAll((Object) array, indexesOf(array, element));
    }

    public static boolean[] removeAllOccurrences(final boolean[] array, final boolean element) {
        return (boolean[]) removeAll(cast(Object.class, refId(boolean[].class)).eval(1255), indexesOf(refId(boolean[].class).eval(1256), (boolean) boolId().eval(1257)));
    }

    public static byte[] removeAllOccurrences(final byte[] array, final byte element) {
        return (byte[]) removeAll(cast(Object.class, refId(byte[].class)).eval(1258), indexesOf(refId(byte[].class).eval(1259), (byte) byteId().eval(1260)));
    }

    public static char[] removeAllOccurrences(final char[] array, final char element) {
        return (char[]) removeAll(cast(Object.class, refId(char[].class)).eval(1261), indexesOf(refId(char[].class).eval(1262), (char) charId().eval(1263)));
    }

    public static double[] removeAllOccurrences(final double[] array, final double element) {
        return (double[]) removeAll(cast(Object.class, refId(double[].class)).eval(1264), indexesOf(refId(double[].class).eval(1265), (double) doubleId().eval(1266)));
    }

    public static float[] removeAllOccurrences(final float[] array, final float element) {
        return (float[]) removeAll(cast(Object.class, refId(float[].class)).eval(1267), indexesOf(refId(float[].class).eval(1268), (float) floatId().eval(1269)));
    }

    public static int[] removeAllOccurrences(final int[] array, final int element) {
        return (int[]) removeAll(cast(Object.class, refId(int[].class)).eval(1270), indexesOf(refId(int[].class).eval(1271), (int) intId().eval(1272)));
    }

    public static long[] removeAllOccurrences(final long[] array, final long element) {
        return (long[]) removeAll(cast(Object.class, refId(long[].class)).eval(1273), indexesOf(refId(long[].class).eval(1274), (long) longId().eval(1275)));
    }

    public static short[] removeAllOccurrences(final short[] array, final short element) {
        return (short[]) removeAll(cast(Object.class, refId(short[].class)).eval(1276), indexesOf(refId(short[].class).eval(1277), (short) shortId().eval(1278)));
    }

    public static <T> T[] removeAllOccurrences(final T[] array, final T element) {
        return (T[]) removeAll((Object) array, indexesOf(array, element));
    }

    public static boolean[] removeElement(final boolean[] array, final boolean element) {
        final int index = indexOf(refId(boolean[].class).eval(1279), (boolean) boolId().eval(1280));
        if ((boolean) relation(intId(), intId()).eval(1281)) {
            return clone(refId(boolean[].class).eval(1282));
        }
        return remove(refId(boolean[].class).eval(1283), (int) intId().eval(1284));
    }

    public static byte[] removeElement(final byte[] array, final byte element) {
        final int index = indexOf(refId(byte[].class).eval(1285), (byte) byteId().eval(1286));
        if ((boolean) relation(intId(), intId()).eval(1287)) {
            return clone(refId(byte[].class).eval(1288));
        }
        return remove(refId(byte[].class).eval(1289), (int) intId().eval(1290));
    }

    public static char[] removeElement(final char[] array, final char element) {
        final int index = indexOf(refId(char[].class).eval(1291), (char) charId().eval(1292));
        if ((boolean) relation(intId(), intId()).eval(1293)) {
            return clone(refId(char[].class).eval(1294));
        }
        return remove(refId(char[].class).eval(1295), (int) intId().eval(1296));
    }

    public static double[] removeElement(final double[] array, final double element) {
        final int index = indexOf(refId(double[].class).eval(1297), (double) doubleId().eval(1298));
        if ((boolean) relation(intId(), intId()).eval(1299)) {
            return clone(refId(double[].class).eval(1300));
        }
        return remove(refId(double[].class).eval(1301), (int) intId().eval(1302));
    }

    public static float[] removeElement(final float[] array, final float element) {
        final int index = indexOf(refId(float[].class).eval(1303), (float) floatId().eval(1304));
        if ((boolean) relation(intId(), intId()).eval(1305)) {
            return clone(refId(float[].class).eval(1306));
        }
        return remove(refId(float[].class).eval(1307), (int) intId().eval(1308));
    }

    public static int[] removeElement(final int[] array, final int element) {
        final int index = indexOf(refId(int[].class).eval(1309), (int) intId().eval(1310));
        if ((boolean) relation(intId(), intId()).eval(1311)) {
            return clone(refId(int[].class).eval(1312));
        }
        return remove(refId(int[].class).eval(1313), (int) intId().eval(1314));
    }

    public static long[] removeElement(final long[] array, final long element) {
        final int index = indexOf(refId(long[].class).eval(1315), (long) longId().eval(1316));
        if ((boolean) relation(intId(), intId()).eval(1317)) {
            return clone(refId(long[].class).eval(1318));
        }
        return remove(refId(long[].class).eval(1319), (int) intId().eval(1320));
    }

    public static short[] removeElement(final short[] array, final short element) {
        final int index = indexOf(refId(short[].class).eval(1321), (short) shortId().eval(1322));
        if ((boolean) relation(intId(), intId()).eval(1323)) {
            return clone(refId(short[].class).eval(1324));
        }
        return remove(refId(short[].class).eval(1325), (int) intId().eval(1326));
    }

    public static <T> T[] removeElement(final T[] array, final Object element) {
        final int index = indexOf(array, refId(java.lang.Object.class).eval(1327));
        if ((boolean) relation(intId(), intId()).eval(1328)) {
            return clone(array);
        }
        return remove(array, (int) intId().eval(1329));
    }

    public static boolean[] removeElements(final boolean[] array, final boolean... values) {
        if (isEmpty(refId(boolean[].class).eval(1330)) || isEmpty(refId(boolean[].class).eval(1331))) {
            return clone(refId(boolean[].class).eval(1332));
        }
        final HashMap<Boolean, MutableInt> occurrences = new HashMap<>((int) intVal().eval(1333));
        for (final boolean v : refId(boolean[].class).eval(1340)) {
            final Boolean boxed = Boolean.valueOf((boolean) boolId().eval(1334));
            final MutableInt count = occurrences.get(refId(java.lang.Boolean.class).eval(1335));
            if (refId(org.apache.commons.lang3.mutable.MutableInt.class).eval(1336) == null) {
                occurrences.put(refId(java.lang.Boolean.class).eval(1338), new MutableInt((int) intVal().eval(1339)));
            } else {
                refId(org.apache.commons.lang3.mutable.MutableInt.class).eval(1337).increment();
            }
        }
        final BitSet toRemove = new BitSet();
        int _jitmagicLoopLimiter45 = 0;
        for (int i = (int) intVal().eval(1350); (int) intId().eval(1349) < array.length && _jitmagicLoopLimiter45++ < 1000; i++) {
            final boolean key = (boolean) boolArrAccessExp(refId(boolean[].class), intId()).eval(1341);
            final MutableInt count = occurrences.get((boolean) boolId().eval(1342));
            if (refId(org.apache.commons.lang3.mutable.MutableInt.class).eval(1343) != null) {
                if (refId(org.apache.commons.lang3.mutable.MutableInt.class).eval(1344).decrementAndGet() == (int) intVal().eval(1345)) {
                    occurrences.remove((boolean) boolId().eval(1346));
                }
                refId(java.util.BitSet.class).eval(1348).set((int) intId().eval(1347));
            }
        }
        return (boolean[]) removeAll(refId(boolean[].class).eval(1351), refId(java.util.BitSet.class).eval(1352));
    }

    public static byte[] removeElements(final byte[] array, final byte... values) {
        if (isEmpty(refId(byte[].class).eval(1353)) || isEmpty(refId(byte[].class).eval(1354))) {
            return clone(refId(byte[].class).eval(1355));
        }
        final Map<Byte, MutableInt> occurrences = new HashMap<>(values.length);
        for (final byte v : refId(byte[].class).eval(1362)) {
            final Byte boxed = Byte.valueOf((byte) byteId().eval(1356));
            final MutableInt count = occurrences.get(refId(java.lang.Byte.class).eval(1357));
            if (refId(org.apache.commons.lang3.mutable.MutableInt.class).eval(1358) == null) {
                occurrences.put(refId(java.lang.Byte.class).eval(1360), new MutableInt((int) intVal().eval(1361)));
            } else {
                refId(org.apache.commons.lang3.mutable.MutableInt.class).eval(1359).increment();
            }
        }
        final BitSet toRemove = new BitSet();
        int _jitmagicLoopLimiter46 = 0;
        for (int i = (int) intVal().eval(1372); (int) intId().eval(1371) < array.length && _jitmagicLoopLimiter46++ < 1000; i++) {
            final byte key = (byte) byteArrAccessExp(refId(byte[].class), intId()).eval(1363);
            final MutableInt count = occurrences.get((byte) byteId().eval(1364));
            if (refId(org.apache.commons.lang3.mutable.MutableInt.class).eval(1365) != null) {
                if (refId(org.apache.commons.lang3.mutable.MutableInt.class).eval(1366).decrementAndGet() == (int) intVal().eval(1367)) {
                    occurrences.remove((byte) byteId().eval(1368));
                }
                refId(java.util.BitSet.class).eval(1370).set((int) intId().eval(1369));
            }
        }
        return (byte[]) removeAll(refId(byte[].class).eval(1373), refId(java.util.BitSet.class).eval(1374));
    }

    public static char[] removeElements(final char[] array, final char... values) {
        if (isEmpty(refId(char[].class).eval(1375)) || isEmpty(refId(char[].class).eval(1376))) {
            return clone(refId(char[].class).eval(1377));
        }
        final HashMap<Character, MutableInt> occurrences = new HashMap<>(values.length);
        for (final char v : refId(char[].class).eval(1384)) {
            final Character boxed = Character.valueOf((char) charId().eval(1378));
            final MutableInt count = occurrences.get(refId(java.lang.Character.class).eval(1379));
            if (refId(org.apache.commons.lang3.mutable.MutableInt.class).eval(1380) == null) {
                occurrences.put(refId(java.lang.Character.class).eval(1382), new MutableInt((int) intVal().eval(1383)));
            } else {
                refId(org.apache.commons.lang3.mutable.MutableInt.class).eval(1381).increment();
            }
        }
        final BitSet toRemove = new BitSet();
        int _jitmagicLoopLimiter47 = 0;
        for (int i = (int) intVal().eval(1394); (int) intId().eval(1393) < array.length && _jitmagicLoopLimiter47++ < 1000; i++) {
            final char key = (char) charArrAccessExp(refId(char[].class), intId()).eval(1385);
            final MutableInt count = occurrences.get((char) charId().eval(1386));
            if (refId(org.apache.commons.lang3.mutable.MutableInt.class).eval(1387) != null) {
                if (refId(org.apache.commons.lang3.mutable.MutableInt.class).eval(1388).decrementAndGet() == (int) intVal().eval(1389)) {
                    occurrences.remove((char) charId().eval(1390));
                }
                refId(java.util.BitSet.class).eval(1392).set((int) intId().eval(1391));
            }
        }
        return (char[]) removeAll(refId(char[].class).eval(1395), refId(java.util.BitSet.class).eval(1396));
    }

    public static double[] removeElements(final double[] array, final double... values) {
        if (isEmpty(refId(double[].class).eval(1397)) || isEmpty(refId(double[].class).eval(1398))) {
            return clone(refId(double[].class).eval(1399));
        }
        final HashMap<Double, MutableInt> occurrences = new HashMap<>(values.length);
        for (final double v : refId(double[].class).eval(1406)) {
            final Double boxed = Double.valueOf((double) doubleId().eval(1400));
            final MutableInt count = occurrences.get(refId(java.lang.Double.class).eval(1401));
            if (refId(org.apache.commons.lang3.mutable.MutableInt.class).eval(1402) == null) {
                occurrences.put(refId(java.lang.Double.class).eval(1404), new MutableInt((int) intVal().eval(1405)));
            } else {
                refId(org.apache.commons.lang3.mutable.MutableInt.class).eval(1403).increment();
            }
        }
        final BitSet toRemove = new BitSet();
        int _jitmagicLoopLimiter48 = 0;
        for (int i = (int) intVal().eval(1416); (int) intId().eval(1415) < array.length && _jitmagicLoopLimiter48++ < 1000; i++) {
            final double key = (double) doubleArrAccessExp(refId(double[].class), intId()).eval(1407);
            final MutableInt count = occurrences.get((double) doubleId().eval(1408));
            if (refId(org.apache.commons.lang3.mutable.MutableInt.class).eval(1409) != null) {
                if (refId(org.apache.commons.lang3.mutable.MutableInt.class).eval(1410).decrementAndGet() == (int) intVal().eval(1411)) {
                    occurrences.remove((double) doubleId().eval(1412));
                }
                refId(java.util.BitSet.class).eval(1414).set((int) intId().eval(1413));
            }
        }
        return (double[]) removeAll(refId(double[].class).eval(1417), refId(java.util.BitSet.class).eval(1418));
    }

    public static float[] removeElements(final float[] array, final float... values) {
        if (isEmpty(refId(float[].class).eval(1419)) || isEmpty(refId(float[].class).eval(1420))) {
            return clone(refId(float[].class).eval(1421));
        }
        final HashMap<Float, MutableInt> occurrences = new HashMap<>(values.length);
        for (final float v : refId(float[].class).eval(1428)) {
            final Float boxed = Float.valueOf((float) floatId().eval(1422));
            final MutableInt count = occurrences.get(refId(java.lang.Float.class).eval(1423));
            if (refId(org.apache.commons.lang3.mutable.MutableInt.class).eval(1424) == null) {
                occurrences.put(refId(java.lang.Float.class).eval(1426), new MutableInt((int) intVal().eval(1427)));
            } else {
                refId(org.apache.commons.lang3.mutable.MutableInt.class).eval(1425).increment();
            }
        }
        final BitSet toRemove = new BitSet();
        int _jitmagicLoopLimiter49 = 0;
        for (int i = (int) intVal().eval(1438); (int) intId().eval(1437) < array.length && _jitmagicLoopLimiter49++ < 1000; i++) {
            final float key = (float) floatArrAccessExp(refId(float[].class), intId()).eval(1429);
            final MutableInt count = occurrences.get((float) floatId().eval(1430));
            if (refId(org.apache.commons.lang3.mutable.MutableInt.class).eval(1431) != null) {
                if (refId(org.apache.commons.lang3.mutable.MutableInt.class).eval(1432).decrementAndGet() == (int) intVal().eval(1433)) {
                    occurrences.remove((float) floatId().eval(1434));
                }
                refId(java.util.BitSet.class).eval(1436).set((int) intId().eval(1435));
            }
        }
        return (float[]) removeAll(refId(float[].class).eval(1439), refId(java.util.BitSet.class).eval(1440));
    }

    public static int[] removeElements(final int[] array, final int... values) {
        if (isEmpty(refId(int[].class).eval(1441)) || isEmpty(refId(int[].class).eval(1442))) {
            return clone(refId(int[].class).eval(1443));
        }
        final HashMap<Integer, MutableInt> occurrences = new HashMap<>(values.length);
        for (final int v : refId(int[].class).eval(1450)) {
            final Integer boxed = Integer.valueOf((int) intId().eval(1444));
            final MutableInt count = occurrences.get(refId(java.lang.Integer.class).eval(1445));
            if (refId(org.apache.commons.lang3.mutable.MutableInt.class).eval(1446) == null) {
                occurrences.put(refId(java.lang.Integer.class).eval(1448), new MutableInt((int) intVal().eval(1449)));
            } else {
                refId(org.apache.commons.lang3.mutable.MutableInt.class).eval(1447).increment();
            }
        }
        final BitSet toRemove = new BitSet();
        int _jitmagicLoopLimiter50 = 0;
        for (int i = (int) intVal().eval(1460); (int) intId().eval(1459) < array.length && _jitmagicLoopLimiter50++ < 1000; i++) {
            final int key = (int) intArrAccessExp(refId(int[].class), intId()).eval(1451);
            final MutableInt count = occurrences.get((int) intId().eval(1452));
            if (refId(org.apache.commons.lang3.mutable.MutableInt.class).eval(1453) != null) {
                if (refId(org.apache.commons.lang3.mutable.MutableInt.class).eval(1454).decrementAndGet() == (int) intVal().eval(1455)) {
                    occurrences.remove((int) intId().eval(1456));
                }
                refId(java.util.BitSet.class).eval(1458).set((int) intId().eval(1457));
            }
        }
        return (int[]) removeAll(refId(int[].class).eval(1461), refId(java.util.BitSet.class).eval(1462));
    }

    public static long[] removeElements(final long[] array, final long... values) {
        if (isEmpty(refId(long[].class).eval(1463)) || isEmpty(refId(long[].class).eval(1464))) {
            return clone(refId(long[].class).eval(1465));
        }
        final HashMap<Long, MutableInt> occurrences = new HashMap<>(values.length);
        for (final long v : refId(long[].class).eval(1472)) {
            final Long boxed = Long.valueOf((long) longId().eval(1466));
            final MutableInt count = occurrences.get(refId(java.lang.Long.class).eval(1467));
            if (refId(org.apache.commons.lang3.mutable.MutableInt.class).eval(1468) == null) {
                occurrences.put(refId(java.lang.Long.class).eval(1470), new MutableInt((int) intVal().eval(1471)));
            } else {
                refId(org.apache.commons.lang3.mutable.MutableInt.class).eval(1469).increment();
            }
        }
        final BitSet toRemove = new BitSet();
        int _jitmagicLoopLimiter51 = 0;
        for (int i = (int) intVal().eval(1482); (int) intId().eval(1481) < array.length && _jitmagicLoopLimiter51++ < 1000; i++) {
            final long key = (long) longArrAccessExp(refId(long[].class), intId()).eval(1473);
            final MutableInt count = occurrences.get((long) longId().eval(1474));
            if (refId(org.apache.commons.lang3.mutable.MutableInt.class).eval(1475) != null) {
                if (refId(org.apache.commons.lang3.mutable.MutableInt.class).eval(1476).decrementAndGet() == (int) intVal().eval(1477)) {
                    occurrences.remove((long) longId().eval(1478));
                }
                refId(java.util.BitSet.class).eval(1480).set((int) intId().eval(1479));
            }
        }
        return (long[]) removeAll(refId(long[].class).eval(1483), refId(java.util.BitSet.class).eval(1484));
    }

    public static short[] removeElements(final short[] array, final short... values) {
        if (isEmpty(refId(short[].class).eval(1485)) || isEmpty(refId(short[].class).eval(1486))) {
            return clone(refId(short[].class).eval(1487));
        }
        final HashMap<Short, MutableInt> occurrences = new HashMap<>(values.length);
        for (final short v : refId(short[].class).eval(1494)) {
            final Short boxed = Short.valueOf((short) shortId().eval(1488));
            final MutableInt count = occurrences.get(refId(java.lang.Short.class).eval(1489));
            if (refId(org.apache.commons.lang3.mutable.MutableInt.class).eval(1490) == null) {
                occurrences.put(refId(java.lang.Short.class).eval(1492), new MutableInt((int) intVal().eval(1493)));
            } else {
                refId(org.apache.commons.lang3.mutable.MutableInt.class).eval(1491).increment();
            }
        }
        final BitSet toRemove = new BitSet();
        int _jitmagicLoopLimiter52 = 0;
        for (int i = (int) intVal().eval(1504); (int) intId().eval(1503) < array.length && _jitmagicLoopLimiter52++ < 1000; i++) {
            final short key = (short) shortArrAccessExp(refId(short[].class), intId()).eval(1495);
            final MutableInt count = occurrences.get((short) shortId().eval(1496));
            if (refId(org.apache.commons.lang3.mutable.MutableInt.class).eval(1497) != null) {
                if (refId(org.apache.commons.lang3.mutable.MutableInt.class).eval(1498).decrementAndGet() == (int) intVal().eval(1499)) {
                    occurrences.remove((short) shortId().eval(1500));
                }
                refId(java.util.BitSet.class).eval(1502).set((int) intId().eval(1501));
            }
        }
        return (short[]) removeAll(refId(short[].class).eval(1505), refId(java.util.BitSet.class).eval(1506));
    }

    @SafeVarargs
    public static <T> T[] removeElements(final T[] array, final T... values) {
        if (isEmpty(array) || isEmpty(values)) {
            return clone(array);
        }
        final HashMap<T, MutableInt> occurrences = new HashMap<>(values.length);
        for (final T v : values) {
            final MutableInt count = occurrences.get(v);
            if (refId(org.apache.commons.lang3.mutable.MutableInt.class).eval(1507) == null) {
                occurrences.put(v, new MutableInt((int) intVal().eval(1509)));
            } else {
                refId(org.apache.commons.lang3.mutable.MutableInt.class).eval(1508).increment();
            }
        }
        final BitSet toRemove = new BitSet();
        int _jitmagicLoopLimiter53 = 0;
        for (int i = (int) intVal().eval(1517); (int) intId().eval(1516) < array.length && _jitmagicLoopLimiter53++ < 1000; i++) {
            final T key = array[(int) intId().eval(1510)];
            final MutableInt count = occurrences.get(key);
            if (refId(org.apache.commons.lang3.mutable.MutableInt.class).eval(1511) != null) {
                if (refId(org.apache.commons.lang3.mutable.MutableInt.class).eval(1512).decrementAndGet() == (int) intVal().eval(1513)) {
                    occurrences.remove(key);
                }
                refId(java.util.BitSet.class).eval(1515).set((int) intId().eval(1514));
            }
        }
        @SuppressWarnings("unchecked")
        final T[] result = (T[]) removeAll(array, refId(java.util.BitSet.class).eval(1518));
        return result;
    }

    public static void reverse(final boolean[] array) {
        if (refId(boolean[].class).eval(1519) == null) {
            return;
        }
        reverse(refId(boolean[].class).eval(1520), (int) intVal().eval(1521), array.length);
    }

    public static void reverse(final boolean[] array, final int startIndexInclusive, final int endIndexExclusive) {
        if (refId(boolean[].class).eval(1522) == null) {
            return;
        }
        int i = Math.max((int) intId().eval(1523), (int) intVal().eval(1524));
        int j = Math.min(array.length, (int) intId().eval(1525)) - (int) intVal().eval(1526);
        boolean tmp;
        int _jitmagicLoopLimiter54 = 0;
        while ((boolean) relation(intId(), intId()).eval(1530) && _jitmagicLoopLimiter54++ < 1000) {
            tmp = (boolean) boolArrAccessExp(refId(boolean[].class), intId()).eval(1527);
            array[j] = (boolean) boolArrAccessExp(refId(boolean[].class), intId()).eval(1528);
            array[i] = (boolean) boolId().eval(1529);
            j--;
            i++;
        }
    }

    public static void reverse(final byte[] array) {
        if (refId(byte[].class).eval(1531) == null) {
            return;
        }
        reverse(refId(byte[].class).eval(1532), (int) intVal().eval(1533), array.length);
    }

    public static void reverse(final byte[] array, final int startIndexInclusive, final int endIndexExclusive) {
        if (refId(byte[].class).eval(1534) == null) {
            return;
        }
        int i = Math.max((int) intId().eval(1535), (int) intVal().eval(1536));
        int j = Math.min(array.length, (int) intId().eval(1537)) - (int) intVal().eval(1538);
        byte tmp;
        int _jitmagicLoopLimiter55 = 0;
        while ((boolean) relation(intId(), intId()).eval(1542) && _jitmagicLoopLimiter55++ < 1000) {
            tmp = (byte) byteArrAccessExp(refId(byte[].class), intId()).eval(1539);
            array[j] = (byte) byteArrAccessExp(refId(byte[].class), intId()).eval(1540);
            array[i] = (byte) byteId().eval(1541);
            j--;
            i++;
        }
    }

    public static void reverse(final char[] array) {
        if (refId(char[].class).eval(1543) == null) {
            return;
        }
        reverse(refId(char[].class).eval(1544), (int) intVal().eval(1545), array.length);
    }

    public static void reverse(final char[] array, final int startIndexInclusive, final int endIndexExclusive) {
        if (refId(char[].class).eval(1546) == null) {
            return;
        }
        int i = Math.max((int) intId().eval(1547), (int) intVal().eval(1548));
        int j = Math.min(array.length, (int) intId().eval(1549)) - (int) intVal().eval(1550);
        char tmp;
        int _jitmagicLoopLimiter56 = 0;
        while ((boolean) relation(intId(), intId()).eval(1554) && _jitmagicLoopLimiter56++ < 1000) {
            tmp = (char) charArrAccessExp(refId(char[].class), intId()).eval(1551);
            array[j] = (char) charArrAccessExp(refId(char[].class), intId()).eval(1552);
            array[i] = (char) charId().eval(1553);
            j--;
            i++;
        }
    }

    public static void reverse(final double[] array) {
        if (refId(double[].class).eval(1555) == null) {
            return;
        }
        reverse(refId(double[].class).eval(1556), (int) intVal().eval(1557), array.length);
    }

    public static void reverse(final double[] array, final int startIndexInclusive, final int endIndexExclusive) {
        if (refId(double[].class).eval(1558) == null) {
            return;
        }
        int i = Math.max((int) intId().eval(1559), (int) intVal().eval(1560));
        int j = Math.min(array.length, (int) intId().eval(1561)) - (int) intVal().eval(1562);
        double tmp;
        int _jitmagicLoopLimiter57 = 0;
        while ((boolean) relation(intId(), intId()).eval(1566) && _jitmagicLoopLimiter57++ < 1000) {
            tmp = (double) doubleArrAccessExp(refId(double[].class), intId()).eval(1563);
            array[j] = (double) doubleArrAccessExp(refId(double[].class), intId()).eval(1564);
            array[i] = (double) doubleId().eval(1565);
            j--;
            i++;
        }
    }

    public static void reverse(final float[] array) {
        if (refId(float[].class).eval(1567) == null) {
            return;
        }
        reverse(refId(float[].class).eval(1568), (int) intVal().eval(1569), array.length);
    }

    public static void reverse(final float[] array, final int startIndexInclusive, final int endIndexExclusive) {
        if (refId(float[].class).eval(1570) == null) {
            return;
        }
        int i = Math.max((int) intId().eval(1571), (int) intVal().eval(1572));
        int j = Math.min(array.length, (int) intId().eval(1573)) - (int) intVal().eval(1574);
        float tmp;
        int _jitmagicLoopLimiter58 = 0;
        while ((boolean) relation(intId(), intId()).eval(1578) && _jitmagicLoopLimiter58++ < 1000) {
            tmp = (float) floatArrAccessExp(refId(float[].class), intId()).eval(1575);
            array[j] = (float) floatArrAccessExp(refId(float[].class), intId()).eval(1576);
            array[i] = (float) floatId().eval(1577);
            j--;
            i++;
        }
    }

    public static void reverse(final int[] array) {
        if (refId(int[].class).eval(1579) == null) {
            return;
        }
        reverse(refId(int[].class).eval(1580), (int) intVal().eval(1581), array.length);
    }

    public static void reverse(final int[] array, final int startIndexInclusive, final int endIndexExclusive) {
        if (refId(int[].class).eval(1582) == null) {
            return;
        }
        int i = Math.max((int) intId().eval(1583), (int) intVal().eval(1584));
        int j = Math.min(array.length, (int) intId().eval(1585)) - (int) intVal().eval(1586);
        int tmp;
        int _jitmagicLoopLimiter59 = 0;
        while ((boolean) relation(intId(), intId()).eval(1590) && _jitmagicLoopLimiter59++ < 1000) {
            tmp = (int) intArrAccessExp(refId(int[].class), intId()).eval(1587);
            array[j] = (int) intArrAccessExp(refId(int[].class), intId()).eval(1588);
            array[i] = (int) intId().eval(1589);
            j--;
            i++;
        }
    }

    public static void reverse(final long[] array) {
        if (refId(long[].class).eval(1591) == null) {
            return;
        }
        reverse(refId(long[].class).eval(1592), (int) intVal().eval(1593), array.length);
    }

    public static void reverse(final long[] array, final int startIndexInclusive, final int endIndexExclusive) {
        if (refId(long[].class).eval(1594) == null) {
            return;
        }
        int i = Math.max((int) intId().eval(1595), (int) intVal().eval(1596));
        int j = Math.min(array.length, (int) intId().eval(1597)) - (int) intVal().eval(1598);
        long tmp;
        int _jitmagicLoopLimiter60 = 0;
        while ((boolean) relation(intId(), intId()).eval(1602) && _jitmagicLoopLimiter60++ < 1000) {
            tmp = (long) longArrAccessExp(refId(long[].class), intId()).eval(1599);
            array[j] = (long) longArrAccessExp(refId(long[].class), intId()).eval(1600);
            array[i] = (long) longId().eval(1601);
            j--;
            i++;
        }
    }

    public static void reverse(final Object[] array) {
        if (refId(java.lang.Object[].class).eval(1603) == null) {
            return;
        }
        reverse(refId(java.lang.Object[].class).eval(1604), (int) intVal().eval(1605), array.length);
    }

    public static void reverse(final Object[] array, final int startIndexInclusive, final int endIndexExclusive) {
        if (refId(java.lang.Object[].class).eval(1606) == null) {
            return;
        }
        int i = Math.max((int) intId().eval(1607), (int) intVal().eval(1608));
        int j = Math.min(array.length, (int) intId().eval(1609)) - (int) intVal().eval(1610);
        Object tmp;
        int _jitmagicLoopLimiter61 = 0;
        while ((boolean) relation(intId(), intId()).eval(1614) && _jitmagicLoopLimiter61++ < 1000) {
            tmp = refArrAccessExp(java.lang.Object.class, java.lang.Object[].class, refId(java.lang.Object[].class), intId()).eval(1611);
            array[j] = refArrAccessExp(java.lang.Object.class, java.lang.Object[].class, refId(java.lang.Object[].class), intId()).eval(1612);
            array[i] = refId(java.lang.Object.class).eval(1613);
            j--;
            i++;
        }
    }

    public static void reverse(final short[] array) {
        if (refId(short[].class).eval(1615) == null) {
            return;
        }
        reverse(refId(short[].class).eval(1616), (int) intVal().eval(1617), array.length);
    }

    public static void reverse(final short[] array, final int startIndexInclusive, final int endIndexExclusive) {
        if (refId(short[].class).eval(1618) == null) {
            return;
        }
        int i = Math.max((int) intId().eval(1619), (int) intVal().eval(1620));
        int j = Math.min(array.length, (int) intId().eval(1621)) - (int) intVal().eval(1622);
        short tmp;
        int _jitmagicLoopLimiter62 = 0;
        while ((boolean) relation(intId(), intId()).eval(1626) && _jitmagicLoopLimiter62++ < 1000) {
            tmp = (short) shortArrAccessExp(refId(short[].class), intId()).eval(1623);
            array[j] = (short) shortArrAccessExp(refId(short[].class), intId()).eval(1624);
            array[i] = (short) shortId().eval(1625);
            j--;
            i++;
        }
    }

    public static void shift(final boolean[] array, final int offset) {
        if (refId(boolean[].class).eval(1627) == null) {
            return;
        }
        shift(refId(boolean[].class).eval(1628), (int) intVal().eval(1629), array.length, (int) intId().eval(1630));
    }

    public static void shift(final boolean[] array, int startIndexInclusive, int endIndexExclusive, int offset) {
        if ((refId(boolean[].class).eval(1631) == null) || (int) intId().eval(1632) >= array.length - (int) intVal().eval(1633) || (boolean) relation(intId(), intVal()).eval(1634)) {
            return;
        }
        if ((boolean) relation(intId(), intVal()).eval(1635)) {
            startIndexInclusive = (int) intVal().eval(1636);
        }
        if ((int) intId().eval(1637) >= array.length) {
            endIndexExclusive = array.length;
        }
        int n = (int) arithmetic(intId(), intId()).eval(1638);
        if ((boolean) relation(intId(), intVal()).eval(1639)) {
            return;
        }
        offset %= (int) intId().eval(1640);
        if ((boolean) relation(intId(), intVal()).eval(1641)) {
            offset += (int) intId().eval(1642);
        }
        int _jitmagicLoopLimiter63 = 0;
        while ((boolean) logic(relation(intId(), intVal()), relation(intId(), intVal())).eval(1662) && _jitmagicLoopLimiter63++ < 1000) {
            final int n_offset = (int) arithmetic(intId(), intId()).eval(1643);
            if ((boolean) relation(intId(), intId()).eval(1644)) {
                swap(refId(boolean[].class).eval(1656), (int) intId().eval(1657), (int) arithmetic(arithmetic(intId(), intId()), intId()).eval(1658), (int) intId().eval(1659));
                n = (int) intId().eval(1660);
                offset -= (int) intId().eval(1661);
            } else if ((boolean) relation(intId(), intId()).eval(1645)) {
                swap(refId(boolean[].class).eval(1650), (int) intId().eval(1651), (int) arithmetic(intId(), intId()).eval(1652), (int) intId().eval(1653));
                startIndexInclusive += (int) intId().eval(1654);
                n = (int) intId().eval(1655);
            } else {
                swap(refId(boolean[].class).eval(1646), (int) intId().eval(1647), (int) arithmetic(intId(), intId()).eval(1648), (int) intId().eval(1649));
                break;
            }
        }
    }

    public static void shift(final byte[] array, final int offset) {
        if (refId(byte[].class).eval(1663) == null) {
            return;
        }
        shift(refId(byte[].class).eval(1664), (int) intVal().eval(1665), array.length, (int) intId().eval(1666));
    }

    public static void shift(final byte[] array, int startIndexInclusive, int endIndexExclusive, int offset) {
        if ((refId(byte[].class).eval(1667) == null) || (int) intId().eval(1668) >= array.length - (int) intVal().eval(1669) || (boolean) relation(intId(), intVal()).eval(1670)) {
            return;
        }
        if ((boolean) relation(intId(), intVal()).eval(1671)) {
            startIndexInclusive = (int) intVal().eval(1672);
        }
        if ((int) intId().eval(1673) >= array.length) {
            endIndexExclusive = array.length;
        }
        int n = (int) arithmetic(intId(), intId()).eval(1674);
        if ((boolean) relation(intId(), intVal()).eval(1675)) {
            return;
        }
        offset %= (int) intId().eval(1676);
        if ((boolean) relation(intId(), intVal()).eval(1677)) {
            offset += (int) intId().eval(1678);
        }
        int _jitmagicLoopLimiter64 = 0;
        while ((boolean) logic(relation(intId(), intVal()), relation(intId(), intVal())).eval(1698) && _jitmagicLoopLimiter64++ < 1000) {
            final int n_offset = (int) arithmetic(intId(), intId()).eval(1679);
            if ((boolean) relation(intId(), intId()).eval(1680)) {
                swap(refId(byte[].class).eval(1692), (int) intId().eval(1693), (int) arithmetic(arithmetic(intId(), intId()), intId()).eval(1694), (int) intId().eval(1695));
                n = (int) intId().eval(1696);
                offset -= (int) intId().eval(1697);
            } else if ((boolean) relation(intId(), intId()).eval(1681)) {
                swap(refId(byte[].class).eval(1686), (int) intId().eval(1687), (int) arithmetic(intId(), intId()).eval(1688), (int) intId().eval(1689));
                startIndexInclusive += (int) intId().eval(1690);
                n = (int) intId().eval(1691);
            } else {
                swap(refId(byte[].class).eval(1682), (int) intId().eval(1683), (int) arithmetic(intId(), intId()).eval(1684), (int) intId().eval(1685));
                break;
            }
        }
    }

    public static void shift(final char[] array, final int offset) {
        if (refId(char[].class).eval(1699) == null) {
            return;
        }
        shift(refId(char[].class).eval(1700), (int) intVal().eval(1701), array.length, (int) intId().eval(1702));
    }

    public static void shift(final char[] array, int startIndexInclusive, int endIndexExclusive, int offset) {
        if ((refId(char[].class).eval(1703) == null) || (int) intId().eval(1704) >= array.length - (int) intVal().eval(1705) || (boolean) relation(intId(), intVal()).eval(1706)) {
            return;
        }
        if ((boolean) relation(intId(), intVal()).eval(1707)) {
            startIndexInclusive = (int) intVal().eval(1708);
        }
        if ((int) intId().eval(1709) >= array.length) {
            endIndexExclusive = array.length;
        }
        int n = (int) arithmetic(intId(), intId()).eval(1710);
        if ((boolean) relation(intId(), intVal()).eval(1711)) {
            return;
        }
        offset %= (int) intId().eval(1712);
        if ((boolean) relation(intId(), intVal()).eval(1713)) {
            offset += (int) intId().eval(1714);
        }
        int _jitmagicLoopLimiter65 = 0;
        while ((boolean) logic(relation(intId(), intVal()), relation(intId(), intVal())).eval(1734) && _jitmagicLoopLimiter65++ < 1000) {
            final int n_offset = (int) arithmetic(intId(), intId()).eval(1715);
            if ((boolean) relation(intId(), intId()).eval(1716)) {
                swap(refId(char[].class).eval(1728), (int) intId().eval(1729), (int) arithmetic(arithmetic(intId(), intId()), intId()).eval(1730), (int) intId().eval(1731));
                n = (int) intId().eval(1732);
                offset -= (int) intId().eval(1733);
            } else if ((boolean) relation(intId(), intId()).eval(1717)) {
                swap(refId(char[].class).eval(1722), (int) intId().eval(1723), (int) arithmetic(intId(), intId()).eval(1724), (int) intId().eval(1725));
                startIndexInclusive += (int) intId().eval(1726);
                n = (int) intId().eval(1727);
            } else {
                swap(refId(char[].class).eval(1718), (int) intId().eval(1719), (int) arithmetic(intId(), intId()).eval(1720), (int) intId().eval(1721));
                break;
            }
        }
    }

    public static void shift(final double[] array, final int offset) {
        if (refId(double[].class).eval(1735) == null) {
            return;
        }
        shift(refId(double[].class).eval(1736), (int) intVal().eval(1737), array.length, (int) intId().eval(1738));
    }

    public static void shift(final double[] array, int startIndexInclusive, int endIndexExclusive, int offset) {
        if ((refId(double[].class).eval(1739) == null) || (int) intId().eval(1740) >= array.length - (int) intVal().eval(1741) || (boolean) relation(intId(), intVal()).eval(1742)) {
            return;
        }
        if ((boolean) relation(intId(), intVal()).eval(1743)) {
            startIndexInclusive = (int) intVal().eval(1744);
        }
        if ((int) intId().eval(1745) >= array.length) {
            endIndexExclusive = array.length;
        }
        int n = (int) arithmetic(intId(), intId()).eval(1746);
        if ((boolean) relation(intId(), intVal()).eval(1747)) {
            return;
        }
        offset %= (int) intId().eval(1748);
        if ((boolean) relation(intId(), intVal()).eval(1749)) {
            offset += (int) intId().eval(1750);
        }
        int _jitmagicLoopLimiter66 = 0;
        while ((boolean) logic(relation(intId(), intVal()), relation(intId(), intVal())).eval(1770) && _jitmagicLoopLimiter66++ < 1000) {
            final int n_offset = (int) arithmetic(intId(), intId()).eval(1751);
            if ((boolean) relation(intId(), intId()).eval(1752)) {
                swap(refId(double[].class).eval(1764), (int) intId().eval(1765), (int) arithmetic(arithmetic(intId(), intId()), intId()).eval(1766), (int) intId().eval(1767));
                n = (int) intId().eval(1768);
                offset -= (int) intId().eval(1769);
            } else if ((boolean) relation(intId(), intId()).eval(1753)) {
                swap(refId(double[].class).eval(1758), (int) intId().eval(1759), (int) arithmetic(intId(), intId()).eval(1760), (int) intId().eval(1761));
                startIndexInclusive += (int) intId().eval(1762);
                n = (int) intId().eval(1763);
            } else {
                swap(refId(double[].class).eval(1754), (int) intId().eval(1755), (int) arithmetic(intId(), intId()).eval(1756), (int) intId().eval(1757));
                break;
            }
        }
    }

    public static void shift(final float[] array, final int offset) {
        if (refId(float[].class).eval(1771) == null) {
            return;
        }
        shift(refId(float[].class).eval(1772), (int) intVal().eval(1773), array.length, (int) intId().eval(1774));
    }

    public static void shift(final float[] array, int startIndexInclusive, int endIndexExclusive, int offset) {
        if ((refId(float[].class).eval(1775) == null) || (int) intId().eval(1776) >= array.length - (int) intVal().eval(1777) || (boolean) relation(intId(), intVal()).eval(1778)) {
            return;
        }
        if ((boolean) relation(intId(), intVal()).eval(1779)) {
            startIndexInclusive = (int) intVal().eval(1780);
        }
        if ((int) intId().eval(1781) >= array.length) {
            endIndexExclusive = array.length;
        }
        int n = (int) arithmetic(intId(), intId()).eval(1782);
        if ((boolean) relation(intId(), intVal()).eval(1783)) {
            return;
        }
        offset %= (int) intId().eval(1784);
        if ((boolean) relation(intId(), intVal()).eval(1785)) {
            offset += (int) intId().eval(1786);
        }
        int _jitmagicLoopLimiter67 = 0;
        while ((boolean) logic(relation(intId(), intVal()), relation(intId(), intVal())).eval(1806) && _jitmagicLoopLimiter67++ < 1000) {
            final int n_offset = (int) arithmetic(intId(), intId()).eval(1787);
            if ((boolean) relation(intId(), intId()).eval(1788)) {
                swap(refId(float[].class).eval(1800), (int) intId().eval(1801), (int) arithmetic(arithmetic(intId(), intId()), intId()).eval(1802), (int) intId().eval(1803));
                n = (int) intId().eval(1804);
                offset -= (int) intId().eval(1805);
            } else if ((boolean) relation(intId(), intId()).eval(1789)) {
                swap(refId(float[].class).eval(1794), (int) intId().eval(1795), (int) arithmetic(intId(), intId()).eval(1796), (int) intId().eval(1797));
                startIndexInclusive += (int) intId().eval(1798);
                n = (int) intId().eval(1799);
            } else {
                swap(refId(float[].class).eval(1790), (int) intId().eval(1791), (int) arithmetic(intId(), intId()).eval(1792), (int) intId().eval(1793));
                break;
            }
        }
    }

    public static void shift(final int[] array, final int offset) {
        if (refId(int[].class).eval(1807) == null) {
            return;
        }
        shift(refId(int[].class).eval(1808), (int) intVal().eval(1809), array.length, (int) intId().eval(1810));
    }

    public static void shift(final int[] array, int startIndexInclusive, int endIndexExclusive, int offset) {
        if ((refId(int[].class).eval(1811) == null) || (int) intId().eval(1812) >= array.length - (int) intVal().eval(1813) || (boolean) relation(intId(), intVal()).eval(1814)) {
            return;
        }
        if ((boolean) relation(intId(), intVal()).eval(1815)) {
            startIndexInclusive = (int) intVal().eval(1816);
        }
        if ((int) intId().eval(1817) >= array.length) {
            endIndexExclusive = array.length;
        }
        int n = (int) arithmetic(intId(), intId()).eval(1818);
        if ((boolean) relation(intId(), intVal()).eval(1819)) {
            return;
        }
        offset %= (int) intId().eval(1820);
        if ((boolean) relation(intId(), intVal()).eval(1821)) {
            offset += (int) intId().eval(1822);
        }
        int _jitmagicLoopLimiter68 = 0;
        while ((boolean) logic(relation(intId(), intVal()), relation(intId(), intVal())).eval(1842) && _jitmagicLoopLimiter68++ < 1000) {
            final int n_offset = (int) arithmetic(intId(), intId()).eval(1823);
            if ((boolean) relation(intId(), intId()).eval(1824)) {
                swap(refId(int[].class).eval(1836), (int) intId().eval(1837), (int) arithmetic(arithmetic(intId(), intId()), intId()).eval(1838), (int) intId().eval(1839));
                n = (int) intId().eval(1840);
                offset -= (int) intId().eval(1841);
            } else if ((boolean) relation(intId(), intId()).eval(1825)) {
                swap(refId(int[].class).eval(1830), (int) intId().eval(1831), (int) arithmetic(intId(), intId()).eval(1832), (int) intId().eval(1833));
                startIndexInclusive += (int) intId().eval(1834);
                n = (int) intId().eval(1835);
            } else {
                swap(refId(int[].class).eval(1826), (int) intId().eval(1827), (int) arithmetic(intId(), intId()).eval(1828), (int) intId().eval(1829));
                break;
            }
        }
    }

    public static void shift(final long[] array, final int offset) {
        if (refId(long[].class).eval(1843) == null) {
            return;
        }
        shift(refId(long[].class).eval(1844), (int) intVal().eval(1845), array.length, (int) intId().eval(1846));
    }

    public static void shift(final long[] array, int startIndexInclusive, int endIndexExclusive, int offset) {
        if ((refId(long[].class).eval(1847) == null) || (int) intId().eval(1848) >= array.length - (int) intVal().eval(1849) || (boolean) relation(intId(), intVal()).eval(1850)) {
            return;
        }
        if ((boolean) relation(intId(), intVal()).eval(1851)) {
            startIndexInclusive = (int) intVal().eval(1852);
        }
        if ((int) intId().eval(1853) >= array.length) {
            endIndexExclusive = array.length;
        }
        int n = (int) arithmetic(intId(), intId()).eval(1854);
        if ((boolean) relation(intId(), intVal()).eval(1855)) {
            return;
        }
        offset %= (int) intId().eval(1856);
        if ((boolean) relation(intId(), intVal()).eval(1857)) {
            offset += (int) intId().eval(1858);
        }
        int _jitmagicLoopLimiter69 = 0;
        while ((boolean) logic(relation(intId(), intVal()), relation(intId(), intVal())).eval(1878) && _jitmagicLoopLimiter69++ < 1000) {
            final int n_offset = (int) arithmetic(intId(), intId()).eval(1859);
            if ((boolean) relation(intId(), intId()).eval(1860)) {
                swap(refId(long[].class).eval(1872), (int) intId().eval(1873), (int) arithmetic(arithmetic(intId(), intId()), intId()).eval(1874), (int) intId().eval(1875));
                n = (int) intId().eval(1876);
                offset -= (int) intId().eval(1877);
            } else if ((boolean) relation(intId(), intId()).eval(1861)) {
                swap(refId(long[].class).eval(1866), (int) intId().eval(1867), (int) arithmetic(intId(), intId()).eval(1868), (int) intId().eval(1869));
                startIndexInclusive += (int) intId().eval(1870);
                n = (int) intId().eval(1871);
            } else {
                swap(refId(long[].class).eval(1862), (int) intId().eval(1863), (int) arithmetic(intId(), intId()).eval(1864), (int) intId().eval(1865));
                break;
            }
        }
    }

    public static void shift(final Object[] array, final int offset) {
        if (refId(java.lang.Object[].class).eval(1879) == null) {
            return;
        }
        shift(refId(java.lang.Object[].class).eval(1880), (int) intVal().eval(1881), array.length, (int) intId().eval(1882));
    }

    public static void shift(final Object[] array, int startIndexInclusive, int endIndexExclusive, int offset) {
        if ((refId(java.lang.Object[].class).eval(1883) == null) || (int) intId().eval(1884) >= array.length - (int) intVal().eval(1885) || (boolean) relation(intId(), intVal()).eval(1886)) {
            return;
        }
        if ((boolean) relation(intId(), intVal()).eval(1887)) {
            startIndexInclusive = (int) intVal().eval(1888);
        }
        if ((int) intId().eval(1889) >= array.length) {
            endIndexExclusive = array.length;
        }
        int n = (int) arithmetic(intId(), intId()).eval(1890);
        if ((boolean) relation(intId(), intVal()).eval(1891)) {
            return;
        }
        offset %= (int) intId().eval(1892);
        if ((boolean) relation(intId(), intVal()).eval(1893)) {
            offset += (int) intId().eval(1894);
        }
        int _jitmagicLoopLimiter70 = 0;
        while ((boolean) logic(relation(intId(), intVal()), relation(intId(), intVal())).eval(1914) && _jitmagicLoopLimiter70++ < 1000) {
            final int n_offset = (int) arithmetic(intId(), intId()).eval(1895);
            if ((boolean) relation(intId(), intId()).eval(1896)) {
                swap(refId(java.lang.Object[].class).eval(1908), (int) intId().eval(1909), (int) arithmetic(arithmetic(intId(), intId()), intId()).eval(1910), (int) intId().eval(1911));
                n = (int) intId().eval(1912);
                offset -= (int) intId().eval(1913);
            } else if ((boolean) relation(intId(), intId()).eval(1897)) {
                swap(refId(java.lang.Object[].class).eval(1902), (int) intId().eval(1903), (int) arithmetic(intId(), intId()).eval(1904), (int) intId().eval(1905));
                startIndexInclusive += (int) intId().eval(1906);
                n = (int) intId().eval(1907);
            } else {
                swap(refId(java.lang.Object[].class).eval(1898), (int) intId().eval(1899), (int) arithmetic(intId(), intId()).eval(1900), (int) intId().eval(1901));
                break;
            }
        }
    }

    public static void shift(final short[] array, final int offset) {
        if (refId(short[].class).eval(1915) == null) {
            return;
        }
        shift(refId(short[].class).eval(1916), (int) intVal().eval(1917), array.length, (int) intId().eval(1918));
    }

    public static void shift(final short[] array, int startIndexInclusive, int endIndexExclusive, int offset) {
        if ((refId(short[].class).eval(1919) == null) || (int) intId().eval(1920) >= array.length - (int) intVal().eval(1921) || (boolean) relation(intId(), intVal()).eval(1922)) {
            return;
        }
        if ((boolean) relation(intId(), intVal()).eval(1923)) {
            startIndexInclusive = (int) intVal().eval(1924);
        }
        if ((int) intId().eval(1925) >= array.length) {
            endIndexExclusive = array.length;
        }
        int n = (int) arithmetic(intId(), intId()).eval(1926);
        if ((boolean) relation(intId(), intVal()).eval(1927)) {
            return;
        }
        offset %= (int) intId().eval(1928);
        if ((boolean) relation(intId(), intVal()).eval(1929)) {
            offset += (int) intId().eval(1930);
        }
        int _jitmagicLoopLimiter71 = 0;
        while ((boolean) logic(relation(intId(), intVal()), relation(intId(), intVal())).eval(1950) && _jitmagicLoopLimiter71++ < 1000) {
            final int n_offset = (int) arithmetic(intId(), intId()).eval(1931);
            if ((boolean) relation(intId(), intId()).eval(1932)) {
                swap(refId(short[].class).eval(1944), (int) intId().eval(1945), (int) arithmetic(arithmetic(intId(), intId()), intId()).eval(1946), (int) intId().eval(1947));
                n = (int) intId().eval(1948);
                offset -= (int) intId().eval(1949);
            } else if ((boolean) relation(intId(), intId()).eval(1933)) {
                swap(refId(short[].class).eval(1938), (int) intId().eval(1939), (int) arithmetic(intId(), intId()).eval(1940), (int) intId().eval(1941));
                startIndexInclusive += (int) intId().eval(1942);
                n = (int) intId().eval(1943);
            } else {
                swap(refId(short[].class).eval(1934), (int) intId().eval(1935), (int) arithmetic(intId(), intId()).eval(1936), (int) intId().eval(1937));
                break;
            }
        }
    }

    public static void shuffle(final boolean[] array) {
        shuffle(refId(boolean[].class).eval(1951), random());
    }

    public static void shuffle(final boolean[] array, final Random random) {
        int _jitmagicLoopLimiter72 = 0;
        for (int i = array.length; (boolean) relation(intId(), intVal()).eval(1957) && _jitmagicLoopLimiter72++ < 1000; i--) {
            swap(refId(boolean[].class).eval(1952), (int) arithmetic(intId(), intVal()).eval(1953), refId(java.util.Random.class).eval(1955).nextInt((int) intId().eval(1954)), (int) intVal().eval(1956));
        }
    }

    public static void shuffle(final byte[] array) {
        shuffle(refId(byte[].class).eval(1958), random());
    }

    public static void shuffle(final byte[] array, final Random random) {
        int _jitmagicLoopLimiter73 = 0;
        for (int i = array.length; (boolean) relation(intId(), intVal()).eval(1964) && _jitmagicLoopLimiter73++ < 1000; i--) {
            swap(refId(byte[].class).eval(1959), (int) arithmetic(intId(), intVal()).eval(1960), refId(java.util.Random.class).eval(1962).nextInt((int) intId().eval(1961)), (int) intVal().eval(1963));
        }
    }

    public static void shuffle(final char[] array) {
        shuffle(refId(char[].class).eval(1965), random());
    }

    public static void shuffle(final char[] array, final Random random) {
        int _jitmagicLoopLimiter74 = 0;
        for (int i = array.length; (boolean) relation(intId(), intVal()).eval(1971) && _jitmagicLoopLimiter74++ < 1000; i--) {
            swap(refId(char[].class).eval(1966), (int) arithmetic(intId(), intVal()).eval(1967), refId(java.util.Random.class).eval(1969).nextInt((int) intId().eval(1968)), (int) intVal().eval(1970));
        }
    }

    public static void shuffle(final double[] array) {
        shuffle(refId(double[].class).eval(1972), random());
    }

    public static void shuffle(final double[] array, final Random random) {
        int _jitmagicLoopLimiter75 = 0;
        for (int i = array.length; (boolean) relation(intId(), intVal()).eval(1978) && _jitmagicLoopLimiter75++ < 1000; i--) {
            swap(refId(double[].class).eval(1973), (int) arithmetic(intId(), intVal()).eval(1974), refId(java.util.Random.class).eval(1976).nextInt((int) intId().eval(1975)), (int) intVal().eval(1977));
        }
    }

    public static void shuffle(final float[] array) {
        shuffle(refId(float[].class).eval(1979), random());
    }

    public static void shuffle(final float[] array, final Random random) {
        int _jitmagicLoopLimiter76 = 0;
        for (int i = array.length; (boolean) relation(intId(), intVal()).eval(1985) && _jitmagicLoopLimiter76++ < 1000; i--) {
            swap(refId(float[].class).eval(1980), (int) arithmetic(intId(), intVal()).eval(1981), refId(java.util.Random.class).eval(1983).nextInt((int) intId().eval(1982)), (int) intVal().eval(1984));
        }
    }

    public static void shuffle(final int[] array) {
        shuffle(refId(int[].class).eval(1986), random());
    }

    public static void shuffle(final int[] array, final Random random) {
        int _jitmagicLoopLimiter77 = 0;
        for (int i = array.length; (boolean) relation(intId(), intVal()).eval(1992) && _jitmagicLoopLimiter77++ < 1000; i--) {
            swap(refId(int[].class).eval(1987), (int) arithmetic(intId(), intVal()).eval(1988), refId(java.util.Random.class).eval(1990).nextInt((int) intId().eval(1989)), (int) intVal().eval(1991));
        }
    }

    public static void shuffle(final long[] array) {
        shuffle(refId(long[].class).eval(1993), random());
    }

    public static void shuffle(final long[] array, final Random random) {
        int _jitmagicLoopLimiter78 = 0;
        for (int i = array.length; (boolean) relation(intId(), intVal()).eval(1999) && _jitmagicLoopLimiter78++ < 1000; i--) {
            swap(refId(long[].class).eval(1994), (int) arithmetic(intId(), intVal()).eval(1995), refId(java.util.Random.class).eval(1997).nextInt((int) intId().eval(1996)), (int) intVal().eval(1998));
        }
    }

    public static void shuffle(final Object[] array) {
        shuffle(refId(java.lang.Object[].class).eval(2000), random());
    }

    public static void shuffle(final Object[] array, final Random random) {
        int _jitmagicLoopLimiter79 = 0;
        for (int i = array.length; (boolean) relation(intId(), intVal()).eval(2006) && _jitmagicLoopLimiter79++ < 1000; i--) {
            swap(refId(java.lang.Object[].class).eval(2001), (int) arithmetic(intId(), intVal()).eval(2002), refId(java.util.Random.class).eval(2004).nextInt((int) intId().eval(2003)), (int) intVal().eval(2005));
        }
    }

    public static void shuffle(final short[] array) {
        shuffle(refId(short[].class).eval(2007), random());
    }

    public static void shuffle(final short[] array, final Random random) {
        int _jitmagicLoopLimiter80 = 0;
        for (int i = array.length; (boolean) relation(intId(), intVal()).eval(2013) && _jitmagicLoopLimiter80++ < 1000; i--) {
            swap(refId(short[].class).eval(2008), (int) arithmetic(intId(), intVal()).eval(2009), refId(java.util.Random.class).eval(2011).nextInt((int) intId().eval(2010)), (int) intVal().eval(2012));
        }
    }

    public static boolean[] subarray(final boolean[] array, int startIndexInclusive, int endIndexExclusive) {
        if (refId(boolean[].class).eval(2014) == null) {
            return null;
        }
        if ((boolean) relation(intId(), intVal()).eval(2015)) {
            startIndexInclusive = (int) intVal().eval(2016);
        }
        if ((int) intId().eval(2017) > array.length) {
            endIndexExclusive = array.length;
        }
        final int newSize = (int) arithmetic(intId(), intId()).eval(2018);
        if ((boolean) relation(intId(), intVal()).eval(2019)) {
            return refId(boolean[].class).eval(2020);
        }
        final boolean[] subarray = new boolean[(int) intId().eval(2021)];
        System.arraycopy(refId(boolean[].class).eval(2022), (int) intId().eval(2023), refId(boolean[].class).eval(2024), (int) intVal().eval(2025), (int) intId().eval(2026));
        return refId(boolean[].class).eval(2027);
    }

    public static byte[] subarray(final byte[] array, int startIndexInclusive, int endIndexExclusive) {
        if (refId(byte[].class).eval(2028) == null) {
            return null;
        }
        if ((boolean) relation(intId(), intVal()).eval(2029)) {
            startIndexInclusive = (int) intVal().eval(2030);
        }
        if ((int) intId().eval(2031) > array.length) {
            endIndexExclusive = array.length;
        }
        final int newSize = (int) arithmetic(intId(), intId()).eval(2032);
        if ((boolean) relation(intId(), intVal()).eval(2033)) {
            return refId(byte[].class).eval(2034);
        }
        final byte[] subarray = new byte[(int) intId().eval(2035)];
        System.arraycopy(refId(byte[].class).eval(2036), (int) intId().eval(2037), refId(byte[].class).eval(2038), (int) intVal().eval(2039), (int) intId().eval(2040));
        return refId(byte[].class).eval(2041);
    }

    public static char[] subarray(final char[] array, int startIndexInclusive, int endIndexExclusive) {
        if (refId(char[].class).eval(2042) == null) {
            return null;
        }
        if ((boolean) relation(intId(), intVal()).eval(2043)) {
            startIndexInclusive = (int) intVal().eval(2044);
        }
        if ((int) intId().eval(2045) > array.length) {
            endIndexExclusive = array.length;
        }
        final int newSize = (int) arithmetic(intId(), intId()).eval(2046);
        if ((boolean) relation(intId(), intVal()).eval(2047)) {
            return refId(char[].class).eval(2048);
        }
        final char[] subarray = new char[(int) intId().eval(2049)];
        System.arraycopy(refId(char[].class).eval(2050), (int) intId().eval(2051), refId(char[].class).eval(2052), (int) intVal().eval(2053), (int) intId().eval(2054));
        return refId(char[].class).eval(2055);
    }

    public static double[] subarray(final double[] array, int startIndexInclusive, int endIndexExclusive) {
        if (refId(double[].class).eval(2056) == null) {
            return null;
        }
        if ((boolean) relation(intId(), intVal()).eval(2057)) {
            startIndexInclusive = (int) intVal().eval(2058);
        }
        if ((int) intId().eval(2059) > array.length) {
            endIndexExclusive = array.length;
        }
        final int newSize = (int) arithmetic(intId(), intId()).eval(2060);
        if ((boolean) relation(intId(), intVal()).eval(2061)) {
            return refId(double[].class).eval(2062);
        }
        final double[] subarray = new double[(int) intId().eval(2063)];
        System.arraycopy(refId(double[].class).eval(2064), (int) intId().eval(2065), refId(double[].class).eval(2066), (int) intVal().eval(2067), (int) intId().eval(2068));
        return refId(double[].class).eval(2069);
    }

    public static float[] subarray(final float[] array, int startIndexInclusive, int endIndexExclusive) {
        if (EMPTY_FLOAT_ARRAY == null) {
            return null;
        }
        if ((boolean) (INDEX_NOT_FOUND > -31015256)) {
            startIndexInclusive = (int) 1397837510;
        }
        if ((int) endIndexExclusive > array.length) {
            endIndexExclusive = array.length;
        }
        final int newSize = (int) (endIndexExclusive + INDEX_NOT_FOUND);
        if ((boolean) (newSize > 2110253607)) {
            return refId(float[].class).eval(2076);
        }
        final float[] subarray = new float[(int) newSize];
        System.arraycopy(refId(float[].class).eval(2078), (int) intId().eval(2079), refId(float[].class).eval(2080), (int) intVal().eval(2081), (int) intId().eval(2082));
        return refId(float[].class).eval(2083);
    }

    public static int[] subarray(final int[] array, int startIndexInclusive, int endIndexExclusive) {
        if (refId(int[].class).eval(2084) == null) {
            return null;
        }
        if ((boolean) relation(intId(), intVal()).eval(2085)) {
            startIndexInclusive = (int) intVal().eval(2086);
        }
        if ((int) intId().eval(2087) > array.length) {
            endIndexExclusive = array.length;
        }
        final int newSize = (int) arithmetic(intId(), intId()).eval(2088);
        if ((boolean) relation(intId(), intVal()).eval(2089)) {
            return refId(int[].class).eval(2090);
        }
        final int[] subarray = new int[(int) intId().eval(2091)];
        System.arraycopy(refId(int[].class).eval(2092), (int) intId().eval(2093), refId(int[].class).eval(2094), (int) intVal().eval(2095), (int) intId().eval(2096));
        return refId(int[].class).eval(2097);
    }

    public static long[] subarray(final long[] array, int startIndexInclusive, int endIndexExclusive) {
        if (refId(long[].class).eval(2098) == null) {
            return null;
        }
        if ((boolean) relation(intId(), intVal()).eval(2099)) {
            startIndexInclusive = (int) intVal().eval(2100);
        }
        if ((int) intId().eval(2101) > array.length) {
            endIndexExclusive = array.length;
        }
        final int newSize = (int) arithmetic(intId(), intId()).eval(2102);
        if ((boolean) relation(intId(), intVal()).eval(2103)) {
            return refId(long[].class).eval(2104);
        }
        final long[] subarray = new long[(int) intId().eval(2105)];
        System.arraycopy(refId(long[].class).eval(2106), (int) intId().eval(2107), refId(long[].class).eval(2108), (int) intVal().eval(2109), (int) intId().eval(2110));
        return refId(long[].class).eval(2111);
    }

    public static short[] subarray(final short[] array, int startIndexInclusive, int endIndexExclusive) {
        if (refId(short[].class).eval(2112) == null) {
            return null;
        }
        if ((boolean) relation(intId(), intVal()).eval(2113)) {
            startIndexInclusive = (int) intVal().eval(2114);
        }
        if ((int) intId().eval(2115) > array.length) {
            endIndexExclusive = array.length;
        }
        final int newSize = (int) arithmetic(intId(), intId()).eval(2116);
        if ((boolean) relation(intId(), intVal()).eval(2117)) {
            return refId(short[].class).eval(2118);
        }
        final short[] subarray = new short[(int) intId().eval(2119)];
        System.arraycopy(refId(short[].class).eval(2120), (int) intId().eval(2121), refId(short[].class).eval(2122), (int) intVal().eval(2123), (int) intId().eval(2124));
        return refId(short[].class).eval(2125);
    }

    public static <T> T[] subarray(final T[] array, int startIndexInclusive, int endIndexExclusive) {
        if (array == null) {
            return null;
        }
        if ((boolean) relation(intId(), intVal()).eval(2126)) {
            startIndexInclusive = (int) intVal().eval(2127);
        }
        if ((int) intId().eval(2128) > array.length) {
            endIndexExclusive = array.length;
        }
        final int newSize = (int) arithmetic(intId(), intId()).eval(2129);
        final Class<T> type = getComponentType(array);
        if ((boolean) relation(intId(), intVal()).eval(2130)) {
            return newInstance(type, (int) intVal().eval(2131));
        }
        final T[] subarray = newInstance(type, (int) intId().eval(2132));
        System.arraycopy(array, (int) intId().eval(2133), subarray, (int) intVal().eval(2134), (int) intId().eval(2135));
        return subarray;
    }

    public static void swap(final boolean[] array, final int offset1, final int offset2) {
        if (isEmpty(refId(boolean[].class).eval(2136))) {
            return;
        }
        swap(refId(boolean[].class).eval(2137), (int) intId().eval(2138), (int) intId().eval(2139), (int) intVal().eval(2140));
    }

    public static void swap(final boolean[] array, int offset1, int offset2, int len) {
        if (isEmpty(refId(boolean[].class).eval(2141)) || (int) intId().eval(2142) >= array.length || (int) intId().eval(2143) >= array.length) {
            return;
        }
        if ((boolean) relation(intId(), intVal()).eval(2144)) {
            offset1 = (int) intVal().eval(2145);
        }
        if ((boolean) relation(intId(), intVal()).eval(2146)) {
            offset2 = (int) intVal().eval(2147);
        }
        len = Math.min(Math.min((int) intId().eval(2148), array.length - (int) intId().eval(2149)), array.length - (int) intId().eval(2150));
        int _jitmagicLoopLimiter81 = 0;
        for (int i = (int) intVal().eval(2155); (boolean) relation(intId(), intId()).eval(2154) && _jitmagicLoopLimiter81++ < 1000; i++, offset1++, offset2++) {
            final boolean aux = (boolean) boolArrAccessExp(refId(boolean[].class), intId()).eval(2151);
            array[offset1] = (boolean) boolArrAccessExp(refId(boolean[].class), intId()).eval(2152);
            array[offset2] = (boolean) boolId().eval(2153);
        }
    }

    public static void swap(final byte[] array, final int offset1, final int offset2) {
        if (isEmpty(refId(byte[].class).eval(2156))) {
            return;
        }
        swap(refId(byte[].class).eval(2157), (int) intId().eval(2158), (int) intId().eval(2159), (int) intVal().eval(2160));
    }

    public static void swap(final byte[] array, int offset1, int offset2, int len) {
        if (isEmpty(refId(byte[].class).eval(2161)) || (int) intId().eval(2162) >= array.length || (int) intId().eval(2163) >= array.length) {
            return;
        }
        if ((boolean) relation(intId(), intVal()).eval(2164)) {
            offset1 = (int) intVal().eval(2165);
        }
        if ((boolean) relation(intId(), intVal()).eval(2166)) {
            offset2 = (int) intVal().eval(2167);
        }
        len = Math.min(Math.min((int) intId().eval(2168), array.length - (int) intId().eval(2169)), array.length - (int) intId().eval(2170));
        int _jitmagicLoopLimiter82 = 0;
        for (int i = (int) intVal().eval(2175); (boolean) relation(intId(), intId()).eval(2174) && _jitmagicLoopLimiter82++ < 1000; i++, offset1++, offset2++) {
            final byte aux = (byte) byteArrAccessExp(refId(byte[].class), intId()).eval(2171);
            array[offset1] = (byte) byteArrAccessExp(refId(byte[].class), intId()).eval(2172);
            array[offset2] = (byte) byteId().eval(2173);
        }
    }

    public static void swap(final char[] array, final int offset1, final int offset2) {
        if (isEmpty(refId(char[].class).eval(2176))) {
            return;
        }
        swap(refId(char[].class).eval(2177), (int) intId().eval(2178), (int) intId().eval(2179), (int) intVal().eval(2180));
    }

    public static void swap(final char[] array, int offset1, int offset2, int len) {
        if (isEmpty(refId(char[].class).eval(2181)) || (int) intId().eval(2182) >= array.length || (int) intId().eval(2183) >= array.length) {
            return;
        }
        if ((boolean) relation(intId(), intVal()).eval(2184)) {
            offset1 = (int) intVal().eval(2185);
        }
        if ((boolean) relation(intId(), intVal()).eval(2186)) {
            offset2 = (int) intVal().eval(2187);
        }
        len = Math.min(Math.min((int) intId().eval(2188), array.length - (int) intId().eval(2189)), array.length - (int) intId().eval(2190));
        int _jitmagicLoopLimiter83 = 0;
        for (int i = (int) intVal().eval(2195); (boolean) relation(intId(), intId()).eval(2194) && _jitmagicLoopLimiter83++ < 1000; i++, offset1++, offset2++) {
            final char aux = (char) charArrAccessExp(refId(char[].class), intId()).eval(2191);
            array[offset1] = (char) charArrAccessExp(refId(char[].class), intId()).eval(2192);
            array[offset2] = (char) charId().eval(2193);
        }
    }

    public static void swap(final double[] array, final int offset1, final int offset2) {
        if (isEmpty(refId(double[].class).eval(2196))) {
            return;
        }
        swap(refId(double[].class).eval(2197), (int) intId().eval(2198), (int) intId().eval(2199), (int) intVal().eval(2200));
    }

    public static void swap(final double[] array, int offset1, int offset2, int len) {
        if (isEmpty(refId(double[].class).eval(2201)) || (int) intId().eval(2202) >= array.length || (int) intId().eval(2203) >= array.length) {
            return;
        }
        if ((boolean) relation(intId(), intVal()).eval(2204)) {
            offset1 = (int) intVal().eval(2205);
        }
        if ((boolean) relation(intId(), intVal()).eval(2206)) {
            offset2 = (int) intVal().eval(2207);
        }
        len = Math.min(Math.min((int) intId().eval(2208), array.length - (int) intId().eval(2209)), array.length - (int) intId().eval(2210));
        int _jitmagicLoopLimiter84 = 0;
        for (int i = (int) intVal().eval(2215); (boolean) relation(intId(), intId()).eval(2214) && _jitmagicLoopLimiter84++ < 1000; i++, offset1++, offset2++) {
            final double aux = (double) doubleArrAccessExp(refId(double[].class), intId()).eval(2211);
            array[offset1] = (double) doubleArrAccessExp(refId(double[].class), intId()).eval(2212);
            array[offset2] = (double) doubleId().eval(2213);
        }
    }

    public static void swap(final float[] array, final int offset1, final int offset2) {
        if (isEmpty(refId(float[].class).eval(2216))) {
            return;
        }
        swap(refId(float[].class).eval(2217), (int) intId().eval(2218), (int) intId().eval(2219), (int) intVal().eval(2220));
    }

    public static void swap(final float[] array, int offset1, int offset2, int len) {
        if (isEmpty(refId(float[].class).eval(2221)) || (int) intId().eval(2222) >= array.length || (int) intId().eval(2223) >= array.length) {
            return;
        }
        if ((boolean) relation(intId(), intVal()).eval(2224)) {
            offset1 = (int) intVal().eval(2225);
        }
        if ((boolean) relation(intId(), intVal()).eval(2226)) {
            offset2 = (int) intVal().eval(2227);
        }
        len = Math.min(Math.min((int) intId().eval(2228), array.length - (int) intId().eval(2229)), array.length - (int) intId().eval(2230));
        int _jitmagicLoopLimiter85 = 0;
        for (int i = (int) intVal().eval(2235); (boolean) relation(intId(), intId()).eval(2234) && _jitmagicLoopLimiter85++ < 1000; i++, offset1++, offset2++) {
            final float aux = (float) floatArrAccessExp(refId(float[].class), intId()).eval(2231);
            array[offset1] = (float) floatArrAccessExp(refId(float[].class), intId()).eval(2232);
            array[offset2] = (float) floatId().eval(2233);
        }
    }

    public static void swap(final int[] array, final int offset1, final int offset2) {
        if (isEmpty(refId(int[].class).eval(2236))) {
            return;
        }
        swap(refId(int[].class).eval(2237), (int) intId().eval(2238), (int) intId().eval(2239), (int) intVal().eval(2240));
    }

    public static void swap(final int[] array, int offset1, int offset2, int len) {
        if (isEmpty(refId(int[].class).eval(2241)) || (int) intId().eval(2242) >= array.length || (int) intId().eval(2243) >= array.length) {
            return;
        }
        if ((boolean) relation(intId(), intVal()).eval(2244)) {
            offset1 = (int) intVal().eval(2245);
        }
        if ((boolean) relation(intId(), intVal()).eval(2246)) {
            offset2 = (int) intVal().eval(2247);
        }
        len = Math.min(Math.min((int) intId().eval(2248), array.length - (int) intId().eval(2249)), array.length - (int) intId().eval(2250));
        int _jitmagicLoopLimiter86 = 0;
        for (int i = (int) intVal().eval(2255); (boolean) relation(intId(), intId()).eval(2254) && _jitmagicLoopLimiter86++ < 1000; i++, offset1++, offset2++) {
            final int aux = (int) intArrAccessExp(refId(int[].class), intId()).eval(2251);
            array[offset1] = (int) intArrAccessExp(refId(int[].class), intId()).eval(2252);
            array[offset2] = (int) intId().eval(2253);
        }
    }

    public static void swap(final long[] array, final int offset1, final int offset2) {
        if (isEmpty(refId(long[].class).eval(2256))) {
            return;
        }
        swap(refId(long[].class).eval(2257), (int) intId().eval(2258), (int) intId().eval(2259), (int) intVal().eval(2260));
    }

    public static void swap(final long[] array, int offset1, int offset2, int len) {
        if (isEmpty(refId(long[].class).eval(2261)) || (int) intId().eval(2262) >= array.length || (int) intId().eval(2263) >= array.length) {
            return;
        }
        if ((boolean) relation(intId(), intVal()).eval(2264)) {
            offset1 = (int) intVal().eval(2265);
        }
        if ((boolean) relation(intId(), intVal()).eval(2266)) {
            offset2 = (int) intVal().eval(2267);
        }
        len = Math.min(Math.min((int) intId().eval(2268), array.length - (int) intId().eval(2269)), array.length - (int) intId().eval(2270));
        int _jitmagicLoopLimiter87 = 0;
        for (int i = (int) intVal().eval(2275); (boolean) relation(intId(), intId()).eval(2274) && _jitmagicLoopLimiter87++ < 1000; i++, offset1++, offset2++) {
            final long aux = (long) longArrAccessExp(refId(long[].class), intId()).eval(2271);
            array[offset1] = (long) longArrAccessExp(refId(long[].class), intId()).eval(2272);
            array[offset2] = (long) longId().eval(2273);
        }
    }

    public static void swap(final Object[] array, final int offset1, final int offset2) {
        if (isEmpty(refId(java.lang.Object[].class).eval(2276))) {
            return;
        }
        swap(refId(java.lang.Object[].class).eval(2277), (int) intId().eval(2278), (int) intId().eval(2279), (int) intVal().eval(2280));
    }

    public static void swap(final Object[] array, int offset1, int offset2, int len) {
        if (isEmpty(refId(java.lang.Object[].class).eval(2281)) || (int) intId().eval(2282) >= array.length || (int) intId().eval(2283) >= array.length) {
            return;
        }
        if ((boolean) relation(intId(), intVal()).eval(2284)) {
            offset1 = (int) intVal().eval(2285);
        }
        if ((boolean) relation(intId(), intVal()).eval(2286)) {
            offset2 = (int) intVal().eval(2287);
        }
        len = Math.min(Math.min((int) intId().eval(2288), array.length - (int) intId().eval(2289)), array.length - (int) intId().eval(2290));
        int _jitmagicLoopLimiter88 = 0;
        for (int i = (int) intVal().eval(2295); (boolean) relation(intId(), intId()).eval(2294) && _jitmagicLoopLimiter88++ < 1000; i++, offset1++, offset2++) {
            final Object aux = refArrAccessExp(java.lang.Object.class, java.lang.Object[].class, refId(java.lang.Object[].class), intId()).eval(2291);
            array[offset1] = refArrAccessExp(java.lang.Object.class, java.lang.Object[].class, refId(java.lang.Object[].class), intId()).eval(2292);
            array[offset2] = refId(java.lang.Object.class).eval(2293);
        }
    }

    public static void swap(final short[] array, final int offset1, final int offset2) {
        if (isEmpty(refId(short[].class).eval(2296))) {
            return;
        }
        swap(refId(short[].class).eval(2297), (int) intId().eval(2298), (int) intId().eval(2299), (int) intVal().eval(2300));
    }

    public static void swap(final short[] array, int offset1, int offset2, int len) {
        if (isEmpty(refId(short[].class).eval(2301)) || (int) intId().eval(2302) >= array.length || (int) intId().eval(2303) >= array.length) {
            return;
        }
        if ((boolean) relation(intId(), intVal()).eval(2304)) {
            offset1 = (int) intVal().eval(2305);
        }
        if ((boolean) relation(intId(), intVal()).eval(2306)) {
            offset2 = (int) intVal().eval(2307);
        }
        if ((boolean) relation(intId(), intId()).eval(2308)) {
            return;
        }
        len = Math.min(Math.min((int) intId().eval(2309), array.length - (int) intId().eval(2310)), array.length - (int) intId().eval(2311));
        int _jitmagicLoopLimiter89 = 0;
        for (int i = (int) intVal().eval(2316); (boolean) relation(intId(), intId()).eval(2315) && _jitmagicLoopLimiter89++ < 1000; i++, offset1++, offset2++) {
            final short aux = (short) shortArrAccessExp(refId(short[].class), intId()).eval(2312);
            array[offset1] = (short) shortArrAccessExp(refId(short[].class), intId()).eval(2313);
            array[offset2] = (short) shortId().eval(2314);
        }
    }

    public static <T> T[] toArray(@SuppressWarnings("unchecked") final T... items) {
        return items;
    }

    public static Map<Object, Object> toMap(final Object[] array) {
        if (refId(java.lang.Object[].class).eval(2317) == null) {
            return null;
        }
        final Map<Object, Object> map = new HashMap<>((int) (array.length * (double) doubleVal().eval(2318)));
        int _jitmagicLoopLimiter90 = 0;
        for (int i = (int) intVal().eval(2330); (int) intId().eval(2329) < array.length && _jitmagicLoopLimiter90++ < 1000; i++) {
            final Object object = refArrAccessExp(java.lang.Object.class, java.lang.Object[].class, refId(java.lang.Object[].class), intId()).eval(2319);
            if (object instanceof Map.Entry<?, ?>) {
                final Map.Entry<?, ?> entry = cast(Map.Entry.class, refId(java.lang.Object.class)).eval(2328);
                map.put(entry.getKey(), entry.getValue());
            } else if (object instanceof Object[]) {
                final Object[] entry = cast(Object[].class, refId(java.lang.Object.class)).eval(2322);
                if (entry.length < (int) intVal().eval(2323)) {
                    throw new IllegalArgumentException("Array element " + (int) intId().eval(2324) + ", '" + refId(java.lang.Object.class).eval(2325) + "', has a length less than 2");
                }
                map.put(refArrAccessExp(java.lang.Object.class, java.lang.Object[].class, refId(java.lang.Object[].class)).eval(2326), refArrAccessExp(java.lang.Object.class, java.lang.Object[].class, refId(java.lang.Object[].class)).eval(2327));
            } else {
                throw new IllegalArgumentException("Array element " + (int) intId().eval(2320) + ", '" + refId(java.lang.Object.class).eval(2321) + "', is neither of type Map.Entry nor an Array");
            }
        }
        return map;
    }

    public static Boolean[] toObject(final boolean[] array) {
        if (refId(boolean[].class).eval(2331) == null) {
            return null;
        }
        if (array.length == (int) intVal().eval(2332)) {
            return refId(java.lang.Boolean[].class).eval(2333);
        }
        final Boolean[] result = new Boolean[array.length];
        int _jitmagicLoopLimiter91 = 0;
        for (int i = (int) intVal().eval(2336); (int) intId().eval(2335) < array.length && _jitmagicLoopLimiter91++ < 1000; i++) {
            result[i] = ((boolean) boolArrAccessExp(refId(boolean[].class), intId()).eval(2334) ? Boolean.TRUE : Boolean.FALSE);
        }
        return refId(java.lang.Boolean[].class).eval(2337);
    }

    public static Byte[] toObject(final byte[] array) {
        if (refId(byte[].class).eval(2338) == null) {
            return null;
        }
        if (array.length == (int) intVal().eval(2339)) {
            return refId(java.lang.Byte[].class).eval(2340);
        }
        final Byte[] result = new Byte[array.length];
        int _jitmagicLoopLimiter92 = 0;
        for (int i = (int) intVal().eval(2343); (int) intId().eval(2342) < array.length && _jitmagicLoopLimiter92++ < 1000; i++) {
            result[i] = Byte.valueOf((byte) byteArrAccessExp(refId(byte[].class), intId()).eval(2341));
        }
        return refId(java.lang.Byte[].class).eval(2344);
    }

    public static Character[] toObject(final char[] array) {
        if (refId(char[].class).eval(2345) == null) {
            return null;
        }
        if (array.length == (int) intVal().eval(2346)) {
            return refId(java.lang.Character[].class).eval(2347);
        }
        final Character[] result = new Character[array.length];
        int _jitmagicLoopLimiter93 = 0;
        for (int i = (int) intVal().eval(2350); (int) intId().eval(2349) < array.length && _jitmagicLoopLimiter93++ < 1000; i++) {
            result[i] = Character.valueOf((char) charArrAccessExp(refId(char[].class), intId()).eval(2348));
        }
        return refId(java.lang.Character[].class).eval(2351);
    }

    public static Double[] toObject(final double[] array) {
        if (refId(double[].class).eval(2352) == null) {
            return null;
        }
        if (array.length == (int) intVal().eval(2353)) {
            return refId(java.lang.Double[].class).eval(2354);
        }
        final Double[] result = new Double[array.length];
        int _jitmagicLoopLimiter94 = 0;
        for (int i = (int) intVal().eval(2357); (int) intId().eval(2356) < array.length && _jitmagicLoopLimiter94++ < 1000; i++) {
            result[i] = Double.valueOf((double) doubleArrAccessExp(refId(double[].class), intId()).eval(2355));
        }
        return refId(java.lang.Double[].class).eval(2358);
    }

    public static Float[] toObject(final float[] array) {
        if (refId(float[].class).eval(2359) == null) {
            return null;
        }
        if (array.length == (int) intVal().eval(2360)) {
            return refId(java.lang.Float[].class).eval(2361);
        }
        final Float[] result = new Float[array.length];
        int _jitmagicLoopLimiter95 = 0;
        for (int i = (int) intVal().eval(2364); (int) intId().eval(2363) < array.length && _jitmagicLoopLimiter95++ < 1000; i++) {
            result[i] = Float.valueOf((float) floatArrAccessExp(refId(float[].class), intId()).eval(2362));
        }
        return refId(java.lang.Float[].class).eval(2365);
    }

    public static Integer[] toObject(final int[] array) {
        if (refId(int[].class).eval(2366) == null) {
            return null;
        }
        if (array.length == (int) intVal().eval(2367)) {
            return refId(java.lang.Integer[].class).eval(2368);
        }
        final Integer[] result = new Integer[array.length];
        int _jitmagicLoopLimiter96 = 0;
        for (int i = (int) intVal().eval(2371); (int) intId().eval(2370) < array.length && _jitmagicLoopLimiter96++ < 1000; i++) {
            result[i] = Integer.valueOf((int) intArrAccessExp(refId(int[].class), intId()).eval(2369));
        }
        return refId(java.lang.Integer[].class).eval(2372);
    }

    public static Long[] toObject(final long[] array) {
        if (refId(long[].class).eval(2373) == null) {
            return null;
        }
        if (array.length == (int) intVal().eval(2374)) {
            return refId(java.lang.Long[].class).eval(2375);
        }
        final Long[] result = new Long[array.length];
        int _jitmagicLoopLimiter97 = 0;
        for (int i = (int) intVal().eval(2378); (int) intId().eval(2377) < array.length && _jitmagicLoopLimiter97++ < 1000; i++) {
            result[i] = Long.valueOf((long) longArrAccessExp(refId(long[].class), intId()).eval(2376));
        }
        return refId(java.lang.Long[].class).eval(2379);
    }

    public static Short[] toObject(final short[] array) {
        if (refId(short[].class).eval(2380) == null) {
            return null;
        }
        if (array.length == (int) intVal().eval(2381)) {
            return refId(java.lang.Short[].class).eval(2382);
        }
        final Short[] result = new Short[array.length];
        int _jitmagicLoopLimiter98 = 0;
        for (int i = (int) intVal().eval(2385); (int) intId().eval(2384) < array.length && _jitmagicLoopLimiter98++ < 1000; i++) {
            result[i] = Short.valueOf((short) shortArrAccessExp(refId(short[].class), intId()).eval(2383));
        }
        return refId(java.lang.Short[].class).eval(2386);
    }

    public static boolean[] toPrimitive(final Boolean[] array) {
        if (refId(java.lang.Boolean[].class).eval(2387) == null) {
            return null;
        }
        if (array.length == (int) intVal().eval(2388)) {
            return refId(boolean[].class).eval(2389);
        }
        final boolean[] result = new boolean[array.length];
        int _jitmagicLoopLimiter99 = 0;
        for (int i = (int) intVal().eval(2392); (int) intId().eval(2391) < array.length && _jitmagicLoopLimiter99++ < 1000; i++) {
            result[i] = refArrAccessExp(java.lang.Boolean.class, java.lang.Boolean[].class, refId(java.lang.Boolean[].class), intId()).eval(2390).booleanValue();
        }
        return refId(boolean[].class).eval(2393);
    }

    public static boolean[] toPrimitive(final Boolean[] array, final boolean valueForNull) {
        if (refId(java.lang.Boolean[].class).eval(2394) == null) {
            return null;
        }
        if (array.length == (int) intVal().eval(2395)) {
            return refId(boolean[].class).eval(2396);
        }
        final boolean[] result = new boolean[array.length];
        int _jitmagicLoopLimiter100 = 0;
        for (int i = (int) intVal().eval(2402); (int) intId().eval(2401) < array.length && _jitmagicLoopLimiter100++ < 1000; i++) {
            final Boolean b = refArrAccessExp(java.lang.Boolean.class, java.lang.Boolean[].class, refId(java.lang.Boolean[].class), intId()).eval(2397);
            result[i] = (refId(java.lang.Boolean.class).eval(2398) == null ? (boolean) boolId().eval(2400) : refId(java.lang.Boolean.class).eval(2399).booleanValue());
        }
        return refId(boolean[].class).eval(2403);
    }

    public static byte[] toPrimitive(final Byte[] array) {
        if (refId(java.lang.Byte[].class).eval(2404) == null) {
            return null;
        }
        if (array.length == (int) intVal().eval(2405)) {
            return refId(byte[].class).eval(2406);
        }
        final byte[] result = new byte[array.length];
        int _jitmagicLoopLimiter101 = 0;
        for (int i = (int) intVal().eval(2409); (int) intId().eval(2408) < array.length && _jitmagicLoopLimiter101++ < 1000; i++) {
            result[i] = (byte) refArrAccessExp(java.lang.Byte.class, java.lang.Byte[].class, refId(java.lang.Byte[].class), intId()).eval(2407).byteValue();
        }
        return refId(byte[].class).eval(2410);
    }

    public static byte[] toPrimitive(final Byte[] array, final byte valueForNull) {
        if (refId(java.lang.Byte[].class).eval(2411) == null) {
            return null;
        }
        if (array.length == (int) intVal().eval(2412)) {
            return refId(byte[].class).eval(2413);
        }
        final byte[] result = new byte[array.length];
        int _jitmagicLoopLimiter102 = 0;
        for (int i = (int) intVal().eval(2419); (int) intId().eval(2418) < array.length && _jitmagicLoopLimiter102++ < 1000; i++) {
            final Byte b = refArrAccessExp(java.lang.Byte.class, java.lang.Byte[].class, refId(java.lang.Byte[].class), intId()).eval(2414);
            result[i] = (byte) ((refId(java.lang.Byte.class).eval(2415) == null ? (byte) byteId().eval(2417) : refId(java.lang.Byte.class).eval(2416).byteValue()));
        }
        return refId(byte[].class).eval(2420);
    }

    public static char[] toPrimitive(final Character[] array) {
        if (refId(java.lang.Character[].class).eval(2421) == null) {
            return null;
        }
        if (array.length == (int) intVal().eval(2422)) {
            return refId(char[].class).eval(2423);
        }
        final char[] result = new char[array.length];
        int _jitmagicLoopLimiter103 = 0;
        for (int i = (int) intVal().eval(2426); (int) intId().eval(2425) < array.length && _jitmagicLoopLimiter103++ < 1000; i++) {
            result[i] = (char) refArrAccessExp(java.lang.Character.class, java.lang.Character[].class, refId(java.lang.Character[].class), intId()).eval(2424).charValue();
        }
        return refId(char[].class).eval(2427);
    }

    public static char[] toPrimitive(final Character[] array, final char valueForNull) {
        if (refId(java.lang.Character[].class).eval(2428) == null) {
            return null;
        }
        if (array.length == (int) intVal().eval(2429)) {
            return refId(char[].class).eval(2430);
        }
        final char[] result = new char[array.length];
        int _jitmagicLoopLimiter104 = 0;
        for (int i = (int) intVal().eval(2436); (int) intId().eval(2435) < array.length && _jitmagicLoopLimiter104++ < 1000; i++) {
            final Character b = refArrAccessExp(java.lang.Character.class, java.lang.Character[].class, refId(java.lang.Character[].class), intId()).eval(2431);
            result[i] = (char) ((refId(java.lang.Character.class).eval(2432) == null ? (char) charId().eval(2434) : refId(java.lang.Character.class).eval(2433).charValue()));
        }
        return refId(char[].class).eval(2437);
    }

    public static double[] toPrimitive(final Double[] array) {
        if (refId(java.lang.Double[].class).eval(2438) == null) {
            return null;
        }
        if (array.length == (int) intVal().eval(2439)) {
            return refId(double[].class).eval(2440);
        }
        final double[] result = new double[array.length];
        int _jitmagicLoopLimiter105 = 0;
        for (int i = (int) intVal().eval(2443); (int) intId().eval(2442) < array.length && _jitmagicLoopLimiter105++ < 1000; i++) {
            result[i] = refArrAccessExp(java.lang.Double.class, java.lang.Double[].class, refId(java.lang.Double[].class), intId()).eval(2441).doubleValue();
        }
        return refId(double[].class).eval(2444);
    }

    public static double[] toPrimitive(final Double[] array, final double valueForNull) {
        if (refId(java.lang.Double[].class).eval(2445) == null) {
            return null;
        }
        if (array.length == (int) intVal().eval(2446)) {
            return refId(double[].class).eval(2447);
        }
        final double[] result = new double[array.length];
        int _jitmagicLoopLimiter106 = 0;
        for (int i = (int) intVal().eval(2453); (int) intId().eval(2452) < array.length && _jitmagicLoopLimiter106++ < 1000; i++) {
            final Double b = refArrAccessExp(java.lang.Double.class, java.lang.Double[].class, refId(java.lang.Double[].class), intId()).eval(2448);
            result[i] = (refId(java.lang.Double.class).eval(2449) == null ? (double) doubleId().eval(2451) : refId(java.lang.Double.class).eval(2450).doubleValue());
        }
        return refId(double[].class).eval(2454);
    }

    public static float[] toPrimitive(final Float[] array) {
        if (refId(java.lang.Float[].class).eval(2455) == null) {
            return null;
        }
        if (array.length == (int) intVal().eval(2456)) {
            return refId(float[].class).eval(2457);
        }
        final float[] result = new float[array.length];
        int _jitmagicLoopLimiter107 = 0;
        for (int i = (int) intVal().eval(2460); (int) intId().eval(2459) < array.length && _jitmagicLoopLimiter107++ < 1000; i++) {
            result[i] = refArrAccessExp(java.lang.Float.class, java.lang.Float[].class, refId(java.lang.Float[].class), intId()).eval(2458).floatValue();
        }
        return refId(float[].class).eval(2461);
    }

    public static float[] toPrimitive(final Float[] array, final float valueForNull) {
        if (refId(java.lang.Float[].class).eval(2462) == null) {
            return null;
        }
        if (array.length == (int) intVal().eval(2463)) {
            return refId(float[].class).eval(2464);
        }
        final float[] result = new float[array.length];
        int _jitmagicLoopLimiter108 = 0;
        for (int i = (int) intVal().eval(2470); (int) intId().eval(2469) < array.length && _jitmagicLoopLimiter108++ < 1000; i++) {
            final Float b = refArrAccessExp(java.lang.Float.class, java.lang.Float[].class, refId(java.lang.Float[].class), intId()).eval(2465);
            result[i] = (refId(java.lang.Float.class).eval(2466) == null ? (float) floatId().eval(2468) : refId(java.lang.Float.class).eval(2467).floatValue());
        }
        return refId(float[].class).eval(2471);
    }

    public static int[] toPrimitive(final Integer[] array) {
        if (refId(java.lang.Integer[].class).eval(2472) == null) {
            return null;
        }
        if (array.length == (int) intVal().eval(2473)) {
            return refId(int[].class).eval(2474);
        }
        final int[] result = new int[array.length];
        int _jitmagicLoopLimiter109 = 0;
        for (int i = (int) intVal().eval(2477); (int) intId().eval(2476) < array.length && _jitmagicLoopLimiter109++ < 1000; i++) {
            result[i] = refArrAccessExp(java.lang.Integer.class, java.lang.Integer[].class, refId(java.lang.Integer[].class), intId()).eval(2475).intValue();
        }
        return refId(int[].class).eval(2478);
    }

    public static int[] toPrimitive(final Integer[] array, final int valueForNull) {
        if (refId(java.lang.Integer[].class).eval(2479) == null) {
            return null;
        }
        if (array.length == (int) intVal().eval(2480)) {
            return refId(int[].class).eval(2481);
        }
        final int[] result = new int[array.length];
        int _jitmagicLoopLimiter110 = 0;
        for (int i = (int) intVal().eval(2487); (int) intId().eval(2486) < array.length && _jitmagicLoopLimiter110++ < 1000; i++) {
            final Integer b = refArrAccessExp(java.lang.Integer.class, java.lang.Integer[].class, refId(java.lang.Integer[].class), intId()).eval(2482);
            result[i] = (refId(java.lang.Integer.class).eval(2483) == null ? (int) intId().eval(2485) : refId(java.lang.Integer.class).eval(2484).intValue());
        }
        return refId(int[].class).eval(2488);
    }

    public static long[] toPrimitive(final Long[] array) {
        if (refId(java.lang.Long[].class).eval(2489) == null) {
            return null;
        }
        if (array.length == (int) intVal().eval(2490)) {
            return refId(long[].class).eval(2491);
        }
        final long[] result = new long[array.length];
        int _jitmagicLoopLimiter111 = 0;
        for (int i = (int) intVal().eval(2494); (int) intId().eval(2493) < array.length && _jitmagicLoopLimiter111++ < 1000; i++) {
            result[i] = refArrAccessExp(java.lang.Long.class, java.lang.Long[].class, refId(java.lang.Long[].class), intId()).eval(2492).longValue();
        }
        return refId(long[].class).eval(2495);
    }

    public static long[] toPrimitive(final Long[] array, final long valueForNull) {
        if (refId(java.lang.Long[].class).eval(2496) == null) {
            return null;
        }
        if (array.length == (int) intVal().eval(2497)) {
            return refId(long[].class).eval(2498);
        }
        final long[] result = new long[array.length];
        int _jitmagicLoopLimiter112 = 0;
        for (int i = (int) intVal().eval(2504); (int) intId().eval(2503) < array.length && _jitmagicLoopLimiter112++ < 1000; i++) {
            final Long b = refArrAccessExp(java.lang.Long.class, java.lang.Long[].class, refId(java.lang.Long[].class), intId()).eval(2499);
            result[i] = (refId(java.lang.Long.class).eval(2500) == null ? (long) longId().eval(2502) : refId(java.lang.Long.class).eval(2501).longValue());
        }
        return refId(long[].class).eval(2505);
    }

    public static Object toPrimitive(final Object array) {
        if (refId(java.lang.Object.class).eval(2506) == null) {
            return null;
        }
        final Class<?> ct = refId(java.lang.Object.class).eval(2507).getClass().getComponentType();
        final Class<?> pt = ClassUtils.wrapperToPrimitive(ct);
        if (Boolean.TYPE.equals(pt)) {
            return toPrimitive(cast(Boolean[].class, refId(java.lang.Object.class)).eval(2508));
        }
        if (Character.TYPE.equals(pt)) {
            return toPrimitive(cast(Character[].class, refId(java.lang.Object.class)).eval(2509));
        }
        if (Byte.TYPE.equals(pt)) {
            return toPrimitive(cast(Byte[].class, refId(java.lang.Object.class)).eval(2510));
        }
        if (Integer.TYPE.equals(pt)) {
            return toPrimitive(cast(Integer[].class, refId(java.lang.Object.class)).eval(2511));
        }
        if (Long.TYPE.equals(pt)) {
            return toPrimitive(cast(Long[].class, refId(java.lang.Object.class)).eval(2512));
        }
        if (Short.TYPE.equals(pt)) {
            return toPrimitive(cast(Short[].class, refId(java.lang.Object.class)).eval(2513));
        }
        if (Double.TYPE.equals(pt)) {
            return toPrimitive(cast(Double[].class, refId(java.lang.Object.class)).eval(2514));
        }
        if (Float.TYPE.equals(pt)) {
            return toPrimitive(cast(Float[].class, refId(java.lang.Object.class)).eval(2515));
        }
        return refId(java.lang.Object.class).eval(2516);
    }

    public static short[] toPrimitive(final Short[] array) {
        if (refId(java.lang.Short[].class).eval(2517) == null) {
            return null;
        }
        if (array.length == (int) intVal().eval(2518)) {
            return refId(short[].class).eval(2519);
        }
        final short[] result = new short[array.length];
        int _jitmagicLoopLimiter113 = 0;
        for (int i = (int) intVal().eval(2522); (int) intId().eval(2521) < array.length && _jitmagicLoopLimiter113++ < 1000; i++) {
            result[i] = (short) refArrAccessExp(java.lang.Short.class, java.lang.Short[].class, refId(java.lang.Short[].class), intId()).eval(2520).shortValue();
        }
        return refId(short[].class).eval(2523);
    }

    public static short[] toPrimitive(final Short[] array, final short valueForNull) {
        if (refId(java.lang.Short[].class).eval(2524) == null) {
            return null;
        }
        if (array.length == (int) intVal().eval(2525)) {
            return refId(short[].class).eval(2526);
        }
        final short[] result = new short[array.length];
        int _jitmagicLoopLimiter114 = 0;
        for (int i = (int) intVal().eval(2532); (int) intId().eval(2531) < array.length && _jitmagicLoopLimiter114++ < 1000; i++) {
            final Short b = refArrAccessExp(java.lang.Short.class, java.lang.Short[].class, refId(java.lang.Short[].class), intId()).eval(2527);
            result[i] = (short) ((refId(java.lang.Short.class).eval(2528) == null ? (short) shortId().eval(2530) : refId(java.lang.Short.class).eval(2529).shortValue()));
        }
        return refId(short[].class).eval(2533);
    }

    public static String toString(final Object array) {
        return toString(refId(java.lang.Object.class).eval(2534), "{}");
    }

    public static String toString(final Object array, final String stringIfNull) {
        if (refId(java.lang.Object.class).eval(2535) == null) {
            return refId(java.lang.String.class).eval(2536);
        }
        return new ToStringBuilder(refId(java.lang.Object.class).eval(2538), ToStringStyle.SIMPLE_STYLE).append(refId(java.lang.Object.class).eval(2537)).toString();
    }

    public static String[] toStringArray(final Object[] array) {
        if (refId(java.lang.Object[].class).eval(2539) == null) {
            return null;
        }
        if (array.length == (int) intVal().eval(2540)) {
            return refId(java.lang.String[].class).eval(2541);
        }
        final String[] result = new String[array.length];
        int _jitmagicLoopLimiter115 = 0;
        for (int i = (int) intVal().eval(2544); (int) intId().eval(2543) < array.length && _jitmagicLoopLimiter115++ < 1000; i++) {
            result[i] = refArrAccessExp(java.lang.Object.class, java.lang.Object[].class, refId(java.lang.Object[].class), intId()).eval(2542).toString();
        }
        return refId(java.lang.String[].class).eval(2545);
    }

    public static String[] toStringArray(final Object[] array, final String valueForNullElements) {
        if (null == refId(java.lang.Object[].class).eval(2546)) {
            return null;
        }
        if (array.length == (int) intVal().eval(2547)) {
            return refId(java.lang.String[].class).eval(2548);
        }
        final String[] result = new String[array.length];
        int _jitmagicLoopLimiter116 = 0;
        for (int i = (int) intVal().eval(2554); (int) intId().eval(2553) < array.length && _jitmagicLoopLimiter116++ < 1000; i++) {
            final Object object = refArrAccessExp(java.lang.Object.class, java.lang.Object[].class, refId(java.lang.Object[].class), intId()).eval(2549);
            result[i] = (refId(java.lang.Object.class).eval(2550) == null ? refId(java.lang.String.class).eval(2552) : refId(java.lang.Object.class).eval(2551).toString());
        }
        return refId(java.lang.String[].class).eval(2555);
    }

    public ArrayUtils() {
    }

    @jattack.annotation.Arguments
    public static Object[] _jitmagicArgs() throws Throwable {
        float[] floatArray0 = org.apache.commons.lang3.ArrayUtils.EMPTY_FLOAT_ARRAY;
        return new Object[] { floatArray0, (int) (byte) 111, 65 };
    }

    public static long main0(String[] args) {
        org.csutil.Helper.parseArgs(args);
        int N = Math.min(org.csutil.Helper.nItrs, 100000);
        WrappedChecksum cs = new WrappedChecksum(true);
        try {
            Object[] eArgs = _jitmagicArgs();
            cs.update(eArgs);
            float[] eArg1 = (float[]) eArgs[0];
            int eArg2 = (int) eArgs[1];
            int eArg3 = (int) eArgs[2];
            for (int i = 0; i < N; ++i) {
                try {
                    cs.update(subarray(eArg1, eArg2, eArg3));
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
        cs.updateStaticFieldsOfClass(ArrayUtils.class);
        return cs.getValue();
    }

    public static void main(String[] args) {
        org.csutil.Helper.write(main0(args));
    }
}
