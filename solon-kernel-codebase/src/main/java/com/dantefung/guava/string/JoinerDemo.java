/*
 * Copyright (C), 2015-2020
 * FileName: JoinerDemo
 * Author:   DANTE FUNG
 * Date:     2023/2/20 11:47 PM
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 * DANTE FUNG        2023/2/20 11:47 PM   V1.0.0
 */
package com.dantefung.guava.string;

import com.google.common.base.Joiner;

import java.util.HashMap;
import java.util.Map;

/**
 * @Title: JoinerDemo
 * @Description:
 * @author DANTE FUNG
 * @date 2023/02/20 23/47
 * @since JDK1.8
 */
public class JoinerDemo {

	public static void main(String[] args) {
		String contentId = "1";
		String fieldName = "status";
		Map<String, String> oldParamMap = new HashMap<>(8);
		oldParamMap.put("contentId", String.valueOf(contentId));
		oldParamMap.put("fieldName", fieldName);
		oldParamMap.put("valueType", "old");
		String oldParamStr = Joiner.on("&").useForNull("").withKeyValueSeparator("=").join(oldParamMap);
		System.out.println(oldParamStr);
		// contentId=1&fieldName=status&valueType=old
	}
}
