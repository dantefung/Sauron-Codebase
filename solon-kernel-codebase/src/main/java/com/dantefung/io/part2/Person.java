package com.dantefung.io.part2;

import java.io.Serializable;

/*
 *NotSerialiableException：未序列化异常
 *
 * 类通过实现java.io.Serializable 接口以启用其序列化功能。为实现此接口的类将无法使其任何状态序列化或者反序列化。
 * 该接口居然没有任何方法，类似于这种没有方法的接口被称为标记接口。
 * 
 * java.io.InvalidClassException:
 * 
 * 
 * 为什么会有问题呢？
 *      person实现类序列化接口，那么它本身也应该有一个标记值。
 *      这个标记值假设是100.
 *      开始的时候
 *      Person.class -- id=100
 *      write数据： oos.txt -- id=100
 *      read数据：  oos.txt -- id=100
 *      
 *      现在：
 *      Persom.class -- id=200
 *      write数据： oos.txt -- id=100
 *      read数据： oos.txt -- id=100
 *      
 *      我们在实际开发中，可能还需要使用以前写过的数据，不能重新写入。怎么办？
 *      回想一下原因是因为了他们的id值不匹配。
 *      
 *      每次修改java文件的内容的时候，class文件的id值都会发生改变。
 *      而读取文件的时候，会和class文件中的id值在java文件中是一个固定的值，这样，你修改文件的时候，这个id值还会发生改变吗？
 *      但是呢，如果我有办法，让这个id值在java文件中是一个固定的值，这样，你修改文件的时候，这个id值还会发生改变吗？
 *      不会。现在关键是我如何能够知道这个id值如何表示呢？
 *      不用担心，你不用记住，也没关系，点击鼠标即可。
 *      你难道没有看到黄色警告线吗？
 *      
 *      我们要知道的是：
 *          看到类实现了序列化接口的时候，要想解决黄色警告线问题，就可以自动产生一个序列化id值。
 *          而且产生这个值以后，我们对类进行任何改动，它读取以前的数据是没有问题的。
 * 
 * 
 * 注意：
 *     我一个类中可能有很多的成员变量，有些我不想进行序列化。请问该怎么办？
 *     使用transient关键字声明不需要序列化的成员变量
 */
public class Person implements Serializable{

	/**
	 * 通过点击黄色警告线生成的。
	 */
	private static final long serialVersionUID = 7501560121587131792L;

	private String name;
	
	private transient int age;
	
	public Person()
	{
		super();
	}
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}
	@Override
	public String toString() {
		return "Person [name=" + name + ", age=" + age + "]";
	}

	
}
