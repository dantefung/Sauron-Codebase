package com.dantefung.jmh;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.util.Map;

@Slf4j
public class JacksonUtils {

    private static ObjectMapper objectMapper = new ObjectMapper();

    /**
     * 将对象转为string
     */
    public static <T> String objectToString(T object) {
        if (object == null) {
            return null;
        }

        try {
            return object instanceof String
                    ? (String) object
                    : objectMapper.findAndRegisterModules().writeValueAsString(object);
        } catch (Exception e) {
        	log.error(e.getMessage(), e);
            return null;
        }
    }

    /**
     * 将对象转为json，并格式化的输出
     */
    public static <T> String objectToStringPretty(T object) {
        if (object == null) {
            return null;
        }

        try {
            return object instanceof String
                    ? (String) object
                    : objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(object);
        } catch (Exception e) {
			log.error(e.getMessage(), e);
			return null;
        }
    }

    /**
     * 将json转为java对象
     */
    public static <T> T stringToObject(String str, Class<T> clazz) {
        if (StringUtils.isEmpty(str) || clazz == null) {
            return null;
        }

        try {
            return clazz.equals(String.class) ? (T) str : objectMapper.readValue(str, clazz);
        } catch (Exception e) {
			log.error(e.getMessage(), e);
			return null;
        }
    }

    public static <T> T stringToObject(String str, TypeReference<T> typeReference) {
        if (StringUtils.isEmpty(str) || typeReference == null) {
            return null;
        }

        try {
            return (T)
                    (typeReference.getType().equals(String.class)
                            ? str
                            : objectMapper.readValue(str, typeReference));
        } catch (Exception e) {
			log.error(e.getMessage(), e);
			return null;
        }
    }

    /**
     * 与上面的不同点是: 不try，有异常直接抛出
     */
    public static <T> T toObject(String str, TypeReference<T> typeReference) throws JsonProcessingException {
        return (T)
                (typeReference.getType().equals(String.class)
                        ? str
                        : objectMapper.readValue(str, typeReference));
    }

    public static <T> T stringToObject(
            String str, Class<?> collectionClass, Class<?>... elementClasses) {
        JavaType javaType =
                objectMapper
                        .getTypeFactory()
                        .constructParametricType(collectionClass, elementClasses);
        try {
            return objectMapper.readValue(str, javaType);
        } catch (Exception e) {
			log.error(e.getMessage(), e);
			return null;
        }
    }

    public static <T> T mapToObject(Map<String, Object> map, Class<T> clazz) {
        try {
            return objectMapper.convertValue(map, clazz);
        } catch (IllegalArgumentException e) {
            log.error(e.getMessage(), e);
            return null;
        }
    }

    /**
     * 注释摘自com.fasterxml.jackson.databind.ObjectMapper#convertValue的javadoc
     * 此方法在功能上类似于首先将给定值序列化为 JSON，然后将 JSON 数据绑定到给定类型的值，但应该更有效，
     * 因为不需要（不需要）发生完全序列化。
     * 但是，相同的转换器（序列化器、反序列化器）将用于数据绑定，这意味着相同的对象映射器配置可以工作。
     * @param obj
     * @param typeReference
     * @param <T>
     * @return
     */
    public static <T> T objectToObject(Object obj, TypeReference<T> typeReference) {
        try {
            return objectMapper.convertValue(obj, typeReference);
        } catch (IllegalArgumentException e) {
            log.error(e.getMessage(), e);
            return null;
        }
    }

}
