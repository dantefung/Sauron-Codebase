/*
 * 示例程序Sample7_6: Integral的高振荡函数求积法
 */
package javaalgorithm.sample;

import javaalgorithm.algorithm.Integral;

public class Sample7_6 
{
	public static void main(String[] args)
	{
		// 建立Integral的子类，在其中重载函数Func
		class Integ extends Integral
		{
			public double func(double x)
			{
				return 0.0;
			}
		};

		// 数据
	    double[] s = new double[2];
		double[] fa={0.0,1.0,0.0,-3.0};
	    double[] fb={6.2831852,1.0,-6.2831852,-3.0};
	    double a=0.0; 
		double b=6.2831852;
	    int m=30; 
		int n=4;

		// 求解
		Integ integ = new Integ();
		double value = integ.getValuePart(a, b, m, n, fa, fb, s);

		// 显示结果
		System.out.println("s[0] = " + s[0]);
		System.out.println("s[1] = " + s[1]);
	}
}
