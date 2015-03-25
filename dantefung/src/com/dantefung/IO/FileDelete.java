package com.dantefung.IO;

import java.io.File;
import java.io.IOException;

/*
 * 
 * 删除功能：
 * public boolen delete()
 * 
 * 注意：
 *     A:若果你创建文件或者文件夹忘了写盘符路径，那么，默认在项目路径下。
 *     B:java中的删除不走回收站。
 *     C：要删除一个文件夹，该文件夹内不能有文件或者文件夹。
 * 
 * */
public class FileDelete {

	/**
	 * @param args
	 * @throws IOException 
	 */
	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		//创建文件
		File file = new File ("e:\\a.txt");
		System.out.println("createNewFile:" + file.createNewFile());
	
		//我不小心写成这样子 了：
		File file2 = new File("a.txt");
		System.out.println("createNewFile:" + file2.createNewFile());
	
		//再玩几个。
		File file3 = new File("aaa\\bbb\\ccc");
		System.out.println("mkdir" + file3.mkdirs());
		
		//删除功能。
		File file4 = new File("a.txt");
		System.out.println("delete:" + file4.delete());
		
		File file5 = new File("\\aaa\\bbb\\ccc");
		System.out.println("delete:" + file5.delete());
		
		//我要删除aaa文件夹。
		File file6 = new File("aaa\\bbb");
		File file7 = new File("aaa");
		System.out.println("delete:" + file6.delete());
		System.out.println("delete:" + file7.delete());
	}

}
