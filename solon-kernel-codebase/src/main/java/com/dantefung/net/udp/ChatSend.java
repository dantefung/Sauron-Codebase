package com.dantefung.net.udp;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class ChatSend implements Runnable
{
	private DatagramSocket ds;
	
	public ChatSend(DatagramSocket ds)
    {
	    this.ds = ds;
    }

	@Override
    public void run()
    {
	    
	    try
        {
	        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
	        String line = null;
	        
	        while((line=br.readLine()) != null)
	        {
	        	
	        	byte[] buf = line.getBytes();
	        	DatagramPacket dp = new DatagramPacket(buf,buf.length,InetAddress.getLocalHost(),10001);
	        	ds.send(dp);
	        	
	        	if("886".equals(line))
	        	{
	        		break;
	        	}
	        }
	        
	        ds.close();
        }
        catch (Exception e)
        {
        	e.printStackTrace();
	        throw new RuntimeException(e);
        }
    }
	
}
