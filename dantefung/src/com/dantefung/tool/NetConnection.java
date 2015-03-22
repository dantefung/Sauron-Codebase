package com.dantefung.tool;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
//Î´²âÊÔÍ¨¹ý¡£
public class NetConnection {

	public static void main(String[] args)
	{
		String str1 = " 123456";
		String outInfo ="";
		String[] disconn ={"cmd","/c","rasdial","/DISCONNECT"}; 
		String[] ping ={"cmd","/c","ping","180.97.33.107","-n","1"};
		
		for (int i = 10; i < 61; i++)
		{		
			StringBuffer conn = new StringBuffer();		
			conn.append("Rasdial my lixueyuan0")
			    .append(Integer.toString(i))
			    .append(str1);			
			System.out.println("cmd /c " + conn);
			try
			{
				Runtime.getRuntime().exec(disconn);
				Process proconn = Runtime.getRuntime().exec("cmd /c" + conn.toString());
				try
				{
					if(proconn.waitFor() !=0 )
					{
						System.err.println("exit value=" + proconn.exitValue());
					}
					else
					{
						System.out.println("²¦ºÅ³É¹¦£¡£¡");
					}
				}
				catch(InterruptedException e)
				{
					System.err.print(e);
					e.printStackTrace();
				}
				Process process = Runtime.getRuntime().exec(ping);
				InputStream in = process.getErrorStream();
				BufferedReader br = new BufferedReader(new InputStreamReader(in));
				String line = "";
				while((line=br.readLine() )!=null)
				{
					 outInfo = outInfo + line +"\n";
					 System.out.println(outInfo);
				}
	
				//¼ì²éÃüÁîÊÇ·ñÊ§°Ü¡£
				try
				{
					if(process.waitFor() !=0)
					{
						System.err.println("exit value=" + process.exitValue());
					}
					else
					{
						System.out.println("ÁªÍø³É¹¦£¡£¡");
						break;
					}
				}
				catch(InterruptedException e)
				{
					System.err.print(e);
					e.printStackTrace();
				}
			}
			catch(IOException e)
			{
				System.out.println("exec error:" + e.getMessage());
				e.printStackTrace();
			}
		}
		

	}
}
