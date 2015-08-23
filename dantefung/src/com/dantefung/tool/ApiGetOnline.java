package com.dantefung.tool;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpMethod;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;

/**
 * 在线获取api文档
 * @author prz
 * @date   Jun 28, 2013
 */
public class ApiGetOnline {  
    
    private static Set<String> filenames = new HashSet<String>();  
    /**
     * 获取url请求的html
     * @param url 给定网页路径
     * @return html字符串
     */
    public static String getSource(String url) {  
        String response = null;  
        HttpClient client = new HttpClient();  
        HttpMethod method = null;  
        try {  
            method = new PostMethod(url);
            client.executeMethod(method); 
            if(method.getStatusCode()==HttpStatus.SC_METHOD_NOT_ALLOWED){
            	method=new GetMethod(url);
            	client.executeMethod(method);
            }
            if (method.getStatusCode() == HttpStatus.SC_OK) {  
                response = method.getResponseBodyAsString();
            }
            System.out.println(method.getStatusCode()+"    "+method.getStatusText());
        } catch (IOException e) {  
            System.out.println("资源获取错误!");  
        } finally {  
            if (method != null)  
                method.releaseConnection();  
        }  
        return response;  
    }  
    /**
     * 根据给定的url基址以及给定的文件名cur获取资源，并下载资源
     * 默认进入url地址，寻找cur文件,请求url+"/"+cur，获取其html文档，写入froot目录。
     * 然后在html文档中选取链接含有.html继续迭代本方法
     * @param url   在线帮助文档基址
     * @param froot 文档根目录
     * @param cur   基于此页获取本页有链接的html文档
     */
    public static void create(String url, String froot, String cur) {
        String curUrl = url + "/" + cur;
        if (filenames.contains(curUrl)) {
        	System.out.println("return1");
            return;  
        } 
        filenames.add(curUrl);  
        System.out.println(curUrl);
        String cont = getSource(curUrl);
        if (cont == null) {  
            return;  
        }  
        File f = null;  
        FileOutputStream fos = null;  
        try {  
            f = new File(froot);  
            if (!f.exists()) {  
                f.mkdirs();  
            }  
            f = new File(froot + "\\" + cur);
            f.createNewFile();  
            fos = new FileOutputStream(f);  
            fos.write(cont.getBytes());  
            fos.flush();  
        } catch (FileNotFoundException e) {  
            e.printStackTrace();  
        } catch (IOException e) {  
            e.printStackTrace();  
        } finally {  
            if (fos != null) {  
                try {  
                    fos.close();  
                } catch (IOException e) {  
                    e.printStackTrace();  
                }  
            }  
        }  
        String[] files = cont.split("\""); 
        for(String file:files){
        	System.out.println(file);
        }
        for (String file : files) {  
            if (file.matches(".*[(\\.html)(\\.htm)]$") && !file.startsWith("http")) {  
                if (file.contains(" "))  
                    continue;  
                String turl = url;  
                String tfroot = froot;  
                file.replaceAll("//", "/");  
                int tindex = file.lastIndexOf("?");  
                if (tindex != -1) {  
                    file = file.substring(0, tindex);  
                }  
                int index = 0;  
                while ((index = file.indexOf("/")) != -1) {  
                    if (file.charAt(index - 1 < 0 ? 0 : index - 1) == '.') {  
                        turl = turl.substring(0, turl.lastIndexOf("/"));  
                        tfroot = tfroot.substring(0, tfroot.lastIndexOf("\\"));  
                    } else {  
                        turl = turl + "/" + file.substring(0, index);  
                        tfroot = tfroot + "\\" + file.substring(0, index);  
                    }  
                    file = file.substring(index + 1);  
                }  
                create(turl, tfroot, file);  
            }  
        }  
    }  
    public static void main(String[] args) {  
//        String url = "http://www.jb51.net/tools"; 
        String url = "http://tomcat.apache.org/tomcat-5.5-doc/catalina/docs/api";
        String froot = "D:\\api";  
//        create(url, froot, "zhengze.html");
        create(url, froot, "");
    }  
}  