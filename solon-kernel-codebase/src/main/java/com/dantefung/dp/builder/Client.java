/**
 * Project Name:dantefung
 * File Name:Client.java
 * Package Name:com.dantefung.dp.builder
 * Date:2016-3-16下午3:24:42
 * Copyright (c) 2016, fhlin0611@foxmail.com All Rights Reserved.
 *
*/

package com.dantefung.dp.builder;
/**
 * ClassName:Client <br/>
 * Function: TODO ADD FUNCTION. <br/>
 * Reason:	 TODO ADD REASON. <br/>
 * Date:     2016-3-16 下午3:24:42 <br/>
 * @author   Dante Fung
 * @version  
 * @since    JDK 1.6
 * @see 	 
 */
public class Client
{
	public static void main(String[] args)
	{
		/*
		 * 链式调用
		 * 1、 先创建一个建造者：new NutritionFacts.Builder();
		 * 2、 选择性的调用建造者内部的方法为可选构造参数赋值。
		 * 
		 * 优点：builder模式模拟了具名的可选参数。
		 * 适用性：如果类的构造器或者静态工厂中具有多个参数，设计这种类时，Builder模式就是中不错的选择。
		 */
		NutritionFacts cocaCola = new NutritionFacts.Builder(240, 8).calories(100)
		.sodium(35).carbohydrate(27).build();
	}
}

