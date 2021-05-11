
package com.dantefung.io.nio.books.buffers;

import java.nio.CharBuffer;

public class BufferWrap
{
	public static void main (String [] argv)
		throws Exception
	{
		CharBuffer cb = CharBuffer.allocate (100);

		cb.put ("This is a test String");

		cb.flip();

		// Boolean型函数hasArray()告诉您这个缓冲区是否有可存取的备份数组。
		System.out.println ("hasArray() = " + cb.hasArray());

		// 如果hasArray()返回true, array()函数会返回这个缓冲区对象所使用的数组存储空间的引用。
		char [] carray = cb.array();

		System.out.print ("array=");

		for (int i = 0; i < carray.length; i++) {
			System.out.print (carray [i]);
		}

		System.out.println ("");
		System.out.flush();
	}
}
