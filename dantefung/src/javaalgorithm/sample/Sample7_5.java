/*
 * 示例程序Sample7_5: Integral的计算一维积分的连分式法
 */
package javaalgorithm.sample;

import javaalgorithm.algorithm.Integral;

public class Sample7_5 
{
	public static void main(String[] args)
	{
		// 建立Integral的子类，在其中重载函数Func
		class Integ extends Integral
		{
			public double func(double x)
			{
				return Math.exp(-x*x);
			}
		};

		// 求解
		Integ integ = new Integ();
		double value = integ.getValuePq(0.0, 4.3, 0.00001);

		// 显示结果
		System.out.println(value);
	}
}
