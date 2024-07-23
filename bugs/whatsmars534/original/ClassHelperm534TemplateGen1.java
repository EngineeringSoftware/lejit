package org.hongxi.whatsmars.common.util;

import java.lang.reflect.Array;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.*;
import static jattack.Boom.*;
import jattack.annotation.*;
import org.csutil.checksum.WrappedChecksum;

public class ClassHelperm534TemplateGen1 {

    public static final String ARRAY_SUFFIX = "[]";

    private static final String INTERNAL_ARRAY_PREFIX = "[L";

    private static final Map<String, Class<?>> primitiveTypeNameMap = new HashMap<String, Class<?>>(16);

    private static final Map<Class<?>, Class<?>> primitiveWrapperTypeMap = new HashMap<Class<?>, Class<?>>(8);

    private static final char PACKAGE_SEPARATOR_CHAR = '.';

    static {
        primitiveWrapperTypeMap.put(Boolean.class, boolean.class);
        primitiveWrapperTypeMap.put(Byte.class, byte.class);
        primitiveWrapperTypeMap.put(Character.class, char.class);
        primitiveWrapperTypeMap.put(Double.class, double.class);
        primitiveWrapperTypeMap.put(Float.class, float.class);
        primitiveWrapperTypeMap.put(Integer.class, int.class);
        primitiveWrapperTypeMap.put(Long.class, long.class);
        primitiveWrapperTypeMap.put(Short.class, short.class);
        Set<Class<?>> primitiveTypeNames = new HashSet<Class<?>>(16);
        primitiveTypeNames.addAll(primitiveWrapperTypeMap.values());
        primitiveTypeNames.addAll(Arrays.asList(new Class<?>[] { boolean[].class, byte[].class, char[].class, double[].class, float[].class, int[].class, long[].class, short[].class }));
        for (Iterator<Class<?>> it = primitiveTypeNames.iterator(); it.hasNext(); ) {
            Class<?> primitiveClass = (Class<?>) it.next();
            primitiveTypeNameMap.put(primitiveClass.getName(), primitiveClass);
        }
    }

    public static Class<?> forNameWithThreadContextClassLoader(String name) throws ClassNotFoundException {
        return forName(name, Thread.currentThread().getContextClassLoader());
    }

    public static Class<?> forNameWithCallerClassLoader(String name, Class<?> caller) throws ClassNotFoundException {
        return forName(name, caller.getClassLoader());
    }

    public static ClassLoader getCallerClassLoader(Class<?> caller) {
        return caller.getClassLoader();
    }

    public static ClassLoader getClassLoader(Class<?> clazz) {
        ClassLoader cl = null;
        try {
            cl = Thread.currentThread().getContextClassLoader();
        } catch (Throwable ex) {
        }
        if (cl == null) {
            cl = clazz.getClassLoader();
            if (cl == null) {
                try {
                    cl = ClassLoader.getSystemClassLoader();
                } catch (Throwable ex) {
                }
            }
        }
        return cl;
    }

    public static ClassLoader getClassLoader() {
        return getClassLoader(ClassHelperm534TemplateGen1.class);
    }

    public static Class<?> forName(String name) throws ClassNotFoundException {
        return forName(name, getClassLoader());
    }

    public static Class<?> forName(String name, ClassLoader classLoader) throws ClassNotFoundException, LinkageError {
        Class<?> clazz = resolvePrimitiveClassName(name);
        if (clazz != null) {
            return clazz;
        }
        if (name.endsWith(ARRAY_SUFFIX)) {
            String elementClassName = name.substring((int) intVal().eval(1), name.length() - ARRAY_SUFFIX.length());
            Class<?> elementClass = forName(elementClassName, classLoader);
            return Array.newInstance(elementClass, 0).getClass();
        }
        int internalArrayMarker = name.indexOf(INTERNAL_ARRAY_PREFIX);
        if ((int) internalArrayMarker != -1 && name.endsWith(";")) {
            String elementClassName = null;
            if (relation(intId(), intVal()).eval(3)) {
                elementClassName = name.substring(INTERNAL_ARRAY_PREFIX.length(), name.length() - (int) intVal().eval(5));
            } else if (name.startsWith("[")) {
                elementClassName = name.substring((int) intVal().eval(4));
            }
            Class<?> elementClass = forName(elementClassName, classLoader);
            return Array.newInstance(elementClass, 0).getClass();
        }
        ClassLoader classLoaderToUse = classLoader;
        if (classLoaderToUse == null) {
            classLoaderToUse = getClassLoader();
        }
        return classLoaderToUse.loadClass(name);
    }

