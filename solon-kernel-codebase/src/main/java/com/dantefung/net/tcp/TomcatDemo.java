package com.dantefung.net.tcp;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * 需求：
 * 模拟服务器给浏览器输送数据，同时证明浏览器与服务器之间的通讯是
 * 使用了tcp协议
 * @author Dante Fung
 *
 */
public class TomcatDemo extends Thread
{
	private Socket socket;
	
	public TomcatDemo(Socket socket)
    {
	    this.socket = socket;
    }
	
	// 写多线程的任务
	@Override
	public void run()
	{
	    try
	    {
	    	InputStream in = socket.getInputStream();
	    	byte[] buf = new byte[1024];
			int length = in.read(buf);
			System.out.println("tcp的服务端接收到的数据：" + new String(buf,0,length));
		    OutputStream out = socket.getOutputStream();
		    // 向浏览器输出数据
		    out.write("<font size='36px' color='red'>你好啊，浏览器！！！</font>".getBytes());
		    socket.close();
	    }
	    catch (Exception e)
	    {
		    e.printStackTrace();
	    }
	}
	
	public static void main(String[] args) throws Exception
    {
	    ServerSocket serverSocket = new ServerSocket(8080);
	    // 不断接收浏览器的请求
	    while(true)
	    {
	    	Socket socket = serverSocket.accept();
	    	new TomcatDemo(socket).start();
	    }
    }
}
