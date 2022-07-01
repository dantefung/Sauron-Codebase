package com.dantefung.io;

import cn.hutool.core.util.ClassUtil;
import org.apache.commons.io.FileUtils;
import org.apache.tika.io.IOUtils;

import java.io.*;
 
public class StreamOperateUtil {

    public static void main(String[] args) throws IOException {

		String classPath = ClassUtil.getClassPath();
		InputStream input =  new FileInputStream(classPath+"tika/20220629100912.png");
        //InputStream input =  httpconn.getInputStream(); //这里可以写获取到的流
        
        ByteArrayOutputStream baos = cloneInputStream(input);
        
        // 打开两个新的输入流  
        InputStream stream1 = new ByteArrayInputStream(baos.toByteArray());  
        InputStream stream2 = new ByteArrayInputStream(baos.toByteArray());

		FileUtils.copyInputStreamToFile(stream1, new File("D:/1.png"));
		FileUtils.copyInputStreamToFile(stream2, new File("D:/2.png"));

		IOUtils.closeQuietly(stream1);
		IOUtils.closeQuietly(stream2);
	}
 
    public static ByteArrayOutputStream cloneInputStream(InputStream input) {
        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            byte[] buffer = new byte[1024];
            int len;
            while ((len = input.read(buffer)) > -1) {
                baos.write(buffer, 0, len);
            }
            baos.flush();
            return baos;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
 
}