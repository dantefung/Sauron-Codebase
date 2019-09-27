package com.dantefung.dp.factory;

import java.net.URL;

import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.Element;

public class BeanFactory {
	
	private static Document document;
	private static  String currentPath;


	public static <T> T getBean(Class<T>  clazz)
	{
		document = Dom4jUtil.parse(currentPath);
		Element bean = (Element) document.selectSingleNode("/beans/bean[@id='"+clazz.getSimpleName()+"']");
		Attribute attribute = bean.attribute("class");
		String value = attribute.getValue();
		try 
		{
			return (T) Class.forName(value).newInstance();
		} catch (Exception e) 
		{
			throw new RuntimeException(e);
		}
	
	}
	
	public static  void setCurrentPath(String currentPath) {
		BeanFactory.currentPath = currentPath;
	}
	
	public static void main(String[] args)  {
/*		URL url = BeanFactory.class.getClassLoader().getResource(".");
		URL url2 = BeanFactory.class.getClassLoader().getResource("/");
		String uri = url.toString();
		System.out.println(uri.substring(uri.indexOf("/")+1) + "Bean.xml");
		System.out.println(url);
		System.out.println(url2);
		System.out.println(BeanFactory.class.getSimpleName());*/
		
		// java 工程路径
		URL url = BeanFactory.class.getClassLoader().getResource(".");
		String uri = url.toString();
		System.out.println(uri.substring(uri.indexOf("/")+1)+"com/dantefung/dp/factory/Bean.xml");
		currentPath=uri.substring(uri.indexOf("/")+1)+"com/dantefung/dp/factory/Bean.xml";
		BeanFactory.setCurrentPath(currentPath);
		
		// web工程路径
		// String currentPath = ServletActionContext.getServletContext().getRealPath("/WEB-INF/classes")+"/"+"Bean.xml";
		AdminsDao bean = BeanFactory.getBean(AdminsDao.class);
		System.out.println(bean);
	}
}
