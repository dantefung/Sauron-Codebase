package com.dantefung.dp.filter_;

public class FaceFilter implements Filter {

	@Override
	public String doFilter(String str) {
		return str.replace(":)", "^V^");
	}

}
