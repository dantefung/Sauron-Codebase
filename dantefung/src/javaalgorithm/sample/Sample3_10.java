/*
 * 示例程序Sample3_10: Matrix类的矩阵的三角分解
 */
package javaalgorithm.sample;

import javaalgorithm.algorithm.Matrix;

public class Sample3_10
{
	public static void main(String[] args) 
	{
		// 矩阵数据
		double[] mtxData11 = {
				2.0,4.0,4.0,2.0,
				3.0,3.0,12.0,6.0,
				2.0,4.0,-1.0,2.0,
				4.0,2.0,1.0,1.0};

		// 构造矩阵
		Matrix mtx11 = new Matrix(4, mtxData11);

		// 矩阵的三角分解
		Matrix mtxL = new Matrix();
		Matrix mtxU = new Matrix();
		if (mtx11.splitLU(mtxL, mtxU))
		{
			System.out.println("分解后的L矩阵=");
			System.out.println(mtxL);
			System.out.println("-------------------------------"); 
			System.out.println("分解后的U矩阵=");
			System.out.println(mtxU);
		}
		else
		{
			System.out.println("失败");
		}
	}
}
