/*
 * 示例程序Sample4_3: LEquations类的全选主元高斯－约当消去法
 */
package javaalgorithm.sample;

import javaalgorithm.algorithm.Matrix;
import javaalgorithm.algorithm.LEquations;

public class Sample4_3 
{
	public static void main(String[] args)
	{
		// 系数矩阵数据
		double[] mtxDataCoef2 = {
				1.0,3.0,2.0,13.0,
				7.0,2.0,1.0,-2.0,
				9.0,15.0,3.0,-2.0,
				-2.0,-2.0,11.0,5.0};
		// 常数矩阵数据
		double[] mtxDataConst2 = {
				9.0,0.0,
				6.0,4.0,
				11.0,7.0,
				-2.0,-1.0};
		
		// 构造系数矩阵
		Matrix mtxCoef2 = new Matrix(4, mtxDataCoef2);
		// 构造常数矩阵
		Matrix mtxConst2 = new Matrix(4, 2, mtxDataConst2);

		// 构造线性方程组
		LEquations leqs2 = new LEquations(mtxCoef2, mtxConst2);
		
		// 全选主元高斯－约当消去法
		Matrix mtxResult2 = new Matrix();
		if (leqs2.GetRootsetGaussJordan(mtxResult2))
		{
			System.out.println(mtxResult2);
		}
		else
		{
			System.out.println("失败");
		}
	}
}
