package com.dantefung.thread.communication;

/**
 * ⽐如我现在有⼀个需求，我想让线程A输出0，然后线程B输出1，再然后线程A输出
 * 2…以此类推。我应该怎样实现呢？
 */
public class Signal {
	private static volatile int signal = 0;

	public static void main(String[] args) throws InterruptedException {
		new Thread(new ThreadA()).start();
		Thread.sleep(1000);
		new Thread(new ThreadB()).start();
	}

	static class ThreadA implements Runnable {
		@Override
		public void run() {
			while (signal < 5) {
				if (signal % 2 == 0) {
					System.out.println("threadA: " + signal);
					synchronized (this) {
						signal++;
					}
				}
			}
		}
	}

	static class ThreadB implements Runnable {
		@Override
		public void run() {
			while (signal < 5) {
				if (signal % 2 == 1) {
					System.out.println("threadB: " + signal);
					synchronized (this) {
						signal = signal + 1;
					}
				}
			}
		}
	}
}