/*
 * 示例程序Sample1_8: 利用Real类对象实现引用传值
 */
package javaalgorithm.sample;

import javaalgorithm.algorithm.Real;

public class Sample1_8
{
	/**
	 * 试图交换a, b的值, 不能成功
	 */
	public static void swap(Real a, Real b)
	{
		int c = a.intValue();
		a.setValue(b);
		b.setValue(c);
		
		System.out.println("\n在函数swap内a, b的值");
		System.out.println("a = " + a.intValue());
		System.out.println("b = " + b.intValue());
	}

	public static void main(String[] args) 
	{
		Real a = new Real(2);
		Real b = new Real(100);
		System.out.println("交换前a, b的值");
		System.out.println("a = " + a.intValue());
		System.out.println("b = " + b.intValue());

		// 试图交换a, b的值
		swap(a, b);
		
		System.out.println("\n交换后a, b的值");
		System.out.println("a = " + a.intValue());
		System.out.println("b = " + b.intValue());
	}
}
