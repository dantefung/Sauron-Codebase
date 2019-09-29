/**
 * Project Name:dantefung
 * File Name:AbstractNoodle.java
 * Package Name:com.dantefung.dp.bridge
 * Date:2016-3-28下午8:49:14
 * Copyright (c) 2016, fhlin0611@foxmail.com All Rights Reserved.
 *
*/

package com.dantefung.dp.bridge;
/**
 * 接着程序提供了一个AbstractNoodle抽象类，该抽象类将会持有一个Peppery属性，
 * 该属性代表了该面条的辣味风格。程序通过AbstractNoodle组合一个Peppery对象，从而运行了面条在辣味风格
 * 这个维度上的变化；而AbstractNoodle本身也可以包含很多实现类，不同实现类则代表了面条在材料风格这个维度上的变化。
 * ClassName:AbstractNoodle <br/>
 * Function: TODO ADD FUNCTION. <br/>
 * Reason:	 TODO ADD REASON. <br/>
 * Date:     2016-3-28 下午8:49:14 <br/>
 * @author   Dante Fung
 * @version  
 * @since    JDK 1.6
 * @see 	 
 */
public abstract class AbstractNoodle
{
	// 组合一个Peppery变量，用于将该维度的变化独立出来
	protected Peppery style;
	// 每份Noodle必须组合一个Peppery对象
	public AbstractNoodle(Peppery style)
	{
		this.style = style;
	}
	/*
	 * 正如上面的程序所示，上面的AbstractNoodle实例将会与一个Peppery实例组合，
	 * 不同的AbstractNoodle实例与不同的Peppery实例组合，就可完成辣味风格、材料风格两个维度上变化的组合了。
	 * 
	 * 由此可见，AbstractNoodle抽象类可以看做是一个桥梁，它用来“桥接”面条的材料风格的改变和辣味风格的变化，
	 * 使面条的特殊属性得到无绑定的扩充。
	 */
	
	public abstract void eat();
}

