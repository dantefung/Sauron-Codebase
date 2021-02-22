package com.dantefung.concurrent.aqs;

import java.lang.reflect.Field;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.locks.LockSupport;
import java.util.stream.Collectors;

import lombok.extern.slf4j.Slf4j;
import sun.misc.Unsafe;

/**
 * 自定义AQS
 * created at 2020-06-27 19:55
 * update at 2021-02-23 00:34:57
 * @author lerry
 * @modifer DANTE FUNG
 * reference: https://blog.csdn.net/limenghua9112/article/details/107080622
 */
@Slf4j
public class DiyAqsLock {

	/**
	 * <pre>
	 * 使用一个Volatile的int类型的成员变量来表示同步状态
	 * 记录锁的状态 0表示没有线程持有锁
	 * >0表示有线程持有锁
	 * </pre>
	 */
	private volatile int state = 0;

	/**
	 * 用于记录持有锁的线程
	 */
	private Thread lockHolder;

	/**
	 * 存放获取锁失败的线程对象
	 */
	private ConcurrentLinkedQueue<Thread> waiters = new ConcurrentLinkedQueue<>();

	/**
	 * 通过Unsafe进行cas操作
	 */
	private static final Unsafe unsafe = UnsafeInstance.getInstance();

	private static long stateOffset;

	static {
		try {
			stateOffset = unsafe.objectFieldOffset(DiyAqsLock.class.getDeclaredField("state"));
		}
		catch (NoSuchFieldException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 加锁
	 */
	public void lock() {
		// 同步获取锁
		if (acquire()) {
			return;
		}
		Thread current = Thread.currentThread();
		log.debug("线程状态为：{}", current.getState());
		// 获取锁失败的 添加进队列里
		waiters.add(current);
		// 自旋获取锁
		for (; ; ) {
			// 如果当前线程是栈首的对象，并且获取锁成功，则在等待队列中移除栈首对象，否则继续等待
			if (current == waiters.peek() && acquire()) {
				// 移除队列
				waiters.poll();
				return;
			}
			// 让出cpu的使用权
			LockSupport.park(current);
		}
	}

	/**
	 * 获取锁
	 * @return
	 */
	private boolean acquire() {
		int state = getState();
		Thread current = Thread.currentThread();
		boolean waitCondition = waiters.size() == 0 || current == waiters.peek();
		if (state == 0 && waitCondition) {
			// 没有线程获取到锁
			if (compareAndSwapState(0, 1)) {
				log.info("获取锁成功");
				// 同步修改成功 将线程持有者修改为当前线程
				setLockHolder(current);
				return true;
			}
		}
		return false;
	}

	/**
	 * cas操作
	 * @param expect
	 * @param update
	 * @return
	 */
	public final boolean compareAndSwapState(int expect, int update) {
		return unsafe.compareAndSwapInt(this, stateOffset, expect, update);
	}

	/**
	 * 解锁
	 */
	public void unlock() {
		System.out.printf("当前等待队列为：%s\n", waiters.stream().map(w -> w.getName()).collect(Collectors.toList()));
		// 1.校验释放锁的线程是不是当前持有锁的线程
		if (Thread.currentThread() != lockHolder) {
			throw new RuntimeException("threadHolder is not current thread");
		}
		// 2. 释放锁修改state
		if (getState() == 1 && compareAndSwapState(1, 0)) {
			log.info("释放锁成功");
			// 将锁的持有线程置为空
			setLockHolder(null);
			// 2.唤醒队列里的第一个线程
			Thread first = waiters.peek();
			if (first != null) {
				// 解除线程的阻塞
				LockSupport.unpark(first);
			}
		}
	}

	public int getState() {
		return state;
	}

	public void setLockHolder(Thread lockHolder) {
		this.lockHolder = lockHolder;
	}
}

@Slf4j
class UnsafeInstance {
	public static Unsafe getInstance() {
		try {
			Field field = Unsafe.class.getDeclaredField("theUnsafe");
			field.setAccessible(true);
			return (Unsafe) field.get(null);
		}
		catch (Exception e) {
			log.error(e.getMessage(), e);
			return null;
		}
	}
}
