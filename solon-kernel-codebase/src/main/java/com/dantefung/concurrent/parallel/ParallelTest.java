package com.dantefung.concurrent.parallel;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 并行获取各个数据源的数据合并成一个数据组合
 */
public class ParallelTest {

	/**
	 * 服务器端最佳线程数量=((线程等待时间+线程cpu时间)/线程cpu时间) * cpu数量
	 */
	ExecutorService executor = Executors.newFixedThreadPool(100);
	/**
	 * 获取基本信息
	 *
	 * @return
	 */
	public String getProductBaseInfo(String productId) {
		return productId + "商品基础信息";
	}

	/**
	 * 获取详情信息
	 *
	 * @return
	 */
	public String getProductDetailInfo(String productId) {
		return productId + "商品详情信息";
	}

	/**
	 * 获取sku信息
	 *
	 * @return
	 */
	public String getProductSkuInfo(String productId) {
		return productId + "商品sku信息";
	}

	/**
	 * 取得一个商品的所有信息（基础、详情、sku）
	 *
	 * @param productId
	 * @return
	 */
	public String getAllInfoByProductId(String productId) {
		CompletableFuture<String> f1 = CompletableFuture.supplyAsync(() -> getProductBaseInfo(productId), executor);
		CompletableFuture<String> f2 = CompletableFuture.supplyAsync(() -> getProductDetailInfo(productId), executor);
		CompletableFuture<String> f3 = CompletableFuture.supplyAsync(() -> getProductSkuInfo(productId), executor);
		//等待三个数据源都返回后，再组装数据。这里会有一个线程阻塞
		CompletableFuture.allOf(f1, f2, f3).join();
		try {
			String baseInfo = f1.get();
			String detailInfo = f2.get();
			String skuInfo = f3.get();
			return baseInfo + "" + detailInfo + skuInfo;
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (ExecutionException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 *   并行获取数据的计算
	 */
	@Test
	public void testGetAllInfoParalleByProductId() throws ExecutionException, InterruptedException {
		ParallelTest test = new ParallelTest();
		String info = test.getAllInfoByProductId("1111");
		System.out.println(info);
		Assertions.assertNotNull(info);
	}

	/**
	 *   同步获取执行的处理
	 */
	@Test
	public void testGetAllInfoDirectly() throws ExecutionException, InterruptedException {
		String info1 = getProductBaseInfo("1111");
		String info2 = getProductDetailInfo("1111");
		String info3 = getProductSkuInfo("1111");
		String info = info1 + "" + info2 + info3;
		System.out.println(info);
		Assertions.assertNotNull(info);
	}
}