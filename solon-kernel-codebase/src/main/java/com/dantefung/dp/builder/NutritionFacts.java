/**
 * Project Name:dantefung
 * File Name:NutritionFacts.java
 * Package Name:com.dantefung.dp.builder
 * Date:2016-3-16下午3:11:17
 * Copyright (c) 2016, fhlin0611@foxmail.com All Rights Reserved.
 *
*/

package com.dantefung.dp.builder;
/**
 * 本例是《Effective Java》第二章 创建和销毁对象 第2条 遇到多个构造
 * 参数时要考虑使用构造器。
 * 
 * 建造者模式
 * 
 * Nutrition:n.营养，营养学，营养品
 * calory:卡路里
 * sodium:钠
 * carbohydrate：碳水化合物，糖类
 * 
 * ClassName:NutritionFacts <br/>
 * Function: TODO ADD FUNCTION. <br/>
 * Reason:	 TODO ADD REASON. <br/>
 * Date:     2016-3-16 下午3:11:17 <br/>
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
	
	// 公开的静态的内部类
	public static class Builder
	{
		// Required parameters
		private final int servingSize;
		private final int servings;
		
		// Optional parameters - initialized to default values
		private int calories = 0;
		private int fat = 0;
		private int carbohydrate = 0;
		private int sodium = 0;
		
		public Builder(int servingSize, int servings)
		{
			this.servingSize = servingSize;
			this.servings = servings;
		}
		
		public Builder calories(int val)
		{
			calories = val;
			return this;
		}
		
		public Builder fat(int val)
		{
			fat = val;
			return this;
		}
		
		public Builder carbohydrate(int val)
		{
			carbohydrate = val;
			return this;
		}
		
		public Builder sodium(int val)
		{
			sodium = val;
			return this;
		}
		
		public NutritionFacts build()
		{
			return new NutritionFacts(this);
		}
	}
	
	// 私有构造器
	private NutritionFacts(Builder builder)
	{
		servingSize = builder.servingSize;
		servings = builder.servings;
		calories = builder.calories;
		fat = builder.fat;
		sodium = builder.sodium;
		carbohydrate = builder.carbohydrate;
	}
}

