/*
 * Copyright (C), 2015-2020
 * FileName: TraditionalTimerTest
 * Author:   DANTE FUNG
 * Date:     2020/11/24 10:35 下午
 * Description: 传统定时器的创建方式
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 * DANTE FUNG        2020/11/24 10:35 下午   V1.0.0
 */
package com.dantefung.task;

import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

/**
 * @Title: TraditionalTimerTest
 * @Description: 传统定时器的创建方式
 * @author DANTE FUNG
 * @date 2020/11/24 22/35
 * @since JDK1.8
 */
public class TraditionalTimerTest {

	private static int count = 0;

	public static void main(String[] args) {
		// 回顾
		/*new Timer().schedule(new TimerTask() {
			@Override
			public void run() {
				System.out.println("bombing!");
			}
		}, 10000, 3000);*/

		// 下面的代码会报错
		/*new Timer().schedule(new TimerTask() {
			@Override
			public void run() {
				System.out.println("bombing!");
				new Timer().schedule(*//*new TimerTask() {
					@Override
					public void run() {
						System.out.println("bombing!");
					}
				}, *//*this,2000);
			}
		}, 2000);


*/
		class MyTimerTask extends TimerTask {

			@Override
			public void run() {
				count = (count + 1) %2;
				System.out.println("bombing!");
				new Timer().schedule(new MyTimerTask(), 2000+2000*count);
			}
		}

		new Timer().schedule(new MyTimerTask(), 2000);

		while (true) {
			System.out.println(new Date().getSeconds());
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}


	}
}
