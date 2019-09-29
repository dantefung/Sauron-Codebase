package com.dantefung.net.udp;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;

public class SafeReceive {
	
	public static void main(String[] args) {
		try {
			//第一步： 建立udp的服务，并且要监听一个端口
			DatagramSocket socket = new DatagramSocket(9090);
			//第二步： 准备空的数据包，准备接收数据
			byte[] buf = new byte[1024];
			DatagramPacket packet = new DatagramPacket(buf, buf.length);
			boolean flag = true;
			while(flag){
				socket.receive(packet);
				System.out.println(new String(buf,0,packet.getLength()));
				Thread.sleep(100);
			}
			//关闭资源
			socket.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
