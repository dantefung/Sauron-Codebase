/*
 * Copyright (C), 2015-2020
 * FileName: Demo4
 * Author:   fenghaolin@zuzuche.com
 * Date:     2022/6/27 14:24
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 * fenghaolin@zuzuche.com        2022/6/27 14:24   V1.0.0
 */
package com.dantefung.java8.stream;

import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @Title: Demo4
 * @Description:
 * @author DANTE FUNG
 * @date 2022/06/27 14/24
 * @since JDK1.8
 */
@Slf4j
public class Demo4 {

	public static void main(String[] args) {
		List<Integer> hashList = Lists.newArrayList(
				1,2,3,4,5,6,7,8,9,10,
						11,12,13,14,15,16,17,18,19,20,
						21,22,23,24,25,26,27,28,29,30);
		if (CollectionUtils.isNotEmpty(hashList)) {
			int i = 0;
			int chunk = 10;
			int toIndex;
			do {
				int fromIndex = i * chunk;
				toIndex = fromIndex + chunk;
				List<Integer> subList = hashList.stream().skip(fromIndex).limit(chunk).collect(Collectors.toList());
				log.info(">>>>>>>>subList: {}", subList);
				log.info("发送文件迁移事件");
				i++;
			} while (toIndex < hashList.size() - 1);
		}
	}
}
