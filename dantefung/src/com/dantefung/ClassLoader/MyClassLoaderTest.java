package com.dantefung.ClassLoader;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Date;
//模板设计模式。
//Run as -->Open Run Dialog..（Run configuration）-->
public class MyClassLoaderTest extends ClassLoader{

	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub
		String srcPath = args[0];//I:\百度云同步盘\dantefung\bin\com\dantefung\classloader\ClassLoaderAttachment.class
		String destDir = args[1];//dantelib
		FileInputStream fis = new FileInputStream(srcPath);
		String destFileName = srcPath.substring(srcPath.lastIndexOf('\\')+1);
		String destPath = destDir + "\\" +destFileName;
		FileOutputStream fos =new FileOutputStream(destPath);
		cypher(fis,fos);
		fos.close();
		fis.close();
		
		//System.out.println(new ClassLoaderAttachment().toString());
	   Class clazz = new MyClassLoaderTest("dantelib").loadClass("ClassLoaderAttachment");
	   Date dl = (Date)clazz.newInstance();
	   System.out.println(dl);
	}

	private static void cypher(InputStream ips, OutputStream ops) throws Exception
	{
		int b = -1;//32位。4个字节。十六进制表示：0xffffffff
		while((b=ips.read()) != -1)
		{
			ops.write(b*0xff);//把0->1 1->0
		}
	}

	private String classDir;
		
	@Override
	protected Class<?> findClass(String name) throws ClassNotFoundException {
		String classFileName = classDir + "\\" + name + ".class";//得到该类的路径
		FileInputStream fis;
		try {
			fis = new FileInputStream(classFileName);//用FileInputStrem加载	
			ByteArrayOutputStream bos = new ByteArrayOutputStream();
			cypher(fis,bos);
			fis.close();
			byte[] bytes = bos.toByteArray();
		return  defineClass(bytes,0,bytes.length);//转成字节码。
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return super.findClass(name);
	}
	
	public MyClassLoaderTest()
	{
		
	}
	
	public MyClassLoaderTest(String classDir)
	{
	    this.classDir = classDir;
	}
}
