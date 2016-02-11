package com.dantefung.net.tcp;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Tcp文本转换服务端
 * @author Dante Fung
 *
 */
public class TransServer
{
	public static void main(String[] args) throws Exception
    {
		System.out.println("服务器端启动... ...");
	    /*
	     * 文本转换服务端：
	     * 分析：
	     * 1、ServerSocket对象。
	     * 2、获取socket对象。
	     * 3、源：socket,读取客户端发送过来需要转换的数据。
	     * 4、目的：显示在控制台上。
	     * 5、将数据转换成大写发送给客户端。
	     */
		// 1、
		ServerSocket serverSocket = new ServerSocket(10004);
		// 2、获取socket对象。
		Socket socket = serverSocket.accept();
		// 获取Ip
		String ip = socket.getInetAddress().getHostAddress();
		System.out.println(ip + ".........connected");
		// 3、获取socket字节读取流，转换并装饰。
		BufferedReader brIn = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		// 4、获取socket字节输出流，转换并装饰（用这个方法的时候，记得在写出数据的时候，记得调用一下刷新方法）。
		BufferedWriter bwOut = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
//		PrintWriter pwOut = new PrintWriter(socket.getOutputStream(),true);
		
		String line = null;
		/**
		 * 阻塞式方法，读到换行标记才认为一行数据读完。否则，会认为数据没有读完，
		 * 一直等待数据过来。
		 */
		while((line=brIn.readLine()) != null)
		{
			System.out.println(line);
//			pwOut.println(line.toUpperCase());
			bwOut.write(line.toUpperCase() + "\r\n");// 换行符为阻塞式方法readLine()的结束标记
			bwOut.flush();//将数据从BufferedReader 或 BufferedWriter刷到socket的输入输出流中
		}
		
		// 5、释放资源
		socket.close();
		serverSocket.close();
    }
}
