package com.dantefung.concurrent;

import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

/**
 * @Description: 破坏占用且等待条件
 * 哲学家就餐经典解法，托管者。 锁的范围变了，所以场景不同性能也会有差异，并发量小的话，性能还会变差
 * 应该杜绝，能不用就不用。循环很消耗CPU
 * 实际应用中，数据库，而且只能是数据库
 *
 * （占用且等待），一次性可以申请所有的资源，这样就不存在等待了
 * @Author: DANTE FUNG
 * @Date: 2021/2/28 20:27
 * @since JDK 1.8
 * @return:
 */
class Allocator {
	private List<Object> als = new ArrayList<>();

	// 一次性申请所有资源
	synchronized boolean apply(Object from, Object to) {
		if (als.contains(from) || als.contains(to)) {
			return false;
		} else {
			als.add(from);
			als.add(to);
		}
		return true;
	}

	// 归还资源
	synchronized void free(Object from, Object to) {
		als.remove(from);
		als.remove(to);
	}
}

class Account1 {
	// actr应该为单例
	private Allocator actr;
	@Getter
	private int balance;

	public Account1(Allocator actr, int balance) {
		this.actr = actr;
		this.balance = balance;
	}

	// 转账
	void transfer(Account1 target, int amt) {
		// 一次性申请转出账户和转入账户，直到成功
		while (!actr.apply(this, target))
			;
		try {
			// 锁定转出账户
			synchronized (this) {
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				// 锁定转入账户
				synchronized (target) {
					if (this.balance > amt) {
						this.balance -= amt;
						target.balance += amt;
					}
				}
			}
		} finally {
			actr.free(this, target);
		}
	}
}

class ClientTest {

	private static final Allocator ALLOCATOR = new Allocator();

	public static void main(String[] args) {
		Account1 a = new Account1(ALLOCATOR, 100);
		Account1 b = new Account1(ALLOCATOR, 100);
		a.transfer(b, 10);
		b.transfer(a, 20);
		System.out.println("a:" + a.getBalance());
		System.out.println("b:" + b.getBalance());
	}


	// 转账操作，实际开发中都是用数据库事务+乐观锁的方式解决的。
}