/* * Copyright (C), 2015-2020 * FileName: Main
 * Author:   DANTE FUNG
 * Date:     2021/2/23 9:00 下午
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 * DANTE FUNG        2021/2/23 9:00 下午   V1.0.0
 */
package com.dantefung.concurrent.aqs.s05;

import java.util.concurrent.locks.Lock;

/**
 * @Title: Main
 * @Description:
 * @author DANTE FUNG
 * @date 2021/02/23 21/00
 * @since JDK1.8
 */
public class Main {

	public static int m = 0;

	// 这个自定义锁是不生效的
	public static Lock lock = new MLock();

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
