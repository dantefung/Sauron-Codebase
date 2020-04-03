package com.dantefung.dp.visitor.theory.impl;

import com.dantefung.dp.visitor.theory.Element;
import com.dantefung.dp.visitor.theory.Visitor;

//具体元素A类
public class ConcreteElementA implements Element {

	public void accept(Visitor visitor) {
		visitor.visit(this);
	}

	public String operationA() {
		return "具体元素A的操作。";
	}
}