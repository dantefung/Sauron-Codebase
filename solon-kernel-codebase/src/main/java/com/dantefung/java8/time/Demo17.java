/*
 * Copyright (C), 2015-2018
 * FileName: Demo17
 * Author:   DANTE FUNG
 * Date:     2020/4/26 11:05
 * Description: 在Java8中获取当前的时间戳
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 * DANTE FUNG        2020/4/26 11:05   V1.0.0
 */
package com.dantefung.java8.time;

import java.time.Instant;

/**
 * @Title: Demo17
 * @Description: 在Java8中获取当前的时间戳
 * @author DANTE FUNG
 * @date 2020/4/26 11:05
 */
public class Demo17 {

	public static void main(String[] args) {
		Instant timestamp = Instant.now();
		System.out.println(timestamp);

		/**
		 * 时间戳信息里同时包含了日期和时间，这和 java.util.Date 很像。
		 * 实际上 Instant 类确实等同于 Java 8 之前的 Date类，你可以使用 Date 类和 Instant 类各自的转换方法互相转换，
		 * 例如：Date.from(Instant) 将 Instant 转换成java.util.Date，Date.toInstant() 则是将 Date 类转换成 Instant 类。
		 */
	}
}
