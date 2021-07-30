package com.dantefung.nio.client;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.Socket;

/**
 * Tcp客户端
 * @author Dante Fung
 *
 */
public class ConnectionPerThreadClient1 {
	/**
	 * 客户端发送数据到服务端
	 *
	 * Tcp传输：客户端建立过程
	 * 1、创建tcp客户端socket服务，使用的是socket对象。建议该对象一创建就要明确目的地，即要连接的主机。
	 * 2、如果建立连接成功，说明数据传输的通道已经建立。
	 *     该通道就是socket流，是低层建立好的。既然是流，说明这里有输入，又有输出。
	 *     想要输入或者输出流对象，可以找socket来获取。
	 *     可以通过getOutputStream（）、getInputStream（）来获取两个字节流对象。
	 * 3、使用输出流，将数据写出。
	 * 4、关闭资源。
	 * @param args
	 * @throws Exception
	 */
	public static void main(String[] args) throws Exception {
		// 1、建立TCP服务,TCP的客户端一旦启动，马上就要建立连接。
		Socket socket = new Socket(InetAddress.getLocalHost(), 9090);
		// 2、获取对应的流对象，因为TCP是基于IO流进行数据传输的。
		String data = "我是1号客户端，这是我的第一个TCP例子！！";
		OutputStream outputStream = socket.getOutputStream();
		// 3、把数据写出
		outputStream.write(data.getBytes());
		// 读取服务端反馈的数据
		InputStream inputStream = socket.getInputStream();
		byte[] buf = new byte[1024];
		int len = inputStream.read(buf);
		System.out.println("tcp的服务器收到的数据：" + new String(buf, 0, len));
		// 4、关闭资源
		socket.close();
	}
}
