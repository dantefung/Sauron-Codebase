package com.dantefung.test;
/**
 * @author dantefung
 * @since  2015-3-30
 * 
 * 被调用者。
 * */
public class IODevice {

	private Computer computer;

	public void IODevice(String data, Computer computer)
	{
		this.computer = computer;
		System.out.println("*****************IODevice：" + "Computer，您好，你的请求已收到..." + "***************");
		int i = 0;
		while(i < 10)
		{
			if(i == 9)
			{
				System.out.println("数据收集完毕-------" + "\n");	
				System.out.println("IODevice:Computer请接收数据" + "\n");
				computer.callback(data.concat("-----IODevice已经附加数据。"));
		        break;
			}
			
			System.out.println("数据收集中。。。");
			++ i;
		}
	}
	


	
}
