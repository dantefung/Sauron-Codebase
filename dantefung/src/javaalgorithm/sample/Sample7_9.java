/*
 * 示例程序Sample7_9: Integral的埃尔米特－高斯求积法
 */
package javaalgorithm.sample;

import javaalgorithm.algorithm.Integral;

public class Sample7_9 
{
	public static void main(String[] args)
	{
		// 建立Integral的子类，在其中重载函数Func
		class Integ extends Integral
		{
			public double func(double x)
			{
				return x*x*Math.exp(-x*x);
			}
		};

		// 求解
		Integ integ = new Integ();
		double value = integ.getValueHermiteGauss();

		// 显示结果
		System.out.println(value);
	}
}
