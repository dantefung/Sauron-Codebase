package com.dantefung.concurrent.c015;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 1.主要原理
 *
 *   CAS操作
 *
 * 在进行数据更新的时候，会进行与内存中的地址进行比较，若预期值与内存中的值相同，则进行数据上的更新，若值不同，则更新失败，  CAS就是Compare and Swap的意思，比较并操作
 *
 * AtomicInteger主要是调用了Unsafe类中的  compareAndSwapInt 方法
 *
 * 2.源码：
 *
 * public final int incrementAndGet() {
 *         for (;;) {
 *             int current = get();
 *             int next = current + 1;
 *             if (compareAndSet(current, next))
 *                 return next;
 *         }
 *     }
 * public final boolean compareAndSet(int expect, int update) {
 *         return unsafe.compareAndSwapInt(this, valueOffset, expect, update);
 *     }
 */
public class T {

	// 只保证了可见性.禁止编译器优化.
	/**
	 * 导致可见性是因为缓存。  解决可见性的办法就是禁用缓存。
	 * 导致有序性是因为编译优化。 解决办法: 禁用编译优化。
	 * volatile
	 * 精确地说就是，编译器在用到这个变量时必须每次都小心地重新读取这个变量的值，而不是使用保存在寄存器里的备份。
	 * 古老的C语言里边也有，它最原始的语义就是禁用CPU缓存。
	 * 告诉编译器，对这个变量的读写，不能使用CPU缓存，必须从内存中读取或者写入。
	 *
	 */
	/*volatile*/ // int count = 0;

	AtomicInteger count = new AtomicInteger(0);

	/*synchronized*/ void m() {
		for (int i = 0; i < 10000; i++) {
			count.incrementAndGet();// 用来替代count++的
		}
	}

	public static void main(String[] args) {
		T t = new T();

		List<Thread> threads = new ArrayList<Thread>();

		for (int i = 0; i < 10; i++) {
			threads.add(new Thread(t::m, "thread" + i));
		}

		threads.forEach(o -> o.start());

		threads.forEach(o->{
			try {
				/**
				 * Java中如何让多线程按照自己指定的顺序执行？
				 *
				 */
				o.join();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		});

		System.out.println(t.count.get());
	}
}
