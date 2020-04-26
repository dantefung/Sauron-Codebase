/*
 * Copyright (C), 2015-2018
 * FileName: Demo4
 * Author:   DANTE FUNG
 * Date:     2020/4/24 10:47
 * Description: Java8中判断两个日期是否相等
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 * DANTE FUNG        2020/4/24 10:47   V1.0.0
 */
package com.dantefung.java8.time;

import java.time.LocalDate;

/**
 * @Title: Demo4
 * @Description: Java8中判断两个日期是否相等
 * @author DANTE FUNG
 * @date 2020/4/24 10:47
 */
public class Demo4 {

	public static void main(String[] args) {
		LocalDate date1 = LocalDate.now();
		LocalDate date2 = LocalDate.of(2020, 4, 24);
		System.out.println(date1.equals(date2));
	}
}
