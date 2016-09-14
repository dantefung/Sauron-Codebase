/*
 * 示例程序Sample6_8: Interpolation类的埃尔米特等距插值
 */
package javaalgorithm.sample;

import javaalgorithm.algorithm.Interpolation;

public class Sample6_8 
{
	public static void main(String[] args)
	{
		// 数据
		int n = 10;
		double x0 = 0.10;
		double xstep = 0.10;
		double[] y = {0.904837,0.818731,0.740818,0.670320,0.606531,0.548812,0.496585,0.449329,0.406570,0.367879};
		double[] t = {0.752};

		double[] dy = new double[y.length];
		for (int i=0; i<y.length; ++i)
			dy[i]=-y[i];

		// 插值运算
		for (int i=0; i<t.length; ++i)
		{
			double yt = Interpolation.getValueHermite(n, x0, xstep, y, dy, t[i]);
			System.out.println("f(" + t[i] + ") = " + yt);
		}
	}
}
