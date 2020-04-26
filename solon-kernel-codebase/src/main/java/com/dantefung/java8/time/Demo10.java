/*
 * Copyright (C), 2015-2018
 * FileName: Demo10
 * Author:   DANTE FUNG
 * Date:     2020/4/24 16:00
 * Description: Java8的Clock时钟类
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 * DANTE FUNG        2020/4/24 16:00   V1.0.0
 */
package com.dantefung.java8.time;

import java.time.Clock;

/**
 * @Title: Demo10
 * @Description: Java8的Clock时钟类
 * @author DANTE FUNG
 * @date 2020/4/24 16:00
 */
public class Demo10 {

	public static void main(String[] args) {
		/**
		 * Java 8增加了一个Clock时钟类用于获取当时的时间戳，
		 * 或当前时区下的日期时间信息。
		 * 以前用到System.currentTimeInMillis()和TimeZone.getDefault()的地方都可用Clock替换。
		 */
		// Returns the current time based on your system clock and set to UTC.
		Clock clock = Clock.systemUTC();
		System.out.println("Clock:" + clock.millis());

		// Returns time based on system clock zone
		Clock defaultClock = Clock.systemDefaultZone();
		System.out.println("Clock:" + defaultClock.millis());

	}
}
