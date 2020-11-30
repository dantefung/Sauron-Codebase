/*
 * Copyright (C), 2015-2020
 * FileName: CallableAndFutureTest
 * Author:   DANTE FUNG
 * Date:     2020/11/30 11:16 下午
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 * DANTE FUNG        2020/11/30 11:16 下午   V1.0.0
 */
package com.dantefung.concurrent;

import java.util.Random;
import java.util.concurrent.*;

/**
 * @Title: CallableAndFutureTest
 * @Description:
 * @author DANTE FUNG
 * @date 2020/11/30 23/16
 * @since JDK1.8
 */
public class CallableAndFutureTest {

	public static void main(String[] args) throws ExecutionException, InterruptedException, TimeoutException {
		ExecutorService threadPool = Executors.newSingleThreadExecutor();
		Future<String> future = threadPool.submit(new Callable<String>() {
			@Override
			public String call() throws Exception {
				Thread.sleep(2000);
				return "hello!";
			}
		});
		System.out.println("等待结果 ...");
		System.out.println("拿到结果:" + future.get());
		//System.out.println("拿到结果:" + future.get(1, TimeUnit.SECONDS));

		ExecutorService threadPool2 = Executors.newFixedThreadPool(10);
		CompletionService<Integer> completionService = new ExecutorCompletionService<Integer>(threadPool2);
		for (int i = 1; i <= 10; i++) {
			final int seq = i;
			completionService.submit(new Callable<Integer>() {
				@Override
				public Integer call() throws Exception {
					Thread.sleep(new Random().nextInt(5000));
					return seq;
				}
			});
		}

		for (int i = 0; i < 10; i++) {
			System.out.println(completionService.take().get());
		}


	}
}
