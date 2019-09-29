/**
 * Project Name:dantefung
 * File Name:NameObserver.java
 * Package Name:com.dantefung.dp.observer
 * Date:2016-3-28下午9:57:34
 * Copyright (c) 2016, fhlin0611@foxmail.com All Rights Reserved.
 *
*/

package com.dantefung.dp.observer;

import javax.swing.JFrame;
import javax.swing.JLabel;

/**
 * 名称观察者
 * ClassName:NameObserver <br/>
 * Function: TODO ADD FUNCTION. <br/>
 * Reason:	 TODO ADD REASON. <br/>
 * Date:     2016-3-28 下午9:57:34 <br/>
 * @author   Dante Fung
 * @version  
 * @since    JDK 1.6
 * @see 	 
 */
public class NameObserver implements Observer
{
	
	// 观察者必须实现的update方法
	@Override
	public void update(Observerable o, Object arg)
	{
		if(arg instanceof String)
		{
			// 产品名称改变值在name中
			String name = (String)arg;
			// 启动一个Jframe窗口来显示被观察对象的状态改变
		/*	JFrame f = new JFrame("观察者");
			JLabel l = new JLabel("名称改变为：" + name);
			f.add(l);
			f.pack();
			f.setVisible(true);*/
			System.out.println("名称观察者："+ o + "物品名称已经改变为：" + name);
		}
	}

}

