package com.dantefung.chainOfResponsibility.filter;

public class FaceFilter implements Filter {

	@Override
	public String doFilter(String str) {
		return str.replace(":）", "^_^");
	}

}
