package com.dantefung.reflecttest;
import java.lang.reflect.Array;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Arrays;
/**
 * @author dantefung 
 * @since 2014-10-31 
 * @version 1.0
 * study by itcast's course
 * take note 
 * 2014-11-1 the first modify(make over/alter)
 * */
public class ReflectTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) throws Exception{
		// TODO Auto-generated method stub
		//如何获得各个字节码对应的实例对象（Class）
		/**
		 *类对象编译成二进制代码放在硬盘里，从硬盘里加载类的字节码到内存（或JVM）中，然后通过字节码复制出一个一个的对象 
		 *总之，在源程序中出现的类型，都有各自的Class实例对象，例如，int[]、void...
		 */
		String str1 = "abc";
		Class cls1 = str1.getClass();//获得字节码
		Class cls2 = String.class;//获取字节码
		Class cls3 = Class.forName("java.lang.String");//获取字节码
		//查看是否是同一份字节码
		System.out.println(cls1 == cls2);
		System.out.println(cls1 == cls3);
		System.out.println(cls1);
		System.out.println(cls2);
		System.out.println(cls3);
		System.out.println(cls1.isPrimitive());//判断String是否为一个基本类型
		System.out.println(int.class.isPrimitive());//判断int是否为一个基本类型
		System.out.println(int.class == Integer.class);
		System.out.println(int.class == Integer.TYPE);//Integer中包含的基本类型
		System.out.println(int[].class.isArray());//任何类型都可以用一个class来表示，任何类型在内存里都是一份字节码。这个一个数组，数组的这种类型，是不是原始类型呢？是一种类型，但不是原始类型
	    
		/**构造方法的反射
		*相当于new String(new StringBuffer("abc"))
		*得到Constructor类型对象，代表一个构造方法
		*String.class--字节码，String.class.getConstructor（）--通过字节码得到的对象。
		*其中getConstrutor是String类的成分之一（构造方法），
		*由于所有的类都有这一成分，因此将其变成一类。 而这个成分类（纯属个人创造的词语，以便自己理解）中有各种操作，
		*而要获得这些操作必须先实例化这个成分类，进而才能调用这其中的方法。
		*注意：
		*1.在编写程序的时候要区分：编译时、运行时。
		*编译时：编译器是现将类翻译成二进制代码放在class文件中，这个过程是严格检查语法错误。编译器只看代码的定义，不看代码的执行。
		*运行时：执行了代码才知道是什么类型。
		*2.得到方法时需要类型，在调用方法时需要定义同样类型的对象。
		*
		*步骤：
		*class-->construtor-->new object
		*
		*反射会导致程序性能下降。调用构造方法时，先缓存起来，再次调用时就直接用以前已经得到的构造方法
		*
		*Class。newInstance();  如果要调用无参的构造方法就使用这个方法。
		*/                                                     //此处的StringBuffer是表示选择哪个构造方法。
		Constructor constructor1 = String.class.getConstructor(StringBuffer.class/*这里是类型*/);//getConstuctor中可以指定若干个class对象，一个class对象代表一个类型
	    //在编写源程序的时候，人家只知道它是一个Construtor，很多个类都有constutor，人家不知道它是String的constructor。程序运行时，才执行String。class.getConstrutor（StringBuffer.class）；这行代码，才知道是String的
		String str2 = (String) constructor1.newInstance(/*"abc"传入的是String类型，这是运行时的错误*/new StringBuffer("abc")/*这里是同样类型的对象*/);//可以调用很多次,每调用一次就相当于new一个String对象。现在只是用它来new一个指针对象。但是对应String类的哪个构造方法，人家是不知道的。编译时，运行时。
	                            //第二个StringBuffer表示用构造方法时，还要传一个StringBuffer对象。
		//public Object newInstance(Object[] initargs),返回值是Object类型，需要告诉编译器，返回的是String类型，因此在迁建加了（String）
	    System.out.println(str2.charAt(2));//求出字符串的第二个
	    
