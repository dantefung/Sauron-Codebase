/*
 * Copyright (C), 2015-2020
 * FileName: HeapOOM
 * Author:   DANTE FUNG
 * Date:     2021/7/11 下午6:59
 * Description: VM Args: -Xms20m -Xmx20m -XX:+HeapDumpOnOutOfMemoryError
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 * DANTE FUNG        2021/7/11 下午6:59   V1.0.0
 */
package com.dantefung.jvm;

import java.util.ArrayList;
import java.util.List;

/**
 * @Title: HeapOOM
 * @Description: VM Args: -Xms20m -Xmx20m -XX:+HeapDumpOnOutOfMemoryError
 * 《深入理解java虚拟机》
 *  Xms:设置堆的最小空间大小。
 *  Xmx:设置堆的最大空间大小。
 * @author DANTE FUNG
 * @date 2021/07/11 18/59
 * @since JDK1.8
 */
public class HeapOOM {

	static class OOMObject{

	}

	public static void main(String[] args) {
		List<OOMObject> list = new ArrayList<>();

		while (true) {
			list.add(new OOMObject());
		}
	}
}
