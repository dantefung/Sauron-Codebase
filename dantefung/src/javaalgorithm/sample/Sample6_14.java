/*
 * 示例程序Sample6_14: Interpolation类的第二种边界条件的三次样条函数插值、微商与积分
 */
package javaalgorithm.sample;

import javaalgorithm.algorithm.Interpolation;

public class Sample6_14 
{
	public static void main(String[] args)
	{
		// 数据
		int n = 12;
		double[] x = {0.52,8.0,17.95,28.65,50.65,104.6,156.6,260.7,364.4,468.0,507.0,520.0};
		double[] y = {5.28794,13.84,20.2,24.9,31.1,36.5,36.6,31.0,20.9,7.8,1.5,0.2};
		double[] t = {4.0,14.0,30.0,60.0,130.0,230.0,450.0,515.0};
		
		// 微分
		double[] dy = new double[n];
		double[] ddy = new double[n];
		ddy[0] = -0.279319;
		ddy[n-1] = 0.011156;

		// 插值运算
		int m = 8;
		double[] z = new double[m];
		double[] dz = new double[m];
		double[] ddz = new double[m];
		
		double yt = Interpolation.getValueSpline2(n, x, y, dy, ddy, m, t, z, dz, ddz);
		System.out.println("定积分值 = " + yt);
		System.out.println("\nt[i]\t\tz[i]\t\t\tdz[i]\t\t\tddz[i]\n");
		for (int i=0; i<m; ++i)
		{
			System.out.println(t[i] + "\t" + z[i] + "\t" + dz[i] + "\t" + ddz[i]);
		}
	}
}
