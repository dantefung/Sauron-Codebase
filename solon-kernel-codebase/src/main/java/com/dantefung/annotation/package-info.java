package com.dantefung.annotation;
/**
 * 基本概念
 *注解的本质
 * 「java.lang.annotation.Annotation」接口中有这么一句话，用来描述『注解』。
 *
 * The facotrymethod interface extended by all annotation types
 *
 * 所有的注解类型都继承自这个普通的接口（Annotation）
 *
 * 「注解的本质就是一个继承了 Annotation 接口的接口」
 *
 * 元注解
 * 『元注解』是用于修饰注解的注解，通常用在注解的定义上，例如：
 * @Target(ElementType.METHOD)
 * @Retention(RetentionPolicy.SOURCE)
 * public @interface Override {
 *
 * }
 *
 * @Target：注解的作用目标
 * @Retention：注解的生命周期
 * @Documented：注解是否应当被包含在 JavaDoc 文档中
 * @Inherited：是否允许子类继承该注解
 *
 *
 *  @Target 注解修饰的注解将只能作用在成员字段上，不能用于修饰方法或者类。其中，ElementType 是一个枚举类型，有以下一些值：
 * ElementType.TYPE：允许被修饰的注解作用在类、接口和枚举上
 * ElementType.FIELD：允许作用在属性字段上
 * ElementType.METHOD：允许作用在方法上
 * ElementType.PARAMETER：允许作用在方法参数上
 * ElementType.CONSTRUCTOR：允许作用在构造器上
 * ElementType.LOCAL_VARIABLE：允许作用在本地局部变量上
 * ElementType.ANNOTATION_TYPE：允许作用在注解上
 * ElementType.PACKAGE：允许作用在包上
 *
 * @Retention 用于指明当前注解的生命周期，它的基本定义如下：
 *
 * RetentionPolicy.SOURCE：当前注解编译期可见，不会写入 class 文件
 * RetentionPolicy.CLASS：类加载阶段丢弃，会写入 class 文件
 * RetentionPolicy.RUNTIME：运行时，可以反射获取
 *
 * JAVA 的内置三大注解
 * @Override
 * @Deprecated
 * @SuppressWarnings
 *
 * 它有一个 value 属性需要你主动的传值，这个 value 代表一个什么意思呢，这个 value 代表的就是需要被压制的警告类型。例如：
 *
 * public static void main(String[] args) {
 *     Date date = new Date(2018, 7, 11);
 * }
 * 这么一段代码，程序启动时编译器会报一个警告。
 *
 * Warning:(8, 21) java: java.util.Date 中的 Date(int,int,int) 已过时
 *
 * 而如果我们不希望程序启动时，编译器检查代码中过时的方法，就可以使用 @SuppressWarnings 注解并给它的 value 属性传入一个参数值来压制编译器的检查。
 *
 * @SuppressWarning(value = "deprecated")
 * public static void main(String[] args) {
 *     Date date = new Date(2018, 7, 11);
 * }
 * 这样你就会发现，编译器不再检查 main 方法下是否有过时的方法调用，也就压制了编译器对于这种警告的检查。
 *
 * 【注解与反射】
 *
 *
 *
 *
 */