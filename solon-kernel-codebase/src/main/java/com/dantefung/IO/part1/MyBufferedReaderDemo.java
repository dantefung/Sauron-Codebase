package com.dantefung.IO.part1;

import java.io.FileReader;
import java.io.IOException;

/*
 * 
 *测试MyBufferedReader的时候，你就把它当做BufferedReader一样的使用。 
 * 
 * 
 */
public class MyBufferedReaderDemo {

	/**
	 * @param args
	 */
	public static void main(String[] args) throws IOException{
		// TODO Auto-generated method stub
       MyBufferedReader mbr = new MyBufferedReader(new FileReader("my.txt"));
       
       String line = null;
       while((line = mbr.readLine()) != null)
       {
    	   System.out.println(line);
       }
       
       //释放资源
       mbr.close();
       
       //如果要知道任意数字的编码，可采取如下的方式获取。
       //System.out.println('\r' + 0);
       //System.out.println('\n' + 0);
	}

}
