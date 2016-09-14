/*
 * 示例程序Sample4_10: LEquations类的求解大型稀疏方程组的全选主元高斯－约去消去法
 */
package javaalgorithm.sample;

import javaalgorithm.algorithm.Matrix;
import javaalgorithm.algorithm.LEquations;

public class Sample4_10 
{
	public static void main(String[] args)
	{
		// 系数矩阵数据
		double[] mtxDataCoef9 = {
				0.0,0.0,-1.0,0.0,0.0,0.0,2.0,0.0,
				0.0,6.0,0.0,0.0,0.0,-6.0,0.0,0.0,
				0.0,0.0,0.0,2.0,0.0,0.0,0.0,-4.0,
				3.0,0.0,0.0,0.0,-2.0,0.0,1.0,0.0,
				0.0,0.0,6.0,0.0,0.0,0.0,5.0,0.0,
				1.0,0.0,0.0,0.0,-3.0,0.0,0.0,2.0,
				0.0,4.0,0.0,-1.0,0.0,0.0,0.0,0.0,
				0.0,0.0,1.0,0.0,-1.0,0.0,0.0,-2.0};
		// 常数矩阵数据
		double[] mtxDataConst9 = {
				4.0,
				6.0,
				-8.0,
				-2.0,
				27.0,
				-9.0,
				2.0,
				-4.0};
		
		// 构造系数矩阵
		Matrix mtxCoef9 = new Matrix(8, mtxDataCoef9);
		// 构造常数矩阵
		Matrix mtxConst9 = new Matrix(8, 1, mtxDataConst9);

		// 构造线性方程组
		LEquations leqs9 = new LEquations(mtxCoef9, mtxConst9);
		
		// 求解大型稀疏方程组的全选主元高斯－约去消去法
		Matrix mtxResult9 = new Matrix();
		if (leqs9.getRootsetGgje(mtxResult9))
		{
			System.out.println(mtxResult9);
		}
		else
		{
			System.out.println("失败");
		}
	}
}
