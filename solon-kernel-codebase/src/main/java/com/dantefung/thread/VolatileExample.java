package com.dantefung.thread;

import lombok.ToString;

@ToString
public class VolatileExample {
	int a = 0;
	volatile boolean flag = false;

	public static void main(String[] args) throws InterruptedException {

		VolatileExample ve = new VolatileExample();// 堆区
		Thread threadA = new Thread(() -> {
			while (true) {
				ve.writer();
				System.out.println(Thread.currentThread().getName() + " -- " + ve.toString());
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		});
		threadA.start();

		/*Thread threadB = new Thread(() -> {
			while (true) {
				ve.reader();
				try {
					Thread.sleep(1500);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				System.out.println(ve.toString());
			}
		});
		threadB.start();*/
		for (int i = 0; i < 10; i++) {
			new Thread(() -> {
				while (true) {
					ve.reader();
					System.out.println(Thread.currentThread().getName() + " -- " + ve.toString());
					try {
						Thread.sleep(1500);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}).start();
		}
	}

	public void writer() {
		System.out.println(Thread.currentThread().getName() + "执行writer...");
		a = 0; // step 1
		flag = true; // step 2
	}

	public void reader() {
		System.out.println(Thread.currentThread().getName() + "执行reader...");
		if (flag) { // step 3
			System.out.println(a); // step 4
		}
	}
}