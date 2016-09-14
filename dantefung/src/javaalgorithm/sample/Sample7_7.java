/*
 * 示例程序Sample7_7: Integral的勒让德－高斯求积法
 */
package javaalgorithm.sample;

import javaalgorithm.algorithm.Integral;

public class Sample7_7 
{
	public static void main(String[] args)
	{
		// 建立Integral的子类，在其中重载函数Func
		class Integ extends Integral
		{
			public double func(double x)
			{
				return x*x+Math.sin(x);
			}
		};

		// 求解
		Integ integ = new Integ();
		double value = integ.getValueLegdGauss(2.5, 8.4, 0.00001);

		// 显示结果
		System.out.println(value);
	}
}
