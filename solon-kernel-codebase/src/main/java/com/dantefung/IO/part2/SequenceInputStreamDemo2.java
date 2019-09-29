package com.dantefung.IO.part2;

import java.io.BufferedOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.SequenceInputStream;
import java.util.Enumeration;
import java.util.Vector;

/**
 * 
 *@author DanteFung
 *@since 2015-4-5 9:19 
 * 
 * 合并流。
 * 
 */
public class SequenceInputStreamDemo2 {

	/**
	 * @param args
	 */
	public static void main(String[] args) throws IOException{
		// TODO Auto-generated method stub
		//需求： 把下面的三个文件的内容复制到Copy.java中。
		//ByteArrayStreamDemo.java,CopyFileDemo.java,DataStreamDemo.java
		
		//SequenceInputStream(Enumeration e)
		//通过简单的回顾我们知道了Enumeration是Vector中的一个方法的返回值类型
		//Enumeration<E> elements()
		
		Vector<InputStream> v = new Vector<InputStream>();
		InputStream s1 = new FileInputStream("ByteArrayStreamDemo.java");
		InputStream s2 = new FileInputStream("CopyFileDemo.java");
		InputStream s3 = new FileInputStream("DataStreamDemo.java");
		
		v.add(s1);
		v.add(s2);
		v.add(s3);
		
		Enumeration<InputStream> en = v.elements();
		SequenceInputStream sis = new SequenceInputStream(en);
		BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream("Copy.java"));
		
		//如何读写呢，其实很简单，你就按照以前怎么读写，现在还是怎么读写
		byte[] bys = new byte[1024];
		int len = 0;
		while((len = sis.read(bys))!= -1)
		{
			bos.write(bys, 0, len);
		}
		
		//释放资源
		sis.close();
		bos.close();
		
	}

}
