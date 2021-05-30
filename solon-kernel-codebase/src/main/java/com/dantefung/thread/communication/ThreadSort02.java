/*
 * Copyright (C), 2015-2020
 * FileName: ThreadSort02
 * Author:   DANTE FUNG
 * Date:     2021/2/28 16:26
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 * DANTE FUNG        2021/2/28 16:26   V1.0.0
 */
package com.dantefung.thread.communication;

/**
 * @Title: ThreadSort02
 * @Description: 线程循序执行.
 *  在实际业务场景中，有时候启动的线程可能需要依赖先启动的线程执行完成
 *  才能正确的执行线程中的业务逻辑。
 *  此时，就需要确保线程的执行顺序。那么如何保证线程的执行顺序呢？
 *  可以使用Thread类中的join()方法来确保线程的执行顺序。
 * @author DANTE FUNG
 * @date 2021/02/28 16/26
 * @since JDK1.8
 */
public class ThreadSort02  {

	/**
	 * synchronized关键字，我们一般称之为”同步锁“，用它来修饰需要同步的方法和需要同步代码块，默认是当前对象作为锁的对象
	 *
	 * public final synchronized void join(long millis, int nanos)
	 * throws InterruptedException {
	 *
	 *     if (millis < 0) {
	 *         throw new IllegalArgumentException("timeout value is negative");
	 *     }
	 *
	 *     if (nanos < 0 || nanos > 999999) {
	 *         throw new IllegalArgumentException(
	 *                             "nanosecond timeout value out of range");
	 *     }
	 *
	 *     if (nanos >= 500000 || (nanos != 0 && millis == 0)) {
	 *         millis++;
	 *     }
	 *
	 *     join(millis);
	 * }
	 *
	 *
	 * @param args
	 * @throws InterruptedException
	 */
	public static void main(String[] args) throws InterruptedException {
		Thread thread1 = new Thread(() -> {
			System.out.println("thread1");
		});
		Thread thread2 = new Thread(() -> {
			System.out.println("thread2");
		});
		Thread thread3 = new Thread(() -> {
			System.out.println("thread3");
		});

		thread1.start();
		// 主线程调用thread1.join，故主线程拥有该锁，等thread1执行完再执行
		// 表示调用thread1.wait()这句代码的线程加入等待队列，即并不是thread1线程进入等待，而是调用这句话的线程进入等待。
		thread1.join();

		thread2.start();
		thread2.join();

		thread3.start();
		thread3.join();
	}
}
