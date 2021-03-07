/*
 * Copyright (C), 2015-2020
 * FileName: TargetObjectImpl
 * Author:   DANTE FUNG
 * Date:     2021/3/7 14:29
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 * DANTE FUNG        2021/3/7 14:29   V1.0.0
 */
package com.dantefung.dp.proxy.sample03.client;

/**
 * @Title: TargetObjectImpl
 * @Description:
 * @author DANTE FUNG
 * @date 2021/03/07 14/29
 * @since JDK1.8
 */
public class TargetObjectImpl implements TargetObject{

	public void showMessage() {
		System.out.println("hello, aop !!");
	}
}
