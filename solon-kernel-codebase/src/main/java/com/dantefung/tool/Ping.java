package com.dantefung.tool;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class Ping {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String[] ping ={"cmd","/c","ping","180.97.33.107","-n","1"};
		String line ="";
		String outInfo ="";
		try {
			Process process = Runtime.getRuntime().exec(ping);
			
			InputStream ins = process.getErrorStream();
			BufferedReader br = new BufferedReader(new InputStreamReader(ins));			
			while((line = br.readLine()) != null)
			{
				outInfo = outInfo + line + "\n";
				System.out.println(outInfo);
			}
			
			process.getOutputStream().close();
			
			//检查命令是否失败。
			try
			{
				if(process.waitFor() !=0)
				{
					System.err.println("exit value=" + process.exitValue());
				}
				else
				{
					System.out.println("联网成功！！");
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
