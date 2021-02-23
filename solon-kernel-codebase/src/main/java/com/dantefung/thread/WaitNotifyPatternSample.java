/*
 * Copyright (C), 2015-2020
 * FileName: WaitNotifyPatternSample
 * Author:   DANTE FUNG
 * Date:     2021/2/23 11:38 下午
 * Description: 等待通知机制入门
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 * DANTE FUNG        2021/2/23 11:38 下午   V1.0.0
 */
package com.dantefung.thread;

/**
 * @Title: WaitNotifyPatternSample
 * @Description: 等待通知机制入门
 * @author DANTE FUNG
 * @date 2021/02/23 23/38
 * @since JDK1.8
 */
public class WaitNotifyPatternSample {

	private static final int N = 3;
	private int count = 0;
	private int finishCount = 0;

	public void doSomething() {
		for (int i = 0; i < 1000_000; i++) {
			synchronized (this) {
				count ++;
			}
		}
		synchronized (this) {
			finishCount++;
			notify();
		}
	}

	public static void main(String[] args) {
		WaitNotifyPatternSample test = new WaitNotifyPatternSample();
		for (int i = 0; i < N; i++) {
			Runnable runnable = () -> test.doSomething();
			new Thread(runnable).start();
		}

		// TODO:
		synchronized (test) {
			while (test.finishCount != N) {
				try {
					// 放在while中，防止中断跟虚假唤醒
					// Perform action appropriate to condition
					// wait()方法强制当前线程释放对象锁。这意味着在调用某对象的wait()方法之前，当前线程必须已经获得该对象的锁。
					test.wait();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}

		System.out.println(test.count);
	}
}
