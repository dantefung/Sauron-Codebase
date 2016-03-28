/**
 * Project Name:dantefung
 * File Name:Product.java
 * Package Name:com.dantefung.dp.observer
 * Date:2016-3-28下午9:51:37
 * Copyright (c) 2016, fhlin0611@foxmail.com All Rights Reserved.
 *
*/

package com.dantefung.dp.observer;
/**
 * 被观察者
 * ClassName:Product <br/>
 * Function: TODO ADD FUNCTION. <br/>
 * Reason:	 TODO ADD REASON. <br/>
 * Date:     2016-3-28 下午9:51:37 <br/>
 * @author   Dante Fung
 * @version  
 * @since    JDK 1.6
 * @see 	 
 */
public class Product extends Observerable
{
	// 定义两个属性
	private String name;
	private double price;
	
	// 无参的构造器
	public Product()
	{

	}
	
	public Product(String name, double price)
	{
		this.name = name;
		this.price = price;
	}
	
	public String getName()
	{
		return name;
	}
	
	// 当程序调用name的setter方法来修改Product的name属性时
	// 程序自然触发该对象上注册的所有观察者
	public void setName(String name)
	{
		this.name = name;
		notifyObservers(name);
	}
	
	public double getPrice()
	{
		return price;
	}
	
	// 当程序调用price的setter方法来修改Product的price属性时
	// 程序自然触发该对象上注册的所有观察者
	public void setPrice(double price)
	{
		this.price = price;
		notifyObservers(price);
	}
}

