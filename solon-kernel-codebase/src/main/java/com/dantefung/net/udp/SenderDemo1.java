package com.dantefung.net.udp;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class SenderDemo1
{
	public static void main(String[] args) throws Exception
    {
		System.out.println("发送端启动......");
	    // 1、建立UDP服务（先建立码头）
		DatagramSocket datagramSocket = new DatagramSocket();
		// 2、准备数据，封装成数据包（准备货物，装进集装箱）
		String data = "Hello World!!";
		DatagramPacket packet = new DatagramPacket(data.getBytes(),data.getBytes().length,InetAddress.getLocalHost(),9090);
		// 3、发送（运送集装箱）
		datagramSocket.send(packet);
		// 4、释放端口
		datagramSocket.close();
    }
}
