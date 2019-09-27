package com.dantefung.dp.factory.springsim;

import java.io.IOException;
import java.util.Properties;

public class Test {

	/**
	 * @param args
	 * @throws IOException 
	 */
	public static void main(String[] args) throws Exception {
		BeanFactory f = new ClassPathApplicationXmlContext("com/dantefung/dp/factory/springsim/applicationContext.xml");
		Object o = f.getBean("v");
		Moveable m = (Moveable)o;
		m.run();
	}

}
