package com.dantefung.concurrent.basic;


/**
 * 可见性问题；
 *
 *     线程对共享变量的读写都必须在自己的工作内存中进行，而不能直接在主内存中读写。
 *
 *     不同线程不能直接访问其他线程的工作内存中的变量，线程间变量值的传递需要主内存作为桥梁。
 *
 *
 * Volatile通过内存屏障和禁止重排序来实现线程可见性。
 * 注意:
 * 1.不能保证原子性。
 * 2.不具备"互斥性"
 *
 * 对于处理器来说，内存屏障会导致cpu缓存的刷新，刷新时，会遵循缓存一致性协议。
 *
 * lock：解锁时，jvm会强制刷新cpu缓存，导致当前线程更改，对其他线程可见。
 *
 * volatile：标记volatile的字段，在写操作时，会强制刷新cpu缓存，标记volatile的字段，每次读取都是直接读内存。
 *
 * final：即时编译器在final写操作后，会插入内存屏障，来禁止重排序，保证可见性
 *
 */
public class TestVolatile {

	/**
	 * jvm运行的时候，会为每个线程提供一个独立的缓存，用于提高效率。
	 *
	 * 主存(理解为堆内存)
	 * 线程独占内存：
	 * @param args
	 */
	public static void main(String[] args) {
		// 脏读
		ThreadDemo threadDemo = new ThreadDemo();
		new Thread(threadDemo, "threadDemo").start();
		while (true) { // 偏底层的指令，执行效率高到主线程没有空闲时间去从主存获取最新的数据

			// 这里是为了让主线程能有点空闲时间去主存获取最新的值.可以打开注释看看效果。
			// 办法一
			/*try {
				Thread.sleep(30);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}*/

			// 办法二  加锁效率低，涉及到锁升级
			/*synchronized (threadDemo) {
				if (threadDemo.isFlag()) {
					System.out.println("-----------------------");
					break;
				}
			}*/

			if (threadDemo.isFlag()) {
				System.out.println("-----------------------");
				break;
			}
		}
	}

}

class ThreadDemo implements Runnable {

	private volatile boolean flag = false;
//	private boolean flag = false;

	@Override
	public void run() {
		try {
			Thread.sleep(20);

		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		flag = true;

		System.out.println("flag=" + flag);

	}

	public boolean isFlag() {
		return flag;
	}

	public void setFlag(boolean flag) {
		this.flag = flag;
	}
}