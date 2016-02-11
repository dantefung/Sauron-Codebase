package com.dantefung.net.tcp;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Tcp服务端
 * @author Dante Fung
 *
 */
public class Demo2Server
{
	/**
	 * 建立服务端的的思路：
	 * 1、创建服务端socket对象。通过serverSocket对象。
	 * 2、服务端必须对外提供一个端口，否则客户端无法连接。（主机上的服务端程序，那么端口就是用于标识这服务端程序的进程）
	 * 3、获取连接过来的客户端对象。
	 * 4、通过客户端对象获取socket流读取客户端发来的数据，并打印到控制台上。
	 * 5、关闭资源。
	 * @param args
	 * @throws Exception
	 */
	public static void main(String[] args) throws Exception
    {
	    // 1、建立tcp服务，而且要监听一个端口。
		ServerSocket serverSocket = new ServerSocket(9090);
		// 2、接收客户端的请求连接
		/**
		 * 按常理来说，tcp是基于IO流进行数据传输的，第二步应该是获取对应输入流对象，读取客户端输送的数据。
		 * 非常遗憾，serverSocket没有getInputStream()这个方法，但是！
		 * 谁有呢？socket有。那socket有跟serverSocket有什么关系呢？
		 * 有，serverSocket可以产生sokcet。
		 */
		System.out.println("====accept之前===");
		Socket socket = serverSocket.accept();// 是一个阻塞型的方法，如果没有客户端与其连接，那么会一直等待下去。
		//3、通过socket对象获取输入流，读取客户端发来的数据
		InputStream in = socket.getInputStream();
		byte[] buf = new byte[1024];
		int length = in.read(buf);
		System.out.println("tcp的服务端接收到的数据：" + new String(buf,0,length));
		System.out.println("====accept之后===");
		
		// 服务端要反馈数据给客户端：使用客户端socket对象的输出流给客户端返回数据。
		OutputStream outputStream = socket.getOutputStream();
		outputStream.write("客户端您辛苦了！！".getBytes());
		
		// 4、关闭资源
		socket.close();
		// 正常情况下，做成服务器的话，一般是不关闭的。
		//serverSocket.close();
		
    }
}
