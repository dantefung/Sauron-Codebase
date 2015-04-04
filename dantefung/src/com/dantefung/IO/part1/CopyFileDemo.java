package com.dantefung.IO.part1;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

/*
 * 复制文本文件：
 * 
 * 分析：
 *    复制数据，如果我们只用用笔记本打开并能够读懂，就用字符流，否则就用字节流。
 *    通过该原理，我们知道我们应该采用字符流更方便一些。
 *    而字符流有5种方式，所以做这个题目我们有5种方式，推荐掌握第5种。
 *    
 *    数据源：
 *       C:\\a.txt -- FileReader -- BufferedReader
 *     
 *    目的地：
 *       D：\\b.txt -- FileWriter -- BufferedWriter
 * 
 * 
 * 
 * */
public class CopyFileDemo {

	/**
	 * @param args
	 * @throws IOException 
	 */
	public static void main(String[] args) throws IOException 
	{
		// TODO Auto-generated method stub
		String srcString = "c:\\a.txt";
		String destString = "d:\\b.txt";
		
		//method1();
		//method2();
		//method3();
		//method4();
		method5(srcString, destString);
	}
	
	//字符缓冲流一次读写一个字符串。
	private static void method5(String srcString, String destString) throws IOException 
	{
		// TODO Auto-generated method stub
		BufferedReader br = new BufferedReader(new FileReader(srcString));
		BufferedWriter bw = new BufferedWriter(new FileWriter(destString));
		
		String line = null;
		while((line = br.readLine()) != null)
		{
			bw.write(line);
			bw.newLine();//换行
			bw.flush();//踹一脚，将数据从缓冲区中踹出来。
		}
		
		//释放资源。
		bw.close();
		br.close();
		
	}
	
	//字符缓冲流一次读写一个字符数组。
	private static void method4(String srcString, String destString) throws IOException
	{
		BufferedReader br = new BufferedReader(new FileReader(srcString));
		BufferedWriter bw = new BufferedWriter(new FileWriter(destString));
		
		char[] chs = new char[1024];
		int len = 0;
		while((len = br.read(chs)) != -1)
		{
			bw.write(chs, 0, len);
		}
		
		//释放资源。
		br.close();
		bw.close();
	}
	
	//字符缓冲流一次读写一个字符。
	private static void method3(String srcString, String destString) throws IOException
	{
		BufferedReader br = new BufferedReader(new FileReader(srcString));
		BufferedWriter bw = new BufferedWriter(new FileWriter(destString));
		
		int ch = 0;
		while((ch = br.read()) != -1)
		{
			bw.write(ch);
		}
		
		//释放资源
		br.close();
		bw.close();
	}
	
	//基本字符流一次读写一个字符数组。
	private static void method2(String srcString, String destString) throws IOException
	{
		FileReader fr = new FileReader(srcString);
		FileWriter fw = new FileWriter(destString);
		
		char[] chs = new char[1024];
		int len = 0;
		while((len = fr.read(chs)) != -1)
		{
			fw.write(chs, 0, len);
		}
		
		//释放资源
		fr.close();
		fw.close();
	} 
	
	//基本字符流一次读写一个字符
	private static void method1(String srcString, String destString) throws IOException
	{
		FileReader fr = new FileReader(srcString);
        FileWriter fw = new FileWriter(destString);
        
        int ch = 0;
        while((ch = fr.read()) != -1)
        {
        	fw.write(ch);
        }
        
        //释放资源
        fr.close();
        fw.close();
	}
}
