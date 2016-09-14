/*
 * 示例程序Sample5_3: NLEquations类的求非线性方程一个实根的埃特金迭代法
 */
package javaalgorithm.sample;

import javaalgorithm.algorithm.NLEquations;
import javaalgorithm.algorithm.Real;

public class Sample5_3 
{
	public static void main(String[] args)
	{
		// 建立NLEquation的子类，在其中重载函数func
	 	class NLEq extends NLEquations
		{
	 		protected double func(double x)
	 		{
			    double y = 6.0-x*x;
				return y;
	 		}
		}
		// 求解
	 	NLEq nleq = new NLEq();
		Real x = new Real(0.0);
		if (nleq.getRootAitken(x, 60, 0.000001))
			System.out.println("求得的根为：" + x);
		else
			System.out.println("求解失败");
	}
}
