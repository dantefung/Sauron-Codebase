/*
 * Copyright (C), 2015-2020
 * FileName: Aspect
 * Author:   DANTE FUNG
 * Date:     2021/3/7 14:36
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 * DANTE FUNG        2021/3/7 14:36   V1.0.0
 */
package com.dantefung.dp.proxy.sample03.framework;

/**
 * @Title: Aspect
 * @Description: 切面
 * @author DANTE FUNG
 * @date 2021/03/07 14/36
 * @since JDK1.8
 */
public class Aspect {

	private Advice advice;

	private Pointcut pointcut;

	public Aspect(Advice advice, Pointcut pointcut) {
		this.advice = advice;
		this.pointcut = pointcut;
	}

	public Advice getAdvice() {
		return advice;
	}

	public void setAdvice(Advice advice) {
		this.advice = advice;
	}

	public Pointcut getPointcut() {
		return pointcut;
	}

	public void setPointcut(Pointcut pointcut) {
		this.pointcut = pointcut;
	}
}
