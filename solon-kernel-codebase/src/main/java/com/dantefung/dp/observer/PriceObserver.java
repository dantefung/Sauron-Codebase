/**
 * Project Name:dantefung
 * File Name:PriceObserver.java
 * Package Name:com.dantefung.dp.observer
 * Date:2016-3-28下午10:02:20
 * Copyright (c) 2016, fhlin0611@foxmail.com All Rights Reserved.
 *
*/

package com.dantefung.dp.observer;
/**
 * ClassName:PriceObserver <br/>
 * Function: TODO ADD FUNCTION. <br/>
 * Reason:	 TODO ADD REASON. <br/>
 * Date:     2016-3-28 下午10:02:20 <br/>
 * @author   Dante Fung
 * @version  
 * @since    JDK 1.6
 * @see 	 
 */
public class PriceObserver implements Observer
{
	// 实现观察者必须实现的update（）方法
	@Override
	public void update(Observerable o, Object arg)
	{
		if(arg instanceof Double)
		{
			System.out.println("价格观察者：" + o + "物品价格已经改变为：" + arg);
		}
	}

}

