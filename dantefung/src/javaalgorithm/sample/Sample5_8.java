/*
 * 示例程序Sample5_8: NLEquations类的求非线性方程组一组实根的梯度法
 */
package javaalgorithm.sample;

import javaalgorithm.algorithm.NLEquations;

public class Sample5_8 
{
	public static void main(String[] args)
	{
		// 建立NLEquation的子类，在其中重载函数func
	 	class NLEq extends NLEquations
		{
			protected double func(double[] x, double[] y)
			{
				double z,f1,f2,f3,df1,df2,df3;

				f1=x[0]-5.0*x[1]*x[1]+7.0*x[2]*x[2]+12.0;
				f2=3.0*x[0]*x[1]+x[0]*x[2]-11.0*x[0];
				f3=2.0*x[1]*x[2]+40.0*x[0];
				z=f1*f1+f2*f2+f3*f3;
				
				df1=1.0; 
				df2=3.0*x[1]+x[2]-11.0; 
				df3=40.0;
				
				y[0]=2.0*(f1*df1+f2*df2+f3*df3);
				df1=10.0*x[1]; 
				df2=3.0*x[0]; 
				df3=2.0*x[2];
				
				y[1]=2.0*(f1*df1+f2*df2+f3*df3);
				df1=14.0*x[2]; 
				df2=x[0]; 
				df3=2.0*x[1];
				y[2]=2.0*(f1*df1+f2*df2+f3*df3);
				
				return(z);
			}
		}
		// 求解
	 	NLEq nleq = new NLEq();
	    double[] x={1.5,7.5,-6.0};
	    double eps = 0.00000000001;
		if (nleq.getRootsetGrad(3, x, 600, eps))
		{
			for (int i=0; i<3; ++i)
			{
				System.out.println(x[i]);
			}
		}
		else
			System.out.println("求解失败");
	}
}
