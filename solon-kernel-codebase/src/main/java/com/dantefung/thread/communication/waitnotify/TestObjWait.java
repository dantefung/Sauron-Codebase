package com.dantefung.thread.communication.waitnotify;

import java.util.concurrent.locks.LockSupport;

public class TestObjWait {

	public static void main(String[] args) throws Exception {
		//test1();
		test2();
	}

	private static void test2() throws InterruptedException {
		final Object obj = new Object();

		Thread threadA = new Thread(new Runnable() {
			@Override
			public void run() {
				int sum = 0;
				for (int i = 0; i < 10; i++) {
					sum += i;
				}
				try {
					synchronized (obj) {
						obj.wait();
					}
				} catch (Exception e) {
					e.printStackTrace();
				}

				System.out.println(Thread.currentThread().getName() + "  sum = " + sum);
			}
		}, "ThreadA");

		threadA.start();

		//睡眠一秒钟，保证线程A已经计算完成，阻塞在wait方法
		Thread.sleep(1000);// 如果不加此方法，可能主线程已经执行完obj.notify();，threadA才执行到obj.wait(),此时就会无限等待下去。
		System.out.println(Thread.currentThread().getName() + " sleep over ");

		synchronized (obj) {
			obj.notify();
		}
		System.out.println(Thread.currentThread().getName() + " over ");
	}

	private static void test1() throws InterruptedException {
		Thread threadA = new Thread(new Runnable() {
			@Override
			public void run() {
				int sum = 0;
				for (int i = 0; i < 10; i++) {
					sum += i;
				}
				LockSupport.park();// 阻塞threadA线程
				System.out.println(Thread.currentThread().getName() + " sum = " + sum);
			}
		}, "ThreadA");

		threadA.start();

		//睡眠一秒钟，保证线程A已经计算完成，阻塞在wait方法
		Thread.sleep(1000);// 主线程让出cpu执行权
		System.out.println(Thread.currentThread().getName() + " sleep over ");
		LockSupport.unpark(threadA);// 唤醒threadA
		Thread.sleep(1000);// 主线程让出cpu执行权,让threadA能得到cpu执行权
		System.out.println(Thread.currentThread().getName() + " over ");
	}
}
