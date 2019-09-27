package com.dantefung.dp.proxy;

import java.lang.reflect.Method;
import com.dantefung.dp.proxy.InvocationHandler;

public class TimeHandler implements InvocationHandler {
	private Object target;
	
	public TimeHandler(Object target)
	{
		super();
		this.target = target;
	}
	
	/**方法的具体实现交由用户**/
	@Override
	public void invoke(Object o, Method m)
	{
		long start = System.currentTimeMillis();
		System.out.println("starttime:" + start);
		System.out.println(o.getClass().getName());
		try
		{
			/**调用java.lang.reflect.Method.invoke方法，调用目标对象的move()方法。或者说实现了用户自定义接口的一个具体实现类中的方法。**/
			m.invoke(target);//Open Declaration Object java.lang.reflect.Method.invoke(Object obj, Object... args) 
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		long end = System.currentTimeMillis();
		System.out.println("time:" + (end-start));
	}
}
