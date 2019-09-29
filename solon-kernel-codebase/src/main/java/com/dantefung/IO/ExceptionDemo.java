package com.dantefung.IO;
/*
 * 异常：程序出现了不正常的情况。
 * 
 * 
 * 程序的异常： 
 *     严重问题：Error  我们不处理。这种问题一般都是很严重的，拨入说内存溢出。
 *     问题：Exception
 *         编译时问题:不是RuntimeException异常。要处理。
 *         运行时问题:RuntimeException  这种问题我们也不处理，因为是你的问题，而且这个问题出现肯定就是我们的代码不严谨。
 * 
 * 如果程序出现了问题，我们没有做出处理，那么jvm会做出默认的处理。
 * 
 * 把异常的名称，原因及出现的问题等信息输出在控制台。
 * 
 * 同时会结束程序。
 * 我们的程序不能继续执行。
 * **/
public class ExceptionDemo {
    public static void main(String[] args)
    {
    	int a = 10;
    	int b = 0;
    	
    	System.out.println(a/b);
    	System.out.println("over");
    }
}
