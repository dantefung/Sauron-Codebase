package com.dantefung.net.udp;


public class ChatMain
{
	public static void main(String[] args) throws Exception
    {
	    // 创建线程对象
		ChatSender sender = new ChatSender();
		ChatReceive receive = new ChatReceive();
		// 启动线程
		sender.start();
		receive.start();
    }
}
