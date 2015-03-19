package com.dantefung.chainOfResponsibility.filter;

public class MsgProcessor {
	private String msg;
//	/**一系列的处理规则。
//	 * 优点：
//	 * 易于扩展、顺序、
//	 * 
//	 * 如果写在配置文件中就更方便了，将配置文件一改就行。
//	 * version2
//	 */
//	Filter[] filters ={new HTMLFilter(),new SensitiveFilter(),new FaceFilter()};
	
	FilterChain fc;
	
	/*这一部分逻辑上可能会产生扩展。能否用容易点的扩展，将其封装起来。
	 * 要求：过滤，过滤的规则能动态的指定。
	 * 
	 * 显然以下的代码扩展性不行。
	 * 
	 * version1
	 * */
//	public String process()
//	{
//		//process the html tag<>
////		String r = msg.replace('<','[');
////		r = r.replace('>',']');
//		//当然也可以采用链式编程的方式。
//		String r = msg.replace('<','[').replace('>',']');
//		//process the sensitive words
//		r = r.replace("被就业","就业")
//		 .replace("敏感", "");
//		return r;
//	}

	public FilterChain getFc() {
		return fc;
	}

	public void setFc(FilterChain fc) {
		this.fc = fc;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}
	
//	//version2
//	public String process()
//	{
//		String r =msg;
//		//过滤器链
//		for(Filter f: filters)
//		{
//			r = f.doFilter(r);
//		}
//		return r;
//	}
	
	//version3
	public String process()
	{
		return fc.doFilter(msg);
	}
}
