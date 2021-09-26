/*
 * Copyright (C), 2015-2020
 * FileName: UserThreadFactory
 * Author:   DANTE FUNG
 * Date:     2021/9/26 17:45
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 * DANTE FUNG        2021/9/26 17:45   V1.0.0
 */
package com.dantefung.thread.threadpool;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @Title: UserThreadFactory
 * @Description:
 * @author DANTE FUNG
 * @date 2021/09/26 17/45
 * @since JDK1.8
 */
@Slf4j
public class UserThreadFactory implements ThreadFactory {

	private final String namePrefix;
	private final AtomicInteger nextId = new AtomicInteger(1);

	// 自定义线程组名称，在使用jstack 来排查问题时，非常有帮助
	UserThreadFactory(String whatFeatureOfGroup) {
		this.namePrefix = "UserThreadFacotry's " + whatFeatureOfGroup + "-Worker-";
	}

	@Override
	public Thread newThread(Runnable r) {
		String name = this.namePrefix + nextId.getAndIncrement();
		Thread thread = new Thread(null, r, name, 0);
		log.debug(thread.getName());
		return thread;
	}

}
