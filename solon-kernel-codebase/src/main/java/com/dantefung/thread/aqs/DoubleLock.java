package com.dantefung.thread.aqs;

/**
 * 共享式同步工具类
 *
 * 假设某个操作只能同时有两个线程操作，
 * 其他的线程需要处于等待状态，
 * 我们可以这么定义这个锁
 *
 * 在共享模式下的道理也差不多，
 * 比如说某项操作我们允许10个线程同时进行，
 * 超过这个数量的线程就需要阻塞等待。
 * 那么我们就可以把state的初始值设置为10，
 * 一个线程尝试获取同步状态的意思就是先判断state的值是否大于0，
 * 如果不大于0的话意味着当前已经有10个线程在同时执行该操作，本线程需要阻塞等待；
 * 如果state的值大于0，那么可以把state的值减1后进入该操作，
 * 每当一个线程完成操作的时候需要释放同步状态，也就是把state的值加1，并通知后续等待的线程。
 *
 */
public class DoubleLock {


	private static class Sync extends CustomAQS {

		public Sync() {
			super();
			setState(2);    //设置同步状态的值
		}

		@Override
		protected int tryAcquireShared(int arg) {
			System.out.println(Thread.currentThread().getName() + "尝试获取共享锁!");
			while (true) {
				int cur = getState();
				int next = getState() - arg;
				if (compareAndSetState(cur, next)) {
					return next;
				}
			}
		}

		@Override
		protected boolean tryReleaseShared(int arg) {
			System.out.println(Thread.currentThread().getName() + "尝试释放共享锁!");
			printSyncronizedQueue();
			while (true) {
				int cur = getState();
				int next = cur + arg;
				if (compareAndSetState(cur, next)) {
					if (next <= 0) continue;// next 至少等于1会跳出循环.
					return true;
				}
			}
		}
	}

	private Sync sync = new Sync();

	public void lock() {
		sync.acquireShared(1);
	}

	public void unlock() {
		sync.releaseShared(1);
	}

	public void print() {
		sync.printSyncronizedQueue();
	}

	public int getSharedLockCount() {
		return sync.getState();
	}

	public static void main(String[] args) {
		DoubleLock lock = new DoubleLock();
		System.out.println("初始化的共享锁数量:" + lock.getSharedLockCount());
		lock.lock();
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		Thread t1 = new Thread(() -> {
			lock.lock();
			try {
				Thread.sleep(2000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			//lock.unlock();
		}, "t1");
		t1.start();

		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		// -------------------  t2, t3 会入列 -----------------------

		Thread t2 = new Thread(() -> {
			lock.lock();
			try {
				Thread.sleep(2000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			lock.unlock();
		}, "t2");
		t2.start();

		Thread t3 = new Thread(() -> {
			lock.lock();
			try {
				Thread.sleep(2000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			lock.unlock();
		}, "t3");
		t3.start();

		System.out.println("剩余共享锁数量: " + lock.getSharedLockCount());

		try {
			Thread.sleep(5000);
			lock.print();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		lock.unlock();
		System.out.println("剩余共享锁数量: " + lock.getSharedLockCount());

	}
}