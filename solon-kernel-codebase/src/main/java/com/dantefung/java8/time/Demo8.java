/*
 * Copyright (C), 2015-2018
 * FileName: Demo8
 * Author:   DANTE FUNG
 * Date:     2020/4/24 15:55
 * Description: Java8计算一周后的日期
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 * DANTE FUNG        2020/4/24 15:55   V1.0.0
 */
package com.dantefung.java8.time;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

/**
 * @Title: Demo8
 * @Description: Java8计算一周后的日期
 * 和上个例子计算3小时以后的时间类似，
 * 这个例子会计算一周后的日期。LocalDate日期不包含时间信息，
 * 它的plus()方法用来增加天、周、月，
 * ChronoUnit类声明了这些时间单位。
 * 由于LocalDate也是不变类型，返回后一定要用变量赋值。
 * @author DANTE FUNG
 * @date 2020/4/24 15:55
 */
public class Demo8 {

	public static void main(String[] args) {
		LocalDate today = LocalDate.now();
		System.out.println("今天的日期为:" + today);
		LocalDate nextWeek = today.plus(1, ChronoUnit.WEEKS);
		System.out.println("一周后的日期为:" + nextWeek);

	}
}
