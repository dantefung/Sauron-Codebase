package com.dantefung.ClassLoader;

public class EXTClassLoaderTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		/**类加载器的委托机制**/
		//D:\ruanjian\myeclipse\Common\binary\com.sun.java.jdk.win32.x86_1.6.0.013\jre\lib\ext\dantefung.jar
		System.out.println(
				EXTClassLoaderTest.class.getClassLoader().getClass().getName()
				);
	}

}
