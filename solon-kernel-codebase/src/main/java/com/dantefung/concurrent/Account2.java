/*
 * Copyright (C), 2015-2020
 * FileName: Account2
 * Author:   DANTE FUNG
 * Date:     2021/2/28 8:27 下午
 * Description: 破坏循环等待条件
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 * DANTE FUNG        2021/2/28 8:27 下午   V1.0.0
 */
package com.dantefung.concurrent;

import lombok.Getter;

/**
 * @Title: Account2
 * @Description: 破坏循环等待条件
 * 哲学家就餐经典解法，资源排序
 *
 * （循环等待），可以靠按顺序申请资源来预防。所谓按顺序申请，就是指资源本身有线性顺序，申请的时候可以先申请资源序号小的，再申请大的，这样线性化后自然不存在循环了
 * @author DANTE FUNG
 * @date 2021/02/28 20/27
 * @since JDK1.8
 */
public class Account2 {

	private int id;
	@Getter
	private int balance;

	public Account2(int id, int balance) {
		this.id = id;
		this.balance = balance;
	}

	// 转账
	void transfer(Account2 target, int amt) {
		Account2 left = this;//       ①
		Account2 right = target;   // ②
		if (this.id > target.id) { //③
			left = target;           //④
			right = this;           // ⑤
		}                          //⑥
		// 锁定序号小的账户
		synchronized (left) {
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			// 锁定序号大的账户
			synchronized (right) {
				if (this.balance > amt) {
					this.balance -= amt;
					target.balance += amt;
				}
			}
		}
	}

	public static void main(String[] args) {
		Account2 a = new Account2(1, 100);
		Account2 b = new Account2(2, 100);
		a.transfer(b, 10);
		b.transfer(a, 20);
		System.out.println("a:" + a.getBalance());
		System.out.println("b:" + b.getBalance());
	}
	/**
	 * 在破坏占用且等待的案例中，为何申请完两个账户的资源后还需要再分别锁定this和target账户呢？
	 * 因为还存在其他业务啊 比如客户取款
	 * 这个时候也是对全局变量balance做操作
	 * 如果不加锁 并发情况下会出问题
	 *
	 *
	 * 为什么按序申请资源就可以破坏循环等待条件呢？
	 * 循环等待，一定是A->B->C->...->N->A形成环状。
	 * 如果按需申请，是不允许N->A出现的，只能N->P。没有环状，也就不会死锁了。
	 *
	 *
	 * 看了老师的讲解学到了很多，联想了下实际转账业务，应该是数据库来实现的，假如有账户表account，利用mysql的悲观锁select ...for update对a，b两条数据锁定，这时也有可能发生死锁，按照您讲到的第三种破坏循环等待的方式，按照id的大小顺序依次锁定。
	 *
	 * 并发情况下，这些代码的加锁对象并不是同一个，所以是有问题的。-- 不同的线程都获取到了账户A的实例对象，但这些实例对象不是同一个。
	 *
	 * 希望所有读者都能看透这个，多线程对账户A，B实例加锁时一定要保证是同一个实例对象，就像在数据库表中通过select * from account where account_id = ? for update 加锁一样，锁住的是同一条账户记录。
	 *
	 * 虽然看起来 while(!actr.apply(this, target));只是锁住了两个对象，但是因为actr是一个单例的对象，这个方法在执行的时候也需要锁住actr，在多线程状态下也相当于是串行化了，那么这和加上一个Account.class的类锁的串行化有什么区别吗?请老师赐教，谢谢。
	 * 作者回复: 有区别，如果转账操作很耗时，那么a-b,c-d能并行还是有价值的
	 *
	 *
	 */
}
