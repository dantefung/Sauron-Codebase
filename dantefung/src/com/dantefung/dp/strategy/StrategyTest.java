/**
 * Project Name:dantefung
 * File Name:StrategyTest.java
 * Package Name:com.dantefung.dp.strategy
 * Date:2016-3-16下午4:49:18
 * Copyright (c) 2016, fhlin0611@foxmail.com All Rights Reserved.
 *
*/

package com.dantefung.dp.strategy;
/**
 * 策略模式用于封装系列的算法，这些算法通常被封装在一个被称为Context的类中，客户端程序可以自由
 * 选择其中一种算法就，或让Context为客户端选择一个最佳的算法----使用策略模式的优势是为了支持
 * 算法的自由切换 。
 * 
 *   考虑如下场景：现在我们正在开发一个网上书店，该书店为了更好地促销，经常需要对图书进行打折促销，
 *   程序需要考虑各种打折促销的计算方法。
 *   
 *   为了实现书店现在所提供的各种打折需求，程序考虑使用如下的方式实现：
 *   
 * // 一段实现discount()方法代码  
 * public double discount(double price)
 * {
 * 	   // 针对不同情况采用不同的算法
 *     switch(getDiscountType())
 *     {
 *     	  case VIP_DISCOUNT:
 *            return vipDiscount(price);
 *            break;
 *        case OLD_DISCOUNT:
 *            return oldDiscount(price);
 *            break;
 *        case SALE_DISCOUNT:
 *            return saleDiscount(price);
 *            break;
 *            
 *        ...
 *            
 *     }
 * }
 *  
 * 
 * 不足：
 * 		程序中的各种打折方法都被直接写进了discount(double price)方法中。
 * 		如果有一天，该书店需要新增一种打折类型呢？那开发人员必须修改至少3处代码：
 *      1、首先需要增加一个常量，该常量代表新增的打折类型。
 *      2、其次需要在switch语句中增加一个case语句。
 *      3、最后开发人员需要实现xxxDiscount方法，用于实现新增的打折算法。
 * 
 * 为了改变这种不好的设计，下面将会选择使用策略模式来实现该功能。
 * 
 * ClassName:StrategyTest <br/>
 * Function: TODO ADD FUNCTION. <br/>
 * Reason:	 TODO ADD REASON. <br/>
 * Date:     2016-3-16 下午4:49:18 <br/>
 * @author   Dante Fung
 * @version  
 * @since    JDK 1.6
 * @see 	 
 */
public class StrategyTest
{
	public static void main(String[] args)
	{
		// 客户端没有选择打折策略类
		DiscountContext dc = new DiscountContext(null);
		double price1 = 79;
		// 使用默认的打折策略
		System.out.println("79元的书默认打折后的价格是：" + dc.getDiscountPrice(price1));
	    // 客户端选择合适的VIP打折策略
		dc.changeDiscount(new VipDiscount());
		double price2 = 89;
		// 使用vip打折得到的打折价格
		System.out.println("89元的书对VIP用户的价格是：" + dc.getDiscountPrice(price2));
	}
	
	/*
	 * 再次考虑前面的需求：当业务需要新增一种打折类型时，系统只需要重新定义一个DiscountStrategy实现类，该实现类实现getDiscount()方法
	 * 用于实现新的打折算法即可。客户端程序需要切换为新的打折策略时，则需要先调用DiscountContext的changeDiscount()方法切换为新的打折策略。
	 * 
	 * 从上面的介绍中可以看出，使用策略模式可以让客户端代码在不同的打折策略之间切换，但也有一个小小遗憾：
	 * 客户端代码需要和不同的策略类耦合。为了弥补不足，我们可以考虑使用配置文件来指定DiscountContext使用哪种打折策略----这就彻底分离了客户端和具体打折策略类。
	 * 
	 * 介绍到这里，相信读者对Hibernate的Dialect会有一点感觉了，这个Dialect类代表各个数据库方言的抽象父类，但不同的数据库持久化访问可能存在一些差别，
	 * 尤其在分页算法上存在较大的差异，Dialect不同子类就代表了一种特定数据库访问策略。为了让客户端代码与具体的数据库、具体的Dialect实现类分离，Hibernate需要在hibernate.cfg.xml
	 * 文件中指定应用所使用的Dialect子类。
	 * 
	 * 与此相似的是，Spring的Resource接口也是一个典型的策略接口，不同的实现类代表了不同的资源访问策略。当然Spring可以非常“智能”地选择合适的Resource实现类，通常来说，Spring可以
	 * 根据前缀来决定使用合适的Resource实现类：还可根据ApplicationContext的实现类来决定使用合适的Resource实现类。
	 *
	 */
}

