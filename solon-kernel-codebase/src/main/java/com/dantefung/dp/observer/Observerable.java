/**
 * Project Name:dantefung
 * File Name:Observerable.java
 * Package Name:com.dantefung.dp.observer
 * Date:2016-3-28下午9:43:12
 * Copyright (c) 2016, fhlin0611@foxmail.com All Rights Reserved.
 *
*/

package com.dantefung.dp.observer;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * ClassName:Observerable <br/>
 * Function: TODO ADD FUNCTION. <br/>
 * Reason:	 TODO ADD REASON. <br/>
 * Date:     2016-3-28 下午9:43:12 <br/>
 * @author   Dante Fung
 * @version  
 * @since    JDK 1.6
 * @see 	 
 */
public abstract class Observerable
{
	// 用一个List来保存该对象上所有绑定的事件监听器
	List<Observer> observers = new ArrayList<Observer>();
	// 定义一个方法，用于从该主题上注册观察者
	public void registerObserver(Observer o)
	{
		observers.add(o);
	}
	
	// 定义一个方法，用于从该主题中删除观察者
	public void removeObserver(Observer o)
	{
		observers.remove(o);
	}
	
	// 通知该主题上注册的所有观察者
	public void notifyObservers(Object value)
	{
		// 遍历注册到该被观察者上的所有观察者
		for(Iterator it = observers.iterator();it.hasNext();)
		{
			Observer o = (Observer)it.next();
			// 显示每个观察者的update()方法
			o.update(this, value);
		}
	}
}

