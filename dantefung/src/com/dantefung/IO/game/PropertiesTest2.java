package com.dantefung.IO.game;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.util.Properties;

/*
 * 
 *我有一个猜数字的小游戏的程序，请编写一个程序实现在测试类中只能玩5次，超过5次就提示：
 *游戏试玩已经结束，请付费。
 * 
 */
public class PropertiesTest2 {

	/**
	 * @param args
	 * @throws IOException 
	 */
	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		
		//把数据加载到集合中
		Properties prop = new Properties();
		Reader r = new FileReader("count.txt");
		prop.load(r);//将r所指向的文件中的内容复制到集合prop中。即    加载。
		
		//我自己的程序，我当然知道里面的键是谁
		String value = prop.getProperty("count");
		int number = Integer.parseInt(value);
		
		if(number > 5)
		{
			System.out.println("游戏试玩已结束，请付费。");
			System.exit(0);
		}
		else
		{
			number ++;
			prop.setProperty("count", String.valueOf(number));
			Writer w = new FileWriter("count.txt");
			prop.store(w, null);
			//释放资源。
			w.close();
			
			GuessNumber.start();
		}
	}

}
