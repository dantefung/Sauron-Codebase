/*
 * 示例程序Sample4_2: LEquations类的全选主元高斯消去法
 */
package javaalgorithm.sample;

import javaalgorithm.algorithm.Matrix;
import javaalgorithm.algorithm.LEquations;

public class Sample4_2 
{
	public static void main(String[] args)
	{
		// 系数矩阵数据
		double[] mtxDataCoef1 = {
				0.2368,0.2471,0.2568,1.2671,
				0.1968,0.2071,1.2168,0.2271,
				0.1581,1.1675,0.1768,0.1871,
				1.1161,0.1254,0.1397,0.1490};
		// 常数矩阵数据
		double[] mtxDataConst1 = {
				1.8471,
				1.7471,
				1.6471,
				1.5471};
		
		// 构造系数矩阵
		Matrix mtxCoef1 = new Matrix(4, mtxDataCoef1);
		// 构造常数矩阵
		Matrix mtxConst1 = new Matrix(4, 1, mtxDataConst1);

		// 构造线性方程组
		LEquations leqs1 = new LEquations(mtxCoef1, mtxConst1);
		
		// 全选主元高斯消去法
		Matrix mtxResult1 = new Matrix();
		if (leqs1.getRootsetGauss(mtxResult1))
		{
			System.out.println(mtxResult1);
		}
		else
		{
			System.out.println("失败");
		}
	}
}
