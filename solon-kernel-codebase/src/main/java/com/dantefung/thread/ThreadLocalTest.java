/*
 * Copyright (C), 2015-2020
 * FileName: ThreadLocalTest
 * Author:   DANTE FUNG
 * Date:     2020/11/26 11:48 下午
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 * DANTE FUNG        2020/11/26 11:48 下午   V1.0.0
 */
package com.dantefung.thread;

import java.util.Random;

/**
 * @Title: ThreadLocalTest
 * @Description:
 * ThreadLocal的应用场景:
 * 订单处理包含一系列操作:
 * 减少库存量、增加一条流水台账、修改总账，这几个操作要在同一个事务中完成，
 * 通常也即同一个线程中进行处理，如果累加公司应收款的操作失败了，则应该把前面的操作回滚，
 * 否则，提交所受操作，这要求这些操作使用相同的数据库连接对象，而这些操作的代码分别位于不同的模块类中。
 *
 * 例如struct2的ActionContext,同一段代码被不同的线程调用运行时，该代码操作的数据是每个线程各自的状态和数据，
 * 对于不同的线程来说，getContext方法拿到的对象都不同，对同一个线程来说，不管调用getContext方法多少次
 * 和再哪个模块中getContext方法，拿到的都是同一个。
 *
 * 实验案例：定义一个全局共享的ThreadLocal变量，然后启动多个线程向该ThreadLocal变量中存储
 * 一个随机值，接着各个线程调用另外其他多个类的方法，这多个类的方法中读取这个ThreadLocal变量的值，
 * 就可以看到多个类再同一个线程中共享同一份数据。
 *
 * 实现对ThreadLocal变量的封装，让外界不要直接操作ThreadLocal变量。
 * 对基本类型的数据的封装，这种应用相对很少见。
 * 对对象类型的数据的封装，比较常见，即让某个类针对不同线程分别创建一个独立的实例对象。
 *
 *
 *
 * @author DANTE FUNG
 * @date 2020/11/26 23/48
 * @since JDK1.8
 */
public class ThreadLocalTest {
	/**
	 * 一个ThreadLocal代表一个变量，故其中里只能放一个数据，你有两个变量都要线程范围内共享，
	 * 则要定义两个ThreadLocal变量。如果有一百个变量要线程共享呢？那请先定义一个对象来装这一百个变量，
	 * 然后在ThreadLocal中存储这个对象。
	 **/
	private static ThreadLocal<Integer> x = new ThreadLocal<Integer>();

	public static void main(String[] args) {
		for (int i = 0; i < 2; i++) {
			new Thread(new Runnable() {
				@Override
				public void run() {
					int data = new Random().nextInt();
					System.out.println(Thread.currentThread().getName() + " has put data: " + data);
					x.set(data);
					MyThreadScopeData.getThreadInstance().setAge(data);
					MyThreadScopeData.getThreadInstance().setName(data+"");

					new A().get();
					new B().get();
				}
			}).start();
		}

		Runtime runtime = Runtime.getRuntime();
		runtime.addShutdownHook(new Thread(new Runnable() {
			@Override
			public void run() {
				System.out.println("虚拟机将死，发送一封邮件...");
			}
		}));
	}

	static class A {
		public void get() {
			int data = x.get();
			System.out.println("A from :" + Thread.currentThread().getName() + " get  data: " + data);
			MyThreadScopeData myData = MyThreadScopeData.getThreadInstance();
			System.out.println("A from :" + Thread.currentThread().getName() + " getMyData:" + myData.getName() + "," + myData.getAge());
		}
	}

	static class B {
		public void get() {
			int data = x.get();
			System.out.println("B from :" + Thread.currentThread().getName() + " get  data: " + data);
			MyThreadScopeData myData = MyThreadScopeData.getThreadInstance();
			System.out.println("B from :" + Thread.currentThread().getName() + " getMyData:" + myData.getName() + "," + myData.getAge());

		}
	}

}

class MyThreadScopeData {
	private static MyThreadScopeData instance = null;// new MyThreadScopeData();
	private static ThreadLocal<MyThreadScopeData> map = new ThreadLocal<>();

	private MyThreadScopeData() {

	}

	public static /*synchronized*/ MyThreadScopeData getThreadInstance() {
		MyThreadScopeData instance = map.get();
		if (instance == null) {
			instance = new MyThreadScopeData();
			map.set(instance);
		}
		return instance;
	}

	private String name;
	private int age;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}
}
