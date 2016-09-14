/*
 * 示例程序Sample4_12: LEquations类的高斯－赛德尔迭代法
 */
package javaalgorithm.sample;

import javaalgorithm.algorithm.Matrix;
import javaalgorithm.algorithm.LEquations;

public class Sample4_12 
{
	public static void main(String[] args)
	{
		// 系数矩阵数据
		double[] mtxDataCoef11 = {
				7.0,2.0,1.0,-2.0,
				9.0,15.0,3.0,-2.0,
				-2.0,-2.0,11.0,5.0,
				1.0,3.0,2.0,13.0};
		// 常数矩阵数据
		double[] mtxDataConst11 = {
				4.0,
				7.0,
				-1.0,
				0.0};
		
		// 构造系数矩阵
		Matrix mtxCoef11 = new Matrix(4, mtxDataCoef11);
		// 构造常数矩阵
		Matrix mtxConst11 = new Matrix(4, 1, mtxDataConst11);

		// 构造线性方程组
		LEquations leqs11 = new LEquations(mtxCoef11, mtxConst11);
		
		// 高斯－赛德尔迭代法
		Matrix mtxResult11 = new Matrix();
		if (leqs11.getRootsetGaussSeidel(mtxResult11, 0.0001))
		{
			System.out.println(mtxResult11);
		}
		else
		{
			System.out.println("失败");
		}
	}
}
