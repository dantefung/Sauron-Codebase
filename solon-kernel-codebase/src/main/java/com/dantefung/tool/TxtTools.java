/**
 * Project Name:Select2Demo
 * File Name:TxtTools.java
 * Package Name:com.dantefung.test
 * Date:2016年11月3日上午10:56:04
 * Copyright (c) 2016, fhlin0611@foxmail.com All Rights Reserved.
 *
*/

package com.dantefung.tool;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.util.regex.Pattern;

/**
 * ClassName:TxtTools <br/>
 * Function: TODO ADD FUNCTION. <br/>
 * Reason:	 TODO ADD REASON. <br/>
 * Date:     2016年11月3日 上午10:56:04 <br/>
 * @author   Dante Fung
 * @version  1.0.1
 * @since    JDK 1.7
 * @see 	 
 */
public class TxtTools {
	
	public static void main(String[] args) throws Exception {
		BufferedReader br = new BufferedReader(new FileReader(new File("D:\\baby1.txt")));
		BufferedWriter zh = new BufferedWriter(new FileWriter(new File("D:\\ZH.txt")));
		BufferedWriter en = new BufferedWriter(new FileWriter(new File("D:\\EN.txt")));
		Pattern pattern = Pattern.compile("^[A-Za-z0-9]+|[A-Za-z0-9]+(\\.|\\,)$");
		
		
		String line = "";
//		System.out.println(Pattern.matches("^[[A-Za-z0-9]{1,}\\.{0,}]$","aaaa."));
		while(null != (line = br.readLine())){
//			System.out.println(line.replace(" ", "")  + " ==== " + pattern.matcher(line.replace(" ", "")).lookingAt());
			// System.out.println(line.replace(" ", "")  + " ==== " + line.indexOf("’"));
			// 1、序号   eg.184
			if(Pattern.matches("^[0-9]+$", line.replace(" ", ""))){
				System.out.println(line);
				write(en, line);
				write(zh, line);
				
			}
			// 2、时间轴  eg.00:05:00,970-->00:05:02,370
			if(Pattern.matches("^([0-9]+):([0-9]+):([0-9]+),([0-9]+)-->([0-9]+):([0-9]+):([0-9]+),([0-9]+)$",line.replace(" ", ""))){
				write(en, line);
				write(zh, line);
			}
			// 3、 字幕   eg.那一天之后就知道是我们想要什么  || eg.it’s what we want
			// 匹配带数字的英文或纯英文的字符串
			if(pattern.matcher(line.replace(" ", "")).lookingAt()){
				if(!Pattern.matches("^[0-9]+$", line.replace(" ", ""))
						&& !Pattern.matches("^([0-9]+):([0-9]+):([0-9]+),([0-9]+)-->([0-9]+):([0-9]+):([0-9]+),([0-9]+)$",line.replace(" ", ""))
						&& !Pattern.matches("^[0-9\u4e00-\u9fa5]+$", line.replace(" ", ""))){
					//System.out.println("EN:" + line);
					write(en, line);
				}
			}
			// 匹配带数字的中文或纯中文字符串
			if(Pattern.matches("^[0-9\u4e00-\u9fa5]+$",line.replace(" ", ""))){
				if(!Pattern.matches("^[0-9]+$", line.replace(" ", ""))){
					//System.out.println("ZH:" + line);
					write(zh, line);
				}
			}
			
			// 4、空行
			if("".equals(line) || Pattern.matches("^[\\s]*$",line)){
				zh.newLine();
				en.newLine();
			}
		}
//		close(br,zh,en);
	}

	/**
	 * 
	 * close:关闭流，释放资源. <br/>
	 * TODO(这里描述这个方法适用条件 – 可选).<br/>
	 * TODO(这里描述这个方法的执行流程 – 可选).<br/>
	 * TODO(这里描述这个方法的使用方法 – 可选).<br/>
	 * TODO(这里描述这个方法的注意事项 – 可选).<br/>
	 *
	 * @author Dante Fung
	 * @param args
	 * @throws IOException
	 * @since JDK 1.7
	 */
	private static void close(Object...args) throws IOException {
		for(int i = 0; i < args.length; i ++){
			if( null != args[i] && args[i] instanceof Reader){
				Reader r = (Reader)args[i];
				r.close();
			}else{
				Writer w = (Writer)args[i];
				w.close();
			}
		}
	}

	/**
	 * 
	 * write:写数据到文件. <br/>
	 * TODO(这里描述这个方法适用条件 – 可选).<br/>
	 * TODO(这里描述这个方法的执行流程 – 可选).<br/>
	 * TODO(这里描述这个方法的使用方法 – 可选).<br/>
	 * TODO(这里描述这个方法的注意事项 – 可选).<br/>
	 *
	 * @author Dante Fung
	 * @param bw
	 * @param line
	 * @throws IOException
	 * @since JDK 1.7
	 */
	private static void write(BufferedWriter bw, String line) throws IOException {
		bw.write(line);
		bw.newLine();
		bw.flush();
	}
	
}

