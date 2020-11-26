/*
 * Copyright (C), 2015-2020
 * FileName: TraditionalThreadSynchronized
 * Author:   DANTE FUNG
 * Date:     2020/11/26 10:44 下午
 * Description: 传统线程同步
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 * DANTE FUNG        2020/11/26 10:44 下午   V1.0.0
 */
package com.dantefung.thread;

/**
 * @Title: TraditionalThreadSynchronized
 * @Description: 传统线程同步
 * @author DANTE FUNG
 * @date 2020/11/26 22/44
 * @since JDK1.8
 */
public class TraditionalThreadSynchronized {

	public static void main(String[] args) {
		new TraditionalThreadSynchronized().init();
	}

	private void init() {
		Outputer outputer = new Outputer();
		new Thread(new Runnable() {
			@Override
			public void run() {
				while (true) {
					try {
						Thread.sleep(10);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					outputer.output("dantefung");
				}
			}
		}).start();

		new Thread(new Runnable() {
			@Override
			public void run() {
				while (true) {
					try {
						Thread.sleep(10);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
//					outputer.output2("Tom");
					outputer.output3("Tom");

				}
			}
		}).start();
	}

	/*class Outputer {
		public void output(String name) {
			synchronized (this) {
				int len = name.length();
				for (int i = 0; i < len; i++) {
					System.out.print(name.charAt(i));
				}
				System.out.println();
			}
		}

		// output2 与 output检查的是同一个锁对象 this.
		public synchronized void output2(String name) {
			int len = name.length();
			for (int i = 0; i < len; i++) {
				System.out.print(name.charAt(i));
			}
			System.out.println();
		}
	}*/

	static class Outputer {
		public void output(String name) {
			/*synchronized (this) {
				int len = name.length();
				for (int i = 0; i < len; i++) {
					System.out.print(name.charAt(i));
				}
				System.out.println();
			}*/
			synchronized (Outputer.class) {
				int len = name.length();
				for (int i = 0; i < len; i++) {
					System.out.print(name.charAt(i));
				}
				System.out.println();
			}
		}

		/**
		 * output2 与 output检查的是同一个锁对象 this.
		 * @param name
		 */
		public synchronized void output2(String name) {
			int len = name.length();
			for (int i = 0; i < len; i++) {
				System.out.print(name.charAt(i));
			}
			System.out.println();
		}

		/**
		 * 该方法能否同步?  只能跟字节码对象
		 * @param name
		 */
		public static synchronized void output3(String name) {
			int len = name.length();
			for (int i = 0; i < len; i++) {
				System.out.print(name.charAt(i));
			}
			System.out.println();
		}
	}
}
