/*
 * Copyright (C), 2015-2020
 * FileName: ForkJoinPoolParallelTest
 * Author:   fenghaolin@zuzuche.com
 * Date:     2022/5/30 11:38
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 * fenghaolin@zuzuche.com        2022/5/30 11:38   V1.0.0
 */
package com.dantefung.concurrent.parallel;

import com.google.common.collect.Lists;
import lombok.Data;

import java.util.List;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinTask;
import java.util.stream.Collectors;

/**
 * @Title: ForkJoinPoolParallelTest
 * @Description:
 * @author DANTE FUNG
 * @date 2022/05/30 11/38
 * @since JDK1.8
 */
public class ForkJoinPoolParallelTest {

	public static void main(String[] args) {
		List<Long> oidList = Lists.newArrayList(1L,2L,3L,4L,5L);
		ForkJoinPool newCustomThreadPool = new ForkJoinPool(50);
		ForkJoinTask<List<SellerOrderListDTO>> forkJoinTask = newCustomThreadPool.submit(
				() ->
						oidList.stream().parallel().map(oid -> {
							SellerOrderListDTO sellerOrderListDTO = new SellerOrderListDTO();
							sellerOrderListDTO.setOid(oid);
							return sellerOrderListDTO;
						}).collect(Collectors.toList())
		);
		forkJoinTask.join();
		List<?> resultList = forkJoinTask.getRawResult();
		System.out.println(resultList);
	}

	@Data
	private static class SellerOrderListDTO {

		private Long oid;

	}
}
