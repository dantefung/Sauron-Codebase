package com.dantefung.chainOfResponsibility.filter;

public class HTMLFilter implements Filter {

	@Override
	public String doFilter(String str)
	{
		//process the tag <>
		String r = str.replace('<','[').replace('>',']');
		return r;
	}
}
