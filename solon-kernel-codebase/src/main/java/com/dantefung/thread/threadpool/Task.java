/*
 * Copyright (C), 2015-2020
 * FileName: Task
 * Author:   DANTE FUNG
 * Date:     2021/9/26 17:52
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 * DANTE FUNG        2021/9/26 17:52   V1.0.0
 */
package com.dantefung.thread.threadpool;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.atomic.AtomicLong;

/**
 * @Title: Task
 * @Description:
 * @author DANTE FUNG
 * @date 2021/09/26 17/52
 * @since JDK1.8
 */
@Slf4j
public class Task implements Runnable {

	private final AtomicLong count = new AtomicLong(0L);

	@Override
	public void run() {
		log.info("running_" + count.getAndIncrement());
	}
}
