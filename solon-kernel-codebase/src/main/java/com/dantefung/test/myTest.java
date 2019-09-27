package com.dantefung.test;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import com.dantefung.enumtest.EnumDemo.WeekDay;

public class myTest {
	//初始化资源
/*     @Before
	public void before()
	{
	    System.out.println("before");	
	}
	*/
	@BeforeClass
	public static void before()
	{
	    System.out.println("before");	
	}
	
	
	@Test
	public void test()
	{
	    print(WeekDay.SUN);	
	}
	
	@Test
	public void test2()
	{
	    print(WeekDay.MON);	
	}
	
	
	public void print(WeekDay w)//SUN MON TUE THI FOR FRI SAT
	{
		String value = w.localValue();
		System.out.println(value);
	}
	
/*	@After
	public void after()
	{
		System.out.println("after");
	}*/
	
	@AfterClass
	public static void after()
	{
		System.out.println("after");
	}
	
}	
/*	//带抽象方法的枚举
	//如何定义枚举的构造方法、函数和字段，去封装更多的信息
      enum WeekDay {//class   SUN 星期天   天气：晴 ；   MON 星期一  天气：阴天  ； TUE 想起二  天气：多云    ； THI 星期三  天气：小雨 ； FRI 星期五  天气：晴转阴    ； SAT  星期六  天气：大雨
		SUN("星期天")
		{//匿名内部类，为WeekD	ay的子类
			   public String localValue()
			   {
				    return "天气：晴";
			   }
		}
		, MON("星期一")
		{
			public String localValue()
			{
				return "天气：阴天";
			}
		}

		, TUE("星期二")
		{
			public String localValue()
			{
				return "天气：多云";
			}
		}
		, THI("星期三")
		{
			public String localValue()
			{
				return "天气：小雨";
			}
		}
		, FRI("星期五")
		{
			public String localValue()
			{
				return "天气：晴转阴";
			}
		}
		, SAT("星期六")//object 通过构造方法实例化对象
		{
			public String localValue()
			{
				return "天气：大雨";
			}
		};
		
		private String value;//封装每个对象对应的中文日期
		//声明WeekDay的构造方法
		private WeekDay(String value)  //默认的午餐构造函数变成有参构造函数
		{
			this.value = value;
		}
		
		//抽象方法将if{...}else{...}装法为一个独立的类
		public abstract String localValue();
	}*/
