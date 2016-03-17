/**
 * Project Name:dantefung
 * File Name:DiscountContext.java
 * Package Name:com.dantefung.dp.strategy
 * Date:2016-3-16下午5:07:34
 * Copyright (c) 2016, fhlin0611@foxmail.com All Rights Reserved.
 *
*/

package com.dantefung.dp.strategy;
/**
 * ClassName:DiscountContext <br/>
 * Function: TODO ADD FUNCTION. <br/>
 * Reason:	 TODO ADD REASON. <br/>
 * Date:     2016-3-16 下午5:07:34 <br/>
 * @author   Dante Fung
 * @version  
 * @since    JDK 1.6
 * @see 	 
 */
public class DiscountContext
{
	// 组合一个DisocuntStrategy对象。合成复用原则。多态。
	private DiscountStrategy strategy;
	// 构造器，传入一个DiscountStrategy对象
	public DiscountContext(DiscountStrategy strategy)
	{
		this.strategy = strategy;
	}
	
	// 根据实际所使用的DiscountStrategy对象得到折扣价
	public double getDiscountPrice(double price)
	{
		// 如果strategy为null，系统自动选择oldDiscount类
		if(strategy == null)
		{
			strategy = new OldDiscount();
		}
		
		return this.strategy.getDiscount(price);
	}
	
	// 提供切换算法的方法
	public void changeDiscount(DiscountStrategy strategy)
	{
		this.strategy = strategy;
	}
}

