/*
 * 示例程序Sample2_5: Complex类求根运算
 */
package javaalgorithm.sample;

import javaalgorithm.algorithm.Complex;

public class Sample2_5
{
	public static void main(String[] args) 
	{
		Complex cpx = new Complex(16, 81);
		int n = 4;
		
		Complex[] cpxR = new Complex[n];
		cpx.root(n, cpxR);
		
		System.out.println(cpx + "的" + n + "次根为：");
		for (int i=0; i<n; ++i)
			System.out.println(cpxR[i]);
	}
}
