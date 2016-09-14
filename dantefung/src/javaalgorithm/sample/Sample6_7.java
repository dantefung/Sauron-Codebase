/*
 * 示例程序Sample6_7: Interpolation类的埃尔米特不等距插值
 */
package javaalgorithm.sample;

import javaalgorithm.algorithm.Interpolation;

public class Sample6_7 
{
	public static void main(String[] args)
	{
		// 数据
		int n = 10;
		double[] x = {0.1,0.15,0.3,0.45,0.55,0.6,0.7,0.85,0.9,1.0};
		double[] y = {0.904837,0.860708,0.740818,0.637628,0.576950,0.548812,0.496585,0.427415,0.406570,0.367879};
		double[] t = {0.356};

		double[] dy = new double[y.length];
		for (int i=0; i<y.length; ++i)
			dy[i]=-y[i];
		
		// 插值运算
		for (int i=0; i<t.length; ++i)
		{
			double yt = Interpolation.getValueHermite(n, x, y, dy, t[i]);
			System.out.println("f(" + t[i] + ") = " + yt);
		}
	}
}
