/*
 * 示例程序Sample4_15: LEquations类的求解线性最小二乘问题的广义逆法
 */
package javaalgorithm.sample;

import javaalgorithm.algorithm.Matrix;
import javaalgorithm.algorithm.LEquations;

public class Sample4_15 
{
	public static void main(String[] args)
	{
		// 系数矩阵数据
		double[] mtxDataCoef14 = {
				1.0,1.0,-1.0,
				2.0,1.0,0.0,
				1.0,-1.0,0.0,
				-1.0,2.0,1.0};
		// 常数矩阵数据
		double[] mtxDataConst14 = {
				2.0,
				-3.0,
				1.0,
				4.0};
		
		// 构造系数矩阵
		Matrix mtxCoef14 = new Matrix(4, 3, mtxDataCoef14);
		// 构造常数矩阵
		Matrix mtxConst14 = new Matrix(4, 1, mtxDataConst14);

		// 构造线性方程组
		LEquations leqs14 = new LEquations(mtxCoef14, mtxConst14);
		
		// 求解线性最小二乘问题的广义逆法
		Matrix mtxResult14 = new Matrix();
		Matrix mtxResultAP = new Matrix();
		Matrix mtxResultU = new Matrix();
		Matrix mtxResultV = new Matrix();
		if (leqs14.getRootsetGinv(mtxResult14, mtxResultAP, mtxResultU, mtxResultV, 0.0001))
		{
			System.out.println(mtxResult14);
		}
		else
		{
			System.out.println("失败");
		}
	}
}
