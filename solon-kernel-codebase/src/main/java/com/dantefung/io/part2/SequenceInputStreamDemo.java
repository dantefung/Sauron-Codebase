package com.dantefung.io.part2;

import java.io.BufferedOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.SequenceInputStream;

/*
 * 合并流。
 * 
 *以前的操作：
 *    a.txt -- b.txt
 *    c.txt -- d.txt 
 * 
 * 现在想要：
 * a.txt + b.txt -- c.txt
 * 
 */
public class SequenceInputStreamDemo {
	public static void main(String[] args) throws IOException
	{
		//SequenceInputStream(InputStream s1,InputStrem s2)
		//需求： 把ByteArrayStreamDemo.java 和 DataStreamDemo.java的内容复制到Copy.java
		InputStream s1 = new FileInputStream("ByteArrayStreamDemo.java");
		InputStream s2 = new FileInputStream("DataStreamDemo.java");
		SequenceInputStream sis = new SequenceInputStream(s1,s2);
		BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream("Copy.java"));
		
		//如何实现读写呢？其实很简单，你就按照以前怎么读写，现在还是怎么读写
		byte[] bys = new byte[1024];
		int len = 0;
		while((len = sis.read(bys)) != -1)
		{
			bos.write(bys, 0, len);
		}
		
		//释放资源
		bos.close();
	    sis.close();
	}
}
