/*
 * 示例程序Sample6_6: Interpolation类的连分式等距插值
 */
package javaalgorithm.sample;

import javaalgorithm.algorithm.Interpolation;

public class Sample6_6 
{
	public static void main(String[] args)
	{
		// 数据
		int n = 11;
		double x0 = -1.0;
		double xstep = 0.20;
		double[] y = {0.0384615,0.0588236,0.1,0.2,0.5,1.0,0.5,0.2,0.1,0.0588236,0.0384615};
		double[] t = {-0.75, -0.05};
		
		// 插值运算
		for (int i=0; i<t.length; ++i)
		{
			double yt = Interpolation.getValuePqs(n, x0, xstep, y, t[i]);
			System.out.println("f(" + t[i] + ") = " + yt);
		}
	}
}
