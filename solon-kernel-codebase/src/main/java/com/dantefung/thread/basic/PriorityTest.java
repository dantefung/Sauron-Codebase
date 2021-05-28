/*
 * Copyright (C), 2015-2020
 * FileName: PriorityTest
 * Author:   DANTE FUNG
 * Date:     2021/5/28 下午11:02
 * Description: 线程优先级
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 * DANTE FUNG        2021/5/28 下午11:02   V1.0.0
 */
package com.dantefung.thread.basic;

import java.util.stream.IntStream;

/**
 * @Title: PriorityTest
 * @Description: 线程优先级
 * Java中线程优先级可以指定，范围是1~10。但是并不是所有的操作系统都⽀持10
 * 级优先级的划分（⽐如有些操作系统只⽀持3级划分：低，中，⾼），Java只是给 操作系统⼀个优先级的参考值，
 * 线程最终在操作系统的优先级是多少还是由操作系 统决定。
 *
 * Java默认的线程优先级为5，线程的执⾏顺序由调度程序来决定，线程的优先级会 在线程被调⽤之前设定。
 * 通常情况下，⾼优先级的线程将会⽐低优先级的线程有更⾼的⼏率得到执⾏。
 * 我们 使⽤⽅法 Thread 类的 setPriority() 实例⽅法来设定线程的优先级。
 * @author DANTE FUNG
 * @date 2021/05/28 23/02
 * @since JDK1.8
 */
public class PriorityTest {

	public static void main(String[] args) {
		//simpleTestPriority();
		//testSuggetionPriority();
		testInconsistencyIPriority();
	}

	/**
	 * 在之前，我们有谈到⼀个线程必然存在于⼀个线程组中，那么当线程和线程组的优 先级不⼀致的时候将会怎样呢？
	 * 我们⽤下⾯的案例来验证⼀下：
	 *
	 * 所以，如果某个线程优先级⼤于线程所在线程组的最⼤优先级，那么该线程的优先 级将会失效，取⽽代之的是线程组的最⼤优先级。
	 */
	private static void testInconsistencyIPriority() {
		ThreadGroup threadGroup = new ThreadGroup("t1");
		threadGroup.setMaxPriority(6);
		Thread thread = new Thread(threadGroup,"thread");
		thread.setPriority(9);
		System.out.println("---------------测试: 线程和线程组的优先级不⼀致 START -------------------");
		System.out.println("我是线程组的优先级"+threadGroup.getMaxPriority());
		System.out.println("我是线程的优先级"+thread.getPriority());
		System.out.println("---------------测试: 线程和线程组的优先级不⼀致 END -------------------");
	}

	/**
	 * 既然有1-10的级别来设定了线程的优先级，这时候可能有些读者会问，
	 * 那么我是不 是可以在业务实现的时候，采⽤这种⽅法来指定⼀些线程执⾏的先后顺序？
	 *
	 * 对于这个问题，我们的答案是:No!
	 *
	 * Java中的优先级来说不是特别的可靠，Java程序中对线程所设置的优先级只是给 操作系统⼀个建议，
	 * 操作系统不⼀定会采纳。⽽真正的调⽤顺序，是由操作系统的 线程调度算法决定的。
	 *
	 * Java提供⼀个线程调度器来监视和控制处于RUNNABLE状态的线程。
	 * 线程的调度 策略采⽤抢占式，优先级⾼的线程⽐优先级低的线程会有更⼤的⼏率优先执⾏。
	 * 在 优先级相同的情况下，按照“先到先得”的原则。每个Java程序都有⼀个默认的主线 程，就是通过JVM启动的第⼀个线程main线程。
	 */
	private static void testSuggetionPriority() {
		System.out.println("---------------测试: 线程的优先级指定⼀些线程执⾏的先后顺序,不可靠！ START --------------------");
		IntStream.range(1, 10).forEach(i -> {
			Thread thread = new Thread(new T1());
			thread.setPriority(i);
			thread.start();
		});
		System.out.println("---------------测试: 线程的优先级指定⼀些线程执⾏的先后顺序,不可靠！ END --------------------");
	}

	public static class T1 extends Thread {
		@Override
		public void run() {
			super.run();
			System.out.println(String.format("当前执⾏的线程是：%s，优先级：%d",
					Thread.currentThread().getName(),
					Thread.currentThread().getPriority()));
		}
	}

	private static void simpleTestPriority() {
		Thread a = new Thread();
		System.out.println("我是默认线程优先级："+a.getPriority());
		Thread b = new Thread();
		b.setPriority(10);
		System.out.println("我是设置过的线程优先级："+b.getPriority());
	}
}
