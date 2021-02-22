package com.dantefung.concurrent.aqs;

import lombok.extern.slf4j.Slf4j;

/**
 * 程序入口: 模拟秒杀场景
 * created at 2021年02月21日 11:48:25
 * @author DANTE FUNG
 */
@Slf4j
public class DiyAqsDemo {
	/**
	 * 剩余库存
	 */
	private volatile int stock = 5;

	/**
	 * 模拟用户个数
	 */
	public static final long USER_COUNT = 100;

	public static void main(String[] args) {
		DiyAqsDemo diyAqsDemo = new DiyAqsDemo();
		for (int i = 0; i < USER_COUNT; i++) {
			Thread thread = new Thread(() -> diyAqsDemo.buy(), String.format("第%d位顾客的线程", i + 1));
			thread.start();
		}
	}

	/**
	 * 购买
	 */
	public void buy() {
		try {
			// 模拟购买的耗时
			Thread.sleep(10);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		if (stock > 0) {
			log.info("购买成功，剩余库存为：{}", this.stock);
			stock--;
		} else {
			log.info("购买失败，库存不足，剩余库存为：{}", this.stock);
		}
	}
}
