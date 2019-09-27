package com.dantefung.net.udp;

import java.net.DatagramSocket;

public class ChatClient
{
	public static void main(String[] args) throws Exception
    {
	    DatagramSocket send = new DatagramSocket();
	    DatagramSocket rece = new DatagramSocket(10001);
	    new Thread(new ChatRece(rece)).start();
	    new Thread(new ChatSend(send)).start();
    }
}
