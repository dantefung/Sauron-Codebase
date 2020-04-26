/*
 * Copyright (C), 2015-2018
 * FileName: Demo13
 * Author:   DANTE FUNG
 * Date:     2020/4/26 10:44
 * Description: 如何表示信用卡到期这类固定日期，答案就在YearMonth
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 * DANTE FUNG        2020/4/26 10:44   V1.0.0
 */
package com.dantefung.java8.time;

import java.time.Month;
import java.time.YearMonth;

/**
 * @Title: Demo13
 * @Description: 如何表示信用卡到期这类固定日期，答案就在YearMonth
 * @author DANTE FUNG
 * @date 2020/4/26 10:44
 */
public class Demo13 {

	/**
	 * 与 MonthDay检查重复事件的例子相似，
	 * YearMonth是另一个组合类，
	 * 用于表示信用卡到期日、FD到期日、期货期权到期日等。
	 * 还可以用这个类得到 当月共有多少天，
	 * YearMonth实例的lengthOfMonth()方法可以返回当月的天数，
	 * 在判断2月有28天还是29天时非常有用。
	 */

	public static void main(String[] args) {
		YearMonth currentYearMonth = YearMonth.now();
		System.out.printf("Days in month year %s: %d%n", currentYearMonth, currentYearMonth.lengthOfMonth());
		YearMonth creditCardExpiry = YearMonth.of(2018, Month.FEBRUARY);
		System.out.printf("Your credit card expires on %s %n", creditCardExpiry);
	}
}
