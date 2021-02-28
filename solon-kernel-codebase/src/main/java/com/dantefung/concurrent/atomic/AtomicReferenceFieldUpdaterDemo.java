/*
 * Copyright (C), 2015-2020
 * FileName: AtomicReferenceFieldUpdaterDemo
 * Author:   DANTE FUNG
 * Date:     2021/3/1 12:39 上午
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 * DANTE FUNG        2021/3/1 12:39 上午   V1.0.0
 */
package com.dantefung.concurrent.atomic;

import org.junit.Test;

import java.util.UUID;
import java.util.concurrent.atomic.AtomicReferenceFieldUpdater;

/**
 * @Title: AtomicReferenceFieldUpdaterDemo
 * @Description:
 * @author DANTE FUNG
 * @date 2021/03/01 00/39
 * @since JDK1.8
 */
public class AtomicReferenceFieldUpdaterDemo {

	private static class Person {
		String name;
		Person next;

		@Override
		public String toString() {
			return "Person{" + "name='" + name + '\'' + ", next=" + next + '}';
		}
	}

	private volatile Person tail;
	AtomicReferenceFieldUpdater<AtomicReferenceFieldUpdaterDemo, Person> updater = AtomicReferenceFieldUpdater
			.newUpdater(AtomicReferenceFieldUpdaterDemo.class, Person.class, "tail");

	@Test
	public void main() {

		for (int i = 0; i < 100; i++) {
			lock();
		}


	}

	private void lock() {
		Person currentNode = new Person();
		currentNode.name = UUID.randomUUID().toString();
		Person preNode = updater.getAndSet(this, currentNode);

		System.out.println(tail.name);
		if (preNode != null) {
			//step2
			preNode.next = currentNode;
			System.out.println("preNode:" + preNode);
		}
	}
}
