package com.dantefung.dp.proxy.sample02;

import java.lang.reflect.Method;

import net.sf.cglib.core.DebuggingClassWriter;
import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

class Father {
	public void sayHello() {
		System.out.println("this is father");
	}
}

interface Person {
	void speak();

	void walk();
}

class Student extends Father implements Person {
	public void study() {
		System.out.println("i am student.");
	}

	@Override
	public void speak() {
		System.out.println("i am student ,i can speak");
	}

	@Override
	public void walk() {
		System.out.println("i am student ,i can walk");
	}
}

class MyMethodInterceptor implements MethodInterceptor {
	@Override
	public Object intercept(Object obj, Method method, Object[] arg, MethodProxy proxy) throws Throwable {
		System.out.println("Before: " + method);
		Object object = proxy.invokeSuper(obj, arg);
		System.out.println("After: " + method);
		return object;
	}

}

public class MyCglibProxy {

	public static void main(String[] args) {
		//可以指定 CGLIB 将动态生成的代理类保存至指定的磁盘路径下
		System.setProperty(DebuggingClassWriter.DEBUG_LOCATION_PROPERTY, System.getProperty("user.dir") + "/solon-kernel-codebase/target");
		System.out.println(System.getProperty("user.dir"));
		Enhancer enhancer = new Enhancer();
		enhancer.setSuperclass(Student.class);
		enhancer.setCallback(new MyMethodInterceptor());
		Student student = (Student) enhancer.create();
		student.sayHello();
		student.speak();
		student.walk();
		student.study();
	}

}
/**
 * 就是只能为接口中的方法完成代理，而委托类自己的方法或者父类中的方法都不可能被代理。
 * CGLIB 应运而生，它是一个高性能的，底层基于 ASM 框架的一个代码生成框架，它完美的解决了 JDK 版本的
 * 动态代理只能为接口方法代理的单一性不足问题
 *
 * Student 是我们需要代理的委托类型，结果生成的代理类就直接继承了委托类。
 * 这一个小设计就完美的解决了 JDK 动态代理那个单一代理的缺陷，继承了委托类，就可以反射出委托类接口中的所有方法，
 * 父类中的所有方法，自身定义的所有方法，完成这些方法的代理就完成了对委托类所有方法的代理。
 *
 * 说明：cglib生成代理是通过字节码生成的子类作为代理类，因此不能对private final方法代理；
 *
 * 比较：
 * CGLib动态代理创建代理实例速度慢，但是运行速度快；JDK动态代理创建实例速度快，但是运行速度慢。如果实例是单例的，
 * 推荐使用CGLib方式动态代理，反之则使用JDK方式进行动态代理。Spring的实例默认是单例，所以这时候使用CGLib性能高。
 *
 */