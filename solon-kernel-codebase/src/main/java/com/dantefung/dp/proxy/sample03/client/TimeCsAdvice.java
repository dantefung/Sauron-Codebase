/*
 * Copyright (C), 2015-2020
 * FileName: TimeCsAdvice
 * Author:   DANTE FUNG
 * Date:     2021/3/7 14:13
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 * DANTE FUNG        2021/3/7 14:13   V1.0.0
 */
package com.dantefung.dp.proxy.sample03.client;

import com.dantefung.dp.proxy.sample03.framework.Advice;

import java.lang.reflect.Method;

/**
 * @Title: TimeCsAdvice
 * @Description:
 * @author DANTE FUNG
 * @date 2021/03/07 14/13
 * @since JDK1.8
 */
public class TimeCsAdvice implements Advice {
	@Override
	public Object invoke(Object target, Method method, Object[] args) throws Exception {
		long stime = System.currentTimeMillis();
		Object ret = target;
		method.invoke(target, args);
		long etime = System.currentTimeMillis();
		System.out.println("target: " + target.getClass().getName() + " method: " + method.getName() + " args: " + args + " 执行耗时: " + (etime - stime));
		return ret;
	}
}
