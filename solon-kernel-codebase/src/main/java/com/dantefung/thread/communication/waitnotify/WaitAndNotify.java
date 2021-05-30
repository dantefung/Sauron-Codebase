package com.dantefung.thread.communication.waitnotify;

/**
 * @Description:
 *
 * Java多线程的等待/通知机制是基于 Object 类的 wait() ⽅法和 notify() ,notifyAll() ⽅法来实现的。
 * notify()⽅法会随机叫醒⼀个正在等待的线程，⽽notifyAll()会叫醒所有正在等 待的线程。
 *
 * 需要注意的是等待/通知机制使⽤的是使⽤同⼀个对象锁，如果你两个线程使 ⽤的是不同的对象锁，那它们之间是不能⽤等待/通知机制通信的。
 *
 * @Author: DANTE FUNG
 * @Date: 2021/5/29 15:05
 * @since JDK 1.8
 */
public class WaitAndNotify {
	private static Object lock = new Object();

	public static void main(String[] args) throws InterruptedException {
		new Thread(new ThreadA()).start();
		Thread.sleep(1000);
		new Thread(new ThreadB()).start();
	}

	static class ThreadA implements Runnable {
		@Override
		public void run() {
			synchronized (lock) {
				for (int i = 0; i < 5; i++) {
					try {
						System.out.println("ThreadA: " + i);
						lock.notify();
						lock.wait();
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
				lock.notify();
			}
		}
	}

	static class ThreadB implements Runnable {
		@Override
		public void run() {
			synchronized (lock) {
				for (int i = 0; i < 5; i++) {
					try {
						System.out.println("ThreadB: " + i);
						lock.notify();
						lock.wait();
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
				lock.notify();
			}
		}
	}
}