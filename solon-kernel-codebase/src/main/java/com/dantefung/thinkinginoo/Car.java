package com.dantefung.thinkinginoo;

public class Car extends Vehicle {
	private String name;
	
	public String getName() 
	{
		return name;
	}

	public void setName(String name) 
	{
		this.name = name;
	}
	//重写了父类的go()方法
	public void go(Address dest)
	{
		System.out.println("一路哼着歌，冒着烟，去了" + dest.getName());
	}
}
