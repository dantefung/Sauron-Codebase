package com.dantefung.dp.factory.springsim;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jdom2.*;
import org.jdom2.input.SAXBuilder;
import org.jdom2.xpath.XPath;
/*
 * JDOM is designed only for Java and uses the natural 
 * Java-specific features that the DOM model avoids.
 * 
 * SAX:Simple APIfor Xml 
 * */
public class ClassPathApplicationXmlContext implements BeanFactory {
	private Map<String, Object> container = new HashMap<String, Object>();

	public ClassPathApplicationXmlContext(String fileName) throws Exception
	{
		  SAXBuilder sb = new SAXBuilder();
		  Document doc = sb.build(this.getClass().getClassLoader()//从文件或流中解析出符合JDOM模型的XML树.
				  .getResourceAsStream(fileName));
		  Element root = doc.getRootElement();//通过Document对象取得根节点。
		  List list = XPath.selectNodes(root, "/beans/bean");//根据一个xpath语句返回一组节点。
		  System.out.println(list.size());
		  
		  //遍历所有的节点，取出key和value
		  for (int i = 0; i < list.size(); i++) { 
		   Element bean = (Element) list.get(i);
		   String id = bean.getAttributeValue("id");
		   String clazz = bean.getAttributeValue("class");
		   Object o = Class.forName(clazz).newInstance();
		   container.put(id, o);
		   System.out.println(id + " " + clazz);
		  }
	}
	
	@Override
	public Object getBean(String id) 
	{
		return container.get(id);
	}

}
