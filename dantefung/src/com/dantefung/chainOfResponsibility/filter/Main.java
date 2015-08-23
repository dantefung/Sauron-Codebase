package com.dantefung.chainOfResponsibility.filter;

/**
 * @autor DanteFung
 * 
 * 这种写法叫：先写测试程序，再写是实现。
 * 《极限编程》里面有一条原则，就是先写测试，当写完测试之后，就知道自己的类，需要实现哪些方法。
 * 
 * Filter   
 * */
public class Main {
	
	public static void main(String[] args){
		String msg = "大家好:）,<script>,敏感，被就业，网络授课没感觉，因为看不到大家伙";
		MsgProcessor mp = new MsgProcessor();
		mp.setMsg(msg); 
		FilterChain fc = new FilterChain();
		fc.addFilter(new HTMLFilter())
		  .addFilter(new SensitiveFilter());
		
		FilterChain fc2 = new FilterChain();
		fc2.addFilter(new FaceFilter());
		fc2.addFilter(fc);
		mp.setFc(fc);
		String result = mp.process();
		System.out.println(result);
	}

}
