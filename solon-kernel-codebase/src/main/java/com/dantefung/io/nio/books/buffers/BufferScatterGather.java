/*
 * Copyright (C), 2015-2020
 * FileName: BufferScatterGather
 * Author:   DANTE FUNG
 * Date:     2021/5/12 下午8:26
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 * DANTE FUNG        2021/5/12 下午8:26   V1.0.0
 */
package com.dantefung.io.nio.books.buffers;

import sun.nio.ch.DirectBuffer;

import java.io.*;
import java.nio.ByteBuffer;
import java.nio.channels.GatheringByteChannel;
import java.util.Arrays;

/**
 * @Title: BufferScatterGather
 * @Description:
 *   本地矢量I/O，大多数现代操作系统都支持本地矢量I/O(native vectored I/O)
 *   使用得当的话，Scatter/Gather会是一个极其强大的工具。它允许您委托操作系统来完成辛苦活：
 *   将读取到的数据分开存放到多个存储桶(bucket)或者将不同的数据区块合并成一个整体。
 *   这是一个巨大的成就，因为操作系统已经被高度优化来完成此类工作了。
 * @author DANTE FUNG
 * @date 2021/05/12 20/26
 * @since JDK1.8
 */
public class BufferScatterGather {

	public static final String FILE_PATH = "test_buffer_scatter_gather.txt";

	public static void main(String[] args) throws IOException {
		testGatherWrite();
		//send();
	}

	/**
	 * 一个使用了四个缓冲区的gather写操作.
	 * 参见src/main/java/com/dantefung/io/nio/assets/图3-5一个使用了四个缓冲区的gather写操作.png
	 * @throws IOException
	 */
	private static void testGatherWrite() throws IOException {
		System.out.println("Four score lemurs leaping 字节长度: "+"Four score lemurs leaping".getBytes().length);
		System.out.println("Starsky and Hutch 字节长度: "+"Starsky and Hutch".getBytes().length);
		System.out.println("Snow White and the seven dwarves 字节长度: "+"Snow White and the seven dwarves".getBytes().length);
		System.out.println("Years and years ago. 字节长度: "+"Years and years ago.".getBytes().length);
		ByteBuffer bf1 = ByteBuffer.allocateDirect(25);// 参数为字节数量
		ByteBuffer bf2 = ByteBuffer.allocateDirect(17);
		ByteBuffer bf3 = ByteBuffer.allocateDirect(32);
		ByteBuffer bf4 = ByteBuffer.allocateDirect(20);
		bf1.put("Four score lemurs leaping".getBytes()).position(0).limit(11);
		bf2.put("Starsky and Hutch".getBytes()).position(8).limit(12);
		bf3.put("Snow White and the seven dwarves".getBytes()).position(19).limit(25);
		bf4.put("Years and years ago.".getBytes()).position(10).limit();
		File file = new File(FILE_PATH);
		if (!file.exists()) {
			file.createNewFile();
		}
		FileOutputStream fos = new FileOutputStream(file);
		GatheringByteChannel gatherChannel = fos.getChannel();
		ByteBuffer[] buffers = {bf1, bf2, bf3, bf4};

		while (gatherChannel.write(buffers) > 0) {
			// empty.
		}

		if (bf1.isDirect()) {
			((DirectBuffer)bf1).cleaner().clean();
		}
		if (bf2.isDirect()) {
			((DirectBuffer)bf2).cleaner().clean();
		}
		if (bf3.isDirect()) {
			((DirectBuffer)bf3).cleaner().clean();
		}
		if (bf4.isDirect()) {
			((DirectBuffer)bf4).cleaner().clean();
		}
		fos.close();
		if (gatherChannel.isOpen()) {
			gatherChannel.close();
		}
		BufferedReader br = new BufferedReader(new FileReader(new File(FILE_PATH)));
		StringBuffer sb = new StringBuffer();
		String line = "";
		while((line=br.readLine())!=null){
			sb.append(line);
		}
		System.out.println(sb.toString());
		System.out.println("期望结果:"+"Four score and seven years ago.".equalsIgnoreCase(sb.toString()));
	}

