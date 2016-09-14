/*
 * 示例程序Sample2_4: Complex类求模运算
 */
package javaalgorithm.sample;

import javaalgorithm.algorithm.Complex;

public class Sample2_4 
{
	public static void main(String[] args) 
	{
		Complex cpx = new Complex(4.2, 9.4);
		
		System.out.println("abs(" + cpx + ") = " + cpx.abs());
	}
}
