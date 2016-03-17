/**
 * Project Name:dantefung
 * File Name:WaiterImpl.java
 * Package Name:com.dantefung.dp.facade
 * Date:2016-3-16下午7:16:36
 * Copyright (c) 2016, fhlin0611@foxmail.com All Rights Reserved.
 *
*/

package com.dantefung.dp.facade;
/**
 * ClassName:WaiterImpl <br/>
 * Function: TODO ADD FUNCTION. <br/>
 * Reason:	 TODO ADD REASON. <br/>
 * Date:     2016-3-16 下午7:16:36 <br/>
 * @author   Dante Fung
 * @version  
 * @since    JDK 1.6
 * @see 	 
 */
public class WaiterImpl implements Waiter
{

	@Override
	public void serve(String food)
	{
		System.out.println("服务员已将" + food + "端过来，请慢用....");
	}

}

