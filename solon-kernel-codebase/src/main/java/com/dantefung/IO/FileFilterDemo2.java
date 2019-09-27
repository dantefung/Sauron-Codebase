package com.dantefung.IO;

import java.io.File;
import java.io.FilenameFilter;

/*
 * A:先获取所有的，然后遍历的时候，依次判断，如果满足条件就输出。
 * B：获取的时候就已经是满足条件的了，然后输出即可。
 * 
 * 要想实现这个效果，就必须学习一个接口：文件名称过滤器
 * public String[] list(FilenameFilter filter)
 * public File[] listFiles(FilenameFilter filter)
 * 
 * 
 * */
public class FileFilterDemo2 {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		//封装e判断目录
		File file = new File("e:\\");
		
		//获取该目录下所有文件或者文件夹的String数组
		//public String[] list(FilenameFilter filter)
		String[] strArray = file.list(new FilenameFilter()
		{

			@Override
			public boolean accept(File dir, String name) {
				// TODO Auto-generated method stub
				
				//通过这个测试，我们就知道了，到底这个文件或者文件夹的名称加不加到数组中，取决于这里的返回值是true还是false
				//所以，这里的true 或者 false应该是我们通过某种判断得到的。
				//System.out.println(dir + "---------" + name);
				File file = new File(dir, name);
				//System.out.println(file);
				
				//boolean flag = file.isFile();
				//boolean flag2 = name.endsWith(".jpg");
				//return flag && flag2;
				
				return new File(dir, name).isFile() && name.endsWith(".jpg");
			}
			
		});
		
		//遍历输出
		for(String str : strArray)
		{
			System.out.println(str);
		}
	}

}
