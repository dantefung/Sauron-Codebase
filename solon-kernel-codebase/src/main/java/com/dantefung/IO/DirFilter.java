package com.dantefung.IO;

import java.io.File;
import java.io.FilenameFilter;
import java.util.regex.Pattern;

public class DirFilter implements FilenameFilter {

	private Pattern pattern;

	public DirFilter(String regex) {
		pattern = Pattern.compile(regex);//指定为字符串的正则表达式必须首先被编译为此类的实例.
	}

	@Override
	public boolean accept(File dir, String name) {
		return pattern.matcher(name).matches();//执行验证。
	}

}
