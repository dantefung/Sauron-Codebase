package com.dantefung.dp.proxy;

import java.lang.reflect.Constructor;
import java.lang.reflect.Proxy;

import com.dantefung.dp.iterator.Collection;

public class ProxyTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Class clazzProxy1 = Proxy.getProxyClass(Collection.class.getClassLoader(),Collection.class);
		System.out.println(clazzProxy1.getName());
		
		System.out.println("-------------begin constructors list-----------------");
		/*$Proxy0() $Proxy(InvocationHandler,int)*/
		Constructor[] constructors = clazzProxy1.getConstructors();
	}

}
