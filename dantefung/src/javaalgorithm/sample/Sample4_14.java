/*
 * 示例程序Sample4_14: LEquations类的求解线性最小二乘问题的豪斯荷尔德变换法
 */
package javaalgorithm.sample;

import javaalgorithm.algorithm.Matrix;
import javaalgorithm.algorithm.LEquations;

public class Sample4_14 
{
	public static void main(String[] args)
	{
		// 系数矩阵数据
		double[] mtxDataCoef13 = {
				1.0,1.0,-1.0,
				2.0,1.0,0.0,
				1.0,-1.0,0.0,
				-1.0,2.0,1.0};
		// 常数矩阵数据
		double[] mtxDataConst13 = {
				2.0,
				-3.0,
				1.0,
				4.0};
		
		// 构造系数矩阵
		Matrix mtxCoef13 = new Matrix(4, 3, mtxDataCoef13);
		// 构造常数矩阵
		Matrix mtxConst13 = new Matrix(4, 1, mtxDataConst13);

		// 构造线性方程组
		LEquations leqs13 = new LEquations(mtxCoef13, mtxConst13);
		
		// 求解线性最小二乘问题的豪斯荷尔德变换法
		Matrix mtxResult13 = new Matrix();
		Matrix mtxResultQ = new Matrix();
		Matrix mtxResultR = new Matrix();
		if (leqs13.getRootsetMqr(mtxResult13, mtxResultQ, mtxResultR))
		{
			System.out.println(mtxResult13);
		}
		else
		{
			System.out.println("失败");
		}
	}
}
