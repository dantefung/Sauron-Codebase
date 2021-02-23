/* * Copyright (C), 2015-2020 * FileName: Main
 * Author:   DANTE FUNG
 * Date:     2021/2/23 9:00 下午
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 * DANTE FUNG        2021/2/23 9:00 下午   V1.0.0
 */
package com.dantefung.concurrent.aqs.s03;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @Title: Main
 * @Description:
 * @author DANTE FUNG
 * @date 2021/02/23 21/00
 * @since JDK1.8
 */
public class Main {

	public static int m = 0;

	public static Lock lock = new ReentrantLock();// 底层使用的是AQS

	// java增加很多current lock的内容，在JVM内存，即用户态实现锁.就不需要再再跟内核态打交道.

	public static void main(String[] args) throws InterruptedException {

		Thread[] threads = new Thread[100];

		for (int i = 0; i < threads.length; i++) {
			threads[i] = new Thread(() -> {
				try {
					lock.lock();
					for (int j = 0; j < 100; j++) {
						m++;
					}
				} finally {
					lock.unlock();
				}
			});
		}

		for (Thread t : threads)
			t.start();

		for (Thread t : threads)
			t.join();// 等待所有线程运行结束

		System.out.println(m);
	}
}
