/*
 * Copyright (C), 2015-2020
 * FileName: MLock
 * Author:   DANTE FUNG
 * Date:     2021/2/23 9:53 下午
 * Description: 自定义锁实现
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 * DANTE FUNG        2021/2/23 9:53 下午   V1.0.0
 */
package com.dantefung.concurrent.aqs.s04;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;

/**
 * @Title: MLock
 * @Description: 自定义锁实现
 * @author DANTE FUNG
 * @date 2021/02/23 21/53
 * @since JDK1.8
 */
public class MLock implements Lock {

	private volatile int i = 0;

	@Override
	public void lock() {
		synchronized (this) {
			i = 1;
		}
	}

	@Override
	public void lockInterruptibly() throws InterruptedException {

	}

	@Override
	public boolean tryLock() {
		return false;
	}

	@Override
	public boolean tryLock(long time, TimeUnit unit) throws InterruptedException {
		return false;
	}

	@Override
	public void unlock() {
		synchronized (this) {
			i = 0;
		}
	}

	@Override
	public Condition newCondition() {
		return null;
	}
}
