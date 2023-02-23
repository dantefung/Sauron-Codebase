package com.dantefung.tool;

import org.springframework.util.StringUtils;

import java.io.IOException;
import java.net.HttpURLConnection;

import java.net.URL;
import java.net.URLConnection;

/**
 * 文件名称为：URLAvailability.java
 * 文件功能简述： 描述一个 URL 地址是否有效
 * @author Jason

 * @time 2010-9-14
 *

 */

public class URLAvailability {

	private static URL url;

	private static HttpURLConnection con;

	private static int state = -1;

	/**
	 * 功能：检测当前 URL 是否可连接或是否有效,
	 * 描述：最多连接网络 5 次, 如果 5 次都不成功，视为该地址不可用
	 * @param urlStr 指定 URL 网络地址

	 * @return URL

	 */

	public synchronized URL isConnect(String urlStr) {

		int counts = 0;

		if (urlStr == null || urlStr.length() <= 0) {

			return null;

		}

		while (counts < 5) {

			try {

				url = new URL(urlStr);

				con = (HttpURLConnection) url.openConnection();

				state = con.getResponseCode();

				System.out.println(counts + "=" + state);

				if (state == 200) {

					System.out.println("URL 可用！");

				}

				break;

			} catch (Exception ex) {

				counts++;

				System.out.println("URL 不可用，连接第" + counts + "次");

				urlStr = null;

				continue;

			}

		}

		return url;
	}

	/**
	 * 判断链接是否有效
	 * 输入链接
	 * 返回true或者false
	 */
	public static boolean isValid(String strLink) {
		URL url;
		try {
			url = new URL(strLink);
			HttpURLConnection connt = (HttpURLConnection)url.openConnection();
			connt.setRequestMethod("HEAD");
			String strMessage = connt.getResponseMessage();
			if (strMessage.compareTo("Not Found") == 0) {
				return false;
			}
			connt.disconnect();
		} catch (Exception e) {
			return false;
		}
		return true;
	}


	public static void main(String[] args) throws IOException {

		URLAvailability u = new URLAvailability();

		u.isConnect("http://www.baidu.com");

		try {
			URL url=new URL( "http://www.dukai168.cn");
			URLConnection conn=url.openConnection();
			String str=conn.getHeaderField(0);
			if (str.indexOf( "OK ")> 0)
			{
				System.out.println( "正常! ");
			}else{
				System.out.println( "不能游览 ");
			}
		} catch (Exception ex) {
		}

		System.out.println(isValid("http://localhost:8090/Demo/clean.sql"));
		// 方法一
		test1();
		// 方法二：
		test2();

	}

	private static void test2() throws IOException {
		URL url = new URL("http://localhost:8090/Demo/clean.sql");
		HttpURLConnection urlcon2 = (HttpURLConnection) url.openConnection();
		Long TotalSize=Long.parseLong(urlcon2.getHeaderField("Content-Length"));
		if (TotalSize>0){
			System.out.println("存在");
		}else{
			System.out.println("不存在");
		}
	}

	private static void test1() throws IOException {
		URL serverUrl = new URL("http://localhost:8090/Demo/clean.sql");
		HttpURLConnection urlcon = (HttpURLConnection) serverUrl.openConnection();
		String message = urlcon.getHeaderField(0);
		if (StringUtils.hasText(message) && message.startsWith("HTTP/1.1 404")) {
			System.out.println("不存在");
		}else{
			System.out.println("存在"+message);
		}
	}

}