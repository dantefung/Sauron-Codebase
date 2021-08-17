/*
 * Copyright (C), 2015-2020
 * FileName: Account3
 * Author:   DANTE FUNG
 * Date:     2021/8/15 8:19 下午
 * Description: 破坏不可抢占条件
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 * DANTE FUNG        2021/8/15 8:19 下午   V1.0.0
 */
package com.dantefung.concurrent;

import java.util.Random;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @Title: Account3
 * @Description: 破坏不可抢占条件
 * @author DANTE FUNG
 * @date 2021/08/15 20/19
 * @since JDK1.8
 */
public class Account3 {

	private int balance;

	private final Lock lock = new ReentrantLock();

	// 转账
	void transfer(Account3 tar, int amt) throws InterruptedException {
		Random random = new Random();
		boolean flag = true;
		while (flag) {
			long randomTime = random.nextLong();
			if (this.lock.tryLock(randomTime, TimeUnit.NANOSECONDS)) {
				try {
					if (tar.lock.tryLock()) {
						try {
							this.balance -= amt;
							tar.balance += amt;
							flag = false;
						} catch (Exception e) {
							e.printStackTrace();
						} finally {
							tar.lock.unlock();
						}
					}
				} catch (Exception e) {
					e.printStackTrace();
				} finally {
					this.lock.unlock();
				}
			}
		}
	}

	@Override
	public String toString() {
		return "Account3{" + "balance=" + balance + ", lock=" + lock + '}';
	}

	public static void main(String[] args) throws InterruptedException {
		Account3 accountA = new Account3();
		Account3 accountB = new Account3();
		accountA.transfer(accountB, 10);
		accountB.transfer(accountA, 20);

		System.out.println("Account A: "+accountA);
		System.out.println("Account B: "+accountB);
	}
}
