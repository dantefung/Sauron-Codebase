/*
 * Copyright (C), 2015-2020
 * FileName: ThreadGroupTest
 * Author:   DANTE FUNG
 * Date:     2021/5/28 16:02
 * Description: 线程组
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 * DANTE FUNG        2021/5/28 16:02   V1.0.0
 */
package com.dantefung.thread;

/**
 * @Title: ThreadGroupTest
 * @Description: 线程组
 * 				ThreadGroup管理着它下⾯的Thread，ThreadGroup是⼀个标准的向下引⽤的树状 结构，
 * 				这样设计的原因是防⽌"上级"线程被"下级"线程引⽤⽽⽆法有效地被GC回 收。
 * @author DANTE FUNG
 * @date 2021/05/28 16/02
 * @since JDK1.8
 */
public class ThreadGroupTest {

	public static void main(String[] args) {
		Thread testThread = new Thread(() -> {
			System.out.println("testThread当前线程组名字：" +
					Thread.currentThread().getThreadGroup().getName());
			System.out.println("testThread线程名字：" +
					Thread.currentThread().getName());
		});
		testThread.start();
		System.out.println("执⾏main⽅法线程名字：" + Thread.currentThread().getName());
	}
}
