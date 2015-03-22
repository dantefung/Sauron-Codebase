package com.dantefung.tool;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
/**
 * @author DanteFung
 * @version 1.0
 * @since 2015.3.20
 * 
 * **/
public class TestConnection {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String[] disconn ={"cmd","/c","rasdial","/DISCONNECT"};
		String[] conn ={"cmd","/c","Rasdial","Test","fenghaolin","113518"};
		String[] ping ={"cmd","/c","ping","180.97.33.107","-n","1"};
		String line ="";
		String outInfo ="";
		
		try {
			Runtime.getRuntime().exec(disconn);
			Process proconn = Runtime.getRuntime().exec(conn);
			//¼ì²éÃüÁîÊÇ·ñÊ§°Ü¡£
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
			
			InputStream ins = process.getErrorStream();
			BufferedReader br = new BufferedReader(new InputStreamReader(ins));			
			while((line = br.readLine()) != null)
			{
				outInfo = outInfo + line + "\n";
				System.out.println(outInfo);
			}
			
			process.getOutputStream().close();
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
				}
			}
			catch(InterruptedException e)
			{
				System.err.print(e);
				e.printStackTrace();
			}
			
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

}
