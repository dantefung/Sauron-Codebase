/*
 * Copyright (C), 2015-2020
 * FileName: Client
 * Author:   DANTE FUNG
 * Date:     2021/1/8 11:27
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 * DANTE FUNG        2021/1/8 11:27   V1.0.0
 */
package com.dantefung.dp.proxy.sample01;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Proxy;

/**
 * @Title: Client
 * @Description:
 * @author DANTE FUNG
 * @date 2021/01/08 11/27
 * @since JDK1.8
 */
public class Client {

	public static void main(String[] args) throws Exception {
		System.setProperty("sun.misc.ProxyGenerator.saveGeneratedFiles", "true");
		//test01();
		test02();
		//test03();
	}

	private static void test01() {
		RealSubject realSubject = new RealSubject();
		Subject proxy = new ProcessorProxy<Subject>(realSubject).getProxy();
		proxy.hello(", world!!!");
	}

	private static void test02()
			throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
		Subject realSubject = new RealSubject();
		//1.0 获取代理类的类对象，主要设置相同的ClassLoader去加载目标类实现的接口Subject类
		Class<?> proxyClass = Proxy.getProxyClass(Client.class.getClassLoader(), new Class[]{Subject.class});
		//2.0 得到代理类后，就可以通过代理类的处理器句柄来得到参数类型的为InvocationHandler.class的构造器
		final Constructor<?> con = proxyClass.getConstructor(InvocationHandler.class);
		//3.0 获取具体执行方法的句柄处理器，目的通过构造器传入被代理目标类对象，注入到代理类处理器句柄中进行代理调用
		final InvocationHandler handler = new ProcessorProxy(realSubject);
		//4.0 通过构造器创建代理类对象
		Subject subject = (Subject) con.newInstance(handler);
		System.out.println(">>>>>>>>>>>>>: " + subject.getClass());
		//5.0 最后调用方法
		subject.hello(", proxy");
	}

	private static void test03() {
		Subject subject2 = (Subject) Proxy.newProxyInstance(Client.class.getClassLoader(), new Class[]{Subject.class},
				new ProcessorProxy(new RealSubject()));
		System.out.println(">>>>>>>>>>>>>: " + subject2.getClass());
		//调用代理类方法
		subject2.hello(", proxy");
	}
}
