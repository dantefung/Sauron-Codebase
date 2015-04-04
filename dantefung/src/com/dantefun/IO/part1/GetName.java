package com.dantefun.IO.part1;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

/*
 * 需求： 我有一个文本文件中存储了几个名称，请大家协议程序实现随机获取一个人的名字。
 * 
 * 
 * 分析：
 *    A：把文本文件中的数据存储到集合中
 *    B：随机产生一个索引
 *    C：根据该索引获取一个值。
 * 
 * 
 * 
 * 
 * 
 * */
public class GetName {

	/**
	 * @param args
	 */
	public static void main(String[] args) throws IOException{
		// TODO Auto-generated method stub
		//把文本文件的数据存储到集合中
		BufferedReader br = new BufferedReader(new FileReader("c.txt"));
	    ArrayList<String> array = new ArrayList<String>();
	    
	    String line = null;
	    while((line = br.readLine()) != null)
	    {
	    	array.add(line);
	    }
	    
	    //随机产生一个索引
	    Random rd = new Random();
	    int index = rd.nextInt(array.size());
	    
	    //根据该索引获取一个值、
	    String name = array.get(index);
	    System.out.println( "该幸运者是："+ name);
	    
	}

}