	private static void printHex() {
		System.out.println("FOO 16进制: "+strTo16("FOO"));
	}

	private static void printBinaryString() {
		String str = "FOO";
		char[] strToChar = str.toCharArray(); // 先把它他变为字符数组

		// 然后通过 Integer 中的 toBinaryString 方法来一个一个转
		for ( int i = 0; i < strToChar.length; i++ ) {
			String binStr = Integer.toBinaryString( strToChar[i] );
			System.out.println( binStr );
		}
	}

	private static void printAscii() {
		System.out.println((int)"F".charAt(0));
		System.out.println((int)"O".charAt(0));
	}

	/**
	 * 字符串转化成为16进制字符串
	 * @param s
	 * @return
	 */
	public static String strTo16(String s) {
		String str = "";
		for (int i = 0; i < s.length(); i++) {
			int ch = (int) s.charAt(i);
			String s4 = Integer.toHexString(ch);
			str = str + s4;
		}
		return str;
	}

	public static final short TYPE_PING = 0;
	public static final short TYPE_FILE = 1;
	private static void send() throws IOException {
		// //可以看到分配内存是通过unsafe.allocateMemory()来实现的，
		// 这个unsafe默认情况下java代码是没有能力可以调用到的，
		// 不过你可以通过反射的手段得到实例进而做操作，当然你需要保证的是程序的稳定性，
		// 既然叫unsafe的，就是告诉你这不是安全的，其实并不是不安全，而是交给程序员来操作，
		// 它可能会因为程序员的能力而导致不安全，而并非它本身不安全。
		ByteBuffer header = ByteBuffer.allocateDirect(10);
		ByteBuffer body = ByteBuffer.allocateDirect(80);
		ByteBuffer[] buffers = {header, body};
		body.clear();
		body.put("FOO".getBytes());
		// position指向的是下一个要写入的位置，limit指向的是最大容量。
		// 我们要读取当然要从0开始读，读到我们最后一个写入的位置。
		// 这个时候就需要手动修改position的值，让position=0;
		// 我们还要记下我们最后一个写入的位置，那么在positon=0之前，让limit=position，
		// 这样，position就是开始读取的位置，limit就是能够读的最大位置。
		// 而buffer提供了这样一个方法flip(),源码里就写了这两句：limit=position;position=0;
		body.flip();
		header.clear();
		System.out.println(Arrays.toString("FOO".getBytes()));
		System.out.printf("body.limit:%s \r\n", body.limit());
		header.putShort(TYPE_FILE).putLong(body.limit()).flip();
		File file = new File(FILE_PATH);
		if (!file.exists()) {
			file.createNewFile();
		}
		FileOutputStream fos = new FileOutputStream(file);
		GatheringByteChannel gatherChannel = fos.getChannel();
		System.out.println(header.getShort(0));
		switch (header.getShort(0)) {
			case TYPE_PING:
				break;
			case TYPE_FILE:
				// 期望结果: 0001 0000 0000 0000 0003 464f 4f
				// 结果为16进制:
				// 0001 16位  2个字节    TYPE_FILE=1
				// 0000 0000 0000 0003  64位 8个字节  即: body.limit=3
				// 464f 4f  24位  3个字节  即: "FOO"
				gatherChannel.write(buffers);
				break;
			default:
				logUnknownPacket(header.getShort(0), header.getLong(2), body);
				break;
		}

		printAscii();
		printBinaryString();
		printHex();

		if (header.isDirect()) {
			((DirectBuffer)header).cleaner().clean();
		}
		if (body.isDirect()) {
			((DirectBuffer)body).cleaner().clean();
		}
		if (gatherChannel.isOpen()) {
			gatherChannel.close();
		}
		fos.close();
	}

	private static void logUnknownPacket(short aShort, long aLong, ByteBuffer body) {
		System.out.println(String.format("aShort:%s aLong:%s body:%s", aShort, aLong, body.toString()));
	}
}
