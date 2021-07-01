/*
 * Copyright (C), 2015-2020
 * FileName: StampedLockTest
 * Author:   DANTE FUNG
 * Date:     2021/6/9 下午10:56
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 * DANTE FUNG        2021/6/9 下午10:56   V1.0.0
 */
package com.dantefung.concurrent;

import java.util.concurrent.locks.StampedLock;

/**
 * @Title: StampedLockTest
 * @Description:
 *
 * StampedLock 的性能之所以比 ReadWriteLock 还要好，
 *
 * 其关键是 StampedLock 支持乐观读的方式。
 * ReadWriteLock 支持多个线程同时读，但是当多个线程同时读的时候，
 * 所有的写操作会被阻塞；
 * 而 StampedLock 提供的乐观读，是允许一个线程获取写锁的，
 * 也就是说不是所有的写操作都被阻塞。
 *
 *
		final StampedLock sl =
		new StampedLock();

		// 获取/释放悲观读锁示意代码
		long stamp = sl.readLock();
		try {
		//省略业务相关代码
		} finally {
		sl.unlockRead(stamp);
		}

		// 获取/释放写锁示意代码
		long stamp = sl.writeLock();
		try {
		//省略业务相关代码
		} finally {
		sl.unlockWrite(stamp);
		}


	StampedLock使用注意事项:
		StampedLock 的功能仅仅是 ReadWriteLock 的子集，在使用的时候，还是有几个地方需要注意一下。
    1、	StampedLock 不支持重入
	2、 StampedLock 的悲观读锁、写锁都不支持条件变量
	3、 如果线程阻塞在 StampedLock 的 readLock() 或者 writeLock() 上时，此时调用该阻塞线程的 interrupt() 方法，会导致 CPU 飙升

	使用 StampedLock 一定不要调用中断操作，如果需要支持中断功能，一定使用可中断的悲观读锁 readLockInterruptibly() 和写锁 writeLockInterruptibly()。

	StampedLock 读模板：

	final StampedLock sl =
	new StampedLock();

	// 乐观读
	long stamp =
	sl.tryOptimisticRead();
	// 读入方法局部变量
	......
	// 校验stamp
	if (!sl.validate(stamp)){
	// 升级为悲观读锁
	stamp = sl.readLock();
	try {
	// 读入方法局部变量
	.....
	} finally {
	//释放悲观读锁
	sl.unlockRead(stamp);
	}
	}
	//使用方法局部变量执行业务操作
	......


	StampedLock 写模板：


	long stamp = sl.writeLock();
	try {
	// 写共享变量
	......
	} finally {
	sl.unlockWrite(stamp);
	}

	课后思考
	private double x, y;
	final StampedLock sl = new StampedLock();
	// 存在问题的方法
	void moveIfAtOrigin(double newX, double newY){
	long stamp = sl.readLock();
	try {
		while(x == 0.0 && y == 0.0){
			long ws = sl.tryConvertToWriteLock(stamp);
			if (ws != 0L) {
				x = newX;
				y = newY;
				break;
			} else {
				sl.unlockRead(stamp);
				stamp = sl.writeLock();
			}
			}
	} finally {
		sl.unlock(stamp);
	}

 * @author DANTE FUNG
 * @date 2021/06/09 22/56
 * @since JDK1.8
 */
public class StampedLockTest {




	class Point {
		private int x, y;
		final StampedLock sl =
				new StampedLock();
		//计算到原点的距离
		int distanceFromOrigin() {
			// 乐观读
			long stamp =
					sl.tryOptimisticRead();
			// 读入局部变量，
			// 读的过程数据可能被修改
			int curX = x, curY = y;
			//判断执行读操作期间，
			//是否存在写操作，如果存在，
			//则sl.validate返回false
			if (!sl.validate(stamp)){
				// 升级为悲观读锁
				stamp = sl.readLock();
				try {
					curX = x;
					curY = y;
				} finally {
					//释放悲观读锁
					sl.unlockRead(stamp);
				}
			}
			 /*
				这个做法挺合理的，否则你就需要在一个循环里反复执行乐观读，
				直到执行乐观读操作的期间没有写操作
				（只有这样才能保证 x 和 y 的正确性和一致性），而循环读会浪费大量的 CPU。
				升级为悲观读锁，代码简练且不易出错，建议你在具体实践时也采用这样的方法。
			*/
			return (int) Math.sqrt(
					curX * curX + curY * curY);
		}
	}

}
