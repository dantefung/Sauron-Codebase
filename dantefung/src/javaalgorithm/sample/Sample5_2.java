/*
 * 示例程序Sample5_2: NLEquations类的求非线性方程一个实根的牛顿法
 */
package javaalgorithm.sample;

import javaalgorithm.algorithm.Real;
import javaalgorithm.algorithm.NLEquations;

public class Sample5_2 
{
	public static void main(String[] args)
	{
		// 建立NLEquation的子类，在其中重载函数func
	 	class NLEq extends NLEquations
		{
			protected void func(double x, double[] y)
			{
				y[0]=x*x*(x-1.0)-1.0;
				y[1]=3.0*x*x-2.0*x;
			}
		}
		// 求解
	 	NLEq nleq = new NLEq();
		Real x = new Real(1.5);
		if (nleq.getRootNewton(x, 60, 0.000001))
			System.out.println("求得的根为：" + x);
		else
			System.out.println("求解失败");
	}
}
