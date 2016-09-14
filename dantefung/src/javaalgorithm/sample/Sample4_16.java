/*
 * 示例程序Sample4_16: LEquations类的病态方程组的求解
 */
package javaalgorithm.sample;

import javaalgorithm.algorithm.Matrix;
import javaalgorithm.algorithm.LEquations;

public class Sample4_16 
{
	public static void main(String[] args)
	{
		// 系数矩阵数据
		double[] mtxDataCoef15 = {
				3.4336,-0.5238,0.67105,-0.15272,
				-0.5238,3.28326,-0.73051,-0.2689,
				0.67105,-0.73051,4.02612,0.01835,
				-0.15272,-0.2689,0.01835,2.75702};
		// 常数矩阵数据
		double[] mtxDataConst15 = {
				-1.0,
				1.5,
				2.5,
				-2.0};
		
		// 构造系数矩阵
		Matrix mtxCoef15 = new Matrix(4, mtxDataCoef15);
		// 构造常数矩阵
		Matrix mtxConst15 = new Matrix(4, 1, mtxDataConst15);

		// 构造线性方程组
		LEquations leqs15 = new LEquations(mtxCoef15, mtxConst15);
		
		// 病态方程组的求解
		Matrix mtxResult15 = new Matrix();
		if (leqs15.getRootsetMorbid(mtxResult15, 60, 0.0001))
		{
			System.out.println(mtxResult15);
		}
		else
		{
			System.out.println("失败");
		}
	}
}
