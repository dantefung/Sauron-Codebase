/**
 * Project Name:dantefung
 * File Name:PaymentImpl.java
 * Package Name:com.dantefung.dp.facade
 * Date:2016-3-16下午7:08:29
 * Copyright (c) 2016, fhlin0611@foxmail.com All Rights Reserved.
 *
*/

package com.dantefung.dp.facade;
/**
 * ClassName:PaymentImpl <br/>
 * Function: TODO ADD FUNCTION. <br/>
 * Reason:	 TODO ADD REASON. <br/>
 * Date:     2016-3-16 下午7:08:29 <br/>
 * @author   Dante Fung
 * @version  
 * @since    JDK 1.6
 * @see 	 
 */
public class PaymentImpl implements Payment
{

	@Override
	public String pay()
	{
		String food = "快餐";
		System.out.println("你已经向收银员支付了费用，您购买的食物是：" + food);
		return food;
	}

}

