/*
 * Copyright (C), 2015-2018
 * FileName: Demo12
 * Author:   DANTE FUNG
 * Date:     2020/4/24 16:07
 * Description: Java 8中处理时区
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 * DANTE FUNG        2020/4/24 16:07   V1.0.0
 */
package com.dantefung.java8.time;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;

/**
 * @Title: Demo12
 * @Description: Java 8中处理时区
 * @author DANTE FUNG
 * @date 2020/4/24 16:07
 */
public class Demo12 {

	public static void main(String[] args) {
		/**
		 * Java 8不仅分离了日期和时间，也把时区分离出来了。
		 * 现在有一系列单独的类如ZoneId来处理特定时区，
		 * ZoneDateTime类来表示某时区下的时间。
		 * 这在Java 8以前都是 GregorianCalendar类来做的。
		 * 下面这个例子展示了如何把本时区的时间转换成另一个时区的时间。
		 */
		// Date and time with timezone in Java8
		ZoneId america = ZoneId.of("America/New_York");
		LocalDateTime localDateAndTime = LocalDateTime.now();
		ZonedDateTime dateAndTimeInNewYork  = ZonedDateTime.of(localDateAndTime, america );
		System.out.println("Current date and time in a particular timezone : " + dateAndTimeInNewYork);
	}
}
