package com.dantefung.net.udp;

import java.net.DatagramPacket;
import java.net.DatagramSocket;

public class ReceiveDemo1
{
	public static void main(String[] args) throws Exception
    {
		// 1、建立UDP服务端，并且要监听一个端口
		DatagramSocket datagramSocket = new DatagramSocket(9090);
		// 2、准备一个空的数据包，准备接收数据
		byte[] buf = new byte[1024];
		DatagramPacket packet = new DatagramPacket(buf,buf.length);// 数据包本身不具备存储的能力，是借用了buf字节数组进行存储的
		System.out.println("receive方法之前...");
		// 3、调用udp的服务，接收数据包
		datagramSocket.receive(packet);// 接收发送过来的数据包，内容已经存储到字节数组中。
		// receive方法是一个阻塞型的方法，如果没有接收到数据包，会一直等待下去。
		System.out.println("接收端接收到的数据：" + new String(buf,0,packet.getLength()));// packet.getLength() 获取数据包本次接收的字节个数
		//System.out.println("接收端接收到的数据：" + new String(buf));
		System.out.println("receive方法之后...");
		// 4、关闭资源（释放端口）
		datagramSocket.close();
    }

}
