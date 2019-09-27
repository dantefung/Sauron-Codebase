/**
 * Project Name:dantefung
 * File Name:OldDiscount.java
 * Package Name:com.dantefung.dp.strategy
 * Date:2016-3-16下午5:05:32
 * Copyright (c) 2016, fhlin0611@foxmail.com All Rights Reserved.
 *
*/

package com.dantefung.dp.strategy;
/**
 * ClassName:OldDiscount <br/>
 * Function: TODO ADD FUNCTION. <br/>
 * Reason:	 TODO ADD REASON. <br/>
 * Date:     2016-3-16 下午5:05:32 <br/>
 * @author   Dante Fung
 * @version  
 * @since    JDK 1.6
 * @see 	 
 */
public class OldDiscount implements DiscountStrategy
{

	// 重写getDiscount(double originPrice)方法，提供旧书打折算法。
	@Override
	public double getDiscount(double originPrice)
	{
		System.out.println("使用旧书折扣.....");
		return originPrice*0.7;
	}

}

