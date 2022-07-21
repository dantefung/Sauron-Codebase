/*
 * Copyright (C), 2015-2020
 * FileName: Demo1
 * Author:   fenghaolin@zuzuche.com
 * Date:     2022/7/20 11:33
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 * fenghaolin@zuzuche.com        2022/7/20 11:33   V1.0.0
 */
package com.dantefung.concurrent.completablefuture;

import com.dantefung.tool.threadpool.MyThreadPool;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * @Title: Demo1
 * @Description:
 * @author DANTE FUNG
 * @date 2022/07/20 11/33
 * @since JDK1.8
 */
public class Demo1 {


	public static void main(String[] args) {
//		testNoTryCatch();
		testWithTryCatch();
	}



	private static void testNoTryCatch() {
		Integer corePoolSize = 1;
		Integer maximumPoolSize = 1;
		Integer queueCapacity = 1;
		MyThreadPool threadPool = new MyThreadPool(corePoolSize, maximumPoolSize, 60, "threadPool",
				new LinkedBlockingQueue<>(queueCapacity));

		CompletableFuture<Void> future1 = null;
		CompletableFuture<Void> future2 = null;
		CompletableFuture<Void> future3 = null;
		CompletableFuture<Void> future4 = null;
		CompletableFuture<Void> future5 = null;
		future1 = CompletableFuture.runAsync(() -> {
			try {
				Thread.sleep(10);
			} catch (InterruptedException e) {
				e.printStackTrace();
				throw new RuntimeException();
			}
			System.out.println(">>>>>>>1");
		}, threadPool);
		System.out.println(">>>>>>>提交1");

		future2 = CompletableFuture.runAsync(() -> {
			try {
				Thread.sleep(10);
			} catch (InterruptedException e) {
				e.printStackTrace();
				throw new RuntimeException();
			}
			System.out.println(">>>>>>>2");
		}, threadPool);
		System.out.println(">>>>>>>提交2");

		future3 = CompletableFuture.runAsync(() -> {
			try {
				Thread.sleep(10);
			} catch (InterruptedException e) {
				e.printStackTrace();
				throw new RuntimeException();
			}
			System.out.println(">>>>>>>3");
		}, threadPool);
		System.out.println(">>>>>>>提交3");

		future4 = CompletableFuture.runAsync(() -> {
			System.out.println(">>>>>>>4");
			//throw new RuntimeException();
		}, threadPool);
		System.out.println(">>>>>>>提交4");

		future5 = CompletableFuture.supplyAsync(() -> {
			System.out.println(">>>>>>>5");
			throw new RuntimeException("异常5");
		}, threadPool);
		System.out.println(">>>>>>>提交5");

		CompletableFuture.allOf(future1, future2, future3, future4, future5).join();
	}

	private static void testWithTryCatch() {
		Integer corePoolSize = 1;
		Integer maximumPoolSize = 1;
		Integer queueCapacity = 1;
		MyThreadPool threadPool = new MyThreadPool(corePoolSize, maximumPoolSize, 60, "threadPool",
				new LinkedBlockingQueue<>(queueCapacity));

		CompletableFuture<Void> future1 = null;
		CompletableFuture<Void> future2 = null;
		CompletableFuture<Void> future3 = null;
		CompletableFuture<Void> future4 = null;
		CompletableFuture<Void> future5 = null;
		try {
			future1 = CompletableFuture.runAsync(() -> {
				try {
					Thread.sleep(10);
				} catch (InterruptedException e) {
					e.printStackTrace();
					throw new RuntimeException();
				}
				System.out.println(">>>>>>>1");
			}, threadPool);
			System.out.println(">>>>>>>提交1");

			future2 = CompletableFuture.runAsync(() -> {
				try {
					Thread.sleep(10);
				} catch (InterruptedException e) {
					e.printStackTrace();
					throw new RuntimeException();
				}
				System.out.println(">>>>>>>2");
			}, threadPool);
			System.out.println(">>>>>>>提交2");

			future3 = CompletableFuture.runAsync(() -> {
				try {
					Thread.sleep(10);
				} catch (InterruptedException e) {
					e.printStackTrace();
					throw new RuntimeException();
				}
				System.out.println(">>>>>>>3");
			}, threadPool);
			System.out.println(">>>>>>>提交3");


			future4 = CompletableFuture.runAsync(() -> {
				System.out.println(">>>>>>>4");
				//throw new RuntimeException();
			}, threadPool);
			System.out.println(">>>>>>>提交4");


			future5 = CompletableFuture.supplyAsync(() -> {
				System.out.println(">>>>>>>5");
				throw new RuntimeException("异常5");
			}, threadPool);
		} catch (Exception ex) {
			// exception.printStackTrace();
			throw new RuntimeException(ex.getMessage(), ex);
		} finally {
			threadPool.shutdownNow();// 这里是因为在主线程测试，所以要关掉。实际业务场景不用加这段代码
		}
		System.out.println(">>>>>>>提交5");


		CompletableFuture.allOf(future1, future2, future3, future4, future5).join();
	}
}
