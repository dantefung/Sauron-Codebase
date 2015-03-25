package com.dantefung.IO;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

/*
 * 获取功能：
 * public String getAbsolutePath()：获取绝对路径
 * public String getPath()：获取相对路径。
 * public String getName()：获取名称
 * public long length()：获取长度。字节数。
 * public long lastModified()：获取最后一次修改的事件，毫秒值。
 * 
 * 高级功能：
 * public String[] list()
 * public File[] listFiles()
 * 
 * **/
public class FileObtain {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
	    //创建文件对象
		File file = new File("demo\\test.txt");
		
		System.out.println("getAbsolutePath:" + file.getAbsoluteFile());
		System.out.println("getPath:" + file.getPath());
		System.out.println("getName:" + file.getName());
		System.out.println("length:" + file.length());
		System.out.println("lastModified:" + file.lastModified());
		
		
		//1427285125847
		Date d = new Date(1427285125847L);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String s = sdf.format(d);
		System.out.println(s);
		
		
	}

}
