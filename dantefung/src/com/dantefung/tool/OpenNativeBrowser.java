package com.dantefung.tool;

import java.io.IOException;
/**
 * 打开本地的浏览器
 * @author Dante Fung
 * @since 2016-2-11
 */
public class OpenNativeBrowser
{
	public static void main(String[] args)
    {
//	    method1();
	    method2();
    }

	/**
	 * 开启一条线程，调用dos命令
	 */
	private static void method2()
    {
		try
        {
	        Runtime.getRuntime().exec("rundll32 url.dll,FileProtocolHandler http://www.baidu.com");
        }
        catch (IOException e)
        {
	        e.printStackTrace();
        }
	    
    }

	/**
	 * AWT：Abstract Window Tookit
	 * Java AWT Desktop扩展
	 */
	private static void method1()
    {
		//判断当前系统是否支持Java AWT Desktop扩展
        if(java.awt.Desktop.isDesktopSupported()){
            try{
                //创建一个URI实例,注意不是URL
                java.net.URI uri=java.net.URI.create("http://www.baidu.com");
                //获取当前系统桌面扩展
                java.awt.Desktop dp=java.awt.Desktop.getDesktop();
                //判断系统桌面是否支持要执行的功能
                if(dp.isSupported(java.awt.Desktop.Action.BROWSE)){
                    //获取系统默认浏览器打开链接
                    dp.browse(uri);
                }
            }catch(java.lang.NullPointerException e){
                //此为uri为空时抛出异常
            }catch(java.io.IOException e){
                //此为无法获取系统默认浏览器
            }
        }
	    
    }
}
