package com.dantefung.enumtest;

public class EnumDemo {
	public static void main(String[] args) {
		WeekDay weekday = WeekDay.MON;
		WeekDay weekday2 = WeekDay.FRI;
		System.out.println(weekday2);
		System.out.println(weekday2.localValue());
		
	}


	//带抽象方法的枚举
	//如何定义枚举的构造方法、函数和字段，去封装更多的信息
	public enum WeekDay {//class   SUN 星期天   天气：晴 ；   MON 星期一  天气：阴天  ； TUE 想起二  天气：多云    ； THI 星期三  天气：小雨 ； FRI 星期五  天气：晴转阴    ； SAT  星期六  天气：大雨
		SUN("星期天")
		{//匿名内部类，为WeekDay的子类
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
		private WeekDay(String value)  //默认的无参构造函数变成有参构造函数
		{
			this.value = value;
		}
		
		//抽象方法将if{...}else{...}装法为一个独立的类
		public abstract String localValue();
	}
	
}
