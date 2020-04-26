/*
 * Copyright (C), 2015-2018
 * FileName: Demo3
 * Author:   DANTE FUNG
 * Date:     2020/4/24 10:05
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 * DANTE FUNG        2020/4/24 10:05   V1.0.0
 */
package com.dantefung.java8.time;

import java.time.LocalDate;

/**
 * @Title: Demo3
 * @Description: Java8 中处理特定的日期
 * @author DANTE FUNG
 * @date 2020/4/24 10:05
 */
public class Demo3 {

	public static void main(String[] args) {
		LocalDate date = LocalDate.of(2020, 4, 24);
		System.out.println("自定义日期:" + date);
	}
}
