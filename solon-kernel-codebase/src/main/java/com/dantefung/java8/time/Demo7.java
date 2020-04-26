/*
 * Copyright (C), 2015-2018
 * FileName: Demo7
 * Author:   DANTE FUNG
 * Date:     2020/4/24 15:52
 * Description: Java8中获取当前时间
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 * DANTE FUNG        2020/4/24 15:52   V1.0.0
 */
package com.dantefung.java8.time;

import java.time.LocalTime;

/**
 * @Title: Demo7
 * @Description: Java8中获取当前时间
 * 通过增加小时、分、秒来计算将来的时间很常见。
 * Java 8除了不变类型和线程安全的好处之外，
 * 还提供了更好的plusHours()方法替换add()，
 * 并且是兼容的。注意，这些方法返回一个全新的LocalTime实例，
 * 由于其不可变性，返回后一定要用变量赋值。
 * @author DANTE FUNG
 * @date 2020/4/24 15:52
 */
public class Demo7 {

	public static void main(String[] args) {
		LocalTime time = LocalTime.now();
		LocalTime newTime = time.plusHours(3);
		System.out.println("三个小时后的时间为:" + newTime);
	}
}
