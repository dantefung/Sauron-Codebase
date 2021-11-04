/*
 * Copyright (C), 2015-2020
 * FileName: PrintInTurnSample
 * Author:   DANTE FUNG
 * Date:     2021/10/21 17:37
 * Description: 两条线程交替打印数字
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 * DANTE FUNG        2021/10/21 17:37   V1.0.0
 */
package com.dantefung.thread.interview;

import lombok.Getter;

/**
 * @Title: PrintInTurnSample
 * @Description: 两条线程交替打印数字
 * @author DANTE FUNG
 * @date 2021/10/21 17/37
 * @since JDK1.8
 */
public class PrintInTurnSample {


	public static void main(String[] args) throws InterruptedException {
		Object lock = new Object();
		Printor printor = new Printor(lock);
		Thread t1 = new Thread(printor);
		t1.setName("t1");
		Thread t2 = new Thread(printor);
		t2.setName("t2");
		t1.start();
		t2.start();
		/*
		 * join()⽅法是Thread类的⼀个实例⽅法。
		 * 它的作⽤是让当前线程陷⼊“等待”状态，等
		 * join的这个线程执⾏完成后，再继续执⾏当前线程。
		 */
		t1.join();
		if (printor.isStop()) {
			System.exit(-1);
		}
	}

	static class Printor implements Runnable {

		private volatile int counter;
		@Getter
		private boolean stop;
		private Object lock;

		public Printor(Object lock) {
			this.lock = lock;
		}

		@Override
		public void run() {
			synchronized (lock) {
				try {
					for (int i = 0; i < 5; i++) {
						++counter;
						System.out.println(Thread.currentThread().getName()+"=="+counter);
						lock.notifyAll();
						lock.wait();
					}
				} catch (InterruptedException e) {
					e.printStackTrace();
					System.out.println(e.getMessage());
				} finally {
					if (counter == 10) {
						stop = true;
					}
				}
			}
		}
	}
}
