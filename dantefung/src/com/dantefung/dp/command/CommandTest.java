/**
 * Project Name:dantefung
 * File Name:CommandTest.java
 * Package Name:com.dantefung.dp.command
 * Date:2016-3-16下午4:24:38
 * Copyright (c) 2016, fhlin0611@foxmail.com All Rights Reserved.
 *
*/

package com.dantefung.dp.command;
/**
 * 
 * 
 * ClassName:CommandTest <br/>
 * Function: TODO ADD FUNCTION. <br/>
 * Reason:	 TODO ADD REASON. <br/>
 * Date:     2016-3-16 下午4:24:38 <br/>
 * @author   Dante Fung
 * @version  
 * @since    JDK 1.6
 * @see 	 
 */
public class CommandTest
{

	/**
	 * main:
	 * 当主程序调用ProcessArray对象的each()方法来处理数组时，每次处理
	 * 数组需要传入不同的“处理行为”----也就是要为each()方法传入不同的Command对象，
	 * 不同的Command对象封装了不同的“处理行为”。
	 * 
	 * 实现了process方法和“处理行为”的分离，两次不同的处理行为分别由
	 * 两个不同的Command对象来提供。
	 * 
	 * 理解了这个命令模式后，相信对spring框架中HibernateTemplate的executeXxx()方法
	 * 找到了一点感觉，HibernateTemplate使用了executeXxx()方法弥补了HibernateTemplate的
	 * 不足，该方法需要接受一个HibernateCallback()接口，该接口的代码如下：
	 * 
	 * //定义一个HibernateCallback接口，该接口封装持久化的处理行为
	 * public interface HibernateCallback()
	 * {
	 * 		Object doInHibernate(Session session);
	 * } 
	 * 
	 * 上面的HibernateCallback接口就是一个典型的Command接口，一个HibernateCallback对象封装
	 * 自定义的持久化处理。
	 * 
	 *   对HibernateTemplate而言，大部分持久化操作都可以通过一个方法来实现，HibernateTemplate对象简化了Hibernate的持久化
	 *   操作，但丢失了使用Hibernate持久化操作的灵活性。
	 *   
	 *   通过HibernateCallback就可以弥补HibernateTemplate灵活性的不足的缺点，当调用HibernateTemplate的executeXxx()方法时，
	 *   传入HibernateCallback对象的doInHibernate()方法就是自定义的持久化处理----即将自定义的持久化处理传入了
	 *   executeXxx()方法。如下面的代码片段所示：
	 *   
	 *   List list = getHibernateTemplate()
	 *         .executeFind(new HibernateCallback()
	 *        {
	 *   		  // 实现HibernateCallback接口必须实现的方法
	 *            public Object doInHibernate(Session session)
	 *            {
	 *            	 // 执行Hibernate分页查询
	 *               List result = session.createQuery(hql)
	 *                           .setFirstResult(offset)
	 *                           .setMaxResults(pageSize)
	 *                           .list();
	 *               return result;
	 *            }
	 *        });
	 * 
	 *  <br/>
	 * TODO(这里描述这个方法适用条件 – 可选).<br/>
	 * TODO(这里描述这个方法的执行流程 – 可选).<br/>
	 * TODO(这里描述这个方法的使用方法 – 可选).<br/>
	 * TODO(这里描述这个方法的注意事项 – 可选).<br/>
	 *
	 * @author Dante Fung
	 * @param args
	 * @since JDK 1.6
	 */
	public static void main(String[] args)
	{
		ProcessArray pa = new ProcessArray();
		int[] target = {3, -4, 6, 4};
		
		//第一次处理数组，具体处理行为取决于Command对象。 回调。
		pa.each(target, new Command()
		{

			@Override
			public void process(int[] target)
			{
				for(int tem : target)
				{
					System.out.println("迭代输入的目标数组的元素：" + tem);
				}
			}
			
		});
		
		System.out.println("--------------------");
		
		// 第二次处理数组，具体处理行为取决于Command对象
		pa.each(target, new Command()
		{
			@Override
			public void process(int[] target)
			{
				int sum = 0;
				for(int tem : target)
				{
					sum += tem;
				}
				System.out.println("数组元素的总和是：" + sum);
			}
		});
		
		
	}

}

