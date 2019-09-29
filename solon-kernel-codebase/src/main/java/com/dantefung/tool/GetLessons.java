package com.dantefung.tool;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.cookie.Cookie;
import org.apache.http.impl.client.AbstractHttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.json.JSONArray;

/**
 * 格式：
 * 		请求行\r\n
 * 		请求头\r\n
 * 		\r\n
 * 		请求正文
 * 
 * @author Dante Fung
 *
 */
public class GetLessons{
	private List<Cookie> cookies;                      //保存获取的cookie  
	private String result="";
	private String username = "13124640128";
	private String password = "199410";
	private ArrayList<HashMap<String,Object>> lesarr=new ArrayList<HashMap<String,Object>>();
	
	public void getCookie(){
		
		try {  
	    HttpClient client = new DefaultHttpClient();
		String uriAPI = "http://218.15.22.136:3008/";
		HttpPost httpPost = new HttpPost(uriAPI);  
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		
	params.add(new BasicNameValuePair("Window1$SimpleForm1$rdl_shenFen","学生"));
	params.add(new BasicNameValuePair("Window1$SimpleForm1$tbx_XueHao", username));
	params.add(new BasicNameValuePair("Window1$SimpleForm1$tbx_pwd", password));
	params.add(new BasicNameValuePair("Window1_Collapsed", "false"));
	params.add(new BasicNameValuePair("Window1_Hidden", "false"));
	params.add(new BasicNameValuePair("Window1_SimpleForm1_Collapsed", "false"));
	params.add(new BasicNameValuePair("X_AJAX", "true"));
	params.add(new BasicNameValuePair("X_CHANGED", "true"));
	params.add(new BasicNameValuePair("X_STATE", "e30="));
	params.add(new BasicNameValuePair("X_TARGET", "Window1_Toolbar1_btn_login"));
	params.add(new BasicNameValuePair("__EVENTARGUMENT", ""));
	params.add(new BasicNameValuePair("__EVENTTARGET", "Window1$Toolbar1$btn_login"));
	
	    // 发出HTTP request  
	   httpPost.setEntity(new UrlEncodedFormEntity(params,HTTP.UTF_8));  
	    // 取得HTTP response  
	    HttpResponse httpResponse = client.execute(httpPost);   //执行  
	    // 若状态码为200 ok  
	    if (httpResponse.getStatusLine().getStatusCode() == 200) {   //返回值正常  
	        // 获取返回的cookie  
	        cookies = ((AbstractHttpClient) client).getCookieStore().getCookies();
//	        System.out.println("cookies="+cookies);
	        
	        if(cookies.isEmpty()) System.out.println("cookies empty");
	    } else {  
	    }  
	} catch (Exception e) {  
	    System.out.println("getCookie error:"+e); 
	} 
	}
	
