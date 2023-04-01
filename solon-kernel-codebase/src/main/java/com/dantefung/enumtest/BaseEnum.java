package com.dantefung.enumtest;

import cn.hutool.core.lang.Pair;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

import java.util.List;
import java.util.Map;

/**
 * The interface Base enum.
 *
 * @param <C> the type parameter
 */
public interface BaseEnum<C> {

    C code();

    /**
     * 功能：Parse.<br>
     * 详细：
     *
     * @param <T>      the type parameter
     * @param enumType the enum type
     * @param code     the code
     * @return the t
     */
    static <T extends BaseEnum<?>> T parse(Class<T> enumType, Object code) {
        T[] enums = enumType.getEnumConstants();
        for (T t : enums) {
            if (String.valueOf(t.code()).equals(String.valueOf(code))) {
                return t;
            }
        }
        return null;
    }


    /**
     * 功能：Parse带默认值.<br>
     * 详细：
     *
     * @param <T>          the type parameter
     * @param enumType     the enum type
     * @param code         the code
     * @param defaultValue the default value
     * @return the t
     */
    static <T extends BaseEnum<?>> T parse(Class<T> enumType, Object code, T defaultValue) {
        T[] enums = enumType.getEnumConstants();
        for (T t : enums) {
            if (String.valueOf(t.code()).equals(String.valueOf(code))) {
                return t;
            }
        }
        return defaultValue;
    }

    /**
     * 功能：生成swagger注释.<br>
     * 详细：
     *
     * @param <T>      the type parameter
     * @param enumType the enum type
     * @return the t
     */
    static <T extends BaseEnum<?>> String swagger(Class<T> enumType) {
        StringBuilder stringBuilder=new StringBuilder("{");
        T[] enums = enumType.getEnumConstants();
        int i=0;
        for (T t : enums) {
            if(i==0){
                stringBuilder.append(t.code()).append(":").append(t.toString());
            }else{
                stringBuilder.append(",").append(t.code()).append(":").append(t.toString());

            }
            i++;
        }
        stringBuilder.append("}");
        return stringBuilder.toString();
    }


    /**
     * 功能： Get item list.<br>
     * 详细：
     *
     * @param <T>      the type parameter
     * @param enumType the enum type
     * @return the item list
     */
    public static <T extends BaseEnum<?>> Map<String, Object> getItemList(Class<T> enumType) {
        T[] enums = enumType.getEnumConstants();
        Map<String, Object> itemMap = Maps.newHashMap();

        for (T t : enums) {

            itemMap.put(t.code() + "", t.toString());
        }
        return itemMap;
    }

    /**
     * 功能： Get key value list.<br>
     * 详细：
     *
     * @param <T>      the type parameter
     * @param enumType the enum type
     * @return the key value list
     */
    static <T extends BaseEnum<?>> List<Pair> getKeyValueList(Class<T> enumType) {
        T[] enums = enumType.getEnumConstants();
        List<Pair> keyValues = Lists.newArrayList();

        for (T t : enums) {

            keyValues.add(new Pair(t.code(), t.toString()));
        }
        return keyValues;
    }
}