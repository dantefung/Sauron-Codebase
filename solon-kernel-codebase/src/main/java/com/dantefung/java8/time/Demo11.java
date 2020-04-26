/*
 * Copyright (C), 2015-2018
 * FileName: Demo11
 * Author:   DANTE FUNG
 * Date:     2020/4/24 16:03
 * Description: 如何用Java判断日期是早于还是晚于另一个日期
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 * DANTE FUNG        2020/4/24 16:03   V1.0.0
 */
package com.dantefung.java8.time;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

/**
 * @Title: Demo11
 * @Description: 如何用Java判断日期是早于还是晚于另一个日期
 * @author DANTE FUNG
 * @date 2020/4/24 16:03
 */
public class Demo11 {

	public static void main(String[] args) {
		LocalDate today = LocalDate.now();

		LocalDate tomorrow = today.plus(1, ChronoUnit.DAYS);
		if (tomorrow.isAfter(today)) {
			System.out.println("之后的日期:" + tomorrow);
		}

		LocalDate yesterday = today.minus(1, ChronoUnit.DAYS);
		if (yesterday.isBefore(today)) {
			System.out.println("之前的日期:" + yesterday);
		}
	}
}
