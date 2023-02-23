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
import com.google.common.util.concurrent.ThreadFactoryBuilder;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.*;
import java.util.stream.Collectors;

/**
 * @Title: Demo1
 * @Description:
 * @author DANTE FUNG
 * @date 2022/07/20 11/33
 * @since JDK1.8
 */
@Slf4j
public class Demo1 {

	private static final ThreadFactory threadFactory = new ThreadFactoryBuilder().setNameFormat("my-pool-%d").build();
	private static final ThreadPoolExecutor executor = new ThreadPoolExecutor(10, 10, 10, TimeUnit.SECONDS, new LinkedBlockingQueue<>(100), threadFactory, new ThreadPoolExecutor.CallerRunsPolicy());
	// private static final List<Integer> resultList = new ArrayList<>();
	private static final List<Integer> resultList = Collections.synchronizedList(new ArrayList<>());
	private static final List<CompletableFuture<Integer>> futureList = new ArrayList<>();

	public static void main(String[] args) throws ExecutionException, InterruptedException, TimeoutException {
//		testNoTryCatch();
//		System.out.println("=====23333=====");// 当线程池满了这行代码执行不了.

		/*MyThreadPool threadPool = new MyThreadPool(1, 1, 60, "threadPool",
				new LinkedBlockingQueue<>(1));
		try {
			testNoTryCatch(threadPool);
		} catch (Exception ex) {
			log.error(ex.getMessage());// 这里能捕获到线程池满的异常
		} finally {
			threadPool.shutdownNow();// 这里是因为在主线程测试，所以要关掉。实际业务场景不用加这段代码
		}*/

		long start = System.currentTimeMillis();
		try {
//			doSomething2(executor, resultList, futureList);
			doSomething3(executor, resultList, futureList);
		} catch (Exception e) {
			System.out.println("main 处理了异常 -> " + e.getMessage());
		}
		System.out.println("方法执行时间: " + (System.currentTimeMillis() - start));
		System.out.println("main 执行完了...");
		executor.shutdown();
//		testWithTryCatch();
	}

	private static void doSomething3(ThreadPoolExecutor executor, List<Integer> resultList, List<CompletableFuture<Integer>> futureList) {
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < 10; i++) {
			int finalI = i;
			CompletableFuture<Integer> future = CompletableFuture.supplyAsync(() -> {
				try {
					Thread.sleep(finalI * 1000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				if (finalI == 4) {
					System.out.println("业务异常... -> " + finalI);
					throw new RuntimeException("业务异常... -> " + finalI);
				}
				return finalI;
			}, executor);
			futureList.add(future);
		}

		CompletableFuture<Void> completableFuture = CompletableFuture.allOf(futureList.toArray(new CompletableFuture[0]));
		if (completableFuture.isCompletedExceptionally()) {
			throw new RuntimeException(sb.toString());
		} else {
			resultList.addAll(futureList.stream().map(CompletableFuture::join).collect(Collectors.toList()));
		}
		System.out.println(resultList);
	}

	private static void doSomething(ThreadPoolExecutor executor, List<Integer> resultList, List<CompletableFuture<Integer>> futureList) {
		for (int i = 0; i < 10; i++) {
			int finalI = i;
			CompletableFuture<Integer> future = CompletableFuture.supplyAsync(() -> {
				try {
					Thread.sleep(finalI * 1000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				if (finalI == 4) {
					System.out.println("业务异常... -> " + finalI);
					throw new RuntimeException("业务异常... -> " + finalI);
				}
				return finalI;
			}, executor).handle((result, e) -> {
				if (e != null) {
					System.out.println("CompletableFuture处理异常 -> " + e.getMessage());
					throw new RuntimeException("CompletableFuture处理异常 -> " + e.getMessage());
				}
				resultList.add(result);
				return result;
			});
			futureList.add(future);
		}

		CompletableFuture.allOf(futureList.toArray(new CompletableFuture[0])).join();
		System.out.println(resultList.size());
		System.out.println(resultList);
	}

	private static void doSomething2(ThreadPoolExecutor executor, List<Integer> resultList, List<CompletableFuture<Integer>> futureList) {
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < 10; i++) {
			int finalI = i;
			CompletableFuture<Integer> future = CompletableFuture.supplyAsync(() -> {
				try {
					Thread.sleep(finalI * 1000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				if (finalI == 4) {
					System.out.println("业务异常... -> " + finalI);
					throw new RuntimeException("业务异常... -> " + finalI);
				}
				return finalI;
			}, executor).handle((result, e) -> {
				if (e != null) {
					sb.append("CompletableFuture处理异常 -> " + e.getCause().getMessage());
					throw new RuntimeException("CompletableFuture处理异常 -> " + e.getMessage());
				}
				resultList.add(result);
				return result;
			});
			futureList.add(future);
		}

		CompletableFuture<Void> completableFuture = CompletableFuture.allOf(futureList.toArray(new CompletableFuture[0]));
		if (completableFuture.isCompletedExceptionally()) {
			throw new RuntimeException(sb.toString());
		} else {
			resultList.addAll(futureList.stream().map(CompletableFuture::join).collect(Collectors.toList()));
		}
		System.out.println(resultList);
	}

	private static void testNoTryCatch(MyThreadPool threadPool) throws ExecutionException, InterruptedException, TimeoutException {

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

		//		CompletableFuture.allOf(future1, future2, future3, future4, future5).join();
		CompletableFuture.allOf(future1, future2, future3, future4, future5).get(30000, TimeUnit.MILLISECONDS);
		System.out.println("=====END====");// 这行代码执行不到

	}


	private static void testNoTryCatch() throws ExecutionException, InterruptedException, TimeoutException {
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

//		CompletableFuture.allOf(future1, future2, future3, future4, future5).join();
		CompletableFuture.allOf(future1, future2, future3, future4, future5).get(30000, TimeUnit.MILLISECONDS);
		System.out.println("=====END====");// 这行代码执行不到

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
