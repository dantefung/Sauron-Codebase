/*
 * 示例程序Sample6_9: Interpolation类的埃特金不等距逐步插值
 */
package javaalgorithm.sample;

import javaalgorithm.algorithm.Interpolation;

public class Sample6_9 
{
	public static void main(String[] args)
	{
		// 数据
		int n = 10;
		double[] x = {-1.0,-0.8,-0.65,-0.4,-0.3,0.0,0.2,0.4,0.6,0.8,1.0};
		double[] y = {0.0384615,0.0588236,0.0864865,0.2,0.307692,1.0,0.5,0.2,0.1,0.0588236,0.0384615};
		double[] t = {-0.75,0.05};
		
		// 插值运算
		for (int i=0; i<t.length; ++i)
		{
			double yt = Interpolation.getValueAitken(n, x, y, t[i], 0.0000001);
			System.out.println("f(" + t[i] + ") = " + yt);
		}
	}
}
