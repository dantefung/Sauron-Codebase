package com.dantefung.IO;

import java.io.File;
import java.io.FilenameFilter;
import java.util.Arrays;
import java.util.regex.Pattern;
//Uses anonymous inner Classes.
//{Args: E:\ }
public class DirList2 {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		File path = new File("E:\\");//匹配除换行符之外的任意字符。
		String[] list;
		if(args.length == 0)
		{
			list = path.list();// 返回一个字符串数组，这些字符串指定此抽象路径名表示的目录中的文件和目录。
		}
		else
		{
			list = path.list(filter(args[0]));//策略模式。回调函数。返回一个字符串数组，这些字符串指定此抽象路径名表示的目录中满足指定过滤器的文件和目录。
		}
		Arrays.sort(list, String.CASE_INSENSITIVE_ORDER);//按字母顺序排序。
		for(String dirItem : list)
		{
			System.out.println(dirItem);
		}
	}
	
	public static FilenameFilter filter(final String regex)
	{
		//Creation of anonymous inner class:
		return new FilenameFilter(){
			private Pattern pattern = Pattern.compile(regex);
			public boolean accept(File dir, String name)
			{
				return pattern.matcher(name).matches();
			}
		};
	}
	
	

}
