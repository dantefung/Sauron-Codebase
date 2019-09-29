/**
 * Project Name:dantefung
 * File Name:NutritionFacts.java
 * Package Name:com.dantefung.dp.telescoping
 * Date:2016-3-16下午3:52:53
 * Copyright (c) 2016, fhlin0611@foxmail.com All Rights Reserved.
 *
*/

package com.dantefung.dp.telescoping;
/**
 *
 *  本例是《Effective Java》第二章 创建和销毁对象 第2条 遇到多个构造
 * 参数时要考虑使用构造器。
 * 
 * 重叠构造器(Telescoping constructor)
 * 
 * ClassName:NutritionFacts <br/>
 * Function: TODO ADD FUNCTION. <br/>
 * Reason:	 TODO ADD REASON. <br/>
 * Date:     2016-3-16 下午3:52:53 <br/>
 * @author   Dante Fung
 * @version  
 * @since    JDK 1.6
 * @see 	 
 */
public class NutritionFacts
{
	private final int servingSize;
	private final int servings;
	private final int calories;
	private final int fat;
	private final int sodium;
	private final int carbohydrate;
	
	public NutritionFacts(int servingSize, int servings)
	{
		this(servingSize, servings, 0);
	}
	
	public NutritionFacts(int servingSize, int servings, int calories)
	{
		this(servingSize, servings, calories, 0);
	}
	
	public NutritionFacts(int servingSize, int servings, int calories, int fat)
	{
		this(servingSize, servings, calories, fat, 0);
	}
	
	public NutritionFacts(int servingSize, int servings, int calories, int fat, int sodium)
	{
		this(servingSize, servings, calories, fat, sodium, 0);
	}
	
	public NutritionFacts(int servingSize, int servings, int calories, int fat, int sodium, int carbohydrate)
	{
		this.servingSize = servingSize;
		this.servings = servings;
		this.calories = calories;
		this.fat = fat;
		this.sodium = sodium;
		this.carbohydrate = carbohydrate;
	}
}

