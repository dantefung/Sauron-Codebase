package com.dantefung.IO;

import java.io.File;
import java.io.IOException;

/**
 * 创建功能：
 * public boolean createNewFile():创建文件   false：如果存在这样的文件就不创建了。
 * public boolean mkdir():创建文件夹  false：如果存在这样的文件就不创建了。
 * public boolean mkdirs():创建多级文件夹。
 * 
 * 骑白马的不一定是王子。
 * 注意：你到底要创建文件还是问佳佳，你最清楚，方法不要调错了。
 * **/
public class FileDemo {
	public static void main(String[] args) throws IOException
	{//需求：我要在e盘目录下创建一个文件夹demo
		File file = new File("e:\\demo");
		System.out.println("mkdir:" + file.mkdir());
		
		//需求：我要在e盘目录demo下创建一个文件a.txt
		File file2 = new File("e:\\demo\\a.txt");
		System.out.println("createNewFile:" + file.createNewFile());
	
		//需求：在e盘目录test下创建一个文件b.txt
		File file3 = new File("e:\\test");
		System.out.println("mkdir:" + file3.mkdir());
		
		File file4 = new File("e:\\test\\a.txt");
		System.out.println("createNewFile:" + file.createNewFile());
		
	    //注意：要想在某个目录下创建内容，该目录首先必须存在
		//否则 Exception in thread "mian" java.io.IOException：系统找不到指定的路径。
	
		//需求：在e盘目录test下创建aaa目录。
		File file5 = new File("e:\\test\\aaa");
		System.out.println("mkdir:" + file5.mkdir());
		
		
		//其实，我们有更简单的方法。
		File file7 = new File("e:\\aaa\\bbb\\ccc\\ddd");
		System.out.println("mkdir:" + file4.mkdirs());
		
		//看下面的这个东西： 不会报错，可以。但是只能创建文件夹。
		File file8 = new File("e:\\liuyi\\a.txt");
		System.out.println("mkdirs:" + file8.mkdirs());
	}
}
