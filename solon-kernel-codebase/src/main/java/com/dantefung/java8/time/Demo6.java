/*
 * Copyright (C), 2015-2018
 * FileName: Demo6
 * Author:   DANTE FUNG
 * Date:     2020/4/24 15:50
 * Description: Java8中获取当前时间
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 * DANTE FUNG        2020/4/24 15:50   V1.0.0
 */
package com.dantefung.java8.time;

import java.time.LocalTime;

/**
 * @Title: Demo6
 * @Description: Java8中获取当前时间
 * @author DANTE FUNG
 * @date 2020/4/24 15:50
 */
public class Demo6 {

	public static void main(String[] args) {
		LocalTime time = LocalTime.now();
		System.out.println("获取当前的时间,不含有日期:" + time);
	}
}
