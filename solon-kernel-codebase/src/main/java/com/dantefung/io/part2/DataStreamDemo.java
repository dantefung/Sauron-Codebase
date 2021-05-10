package com.dantefung.io.part2;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * 
 * @since 2015-4-4
 * 
 *可以读写基本数据类型的数据
 *
 * 数据输入流：DataInputStream
 *         DataInputStream(InputStream in)
 * 数据输出流：DataOoutputStream
 *         DataOutputStream(OutputStream out)
 * 
 */
public class DataStreamDemo {

	/**
	 * @param args
	 * @throws IOException 
	 */
	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
        //写
		write();
		//读
		read();
	}

	private static void read() throws IOException{
		// TODO Auto-generated method stub
		
		//DataInputStream
		//创建数据输入流对象。
		DataInputStream dis = new DataInputStream(new FileInputStream("dos.txt"));
		
		//读数据
		byte b = dis.readByte();
		short s = dis.readShort();
		int i = dis.readInt();
		long l = dis.readLong();
		float f = dis.readFloat();
		double d = dis.readDouble();
		char c = dis.readChar();
		boolean bb = dis.readBoolean();
		
		//释放资源
		dis.close();
		
		//控制台打印输出
		System.out.println(b);
		System.out.println(s);
		System.out.println(i);
		System.out.println(l);
		System.out.println(f);
		System.out.println(d);
		System.out.println(c);
		System.out.println(bb);
		
	}

	private static void write() throws IOException{
		// TODO Auto-generated method stub
		//DataOutputStream(OutputStream out)
		//创建数据输出流对象。
	    DataOutputStream dos = new DataOutputStream(new FileOutputStream("dos.txt"));
	    
	    //写数据了
	    dos.writeByte(10); 
	    dos.writeShort(100); 
	    dos.writeInt(10000); 
	    dos.writeLong(100000); 
	    dos.writeFloat(12.3F); 
	    dos.writeDouble(12.56); 
	    dos.writeChar('a'); 
	    dos.writeBoolean(true); 
	    
	    //释放资源
	    dos.close();
	    
	}

}
