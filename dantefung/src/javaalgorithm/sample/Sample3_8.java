/*
 * 示例程序Sample3_8: Matrix类的求矩阵秩的全选主元高斯消去法
 */
package javaalgorithm.sample;

import javaalgorithm.algorithm.Matrix;

public class Sample3_8
{
	public static void main(String[] args) 
	{
		// 矩阵数据
		double[] mtxData9 = {
				3.0,-3.0,-2.0,4.0,
				5.0,-5.0,1.0,8.0,
				11.0,8.0,5.0,-7.0,
				5.0,-1.0,-3.0,-1.0};

		// 构造矩阵
		Matrix mtx9 = new Matrix(4, mtxData9);

		// 求矩阵秩的全选主元高斯消去法
		int rankValue = mtx9.computeRankGauss();
		System.out.println(new Integer(rankValue).toString());
	}
}
