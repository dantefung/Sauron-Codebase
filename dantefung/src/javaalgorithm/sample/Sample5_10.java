/*
 * 示例程序Sample5_10: NLEquations类的求非线性方程组最小二乘解的广义逆法
 */
package javaalgorithm.sample;

import javaalgorithm.algorithm.NLEquations;

public class Sample5_10
{
	public static void main(String[] args)
	{
		// 建立NLEquation的子类，在其中重载函数func和funcMJ
	 	class NLEq extends NLEquations
		{
			protected double func(double[] x, double[] y)
			{
				y[0]=x[0]*x[0]+7.0*x[0]*x[1]+3.0*x[1]*x[1]+0.5;
				y[1]=x[0]*x[0]-2.0*x[0]*x[1]+x[1]*x[1]-1.0;
				y[2]=x[0]+x[1]+1.0;

				return 0.0;
			}
			
			protected void funcMJ(double[] x, double[] p)
			{
				int n = x.length;
				p[0*n+0]=2.0*x[0]+7.0*x[1];
				p[0*n+1]=7.0*x[0]+6.0*x[1];
				p[1*n+0]=2.0*x[0]-2.0*x[1];
				p[1*n+1]=-2.0*x[0]+2.0*x[1];
				p[2*n+0]=1.0;
				p[2*n+1]=1.0;
			}
		}
		// 求解
	 	NLEq nleq = new NLEq();
	    double eps1 = 0.000001;
		double eps2 = 0.000001;
	    double[] x={1.0,-1.0};
	    int m=3; 
		int n=2;
		if (nleq.getRootsetGinv(m, n, x, eps1, eps2))
		{
			for (int i=0; i<n; ++i)
			{
				System.out.println(x[i]);
			}
		}
		else
			System.out.println("求解失败");
	}
}
