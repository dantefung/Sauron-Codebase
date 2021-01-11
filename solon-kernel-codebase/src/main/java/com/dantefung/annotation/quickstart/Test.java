package com.dantefung.annotation.quickstart;

import java.lang.reflect.Method;

/**
 * @Description:
 * 虚拟机规范定义了一系列和注解相关的属性表，也就是说，无论是字段、方法或是类本身，如果被注解修饰了，就可以被写进字节码文件。属性表有以下几种：
 *
 * RuntimeVisibleAnnotations：运行时可见的注解
 * RuntimeInVisibleAnnotations：运行时不可见的注解
 * RuntimeVisibleParameterAnnotations：运行时可见的方法参数注解
 * RuntimeInVisibleParameterAnnotations：运行时不可见的方法参数注解
 * AnnotationDefault：注解类元素的默认值
 * 给大家看虚拟机的这几个注解相关的属性表的目的在于，让大家从整体上构建一个基本的印象，注解在字节码文件中是如何存储的。
 *
 * 所以，对于一个类或者接口来说，Class 类中提供了以下一些方法用于反射注解。
 *
 * getAnnotation：返回指定的注解
 * isAnnotationPresent：判定当前元素是否被指定注解修饰
 * getAnnotations：返回所有的注解
 * getDeclaredAnnotation：返回本元素的指定注解
 * getDeclaredAnnotations：返回本元素的所有注解，不包含父类继承而来的
 * 方法、字段中相关反射注解的方法基本是类似的，这里不再赘述，我们下面看一个完整的例子。
 *
 * 首先，设置一个虚拟机启动参数，用于捕获 JDK 动态代理类。
 *
 * -Dsun.misc.ProxyGenerator.saveGeneratedFiles=true
 *
 * 或者
 *
 * System.setProperty("sun.misc.ProxyGenerator.saveGeneratedFiles","true");
 *
 * 然后 main 函数。
 * @Author: DANTE FUNG
 * @Date:2019/9/29 15:07
 */
public class Test {

    @Hello("hello")
    public static void main(String[] args) throws NoSuchMethodException {
		System.setProperty("sun.misc.ProxyGenerator.saveGeneratedFiles","true");
        Class cls = Test.class;
        Method method = cls.getMethod("main", String[].class);
        /**
         * 注解本质上是继承了 Annotation 接口的接口，而当你通过反射，也就是我们这里的 getAnnotation 方法去获取一个注解类实例的时候，
         * 其实 JDK 是通过动态代理机制生成一个实现我们注解（接口）的代理类。
         * 代理类实现接口 Hello 并重写其所有方法，包括 value 方法以及接口 Hello 从 Annotation 接口继承而来的方法。
         *
         * InvocationHandler的实现类
         */
        Hello annotation = method.getAnnotation(Hello.class);
        System.out.println(annotation.value());
    }
}
