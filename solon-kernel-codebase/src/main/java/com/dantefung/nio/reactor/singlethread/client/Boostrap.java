/*
 * Copyright (C), 2015-2020
 * FileName: Boostrap
 * Author:   DANTE FUNG
 * Date:     2021/7/31 上午2:32
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 * DANTE FUNG        2021/7/31 上午2:32   V1.0.0
 */
package com.dantefung.nio.reactor.singlethread.client;

/**
 * @Title: Boostrap
 * @Description:
 * @author DANTE FUNG
 * @date 2021/07/31 02/32
 * @since JDK1.8
 */
public class Boostrap {

	public static void main(String[] args) {
		new Thread(new NIOClient("127.0.0.1", 9090)).start();
		new Thread(new NIOClient("127.0.0.1", 9090)).start();
	}
}
