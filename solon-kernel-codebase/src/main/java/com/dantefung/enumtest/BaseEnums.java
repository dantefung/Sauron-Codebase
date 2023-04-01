package com.dantefung.enumtest;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public interface BaseEnums<K, V> {

    /**
     * 获取枚举标识
     *
     * @return
     */
    K getCode();

    /**
     * 获取枚举描述
     *
     * @return
     */
    V getDesc();

    /**
     * 通过枚举类型和code值获取对应的枚举类型
     *
     * @param enumType
     * @param code
     * @param <T>
     * @return
     */
    static <T extends BaseEnums> T valueOf(Class<? extends BaseEnums> enumType, Integer code) {
        if (enumType == null || code == null) {
            return null;
        }
        T[] enumConstants = (T[]) enumType.getEnumConstants();
        if (enumConstants == null) {
            return null;
        }
        for (T enumConstant : enumConstants) {
            Object enumCode = enumConstant.getCode();
            if (code.equals(enumCode)) {
                return enumConstant;
            }
        }
        return null;
    }

    /**
     * 将enum转换为list
     *
     * @param enumType
     * @param <T>
     * @return
     */
    static <T extends BaseEnums> List<Map<String, Object>> enum2List(Class<? extends BaseEnums> enumType) {
        if (enumType == null) {
            return null;
        }
        T[] enumConstants = (T[]) enumType.getEnumConstants();
        if (enumConstants == null) {
            return null;
        }
        List<Map<String, Object>> results = new ArrayList<>();
        for (T bean : enumConstants) {
			Object desc = bean.getDesc();
			Object code = bean.getCode();
            HashMap<String, Object> map = new HashMap<>();
            map.put("code", code);
            map.put("desc", desc);
            results.add(map);
        }
        return results;
    }
}