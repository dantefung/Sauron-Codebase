package com.dantefung.dp.factory.productseries;
/**MagicFactory , a class extends DefualtFactory , 
 * is implements super class ' methods.**/
public class MagicFactory extends DefualtFactory {
	private Broom broom = new Broom();
	private MushRoom mushroom = new MushRoom();
	private MagicStick magicstick = new MagicStick();
	
	@Override
	public Moveable creatVehicle() {
		return broom;
	}

	@Override
	public Food creatFood() {
		return mushroom;
	}

	@Override
	public Weapon creatWeapon() {
		return magicstick;
	}



}
