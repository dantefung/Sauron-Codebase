package com.dantefung.tool;

import java.lang.reflect.Type;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import org.apache.commons.lang3.ClassUtils;

public class TypeUtil {

    private static final Set<Class<?>> PRIMITIVE_TYPES = new HashSet<>(Arrays.asList(
            int.class, double.class, float.class, long.class, short.class, byte.class, char.class, boolean.class,
            Integer.class, Double.class, Float.class, Long.class, Short.class, Byte.class, Character.class,
            Boolean.class));

    public static boolean isPrimitiveOrWrapperType(Object obj) {
        return PRIMITIVE_TYPES.contains(obj.getClass());
    }

    public static boolean isPrimitiveOrWrapperType0(Object obj) {
        return ClassUtils.isPrimitiveOrWrapper(obj.getClass());
    }

    public static boolean isPrimitiveType(Object obj) {
        return obj.getClass().isPrimitive();
    }

    @SuppressWarnings("unused")
    public static boolean isNumericType(Class<?> type) {
        return type == int.class || type == Integer.class ||
                type == long.class || type == Long.class ||
                type == double.class || type == Double.class ||
                type == float.class || type == Float.class ||
                type == BigDecimal.class;
    }

    public static boolean compareNumericValues(Object oldValue, Object newValue) {
        if (oldValue instanceof BigDecimal && newValue instanceof BigDecimal) {
            return ((BigDecimal) oldValue).compareTo((BigDecimal) newValue) == 0;
        } else if (oldValue instanceof Number && newValue instanceof Number) {
            return ((Number) oldValue).doubleValue() == ((Number) newValue).doubleValue();
        }
        return false;
    }

    public static void main(String[] args) {
        System.out.println(TypeUtil.isPrimitiveOrWrapperType(BigDecimal.ZERO));
        System.out.println(TypeUtil.isPrimitiveOrWrapperType0(Integer.valueOf(1)));
        System.out.println(TypeUtil.isPrimitiveType(String.valueOf("0"))); 
        System.out.println(TypeUtil.isNumericType(BigInteger.class));
    }

}
