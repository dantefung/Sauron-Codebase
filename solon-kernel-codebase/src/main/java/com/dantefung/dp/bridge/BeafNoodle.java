/**
 * Project Name:dantefung
 * File Name:BeafNoodle.java
 * Package Name:com.dantefung.dp.bridge
 * Date:2016-3-28下午8:59:03
 * Copyright (c) 2016, fhlin0611@foxmail.com All Rights Reserved.
 *
*/

package com.dantefung.dp.bridge;
/**
 * 从PorkyNoodle和BeafNoodle中可以看出：AbstractNoodle的两个
 * 具体实现类实现eat()方法时，既组合了材料风格的变化，也组合了辣味风格的变化，从而可表现出两个维度上的
 * 变化。
 * ClassName:BeafNoodle <br/>
 * Function: TODO ADD FUNCTION. <br/>
 * Reason:	 TODO ADD REASON. <br/>
 * Date:     2016-3-28 下午8:59:03 <br/>
 * @author   Dante Fung
 * @version  
 * @since    JDK 1.6
 * @see 	 
 */
public class BeafNoodle extends AbstractNoodle
{

	public BeafNoodle(Peppery peppery)
	{
		super(peppery);
	}

	@Override
	public void eat()
	{
		System.out.println("这是一碗美味的牛肉面条," + super.style.style());
	}

}

