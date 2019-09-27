package com.dantefung.tool;

import java.io.IOException;
/**
 * �򿪱��ص������
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
	 * ����һ���̣߳�����dos����
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
	 * AWT��Abstract Window Tookit
	 * Java AWT Desktop��չ
	 */
	private static void method1()
    {
		//�жϵ�ǰϵͳ�Ƿ�֧��Java AWT Desktop��չ
        if(java.awt.Desktop.isDesktopSupported()){
            try{
                //����һ��URIʵ��,ע�ⲻ��URL
                java.net.URI uri=java.net.URI.create("http://www.baidu.com");
                //��ȡ��ǰϵͳ������չ
                java.awt.Desktop dp=java.awt.Desktop.getDesktop();
                //�ж�ϵͳ�����Ƿ�֧��Ҫִ�еĹ���
                if(dp.isSupported(java.awt.Desktop.Action.BROWSE)){
                    //��ȡϵͳĬ�������������
                    dp.browse(uri);
                }
            }catch(NullPointerException e){
                //��ΪuriΪ��ʱ�׳��쳣
            }catch(IOException e){
                //��Ϊ�޷���ȡϵͳĬ�������
            }
        }
	    
    }
}
