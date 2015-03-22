package com.dantefung.ClassLoader;

public class ClassLoaderTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		//CLASSPATH指定的所有jar或目录。
		System.out.println(
				ClassLoaderTest.class.getClassLoader().getClass().getName()
				);
		
		//BootStrap 类加载器     JRE/lib/rt.jar  C++写的  处于jvm内核
		System.out.println(
				System.class.getClassLoader()
				);
		
		ClassLoader loader = ClassLoaderTest.class.getClassLoader();
		while(loader != null)
		{
			System.out.println(loader.getClass().getName());
			loader = loader.getParent();
		}
		System.out.println(loader);
	}

}
