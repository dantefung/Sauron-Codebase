/*
 * Copyright (C), 2015-2020
 * FileName: Demo3
 * Author:   fenghaolin@zuzuche.com
 * Date:     2022/6/2 17:51
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 * fenghaolin@zuzuche.com        2022/6/2 17:51   V1.0.0
 */
package com.dantefung.java8.stream;

import com.google.common.collect.Lists;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @Title: Demo3
 * @Description:
 * @author DANTE FUNG
 * @date 2022/06/02 17/51
 * @since JDK1.8
 */
public class Demo3 {

	public static void main(String[] args) {
		List<String> alist = Lists.newArrayList("a","b","c");
		List<String> blist = Lists.newArrayList("a","b","d");
		List<String> collect = Stream.of(alist, blist).flatMap(List<String>::stream).distinct()
				.collect(Collectors.toList());
		System.out.println(collect);
	}
}
