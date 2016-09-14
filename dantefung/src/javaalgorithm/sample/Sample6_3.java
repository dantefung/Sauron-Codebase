/*
 * 示例程序Sample6_3: Interpolation类的一元三点不等距插值
 */
package javaalgorithm.sample;

import javaalgorithm.algorithm.Interpolation;

public class Sample6_3 
{
	public static void main(String[] args)
	{
		// 数据
		int n = 5;
		double[] x = {1.615,1.634,1.702,1.828,1.921};
		double[] y = {2.41450,2.46459,2.65271,3.03035,3.34066};
		double[] t = {1.682,1.813};
		
		// 插值运算
		for (int i=0; i<t.length; ++i)
		{
			double yt = Interpolation.getValueLagrange3(n, x, y, t[i]);
			System.out.println("f(" + t[i] + ") = " + yt);
		}
	}
}
