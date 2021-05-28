/*
 * Copyright (C), 2015-2020
 * FileName: CopyThreadGroupTest
 * Author:   DANTE FUNG
 * Date:     2021/5/28 下午11:17
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 * DANTE FUNG        2021/5/28 下午11:17   V1.0.0
 */
package com.dantefung.thread.basic;

/**
 * @Title: CopyThreadGroupTest
 * @Description:
 * @author DANTE FUNG
 * @date 2021/05/28 23/17
 * @since JDK1.8
 */
public class CopyThreadGroupTest {

	public static void main(String[] args) throws InterruptedException {
		ThreadGroup threadGroup = new ThreadGroup("t1");
		Thread thread = new Thread(threadGroup,"thread");
		thread.start();
		Thread.sleep(1000);
		// 复制⼀个线程数组到⼀个线程组
		Thread[] threads = new Thread[threadGroup.activeCount()];
		ThreadGroup threadGroup2 = new ThreadGroup("t2");
		threadGroup2.enumerate(threads);
		System.out.println(threadGroup2.getName());
		System.out.println(threadGroup2.activeCount());
	}
}
