/*
 * Copyright (C), 2015-2018
 * FileName: Demo16
 * Author:   DANTE FUNG
 * Date:     2020/4/26 11:04
 * Description: 包含时差信息的日期和时间
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 * DANTE FUNG        2020/4/26 11:04   V1.0.0
 */
package com.dantefung.java8.time;

import java.time.LocalDateTime;
import java.time.Month;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;

/**
 * @Title: Demo16
 * @Description: 包含时差信息的日期和时间
 * @author DANTE FUNG
 * @date 2020/4/26 11:04
 */
public class Demo16 {

	public static void main(String[] args) {
		LocalDateTime datetime = LocalDateTime.of(2014, Month.JANUARY, 14,19,30);
		ZoneOffset offset = ZoneOffset.of("+05:30");
		OffsetDateTime date = OffsetDateTime.of(datetime, offset);
		System.out.println("Date and Time with timezone offset in Java : " + date);
	}
}
