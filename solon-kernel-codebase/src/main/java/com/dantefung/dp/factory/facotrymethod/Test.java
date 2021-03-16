package com.dantefung.dp.factory.facotrymethod;
//面向接口编程测试。  普通工厂，针对具体的单个产品而言。
public class Test {

	public static void main(String[] args) {
		//统一，多態。
		VehicleFactory factory = new CarFactory();//优点：扩展性好。要改只需改这一个地方。
//		VehicleFactory factory = new PlaneFactory();
		Moveable moveable = factory.creatVehicle();
		moveable.run();
	}

}
