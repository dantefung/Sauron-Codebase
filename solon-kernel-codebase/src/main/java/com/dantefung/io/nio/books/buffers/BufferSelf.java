
package com.dantefung.io.nio.books.buffers;

import java.nio.CharBuffer;

public class BufferSelf
{
	public static void main (String [] argv)
		throws Exception
	{
		CharBuffer cb = CharBuffer.allocate (100);

		cb.put ("This is a test String");

		cb.flip();

		// This throws an IllegalArgumentException
		cb.put (cb);

		System.out.println (cb);
	}
}
