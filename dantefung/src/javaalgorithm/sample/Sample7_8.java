/*
 * 示例程序Sample7_8: Integral的拉盖尔－高斯求积法
 */
package javaalgorithm.sample;

import javaalgorithm.algorithm.Integral;

public class Sample7_8 
{
	public static void main(String[] args)
	{
		// 建立Integral的子类，在其中重载函数Func
		class Integ extends Integral
		{
			public double func(double x)
			{
				return x*Math.exp(-x);
			}
		};

		// 求解
		Integ integ = new Integ();
		double value = integ.getValueLgreGauss();

		// 显示结果
		System.out.println(value);
	}
}