	    /**
	     * 成员变量 （字段）
	     * 
	     * */
	    ReflectPoint pt1 = new ReflectPoint(3,5);
	    Field fieldY = pt1.getClass().getField("y");
	   /**FieldY的值是多少？5，错！！ 
	    * FieldY只表示的是类字节码身上的变量，它（5）没有对应到对象身上。不代表一个具体的值，
	    * 只代表一个(类级别的)引用变量（成分（字段）类），不代表引用变量映射的对象身上（带有的、封装的）具体的值。
	    * 这个成分类去取ReflectPoint类的对象pt1身上封装好的值。
	    * */	
	    System.out.println(fieldY.get(pt1));
	    /*Exception in thread "main" java.lang.NoSuchFieldException: y& 说明这个y是私有的，因此看不见*/
	    /*------------暴力反射：start----------------*/
	    Field fieldX = pt1.getClass().getDeclaredField("x");
	                                  //只要声明过的都取出来，属于看得见，但不给你用（访问）。
	    fieldX.setAccessible(true);//设置可以访问。
	    System.out.println(fieldX.get(pt1));
	    /*-------------暴力反射：end----------------------*/
	    /**
	     * 换掉一个对象里面的字段
	     * 
	     * */
	    changStringValue(pt1);
	    System.out.println(pt1);//为了能看到打印的效果，有必要覆盖toString()方法 不覆写的话，则控制台会打印：com.dantefung.reflecttest.ReflectPoint@6e1408
	    /*通常调用方法的方式：str1.charAt(1);
	     *反射的方式：charAt.invoke(); 
	     */
	    /**
	     * 用反射的方式，得到（String类的）字节码里面的方法（Method类的对象），
	     * 再拿这个方法（Method类的的对象）去作用于某个对象（String类的对象）
	     * 
	     * invoke:调用
	     *
	     * 理解：这是methodCharAt对象内的一个方法。
	     * 例子：列车司机将列车停了下来。列车司机真有那么大的能力吗？他能跳下车将车停住？实际上是列车司机踩了离合器，给列车发送信号，让列车自己停下来。
	     * circle.draw();
	     * 面向对象的设计：
	     * 人关门，关门时谁的动作？是门的动作。人只是推了一下门。
	     * 面向对象超级简单，只要将变量变成私有的，如果谁操作这个变量，变量在谁的身上，方法就在谁的身上。----专家模式。（谁拥有数据，谁就是干这个数据的专家）
	     * */
	    Method methodCharAt =String.class.getMethod("charAt",int.class);
	    System.out.println(methodCharAt.invoke(str1/*null,则该Method对象对应的是一个静态方法。*/,1));//这个方法时给人调用的，人只是发送信号（告诉它你的方法要调用一下），调用的动作就再方法自己身上。
	    System.out.println(methodCharAt.invoke(str2, new Object[]{2}));//new Object[]{new String("abc"),1/*JDK1.5里面有自动装箱的功能 ，自动将其装成对象*/}
	    
	    
	    
	    /*为什么要用反射的方式调？*/
	    /**
	     * 反射用来写框架
	     * 要调用某个类的main方法，这个源程序可以先写好，然后，args[0]被赋值一个字符串（这个字符串表达的是一个类的类名）
	     * 编译时，你的类还没有开始写好，我可以在源程序里先写好调用你的类的代码；只要在运行时，你的类写好了就行
	     * */
	    //TestAguments.main(new String[]{"123","222","3333"});
	    String startingClassName = args[0];//传递一个参数，源程序里面没有出现类名，作用（意义）：哥们，给我启动A这个类，你就启动A这个类。而源程序中根本不知道要执行哪个类？
	    //arg[0]在run configuration里设置了传递的参数为com.dantefung.reflecttest.TestAguments

	    Method mainMethod = Class.forName(startingClassName).getMethod("main",String[].class);
	    mainMethod.invoke(null/*由于方法是静态的，因此用null*/, new Object[]{new String[]{"123","222","333"}}/*再次 打包，每次只能拆一次包*/); /*mainMethod.invoke(null由于方法是静态的，因此用null, new String[]{"123","222","333"});
	                                                                                                   Exception in thread "main" java.lang.IllegalArgumentException: wrong number of arguments
	                                                                                                                                                                                                                                                                                                                                                            以数组的角度来说，确实是一个参数；但是以一包东西的角度来说（jdk1.4里面认为数组的每一个元素对应一个参数。），三个参数。因为jdk1.5要兼容jdk1.4，在运行时，将数组进行拆分（只能拆一次包）。*/
	    mainMethod.invoke(null/*由于方法是静态的，因此用null*/, (Object)new String[]{"123","222","333"}/*Oject类是String的父类,因此String类型的对象数组也是Oject类型的对象数组，这样做告诉编译器这是一个对象不要拆包--张孝祥老师如是说*/);//调用了com.dantefung.reflecttest.TestAguments的main方法
	    //invoke(Object obj, Object... args) 可变参数： 返回值类型 方法名称（类型。。。参数名称）{}
	    
	    /*------------------------测试数组是否是一个特殊类：start------------------------------*/
	    byte[] bText = new byte [250];//声明数组，同时分配内存
	    System.out.println(bText.getClass().getName());
	    //结论：？？？？
	    /*------------------------测试数组是否是一个特殊的类：end------------------------------*/
	    
