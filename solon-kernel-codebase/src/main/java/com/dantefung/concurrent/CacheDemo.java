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
 * 1、全量初始化
 * 2、按需加载
 *
 * 数据同步:
 * 1、超时机制。所谓超时机制指的是加载进缓存的数据不是永久有效的，而是
 * 有时效的，当缓存的数据超过时效，也就是超时之后，
 * 这条数据在缓存中就失效了。而访问缓存中时效的数据，会出发缓存重新从
 * 源头把数据加载进内存。
 *
 * 2、MySQL作为数据源头，可以通过近实时地解析binlog来识别数据是否发生变化，
 * 如果发生了变化就将最新的数据推送给缓存。
 *
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
	 * 按需加载：
	 *
	 * 读读不互斥，读写互斥，写写互斥
	 *
	 * 允许多个线程同时读共享变量；
	 * 只允许一个线程写共享变量；
	 * 如果有一个线程在执行写操作，那么禁止读操作。
	 *
	 * 不支持锁升级
	 * 支持锁降级
	 *
	 * 必须要先将读锁进行解锁，才能给写锁进行加锁。
	 * 锁的降级是支持的，将写锁直接降级成读锁是支持的。
	 * @param key
	 * @return
	 */
	public /*synchronized*/ Object getData(String key) {
		// 获取读锁
		rwl.readLock().lock();
		Object value = null;
		try {
			value = cache.get(key);
			if (value == null) {
				// 释放读锁, 因为不允许读锁的升级
				rwl.readLock().unlock();
				// 获取写锁
				rwl.writeLock().lock();
				try {
					// 再次验证
					// 其他线程可能已经查询过数据库,就没必要再查询数据库了.
					if (value == null) {
						value = "aaaa";// 实际是去查询数据数据库
					}
					// 释放写锁前，降级为读锁
					// 降级是可以
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
