package com.dantefung.introspector;

import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;

import org.junit.Test;

/**
 * 为什么要学内省？
* 开发框架时，经常要使用java对象的属性来封装程序的数据，
*每次都使用发射技术完成此类操作过于麻烦，
* 所以sun公司开发了一套API，专门用于java对象的属性。
*
*什么是java对象的属性和属性的读写方法？
*
*内省访问javaBean属性的两种方式：
*通过PropertyDescriptor类操作Bean的属性
*通过Introspector类来获得Bean对象的BeanInfo，
*然后通过BeanInfo来获取属性的描述器（PropertyDescriptor），
*通过这个属性描述器就可以获取某个属性对应的getter/setter方法，
*然后通过反射机制来调用这些方法。
* **/
//使用内省API操作bean属性。
public class Demo1 {
	
	//得到bean的所有属性
	@Test
	public void test1()throws Exception
	{
		System.out.println("-----------获取所有的属性，包括从父类那继承来得属性---------------");
	    BeanInfo info = Introspector.getBeanInfo(Person.class);		
	    PropertyDescriptor[] pds = info.getPropertyDescriptors();
	    for(PropertyDescriptor pd :pds)
	    {
	    	System.out.println(pd.getName());
	       /*运行结果：
	    	*ab
	    	*age
	    	*class
	    	*name
	    	*password
	    	*
	    	*可见,Bean拥有什么样的属性是由getter和setter方法来决定的。
	    	*
	    	**/
	    }
	    //那么，我就想获取自己的属性，不想要从爸爸那里继承来得属性。
	    System.out.println("-----------获取bean自己所固有的属性---------------");
	    BeanInfo info2 = Introspector.getBeanInfo(Person.class,Object.class/*Stop,停止。谐音，剁掉（冷笑话）*/);
	    PropertyDescriptor[] pd2s = info2.getPropertyDescriptors();
	    for(PropertyDescriptor pd :pd2s)
	    {
	    	System.out.println(pd.getName());
	       /*运行结果：
	    	*ab
	    	*age
	    	*name
	    	*password
	    	*
	    	**/
	    }
	}
	    
	   
	  
	    //操作bean的指定属性：age
	    @Test
		public void test2() throws Exception
		{
	    	System.out.println("-------------操作bean的指定属性：age-----------");
	    	
	    	
	    	Person p = new Person();
	    	//p.setAge(45),传统的编程。不写框架，下面的技术室所用到的API是没用的
	    	
	        PropertyDescriptor pd = new PropertyDescriptor("age",Person.class);
	        Method method = pd.getWriteMethod();//public void setAge(int age) 
	        method.invoke(p, 45/*自动装箱*/);
	        
	        System.out.println(p.getAge());
	        
	        //获取属性的值
	        method = pd.getReadMethod();// public getAge()
	        System.out.println(method.invoke(p,null));

		}
	    
	    //高级点的内同，获取当前操作的属性的类型。
	    @Test
	    public void test3() throws Exception
	    {
	    	Person p = new Person();
	    	
	    	PropertyDescriptor pd = new PropertyDescriptor("age",Person.class);
	    	System.out.println(pd.getPropertyType());
	    }
	

}
