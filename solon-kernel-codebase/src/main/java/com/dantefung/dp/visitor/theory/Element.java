package com.dantefung.dp.visitor.theory;

/**
 * @Title: Element
 * @Description: <strong>抽象元素（Element）角色：</strong><br/>
 * <p>声明一个包含接受操作 accept() 的接口，被接受的访问者对象作为 accept() 方法的参数</p>
 * @author DANTE FUNG
 * @date 2020/4/3 10:41
 */
public interface Element {

	void accept(Visitor visitor);
}
