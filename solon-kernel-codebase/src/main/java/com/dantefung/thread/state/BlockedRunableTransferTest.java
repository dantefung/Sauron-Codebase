/*
 * Copyright (C), 2015-2020
 * FileName: Blockedest
 * Author:   DANTE FUNG
 * Date:     2021/5/29 上午1:26
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 * DANTE FUNG        2021/5/29 上午1:26   V1.0.0
 */
package com.dantefung.thread.state;

/**
 * @Title: Blockedest
 * @Description:  BLOCKED与RUNNABLE状态的转换
 * @author DANTE FUNG
 * @date 2021/05/29 01/26
 * @since JDK1.8
 */
public class BlockedRunableTransferTest {

	public static void main(String[] args) throws InterruptedException {
		blockedTest();
	}


	public static void blockedTest() throws InterruptedException {
		Thread a = new Thread(new Runnable() {
			@Override
			public void run() {
				testMethod();
			}
		}, "a");
		Thread b = new Thread(new Runnable() {
			@Override
			public void run() {
				testMethod();
			}
		}, "b");
		a.start();
		Thread.sleep(1000L);
		b.start();
		System.out.println(a.getName() + ":" + a.getState()); // 输出？
		System.out.println(b.getName() + ":" + b.getState()); // 输出？
	}

	// 同步⽅法争夺锁
	private static synchronized void testMethod() {
		try {
			Thread.sleep(2000L);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
