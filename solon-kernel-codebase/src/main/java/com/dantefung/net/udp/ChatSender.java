package com.dantefung.net.udp;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
/**
 * 群聊的发送端
 * @author Dante Fung
 *
 */
public class ChatSender extends Thread
{
	@Override
	public void run()
	{
		try
        {
			// 1、建立UDP服务
	        DatagramSocket socket = new DatagramSocket();
	        // 2、准备要发送的数据，把数据封装到数据包中
	        String data = null;
	        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
	        // 准备数据包
	        DatagramPacket p = null;
	        while((data=br.readLine()) != null)
	        {
	        	System.out.println(data);
	        	// 把数据封装到数据包中,群发，用广播IP地址
	        	p = new DatagramPacket(data.getBytes(),data.getBytes().length,InetAddress.getByName("10.10.20.255"),9090);
	        	socket.send(p);
	        }
	        // 关闭资源
	        socket.close();
        }
        catch (Exception e)
        {
	        e.printStackTrace();
        }
	}
}
