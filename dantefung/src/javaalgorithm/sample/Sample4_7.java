/*
 * 示例程序Sample4_7: LEquations类的一般带型方程组的求解
 */
package javaalgorithm.sample;

import javaalgorithm.algorithm.Matrix;
import javaalgorithm.algorithm.LEquations;

public class Sample4_7 
{
	public static void main(String[] args)
	{
		// 系数矩阵数据
		double[] mtxDataCoef6 = {
				3.0,-4.0,1.0,0.0,0.0,0,0,0,
				-2.0,-5.0,6.0,1.0,0.0,0,0,0,
				1.0,3.0,-1.0,2.0,-3.0,0,0,0,
				0,2.0,5.0,-5.0,6.0,-1.0,0,0,
				0,0,-3.0,1.0,-1.0,2.0,-5.0,0,
				0,0,0,6.0,1.0,-3.0,2.0,-9.0,
				0,0,0,0,-4.0,1.0,-1.0,2.0,
				0,0,0,0,0,5.0,1.0,-7.0};
		// 常数矩阵数据
		double[] mtxDataConst6 = {
				13.0,29.0,-13.0,
				-6.0,17.0,-21.0,
				-31.0,-6.0,4.0,
				64.0,3.0,16.0,
				-20.0,1.0,-5.0,
				-22.0,-41.0,56.0,
				-29.0,10.0,-21.0,
				7.0,-24.0,20.0};
		
		// 构造系数矩阵
		Matrix mtxCoef6 = new Matrix(8, mtxDataCoef6);
		// 构造常数矩阵
		Matrix mtxConst6 = new Matrix(8, 3, mtxDataConst6);

		// 构造线性方程组
		LEquations leqs6 = new LEquations(mtxCoef6, mtxConst6);
		
		// 一般带型方程组的求解
		Matrix mtxResult6 = new Matrix();
		if (leqs6.getRootsetBand(5, mtxResult6))
		{
			System.out.println(mtxResult6);
		}
		else
		{
			System.out.println("失败");
		}
	}
}
