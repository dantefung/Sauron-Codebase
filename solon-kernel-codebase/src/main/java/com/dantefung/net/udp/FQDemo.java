package com.dantefung.net.udp;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
/**
 * 需求：给FeiQ发送消息
 * 
 * 凡是网络编程软件都要对数据进行加密，这样子做的目的是考虑到了安全性问题。
 * 而加密的方式是最简单的就是接收指定格式的字符串。如果接收到的数据不符合所要
 * 求的格式，那么该数据就会直接丢弃，不处理。
 * 
 * 飞Q要求接收的数据格式：
 *     version:time:sender:ip：flag:content
 *     version:版本。1.0
 *     time:时间。
 *     sender:发送者。
 *     ip:发送者的IP地址。
 *     flag:发送的标识，32（标识你要聊天）
 *     content:才是你真正希望要发送的内容。
 *     
 * @author Dante Fung
 *
 */
public class FQDemo
{
	public static void main(String[] args) throws Exception
    {
	    // 1、建立UDP服务
		DatagramSocket datagramSocket = new DatagramSocket();
		// 2、准备要发送的数据，封装成数据包
		String data = getData("Hello FQ!");
		byte[] buf = data.getBytes();
		DatagramPacket packet = new DatagramPacket(buf, buf.length, InetAddress.getByName("169.254.147.26"), 2425);
		// 3、发送数据
		datagramSocket.send(packet);
		// 4、释放资源（释放端口）
		datagramSocket.close();
    }
	
	public static String getData(String content)
	{
		StringBuilder sb = new StringBuilder();
		sb.append("1.0:")
		  .append(System.currentTimeMillis()+":")
		  .append("Dante:")
		  .append("192.168.1.1:")
		  .append("32:")
		  .append(content);
		return sb.toString();
	}
}
