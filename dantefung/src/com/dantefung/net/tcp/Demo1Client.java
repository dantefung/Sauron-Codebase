package com.dantefung.net.tcp;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.Socket;
/**
 * Tcp�ͻ���
 * @author Dante Fung
 *
 */
public class Demo1Client
{
	/**
	 * �ͻ��˷������ݵ������
	 * 
	 * Tcp���䣺�ͻ��˽�������
	 * 1������tcp�ͻ���socket����ʹ�õ���socket���󡣽���ö���һ������Ҫ��ȷĿ�ĵأ���Ҫ���ӵ�������
	 * 2������������ӳɹ���˵�����ݴ����ͨ���Ѿ�������
	 *     ��ͨ������socket�����ǵͲ㽨���õġ���Ȼ������˵�����������룬���������
	 *     ��Ҫ���������������󣬿�����socket����ȡ��
	 *     ����ͨ��getOutputStream������getInputStream��������ȡ�����ֽ�������
	 * 3��ʹ���������������д����
	 * 4���ر���Դ��
	 * @param args
	 * @throws Exception
	 */
	public static void main(String[] args) throws Exception
    {
	    // 1������TCP����,TCP�Ŀͻ���һ�����������Ͼ�Ҫ�������ӡ�
		Socket socket = new Socket(InetAddress.getLocalHost(),9090);
		// 2����ȡ��Ӧ����������ΪTCP�ǻ���IO���������ݴ���ġ�
		String data = "�����ҵĵ�һ��TCP���ӣ���";
		OutputStream outputStream = socket.getOutputStream();
		// 3��������д��
		outputStream.write(data.getBytes());
		// ��ȡ����˷���������
		InputStream inputStream = socket.getInputStream();
		byte[] buf = new byte[1024];
		int len = inputStream.read(buf);
		System.out.println("tcp�ķ������յ������ݣ�" + new String(buf,0,len));
		// 4���ر���Դ
		socket.close();
    }
}