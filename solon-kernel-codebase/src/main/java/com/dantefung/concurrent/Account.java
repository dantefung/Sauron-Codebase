package com.dantefung.concurrent;

/**
 * @Description: 但是用一把锁有个问题，就是性能太差，会导致取款、查看余额、修改密码、查看密码这四个操作都是串行的。
 * 用不同的锁对受保护资源进行精细化管理，能够提升性能。这种锁还有个名字，叫细粒度锁。
 * “原子性”的本质是什么？其实不是不可分割，不可分割只是外在表现，其本质是多个资源间有一致性的要求，操作的中间状态对外不可见。
 * 例如，在 32 位的机器上写 long 型变量有中间状态（只写了 64 位中的 32 位），
 * 在银行转账的操作中也有中间状态（账户 A 减少了 100，账户 B 还没来得及发生变化）。
 * 所以解决原子性问题，是要保证中间状态对外不可见。
 * @Author: DANTE FUNG
 * @Date: 2021/2/28 17:29
 * @since JDK 1.8
 * @return:
 */
public class Account {

	// 锁：保护账户余额
	private final Object balLock = new Object();
	// 账户余额
	private Integer balance;
	// 锁：保护账户密码
	private final Object pwLock = new Object();
	// 账户密码
	private String password;

	// 取款
	void withdraw(Integer amt) {
		synchronized (balLock) {
			if (this.balance > amt) {
				this.balance -= amt;
			}
		}
	}

	// 查看余额
	Integer getBalance() {
		synchronized (balLock) {
			return balance;
		}
	}

	// 更改密码
	void updatePassword(String pw) {
		synchronized (pwLock) {
			this.password = pw;
		}
	}

	// 查看密码
	String getPassword() {
		synchronized (pwLock) {
			return password;
		}
	}

	// 转账
	void transfer(Account target, int amt) {
		// 所有的转账操作就变成串行了，在实际的业务场景中是不可行的。性能太差。
		synchronized (Account.class) {
			if (this.balance > amt) {
				this.balance -= amt;
				target.balance += amt;
			}
		}
	}
}