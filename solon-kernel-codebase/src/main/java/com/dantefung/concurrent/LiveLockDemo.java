/*
 * Copyright (C), 2015-2020
 * FileName: LiveLockDemo
 * Author:   DANTE FUNG
 * Date:     2021/8/15 8:33 下午
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 * DANTE FUNG        2021/8/15 8:33 下午   V1.0.0
 */
package com.dantefung.concurrent;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @Title: LiveLockDemo
 * @Description: 活锁示例
 * @author DANTE FUNG
 * @date 2021/08/15 20/33
 * @since JDK1.8
 */
public class LiveLockDemo {

	static class Account {

		private int balance;
		private Lock lock = new ReentrantLock();

		// 转账
		void transfer(Account tar, int amt) {
			while (true) {
				try {
					if (this.lock.tryLock()) {
						try {
							if (tar.lock.tryLock()) {
								this.balance -=amt;
								tar.balance += amt;
							}
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

	public static void main(String[] args) {
		Account accountA = new Account();
		Account accountB = new Account();
		accountA.transfer(accountB, 10);
		accountB.transfer(accountA, 20);
	}
}