	    /**
	     * 数组的反射
	     * 1.具有相同维数和元素类型的数组属于同一个类型，即具有相同的Class实例对象。
	     * 2.代表数组的Class实例对象的getSuperclass()方法返回父类为Object类对应的class
	     * 3.基本数据类型的数组可以被当作Object类型使用，不能当作Object[]类型使用；非基本类型的数组，既可以当做Object类型使用，又可以当作Object[]类型使用
	     * 4.Arrays。asList()方法处理int[]和String[]的差异
	     * 5.Array工具类用于完成对数组的反射操作
	     * 6.思考题：怎么得到数组中的元素类型？  答：没有办法得到数组中元素的类型。
	     * int [] a = new int[3];
	     * 因为，Object[] a = new Object[]{"a",1}//String,int 类型。
	     * 只能得到具体某个元素的类型，不嫩得到整个数组的类型。
	     * 啊[0]。getClass().getName();
	     * 我们如果要自己动手开发框架，就要对这些知识（反射）了解得很清楚。不了解反射，去看框架也看不懂。
	     * */
	    int [] a1 = new int[]{1,2,3};
	    int [] a2 = new int[4];
	    int [] [] a3 = new int[2][3];
	    String [] a4 = new String[]{"a","b","c"};//对象数组，自动装箱
	    //JDK1.6文档里面说了，具有相同的维数和相同的类型
	    System.out.println(a1.getClass() == a2.getClass());
//	    System.out.println(a1.getClass() == a4.getClass());//输出时flase
//	    System.out.println(a1.getClass() == a3.getClass());//输出时flase
	    System.out.println(a1.getClass().getName());//输出的结果为：[I  说明：[表示数组 I表示int类型
	    System.out.println(a1.getClass().getSuperclass().getName());//运行结果：java.lang.Object
	    System.out.println(a4.getClass().getSuperclass().getName());//运行结果：java.lang.Object
	    
	    //so...
	    Object aobj1 = a1;//对象的多态性，子类实例化父类对象
	    Object aobj2 = a4;
//	    Object[] aobj3 = a1;//有一个数组里面装的是int ，但int是基本呢数据类型，不是Object类类型
	    Object[]/*有一个数组里面装的是Object*/ aobj4 = a3;//a3表示数组的数组，有一个数组，里面装的是int类型数组(属于Object)
	    Object[] aobj5 = a4;
	    
	    System.out.println(a1);//运行结果：[I@1f33675   表示：这个对象是int类型的数组，其hashcode的值为@1f33675
	    System.out.println(a4);//运行结果：[Ljava.lang.String;@7c6768 表示：这个对象时String类型的数组，其hashcode的值为@7c6768 
	    //想打印出数组中的内容，1.用循环输出；2.Arrays类，工具类，里面有大量对数组进行操作的方法。
	    /**
	     * 注意：Arrays。asList()方法处理int[]和String[]的差异
	     * */
	    System.out.println(Arrays.asList(a1));//运行结果：[[I@1f33675]，表示：[]转化为list，而里面装的是list的唯一个元素（原来的数组对象）  JDK1.6中public static <int[]> List<int[]> asList(int[]... a)，里面传递的参数这是一个数组
	    System.out.println(Arrays.asList(a4));//运行结果：[a, b, c]  对象自动拆箱成字符串 .JDK1.4的语法:public static List asList(Object[] a)，JDK1.6兼容JDK1.4的语法。因此，可以打印出来
	    
	    /*我们对数组进行反射可以得到它的class
	     * 
	     * 数组反射 Class Array
	     * 
	     * int[3]
	     * 得到
	     * 数组的长度： lenght
	     * 数组的值：int[0]
	     * 要用反射的方式来做。
	     * */
//	    Object obj = null;
	    printObject(a4);
	    printObject("xyz");
	    
	}

	private static void printObject(Object obj) {
		Class clazz = obj.getClass();
		if(clazz.isArray())
		{
			//你给我的是一个数组，我就一个一个地给你拆出来
			int len = Array.getLength(obj);
			for(int i=0;i < len;i ++)
			{
				System.out.println(Array.get(obj,i));
			}
		}
		else
		{
			System.out.println(obj);//如果不是数组，就将你当单个打印出来
		}
		
	}

	/**
	 *  会写这个程序的话，说明你会反射了
	 * */
	private static void changStringValue(Object obj) throws Exception {
        //扫描这个类对象身上的String类型的的变量，怎么扫描？得到所有的字段。
		Field[] fields = obj.getClass().getFields();
		for(Field field : fields)
		{
		//if(field.getType().equals(String.class))（也可以，语义不明确）equals用於比較兩個不同的字符串对象
		//字节码用 == 做比较 ,因為這裡是同一份字節碼。（专业）
		    if(field.getType() == String.class)//假如你是String類型的字節碼，我就要取得你的值。
		    {
		    	String oldValue = (String)field.get(obj);
		    	String newValue = oldValue.replace('b','a');
		    	field.set(obj,newValue);
		    }
		}
	}

}
/**
 * 写一个程序，这个程序能够根据用户提供的类名，去执行类中的main方法，用完普通方式调用之后，要明白为什么要用反射的方式调、
 * 
 * */
class TestAguments
{
    public static void main(String[] args){
    	for(String arg:args)
    	{
    		System.out.println(arg);
    	}
    }
}