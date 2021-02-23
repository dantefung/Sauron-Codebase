/* * Copyright (C), 2015-2020 * FileName: Main
 * Author:   DANTE FUNG
 * Date:     2021/2/23 9:00 下午
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 * DANTE FUNG        2021/2/23 9:00 下午   V1.0.0
 */
package com.dantefung.concurrent.aqs.s02;

/**
 * @Title: Main
 * @Description:
 * @author DANTE FUNG
 * @date 2021/02/23 21/00
 * @since JDK1.8
 */
public class Main {

	public static int m = 0;

	public static void main(String[] args) throws InterruptedException {

		Thread[] threads = new Thread[100];

		for (int i = 0; i < threads.length; i++) {
			threads[i] = new Thread(() -> {
				// synchronized关键字内部有锁升级的过程
				// 偏向锁->轻量级锁(自旋锁：有条线程自旋了10次)->重量级锁(属于内核态指令，消耗系统资源).
				// jdk1.6后优化了，先偏向锁
				// jdk1.5之前，认为synchronized是重量的,JVM(用户态)--- OS(内核态) 之间的切换比较费时间.
				synchronized (Main.class) {
					for (int j = 0; j < 100; j++) {
						m++;
					}
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
