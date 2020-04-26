/*
 * Copyright (C), 2015-2018
 * FileName: Demo15
 * Author:   DANTE FUNG
 * Date:     2020/4/26 10:54
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 * DANTE FUNG        2020/4/26 10:54   V1.0.0
 */
package com.dantefung.java8.time;

import java.time.*;

/**
 * @Title: Demo15
 * @Description:
 * @author DANTE FUNG
 * @date 2020/4/26 10:54
 */
public class Demo15 {

	public static void main(String[] args) {
		LocalDateTime lastUpdate = LocalDateTime.of(2020, 4, 20, 4, 5, 6);
		Duration duration = Duration.between(lastUpdate, LocalDateTime.now());
		System.out.println(duration.toDays());

		/**
		 * 有一个常见日期操作是计算两个日期之间的天数、周数或月数。
		 * 在 Java 8 中可以用 java.time.Period 类来做计算。
		 * 下面这个例子中，我们计算了当天和将来某一天之间的月数。
		 */
		LocalDate now = LocalDate.now();
		LocalDate date = LocalDate.of(2020, Month.AUGUST, 20);
		Period preiod = Period.between(now, date);
		System.out.println("离下个时间还有" + preiod.getMonths() + "个月!");

	}
}
