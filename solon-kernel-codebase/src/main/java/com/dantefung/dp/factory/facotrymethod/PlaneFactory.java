package com.dantefung.dp.factory.facotrymethod;

public class PlaneFactory implements VehicleFactory {
	private static Plane plane = new Plane();
	
	private static Plane getInstance()
	{
		return plane;
	}
	@Override
	public Moveable creatVehicle() {
		return PlaneFactory.getInstance();
	}

}
