package com.dantefung.test;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public class Test {

	public static void main(String[] args) {
		Animals animals = new Cat();
		animals.run();
	}
	
	
	

}
