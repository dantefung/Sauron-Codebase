package com.dantefung.dp.proxy.sample01;

//目标类，对应Target
public class RealSubject implements Subject {

	public void hello(String str) {
		System.out.println("hello" + str);
	}
}
