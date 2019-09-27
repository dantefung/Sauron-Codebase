package com.dantefung.tool;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * 联网神器
 * Process 进程类 
 * 		         本机窗口进程，守护进程，Microsoft Windows 上的 Win16/DOS 进程，
 *         或者 shell 脚本。
 *         
 * Runtime 每个 Java 应用程序都有一个 Runtime 类实例，
 *         使应用程序能够与其运行的环境相连接。
 *         可以通过 getRuntime 方法获取当前运行时。 
 * @author Dante Fung
 *
 */
public class NetConnector
{
	public void connect(String no)
	{
		// 查看ip
//		String[] cmd = {"cmd.exe","/C","ipconfig"};
//		String cmd = "cmd.exe /C ipconfig";
		// 拨号联网
		String cmd = "cmd.exe /c rasdial 宽带连接 lixueyuan0" + no + " 123456";
		int exitValue = createProcess(cmd);
        if( exitValue == 0)
        {
        	System.out.println("拨号成功！！");
        }
        else
        {
        	System.err.println("exit value=" + exitValue);
        }
	    
	}
	
	public int createProcess(String cmd)
	{
		Process process = null;
		 try
	        {
		    	// 创建一个本机进程
		        process = Runtime.getRuntime().exec(cmd);
	        }
	        catch (IOException e)
	        {
		        e.printStackTrace();
		        throw new RuntimeException(e);
	        }
		    // 获取子进程的输入流 ：实现注意事项：对输入流进行缓冲是一个好主意。 
		    InputStream in = process.getInputStream();
		    // 字节字符转换流
		    InputStreamReader isr = new InputStreamReader(in);
		    // 增加字符缓冲能力
		    BufferedReader br = new BufferedReader(isr);
		    
		    String line;
		    try
	        {
		        while((line = br.readLine()) != null)
		        {
		        	System.out.println(line);
		        }
		        
		        // 0 表示正常终止
		        int exitValue = process.waitFor();
		        System.out.println(exitValue);
		        return exitValue;
	        }
	        catch (Exception e)
	        {
		        e.printStackTrace();
		        throw new RuntimeException(e);
	        }
		    finally
		    {
		    	// 关闭资源
		    	try
	            {
		            br.close();
		            process.getOutputStream().close();
		            in.close();
		            isr.close();
	            }
	            catch (IOException e)
	            {
		            e.printStackTrace();
		            throw new RuntimeException(e);
	            }
		    }
	}
	
	public boolean ping()
	{
		Process process = null;
		// ping
		String ping = "cmd.exe /c ping www.baidu.com -n 1";
		// 断开网络连接
		String disconn = "cmd.exe /c rasdial /DISCONNECT";
		int exitValue = createProcess(ping);
		if(exitValue == 0)
		{
			System.out.println("联网成功！！");
			return true;
		}
		else
		{
			System.err.println("联网失败！正在断开连接...");
			int exitCode = createProcess(disconn);
			if(exitCode == 0)
			{
				System.out.println("断开连接成功！！");
			}
			return false;
		}
	}
	
	public static void main(String[] args)
    {
		
	    NetConnector connector = new NetConnector();
	    for(int i = 1; i < 61; i ++)
	    {
	    	System.out.println("==========第" + i + "次 拨号连接===========");
	    	connector.connect(i<=9?"0"+i:i+"");
	    	//connector.connect(String.format("%02d", i));
	    	if(connector.ping())
	    	{
	    		break;
	    	}
	    	System.out.println("正在重新尝试连接...");
	    }
    }
}
