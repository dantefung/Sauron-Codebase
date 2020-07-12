package com.dantefung.concurrent.basic;

import java.util.ArrayList;
import java.util.List;

public class VolatileExample {

	int x = 0;

	/**
	 * 导致可见性是因为缓存。  解决可见性的办法就是禁用缓存。
	 * 导致有序性是因为编译优化。 解决办法: 禁用编译优化。
	 * volatile
	 * 精确地说就是，编译器在用到这个变量时必须每次都小心地重新读取这个变量的值，而不是使用保存在寄存器里的备份。
	 * 古老的C语言里边也有，它最原始的语义就是禁用CPU缓存。
	 * 告诉编译器，对这个变量的读写，不能使用CPU缓存，必须从内存中读取或者写入。
	 *
	 */
	volatile boolean v = false;

	public void writer() {
		x = 42;
		v = true;
	}

	public void reader() {
		if (v == true) {
			// 这里的x会是多少呢?
			System.out.println(x);
		}
	}

	public static void main(String[] args) {
		VolatileExample ve = new VolatileExample();
		List<Thread> threads = new ArrayList<Thread>();
/*		threads.add(new Thread(()->{
			ve.writer();
		},"thread-0"));
		threads.add(new Thread(() -> {
			ve.reader();
		}, "thread-1"));
		threads.forEach(t->{
			t.start();
		});*/

		threads.add(new Thread(()->{
			System.out.println(Thread.currentThread().getName()+" start!");
			ve.reader();
			System.out.println(Thread.currentThread().getName()+" end!");
		},"thread-1"));
		threads.add(new Thread(() -> {
			System.out.println(Thread.currentThread().getName()+" start!");
			ve.writer();
			System.out.println(Thread.currentThread().getName()+" end!");
		}, "thread-0"));
		threads.forEach(t->{
			t.start();
		});

	}

	/**
	 * 直觉上看，应该是42，那实际应该是多少呢?
	 *
	 * 这个要看Java的版本，如果低于1.5版本上运行，x可能是42，也可能是0；
	 * 如果在1.5以上的版本上运行，x就是等于42.
	 *
	 * 分析一下，为什么1.5以前的版本会出现x=0的情况呢?
	 * 我相信你一定想到了，变量x可能被CPU缓存导致可见性问题。
	 *
	 * 这个问题在1.5版本已经被圆满解决了。
	 *
	 * Java内存模型在1.5版本对volatile语义进行了增强。
	 *
	 * 怎么增强的呢?
	 *
	 * 答案是一项Happens-Before规则。
	 *
	 * 如何理解Happens-Before呢?
	 *
	 * 它真正要表达的意思是: 前面一个操作的结果对后续操作是可见的。
	 *
	 * 正式的说法是:
	 *
	 *     Happens-Before约束了编译器的优化行为，虽允许编译器优化，但是要求编译器优化后要一定遵循Happens-Before规则。
	 *
	 *
	 *     1.程序的顺序性规则
	 *
	 *     这条规则是指在一个线程中，按照程序顺序，前面的操作Happens-Before于后续的任意操作。
	 *
	 *     2.volatile变量规则
	 *     （给程序员提供了按需禁用的关键字，禁用缓存，但是有顺序问题，所以要约束一下，写 优先于 读）
	 *     这条规则是指对一个volatile变量的写操作，Happens-Before于后续对这个变量的读操作。
	 */
}
