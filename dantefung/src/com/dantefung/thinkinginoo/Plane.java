package com.dantefung.thinkinginoo;

public class Plane extends Vehicle {
	private String name;

	public String getName()
	{
		return name;
	}

	public void setName(String name) 
	{
		this.name = name;
	}
	
	public void go(Address dest)
	{
		System.out.println("一路扇着翅膀，去了" + dest.getName());
	}
}
