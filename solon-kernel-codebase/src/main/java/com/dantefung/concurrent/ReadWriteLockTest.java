/*
 * Copyright (C), 2015-2020
 * FileName: ReadWriteLockTest
 * Author:   DANTE FUNG
 * Date:     2020/11/30 11:34 下午
 * Description: 读写锁测试
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 * DANTE FUNG        2020/11/30 11:34 下午   V1.0.0
 */
package com.dantefung.concurrent;

import java.util.Random;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * @Title: ReadWriteLockTest
 * @Description: 读写锁测试
 * 为什么需要读写锁？
 * 与传统锁不同的是读写锁的规则是可以共享读，但只能一个写，
 * 总结起来为： 读读不互斥，读写互斥，写写互斥 ，
 * 而一般的独占锁是： 读读互斥，读写互斥，写写互斥 ，
 * 而场景中往往 读远远大于写 也就是读多写少的场景 ，
 * 读写锁就是为了这种优化而创建出来的一种机制。
 *
 * 什么是读写锁?
 * 1、允许多个线程同时读取共享变量
 * 2、只允许一个线程写共享变量
 * 3、如果一个写线程正在执行写操作，此时禁止读线程读共享变量
 *
 * 注意是 读远远大于写 ，一般情况下独占锁的效率低来源于高并发下对临界区的激烈竞争导致线程上下文切换。
 * 因此当并发不是很高的情况下，读写锁由于需要额外维护读锁的状态，可能还不如独占锁的效率高。
 * 因此需要根据实际情况选择使用。
 * 
 * @author DANTE FUNG
 * @date 2020/11/30 23/34
 * @since JDK1.8
 */
public class ReadWriteLockTest {

	public static void main(String[] args) {
		final Queue3 q3 = new Queue3();
		for(int i=0;i<3;i++) {
			new Thread() {
				public void run() {
					for(;;) {
						q3.get();
					}
				}
			}.start();
			new Thread() {
				public void run() {
					for(;;) {
						q3.put(new Random().nextInt(1000));
					}
				};
			}.start();
		}

	}
}

class Queue3 {

	// 共享数据，只能有一个线程能写该数据，但可以有多个线程同时读数据
	private Object data = null;
	ReadWriteLock rwl = new ReentrantReadWriteLock();

	public void get() {
		rwl.readLock().lock();

		try {
			System.out.println(Thread.currentThread().getName() + " be ready to read data!");
			Thread.sleep((long)Math.random()*1000);
			System.out.println(Thread.currentThread().getName()+" have read data :"+data);
		} catch (InterruptedException e) {
			e.printStackTrace();
		} finally {
			rwl.readLock().unlock();
		}
	}

	public void put(Object data) {
		rwl.writeLock().lock();
		try {
			System.out.println(Thread.currentThread().getName()+" be ready to write data!");
			Thread.sleep((long)Math.random()*1000);
			this.data = data;
			System.out.println(Thread.currentThread().getName()+" have write data :"+data);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			rwl.writeLock().unlock();
		}
	}
}

/*
class CachedData {
	Object data;
	volatile boolean cacheValid;
	final ReentrantReadWriteLock rwl = new ReentrantReadWriteLock();

	void processCachedData() {
		rwl.readLock().lock();	//进来就开始读，上读锁，不允许写
		if (!cacheValid) {		//缓存不可用
			// Must release read lock before acquiring write lock
			rwl.readLock().unlock();	//缓存不可用，立马释放读锁，等待数据
			rwl.writeLock().lock();		//上写锁，不允许读
			try {
				// Recheck state because another thread might have
				// acquired write lock and changed state before we did.
				if (!cacheValid) {	//重新检查缓存状态,rwl.writeLock().lock()会造成线程阻塞，第一个线程成功写入后，其后阻塞的线程需要再次判断
					data = ...
					cacheValid = true;
				}
				// Downgrade by acquiring read lock before releasing write lock
				rwl.readLock().lock();	//降级通过在释放写锁之前获取读锁(锁降级)
			} finally {
				rwl.writeLock().unlock(); // Unlock write, still hold read	释放写锁，保持读锁状态
			}
		}

		try {
			use(data);
		} finally {
			rwl.readLock().unlock();	//释放读锁
		}
	}
}
 */
