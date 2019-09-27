package com.dantefung.net.udp;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

/**
 * 群聊的接收端
 * @author Dante Fung
 *
 */
public class ChatReceive extends Thread
{
	@Override
	public void run()
	{
		try
        {
			// 1、建立UDP服务
	        DatagramSocket socket = new DatagramSocket(9090);
	        // 2、准备一个空的数据包，接收数据
	        byte[] buf = new byte[1024];
	        DatagramPacket packet = new DatagramPacket(buf,buf.length);
	        // 3、不断的接收数据
	        boolean flag = true;
	        while(flag)
	        {
	        	socket.receive(packet);
	        	// 获取对方的ip地址对象
	        	InetAddress ip = packet.getAddress();
	        	System.out.println(ip.getHostAddress() + "说：" + new String(buf,0,packet.getLength()));
	        }
	        // 4、关闭资源（释放端口）
	        socket.close();
        }
        catch (Exception e)
        {
	        e.printStackTrace();
        }
	}
}
