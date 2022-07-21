package com.dantefung.tool;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.time.StopWatch;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Consumer;

/*** 数据集并行处理工具 */
@Slf4j
public class DataHelper {
	/*** 并行处理线程数字 */
	static final int THREAD_COUNT = 50;
	/*** 单线程中处理的集合的长度,50个线程，每个线程处理2条，如果处理时间为1S，则需要2S的时间. */
	static final int INNER_LIST_LENGTH = 2;

	/** 大集合拆分. ** @param list * @param len * @param <T> * @return */
	private static <T> List<List<T>> splitList(List<T> list, int len) {
		if (list == null || list.size() == 0 || len < 1) { return null; }
		List<List<T>> result = new ArrayList<List<T>>();
		int size = list.size();
		int count = (size + len - 1) / len;
		for (int i = 0; i < count; i++) {
			List<T> subList = list.subList(i * len, ((i + 1) * len > size ? size : len * (i + 1)));
			result.add(subList);
		}
		return result;
	}

	/*** 并行处理. ** @param list 大集合 * @param pageSize 单页数据大小 * @param consumer 处理程序 * @param <T> */
	public static <T> void fillDataByPage(List<T> list, int pageSize, Consumer<T> consumer) {
		List<List<T>> pageList = new ArrayList<>();
		splitList(list, pageSize).forEach(o -> pageList.add(o));
		int totalPage = pageList.size();
		AtomicInteger i = new AtomicInteger();
		// 遍历每页数据
		pageList.forEach(items -> {
			// 每页使用50条线程跑
			ExecutorService executor = Executors.newFixedThreadPool(THREAD_COUNT);
			i.getAndIncrement();
			Collection<BufferInsert<T>> bufferInserts = new ArrayList<>();
			// 每条线程分配INNER_LIST_LENGTH条任务
			splitList(items, INNER_LIST_LENGTH).forEach(o -> {
				bufferInserts.add(new BufferInsert(o, consumer));
			});

			try {
				executor.invokeAll(bufferInserts);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			executor.shutdown();
			log.info("【当前数据页:{}/{}】", i.get(), totalPage);
		});
	}


	/*** 多线程并发处理数据. ** @param <T> */
	static class BufferInsert<T> implements Callable<Integer> {
		/*** 要处理的数据列表. */
		List<T> items;
		/*** 处理程序. */
		Consumer<T> consumer;

		public BufferInsert(List<T> items, Consumer<T> consumer) {
			this.items = items;
			this.consumer = consumer;
		}

		@Override
		public Integer call() {
			for (T item : items) {
				this.consumer.accept(item);
			}
			return 1;
		}
	}


	/*** 8秒处理400个任务，每个任务执行时间为1S，并行的威力 */
	@Test
	public void test() {
		List<Integer> sumList = new ArrayList<>();
		for (int i = 0; i < 400; i++) {
			sumList.add(i);
		}
		StopWatch stopWatch = new StopWatch();
		stopWatch.start();
		DataHelper.fillDataByPage(sumList, 100, (o) -> {
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		});
		stopWatch.stop();
		System.out.println("time:" + stopWatch.getTime());
	}
}