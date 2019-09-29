package com.dantefung.dp.factory.common;

public class CarFactory implements VehicleFactory {
	private static Car car = new Car();//由于是静态的，所以在内存中是恒定不变的。
	
	private static Car getInstance()//单例模式,由于是私有的仅限本类中使用。
	{
		return car;
	}
	
	@Override
	public Moveable creatVehicle() 
	{
		return CarFactory.getInstance();
	}

}
