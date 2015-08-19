package com.dantefung.chainOfResponsibility.filter;

import java.util.ArrayList;
import java.util.List;

/**Tomcat内是接口。
 * 
 * 工具类，面向切片思想。AOP。--dantefung自己的感悟
 * 
 * @author DanteFung
 *
 */
public class FilterChain implements Filter {
	List<Filter> filters = new ArrayList<Filter>();
	
	public FilterChain addFilter(Filter f)
	{
		this.filters.add(f);
		return this;//这样写的好处就是可以实现链式编程。
	}
	
	//将Filter链条看成一个整体，那么这个整体也是一个大的Filter，则也有doFilter（）方法。
	public String doFilter(String str)
	{
		String r =str;
		for(Filter f: filters)
		{
			r = f.doFilter(r);
		}
		return r;
	}
}
