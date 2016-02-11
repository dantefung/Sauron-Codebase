package com.dantefung.net.tcp;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
/**
 * TCP文本转换客户端
 * @author Dante Fung
 *
 */
public class TransClient
{
	public static void main(String[] args) throws Exception
    {
		System.out.println("客户端启动... ...");
	    /*
	     * 思路：
	     * 客户端：
	     * 1、先有socket端点。
	     * 2、客户端的数据源：键盘。
	     * 3、客户端的目的：socket。
	     * 4、接收服务端的数据，源：socket。
	     * 5、将数据显示在控制台，目的：控制台。
	     * 6、在这些流中操作的数据都是文本数据。
	     * 
	     * 转换客户端：
	     * 1、创建socket客户端对象。
	     * 2、获取键盘录入。
	     * 3、将录入的信息发送给socket输入流。
	     */
		// 1、创建socket客户端对象。
		Socket socket = new Socket("192.168.1.102",10004);
		// 2、获取键盘录入。
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		// 3、socket输出流
		/**
		 * 你输出数据的时候，是不是保证了数据原样性不变的出去了。
		 * PrintWriter
		 * 
		 * 记住一点，我们写socket流的时候,凡是名称里面带有out、in的，我们都视为socket流。
		 * 凡是不带in、out的我们都视为一般流，本地流。
		 * 注意：这不是规范，这只是我总结的规律，便于能从名字中一眼看出是什么流。
		 */
		BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
		// 使用个高级一点的打印流
		/*
		 * 参数说明：
		 * 1、字节输出流
		 * 2、是否自动刷新
		 *   还带自动换行。
		 */
//		PrintWriter out = new PrintWriter(socket.getOutputStream(),true);
		
		// 4、 socket的输入流，读取服务端返回的大写数据。
		InputStream in = socket.getInputStream();
		// 为了操作字符方便，我们将字节输入流转换为字符输入流。
		InputStreamReader isr = new InputStreamReader(in);
		// 为了让读取效率高些，我们转换后的字符输入流提供缓冲功能（装饰者模式）。
		BufferedReader bufIn = new BufferedReader(isr);
		String line = null;
		/**
		 * readLine方法是一个阻塞的方法,读到换行标记才认为一行数据读完。
		 * 否则，会认为数据没有读完，一直等待数据过来。
		 */
		while((line=br.readLine()) != null )
		{
			if("over".equals(line))
			{
				break;
			}
			
			// 这是带着自动刷新功能在打印
//			out.println(line);
			bw.write(line + "\r\n");
			bw.flush();
			
			// 读取服务端发送回来的一行大写数据
			String upperStr = bufIn.readLine();
			System.out.println("客户端===" + upperStr);
			System.out.println(upperStr);
		}
		
		// 5、释放资源（关闭流，并给socket打上结束标记）。
		socket.close();
    }
}
