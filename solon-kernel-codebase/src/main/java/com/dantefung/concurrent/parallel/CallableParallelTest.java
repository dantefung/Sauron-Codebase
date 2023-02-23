/*
 * Copyright (C), 2015-2020
 * FileName: CallableParallelTest
 * Author:   fenghaolin@zuzuche.com
 * Date:     2022/7/21 13:40
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 * fenghaolin@zuzuche.com        2022/7/21 13:40   V1.0.0
 */
package com.dantefung.concurrent.parallel;

import com.google.common.collect.Lists;
import org.apache.commons.lang3.time.StopWatch;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.*;

/**
 * @Title: CallableParallelTest
 * @Description:
 * @author DANTE FUNG
 * @date 2022/07/21 13/40
 * @since JDK1.8
 */
public class CallableParallelTest {

	/**
	 * 干一件不好干的事，使用Callable接口，需要 FutureTask实现类的支持，用于接收运算结果.
	 */
	class DoWork implements Callable<Integer> {

		/**
		 * 需要处理的对象集合，每个线程传递自己的对象.
		 */
		List<String> list;

		public DoWork(List<String> list) {
			this.list = list;
		}

		@Override
		public Integer call() throws Exception {
			for (String s : list) {
				System.out.println(Thread.currentThread().getId() + ":" + s);
			}
			Thread.sleep(3000);
			return 1;
		}
	}

	@Test
	public void doFast() throws InterruptedException, ExecutionException {
		StopWatch stopWatch = new StopWatch();
		stopWatch.start();
		ExecutorService executor = Executors.newFixedThreadPool(2);
		List<DoWork> doWorks = Lists
				.newArrayList(new DoWork(Arrays.asList("a", "b")), new DoWork(Arrays.asList("c", "d")));
		List<Future<Integer>> results = executor.invokeAll(doWorks);
		executor.shutdown();

		//合并结果
		for (Future<Integer> result : results) {
			System.out.println(result.get());
		}
		stopWatch.stop();
		System.out.println(stopWatch.getTime());
	}
}
