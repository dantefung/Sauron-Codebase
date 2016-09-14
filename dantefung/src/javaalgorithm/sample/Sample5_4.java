/*
 * 示例程序Sample5_4: NLEquations类的求非线性方程一个实根的连分式解法
 */
package javaalgorithm.sample;

import javaalgorithm.algorithm.NLEquations;
import javaalgorithm.algorithm.Real;

public class Sample5_4 
{
	public static void main(String[] args)
	{
		// 建立NLEquation的子类，在其中重载函数func
	 	class NLEq extends NLEquations
		{
	 		protected double func(double x)
	 		{
			    double y = x*x*(x-1.0)-1.0;
				return y;
	 		}
		}
		// 求解
	 	NLEq nleq = new NLEq();
		Real x = new Real(1.0);
		if (nleq.getRootPq(x, 0.000001))
			System.out.println("求得的根为：" + x);
		else
			System.out.println("求解失败");
	}
}
