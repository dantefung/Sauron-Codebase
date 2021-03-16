/*
 * Copyright (C), 2015-2020
 * FileName: ClientTest
 * Author:   DANTE FUNG
 * Date:     2021/3/16 16:17
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 * DANTE FUNG        2021/3/16 16:17   V1.0.0
 */
package com.dantefung.java8.function;

/**
 * @Title: ClientTest
 * @Description:
 * @author DANTE FUNG
 * @date 2021/03/16 16/17
 * @since JDK1.8
 */
public class ClientTest {

	public static void main(String[] args) {
		Head head = Builder.of(Head::new).with(Head::setActionType, "aaaa").with(Head::setCurrentTime, "bbbb").build();
		System.out.println(head);
	}
}
