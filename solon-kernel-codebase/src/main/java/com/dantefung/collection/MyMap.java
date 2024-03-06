package com.dantefung.collection;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Data;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.cglib.beans.BeanMap;

import java.util.HashMap;

/**
 * @author fenghaolin
 * @date 2024/03/06 10/34
 * @since JDK1.8
 */
@Data
@Slf4j
public class MyMap extends HashMap {

	private String a;
	private String b;
	private String c;
	private String d;

	@SneakyThrows
	public static void main(String[] args) {
		MyMap myMap = new MyMap();
		myMap.setA("1");
		myMap.setB("2");
		myMap.setC("3");
		myMap.setD("4");
		myMap.put("x", "11");
		myMap.put("y", "22");
		myMap.put("z", "33");
		BeanMap beanMap = BeanMap.create(myMap);
		System.out.println("describe: "+ beanMap);
		beanMap.forEach((k,v)-> {
			if (!StringUtils.equalsIgnoreCase(String.valueOf(k), "empty")) {
				myMap.put(k, v);
			}
		});
		System.out.println(objectToString(myMap));
	}

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
}
