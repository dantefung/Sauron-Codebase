/*
 * 示例程序Sample4_9: LEquations类的求解对称正定方程组的平方根法
 */
package javaalgorithm.sample;

import javaalgorithm.algorithm.Matrix;
import javaalgorithm.algorithm.LEquations;

public class Sample4_9 
{
	public static void main(String[] args)
	{
		// 系数矩阵数据
		double[] mtxDataCoef8 = {
				5.0,7.0,6.0,5.0,
				7.0,10.0,8.0,7.0,
				6.0,8.0,10.0,9.0,
				5.0,7.0,9.0,10.0};
		// 常数矩阵数据
		double[] mtxDataConst8 = {
				23.0,92.0,
				32.0,128.0,
				33.0,132.0,
				31.0,124.0};
		
		// 构造系数矩阵
		Matrix mtxCoef8 = new Matrix(4, mtxDataCoef8);
		// 构造常数矩阵
		Matrix mtxConst8 = new Matrix(4, 2, mtxDataConst8);

		// 构造线性方程组
		LEquations leqs8 = new LEquations(mtxCoef8, mtxConst8);
		
		// 求解对称正定方程组的平方根法
		Matrix mtxResult8 = new Matrix();
		if (leqs8.getRootsetCholesky(mtxResult8))
		{
			System.out.println(mtxResult8);
		}
		else
		{
			System.out.println("失败");
		}
	}
}
