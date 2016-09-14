/*
 * 示例程序Sample4_11: LEquations类的求解托伯利兹方程组的列文逊方法
 */
package javaalgorithm.sample;

import javaalgorithm.algorithm.Matrix;
import javaalgorithm.algorithm.LEquations;

public class Sample4_11 
{
	public static void main(String[] args)
	{
		// 系数矩阵数据
		double[] mtxDataCoef10 = {
				6.0,5.0,4.0,3.0,2.0,1.0,
				5.0,6.0,5.0,4.0,3.0,2.0,
				4.0,5.0,6.0,5.0,4.0,3.0,
				3.0,4.0,5.0,6.0,5.0,4.0,
				2.0,3.0,4.0,5.0,6.0,5.0,
				1.0,2.0,3.0,4.0,5.0,6.0};
		// 常数矩阵数据
		double[] mtxDataConst10 = {
				11.0,
				9.0,
				9.0,
				9.0,
				13.0,
				17.0};
		
		// 构造系数矩阵
		Matrix mtxCoef10 = new Matrix(6, mtxDataCoef10);
		// 构造常数矩阵
		Matrix mtxConst10 = new Matrix(6, 1, mtxDataConst10);

		// 构造线性方程组
		LEquations leqs10 = new LEquations(mtxCoef10, mtxConst10);
		
		// 求解托伯利兹方程组的列文逊方法
		Matrix mtxResult10 = new Matrix();
		if (leqs10.getRootsetTlvs(mtxResult10))
		{
			System.out.println(mtxResult10);
		}
		else
		{
			System.out.println("失败");
		}
	}
}
