package com.dantefung.dp.proxy;

import java.lang.reflect.Method;
//方法处理器。具体的实现留给用户。不写死在代码里面。
public interface InvocationHandler {
	public void invoke(Object o, Method m);
}
