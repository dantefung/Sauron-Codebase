package com.dantefung.net.tcp;

import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Tcp服务端
 * @author Dante Fung
 *
 */
public class Demo2Server
{
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
		InputStream in = socket.getInputStream();
		byte[] buf = new byte[1024];
		int length = in.read(buf);
		System.out.println("tcp的服务端接收到的数据：" + new String(buf,0,length));
		System.out.println("====accept之后===");
		
		// 服务端要反馈数据给客户端
		
		
		// 4、关闭资源
		socket.close();
		// 正常情况下，做成服务器的话，一般是不关闭的。
		//serverSocket.close();
		
    }
}
