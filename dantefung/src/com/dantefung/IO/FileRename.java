package com.dantefung.IO;

import java.io.File;

/**
 * 
 * 
 * 重命名功能：
 * public boolean renameTo(File dest)
 * 
 * 如果路径名相同，就是改名。
 * 如果路径名不同，就是改名并剪切。
 * 
 * 
 * 路径以盘符开始：绝对路径 c:\\a.txt\
 * 路径不以盘符开始：相对路径 a.txt
 * */
public class FileRename {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		//创建一个文件对象
		File file = new File("e:\\帅哥.jpg");
		
		//需求：我要修改这这个文件的名称为"东方不败.jpg"
		File newFile = new File("东方不败.jpg");
		
		//既可以换名字也可以顺带的换文件路径。
		System.out.println("renameTo:" + file.renameTo(newFile));
	}

}
