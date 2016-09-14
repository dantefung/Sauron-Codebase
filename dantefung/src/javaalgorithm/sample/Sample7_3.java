/*
 * 示例程序Sample7_3: Integral的自适应梯形求积法
 */
package javaalgorithm.sample;

import javaalgorithm.algorithm.Integral;

public class Sample7_3 
{
	public static void main(String[] args)
	{
		// 建立Integral的子类，在其中重载函数Func
		class Integ extends Integral
		{
			public double func(double x)
			{
				return 1.0/(1.0+25.0*x*x);
			}
		};

		// 求解
		Integ integ = new Integ();
		double value = integ.getValueATrapezia(-1.0, 1.0, 0.0001, 0.000001);

		// 显示结果
		System.out.println(value);
	}
}
