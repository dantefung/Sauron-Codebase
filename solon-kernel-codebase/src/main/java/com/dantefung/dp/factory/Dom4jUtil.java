package com.dantefung.dp.factory;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;

public class Dom4jUtil {

	/**
	 * Dom4j:读取xml文件
	 * @param url
	 * @param bean
	 * @return
	 */
	public static Document parse(String url)
	{
		SAXReader reader = new SAXReader();
		Document document;
		try 
		{
			document = reader.read(url);
		} 
		catch (DocumentException e) 
		{
			throw new RuntimeException(e);
		}
		return document;
	}
	
	 /** 
     * Dom4j:把document对象写入新的文件 
     *  
     * @param document 
     * @throws Exception 
     */  
    public static void writer(Document document, String fileName){  
        // 紧凑的格�? 
        // OutputFormat format = OutputFormat.createCompactFormat();  
        // 排版缩进的格�? 
        OutputFormat format = OutputFormat.createPrettyPrint();  
        // 设置编码  
        format.setEncoding("UTF-8");  
        // 创建XMLWriter对象,指定了写出文件及编码格式  
        // XMLWriter writer = new XMLWriter(new FileWriter(new  
        // File("src//a.xml")),format);  
        XMLWriter writer;
		try 
		{
			writer = new XMLWriter(new OutputStreamWriter(  
			        new FileOutputStream(new File(fileName)), "UTF-8"), format);
	        // 写入  
	        writer.write(document);  
	        // 立即写入  
	        writer.flush();  
	        // 关闭操作  
	        writer.close();  
		} 
		catch (Exception e)
		{
			throw new RuntimeException(e);
		}  

    }
}
