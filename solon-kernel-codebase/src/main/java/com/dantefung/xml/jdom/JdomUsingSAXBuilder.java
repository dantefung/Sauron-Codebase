package com.dantefung.xml.jdom;

import java.io.File;
import java.io.IOException;
 
import org.jdom2.Comment;
import org.jdom2.Content;
import org.jdom2.Content.CType;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.filter.Filters;
import org.jdom2.input.SAXBuilder;
import org.jdom2.util.IteratorIterable;
 
/*
 * 出处：http://www.studytrails.com/java/xml/jdom2/java-xml-jdom2-SAXBuilderExample.jsp
 * 时间：2015/2/15
 * 目的：DanteFung学习之用。
 * */
public class JdomUsingSAXBuilder {
 
    private static String file1 = "tomcat-web-dtd.xml";
    private static String fileName = "com/dantefung/xml/jdom/tomcat-web-app-dtd.xml";
 
    public static void main(String[] args) throws JDOMException, IOException {
 
        // Use a SAX builder
        SAXBuilder builder = new SAXBuilder();
        //方式1：从文件或流中解析出符合JDOM模型的XML树.
        Document jdomDoc = builder.build(new JdomUsingSAXBuilder().getClass().getClassLoader().getResourceAsStream(fileName));
       
        //方式2：build a JDOM2 Document using the SAXBuilder.
//        Document jdomDoc = builder.build(new File(file1));  
 														  
                                                              /*这样写的话，系统是默认到我们的java工程下
	                                                           *tomcat-web-dtd.xml
													           *
													           *同一根目录下，若相对于我们当前工程没有tomcat-web-dtd.xml文件
													           *则会报错：
													           *thread "main" java.io.FileNotFoundException: 
													           *F:\ImportToEclipse\dantefung\tomcat-web-dtd.xml 
													           *(系统找不到指定的文件。）
													           */
 
        // get the document type
        System.out.println(jdomDoc.getDocType());
 
        //get the root element
        Element web_app = jdomDoc.getRootElement();
        System.out.println(web_app.getName());
         
        // get the first child with the name 'servlet'
        Element servlet = web_app.getChild("servlet");
 
        // iterate through the descendants and print non-Text and non-Comment values
        /*
         * CType: Content Type.
         * CType为一个枚举类型的类。
         * Text、Comment等都是该类中的元素（该类的匿名内部子类）。
         * 
         * */
        IteratorIterable<Content> contents = web_app.getDescendants();//设计模式中的迭代器-- --对象行为型模式。
        while (contents.hasNext()) {
            Content web_app_content = contents.next();
            if (!web_app_content.getCType().equals(CType.Text) && !web_app_content.getCType().equals(CType.Comment)) {
                System.out.println(web_app_content.toString());
            }
        }
 
        // get comments using a Comment filter
        IteratorIterable<Comment> comments = web_app.getDescendants(Filters.comment());
        while (comments.hasNext()) {
            Comment comment = comments.next();
            System.out.println(comment);
        }
 
    }
}
