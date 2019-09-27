package com.dantefung.thinkinginoo;

public class Test {
	public static void main(String[] args) {
		Driver d = new Driver();
		d.setName("老张");
		//對象的多態性
		d.drive(new Car());
		d.drive(new Plane());	
		d.drive(new Broom());	
	}

}
