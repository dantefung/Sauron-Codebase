/*
 * 示例程序Sample3_12: Matrix类的一般实矩阵的奇异值分解
 */
package javaalgorithm.sample;

import javaalgorithm.algorithm.Matrix;

public class Sample3_12
{
	public static void main(String[] args) 
	{
		// 矩阵数据
		double[] mtxData13 = {
				1.0,1.0,-1.0,
				2.0,1.0,0.0,
				1.0,-1.0,0.0,
				-1.0,2.0,1.0};

		// 构造矩阵
		Matrix mtx13 = new Matrix(4, 3, mtxData13);

		// 一般实矩阵的奇异值分解
		System.out.println("一般实矩阵的奇异值分解");
		Matrix mtxU1 = new Matrix();
		Matrix mtxV = new Matrix();
		if (mtx13.splitUV(mtxU1, mtxV, 0.0001))
		{
			System.out.println("分解后的U矩阵=");
			System.out.println(mtxU1);
			System.out.println("-------------------------------"); 
			System.out.println("分解后的V矩阵=");
			System.out.println(mtxV);
			System.out.println("-------------------------------"); 
			System.out.println("分解后的A矩阵=");
			System.out.println(mtx13);
		}
		else
		{
			System.out.println("失败");
		}
	}
}
