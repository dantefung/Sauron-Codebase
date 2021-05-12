package com.dantefung.io;

import java.io.File;

/*
 *需求：
 *    扫描 C:\Users\Administrator\myJavaPath\Java_Learning_Path\dantefung
 *    下所有文件，并将以.java为后缀的文件名打印输出。
 *    
 *实现：
 *    1.封装目录
 *    2.获取该目录下的文件及文件夹对象。
 *    3.遍历判断是否为文件夹：
 *        是：
 *            返回2
 *        否：
 *            判断是否以.java为后缀的文件：
 *                是：
 *                    打印输出该文件的名字
 *                否：无任何操作。
 *               
 *                
 * 
 * 
 * 
 **/
public class FileFilterScan {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		//1.封装目录
		File srcFolder = new File("C:\\Users\\Administrator\\myJavaPath\\Java_Learning_Path\\dantefung");
		//递归调用
		getAllJavaFilePaths(srcFolder);
	}

	public static void getAllJavaFilePaths(File srcFolder) {		
		
		//2.获取该目录下的文件及文件夹的File对象。
		File[] fileArray = srcFolder.listFiles();
		
		
		
		for(File f : fileArray)
		{    
			//遍历判断是否为文件夹
			if(f.isDirectory())
			{
				//返回2
				getAllJavaFilePaths(f);
			}
			else
			{
				//判断是否以.java为后缀的文件。
				if(f.getName().endsWith(".java"))
				{
					//是，就输出该文件的据对路径。
				    System.out.println( f.getAbsolutePath());
				}
			}
		}
	}

}
