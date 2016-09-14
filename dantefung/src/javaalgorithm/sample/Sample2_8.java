/*
 * 示例程序Sample2_8: Complex类的自然对数运算
 */
package javaalgorithm.sample;

import javaalgorithm.algorithm.Complex;

public class Sample2_8 
{
	public static void main(String[] args) 
	{
		Complex cpx = new Complex(3, 2);
		Complex cpxLog = cpx.log();
		System.out.println(cpx + "的自然对数为" + cpxLog);
	}
}
