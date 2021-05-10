package com.dantefung.io;

import java.io.File;

/*
 * 获取功能（高级）：
 * public String[] list():获取指定目录下的所有文件或者文件夹的名称数组
 * public File[] listFiles():获取指定目录下所有文件或者文件夹的File数组
 * 
 * 
 * */
public class FileObtainHigh {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
	   //指定一个目录
		File file = new File("e:\\");
		
		//public String[] list():获取指定目录下的所有文件或者文件夹的名称数组
		String[] strArray = file.list();
		for(String s : strArray)
		{
			System.out.println(s);
		}
		
		System.out.println("-------------------");
		
		//public File[] listFiles():获取指定目录下的所有文件或者文件夹的file数组
		File[] fileArray = file.listFiles();
		for(File f : fileArray)
		{
			System.out.println(f.getName());
		}
	}

}
