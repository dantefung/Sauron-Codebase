/*
 * Copyright (C), 2015-2020
 * FileName: CacheDemo
 * Author:   DANTE FUNG
 * Date:     2020/12/1 12:02 上午
 * Description: 缓存示例
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 * DANTE FUNG        2020/12/1 12:02 上午   V1.0.0
 */
package com.dantefung.concurrent;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * @Title: CacheDemo
 * @Description: 缓存示例
 * @author DANTE FUNG
 * @date 2020/12/01 00/02
 * @since JDK1.8
 */
public class CacheDemo {

	private Map<String, Object> cache = new HashMap<>();

	public static void main(String[] args) {

	}

	private ReadWriteLock rwl = new ReentrantReadWriteLock();

	/**
	 * 读读不互斥，读写互斥，写写互斥
	 *
	 * 允许多个线程同时读共享变量；
	 * 只允许一个线程写共享变量；
	 * 如果有一个线程在执行写操作，那么禁止读操作。
	 *
	 * 必须要先将读锁进行解锁，才能给写锁进行加锁。
	 * 锁的降级是支持的，将写锁直接降级成读锁是支持的。
	 * @param key
	 * @return
	 */
	public /*synchronized*/ Object getData(String key) {
		rwl.readLock().lock();
		Object value = null;
		try {
			value = cache.get(key);
			if (value == null) {
				rwl.readLock().unlock();
				rwl.writeLock().lock();
				try {
					if (value == null) {
						value = "aaaa";// 实际是去查询数据数据库
					}
				} finally {
					rwl.writeLock().unlock();
				}
				rwl.readLock().lock();
			}
		} finally {
			rwl.readLock().unlock();
		}
		return value;
	}
}
