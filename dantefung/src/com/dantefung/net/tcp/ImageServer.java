package com.dantefung.net.tcp;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashSet;

/**
 * tcp传输文件的服务器，服务端要接受多个客户端的连接，
 * 一旦建立连接，马上给客户端发送图片数据
 * 
 * @author Dante Fung
 *
 */
public class ImageServer extends Thread
{
	// 单线程版本的代码
	/*@Override
	public void run()
	{
		try
        {
	        // 1、建立tcp服务端
			ServerSocket serverSocket = new ServerSocket(9090);
			// 2、接收客户端的请求连接
			Socket socket = serverSocket.accept();
			// 3、给客户端发送图片数据
			// 3.1、获取输入流对象
			BufferedInputStream bis = new BufferedInputStream(new FileInputStream("c:aa.jpg"));
			// 3.2、获取输出流对象
			OutputStream out = socket.getOutputStream();
			// 3.3、回写图片数据
			byte[] buf = new byte[1024];
			int len = 0;
			while((len = bis.read(buf)) != -1)
			{
				out.write(buf, 0, len);
			}
			// 4、释放资源
			bis.close();
			socket.close();
        }
        catch (Exception e)
        {
        	e.printStackTrace();
        	throw new RuntimeException(e);
        }
	}*/
	
	// 改造为多线程版本的代码
	
	private Socket socket;
	
	// 用于存储ip地址
	private HashSet<String> ips = new HashSet<String>();
	
	public ImageServer(Socket socket)
    {
		this.socket = socket;
    }
	
	@Override
	public void run()
	{
		try
		{
			// 3、给客户端发送图片数据
			// 3.1、获取输入流对象
			BufferedInputStream bis = new BufferedInputStream(new FileInputStream("c:aa.jpg"));
			// 3.2、获取输出流对象
			OutputStream out = socket.getOutputStream();
			// 3.3、回写图片数据
			byte[] buf = new byte[1024];
			int len = 0;
			while((len = bis.read(buf)) != -1)
			{
				out.write(buf, 0, len);
			}
			
			// 获取对方的IP地址对象
			String ip = socket.getInetAddress().getHostAddress();
			/**
			 * 由于是多个客户端可以下载且每个客户端独立且唯一，因此，统计的时候
			 * 不应该有重复的ip地址，所以，我们将选用一个集合来存储，根据以上的需求
			 * 我应该选择的是set集合（set集合里面的元素是不允许重复的），set集合
			 * 有哪些子接口？treeSet（用于排序用的） & hashSet(不允许重复)
			 */
			if(ips.add(ip))
			{
				System.out.println("恭喜：" + ip + "用户下载成功！！  当前下载的人数是：" + ips.size());
			}
			
			// 4、释放资源
			bis.close();
			socket.close();
	    }
	    catch (Exception e)
	    {
	    	e.printStackTrace();
	    	throw new RuntimeException(e);
	    }
	}
	
	public static void main(String[] args) throws Exception
    {
	    // 1、建立tcp服務端
		ServerSocket serverSocket = new ServerSocket(9090);
		// 不断接受客户端的连接
		while(true)
		{
			// 2、接收客户端的请求连接
			Socket socket = serverSocket.accept();
			// 为每个客户端开启一条线程
			new ImageServer(socket).start();
		}
		
    }
}
