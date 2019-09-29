package com.dantefung.net.tcp;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;

/**
 * Tcp协议，文本文件上传客户端。
 * @author Dante Fung
 *
 */
public class UploadClient
{
	public static void main(String[] args) throws Exception
    {
	    // 1、建立tcp通讯插座。
		Socket socket = new Socket(InetAddress.getLocalHost(),10005);
		// 2、创建字符输入流，读取本地的文本文件。
		BufferedReader br = new BufferedReader(new FileReader("src\\com\\dantefung\\net\\tcp\\client.txt"));
		// 3、获取socket的输出流，将该流包装成打印流(打印流，可指定自动刷新和输出自动换行)。
		PrintWriter out = new PrintWriter(socket.getOutputStream(),true);
		
		// 4、不断地给服务端写数据
		String line = null;
		while((line=br.readLine()) != null)
		{
			out.println(line);
		}
		// 告诉服务端，客户端的数据写完了。
		socket.shutdownOutput();
//		out.println("over");// 结束标记，软件开发中一般先发送一个当前时间的毫秒值给服务端，服务端到时就直接以这个毫秒值作为客户端结束写数据的标记
		
		// 5、读取服务端返回的数据。
		BufferedReader bufIn = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		
		String str = bufIn.readLine();
		System.out.println(str);
		
		// 6、释放资源.
		socket.close();
		
    }
}
