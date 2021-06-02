/*
 * Copyright (C), 2015-2020
 * FileName: BlockTest
 * Author:   DANTE FUNG
 * Date:     2021/6/2 17:38
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 * DANTE FUNG        2021/6/2 17:38   V1.0.0
 */
package com.dantefung.thread.aqs;

import lombok.Getter;
import lombok.Setter;
import sun.misc.Unsafe;
import sun.reflect.CallerSensitive;

import java.lang.reflect.Field;
import java.security.PrivilegedExceptionAction;
import java.util.concurrent.locks.LockSupport;

/**
 * @Title: BlockTest
 * @Description:
 * @author DANTE FUNG
 * @date 2021/06/02 17/38
 * @since JDK1.8
 */
public class BlockTest {

	private static final Unsafe unsafe = getUnsafe();
	private static final long stateOffset;

	static {
		try {
			stateOffset = unsafe.objectFieldOffset(CustomAQS.class.getDeclaredField("state"));
		} catch (Exception ex) { throw new Error(ex); }
	}

	@Getter
	@Setter
	private volatile int state;

	public static void main(String[] args) throws InterruptedException {
		BlockTest block = new BlockTest();
		// 先让主线程获取锁成功.
		block.tryAcquire();
		Thread t1 = new Thread(() -> {
			if (!block.acquireQueued(block)) {
				block.tryRelease();
			}
		}, "t1");
		t1.start();
		Thread.sleep(2000);
		// 主线程释放锁
		block.tryRelease();
		while (true) {
			// 唤醒t1线程
			LockSupport.unpark(t1);
		}
	}

	@CallerSensitive
	private static sun.misc.Unsafe getUnsafe() {
		try {
			return sun.misc.Unsafe.getUnsafe();
		} catch (SecurityException tryReflectionInstead) {
		}
		try {
			return java.security.AccessController.doPrivileged((PrivilegedExceptionAction<Unsafe>) () -> {
				Class<Unsafe> k = Unsafe.class;
				for (Field f : k.getDeclaredFields()) {
					f.setAccessible(true);
					Object x = f.get(null);
					if (k.isInstance(x))
						return k.cast(x);
				}
				throw new NoSuchFieldError("the Unsafe");
			});
		} catch (java.security.PrivilegedActionException e) {
			throw new RuntimeException("Could not initialize intrinsics", e.getCause());
		}
	}

	/**
	 * Convenience method to park and then check if interrupted
	 * 阻塞并判断当前线程是否已经阻塞成功
	 * @return {@code true} if interrupted
	 */
	private final boolean parkAndCheckInterrupt() {
		System.out.println(System.currentTimeMillis() + "::" + Thread.currentThread().getName() + "即将被阻塞!");
		LockSupport.park(this);
		System.out.println(System.currentTimeMillis() + "::" + Thread.currentThread().getName() + "被唤醒!");
		return Thread.interrupted();
	}

	final boolean acquireQueued(BlockTest block) {
		boolean interrupted = false;
		int count = 0;
		for (; ; ) {
			++count;
			System.out.println("第" + count + "次循环, " + "阻塞状态: " + interrupted);
			// 设计同步状态state=1为加锁成功，state=0为释放锁
			if (tryAcquire()) {// 假设获取锁成功就跳出循环
				return interrupted;
			}
			// 获取不到锁就阻塞
			if (block.parkAndCheckInterrupt()) {
				System.out.println("-------interrupted!-----");
				interrupted = true;
			}
			System.out.println(String.format("-------%s已被唤醒!------", Thread.currentThread().getName()));
		}
	}

	protected boolean tryAcquire() {
		boolean flag = compareAndSetState(0, 1);
		if (flag) {
			System.out.println(Thread.currentThread().getName() + "获取锁成功!");
		}
		return flag;
	}

	protected boolean tryRelease() {
		setState(0);
		System.out.println(Thread.currentThread().getName() + "释放锁成功!");
		return true;
	}

	protected final boolean compareAndSetState(int expect, int update) {
		// See below for intrinsics setup to support this
		return unsafe.compareAndSwapInt(this, stateOffset, expect, update);
	}
}
