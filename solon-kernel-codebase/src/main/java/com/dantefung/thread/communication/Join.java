package com.dantefung.thread.communication;

/**
 * join()⽅法是Thread类的⼀个实例⽅法。
 * 它的作⽤是让当前线程陷⼊“等待”状态，等
 * join的这个线程执⾏完成后，再继续执⾏当前线程。
 * 有时候，主线程创建并启动了⼦线程，如果⼦线程中需要进⾏⼤量的耗时运算，
 * 主线程往往将早于⼦线程结束之前结束。 如果主线程想等待⼦线程执⾏完毕后，
 * 获得⼦线程中的处理完的某个数据，就要⽤ 到join⽅法了。
 *
 * 注意join()⽅法有两个重载⽅法，⼀个是join(long)， ⼀个是join(long, int)。
 * 实际上，通过源码你会发现，join()⽅法及其重载⽅法底层都是利⽤了
 * wait(long)这个⽅法。 对于join(long, int)，通过查看源码(JDK 1.8)发现，
 * 底层并没有精确到纳秒， ⽽是对第⼆个参数做了简单的判断和处理。
 */
public class Join {

	public static void main(String[] args) throws InterruptedException {
		Thread thread = new Thread(new ThreadA());
		thread.start();
		thread.join();
		System.out.println("如果不加join⽅法，我会先被打出来，加了就不⼀样了");
	}

	static class ThreadA implements Runnable {
		@Override
		public void run() {
			try {
				System.out.println("我是⼦线程，我先睡⼀秒");
				Thread.sleep(1000);
				System.out.println("我是⼦线程，我睡完了⼀秒");
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}