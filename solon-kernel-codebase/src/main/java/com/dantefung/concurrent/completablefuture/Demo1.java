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
		Integer corePoolSize = 1;
		Integer maximumPoolSize = 1;
		Integer queueCapacity = 1;
		MyThreadPool threadPool = new MyThreadPool(corePoolSize, maximumPoolSize, 60, "threadPool",
				new LinkedBlockingQueue<>(queueCapacity));
		CompletableFuture<Void> future1 = CompletableFuture.runAsync(() -> {
			try {
				Thread.sleep(10);
			} catch (InterruptedException e) {
				e.printStackTrace();
				throw new RuntimeException();
			}
			System.out.println(">>>>>>>1");
		}, threadPool);
		CompletableFuture<Void> future2 = CompletableFuture.runAsync(() -> {
			try {
				Thread.sleep(10);
			} catch (InterruptedException e) {
				e.printStackTrace();
				throw new RuntimeException();
			}
			System.out.println(">>>>>>>2");
		}, threadPool);
		CompletableFuture<Void> future3 = CompletableFuture.runAsync(() -> {
			try {
				Thread.sleep(10);
			} catch (InterruptedException e) {
				e.printStackTrace();
				throw new RuntimeException();
			}
			System.out.println(">>>>>>>3");
		}, threadPool);
		CompletableFuture<Void> future4 = CompletableFuture.runAsync(() -> {
			System.out.println(">>>>>>>4");
			//throw new RuntimeException();
		}, threadPool);
		CompletableFuture<Void> future5 = CompletableFuture.supplyAsync(() -> {
			System.out.println(">>>>>>>5");
			throw new RuntimeException("异常5");
		}, threadPool);
		CompletableFuture.allOf(future1, future2, future3, future4, future5).join();
	}
}
