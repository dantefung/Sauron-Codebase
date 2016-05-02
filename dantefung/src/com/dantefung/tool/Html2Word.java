/**
 * Project Name:TestDemo
 * File Name:Html2Word.java
 * Package Name:com.dantefung.util
 * Date:2016-4-1ÉÏÎç2:26:10
 * Copyright (c) 2016, fhlin0611@foxmail.com All Rights Reserved.
 *
*/

package com.dantefung.tool;

import com.jacob.activeX.ActiveXComponent;
import com.jacob.com.ComThread;
import com.jacob.com.Dispatch;
import com.jacob.com.Variant;

/**
 * ClassName:Html2Word <br/>
 * Function: TODO ADD FUNCTION. <br/>
 * Reason:	 TODO ADD REASON. <br/>
 * Date:     2016-4-1 ÉÏÎç2:26:10 <br/>
 * @author   Dante Fung
 * @version  
 * @since    JDK 1.6
 * @see 	 
 */
public class Html2Word
{
	public static void htmlToWord(String html, String docFile) {    
        ActiveXComponent app = new ActiveXComponent("Word.Application"); // Æô¶¯word        
        try {    
            app.setProperty("Visible", new Variant(false));    
            Dispatch docs = app.getProperty("Documents").toDispatch();    
            Dispatch doc = Dispatch.invoke(docs, "Open", Dispatch.Method, new Object[] { html, new Variant(false), new Variant(true) }, new int[1]).toDispatch();    
            Dispatch.invoke(doc, "SaveAs", Dispatch.Method, new Object[] { docFile, new Variant(1) }, new int[1]);    
            Variant f = new Variant(false);    
            Dispatch.call(doc, "Close", f);    
        } catch (Exception e) {    
            e.printStackTrace();    
        } finally {    
            app.invoke("Quit", new Variant[] {});    
            ComThread.Release();    
        }    
    }   
    public static void main(String[] args){  
         String fileName = "D:\\temp.doc";  
         String content = "D:\\result.html";  
         htmlToWord(content,fileName);  
    }
}

