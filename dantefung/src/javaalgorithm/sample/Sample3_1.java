/*
 * 示例程序Sample3_1: Matrix类的基本用法
 */
package javaalgorithm.sample;

import javaalgorithm.algorithm.Matrix;

public class Sample3_1 
{
	public static void main(String[] args) 
	{
		// 矩阵数据
		double[] mtxData1 = {
				 1,  3, -2,  0,  4,
				-2, -1,  5, -7,  2,
				 0,  8,  4,  1, -5,
				 3, -3,  2, -4,  1};
		double[] mtxData2 = {
				 4,  2, -7,  0,  3,
				 9,  6,  1,  8, -2,
				-4,  7,  2, -5,  5,
				 9, -8,  3,  6,  5};

		// 构造矩阵
		Matrix mtx1 = new Matrix(4, 5, mtxData1);
		Matrix mtx2 = new Matrix(4, 5, mtxData2);
		Matrix mtx3 = new Matrix(4, 5, mtxData1);
		
		// 显示结果
		System.out.println("mtx1 = ");
		System.out.println(mtx1);
		System.out.println("\nmtx2 = ");
		System.out.println(mtx2);
		System.out.println("\nmtx3 = ");
		System.out.println(mtx3);
		System.out.println("\nmtx1的第2个列向量为（" + mtx1.toStringCol(1, ",") + ")");
		
		// 比较
		if (mtx1.equal(mtx2))
			System.out.println("\nmtx1 = mtx2");
		else
			System.out.println("\nmtx1 != mtx2");
			
		if (mtx1.equal(mtx3))
			System.out.println("\nmtx1 = mtx3");
		else
			System.out.println("\nmtx1 != mtx3");
	}
}
