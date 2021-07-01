/*
 * Copyright (C), 2015-2020
 * FileName: MyCountDownLatch
 * Author:   DANTE FUNG
 * Date:     2021/6/6 下午5:38
 * Description: 抄袭CountDownLatch
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 * DANTE FUNG        2021/6/6 下午5:38   V1.0.0
 */
package com.dantefung.thread.aqs;


import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

/**
 * @Title: MyCountDownLatch
 * @Description: 抄袭CountDownLatch
 * @author DANTE FUNG
 * @date 2021/06/06 17/38
 * @since JDK1.8
 */
public class MyCountDownLatch {

	private static final class Sync extends CustomAQS {
		private static final long serialVersionUID = 4982264981922014374L;

		Sync(int count) {
			setState(count);
		}

		int getCount() {
			return getState();
		}

		protected int tryAcquireShared(int acquires) {
			return (getState() == 0) ? 1 : -1;
		}

		protected boolean tryReleaseShared(int releases) {
			// Decrement count; signal when transition to zero
			for (; ; ) {
				int c = getState();
				if (c == 0)
					return false;
				int nextc = c - 1;
				if (compareAndSetState(c, nextc))
					return nextc == 0;
			}
		}
	}

	private final Sync sync;

	public MyCountDownLatch(int count) {
		if (count < 0)
			throw new IllegalArgumentException("count < 0");
		this.sync = new Sync(count);
	}

	/**
	 * 通过获取同步状态进行判断阻塞
	 *
	 * @throws InterruptedException
	 */
	public void await() throws InterruptedException {
		sync.acquireSharedInterruptibly(1);
	}

	public boolean await(long timeout, TimeUnit unit) throws InterruptedException {
		return sync.tryAcquireSharedNanos(1, unit.toNanos(timeout));
	}


	/**
	 * 通过减少同步状态进行倒数
	 */
	public void countDown() {
		sync.releaseShared(1);
	}


	public long getCount() {
		return sync.getCount();
	}


	public String toString() {
		return super.toString() + "[Count = " + sync.getCount() + "]";
	}

}