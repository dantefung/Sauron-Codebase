package com.dantefung.IO.part2;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

/**
 * @since 2015-4-4
 * 
 * 内存操作流：用于处理临时存储信息的，程序结束，数据就从内存中消失。
 * 
 * 字节数组：
 *     ByteArrayInputStream
 *     ByteArrayOutputStream
 * 字符数组：
 *     CharArrayReader
 *     CharArrayWriter
 * 字符串：
 *     StringReader
 *     StringWriter
 */
public class ByteArrayStreamDemo {

	/**
	 * @param args
	 */
	public static void main(String[] args) throws IOException{
		// TODO Auto-generated method stub
        //写数据
		//ByteArrayOutputStream
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		
		//写数据
		for(int x = 0; x < 10; x++)
		{
			 baos.write(("hello" + x).getBytes());
		}
		
		//释放资源 
		//通过查看源码我们知道这里什么都没有做，所以根本不需要close()
		//baos.close();
		
		//public byte[] toByteArray()
		byte[] bys = baos.toByteArray();
		
		//读数据
		//ByteArrayInputStream(byte[] buf)
		ByteArrayInputStream bais = new ByteArrayInputStream(bys);
		
		int by = 0;
		while((by=bais.read()) != -1)
		{
			System.out.print((char)by);
		}
		
		//释放资源
		bais.close();
	}

}
