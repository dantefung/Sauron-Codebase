package com.dantefung.dp.factory.productseries;
/**
 * @author DanteFung
 * @since 2015/2/14
 * @version 1.0
 * abstract factory
 * DefualtFactory ,a super abstract class
 *  to generic a series of products.
 * 
 * */
public abstract class DefualtFactory {
	public abstract Moveable creatVehicle();
	public abstract Food creatFood();
	public abstract Weapon creatWeapon();
}
