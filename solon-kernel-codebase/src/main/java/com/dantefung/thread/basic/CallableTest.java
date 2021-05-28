/*
 * Copyright (C), 2015-2020
 * FileName: CallableTest
 * Author:   DANTE FUNG
 * Date:     2021/5/28 15:41
 * Description: ⾃定义Callable
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 * DANTE FUNG        2021/5/28 15:41   V1.0.0
 */
package com.dantefung.thread.basic;

import java.util.concurrent.*;

/**
 * @Title: CallableTest
 * @Description: ⾃定义Callable
 * @author DANTE FUNG
 * @date 2021/05/28 15/41
 * @since JDK1.8
 */
public class CallableTest implements Callable<Integer> {

	@Override
	public Integer call() throws Exception {
		// 模拟计算需要⼀秒
		Thread.sleep(1000);
		return 2;
	}

	public static void main(String args[]) throws ExecutionException, InterruptedException {
		// 使⽤
		ExecutorService executor = Executors.newCachedThreadPool();
		CallableTest task = new CallableTest();
		Future<Integer> result = executor.submit(task);
		// 注意调⽤get⽅法会阻塞当前线程，直到得到结果。
		// 所以实际编码中建议使⽤可以设置超时时间的重载get⽅法。
		System.out.println(result.get());
	}
}
