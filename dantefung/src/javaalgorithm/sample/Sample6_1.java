/*
 * 示例程序Sample6_1: Interpolation类的一元全区间不等距插值
 */
package javaalgorithm.sample;

import javaalgorithm.algorithm.Interpolation;

public class Sample6_1 
{
	public static void main(String[] args)
	{
		// 数据
		int n = 10;
		double[] x = {0.10,0.15,0.25,0.40,0.50,0.57,0.70,0.85,0.93,1.00};
		double[] y = {0.904837,0.860708,0.778801,0.670320,0.606531,0.565525,0.496585,0.427415,0.394554,0.367879};
		double t = 0.63;
		
		// 插值运算
		double yt = Interpolation.getValueLagrange(n, x, y, t);
		System.out.println("f(" + t + ") = " + yt);
	}
}
