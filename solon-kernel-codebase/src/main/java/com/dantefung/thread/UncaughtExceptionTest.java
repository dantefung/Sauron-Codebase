/*
 * Copyright (C), 2015-2020
 * FileName: UncaughtExceptionTest
 * Author:   DANTE FUNG
 * Date:     2021/5/28 下午11:24
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 * DANTE FUNG        2021/5/28 下午11:24   V1.0.0
 */
package com.dantefung.thread;

/**
 * @Title: UncaughtExceptionTest
 * @Description: 线程组统⼀异常处理
 * @author DANTE FUNG
 * @date 2021/05/28 23/24
 * @since JDK1.8
 */
public class UncaughtExceptionTest {

	public static void main(String[] args) {
		ThreadGroup threadGroup1 = new ThreadGroup("group1") {
			// 继承ThreadGroup并重新定义以下⽅法
			// 在线程成员抛出unchecked exception
			// 会执⾏此⽅法
			public void uncaughtException(Thread t, Throwable e) {
				System.out.println(t.getName() + ": " + e.getMessage());
			}
		};
		// 这个线程是threadGroup1的⼀员
		Thread thread1 = new Thread(threadGroup1, new Runnable() {
			public void run() {
				// 抛出unchecked异常
				throw new RuntimeException("测试异常");
			}
		});
		thread1.start();
	}
}
