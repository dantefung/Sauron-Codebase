package com.dantefung.dp.factory.common;

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
