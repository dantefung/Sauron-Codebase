package com.dantefung.dp.visitor.theory;

import com.dantefung.dp.visitor.theory.impl.ConcreteElementA;
import com.dantefung.dp.visitor.theory.impl.ConcreteElementB;

/**
 * @Title: Visitor
 * @Description: <strong>抽象访问者（Visitor）角色：</strong>
 * <p>定义一个访问具体元素的接口，为每个具体元素类对应一个访问操作 visit() ，该操作中的参数类型标识了被访问的具体元素。</p>
 * @author DANTE FUNG
 * @date 2020/4/3 10:43
 */
public interface Visitor {

	void visit(ConcreteElementA element);
	void visit(ConcreteElementB element);
}
