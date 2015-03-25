package com.dantefung.dp.proxy;

import com.dantefung.dp.proxy.*;

public class Client {
	public static void main(String[] args) throws Exception {
		Tank t = new Tank();
		InvocationHandler h = new TimeHandler(t);//多態。
		
		Moveable m = (Moveable)Proxy.newProxyInstance(Moveable.class, h);
		
		m.move();//调用动态生成的类中用户自定义接口中的方法。
	}
}