	public ArrayList<HashMap<String,Object>> getResult(){
		
		try {
		String uriAPI = "http://218.15.22.136:3008/PaiKeXiTong.aspx";//http://218.15.22.136:3008/PaiKeXiTong.aspx
//		String uriAPI = "http://218.15.22.136:3008/ChengJiChaXun.aspx";//http://218.15.22.136:3008/PaiKeXiTong.aspx
		HttpPost httpRequest = new HttpPost(uriAPI); 
        List<NameValuePair> params = new ArrayList<NameValuePair>();  
        params.add(new BasicNameValuePair("Grid1$Toolbar1$ddl_XueYuan","12"));//["12  ", "08理学院                 ", 1]
        params.add(new BasicNameValuePair("Grid1$Toolbar1$ddl_BanJi", "104361"));//5100,104627  ["104361", "信息13-2", 1]
        params.add(new BasicNameValuePair("Grid1_Collapsed", "false"));
        params.add(new BasicNameValuePair("Grid1_HiddenColumnIndexArray", ""));
        params.add(new BasicNameValuePair("Grid1_SelectedRowIndexArray", ""));
        params.add(new BasicNameValuePair("Grid2_Collapsed", "false"));
        params.add(new BasicNameValuePair("Grid2_HiddenColumnIndexArray", ""));
        params.add(new BasicNameValuePair("Grid2_SelectedRowIndexArray", ""));
        params.add(new BasicNameValuePair("X_AJAX", "true"));
        params.add(new BasicNameValuePair("X_CHANGED", ""));
        params.add(new BasicNameValuePair("X_STATE", "eyJHcmlkMV9Ub29sYmFyMV9kZGxfWHVlWXVhbiI6eyJEYXRhVGV4dEZpZWxkIjoiamdtYyIsIkRhdGFWYWx1ZUZpZWxkIjoiamdkbSIsIlhfSXRlbXMiOltbIjAyICAiLCLmnLrnlLXlt6XnqIvlrabpmaIgICAgICAgICAgICAgIiwxXSxbIjAzICAiLCLorqHnrpfmnLrkuI7nlLXlrZDkv6Hmga/lrabpmaIgICAgICIsMV0sWyIwNCAgIiwi5bu6562R5bel56iL5a2m6ZmiICAgICAgICAgICAgICIsMV0sWyIwNSAgIiwi57uP5rWO566h55CG5a2m6ZmiICAgICAgICAgICAgICIsMV0sWyIwNiAgIiwi5paH5rOV5a2m6ZmiICAgICAgICAgICAgICAgICAiLDFdLFsiMDggICIsIuS9k+iCsuWtpuezuyAgICAgICAgICAgICAgICAgIiwxXSxbIjEwICAiLCLoibrmnK/ns7sgICAgICAgICAgICAgICAgICAgIiwxXSxbIjEyICAiLCLnkIblrabpmaIgICAgICAgICAgICAgICAgICAgIiwxXSxbIjEzICAiLCLlpJblm73or63lrabpmaIgICAgICAgICAgICAgICAiLDFdLFsiMTQgICIsIueOr+Wig+S4jueUn+eJqeW3peeoi+WtpumZoiAgICAgICAiLDFdLFsiMTUgICIsIuefs+ayueW3peeoi+WtpumZoiAgICAgICAgICAgICAiLDFdLFsiMTYgICIsIuWMluWtpuW3peeoi+WtpumZoiAgICAgICAgICAgICAiLDFdXSwiU2VsZWN0ZWRWYWx1ZSI6IjEyICAifSwiR3JpZDFfVG9vbGJhcjFfZGRsX0JhbkppIjp7IkRhdGFUZXh0RmllbGQiOiJiam1jIiwiRGF0YVZhbHVlRmllbGQiOiJiamRtIiwiWF9JdGVtcyI6W1siMzkwNyIsIuWcsOeQhijluIgpMTEtMSAgICAgICAgICAgICAgICAgICAgICAgICAgICAiLDFdLFsiMTA0MzkwIiwi5Zyw55CGKOW4iCkxMS0zIiwxXSxbIjUxMDIiLCLlnLDnkIYo5biIKTEyLTEgICAgICAiLDFdLFsiMTA0MjU0Iiwi5Zyw55CGKOW4iCkxMy0xIiwxXSxbIjEwNDYyNSIsIuWcsOeQhijluIgpMTQtMSIsMV0sWyIzOTY2Iiwi5Zyw55CGMTEtMiIsMV0sWyIzOTY0Iiwi5pWZ6IKy5oqA5pyvMTEtMSAgICAgICAgICAgICAgICAgICAgICAgICAgICAiLDFdLFsiNTEwNSIsIuaVmeiCsuaKgOacrzEyLTEgICAgICAgICIsMV0sWyI1MTA2Iiwi5pWZ6IKy5oqA5pyvMTItMiAgICAgICAgIiwxXSxbIjEwNDMxNyIsIuaVmeiCsuaKgOacrzEzLTEiLDFdLFsiMTA0MzE4Iiwi5pWZ6IKy5oqA5pyvMTMtMiIsMV0sWyIxMDQ2MjgiLCLmlZnogrLmioDmnK8xNC0xIiwxXSxbIjEwNDYyOSIsIuaVmeiCsuaKgOacrzE0LTIiLDFdLFsiMzkxMSIsIuaVsOWtpijluIgpMTEtMSAgICAgICAgICAgICAgICAgICAgICAgICAgICAiLDFdLFsiNTA4MiIsIuaVsOWtpijluIgpMTItMSAgICAgICIsMV0sWyIxMDQzNDMiLCLmlbDlraYo5biIKTEzLTEiLDFdLFsiMTA0NjIxIiwi5pWw5a2mKOW4iCkxNC0xIiwxXSxbIjM5NzkiLCLmlbDlraYxMS0yIiwxXSxbIjUwODMiLCLmlbDlraYxMi0yICAgICAgICAgICAgIiwxXSxbIjEwNDY1OSIsIuaVsOWtpjEyLTMiLDFdLFsiMTA0MzQ0Iiwi5pWw5a2mMTMtMiIsMV0sWyIxMDQ2MjIiLCLmlbDlraYxNC0yIiwxXSxbIjM5NjUiLCLniannkIYo5biIKTExLTEgICAgICAgICAgICAgICAgICAgICAgICAgICAgIiwxXSxbIjUxMDMiLCLniannkIYo5biIKTEyLTEgICAgICAiLDFdLFsiMTA0MzU5Iiwi54mp55CGKOW4iCkxMy0xIiwxXSxbIjEwNDYyNiIsIueJqeeQhijluIgpMTQtMSIsMV0sWyIzOTgyIiwi54mp55CGMTEtMiIsMV0sWyI1MTA0Iiwi54mp55CGMTItMiAgICAgICAgICAgICIsMV0sWyIxMDQzNTgiLCLniannkIYxMy0yIiwxXSxbIjEwNDYyNyIsIueJqeeQhjE0LTIiLDFdLFsiMzk4MCIsIuS/oeaBrzExLTEgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICIsMV0sWyIzOTgxIiwi5L+h5oGvMTEtMiAgICAgICAgICAgICAgICAgICAgICAgICAgICAgICAgIiwxXSxbIjUxMDAiLCLkv6Hmga8xMi0xICAgICAgICAgICAgIiwxXSxbIjUxMDEiLCLkv6Hmga8xMi0yICAgICAgICAgICAgIiwxXSxbIjEwNDM2MCIsIuS/oeaBrzEzLTEiLDFdLFsiMTA0MzYxIiwi5L+h5oGvMTMtMiIsMV0sWyIxMDQ2MjMiLCLkv6Hmga8xNC0xIiwxXSxbIjEwNDYyNCIsIuS/oeaBrzE0LTIiLDFdLFsiMzA2NSIsIuWcsOeQhijluIgpMTAtMSIsMV0sWyIzMDY2Iiwi5Zyw55CGKOW4iCkxMC0yIiwxXSxbIjMwNjkiLCLmlZnogrLmioDmnK8xMC0xIiwxXSxbIjMwNzAiLCLmlZnogrLmioDmnK8xMC0yIiwxXSxbIjMwNTkiLCLmlbDlraYo5biIKTEwLTEiLDFdLFsiNjAwOCIsIuaVsOWtpijluIgpMTAtMyAgICAgICIsMV0sWyIzMDYwIiwi5pWw5a2mMTAtMiIsMV0sWyIzMDY3Iiwi54mp55CGKOW4iCkxMC0xIiwxXSxbIjMwNjgiLCLniannkIYxMC0yIiwxXSxbIjMwNjEiLCLkv6Hmga8xMC0xIiwxXSxbIjMwNjIiLCLkv6Hmga8xMC0yIiwxXSxbIjMwNjMiLCLkv6Hmga8xMC0zIiwxXSxbIjMwNjQiLCLkv6Hmga8xMC00IiwxXV0sIlNlbGVjdGVkVmFsdWUiOiI1MTAwIn0sIkdyaWQxIjp7IlRpdGxlIjoi5pWZ5a2m5o6I6K++5L+h5oGv6KGoIDxzcGFuIHN0eWxlPSdjb2xvcjpyZWQ7Jz7vvIjmn6Xor6Lnj63nuqfvvJrkv6Hmga8xMi0x77yJPC9zcGFuPiIsIlJlY29yZENvdW50IjoxMiwiWF9Sb3dzIjp7IlZhbHVlcyI6W1siKirorqHnrpfmnLrnvZHnu5zvvIjlrp7pqozvvInvvIgxKjE277yJIiwi5ZGo5LiAIDHjgIEy6IqCIiwi55CG5a2m6Zmi5a6e6aqM5a6k77yI6Jma5ouf77yJIiwi5a2f5Lqa6L6JICAgICAgICAgICAgICAgIl0sWyLorqHnrpfmnLrnvZHnu5zvvIgyKjE2KzE277yI5a6e6aqM77yJ77yJIiwi5ZGo5LiAIDPjgIE06IqCIiwi5LqM5pWZQi0zMDgiLCLlrZ/kuprovokgICAgICAgICAgICAgICAiXSxbIuamgueOh+iuui/mpoLnjoforrrkuI7mlbDnkIbnu5/orqHvvIgzKjE2LOaVsOWtpjEzLTI65qaC546H6K6677yJIiwi5ZGo5LiAIDXjgIE26IqCIiwi5LqM5pWZQi03MDIiLCLmnY7mmKXpppkgICAgICAgICAgICAgICAiXSxbIioq5qaC546H6K66L+amgueOh+iuuuS4juaVsOeQhue7n+iuoe+8iDMqMTYs5pWw5a2mMTMtMjrmpoLnjoforrrvvIkiLCLlkajkuowgN+OAgTjoioIiLCLkuozmlZlCLTcwMiIsIuadjuaYpemmmSAgICAgICAgICAgICAgICJdLFsi5pWw5YC85YiG5p6Q77yIMzIoMioxNikrMTbvvIjlrp7pqozvvInvvIkiLCLlkajkuIkgN+OAgTjoioIiLCLkuozmlZlCLTcwMiIsIuavm+W7uuagkSAgICAgICAgICAgICAgICJdLFsi5LyB5Lia6LWE5rqQ6K6h5YiS77yIRVJQ77yJ77yI5a6e6aqM77yJ77yIMioxMu+8iSIsIuWRqOS4iSA544CBMTDoioIiLCLnkIblrabpmaLlrp7pqozlrqTvvIjomZrmi5/vvIkiLCLmooHmn7Hmo64gICAgICAgICAgICAgICAiXSxbIuaTjeS9nOezu+e7n+WOn+eQhu+8iExJTlVY77yJ77yIMioxMisyNO+8iOWunumqjO+8ie+8iSIsIuWRqOWbmyAx44CBMuiKgiIsIuS6jOaVmUItMzA4Iiwi6aG56aG65LyvICAgICAgICAgICAgICAgIl0sWyLkvIHkuJrotYTmupDorqHliJLvvIhFUlDvvInvvIgyKjEyKzI077yI5LiK5py677yJ77yJIiwi5ZGo5ZubIDPjgIE06IqCIiwi5LqM5pWZQi0zMDgiLCLmooHmn7Hmo64gICAgICAgICAgICAgICAiXSxbIuaTjeS9nOezu+e7n+WOn+eQhu+8iExJTlVY77yJ77yI5a6e6aqM77yJ77yIMioxMu+8iSIsIuWRqOWbmyA144CBNuiKgiIsIueQhuWtpumZouWunumqjOWupC4iLCLpobnpobrkvK8gICAgICAgICAgICAgICAiXSxbIuaVsOaNruW6k+ezu+e7n+WOn+eQhuS4juW6lOeUqG9yYWNsZe+8iOaVmeaKgDEyLTE65pWw5o2u5bqT5Y6f55CG5LiO6K6+6K6h77yJIiwi5ZGo5LqUIDHjgIEy6IqCIiwi5LqM5pWZQi0zMDgiLCLmooHmn7Hmo64gICAgICAgICAgICAgICAiXSxbIuavm+azveS4nOaAneaDs+S4reWbveeJueiJsuamguiuuu+8iDIqMTXvvIkiLCLlkajkupQgM+OAgTToioIiLCLkuJwyMDQiLCLosKLlu7rkuK0gICAgICAgICAgICAgICAiXSxbIuaVsOaNruW6k+ezu+e7n+WOn+eQhuS4juW6lOeUqG9yYWNsZe+8iOWunumqjO+8ie+8iDIqMTbvvIkiLCLlkajkupQgNeOAgTboioIiLCLnkIblrabpmaLlrp7pqozlrqQuIiwi5qKB5p+x5qOuICAgICAgICAgICAgICAgIl1dLCJEYXRhS2V5cyI6W1tudWxsXSxbbnVsbF0sW251bGxdLFtudWxsXSxbbnVsbF0sW251bGxdLFtudWxsXSxbbnVsbF0sW251bGxdLFtudWxsXSxbbnVsbF0sW251bGxdXSwiU3RhdGVzIjpbW10sW10sW10sW10sW10sW10sW10sW10sW10sW10sW10sW11dfX0sIkdyaWQyIjp7IlRpdGxlIjoi5pWZ5a2m5ZGo6L+b56iL6KGoIDxzcGFuIHN0eWxlPSdjb2xvcjpyZWQ7Jz4o6ICD6K+V56eR55uu77ya6K6h566X5py6572R57ucIOamgueOh+iuui/mpoLnjoforrrkuI7mlbDnkIbnu5/orqEg5pWw5YC85YiG5p6Q77yJPC9zcGFuPiIsIlJlY29yZENvdW50IjoxLCJYX1Jvd3MiOnsiVmFsdWVzIjpbWyLimIYgICIsIuKAlCAgIiwi4oCUICAiLCLigJQgICIsIuKAlCAgIiwi4oCUICAiLCLigJQgICIsIuKAlCAgIiwi4oCUICAiLCLigJQgICIsIuKAlCAgIiwi4oCUICAiLCLigJQgICIsIuKAlCAgIiwi4oCUICAiLCLigJQgICIsIuKAlCAgIiwi77yaICAiLCLimIYgICIsIuKYhiAgIl1dLCJEYXRhS2V5cyI6W1tudWxsXV0sIlN0YXRlcyI6W1tdXX19fQ=="));
        params.add(new BasicNameValuePair("X_TARGET", "Grid1_Toolbar1_query_PaiKe"));
        params.add(new BasicNameValuePair("__EVENTARGUMENT", ""));
        params.add(new BasicNameValuePair("__EVENTTARGET", "Grid1$Toolbar1$query_PaiKe"));
//        params.add(new BasicNameValuePair("__VIEWSTATE", "/wEPDwUKLTY0NjUxODI2MWQYAQUeX19Db250cm9sc1JlcXVpcmVQb3N0QmFja0tleV9fFgYFBUdyaWQxBRpHcmlkMSRUb29sYmFyMSRkZGxfWHVlWXVhbgUYR3JpZDEkVG9vbGJhcjEkZGRsX0JhbkppBRpHcmlkMSRUb29sYmFyMSRxdWVyeV9QYWlLZQUaR3JpZDEkVG9vbGJhcjEkcHJpbnRfUGFpS2UFBUdyaWQyX0pj3LcLPgXBM6N7lFRXdRvwpAziEmvGRJw79SyS+HI="));
        params.add(new BasicNameValuePair("__VIEWSTATE", "/wEPDwUKLTY0NjUxODI2MWQYAQUeX19Db250cm9sc1JlcXVpcmVQb3N0QmFja0tleV9fFgYFBUdyaWQxBRpHcmlkMSRUb29sYmFyMSRkZGxfWHVlWXVhbgUYR3JpZDEkVG9vbGJhcjEkZGRsX0JhbkppBRpHcmlkMSRUb29sYmFyMSRxdWVyeV9QYWlLZQUaR3JpZDEkVG9vbGJhcjEkcHJpbnRfUGFpS2UFBUdyaWQy9EAoDHQdtDQOKcXI0dh/ynDMTUmpMgxvOu2w42XnwM4="));
        
//        System.out.println(cookies);
//        System.out.println(cookies.get(0).getValue());
        httpRequest.setHeader("Cookie","ASP.NET_SessionId="+ cookies.get(0).getValue());  
        httpRequest.setEntity(new UrlEncodedFormEntity(params, HTTP.UTF_8)); 
        HttpResponse httpResponse2 = new DefaultHttpClient().execute(httpRequest);  
        System.out.println(httpResponse2.getStatusLine().getStatusCode());
        if (httpResponse2.getStatusLine().getStatusCode() == 200) { 
            StringBuffer sb = new StringBuffer();  
            HttpEntity entity = httpResponse2.getEntity();  
            InputStream is = entity.getContent();  
            BufferedReader br = new BufferedReader(new InputStreamReader(is,HTTP.UTF_8));  
            //是读取要改编码的源，源的格式是GB2312的，安源格式读进来，然后再对源码转换成想要的编码就行  
            String data = "";  
            while ((data = br.readLine()) != null) {  
                sb.append(data);  
            }
//            System.out.println(">>>>>>>>>>>:" + sb.toString());
            
            result = sb.toString();  //此时result中就是我们成绩的HTML的源代码了  
            int index=result.indexOf("[[");
            int end=result.indexOf("]]");
            result=result.substring(index, end+2);
            System.out.println("result="+result);
            
            JSONArray jarr=new JSONArray(result);
            for(int i=0;i<jarr.length();i++){
            	String jobj=jarr.get(i).toString().replace("\"","");
            	jobj=jobj.substring(1,jobj.length()-1);
            	
            	String[] strs=jobj.split(",");
            	
            	HashMap<String,Object> hmap=new HashMap<String,Object>();
            	hmap.put("les",strs[0]);
            	hmap.put("time",strs[1]);
            	hmap.put("pos",strs[2]);
            	hmap.put("tcher",strs[3].trim());
            	lesarr.add(hmap);
            	
            }
//            System.out.println(lesarr);
            return lesarr;
        } else {  
        	return null;
        }  
        } catch (Exception e) {  
        System.out.println("getResult error:"+e);
        return null;
        }  
	}
	
	public static void main(String[] args)
    {
//		while(true)
//		{
			GetLessons gls=new GetLessons();
			gls.getCookie();
			gls.getResult();
//		}
    }
}
