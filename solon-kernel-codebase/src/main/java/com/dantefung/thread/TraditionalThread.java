/*
 * Copyright (C), 2015-2020
 * FileName: TraditionalThread
 * Author:   DANTE FUNG
 * Date:     2020/11/24 10:15 下午
 * Description: 传统线程
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 * DANTE FUNG        2020/11/24 10:15 下午   V1.0.0
 */
package com.dantefung.thread;

/**
 * @Title: TraditionalThread
 * @Description: 创建线程的两种传统方式.
 * @author DANTE FUNG
 * @date 2020/11/24 22/15
 * @since JDK1.8
 */
public class TraditionalThread {

	public static void main(String[] args) {

		// 子类复写父类的run方法.
		Thread thread1 = new Thread(){
			@Override
			public void run() {
				while (true) {
					try {
						Thread.sleep(500);
					} catch (Exception exception) {
						exception.printStackTrace();
					}
					System.out.println("1:" + Thread.currentThread().getName());
					System.out.println("2:" + this.getName());
				}
			}
		};

		// 通过传入Runnable方式
		Thread thread2 = new Thread(new Runnable() {
			@Override
			public void run() {
				while (true) {
					try {
						Thread.sleep(500);
					} catch (Exception exception) {
						exception.printStackTrace();
					}
					System.out.println("2:" + Thread.currentThread());
				}
			}
		});

		thread2.start();

		// 父类的run方法会执行Runnable的run方法，详见源码
		Thread thread3 = new Thread(new Runnable() {
			@Override
			public void run() {
				while (true) {
					try {
						Thread.sleep(500);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					System.out.println("runnable1:" + Thread.currentThread());
				}
			}
		}) { // 子类覆盖父类的run方法
			@Override
			public void run() {
				while (true) {
					try {
						Thread.sleep(500);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					System.out.println("runnable2:" + Thread.currentThread());
				}
			}
		};

		thread3.run();

	}
}
