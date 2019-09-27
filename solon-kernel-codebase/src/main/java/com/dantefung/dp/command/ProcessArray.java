/**
 * Project Name:dantefung
 * File Name:ProcessArray.java
 * Package Name:com.dantefung.dp.command
 * Date:2016-3-16下午4:04:29
 * Copyright (c) 2016, fhlin0611@foxmail.com All Rights Reserved.
 *
*/

package com.dantefung.dp.command;
/**
 * 考虑这样一个场景：某个方法需要完成某一个功能，完成这个功能的大部分步骤已经确定了，
 * 但可能有少量具体步骤无法确定，必须等到执行该方法时才可以确定。具体一点，假设有个方法需要
 * 遍历某个数组的数组元素，但无法确定在遍历数组元素时如何吃力这些元素，需要在调用该方法时
 * 指定具体处理行为。
 * 
 * 这个要求看起来有点奇怪：这个方法不仅要求参数可以变化，甚至要求方法执行体的代码也可以变化，
 * 难道我们能 把“处理行为”作为一个参数传入该方法？
 * 
 * 对于这样的需求，我们必须把“处理行为”作为参数传入该方法，而“处理行为”用编程来实现就是一段代码。
 * 那如何把这段代码传入某个方法呢？java暂时又不支持代码块参数。
 * 
 * ========================================================
 * Tips：
 *     在某些编程语言（如Ruby、Perl等）里，确实允许传入一个代码块作为参数
 *     但Java暂时还不支持代码块作为参数，这可能是现阶段Java存在的一个小小
 *     的缺陷。
 * ========================================================
 * 
 * 因为Java不允许代码块单独存在，所以我们实际传入该方法的应该是一个对象，该对象通常是某个接口的匿名
 * 实现类的实例，该接口通常被称为『命令接口』,这种设计方式也被称为命令模式。
 * 
 * ClassName:ProcessArray <br/>
 * Function: TODO ADD FUNCTION. <br/>
 * Reason:	 TODO ADD REASON. <br/>
 * Date:     2016-3-16 下午4:04:29 <br/>
 * @author   Dante Fung
 * @version  
 * @since    JDK 1.6
 * @see 	 
 */
public class ProcessArray
{
	// 定义一个each()方法，用于处理数组，
	public void each(int[] target, Command cmd)
	{
		cmd.process(target);
	}
}

