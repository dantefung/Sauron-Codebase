/*
 * Copyright (C), 2015-2020
 * FileName: AopInvocationHandler
 * Author:   DANTE FUNG
 * Date:     2021/3/7 15:12
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 * DANTE FUNG        2021/3/7 15:12   V1.0.0
 */
package com.dantefung.dp.proxy.sample03.framework;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * @Title: AopInvocationHandler
 * @Description:
 * @author DANTE FUNG
 * @date 2021/03/07 15/12
 * @since JDK1.8
 */
public class AopInvocationHandler implements InvocationHandler {

	// 持有被代理的对象实例
	private Object target;

	private Aspect aspect;

	public AopInvocationHandler(Object target, Aspect aspect) {
		this.target = target;
		this.aspect = aspect;
	}

	@Override
	public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
		// 判断当前方法是否需要增强
		if (method.getName().matches(aspect.getPointcut().getMethodPattern())) {
			return this.aspect.getAdvice().invoke(target, method, args);
		}
		// 如果方法不需要增强, 直接调用业务对象的方法.
		return method.invoke(target, args);
	}
}
