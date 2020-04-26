/*
 * Copyright (C), 2015-2018
 * FileName: Demo14
 * Author:   DANTE FUNG
 * Date:     2020/4/26 10:50
 * Description: 如何在 Java 8 中检查闰年
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 * DANTE FUNG        2020/4/26 10:50   V1.0.0
 */
package com.dantefung.java8.time;

import java.time.LocalDate;

/**
 * @Title: Demo14
 * @Description: 如何在 Java 8 中检查闰年
 * @author DANTE FUNG
 * @date 2020/4/26 10:50
 */
public class Demo14 {

	public static void main(String[] args) {
		LocalDate today = LocalDate.now();
		if(today.isLeapYear()){
			System.out.println("This year is Leap year");
		}else {
			System.out.println("2018 is not a Leap year");
		}

	}
}
