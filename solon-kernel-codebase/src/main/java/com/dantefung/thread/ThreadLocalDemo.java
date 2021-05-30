package com.dantefung.thread;

/**
 * 可以看到，虽然两个线程使⽤的同⼀个ThreadLocal实例（通过构造⽅法传⼊），
 * 但是它们各⾃可以存取⾃⼰当前线程的⼀个值。
 *
 *
 * 5.5.4 InheritableThreadLocal
 * InheritableThreadLocal类与ThreadLocal类稍有不同，Inheritable是继承的意思。
 * 它不仅仅是当前线程可以存取副本值，⽽且它的⼦线程也可以存取这个副本值
 */
public class ThreadLocalDemo {
	static class ThreadA implements Runnable {
		private ThreadLocal<String> threadLocal;

		public ThreadA(ThreadLocal<String> threadLocal) {
			this.threadLocal = threadLocal;
		}

		public static void main(String[] args) {
			ThreadLocal<String> threadLocal = new ThreadLocal<>();
			new Thread(new ThreadA(threadLocal)).start();
			new Thread(new ThreadB(threadLocal)).start();
		}

		@Override
		public void run() {
			threadLocal.set("A");
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			System.out.println("ThreadA输出：" + threadLocal.get());
		}

		static class ThreadB implements Runnable {
			private ThreadLocal<String> threadLocal;

			public ThreadB(ThreadLocal<String> threadLocal) {
				this.threadLocal = threadLocal;
			}

			@Override
			public void run() {
				threadLocal.set("B");
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				System.out.println("ThreadB输出：" + threadLocal.get());
			}
		}
	}
}