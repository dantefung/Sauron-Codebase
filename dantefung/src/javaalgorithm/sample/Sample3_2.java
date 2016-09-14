/*
 * 示例程序Sample3_2: Matrix类的基础运算
 */
package javaalgorithm.sample;

import javaalgorithm.algorithm.Matrix;

public class Sample3_2 
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
		double[] mtxData3 = {
				 4,  5, -1,
				 2, -2,  6,
				 7,  8,  1,
				 0,  3, -5,
				 9,  8, -6};
		double doubleNum = 2.0;

		// 构造矩阵
		Matrix mtx1 = new Matrix(4, 5, mtxData1);
		Matrix mtx2 = new Matrix(4, 5, mtxData2);
		Matrix mtx3 = new Matrix(5, 3, mtxData3);
		
		// 矩阵基本运算
		Matrix mtxAdd = mtx1.add(mtx2);
		Matrix mtxSubtract = mtx1.subtract(mtx2);
		Matrix mtxMultiplyNumber = mtx1.multiply(doubleNum);
		Matrix mtxMultiply = mtx1.multiply(mtx3);
		Matrix mtxTranspose = mtx1.transpose();
		
		// 显示结果
		System.out.println("mtx1 = ");
		System.out.println(mtx1);
		System.out.println("\nmtx2 = ");
		System.out.println(mtx2);
		System.out.println("\nmtx3 = ");
		System.out.println(mtx3);
		System.out.println("\nmtx1 + mtx2 = ");
		System.out.println(mtxAdd);
		System.out.println("\nmtx1 - mtx2 = ");
		System.out.println(mtxSubtract);
		System.out.println("\nmtx1 * " + new Double(doubleNum).toString() + " = ");
		System.out.println(mtxMultiplyNumber);
		System.out.println("\nmtx1 * mtx2 = ");
		System.out.println(mtxMultiply);
		System.out.println("\nTranspose(mtx1) = ");
		System.out.println(mtxTranspose);
	}
}
