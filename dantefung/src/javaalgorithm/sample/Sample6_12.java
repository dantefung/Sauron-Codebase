/*
 * 示例程序Sample6_12: Interpolation类的光滑等距插值
 */
package javaalgorithm.sample;

import javaalgorithm.algorithm.Interpolation;

public class Sample6_12 
{
	public static void main(String[] args)
	{
		// 数据
		int n = 10;
		double x0 = -1;
		double xstep = 0.2;
		double[] y = {0.0384615,0.0588236,0.1,0.2,0.5,1.0,0.5,0.2,0.1,0.0588236,0.0384615};
		double[] t = {-0.65,0.25};
		
		// 插值运算
		for (int i=0; i<t.length; ++i)
		{
			double[] coef = new double[5];
			double yt = Interpolation.getValueAkima(n, x0, xstep, y, t[i], coef, -1);
			System.out.println("f(" + t[i] + ") = " + yt);
			for (int j=0; j<4; ++j)
				System.out.println("s" + j + " = " + coef[j]);
		}
	}
}
