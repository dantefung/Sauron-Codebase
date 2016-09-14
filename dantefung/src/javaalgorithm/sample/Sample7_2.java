/*
 * 示例程序Sample7_2: Integral的变步长辛卜生求积法
 */
package javaalgorithm.sample;

import javaalgorithm.algorithm.Integral;

public class Sample7_2 
{
	public static void main(String[] args)
	{
		// 建立Integral的子类，在其中重载函数Func
		class Integ extends Integral
		{
			public double func(double x)
			{
				return Math.log(1.0+x)/(1.0+x*x);
			}
		};

		// 求解
		Integ integ = new Integ();
		double value = integ.getValueSimpson(0.0, 1.0, 0.00001);

		// 显示结果
		System.out.println(value);
	}
}
