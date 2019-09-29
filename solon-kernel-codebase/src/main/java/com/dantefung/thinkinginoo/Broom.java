package com.dantefung.thinkinginoo;

public class Broom extends Vehicle{

	@Override
	public void go(Address dest)
	{
		System.out.println("掃著土去" + dest.getName());
	}
}
