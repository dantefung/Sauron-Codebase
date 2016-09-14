/*
 * 示例程序Sample2_9: Complex类正弦运算
 */
package javaalgorithm.sample;

import javaalgorithm.algorithm.Complex;

public class Sample2_9 
{
	public static void main(String[] args) 
	{
		Complex cpx = new Complex(5, 7);
		Complex cpxSin = cpx.sin();
		System.out.println(cpx + "的正弦为" + cpxSin);
	}
}
