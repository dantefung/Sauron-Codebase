/*
 * 示例程序Sample6_11: Interpolation类的光滑不等距插值
 */
package javaalgorithm.sample;

import javaalgorithm.algorithm.Interpolation;

public class Sample6_11 
{
	public static void main(String[] args)
	{
		// 数据
		int n = 11;
		double[] x = {-1.0,-0.95,-0.75,-0.55,-0.3,0.0,0.2,0.45,0.6,0.8,1.0};
		double[] y = {0.0384615,0.0424403,0.06639,0.116788,0.307692,1.0,0.5,0.164948,0.1,0.0588236,0.0384615};
		double[] t = {-0.85,0.15};
		
		// 插值运算
		for (int i=0; i<t.length; ++i)
		{
			double[] coef = new double[5];
			double yt = Interpolation.getValueAkima(n, x, y, t[i], coef, -1);
			System.out.println("f(" + t[i] + ") = " + yt);
			for (int j=0; j<coef.length; ++j)
				System.out.println("s" + j + " = " + coef[j]);
		}
	}
}
