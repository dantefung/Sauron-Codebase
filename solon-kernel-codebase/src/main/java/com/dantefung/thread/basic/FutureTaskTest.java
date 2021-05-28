package com.dantefung.thread.basic;

import java.util.concurrent.*;

// ⾃定义Callable，与上⾯⼀样
class FutureTaskTest implements Callable<Integer> {

	public static void main(String args[]) throws ExecutionException, InterruptedException {
		// 使⽤
		ExecutorService executor = Executors.newCachedThreadPool();
		// 在很多⾼并发的环境下，有可能Callable和FutureTask会创建多次。
		// FutureTask能 够在⾼并发环境下确保任务只执⾏⼀次。这块有兴趣的同学可以参看FutureTask源 码。
		FutureTask<Integer> futureTask = new FutureTask<>(new FutureTaskTest());
		executor.submit(futureTask);
		System.out.println(futureTask.get());
	}

	@Override
	public Integer call() throws Exception {
		// 模拟计算需要⼀秒
		Thread.sleep(1000);
		return 2;
	}
	/*
	 *  FutureTask的⼏个状态
	 * state可能的状态转变路径如下：
	 * NEW -> COMPLETING -> NORMAL
	 * NEW -> COMPLETING -> EXCEPTIONAL
	 * NEW -> CANCELLED
	 * NEW -> INTERRUPTING -> INTERRUPTED
	 */
}