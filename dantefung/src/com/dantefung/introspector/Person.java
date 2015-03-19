package com.dantefung.introspector;

public class Person {//javabean   面向对象编程，用户一将数据提交过来，我们就要用对象进行封装。

	/**
	 * 字段什么时候才能称为属性呢？
	 * 只有这个字段对外提供了getter和setter的方法，
	 * 我们才能称之为属性。一个Bean的属性是由它的getter和setter方法决定
	 * */
	private String name;  //字段（成员变量）
	private String password;//字段（成员变量）
	private int age;//字段（成员变量）
	
	
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public int getAge() {
		return age;
	}
	public void setAge(int age) {
		this.age = age;
	}
	
	/**问：这个Bean有没有Ab属性？
	 * 有。
	 *因为Bean拥有什么样的属性是由getter和setter方法来决定的。
	 *而不是由字段决定的。
	 *
	 * 问：这个Bean有多少个属性？
	 * 答：5个。
	 * 由于每个类都是Object类的子类，因此继承了getClass()方法
	 * getClass（）、getPassword()/setPassword()、getName()/setName()、getAge()/setAge()、getAb()、
	 * 从这些方法名可以看出有5个属性：Class、Password、Name、Age、Ab
	 * **/
	public String getAb(){
		return null;
	}
	
}
