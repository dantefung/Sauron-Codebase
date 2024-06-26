package com.dantefung.nio.reactor.multithread.client;
 
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
 
public class Client {
 
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
//		String hostname=args[0];
//		int port = Integer.parseInt(args[1]);
		String hostname="127.0.0.1";
		int port=9090;
		
		System.out.println("Connecting to "+ hostname +":"+port);
		try {
			Socket client = new Socket(hostname, port); // 連接至目的地
			System.out.println("Connected to "+ hostname);
			
			PrintWriter out = new PrintWriter(client.getOutputStream());
			BufferedReader in = new BufferedReader(new InputStreamReader(client.getInputStream()));
			BufferedReader stdIn = new BufferedReader(new InputStreamReader(System.in));
			String input;
			
			while((input=stdIn.readLine()) != null) { // 讀取輸入
				out.println(input); // 發送輸入的字符串
				out.flush(); // 強制將緩衝區內的數據輸出
				if(input.equals("exit"))
				{
					break;
				}
				System.out.println("server: "+in.readLine());
			}
			client.close();
			System.out.println("client stop.");
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			System.err.println("Don't know about host: " + hostname);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			System.err.println("Couldn't get I/O for the socket connection");
		}
		
	}
 
}