/*
 * Copyright (C), 2015-2018
 * FileName: Demo9
 * Author:   DANTE FUNG
 * Date:     2020/4/24 15:57
 * Description: Java 8计算一年前或一年后的日期
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 * DANTE FUNG        2020/4/24 15:57   V1.0.0
 */
package com.dantefung.java8.time;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

/**
 * @Title: Demo9
 * @Description: Java 8计算一年前或一年后的日期
 * 利用minus()方法计算一年前的日期
 * @author DANTE FUNG
 * @date 2020/4/24 15:57
 */
public class Demo9 {

	public static void main(String[] args) {
		LocalDate today = LocalDate.now();

		LocalDate previousYear = today.minus(1, ChronoUnit.YEARS);
		System.out.println("一年前的日期:" + previousYear);

		LocalDate nextYear = today.plus(1, ChronoUnit.YEARS);
		System.out.println("一年后的日期:" + nextYear);
	}
}
