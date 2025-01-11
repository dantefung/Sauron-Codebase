package com.dantefung.net.tcp;

import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.Socket;
/**
 * Tcp客户端
 * @author Dante Fung
 *
 */
public class ImageClient
{
    public static void main(String[] args) throws Exception
    {
        // 1、建立TCP服务,TCP的客户端一旦启动，马上就要建立连接。
        Socket socket = new Socket(InetAddress.getLocalHost(),9090);
        // 2、获取对应的流对象，因为TCP是基于IO流进行数据传输的。
        String data = "这是我的第一个TCP例子！！";
        OutputStream outputStream = socket.getOutputStream();
        // 3、把数据写出
        outputStream.write(data.getBytes());
        // 读取服务端反馈的数据
        InputStream inputStream = socket.getInputStream();
        BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream("D:/c.jpg"));
        byte[] buf = new byte[1024];
        int len = 0;
        while((len = inputStream.read(buf)) != -1)
        {
            bos.write(buf, 0, len);
        }
        // 4、关闭资源
        socket.close();
    }
}
