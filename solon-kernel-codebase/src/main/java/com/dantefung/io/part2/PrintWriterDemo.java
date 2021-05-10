package com.dantefung.io.part2;

import java.io.IOException;
import java.io.PrintWriter;

/**
 * @since 2015-4-5
 * 
 * 打印流
 * 字节打印流 PrintStream
 * 字符打印流 printWriter
 * 
 * 打印流的特点：
 *     A：只有些数据的，没有读取数据。智能操作目的地，不能操作数据源。
 *     B：可以操作任意类型的数据。
 *     C：如果启动了自动刷新，能够自动刷新。
 *     D：该流是可以直接操作文本文件的。
 *         哪些流对象时可以直接操作文本文件的呢？
 *         FileInputStream
 *         FileOutputStream
 *         FileReader
 *         FileWriter
 *         PrintStream
 *         PrintWriter
 *         看API,查看流对象的构造方法，如果同时又File类型和String类型的参数，一般来说就是可以直接操作文件的
 *         
 *         流：
 *             基本流：就是能够直接读写文件的
 *             高级流：在基本流基础上提供了一些其他的功能。
 * 
 * 运行完后，点击选中当前的dantefung项目，右键--刷新，即可看到在项目的目录下生成了pw.txt文件。
 * 
 */
public class PrintWriterDemo {

	/**
	 * @param args
	 */
	public static void main(String[] args) throws IOException{
		// TODO Auto-generated method stub
        //作为Writer的子类使用
		PrintWriter pw = new PrintWriter("pw.txt");
		
		pw.write("Hello");
		pw.write("world");
		pw.write("java");
		
		//释放资源
		pw.close();
	}

}
