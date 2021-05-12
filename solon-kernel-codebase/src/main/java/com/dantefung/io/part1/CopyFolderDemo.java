package com.dantefung.io.part1;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

/*
 * 需求：复制单级文件夹
 * 
 * 数据源：e:\\demo
 * 目的地：e:\\test
 * 
 * 分析：
 * 
 *    	A：封装目录
 *      B：获取该目录下的所有文本的File数组
 *      C：遍历该File数组，得到每一个File对象
 *      D：把该File进行复制
 * 
 * 
 * 
 * 
 * */
public class CopyFolderDemo {

	/**
	 * @param args
	 * @throws IOException 
	 */
	public static void main(String[] args) throws IOException 
	{
		// TODO Auto-generated method stub
		//封装目录
		File srcFolder = new File("e:\\demo");
		//封装目的地
		File destFolder = new File("e:\\test");
		
		//如果目的的文件夹不存在，就创建
		if(!destFolder.exists())
		{
			destFolder.mkdir();
		}
		
		//获取该目录下的所有文本的File数组
		File[] files = srcFolder.listFiles();
		
		//遍历该File数组，得到每一个File对象
		for(File file : files)
		{
			//System.out.println(file);
			//数据源：e:\\demo
			//目的地：e:\\test
			String name = file.getName();//e.mp3
			File newFile = new File(destFolder, name);
			
			copyFile(file, newFile);
		}
	}

	private static void copyFile(File file, File newFile) throws IOException 
	{
		// TODO Auto-generated method stub
		
	    BufferedInputStream bis = new BufferedInputStream(new FileInputStream(file));
	    BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(newFile));
		
		byte[] by = new byte[1024];
	    int len = 0;
	    while((len = bis.read(by)) != -1)
	    {
	    	bos.write(by, 0, len);
	    }

	    //释放资源
	    bis.close();
	    bos.close();
	
	}

}
