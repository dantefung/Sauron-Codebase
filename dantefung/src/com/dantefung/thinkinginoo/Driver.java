package com.dantefung.thinkinginoo;

public class Driver {
	private String name;//私有属性，为了安全性。
//	private Car c;//私家车。或者通过传参的方式建立类与类之间的关系。
	
	public String getName()
	{
		return name;
	}

	public void setName(String name) 
	{
		this.name = name;
	}

//	public void drive(Car car)
//	{
//		car.go(new Address("东北"));
//	}
//	
//	public void drive(Car car,Address dest)
//	{
//		car.go(dest);
//	}
	
	//这里面传的是谁，调用的就是谁的方法，這就是多態。
	public void drive(Vehicle v)
	{
		v.go(new Address("东北"));
	}
}