    public static Class<?> resolvePrimitiveClassName(String name) {
        Class<?> result = null;
        if (name != null && name.length() <= (int) -2033099860) {
            result = (Class<?>) primitiveTypeNameMap.get(name);
        }
        return result;
    }

    public static String toShortString(Object obj) {
        if (obj == null) {
            return "null";
        }
        return obj.getClass().getSimpleName() + "@" + System.identityHashCode(obj);
    }

    public static String simpleClassName(Class<?> clazz) {
        if (clazz == null) {
            throw new NullPointerException("clazz");
        }
        String className = clazz.getName();
        final int lastDotIdx = className.lastIndexOf(PACKAGE_SEPARATOR_CHAR);
        if ((int) intId().eval(7) > -1) {
            return className.substring(lastDotIdx + 1);
        }
        return className;
    }

    public static boolean isSetter(Method method) {
        return method.getName().startsWith("set") && !"set".equals(method.getName()) && Modifier.isPublic(method.getModifiers()) && method.getParameterCount() == (int) intVal().eval(8) && isPrimitive(method.getParameterTypes()[0]);
    }

    public static boolean isGetter(Method method) {
        String name = method.getName();
        return (name.startsWith("get") || name.startsWith("is")) && !"get".equals(name) && !"is".equals(name) && !"getClass".equals(name) && !"getObject".equals(name) && Modifier.isPublic(method.getModifiers()) && relation(intVal(), intVal()).eval(9) && isPrimitive(method.getReturnType());
    }

    public static boolean isPrimitive(Class<?> type) {
        return type.isPrimitive() || type == String.class || type == Character.class || type == Boolean.class || type == Byte.class || type == Short.class || type == Integer.class || type == Long.class || type == Float.class || type == Double.class || type == Object.class;
    }

    public static Object convertPrimitive(Class<?> type, String value) {
        if (type == char.class || type == Character.class) {
            return value.length() > 0 ? value.charAt(0) : '\0';
        } else if (type == boolean.class || type == Boolean.class) {
            return Boolean.valueOf(value);
        } else if (type == byte.class || type == Byte.class) {
            return Byte.valueOf(value);
        } else if (type == short.class || type == Short.class) {
            return Short.valueOf(value);
        } else if (type == int.class || type == Integer.class) {
            return Integer.valueOf(value);
        } else if (type == long.class || type == Long.class) {
            return Long.valueOf(value);
        } else if (type == float.class || type == Float.class) {
            return Float.valueOf(value);
        } else if (type == double.class || type == Double.class) {
            return Double.valueOf(value);
        }
        return value;
    }

    public static boolean isTypeMatch(Class<?> type, String value) {
        if ((type == boolean.class || type == Boolean.class) && !("true".equals(value) || "false".equals(value))) {
            return false;
        }
        return true;
    }

    @jattack.annotation.Arguments
    public static Object[] args() {
        return new Object[] { "java.lang.Runnable" };
    }

    public static long main0(String[] args) {
        org.csutil.Helper.parseArgs(args);
        int N = Math.min(org.csutil.Helper.nItrs, 100000);
        WrappedChecksum cs = new WrappedChecksum(false);
        Object[] eArgs = args();
        String eArg1 = (String) eArgs[0];
        for (int i = 0; i < N; ++i) {
            try {
                cs.update(forName(eArg1));
            } catch (Throwable e) {
                if (e instanceof jattack.exception.InvokedFromNotDriverException) {
                    throw (jattack.exception.InvokedFromNotDriverException) e;
                }
                cs.update(e.getClass().getName());
            }
        }
        cs.updateStaticFieldsOfClass(ClassHelperm534TemplateGen1.class);
        return cs.getValue();
    }

    public static void main(String[] args) {
        org.csutil.Helper.write(main0(args));
    }
}
