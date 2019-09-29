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
	 * Dom4j:璇诲彇xml鏂囦欢
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
     * Dom4j:鎶奷ocument瀵硅薄鍐欏叆鏂扮殑鏂囦欢 
     *  
     * @param document 
     * @throws Exception 
     */  
    public static void writer(Document document, String fileName){  
        // 绱у噾鐨勬牸寮? 
        // OutputFormat format = OutputFormat.createCompactFormat();  
        // 鎺掔増缂╄繘鐨勬牸寮? 
        OutputFormat format = OutputFormat.createPrettyPrint();  
        // 璁剧疆缂栫爜  
        format.setEncoding("UTF-8");  
        // 鍒涘缓XMLWriter瀵硅薄,鎸囧畾浜嗗啓鍑烘枃浠跺強缂栫爜鏍煎紡  
        // XMLWriter writer = new XMLWriter(new FileWriter(new  
        // File("src//a.xml")),format);  
        XMLWriter writer;
		try 
		{
			writer = new XMLWriter(new OutputStreamWriter(  
			        new FileOutputStream(new File(fileName)), "UTF-8"), format);
	        // 鍐欏叆  
	        writer.write(document);  
	        // 绔嬪嵆鍐欏叆  
	        writer.flush();  
	        // 鍏抽棴鎿嶄綔  
	        writer.close();  
		} 
		catch (Exception e)
		{
			throw new RuntimeException(e);
		}  

    }
}
