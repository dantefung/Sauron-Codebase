/*
 * 示例程序Sample6_15: Interpolation类的第三种边界条件的三次样条函数插值、微商与积分
 */
package javaalgorithm.sample;

import javaalgorithm.algorithm.Interpolation;

public class Sample6_15 
{
	public static void main(String[] args)
	{
		// 数据
		int n = 37;
		double[] x = new double[n];
		double[] y = new double[n];
		double[] dy = new double[n];
		double[] ddy = new double[n];
		int m = 36;
		double[] t = new double[m];
		double[] z = new double[m];
		double[] dz = new double[m];
		double[] ddz = new double[m];

		for (int i=0; i<n; ++i)
		{
			x[i]=i*6.2831852/36.0;
	        y[i]=Math.sin(x[i]);
		}

		for (int i=0; i<m; ++i)
		{
	        t[i]=(0.5+i)*6.2831852/36.0;
		}

		// 插值运算
		
		double yt = Interpolation.getValueSpline3(n, x, y, dy, ddy, m, t, z, dz, ddz);
		System.out.println("定积分值 = " + yt);
		System.out.println("\nx[i]\ty[i]=six(x)\t\tdy[i]=cos(x)\t\tddy[i]=-sin(x)\n");
		System.out.println(x[0] + "  " + y[0] + "\t\t" + dy[0] + "  " + ddy[0]);
		for (int i=0; i<m; ++i)
		{
			float u=(float)(t[i]*36.0/0.62831852);
			System.out.println(u + "  " + z[i] + "  " + dz[i] + "  " + ddz[i]);
	        u=(float)(x[i+1]*36.0/0.62831852);
			System.out.println(u + "  " + z[i] + "  " + dz[i] + "  " + ddz[i]);
		}
	}
}
