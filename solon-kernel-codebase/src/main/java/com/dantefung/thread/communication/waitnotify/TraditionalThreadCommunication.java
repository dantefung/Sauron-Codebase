/*
 * Copyright (C), 2015-2020
 * FileName: TraditionalThreadCommunication
 * Author:   DANTE FUNG
 * Date:     2020/11/26 11:03 下午
 * Description: 传统线程通信
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 * DANTE FUNG        2020/11/26 11:03 下午   V1.0.0
 */
package com.dantefung.thread.communication.waitnotify;

/**
 * @Title: TraditionalThreadCommunication
 * @Description: 传统线程通信
 * @author DANTE FUNG
 * @date 2020/11/26 23/03
 * @since JDK1.8
 */
public class TraditionalThreadCommunication {

	public static void main(String[] args) {

		final Bussiness bussiness = new Bussiness();
		new Thread(new Runnable() {
			@Override
			public void run() {
				for (int i = 0; i <50 ; i++) {
						bussiness.sub(i);
				}
			}
		}).start();
		for (int i = 0; i <50 ; i++) {
			bussiness.main(i);
		}
	}
}

class Bussiness {

	private boolean bShouldSub = true;
	public synchronized void sub(int i) {
		/*if (!bShouldSub) {
			try {
				this.wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}*/
		// 参见wait()方法的注释说明，用while是为了防止假唤醒.
		while (!bShouldSub) {
			try {
				this.wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		for (int j = 0; j < 10; j++) {
			System.out.println("sub thread sequece of " + j + ", loop of " + i);
		}
		bShouldSub = false;
		this.notify();
	}

	public synchronized void main(int i) {
		/*if (bShouldSub) {
			try {
				this.wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}*/
		while (bShouldSub) {
			try {
				this.wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}

		for (int j = 0; j < 100; j++) {
			System.out.println("main thread sequece of " + j + ", loop of " + i);
		}
		bShouldSub = true;
		this.notify();
	}

}
