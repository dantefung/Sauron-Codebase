/**
 * Project Name:dantefung
 * File Name:DiscountStrategy.java
 * Package Name:com.dantefung.dp.strategy
 * Date:2016-3-16下午5:02:18
 * Copyright (c) 2016, fhlin0611@foxmail.com All Rights Reserved.
 *
*/

package com.dantefung.dp.strategy;
/**
 * ClassName:DiscountStrategy <br/>
 * Function: TODO ADD FUNCTION. <br/>
 * Reason:	 TODO ADD REASON. <br/>
 * Date:     2016-3-16 下午5:02:18 <br/>
 * @author   Dante Fung
 * @version  
 * @since    JDK 1.6
 * @see 	 
 */
public interface DiscountStrategy
{
	
	// 定义一个用于计算打折价的方法
	double getDiscount(double originPrice);
}

