/*
 * 示例程序Sample6_4: Interpolation类的一元三点等距插值
 */
package javaalgorithm.sample;

import javaalgorithm.algorithm.Interpolation;

public class Sample6_4
{
	public static void main(String[] args)
	{
		// 数据
		int n = 10;
		double x0 = 0.10;
		double xstep = 0.10;
		double[] y = {0.904837,0.860708,0.778801,0.670320,0.606531,0.565525,0.496585,0.427415,0.394554,0.367879};
		double[] t = {0.25, 0.63, 0.95};
		
		// 插值运算
		for (int i=0; i<t.length; ++i)
		{
			double yt = Interpolation.getValueLagrange3(n, x0, xstep, y, t[i]);
			System.out.println("f(" + t[i] + ") = " + yt);
		}
	}
}
