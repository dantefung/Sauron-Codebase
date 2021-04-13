/*
 * Copyright (C), 2015-2020
 * FileName: FunctionUtil
 * Author:   DANTE FUNG
 * Date:     2021/3/30 16:50
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 * DANTE FUNG        2021/3/30 16:50   V1.0.0
 */
package com.dantefung.tool;

import lombok.experimental.UtilityClass;

import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.Function;

/**
 * @Title: FunctionUtil
 * @Description:
 * @author DANTE FUNG
 * @date 2021/03/30 16/50
 * @since JDK1.8
 */
@UtilityClass
public class FunctionUtil {

	public static <T,R> R sleepFunction(T t, Function<T, R> callback) {
		R r = null;
		try {
			r =callback.apply(t);
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		return r;
	}

	public static <T, U, R> R sleepFunction(T t,U u, BiFunction<T, U, R> callback) {
		R r = null;
		try {
			r =callback.apply(t, u);
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		return r;
	}

	public static <T, U> void sleepFunction(T t, U u, BiConsumer<T, U> callback) {
		try {
			callback.accept(t, u);
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
