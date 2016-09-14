/*
 * 示例程序Sample5_5: NLEquations类的求实系数代数方程全部根的QR方法
 */
package javaalgorithm.sample;

import javaalgorithm.algorithm.Complex;
import javaalgorithm.algorithm.NLEquations;

public class Sample5_5 
{
	public static void main(String[] args)
	{
		// 建立NLEquation的子类
	 	class NLEq extends NLEquations
		{
		}
		// 求解
	 	NLEq nleq = new NLEq();
		int n = 6;
		double[] xr = new double[n];
		double[] xi = new double[n];
		double[] a = {-30.0,10.5,-10.5,1.5,4.5,-7.5,1.5};
		if (nleq.getRootQr(n, a, xr, xi, 60, 0.000001))
		{
			for (int i=0; i<n; ++i)
			{
				Complex cpx = new Complex(xr[i], xi[i]);
				System.out.println(cpx);
			}
		}
		else
			System.out.println("求解失败");
	}
}
