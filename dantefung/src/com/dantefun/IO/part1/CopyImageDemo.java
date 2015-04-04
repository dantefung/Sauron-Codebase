package com.dantefun.IO.part1;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

/*
 *  复制图片：
 *  
 *   分析：
 *       复制数据，如果我们知道用笔记本打开并能够读懂，就用字符流，否则就用字节流。
 *       通过该原理，我们知道应该采用字节流。
 *       而字节流有4中方式，所以，做这个题目我们就有4中方式，推荐掌握着4中方式。
 *       
 *       数据源：
 *       C：\\a.jpg -- FileInputStream -- BufferedInputStream
 *       
 *       目的地：
 *       D：\\b.jpg -- FileOutputStream -- BufferedOutputStream
 * 
 * 
 * */
public class CopyImageDemo {

	/**
	 * @param args
	 * @throws IOException 
	 */
	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		//使用字符串作为路径
		//String srcString = "C:\\a.jpg";
		//String destString = "D:\\b.jpg";
		File srcFile = new File("C:\\a.jpg");
		File destFile = new File("D:\\b.jpg");
		
		//method1(srcFile,destFile);
		//method2(srcFile,destFile);
		//method3(srcFile,destFile);
		method4(srcFile,destFile);
		
	}

	//高效流读写数据。 字节缓流一次读写一个字节数组。
	private static void method4(File srcFile, File destFile) throws IOException 
	{
		// TODO Auto-generated method stub
	    //多重串接，文件字节流套接了高效缓冲流(Decorator模式)。
		BufferedInputStream bis = new BufferedInputStream(new FileInputStream(srcFile));
		BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(destFile));
		
		//创建字节数组。（一般用数据来接收数据，起到了缓冲的作用，更加高效的读写数据。）
		byte[] by = new byte[1024];
		int len = 0;
		while((len = bis.read()) != -1)
		{
	          bos.write(by, 0, len);	          
		}
		
		//由于java通过C或C++调用底层系统的资源，因此要释放系统资源。
		
		//释放资源。
		bis.close();
		bos.close();
		
	}
	
	//高效流读写数据，字节缓冲流一次读写一个字节。
	private static void method3(File srcFile, File destFile) throws IOException
	{
		//多重串接，文件字节流套接了高效缓冲流
		
		//封装从数据源读取数据的对象。 该对象指向文件。
		BufferedInputStream bis = new BufferedInputStream(new FileInputStream(srcFile));
		
		//封装向目的地写数据的对象。
		BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(destFile));
		
		int len = 0;
		while((len = bis.read()) != -1)
		{
			bos.write(len);
		}
		
		//释放资源
		bis.close();
		bos.close();
		
	}
	
	//基本字节流一次读写一个字节数组。
	private static void method2(File srcFile, File destFile) throws IOException
	{
		FileInputStream fis = new FileInputStream(srcFile);
		FileOutputStream fos = new FileOutputStream(destFile);
		
		byte[] by = new byte[1024];
		int len = 0;
		while((len = fis.read()) != -1)
		{
			fos.write(by, 0, len);
		}
		
		//释放资源
		fis.close();
		fos.close();
	}
	
	//基本字节流一次读写一个字节
	private static void method1(File srcFile, File destFile) throws IOException
	{
		FileInputStream fis = new FileInputStream(srcFile);
		FileOutputStream fos = new FileOutputStream(destFile);
		
		int len = 0;
		while((len = fis.read()) != -1)
		{
			fos.write(len);
		}
		
		//释放资源
		fis.close();
		fos.close();
	}

}
