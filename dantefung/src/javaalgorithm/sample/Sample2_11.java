/*
 * 示例程序Sample2_11: Complex类的正切运算
 */
package javaalgorithm.sample;

import javaalgorithm.algorithm.Complex;

public class Sample2_11 
{
	public static void main(String[] args) 
	{
		Complex cpx = new Complex(0.25, 0.25);
		Complex cpxTan = cpx.tan();
		System.out.println(cpx + "的正切为" + cpxTan);
	}
}
