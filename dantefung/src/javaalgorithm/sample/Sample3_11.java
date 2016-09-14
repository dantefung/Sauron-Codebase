/*
 * 示例程序Sample3_11: Matrix类的一般实矩阵的QR分解
 */
package javaalgorithm.sample;

import javaalgorithm.algorithm.Matrix;

public class Sample3_11 
{
	public static void main(String[] args) 
	{
		// 矩阵数据
		double[] mtxData12 = {
				1.0,1.0,-1.0,
				2.0,1.0,0.0,
				1.0,-1.0,0.0,
				-1.0,2.0,1.0};

		// 构造矩阵
		Matrix mtx12 = new Matrix(4, 3, mtxData12);

		// 一般实矩阵的QR分解
		Matrix mtxQ = new Matrix();
		if (mtx12.splitQR(mtxQ))
		{
			System.out.println("分解后的Q矩阵=");
			System.out.println(mtxQ);
			System.out.println("-------------------------------"); 
			System.out.println("分解后的R矩阵=");
			System.out.println(mtx12);
		}
		else
		{
			System.out.println("失败");
		}
	}
}
