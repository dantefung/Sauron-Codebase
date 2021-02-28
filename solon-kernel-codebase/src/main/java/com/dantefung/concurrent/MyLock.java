package com.dantefung.concurrent;

import java.util.ArrayList;
import java.util.List;
/**
 * @Description: 等待-通知机制优化资源托管方式。
 * wait()方法与sleep()方法的不同之处在于，wait()方法会释放对象的“锁标志”。
 * 当调用某一对象的wait()方法后，会使当前线程暂停执行，并将当前线程放入对象等待池中，
 * 直到调用了notify()方法后，将从对象等待池中移出任意一个线程并放入锁标志等待池中，只有锁标志等待池中的线程可以获取锁标志，
 * 它们随时准备争夺锁的拥有权。当调用了某个对象的notifyAll()方法，会将对象等待池中的所有线程都移动到该对象的锁标志等待池。
 * sleep()方法需要指定等待的时间，它可以让当前正在执行的线程在指定的时间内暂停执行，进入阻塞状态，
 * 该方法既可以让其他同优先级或者高优先级的线程得到执行的机会，也可以让低优先级的线程得到执行机会。
 * 但是sleep()方法不会释放“锁标志”，也就是说如果有synchronized同步块，其他线程仍然不能访问共享数据。
 *
 * wait和sleep区别
 * 1：wait释放资源，sleep不释放资源
 * 2：wait需要被唤醒，sleep不需要
 * 3：wait需要获取到监视器，否则抛异常，sleep不需要
 * 4：wait是object顶级父类的方法，sleep则是Thread的方法
 *
 * 1. wait会释放所有锁而sleep不会释放锁资源.
 * 2. wait只能在同步方法和同步块中使用，而sleep任何地方都可以.
 * 3. wait无需捕捉异常，而sleep需要.（都抛出InterruptedException ，wait也需要捕获异常）
 * 4. wait()无参数需要唤醒，线程状态WAITING；wait(1000L);到时间自己醒过来或者到时间之前被其他线程唤醒，状态和sleep都是TIME_WAITING
 * 两者相同点：都会让渡CPU执行时间，等待再次调度！
 *
 * 上面我们一直强调 wait()、notify()、notifyAll() 方法操作的等待队列是互斥锁的等待队列，
 * 所以如果 synchronized 锁定的是 this，那么对应的一定是 this.wait()、this.notify()、this.notifyAll()；
 * 如果 synchronized 锁定的是 target，那么对应的一定是 target.wait()、target.notify()、target.notifyAll() 。
 * 而且 wait()、notify()、notifyAll() 这三个方法能够被调用的前提是已经获取了相应的互斥锁，所以我们会发现 wait()、notify()、notifyAll() 都是在 synchronized{}内部被调用的。
 * 如果在 synchronized{}外部调用，或者锁定的 this，而用 target.wait() 调用的话，JVM 会抛出一个运行时异常：java.lang.IllegalMonitorStateException。
 *
 *
 * 小试牛刀：一个更好地资源分配器等待 - 通知机制的基本原理搞清楚后，我们就来看看它如何解决一次性申请转出账户和转入账户的问题吧。
 * 在这个等待 - 通知机制中，我们需要考虑以下四个要素。互斥锁：上一篇文章我们提到 Allocator 需要是单例的，所以我们可以用 this 作为互斥锁。
 * 线程要求的条件：
 * 1、转出账户和转入账户都没有被分配过。
 * 2、何时等待：线程要求的条件不满足就等待。
 * 3、何时通知：当有线程释放账户时就通知。
 *
 * @Author: DANTE FUNG
 * @Date: 2021/2/28 21:21
 * @since JDK 1.8
 * @return:
 */
public class MyLock {
	// 测试转账的main方法
	public static void main(String[] args) throws InterruptedException {
//		Account src = new Account(10000);
//		Account target = new Account(10000);
//		CountDownLatch countDownLatch = new CountDownLatch(9999);
//		for (int i = 0; i < 9999; i++) {
//			new Thread(() -> {
//				src.transactionToTarget(1, target);
//				countDownLatch.countDown();
//			}).start();
//		}
//		countDownLatch.await();
//		System.out.println("src=" + src.getBalance());
//		System.out.println("target=" + target.getBalance());
		Account a = new Account(100);
		Account b = new Account(100);
		a.transactionToTarget(10, b);
		b.transactionToTarget(20, a);
		System.out.println("a:" + a.getBalance());
		System.out.println("b:" + b.getBalance());
	}

	static class Account { //账户类
		public Account(Integer balance) {
			this.balance = balance;
		}

		private Integer balance;

		public void transactionToTarget(Integer money, Account target) {//转账方法
			Allocator.getInstance().apply(this, target);
			synchronized (this) {
				this.balance -= money;
				// 模拟耗时操作
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				synchronized (target) {
					target.setBalance(target.getBalance() + money);
				}
			}


			Allocator.getInstance().release(this, target);
		}

		public Integer getBalance() {
			return balance;
		}

		public void setBalance(Integer balance) {
			this.balance = balance;
		}
	}

	static class Allocator { //单例锁类
		private Allocator() {
		}

		private List<Account> locks = new ArrayList<>();

		public synchronized void apply(Account src, Account tag) {
			while (locks.contains(src) || locks.contains(tag)) {
				try {
					this.wait();
				} catch (InterruptedException e) {
				}
			}
			locks.add(src);
			locks.add(tag);
		}

		public synchronized void release(Account src, Account tag) {
			locks.remove(src);
			locks.remove(tag);
			this.notifyAll();
		}

		public static Allocator getInstance() {
			return AllocatorSingle.install;
		}

		static class AllocatorSingle {
			public static Allocator install = new Allocator();
		}
	}
}