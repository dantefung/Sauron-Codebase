package com.dantefung.reflecttest.template;

import cn.hutool.core.map.MapUtil;
import cn.hutool.core.util.StrUtil;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

public abstract class AbstractKeywordValue implements Serializable {

    private static final long serialVersionUID = 1L;

    private volatile boolean hasParse = false;

    private final Map<String, String> kvMap = new ConcurrentHashMap<>();

	private final Map<String, String> varMap = new ConcurrentHashMap<>();

    public final Map<String, String> getKeywordValueMap() throws IllegalAccessException {
        if (hasParse) {
            return kvMap;
        }
        Class<?> clazz = this.getClass();
        Field[] fields = clazz.getDeclaredFields();
        List<Field> keywordFieldList = Arrays.asList(fields)
                .stream()
                .filter(f -> f.getAnnotation(TemplateKeyword.class) != null)
                .collect(Collectors.toList());

        if (CollectionUtils.isEmpty(keywordFieldList)) {
            return kvMap;
        }
        for (Field field : keywordFieldList) {
            TemplateKeyword keyword = field.getAnnotation(TemplateKeyword.class);
            field.setAccessible(true);
            Object value = field.get(this);
            kvMap.put(keyword.value(), Objects.nonNull(value) ? value.toString() : StringUtils.EMPTY);
        }
        hasParse = true;
        return kvMap;
    }

    public final Map<String, String> getVarMap() throws IllegalAccessException {
		Map<String, String> keywordValueMap = getKeywordValueMap();
		if (MapUtil.isNotEmpty(this.varMap)) {
			return this.varMap;
		}
		this.varMap.putAll(keywordValueMap.entrySet().stream().collect(Collectors.toMap(entry -> {
			String key = StrUtil.removePrefix(entry.getKey(), "${");
			key = StrUtil.removeSuffix(key, "}");
			return key;
		}, Map.Entry::getValue)));
		return this.varMap;
	}


    /**
     * 获取接收消息的公司id
     *
     * @return 返回公司id
     */
    public abstract Long getReceiveCompany();
}
