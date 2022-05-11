/*
 * Copyright (C), 2015-2020
 * FileName: Demo2
 * Author:   fenghaolin@zuzuche.com
 * Date:     2022/5/11 11:00
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 * fenghaolin@zuzuche.com        2022/5/11 11:00   V1.0.0
 */
package com.dantefung.java8.stream;

import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @Title: Demo2
 * @Description: 合并两个列表并去重
 * @author DANTE FUNG
 * @date 2022/05/11 11/00
 * @since JDK1.8
 */
@Slf4j
public class Demo2 {

	public static void main(String[] args) {
		List<String> result = Stream.of(Lists.newArrayList("A", "B", "C"), Lists.newArrayList("A", "B"))
				.flatMap(Collection::stream).distinct().collect(Collectors.toList());
		log.info(result.toString());
	}
}
