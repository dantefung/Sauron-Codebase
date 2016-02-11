package com.dantefung.net.udp;

import java.net.DatagramPacket;
import java.net.DatagramSocket;

public class ReceiveDemo1
{
	/**
	 * 建立UDP接收端的思路：
	 * 1、建立UDP socket服务,因为要接收数据，必须明确一个端口号。
	 * 2、创建数据包，用于存储收到的数据。方便用数据包对象的方法解析这些数据。
	 * 3、使用socket服务的receive方法将接收的数据存储到数据包中。
	 * 4、通过数据包的方法解析数据包中的数据。
	 * 5、关闭资源。
	 * @param args
	 * @throws Exception
	 */
	public static void main(String[] args) throws Exception
    {
		System.out.println("接收端启动.......");
		// 1、建立UDP接收端，并且要监听一个端口
		DatagramSocket datagramSocket = new DatagramSocket(9090);
		// 2、准备一个空的数据包，准备接收数据
		byte[] buf = new byte[1024];
		DatagramPacket packet = new DatagramPacket(buf,buf.length);// 数据包本身不具备存储的能力，是借用了buf字节数组进行存储的
		System.out.println("receive方法之前...");
		// 3、调用udp的服务，接收数据包
		datagramSocket.receive(packet);// 接收发送过来的数据包，内容已经存储到字节数组中。
		// receive方法是一个阻塞型的方法，如果没有接收到数据包，会一直等待下去。
		// 4、通过数据包对象的方法，解析其中的数据，比如：地址、端口、数据内容等
		String ip = packet.getAddress().getHostAddress();
		int port = packet.getPort();
		String text = new String(packet.getData(),0,packet.getLength());
		System.out.println(" ip: " + ip + " port: " + port + " text: " + text);
		System.out.println("接收端接收到的数据：" + new String(buf,0,packet.getLength()));// packet.getLength() 获取数据包本次接收的字节个数
		//System.out.println("接收端接收到的数据：" + new String(buf));
		System.out.println("receive方法之后...");
		// 5、关闭资源（释放端口）
		datagramSocket.close();
    }

}
