package com.dantefung.net.tcp;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Tcp协议，文本文件上传服务端
 * @author Dante Fung
 *
 */
public class UploadServer
{
	public static void main(String[] args) throws Exception
    {
	    // 1、建立tcp服务。
		ServerSocket serverSocket = new ServerSocket(10005);
		// 2、接收客户端请求。
		Socket socket = serverSocket.accept();
		System.out.println(socket.getInetAddress().getHostAddress() + "......connected");
		// 3、获取socket的字节输入流对象，转换并装饰。
		BufferedReader bufIn = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		// 4、创建字符输入流。
		BufferedWriter bw = new BufferedWriter(new FileWriter("src\\com\\dantefung\\net\\tcp\\server.txt"));
		// 不断读取客户端发送过来的数据。
		String line = null;
		while((line=bufIn.readLine()) != null)
		{
//			if("over".equals(line)) break;
			
			bw.write(line);
			bw.newLine();
			// 缓冲区默认是8kb，一旦装满会自动刷出，但一般情况下，缓冲区都未被装满
			// 因此，需要自己手动刷新一下数据，将缓冲区的数据输出去。
			bw.flush();
		}
		// 5、给客户端回写消息
		PrintWriter out = new PrintWriter(socket.getOutputStream(),true);
		out.println("上传成功！！！");
		
		// 6、释放资源.
		bw.close();
		socket.close();
		serverSocket.close();
    }
}
