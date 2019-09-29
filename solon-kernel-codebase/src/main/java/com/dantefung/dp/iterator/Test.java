package com.dantefung.dp.iterator;
import com.dantefung.dp.iterator.ArrayList;
/**
 * @author DanteFung
 * @since 2015/2/12
 * 
 * Learning from mashibing's vedio. 
 * */
import com.dantefung.dp.iterator.LinkedList;
public class Test {

	public static void main(String[] args) {
//		ArrayList al = new ArrayList();
//		LinkedList al = new LinkedList();
		
		/* 由父类的引用指向子类。
		 * 面向接口编程。
		 * 
		 * 只针对接口编程,就不需要考虑我的具体实现类是什么。
		 * 优点：
		 * 这样的程序更灵活。想替换其他实现只需改一处代码即可。
		 * 或改配置文件，即代码都不用改。
		 * */
//		Collection c = new ArrayList();
		Collection c = new LinkedList();
		for(int i=0; i<15; i++)
		{
			c.add(new Cat(i));
		}
		System.out.println(c.size());
		
		/*
		 * 对于容器来说，最常用的就是遍历，需要统一一种实现方式。
		 * 
		 * 每一种容器都有自己的遍历方式，但我们要想方设法将这些容器的遍历方式统一起来。
		 * 
		 * 只能用一种共同的方式，接口或抽象类。
		 * 
		 * */
//		ArrayList al = (ArrayList)c;
//		for(int i=0; i<al.getIndex(); i ++)
//		{
//			
//		}
		/**
		 * 定义了统一的接口，就有统一的实现方式，统一的迭代方式，内部的具体实现不管。
		 * **/
		Iterator it = c.iterator();
		while(it.hasNext())//如果你有下一个。
		{
			Object o = it.next();
			System.out.println(o + " ");
		}
		
		c.add(new Cat(15));
		Iterator it2 = c.iterator();
		while(it.hasNext())//如果你有下一个。
		{
			Object o = it2.next();
			System.out.println(o + " ");
		}
		
	}

}
