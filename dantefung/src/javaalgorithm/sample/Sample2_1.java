/*
 * 示例程序Sample2_1: Complex类的基本用法
 */
package javaalgorithm.sample;

import javaalgorithm.algorithm.Complex;

public class Sample2_1 
{
	public static void main(String[] args) 
	{
		Complex cpx1 = new Complex(2.5, 7.9);
		Complex cpx2 = new Complex("3.6,1.4", ",");
		Complex cpx3 = cpx1.add(cpx2);
		Complex cpx4 = cpx3.subtract(cpx2);
		
		System.out.println("cpx1 = " + cpx1);
		System.out.println("cpx2 = " + cpx2);
		System.out.println("cpx3 = cpx1 + cpx2 = " + cpx3);
		System.out.println("cpx4 = cpx3 - cpx2 = " + cpx4);
		
		if (cpx4.equal(cpx1))
			System.out.println("cpx4 = cpx1");
		else
			System.out.println("cpx4 != cpx1");
	}
}
