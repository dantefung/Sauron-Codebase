package com.dantefung.io;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
//Thinking in java
//缓冲输入文件。
public class BufferedInputFile {

	/**
	 * @param args
	 */
	public static void main(String[] args) throws Exception
	{
		System.out.println(read("BufferedInputFile.java"));
	}
	
	//Throw exception to console:
	public static String read(String filename) throws IOException
	{
		//Reading input by lines:
		BufferedReader in = new BufferedReader(new FileReader(filename));
		String s;
		StringBuilder sb = new StringBuilder();
		while((s = in.readLine()) != null)
		{
			sb.append(s + "\n");
		}
		in.close();
		return sb.toString();
	}

}
