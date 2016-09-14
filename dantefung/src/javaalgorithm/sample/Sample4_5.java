/*
 * 示例程序Sample4_5: LEquations类的复系数方程组的全选主元高斯－约当消去法
 */
package javaalgorithm.sample;

import javaalgorithm.algorithm.Complex;
import javaalgorithm.algorithm.Matrix;
import javaalgorithm.algorithm.LEquations;

public class Sample4_5 
{
	public static void main(String[] args)
	{
		// 系数矩阵数据
		// 实部
		double[] mtxDataCoef4Real = {
				1.0,3.0,2.0,13.0,
				7.0,2.0,1.0,-2.0,
				9.0,15.0,3.0,-2.0,
				-2.0,-2.0,11.0,5.0};
		// 虚部
		double[] mtxDataCoef4Imag = {
				3.0,-2.0,1.0,6.0,
				-2.0,7.0,5.0,8.0,
				9.0,-3.0,15.0,1.0,
				-2.0,-2.0,7.0,6.0};
		// 常数矩阵数据
		// 实部
		double[] mtxDataConst4Real = {
				2.0,-2.0,
				7.0,3.0,
				3.0,2.0,
				9.0,1.0};
		// 虚部
		double[] mtxDataConst4Imag = {
				1.0,3.0,
				2.0,7.0,
				-2.0,9.0,
				3.0,2.0};
		
		// 构造系数矩阵
		Matrix mtxCoef4Real = new Matrix(4, mtxDataCoef4Real);
		Matrix mtxCoef4Imag = new Matrix(4, mtxDataCoef4Imag);
		// 构造常数矩阵
		Matrix mtxConst4Real = new Matrix(4, 2, mtxDataConst4Real);
		Matrix mtxConst4Imag = new Matrix(4, 2, mtxDataConst4Imag);

		// 构造线性方程组
		LEquations leqs4 = new LEquations(mtxCoef4Real, mtxConst4Real);
		
		// 复系数方程组的全选主元高斯－约当消去法
		Matrix mtxResult4Real = new Matrix();
		Matrix mtxResult4Imag = new Matrix();
		if (leqs4.GetRootsetGaussJordan(mtxCoef4Imag, mtxConst4Imag, mtxResult4Real, mtxResult4Imag))
		{
			for (int i=0; i<mtxConst4Real.getNumRows(); ++i)
			{
				Complex cp1 = new Complex(mtxResult4Real.getElement(i,0), mtxResult4Imag.getElement(i,0));
				Complex cp2 = new Complex(mtxResult4Real.getElement(i,1), mtxResult4Imag.getElement(i,1));
				System.out.println(cp1 + ", " + cp2);
			}
		}
		else
		{
			System.out.println("失败");
		}
	}
}
