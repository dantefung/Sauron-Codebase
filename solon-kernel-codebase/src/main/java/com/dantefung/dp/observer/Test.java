/**
 * Project Name:dantefung
 * File Name:Test.java
 * Package Name:com.dantefung.dp.observer
 * Date:2016-3-28下午10:04:14
 * Copyright (c) 2016, fhlin0611@foxmail.com All Rights Reserved.
 *
*/

package com.dantefung.dp.observer;
/**
 * ClassName:Test <br/>
 * Function: TODO ADD FUNCTION. <br/>
 * Reason:	 TODO ADD REASON. <br/>
 * Date:     2016-3-28 下午10:04:14 <br/>
 * @author   Dante Fung
 * @version  
 * @since    JDK 1.6
 * @see 	 
 */
public class Test
{
	public static void main(String[] args)
	{
		// 创建一个被观察对象
		Product p = new Product("电视机",176);
		// 创建两个观察者对象
		NameObserver no = new NameObserver();
		PriceObserver po = new PriceObserver();
		// 向被观察者对象上注册两个观察者对象
		p.registerObserver(no);
		p.registerObserver(po);
		// 程序调用setter方法来改变Product的name和price属性
		p.setName("书桌");
		p.setPrice(345f);
	}
}

