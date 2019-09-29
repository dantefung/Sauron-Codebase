package com.dantefung.annotation.apt;
/**
 * 1.什么是APT？
 * APT(Annotation Processing Tool 的简称)，可以在代码编译期解析注解，并且生成新的 Java 文件，减少手动的代码输入。
 * APT即为Annotation Processing Tool，它是javac的一个工具，中文意思为编译时注解处理器。APT可以用来在编译时扫描和处理注解。
 * 通过APT可以获取到注解和被注解对象的相关信息，在拿到这些信息后我们可以根据需求来自动的生成一些代码，省去了手动编写。
 * 注意，获取注解及生成代码都是在代码编译时候完成的，相比反射在运行时处理注解大大提高了程序性能。
 * APT的核心是AbstractProcessor类，关于AbstractProcessor类后面会做详细说明。
 *
 *
 *
 *
 */