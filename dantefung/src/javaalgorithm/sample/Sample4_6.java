/*
 * 示例程序Sample4_6: LEquations类的求解三对角线方程组的追赶法
 */
package javaalgorithm.sample;

import javaalgorithm.algorithm.Matrix;
import javaalgorithm.algorithm.LEquations;

public class Sample4_6 
{
	public static void main(String[] args)
	{
		// 系数矩阵数据
		double[] mtxDataCoef5 = {
				13,12,0,0,0,
				11,10,9,0,0,
				 0, 8,7,6,0,
				 0, 0,5,4,3,
				 0, 0,0,2,1};
		// 常数矩阵数据
		double[] mtxDataConst5 = {
				3.0,
				0.0,
				-2.0,
				6.0,
				8.0};
		
		// 构造系数矩阵
		Matrix mtxCoef5 = new Matrix(5, mtxDataCoef5);
		// 构造常数矩阵
		Matrix mtxConst5 = new Matrix(5, 1, mtxDataConst5);

		// 构造线性方程组
		LEquations leqs5 = new LEquations(mtxCoef5, mtxConst5);
		
		// 求解三对角线方程组的追赶法
		Matrix mtxResult5 = new Matrix();
		if (leqs5.getRootsetTriDiagonal(mtxResult5))
		{
			System.out.println(mtxResult5);
		}
		else
		{
			System.out.println("失败");
		}
	}
}
