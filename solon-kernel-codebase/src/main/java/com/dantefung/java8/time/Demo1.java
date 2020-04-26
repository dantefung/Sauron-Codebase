/*
 * Copyright (C), 2015-2018
 * FileName: Demo1
 * Author:   DANTE FUNG
 * Date:     2020/4/24 10:01
 * Description: Java8中获取今天的日期
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 * DANTE FUNG        2020/4/24 10:01   V1.0.0
 */
package com.dantefung.java8.time;

import java.time.LocalDate;

/**
 * @Title: Demo1
 * @Description: Java8中获取今天的日期
 * @author DANTE FUNG
 * @date 2020/4/24 10:01
 */
public class Demo1 {

	public static void main(String[] args) {
		LocalDate now = LocalDate.now();
		System.out.println(now);
	}
}
