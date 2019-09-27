package com.dantefung.test;

import java.util.*;
import java.io.*;
//模拟迅雷下载，单线程，本地版。
public class XunLeiDemo {
	public static void main(String[] args) throws IOException {
		File file = new File("D:\\mysql-5.5.27-win32.msi");
		new File("D:\\xiazai").mkdir();
		if(file.exists()){
			Vector<InputStream> vector = new Vector<InputStream>();
			FileInputStream fis = new FileInputStream(file);
			FileOutputStream fos = null;
			byte[] by = new byte[1024*1024];
			int len = 0,i=0;
			while((len = fis.read(by))!= -1){
				File xiazai = new File("D:\\xiazai\\"+(++i)+".suipian");
				fos = new FileOutputStream(xiazai);
				fos.write(by, 0, len);
				fos.close();
				vector.add(new FileInputStream(xiazai));
			}
			Enumeration<InputStream> ert = vector.elements();
			SequenceInputStream sis = new SequenceInputStream(ert);
			fos = new FileOutputStream("D:\\下载成功"+file.toString().substring(file.toString().lastIndexOf(".")));
			while((len = sis.read(by))!=-1){
				fos.write(by, 0, len);
			}
			fos.close();
			System.out.println("哇塞，文件下载成功了!");
		}else
			throw new RuntimeException("对不起，您所下载的文件不存在!");
	}
}
