/*
 * Copyright (C), 2015-2018
 * FileName: VisitorPattern
 * Author:   DANTE FUNG
 * Date:     2020/4/3 10:39
 * Description: 客户类
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 * DANTE FUNG        2020/4/3 10:39   V1.0.0
 */
package com.dantefung.dp.visitor.theory;

import com.dantefung.dp.visitor.theory.impl.*;

/**
 * @Title: VisitorPattern
 * @Description: 客户端调用类
 * @author DANTE FUNG
 * @date 2020/4/3 10:39
 */
public class VisitorPattern {

	public static void main(String[] args) {
		ObjectStructure os=new ObjectStructure();
		os.add(new ConcreteElementA());
		os.add(new ConcreteElementB());
		Visitor visitor=new ConcreteVisitorA();
		os.accept(visitor);
		System.out.println("------------------------");
		visitor=new ConcreteVisitorB();
		os.accept(visitor);
	}
}
