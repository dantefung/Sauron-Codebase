/*
 * Copyright (C), 2015-2020
 * FileName: ThreadStateTest
 * Author:   DANTE FUNG
 * Date:     2021/5/29 上午12:31
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 * DANTE FUNG        2021/5/29 上午12:31   V1.0.0
 */
package com.dantefung.thread.state;

/**
 * @Title: ThreadStateTest
 * @Description:
 * // Thread.State 源码
 * public enum State {
 *  NEW,
 *  RUNNABLE, Java线程的RUNNABLE状态其实是包括了传统操作系统线程的ready和running两个状态的。
 *  BLOCKED,  (等待/阻塞/睡眠)  处于BLOCKED状态的线程正等待锁的释放以进⼊同步区。
 *  WAITING,
 *  Object.wait()：使当前线程处于等待状态直到另⼀个线程唤醒它；
 *  / Thread.join()：等待线程执⾏完毕，底层调⽤的是Object实例的wait⽅法；
 *  / LockSupport.park()：除⾮获得调⽤许可，否则禁⽤当前线程进⾏线程调度。
 *  要是经理t1不主动唤醒你t2（notify、notifyAll..），可以说你t2只能⼀直等待了。
 *  TIMED_WAITING,
 *  Thread.sleep(long millis)：使当前线程睡眠指定时间；/
 *  Object.wait(long timeout)：线程休眠指定时间，等待期间可以通过 notify()/notifyAll()唤醒；
 *  Thread.join(long millis)：等待当前线程最多执⾏millis毫秒，如果millis为0，则 会⼀直执⾏；
 *  LockSupport.parkNanos(long nanos)： 除⾮获得调⽤许可，否则禁⽤当前线 程进⾏线程调度指定时间；
 *  LockSupport.parkUntil(long deadline)：同上，也是禁⽌线程进⾏调度指定时间；
 *  TERMINATED;  终⽌状态。此时线程已执⾏完毕。
 * }
 * @author DANTE FUNG
 * @date 2021/05/29 00/31
 * @since JDK1.8
 */
public class ThreadStateTest {

	public static void main(String[] args) {
		//testStateNew();
		testStartMethod();
	}

	/**
	 * 处于NEW状态的线程此时尚未启动。这⾥的尚未启动指的是还没调⽤Thread实例 的start()⽅法。
	 */
	private static void testStateNew() {
		Thread thread = new Thread(() -> {});
		System.out.println(thread.getState()); // 输出 NEW
	}

	/**
	 * 关于start()的两个引申问题
	 * 1. 反复调⽤同⼀个线程的start()⽅法是否可⾏？
	 * 2. 假如⼀个线程执⾏完毕（此时处于TERMINATED状态），再次调⽤这个线程 的start()⽅法是否可⾏？
	 *
	 * 我是在start()⽅法内部的最开始打的断点，叙述下在我这⾥打断点看到的结果：
	 * 第⼀次调⽤时threadStatus的值是0。
	 * 第⼆次调⽤时threadStatus的值不为0。
	 *
	 * // Thread.getState⽅法源码：
	 * // sun.misc.VM 源码：
	 *
	 * 两个问题的答案都是不可⾏，在调⽤⼀次start()之后，threadStatus的值会改 变（threadStatus !=0），此时再次调⽤start()⽅法会抛出
	 * IllegalThreadStateException异常。 ⽐如，threadStatus为2代表当前线程状态为TERMINATED。
	 */
	public static void testStartMethod() {
		Thread thread = new Thread(() -> {});
		thread.start(); // 第⼀次调⽤
		thread.start(); // 第⼆次调⽤
	}
}
