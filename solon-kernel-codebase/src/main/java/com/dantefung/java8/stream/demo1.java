/*
 * Copyright (C), 2015-2020
 * FileName: demo1
 * Author:   DANTE FUNG
 * Date:     2021/4/13 16:29
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 * DANTE FUNG        2021/4/13 16:29   V1.0.0
 */
package com.dantefung.java8.stream;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @Title: 升序/降序/随机数/求和/平均数/最大数/最小数
 * @Description:
 * @author DANTE FUNG
 * @date 2021/04/13 16/29
 * @since JDK1.8
 */
public class demo1 {

	public static void main(String[] args) {
		//对象类型stream排序
		List<User> users = new ArrayList<User>() {
			{
				add(new User("a", "1983/12/24", 36));
				add(new User("a", "1993/12/24", 26));
				add(new User("a", "1973/12/24", 46));
				add(new User("a", "1995/12/24", 24));
				add(new User("a", "1981/12/24", 38));
				add(new User("a", "1991/12/24", 28));
			}
		};

		/*升序*/
		List<User> ascUsers = users.stream().sorted(Comparator.comparing(User::getAge)).collect(Collectors.toList());
		System.out.println("升序：" + ascUsers);
		/*降序*/
		List<User> descUsers = users.stream().sorted(Comparator.comparing(User::getAge).reversed()).collect(Collectors.toList());
		System.out.println("降序：" + descUsers);
		/*随机数*/
		Random random = new Random();
		//random.ints(0,100).limit(10).sorted().forEach(System.out::print);

		random.ints(0, 100).limit(10).sorted().forEach(obj -> {
			System.out.print(obj + ",");
		});
		List<Integer> integers = Arrays.asList(1, 2, 13, 4, 15, 6, 17, 8, 19);
		IntSummaryStatistics stats = integers.stream().mapToInt((x) -> x).summaryStatistics();

		System.out.println("列表中最大的数 : " + stats.getMax());
		System.out.println("列表中最小的数 : " + stats.getMin());
		System.out.println("所有数之和 : " + stats.getSum());
		System.out.println("平均数 : " + stats.getAverage());
		System.out.println("ToString : " + stats.toString());
	}
}
