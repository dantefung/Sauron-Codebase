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
import lombok.Data;
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

		OrderListIndexDO o1 = new OrderListIndexDO();
		o1.setId(1L);
		OrderListIndexDO o2 = new OrderListIndexDO();
		o2.setId(2L);

		OrderListIndexDO o3 = new OrderListIndexDO();
		o3.setId(3L);
		OrderListIndexDO o4 = new OrderListIndexDO();
		o4.setId(1L);
		List<OrderListIndexDO> sortList = Lists.newArrayList(o1, o2);
		List<OrderListIndexDO> noSortList = Lists.newArrayList(o3, o4);
		List<?> r = Stream.of(sortList, noSortList)
				.flatMap(Collection::stream).distinct().collect(Collectors.toList());
		System.out.println(r);
	}

	@Data
	static class OrderListIndexDO {
		private Long id;
	}
}
