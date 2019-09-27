package com.dantefung.test;
//调用者。
public class Computer implements CallBackFun{	
	String data = "";
	private IODevice IODevice;
	
	public Computer()
	{
		
	}
	
	public Computer(IODevice iodevice)
	{
		this.IODevice = iodevice;
	}
	
	//正常作业的计算函数。
	public void compute()
	{
		System.out.println("Computer 处理作业中... ...");
		System.out.println("*********computer:iodevice请给我数据***********" + "\n");
		this.IODevice.IODevice("回调Computer的回调函数", new Computer());/*向IO设备发出收集数据的请求的同时，
																	将自己的对象传入，待IO设备收集完了数据，
																	以便回调回调函数来通知我数据已经收集完成。
																	*/
		
		
		//29行有bug待修正。
		if(getData().equals("回调Computer的回调函数-----IODevice已经附加数据。"))
		{
			System.out.println("IODevice的通知已经收到！");
		}
		else
		{
			System.out.println("未收到IODevice的信息。");
		}
		

	}
	
	//回调函数。
	@Override
	public void callback(String data) 
	{
		// TODO Auto-generated method stub
		setData(data);
		System.out.println("Computer:你的数据已经收到。" + "\n");
		System.out.println(data);
		System.out.println("Sucessful!!!");
	}
	
	private void setData(String data)
	{
		this.data = data;
	}
	
	private String getData()
	{
		return this.data;
	}

					
}

