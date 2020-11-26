/*
 * Copyright (C), 2015-2020
 * FileName: ThreadScopeShareData
 * Author:   DANTE FUNG
 * Date:     2020/11/26 11:35 下午
 * Description: 线程范围内的共享数据
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 * DANTE FUNG        2020/11/26 11:35 下午   V1.0.0
 */
package com.dantefung.thread;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

/**
 * @Title: ThreadScopeShareData
 * @Description: 线程范围内的共享数据。事务管理就是线程范围内共享一个connection.
 * @author DANTE FUNG
 * @date 2020/11/26 23/35
 * @since JDK1.8
 */
public class ThreadScopeShareData {

	private static Map<Thread, Integer> threadData = new HashMap();
	public static void main(String[] args) {
		for (int i = 0; i < 2; i++) {
			new Thread(new Runnable() {
				@Override
				public void run() {
					int data = new Random().nextInt();
					System.out.println(Thread.currentThread().getName() + " has put data: " +data);
					threadData.put(Thread.currentThread(), data);
					new A().get();
					new B().get();
				}
			}).start();
		}
	}

	static class A {
		public void get() {
			int data = threadData.get(Thread.currentThread());
			System.out.println("A from" + Thread.currentThread().getName() + " get  data: " +data);
		}
	}

	static class B {
		public void get() {
			int data = threadData.get(Thread.currentThread());
			System.out.println( "B from " + Thread.currentThread().getName() + " get  data: " +data);
		}
	}
}
