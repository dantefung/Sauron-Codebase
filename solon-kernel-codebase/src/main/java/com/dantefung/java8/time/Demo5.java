/*
 * Copyright (C), 2015-2018
 * FileName: Demo5
 * Author:   DANTE FUNG
 * Date:     2020/4/24 10:50
 * Description: Java8中检查像生日这种周期性事件
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 * DANTE FUNG        2020/4/24 10:50   V1.0.0
 */
package com.dantefung.java8.time;

import java.time.LocalDate;
import java.time.MonthDay;

/**
 * @Title: Demo5
 * @Description: Java8中检查像生日这种周期性事件
 * @author DANTE FUNG
 * @date 2020/4/24 10:50
 */
public class Demo5 {

	public static void main(String[] args) {
		LocalDate date1 = LocalDate.now();
		LocalDate date2 = LocalDate.of(2018, 2, 6);

		MonthDay birthday = MonthDay.of(date2.getMonth(), date2.getDayOfMonth());
		MonthDay currentMonthDay = MonthDay.from(date1);

		if (currentMonthDay.equals(birthday)) {
			System.out.println("是你的生日!");
		} else {
			System.out.println("你的生日还没有到!");
		}
	}
}
