/*
 * 示例程序Sample4_13: LEquations类的求解对称正定方程组的共轭梯度法
 */
package javaalgorithm.sample;

import javaalgorithm.algorithm.Matrix;
import javaalgorithm.algorithm.LEquations;

public class Sample4_13 
{
	public static void main(String[] args)
	{
		// 系数矩阵数据
		double[] mtxDataCoef12 = {
				5.0,7.0,6.0,5.0,
				7.0,10.0,8.0,7.0,
				6.0,8.0,10.0,9.0,
				5.0,7.0,9.0,10.0};
		// 常数矩阵数据
		double[] mtxDataConst12 = {
				23.0,
				32.0,
				33.0,
				31.0};
		
		// 构造系数矩阵
		Matrix mtxCoef12 = new Matrix(4, mtxDataCoef12);
		// 构造常数矩阵
		Matrix mtxConst12 = new Matrix(4, 1, mtxDataConst12);

		// 构造线性方程组
		LEquations leqs12 = new LEquations(mtxCoef12, mtxConst12);
		
		// 求解对称正定方程组的共轭梯度法
		Matrix mtxResult12 = new Matrix();
		leqs12.getRootsetGrad(mtxResult12, 0.0001);
		System.out.println(mtxResult12);
	}
}
