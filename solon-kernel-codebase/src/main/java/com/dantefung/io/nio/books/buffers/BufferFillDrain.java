
package com.dantefung.io.nio.books.buffers;

import java.nio.CharBuffer;

/**
 * Buffer fill/drain example.  This code uses the simplest
 * means of filling and draining a buffer: one element at
 * a time.
 *
 * Created May 2002
 * @author Ron Hitchens (ron@ronsoft.com)
 * @version $Id: BufferFillDrain.java,v 1.2 2002/05/19 04:55:41 ron Exp $
 */
public class BufferFillDrain
{
	public static void main (String [] argv)
		throws Exception
	{
		// 分配一个容量为100个char变量的CharBuffer
		// 这段代码隐含地从堆空间中分配了一个char型数组作为备份存储器来储存100个char变量。
		CharBuffer buffer = CharBuffer.allocate (100);
		// 这意味着通过调用put()函数造成的对缓冲区的改动会直接影响这个数组，而且对这个数组的任何改动也会对这个缓存区对象可见。
		char[] myArray = new char[100];
		CharBuffer charBuffer = CharBuffer.wrap(myArray);
		// 带有offset和length作为参数的wrap()函数版本则会构造一个按照您提供的offset和length参数值初始化位置和上界的缓冲区。
		// 创建了一个positions值为12，limit值为54，容量为myArray.length的缓冲区。
		// 这个缓冲区可以存储这个数组的全部范围:offset和length参数知识设置了初始的状态。
		CharBuffer wrapBuffer = CharBuffer.wrap(myArray, 12, 42);

		while (fillBuffer (buffer)) {
			// 翻转缓冲区
			buffer.flip();
			// 排干缓冲区
			drainBuffer (buffer);
			// 清空缓冲区
			buffer.clear();
		}
	}

	private static void drainBuffer (CharBuffer buffer)
	{
		while (buffer.hasRemaining()) {
			System.out.print (buffer.get());
		}

		System.out.println ("");
	}

	/**
	 * @Description: 填充缓冲区
	 * @param buffer: 缓冲数据组
	 * @Author: DANTE FUNG
	 * @Date: 2021/5/11 22:00
	 * @since JDK 1.8
	 * @return: boolean
	 */
	private static boolean fillBuffer (CharBuffer buffer)
	{
		if (index >= strings.length) {
			return (false);
		}

		String string = strings [index++];

		for (int i = 0; i < string.length(); i++) {
			buffer.put (string.charAt (i));
		}

		return (true);
	}

	private static int index = 0;

	private static String [] strings = {
		"A random string value",
		"The product of an infinite number of monkeys",
		"Hey hey we're the Monkees",
		"Opening act for the Monkees: Jimi Hendrix",
		"'Scuse me while I kiss this fly",  // Sorry Jimi ;-)
		"Help Me!  Help Me!",
	};
}
