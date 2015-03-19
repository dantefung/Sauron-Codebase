package com.dantefung.reflecttest;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Collection;
import java.util.Properties;

public class ReflectTest2 {

	/**
	 * @param args
	 *     
	    /**
	     * 反射的作用---->实现框架功能。
	     * ArrayList_HashSet的比较及Hashcode分析
	     * 1.框架与框架要解决的核心问题
	     *   我做房子卖给用户住，由用户自己安装门窗和空调，我做得房子就是框架，
	     *   用户需要使用我的框架，把门窗插入我提供的挂架中。
	     *   框架与工具类有区别，工具类被用户的类调用，而框架则是调用用户提供的类。
	     * 
	     * 2.框架要解决的核心问题
	     *   我在写框架（房子）时，你这个用户还可能在上小学，还不会写程序呢？我写的框架程序怎样能调用到你以后写的类（门窗）呢？
	     *   因为在写程序的时候，无法知道要被调用的类的类名，所以，在程序中无法直接new某个类的实例对象了，而要用反射的方式来做。
	     *   
	     *   我们只能做class.forName(字符串);字符串，在运行时，再给我就行了。
	     *   
	     * 3.综合实例
	     *   先直接用new语句创建ArrayList和HashSet的实力对象，演示用eclipse自动生成的ReflectPoint类的equalshashcode方法。
	     *   比较两个集合的运行结果的差异。
	     *   然后改为采用配置文件加反射的方式创建ArrayList和HashSetd的实力对象。比较观察运行结果的差异。
	     *   引入了eclipse对资源文件管理方式的讲解。
	     * 
	     *   你在用框架，实际上是框架在调用你的类
	     *   工具类，你自己写好的类调用人家的类。。
	     *   
	     *   我若干年以前写的程序就是为了调用你若干年以后写的
	     * */
	
