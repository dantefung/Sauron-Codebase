/*
 * Copyright (C), 2015-2018
 * FileName: Demo2
 * Author:   DANTE FUNG
 * Date:     2020/4/24 10:02
 * Description: Java8中获取年、月、日信息
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 * DANTE FUNG        2020/4/24 10:02   V1.0.0
 */
package com.dantefung.java8.time;

import java.time.LocalDate;

/**
 * @Title: Demo2
 * @Description: Java8中获取年、月、日信息
 * @author DANTE FUNG
 * @date 2020/4/24 10:02
 */
public class Demo2 {

	public static void main(String[] args) {
		LocalDate now = LocalDate.now();
		int year = now.getYear();
		int monthValue = now.getMonthValue();
		int day = now.getDayOfMonth();
		System.out.println("year:" + year);
		System.out.println("month:" + monthValue);
		System.out.println("day:" + day);
	}
}
