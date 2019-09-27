package com.dantefung.net.udp;

import java.net.DatagramPacket;
import java.net.DatagramSocket;

public class ChatRece implements Runnable
{
	private DatagramSocket ds;
	
	public ChatRece(DatagramSocket ds)
    {
	    this.ds = ds;
    }

	@Override
    public void run()
    {
	    try
        {
	        while(true)
	        {
	        	// 2、准备一个空的数据包，准备接收数据
	        	byte[] buf = new byte[1024];
	        	DatagramPacket packet = new DatagramPacket(buf,buf.length);// 数据包本身不具备存储的能力，是借用了buf字节数组进行存储的
	        	// 3、调用udp的服务，接收数据包
	        	ds.receive(packet);// 接收发送过来的数据包，内容已经存储到字节数组中。
	        	// receive方法是一个阻塞型的方法，如果没有接收到数据包，会一直等待下去。
	        	// 4、通过数据包对象的方法，解析其中的数据，比如：地址、端口、数据内容等
	        	String ip = packet.getAddress().getHostAddress();
	        	int port = packet.getPort();
	        	String text = new String(packet.getData(),0,packet.getLength());
	        	System.out.println(" ip: " + ip + " :port: " + port + " ：: " + text);
	        	if("886".equals(text))
	        	{
	        		System.out.println(ip + "::" + "退出了聊天室！！");
	        	}
	        }
        }
        catch (Exception e)
        {
	        e.printStackTrace();
	        throw new RuntimeException(e);
        }
	    
    }
	
}