	public static void main(String[] args) throws Exception {
		// 面向接口编程，面向父类编程。
//		Collection collections = new ArrayList();//打印出的集合长度是4
//		Collection collections = new HashSet();//打印出的集合长度是3
//		collections.add(new ReflectPoint(3,3));
//		ReflectPoint pt1 = new ReflectPoint(3,3);//①
//		ReflectPoint pt2 = new ReflectPoint(5,5);
//		ReflectPoint pt3 = new ReflectPoint(3,3);//②
		/**①、②的值是否相等呢？ 如果要这两个相等，则要自己去写equlas方法。
		 * 否则，默认的equals方法时比较hashcode，hashcode的值通常是通过内存机制换算出来的
		 * pt1、 pt3是两个独立的对象。==肯定不等吧，equals()方法我们没有覆盖，所以也不等。
		 * 因此，我们要覆盖equals()方法。
		 */
		 
//		collections.add(pt1);
//		collections.add(pt2);
//		collections.add(pt3);
//		collections.add(pt1);
		
		/**
		 * 1.如果将hashCode()方法去掉，equals（）相等，这运行结果可能为2或3；
		 * ①②分别按自己的内存机制算出HashCode的值，这两个本来应该相同的对象，被分别存放在不同的区域。
		 * 当我要找这个对象时，我在我的这个区域里找，不去那个区域里找，虽然那个屈戌存放着一个与我相同的对象。
		 * 只要我在我的区域检索不到与我相同的对象，我就可以放进去了。
		 * 
		 * 
		 * 2.当一个对象呗存储进HashSet集合中以后，就不能修改这个对象中的那些参与计算哈希值的字段了，
		 * 否则，对象修改后的哈希值与最初存储进HashSet集合中的哈希值就不同了，
		 * 在这种情况下，及时在Contains方法使用该对象的当前引用作为的参数去HashSet集合中检索对象，
		 * 也将返回找不到对象的结果，这也会导致无法从HashSet集合中单独删除当前对象，造成内存泄露。
		 * 
		 * 所谓内存泄露，就是说，这个对象我不用了，结果你还一个占用内存空间，未被释放。
		 * */
//		pt1.y = 7;//存储进去之后修改字段y的值。
//		collections.remove(pt1);//此时，删除不了该对象。
		
//		System.out.println(collections.size());
		
		/**
		 * 下面改用反射的方式来做，不再出现具体的类的名字，而是从一个配置文件里面读取出来。
		 * 说明：
		 * 在config.properties文件中：
		 * 1.className = java.util.ArrayList  运行结果：4
		 * 2.className = java.util.HashSet    运行结果：2
		 * 
		 * 这里，我们看到一个很小的框架，把我们程序要调用的类放在配置文件里面配，在源程序里面不要出现这个类的名字。
		 * */
		
		//尽量面向父类或接口编程。
		/**加载Properties文件
		 * 
		 * 在WEB中，getRealPath();得到项目工程的总的目录。  如果配置文件时放在项目工程里面，只需要在getRealPath() +　内部的路径
		 * 
		 * 一定要用完整的路径，但完整的路径不是硬编码，而是运算出来的。
		 * 
		 * */
//		InputStream ips = new FileInputStream("config.properties");//此处用的是相对路径
		/**Properties就相当于HashMap,内存里面装的是key-value（键值对），
		 * 但在HashMap的基础上扩展了一些功能：
		 * 1.它可以将自己内存里面的key-value存储在硬盘的文件里面去
		 * 2.它可以再初始化的时候从一个文件里面将自己的key-value加载进来
		 * 一个空的HashMap，需要手动的一对一对地加入key-value
		 * 而Properties 一上来就可以，从文件里面加载很多的key-value
		 * 
		 * 有一种最常用的获得资源配置文件的方式，但是无法替代以上的那种方式。
		 * 类加载器将．ｃｌａｓｓ文件加载到内存中。因此，也可以用类加载器加载普通文件。
		 * 
		 * 配置文件的加载，往往用的是类加载器加载。classpath指定的目录下。如果用eclipse开发德话，就是src目录或者src的子目录(就是包下面嘛。)它会自动拷贝到classpath指定的目录下边去。
		 * */
		//只要运行ReflectTest2这个类，其字节码就被加载到内存当中。
		/*这种应用在我们学习框架的时候应用得很多，我们写完配置文件后，往往将配置文件与类放在一起。局限：只读。strut等框架的配置文件都是通过类加载器加载的，因此配置文件应该放在classpath指定的目录下*/
//		InputStream ips = ReflectTest2.class.getClassLoader().getResourceAsStream("com/dantefung/reflecttest/config2.properties");//getResourceAsStream("config2.properties")表示的是将在classpath指定的根目录下逐一地去找文件加载进来
        /**不管是相对路径，还是绝对路径，内部用的都是classloader**/
//		InputStream ips = ReflectTest2.class.getResourceAsStream("config2.properties");//相对路径,相对于com/dantefung/reflecttest这个包下的一个配置文件
//		InputStream ips = ReflectTest2.class.getResourceAsStream("resource/config3.properties");//相对路径。】
		InputStream ips = ReflectTest2.class.getResourceAsStream("/com/dantefung/reflecttest/resource/config3.properties");//用类提供的简便方法去加载时，可以用绝对路径.从根目录开始慢慢地找。如果跟我有关系，我不用相对我是不是有病啊？假设我要加载的却是跟XX没关系，那就用绝对路径
		Properties props = new Properties();
		props.load(ips);
		/**良好的习惯，马上关门,否则，有一点小小的内存泄露（这个ips对象关联的系统资源没被释放。）
		 * 在ips自己被java的垃圾回收机制干掉前，将与自己相关联的一些物理资源给干掉。否则，ips对象没了，但ips所指向的操作系统资源还在。。
		 * */
		ips.close();
		String className = props.getProperty("className");
		/**编译时：编译器是现将类翻译成二进制代码放在class文件中，这个过程是严格检查语法错误。编译器只看代码的定义，不看代码的执行。不知道对象是什么类型，只知道是对象。*/
		Collection collections = (Collection)Class.forName(className).newInstance();
		
		ReflectPoint pt1 = new ReflectPoint(3,3);
		ReflectPoint pt2 = new ReflectPoint(5,5);
		ReflectPoint pt3 = new ReflectPoint(3,3);
		 
		collections.add(pt1);
		collections.add(pt2);
		collections.add(pt3);
		collections.add(pt1);
		
		System.out.println(collections.size());
		
				

	}

}
