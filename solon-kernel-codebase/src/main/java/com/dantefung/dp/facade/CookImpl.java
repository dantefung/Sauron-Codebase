/**
 * Project Name:dantefung
 * File Name:CookImpl.java
 * Package Name:com.dantefung.dp.facade
 * Date:2016-3-16下午7:12:18
 * Copyright (c) 2016, fhlin0611@foxmail.com All Rights Reserved.
 *
*/

package com.dantefung.dp.facade;
/**
 * ClassName:CookImpl <br/>
 * Function: TODO ADD FUNCTION. <br/>
 * Reason:	 TODO ADD REASON. <br/>
 * Date:     2016-3-16 下午7:12:18 <br/>
 * @author   Dante Fung
 * @version  
 * @since    JDK 1.6
 * @see 	 
 */
public class CookImpl implements Cook
{

	@Override
	public String cook(String food)
	{
		System.out.println("厨师正在烹饪：" + food);
		return food;
	}

}

