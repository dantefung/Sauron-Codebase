/**
 * Project Name:dantefung
 * File Name:Peppery.java
 * Package Name:com.dantefung.dp.bridge
 * Date:2016-3-17下午1:18:35
 * Copyright (c) 2016, fhlin0611@foxmail.com All Rights Reserved.
 *
*/

package com.dantefung.dp.bridge;
/**
 * Peppery接口代表了面条在辣味风格这个维度上的变化，不论面条在该维度上
 * 有多少种变化，程序只需要为这几种变化分别提供实现类即可。对于系统而言，辣味风格在这个
 * 维度上的变化是固定的，程序必须面对的，程序使用桥接模式将辣味风格这个维度的变化分离出来了，
 * 避免与牛肉、猪肉材料风格这个维度的变化耦合在一起。
 *
 * ClassName:Peppery <br/>
 * Function: TODO ADD FUNCTION. <br/>
 * Reason:	 TODO ADD REASON. <br/>
 * Date:     2016-3-17 下午1:18:35 <br/>
 * @author   Dante Fung
 * @version  
 * @since    JDK 1.6
 * @see 	 
 */
public interface Peppery
{
	String style();
}

