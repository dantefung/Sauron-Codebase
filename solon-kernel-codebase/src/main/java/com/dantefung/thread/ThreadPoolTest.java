/*
 * Copyright (C), 2015-2020
 * FileName: ThreadPoolTest
 * Author:   DANTE FUNG
 * Date:     2020/11/30 10:56 下午
 * Description: 线程池测试
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 * DANTE FUNG        2020/11/30 10:56 下午   V1.0.0
 */
package com.dantefung.thread;

import cn.hutool.core.date.DateTime;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * @Title: ThreadPoolTest
 * @Description: 线程池测试
 * @author DANTE FUNG
 * @date 2020/11/30 22/56
 * @since JDK1.8
 */
public class ThreadPoolTest {

	public static void main(String[] args) {
		//ExecutorService threadPool = Executors.newFixedThreadPool(3);
		//ExecutorService threadPool = Executors.newCachedThreadPool();
		// 如何实现线程死后重新启动? 这个单线程池的线程死了，会再开一条新的线程替补
		ExecutorService threadPool = Executors.newSingleThreadExecutor();
		for (int i = 1; i <= 10; i++) {
			final int task = i;
			threadPool.execute(new Runnable() {
				@Override
				public void run() {
					for (int j = 1; j <= 10; j++) {
						try {
							Thread.sleep(20);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
						System.out.println(Thread.currentThread().getName() + " is looping of " + j + " for task of " + task);
					}
				}
			});
		}
		System.out.println("all of 10 tasks have committed!");
		// 所有任务执行完才关闭
		threadPool.shutdown();
//		threadPool.shutdownNow();

		// 10秒以后炸一下
		Executors.newScheduledThreadPool(3).schedule(new Runnable() {
			@Override
			public void run() {
				System.out.println(DateTime.now().second() + " bombing !!");
			}
		},10 , TimeUnit.SECONDS);

		// 6秒后每隔2秒炸一下
		Executors.newScheduledThreadPool(3).scheduleAtFixedRate(new Runnable() {
			@Override
			public void run() {
				System.out.println(DateTime.now().second() + " bombing bombing !!");
			}
		},6 ,2, TimeUnit.SECONDS);
	}
}
