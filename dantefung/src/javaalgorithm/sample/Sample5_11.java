/*
 * 示例程序Sample5_11: NLEquations类的求非线性方程一个实根的蒙特卡洛法
 */
package javaalgorithm.sample;

import javaalgorithm.algorithm.Real;
import javaalgorithm.algorithm.NLEquations;

public class Sample5_11 
{
	public static void main(String[] args)
	{
		// 建立NLEquation的子类，在其中重载函数func
	 	class NLEq extends NLEquations
		{
			protected double func(double x)
			{
				double z = Math.exp(-x*x*x)-Math.sin(x)/Math.cos(x)+800.0;
				return z;
			}
		}
		// 求解
	 	NLEq nleq = new NLEq();
	    Real x=new Real(0.5); 
		double b=1.0; 
		int m=10;
		double eps=0.000001;
		nleq.getRootMonteCarlo(x, b, m, eps);
		System.out.println(x);
	}
}
