package com.dantefung.test;
//回调函数的测试类。
public class ToTestCallbackfun {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Computer computer = new Computer(new IODevice());
		computer.compute();/*计算机在处理数据的过程中向IO设备发送消息，
		*让其收集数据，并在收集完成后，回调计算机的回调函数，
		*告诉计算机数据已经收集完成。
		*/
		
	
	}
}
