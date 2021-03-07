/*
 * Copyright (C), 2015-2020
 * FileName: Pointcut
 * Author:   DANTE FUNG
 * Date:     2021/3/7 14:22
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 * DANTE FUNG        2021/3/7 14:22   V1.0.0
 */
package com.dantefung.dp.proxy.sample03.framework;

/**
 * @Title: Pointcut
 * @Description: 用户来指定在哪些类的哪些方法上进行增强。
 * @author DANTE FUNG
 * @date 2021/03/07 14/22
 * @since JDK1.8
 */
public class Pointcut {

	// 类名匹配模式 (正则表达式)
	private String classPattern;
	// 方法名匹配模式 (正则表达式)
	private String methodPattern;


	public Pointcut(String classPattern, String methodPattern) {
		super();
		this.classPattern = classPattern;
		this.methodPattern = methodPattern;
	}

	public String getClassPattern() {
		return classPattern;
	}

	public void setClassPattern(String classPattern) {
		this.classPattern = classPattern;
	}

	public String getMethodPattern() {
		return methodPattern;
	}

	public void setMethodPattern(String methodPattern) {
		this.methodPattern = methodPattern;
	}
}
