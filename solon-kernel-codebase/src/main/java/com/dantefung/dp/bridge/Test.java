/**
 * Project Name:dantefung
 * File Name:Test.java
 * Package Name:com.dantefung.dp.bridge
 * Date:2016-3-17下午1:18:53
 * Copyright (c) 2016, fhlin0611@foxmail.com All Rights Reserved.
 *
*/

package com.dantefung.dp.bridge;
/**
 *   桥接模式是一种结构型模式，它主要应对的是：由于实际的需要，某个类具有两个或两个以上的维度变化，
 * 如果只是使用继承将无法实现这种需要，或者使得设计变得相当臃肿。
 *   举例来说，假设现在我们需要某个餐厅制造菜单，餐厅供应牛肉面、猪肉面... ...，而且顾客可以根据
 * 自己的口味选择是否添加辣椒。此时就产生了一个问题，我们如何来应对这种变化：我们是否需要定义辣椒牛肉面、无辣椒牛肉面
 * 辣椒猪肉面、无辣椒猪肉面4个子类？如果餐厅还供应羊肉面、韭菜面....呢？如果添加辣椒时可选择乌拉、微辣、中辣、重辣....风味呢？
 * 那程序岂非一直忙于定义子类？
 *   为了解决这个问题，我们可以使用桥接模式，桥接模式的做法是把变化部分抽象出来，使变化部分与主类分离开来，从而将多个维度的变化彻底分离。最后
 * 提供一个管理类来组合不同维度上的变化。
 * 
 * ClassName:Test <br/>
 * Function: TODO ADD FUNCTION. <br/>
 * Reason:	 TODO ADD REASON. <br/>
 * Date:     2016-3-17 下午1:18:53 <br/>
 * @author   Dante Fung
 * @version  
 * @since    JDK 1.6
 * @see 	 
 */
public class Test
{
	public static void main(String[] args)
	{
		// 下面将得到“辣味”的牛肉面
		AbstractNoodle noodle1 = new BeafNoodle(new PepperyStyle());
		noodle1.eat();
		// 下面将得到“不辣”的牛肉面
		AbstractNoodle noodle2 = new BeafNoodle(new PlainStyle());
		noodle2.eat();
		// 下面将得到“辣味”的猪肉面
		AbstractNoodle noodle3 = new PorkyNoodle(new PepperyStyle());
		noodle3.eat();
		// 下面将得到“不辣”的猪肉面
		AbstractNoodle noodle4 = new PorkyNoodle(new PlainStyle());
		noodle4.eat();
	}
	
	/*
	 * 上面程序的main（）方法中得到了4种面条，这4中面条就满足了面条在两个维度上的变化，但程序结构比较简洁。
	 * 
	 * 桥接模式在JavaEE架构中有非常广泛的用途，由于JavaEE应用需要实现跨数据库的功能，程序为了在不同数据库之间迁移，因此系统需要在持久化技术这个
	 * 维度上存在变化；除此之外，系统也需要在不同业务逻辑实现之间迁移，因此也需要在逻辑实现这个维度上存在改变，这正好符合桥接模式的使用场景。
	 * 因此，JavaEE应用都会推荐使用业务逻辑组件和DAO组件分离的结构，让DAO组件负责持久化技术这个维度上的改变，让业务逻辑组件负责业务逻辑实现这个
	 * 维度上的改变。由此可见JavaEE应用中常见的DAO模式正式桥接模式的应用。
	 * 
	 * 可能有读者会感到奇怪，刚才我们还提到用业务逻辑组件来包装DAO组件是门面模式，怎么现在又说这种方式是桥接模式呢？其实这两种说法都没有问题，称这种方式
	 * 为门面模式，是从每个业务逻辑组件底层包装了多个DAO组件这个角度来看的，从这个角度看，业务逻辑组件就是DAO组件的门面；
	 * 
	 * 如果从DAO组件的设计初衷来看，设计DAO组件是为了让应用在不同持久化技术之间自由切换，也就是分离系统在持久化技术这个维度上的变化，从这个角度来看，JavaEE应用中
	 * 分离出DAO组件本身就是遵循桥接模式的。
	 */
}

