package com.dantefung.IO.part2;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.util.Properties;

/*
 *这里的集合必须是Properties集合：
 *public void load(Reader r):把文件中的数据读取到集合中 
 *public void store(Writer writer, String comments):把集合中的数据存储到文件中。
 *
 *
 *单机版游戏：
 *   进度保存和加载
 *   三国群英传，三国志，仙剑奇侠传。。。
 *   
 *   吕布=1
 *   方天画戟=1
 * 
 */
public class PropertiesDemo3 {

	/**
	 * @param args
	 */
	public static void main(String[] args) throws IOException{
		// TODO Auto-generated method stub
		myStore();
		myLoad();	
	}

	private static void myStore() throws IOException {
		// TODO Auto-generated method stub
		//创建集合对象
		Properties prop = new Properties();
		
		prop.setProperty("张三", "27");
		prop.setProperty("刘备", "25");
		prop.setProperty("关羽", "30");
		
		//public void store(Writer writer, String comments):把集合中的数据存储到文件中。
		Writer w = new FileWriter("name.txt");
		prop.store(w, "2015-4-5 14:55");//第二个参数是添加备注信息。
		w.close();
	}

	private static void myLoad() throws IOException {
		// TODO Auto-generated method stub
		//public void load(Reader reader):把文件中的数据读取到集合中
		//注意：这个文件的数据必须是键值对形式
		Reader r = new FileReader("name.txt");
		//创建集合对象
		Properties prop = new Properties();
		prop.load(r);
		//释放资源
		r.close();
		
		System.out.println("prop:" + prop);
	}

}
