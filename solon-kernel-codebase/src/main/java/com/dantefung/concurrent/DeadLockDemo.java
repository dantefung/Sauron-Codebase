/*
 * Copyright (C), 2015-2020
 * FileName: DeadLockDemo
 * Author:   DANTE FUNG
 * Date:     2021/2/28 17:56
 * Description: 死锁案例
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 * DANTE FUNG        2021/2/28 17:56   V1.0.0
 */
package com.dantefung.concurrent;

/**
 * @Title: DeadLockDemo
 * @Description: 死锁案例
 * @author DANTE FUNG
 * @date 2021/02/28 17/56
 * @since JDK1.8
 */
public class DeadLockDemo {

	private int balance;

	// 转账
	void transfer(DeadLockDemo target, int amt) {
		// 锁定转出账户
		synchronized (this) {     //①
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			// 锁定转入账户
			synchronized (target) { //②
				if (this.balance > amt) {
					this.balance -= amt;
					target.balance += amt;
				}
			}
		}
	}

	public static void main(String[] args) throws InterruptedException {
		DeadLockDemo a = new DeadLockDemo();
		DeadLockDemo b = new DeadLockDemo();
		new Thread(() -> {
			a.transfer(b, 10);
		}).start();

		new Thread(() -> {
			b.transfer(a, 20);
		}).start();

	}

}