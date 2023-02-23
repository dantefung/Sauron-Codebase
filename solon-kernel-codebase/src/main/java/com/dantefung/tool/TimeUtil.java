/*
 * Copyright (C), 2015-2020
 * FileName: TimeUtil
 * Author:   fenghaolin@zuzuche.com
 * Date:     2022/10/27 1:16
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 * fenghaolin@zuzuche.com        2022/10/27 1:16   V1.0.0
 */
package com.dantefung.tool;

/**
 * @Title: TimeUtil
 * @Description:
 * @author fenghaolin
 * @date 2022/10/27 01/16
 * @since JDK1.8
 */
public class TimeUtil {

	private static int ONE_MINUTE_SECONDS = 60;
	private static int ONE_HOUR_SECONDS = 60 * 60;


	public static void main(String[] args) {
		int ONE_MINUTE_SECONDS = 60;
		int ONE_HOUR_SECONDS = 60 * 60;
		int seconds = 12425;
		int i = 3*ONE_HOUR_SECONDS+27*ONE_MINUTE_SECONDS+05;
		System.out.println(i);
		int convertHours = seconds / ONE_HOUR_SECONDS;
		int convertMinutese = seconds % ONE_HOUR_SECONDS / ONE_MINUTE_SECONDS;
		int convertSeconds = seconds % ONE_HOUR_SECONDS % ONE_MINUTE_SECONDS /*% ONE_MINUTE_SECONDS*/;
		System.out.println(timeFormat(convertHours, convertMinutese, convertSeconds));
		System.out.println(secondsToFormat(seconds));
	}

	/**
	 *
	 * @param seconds 秒数
	 * @return 返回格式如 “03:27:05” 的字符串
	 */
	public static String secondsToFormat(int seconds) {
		int convertHours = seconds / ONE_HOUR_SECONDS;
		int convertMinutese = seconds % ONE_HOUR_SECONDS / ONE_MINUTE_SECONDS;
		int convertSeconds = seconds % ONE_HOUR_SECONDS % ONE_MINUTE_SECONDS /*% ONE_MINUTE_SECONDS*/;
		return timeFormat(convertHours, convertMinutese, convertSeconds);
	}


	public static String timeFormat(int... params) {
		StringBuilder sbd = new StringBuilder();
		for (int i = 0, length = params.length; i < length; i++) {
			if (params[i] < 10) { sbd.append("0").append(params[i]).append(":"); } else {
				sbd.append(params[i]).append(":");
			}
		}
		return sbd.deleteCharAt(sbd.length() - 1).toString();
	}
}
