/*
 * Copyright (C), 2015-2020
 * FileName: Main
 * Author:   DANTE FUNG
 * Date:     2021/7/31 20:17
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 * DANTE FUNG        2021/7/31 20:17   V1.0.0
 */
package com.dantefung.nio.reactor.multithread.server;

import java.io.IOException;

/**
 * @Title: Main
 * @Description:
 * @author DANTE FUNG
 * @date 2021/07/31 20/17
 * @since JDK1.8
 */
public class Main {

	public static void main(String[] args) throws IOException {
		new Thread(new Reactor(9090)).start();
	}
}
