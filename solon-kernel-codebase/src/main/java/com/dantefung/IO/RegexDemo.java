package com.dantefung.IO;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegexDemo {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		String str = "A1B22C333D4444E55555F";
		/**常用正则规范：\d（一组规范） 表示数字   数量表示：\d+ 描述：可以出现1次或多次。**/
		String pat = "\\d+";  //定义替换规则。
		
		//Step1:将该规则抽象成一个模型类。
		Pattern p = Pattern.compile(pat);
		
		//Step2:验证、匹配。
		Matcher m = p.matcher(str);
		
		//Step3:替换
		String newString = m.replaceAll("_");
		
		System.out.println(newString);
	}

}
