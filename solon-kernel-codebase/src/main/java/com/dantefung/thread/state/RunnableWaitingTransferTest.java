/*
 * Copyright (C), 2015-2020
 * FileName: RunnableWaitingTransferTest
 * Author:   DANTE FUNG
 * Date:     2021/5/29 上午1:38
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 * DANTE FUNG        2021/5/29 上午1:38   V1.0.0
 */
package com.dantefung.thread.state;

/**
 * @Title: RunnableWaitingTransferTest
 * @Description: WAITING状态与RUNNABLE状态的转换
 *
 * 调⽤wait()⽅法前线程必须持有对象的锁。
 * 线程调⽤wait()⽅法时，会释放当前的锁，直到有其他线程调⽤
 * notify()/notifyAll()⽅法唤醒等待锁的线程。
 * 需要注意的是，其他线程调⽤notify()⽅法只会唤醒单个等待锁的线程
 * ，如有 有多个线程都在等待这个锁的话不⼀定会唤醒到之前调⽤wait()⽅法的线程。
 * 同样，调⽤notifyAll()⽅法唤醒所有等待锁的线程之后，
 * 也不⼀定会⻢上把时 间⽚分给刚才放弃锁的那个线程，具体要看系统的调度。
 *
 * Thread.join()
 * 调⽤join()⽅法不会释放锁，会⼀直等待当前线程执⾏完毕（转换为
 * TERMINATED状态）。
 *
 * @author DANTE FUNG
 * @date 2021/05/29 01/38
 * @since JDK1.8
 */
public class RunnableWaitingTransferTest {

	public static void main(String[] args) throws InterruptedException {
		blockedTest();
	}

	/**
	 * 要是没有调⽤join⽅法，main线程不管a线程是否执⾏完毕都会继续往下⾛。
	 * a线程启动之后⻢上调⽤了join⽅法，这⾥main线程就会等到a线程执⾏完毕，所以 这⾥a线程打印的状态固定是TERMIATED。
	 * ⾄于b线程的状态，有可能打印RUNNABLE（尚未进⼊同步⽅法），也有可能打印
	 * TIMED_WAITING（进⼊了同步⽅法）。
	 * @throws InterruptedException
	 */
	public static void blockedTest() throws InterruptedException {
		Thread a = new Thread(new Runnable() {
			@Override
			public void run() {
				testMethod();
			}
		}, "a");
		Thread b = new Thread(new Runnable() {
			@Override
			public void run() {
				testMethod();
			}
		}, "b");
		a.start();
		a.join();
		b.start();
		System.out.println(Thread.currentThread().getName() + ":" + Thread.currentThread().getState()); // 输出？
		System.out.println(a.getName() + ":" + a.getState()); // 输出？
		System.out.println(b.getName() + ":" + b.getState()); // 输出？
	}

	// 同步⽅法争夺锁
	private static synchronized void testMethod() {
		try {
			Thread.sleep(2000L);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
