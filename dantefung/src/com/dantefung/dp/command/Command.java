/**
 * Project Name:dantefung
 * File Name:Command.java
 * Package Name:com.dantefung.dp.command
 * Date:2016-3-16下午4:22:16
 * Copyright (c) 2016, fhlin0611@foxmail.com All Rights Reserved.
 *
*/

package com.dantefung.dp.command;
/**
 * 面向接口编程。依赖倒转原则。
 * Command接口里定义了一个process方法，这个方法用于封装“处理行为”，但这个
 * 方法没有方法体----因为现在还无法确定这个处理行为。
 * 
 * 
 * ClassName:Command <br/>
 * Function: TODO ADD FUNCTION. <br/>
 * Reason:	 TODO ADD REASON. <br/>
 * Date:     2016-3-16 下午4:22:16 <br/>
 * @author   Dante Fung
 * @version  
 * @since    JDK 1.6
 * @see 	 
 */
public interface Command
{
	// 接口里定义的process方法用于封装“处理行为”
	void process(int[] target);
}

