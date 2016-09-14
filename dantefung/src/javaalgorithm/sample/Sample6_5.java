/*
 * 示例程序Sample6_5: Interpolation类的连分式不等距插值
 */
package javaalgorithm.sample;

import javaalgorithm.algorithm.Interpolation;

public class Sample6_5
{
	public static void main(String[] args)
	{
		// 数据
		int n = 10;
		double[] x = {-1.0,-0.8,-0.65,-0.4,-0.3,0.0,0.2,0.45,0.8,1.0};
		double[] y = {0.0384615,0.0588236,0.0864865,0.2,0.307692,1.0,0.5,0.164948,0.0588236,0.0384615};
		double[] t = {0.85, 0.25};
		
		// 插值运算
		for (int i=0; i<t.length; ++i)
		{
			double yt = Interpolation.getValuePqs(n, x, y, t[i]);
			System.out.println("f(" + t[i] + ") = " + yt);
		}
	}
}
