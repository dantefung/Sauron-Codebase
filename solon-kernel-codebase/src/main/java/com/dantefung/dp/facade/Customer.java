/**
 * Project Name:dantefung
 * File Name:Customer.java
 * Package Name:com.dantefung.dp.facade
 * Date:2016-3-17下午12:51:47
 * Copyright (c) 2016, fhlin0611@foxmail.com All Rights Reserved.
 *
*/

package com.dantefung.dp.facade;
/**
 * 当程序使用了门面模式后，客户端代码只需要和门面类进行交互，客户端代码变得极为简单。
 * 
 * 阅读到此处相信读者对Spring的HibernateTemplate类有点感觉了，当我们
 * 的程序使用HibernateTemplate的find()方法时，程序只要此一行代码即可得到
 * 查询返回的List。但实际上该find()方法后隐藏了如下的代码：
 * 
 * Session session = sf.openSession();
 * Query query = session.createQuery(hql);
 * for(int i = 0; i < args.length; i ++)
 * {
 * 		query.setParameter(i,args[i]);
 * }
 * query.list();
 * 
 * 因此，我们可以认为HibernateTemplate是SessionFactory、Session、Query等类的门面，
 * 当客户端程序需要进行持久化查询时，程序无需调用这些类，而是直接调用HibernateTemplate门面类的方法即可。
 * 
 *  除此之外，JavaEE应用里使用业务逻辑组件来封装DAO组件也是典型的门面模式----每个业务逻辑组件都是众多
 *  DAO组件的门面，系统的控制器类无需直接访问DAO组件，而是由业务逻辑方法来组合多个DAO方法以完成所需功能，
 *  而Action只需与业务逻辑组件交互即可。
 * 
 * ClassName:Customer <br/>
 * Function: TODO ADD FUNCTION. <br/>
 * Reason:	 TODO ADD REASON. <br/>
 * Date:     2016-3-17 下午12:51:47 <br/>
 * @author   Dante Fung
 * @version  
 * @since    JDK 1.6
 * @see 	 
 */
public class Customer
{
	public void haveDinner()
	{
		/*// 依次创建三个部门实例
		Payment pay = new PaymentImpl();
		Cook cook = new CookImpl();
		Waiter waiter = new WaiterImpl();
		// 依次调用三个部门实例的方法来实现用餐功能
		String food = pay.pay();
		food = cook.cook(food);
		waiter.serve(food);*/
		
		// 直接一类于Facade类来实现用餐的方法
		Facade f = new Facade();
		f.serveFood();
	}
}

