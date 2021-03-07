package com.dantefung.dp.proxy.sample03.framework;

import java.lang.reflect.Method;

/**
 * @Title: Advice
 * @Description: 通知，增强逻辑
 * @author DANTE FUNG
 * @date 2021/03/07 14/08
 * @since JDK1.8
 */
public interface Advice {

	/**
	 * @Description: 定义一个方法，用户在此提供增强逻辑。
	 * @param target: 被代理的目标对象
	 * @param method: 被代理的目标方法
	 * @param args: 目标方法执行所需要的参数
	 * @Author: DANTE FUNG
	 * @Date: 2021/3/7 14:10
	 * @since JDK 1.8
	 * @return: java.lang.Object
	 */
	Object invoke(Object target, Method method, Object[] args) throws Exception;

}
