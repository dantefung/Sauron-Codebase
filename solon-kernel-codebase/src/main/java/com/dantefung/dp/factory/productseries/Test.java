package com.dantefung.dp.factory.productseries;

public class Test {

	public static void main(String[] args) {
		DefualtFactory defualtFactory = new MagicFactory();//要换另一个生产产品系列的工厂在此修改即可。
		Moveable vehicle = defualtFactory.creatVehicle();
		Food food = defualtFactory.creatFood();
		Weapon weapon = defualtFactory.creatWeapon();
		vehicle.run();
		food.printName();
		weapon.shoot();
	}

}
