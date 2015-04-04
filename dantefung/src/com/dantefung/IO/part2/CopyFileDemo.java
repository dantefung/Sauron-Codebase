package com.dantefung.IO.part2;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

/*
 * 需求： DataStreamDemo.java 复制到Copy.java中
 *
 * 数据源：
 *     DataStreamDemo.java -- 读取数据 -- FileReader -- BufferedReader
 * 目的地：
 *     Copy.java -- 写数据 -- FileWriter -- BufferedWriter -- PrintWriter    
 * 
 */
public class CopyFileDemo {

	/**
	 * @param args
	 */
	public static void main(String[] args) throws IOException{
		// TODO Auto-generated method stub
        //以前的版本
		//封装数据源
		//BufferedReader br = new BufferedReader(new FileReader("DataStreamDemo.java"));
		//封装目的地
		//BufferedWriter bw = new BufferedWriter(new FileWriter("Copy.java"));
		
		//String line = null;
		//while((line=br.read()) != null)
		//{
		//	bw.write(line);
		//  bw.newLine();
		//  bw.flush();
		//}
		
		//释放资源
		//bw.close();
		//br.close();
		
		
		//打印流改进版
		//封装数据源
		BufferedReader pr = new BufferedReader(new FileReader("DataStreamDemo.java"));
		//封装目的地
		PrintWriter pw = new PrintWriter(new FileWriter("Copy.java"),true);
		
		String line = null;
		while((line=pr.readLine()) != null)
		{
			pw.println(line);
		}
		
		//释放资源
		pw.close();
		pr.close();
	}

}
