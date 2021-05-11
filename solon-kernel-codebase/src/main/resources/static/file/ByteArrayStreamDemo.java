package com.dantefung.io.part2;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

/**
 * @since 2015-4-4
 * 
 * �ڴ�����������ڴ�����ʱ�洢��Ϣ�ģ�������������ݾʹ��ڴ�����ʧ��
 * 
 * �ֽ����飺
 *     ByteArrayInputStream
 *     ByteArrayOutputStream
 * �ַ����飺
 *     CharArrayReader
 *     CharArrayWriter
 * �ַ�����
 *     StringReader
 *     StringWriter
 */
public class ByteArrayStreamDemo {

	/**
	 * @param args
	 */
	public static void main(String[] args) throws IOException{
		// TODO Auto-generated method stub
        //д����
		//ByteArrayOutputStream
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		
		//д����
		for(int x = 0; x < 10; x++)
		{
			 baos.write(("hello" + x).getBytes());
		}
		
		//�ͷ���Դ 
		//ͨ���鿴Դ������֪������ʲô��û���������Ը�������Ҫclose()
		//baos.close();
		
		//public byte[] toByteArray()
		byte[] bys = baos.toByteArray();
		
		//������
		//ByteArrayInputStream(byte[] buf)
		ByteArrayInputStream bais = new ByteArrayInputStream(bys);
		
		int by = 0;
		while((by=bais.read()) != -1)
		{
			System.out.print((char)by);
		}
		
		//�ͷ���Դ
		bais.close();
	}

}
